package com.joker.gw.outbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.apache.http.HttpResponse;

/**
 * @author zoujintao
 */
public interface IOutBoundHandler {


    /**
     *   outbound handle 入口
      */
    default void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx){

    };

    /**
     * 处理返回
     */
    default void handleResponse(FullHttpRequest fullRequest, ChannelHandlerContext ctx, HttpResponse endpointResponse) throws Exception {

    }


}
