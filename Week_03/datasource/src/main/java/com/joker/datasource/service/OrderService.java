package com.joker.datasource.service;


import com.joker.datasource.annotation.Source;
import com.joker.datasource.consts.DataSourceType;
import com.joker.datasource.dao.OrderJPA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/30 12:42
 */

@Slf4j
//@Service
public class OrderService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    //不加数据源注解
    public List<Map<String,Object>> getOrder(){
        String SQL = "SELECT track_info FROM t_user_order` limit 2";
        List<Map<String,Object>> list =  jdbcTemplate.queryForList(SQL) ;
        return list;
    }

    //不指定数据源
    @Source
    public List<Map<String,Object>> getOrderOne(){
        String SQL = "SELECT track_info FROM t_user_order limit 2";
        List<Map<String,Object>> list =  jdbcTemplate.queryForList(SQL) ;
        return list;
    }

    //指定数据源
    @Source(DataSourceType.SECONDARY)
    public List<Map<String,Object>> getOrderSecond(){
        String SQL = "SELECT id,user_id FROM t_user_order limit 2";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(SQL) ;
        return list;
    }


}
