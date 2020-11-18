package com.joker.homework.config;

import com.joker.homework.entity.Student;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/17 14:45
 */
@Configuration
public class TeacherConfig {

    @Bean(initMethod = "init" ,destroyMethod = "destroy")
    public Teacher teacher(){
        Teacher teacher = new Teacher();
        teacher.setName("英语老师");
        teacher.setSexy("99.99%");
        return teacher;
    }

    @Data
    public class Teacher{
        private String name;
        private String sexy;

        public void init(){
            System.out.println(Student.LINE_PRE + "bean 初始化,创建 teacher");
        }

        public void destroy(){
            System.out.println(Student.LINE_PRE + "bean 销毁,销毁 teacher");
        }

        public String toString(){
            return String.format("老师{%s,%s}",name,sexy);
        }
    }

}
