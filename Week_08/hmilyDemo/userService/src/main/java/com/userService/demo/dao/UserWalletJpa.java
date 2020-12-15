package com.userService.demo.dao;

import com.userService.demo.entity.UserWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/10 10:05
 */
@Repository
public interface UserWalletJpa  extends JpaRepository<UserWallet,Long> {

}
