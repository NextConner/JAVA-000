package com.transfer.demo.feign;


import com.transfer.demo.entity.SellerWallet;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * seller-service 的feign类，用于远程调用seller 服务
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/10 9:53
 */
@FeignClient(value = "seller-service")
public interface SellerFeignClient {

    /**
     * 根据 sellerId 获取 seller 对象信息
     * @param sellerId
     * @return
     */
    @Hmily
    @RequestMapping("/seller/v1/getSellerWallet")
    SellerWallet getSeller(@RequestParam("sellerId") Long sellerId);

    /**
     * 1.更新商家账户信息
     * 2.参与分布式事务的方法
     * @param sellerWallet
     * @return
     */
    @Hmily
    @RequestMapping("/seller/v1/updateSellerWallet")
    SellerWallet updateSeller(@RequestBody SellerWallet sellerWallet);

    /**
     *  新增商家账户信息
     * @param sellerWallet
     * @return
     */
    @Hmily
    @RequestMapping("/seller/v1/addSellerWallet")
    SellerWallet addSeller(@RequestBody SellerWallet sellerWallet);


}
