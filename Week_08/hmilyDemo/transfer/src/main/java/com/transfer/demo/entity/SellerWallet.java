package com.transfer.demo.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/10 0:07
 */
@Data
public class SellerWallet {

        private Long id;
        private BigDecimal income;
        private BigDecimal outcome;
        private BigDecimal fee;
        private BigDecimal frozenFee;
        private Date createTime;
        private Date updateTime;

        @Override
        public String toString() {
                return String.format("商户账户信息:ID:%d , 余额:%s, 入账:%s, 出账:%s, 冻结金额:%s,更新时间:%s",id,fee,income,outcome,frozenFee,updateTime);
        }
}
