package io.kimmking.rpcfx.demo.provider.handler.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.FullHttpResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2021/1/29 16:42
 */
@Slf4j
@ChannelHandler.Sharable
public class ServerOutboundHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        log.info("server out read :{}",new Date().toString());
        super.read(ctx);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        log.info("server out write :{}",new Date().toString());
////        ReferenceCountUtil.release(msg);
//        promise.setSuccess();
//        promise.addListener(new ServerChannelListener());
//        ctx.writeAndFlush(msg,promise);
////        ctx.fireChannelRead(msg);
//        ctx.close();
        if(msg  instanceof FullHttpResponse){
            FullHttpResponse response = (FullHttpResponse) msg;
            response.headers().add("key","zoujintao");
        }
        super.write(ctx,msg,promise);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        log.info("server out flush :{}",new Date().toString());
        super.flush(ctx);
    }
}
