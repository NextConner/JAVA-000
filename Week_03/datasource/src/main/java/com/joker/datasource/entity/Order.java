package com.joker.datasource.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "t_user_order")
public class Order {

    @Id
    private long id;
    @Column(name = "user_id")
    private long userid;
    @Column(name = "user_name")
    private String username;
    @Column(name = "nick_name")
    private String nickname;
    private long phone;
    @Column(name = "rece_addr")
    private String receAddr;
    double shipping;
    @Column(name = "order_price")
    double orderPrice;
    @Column(name = "pay_price")
    double payPrice;
    String couponId;
    double discount;
    @Column(name = "track_number")
    String trackNumber;
    @Column(name = "track_name")
    String trackName;
    @Column(name = "track_info")
    String trackInfo;
    int status;
    @Column(name = "is_del")
    boolean isDel;
    @Column(name = "create_time")
    Timestamp createTime;
    @Column(name = "update_time")
    Timestamp updateTime;


    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userid=" + userid +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", phone=" + phone +
                ", receAddr='" + receAddr + '\'' +
                ", shipping=" + shipping +
                ", orderPrice=" + orderPrice +
                ", payPrice=" + payPrice +
                ", couponId='" + couponId + '\'' +
                ", discount=" + discount +
                ", trackNumber='" + trackNumber + '\'' +
                ", trackName='" + trackName + '\'' +
                ", trackInfo='" + trackInfo + '\'' +
                ", status=" + status +
                ", isDel=" + isDel +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
