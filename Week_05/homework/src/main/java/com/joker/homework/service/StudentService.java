package com.joker.homework.service;


import com.joker.homework.annotation.SimpleTransaction;
import com.joker.homework.aop.JdbcProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 测试 SimpleTransaction注解的service 类
 */
@Service
public class StudentService {


    @Autowired
    private JdbcProxy proxy;

    @SimpleTransaction(open = true)
    public int insertThenDel(String sql,String table,Map<String,Object> param)throws  Exception{

        int result = proxy.insert(sql,param);
        //测试事务是否生效
        TimeUnit.SECONDS.sleep(5);
//        int result = proxy.update("DELETE FROM " + table);
        return result;
    }

    @SimpleTransaction(open = false)
    public int updateThenDel(String sql)throws Exception{
        return proxy.update(sql);
    }

    public List<Map<String,Object>> getAll(String sql,Class returnType) throws Exception {
        return proxy.select(sql,returnType);
    }

    @SimpleTransaction(open = true)
    public int batch(String sql,Object[][] params){

        try{
            return proxy.batchExec(sql,params);
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public int batch(Connection connection, String sql, Object[][] params){

        try{
            return proxy.batchExec(connection,sql,params);
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

}
