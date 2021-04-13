package com.redis.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.redis.demo.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2021/1/21 11:04
 */

@Slf4j
@RestController
@RequestMapping("/order/")
public class OrderController {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @RequestMapping("/add")
    public void addOrder(Integer num) {

        log.info("创建{}笔订单!",num);
        for (int i = 0; i < num; i++) {

            Order  order = Order.builder()
                    .orderId(UUID.randomUUID().toString())
                    .orderDetailId(UUID.randomUUID().toString())
                    .goodsId(UUID.randomUUID().toString())
                    .goodsName("初号机-IVV"+ i +"型")
                    .price(new BigDecimal((i+1)*19))
                    .totalPrice(new BigDecimal((i+1)*3*19))
                    .createTime(new Date())
                    .updateTime(new Date())
                    .build();
            log.info("下单:{}" ,order.toString());
            JSONObject json = new JSONObject();
            json.put("message", order.toString());
            json.put("time", new Date());

            log.info("redis 发布消息:{}", json.toJSONString());
            redisTemplate.convertAndSend("order",JSONObject.toJSONString(order));
        }
        log.info("下单结束");
    }


    @RequestMapping("/lockAdd")
    public void lockAddOrder(Integer num,String lockKey) {

        RLock lock = redissonClient.getLock(lockKey);

        log.info("创建{}笔订单!",num);
        for (int i = 0; i < num; i++) {

            Order  order = Order.builder()
                    .orderId(UUID.randomUUID().toString())
                    .orderDetailId(UUID.randomUUID().toString())
                    .goodsId(UUID.randomUUID().toString())
                    .goodsName("初号机-IVV"+ i +"型")
                    .price(new BigDecimal((i+1)*19))
                    .totalPrice(new BigDecimal((i+1)*3*19))
                    .createTime(new Date())
                    .updateTime(new Date())
                    .build();
            log.info("下单:{}" ,order.toString());JSONObject json = new JSONObject();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        lock.lock(10,TimeUnit.SECONDS);
                        TimeUnit.SECONDS.sleep(8);
                        order.setUpdateTime(new Date());
                        log.info("修改订单时间！");
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        lock.unlock();
                    }
                }
            }).start();
            try {
                lock.lock();
                json.put("message", order.toString());
                json.put("time", new Date());
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
            log.info("redis 发布消息:{}", json.toJSONString());
            redisTemplate.convertAndSend("order",JSONObject.toJSONString(order));
        }
        log.info("下单结束");
    }


    @RequestMapping("/idFilter")
    public void bitMapDistinct(Integer idTotal,String cacheKey){

        for(int i=0;i<idTotal;i++){
            long randomLong = new Random().nextInt(100);
            if(randomLong <0){
                randomLong = Math.abs(randomLong);
            }
            boolean isExist = redissonClient.getBitSet(cacheKey).get(randomLong);
            if(isExist){
                log.info("{}-id已存在！",randomLong);
            }else{
                log.info("新增id-{}!",randomLong);
                redissonClient.getBitSet(cacheKey).set(randomLong);
            }
        }

    }

    /**
     * 模拟访问点击数
     */
    @GetMapping("/click")
    public void click(String key){

        log.info("click -- {}" ,new Date());
        String uv = "UV20210128";
        long countClick = redisTemplate.opsForHyperLogLog().size(key);
        log.info("{} 点击量为:{}",new Date(),countClick);
        redisTemplate.opsForHyperLogLog().add(key,uv+(countClick++));
    }

}
