package com.jokergw.router.routers.impl;

import com.jokergw.router.endpoint.EndPoint;
import com.jokergw.router.endpoint.impl.WeightEndPoint;
import com.jokergw.router.routers.HttpEndpointRouter;
import com.jokergw.router.routers.RouterProviderProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/27 16:19
 * 权重路由
 */
@Slf4j
public class WeightRouter implements HttpEndpointRouter {

    final Semaphore semaphore = new Semaphore(1);
    AtomicBoolean isSort = new AtomicBoolean(false);
    private AtomicInteger step = new AtomicInteger(0);
    private List<WeightEndPoint> list;

    public static void main(String[] args) throws Exception {

        ExecutorService service = Executors.newFixedThreadPool(3);

        List<WeightEndPoint> endPoints = new ArrayList<>();
        for (int i = 0; i < 5; i++) {

            String host = "192.168.100." + i;
            int port = 8000 + i;
            endPoints.add(new WeightEndPoint(host, port, i + i,0));
        }
        List<EndPoint> l = new ArrayList<>();
        l.addAll(endPoints);
        RouterProviderProperties.endPoints = l;

        WeightRouter robin = new WeightRouter();
        AtomicInteger i = new AtomicInteger(30);
        while (i.get() > 0) {
            service.submit(()->{
                i.getAndDecrement();
                robin.route();
            });
        }


    }

    @Override
    public EndPoint route() {
        WeightEndPoint weightEndPoint = null;

        try {
            if (semaphore.tryAcquire(2000, TimeUnit.MILLISECONDS)) {

                List<EndPoint> endPoints = RouterProviderProperties.endPoints;
                List<WeightEndPoint> weightEndPoints = new ArrayList<>();
                endPoints.forEach(ep -> {
                    if (ep instanceof WeightEndPoint) {
                        weightEndPoints.add((WeightEndPoint) ep);
                    }
                });

                if(!weightEndPoints.equals(list)){
                    list = weightEndPoints;
                }
                final int sumWeight = list.stream().mapToInt(WeightEndPoint::getWeight).sum();
                //增加当前权重
                list.forEach(endPoint -> endPoint.setCurrentWeight(endPoint.getWeight()+endPoint.getCurrentWeight()));
                //平滑加权算法
                weightEndPoint = list.parallelStream().max(Comparator.comparingInt(WeightEndPoint::getCurrentWeight)).get();
                weightEndPoint.setCurrentWeight(weightEndPoint.getCurrentWeight()-sumWeight);
            } else {
                log.info("未获取到许可！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }

        if (null == weightEndPoint) {
            log.info("Can not get useful WeightEndPoint , instead of normal endPoint!");
            return RouterProviderProperties.endPoints.get(new Random().nextInt(RouterProviderProperties.endPoints.size() - 1));
        } else {
            log.info("当前端点信息:{}", weightEndPoint.toString());
            return weightEndPoint;
        }
    }

}
