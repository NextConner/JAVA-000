package com.joker.datasource.common;

import com.joker.datasource.consts.DataSourceType;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/30 13:01
 */
public class DataSourceHolder {

    private static ThreadLocal<String> local = new ThreadLocal<>();

    static public void setDataSource(DataSourceType dataSource){
        local.set(dataSource.getType());
    }

    static public String getDataSourceType(){
        return local.get();
    }

    static public void clear(){
        local.remove();
    }

}
