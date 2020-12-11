package com.sharding.demo.util;

import java.math.BigInteger;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/9 20:01
 */
public class DataSourceUtil {

    public static void main(String[] args) {

        long orderId =809619478;
        long userId = 341818360;
        //ds0.t6
        System.out.println( "ds" + userId%4 + ".t_order_detail_" + orderId%16);
//        System.out.println(" orderId 分表 : " +  orderId%16);


    }

}
