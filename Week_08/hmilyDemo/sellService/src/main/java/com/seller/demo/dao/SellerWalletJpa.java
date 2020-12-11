package com.seller.demo.dao;

import com.seller.demo.entity.SellerWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/10 0:08
 */
@Repository
public interface SellerWalletJpa extends JpaRepository<SellerWallet,Long> {
}
