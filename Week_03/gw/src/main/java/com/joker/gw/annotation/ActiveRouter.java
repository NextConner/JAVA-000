package com.joker.gw.annotation;


import com.jokergw.router.routers.HttpEndpointRouter;

import java.lang.annotation.*;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/27 16:21
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ActiveRouter {

    Class<? extends HttpEndpointRouter> enableRouter();
}
