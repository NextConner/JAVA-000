package com.transfer.demo.feign;


import com.transfer.demo.entity.SellerWallet;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/10 9:53
 */
@FeignClient(value = "seller-service")
public interface SellerFeignClient {

    @RequestMapping("/seller/v1/getSellerWallet")
    SellerWallet getSeller(@RequestParam("sellerId") Long sellerId);

    @Hmily
    @RequestMapping("/seller/v1/updateSellerWallet")
    SellerWallet updateSeller(@RequestBody SellerWallet sellerWallet);

    @RequestMapping("/seller/v1/addSellerWallet")
    SellerWallet addSeller(@RequestBody SellerWallet sellerWallet);


}
