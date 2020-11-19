package com.joker.homework.controller;

import com.joker.homework.entity.Student;
import com.joker.homework.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import myStarter.service.AllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/18 17:22
 */
@Slf4j
@Component
public class JDBCRunner implements CommandLineRunner {

    @Autowired
    private StudentService service;

    /**
     *   引入的自定义stater中的对象
      */
    @Autowired
    private AllService allService;

    @Override
    public void run(String... args) throws Exception {

        //测试starter 是否有效
        log.info(allService.getSchool().toString());

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

//        log.info("batch insert result:{}",service.batch(batchSQL,params));

        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",UUID.randomUUID());
        paramMap.put("name","增后删除");
        paramMap.put("age",21);
        paramMap.put("sex",1);

        //子线程查询数据验证事务是否生效
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
        String insertSQL = "INSERT INTO student VALUES(:id,:name,:age,:sex)";
        //以事务方式执i行数据插入,插入后 sleep 5 秒再提交事务
//        service.insertThenDel(insertSQL,"student",paramMap);
        //删除数据
//        service.updateThenDel("DELETE FROM student");
    }



}
