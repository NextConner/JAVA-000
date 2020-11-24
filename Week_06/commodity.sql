/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50712
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50712
File Encoding         : 65001

Date: 2020-11-24 08:29:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for commodity
-- ----------------------------
DROP TABLE IF EXISTS `commodity`;
CREATE TABLE `commodity` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `sub_name` varchar(125) COLLATE utf8_bin NOT NULL,
  `full_name` varchar(255) COLLATE utf8_bin NOT NULL,
  `brand_id` bigint(16) NOT NULL,
  `brand_info` varchar(255) COLLATE utf8_bin NOT NULL,
  `sku_id` bigint(16) NOT NULL,
  `sku_info` varchar(255) COLLATE utf8_bin NOT NULL,
  `pre_price` int(8) NOT NULL,
  `suf_price` double(2,0) NOT NULL DEFAULT '0',
  `cover_img` varchar(255) COLLATE utf8_bin NOT NULL,
  `imgs` text COLLATE utf8_bin,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL,
  `user_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `phone` bigint(13) unsigned NOT NULL,
  `mail` varchar(32) COLLATE utf8_bin NOT NULL,
  `password` varchar(64) COLLATE utf8_bin NOT NULL,
  `default_address` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNI_PHONE` (`phone`) USING BTREE,
  UNIQUE KEY `UNI_MAIL` (`mail`) USING BTREE,
  KEY `NAME_LOGIN_INDEX` (`user_name`,`password`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
