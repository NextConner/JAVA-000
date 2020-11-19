package com.joker.homework.controller;

import com.joker.homework.entity.Student;
import com.joker.homework.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Component
@Slf4j
public class HikariRunner implements CommandLineRunner {


    @Autowired
    private StudentService service;

    /**
     * 引入 hikari 连接池，引入Spring jdbc 或者类似的依赖
     */
    @Autowired
    private DataSource dataSource;


    @Override
    public void run(String... args) throws Exception {


        new Thread(()->{
            try{
                int waitTimes = 10;
                List<Map<String,Object>> result = service.getAll("SELECT * FROM student",Student.class);
                while(null == result || result.size()<1){
                    waitTimes--;
                    log.info(Student.LINE_PRE + "数据尚未入库!");
                    TimeUnit.MILLISECONDS.sleep(500);
                    result = service.getAll("SELECT * FROM student",Student.class);
                    if(waitTimes==0){
                        break;
                    }
                }
                log.info("查询到的入库信息:{}",result.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();

        //获取连接
        Connection connection=null;
        try{
            connection=dataSource.getConnection();
            if(null != connection && connection.isValid(30)){
//                connection.setAutoCommit(false);
                //测试事务是否开启
                String batchSQL = "INSERT INTO student VALUES(?,?,?,?)";
                String[][] params = new String[10][4];

                for(int i=0;i<10;i++){
                    String[] param = new String[4];
                    param[0] = UUID.randomUUID().toString().substring(0,6);
                    param[1] = "name" +i;
                    param[2] = String.valueOf(i*(new Random().nextInt(5)));
                    param[3] = "1";
                    params[i]=param;
                }
                //等待5秒再执行入库操作
                TimeUnit.SECONDS.sleep(5);
                log.info("batch insert result:{}",service.batch(batchSQL,params));
//                connection.commit();
            }else{
                log.error("can not get connection!");
            }

        }catch (Exception e){
//            connection.rollback();
            log.error("SQL execution:{}",e);
        }
    }


}
