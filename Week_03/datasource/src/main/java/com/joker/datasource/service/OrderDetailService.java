package com.joker.datasource.service;

import com.joker.datasource.annotation.Source;
import com.joker.datasource.dao.OrderDetailJPA;
import com.joker.datasource.entity.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailJPA orderDetailJPA;

//    @Source
    public int addOrderDetails(List<OrderDetail> orderDetailList){
        return orderDetailJPA.saveAll(orderDetailList).size();
    }

}
