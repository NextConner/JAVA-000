/*
 Navicat Premium Data Transfer

 Source Server         : slave
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : localhost:3340
 Source Schema         : transfer

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 17/12/2020 00:18:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_transfer_record
-- ----------------------------
DROP TABLE IF EXISTS `t_transfer_record`;
CREATE TABLE `t_transfer_record`  (
  `id` bigint(64) NOT NULL COMMENT 'id',
  `user_id` bigint(64) NOT NULL COMMENT '用户ID',
  `transfer_fee` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '用户转账冻结金额',
  `seller_id` bigint(64) NOT NULL COMMENT '商户ID',
  `status` int(2) NOT NULL COMMENT '当前转账交易完成状态',
  `create_time` datetime(0) NOT NULL COMMENT '交易记录创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '交易记录更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
