package com.joker.homework.config;

import com.joker.homework.entity.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/17 15:13
 */
@Component
public class JDBCConfig {

    enum ExecType{
        UPDATE("update"),SELECT("select"),DELETE("delete"),INSERT("insert");
        String type;
        private ExecType(String type){
            this.type = type;
        }
    }

    ExecutorService executor = new ThreadPoolExecutor(5,10,100,TimeUnit.MILLISECONDS,new ArrayBlockingQueue<>(5000));

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/jdbcTest";

    static final String USER = "root";
    static final String PASS = "123456";

    @Value("${conn.size}")
    private int size;
    @Value("${conn.takeTime}")
    private long takeTime;
    @Value("${exec.wait}")
    private long execWait;

    private ArrayBlockingQueue<Connection> connQueue;

    @PostConstruct
    public void init(){
        try {
            if(size == 0){
                size = 20;
            }
            Class.forName(JDBC_DRIVER);
            this.connQueue = new ArrayBlockingQueue<>(size);
            while(connQueue.remainingCapacity()>0){
                Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
                connQueue.put(connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取连接
    public Connection getConnection() throws InterruptedException {
        if(takeTime == 0L){
            takeTime = 3000;
        }
        return connQueue.poll(takeTime,TimeUnit.MILLISECONDS);
    }

    //归还连接
    public boolean returnConn(Connection connection) throws InterruptedException {
        if(null != connection){
            if(connQueue.remainingCapacity()>0){
                connQueue.put(connection);
                return true;
            }else{
                //丢弃
                connection = null;
                return true;
            }
        }else{
            return false;
        }
    }

    //执行update
    public Object exec(ExecType type, String sql, Object result) throws InterruptedException {
        Connection connection = null;
        try{
            connection = getConnection();
            if(null != connection){
                //线程池
                Connection finalConnection1 = connection;
                switch (type){
                    case UPDATE:
                    case DELETE:
                        return executor.submit(() -> {
                            Integer updateResult=-1;
                            updateResult = update(finalConnection1,sql);
                            return updateResult;
                        }).get(execWait,TimeUnit.MILLISECONDS);
                    case SELECT:
                        return select(connection,sql,result);
                    case INSERT:
                        break;
                    default:
                        System.out.println("未知的执行类型！");
                }

            }

        }catch (InterruptedException e){
            System.out.println("未获取到可用连接！" + e.getMessage());
        }catch (Exception e){
            System.out.println("获取数据库连接异常！" + e.getMessage());
        }finally {
            returnConn(connection);
        }
        return null;
    }

    //update
    private int update(Connection connection, String sql){
        int result=-1;
        try {
            Statement statement = connection.createStatement();
            result =  statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //select
    private List<Map<String,Object>> select(Connection connection, String sql, Object result){

        List<Map<String,Object>> resultList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery(sql);
            Class clazz = result.getClass();
            Field[] fields = clazz.getClass().getDeclaredFields();
            while(resultSet.next()){
                Map<String,Object> map = new HashMap<>();
                for(Field field : fields){
                    map.put(field.getName(),resultSet.getObject(field.getName(),field.getType()));
                }
                resultList.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public static void main(String[] args) {
        //第四步：注册JDBC驱动

        try {
            Class.forName(JDBC_DRIVER);
//            connection = DriverManager.getConnection(DB_URL, USER, PASS);
//            //第六步：执行查询语句
//            statement = connection.createStatement();
//            String sql = "SELECT * FROM student";
//            rs = statement.executeQuery(sql);
//            while (rs.next()) {
//                String title = rs.getString("title");
//                String author = rs.getString("author");
//                System.out.println(title + ":" + author);
//            }
            //第七步：关闭连接资源

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
//                rs.close();
//                statement.close();
//                connection.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }



}
