package com.joker.homework;

import com.joker.homework.config.JDBCUtil;
import com.joker.homework.config.TeacherConfig;
import com.joker.homework.entity.ClassRoom;
import com.joker.homework.entity.School;
import com.joker.homework.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class HomeworkApplication {

	final static String linePre = "=========================================================";


	public static void main(String[] args) throws Exception {

		ConfigurableApplicationContext applicationContext = SpringApplication.run(HomeworkApplication.class, args);

		ClassRoom room = (ClassRoom) applicationContext.getBean("classRoom");
		System.out.println( linePre + room.toString());

		Student monitor = (Student) applicationContext.getBean("monitor");
		System.out.println(linePre + monitor.toString());

		School school = (School) applicationContext.getBean("school");
		System.out.println(linePre+school.toString());

		TeacherConfig.Teacher teacher = (TeacherConfig.Teacher)applicationContext.getBean("teacher");
		System.out.println(linePre + teacher.toString());

		applicationContext.close();
	}

}
