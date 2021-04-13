package io.kimmking.rpcfx.demo.provider.iniaialize;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 处理连接抖动或空闲时的状况
 * @Author: zoujintao@daoran.tv
 * @Date: 2021/2/20 14:01
 */
public class IdleStateHandlerInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new IdleStateHandler(0,0,10, TimeUnit.SECONDS));
    }

}
