package com.joker.datasource.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/1 18:03
 */

@Data
@Entity
@Table(name = "t_order_detail")
public class OrderDetail {

    @Id
    long id;
    @Column(name = "order_id")
    long orderId;
    @Column(name = "comdi_id")
    long comdId;
    @Column(name = "comdi_name")
    String condName;
    @Column(name = "store_name")
    String storeName;
    @Column(name = "sku_info")
    String skuInfo;
    int status;
    BigDecimal price;
    @Column(name = "sell_price")
    BigDecimal sellPrice;
    int num;
    BigDecimal discount;
    BigDecimal shipping;
    @Column(name = "total_price")
    BigDecimal totalPrice;
    @Column(name = "create_time")
    Timestamp createTime;
    @Column(name = "update_time")
    Timestamp updateTime;


}
