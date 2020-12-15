package com.userService.demo.service.impl;


import com.userService.demo.dao.UserWalletJpa;
import com.userService.demo.entity.UserWallet;
import com.userService.demo.service.UserService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/10 9:51
 */
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserWalletJpa userJpa;

    @Override
    public List<UserWallet> getUserWallet(){
        return userJpa.findAll();
    }

    @Override
    public boolean checkUserWallet(long userId){
        return userJpa.existsById(userId);
    }

    @Override
    public UserWallet getUserWallet(long userId){
        return userJpa.findById(userId).get();
    }

    @Override
    @HmilyTCC(confirmMethod = "confirm",cancelMethod = "cancel")
    public UserWallet saveUserWallet(UserWallet userWallet){
        return userJpa.save(userWallet);
    }

    @Override
    public UserWallet confirm(UserWallet userWallet){
        System.out.println("update userWallet confirm");
        return new UserWallet();
    }

    @Override
    public UserWallet cancel(UserWallet userWallet){
        System.out.println("update userWallet cancel");
        return new UserWallet();
    }

}
