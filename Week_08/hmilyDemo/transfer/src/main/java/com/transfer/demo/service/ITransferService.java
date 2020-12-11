package com.transfer.demo.service;

import java.math.BigDecimal;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/10 17:53
 */

public interface ITransferService {

    String transferToSeller(Long userId , Long sellerId, BigDecimal transferMoney);

    String transferToUser(Long sellerId , Long userId, BigDecimal transferMoney);

}
