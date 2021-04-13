package io.kimmking.rpcfx.demo.provider.server;

import io.kimmking.rpcfx.demo.provider.handler.server.ServerInboundHandler;
import io.kimmking.rpcfx.demo.provider.handler.server.ServerOutboundHandler;
import io.kimmking.rpcfx.demo.provider.iniaialize.ServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2021/4/13 11:02
 */
@Slf4j
public class ProviderServer {


    public static final int port = 8099;

    final static ServerInboundHandler serverHandler = new ServerInboundHandler();

    final static ServerOutboundHandler serverOutHandler = new ServerOutboundHandler();


    static ServerInitializer serverInitializer = new ServerInitializer(true, false, false, false, null, false);

    static EventLoopGroup bossGroup = new NioEventLoopGroup(2);
    static EventLoopGroup workerGroup = new NioEventLoopGroup(4);


    public static void startServer() {

        try {
            // 服务初始化
            serverInitializer
                    .addInboundHandler("firsthandler", serverHandler)
                    .addOutboundHandler("outhandler ", serverOutHandler);

            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.SO_RCVBUF, 8 * 1024)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .option(ChannelOption.AUTO_CLOSE, true)
                    .childOption(ChannelOption.ALLOW_HALF_CLOSURE, true)
                    .childOption(ChannelOption.TCP_NODELAY, false)
                    .childHandler(serverInitializer);

            ChannelFuture channelFuture = b.bind(port).sync();

        } catch (Exception e) {
            log.info("Netty 客户端初始化链接异常:{}", e);
        }
    }

    public void close() {
        try {
            bossGroup.shutdownGracefully().sync();
            workerGroup.shutdownGracefully().sync();
        }catch (InterruptedException e) {
            log.error("group shutdown exception :{}",e);
        }
    }

}
