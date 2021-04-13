package io.kimmking.rpcfx.demo.provider.handler.client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2021/1/29 16:15
 */
@Slf4j
@ChannelHandler.Sharable
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("client handler read! ");
        super.channelRead(ctx, msg);
    }
}
