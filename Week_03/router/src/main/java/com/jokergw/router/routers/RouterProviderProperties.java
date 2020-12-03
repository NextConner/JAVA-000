package com.jokergw.router.routers;

import com.jokergw.router.endpoint.EndPoint;
import com.jokergw.router.endpoint.impl.NormalEndPoint;
import com.jokergw.router.endpoint.impl.WeightEndPoint;
import com.jokergw.router.routers.impl.RandomRouter;
import com.jokergw.router.routers.impl.RoundRobinRouter;
import com.jokergw.router.routers.impl.WeightRouter;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/27 16:45
 */
@ConfigurationProperties(prefix = "joker.router")
@Data
public class RouterProviderProperties {

    private boolean random;
    private boolean roundRobin;
    private boolean weight;

    /**
     * 不同的配置对应不同的EndPoint实现
     */
    private String serverList;
    private String weightServerList;

    private LinkedList<HttpEndpointRouter> routers;

    public static List<EndPoint> endPoints;

    @PostConstruct
    public void init(){
        //初始化路由器
        routers = new LinkedList<>();
        if(random){
            routers.add(new RandomRouter());
        }
        if(roundRobin){
            routers.add(new RoundRobinRouter());
        }
        if(weight){
            routers.add(new WeightRouter());
        }

        //初始化路由地址信息列表
        endPoints = new ArrayList<>(12);
        if(StringUtils.hasLength(serverList)){
            String[] serverAddress = serverList.split(",");
            for(String serverInfo : serverAddress){
                endPoints.add(new NormalEndPoint(serverInfo.split("-")[0],Integer.valueOf(serverInfo.split("-")[1])));
            }
        }
        if(StringUtils.hasLength(weightServerList)){
            String[] serverAddress = serverList.split(",");
            for(String serverInfo : serverAddress){
                endPoints.add(new WeightEndPoint(serverInfo.split("-")[0],Integer.valueOf(serverInfo.split("-")[1]),Integer.valueOf(serverInfo.split("-")[2]),0));
            }
        }
    }

}
