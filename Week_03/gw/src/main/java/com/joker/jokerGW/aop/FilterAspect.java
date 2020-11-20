package com.joker.jokerGW.aop;

import com.joker.jokerGW.annotation.FilterEnableAnnotation;
import com.jokerGW.filter.filter.RequestFilter;
import com.jokerGW.filter.filter.impl.EasyAuthRequestFilter;
import com.jokerGW.filter.service.FilterService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.stream.Stream;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/20 17:19
 */
@Slf4j
@Aspect
public class FilterAspect {

    @Autowired
    private FilterService filterService;

    @Pointcut("@annotation(com.joker.jokerGW.annotation.FilterEnableAnnotation)")
    public void filterProxyCut() {
    }


    @Around("filterProxyCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Exception {

        log.info("进入切面方法！");
        Object result = null;
        Object[] args = joinPoint.getArgs();
        for(Object o : args){
            System.out.println(o.getClass().getName());
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取过滤器注解
        try{
            FilterEnableAnnotation annotation = signature.getMethod().getAnnotation(FilterEnableAnnotation.class);
            if(null != annotation){
                //获取过滤器所需参数
                Method method  = signature.getMethod();
            }
            LinkedList<RequestFilter> filters = filterService.getProviderProperties().getFilterChines();
            Stream stream = filters.parallelStream();
            if(annotation.enableAuth()){
                stream.forEach( filter -> {
                            if (filter instanceof EasyAuthRequestFilter) {
                                    ((EasyAuthRequestFilter) filter).filter(null,null);
                            }
                        }
                    );
            }
            joinPoint.proceed();
        }catch (Exception e){

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        log.info("切面方法结束！");
        return result;
    }

}
