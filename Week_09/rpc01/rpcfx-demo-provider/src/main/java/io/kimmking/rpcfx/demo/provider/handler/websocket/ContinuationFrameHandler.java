package io.kimmking.rpcfx.demo.provider.handler.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2021/2/3 14:14
 */
@Slf4j
public class ContinuationFrameHandler extends SimpleChannelInboundHandler<ContinuationWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ContinuationWebSocketFrame msg) throws Exception {
        // ContinuationWebSocketFrame : 前一个数据帧(二进制或文本)的后续数据帧
        log.info("继续帧!");
        ctx.fireChannelRead(msg);
        ReferenceCountUtil.release(msg);
    }

}
