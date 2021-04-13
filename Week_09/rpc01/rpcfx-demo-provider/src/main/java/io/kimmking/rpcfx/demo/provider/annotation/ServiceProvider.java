package io.kimmking.rpcfx.demo.provider.annotation;

import java.lang.annotation.*;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2021/4/13 16:37
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface ServiceProvider {
}
