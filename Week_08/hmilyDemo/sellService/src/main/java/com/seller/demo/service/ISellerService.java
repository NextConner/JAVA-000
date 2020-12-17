package com.seller.demo.service;

import com.seller.demo.entity.SellerWallet;

import java.util.List;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/14 20:42
 */
public interface ISellerService {

    public SellerWallet update(SellerWallet sellerWallet);

    public SellerWallet getSellerWallet(Long id);

    List<SellerWallet> getAllSeller();
}
