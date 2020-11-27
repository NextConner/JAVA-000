package com.jokergw.router.routers;

import com.jokergw.router.routers.impl.RandomRouter;
import com.jokergw.router.routers.impl.RoundRobinRouter;
import com.jokergw.router.routers.impl.WeightRouter;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.Random;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/27 16:45
 */
@ConfigurationProperties(prefix = "joker.router")
@Data
public class RouterProviderProperties {

    boolean random;
    boolean roundRobin;
    boolean weight;

    private LinkedList<HttpEndpointRouter> routers;

    @PostConstruct
    public void init(){
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
    }

}
