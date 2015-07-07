/*
Navicat MySQL Data Transfer

Source Server         : gxf
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : snmp_manager

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2015-05-10 15:44:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for communication
-- ----------------------------
DROP TABLE IF EXISTS `communication`;
CREATE TABLE `communication` (
  `communicationId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `ip` varchar(30) DEFAULT NULL,
  `port` int(255) DEFAULT '-1',
  PRIMARY KEY (`communicationId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of communication
-- ----------------------------
INSERT INTO `communication` VALUES ('7', '网络通信', '210.38.235.186', '0');
INSERT INTO `communication` VALUES ('8', '网络通信', '210.38.235.186', '0');

-- ----------------------------
-- Table structure for display
-- ----------------------------
DROP TABLE IF EXISTS `display`;
CREATE TABLE `display` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `curPlaySolutionName` varchar(50) DEFAULT NULL,
  `comment` varchar(100) DEFAULT NULL,
  `communicationId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `curPlaySolutionId` (`curPlaySolutionName`),
  KEY `communicationId` (`communicationId`),
  CONSTRAINT `communicationId` FOREIGN KEY (`communicationId`) REFERENCES `communication` (`communicationId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of display
-- ----------------------------
INSERT INTO `display` VALUES ('7', '兴中道', '双色屏', null, '7', '7');
INSERT INTO `display` VALUES ('8', '博爱路', '双色屏', null, '8', '8');

-- ----------------------------
-- Table structure for picture
-- ----------------------------
DROP TABLE IF EXISTS `picture`;
CREATE TABLE `picture` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `picName` varchar(50) DEFAULT NULL,
  `picPath` varchar(100) DEFAULT NULL,
  `playsolutionId` int(11) DEFAULT NULL,
  `playControlId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `playSolutionId` (`playsolutionId`),
  KEY `playControlId` (`playControlId`),
  CONSTRAINT `playControlId` FOREIGN KEY (`playControlId`) REFERENCES `playcontrol` (`id`) ON DELETE CASCADE,
  CONSTRAINT `playSolutionId` FOREIGN KEY (`playsolutionId`) REFERENCES `playsolution` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of picture
-- ----------------------------
INSERT INTO `picture` VALUES ('33', '20150507_150143984.bmp', 'D:\\Github_Eclipse\\Manager_JFace\\兴中道\\兴中道注意安全\\20150507_150143984.bmp', '9', '16');
INSERT INTO `picture` VALUES ('34', '20150507_150159468.bmp', 'D:\\Github_Eclipse\\Manager_JFace\\兴中道\\兴中道注意安全\\20150507_150159468.bmp', '9', '17');
INSERT INTO `picture` VALUES ('35', '20150507_150207421.bmp', 'D:\\Github_Eclipse\\Manager_JFace\\兴中道\\兴中道注意安全\\20150507_150207421.bmp', '9', '18');
INSERT INTO `picture` VALUES ('36', '20150507_150218781.bmp', 'D:\\Github_Eclipse\\Manager_JFace\\兴中道\\兴中道注意安全\\20150507_150218781.bmp', '9', '19');
INSERT INTO `picture` VALUES ('37', '20150507_150232531.bmp', 'D:\\Github_Eclipse\\Manager_JFace\\兴中道\\兴中道注意安全\\20150507_150232531.bmp', '9', '20');

-- ----------------------------
-- Table structure for playcontrol
-- ----------------------------
DROP TABLE IF EXISTS `playcontrol`;
CREATE TABLE `playcontrol` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `playType` int(2) DEFAULT NULL,
  `timeInterval` int(11) DEFAULT NULL,
  `dateTimeStart` date DEFAULT NULL,
  `dateTimeEnd` date DEFAULT NULL,
  `timeStart` time DEFAULT NULL,
  `timeEnd` time DEFAULT NULL,
  `weekdays` char(7) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of playcontrol
-- ----------------------------
INSERT INTO `playcontrol` VALUES ('16', '1', '1', '2015-04-28', '3000-07-08', '00:00:00', '23:59:59', '1111110');
INSERT INTO `playcontrol` VALUES ('17', '1', '2', '2015-04-07', '3000-07-25', '00:00:00', '23:59:59', '1111101');
INSERT INTO `playcontrol` VALUES ('18', '1', '3', '2015-04-14', '3000-07-06', '05:00:00', '23:59:59', '1101111');
INSERT INTO `playcontrol` VALUES ('19', '1', '4', '2015-04-07', '3000-07-23', '00:00:06', '23:59:59', '1111011');
INSERT INTO `playcontrol` VALUES ('20', '2', '6', '2015-04-20', '3002-07-06', '02:00:00', '20:59:59', '1100011');

-- ----------------------------
-- Table structure for playsolution
-- ----------------------------
DROP TABLE IF EXISTS `playsolution`;
CREATE TABLE `playsolution` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `updatecount` int(11) DEFAULT NULL,
  `comment` varchar(100) DEFAULT NULL,
  `displayId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_1yjxdljdby1hdspm6rjbu7nny` (`displayId`),
  CONSTRAINT `FK_1yjxdljdby1hdspm6rjbu7nny` FOREIGN KEY (`displayId`) REFERENCES `display` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of playsolution
-- ----------------------------
INSERT INTO `playsolution` VALUES ('9', '兴中道注意安全', '2015-05-07 00:00:00', '2015-05-07 00:00:00', '0', '兴中道注意安全', '7');
INSERT INTO `playsolution` VALUES ('10', '博爱路注意安全', '2015-05-07 00:00:00', '2015-05-07 00:00:00', '0', '博爱路注意安全', '8');
