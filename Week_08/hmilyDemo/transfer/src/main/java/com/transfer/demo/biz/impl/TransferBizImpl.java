package com.transfer.demo.biz.impl;

import com.transfer.demo.biz.ITransferBzi;
import com.transfer.demo.entity.SellerWallet;
import com.transfer.demo.entity.UserWallet;
import com.transfer.demo.feign.SellerFeignClient;
import com.transfer.demo.feign.UserFeignClient;
import com.transfer.demo.service.ITransferService;
import com.transfer.demo.service.impl.TransferServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/12 16:51
 */
@Slf4j
@Service("transferBzi")
public class TransferBizImpl implements ITransferBzi {

    private final ITransferService transferService;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private SellerFeignClient sellerFeignClient;

    @Autowired(required = false)
    public TransferBizImpl(ITransferService transferService){
        this.transferService = transferService;
    }


    @Override
    public boolean transfer(Long userId, Long sellerId , BigDecimal transferMoney){
        log.info("transferBiz transfer to seller");

        UserWallet userWallet = userFeignClient.getUserWallet(userId);
        SellerWallet sellerWallet = sellerFeignClient.getSeller(sellerId);

        if (null == userWallet) {
            log.info("用户不存在!");
            throw new RuntimeException("用户不存在!");
        }
        if (userWallet.getFee().compareTo(transferMoney) < 1) {
            log.info("用户账户余额不足！");
            throw new RuntimeException("用户账户余额不足！");
        }
        if (null == sellerWallet) {
            log.info("商户不存在！");
            throw new RuntimeException("商户不存在！");
        }

        return transferService.transferToSeller(userWallet,sellerWallet,transferMoney);
    }


}
