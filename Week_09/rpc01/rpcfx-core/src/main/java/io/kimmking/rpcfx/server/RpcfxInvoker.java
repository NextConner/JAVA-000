package io.kimmking.rpcfx.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResolver;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.client.Rpcfx;
import io.kimmking.rpcfx.exception.RpcfxException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Date;

public class RpcfxInvoker {

    private RpcfxResolver resolver;

    public RpcfxInvoker(RpcfxResolver resolver) {
        this.resolver = resolver;
    }

    public RpcfxResponse invoke(RpcfxRequest request) {

        RpcfxResponse response = new RpcfxResponse();
        String serviceClass = request.getServiceClass();

        // 改成泛型和反射
        Object service = resolver.resolve(serviceClass);//this.applicationContext.getBean(serviceClass);


        try {
            Method method = resolveMethodFromClass(service.getClass(), request.getMethod());
            Object result = method.invoke(service, request.getParams()); // dubbo, fastjson,
            // 两次json序列化能否合并成一个
            response.setResult(JSON.toJSONString(result, SerializerFeature.WriteClassName));
            response.setStatus(true);
            return response;
        } catch (IllegalAccessException | InvocationTargetException e) {

            // 3.Xstream

            // 2.封装一个统一的RpcfxException
            // 客户端也需要判断异常
            e.printStackTrace();
            response.setException(e);
            response.setStatus(false);
            return response;
        }
    }


    //泛型和反射
    public <T> RpcfxResponse invoke(RpcfxRequest<T> request) {

        RpcfxResponse response = new RpcfxResponse();
        Object service = request.getServiceClass();
        String methodName = request.getMethod();
        Object[] params = request.getParams();
        Class<?>[] paramTypes = new Class[params.length];
        StringBuffer strParam = new StringBuffer();
        for (int i = 0; i < params.length; i++) {
            paramTypes[i] = params[i].getClass();
            strParam.append(params[i]+",");
        }

        try {
            Method method = service.getClass().getMethod(methodName, paramTypes);
            Object result = method.invoke(service, request.getParams()); // dubbo, fastjson,
            response.setResult(JSON.toJSONString(result, SerializerFeature.WriteClassName));
            response.setStatus(true);
        } catch (Exception e) {
            RpcfxException rpcfxException = new RpcfxException();
            rpcfxException.setDate(new Date());
            rpcfxException.setException(e);
            rpcfxException.setExceptionMsg(e.getMessage());
            rpcfxException.setMethod(methodName);
            rpcfxException.setStrParams(strParam.toString());
            rpcfxException.setRequestService(service.toString());

            response.setException(rpcfxException);
            response.setStatus(true);
        }
        return response;
    }


    private Method resolveMethodFromClass(Class<?> klass, String methodName) {
        return Arrays.stream(klass.getMethods()).filter(m -> methodName.equals(m.getName())).findFirst().get();
    }

}
