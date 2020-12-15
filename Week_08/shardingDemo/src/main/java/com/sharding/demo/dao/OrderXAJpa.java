package com.sharding.demo.dao;

import com.sharding.demo.entity.OrderDetail;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.hibernate.annotations.SQLDelete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 将当前jap下所有方法开启xa支持
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/13 22:19
 */

@Repository
public interface OrderXAJpa extends JpaRepository<OrderDetail,Long> {

    @Query("FROM OrderDetail WHERE userId = :userId AND orderId = :orderId ")
    List<OrderDetail> selectList(long userId, long orderId);

}
