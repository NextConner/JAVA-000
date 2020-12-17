/*
 Navicat Premium Data Transfer

 Source Server         : master
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : localhost:3339
 Source Schema         : ds2

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 17/12/2020 11:43:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_order_detail_0
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail_0`;
CREATE TABLE `t_order_detail_0`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(64) NOT NULL COMMENT '订单ID',
  `user_id` bigint(64) NOT NULL COMMENT '用户id',
  `comdi_id` bigint(32) UNSIGNED NOT NULL COMMENT '商品id',
  `comdi_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品名称',
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品所属店铺名称',
  `sku_info` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '交易时sku信息',
  `status` int(2) NOT NULL COMMENT '订单详情状态，区分是否发生部分退款/退货\r\n0：交易完成；1：申请退货/退款；2：退货/退款处理中；3：退货/退款完成；\r\n',
  `price` decimal(10, 2) NOT NULL COMMENT '商品价格',
  `sell_price` decimal(10, 2) NOT NULL COMMENT '交易价格',
  `num` int(16) NOT NULL COMMENT '交易数量',
  `discount` decimal(10, 2) NOT NULL COMMENT '优惠金额',
  `shipping` decimal(10, 2) NOT NULL COMMENT '商品运费',
  `total_price` decimal(10, 2) NOT NULL COMMENT '当前商品总价',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_name`(`comdi_name`, `store_name`) USING BTREE COMMENT '订单id和商品名称索引',
  INDEX `idx_order_time`(`create_time`, `update_time`) USING BTREE COMMENT '时间索引',
  INDEX `idx_order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1607941978568 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order_detail_1
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail_1`;
CREATE TABLE `t_order_detail_1`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(64) NOT NULL COMMENT '订单ID',
  `user_id` bigint(64) NOT NULL COMMENT '用户id',
  `comdi_id` bigint(32) UNSIGNED NOT NULL COMMENT '商品id',
  `comdi_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品名称',
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品所属店铺名称',
  `sku_info` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '交易时sku信息',
  `status` int(2) NOT NULL COMMENT '订单详情状态，区分是否发生部分退款/退货\r\n0：交易完成；1：申请退货/退款；2：退货/退款处理中；3：退货/退款完成；\r\n',
  `price` decimal(10, 2) NOT NULL COMMENT '商品价格',
  `sell_price` decimal(10, 2) NOT NULL COMMENT '交易价格',
  `num` int(16) NOT NULL COMMENT '交易数量',
  `discount` decimal(10, 2) NOT NULL COMMENT '优惠金额',
  `shipping` decimal(10, 2) NOT NULL COMMENT '商品运费',
  `total_price` decimal(10, 2) NOT NULL COMMENT '当前商品总价',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_name`(`comdi_name`, `store_name`) USING BTREE COMMENT '订单id和商品名称索引',
  INDEX `idx_order_time`(`create_time`, `update_time`) USING BTREE COMMENT '时间索引',
  INDEX `idx_order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1607873189980 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order_detail_10
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail_10`;
CREATE TABLE `t_order_detail_10`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(64) NOT NULL COMMENT '订单ID',
  `user_id` bigint(64) NOT NULL COMMENT '用户id',
  `comdi_id` bigint(32) UNSIGNED NOT NULL COMMENT '商品id',
  `comdi_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品名称',
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品所属店铺名称',
  `sku_info` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '交易时sku信息',
  `status` int(2) NOT NULL COMMENT '订单详情状态，区分是否发生部分退款/退货\r\n0：交易完成；1：申请退货/退款；2：退货/退款处理中；3：退货/退款完成；\r\n',
  `price` decimal(10, 2) NOT NULL COMMENT '商品价格',
  `sell_price` decimal(10, 2) NOT NULL COMMENT '交易价格',
  `num` int(16) NOT NULL COMMENT '交易数量',
  `discount` decimal(10, 2) NOT NULL COMMENT '优惠金额',
  `shipping` decimal(10, 2) NOT NULL COMMENT '商品运费',
  `total_price` decimal(10, 2) NOT NULL COMMENT '当前商品总价',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_name`(`comdi_name`, `store_name`) USING BTREE COMMENT '订单id和商品名称索引',
  INDEX `idx_order_time`(`create_time`, `update_time`) USING BTREE COMMENT '时间索引',
  INDEX `idx_order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1607941868621 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order_detail_11
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail_11`;
CREATE TABLE `t_order_detail_11`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(64) NOT NULL COMMENT '订单ID',
  `user_id` bigint(64) NOT NULL COMMENT '用户id',
  `comdi_id` bigint(32) UNSIGNED NOT NULL COMMENT '商品id',
  `comdi_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品名称',
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品所属店铺名称',
  `sku_info` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '交易时sku信息',
  `status` int(2) NOT NULL COMMENT '订单详情状态，区分是否发生部分退款/退货\r\n0：交易完成；1：申请退货/退款；2：退货/退款处理中；3：退货/退款完成；\r\n',
  `price` decimal(10, 2) NOT NULL COMMENT '商品价格',
  `sell_price` decimal(10, 2) NOT NULL COMMENT '交易价格',
  `num` int(16) NOT NULL COMMENT '交易数量',
  `discount` decimal(10, 2) NOT NULL COMMENT '优惠金额',
  `shipping` decimal(10, 2) NOT NULL COMMENT '商品运费',
  `total_price` decimal(10, 2) NOT NULL COMMENT '当前商品总价',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_name`(`comdi_name`, `store_name`) USING BTREE COMMENT '订单id和商品名称索引',
  INDEX `idx_order_time`(`create_time`, `update_time`) USING BTREE COMMENT '时间索引',
  INDEX `idx_order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1607872991350 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order_detail_12
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail_12`;
CREATE TABLE `t_order_detail_12`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(64) NOT NULL COMMENT '订单ID',
  `user_id` bigint(64) NOT NULL COMMENT '用户id',
  `comdi_id` bigint(32) UNSIGNED NOT NULL COMMENT '商品id',
  `comdi_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品名称',
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品所属店铺名称',
  `sku_info` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '交易时sku信息',
  `status` int(2) NOT NULL COMMENT '订单详情状态，区分是否发生部分退款/退货\r\n0：交易完成；1：申请退货/退款；2：退货/退款处理中；3：退货/退款完成；\r\n',
  `price` decimal(10, 2) NOT NULL COMMENT '商品价格',
  `sell_price` decimal(10, 2) NOT NULL COMMENT '交易价格',
  `num` int(16) NOT NULL COMMENT '交易数量',
  `discount` decimal(10, 2) NOT NULL COMMENT '优惠金额',
  `shipping` decimal(10, 2) NOT NULL COMMENT '商品运费',
  `total_price` decimal(10, 2) NOT NULL COMMENT '当前商品总价',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_name`(`comdi_name`, `store_name`) USING BTREE COMMENT '订单id和商品名称索引',
  INDEX `idx_order_time`(`create_time`, `update_time`) USING BTREE COMMENT '时间索引',
  INDEX `idx_order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1607937865477 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order_detail_13
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail_13`;
CREATE TABLE `t_order_detail_13`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(64) NOT NULL COMMENT '订单ID',
  `user_id` bigint(64) NOT NULL COMMENT '用户id',
  `comdi_id` bigint(32) UNSIGNED NOT NULL COMMENT '商品id',
  `comdi_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品名称',
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品所属店铺名称',
  `sku_info` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '交易时sku信息',
  `status` int(2) NOT NULL COMMENT '订单详情状态，区分是否发生部分退款/退货\r\n0：交易完成；1：申请退货/退款；2：退货/退款处理中；3：退货/退款完成；\r\n',
  `price` decimal(10, 2) NOT NULL COMMENT '商品价格',
  `sell_price` decimal(10, 2) NOT NULL COMMENT '交易价格',
  `num` int(16) NOT NULL COMMENT '交易数量',
  `discount` decimal(10, 2) NOT NULL COMMENT '优惠金额',
  `shipping` decimal(10, 2) NOT NULL COMMENT '商品运费',
  `total_price` decimal(10, 2) NOT NULL COMMENT '当前商品总价',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_name`(`comdi_name`, `store_name`) USING BTREE COMMENT '订单id和商品名称索引',
  INDEX `idx_order_time`(`create_time`, `update_time`) USING BTREE COMMENT '时间索引',
  INDEX `idx_order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1607948333258 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order_detail_14
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail_14`;
CREATE TABLE `t_order_detail_14`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(64) NOT NULL COMMENT '订单ID',
  `user_id` bigint(64) NOT NULL COMMENT '用户id',
  `comdi_id` bigint(32) UNSIGNED NOT NULL COMMENT '商品id',
  `comdi_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品名称',
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品所属店铺名称',
  `sku_info` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '交易时sku信息',
  `status` int(2) NOT NULL COMMENT '订单详情状态，区分是否发生部分退款/退货\r\n0：交易完成；1：申请退货/退款；2：退货/退款处理中；3：退货/退款完成；\r\n',
  `price` decimal(10, 2) NOT NULL COMMENT '商品价格',
  `sell_price` decimal(10, 2) NOT NULL COMMENT '交易价格',
  `num` int(16) NOT NULL COMMENT '交易数量',
  `discount` decimal(10, 2) NOT NULL COMMENT '优惠金额',
  `shipping` decimal(10, 2) NOT NULL COMMENT '商品运费',
  `total_price` decimal(10, 2) NOT NULL COMMENT '当前商品总价',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_name`(`comdi_name`, `store_name`) USING BTREE COMMENT '订单id和商品名称索引',
  INDEX `idx_order_time`(`create_time`, `update_time`) USING BTREE COMMENT '时间索引',
  INDEX `idx_order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1607948467221 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order_detail_15
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail_15`;
CREATE TABLE `t_order_detail_15`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(64) NOT NULL COMMENT '订单ID',
  `user_id` bigint(64) NOT NULL COMMENT '用户id',
  `comdi_id` bigint(32) UNSIGNED NOT NULL COMMENT '商品id',
  `comdi_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品名称',
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品所属店铺名称',
  `sku_info` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '交易时sku信息',
  `status` int(2) NOT NULL COMMENT '订单详情状态，区分是否发生部分退款/退货\r\n0：交易完成；1：申请退货/退款；2：退货/退款处理中；3：退货/退款完成；\r\n',
  `price` decimal(10, 2) NOT NULL COMMENT '商品价格',
  `sell_price` decimal(10, 2) NOT NULL COMMENT '交易价格',
  `num` int(16) NOT NULL COMMENT '交易数量',
  `discount` decimal(10, 2) NOT NULL COMMENT '优惠金额',
  `shipping` decimal(10, 2) NOT NULL COMMENT '商品运费',
  `total_price` decimal(10, 2) NOT NULL COMMENT '当前商品总价',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_name`(`comdi_name`, `store_name`) USING BTREE COMMENT '订单id和商品名称索引',
  INDEX `idx_order_time`(`create_time`, `update_time`) USING BTREE COMMENT '时间索引',
  INDEX `idx_order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1607941868772 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order_detail_2
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail_2`;
CREATE TABLE `t_order_detail_2`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(64) NOT NULL COMMENT '订单ID',
  `user_id` bigint(64) NOT NULL COMMENT '用户id',
  `comdi_id` bigint(32) UNSIGNED NOT NULL COMMENT '商品id',
  `comdi_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品名称',
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品所属店铺名称',
  `sku_info` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '交易时sku信息',
  `status` int(2) NOT NULL COMMENT '订单详情状态，区分是否发生部分退款/退货\r\n0：交易完成；1：申请退货/退款；2：退货/退款处理中；3：退货/退款完成；\r\n',
  `price` decimal(10, 2) NOT NULL COMMENT '商品价格',
  `sell_price` decimal(10, 2) NOT NULL COMMENT '交易价格',
  `num` int(16) NOT NULL COMMENT '交易数量',
  `discount` decimal(10, 2) NOT NULL COMMENT '优惠金额',
  `shipping` decimal(10, 2) NOT NULL COMMENT '商品运费',
  `total_price` decimal(10, 2) NOT NULL COMMENT '当前商品总价',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_name`(`comdi_name`, `store_name`) USING BTREE COMMENT '订单id和商品名称索引',
  INDEX `idx_order_time`(`create_time`, `update_time`) USING BTREE COMMENT '时间索引',
  INDEX `idx_order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1607921294139 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order_detail_3
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail_3`;
CREATE TABLE `t_order_detail_3`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(64) NOT NULL COMMENT '订单ID',
  `user_id` bigint(64) NOT NULL COMMENT '用户id',
  `comdi_id` bigint(32) UNSIGNED NOT NULL COMMENT '商品id',
  `comdi_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品名称',
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品所属店铺名称',
  `sku_info` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '交易时sku信息',
  `status` int(2) NOT NULL COMMENT '订单详情状态，区分是否发生部分退款/退货\r\n0：交易完成；1：申请退货/退款；2：退货/退款处理中；3：退货/退款完成；\r\n',
  `price` decimal(10, 2) NOT NULL COMMENT '商品价格',
  `sell_price` decimal(10, 2) NOT NULL COMMENT '交易价格',
  `num` int(16) NOT NULL COMMENT '交易数量',
  `discount` decimal(10, 2) NOT NULL COMMENT '优惠金额',
  `shipping` decimal(10, 2) NOT NULL COMMENT '商品运费',
  `total_price` decimal(10, 2) NOT NULL COMMENT '当前商品总价',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_name`(`comdi_name`, `store_name`) USING BTREE COMMENT '订单id和商品名称索引',
  INDEX `idx_order_time`(`create_time`, `update_time`) USING BTREE COMMENT '时间索引',
  INDEX `idx_order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1607948332035 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order_detail_4
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail_4`;
CREATE TABLE `t_order_detail_4`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(64) NOT NULL COMMENT '订单ID',
  `user_id` bigint(64) NOT NULL COMMENT '用户id',
  `comdi_id` bigint(32) UNSIGNED NOT NULL COMMENT '商品id',
  `comdi_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品名称',
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品所属店铺名称',
  `sku_info` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '交易时sku信息',
  `status` int(2) NOT NULL COMMENT '订单详情状态，区分是否发生部分退款/退货\r\n0：交易完成；1：申请退货/退款；2：退货/退款处理中；3：退货/退款完成；\r\n',
  `price` decimal(10, 2) NOT NULL COMMENT '商品价格',
  `sell_price` decimal(10, 2) NOT NULL COMMENT '交易价格',
  `num` int(16) NOT NULL COMMENT '交易数量',
  `discount` decimal(10, 2) NOT NULL COMMENT '优惠金额',
  `shipping` decimal(10, 2) NOT NULL COMMENT '商品运费',
  `total_price` decimal(10, 2) NOT NULL COMMENT '当前商品总价',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_name`(`comdi_name`, `store_name`) USING BTREE COMMENT '订单id和商品名称索引',
  INDEX `idx_order_time`(`create_time`, `update_time`) USING BTREE COMMENT '时间索引',
  INDEX `idx_order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1607948467703 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order_detail_5
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail_5`;
CREATE TABLE `t_order_detail_5`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(64) NOT NULL COMMENT '订单ID',
  `user_id` bigint(64) NOT NULL COMMENT '用户id',
  `comdi_id` bigint(32) UNSIGNED NOT NULL COMMENT '商品id',
  `comdi_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品名称',
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品所属店铺名称',
  `sku_info` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '交易时sku信息',
  `status` int(2) NOT NULL COMMENT '订单详情状态，区分是否发生部分退款/退货\r\n0：交易完成；1：申请退货/退款；2：退货/退款处理中；3：退货/退款完成；\r\n',
  `price` decimal(10, 2) NOT NULL COMMENT '商品价格',
  `sell_price` decimal(10, 2) NOT NULL COMMENT '交易价格',
  `num` int(16) NOT NULL COMMENT '交易数量',
  `discount` decimal(10, 2) NOT NULL COMMENT '优惠金额',
  `shipping` decimal(10, 2) NOT NULL COMMENT '商品运费',
  `total_price` decimal(10, 2) NOT NULL COMMENT '当前商品总价',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_name`(`comdi_name`, `store_name`) USING BTREE COMMENT '订单id和商品名称索引',
  INDEX `idx_order_time`(`create_time`, `update_time`) USING BTREE COMMENT '时间索引',
  INDEX `idx_order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1607922387272 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order_detail_6
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail_6`;
CREATE TABLE `t_order_detail_6`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(64) NOT NULL COMMENT '订单ID',
  `user_id` bigint(64) NOT NULL COMMENT '用户id',
  `comdi_id` bigint(32) UNSIGNED NOT NULL COMMENT '商品id',
  `comdi_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品名称',
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品所属店铺名称',
  `sku_info` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '交易时sku信息',
  `status` int(2) NOT NULL COMMENT '订单详情状态，区分是否发生部分退款/退货\r\n0：交易完成；1：申请退货/退款；2：退货/退款处理中；3：退货/退款完成；\r\n',
  `price` decimal(10, 2) NOT NULL COMMENT '商品价格',
  `sell_price` decimal(10, 2) NOT NULL COMMENT '交易价格',
  `num` int(16) NOT NULL COMMENT '交易数量',
  `discount` decimal(10, 2) NOT NULL COMMENT '优惠金额',
  `shipping` decimal(10, 2) NOT NULL COMMENT '商品运费',
  `total_price` decimal(10, 2) NOT NULL COMMENT '当前商品总价',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_name`(`comdi_name`, `store_name`) USING BTREE COMMENT '订单id和商品名称索引',
  INDEX `idx_order_time`(`create_time`, `update_time`) USING BTREE COMMENT '时间索引',
  INDEX `idx_order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1607948333071 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order_detail_7
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail_7`;
CREATE TABLE `t_order_detail_7`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(64) NOT NULL COMMENT '订单ID',
  `user_id` bigint(64) NOT NULL COMMENT '用户id',
  `comdi_id` bigint(32) UNSIGNED NOT NULL COMMENT '商品id',
  `comdi_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品名称',
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品所属店铺名称',
  `sku_info` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '交易时sku信息',
  `status` int(2) NOT NULL COMMENT '订单详情状态，区分是否发生部分退款/退货\r\n0：交易完成；1：申请退货/退款；2：退货/退款处理中；3：退货/退款完成；\r\n',
  `price` decimal(10, 2) NOT NULL COMMENT '商品价格',
  `sell_price` decimal(10, 2) NOT NULL COMMENT '交易价格',
  `num` int(16) NOT NULL COMMENT '交易数量',
  `discount` decimal(10, 2) NOT NULL COMMENT '优惠金额',
  `shipping` decimal(10, 2) NOT NULL COMMENT '商品运费',
  `total_price` decimal(10, 2) NOT NULL COMMENT '当前商品总价',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_name`(`comdi_name`, `store_name`) USING BTREE COMMENT '订单id和商品名称索引',
  INDEX `idx_order_time`(`create_time`, `update_time`) USING BTREE COMMENT '时间索引',
  INDEX `idx_order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1607942008634 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order_detail_8
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail_8`;
CREATE TABLE `t_order_detail_8`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(64) NOT NULL COMMENT '订单ID',
  `user_id` bigint(64) NOT NULL COMMENT '用户id',
  `comdi_id` bigint(32) UNSIGNED NOT NULL COMMENT '商品id',
  `comdi_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品名称',
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品所属店铺名称',
  `sku_info` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '交易时sku信息',
  `status` int(2) NOT NULL COMMENT '订单详情状态，区分是否发生部分退款/退货\r\n0：交易完成；1：申请退货/退款；2：退货/退款处理中；3：退货/退款完成；\r\n',
  `price` decimal(10, 2) NOT NULL COMMENT '商品价格',
  `sell_price` decimal(10, 2) NOT NULL COMMENT '交易价格',
  `num` int(16) NOT NULL COMMENT '交易数量',
  `discount` decimal(10, 2) NOT NULL COMMENT '优惠金额',
  `shipping` decimal(10, 2) NOT NULL COMMENT '商品运费',
  `total_price` decimal(10, 2) NOT NULL COMMENT '当前商品总价',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_name`(`comdi_name`, `store_name`) USING BTREE COMMENT '订单id和商品名称索引',
  INDEX `idx_order_time`(`create_time`, `update_time`) USING BTREE COMMENT '时间索引',
  INDEX `idx_order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1607941978047 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order_detail_9
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail_9`;
CREATE TABLE `t_order_detail_9`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(64) NOT NULL COMMENT '订单ID',
  `user_id` bigint(64) NOT NULL COMMENT '用户id',
  `comdi_id` bigint(32) UNSIGNED NOT NULL COMMENT '商品id',
  `comdi_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品名称',
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品所属店铺名称',
  `sku_info` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '交易时sku信息',
  `status` int(2) NOT NULL COMMENT '订单详情状态，区分是否发生部分退款/退货\r\n0：交易完成；1：申请退货/退款；2：退货/退款处理中；3：退货/退款完成；\r\n',
  `price` decimal(10, 2) NOT NULL COMMENT '商品价格',
  `sell_price` decimal(10, 2) NOT NULL COMMENT '交易价格',
  `num` int(16) NOT NULL COMMENT '交易数量',
  `discount` decimal(10, 2) NOT NULL COMMENT '优惠金额',
  `shipping` decimal(10, 2) NOT NULL COMMENT '商品运费',
  `total_price` decimal(10, 2) NOT NULL COMMENT '当前商品总价',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_name`(`comdi_name`, `store_name`) USING BTREE COMMENT '订单id和商品名称索引',
  INDEX `idx_order_time`(`create_time`, `update_time`) USING BTREE COMMENT '时间索引',
  INDEX `idx_order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1607923867842 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
