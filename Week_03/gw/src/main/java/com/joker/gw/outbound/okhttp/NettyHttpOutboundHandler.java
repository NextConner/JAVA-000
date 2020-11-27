package com.joker.gw.outbound.okhttp;

import com.joker.gw.annotation.ActiveRouter;
import com.joker.gw.aop.RouterAspect;
import com.joker.gw.outbound.IOutBoundHandler;
import com.jokerGW.filter.filter.impl.EasyAuthRequestFilter;
import com.jokergw.router.routers.RouterStrategy;
import com.jokergw.router.routers.impl.RandomRouter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.internal.StringUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author zoujintao
 */
public class NettyHttpOutboundHandler implements IOutBoundHandler {

    private Logger logger = LoggerFactory.getLogger(NettyHttpOutboundHandler.class);

    public static Map<String,String> map = new HashMap<>(1);

    @ActiveRouter(enableRouter = RandomRouter.class)
    @Override
    public void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        //获取代理服务器地址
        String proxyServer = fullRequest.headers().get(RouterAspect.PROXY_KEY);
        final String url = proxyServer + fullRequest.uri();
        NettyClient.send(url,fullRequest);

        HttpResponse responseResult = new BasicHttpResponse(org.apache.http.HttpVersion.HTTP_1_1, 200, "success");
        BasicHttpEntity entity = new BasicHttpEntity();
        String result = map.get("msg");
        while(StringUtil.isNullOrEmpty(result)){
            result = map.get("msg");
        }
        try {
            entity.setContent(IOUtils.toInputStream(result, "UTF-8"));
        }catch (Exception e){
            e.printStackTrace();
        }
        entity.setContentLength(result.getBytes().length);
        responseResult.setEntity(entity);
        responseResult.setStatusCode(200);
        responseResult.addHeader(EasyAuthRequestFilter.NIO_KEY, fullRequest.headers().get(EasyAuthRequestFilter.NIO_KEY));
        handleResponse(fullRequest,ctx,responseResult);
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

    @Override
    public void handleResponse(FullHttpRequest fullRequest, ChannelHandlerContext ctx, HttpResponse endpointResponse) {

        FullHttpResponse response = null;
        //
        try {

            byte[] body = EntityUtils.toByteArray(endpointResponse.getEntity());
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", body.length);
            //写出指定的header
            response.headers().add(EasyAuthRequestFilter.NIO_KEY, "zoujintao");

        } catch (Exception e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    //response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(response);
                }
            }
            ctx.flush();
            //ctx.close();
        }


    }
}
