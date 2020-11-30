package com.joker.datasource.config;

import com.joker.datasource.common.DynamicDataSource;
import com.joker.datasource.consts.DataSourceType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/30 12:39
 */

@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@Configuration
public class DataSourceConfiguration {

    @Bean("dataSource")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource dataSource() {
        return  DataSourceBuilder.create().build();
    }

    @Bean("secondaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource secondDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 指定默认数据源
     */
    @Bean
    @Primary
    public DataSource dynamicDataSource(){
        Map<Object,Object> dataSourceMap = new HashMap<>(8);
        dataSourceMap.put(DataSourceType.PRIMARY.getType(),dataSource());
        dataSourceMap.put(DataSourceType.SECONDARY.getType(),secondDataSource());
        //设置默认的数据源类型
        DynamicDataSource dynamicDataSource  = new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(dataSource());
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        return dynamicDataSource;
    }

}
