package io.kimmking.rpcfx.demo.provider.handler.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2021/2/3 14:23
 */
@Slf4j
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    /**
     *     定制心跳信息，只需要发送过去
      */
    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("HEARTBEAT", CharsetUtil.ISO_8859_1));

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object event) throws Exception {

        if(event instanceof IdleStateEvent){
            log.info("idle event!");
            ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate()).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        }else{
            super.channelRead(ctx,event);
        }
    }

}
