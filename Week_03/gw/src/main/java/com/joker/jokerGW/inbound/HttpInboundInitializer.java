package com.joker.jokerGW.inbound;


import com.joker.jokerGW.filter.HttpRequestFilter;
import com.joker.jokerGW.filter.impl.EasyAuthRequestFilter;
import com.joker.jokerGW.outbound.okhttp.NettyHttpOutboundHandler;
import com.joker.jokerGW.router.RouterStrategy;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import java.util.LinkedList;

//@Component
public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {


	private String proxyServer;

    // 过滤器链
    private LinkedList<HttpRequestFilter> filters;


	public HttpInboundInitializer(String proxyServer) {
        this.proxyServer = proxyServer;
    }


	@Override
	public void initChannel(SocketChannel ch) {
		ChannelPipeline p = ch.pipeline();
//		if (sslCtx != null) {
//			p.addLast(sslCtx.newHandler(ch.alloc()));
//		}
		p.addLast(new HttpServerCodec());
		//p.addLast(new HttpServerExpectContinueHandler());
		p.addLast(new HttpObjectAggregator(1024 * 1024));
//		p.addLast(new HttpInboundHandler(this.proxyServers));
        /**
         *  使用
         */
        filters = new LinkedList<>();
        filters.add(new EasyAuthRequestFilter());
//		p.addLast(new HttpInboundHandler(RouterStrategy.Random,new OkHttpOutboundHandler(),filters));
//		p.addLast(new HttpInboundHandler(RouterStrategy.Random,new NettyHttpOutboundHandler(),filterProvider.getFilterChines()));
	}
}
