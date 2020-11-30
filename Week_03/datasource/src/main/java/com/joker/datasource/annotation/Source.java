package com.joker.datasource.annotation;

import com.joker.datasource.consts.DataSourceType;

import java.lang.annotation.*;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/30 13:07
 */

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Source {

    DataSourceType value()  default DataSourceType.PRIMARY;
}
