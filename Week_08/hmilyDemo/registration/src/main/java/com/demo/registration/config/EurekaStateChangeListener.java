package com.demo.registration.config;

import com.netflix.appinfo.InstanceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/8/21 17:22
 */
@Slf4j
@Component
public class EurekaStateChangeListener {

    /**
     * 服务下线事件
     * @param canceledEvent
     */
    @EventListener
    public void listen(EurekaInstanceCanceledEvent canceledEvent){
        log.error("{} : {} 服务下线",canceledEvent.getServerId(),canceledEvent.getAppName());
    }

    /**
     * 服务注册事件
     * @param registeredEvent
     */
    @EventListener
    public void listen(EurekaInstanceRegisteredEvent registeredEvent){
        InstanceInfo info = registeredEvent.getInstanceInfo();
        log.info("{} 服务注册",info.getAppName());
    }

    /**
     * 服务续约事件
     * @param renewedEvent
     */
    @EventListener
    public void listen(EurekaInstanceRenewedEvent renewedEvent){
        log.info("{} : {} 服务续约成功",renewedEvent.getServerId(),renewedEvent.getAppName());
    }

    /**
     * 注册中心可用事件
     * @param availableEvent
     */
    @EventListener
    public void listen(EurekaRegistryAvailableEvent availableEvent){
        log.info("注册中心启动");
    }

    /**
     * eureka server 启动
     * @param serverStartedEvent
     */
    @EventListener
    public void listen(EurekaServerStartedEvent serverStartedEvent){
        log.info("eureka server 启动！");
    }

}
