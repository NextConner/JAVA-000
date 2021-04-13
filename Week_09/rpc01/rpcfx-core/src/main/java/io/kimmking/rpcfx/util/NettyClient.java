package io.kimmking.rpcfx.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Charsets;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/3 14:18
 */
public class NettyClient {

    private static Logger logger = LoggerFactory.getLogger(NettyClient.class);

    private static ChannelFuture channelFuture;


    private static EventLoopGroup init(String host, int port) {

        if (StringUtils.isEmpty(host)) {
            host = "127.0.0.1";
            port = 8099;
        }

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.SO_RCVBUF, 8 * 1024)
                    .option(ChannelOption.SO_SNDBUF, 8 * 1024)
                    .option(ChannelOption.SO_REUSEADDR, true)
//                    .option(ChannelOption.AUTO_CLOSE,true)
                    .option(ChannelOption.ALLOW_HALF_CLOSURE, true)
                    .option(ChannelOption.TCP_NODELAY, false)
                    .channel(NioSocketChannel.class)
                    .handler(new NettyClientInitializer());
            // Make the connection attempt.
            try {
                channelFuture = b.connect(host, port).sync();
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e) {
            logger.info("Netty 客户端初始化链接异常:{}", e);
        }
        return group;
    }

    /**
     * 发送请求
     *
     * @param
     * @param request 请求对象
     */
    public static void send(RpcfxRequest request) throws InterruptedException {

        EventLoopGroup group = null;
        if (channelFuture == null || channelFuture.isVoid()) {
            group = init("", 8099);
        }
        try {
            channelFuture.channel().writeAndFlush(request);
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            throw e;
        }finally {
            group.shutdownGracefully();
        }
    }

    public static <T> DefaultFullHttpRequest createRequest(String url, RpcfxRequest<T> body) throws IOException {

        String json = body.toString();
        ByteBuf byteBuf  = Unpooled.copiedBuffer(json, Charset.forName("UTF-8"));

        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.POST, url,byteBuf);
        HttpHeaders headers = request.headers();
//        request.touch(body);
        headers.set(HttpHeaderNames.HOST,"127.0.0.1");
//        headers.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
//        headers.set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP.toString() + ','
//                + HttpHeaderValues.DEFLATE.toString());
//        headers.set(HttpHeaderNames.ACCEPT_CHARSET, "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
//        headers.set(HttpHeaderNames.ACCEPT_LANGUAGE, "fr");
//        headers.set(HttpHeaderNames.USER_AGENT, "Netty Simple Http Client side");
//        headers.set(HttpHeaderNames.ACCEPT, "");
//        request.headers().set(HttpHeaders.Names.HOST, server);
        request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        request.headers().set("accept-type", Charsets.UTF_8);
        request.headers().set(HttpHeaders.Names.CONTENT_TYPE, "application/json; charset=UTF-8");
        //    request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());
        return request;
    }

}
