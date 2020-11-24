/*
 Navicat Premium Data Transfer

 Source Server         : 本地库
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3306
 Source Schema         : yuedaovideo

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 24/11/2020 18:24:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for commodity
-- ----------------------------
DROP TABLE IF EXISTS `commodity`;
CREATE TABLE `commodity`  (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `first_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品名称',
  `sub_name` varchar(125) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品附加名称',
  `full_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品全称',
  `brand_id` bigint(16) NOT NULL COMMENT '品牌ID',
  `brand_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '品牌信息',
  `sku_id` bigint(16) NOT NULL COMMENT 'skuID',
  `sku_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'sku 信息',
  `pre_price` int(8) NOT NULL COMMENT '价格整数部分',
  `suf_price` double(2, 0) NOT NULL DEFAULT 0 COMMENT '价格小数部分',
  `cover_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品封面图',
  `imgs` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '商品展示图',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `user_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `phone` bigint(13) UNSIGNED NOT NULL COMMENT '手机号',
  `mail` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '邮箱',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  `default_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '默认收货地址',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UNI_PHONE`(`phone`) USING BTREE,
  UNIQUE INDEX `UNI_MAIL`(`mail`) USING BTREE,
  INDEX `NAME_LOGIN_INDEX`(`user_name`, `password`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
