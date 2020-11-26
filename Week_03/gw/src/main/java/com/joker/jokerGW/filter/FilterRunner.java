package com.joker.jokergw.filter;

import com.joker.jokergw.inbound.HttpInboundInitializer;
import com.joker.jokergw.inbound.HttpInboundServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/20 15:21
 */
@Slf4j
@Component
public class FilterRunner implements CommandLineRunner {

    @Autowired
    private HttpInboundInitializer inboundInitializer;

    @Override
    public void run(String... args) throws Exception {
        log.info("runner run!");
        HttpInboundServer server = new HttpInboundServer(9062,"192.168.1.95");
        server.run(inboundInitializer);
    }


}
