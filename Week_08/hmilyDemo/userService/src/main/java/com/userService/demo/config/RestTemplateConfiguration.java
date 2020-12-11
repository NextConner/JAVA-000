package com.userService.demo.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/8/21 12:40
 */
@Configuration
public class RestTemplateConfiguration {

    /**
     *  使用Spring提供的 restTemplate 访问 RMI
     */
    @Bean
//    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

}
