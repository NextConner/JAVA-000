package com.jokergw.router.routers.impl;


import com.jokergw.router.endpoint.EndPoint;
import com.jokergw.router.routers.HttpEndpointRouter;
import com.jokergw.router.routers.RouterProviderProperties;
import lombok.Data;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.LongAdder;

/**
 * 随机路由
 * @author zoujintao
 * @date  2020-11-01 23:20
 */
public class RandomRouter implements HttpEndpointRouter {

    @Override
    public EndPoint route() {
        List<EndPoint> endPoints = RouterProviderProperties.endPoints;
        return endPoints.get(new Random().nextInt(endPoints.size()-1));
    }

}
