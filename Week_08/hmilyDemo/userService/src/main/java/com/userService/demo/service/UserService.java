package com.userService.demo.service;

import com.userService.demo.entity.UserWallet;

import java.util.List;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/10 9:51
 */

public interface UserService {


    public List<UserWallet> getUserWallet();

    public boolean checkUserWallet(long userId);

    public UserWallet getUserWallet(long userId);

    public UserWallet saveUserWallet(UserWallet userWallet);

    public UserWallet confirm(UserWallet userWallet);

    public UserWallet cancel(UserWallet userWallet);

}
