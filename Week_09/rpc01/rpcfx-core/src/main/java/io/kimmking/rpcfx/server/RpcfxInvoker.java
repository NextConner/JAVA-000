package io.kimmking.rpcfx.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResolver;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.exception.RpcfxException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class RpcfxInvoker {

    private RpcfxResolver resolver;

    public RpcfxInvoker(RpcfxResolver resolver) {
        this.resolver = resolver;
    }

//    public RpcfxResponse invoke(RpcfxRequest request) {
//
//        RpcfxResponse response = new RpcfxResponse();
//        String serviceClass = request.getServiceClass();
//
//        // 改成泛型和反射
//        Object service = resolver.resolve(serviceClass);//this.applicationContext.getBean(serviceClass);
//
//
//        try {
//            Method method = resolveMethodFromClass(service.getClass(), request.getMethod());
//            Object result = method.invoke(service, request.getParams()); // dubbo, fastjson,
//            // 两次json序列化能否合并成一个
//            response.setResult(JSON.toJSONString(result, SerializerFeature.WriteClassName));
//            response.setStatus(true);
//            return response;
//        } catch (IllegalAccessException | InvocationTargetException e) {
//
//            // 3.Xstream
//
//            // 2.封装一个统一的RpcfxException
//            // 客户端也需要判断异常
//            e.printStackTrace();
//            response.setException(e);
//            response.setStatus(false);
//            return response;
//        }
//    }



    public <T> RpcfxResponse invoke(RpcfxRequest<T> request) {

        RpcfxResponse response = new RpcfxResponse();
        Class<T> service = request.getServiceClass();
        String methodName = request.getMethod();
        Object[] params = request.getParams();
        StringBuffer strParam = new StringBuffer();
        Class<?>[] paramTypes = new Class[0];
        if(null != params && params.length>0){
            paramTypes = new Class[params.length];

            for (int i = 0; i < params.length; i++) {
                paramTypes[i] = params[i].getClass();
                strParam.append(params[i]+",");
            }
        }

        try {
            Method method = service.getMethod(methodName,paramTypes);
            // dubbo, fastJson,
            Object serviceInstance = resolver.resolve(service.getName());
            Object result = method.invoke(serviceInstance, request.getParams());
            response.setResult(JSON.toJSONString(result, SerializerFeature.WriteClassName));
            response.setStatus(true);
        } catch (NoSuchMethodException | IllegalAccessException |  InvocationTargetException e) {
            //抛出 RpcfxException 异常
            RpcfxException rpcfxException = new RpcfxException();
            rpcfxException.setDate(new Date());
            rpcfxException.setException(e);
            rpcfxException.setExceptionMsg(e.getMessage());
            rpcfxException.setMethod(methodName);
            rpcfxException.setStrParams(strParam.toString());
            rpcfxException.setRequestService(service.toString());

            response.setException(rpcfxException);
            response.setStatus(false);
        }

        return response;
    }


    private Method resolveMethodFromClass(Class<?> klass, String methodName) {
        return Arrays.stream(klass.getMethods()).filter(m -> methodName.equals(m.getName())).findFirst().get();
    }


}
