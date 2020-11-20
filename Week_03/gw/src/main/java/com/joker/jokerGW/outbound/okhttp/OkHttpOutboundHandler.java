package com.joker.jokerGW.outbound.okhttp;

import com.joker.jokerGW.filter.impl.EasyAuthRequestFilter;
import com.joker.jokerGW.outbound.IOutBoundHandler;
import com.joker.jokerGW.router.HttpEndpointRouterHandler;
import com.joker.jokerGW.router.RouterStrategy;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import okhttp3.*;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author zoujintao
 */
public class OkHttpOutboundHandler extends ChannelInboundHandlerAdapter implements IOutBoundHandler {

    private Logger logger = LoggerFactory.getLogger(OkHttpOutboundHandler.class);

    /**
     * 1. 代理地址获取
     * 2. 拼接真实访问地址
     * 3. 服务路由
     * 4. 请求真实服务的方法
     * 5. 响应
     */
    private RouterStrategy strategy = null;

    //okhttpClient
    private static OkHttpClient okHttpClient;

    static {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(100, 3000, TimeUnit.MILLISECONDS))
                .build();
    }

    /**
     * 构造
     */
    public OkHttpOutboundHandler(RouterStrategy strategy) {
        this.strategy = strategy;
    }

    public OkHttpOutboundHandler() {
        this.strategy = RouterStrategy.Random;
    }


    @Override
    public void setStreagy(RouterStrategy strategy) {
        if (null == this.strategy) {
            this.strategy = strategy;
        }
    }

    @Override
    public void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        //获取代理服务器地址
        String proxyServer = HttpEndpointRouterHandler.route(this.strategy);
        //拼接真实访问路径
        final String url = proxyServer + fullRequest.uri();
        fetchGet(fullRequest, ctx, url);
    }

    //读写请求头
    private void fetchGet(FullHttpRequest fullRequest, ChannelHandlerContext ctx, String url) {

        HttpHeaders headers = fullRequest.headers();
        Request request = new Request.Builder()
                .get()
                .addHeader(EasyAuthRequestFilter.NIO_KEY, headers.get(EasyAuthRequestFilter.NIO_KEY))
                .addHeader("Connection","keep-alive")
                .addHeader("Accept","application/json")
                .url("http://"+url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                logger.error("请求异常:{}", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                HttpResponse responseResult = new BasicHttpResponse(HttpVersion.HTTP_1_1, response.code(), response.message());
                BasicHttpEntity entity = new BasicHttpEntity();

                String result = response.body().string();
                System.out.println("返回信息:" + result);
                entity.setContent(IOUtils.toInputStream(result,"UTF-8"));
                entity.setContentLength(result.getBytes().length);
                responseResult.setEntity(entity);
                responseResult.setStatusCode(response.code());
                responseResult.addHeader(EasyAuthRequestFilter.NIO_KEY, response.request().header(EasyAuthRequestFilter.NIO_KEY));
                try {
                    handleResponse(fullRequest, ctx, responseResult);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void handleResponse(FullHttpRequest fullRequest, ChannelHandlerContext ctx, HttpResponse endpointResponse) throws Exception {
        FullHttpResponse response = null;
        //
        try {

            byte[] body = EntityUtils.toByteArray(endpointResponse.getEntity());
//            System.out.println(EntityUtils.toString(endpointResponse.getEntity()));
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", body.length);
            //写出指定的header
            response.headers().add(EasyAuthRequestFilter.NIO_KEY, endpointResponse.getFirstHeader(EasyAuthRequestFilter.NIO_KEY).getValue());

        } catch (Exception e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(ctx, e);
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

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("msg read:" + msg);
        super.channelRead(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
