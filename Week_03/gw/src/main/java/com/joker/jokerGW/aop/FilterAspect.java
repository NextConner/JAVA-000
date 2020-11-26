package com.joker.jokergw.aop;

import com.joker.jokergw.annotation.FilterEnableAnnotation;
import com.jokerGW.filter.filter.RequestFilter;
import com.jokerGW.filter.filter.impl.AccessRequestFilter;
import com.jokerGW.filter.filter.impl.EasyAuthRequestFilter;
import com.jokerGW.filter.service.FilterService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.LinkedList;
import java.util.stream.Stream;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/20 17:19
 */
@Slf4j
@Aspect
@Component
public class FilterAspect {

    @Autowired
    private FilterService filterService;

    @Pointcut("@annotation(com.joker.jokergw.annotation.FilterEnableAnnotation)")
    public void filterProxyCut() {
    }


    @Around("filterProxyCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Exception {

        log.info("进入切面方法！");
        Object result = null;
        FullHttpRequest request = null;
        ChannelHandlerContext context = null;

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取过滤器注解
        try{
            FilterEnableAnnotation annotation = signature.getMethod().getAnnotation(FilterEnableAnnotation.class);
            if(null != annotation){
                //获取过滤器所需参数
                Object[] args = joinPoint.getArgs();
                for(Object o : args){
                    if(o instanceof  FullHttpRequest){
                        request= (FullHttpRequest) o;
                    }
                    if(o instanceof ChannelHandlerContext){
                        context = (ChannelHandlerContext) o;
                    }
                }
            }
            LinkedList<RequestFilter> filters = filterService.getProviderProperties().getFilterChines();
            final FullHttpRequest finalRequest = request;
            final ChannelHandlerContext finalContext = context;
            if(annotation.enableAuth()){
                filters.parallelStream().forEach(filter -> {
                            if (filter instanceof EasyAuthRequestFilter) {
                                filter.filter(finalRequest, finalContext);
                            }
                        }
                    );
            }
            if(annotation.enableAccess()){
                filters.parallelStream().forEach(filter -> {
                            if (filter instanceof AccessRequestFilter) {
                                filter.filter(finalRequest, finalContext);
                            }
                        }
                );
            }
            result = joinPoint.proceed();
        }catch (Exception e){
            log.info("过滤器切面方法异常:{}",e);

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        log.info("切面方法结束！");
        return result;
    }

}
