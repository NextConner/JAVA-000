package com.transfer.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/12 14:45
 */
@Builder
@Data
@Entity
@AllArgsConstructor
@Table(name = "t_transfer_record")
public class TransferRecord {

    @Id
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name="seller_id")
    private Long sellerId;

    /**
     * 转账状态 ： 0:进行中;1:转账成功;2:转账失败并取消
     */
    @Column(name = "status")
    private int status;

    @Column(name = "transfer_fee")
    private BigDecimal transferFee;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    public TransferRecord(){}

}




