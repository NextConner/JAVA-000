package com.userService.demo.service.impl;


import com.userService.demo.dao.UserWalletJpa;
import com.userService.demo.entity.UserWallet;
import com.userService.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/10 9:51
 */
@Slf4j
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserWalletJpa userJpa;

    @Override
    public UserWallet getUserWallet(long id){
        return userJpa.findById(id).get();
    }

    @Override
    @HmilyTCC(confirmMethod = "confirm",cancelMethod = "cancel")
    public UserWallet saveUserWallet(UserWallet userWallet){
        return userJpa.save(userWallet);
    }

    @Override
    public List<UserWallet> getAllUser() {
        return userJpa.findAll();
    }

    public UserWallet confirm(UserWallet userWallet){
        log.info("update userWallet confirm!");
        return new UserWallet();
    }

    public UserWallet cancel(UserWallet userWallet){
        log.info("update userWallet cancel!");
        return new UserWallet();
    }

}
