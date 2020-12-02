package com.joker.datasource.consts;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/30 12:55
 */
public enum DataSourceType {

    PRIMARY("dataSource"),SECONDARY("secondaryDataSource");

    public String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    DataSourceType(String type){
        this.type=type;
    }
}
