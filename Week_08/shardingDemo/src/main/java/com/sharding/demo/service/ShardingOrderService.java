package com.sharding.demo.service;

import com.sharding.demo.dao.OrderDetailJPA;
import com.sharding.demo.entity.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/1 13:46
 */

@Service
public class ShardingOrderService {


    @Autowired
    private OrderDetailJPA orderDetailJPA;


    public OrderDetail addOrderDetail(OrderDetail orderDetail){
        return orderDetailJPA.save(orderDetail);
    }


    public List<OrderDetail> selectList(long orderId){
        return orderDetailJPA.selectListByOrderId(orderId);
    }

    public List<OrderDetail> selectList(long userId,long orderId){
        return orderDetailJPA.selectListByUserAndOrder(userId,orderId);
    }

    public OrderDetail update(OrderDetail detail){
        return orderDetailJPA.save(detail);
    }

    public void delOrderDetail(OrderDetail detail){
         orderDetailJPA.delete(detail);
    }

}
