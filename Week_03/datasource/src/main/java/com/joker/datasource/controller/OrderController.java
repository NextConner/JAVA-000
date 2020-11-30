package com.joker.datasource.controller;

import com.joker.datasource.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/30 18:04
 */
@Slf4j
@RequestMapping("/order/")
@RestController
public class OrderController {

    @Autowired
    private OrderService service;


    @GetMapping("writeDB")
    public String getOrder(){
        return service.getOrder().toString();
    }

    @GetMapping("readDB")
    public String getOrder1(){
        return service.getOrderSecond().toString();
    }

}
