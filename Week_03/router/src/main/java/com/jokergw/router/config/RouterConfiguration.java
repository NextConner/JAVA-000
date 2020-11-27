package com.jokergw.router.config;

import com.jokergw.router.routers.RouterProviderProperties;
import com.jokergw.router.service.RouterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/27 16:49
 */
@Configuration
@EnableConfigurationProperties(RouterProviderProperties.class)
@ConditionalOnClass(RouterService.class)
public class RouterConfiguration {

    @Autowired
    private RouterProviderProperties routerProviderProperties;

    @Bean
    public RouterService routerService(){
        RouterService routerService = new RouterService();
        routerService.setRouterProviderProperties(routerProviderProperties);
        return routerService;
    }

}