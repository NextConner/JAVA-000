package com.sharding.demo;

import com.sharding.demo.dao.OrderDetailJPA;
import com.sharding.demo.dao.OrderDetailXaDao;
import com.sharding.demo.dao.OrderXAJpa;
import com.sharding.demo.entity.OrderDetail;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/13 22:17
 */

@Slf4j
@SpringBootTest
public class ShardingXATest {


    static long[] userIds = new long[30];
    static long[] orderIds = new long[30];
    static{
        for(int i=0;i<30;i++){
            orderIds[i] = Math.abs(new Random().nextInt(1000000000));
            userIds[i] = Math.abs(new Random().nextInt(2000000000));
        }
    }

    @Autowired
    private OrderDetailXaDao orderDetailXaDao;

    @Autowired
    private OrderDetailJPA orderDetailJPA;

    @Test
    public void testXAUpdate() throws InterruptedException {

        String[] goods = {"Cooler", "Adidas", "Hades", "miniCooper", "switch", "PS5", "GTA5"};
        Random random = new Random(1000);
        DateTime createTime = DateTime.parse("2020-01-01");

        log.info("当前订单数量 : {}", orderDetailXaDao.selectAll());
        orderDetailXaDao.clear();
        log.info("清除后订单数量 : {}", orderDetailXaDao.selectAll());

        //测试XA事务是否生效，开启异步线程查询数据库
        log.info("xa start");
        for (int i = 0; i < 30; i++) {
            OrderDetail detail = new OrderDetail();
            detail.setCondName(goods[new Random().nextInt(goods.length - 1)]);
            detail.setComdId(Math.abs(new Random().nextInt(1000000000)));
            detail.setCreateTime(createTime.plusMonths(new Random().nextInt(10)).plusDays(new Random(50).nextInt()).plusSeconds(new Random().nextInt(600000)).withYear(2020).toDate());
            detail.setUpdateTime(detail.getCreateTime());
            detail.setId(System.currentTimeMillis());
            TimeUnit.MILLISECONDS.sleep(50);
            detail.setDiscount(BigDecimal.valueOf(random.nextDouble() * 100).setScale(2, BigDecimal.ROUND_HALF_UP));
            detail.setNum(new Random().nextInt(43));
//			detail.setOrderId(Math.abs(new Random().nextInt(1000000000)));
            detail.setOrderId(orderIds[i]);
//			orderIds[i] = detail.getOrderId();
            detail.setPrice(BigDecimal.valueOf(random.nextDouble() * 100).setScale(2, BigDecimal.ROUND_HALF_UP));
            detail.setSellPrice(BigDecimal.valueOf(random.nextDouble() * 100).setScale(2, BigDecimal.ROUND_HALF_UP));
            detail.setShipping(BigDecimal.valueOf(random.nextDouble() * 100).setScale(2, BigDecimal.ROUND_HALF_UP));
            detail.setSkuInfo("is black and weight 20 KG");
            detail.setStatus(new Random().nextInt(3));
            detail.setStoreName("TMALL");
//			detail.setUserId(Math.abs(new Random().nextInt(1000000000)));
            detail.setUserId(Math.abs(userIds[i]));
//			userIds[i] = detail.getUserId();
            detail.setTotalPrice(BigDecimal.valueOf(detail.getPrice().doubleValue() * detail.getNum()).setScale(2, BigDecimal.ROUND_HALF_UP));
            log.info("当前订单详情,用户ID:{},订单ID:{}", detail.getUserId(), detail.getOrderId());
//            orderXAJpa.save(detail);

            if(i >9){
                try {
                    orderDetailXaDao.insertWithException(detail);
                }catch (Exception e){
                    log.info("异常后当前输入数量 : {}", orderDetailXaDao.selectAll());
                    throw  e;
                }
            }else{
                orderDetailXaDao.insert(detail);
            }
            orderDetailXaDao.update(detail);
        }
        log.info("xa end");

    }

}
