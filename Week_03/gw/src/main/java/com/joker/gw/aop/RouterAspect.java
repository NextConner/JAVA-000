package com.joker.gw.aop;

import com.joker.gw.annotation.ActiveRouter;
import com.jokergw.router.routers.HttpEndpointRouter;
import com.jokergw.router.service.RouterService;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/27 16:59
 */
@Slf4j
@Aspect
@Component
public class RouterAspect {

    public static final String PROXY_KEY = "proxyServer";

    @Value("{router.proxyServerList}")
    private String proxyServerList;

    @Autowired
    private RouterService routerService;

    @Pointcut("@annotation(com.joker.jokergw.annotation.ActiveRouter)")
    public void filterProxyCut() {
    }

    /**
     * 路由策略切面，将最终代理的地址设置到 header 中
     *
     * @param joinPoint
     * @return
     */
    @Around("filterProxyCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("开始执行路由切面方法!");
        Object obj;
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //获取过滤器注解
            ActiveRouter annotation = signature.getMethod().getAnnotation(ActiveRouter.class);
            if (null != annotation) {
                //获取 fullHttpRequest 对象
                Object[] args = joinPoint.getArgs();
                for (Object param : args) {
                    if (param instanceof FullHttpRequest) {
                        Class<? extends HttpEndpointRouter> routerClass = annotation.enableRouter();
                        HttpEndpointRouter router = routerClass.newInstance();
                        try {
                            String proxyServer = router.route(Arrays.asList(proxyServerList.split(",")));
                            ((FullHttpRequest) param).headers().add(PROXY_KEY, proxyServer);
                        } catch (Exception e) {
                            log.error("获取路由实现异常{}", e);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("切面方法异常:{}", e);
        } catch (Throwable t) {
            log.error("切入方法执行异常:{}", t);
        } finally {
            obj = joinPoint.proceed();
        }
        log.info("路由切面方法结束！");
        return obj;
    }


}
