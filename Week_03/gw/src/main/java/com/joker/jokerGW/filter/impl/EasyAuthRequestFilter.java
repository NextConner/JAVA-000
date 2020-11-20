package com.joker.jokerGW.filter.impl;

import com.joker.jokerGW.annotation.FilterEnableAnnotation;
import com.joker.jokerGW.filter.HttpRequestFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * @author zoujintao
 * @date 2020-11-01 22:21
 * 简单的网关请求过滤器
 */
@Component
public class EasyAuthRequestFilter implements HttpRequestFilter {

    public static String NIO_KEY = "nio";
    private final String AUTHOR_KEY = "author";
    private final String DEFAULT_AUTHOE = "zoujintao";

    @FilterEnableAnnotation
    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {

        /**
         *  1.直接在 request 头添加key-value , nio:作者 ,
         *  2.在真实发起后端请求的地方，读出这个头，复制一份作为请求头信息发给后端
         */
        HttpHeaders headers = fullRequest.headers();
        if (headers.contains(AUTHOR_KEY)) {
            headers.add(NIO_KEY, headers.get(AUTHOR_KEY));
        } else {
            headers.add(NIO_KEY, DEFAULT_AUTHOE);
        }

    }

}
