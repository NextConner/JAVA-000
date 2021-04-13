package io.kimmking.rpcfx.demo.provider.iniaialize;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLEngine;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2021/2/3 13:56
 */
@Slf4j
public class ClientInitializer extends ChannelInitializer {

    private boolean isSslSupport;
    private final SslContext context;
    private final boolean startTls;

    public ClientInitializer(boolean isSslSupport,SslContext sslContext,boolean startTls){
        this.isSslSupport = isSslSupport;
        this.context = sslContext;
        this.startTls = startTls;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {

        //默认添加 http 消息编解码器
        ChannelPipeline pipeline = ch.pipeline();

        if(isSslSupport){
            SSLEngine engine = context.newEngine(ch.alloc());
            ch.pipeline().addFirst("ssl",new SslHandler(engine,startTls));
        }
        pipeline.addLast("decompressor", new HttpContentDecompressor());
        pipeline.addLast("decoder", new HttpResponseDecoder());
        pipeline.addLast("encoder", new HttpRequestEncoder());
        pipeline.addLast("codec", new HttpClientCodec());
        pipeline.addLast("aggregator", new HttpObjectAggregator(512 * 1024));

    }

}
