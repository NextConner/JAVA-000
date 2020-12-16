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
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    private Map<String, Long> hasExecute = new ConcurrentHashMap<>(16);


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
        userWallet.setFrozenFee(transferMoney);
        userWallet.setUpdateTime(new Date());
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
        transferRecordJpa.save(transferRecord);

        //记录try已执行
        hasExecute.put(userWallet.getUserId().toString() +  sellerWallet.getSellerId().toString() , sellerWallet.getSellerId());
        return true;
    }

    /**
     * 模拟转账异常
     * @param userWallet
     * @param sellerWallet
     * @param transferMoney
     * @return
     */
    @HmilyTCC(confirmMethod = "transferConfirm", cancelMethod = "transferCancel")
    @Override
    public boolean transferToSellerException(UserWallet userWallet, SellerWallet sellerWallet, BigDecimal transferMoney) {
        /**
         * TCC try 预留业务资源，在这个转账的场景中，业务资源就是用户将要转账的金额
         */

        //转账
        userWallet.setFee(userWallet.getFee().subtract(transferMoney));
        userWallet.setOutcome(userWallet.getOutcome().add(transferMoney));
        //添加冻结部分资金
        userWallet.setFrozenFee(transferMoney);
        userWallet.setUpdateTime(new Date());
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
        transferRecordJpa.save(transferRecord);

        //记录try已执行
        hasExecute.put(userWallet.getUserId().toString() +  sellerWallet.getSellerId().toString() , sellerWallet.getSellerId());
//        System.out.println(1/0);
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
        String key = userWallet.getUserId().toString() + sellerWallet.getSellerId().toString();
        if (hasExecute.containsKey(key) && sellerWallet.getSellerId().equals(hasExecute.get(key))) {

            //转账
            sellerWallet.setFee(sellerWallet.getFee().add(transferMoney));
            sellerWallet.setIncome(sellerWallet.getIncome().add(transferMoney));
            sellerWallet.setUpdateTime(new Date());
            sellerFeignClient.updateSeller(sellerWallet);
            log.info("商户转账完成！");
            userWallet.setFrozenFee(new BigDecimal(0));
            userFeignClient.updateUserWallet(userWallet);
            log.info("用户转账完成，更新用户账户信息！");

            System.out.println(1/0);
            //清除try记录
            hasExecute.remove(key);
            return true;
        } else {
            log.info("try 未执行 直接进行了 confirm,取消此次 confirm! ");
            return false;
        }
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
        String key = userWallet.getUserId().toString() + sellerWallet.getSellerId().toString();

        if (hasExecute.containsKey(key) && sellerWallet.getSellerId().equals(hasExecute.get(key))) {

            UserWallet dbUserWallet = userFeignClient.getUserWallet(userWallet.getId());
            if (dbUserWallet.getFrozenFee().compareTo(transferMoney) ==0  && userWallet.getFee().compareTo(dbUserWallet.getFee()) ==0) {
                synchronized (userWallet) {
                    userWallet.setFee(userWallet.getFee().add(transferMoney));
                    userWallet.setOutcome(userWallet.getOutcome().subtract(transferMoney));
                    userWallet.setUpdateTime(new Date());
                    //只更新现有的冻结金额
                    userWallet.setFrozenFee(userWallet.getFrozenFee().subtract(transferMoney));
                    userFeignClient.updateUserWallet(userWallet);
                }
            } else {
                log.info("用户账户信息不一致，需手动修复！");
            }
            hasExecute.remove(key);
            return true;
        }else{
            log.info("try 未执行 直接进行了 cancel,取消此次 cancel! ");
            return false;
        }

    }


}
