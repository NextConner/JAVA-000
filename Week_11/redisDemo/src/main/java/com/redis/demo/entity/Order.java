package com.redis.demo.entity;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2021/1/21 11:05
 */
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order implements Serializable {


    private String orderId;
    private String orderDetailId;
    private String goodsId;
    private String goodsName;
    private BigDecimal price;
    private BigDecimal totalPrice;
    private Date createTime;
    private Date updateTime;

}