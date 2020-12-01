package com.joker.datasource;

import com.joker.datasource.entity.Order;
import com.joker.datasource.service.OrderJPAService;
import com.joker.datasource.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.AfterTestMethod;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.sql.Timestamp;
import java.util.*;

@Slf4j
@SpringBootTest
class DatasourceApplicationTests {


    @Autowired
    private OrderService service;

    @Autowired
    private OrderJPAService orderJPAService;

    @Test
    public void testOrder() {

        List<Order> orderList = new ArrayList<>(100);
        Random random = new Random(1000);

        for (int i = 0; i < 100; i++) {
            Order order = new Order();
            long id = System.currentTimeMillis() - (random.nextLong() + random.nextLong());
            order.setId(id < 0 ? id * -1 : id);
            order.setCouponId("noCoupon");
            order.setCreateTime(new Timestamp(System.currentTimeMillis()));
            order.setDel(false);
            order.setDiscount(Math.round(random.nextDouble()));
            order.setNickname("路过的买家");
            order.setOrderPrice(Math.round(random.nextDouble()));
            order.setPayPrice(Math.round(random.nextDouble()));
            order.setPhone(13333333333l);
            order.setReceAddr("广东省 广州市 天河区");
            order.setShipping(Math.round(random.nextDouble()));
            order.setStatus(9);
            order.setTrackInfo("运送中");
            order.setTrackName("顺丰速运");
            order.setTrackNumber(String.valueOf(random.nextInt() + 10000000000L));
            order.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            long userId = System.currentTimeMillis() - (random.nextLong() + random.nextLong());
            order.setUserid(userId < 0 ? userId * -1 : userId);
            order.setUsername("路人甲");
            orderList.add(order);
        }

        //数据插入到主库
        orderJPAService.addOrders(orderList);
        //在主库和从库中查询数据
        log.info("未添加数据源注解:{}", orderJPAService.findOrders().get(0).toString());
        log.info("未指定数据源 :{}", orderJPAService.findOrdersOne().get(0).toString());
        log.info("指定从库数据源 :{}", orderJPAService.findOrdersSecond().toString());

        log.info("插入数据到从库!");
        orderJPAService.addOrdersToReadDB(orderList);
        log.info("再次指定从库数据源查询{}:" , orderJPAService.findOrdersSecond().toString());
    }

    @BeforeTestMethod
    public void before() {
        orderJPAService.clearMaster();
        orderJPAService.clearSlave();
    }

    @AfterTestMethod
    public void after() {
        orderJPAService.clearMaster();
        orderJPAService.clearSlave();
    }

}
