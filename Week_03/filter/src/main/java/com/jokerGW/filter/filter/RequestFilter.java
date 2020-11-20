package com.jokerGW.filter.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;


/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/19 17:38
 */
public interface RequestFilter {

    /**
     * 过滤器接口
     * @param fullRequest 来自 nettyServer 的 FullHttpRequest
     * @param ctx netty channelHandler
     */
    void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx);

}
