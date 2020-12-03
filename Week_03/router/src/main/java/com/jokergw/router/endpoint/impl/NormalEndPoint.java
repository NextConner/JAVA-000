package com.jokergw.router.endpoint.impl;

import com.jokergw.router.endpoint.EndPoint;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/3 10:40
 * 单纯的 host:port / host-port 地址配置格式
 */
@Data
@AllArgsConstructor
public class NormalEndPoint implements EndPoint {

    private String host;
    private int port;

}
