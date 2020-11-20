package com.jokerGW.filter.consts;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/19 17:55
 */
public enum FilterType {

    AUTH_FILTER("auth"),ACCESS_FILTER("acc");

    private String type;
    FilterType(String type){
        this.type = type;
    }

}
