package com.joker.jokerGW.filter;

import com.joker.jokerGW.service.TestService;
import com.jokerGW.filter.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/20 15:21
 */
@Component
public class FilterRunner implements CommandLineRunner {

    @Autowired
    private FilterService filterService;

    @Autowired
    private TestService testService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(filterService.getProviderProperties());
        TestService.Sntity sntity = new TestService.Sntity("张三");
        testService.testAnnotation("李四",sntity);
    }


}
