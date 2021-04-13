package com.redis.demo.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/7/21 15:52
 */
@Data
public class Rating {

    private String system;
    private String level;
    private int ratingAge;

}
