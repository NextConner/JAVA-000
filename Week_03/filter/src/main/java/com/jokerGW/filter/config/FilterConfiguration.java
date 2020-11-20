package com.jokerGW.filter.config;

import com.jokerGW.filter.filter.FilterProviderProperties;
import com.jokerGW.filter.filter.RequestFilter;
import com.jokerGW.filter.filter.impl.EasyAuthRequestFilter;
import com.jokerGW.filter.service.FilterService;
import com.jokerGW.filter.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;
import java.util.Map;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/19 17:41
 */
@Configuration
@EnableConfigurationProperties(FilterProviderProperties.class)
@ConditionalOnClass(FilterService.class)
public class FilterConfiguration {

    @Autowired
    private FilterProviderProperties providerProperties;

    @Bean
    public FilterService filterService(){
        FilterService filterService = new FilterService();
        filterService.setProviderProperties(providerProperties);
        return filterService;
    }

}
