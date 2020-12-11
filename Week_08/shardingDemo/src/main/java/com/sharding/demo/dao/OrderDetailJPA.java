package com.sharding.demo.dao;


import com.sharding.demo.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/1 18:12
 */
@Repository
public interface OrderDetailJPA extends JpaRepository<OrderDetail,Long> {

    @Query(value = "FROM OrderDetail WHERE orderId = :orderId")
    public List<OrderDetail> selectListByOrderId(@Param("orderId") long orderId);

    @Query(value = "FROM OrderDetail WHERE userId = :userId AND orderId = :orderId ")
    public List<OrderDetail> selectListByUserAndOrder(@Param("userId") long userId , @Param("orderId") long orderId);

}
