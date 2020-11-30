package com.joker.datasource;

import com.joker.datasource.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class DatasourceApplicationTests {


    @Autowired
    private OrderService service;

    @Test
    public void testDataSource(){
        List<Map<String,Object>> re = service.getOrder();
        List<Map<String,Object>> re1  = service.getOrderSecond();
        System.out.println(re.toString() + " : "+ re1.toString());
    }

}
