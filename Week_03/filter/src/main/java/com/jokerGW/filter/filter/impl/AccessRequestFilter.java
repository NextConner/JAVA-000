package com.jokerGW.filter.filter.impl;

import com.jokerGW.filter.filter.RequestFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/20 15:13
 */
@Slf4j
@Component
public class AccessRequestFilter implements RequestFilter {

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {

        HttpHeaders httpHeaders = fullRequest.headers();
        String host = httpHeaders.get("host");
        String requestUrl = httpHeaders.get("Request-URL");
        log.info("request info :{}",host);
        System.out.println(this.getClass().getName());
    }

}
