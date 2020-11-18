package com.joker.homework.controller;

import com.joker.homework.config.JDBCUtil;
import com.joker.homework.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/18 17:22
 */
@Component
public class JDBCRunner implements CommandLineRunner {

    @Autowired
    private JDBCUtil jdbcUtil;

    @Override
    public void run(String... args) throws Exception {
        String INSERT_SQL = "INSERT INTO student VALUES(1231,'叶良辰','14',2); ";
        int addResult = (int) jdbcUtil.exec(JDBCUtil.ExecType.INSERT,INSERT_SQL,null,null);
        System.out.println("insert 结果 " + (addResult>0));

        String SELECT_SQL = "SELECT * FROM student ; ";
        List<Map<String,Object>> selectResult = (List<Map<String, Object>>) jdbcUtil.exec(JDBCUtil.ExecType.SELECT,SELECT_SQL,new Student(),null);
        System.out.println("查询结果:" + selectResult.toString());

        String DELETE_SQL = "DELETE FROM student";
        int delResult = (int) jdbcUtil.exec(JDBCUtil.ExecType.DELETE,DELETE_SQL,null,null);
        System.out.println("delete 结果 " + (delResult>0));
    }
}
