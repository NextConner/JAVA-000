package com.joker.jokergw.outbound;


import com.joker.jokergw.router.RouterStrategy;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.apache.http.HttpResponse;

/**
 * @author zoujintao
 */
public interface IOutBoundHandler {

    /**
     * 临时更改路由策略的方法
     * @param streagy 路由策略
     */
    void setStreagy(RouterStrategy streagy);

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
