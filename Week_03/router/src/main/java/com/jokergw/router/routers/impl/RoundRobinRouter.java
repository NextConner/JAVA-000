package com.jokergw.router.routers.impl;

import com.jokergw.router.endpoint.EndPoint;
import com.jokergw.router.endpoint.impl.NormalEndPoint;
import com.jokergw.router.routers.HttpEndpointRouter;
import com.jokergw.router.routers.RouterProviderProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/27 16:19
 * 轮询路由
 */
@Slf4j
public class RoundRobinRouter implements HttpEndpointRouter {

    /**
     * 当前轮询到的端点信息偏移量
     */
    private AtomicInteger nowEndPoint = new AtomicInteger(0);

    final Semaphore semaphore = new Semaphore(1);

    @Override
    public EndPoint route() {
        List<EndPoint> endPoints = RouterProviderProperties.endPoints;
        final int count = endPoints.size();
        EndPoint endPoint = null;
        try {
            if(semaphore.tryAcquire(2,TimeUnit.SECONDS)){
                endPoint = endPoints.get(nowEndPoint.get());
                log.info("当前路由的服务端点信息:{}-{}", nowEndPoint.get(), endPoint.toString());
                nowEndPoint.getAndIncrement();
                if (nowEndPoint.get() >= count) {
                    nowEndPoint.set(0);
                }
            }else{
                log.info("未获取到许可！");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            semaphore.release();
        }
        return endPoint;
    }




}
