package com.joker.datasource.service;

import com.joker.datasource.annotation.Source;
import com.joker.datasource.consts.DataSourceType;
import com.joker.datasource.dao.OrderJPA;
import com.joker.datasource.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderJPAService {

    @Autowired
    private OrderJPA orderJPA;

    public void clearMaster(){
        orderJPA.deleteAll();
    }
    @Source(DataSourceType.SECONDARY)
    public void clearSlave(){
        orderJPA.deleteAll();
    }

    //不添加数据源注解
    public List<Order> findOrders(){
        return orderJPA.findAll();
    }

    //不指定数据源
    @Source
    public List<Order> findOrdersOne(){
        return orderJPA.findAll();
    }

    //指定从库数据源
    @Source(DataSourceType.SECONDARY)
    public List<Order> findOrdersSecond(){
        return orderJPA.findAll();
    }

    @Source
    public List<Order> addOrders(List<Order> orderList){
        return orderJPA.saveAll(orderList);
    }

    @Source(DataSourceType.SECONDARY)
    public List<Order> addOrdersToReadDB(List<Order> orderList){
        return orderJPA.saveAll(orderList);
    }

    public Order addOrder(Order order){
        return orderJPA.save(order);
    }

}
