package com.joker.datasource.dao;

import com.joker.datasource.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJPA extends JpaRepository<Order,Long> {
}
