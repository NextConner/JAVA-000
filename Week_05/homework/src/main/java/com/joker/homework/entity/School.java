package com.joker.homework.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/17 13:58
 */


@Data
@Component
public class School {

    @Autowired(required = false)
    ClassRoom classRoom;

    private String name = "光明中学";

    @PostConstruct
    public void init() {
        System.out.println(Student.LINE_PRE + "bean 初始化,创建 school");
    }

    @PreDestroy
    public void destroy() {
        System.out.println(Student.LINE_PRE + "bean 销毁,销毁 school");
    }

}
