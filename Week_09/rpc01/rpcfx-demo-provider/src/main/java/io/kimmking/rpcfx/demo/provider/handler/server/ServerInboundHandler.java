package io.kimmking.rpcfx.demo.provider.handler.server;

import com.alibaba.fastjson.JSONObject;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.demo.provider.RpcfxServerApplication;
import io.kimmking.rpcfx.util.ByteBufMsgRead;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2021/1/29 16:02
 */
@ChannelHandler.Sharable
@Slf4j
public class ServerInboundHandler extends SimpleChannelInboundHandler<RpcfxRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcfxRequest msg) throws Exception {

        log.info("捕获 RpcfxRequest 请求:{}",msg);
//        HttpHeaders headers = msg.headers();
//        log.info("请求头:{}",headers.toString());
//        String host = headers.get("host");
//        String uri = msg.uri();
//        ByteBuf buf = msg.content();
//        if(msg.method().equals(HttpMethod.POST)){
//            String content = ByteBufMsgRead.getByteBufMsg(buf,"");
//            log.info("请求体:{}",content);
//        }
//        log.info("请求路径-- {}:{}",host,uri);

        Object result =  RpcfxServerApplication.getBeanByClass(msg.getServiceClass());

        RpcfxResponse response = new RpcfxResponse();
        response.setStatus(true);
        response.setResult(result);

//        JSONObject json = new JSONObject();
//        json.put("success","true");
//        ByteBuf byteBuf  = Unpooled.copiedBuffer(json.toJSONString(),Charset.forName("UTF-8"));
//        HttpHeaders httpHeaders = new DefaultHttpHeaders();
//        httpHeaders.add("Content-type","application/json");
//        FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK,byteBuf,httpHeaders,httpHeaders);

        // 关闭channel listener ，消息不会再被转发到下一个handler
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

//        msg.setUri("http://localhost:8080/test?name=zoujintao");
//        ctx.writeAndFlush(msg).addListener(ChannelFutureListener.CLOSE);

//        ctx.close();
//        ctx.fireChannelRead(msg);

    }

}
