/*
 Navicat Premium Data Transfer

 Source Server         : master
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : localhost:3339
 Source Schema         : seller

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 17/12/2020 00:19:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_seller_wallet
-- ----------------------------
DROP TABLE IF EXISTS `t_seller_wallet`;
CREATE TABLE `t_seller_wallet`  (
  `id` int(64) NOT NULL COMMENT '用户账户id',
  `wallet_income` decimal(10, 2) NOT NULL COMMENT '入账',
  `wallet_outcome` decimal(10, 2) NOT NULL COMMENT '出账',
  `balance_fee` decimal(10, 2) NOT NULL COMMENT '余额',
  `frozen_fee` decimal(10, 2) NULL DEFAULT NULL COMMENT '转账时冻结金额',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '账户建立时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT ' 更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_seller_wallet
-- ----------------------------
INSERT INTO `t_seller_wallet` VALUES (10001, 0.00, 0.00, 100.00, 0.00, '2020-12-01 11:14:10', '2020-12-17 00:16:14');

SET FOREIGN_KEY_CHECKS = 1;
