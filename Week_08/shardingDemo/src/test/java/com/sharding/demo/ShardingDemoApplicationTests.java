package com.sharding.demo;

import com.sharding.demo.entity.OrderDetail;
import com.sharding.demo.service.ShardingOrderService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
class ShardingDemoApplicationTests {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private ShardingOrderService shardingOrderService;

	static long[] userIds = new long[30];
	static long[] orderIds = new long[30];
	static{
		for(int i=0;i<30;i++){
			orderIds[i] = Math.abs(new Random().nextInt(1000000000));
			userIds[i] = Math.abs(new Random().nextInt(2000000000));
		}
	}

	/**
	 * 测试数据插入的路由
	 */
	@Test
	public void testShardingOrderAdd() throws InterruptedException {

		String[] goods = {"Cooler", "Adases", "Hades", "miniCoper", "switch", "PS5", "GTAV"};
//		CopyOnWriteArrayList<OrderDetail> orderList = new CopyOnWriteArrayList();
		Random random = new Random(1000);
		DateTime createTime = DateTime.parse("2020-01-01");

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
			log.info("当前订单详情,用户ID:{},订单ID:{}",detail.getUserId(),detail.getOrderId());
			shardingOrderService.addOrderDetail(detail);
		}

	}

	//订单详情的删改查
	@Test
	public void testOrderNoUserId(){

		log.info("指定 order_id 不指定 user_id");
		long start = System.currentTimeMillis();
		List<OrderDetail> withUserId = new ArrayList<>(10);
		for(long orderId : orderIds){
			withUserId.addAll(shardingOrderService.selectList(orderId));
		}
		log.info("查询10个订单信息花费:{}毫秒",System.currentTimeMillis()-start);

		long startUpdate = System.currentTimeMillis();
		withUserId.forEach( detail -> {
			detail.setUpdateTime(new Date());
			shardingOrderService.update(detail);
		});
		log.info("更新10个订单信息花费{}毫秒!",System.currentTimeMillis()-startUpdate);

		long delStart = System.currentTimeMillis();
		withUserId.forEach( detail -> shardingOrderService.delOrderDetail(detail));
		log.info("删除10个订单信息耗费:{}毫秒",System.currentTimeMillis()-delStart);
	}


	/**
	 *  指定userId 和 orderId 的删改查
	 */
	@Test
	public void testOrderWithUserIdAndOrderId(){

		log.info("指定user_id 和 order_id");
		List<OrderDetail> withUserIdAndOrderId = new ArrayList<>(10);
		long start1 = System.currentTimeMillis();
		for(int i=0;i<orderIds.length;i++){
			withUserIdAndOrderId.addAll(shardingOrderService.selectList(userIds[i],	orderIds[i])) ;
		}
		log.info("查询10个订单信息花费:{}毫秒",System.currentTimeMillis()-start1);

		long startUpdate1 = System.currentTimeMillis();
		withUserIdAndOrderId.parallelStream().forEach( detail -> {
			detail.setUpdateTime(new Date());
			shardingOrderService.update(detail);
		});
		log.info("更新10个订单信息花费{}毫秒!",System.currentTimeMillis()-startUpdate1);

		long delStart1 = System.currentTimeMillis();
		withUserIdAndOrderId.forEach( detail -> shardingOrderService.delOrderDetail(detail));
		log.info("删除10个订单信息耗费:{}毫秒",System.currentTimeMillis()-delStart1);

	}


}
