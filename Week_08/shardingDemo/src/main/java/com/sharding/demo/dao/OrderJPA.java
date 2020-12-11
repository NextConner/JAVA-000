package com.sharding.demo.dao;

import org.springframework.data.jpa.repository.Query;

//@Repository
public interface OrderJPA{

    @Query(value = " INSERT INTO slave0.t_user_order SELECT * FROM masterdb.t_user_order ",nativeQuery = true)
    public void slave0DBWrite();

    @Query(value = " INSERT INTO slave1.t_user_order SELECT * FROM masterdb.t_user_order ",nativeQuery = true)
    public void slave1DBWrite();

}
