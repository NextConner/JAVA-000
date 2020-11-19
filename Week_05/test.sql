/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50712
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50712
File Encoding         : 65001

Date: 2020-11-19 09:03:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for elementsdata
-- ----------------------------
DROP TABLE IF EXISTS `elementsdata`;
CREATE TABLE `elementsdata` (
  `DATANAME` varchar(255) COLLATE utf8_bin NOT NULL,
  `DATAVALUE` varchar(255) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `age` int(20) NOT NULL,
  `sex` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for webelements
-- ----------------------------
DROP TABLE IF EXISTS `webelements`;
CREATE TABLE `webelements` (
  `ELEMENTNAME` varchar(255) COLLATE utf8_bin NOT NULL,
  `ELEMENTXPATH` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ELEMENTSELECT` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ELEMENTNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
