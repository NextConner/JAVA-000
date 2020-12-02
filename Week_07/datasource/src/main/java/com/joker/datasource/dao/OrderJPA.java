package com.joker.datasource.dao;

import com.joker.datasource.entity.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJPA extends JpaRepository<UserOrder,Long> {

    @Query(value = " INSERT INTO slave0.t_user_order SELECT * FROM masterdb.t_user_order ",nativeQuery = true)
    public void slave0DBWrite();

    @Query(value = " INSERT INTO slave1.t_user_order SELECT * FROM masterdb.t_user_order ",nativeQuery = true)
    public void slave1DBWrite();

}
