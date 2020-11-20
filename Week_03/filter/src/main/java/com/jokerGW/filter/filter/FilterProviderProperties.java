package com.jokerGW.filter.filter;

import com.jokerGW.filter.filter.impl.AccessRequestFilter;
import com.jokerGW.filter.filter.impl.EasyAuthRequestFilter;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.util.LinkedList;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/19 17:42
 */
@ConfigurationProperties(prefix = "joker.filter")
@Data
public class FilterProviderProperties {

    private boolean auth;
    private boolean access;
    private LinkedList<RequestFilter> filterChines;

    @PostConstruct
    public void init(){
        filterChines = new LinkedList<>();
        if(auth){
            filterChines.add(new EasyAuthRequestFilter());
        }
        if(access){
            filterChines.add(new AccessRequestFilter());
        }

    }


}
