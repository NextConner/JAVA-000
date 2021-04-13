package com.redis.demo.config;

import com.alibaba.fastjson.JSONObject;
import com.redis.demo.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2021/1/7 17:52
 */
@Slf4j
@Component
public class RedisSubscriber  extends MessageListenerAdapter {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {

        byte[] channel = message.getChannel();
        byte[] body = message.getBody();

        String msg = convertByteToStr(body);
        String topic = convertByteToStr(channel);
        if("order".equals(topic)){
            Order order = JSONObject.parseObject(body,Order.class);
            log.info("处理订单:{}",order.toString());
        }
        log.info("接收到主题:{}的消息，内容为:{}",topic,msg);
    }

    private String convertByteToStr(byte[] bytes){
        return redisTemplate.getStringSerializer().deserialize(bytes);
    }
}
