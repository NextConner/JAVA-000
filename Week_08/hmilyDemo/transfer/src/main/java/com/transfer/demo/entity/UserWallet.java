package com.transfer.demo.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/10 0:02
 */

@Data
public class UserWallet {

    private Long id;
    private BigDecimal income;
    private BigDecimal outcome;
    private BigDecimal fee;
    private BigDecimal frozenFee;
    private Date createTime;
    private Date updateTime;

    @Override
    public String toString() {
        return String.format("用户账户信息:余额:%s, 入账:%s, 出账:%s, 冻结金额:%s,更新时间:%s",fee,income,outcome,frozenFee,updateTime);
    }
}
