package com.joker.datasource.common;

import com.joker.datasource.consts.DataSourceType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
        String type =  local.get();
        return StringUtils.hasLength(type) ? DataSourceType.PRIMARY.type : type;
    }

    static public void clear(){
        local.remove();
    }

}
