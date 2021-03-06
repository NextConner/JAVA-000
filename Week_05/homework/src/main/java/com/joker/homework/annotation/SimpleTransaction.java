package com.joker.homework.annotation;

import java.lang.annotation.*;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/18 17:37
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SimpleTransaction {

    boolean open() default true;

}
