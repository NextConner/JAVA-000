package io.kimmking.rpcfx.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2021/4/12 17:47
 */
@Slf4j
public class NettyHttpResponseHandler extends SimpleChannelInboundHandler<FullHttpResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpResponse msg) throws Exception {
        log.info("read response!");
        ctx.close();
    }

}
