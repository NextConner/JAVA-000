package com.joker.jokerGW.service;

import com.joker.jokerGW.annotation.FilterEnableAnnotation;
import org.springframework.stereotype.Service;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/11/25 16:56
 */
@Service
public class TestService {

    public static class Sntity{
        String name;

        public Sntity(String name){
            this.name  =name;
        }

        @Override
        public String toString() {
            return "name:"+name;
        }
    }

    @FilterEnableAnnotation
    public void testAnnotation(String name,Sntity sntity){
        System.out.println("test aspect");
    }

}
