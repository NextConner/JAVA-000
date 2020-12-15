package com.sharding.demo.service;

import com.sharding.demo.dao.OrderDetailXaDao;
import com.sharding.demo.entity.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/14 12:39
 */

@Service
public class OrderDetailXaService {


    @Autowired
    private OrderDetailXaDao detailXaDao;

    public void addOrderDetail(OrderDetail orderDetail){
        detailXaDao.insert(orderDetail);
    }

    public void updateOrderDetail(OrderDetail orderDetail){
        detailXaDao.update(orderDetail);
    }


    public String getOrderDetail(Long userId,Long orderId) {
        return detailXaDao.selectAll(userId,orderId);
    }

    public int getCount(){
        return detailXaDao.selectAll();
    }

    public int clear(){
        return detailXaDao.clear();
    }

}
