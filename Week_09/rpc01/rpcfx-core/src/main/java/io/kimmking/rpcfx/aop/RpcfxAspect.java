package io.kimmking.rpcfx.aop;

import com.sun.org.apache.regexp.internal.RE;
import io.kimmking.rpcfx.annotation.RpcServiceCreate;
import io.kimmking.rpcfx.api.Filter;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.client.Rpcfx;
import io.kimmking.rpcfx.util.NettyClient;
import io.netty.handler.codec.http.FullHttpRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/18 14:42
 */

@Aspect
@Component
public class RpcfxAspect {

    private Logger logger = LoggerFactory.getLogger(RpcfxAspect.class);

//    @Pointcut("@annotation(io.kimmking.rpcfx.annotation.RpcServiceCreate)")
    @Pointcut("execution(* io.kimmking.rpcfx.client.Rpcfx.create(..))")
    public void rpcFxCut(){}


    public Map<String,RpcfxResponse> responseMap = new ConcurrentHashMap<>(16);

    @Around("rpcFxCut()")
    public <service> Object around(ProceedingJoinPoint joinPoint){

        logger.info("rpcService create 切面方法开始 ");

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        try{
            RpcServiceCreate serviceCreate = signature.getMethod().getAnnotation(RpcServiceCreate.class);
            if(null != serviceCreate){
                //构造Rpc服务接口， 参数 Class<T> serviceClass ,String url , Filter filter,返回值 RpcfxResponse
                Class<? extends  Object> service = null;
                Method method = null;
                String url="";
                Filter filter = null;

                Object[] args = joinPoint.getArgs();
                for(Object arg : args){
                   if(arg instanceof  Method){
                       method = (Method) arg;
                   }else if(arg instanceof  String){
                       url = (String) arg;
                   }else if(filter instanceof  Filter){
                       filter = (Filter) arg;
                   }else if(arg instanceof  Class){
                       service = (Class<? extends Object>) arg;
                   }
                }
                //TODO 构造远程服务对象，复用client ，使用 nettyClient
                RpcfxRequest<service> request = new RpcfxRequest<>();
                request.setMethod(method.getName());
                request.setParams(args);
                //TODO 过滤
                filter.filter(request);
                RpcfxResponse response = new RpcfxResponse();
                FullHttpRequest fullHttpRequest =  NettyClient.createRequest(url,request);
//                NettyClient.send(fullHttpRequest);

                while(responseMap.containsKey(service.getSimpleName())){
                    TimeUnit.MILLISECONDS.sleep(100);
                    response =  responseMap.get(service.getSimpleName());
                    responseMap.remove(service);
                    break;
                }
                return response;
            }
        }catch (Exception e){
            logger.error("切面方法调用异常:{]",e);
        }
        logger.info("开始 rpcService create 切面方法结束");
        return new RpcfxResponse();
    }

}
