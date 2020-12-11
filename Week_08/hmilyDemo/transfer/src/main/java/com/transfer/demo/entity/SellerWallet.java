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

        private long id;
        private long userId;
        private BigDecimal income;
        private BigDecimal outcome;
        private BigDecimal fee;
        private Date createTime;
        private Date updateTime;
}
