package io.kimmking.rpcfx.nettyClient;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/31 16:39
 */
@Getter
@Setter
public class RpcProtocol {
    /**
     * 数据大小
     */
    private int len;

    /**
     * 数据内容
     */
    private byte[] content;
}