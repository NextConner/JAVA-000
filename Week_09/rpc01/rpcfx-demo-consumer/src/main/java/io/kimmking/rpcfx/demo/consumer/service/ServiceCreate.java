package io.kimmking.rpcfx.demo.consumer.service;

import io.kimmking.rpcfx.annotation.RpcServiceCreate;
import io.kimmking.rpcfx.api.Filter;
import org.springframework.stereotype.Component;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/31 15:29
 */
@Component
public class ServiceCreate {

    @RpcServiceCreate
    public  <T> T create(final Class<T> serviceClass, final String url, Filter filter) {
        return null;
    }
}
