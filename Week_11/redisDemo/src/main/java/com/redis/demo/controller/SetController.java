package com.redis.demo.controller;

import com.redis.demo.entity.Rating;
import org.joda.time.DateTime;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/7/31 11:44
 */
@RestController
@RequestMapping("/redis/set/")
public class SetController {


    public static String TIME_FORMATTER = "YYYY-MM-dd hh:mm:ss:SSSS";
    //使用ThreadLocal将指定的数据绑定到当前线程
    ThreadLocal<String> threadLocal = new ThreadLocal<>();
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;


    /**
     *  redis  set 结构
     **/
    @RequestMapping("zsetAdd")
    public void setAdd(String key){

        int i=200;
        int j=0;
        redisTemplate.expire(key,60, TimeUnit.SECONDS);
        //sorted set
        ZSetOperations operations =redisTemplate.opsForZSet();
        while(i>0){
            i--;
            DateTime now = DateTime.now();
            operations.add(key,now.toString(),i*1.0);
        }
        //获取指定score范围的数据
        Set rangeScore = operations.rangeByScore(key,5.0,15.0);
        for(Object str : rangeScore){
            System.out.print(str.toString() + " : ");
        }

        Set objectWithScore = operations.reverseRangeByScoreWithScores(key,5,15);
    }


    @RequestMapping("hashAdd")
    public void hashStruct(){

        redisTemplate.delete("rating");
        Rating rating = new Rating();
        rating.setRatingAge(10);
        rating.setLevel("1");
        rating.setSystem("cn");

        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put("rating","age",rating.getRatingAge());
        hashOperations.put("rating","level",rating.getLevel());
        hashOperations.put("rating","system",rating.getSystem());
        System.out.println(hashOperations.get("rating","age"));
        System.out.println(hashOperations.get("rating","level"));
        System.out.println(hashOperations.get("rating","system"));
        hashOperations.put("rating","age",21);


        System.out.println(hashOperations.get("rating","age"));
    }


    @RequestMapping("transactionData")
    public void transaction(String key){

        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.delete(key);
        redisTemplate.watch(key);
        redisTemplate.multi();
        redisTemplate.opsForValue().set(key,1);
        for(int i=0;i<5;i++){
            redisTemplate.opsForValue().set(key,i);
//            if(i==3){
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        redisTemplate.opsForValue().set(key,10);
//                    }
//                }).start();
//            }
        }
        redisTemplate.exec();
        System.out.println(redisTemplate.opsForValue().get(key));
        assert  redisTemplate.opsForValue().get(key).equals(10);
        assert  !redisTemplate.opsForValue().get(key).equals(4);

    }


}
