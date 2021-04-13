package io.kimmking.rpcfx.api;

import java.util.Map;

public interface RpcfxResolver {

    Object resolve(String serviceClass);

//    <T> Object resolve(Class<T> clazz);

    <T> Map<String, T> resolve(Class<T> clazz);
}
