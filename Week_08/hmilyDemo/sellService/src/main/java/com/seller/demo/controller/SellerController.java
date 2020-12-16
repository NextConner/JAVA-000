package com.seller.demo.controller;

import com.seller.demo.entity.SellerWallet;
import com.seller.demo.service.impl.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/10 0:12
 */
@RestController
@RequestMapping("/seller/v1/")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @RequestMapping("getSellerWallet")
    public SellerWallet getSellerWallet(Long sellerId){
        SellerWallet sellerWallet = sellerService.getSellerWallet(sellerId);
        return sellerWallet;
    }

    @RequestMapping("updateSellerWallet")
    public SellerWallet updateSellerWallet(@RequestBody SellerWallet sellerWallet){
        return sellerService.update(sellerWallet);
    }


}
