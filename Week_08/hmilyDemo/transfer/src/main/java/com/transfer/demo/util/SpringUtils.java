package com.transfer.demo.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/15 13:56
 */
@Component
public class SpringUtils implements ApplicationContextAware {

    static private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public static <T> Object getBeanByType(Class<T> clazz){
        return context.getBean(clazz);
    }

    public static Object getBean(String beanName){
        return context.getBean(beanName);
    }

}
