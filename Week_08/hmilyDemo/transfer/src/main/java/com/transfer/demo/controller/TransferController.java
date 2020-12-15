package com.transfer.demo.controller;

import com.transfer.demo.biz.ITransferBzi;
import com.transfer.demo.service.ITransferService;
import com.transfer.demo.service.impl.TransferServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/12 16:15
 */

@Slf4j
@RestController
@RequestMapping("/transfer/")
public class TransferController {

    private final ITransferBzi transferBzi;

    @Autowired
    public TransferController(ITransferBzi transferBzi){
        this.transferBzi = transferBzi;
    }

    @GetMapping("toSeller")
    public boolean transferToSeller(Long userId, Long sellerId, BigDecimal transferMoney){
        return transferBzi.transfer(userId,sellerId,transferMoney);
    }


}
