package io.kimmking.rpcfx.annotation;

import io.kimmking.rpcfx.conts.ServiceTagWeight;

import javax.xml.ws.Service;
import java.lang.annotation.*;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/18 14:52
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcServiceTag {

    ServiceTagWeight serviceWeight() default ServiceTagWeight.NORMAL;

}
