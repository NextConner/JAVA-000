package com.userService.demo.service;

import com.userService.demo.dao.UserJpa;
import com.userService.demo.entity.UserWallet;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/10 9:51
 */
@Service
public class UserService {

    @Autowired
    private UserJpa userJpa;

    public List<UserWallet> getUserWallet(){
        return userJpa.findAll();
    }

    public boolean checkUserWallet(long userId){
        return userJpa.existsById(userId);
    }


    public UserWallet getUserWallet(long userId){
        return userJpa.findById(userId).get();
    }

    public UserWallet saveUserWallet(UserWallet userWallet){
        return userJpa.save(userWallet);
    }

}
