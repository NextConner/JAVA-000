package com.transfer.demo.feign;

import com.transfer.demo.entity.UserWallet;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/10 18:05
 */
@FeignClient(value = "user-service")
public interface UserFeignClient {

    @RequestMapping("/user/v1/getUserWallet")
    UserWallet getUserWallet(@RequestParam("userId") Long userId);

    @Hmily
    @RequestMapping("/user/v1/updateUserWallet")
    UserWallet updateUserWallet(@RequestBody UserWallet userWallet);

}
