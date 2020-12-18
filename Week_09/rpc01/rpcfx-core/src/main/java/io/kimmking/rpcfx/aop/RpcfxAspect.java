package io.kimmking.rpcfx.aop;

import io.kimmking.rpcfx.annotation.RpcServiceCreate;
import io.kimmking.rpcfx.api.Filter;
import io.kimmking.rpcfx.client.Rpcfx;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/18 14:42
 */

@Aspect
@Component
public class RpcfxAspect {

    private Logger logger = LoggerFactory.getLogger(RpcfxAspect.class);

    @Pointcut("@annotation(io.kimmking.rpcfx.annotation.RpcServiceCreate)")
    public void rpcFxCut(){}

    @Around("rpcFxCut()")
    public Object around(ProceedingJoinPoint joinPoint){

        logger.info("rpcService create 切面方法开始 ");



        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        try{
            RpcServiceCreate serviceCreate = signature.getMethod().getAnnotation(RpcServiceCreate.class);
            if(null != serviceCreate){
                //构造Rpc服务接口， 参数 Class<T> serviceClass ,String url , Filter filter,返回值 RpcfxResponse
                Class serviceClass;
                String url ;
                Filter filter ;

                Object[] args = joinPoint.getArgs();
                for(Object arg : args){
                   if(arg instanceof  String){
                        url = (String) arg;
                   }else if(arg instanceof  Filter){
                       filter = (Filter) arg;
                   }else{
                       serviceClass = (Class) arg;
                   }
                }

                //TODO 构造远程服务对象，复用client ，使用 nettyClient



            }

        }catch (Exception e){

        }

        logger.info("开始 rpcService create 切面方法结束");

    }

}
