package com.joker.datasource.service;


import com.joker.datasource.annotation.Source;
import com.joker.datasource.consts.DataSourceType;
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
@Service
public class OrderService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Source
    public List<Map<String,Object>> getOrder(){
        String SQL = "SELECT track_info FROM `order` limit 2";
        List<Map<String,Object>> list =  jdbcTemplate.queryForList(SQL) ;
        return list;
    }

    @Source(DataSourceType.SECONDARY)
    public List<Map<String,Object>> getOrderSecond(){
        String SQL = "SELECT id,user_id FROM `order` limit 2";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(SQL) ;
        return list;
    }

    @Source
    public int updateUserOrder(String userId){
        String SQL = "UPDATE `order` SET track_info = '转运中' WHERE  user_id = ?";
        return jdbcTemplate.update(SQL,userId);
    }

}
