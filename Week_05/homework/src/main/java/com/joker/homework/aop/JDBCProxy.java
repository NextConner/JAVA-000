package com.joker.homework.aop;

import com.joker.homework.config.JDBCUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JDBC类的静态代理类
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/18 17:45
 */

@Component
@Slf4j
public class JdbcProxy extends JDBCUtil {

    private Connection connection;
    private final int VALID_TIME = 100;

    private synchronized boolean checkConnection() throws Exception{

        if(null == connection){
            connection = super.getConnection();
        }
        if(connection.isValid(VALID_TIME)){
            return true;
        }
        return false;
    }

    /**
     *  batch execute with transaction
      */
    @Override
    public int batchExec(Connection connection, String sql, Object[][] params) throws SQLException {
        int result = -1;
        try{
            connection.setAutoCommit(false);
            result = super.batchExec(connection, sql, params);
            connection.commit();
        }catch (Exception e){
            log.error("execution exception : {}",e);
            connection.rollback();
        }finally {
            try{
                super.returnConn(connection);
            }catch (Exception e){
                log.error(" return connection error:{}",e);
                connection = null;
            }
        }
        return result;
    }

    public int update(String sql) throws Exception {
        if(!checkConnection()){
            return -1;
        }
        return super.update(connection, sql);
    }


    public List<Map<String, Object>> select(String sql, Class resultType) throws Exception {
        if(!checkConnection()){
            return new ArrayList<>();
        }
//        return super.select(super.getConnection(), sql, resultType);
        return super.select(connection, sql, resultType);
    }

    public int insert(String sql, Map<String, Object> params) throws Exception {
        if(!checkConnection()){
            return -1;
        }
        return super.insert(connection, sql, params);
    }

}
