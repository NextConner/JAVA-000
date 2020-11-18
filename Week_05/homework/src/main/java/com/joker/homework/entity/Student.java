package com.joker.homework.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.context.annotation.Bean;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/17 12:48
 */
@ToString
public class Student {

    public final static String LINE_PRE = "=========================================================";

    private String name;
    private String age;
    private int sex;

    //指定的bean 初始化方法
    public void create(){
        System.out.println(LINE_PRE + "bean 初始化,创建 monitor");
    }

    public void destroy(){
        System.out.println(LINE_PRE + "bean 销毁,销毁 monitor");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
