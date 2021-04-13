package io.kimmking.rpcfx.demo.provider.handler.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2021/2/3 9:32
 */
@Slf4j
public class ClientHttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        log.info("msg:{}",msg.toString());
        log.info(msg.getClass().getName());
    }
}
