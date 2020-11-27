package com.joker.gw.outbound.netty4;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author TaoGeZou
 */

public class NettyHttpClientOutboundHandler  extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx)
            throws Exception {
        System.out.println("channelActive : ");
        super.channelActive(ctx);
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        System.out.println("read : " + msg.toString());
        super.channelRead(ctx,msg);
    }
}