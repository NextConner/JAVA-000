package com.transfer.demo.service.impl;

import com.transfer.demo.consts.TransferStatus;
import com.transfer.demo.dao.TransferRecordJpa;
import com.transfer.demo.entity.SellerWallet;
import com.transfer.demo.entity.TransferRecord;
import com.transfer.demo.entity.UserWallet;
import com.transfer.demo.feign.SellerFeignClient;
import com.transfer.demo.feign.UserFeignClient;
import com.transfer.demo.service.ITransferService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/10 18:03
 */
@Slf4j
@Service("serviceImpl")
public class TransferServiceImpl implements ITransferService {

    @Autowired
    private SellerFeignClient sellerFeignClient;
    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private TransferRecordJpa transferRecordJpa;

    private ThreadLocal<Long> local = new ThreadLocal<>();

//    @Autowired(required = false)
//    public TransferServiceImpl(SellerFeignClient sellerFeignClient, UserFeignClient userFeignClient, TransferRecordJpa transferRecordJpa) {
//        this.sellerFeignClient = sellerFeignClient;
//        this.userFeignClient = userFeignClient;
//        this.transferRecordJpa = transferRecordJpa;
//    }

    @HmilyTCC(confirmMethod = "transferConfirm", cancelMethod = "transferCancel")
    @Override
    public boolean transferToSeller(UserWallet userWallet, SellerWallet sellerWallet, BigDecimal transferMoney) {

        /**
         * TCC try 预留业务资源，在这个转账的场景中，业务资源就是用户将要转账的金额
         */

        //转账
        userWallet.setFee(userWallet.getFee().subtract(transferMoney));
        userWallet.setOutcome(userWallet.getOutcome().add(transferMoney));
        //添加冻结部分资金
        userWallet.setUpdateTime(new Date());
        //记录到本地变量
        userFeignClient.updateUserWallet(userWallet);
        Long recordId = System.currentTimeMillis();
        //添加转账记录
        TransferRecord transferRecord = TransferRecord.builder()
                .id(recordId)
                .userId(userWallet.getUserId())
                .sellerId(sellerWallet.getSellerId())
                .status(TransferStatus.TRANSFER_ING.getCode())
                .transferFee(transferMoney)
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        //先设置到线程本地变量，用来在cancel 做幂等和空回滚判断
        transferRecordJpa.save(transferRecord);
        local.set(recordId);
        return true;
    }

    /**
     * hmily TCC confirm 方法
     *
     * @param userWallet    用户对象
     * @param sellerWallet  商户对象
     * @param transferMoney 转账金额
     */
    public boolean transferConfirm(UserWallet userWallet, SellerWallet sellerWallet, BigDecimal transferMoney) {
        log.info("=====================================transferConfirm=======================");

//        //转账
        sellerWallet.setFee(sellerWallet.getFee().add(transferMoney));
        sellerWallet.setIncome(sellerWallet.getIncome().add(transferMoney));
        sellerWallet.setUpdateTime(new Date());
        sellerFeignClient.updateSeller(sellerWallet);
        log.info("商户转账完成！");
        Long id = local.get();
        TransferRecord transferRecord = transferRecordJpa.getOne(id);
        transferRecord.setStatus(TransferStatus.TRANSFER_SUC.getCode());
        transferRecordJpa.save(transferRecord);
        log.info("清除本地转账记录！");
        local.remove();
        return true;
    }

    /**
     * hmily TCC cancel 方法
     *
     * @param userWallet    用户对象
     * @param sellerWallet  商户对象
     * @param transferMoney 转账金额
     */
    public boolean transferCancel(UserWallet userWallet, SellerWallet sellerWallet, BigDecimal transferMoney) {

        log.info("====================================transferCancel=======================");
        TransferRecord record = transferRecordJpa.getOne(local.get());
        if (userWallet == null) {
            log.info("用户账户更新失败，空回滚!");
            return true;
        }
        UserWallet dbUserWallet = userFeignClient.getUserWallet(userWallet.getUserId());
        if (userWallet.equals(dbUserWallet)) {
            dbUserWallet.setFee(dbUserWallet.getFee().add(transferMoney));
            dbUserWallet.setUpdateTime(new Date());
            userFeignClient.updateUserWallet(dbUserWallet);
            local.remove();
            log.info("还原用户账户金额:{}", dbUserWallet.toString());

            //更新转账记录
            if (null == record) {
                log.info("转账记录添加失败!");
                record = TransferRecord.builder()
                        .id(System.currentTimeMillis())
                        .userId(userWallet.getUserId())
                        .sellerId(sellerWallet.getSellerId())
                        .status(TransferStatus.TRANSFER_FAIL.getCode())
                        .transferFee(transferMoney)
                        .createTime(new Date())
                        .updateTime(new Date())
                        .build();
            } else {
                record.setStatus(TransferStatus.TRANSFER_FAIL.getCode());
                record.setUpdateTime(new Date());

            }
            transferRecordJpa.save(record);
            local.remove();
            return true;
        } else {
            log.info("用户账户信息不一致，本地信息:{};持久化信息:{}", userWallet.toString(), dbUserWallet.toString());
        }
        return true;
    }


}
