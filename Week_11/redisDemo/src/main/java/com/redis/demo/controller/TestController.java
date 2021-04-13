package com.redis.demo.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/7/29 15:49
 */
@Slf4j
@RestController
@RequestMapping("/test/")
public class TestController {

    public static String TIME_FORMATTER = "YYYY-MM-dd hh:mm:ss:SSSS";
    private static ExecutorService service = Executors.newFixedThreadPool(6);
    //使用ThreadLocal将指定的数据绑定到当前线程
    ThreadLocal<String> threadLocal = new ThreadLocal<>();
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @RequestMapping("testCache")
    public String testExec() throws InterruptedException {
//        redisTemplate.opsForValue().set("userKey", "kingTaoZou");

        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(8);
        ExecutorService exec = Executors.newFixedThreadPool(8);
        for (int i = 0; i < 8; i++) {
            int finalI = i;
            exec.execute(() -> {
                try {
                    start.await();
//                    System.out.println("尚未完成所有线程创建！");
                    end.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                System.out.println("时间 : " + System.currentTimeMillis());
                //使用随机数产生锁，避免随机数重复导致其它连接获得了锁，匹配随机数代表获取到了锁，

                //加解锁
                boolean lockResult = false;
                lockResult = lock("userKeyLock", 5, TimeUnit.SECONDS);
                if (finalI == 0) {
                    lockResult = lock("userKeyLock", 5, TimeUnit.SECONDS);
                } else {
                    lockResult = lock("userKeyLock");
                }
                int times = 100;
                int waitTime = 2;
                int hasWait = 0;
                //自旋五次
                while (!lockResult && times > 0) {
                    try {
                        hasWait++;
                        System.out.println(System.currentTimeMillis() + "-------- : " + Thread.currentThread().getName() + "自旋" + hasWait + "次");
                        times--;
                        TimeUnit.MILLISECONDS.sleep(500);
                        if (finalI == 0) {
                            lockResult = lock("userKeyLock", 3, TimeUnit.SECONDS);
//                            System.out.println(Thread.currentThread().getName() + " 加锁5秒，阻塞7秒");

                        } else {
                            lockResult = lock("userKeyLock");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (lockResult) {
                    System.out.println(Thread.currentThread().getName() + " 加锁成功！");
                    if(Thread.currentThread().getName().contains("thread-2")){
                        throw new RuntimeException("自动异常测试锁释放！");
                    }
                    if (finalI == 0) {
                        try {
                            TimeUnit.SECONDS.sleep(7);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("上一个线程设置的值 : " + String.valueOf(redisTemplate.opsForValue().get("userKey")));
                        }
                    }).start();
                    redisTemplate.opsForValue().set("userKey", "JokerZou : " + finalI);
                    boolean unlockResult = unlock("userKeyLock");
                    if (unlockResult) {
                        System.out.println(Thread.currentThread().getName() + " 解锁完成");
                    } else {
                        System.out.println(Thread.currentThread().getName() + " 解锁失败，当前线程锁已过期，此时锁为:" + redisTemplate.opsForValue().get("userKeyLock"));
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + "未获得锁，暂时无法操作!");
                }
                //                redisTemplate.execute(new RedisCallback() {
//                    @Override
//                    public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
//                        redisConnection.set
//                    }
//                });

//                oper.set("userKey", "kingTaoZou" + ":" + String.valueOf(finalI));
            });
        }
        start.countDown();
        System.out.println("开始");
        end.await();
        exec.shutdown();
        Object re = redisTemplate.opsForValue().get("userKey");
        if (null == re) {
            return "";
        } else {
            return re.toString();
        }
    }

    @RequestMapping("testRedisData")
    public void testRedisData(String key) {

        ListOperations operations = redisTemplate.opsForList();
        int i = 200;
        while (i > 0) {
            i--;
            UUID uuid = UUID.randomUUID();
            long timeStamp = System.currentTimeMillis();
            operations.leftPush(key, timeStamp + "-" + uuid);
        }

        System.out.println("list lpop");
        int j = 10;
        while (j > 0) {
            j--;
            System.out.println(operations.leftPop(key));
        }

        j = 5;
        System.out.println("list rpop");
        while (j > 0) {
            j--;
            System.out.println(operations.rightPop(key));
        }
        j = 5;
        System.out.println("list range");
        System.out.println(operations.range(key, 0, 10));

    }

    @RequestMapping("safeBlockQueue")
    public void safeBlockQueue(String key, String dealKey) {

        redisTemplate.delete(key);
        ListOperations operations = redisTemplate.opsForList();
        LinkedList<UUID> linkedList = new LinkedList<>();
        int i = 200;
        while (i > 0) {
            i--;
            UUID uuid = UUID.randomUUID();
            long timeStamp = System.currentTimeMillis();
            operations.leftPush(key, timeStamp + "-" + uuid);
        }

        new Thread(() -> {
            int finalI = 0;
            while (true) {
                finalI++;
                if (finalI >= 200) {
                    break;
                }
                Object value = operations.rightPopAndLeftPush(key, dealKey);
                System.out.println(value.toString());

                if (System.currentTimeMillis() % 2 == 0) {
                    System.out.println("假设此资源未被消费");
                    //从处理中队列删除
                    System.out.println(operations.rightPopAndLeftPush(dealKey, key).toString());
                } else {
                    System.out.println("资源已被处理");
                    System.out.println(operations.rightPop(dealKey));
                }


            }
        }).start();

    }

    @RequestMapping("cycleList")
    public void cycleList(String key) throws InterruptedException {

        redisTemplate.delete(key);
        ListOperations operations = redisTemplate.opsForList();
        LinkedList<UUID> linkedList = new LinkedList<>();
        int i = 20;
        while (i > 0) {
            i--;
            UUID uuid = UUID.randomUUID();
            long timeStamp = System.currentTimeMillis();
            operations.leftPush(key, timeStamp + "-" + uuid);
        }
        long now = System.currentTimeMillis();
        while (true) {
            TimeUnit.SECONDS.sleep(1);
            System.out.println(" 循环Key : " + operations.rightPopAndLeftPush(key, key));
            if (now + 30000 <= System.currentTimeMillis()) {
                break;
            }
        }
    }

    @RequestMapping("ltrime")
    public void ltrime(String key) throws InterruptedException {

        /**
         *  假设按照访问时间记录用户信息，LPUSH + LTRIM 可以维持一个最近访问列表
         */

        redisTemplate.delete(key);
        ListOperations operations = redisTemplate.opsForList();
        int i = 200;
        int j = 0;
        while (i > 0) {
            i--;
            j++;
            DateTime now = DateTime.now();
            DateTimeFormatter format = DateTimeFormat.forPattern(TIME_FORMATTER);
            String nowStr = now.toString(format);
            System.out.println(nowStr);
            operations.leftPush(key, "THE " + i + ":" + nowStr);
            if (j > 5) {
                //裁剪最近入队的三个数据
                operations.trim(key, 0, 2);
                System.out.println(operations.range(key, 0, 2));
                j = 0;
                return;
            }
        }


    }

    private boolean lock(String key, int lockTime, TimeUnit unit) {
        //随机数存储，多线程访问且唯一
        UUID uuid = UUID.randomUUID();
        long milTime = System.currentTimeMillis();
        String randomKey = uuid.toString().replace("-", "").substring(0, 10) + String.valueOf(milTime);
        boolean result = redisTemplate.opsForValue().setIfAbsent(key, randomKey, lockTime, unit);
        if (result) {
            System.out.println(Thread.currentThread().getName() + " 随机加锁值 " + randomKey);
            threadLocal.set(randomKey);
        }
        return result;
    }

    /**
     * 单实例redis 客户端加锁，
     * 并不是线程安全的操作，可能客户端A加锁成功，但是在锁过期时间内没有执行完成；
     * 锁会被redis自动释放掉，然后客户端B获取到，如果此时A 直接删除锁，就会删除掉B的锁；
     * 所以客户端A删除锁之前再校验一次当前的随机值，一样的情况下才进行锁删除
     *
     * @param key
     * @return
     */
    private boolean lock(String key) {
        //随机数存储，多线程访问且唯一
        UUID uuid = UUID.randomUUID();
        long milTime = System.currentTimeMillis();
        String randomKey = uuid.toString().replace("-", "").substring(0, 10) + String.valueOf(milTime);
        System.out.println(Thread.currentThread().getName() + " 随机加锁值 " + randomKey);
        boolean result = redisTemplate.opsForValue().setIfAbsent(key, randomKey, 5, TimeUnit.SECONDS);
        if (result) {
            threadLocal.set(randomKey);
        }
        return result;
    }

    private boolean unlock(String key) {
        boolean unlockResult = false;
        String lockValue = String.valueOf(redisTemplate.opsForValue().get(key));
        if (StringUtils.isNotBlank(lockValue) && lockValue.equals(threadLocal.get())) {
            unlockResult = redisTemplate.delete(key);
        }
        return unlockResult;
    }

    @RequestMapping("producerConsumer")
    public void producerConsumer(String producerKey, String consumerKey) throws Exception {

        /**
         *  基于 RPOPLPUSH 的阻塞版本 BRPOPLPUSH 实现阻塞队列
         */
        redisTemplate.delete(producerKey);
        redisTemplate.delete(consumerKey);
        ListOperations operations = redisTemplate.opsForList();
        LinkedList<UUID> linkedList = new LinkedList<>();
        int i = 200;
        new Thread(() -> {
            //阻塞5秒等待生产
            while (true) {
                try {
                    Object result = operations.rightPopAndLeftPush(producerKey, consumerKey, 5, TimeUnit.SECONDS);
                    if (null == result) {
                        System.out.println("生产对象: 空");
                    } else {
                        System.out.println("生产对象: " + result.toString());
                        System.out.println("消费对象 : " + operations.leftPop(consumerKey).toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        while (i > 0) {
            i--;
            if (i < 150) {
                TimeUnit.SECONDS.sleep(6);
            }
            UUID uuid = UUID.randomUUID();
            operations.leftPush(producerKey, uuid);
        }
    }


    @RequestMapping(value = "pubOrderMsg", method = RequestMethod.GET)
    public void pubOrderMsg(String message) {

        JSONObject json = new JSONObject();
        json.put("message", message);
        json.put("time", new Date());

        log.info("发redis 发布消息:{}", json.toJSONString());
        redisTemplate.convertAndSend("order", json.toJSONString());
    }


    /**
     * 尝试模拟分布式扣减库存
     */
    @RequestMapping("storeDesc")
    public String storeDesc(String key) throws InterruptedException {

        CountDownLatch lock = new CountDownLatch(5);
        CountDownLatch start = new CountDownLatch(1);

        JSONObject jsonObject = new JSONObject();
        try {
            redisTemplate.opsForValue().set(key, "20");
            //多线程扣库存
            for (int i = 0; i < 30; i++) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            start.await();
                            desc(key);
                            lock.countDown();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                //线程池执行
                service.execute(runnable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        start.countDown();
        lock.await();

        return jsonObject.toJSONString();
    }

    private void desc(String key) {
        try {
            boolean lockResult = lock("userKeyLock", 500, TimeUnit.MILLISECONDS);
            System.out.println(Thread.currentThread().getName() + " 加锁5秒，阻塞7秒");
            while(!lockResult) {
                TimeUnit.SECONDS.sleep(1);
                lockResult = lock("userKeyLock", 500, TimeUnit.MILLISECONDS);
            }

            if (lockResult) {
                AtomicLong lastStore = new AtomicLong(0L);
                lastStore.set(redisTemplate.opsForValue().decrement(key, 0L));
                if (lastStore.get() < 1) {
                    log.info("当前库存量:{}       {}", DateTime.now().toString(), lastStore.get());
                } else {
                    long re = redisTemplate.opsForValue().decrement(key, 1L);
                    log.info("扣减后库存量:{}      {}", DateTime.now().toString(), re);
                }
                boolean unlockResult = unlock("userKeyLock");
                if (unlockResult) {
                    System.out.println(Thread.currentThread().getName() + " 解锁完成");
                } else {
                    System.out.println(Thread.currentThread().getName() + " 解锁失败，当前线程锁已过期，此时锁为:" + redisTemplate.opsForValue().get("userKeyLock"));
                }
            }else{
                log.info("{}-加锁失败",Thread.currentThread().getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
