package com.joker.jokerGW.annotation;

import java.lang.annotation.*;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/20 17:17
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FilterEnableAnnotation {

    boolean enableAuth() default true;

    boolean enableAccess() default true;

}
