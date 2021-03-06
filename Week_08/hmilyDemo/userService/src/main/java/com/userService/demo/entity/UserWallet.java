package com.userService.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/10 0:02
 */

@Data
@Entity
@Table(name = "t_user_wallet")
public class UserWallet {

    @Id
    private long id;

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
