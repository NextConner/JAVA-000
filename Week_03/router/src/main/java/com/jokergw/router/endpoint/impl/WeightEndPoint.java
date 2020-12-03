package com.jokergw.router.endpoint.impl;

import com.jokergw.router.endpoint.EndPoint;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/3 10:56
 * 带权重的serverList 信息 ， 结构为 host:port:wight，weight 越小，优先级别越高
 */
@Data
@AllArgsConstructor
public class WeightEndPoint implements EndPoint {

    private String serverHost;
    private int port;
    private int weight;
    private int currentWeight;

}
