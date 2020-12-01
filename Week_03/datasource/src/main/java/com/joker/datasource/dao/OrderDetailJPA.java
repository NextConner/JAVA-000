package com.joker.datasource.dao;

import com.joker.datasource.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/1 18:12
 */
@Repository
public interface OrderDetailJPA extends JpaRepository<OrderDetail,Long> {
}
