package io.kimmking.rpcfx.api;

public class RpcfxRequest<T> {

  private T serviceClass;

  private String method;

  private Object[] params;

    public T getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(T serviceClass) {
        this.serviceClass = serviceClass;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
