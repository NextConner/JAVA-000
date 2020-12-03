package com.jokergw.router.routers;

import com.jokergw.router.endpoint.EndPoint;

/**
 * @author TaoGeZou
 */
public interface HttpEndpointRouter {

    /**
     * 根据初始化的路由服务信息列表，进行代理服务的路由
     * @return
     */
    EndPoint route();
    
}
