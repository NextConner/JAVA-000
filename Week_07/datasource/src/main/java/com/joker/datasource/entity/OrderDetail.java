package com.joker.datasource.entity;

import lombok.Data;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

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
    Date createTime;
    @Column(name = "update_time")
    Date updateTime;

    @Override
    public String toString() {
        return "订单详情:{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", comdId=" + comdId +
                ", condName='" + condName + '\'' +
                ", storeName='" + storeName + '\'' +
                ", skuInfo='" + skuInfo + '\'' +
                ", status=" + status +
                ", price=" + price +
                ", sellPrice=" + sellPrice +
                ", num=" + num +
                ", discount=" + discount +
                ", shipping=" + shipping +
                ", totalPrice=" + totalPrice +
                ", createTime=" + new DateTime(createTime.getTime()).toDate() +
                ", updateTime=" +  new DateTime(updateTime.getTime()).toDate() +
                '}';
    }
}
