package com.joker.homework.controller;


import com.joker.homework.config.JDBCUtil;
import com.joker.homework.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/18 16:17
 */

@Slf4j
@RequestMapping("/query/")
@RestController
public class QueryController {

    @Autowired
    private JDBCUtil jdbcUtil;

    @PostMapping("all")
    public String query(String table){

        List<Map<String,Object>> result=null;
        String SQL = " SELECT * FROM " + table;
        Student student = new Student();
        try{
            result = (List<Map<String, Object>>) jdbcUtil.exec(JDBCUtil.ExecType.SELECT,SQL,student,null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return String.valueOf(result);
    }


    @PostMapping("add")
    public String add(){

        int result=-1;
        String SQL = "INSERT INTO student VALUES(1231,'叶良辰','14',2); ";
        Student student = new Student();
        try{
            result = (int) jdbcUtil.exec(JDBCUtil.ExecType.INSERT,SQL,student,null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return String.valueOf(result);
    }

    @PostMapping("delete")
    public String delete(String id){

        int result=-1;
        String SQL = "DELETE FROM student WHERE id = '" + id + "'";
        Student student = new Student();
        try{
            result = (int) jdbcUtil.exec(JDBCUtil.ExecType.INSERT,SQL,student,null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return String.valueOf(result);
    }

}
