package com.userService.demo.controller;

import com.userService.demo.entity.UserWallet;
import com.userService.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/10 14:22
 */
@RestController
@RequestMapping("/user/v1/")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("getUserWallet")
    public UserWallet getUserWallet(@RequestParam("userId") Long userId){
        return userService.getUserWallet(userId);
    }

    @RequestMapping("updateUserWallet")
    public UserWallet updateUserWallet(@RequestBody UserWallet userWallet){
        return userService.saveUserWallet(userWallet);
    }

}
