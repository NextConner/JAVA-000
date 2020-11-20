package com.joker.jokerGW.router.impl;


import com.joker.jokerGW.router.HttpEndpointRouter;

import java.util.List;
import java.util.Random;

/**
 * 随机路由
 * @author zoujintao
 * @date  2020-11-01 23:20
 */
public class RandomRouter implements HttpEndpointRouter {

    @Override
    public String route(List<String> endpoints) {
        return endpoints.get(new Random().nextInt(endpoints.size()));
    }

}
