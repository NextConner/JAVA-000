package io.kimmking.rpcfx.api;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author TaoGeZou
 */
@Data
public class RpcfxRequest<T> implements Serializable {

    private static final long serialVersionUID = -5809782578272943999L;

    private long requestId = 1;

    private Class<T> serviceClass;

    private String method;

    private Object[] params;

    private AtomicLong requestPool = new AtomicLong(1);

    public RpcfxRequest(){
        requestId = requestPool.getAndIncrement();
    }


    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("serviceClass",serviceClass);
        json.put("method",method);
        json.put("params",params);
        return json.toJSONString();
    }
}
