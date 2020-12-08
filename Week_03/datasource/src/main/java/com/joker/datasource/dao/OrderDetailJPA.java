package com.joker.datasource.dao;

import com.joker.datasource.entity.OrderDetail;
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
    @Query(value = "FROM OrderDetail WHERE userId = :userId")
    public List<OrderDetail> selectList(@Param("userId") long userId);

}
