package com.joker.datasource.common;

import lombok.Data;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/30 12:51
 */
@Data
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        String key =   DataSourceHolder.getDataSourceType();
        return key;
    }

}
