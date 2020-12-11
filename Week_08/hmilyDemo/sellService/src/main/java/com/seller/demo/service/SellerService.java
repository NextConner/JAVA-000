package com.seller.demo.service;

import com.seller.demo.dao.SellerWalletJpa;
import com.seller.demo.entity.SellerWallet;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/10 0:09
 */
@Service
public class SellerService {

    @Autowired
    private SellerWalletJpa jpa;

    public SellerWallet add(SellerWallet sellerWallet){
        return  jpa.save(sellerWallet);
    }
    @Hmily
    public SellerWallet update(SellerWallet sellerWallet){
        return jpa.save(sellerWallet);
    }

    public SellerWallet getSellerWallet(Long sellerId){
        return jpa.findById(sellerId).get();
    }

}
