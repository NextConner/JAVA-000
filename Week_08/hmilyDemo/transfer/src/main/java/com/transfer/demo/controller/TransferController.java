package com.transfer.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.transfer.demo.biz.ITransferBzi;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public TransferController(ITransferBzi transferBzi) {
        this.transferBzi = transferBzi;
    }

    /**
     * 用户向商家转账
     * 1. 在 try 阶段检查是否有足够完成业务操作的资源，在这里就是检查用户账户余额是否满足转账要求，
     * 满足的话对用户账户余额进行扣减，并添加对应的冻结金额
     * 2. 在confirm 阶段 需要使用 try 预留的资源，也就是冻结的金额进行转账，
     * 顺序应该是转账成功后，再将用户账户的冻结金额减去转账金额；这里不能直接将冻结金额清零，因为可能存在连续的对多人转账
     * 3. confirm 阶段可能发生异常的情况，例如商户转账失败，那用户金额就一直被冻结一部分；
     * 或者商户转账成功，更新用户冻结金额失败，例如表记录被某个本地事务锁住，更新超时，应该就成了单边事务，
     * 但是对用户和商家来说，结果是对的--用户金额扣减，商户金额增加。按照 hmily 的TCC逻辑，confirm 异常会重试一定次数，
     * 这种情况下需要做幂等判断，不能发生重复的confirm
     * 4. cancel 就是取消try 的操作，内容最简单，只需要反向操作try ，但是需要允许空回滚和处理防悬挂
     *
     * @param userId        用户ID
     * @param sellerId      商户ID
     * @param transferMoney 转账金额
     * @return
     */
    @ApiOperation(value = "向商户转账接口")
    @GetMapping("toSeller")
    public boolean transferToSeller(@RequestParam(value = "userId") Long userId,
                                    @RequestParam(value = "sellerId") Long sellerId,
                                    @RequestParam(value = "transferMoney") BigDecimal transferMoney) {

        return transferBzi.transfer(userId, sellerId, transferMoney);
    }

    /**
     * 模拟 try 阶段异常
     *
     * @param userId
     * @param sellerId
     * @param transferMoney
     * @return
     */
    @ApiOperation(value = "向商户转账接口,模拟 try 阶段异常")
    @GetMapping("toSellerTryException")
    public boolean transferToSellerWithTryException(@RequestParam(value = "userId") Long userId,
                                                    @RequestParam(value = "sellerId") Long sellerId,
                                                    @RequestParam(value = "transferMoney") BigDecimal transferMoney) {
        return transferBzi.transferTryException(userId, sellerId, transferMoney);
    }

    /**
     * 模拟 confirm 阶段异常
     *
     * @param userId
     * @param sellerId
     * @param transferMoney
     * @return
     */
    @ApiOperation(value = "向商户转账接口,模拟 confirm 阶段异常")
    @GetMapping("toSellerConfirmException")
    public boolean transferToSellerWithConfirmException(@RequestParam(value = "userId") Long userId,
                                                        @RequestParam(value = "sellerId") Long sellerId,
                                                        @RequestParam(value = "transferMoney") BigDecimal transferMoney) {
        return transferBzi.transferConfirmException(userId, sellerId, transferMoney);
    }

    /**
     * 查询当前用户和商家账户信息
     *
     * @return
     */
    @ApiOperation(value = "查询所有用户和商家信息")
    @GetMapping("getUserSellerInfo")
    public String getInfo() {

        JSONObject result = transferBzi.getUserAndSellerInfo();
        result.put("success", true);
        return result.toJSONString();
    }

}
