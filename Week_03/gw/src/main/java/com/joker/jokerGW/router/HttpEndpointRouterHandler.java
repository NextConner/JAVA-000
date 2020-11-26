package com.joker.jokergw.router;

import com.joker.jokergw.router.impl.RandomRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @author zoujintao
 */
public class HttpEndpointRouterHandler {

    private static Logger logger = LoggerFactory.getLogger(HttpEndpointRouterHandler.class);

    private static RouterStrategy strategy;
    private static List<String> proxyServers;

    /**
     * 指定默认的代理服务器地址列表
     */
    private static List<String> defaultServerList = Arrays.asList(new String[]{"localhost:8091","localhost:8092","localhost:8093"});

    public void setProxyList(List<String> serverList){
        HttpEndpointRouterHandler.proxyServers = serverList;
    }

    public static String route(RouterStrategy strategy) {
        if(null == proxyServers){
            proxyServers = defaultServerList;
        }
        return strategeyRouter(strategy).route(proxyServers);
    }

    private static HttpEndpointRouter strategeyRouter(RouterStrategy strategy){

        HttpEndpointRouter router = new RandomRouter();
        try {
            Class clazz  = Class.forName("com.joker.jokergw.router.impl." + strategy.getStrategyKey());
            Object obj = clazz.newInstance();
            if(null != obj){
                router = (HttpEndpointRouter)obj;
            }
        }catch (Exception e){
            logger.error("获取路由实现异常{}",e);
        }
        return router;
    }


}
