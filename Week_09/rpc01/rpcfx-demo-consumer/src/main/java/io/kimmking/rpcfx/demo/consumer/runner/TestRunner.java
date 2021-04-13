package io.kimmking.rpcfx.demo.consumer.runner;

import io.kimmking.rpcfx.client.Rpcfx;
import io.kimmking.rpcfx.demo.api.User;
import io.kimmking.rpcfx.demo.api.UserService;
import io.kimmking.rpcfx.demo.consumer.service.ServiceCreate;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2021/4/12 16:20
 */

@Component
public class TestRunner implements ApplicationRunner {

    @Autowired
    private ServiceCreate serviceCreate;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        UserService userService = serviceCreate.create(UserService.class, "http://localhost:8080/test?name=zoujintao",null);
//		UserService userService = serviceCreate.create(UserService.class,"http://localhost:8080/",null);
        User user = userService.findById(1);
        System.out.println("find user id=1 from server: " + user.getName());

    }
}

