package com.joker.datasource.aop;

import com.joker.datasource.annotation.Source;
import com.joker.datasource.common.DataSourceHolder;
import com.joker.datasource.consts.DataSourceType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/30 13:32
 */

@Slf4j
@Aspect
@Component
public class DataSourceAspect {

    @Pointcut("@annotation(com.joker.datasource.annotation.Source)")
    public void dataSourceProxyCut() {
    }

    @Around("dataSourceProxyCut()")
    public Object around(ProceedingJoinPoint joinPoint) {

        log.info("数据源切面方法开始:{}",System.currentTimeMillis());
        Object result = null;
        try{
            /**
             * 根据注解值，切换当前数据源
             */
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Source dataSource = signature.getMethod().getAnnotation(Source.class);
            if(null != dataSource){
                log.info("切换至数据源:{}",dataSource.value().getType());
                DataSourceType type = dataSource.value();
                DataSourceHolder.setDataSource(type);
            }else{
                log.info("未获取到注解值！");
            }
            result =joinPoint.proceed();
        }catch (Exception e){
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }finally {
            DataSourceHolder.clear();
        }
        log.info("数据源切面方法结束:{}",System.currentTimeMillis());
        return result;
    }

}
