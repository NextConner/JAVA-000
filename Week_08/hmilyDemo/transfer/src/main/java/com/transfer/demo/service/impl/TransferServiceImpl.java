package com.transfer.demo.service.impl;

import com.netflix.discovery.converters.Auto;
import com.transfer.demo.entity.SellerWallet;
import com.transfer.demo.entity.UserWallet;
import com.transfer.demo.feign.SellerFeignClient;
import com.transfer.demo.feign.UserFeignClient;
import com.transfer.demo.service.ITransferService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/10 18:03
 */
@Slf4j
@Repository
public class TransferServiceImpl implements ITransferService {


    private ThreadLocal<UserWallet> userWalletThreadLocal = new ThreadLocal<>();
    private ThreadLocal<SellerWallet> sellerWalletThreadLocal = new ThreadLocal<>();

    @Autowired
    private SellerFeignClient sellerFeignClient;
    @Autowired
    private UserFeignClient userFeignClient;

    @HmilyTCC(confirmMethod = "transferConfirm" , cancelMethod = "transferCancel")
    @Override
    public String transferToSeller(Long userId, Long sellerId, BigDecimal transferMoney) {

        /**
         * TCC try
         */
        UserWallet userWallet = userFeignClient.getUserWallet(userId);
        if(null == userWallet){
            log.info("用户不存在!");
            throw new RuntimeException("用户不存在!");
        }
        if(userWallet.getFee().compareTo(transferMoney)<1){
            log.info("用户账户余额不足！");
            throw new RuntimeException("用户账户余额不足！");
        }
        //填充到本地线程，用做confirm 和 cancel
        SellerWallet sellerWallet = sellerFeignClient.getSeller(sellerId);
        if(null == sellerWallet){
            log.info("商户不存在！");
            throw new RuntimeException("商户不存在！");
        }

        //转账
        userWallet.setFee(userWallet.getFee().subtract(transferMoney));
        userWallet.setOutcome(userWallet.getOutcome().add(transferMoney));
        userWallet.setUpdateTime(new Date());
        userWalletThreadLocal.set(userWallet);

        sellerWallet.setFee(sellerWallet.getFee().add(transferMoney));
        sellerWallet.setIncome(sellerWallet.getIncome().add(transferMoney));
        sellerWallet.setUpdateTime(new Date());
        sellerWalletThreadLocal.set(sellerWallet);

        return "success";
    }

    @Override
    public String transferToUser(Long userId, Long sellerId, BigDecimal transferMoney) {
        return null;
    }


    /**
     *  hmily TCC confirm 方法
     * @param userId 用户ID
     * @param sellerId 商户ID
     * @param transferMoney 转账金额
     */
    public void transferConfirm(Long userId, Long sellerId, BigDecimal transferMoney){
        log.info("=====================================transferConfirm=======================");
        userFeignClient.updateUserWallet(userWalletThreadLocal.get());
        sellerFeignClient.updateSeller(sellerWalletThreadLocal.get());
    }

    /**
     *  hmily TCC cancel 方法
     * @param userId 用户ID
     * @param sellerId 商户ID
     * @param transferMoney 转账金额
     */
    public void transferCancel(Long userId, Long sellerId, BigDecimal transferMoney){

        log.info("=====================================transferCancel=======================");
        userWalletThreadLocal.remove();
        sellerWalletThreadLocal.remove();
    }

}
