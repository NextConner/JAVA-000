package com.joker.homework.aop;


import com.joker.homework.annotation.SimpleTransaction;
import com.joker.homework.util.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;

/**
 *  SimpleTransaction注解生效的切面类
 */
@Slf4j
@Component
@Aspect
public class SimpleTransactionAspect {

    @Autowired
    private DataSource source;

    @Pointcut("@annotation(com.joker.homework.annotation.SimpleTransaction)")
    public void jdbcProxyCut() {
    }


    @Around("jdbcProxyCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Exception {

        log.info("进入切面方法！");

        Object result=null;

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        SimpleTransaction simpleTransaction = signature.getMethod().getAnnotation(SimpleTransaction.class);
        if (simpleTransaction.open()) {
            log.info("当前执行方法:{} 已开启事务！",signature.getMethod().getName());
            JdbcProxy proxy = SpringBeanUtil.getBean(JdbcProxy.class);
            Class<JdbcProxy> proxyClass = JdbcProxy.class;
            Field field = proxyClass.getDeclaredField("connection");
            field.setAccessible(true);
            if (field.get(proxy) instanceof Connection) {
                //获取当前connection 对象，开启事务
//                Connection connection = (Connection) field.get(proxy);
                //从数据源重新获取一个新的连接
                Connection connection = source.getConnection();
                connection.setAutoCommit(false);
                try {
                    result = joinPoint.proceed();
                    connection.commit();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    connection.rollback();
                }finally {
                    proxy.returnConn(connection);
                }
            }
        } else {
            log.info("未开启事务！");
            try {
                result = joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

        }
        log.info("切面方法结束！");
        return result;
    }
}