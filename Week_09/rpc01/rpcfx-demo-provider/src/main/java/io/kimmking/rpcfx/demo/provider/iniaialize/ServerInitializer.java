package io.kimmking.rpcfx.demo.provider.iniaialize;

import io.kimmking.rpcfx.demo.provider.codec.ByteToMsg;
import io.kimmking.rpcfx.demo.provider.codec.MsgToByte;
import io.kimmking.rpcfx.demo.provider.handler.server.BasicInboundHandler;
import io.netty.channel.*;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLEngine;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2021/2/1 9:36
 */
@Slf4j
public class ServerInitializer extends ChannelInitializer {

    private Map<String,ChannelHandler> channelHandler = new HashMap(4);
    private Map<String,ChannelInboundHandler> inboundHandlers = new HashMap<>();
    private Map<String,ChannelOutboundHandler> outboundHandlers = new HashMap<>();

    //处理webSocket 的handler

    private Map<String,ChannelHandler> webSocketHandler = new HashMap<>();


    //标记webSocket 请求的标志

    private static final String SOCKET_FLAG = "/websocket";

    private boolean isAutoRead;
    private boolean isAutoClose;
    private boolean isSslSupport;
    private boolean isWebSocketSupport;

    private final SslContext context;
    private final boolean startTls;

    public ServerInitializer(boolean isAutoRead, boolean isAutoClose, boolean isSslSupport,boolean isWebSocketSupport, SslContext context, boolean startTls){
        this.isAutoClose = isAutoClose;
        this.isAutoRead = isAutoRead;
        this.isSslSupport = isSslSupport;
        this.context = context;
        this.startTls = startTls;
        this.isWebSocketSupport = isWebSocketSupport;
    }

    public boolean isAutoRead() {
        return isAutoRead;
    }

    public void setAutoRead(boolean autoRead) {
        isAutoRead = autoRead;
    }

    public boolean isAutoClose() {
        return isAutoClose;
    }

    public void setAutoClose(boolean autoClose) {
        isAutoClose = autoClose;
    }

    public ServerInitializer addInboundHandler(String handlerName , ChannelInboundHandler inboundHandler) {
        this.inboundHandlers.put(handlerName,inboundHandler);
        return this;
    }

    public ServerInitializer addOutboundHandler(String handlerName , ChannelOutboundHandler outboundHandler) {
        this.outboundHandlers.put(handlerName,outboundHandler);
        return this;
    }


    @Override
    protected void initChannel(Channel ch) throws Exception {

        channelHandler.putAll(inboundHandlers);
        channelHandler.putAll(outboundHandlers);
        if(isAutoRead){
            ch.config().setAutoRead(true);
        }
        if(isAutoClose){
            ch.config().setAutoClose(true);
        }
        ch.pipeline().addFirst(new BasicInboundHandler());
        //SSL 支持
        if(isSslSupport){
            log.info("SSL handler 作为第一个处理器被添加！");
            SSLEngine engine = context.newEngine(ch.alloc());
            ch.pipeline().addFirst("ssl",new SslHandler(engine,startTls));
        }
        //webSocket 支持
        if(isWebSocketSupport){
            if(null == webSocketHandler || webSocketHandler.size() ==0){
                log.info("websocket请求的处理器未添加，请确认是否已经处理 websocket 请求！");
            }else{
                channelHandler.putAll(webSocketHandler);
            }
            ch.pipeline().addLast(new WebSocketServerProtocolHandler(SOCKET_FLAG));
        }

        //添加http请求支持
        ch.pipeline().addLast("httpRequestDecoder",new HttpRequestDecoder());
        ch.pipeline().addLast("httpResponseEncoder",new HttpResponseEncoder());

        //添加特定的RpxRequest 消息的编解码
        ch.pipeline().addLast("requestEncoder",new MsgToByte());
        ch.pipeline().addLast("requestDecoder",new ByteToMsg());

        ch.pipeline().addLast("aggregator", new HttpObjectAggregator(512 * 1024));
        if (null != channelHandler && channelHandler.size() > 0) {
            channelHandler.forEach( (handlerName, channelHandler) -> ch.pipeline().addLast(handlerName,channelHandler));
        }

    }

}
