package com.transfer.demo.biz;

import com.alibaba.fastjson.JSONObject;
import org.dromara.hmily.annotation.HmilyTCC;

import java.math.BigDecimal;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/12 16:54
 */
public interface ITransferBzi {

    boolean transfer(Long userId, Long sellerId , BigDecimal transferMoney);

    boolean transferTryException(Long userId, Long sellerId , BigDecimal transferMoney);

    boolean transferConfirmException(Long userId, Long sellerId , BigDecimal transferMoney);

    JSONObject getUserAndSellerInfo();

//    public boolean confirm(Long userId, Long sellerId , BigDecimal transferMoney);
//
//    public boolean cancel(Long userId, Long sellerId , BigDecimal transferMoney);

}
