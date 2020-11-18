package com.joker.homework.config;

import com.joker.homework.entity.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/17 15:13
 */
@Component
public class JDBCUtil {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/jdbctest?useUnicode=true&characterEncoding=UTF-8";
    static final String USER = "root";
    static final String PASS = "";
    static ExecutorService executor = new ThreadPoolExecutor(5, 10, 100, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10000));
    @Value("${conn.size}")
    private int size;
    @Value("${conn.takeTime}")
    private int takeTime;
    @Value("${exec.wait}")
    private int execWait;
    @Value("${batch.size}")
    private int batchSize;
    private ArrayBlockingQueue<Connection> connQueue;

    @PostConstruct
    public void init() {

        try {
            Class.forName(JDBC_DRIVER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (size == 0) {
                size = 20;
            }
            this.connQueue = new ArrayBlockingQueue<>(size);
            while (connQueue.remainingCapacity() > 0) {
                Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
                connQueue.put(connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取连接
    public Connection getConnection() throws InterruptedException {
        if (takeTime == 0) {
            takeTime = 30000;
        }
        if (execWait == 0) {
            execWait = 30000;
        }
        return connQueue.poll(takeTime, TimeUnit.MILLISECONDS);
    }

    //获取连接，关闭自动提交，开启事务
    public Connection getTSConnection() throws Exception {
        if (takeTime == 0) {
            takeTime = 30000;
        }
        if (execWait == 0) {
            execWait = 30000;
        }
        Connection connection = connQueue.poll(takeTime, TimeUnit.MILLISECONDS);
        connection.setAutoCommit(false);
        return connection;
    }

    //归还连接
    public boolean returnConn(Connection connection) throws InterruptedException {
        if (null != connection) {
            if (connQueue.remainingCapacity() > 0) {
                connQueue.put(connection);
                return true;
            } else {
                //丢弃
                connection = null;
                return true;
            }
        } else {
            return false;
        }
    }

    //check execType
    private boolean checkExecType(ExecType type, String sql) {
        return sql.toLowerCase().contains(type.type);
    }

    //执行sql
    public Object exec(ExecType type, String sql, Object result, Map<String, Object> params) throws Exception {

        if (!checkExecType(type, sql)) {
            throw new SQLException("执行方法类型与 SQL 类型不符！");
        }

        Connection connection = null;
        try {
            connection = getConnection();
            if (null != connection) {
                final Connection finalConnection1 = connection;
                switch (type) {
                    case UPDATE:
                    case DELETE:
                        return executor.submit(() -> {
                            Integer updateResult = -1;
                            updateResult = update(finalConnection1, sql);
                            return updateResult;
                        }).get(execWait, TimeUnit.MILLISECONDS);
                    case SELECT:
                        return executor.submit(() -> {
                            List<Map<String, Object>> selectResult = select(finalConnection1, sql, result);
                            return selectResult;
                        }).get();
                    case INSERT:
                        return executor.submit(() -> {
                            Integer updateResult = -1;
                            updateResult = insert(finalConnection1, sql, result, params);
                            return updateResult;
                        }).get(execWait, TimeUnit.MILLISECONDS);
                    default:
                        System.out.println("未知的执行类型！");
                        return null;
                }
            }
        } catch (InterruptedException e) {
            System.out.println("未获取到可用连接！" + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.out.println("获取数据库连接异常！" + e.getMessage());
            throw e;
        } finally {
            returnConn(connection);
        }
        return null;
    }

    //以事务的方式执行单个sql
    public Object execTS(ExecType type, String sql, Object result, Map<String, Object> params) throws Exception {

        if (!checkExecType(type, sql)) {
            throw new SQLException("执行方法类型与 SQL 类型不符！");
        }

        Connection connection = null;
        try {
            connection = getTSConnection();
            if (null != connection) {
                final Connection finalConnection1 = connection;
                switch (type) {
                    case UPDATE:
                    case DELETE:
                        return executor.submit(() -> {
                            Integer updateResult = -1;
                            try {
                                updateResult = update(finalConnection1, sql);
                                finalConnection1.commit();
                            } catch (Exception e) {
                                finalConnection1.rollback();
                            }
                            return updateResult;
                        }).get(execWait, TimeUnit.MILLISECONDS);
                    case INSERT:
                        return executor.submit(() -> {
                            Integer updateResult = -1;
                            try {
                                updateResult = insert(finalConnection1, sql, result, params);
                                finalConnection1.commit();
                            } catch (Exception e) {
                                finalConnection1.rollback();
                            }
                            return updateResult;
                        }).get(execWait, TimeUnit.MILLISECONDS);
                    default:
                        System.out.println("未知的执行类型！");
                        return null;
                }
            }
        } catch (InterruptedException e) {
            System.out.println("未获取到可用连接！" + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.out.println("获取数据库连接异常！" + e.getMessage());
            throw e;
        } finally {
            returnConn(connection);
        }
        return null;
    }

    //批量
    public Object batchExec(Connection connection, String sql, Object[][] params) throws SQLException {

        Integer[] batchResult =null;
        int executed = 0;

        try {
            connection.setAutoCommit(false);
            if (batchSize == 0) {
                batchSize = 200;
            }
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (Object[] param : params) {
                for (int i = 0; i < params.length; i++) {
                    preparedStatement.setObject(i, params[i]);
                }
                batchSize--;
                preparedStatement.addBatch();
                if (batchSize == 0) {
                    int[] pre = preparedStatement.executeBatch();
                    List list = new ArrayList(Arrays.asList(pre));
                    list.addAll(Arrays.asList(batchResult));
//                    batchResult = Arrays
                    batchSize = 200;
                    executed+=200;
                }
            }
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
        }


    }

    //update
    private int update(Connection connection, String sql) throws SQLException {
        int result = -1;
        try {
            Statement statement = connection.createStatement();
            result = statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw e;
        }
        return result;
    }

    //select
    private List<Map<String, Object>> select(Connection connection, String sql, Object result) throws SQLException {

        List<Map<String, Object>> resultList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            Field[] fields = result.getClass().getDeclaredFields();
            while (resultSet.next()) {
                Map<String, Object> map = new HashMap<>();
                for (Field field : fields) {
                    field.setAccessible(true);
                    try {
                        map.put(field.getName(), resultSet.getObject(field.getName(), field.getType()));
                    } catch (Exception e) {
//                        System.out.println("未匹配属性 " + field.getName());
                    }

                }
                resultList.add(map);
            }
        } catch (SQLException e) {
            throw e;
        }
        return resultList;
    }

    //insert
    private int insert(Connection connection, String sql, Object result, Map<String, Object> params) throws SQLException {

        List<Map<String, Object>> resultList = new ArrayList<>();
        int execResult = -1;
        try {
            //假设有对应的参数key
            if (null != params && params.size() > 0) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    if (sql.contains(":" + entry.getKey())) {
                        sql = sql.replace(":" + entry.getKey(), String.valueOf(entry.getValue()));
                    }
                }
            }
            Statement statement = connection.createStatement();
            execResult = statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw e;
        }
        return execResult;
    }

    public enum ExecType {
        UPDATE("update"), SELECT("select"), DELETE("delete"), INSERT("insert");
        String type;

        ExecType(String type) {
            this.type = type;
        }
    }

}
