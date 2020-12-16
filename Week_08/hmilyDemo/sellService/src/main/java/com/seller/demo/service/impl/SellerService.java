package com.seller.demo.service.impl;

import com.seller.demo.dao.SellerWalletJpa;
import com.seller.demo.entity.SellerWallet;
import com.seller.demo.service.ISellerService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/10 0:09
 */
@Slf4j
@Service
public class SellerService implements ISellerService {

    @Autowired
    private SellerWalletJpa jpa;

    @Override
    @HmilyTCC(confirmMethod = "confirm" ,cancelMethod = "cancel")
    public SellerWallet update(SellerWallet sellerWallet){
        return jpa.save(sellerWallet);
    }

    @Override
    public SellerWallet getSellerWallet(Long id){
        return jpa.findById(id).get();
    }

    public SellerWallet confirm(SellerWallet sellerWallet){
        log.info("seller update confirm");
        return new SellerWallet();
    }

    public SellerWallet cancel(SellerWallet sellerWallet){
        log.info("seller update cancel");
        return new SellerWallet();
    }

}
