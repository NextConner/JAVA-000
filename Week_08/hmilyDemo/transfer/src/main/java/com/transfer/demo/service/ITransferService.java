package com.transfer.demo.service;

import com.transfer.demo.entity.SellerWallet;
import com.transfer.demo.entity.UserWallet;

import java.math.BigDecimal;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/10 17:53
 */

public interface ITransferService {

    boolean transferToSeller(UserWallet userWallet , SellerWallet sellerWallet, BigDecimal transferMoney);

    boolean transferToSellerException(UserWallet userWallet , SellerWallet sellerWallet, BigDecimal transferMoney);

}
