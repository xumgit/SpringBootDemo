/*
 Navicat Premium Data Transfer

 Source Server         : smartinstall
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost
 Source Database       : studyplugin

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : utf-8

 Date: 11/05/2018 20:42:04 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `nbaStar`
-- ----------------------------
DROP TABLE IF EXISTS `nbaStar`;
CREATE TABLE `nbaStar` (
  `id` int(11) NOT NULL,
  `number` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `team` varchar(100) DEFAULT NULL,
  `thumb` varchar(100) DEFAULT NULL,
  `votes` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `nbaStar`
-- ----------------------------
BEGIN;
INSERT INTO `nbaStar` VALUES 
('1', '23', 'James', 'PF', 'CLE', 'james.png', '1988'), 
('2', '30', 'Curry', 'SG', 'GSW', 'curry.png', '1865'), 
('3', '7', 'Anthony', 'C', 'NYN', 'anthony.png', '1495'), 
('4', '3', 'Paul', 'PG', 'LAC', 'paul.png', '1600'), 
('5', '13', 'Harden', 'SF', 'HOU', 'harden.png', '1813'), 
('6', '2', 'Irving', 'PG', 'CLE', 'irving.png', '1361'), 
('7', '7', 'Lin', 'PG', 'NET', 'lin.png', '1200'), 
('8', '3', 'Wade', 'SG', 'MIA', 'wade.png', '1532'), 
('9', '0', 'Westbrook', 'SG', 'OCT', 'westbrook.png', '2017'), 
('10', '35', 'Durant', 'SF', 'ASW', 'durant.png', '1721');
COMMIT;
