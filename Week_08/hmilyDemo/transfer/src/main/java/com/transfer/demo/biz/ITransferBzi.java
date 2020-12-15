package com.transfer.demo.biz;

import org.dromara.hmily.annotation.HmilyTCC;

import java.math.BigDecimal;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/12 16:54
 */
public interface ITransferBzi {

    boolean transfer(Long userId, Long sellerId , BigDecimal transferMoney);

//    public boolean confirm(Long userId, Long sellerId , BigDecimal transferMoney);
//
//    public boolean cancel(Long userId, Long sellerId , BigDecimal transferMoney);

}
