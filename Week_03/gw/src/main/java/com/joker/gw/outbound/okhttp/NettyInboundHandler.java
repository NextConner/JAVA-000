package com.joker.gw.outbound.okhttp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultLastHttpContent;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.CharsetUtil;
import org.apache.commons.io.IOUtils;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/3 17:17
 */
public class NettyInboundHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println("msg -> "+msg);
        if (msg instanceof FullHttpResponse) {
            FullHttpResponse response = (FullHttpResponse) msg;
            ByteBuf buf = response.content();
            String result = buf.toString(CharsetUtil.UTF_8);
            System.out.println("response -> " + result);
        } else if (msg instanceof DefaultLastHttpContent) {
            DefaultLastHttpContent content = (DefaultLastHttpContent) msg;
//            System.out.write(ByteBufUtil.getBytes(content.content()));
            String result = IOUtils.toString(ByteBufUtil.getBytes(content.content()), "UTF-8");
            NettyHttpOutboundHandler.map.put("msg", result);
        }

    }
}
