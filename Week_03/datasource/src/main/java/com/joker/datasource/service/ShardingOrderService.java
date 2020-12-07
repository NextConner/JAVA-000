package com.joker.datasource.service;

import com.joker.datasource.dao.OrderDetailJPA;
import com.joker.datasource.dao.OrderJPA;
import com.joker.datasource.entity.OrderDetail;
import com.joker.datasource.entity.UserOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/1 13:46
 */

@Service
public class ShardingOrderService {

    /**
     *  使用sharding-jdbc 的主从和读写分离数据源
     */
    @Autowired
    private OrderJPA orderJPA;

    @Autowired
    private OrderDetailJPA orderDetailJPA;

    public UserOrder addOrder(UserOrder order){
        return orderJPA.save(order);
    }

    public List<UserOrder> addOrders(List<UserOrder> orders){
        return orderJPA.saveAll(orders);
    }

    public UserOrder getOne(){
        return orderJPA.findAll().get(0);
    }

    public long count(){
        return orderJPA.count();
    }

    public void syncSlave(){
        orderJPA.slave0DBWrite();
        orderJPA.slave1DBWrite();
    }

    public List<OrderDetail> addOrderDetails(List<OrderDetail> orderDetails){
        return orderDetailJPA.saveAll(orderDetails);
    }

    public long countDate(){
        return orderDetailJPA.count();
    }

}
