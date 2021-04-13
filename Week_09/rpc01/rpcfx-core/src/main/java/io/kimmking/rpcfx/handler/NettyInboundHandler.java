package io.kimmking.rpcfx.handler;

import com.alibaba.fastjson.JSONObject;
import io.kimmking.rpcfx.util.ByteBufMsgRead;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/21 22:06
 */
@Slf4j
@ChannelHandler.Sharable
public class NettyInboundHandler extends SimpleChannelInboundHandler<FullHttpRequest> {


    private Logger logger = LoggerFactory.getLogger(NettyInboundHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {

        log.info("捕获http请求!");
        HttpHeaders headers = msg.headers();
        log.info("请求头:{}",headers.toString());
        String host = headers.get("host");
        String uri = msg.uri();
        ByteBuf buf = msg.content();
        if(msg.method().equals(HttpMethod.POST)){
            String content = ByteBufMsgRead.getByteBufMsg(buf,"");
            log.info("请求体:{}",content);
        }
        log.info("请求路径-- {}:{}",host,uri);

        Class clazz = Class.forName(msg.headers().get("service"));
        Object result = clazz.newInstance();

        ByteBuf byteBuf  = Unpooled.copiedBuffer(result.toString(), Charset.forName("UTF-8"));
        HttpHeaders httpHeaders = new DefaultHttpHeaders();
        httpHeaders.add("Content-type","application/json");
        FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK,byteBuf,httpHeaders,httpHeaders);

        // 关闭channel listener ，消息不会再被转发到下一个handler
        ctx.writeAndFlush(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
    }

}
