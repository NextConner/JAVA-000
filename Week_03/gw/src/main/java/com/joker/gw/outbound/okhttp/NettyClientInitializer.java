package com.joker.gw.outbound.okhttp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/3 15:21
 */
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        p.addLast("log", new LoggingHandler(LogLevel.INFO));
        //编码器
        p.addLast("request-encoder", new HttpRequestEncoder());
        //解码器
        p.addLast("response-decoder", new HttpResponseDecoder());
//        p.addLast("inflater", new HttpContentDecompressor());
        p.addLast(new NettyInboundHandler());
    }


}
