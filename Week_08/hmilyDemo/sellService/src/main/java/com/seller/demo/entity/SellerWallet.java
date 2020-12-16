package com.seller.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/10 0:07
 */
@Data
@Entity
@Table(name = "t_seller_wallet")
public class SellerWallet {

        @Id
        private long id;
        @Column(name = "seller_id")
        private long sellerId;

        @Column(name = "wallet_income")
        private BigDecimal income;

        @Column(name = "wallet_outcome")
        private BigDecimal outcome;

        @Column(name = "balance_fee")
        private BigDecimal fee;

        @Column(name = "frozen_fee")
        private BigDecimal frozenFee;

        @Column(name = "create_time")
        private Date createTime;

        @Column(name = "update_time")
        private Date updateTime;

}
