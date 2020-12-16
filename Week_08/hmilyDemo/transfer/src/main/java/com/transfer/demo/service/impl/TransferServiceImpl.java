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

    private Map<String, Boolean> hadTry = new ConcurrentHashMap<>(16);


    @HmilyTCC(confirmMethod = "transferConfirm", cancelMethod = "transferCancel")
    @Override
    public boolean transferToSeller(UserWallet userWallet, SellerWallet sellerWallet, BigDecimal transferMoney, String key) {

        /**
         * TCC try 预留业务资源，在这个转账的场景中，业务资源就是用户将要转账的金额
         */
        //记录try未完成
        hadTry.put(key, false);

        //转账
        userWallet.setFee(userWallet.getFee().subtract(transferMoney));
        userWallet.setOutcome(userWallet.getOutcome().add(transferMoney));
        //添加冻结部分资金
        userWallet.setFrozenFee(transferMoney);
        userWallet.setUpdateTime(new Date());
        //这里做了第一次update ，会导致用户账户confirm 一次
        userFeignClient.updateUserWallet(userWallet);

        Long recordId = System.currentTimeMillis();
        //添加转账记录
        TransferRecord transferRecord = TransferRecord.builder()
                .id(recordId)
                .userId(userWallet.getId())
                .sellerId(sellerWallet.getId())
                .status(TransferStatus.TRANSFER_ING.getCode())
                .transferFee(transferMoney)
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        transferRecordJpa.save(transferRecord);

        //记录try已完成
        hadTry.put(key, true);
        return true;
    }

    /**
     * 模拟转账try异常
     *
     * @param userWallet
     * @param sellerWallet
     * @param transferMoney
     * @return
     */
    @HmilyTCC(confirmMethod = "transferConfirm", cancelMethod = "transferCancel")
    @Override
    public boolean transferToSellerTryException(UserWallet userWallet, SellerWallet sellerWallet, BigDecimal transferMoney, String key) {
        /**
         * TCC try 预留业务资源，在这个转账的场景中，业务资源就是用户将要转账的金额
         */
        hadTry.put(key, false);

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
                .userId(userWallet.getId())
                .sellerId(sellerWallet.getId())
                .status(TransferStatus.TRANSFER_ING.getCode())
                .transferFee(transferMoney)
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        transferRecordJpa.save(transferRecord);
        log.info("===================== try 异常! =============================");
        log.info("当前用户账户信息:{}; ", userWallet.toString());
        log.info("当前商户账户信息:{}; ", sellerWallet.toString());
        //除0异常
        System.out.println(1 / 0);

        //记录try已执行
        hadTry.put(key, true);
        return true;
    }

    /**
     * hmily TCC confirm 方法
     *
     * @param userWallet    用户对象
     * @param sellerWallet  商户对象
     * @param transferMoney 转账金额
     */
    public boolean transferConfirm(UserWallet userWallet, SellerWallet sellerWallet, BigDecimal transferMoney, String key) {

        log.info("=====================================transferConfirm=======================");
        if (hadTry.containsKey(key) && hadTry.get(key)) {
            //转账
            boolean sellerUpdate = false;
            sellerWallet.setFee(sellerWallet.getFee().add(transferMoney));
            sellerWallet.setIncome(sellerWallet.getIncome().add(transferMoney));
            sellerWallet.setUpdateTime(new Date());

            try {
                // 对confirm 的两次操作进行try,并标记其中的操作，任意一次失败都手动反向操作然后调用cancel
                sellerFeignClient.updateSeller(sellerWallet);
                sellerUpdate = true;
                log.info("商户转账完成！");
                //再次更新用户冻结账户信息
                userWallet.setFrozenFee(new BigDecimal(0));
                userWallet.setUpdateTime(new Date());
                userFeignClient.updateUserWallet(userWallet);
            } catch (Exception e) {
                log.error("转账confirm异常：{}", e);
                if (sellerUpdate) {
                    log.info("商户转账失败，手动回滚!");
                    sellerWallet.setFee(sellerWallet.getFee().subtract(transferMoney));
                    sellerWallet.setIncome(sellerWallet.getIncome().subtract(transferMoney));
                    sellerWallet.setUpdateTime(new Date());
                    //但是这里可能会异常
                    sellerFeignClient.updateSeller(sellerWallet);
                }
                //手动cancel
                transferCancel(userWallet, sellerWallet, transferMoney, key);
            }

            log.info("用户转账完成，更新用户账户信息！");
            //清除try记录
            hadTry.remove(key);
            return true;
        } else {
            log.info("try 执行异常进行了 confirm,取消此次 confirm! ");
            return false;
        }
    }

    @HmilyTCC(confirmMethod = "transferConfirmException", cancelMethod = "transferCancel")
    @Override
    public boolean transferToSellerConfirmException(UserWallet userWallet, SellerWallet sellerWallet, BigDecimal transferMoney, String key) {
        /**
         * TCC try 预留业务资源，在这个转账的场景中，业务资源就是用户将要转账的金额
         */
        hadTry.put(key, false);

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
                .userId(userWallet.getId())
                .sellerId(sellerWallet.getId())
                .status(TransferStatus.TRANSFER_ING.getCode())
                .transferFee(transferMoney)
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        transferRecordJpa.save(transferRecord);
        //记录try已执行
        hadTry.put(key, true);
        return true;
    }

    //confirm 异常
    public boolean transferConfirmException(UserWallet userWallet, SellerWallet sellerWallet, BigDecimal transferMoney, String key) {

        log.info("=====================================transferConfirm=======================");
        if (hadTry.containsKey(key) && hadTry.get(key)) {

            boolean sellerUpdate = false;
            //转账
            sellerWallet.setFee(sellerWallet.getFee().add(transferMoney));
            sellerWallet.setIncome(sellerWallet.getIncome().add(transferMoney));
            sellerWallet.setUpdateTime(new Date());

            try {
                sellerFeignClient.updateSeller(sellerWallet);
                sellerUpdate=true;
                //异常
                System.out.println(1 / 0);
                log.info("商户转账完成！");
                //再次更新用户冻结账户信息
                userWallet.setFrozenFee(new BigDecimal(0));
                userWallet.setUpdateTime(new Date());
                userFeignClient.updateUserWallet(userWallet);
            }catch (Exception e){

                log.info("===================== confirm 异常! =============================");
                log.info("当前用户账户信息:{}; ", userWallet.toString());
                log.info("当前商户账户信息:{}; ", sellerWallet.toString());
                //如果商户转账完成，手动回滚
                if(sellerUpdate){
                    sellerWallet.setFee(sellerWallet.getFee().subtract(transferMoney));
                    sellerWallet.setIncome(sellerWallet.getIncome().subtract(transferMoney));
                    sellerWallet.setUpdateTime(new Date());
                    //这里可能会异常
                    sellerFeignClient.updateSeller(sellerWallet);
                    //手动调用cancel
                    hadTry.put(key,false);
                    transferCancel(userWallet,sellerWallet,transferMoney,key);
                }
                throw e;
            }

            log.info("用户转账完成，更新用户账户信息！");
            //清除try记录
            hadTry.remove(key);
            return true;
        } else {
            log.info("try 执行异常进行了 confirm,取消此次 confirm! ");
            return false;
        }
    }

    /**
     * hmily TCC cancel 方法,取消 try 的影响
     *
     * @param userWallet    用户对象
     * @param sellerWallet  商户对象
     * @param transferMoney 转账金额
     */
    public boolean transferCancel(UserWallet userWallet, SellerWallet sellerWallet, BigDecimal transferMoney, String key) {

        log.info("====================================transferCancel=======================");

        //防悬挂
        if (!hadTry.containsKey(key)) {
            log.info("try 未执行！");
            return false;
        }

        if (hadTry.containsKey(key) && !hadTry.get(key)) {

            userWallet.setFee(userWallet.getFee().add(transferMoney));
            userWallet.setOutcome(userWallet.getOutcome().subtract(transferMoney));
            userWallet.setUpdateTime(new Date());
            //扣减冻结金额而不是将冻结金额清零
            userWallet.setFrozenFee(userWallet.getFrozenFee().subtract(transferMoney));
            userFeignClient.updateUserWallet(userWallet);
            //清除try记录
            hadTry.remove(key);
            log.info("cancel 后用户账户信息:{}", userWallet.toString());
            return true;
        } else {
            log.info("try 未执行或已执行成功但进行了 cancel,取消此次 cancel! ");
            return false;
        }
    }


}
