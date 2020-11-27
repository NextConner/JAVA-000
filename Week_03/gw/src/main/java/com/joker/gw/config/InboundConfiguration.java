package com.joker.gw.config;

import com.joker.gw.inbound.HttpInboundHandler;
import com.joker.gw.outbound.okhttp.OkHttpOutboundHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/26 17:44
 */

@Configuration
public class InboundConfiguration {


    @Bean
    public HttpInboundHandler httpInboundHandler(){
        HttpInboundHandler inboundHandler = new HttpInboundHandler();
        inboundHandler.setHandler(okHttpOutboundHandler());
        return inboundHandler;
    }

    @Bean
    public OkHttpOutboundHandler okHttpOutboundHandler(){
        OkHttpOutboundHandler okHttpOutboundHandler = new OkHttpOutboundHandler();
        return okHttpOutboundHandler;
    }

}
