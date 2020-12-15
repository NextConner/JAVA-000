package com.sharding.demo.controller;

import com.sharding.demo.entity.OrderDetail;
import com.sharding.demo.service.OrderDetailXaService;
import com.sharding.demo.service.ShardingOrderService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/14 12:38
 */

@Slf4j
@RestController
@RequestMapping("/orderDetail/")
public class OrderDetailController {


    static long[] userIds = new long[30];
    static long[] orderIds = new long[30];

    static {
        for (int i = 0; i < 30; i++) {
            orderIds[i] = Math.abs(new Random().nextInt(1000000000));
            userIds[i] = Math.abs(new Random().nextInt(2000000000));
        }
    }

    @Autowired
    private ShardingOrderService shardingOrderService;

    @Autowired
    private OrderDetailXaService xaService;

    @GetMapping("add")
    public String addOrderDetail() throws InterruptedException {

        log.info("当前数据总量:{}", xaService.getCount());
        log.info("清除数据！");
        xaService.clear();
        log.info("当前数据总量:{}", xaService.getCount());

        String[] goods = {"Cooler", "Adidas", "Hades", "miniCooper", "switch", "PS5", "GTA5"};
        Random random = new Random(1000);
        DateTime createTime = DateTime.parse("2020-01-01");
        log.info("xa start");
        for (int i = 0; i < 30; i++) {
            OrderDetail detail = new OrderDetail();
            detail.setCondName(goods[new Random().nextInt(goods.length - 1)]);
            detail.setComdId(Math.abs(new Random().nextInt(1000000000)));
            detail.setCreateTime(createTime.plusMonths(new Random().nextInt(10)).plusDays(new Random(50).nextInt()).plusSeconds(new Random().nextInt(600000)).withYear(2020).toDate());
            detail.setUpdateTime(detail.getCreateTime());
            detail.setId(System.currentTimeMillis());
            TimeUnit.MILLISECONDS.sleep(50);
            detail.setDiscount(BigDecimal.valueOf(random.nextDouble() * 100).setScale(2, BigDecimal.ROUND_HALF_UP));
            detail.setNum(new Random().nextInt(43));
//			detail.setOrderId(Math.abs(new Random().nextInt(1000000000)));
            detail.setOrderId(orderIds[i]);
//			orderIds[i] = detail.getOrderId();
            detail.setPrice(BigDecimal.valueOf(random.nextDouble() * 100).setScale(2, BigDecimal.ROUND_HALF_UP));
            detail.setSellPrice(BigDecimal.valueOf(random.nextDouble() * 100).setScale(2, BigDecimal.ROUND_HALF_UP));
            detail.setShipping(BigDecimal.valueOf(random.nextDouble() * 100).setScale(2, BigDecimal.ROUND_HALF_UP));
            detail.setSkuInfo("is black and weight 20 KG");
            detail.setStatus(new Random().nextInt(3));
            detail.setStoreName("TMALL");
//			detail.setUserId(Math.abs(new Random().nextInt(1000000000)));
            if (i > 5) {
                log.info("插入异常数据!");
                detail.setUserId(7654321);
            } else {
                detail.setUserId(Math.abs(userIds[i]));
            }

//			userIds[i] = detail.getUserId();
            detail.setTotalPrice(BigDecimal.valueOf(detail.getPrice().doubleValue() * detail.getNum()).setScale(2, BigDecimal.ROUND_HALF_UP));
            log.info("当前订单详情,用户ID:{},订单ID:{}", detail.getUserId(), detail.getOrderId());
//            orderXAJpa.save(detail);
            try {
                xaService.addOrderDetail(detail);
            }catch (Exception e){
                log.info("异常后数据总量:{}", xaService.getCount());
                throw e;
            }
        }


        log.info("xa end");
        return "add over";
    }

    @GetMapping("get")
    public String getOrderDetail() {
       return String.valueOf(xaService.getCount());
    }


}
