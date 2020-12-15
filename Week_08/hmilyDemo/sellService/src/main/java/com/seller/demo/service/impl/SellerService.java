package com.seller.demo.service.impl;

import com.seller.demo.dao.SellerWalletJpa;
import com.seller.demo.entity.SellerWallet;
import com.seller.demo.service.ISellerService;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/10 0:09
 */
@Service
public class SellerService implements ISellerService {

    @Autowired
    private SellerWalletJpa jpa;

    public SellerWallet add(SellerWallet sellerWallet){
        return  jpa.save(sellerWallet);
    }

    @Override
    @HmilyTCC(confirmMethod = "confirm" ,cancelMethod = "cancel")
    public SellerWallet update(SellerWallet sellerWallet){
        return jpa.save(sellerWallet);
    }

    public SellerWallet getSellerWallet(Long sellerId){
        return jpa.findById(sellerId).get();
    }

    public SellerWallet confirm(SellerWallet sellerWallet){
        System.out.println("seller update confirm");
        return new SellerWallet();
    }

    public SellerWallet cancel(SellerWallet sellerWallet){
        System.out.println("seller update cancel");
        return new SellerWallet();
    }

}
