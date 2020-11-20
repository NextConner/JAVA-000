package com.joker.jokerGW.filter;

import com.joker.jokerGW.annotation.FilterEnableAnnotation;
import com.joker.jokerGW.filter.impl.EasyAuthRequestFilter;
import com.jokerGW.filter.service.FilterService;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/20 15:21
 */
@Component
public class Runner implements CommandLineRunner {

    @Autowired
    private FilterService filterService;
    @Autowired
    private EasyAuthRequestFilter filter;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(filterService.getProviderProperties());
        filter.filter(new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, ""), null);
    }


}
