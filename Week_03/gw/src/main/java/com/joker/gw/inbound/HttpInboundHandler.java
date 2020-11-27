package com.joker.gw.inbound;

import com.joker.gw.outbound.IOutBoundHandler;
import com.joker.gw.outbound.okhttp.OkHttpOutboundHandler;
import com.joker.gw.router.RouterStrategy;
import com.jokerGW.filter.filter.RequestFilter;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

/**
 * @author TaoGeZou
 */
@ChannelHandler.Sharable
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);

    /**
     *  使用接口代替具体的 outbound 实现
     */
    private IOutBoundHandler handler;
    //过滤器链
    private LinkedList<RequestFilter> requestFilters;
    //路由策略
    private RouterStrategy strategy;

    public IOutBoundHandler getHandler() {
        return handler;
    }

    public void setHandler(IOutBoundHandler handler) {
        this.handler = handler;
    }

    /**
     * 指定outbound实现
     * @param strategy 路由策略，不传入具体的路由地址信息，Inbound无需知道代理了哪些地址
     * @param handler 传入的 outbound 处理器
     * @param filters 过滤器链
     */
    public HttpInboundHandler(RouterStrategy strategy,IOutBoundHandler handler, LinkedList<RequestFilter> filters) {
        this.strategy = strategy;
        this.handler = handler;
        this.requestFilters = filters;
    }

    public HttpInboundHandler(RouterStrategy strategy,IOutBoundHandler handler) {
        this.strategy = strategy;
        this.handler = handler;
    }

    public HttpInboundHandler(IOutBoundHandler handler) {
        this.strategy = RouterStrategy.Random;
        this.handler = handler;
    }

    public HttpInboundHandler() {
        this.strategy = RouterStrategy.Random;
        this.handler = new OkHttpOutboundHandler(this.strategy);
    }

    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            //logger.info("channelRead流量接口请求开始，时间为{}", startTime);
            FullHttpRequest fullRequest = (FullHttpRequest) msg;

//            String uri = fullRequest.uri();
//            logger.info("接收到的请求url为{}", uri);
//            if (uri.contains("/test")) {
//                handlerTest(fullRequest, ctx);
//            }

            /**
             *  执行filter链
              */
            if(null != requestFilters && requestFilters.size() > 0){
                doFilter(fullRequest,ctx);
            }
            // 执行具体的handle方法
            handler.handle(fullRequest, ctx);
    
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * 执行所有的filter方法
     * @param fullRequest 请求
     * @param ctx 上下文信息
     */
    private void doFilter(FullHttpRequest fullRequest,ChannelHandlerContext ctx){
        for(RequestFilter filter : this.requestFilters){
            filter.filter(fullRequest,ctx);
        }
    }

//    private void handlerTest(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
//        FullHttpResponse response = null;
//        try {
//            String value = "hello,kimmking";
//            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(value.getBytes("UTF-8")));
//            response.headers().set("Content-Type", "application/json");
//            response.headers().setInt("Content-Length", response.content().readableBytes());
//
//        } catch (Exception e) {
//            logger.error("处理测试接口出错", e);
//            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
//        } finally {
//            if (fullRequest != null) {
//                if (!HttpUtil.isKeepAlive(fullRequest)) {
//                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
//                } else {
//                    response.headers().set(CONNECTION, KEEP_ALIVE);
//                    ctx.write(response);
//                }
//            }
//        }
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//        cause.printStackTrace();
//        ctx.close();
//    }

}
