package com.joker.gw.outbound.okhttp;
import com.jokerGW.filter.filter.impl.EasyAuthRequestFilter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/3 14:18
 */
public class NettyClient {

    private static Logger logger = LoggerFactory.getLogger(NettyClient.class);

    /**
     *  发送请求
     * @param
     * @param request 请求对象
     */
    public static void send(String url,FullHttpRequest request){

        String host = url.split(":")[0];
        Integer port = Integer.valueOf(url.split(":")[1].split("/")[0]);

        FullHttpRequest localResult = createRequest(url,request);

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
//                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .option(ChannelOption.SO_RCVBUF,8*1024)
                    .option(ChannelOption.SO_SNDBUF,8*1024)
                    .option(ChannelOption.SO_REUSEADDR,true)
//                    .option(ChannelOption.AUTO_CLOSE,true)
////                    .option(ChannelOption.ALLOW_HALF_CLOSURE,true)
//                    .option(ChannelOption.TCP_NODELAY,false)
                    .channel(NioSocketChannel.class)
                    .handler(new NettyClientInitializer());
            // Make the connection attempt.
            try{
                ChannelFuture result = b.connect(host, port).sync();
                result.channel().writeAndFlush(localResult);
                result.addListener((ChannelFutureListener)->{
                    if(result.isCancelled()){
                        System.out.println("异步请求被取消!原因是:" + result.cause().getMessage());
                    }

                });
                result.channel().closeFuture().sync();
            }catch (Exception e){
                throw e;
            }
        }catch (Exception e){
            logger.info("Netty 客户端初始化链接异常:{}",e);
        } finally {
            group.shutdownGracefully();
        }
    }


    private static DefaultFullHttpRequest createRequest(String url, FullHttpRequest fullHttpRequest){

        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0,HttpMethod.GET,"http://"+url);
        HttpHeaders headers = request.headers();
        //获取初始请求头
        String nioHeader = fullHttpRequest.headers().contains(EasyAuthRequestFilter.NIO_KEY) ?  fullHttpRequest.headers().get("nio") : "nettyClient";
        headers.add("nio", nioHeader);
        headers.set(HttpHeaders.Names.HOST, url.split(":")[0]);
        headers.set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.CLOSE);
        headers.set(HttpHeaders.Names.ACCEPT_ENCODING, HttpHeaders.Values.GZIP.toString() + ','
                + HttpHeaders.Values.DEFLATE.toString());

        headers.set(HttpHeaders.Names.ACCEPT_CHARSET, "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
        headers.set(HttpHeaders.Names.ACCEPT_LANGUAGE, "fr");
        headers.set(HttpHeaders.Names.USER_AGENT, "Netty Simple Http Client side");
        headers.set(HttpHeaders.Names.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");

        headers.set(HttpHeaders.Names.COOKIE, ClientCookieEncoder.encode(
                new DefaultCookie("my-cookie", "foo"),
                new DefaultCookie("another-cookie", "bar")));
        return request;
    }

}
