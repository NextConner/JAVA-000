package com.jokerGW.filter.filter.impl;

import com.jokerGW.filter.filter.RequestFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/19 17:38
 */
public class EasyAuthRequestFilter implements RequestFilter {

    public final static String NIO_KEY = "nio";
    private final String AUTHOR_KEY = "author";
    private final String DEFAULT_AUTHOE = "zoujintao";

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
