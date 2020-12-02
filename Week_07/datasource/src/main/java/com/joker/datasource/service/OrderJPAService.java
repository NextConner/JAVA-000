package com.joker.datasource.service;

import com.joker.datasource.annotation.Source;
import com.joker.datasource.consts.DataSourceType;
import com.joker.datasource.dao.OrderJPA;
import com.joker.datasource.entity.UserOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderJPAService {

    @Autowired
    private OrderJPA orderJPA;

    @Source(DataSourceType.PRIMARY)
    public void clearMaster(){
        orderJPA.deleteAll();
    }
    @Source(DataSourceType.SECONDARY)
    public void clearSlave(){
        orderJPA.deleteAll();
    }

    //不添加数据源注解
    public List<UserOrder> findUserOrders(){
        return orderJPA.findAll();
    }

    //不指定数据源
    @Source
    public List<UserOrder> findUserOrdersOne(){
        return orderJPA.findAll();
    }

    //指定从库数据源
    @Source(DataSourceType.SECONDARY)
    public List<UserOrder> findUserOrdersSecond(){
        return orderJPA.findAll();
    }

    @Source
    public List<UserOrder> addUserOrders(List<UserOrder> orderList){
        return orderJPA.saveAll(orderList);
    }

    @Source(DataSourceType.SECONDARY)
    public List<UserOrder> addUserOrdersToReadDB(List<UserOrder> orderList){
        return orderJPA.saveAll(orderList);
    }

    public UserOrder addUserOrder(UserOrder order){
        return orderJPA.save(order);
    }

    public List<UserOrder> addUserOrderList(List<UserOrder> orderList){
        return orderJPA.saveAll(orderList);
    }

}
