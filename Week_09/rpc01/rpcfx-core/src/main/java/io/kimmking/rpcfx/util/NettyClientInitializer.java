package io.kimmking.rpcfx.util;

import io.kimmking.rpcfx.handler.NettyHttpResponseHandler;
import io.kimmking.rpcfx.handler.NettyInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/3 15:21
 */
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        p.addLast("log", new LoggingHandler(LogLevel.INFO));
        //添加http请求支持
        ch.pipeline().addLast("httpRequestDecoder",new HttpRequestDecoder());
        ch.pipeline().addLast("httpResponseEncoder",new HttpResponseEncoder());

        ch.pipeline().addLast("aggregator", new HttpObjectAggregator(512 * 1024));
//        p.addLast("inflater", new HttpContentDecompressor());
        p.addLast(new NettyInboundHandler());
        p.addLast(new NettyHttpResponseHandler());
    }


}
