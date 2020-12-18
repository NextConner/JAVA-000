package io.kimmking.rpcfx.annotation;

import java.lang.annotation.*;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/18 15:05
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcServiceCreate {

    boolean withFilter() default false;

}
