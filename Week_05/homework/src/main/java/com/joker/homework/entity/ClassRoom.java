package com.joker.homework.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.context.annotation.Bean;

import javax.annotation.PreDestroy;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/17 12:49
 */
@Data
@ToString
public class ClassRoom {

    private int number;
    private String roomName;
    private Student monitor;

    public void create(){
        System.out.println(Student.LINE_PRE + "bean 初始化,创建 classRoom");
    }

    public void destroy(){
        System.out.println(Student.LINE_PRE + "bean 销毁,销毁 classRoom");
    }

}
