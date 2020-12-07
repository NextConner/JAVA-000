/*
 Navicat Premium Data Transfer

 Source Server         : master
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : localhost:3339
 Source Schema         : order_master

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 07/12/2020 15:13:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_commodity
-- ----------------------------
DROP TABLE IF EXISTS `t_commodity`;
CREATE TABLE `t_commodity`  (
  `id` bigint(32) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `store_id` int(32) NOT NULL COMMENT '商品所属店铺ID',
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品所属店铺名称',
  `first_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品名称',
  `sub_name` varchar(125) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品附加名称',
  `full_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品全称',
  `brand_id` bigint(16) NOT NULL COMMENT '品牌ID',
  `brand_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '品牌信息',
  `status` tinyint(1) UNSIGNED NOT NULL COMMENT '上下架状态：1：上架；0:下架',
  `stock` int(8) NOT NULL COMMENT '库存',
  `sku_id` bigint(16) NOT NULL COMMENT 'skuID',
  `sku_info` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'sku 信息',
  `price` decimal(16, 2) NOT NULL COMMENT '基础价',
  `sale_price` decimal(16, 2) NOT NULL COMMENT '销售价',
  `cover_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品封面图',
  `imgs` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '商品展示图',
  `des` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述信息',
  `create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '商品的创建时间',
  `update_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '商品的更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_price`(`sale_price`) USING BTREE,
  INDEX `idx_name`(`first_name`, `sub_name`, `full_name`, `brand_info`, `create_time`, `update_time`) USING BTREE COMMENT '搜索索引',
  INDEX `idx_time`(`create_time`, `update_time`) USING BTREE,
  INDEX `uk_id`(`id`, `sku_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail`;
CREATE TABLE `t_order_detail`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(64) NOT NULL COMMENT '订单ID',
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
) ENGINE = InnoDB AUTO_INCREMENT = 9223366075329572693 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint(32) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `id_card` varchar(18) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `user_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `nick_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `phone` bigint(11) UNSIGNED NOT NULL COMMENT '手机号',
  `mail` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '邮箱',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  `head_pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户头像',
  `age` int(3) UNSIGNED NOT NULL,
  `sex` tinyint(1) NOT NULL COMMENT '用户性别：0-男性；1-女性',
  `home_town` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '籍贯信息',
  `default_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '默认收货地址',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UNI_PHONE`(`phone`) USING BTREE,
  UNIQUE INDEX `UNI_MAIL`(`mail`) USING BTREE,
  UNIQUE INDEX `UNI_ID_CARD`(`id_card`) USING BTREE,
  INDEX `NAME_LOGIN_INDEX`(`user_name`, `password`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_order
-- ----------------------------
DROP TABLE IF EXISTS `t_user_order`;
CREATE TABLE `t_user_order`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `user_id` bigint(64) UNSIGNED NOT NULL COMMENT '用户id',
  `user_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `nick_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户昵称',
  `phone` bigint(11) NOT NULL COMMENT '手机号码',
  `rece_addr` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '收货地址',
  `shipping` decimal(10, 2) NOT NULL COMMENT '运费',
  `order_price` decimal(10, 2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '订单价格',
  `pay_price` decimal(10, 2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '支付价格',
  `coupon_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '优惠券ID',
  `discount` decimal(10, 2) NOT NULL COMMENT '优惠金额',
  `track_number` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '快递单号',
  `track_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '承运快递方',
  `track_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '快递基本信息',
  `status` int(10) UNSIGNED NOT NULL COMMENT '订单状态：0:未付款；1:已付款，待发货；2: 运输中；3:待签收；4:已签收；5:已取消；6:申请退款/退货；7:发起投诉；8:退货/退款/投诉处理中；9:退款/退货/投诉处理完成；10：订单作废；11：订单交易完成；12：待评价；',
  `is_del` tinyint(1) NOT NULL COMMENT '订单删除状态: 1:已删除；0：未删除',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '订单创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '订单更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_name_status_time`(`status`, `create_time`, `update_time`) USING BTREE COMMENT '组合索引',
  INDEX `idx_user_id`(`user_id`) USING BTREE COMMENT '用户id索引'
) ENGINE = InnoDB AUTO_INCREMENT = 9079182131175074972 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
