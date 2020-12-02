package com.joker.datasource;

import com.joker.datasource.entity.OrderDetail;
import com.joker.datasource.entity.UserOrder;
import com.joker.datasource.service.OrderDetailService;
import com.joker.datasource.service.OrderJPAService;
import com.joker.datasource.service.OrderService;
import com.joker.datasource.service.ShardingOrderService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@SpringBootTest
class DatasourceApplicationTests {

    @Autowired
    private OrderJPAService orderJPAService;

    @Autowired
    private  JdbcTemplate jdbcTemplate;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ShardingOrderService shardingOrderService;

    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 测试数据源是否生效
     */
    @Test
    public void testJdbcOrder(){
        log.info("主库查询 :" + orderService.getOrderOne().toString());
        log.info("从库查询 ： " + orderService.getOrderSecond().toString());
    }

    @BeforeEach
    public void before(){
        log.info("before");
    }

//    @AfterEach
    public void after(){
        log.info("after");
        jdbcTemplate.update(" DELETE FROM slave0.t_user_order");
        jdbcTemplate.update(" DELETE FROM slave1.t_user_order");
        jdbcTemplate.update(" DELETE FROM masterdb.t_user_order");
        jdbcTemplate.update(" DELETE FROM slavedb.t_user_order");
    }

    /**
     * 1.测试动态数据源的读写分离
     * 2.配置类为 DataSourceConfiguration ，设置了默认的数据源和从数据源
     * 3.自定义注解 @Source 注释在方法上，默认指定主库的数据源，DataSourceAspect 拦截 @Source的方法，根据注解值切换数据源
     * 插入100万数据结束，耗时:907s
     */
    @Test
    public void testJpaOrder() {

        List<UserOrder> orderList = new ArrayList<>(100);
        Random random = new Random(1000);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            UserOrder order = new UserOrder();
            long id = System.currentTimeMillis() - (random.nextLong() + random.nextLong());
            order.setId(Math.abs(id));
            order.setCouponId("noCoupon");
            order.setCreateTime(new Timestamp(System.currentTimeMillis()));
            order.setDel(false);
            order.setDiscount(BigDecimal.valueOf(random.nextDouble()));
            order.setNickname("路过的买家");
            order.setOrderPrice(BigDecimal.valueOf(random.nextDouble()));
            order.setPayPrice(BigDecimal.valueOf(random.nextDouble()));
            order.setPhone(13333333333l);
            order.setReceAddr("广东省 广州市 天河区");
            order.setShipping(BigDecimal.valueOf(random.nextDouble()));
            order.setStatus(9);
            order.setTrackInfo("运送中");
            order.setTrackName("顺丰速运");
            order.setTrackNumber(String.valueOf(random.nextInt() + 10000000000L));
            order.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            long userId = System.currentTimeMillis() - (random.nextLong() + random.nextLong());
            order.setUserId(Math.abs(userId));
            order.setUsername("路人甲");
            orderList.add(order);
            if(orderList.size()>=5000){
                orderJPAService.addUserOrders(orderList);
                orderList.clear();
            }
        }

        //数据插入到主库
        if(orderList.size()>0) {
            orderJPAService.addUserOrders(orderList);
        }
        log.info("插入100万数据结束，耗时:{}",(System.currentTimeMillis()-start)/1000);
        //在主库和从库中查询数据
        log.info("未添加数据源注解，插入数据量:{}", orderJPAService.findUserOrders().size());
        log.info("未指定数据源，插入数据量 :{}", orderJPAService.findUserOrdersOne().size());
        log.info("指定从库数据源 ，插入数据量:{}", orderJPAService.findUserOrdersSecond().size());

        log.info("插入数据到从库!");
        orderJPAService.addUserOrdersToReadDB(orderList);
        log.info("再次指定从库数据源查询，插入数据量{},:" , orderJPAService.findUserOrdersSecond().size());

    }

    /**
     *  1.sharding-jdbc 数据源测试用例，没有实现数据库的主从，只是手动写了数据到从库
     *  2.这里验证了读写数据源的分离，第一次写入的是masterdb，之后两次查询分别去了slave0,slave1 ，查询结果为空
     *  3.手动插入数据到从库，再次查询就有数据，可以证明读写是分离了
     *  插入100万数据结束，耗时:849s
     */
    @Test
    public void testShardingJDBC(){

        List<UserOrder> orderList = new ArrayList<>(100);
        Random random = new Random(1000);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            UserOrder order = new UserOrder();
            long id = System.currentTimeMillis() - (random.nextLong() + random.nextLong());
            order.setId(Math.abs(id));
            order.setCouponId("noCoupon");
            order.setCreateTime(new Timestamp(System.currentTimeMillis()));
            order.setDel(false);
            order.setDiscount(BigDecimal.valueOf(random.nextDouble()));
            order.setNickname("路过的买家");
            order.setOrderPrice(BigDecimal.valueOf(random.nextDouble()));
            order.setPayPrice(BigDecimal.valueOf(random.nextDouble()));
            order.setPhone(13333333333l);
            order.setReceAddr("广东省 广州市 天河区");
            order.setShipping(BigDecimal.valueOf(random.nextDouble()));
            order.setStatus(9);
            order.setTrackInfo("运送中");
            order.setTrackName("顺丰速运");
            order.setTrackNumber(String.valueOf(random.nextInt() + 10000000000L));
            order.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            long userId = System.currentTimeMillis() - (random.nextLong() + random.nextLong());
            order.setUserId(Math.abs(userId));
            order.setUsername("路人甲");
            orderList.add(order);
            if(orderList.size()>=5000){
                shardingOrderService.addOrders(orderList);
                orderList.clear();
            }
        }
        if(orderList.size()>0){
            shardingOrderService.addOrders(orderList);
        }
        log.info("插入100万数据，耗时:{}",(System.currentTimeMillis()-start)/1000);

        log.info("插入数据{}，默认写入主库",shardingOrderService.addOrders(orderList));
        log.info("第一次查询入库数据量:{}",shardingOrderService.count());
        log.info("第二次查询入库数据量:{}",shardingOrderService.count());
        log.info("手动同步!");
        jdbcTemplate.update("INSERT INTO slave0.t_user_order SELECT * FROM masterdb.t_user_order");
        jdbcTemplate.update("INSERT INTO slave1.t_user_order SELECT * FROM masterdb.t_user_order");
        log.info("第三次查询入库数据量:{}",shardingOrderService.count());
    }

    /**
     * 插入100万订单数据
     */
    @Test
    public void testOrderInfoQuery(){

        String[] goods = {"洗衣机","电吹风","电暖器","miniCoper","西铁城","夜光石","太极剑","茅台","酸奶","喜多多","switch","PS5","GTAV"};

        ExecutorService service = Executors.newFixedThreadPool(4);

        CopyOnWriteArrayList<OrderDetail> orderList = new CopyOnWriteArrayList();
        Random random = new Random(1000);
        // createTime.plusMonths(new Random().nextInt(10)).plusDays(new Random(50).nextInt()).plusSeconds(new Random().nextInt(600000)).toDate().getTime())
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        DateTime createTime = DateTime.parse("2020-01-01");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {

            OrderDetail detail = new OrderDetail();
            detail.setCondName(goods[new Random().nextInt(goods.length-1)]);
            long commId = System.currentTimeMillis() - (random.nextLong() + random.nextLong());
            detail.setComdId(Math.abs(commId));
            detail.setCreateTime(createTime.plusMonths(new Random().nextInt(10)).plusDays(new Random(50).nextInt()).plusSeconds(new Random().nextInt(600000)).withYear(2020).toDate());
            detail.setUpdateTime(detail.getCreateTime());
            detail.setId(Math.abs(System.currentTimeMillis() + (new Random().nextLong() + new Random().nextLong()*new Random().nextInt(21))));
            detail.setDiscount(BigDecimal.valueOf(random.nextDouble()*100).setScale(2,BigDecimal.ROUND_HALF_UP));
            detail.setNum(new Random().nextInt(43));
            detail.setOrderId(Math.abs(System.currentTimeMillis() - (random.nextLong() + random.nextLong())));
            detail.setPrice(BigDecimal.valueOf(random.nextDouble()*100).setScale(2,BigDecimal.ROUND_HALF_UP));
            detail.setSellPrice(BigDecimal.valueOf(random.nextDouble()*100).setScale(2,BigDecimal.ROUND_HALF_UP));
            detail.setShipping(BigDecimal.valueOf(random.nextDouble()*100).setScale(2,BigDecimal.ROUND_HALF_UP));
            detail.setSkuInfo("蓝色 黑底 描边 长 10 宽 3 重 4");
            detail.setStatus(new Random().nextInt(3));
            detail.setStoreName("某宝");
            detail.setTotalPrice(BigDecimal.valueOf(detail.getPrice().doubleValue()*detail.getNum()).setScale(2,BigDecimal.ROUND_HALF_UP));
//            log.info(" {} --:{}",i,detail.toString());
            orderList.add(detail);
            if(orderList.size()>1000) {
                try{
                    orderDetailService.addOrderDetails(orderList);
                }catch (Exception e){
                    log.error("error:{}",e);
                }
                orderList.clear();
            }
        }

        if(orderList.size()>0){
            orderDetailService.addOrderDetails(orderList);
        }
        log.info("插入100万数耗时{}秒",(System.currentTimeMillis()-start)/1000);



    }


}
