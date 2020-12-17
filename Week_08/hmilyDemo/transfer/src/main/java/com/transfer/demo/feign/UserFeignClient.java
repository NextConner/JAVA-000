package com.transfer.demo.feign;

import com.transfer.demo.entity.UserWallet;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * user-service 的feign 类， 提供user-service 的远程调用
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/10 18:05
 */
@FeignClient(value = "user-service")
public interface UserFeignClient {

    /**
     * 根据userId 获取UserWallet 信息
     * @param userId 用户ID
     * @return
     */
    @Hmily
    @RequestMapping("/user/v1/getUserWallet")
    UserWallet getUserWallet(@RequestParam("userId") Long userId);

    /**
     * 1.更新用户账号信息
     * 2.参与分布式事务的方法
     * @param userWallet
     * @return
     */
    @Hmily
    @RequestMapping("/user/v1/updateUserWallet")
    UserWallet updateUserWallet(@RequestBody UserWallet userWallet);

    @RequestMapping("/user/v1/getAllUser")
    List<UserWallet> getAllUser();

}
