package io.kimmking.rpcfx.demo.provider.handler.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @Sharable 注解应该在确保 Handler 是线程安全的时候使用
 *
 * @Author: zoujintao@daoran.tv
 * @Date: 2021/2/1 13:49
 */
@ChannelHandler.Sharable
@Slf4j
public class ExceptionHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("消息处理异常:{}",cause);
        ctx.close();
    }

}
