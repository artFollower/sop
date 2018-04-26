/*
Navicat MySQL Data Transfer

Source Server         : 192.168.35.117-mysql-sop
Source Server Version : 50529
Source Host           : 192.168.35.117:3306
Source Database       : sop

Target Server Type    : MYSQL
Target Server Version : 50529
File Encoding         : 65001

Date: 2015-01-21 10:51:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_auth_accountabilities
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_accountabilities`;
CREATE TABLE `t_auth_accountabilities` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category` int(11) DEFAULT NULL,
  `fromDate` bigint(20) DEFAULT NULL,
  `endDate` bigint(20) DEFAULT NULL,
  `isParincipal` tinyint(1) DEFAULT NULL,
  `parentId` bigint(20) DEFAULT NULL,
  `childId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK779E29366D0466CB` (`childId`),
  KEY `FK779E2936B4258FA9` (`parentId`),
  CONSTRAINT `FK779E29366D0466CB` FOREIGN KEY (`childId`) REFERENCES `t_auth_parties` (`id`),
  CONSTRAINT `FK779E2936B4258FA9` FOREIGN KEY (`parentId`) REFERENCES `t_auth_parties` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_auth_accountabilities
-- ----------------------------

-- ----------------------------
-- Table structure for t_auth_authorization
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_authorization`;
CREATE TABLE `t_auth_authorization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `accountId` bigint(20) DEFAULT NULL,
  `roleId` bigint(20) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9A527AD1B3340F61` (`accountId`),
  KEY `FK9A527AD1349C2921` (`roleId`),
  CONSTRAINT `FK9A527AD1349C2921` FOREIGN KEY (`roleId`) REFERENCES `t_auth_role` (`id`),
  CONSTRAINT `FK9A527AD1B3340F61` FOREIGN KEY (`accountId`) REFERENCES `t_auth_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_auth_authorization
-- ----------------------------
INSERT INTO `t_auth_authorization` VALUES ('10', null, '1', '4');
INSERT INTO `t_auth_authorization` VALUES ('11', null, '22', '23');
INSERT INTO `t_auth_authorization` VALUES ('15', null, '23', '26');
INSERT INTO `t_auth_authorization` VALUES ('16', null, '22', '24');
INSERT INTO `t_auth_authorization` VALUES ('17', null, '22', '25');
INSERT INTO `t_auth_authorization` VALUES ('18', null, '24', '27');
INSERT INTO `t_auth_authorization` VALUES ('22', null, '23', '28');
INSERT INTO `t_auth_authorization` VALUES ('23', null, '24', '28');

-- ----------------------------
-- Table structure for t_auth_menu_resources_relation
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_menu_resources_relation`;
CREATE TABLE `t_auth_menu_resources_relation` (
  `id` int(11) NOT NULL,
  `parentId` bigint(20) NOT NULL,
  `childId` bigint(20) NOT NULL,
  `level` int(11) DEFAULT NULL,
  `menu_icon` char(100) DEFAULT NULL,
  `position` int(11) DEFAULT NULL,
  `menuIcon` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA4E3D4E4C6FAEA04` (`childId`),
  KEY `FKA4E3D4E4DF7783B6` (`parentId`),
  CONSTRAINT `FKA4E3D4E4C6FAEA04` FOREIGN KEY (`childId`) REFERENCES `t_auth_security_resources` (`id`),
  CONSTRAINT `FKA4E3D4E4DF7783B6` FOREIGN KEY (`parentId`) REFERENCES `t_auth_security_resources` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_auth_menu_resources_relation
-- ----------------------------

-- ----------------------------
-- Table structure for t_auth_parties
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_parties`;
CREATE TABLE `t_auth_parties` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category` char(100) DEFAULT NULL,
  `createDate` bigint(20) DEFAULT NULL,
  `name` varchar(30) NOT NULL,
  `SN` varchar(50) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `orgPrincipal` tinyint(1) DEFAULT NULL,
  `persionId` bigint(20) DEFAULT NULL,
  `parentId` bigint(20) DEFAULT NULL,
  `jobId` int(11) DEFAULT NULL,
  `orgId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  KEY `PK_KO_PARTIES` (`id`),
  KEY `FKDABA62098D5C4D9` (`persionId`),
  KEY `FK_lljmyhg155m3lrudu1mtnpwc4` (`parentId`),
  CONSTRAINT `FKDABA62098D5C4D9` FOREIGN KEY (`persionId`) REFERENCES `t_auth_persion` (`id`),
  CONSTRAINT `FK_lljmyhg155m3lrudu1mtnpwc4` FOREIGN KEY (`parentId`) REFERENCES `t_auth_parties` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_auth_parties
-- ----------------------------
INSERT INTO `t_auth_parties` VALUES ('1', 'COMPANY', null, '石化', null, null, null, null, null, null, null, null);
INSERT INTO `t_auth_parties` VALUES ('2', 'DEPARTMENT', '0', '研发部', null, '', '0', null, null, null, null, null);
INSERT INTO `t_auth_parties` VALUES ('3', 'DEPARTMENT', '0', '售前', null, '', '0', null, null, null, null, null);
INSERT INTO `t_auth_parties` VALUES ('4', 'EMPLOYEE', null, '管理员', null, null, null, null, '2', null, null, '4');

-- ----------------------------
-- Table structure for t_auth_persion
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_persion`;
CREATE TABLE `t_auth_persion` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `EMAIL` varchar(255) DEFAULT NULL,
  `FAMILY_PHONE` varchar(255) DEFAULT NULL,
  `GENDER` varchar(255) DEFAULT NULL,
  `ID_NUMBER` varchar(255) DEFAULT NULL,
  `MOBILE_PHONE` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `sn` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ID_NUMBER` (`ID_NUMBER`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_auth_persion
-- ----------------------------

-- ----------------------------
-- Table structure for t_auth_resource_assignments
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_resource_assignments`;
CREATE TABLE `t_auth_resource_assignments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleId` bigint(20) DEFAULT NULL,
  `sourceId` bigint(20) DEFAULT NULL,
  `orgId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_424r7jpglc49pmuvd00y82iup` (`sourceId`),
  KEY `FK_4vaecdmli8l1netwo5q83wems` (`roleId`),
  CONSTRAINT `FK_424r7jpglc49pmuvd00y82iup` FOREIGN KEY (`sourceId`) REFERENCES `t_auth_security_resources` (`id`),
  CONSTRAINT `FK_4vaecdmli8l1netwo5q83wems` FOREIGN KEY (`roleId`) REFERENCES `t_auth_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_auth_resource_assignments
-- ----------------------------
INSERT INTO `t_auth_resource_assignments` VALUES ('19', '1', '5', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('20', '1', '6', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('21', '1', '7', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('22', '1', '8', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('25', '1', '12', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('26', '1', '13', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('27', '1', '14', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('28', '1', '15', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('29', '1', '16', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('30', '1', '17', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('31', '1', '18', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('34', '1', '19', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('35', '1', '20', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('36', '1', '21', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('37', '1', '22', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('38', '1', '23', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('39', '1', '24', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('40', '1', '25', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('41', '1', '26', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('42', '1', '27', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('43', '1', '28', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('44', '1', '29', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('45', '1', '30', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('46', '1', '31', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('47', '1', '32', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('49', '22', '20', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('50', '22', '21', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('51', '22', '22', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('52', '22', '23', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('53', '22', '24', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('54', '22', '25', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('55', '22', '26', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('56', '22', '27', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('57', '22', '28', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('58', '22', '29', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('59', '22', '30', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('60', '22', '31', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('61', '22', '32', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('63', '23', '19', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('64', '23', '20', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('65', '23', '22', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('66', '23', '28', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('67', '23', '21', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('68', '23', '26', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('69', '23', '27', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('70', '1', '11', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('71', '22', '5', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('72', '22', '6', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('73', '24', '21', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('74', '24', '22', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('75', '24', '23', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('76', '24', '24', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('77', '24', '25', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('78', '24', '27', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('79', '24', '29', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('80', '24', '30', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('81', '24', '31', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('82', '24', '32', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('83', '23', '29', null);
INSERT INTO `t_auth_resource_assignments` VALUES ('84', '1', '10', null);

-- ----------------------------
-- Table structure for t_auth_role
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_role`;
CREATE TABLE `t_auth_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `type` varchar(50) NOT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_auth_role
-- ----------------------------
INSERT INTO `t_auth_role` VALUES ('1', '对系统各个模块进行管理', '超级管理员', 'ADMINISTRATOR', '0');
INSERT INTO `t_auth_role` VALUES ('22', '', '管理员', 'ADMINISTRATOR', '0');
INSERT INTO `t_auth_role` VALUES ('23', '', '商务部经理', 'MONITOR', '0');
INSERT INTO `t_auth_role` VALUES ('24', '', '生产运行部调度经理', 'MONITOR', '0');

-- ----------------------------
-- Table structure for t_auth_security_resources
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_security_resources`;
CREATE TABLE `t_auth_security_resources` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category` char(100) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `indentifier` varchar(255) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_auth_security_resources
-- ----------------------------
INSERT INTO `t_auth_security_resources` VALUES ('5', 'MENU', '系统管理菜单项', '系统管理', 'MSYSCONFIG', '0');
INSERT INTO `t_auth_security_resources` VALUES ('6', 'MENU', '', '用户管理', 'MUSER', '0');
INSERT INTO `t_auth_security_resources` VALUES ('7', 'MENU', '', '角色管理', 'MROLE', '0');
INSERT INTO `t_auth_security_resources` VALUES ('8', 'MENU', '', '权限管理', 'MPERMISSION', '0');
INSERT INTO `t_auth_security_resources` VALUES ('10', 'ACCESS', '添加用户的操作权限', '添加用户', 'userAdd', '0');
INSERT INTO `t_auth_security_resources` VALUES ('11', 'ACCESS', '', '修改用户', 'userUpdate', '0');
INSERT INTO `t_auth_security_resources` VALUES ('12', 'ACCESS', '', '删除用户', 'userDelete', '0');
INSERT INTO `t_auth_security_resources` VALUES ('13', 'ACCESS', '', '添加角色', 'roleAdd', '0');
INSERT INTO `t_auth_security_resources` VALUES ('14', 'ACCESS', '', '修改角色', 'roleUpdate', '0');
INSERT INTO `t_auth_security_resources` VALUES ('15', 'ACCESS', '', '删除角色', 'roleDelete', '0');
INSERT INTO `t_auth_security_resources` VALUES ('16', 'ACCESS', '', '添加权限', 'resourceAdd', '0');
INSERT INTO `t_auth_security_resources` VALUES ('17', 'ACCESS', '', '修改权限', 'resourceUpdate', '0');
INSERT INTO `t_auth_security_resources` VALUES ('18', 'ACCESS', '', '删除权限', 'resourceDelete', '0');
INSERT INTO `t_auth_security_resources` VALUES ('19', 'MENU', '', '合同管理', 'MCONTRACT', '0');
INSERT INTO `t_auth_security_resources` VALUES ('20', 'MENU', '', '意向管理', 'MINTENT', '0');
INSERT INTO `t_auth_security_resources` VALUES ('21', 'MENU', '', '入库管理', 'MINBOUND', '0');
INSERT INTO `t_auth_security_resources` VALUES ('22', 'MENU', '', '到港计划', 'MARRIVALPLAN', '0');
INSERT INTO `t_auth_security_resources` VALUES ('23', 'MENU', '', '入库作业', 'MINBOUNDWORK', '0');
INSERT INTO `t_auth_security_resources` VALUES ('24', 'MENU', '', '调度日志', 'MDISPARCHLOG', '0');
INSERT INTO `t_auth_security_resources` VALUES ('25', 'MENU', '', '储罐台账', 'MTANKLEDGER', '0');
INSERT INTO `t_auth_security_resources` VALUES ('26', 'MENU', '', '入库确认', 'MINBOUNDCONFIRM', '0');
INSERT INTO `t_auth_security_resources` VALUES ('27', 'MENU', '', '出库管理', 'MOUTBOUND', '0');
INSERT INTO `t_auth_security_resources` VALUES ('28', 'MENU', '', '提单管理', 'MLOADING', '0');
INSERT INTO `t_auth_security_resources` VALUES ('29', 'MENU', '', '出港计划', 'MOUTBOUNDPLAN', '0');
INSERT INTO `t_auth_security_resources` VALUES ('30', 'MENU', '', '船舶出库', 'MSHIPDELIVERY', '0');
INSERT INTO `t_auth_security_resources` VALUES ('31', 'MENU', '', '船发台账', 'MSHIPLEDGER', '0');
INSERT INTO `t_auth_security_resources` VALUES ('32', 'MENU', '', '车发出库', 'MCARDELIVERY', '0');

-- ----------------------------
-- Table structure for t_auth_user
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_user`;
CREATE TABLE `t_auth_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createDate` bigint(20) DEFAULT NULL,
  `createOwen` bigint(20) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `account` varchar(255) DEFAULT NULL,
  `personId` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `photo` char(255) DEFAULT NULL,
  `category` char(100) DEFAULT NULL,
  `salt` char(100) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `persionId` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_o6ir8ao2fkgehqgx5u4qw9dnr` (`createOwen`),
  CONSTRAINT `FK_o6ir8ao2fkgehqgx5u4qw9dnr` FOREIGN KEY (`createOwen`) REFERENCES `t_auth_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_auth_user
-- ----------------------------
INSERT INTO `t_auth_user` VALUES ('4', '0', null, null, '02d3062dc41c96ad5eada2a9bab11058', '18201752466', 'admin', null, '0', null, null, 'd913def689979bb2f96b3b2343ca2523', 'xiewei@skycloud.com', null, '管理员');
INSERT INTO `t_auth_user` VALUES ('23', '0', null, '', '52233618b5e202cb248359810171b62c', '18211011128', 'xw', null, '1', null, null, '064494a4be4498589775e44860885e90', 'xw@skycloudtech.com', null, '解伟');
INSERT INTO `t_auth_user` VALUES ('24', '0', null, '密码123456', '81e9ea257122f8660d0fa7fbdd33d3a9', '13871320298', 'gaowei', null, '0', null, null, '4a77ae65c857bb7c0296d00a2caaf78d', 'gaowei@sina.com', null, '高巍');
INSERT INTO `t_auth_user` VALUES ('25', '0', null, '密码123456', '5c4e48cb2c8f189a37d40e3c909becdc', '18211111111', 'wuke', null, '0', null, null, '4045404a7db4858db94650079bd9f3d6', 'wuke@qq.com', null, '吴科');
INSERT INTO `t_auth_user` VALUES ('26', '0', null, '密码123456', '73af4f98fffc9750f84cee3f28d49865', '13912341234', 'gumin', null, '0', null, null, '51f6b3580f5e030caafcfe37cdf28447', 'gumin@yzpc.com', null, '顾珉');
INSERT INTO `t_auth_user` VALUES ('27', '0', null, '密码123456', '4fbb9f0ce1432f3ebf867508cfd44508', '13612341234', 'gubin', null, '0', null, null, 'b4c36259d4f4e82fbec85f1e4ac11af4', 'gubin@yzpc.com', null, '顾彬');
INSERT INTO `t_auth_user` VALUES ('28', '0', null, '密码123456', '3afbbd733c7644639e31ace0349b868d', '13312341234', 'xxf', null, '0', null, null, 'a876e802f46a8c839aa194784899938e', 'xxf@yzpc.com', null, '徐宪锋');

-- ----------------------------
-- Table structure for t_pcs_arrival
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_arrival`;
CREATE TABLE `t_pcs_arrival` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '到港ID，主键，自增\r\n            ',
  `type` int(7) unsigned DEFAULT NULL COMMENT '0：接卸入库；1：船发出库；',
  `shipId` int(15) unsigned DEFAULT NULL,
  `shipRefId` int(7) DEFAULT NULL,
  `arrivalStartTime` timestamp NULL DEFAULT NULL,
  `arrivalEndTime` timestamp NULL DEFAULT NULL,
  `shipAgentId` int(15) unsigned DEFAULT NULL,
  `description` text,
  `status` int(7) unsigned DEFAULT NULL COMMENT '到港状态：0：预计到港；1：已到港；2：已离港\r\n            ',
  `goodsShip` varchar(15) DEFAULT NULL,
  `disptachId` int(20) unsigned DEFAULT NULL,
  `productId` int(11) DEFAULT NULL,
  `berthId` int(11) DEFAULT NULL,
  `arrivalTime` datetime DEFAULT NULL,
  `tradeType` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='类型区分是入库还是出库';

-- ----------------------------
-- Records of t_pcs_arrival
-- ----------------------------
INSERT INTO `t_pcs_arrival` VALUES ('1', '1', '1', '2', '2015-01-11 00:00:00', '2015-01-12 00:00:00', '0', '', '9', null, null, '0', '0', null, null);
INSERT INTO `t_pcs_arrival` VALUES ('5', '2', '2', '3', '2015-01-15 00:00:00', '2015-01-21 00:00:00', '0', 'sdf', '53', null, null, '5', '3', null, null);
INSERT INTO `t_pcs_arrival` VALUES ('8', '1', '4', '8', '2015-01-21 00:00:00', '2015-01-22 00:00:00', '0', '', '10', null, null, '0', '0', null, null);
INSERT INTO `t_pcs_arrival` VALUES ('9', '1', '4', '8', '2015-01-29 00:00:00', '2015-01-30 00:00:00', '2', '模拟2', '6', null, null, '0', '0', null, null);
INSERT INTO `t_pcs_arrival` VALUES ('12', '1', '3', '6', '2015-01-22 00:00:00', '2015-01-24 00:00:00', '3', '', '5', null, '0', '0', '0', null, null);
INSERT INTO `t_pcs_arrival` VALUES ('13', '2', '1', '1', '2015-01-08 00:00:00', '2015-01-15 00:00:00', '0', 'sdfsd', '53', null, '0', '2', '3', null, null);

-- ----------------------------
-- Table structure for t_pcs_arrival_bill
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_arrival_bill`;
CREATE TABLE `t_pcs_arrival_bill` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(15) DEFAULT NULL,
  `contractId` int(20) unsigned DEFAULT NULL,
  `totalPayable` varchar(15) DEFAULT NULL,
  `totalPaid` varchar(15) DEFAULT NULL,
  `status` int(7) unsigned DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `createUserId` int(20) unsigned DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT NULL,
  `editUserId` int(20) unsigned DEFAULT NULL,
  `editTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `dueTime` timestamp NULL DEFAULT NULL,
  `payTime` timestamp NULL DEFAULT NULL,
  `arrivalId` int(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_arrival_bill
-- ----------------------------
INSERT INTO `t_pcs_arrival_bill` VALUES ('14', 'Th002', '52', null, null, null, null, null, '2014-12-28 10:11:51', null, '2014-12-28 10:11:51', null, null, '1');

-- ----------------------------
-- Table structure for t_pcs_arrival_charge
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_arrival_charge`;
CREATE TABLE `t_pcs_arrival_charge` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `billId` int(20) unsigned DEFAULT NULL,
  `type` int(7) unsigned DEFAULT NULL,
  `quantity` varchar(15) DEFAULT NULL,
  `unitPrice` varchar(15) DEFAULT NULL,
  `totalPrice` varchar(15) DEFAULT NULL,
  `startTime` timestamp NULL DEFAULT NULL,
  `endTime` timestamp NULL DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_arrival_charge
-- ----------------------------
INSERT INTO `t_pcs_arrival_charge` VALUES ('61', '14', '4', '3', '3', '3', null, null, 'sdfsdf');
INSERT INTO `t_pcs_arrival_charge` VALUES ('62', '14', '5', '4', '4', '5', null, null, 'sdfds');
INSERT INTO `t_pcs_arrival_charge` VALUES ('63', '14', '6', '5', '5', '5', null, null, 'null');
INSERT INTO `t_pcs_arrival_charge` VALUES ('64', '14', '7', '', '', '', null, null, 'null');
INSERT INTO `t_pcs_arrival_charge` VALUES ('65', '14', '8', 'null', 'null', 'null', null, null, 'null');

-- ----------------------------
-- Table structure for t_pcs_arrival_info
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_arrival_info`;
CREATE TABLE `t_pcs_arrival_info` (
  `id` int(20) NOT NULL,
  `cjTime` bigint(20) DEFAULT NULL,
  `tcTime` bigint(20) DEFAULT NULL,
  `norTime` bigint(20) DEFAULT NULL,
  `anchorDate` bigint(20) DEFAULT NULL,
  `anchorTime` bigint(20) DEFAULT NULL,
  `pumpOpenTime` bigint(20) DEFAULT NULL,
  `pumpStopTime` bigint(20) DEFAULT NULL,
  `workTime` bigint(20) DEFAULT NULL,
  `leaveTime` bigint(20) DEFAULT NULL,
  `tearPipeTime` bigint(20) DEFAULT NULL,
  `portNum` varchar(100) DEFAULT NULL,
  `port` varchar(255) DEFAULT NULL,
  `report` char(100) DEFAULT NULL,
  `shipInfo` char(100) DEFAULT NULL,
  `note` text,
  `overTime` float(20,2) DEFAULT NULL,
  `repatriateTime` float(20,2) DEFAULT NULL,
  `lastLeaveTime` bigint(20) DEFAULT NULL,
  `arrivalId` int(11) DEFAULT NULL,
  `createUserId` int(11) DEFAULT NULL,
  `reviewUserId` int(11) DEFAULT NULL,
  `createTime` bigint(20) DEFAULT NULL,
  `reviewTime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_arrival_info
-- ----------------------------
INSERT INTO `t_pcs_arrival_info` VALUES ('1', '1420669854', '1419782454', '1420155354', null, '1421725560', '1420775754', '1421214355', '1420316454', '1420418455', '1421303455', null, '1', '已申报', '已收到', '接卸-1', null, null, '1420688154', '1', '4', null, null, null);
INSERT INTO `t_pcs_arrival_info` VALUES ('2', '1421822127', '1421822127', '1421822127', '1421818527', '1421822127', '1421823327', '1421822127', '1421823327', '1421909727', '323', '2', '323', '23', '23', '23', '23.00', '23.00', '2323', '3', null, null, null, null);
INSERT INTO `t_pcs_arrival_info` VALUES ('3', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '3', null, null, null, null);
INSERT INTO `t_pcs_arrival_info` VALUES ('4', '1420506633', '1421112333', '1420669833', '1420418433', '1420583434', '1420493134', '1420579534', '1419888334', '1420493134', '23', '1', '1', 'å·²ç³æ¥', 'å·²æ¶å°', 'å¤©å¤©', '11.00', '11.00', '11', '7', null, null, null, null);
INSERT INTO `t_pcs_arrival_info` VALUES ('5', '1420668646', '1421094046', '1421883046', null, '1421694960', '1421188246', '1421641546', '1421202646', '1421037946', '1421217046', '4', '汉口，连云港，仓头', '已申报', '已收到', '啊水水水水谁谁谁', '4.00', '5.00', '1422415846', '9', '4', null, '1421769600', '1421769600');
INSERT INTO `t_pcs_arrival_info` VALUES ('6', '1420049447', '1419782447', '1420065047', null, '1421838600', '1420599047', '1420065047', '1419798047', '1419888347', '1420065047', null, '1', '已申报', '已收到', '的风范', null, null, '1420597847', '8', '4', null, '1421769600', '1421769600');
INSERT INTO `t_pcs_arrival_info` VALUES ('7', '1420493131', '1419798031', '1419888331', '1420402831', '1420065031', '1419798031', '1420669831', '1419798031', '1420065031', '11', '1', '1', 'å·²ç³æ¥', 'å·²æ¶å°', 'èå¾·è¨', '11.00', '11.00', '11', '3', null, null, null, null);
INSERT INTO `t_pcs_arrival_info` VALUES ('8', '1421837727', '1421836527', '1422456927', '1421848227', '1422453327', '1421923827', '1421844628', '1421836528', '1421837728', '4234', '23', '234', '234', '4234', '23', '23.00', '4234.00', '234', '5', null, null, null, null);
INSERT INTO `t_pcs_arrival_info` VALUES ('9', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '11', null, null, null, null);
INSERT INTO `t_pcs_arrival_info` VALUES ('10', '1421188226', '1421732726', '1421823026', null, '1421781840', '1421879426', '1421732726', '1421718326', '1421732726', '1421909426', '5', '汉口，连云港，仓头', '已申报', '已收到', '大是大非', '4.00', '5.00', '1422355826', '12', '4', null, '1421769600', '1421769600');
INSERT INTO `t_pcs_arrival_info` VALUES ('11', '1421898906', '1421897406', '1421893806', '1421907906', '1421898906', '1421907906', '1421905506', '1421907906', '1421909406', '423', '23', '34', '234', '234', '3242', '434.00', '23.00', '234', '13', null, null, null, null);
INSERT INTO `t_pcs_arrival_info` VALUES ('12', '1421898906', '1421897406', '1421893806', '1421907906', '1421898906', '1421907906', '1421905506', '1421907906', '1421909406', '423', '23', '34', '234', '234', '3242', '434.00', '23.00', '234', '13', null, null, null, null);

-- ----------------------------
-- Table structure for t_pcs_arrival_plan
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_arrival_plan`;
CREATE TABLE `t_pcs_arrival_plan` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `arrivalId` int(20) unsigned DEFAULT NULL,
  `type` int(10) unsigned DEFAULT NULL,
  `tradeType` int(10) unsigned DEFAULT NULL,
  `shipId` int(10) unsigned DEFAULT NULL,
  `arrivalStartTime` timestamp NULL DEFAULT NULL,
  `arrivalEndTime` timestamp NULL DEFAULT NULL,
  `productId` int(10) unsigned DEFAULT NULL,
  `clientId` int(10) unsigned DEFAULT NULL,
  `goodsTotal` varchar(16) DEFAULT NULL,
  `shipAgentId` int(10) unsigned DEFAULT NULL,
  `requirement` varchar(255) DEFAULT NULL,
  `originalArea` varchar(64) DEFAULT NULL,
  `createUserId` int(10) unsigned DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `reviewUserId` int(10) unsigned DEFAULT NULL,
  `reviewTime` timestamp NULL DEFAULT NULL,
  `status` int(10) unsigned DEFAULT NULL,
  `arrivalTime` datetime DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  `goodTotal` varchar(255) DEFAULT NULL,
  `ladingId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_arrival_plan
-- ----------------------------
INSERT INTO `t_pcs_arrival_plan` VALUES ('1', '1', '0', '1', '1', '2015-01-11 00:00:00', '2015-01-12 00:00:00', '1', '1', '400', '0', '', null, '4', '2015-01-20 11:23:14', '0', null, '2', null, null, null, '0');
INSERT INTO `t_pcs_arrival_plan` VALUES ('2', '1', '0', '1', '1', '2015-01-11 00:00:00', '2015-01-12 00:00:00', '1', '2', '600', '0', '', null, '4', '2015-01-20 11:23:14', '0', null, '2', null, null, null, '0');
INSERT INTO `t_pcs_arrival_plan` VALUES ('3', '2', '2', '3', '0', null, null, '0', '3', '2322', '0', null, null, '4', '2015-01-20 12:48:37', '0', null, '0', null, null, null, '4');
INSERT INTO `t_pcs_arrival_plan` VALUES ('4', '2', '2', '1', '0', null, null, '0', '4', '2343', '0', null, null, '4', '2015-01-20 12:48:37', '0', null, '0', null, null, null, '3');
INSERT INTO `t_pcs_arrival_plan` VALUES ('5', '3', '2', '2', '0', null, null, '0', '3', '32', '0', null, null, '4', '2015-01-20 12:52:28', '0', null, '0', null, null, null, '4');
INSERT INTO `t_pcs_arrival_plan` VALUES ('6', '3', '2', '3', '0', null, null, '0', '3', '234', '0', null, null, '4', '2015-01-20 12:52:28', '0', null, '2', null, null, null, '3');
INSERT INTO `t_pcs_arrival_plan` VALUES ('7', '4', '2', '2', '0', null, null, '0', '1', '23', '0', null, null, '4', '2015-01-20 13:38:40', '0', null, '0', null, null, null, '4');
INSERT INTO `t_pcs_arrival_plan` VALUES ('9', '6', '2', '1', '0', null, null, '0', '1', '200', '0', null, null, '4', '2015-01-20 13:41:23', '0', null, '2', null, null, null, '4');
INSERT INTO `t_pcs_arrival_plan` VALUES ('10', '5', '2', '1', '0', null, null, '0', '3', '12', '0', null, null, '0', '2015-01-21 09:19:02', '0', null, '2', null, null, null, '1');
INSERT INTO `t_pcs_arrival_plan` VALUES ('11', '7', '2', '1', '0', null, null, '0', '3', '200', '0', null, null, '0', '2015-01-20 23:53:16', '0', null, '2', null, null, null, '1');
INSERT INTO `t_pcs_arrival_plan` VALUES ('12', '8', '0', '2', '4', '2015-01-20 00:00:00', '2015-01-21 00:00:00', '1', '2', '800', '0', '', null, '4', '2015-01-20 18:38:57', '0', null, '2', null, null, null, '0');
INSERT INTO `t_pcs_arrival_plan` VALUES ('13', '8', '0', '2', '4', '2015-01-20 00:00:00', '2015-01-21 00:00:00', '2', '3', '1200', '0', '', null, '4', '2015-01-20 18:38:57', '0', null, '2', null, null, null, '0');
INSERT INTO `t_pcs_arrival_plan` VALUES ('14', '9', '0', '1', '4', '2015-01-29 00:00:00', '2015-01-30 00:00:00', '1', '1', '1200', '0', '模拟1 ', null, '4', '2015-01-20 18:41:54', '0', null, '2', null, null, null, '0');
INSERT INTO `t_pcs_arrival_plan` VALUES ('15', '9', '0', '1', '4', '2015-01-29 00:00:00', '2015-01-30 00:00:00', '1', '2', '2100', '0', '模拟1', null, '4', '2015-01-20 18:41:54', '0', null, '2', null, null, null, '0');
INSERT INTO `t_pcs_arrival_plan` VALUES ('16', '9', '0', '1', '4', '2015-01-29 00:00:00', '2015-01-30 00:00:00', '2', '3', '1000', '0', '模拟1', null, '4', '2015-01-20 18:41:54', '0', null, '2', null, null, null, '0');
INSERT INTO `t_pcs_arrival_plan` VALUES ('17', '9', '0', '1', '4', '2015-01-29 00:00:00', '2015-01-30 00:00:00', '2', '4', '1300', '0', '模拟1', null, '4', '2015-01-20 18:41:54', '0', null, '2', null, null, null, '0');
INSERT INTO `t_pcs_arrival_plan` VALUES ('18', '11', '2', '3', '0', null, null, '0', '2', '234', '0', null, null, '0', '2015-01-20 23:49:54', '0', null, '2', null, null, null, '0');
INSERT INTO `t_pcs_arrival_plan` VALUES ('19', '11', '2', '1', '0', null, null, '0', '4', '100', '0', null, null, '0', '2015-01-20 23:35:18', '0', null, '2', null, null, null, '3');
INSERT INTO `t_pcs_arrival_plan` VALUES ('20', '11', '2', '1', '0', null, null, '0', '4', '300', '0', null, null, '0', '2015-01-20 23:34:55', '0', null, '2', null, null, null, '3');
INSERT INTO `t_pcs_arrival_plan` VALUES ('21', '12', '0', '1', '3', '2015-01-22 00:00:00', '2015-01-24 00:00:00', '1', '1', '400', '0', '', null, '4', '2015-01-20 21:16:49', '0', null, '2', null, null, null, '0');
INSERT INTO `t_pcs_arrival_plan` VALUES ('22', '12', '1', '1', '3', '2015-01-22 00:00:00', '2015-01-24 00:00:00', '1', '2', '400', '0', '', null, '4', '2015-01-20 21:17:04', '0', null, '2', null, null, null, '0');
INSERT INTO `t_pcs_arrival_plan` VALUES ('23', '12', '1', '3', '3', '2015-01-22 00:00:00', '2015-01-24 00:00:00', '2', '2', '500', '0', '', null, '4', '2015-01-20 21:17:27', '0', null, '2', null, null, null, '0');
INSERT INTO `t_pcs_arrival_plan` VALUES ('24', '13', '2', '2', '0', null, null, '0', '3', '23', '0', null, null, '4', '2015-01-21 09:58:00', '0', null, '2', null, null, null, '2');
INSERT INTO `t_pcs_arrival_plan` VALUES ('25', '13', '2', '1', '0', null, null, '0', '4', '22', '0', null, null, '4', '2015-01-21 09:58:00', '0', null, '2', null, null, null, '3');

-- ----------------------------
-- Table structure for t_pcs_arrival_status
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_arrival_status`;
CREATE TABLE `t_pcs_arrival_status` (
  `key` int(7) unsigned NOT NULL,
  `value` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_arrival_status
-- ----------------------------
INSERT INTO `t_pcs_arrival_status` VALUES ('0', '预计到港');
INSERT INTO `t_pcs_arrival_status` VALUES ('1', '预计确认');
INSERT INTO `t_pcs_arrival_status` VALUES ('2', '作业计划');
INSERT INTO `t_pcs_arrival_status` VALUES ('3', '靠泊评估');
INSERT INTO `t_pcs_arrival_status` VALUES ('4', '靠泊方案');
INSERT INTO `t_pcs_arrival_status` VALUES ('5', '接卸方案');
INSERT INTO `t_pcs_arrival_status` VALUES ('6', '接卸准备');
INSERT INTO `t_pcs_arrival_status` VALUES ('7', '打回流方案');
INSERT INTO `t_pcs_arrival_status` VALUES ('8', '打回流准备');
INSERT INTO `t_pcs_arrival_status` VALUES ('9', '数量审核');
INSERT INTO `t_pcs_arrival_status` VALUES ('10', '入库放行');
INSERT INTO `t_pcs_arrival_status` VALUES ('20', '预计到港');
INSERT INTO `t_pcs_arrival_status` VALUES ('21', '预计确认');
INSERT INTO `t_pcs_arrival_status` VALUES ('22', '作业计划');
INSERT INTO `t_pcs_arrival_status` VALUES ('23', '作业方案');
INSERT INTO `t_pcs_arrival_status` VALUES ('24', '船发准备');
INSERT INTO `t_pcs_arrival_status` VALUES ('25', '数量审核');
INSERT INTO `t_pcs_arrival_status` VALUES ('26', '打单出库');
INSERT INTO `t_pcs_arrival_status` VALUES ('40', '发货开票');
INSERT INTO `t_pcs_arrival_status` VALUES ('41', '车发作业');
INSERT INTO `t_pcs_arrival_status` VALUES ('42', '数量确认');
INSERT INTO `t_pcs_arrival_status` VALUES ('43', '打单出库');
INSERT INTO `t_pcs_arrival_status` VALUES ('50', '到港预报');
INSERT INTO `t_pcs_arrival_status` VALUES ('51', '靠泊方案');
INSERT INTO `t_pcs_arrival_status` VALUES ('52', '发货准备');
INSERT INTO `t_pcs_arrival_status` VALUES ('53', '数量确认');

-- ----------------------------
-- Table structure for t_pcs_arrival_work
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_arrival_work`;
CREATE TABLE `t_pcs_arrival_work` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `type` int(10) unsigned DEFAULT NULL,
  `tradeType` int(10) unsigned DEFAULT NULL,
  `contractId` int(11) DEFAULT NULL,
  `cargoId` int(11) DEFAULT NULL,
  `shipId` int(11) DEFAULT NULL,
  `arricalTime` timestamp NULL DEFAULT NULL,
  `productId` int(10) unsigned DEFAULT NULL,
  `clientId` int(10) unsigned DEFAULT NULL,
  `goodsTotal` varchar(16) DEFAULT NULL,
  `shipAgentId` int(10) unsigned DEFAULT NULL,
  `requirement` varchar(255) DEFAULT NULL,
  `originalArea` varchar(64) DEFAULT NULL,
  `createUserId` int(10) unsigned DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `reviewUserId` int(10) unsigned DEFAULT NULL,
  `reviewTime` timestamp NULL DEFAULT NULL,
  `status` int(10) unsigned DEFAULT NULL,
  `arrivalId` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_arrival_work
-- ----------------------------
INSERT INTO `t_pcs_arrival_work` VALUES ('1', '0', '0', '67', '1', '1', null, '1', '1', '400', '0', null, null, '0', '2015-01-20 11:23:35', '0', null, '0', '1');
INSERT INTO `t_pcs_arrival_work` VALUES ('2', '0', '0', '66', '2', '1', null, '1', '2', '600', '0', null, null, '0', '2015-01-20 11:23:42', '0', null, '0', '1');
INSERT INTO `t_pcs_arrival_work` VALUES ('3', '0', '0', '81', '4', '4', null, '1', '1', '1200', '0', null, null, '0', '2015-01-20 18:42:35', '0', null, '0', '9');
INSERT INTO `t_pcs_arrival_work` VALUES ('4', '0', '0', '67', '5', '4', null, '1', '2', '2100', '0', null, null, '0', '2015-01-20 18:42:44', '0', null, '0', '9');
INSERT INTO `t_pcs_arrival_work` VALUES ('5', '0', '0', '64', '6', '4', null, '2', '3', '1000', '0', null, null, '0', '2015-01-20 18:42:54', '0', null, '0', '9');
INSERT INTO `t_pcs_arrival_work` VALUES ('6', '0', '0', '54', '7', '4', null, '2', '4', '1300', '0', null, null, '0', '2015-01-20 18:43:04', '0', null, '0', '9');
INSERT INTO `t_pcs_arrival_work` VALUES ('7', '0', '0', '64', '8', '4', null, '1', '2', '800', '0', null, null, '0', '2015-01-20 18:44:40', '0', null, '0', '8');
INSERT INTO `t_pcs_arrival_work` VALUES ('8', '0', '0', '82', '8', '4', null, '1', '2', '1200', '0', null, null, '0', '2015-01-20 18:46:11', '0', null, '0', '8');
INSERT INTO `t_pcs_arrival_work` VALUES ('9', '0', '0', '70', '9', '4', null, '2', '3', '800', '0', null, null, '0', '2015-01-20 18:46:27', '0', null, '0', '8');
INSERT INTO `t_pcs_arrival_work` VALUES ('10', '0', '0', '84', '10', '4', null, '1', '3', '300', '0', null, null, '0', '2015-01-20 21:15:21', '0', null, '0', '11');
INSERT INTO `t_pcs_arrival_work` VALUES ('11', '0', '0', '67', '11', '4', null, '1', '2', '100', '0', null, null, '0', '2015-01-20 21:15:29', '0', null, '0', '11');
INSERT INTO `t_pcs_arrival_work` VALUES ('12', '0', '0', '69', '12', '3', null, '1', '2', '400', '0', null, null, '0', '2015-01-20 21:17:40', '0', null, '0', '12');
INSERT INTO `t_pcs_arrival_work` VALUES ('13', '0', '0', '73', '13', '3', null, '1', '1', '400', '0', null, null, '0', '2015-01-20 21:17:47', '0', null, '0', '12');
INSERT INTO `t_pcs_arrival_work` VALUES ('14', '0', '0', '67', '14', '3', null, '2', '2', '500', '0', null, null, '0', '2015-01-20 21:17:54', '0', null, '0', '12');

-- ----------------------------
-- Table structure for t_pcs_batch_vehicle_info
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_batch_vehicle_info`;
CREATE TABLE `t_pcs_batch_vehicle_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `trainId` int(11) DEFAULT NULL,
  `plateId` varchar(20) DEFAULT NULL,
  `deliverNum` double DEFAULT NULL,
  `plateNo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_batch_vehicle_info
-- ----------------------------
INSERT INTO `t_pcs_batch_vehicle_info` VALUES ('8', '7', '6', '34', null);
INSERT INTO `t_pcs_batch_vehicle_info` VALUES ('9', '7', '7', '23', null);

-- ----------------------------
-- Table structure for t_pcs_berth
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_berth`;
CREATE TABLE `t_pcs_berth` (
  `id` int(7) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(15) DEFAULT NULL,
  `limitLength` varchar(15) DEFAULT NULL,
  `limitDrought` varchar(15) DEFAULT NULL,
  `limitDisplacement` varchar(15) DEFAULT NULL,
  `description` text,
  `editUserId` int(20) unsigned DEFAULT NULL,
  `editTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_berth
-- ----------------------------
INSERT INTO `t_pcs_berth` VALUES ('1', '1#泊位', '245', '10.5', '48000', '大型船舶需减载后靠离泊，严格控制3000吨级以下海轮靠泊。靠离泊风速≤12.3m/s（6级风），靠泊法向速度0.10m/s，靠泊角度≤5度，作业波高，顺浪1.2m、横浪1.0m。如遇实际风力达5级及5级以上东南风、东北风时，停止分流小船靠离泊作业。限制载重500吨以下，总长40m以下的船舶离泊。', null, '2015-01-13 01:19:06');
INSERT INTO `t_pcs_berth` VALUES ('2', '2#泊位', '104', '5.5', '4500', '特殊泊位，1000吨级以上船舶必须在落潮并在潮流比较平顺时进行靠离泊。靠离泊风速≤12.3m/s（6级风），靠泊法向速度≤0.20m/s，靠泊角度≤15度。如遇实际风力达5级及5级以上东南风、东北风时，停止分流小船靠离泊作业。限制载重300吨以下，总长36m以下、104m以上的船舶离泊。', null, '2015-01-13 01:19:09');
INSERT INTO `t_pcs_berth` VALUES ('3', '3#泊位', '104', '5.0', '4500', '特殊泊位，1000吨级以上船舶必须在落潮并在潮流比较平顺时进行靠离泊。靠离泊风速≤12.3m/s（6级风），靠泊法向速度≤0.20m/s，靠泊角度≤15度。', null, '2015-01-13 01:19:14');
INSERT INTO `t_pcs_berth` VALUES ('4', '4#泊位', '86', '4.5', '1500', '特殊泊位，1000吨级以上船舶必须在落潮并在潮流比较平顺时进行靠离泊。靠离泊风速≤12.3m/s（6级风），靠泊法向速度≤0.20m/s，靠泊角度≤15度。如遇实际风力达5级及5级以上东南风、东北风时，停止分流小船靠离泊作业。', null, '2015-01-13 01:19:17');
INSERT INTO `t_pcs_berth` VALUES ('5', '5#泊位', '86', '3.5', '1500', '特殊泊位，1000吨级以上船舶必须在落潮并在潮流比较平顺时进行靠离泊。靠离泊风速≤12.3m/s（6级风），靠泊法向速度≤0.20m/s，靠泊角度≤15度。如遇实际风力达5级及5级以上西南风、西北风时，停止分流小船靠离泊作业。', null, '2015-01-13 01:19:20');
INSERT INTO `t_pcs_berth` VALUES ('6', '6#泊位', '240', '11', '68000', '在落潮并在潮流比较平顺时进行靠离泊。靠离泊风速≤12.3m/s（6级风），靠泊法向速度≤0.20m/s，靠泊角度≤15度，作业波高，顺浪1.2m、横浪1.0m。限制载重2000吨以下，总长80m以下的船舶靠泊。', null, '2015-01-13 01:19:23');
INSERT INTO `t_pcs_berth` VALUES ('7', '7#泊位', '115', '7.1', '7500', '特殊泊位，必须在落潮并在潮流比较平顺时进行靠离泊。靠离泊风速≤12.3m/s（6级风），靠泊法向速度≤0.18m/s。如遇实际风力达5级及5级以上西北风时，停止分流小船靠离泊作业。限制载重500吨以下，总长40m以下、115m以上的船舶靠泊。', null, '2015-01-13 01:19:27');

-- ----------------------------
-- Table structure for t_pcs_berth_assess
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_berth_assess`;
CREATE TABLE `t_pcs_berth_assess` (
  `id` int(20) DEFAULT NULL,
  `arrivalId` int(20) DEFAULT NULL,
  `weather` int(7) DEFAULT NULL,
  `windDirection` int(7) DEFAULT NULL,
  `windPower` int(7) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `createUserId` int(20) DEFAULT NULL,
  `createTime` bigint(20) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `reviewUserId` int(20) DEFAULT NULL,
  `reviewTime` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_berth_assess
-- ----------------------------
INSERT INTO `t_pcs_berth_assess` VALUES ('1', '1', null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_berth_assess` VALUES ('2', '3', '4', '3', '3', '风力过大', null, null, '同意靠泊', null, null);
INSERT INTO `t_pcs_berth_assess` VALUES ('3', '3', null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_berth_assess` VALUES ('4', '9', null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_berth_assess` VALUES ('5', '8', null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_berth_assess` VALUES ('6', '5', '3', '5', '5', '2sdfsd', null, null, '是第三方', null, null);
INSERT INTO `t_pcs_berth_assess` VALUES ('7', '11', null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_berth_assess` VALUES ('8', '12', null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for t_pcs_berth_program
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_berth_program`;
CREATE TABLE `t_pcs_berth_program` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `berthId` int(7) DEFAULT NULL,
  `safeInfo` text,
  `comment` text,
  `createUserId` int(20) DEFAULT NULL,
  `createTime` bigint(20) DEFAULT NULL,
  `reviewUserId` int(20) DEFAULT NULL,
  `reviewTime` bigint(20) DEFAULT NULL,
  `arrivalId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_berth_program
-- ----------------------------
INSERT INTO `t_pcs_berth_program` VALUES ('1', '1', '1.靠泊时使用2艘拖轮，带缆人员6名，现场风力小于6级\n2.靠泊时调度及码头负责人均要到现场\n3.作业现场加强值班，关注潮位，提醒船方及时调整缆绳松紧及软管位置\n4.船岸双方关注风力变化\n5.船岸双方关注船岸安全通道的通畅，关注船梯位置变化\n						', null, null, null, null, null, '1');
INSERT INTO `t_pcs_berth_program` VALUES ('2', '3', '水电费水电费地方 ', '的发生点访问法违法', null, null, null, null, '3');
INSERT INTO `t_pcs_berth_program` VALUES ('3', null, null, null, null, null, null, null, '3');
INSERT INTO `t_pcs_berth_program` VALUES ('4', '1', '注意台风', '', null, null, null, null, '7');
INSERT INTO `t_pcs_berth_program` VALUES ('5', '1', '注意台风', '', null, null, null, null, '7');
INSERT INTO `t_pcs_berth_program` VALUES ('6', '4', '1.靠泊时使用2艘拖轮，带缆人员6名，现场风力小于6级\n2.靠泊时调度及码头负责人均要到现场\n3.作业现场加强值班，关注潮位，提醒船方及时调整缆绳松紧及软管位置\n4.船岸双方关注风力变化\n5.船岸双方关注船岸安全通道的通畅，关注船梯位置变化\n						', '同意', null, null, null, null, '9');
INSERT INTO `t_pcs_berth_program` VALUES ('7', '5', '1.靠泊时使用2艘拖轮，带缆人员6名，现场风力小于6级\n2.靠泊时调度及码头负责人均要到现场\n3.作业现场加强值班，关注潮位，提醒船方及时调整缆绳松紧及软管位置\n4.船岸双方关注风力变化\n5.船岸双方关注船岸安全通道的通畅，关注船梯位 \n						', '同意靠泊', null, null, null, null, '8');
INSERT INTO `t_pcs_berth_program` VALUES ('8', '4', '费大幅', '范德萨发的', null, null, null, null, '3');
INSERT INTO `t_pcs_berth_program` VALUES ('9', '3', '234', '23423', null, null, null, null, '5');
INSERT INTO `t_pcs_berth_program` VALUES ('10', null, null, null, null, null, null, null, '11');
INSERT INTO `t_pcs_berth_program` VALUES ('11', '4', '1.靠泊时使用2艘拖轮，带缆人员6名，现场风力小于6级\n2.靠泊时调度及码头负责人均要到现场\n3.作业现场加强值班，关注潮位，提醒船方及时调整缆绳松紧及软管位置\n4.船岸双方关注风力变化\n5.船岸双方关注船岸安全通道的通畅，关注船梯位置变化\n						', 'ty', null, null, null, null, '12');
INSERT INTO `t_pcs_berth_program` VALUES ('12', '3', '0吨级以上船舶必须在落潮并在潮流比较平顺时进行靠离泊。靠离', '0吨级以上船舶必须在落潮并在潮流比较平顺时进行靠离泊。靠离', null, null, null, null, '13');
INSERT INTO `t_pcs_berth_program` VALUES ('13', '3', '0吨级以上船舶必须在落潮并在潮流比较平顺时进行靠离泊。靠离', '0吨级以上船舶必须在落潮并在潮流比较平顺时进行靠离泊。靠离', null, null, null, null, '13');
INSERT INTO `t_pcs_berth_program` VALUES ('14', '3', '0吨级以上船舶必须在落潮并在潮流比较平顺时进行靠离泊。靠离', '0吨级以上船舶必须在落潮并在潮流比较平顺时进行靠离泊。靠离', null, null, null, null, '13');
INSERT INTO `t_pcs_berth_program` VALUES ('15', '3', '0吨级以上船舶必须在落潮并在潮流比较平顺时进行靠离泊。靠离', '0吨级以上船舶必须在落潮并在潮流比较平顺时进行靠离泊。靠离', null, null, null, null, '13');

-- ----------------------------
-- Table structure for t_pcs_bill
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_bill`;
CREATE TABLE `t_pcs_bill` (
  `id` int(11) NOT NULL,
  `clientId` int(11) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `contractId` int(11) NOT NULL,
  `createTime` datetime DEFAULT NULL,
  `createUserId` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `dueTime` datetime DEFAULT NULL,
  `editTime` datetime DEFAULT NULL,
  `editUserId` int(11) NOT NULL,
  `payTime` datetime DEFAULT NULL,
  `status` int(11) NOT NULL,
  `totalPaid` varchar(255) DEFAULT NULL,
  `totalPayable` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_bill
-- ----------------------------

-- ----------------------------
-- Table structure for t_pcs_cache
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_cache`;
CREATE TABLE `t_pcs_cache` (
  `keyword` varchar(30) DEFAULT NULL,
  `hitRate` int(20) unsigned DEFAULT NULL,
  `userId` int(20) unsigned DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_cache
-- ----------------------------
INSERT INTO `t_pcs_cache` VALUES ('尼玛', '2', '0');
INSERT INTO `t_pcs_cache` VALUES ('阿尔玛', '3', '0');
INSERT INTO `t_pcs_cache` VALUES ('阿尔玛1', '1', '0');

-- ----------------------------
-- Table structure for t_pcs_cargo
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_cargo`;
CREATE TABLE `t_pcs_cargo` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(32) DEFAULT NULL,
  `type` int(10) unsigned DEFAULT NULL,
  `productId` int(10) unsigned DEFAULT NULL,
  `clientId` int(10) unsigned DEFAULT NULL,
  `contractId` int(10) unsigned DEFAULT NULL,
  `taxType` int(10) unsigned DEFAULT NULL,
  `status` int(10) unsigned DEFAULT NULL,
  `requirement` varchar(255) DEFAULT NULL,
  `originalArea` varchar(64) DEFAULT NULL,
  `cargoAgentId` int(10) unsigned DEFAULT NULL,
  `inspectAgentId` int(10) unsigned DEFAULT NULL,
  `certifyAgentId` int(10) unsigned DEFAULT NULL,
  `officeGrade` int(10) unsigned DEFAULT NULL,
  `arrivalId` int(10) unsigned DEFAULT NULL,
  `importId` int(10) unsigned DEFAULT NULL,
  `lossRate` varchar(16) DEFAULT NULL,
  `goodsInspect` varchar(16) DEFAULT '0',
  `goodsTank` varchar(16) DEFAULT '0',
  `goodsShip` varchar(16) DEFAULT NULL,
  `goodsTotal` varchar(16) DEFAULT NULL,
  `goodsCurrent` varchar(16) DEFAULT NULL,
  `passStatus` int(10) unsigned DEFAULT NULL,
  `goodsInPass` varchar(16) DEFAULT '0',
  `goodsOutPass` varchar(16) DEFAULT '0',
  `description` text,
  `goodsPlan` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_cargo
-- ----------------------------
INSERT INTO `t_pcs_cargo` VALUES ('1', '15YZB003HP01', '1', '1', '1', '67', '0', '0', '', 'null', '0', '0', '0', '0', '1', '0', '2', '498.0', '498', '0', '498', '0', '0', '498.0', '498.0', null, '400');
INSERT INTO `t_pcs_cargo` VALUES ('2', '15YZA004HP01', '1', '1', '2', '66', '0', '0', '', 'null', '0', '0', '0', '0', '1', '0', '38', '500.0', '500', '0', '500', '0', '0', '500.0', '500.0', null, '600');
INSERT INTO `t_pcs_cargo` VALUES ('3', null, '3', '0', '3', '0', '0', '0', 'null', 'null', '0', '0', '0', '0', '3', '0', null, '0', '0', '0', '0', '0', '0', '0', '0', null, '234');
INSERT INTO `t_pcs_cargo` VALUES ('4', '15YZA003HP01', '1', '1', '1', '81', '0', '0', '模拟1 ', 'null', '0', '0', '0', '0', '9', '0', null, '0', '0', '0', '0', '0', '0', '0', '0', null, '1200');
INSERT INTO `t_pcs_cargo` VALUES ('5', '15YZB003HP02', '1', '1', '2', '67', '0', '0', '模拟1', 'null', '0', '0', '0', '0', '9', '0', null, '0', '0', '0', '0', '0', '0', '0', '0', null, '2100');
INSERT INTO `t_pcs_cargo` VALUES ('6', '15YZB002HP01', '1', '2', '3', '64', '0', '0', '模拟1', 'null', '0', '0', '0', '0', '9', '0', null, '0', '0', '0', '0', '0', '0', '0', '0', null, '1000');
INSERT INTO `t_pcs_cargo` VALUES ('7', '14YZA001HP01', '1', '2', '4', '54', '0', '0', '模拟1', 'null', '0', '0', '0', '0', '9', '0', null, '0', '0', '0', '0', '0', '0', '0', '0', null, '1300');
INSERT INTO `t_pcs_cargo` VALUES ('8', '15YZB011HP01', '2', '1', '2', '82', '0', '0', '', 'null', '0', '0', '0', '0', '8', '0', '4', '0', '1185', '0', '1185', '0', '0', '0', '0', null, '1200');
INSERT INTO `t_pcs_cargo` VALUES ('9', '15YZB005HP01', '2', '2', '3', '70', '0', '0', '', 'null', '0', '0', '0', '0', '8', '0', '9', '0', '792', '0', '792', '0', '0', '0', '0', null, '800');
INSERT INTO `t_pcs_cargo` VALUES ('10', '15YZA011HP01', '1', '1', '3', '84', '0', '0', '', 'null', '0', '0', '0', '0', '11', '0', null, '0', '0', '0', '0', '0', '0', '0', '0', null, '300');
INSERT INTO `t_pcs_cargo` VALUES ('11', '15YZB003HP03', '1', '1', '2', '67', '0', '0', '', 'null', '0', '0', '0', '0', '11', '0', null, '0', '0', '0', '0', '0', '0', '0', '0', null, '100');
INSERT INTO `t_pcs_cargo` VALUES ('12', '15YZB004HP01', '1', '1', '2', '69', '0', '0', '', 'null', '0', '0', '0', '0', '12', '0', null, '0', '0', '0', '0', '0', '0', '0', '0', null, '400');
INSERT INTO `t_pcs_cargo` VALUES ('13', '15YZA007HP01', '1', '1', '1', '73', '0', '0', '', 'null', '0', '0', '0', '0', '12', '0', null, '0', '0', '0', '0', '0', '0', '0', '0', null, '400');
INSERT INTO `t_pcs_cargo` VALUES ('14', '15YZB003HP04', '3', '2', '2', '67', '0', '0', '', 'null', '0', '0', '0', '0', '12', '0', null, '0', '0', '0', '0', '0', '0', '0', '0', null, '500');

-- ----------------------------
-- Table structure for t_pcs_cargo_agent
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_cargo_agent`;
CREATE TABLE `t_pcs_cargo_agent` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(15) DEFAULT NULL,
  `name` varchar(60) DEFAULT NULL,
  `type` int(7) unsigned DEFAULT NULL,
  `contactName` varchar(60) DEFAULT NULL,
  `contactEmail` varchar(30) DEFAULT NULL,
  `contactPhone` varchar(30) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` int(7) unsigned DEFAULT NULL,
  `editUserId` int(20) unsigned DEFAULT NULL,
  `editTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `createTime` datetime DEFAULT NULL,
  `createUserId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_cargo_agent
-- ----------------------------
INSERT INTO `t_pcs_cargo_agent` VALUES ('4', '中化', '中国化工外贸集团', null, '刘军', null, null, null, null, null, '2014-11-30 21:22:56', null, null);
INSERT INTO `t_pcs_cargo_agent` VALUES ('5', '中油', '中石油进出口公司', null, '张辉', null, null, null, null, null, '2014-11-30 21:23:33', null, null);
INSERT INTO `t_pcs_cargo_agent` VALUES ('6', '武钢', '武汉钢铁集团', null, '李刚', null, null, null, null, null, '2014-11-30 21:24:00', null, null);

-- ----------------------------
-- Table structure for t_pcs_cargo_status
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_cargo_status`;
CREATE TABLE `t_pcs_cargo_status` (
  `key` int(7) unsigned NOT NULL,
  `value` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_cargo_status
-- ----------------------------
INSERT INTO `t_pcs_cargo_status` VALUES ('0', '计划到港');
INSERT INTO `t_pcs_cargo_status` VALUES ('1', '正在接卸');
INSERT INTO `t_pcs_cargo_status` VALUES ('2', '已入库');

-- ----------------------------
-- Table structure for t_pcs_certify_agent
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_certify_agent`;
CREATE TABLE `t_pcs_certify_agent` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(15) DEFAULT NULL,
  `name` varchar(60) DEFAULT NULL,
  `contactName` varchar(60) DEFAULT NULL,
  `contactEmail` varchar(30) DEFAULT NULL,
  `contactPhone` varchar(30) DEFAULT NULL,
  `editTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `editUserId` int(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_certify_agent
-- ----------------------------
INSERT INTO `t_pcs_certify_agent` VALUES ('2', '真北', '真北化工质检局', '2223213', '', '', '2015-01-09 14:44:43', null);
INSERT INTO `t_pcs_certify_agent` VALUES ('3', '嘉定', '嘉定化工质检局', '', '', '', '2015-01-09 14:44:43', null);
INSERT INTO `t_pcs_certify_agent` VALUES ('7', '杨浦', '杨浦化工质检局', '2223213', '', '', '2015-01-09 14:44:43', null);
INSERT INTO `t_pcs_certify_agent` VALUES ('8', '浦东', '浦东化工质检局', '2223213', '', '', '2015-01-09 14:44:43', null);

-- ----------------------------
-- Table structure for t_pcs_charge
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_charge`;
CREATE TABLE `t_pcs_charge` (
  `id` int(11) NOT NULL,
  `billId` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  `quantity` varchar(255) DEFAULT NULL,
  `startTime` datetime DEFAULT NULL,
  `totalPrice` varchar(255) DEFAULT NULL,
  `type` int(11) NOT NULL,
  `unitPrice` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_charge
-- ----------------------------

-- ----------------------------
-- Table structure for t_pcs_client
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_client`;
CREATE TABLE `t_pcs_client` (
  `id` int(15) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(15) DEFAULT NULL,
  `name` varchar(60) DEFAULT NULL,
  `type` int(7) unsigned DEFAULT NULL,
  `clientGroupId` int(15) DEFAULT NULL,
  `guestId` int(30) unsigned DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `fax` varchar(30) DEFAULT NULL,
  `phone` varchar(30) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `postcode` varchar(30) DEFAULT NULL,
  `contactName` varchar(30) DEFAULT NULL,
  `contactSex` varchar(7) DEFAULT NULL,
  `contactEmail` varchar(30) DEFAULT NULL,
  `contactPhone` varchar(30) DEFAULT NULL,
  `bankAccount` varchar(30) DEFAULT NULL,
  `bankName` varchar(60) DEFAULT NULL,
  `creditGrade` int(7) unsigned DEFAULT NULL,
  `paymentGrade` int(7) unsigned DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` int(7) unsigned DEFAULT NULL,
  `ladingSample` varchar(255) DEFAULT NULL,
  `editTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `password` varchar(30) DEFAULT NULL,
  `username` varchar(30) DEFAULT NULL,
  `editUserId` int(20) unsigned DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_client
-- ----------------------------
INSERT INTO `t_pcs_client` VALUES ('1', 'ss', '无锡石化', null, '1', '1', 'sales@wxsh.com', '22', '1', '中关村', '222', '111', null, null, '13331002222', 'sssss1111', '22', '1', '1', '', null, null, '2015-01-08 21:59:03', null, null, null, null);
INSERT INTO `t_pcs_client` VALUES ('2', 'aa', '中石油苏州公司', null, '2', '1', 'suzhou@zsy.com', '2', '2', '2', '2', '2', null, null, '2', 'qqqqqq', '2', '1', '1', '111', null, null, '2015-01-08 21:58:57', null, null, null, null);
INSERT INTO `t_pcs_client` VALUES ('3', 'df', '上海家化', null, '3', '1', 'business@shjh.com', null, null, null, null, null, null, null, null, 'ewerwer', null, '1', '1', null, null, null, '2015-01-08 21:59:12', null, null, null, null);
INSERT INTO `t_pcs_client` VALUES ('4', 'as', '南京日化', null, '1', '1', 'manager@njrh.com', null, null, null, null, null, null, null, null, 'sdfsa', null, '1', '1', null, null, null, '2015-01-08 21:59:16', null, null, null, null);
INSERT INTO `t_pcs_client` VALUES ('6', 'nj', '南京中石化', null, '1', '1', '1', '2', null, '2', null, null, null, null, null, '123213', null, '123', '123', null, null, null, '2015-01-08 21:59:07', null, null, null, null);

-- ----------------------------
-- Table structure for t_pcs_client_grade
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_client_grade`;
CREATE TABLE `t_pcs_client_grade` (
  `key` int(7) unsigned NOT NULL,
  `value` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_client_grade
-- ----------------------------
INSERT INTO `t_pcs_client_grade` VALUES ('1', '较差');
INSERT INTO `t_pcs_client_grade` VALUES ('2', '一般');
INSERT INTO `t_pcs_client_grade` VALUES ('3', '良好');

-- ----------------------------
-- Table structure for t_pcs_client_group
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_client_group`;
CREATE TABLE `t_pcs_client_group` (
  `id` int(15) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(60) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `editUserId` int(20) unsigned DEFAULT NULL,
  `editTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_client_group
-- ----------------------------
INSERT INTO `t_pcs_client_group` VALUES ('1', '中石化集团', '中国石油化工集团公司', null, '2015-01-16 16:39:39');
INSERT INTO `t_pcs_client_group` VALUES ('2', '中石油总公司', null, null, '2015-01-09 16:39:44');
INSERT INTO `t_pcs_client_group` VALUES ('3', '中海油', null, null, '2015-01-09 16:39:47');

-- ----------------------------
-- Table structure for t_pcs_client_qualification
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_client_qualification`;
CREATE TABLE `t_pcs_client_qualification` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `clientId` int(15) unsigned DEFAULT NULL,
  `type` int(7) unsigned DEFAULT NULL,
  `fileUrl` varchar(255) DEFAULT NULL,
  `editUserId` int(20) unsigned DEFAULT NULL,
  `editTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `createTime` datetime DEFAULT NULL,
  `createUserId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_client_qualification
-- ----------------------------
INSERT INTO `t_pcs_client_qualification` VALUES ('1', '1', '1', null, null, '2014-11-30 21:26:49', null, null);
INSERT INTO `t_pcs_client_qualification` VALUES ('2', '1', '2', null, null, '2014-11-30 21:26:59', null, null);
INSERT INTO `t_pcs_client_qualification` VALUES ('3', '2', '1', null, null, '2014-11-30 21:27:09', null, null);
INSERT INTO `t_pcs_client_qualification` VALUES ('4', '3', '1', null, null, '2014-11-30 21:27:16', null, null);
INSERT INTO `t_pcs_client_qualification` VALUES ('5', '4', '2', null, null, '2014-11-30 21:27:25', null, null);

-- ----------------------------
-- Table structure for t_pcs_contract
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_contract`;
CREATE TABLE `t_pcs_contract` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(16) DEFAULT NULL,
  `title` varchar(64) DEFAULT NULL,
  `type` int(10) unsigned DEFAULT NULL,
  `clientId` int(10) unsigned DEFAULT NULL,
  `productId` int(10) unsigned DEFAULT NULL,
  `quantity` varchar(255) DEFAULT NULL,
  `lossRate` varchar(16) DEFAULT NULL,
  `totalPrice` varchar(16) DEFAULT NULL,
  `startDate` timestamp NULL DEFAULT NULL,
  `endDate` timestamp NULL DEFAULT NULL,
  `description` text,
  `signDate` timestamp NULL DEFAULT NULL,
  `supplementary` text,
  `fileUrl` varchar(255) DEFAULT NULL,
  `status` int(10) unsigned DEFAULT NULL,
  `intentionId` int(10) unsigned DEFAULT NULL,
  `sourceContractId` int(10) unsigned DEFAULT '0',
  `createUserId` int(20) DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT NULL,
  `editUserId` int(20) DEFAULT NULL,
  `editTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `tradeType` int(11) DEFAULT '0',
  `otherPrice` varchar(255) DEFAULT NULL,
  `overtimePrice` varchar(255) DEFAULT NULL,
  `passPrice` varchar(255) DEFAULT NULL,
  `period` varchar(255) DEFAULT NULL,
  `portSecurityPrice` varchar(255) DEFAULT NULL,
  `portServicePrice` varchar(255) DEFAULT NULL,
  `storagePrice` varchar(255) DEFAULT NULL,
  `taxType` int(11) DEFAULT NULL,
  `unitPrice` varchar(255) DEFAULT NULL,
  `workId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_contract
-- ----------------------------
INSERT INTO `t_pcs_contract` VALUES ('52', '15Y43223', '南化包罐合同001', '1', '4', '2', '1000', null, '10000', null, null, '啊啊啊', '2014-12-20 11:55:51', null, null, '5', '17', '0', '1', '2014-12-10 17:11:58', '0', '2015-01-20 21:25:40', '0', null, null, null, null, null, null, null, '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('53', '14-3221', '南化包罐合同001', '1', '4', '2', '1000', null, '12000', null, null, ' 啊啊啊', '2014-12-20 11:55:51', null, null, '4', '17', '52', '1', '2014-12-10 17:12:21', '0', '2015-01-20 21:25:45', '0', null, null, null, null, null, null, null, '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('54', '14YZA001', '南化包罐合同001', '1', '4', '2', '1000', null, '12000', null, null, ' 点点滴滴', '2014-12-20 11:55:51', null, null, '2', '17', '52', '1', '2014-12-15 09:33:45', '0', '2015-01-06 10:14:56', '0', null, null, null, null, null, null, null, '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('55', '14YZA002', '2', '1', '3', '2', '1123', null, '111111123', null, null, '  32', '2014-12-03 19:50:36', null, null, '4', '16', '0', '1', '2014-12-26 19:25:16', '0', '2015-01-04 13:48:59', '0', null, null, null, null, null, null, null, '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('56', '14YZA003', '2', '1', '3', '2', '1123', '32', '49412', null, null, '  32', '2014-12-10 10:50:27', null, null, '4', '16', '0', '1', '2014-12-31 19:24:37', '0', '2015-01-04 14:40:37', '3', '', '11', '11', '112', '11', '11', '11', '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('57', '14YZA004', '', '1', '2', '2', '100', '10', '2001', null, null, '   方法', '2014-12-10 09:40:29', null, null, '2', '12', '0', '1', '2014-12-31 19:42:12', '0', '2015-01-09 17:20:22', '3', '1', '10', '1', '10', '1', '10', '8', '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('58', '15-2321', null, '1', '2', '2', '4341', '1', '499216', null, null, '   方法', '2014-12-31 20:21:31', null, null, '4', '0', '0', '0', '2014-12-31 19:44:38', '0', '2015-01-20 21:25:49', '3', '1', '1', '1', '1', '1', '1', '112', '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('59', '15YZA001', '合同测试1', '1', '2', '4', '22', '1', '242', null, null, '没有备注', '2015-01-09 09:30:51', null, null, '2', '25', '0', '1', '2015-01-04 15:03:52', '0', '2015-01-06 10:08:26', '1', '1', '1', '1', '23', '1', '1', '11', '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('60', '15-543', '苏州石油合同', '1', '3', '1', '4000', '10', '80000', null, null, '包量合同', '2015-01-09 14:45:15', null, null, '4', '26', '0', '0', '2015-01-06 10:11:16', '0', '2015-01-20 21:25:52', '1', '10', '10', '10', '60', '10', '101', '20', '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('61', '15-3221', '123123', '1', '3', '1', '4000', '10', '80000', null, null, '包量合同', '2015-01-09 10:20:57', null, null, '4', '26', '0', '0', '2015-01-06 10:14:47', '0', '2015-01-20 21:25:56', '1', '10', '10', '10', '60', '10', '101', '20', '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('62', '15YZA002', '苏州包量合同', '1', '3', '1', '4000', '10', '80000', null, null, '包量合同', '2015-01-09 10:35:47', null, null, '4', '26', '0', '1', '2015-01-06 10:18:06', '0', '2015-01-06 10:20:33', '1', '10', '10', '10', '60', '10', '101', '20', '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('63', '15YZB001', '苏州包量合同', '2', '3', '1', '4000', '10', '80000', null, null, '包量合同123', '2015-01-08 10:45:59', null, null, '4', '26', '0', '1', '2015-01-06 10:20:50', '0', '2015-01-06 10:24:22', '1', '10', '10', '10', '60', '10', '101', '20', '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('64', '15YZB002', '苏州石油合同', '2', '3', '2', '4000', '10', '80000', null, null, '包量合同', '2015-01-09 07:35:48', null, null, '2', '26', '0', '0', '2015-01-06 10:24:59', '0', '2015-01-06 10:26:31', '1', '10', '10', '10', '60', '10', '101', '20', '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('65', '15YZA003', '111', '1', '2', '4', '22', '1', '242', null, null, '没有备注', '2015-01-09 15:55:29', null, null, '5', '25', '0', '0', '2015-01-06 11:24:15', '0', '2015-01-19 19:35:54', '1', '1', '1', '1', '23', '1', '1', '11', '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('66', '15YZA004', '111', '1', '3', '2', '1123', '1', '12353', null, null, '  32', '2015-01-08 10:50:28', null, null, '2', '16', '0', '0', '2015-01-06 13:22:17', '0', '2015-01-06 13:22:26', '1', '1', '1', '1', '1', '1', '1', '11', '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('67', '15YZB003', '12213123', '1', '3', '1', '4000', '10', '80000', null, null, '包量合同', '2015-01-15 13:25:18', null, null, '2', '26', '0', '1', '2015-01-06 13:24:07', '0', '2015-01-06 13:24:21', '1', '10', '10', '10', '60', '10', '101', '20', '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('68', '15YZA005', '', '1', '3', '2', '1123', '', '111111123', null, null, '  32', '2015-01-07 16:35:34', null, null, '4', '16', '0', '1', '2015-01-06 16:12:42', '0', '2015-01-06 16:12:54', '1', '', '', '', '', '', '', '', '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('69', '15YZB004', '中石油包量合同-15-01', '2', '2', '2', '10000', '0.2', '100000', null, null, '', '2015-01-10 14:10:21', null, null, '1', '35', '0', '1', '2015-01-13 13:49:23', '0', '2015-01-13 15:19:01', '1', '', '12', '', '60', '10', '10', '10', '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('70', '15YZB005', '中石油包量合同-15-02', '2', '2', '2', '10000', '0.2', '100000', null, null, '', '2015-01-20 13:45:29', null, null, '0', '35', '0', '4', '2015-01-14 10:42:33', '0', '2015-01-14 10:42:33', '1', '', '12', '', '60', '10', '10', '10', '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('71', '15YZB006', '中石油包量合同-15-03', '2', '2', '2', '8000', '0.2', '80000', null, null, '', '2015-01-20 11:45:58', null, null, '0', '35', '0', '4', '2015-01-14 10:50:25', '0', '2015-01-14 10:50:25', '1', '', '12', '', '60', '10', '10', '10', '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('72', '15YZA006', '中石化甲醇合同-15-043', '1', '6', '1', '2000', '7', '24012', null, null, '', '2015-01-16 10:50:57', null, null, '0', '37', '0', '4', '2015-01-14 11:02:37', '4', '2015-01-20 21:27:24', '1', '', '12', '12', '20', '12', '12', '12', '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('73', '15YZA007', '中石化甲醇合同-15-053', '1', '6', '1', '4000', '1', '13', null, null, '', '2015-01-15 14:50:34', null, null, '3', '0', '0', '4', '2015-01-14 11:07:43', '4', '2015-01-20 21:28:22', '1', '1', '1', '1', '1', '1', '1', '1', '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('74', '15YZB007', '南京日化包量合同-15-11', '2', '4', '2', '4000', '0.98', '32012', null, null, '', '2015-01-20 11:00:06', null, null, '4', '40', '0', '4', '2015-01-19 10:39:43', '0', '2015-01-19 10:42:10', '1', '', '12', '12', '30', '20', '8', '8', '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('75', '15YZB008', '中石油包量合同-15-11', '2', '2', '2', '12000', '0.96', '120000', null, null, '', '2015-01-20 11:00:49', null, null, '4', '35', '0', '4', '2015-01-19 10:41:28', '0', '2015-01-19 10:44:19', '1', '', '12', '', '60', '10', '10', '10', '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('76', '15YZD001', '中海油临租合同-15-02', '4', '3', '9', '800', '0.92', '16010', null, null, '', '2015-01-30 11:05:28', null, null, '4', '0', '0', '4', '2015-01-19 10:43:56', '0', '2015-01-19 10:44:19', '1', '', '30', '10', '30', '30', '20', '20', '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('77', '15YZB009', '中石油包量合同-15-14', '2', '2', '2', '10000', '0.2', '100000', null, null, '', '2015-01-21 14:50:57', null, null, '4', '35', '0', '4', '2015-01-19 10:46:10', '0', '2015-01-19 10:47:48', '1', '', '12', '', '60', '10', '10', '10', '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('78', '15YZB010', '中石油包量合同-15-16', '2', '2', '2', '10000', '0.2', '100000', null, null, '', '2015-01-22 14:50:43', null, null, '4', '35', '0', '4', '2015-01-19 10:48:57', '0', '2015-01-19 10:59:27', '1', '', '12', '', '60', '10', '10', '10', '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('79', '15YZA008', '中石油汽油合同-15-023', '1', '2', '4', '3200', '12', '38402', null, null, '没有备注', '2015-01-09 15:55:29', null, null, '0', '25', '65', '4', '2015-01-19 19:21:38', '4', '2015-01-20 21:26:53', '1', '1', '12', '2', '32', '1', '12', '12', '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('80', '15YZA003', '111', '1', '2', '4', '22', '1', '242', null, null, '没有备注', '2015-01-09 15:55:29', null, null, '2', '25', '65', '4', '2015-01-19 19:27:14', '4', '2015-01-19 19:27:43', '1', '1', '1', '1', '23', '1', '1', '11', '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('81', '15YZA003', '111', '1', '2', '1', '22', '1', '242', null, null, '没有备注', '2015-01-09 15:55:29', null, null, '2', '0', '65', '4', '2015-01-19 19:35:37', '4', '2015-01-19 19:35:54', '1', '1', '1', '1', '23', '1', '1', '11', '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('82', '15YZB011', '中石油包量合同-15-22', '2', '2', '2', '1000', '98', '10000', null, null, '修改', '2015-01-28 20:15:40', null, null, '2', '44', '0', '4', '2015-01-19 19:57:16', '4', '2015-01-19 19:57:55', '1', '', '12', '0', '30', '20', '16', '10', '0', null, null);
INSERT INTO `t_pcs_contract` VALUES ('84', '15YZA011', '中石油包罐合同-15-001', '1', '2', '2', '2000', '8', '20020', null, null, '', '2015-01-20 09:25:36', null, null, '0', '45', '0', '4', '2015-01-20 21:02:53', '0', '2015-01-20 21:02:53', '1', '', '8', '20', '30', '44', '21', '10', null, null, null);

-- ----------------------------
-- Table structure for t_pcs_contract_type
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_contract_type`;
CREATE TABLE `t_pcs_contract_type` (
  `key` int(7) unsigned NOT NULL,
  `value` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_contract_type
-- ----------------------------
INSERT INTO `t_pcs_contract_type` VALUES ('1', '包罐');
INSERT INTO `t_pcs_contract_type` VALUES ('2', '包量');
INSERT INTO `t_pcs_contract_type` VALUES ('3', '全年');
INSERT INTO `t_pcs_contract_type` VALUES ('4', '临租');
INSERT INTO `t_pcs_contract_type` VALUES ('5', '通过');

-- ----------------------------
-- Table structure for t_pcs_contract_work
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_contract_work`;
CREATE TABLE `t_pcs_contract_work` (
  `contractId` int(10) unsigned NOT NULL,
  `submitUserId` varchar(64) DEFAULT NULL,
  `submitTime` timestamp NULL DEFAULT NULL,
  `submitComment` text,
  `reviewUserId1` varchar(64) DEFAULT NULL,
  `reviewTime1` timestamp NULL DEFAULT NULL,
  `reviewComment1` text,
  `reviewUserId2` varchar(64) DEFAULT NULL,
  `reviewTime2` timestamp NULL DEFAULT NULL,
  `reviewComment2` text,
  `reviewUserId3` varchar(64) DEFAULT NULL,
  `reviewTime3` timestamp NULL DEFAULT NULL,
  `reviewComment3` text,
  `reviewUserId4` varchar(64) DEFAULT NULL,
  `reviewTime4` timestamp NULL DEFAULT NULL,
  `reviewComment4` text,
  PRIMARY KEY (`contractId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_contract_work
-- ----------------------------

-- ----------------------------
-- Table structure for t_pcs_delivery_lading
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_delivery_lading`;
CREATE TABLE `t_pcs_delivery_lading` (
  `ladingId` int(20) DEFAULT NULL,
  `deliveryId` int(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_delivery_lading
-- ----------------------------
INSERT INTO `t_pcs_delivery_lading` VALUES ('1', '1');

-- ----------------------------
-- Table structure for t_pcs_delivery_loading
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_delivery_loading`;
CREATE TABLE `t_pcs_delivery_loading` (
  `loadingId` int(11) NOT NULL,
  `deliveryId` int(11) NOT NULL,
  PRIMARY KEY (`loadingId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_delivery_loading
-- ----------------------------

-- ----------------------------
-- Table structure for t_pcs_delivery_plan
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_delivery_plan`;
CREATE TABLE `t_pcs_delivery_plan` (
  `id` int(11) NOT NULL,
  `clientId` int(11) DEFAULT NULL,
  `amount` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_delivery_plan
-- ----------------------------
INSERT INTO `t_pcs_delivery_plan` VALUES ('3', '1', '343.0');

-- ----------------------------
-- Table structure for t_pcs_delivery_ship
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_delivery_ship`;
CREATE TABLE `t_pcs_delivery_ship` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `transportId` int(20) unsigned DEFAULT NULL,
  `tubeId` int(20) unsigned DEFAULT NULL,
  `tubeStatus` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_delivery_ship
-- ----------------------------
INSERT INTO `t_pcs_delivery_ship` VALUES ('1', '4', '2', '1');
INSERT INTO `t_pcs_delivery_ship` VALUES ('2', '4', '2', '1');
INSERT INTO `t_pcs_delivery_ship` VALUES ('3', '9', '6', '1');
INSERT INTO `t_pcs_delivery_ship` VALUES ('4', '14', '15', '1');

-- ----------------------------
-- Table structure for t_pcs_dispatch
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_dispatch`;
CREATE TABLE `t_pcs_dispatch` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `weather` int(7) DEFAULT NULL,
  `windDirection` int(7) DEFAULT NULL,
  `windPower` int(7) DEFAULT NULL,
  `dispatchUserId` int(20) DEFAULT NULL,
  `deliveryUserId` int(20) DEFAULT NULL,
  `dayWordUserId` int(20) DEFAULT NULL,
  `dockUserId` int(20) DEFAULT NULL,
  `powerUserId` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_dispatch
-- ----------------------------
INSERT INTO `t_pcs_dispatch` VALUES ('1', '23', null, null, '4', '4', '4', '4', '4');
INSERT INTO `t_pcs_dispatch` VALUES ('2', '1', '4', '7', '28', '27', '24', '25', '25');

-- ----------------------------
-- Table structure for t_pcs_duty_record
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_duty_record`;
CREATE TABLE `t_pcs_duty_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `disptachId` int(11) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  `weather` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `createUserId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_duty_record
-- ----------------------------
INSERT INTO `t_pcs_duty_record` VALUES ('3', '1', '10:39', '234', '234', '4');
INSERT INTO `t_pcs_duty_record` VALUES ('8', '1', '10:46', '多云', '内桶', '4');
INSERT INTO `t_pcs_duty_record` VALUES ('18', '1', '15:06', 's', 's', '4');
INSERT INTO `t_pcs_duty_record` VALUES ('19', '1', '15:08', 'sq', 'wef', '4');

-- ----------------------------
-- Table structure for t_pcs_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_goods`;
CREATE TABLE `t_pcs_goods` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(32) DEFAULT NULL,
  `type` int(10) unsigned DEFAULT NULL,
  `cargoId` int(10) unsigned DEFAULT NULL,
  `contractId` int(10) unsigned DEFAULT NULL,
  `goodsGroupId` int(10) unsigned DEFAULT NULL,
  `clientId` int(10) unsigned DEFAULT NULL,
  `ladingClientId` int(10) unsigned DEFAULT NULL,
  `productId` int(10) unsigned DEFAULT NULL,
  `sourceGoodsId` int(10) unsigned DEFAULT NULL,
  `rootGoodsId` int(10) unsigned DEFAULT NULL,
  `tankId` int(10) unsigned DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lossRate` varchar(16) DEFAULT NULL,
  `goodsInspect` varchar(16) DEFAULT NULL,
  `goodsTank` varchar(16) DEFAULT NULL,
  `goodsTotal` varchar(16) DEFAULT NULL,
  `goodsIn` varchar(16) DEFAULT NULL,
  `goodsOut` varchar(16) DEFAULT NULL,
  `goodsCurrent` varchar(16) DEFAULT NULL,
  `goodsInPass` varchar(16) DEFAULT NULL,
  `goodsOutPass` varchar(16) DEFAULT NULL,
  `status` int(10) unsigned DEFAULT '0',
  `description` text,
  `sourceGoodId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_goods
-- ----------------------------
INSERT INTO `t_pcs_goods` VALUES ('1', '15YZB003HP01-1/1', null, '1', '67', null, '1', null, '1', null, null, '1', '2015-01-20 11:29:31', '0', '498', '498', '498', '0', '0', '498', '498', '498', '0', null, null);
INSERT INTO `t_pcs_goods` VALUES ('2', '15YZA004HP01-1/1', null, '2', '66', null, '2', null, '1', null, null, '1', '2015-01-20 11:29:31', '0', '500', '500', '500', '0', '412.0', '88.0', '500', '500', '0', null, null);
INSERT INTO `t_pcs_goods` VALUES ('10', '3333    1', null, '2', '66', '8', '3', null, '1', '2', '2', '1', '2015-01-20 14:56:32', '0', '0', '0', '100', '100', '0', '100', '100', '100', '0', null, null);
INSERT INTO `t_pcs_goods` VALUES ('11', 'ZSY2015-1', null, '2', '66', '1', '3', null, '1', '2', '2', '1', '2015-01-20 16:36:40', '0', '0', '0', '200', '200', '120.0', '80.0', '200', '200.0', '0', null, null);
INSERT INTO `t_pcs_goods` VALUES ('12', 'JC-001-1', null, '2', '66', '2', '3', null, '1', '2', '2', '1', '2015-01-20 17:05:28', '0', '0', '0', '100', '100', '40.0', '60.0', '100', '100', '0', null, null);
INSERT INTO `t_pcs_goods` VALUES ('13', 'JC-002-1', null, '2', '66', '3', '4', null, '1', '11', '2', '1', '2015-01-20 17:07:14', '0', '0', '0', '120', '120', '40.0', '80.0', '120', '80.0', '0', null, null);
INSERT INTO `t_pcs_goods` VALUES ('14', 'JC-002-2', null, '2', '66', '3', '4', null, '1', '12', '2', '1', '2015-01-20 17:07:14', '0', '0', '0', '40', '40', '40.0', '0.0', '40', '40', '0', null, null);
INSERT INTO `t_pcs_goods` VALUES ('15', 'JC-003-1', null, '2', '66', '4', '6', null, '1', '13', '2', '1', '2015-01-20 17:11:29', '0', '0', '0', '40', '40', '0', '40', '40', '40', '0', null, null);
INSERT INTO `t_pcs_goods` VALUES ('16', 'JC-003-2', null, '2', '66', '4', '6', null, '1', '14', '2', '1', '2015-01-20 17:11:29', '0', '0', '0', '40', '40', '0', '40', '40', '40', '0', null, null);
INSERT INTO `t_pcs_goods` VALUES ('17', 'TD-15-001-1', null, '2', '66', '5', '3', null, '1', '2', '2', '1', '2015-01-20 19:40:23', '0', '0', '0', '100', '100', '0', '100', '100', '100', '0', null, null);
INSERT INTO `t_pcs_goods` VALUES ('18', 'ZSY2015-2', null, '2', '66', '1', '3', null, '1', '2', '2', '1', '2015-01-21 10:18:15', '0', '0', '0', '1', '1', '0', '1', '1', '1', '0', null, null);
INSERT INTO `t_pcs_goods` VALUES ('19', 'JC-001-2', null, '2', '66', '2', '3', null, '1', '2', '2', '1', '2015-01-21 10:20:52', '0', '0', '0', '11', '11', '0', '11', '11', '11', '0', null, null);

-- ----------------------------
-- Table structure for t_pcs_goods_group
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_goods_group`;
CREATE TABLE `t_pcs_goods_group` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(16) DEFAULT NULL,
  `clientId` int(10) unsigned DEFAULT NULL,
  `productId` int(10) unsigned DEFAULT NULL,
  `ladingId` int(10) unsigned DEFAULT NULL,
  `goodsInspect` varchar(16) DEFAULT NULL,
  `goodsTank` varchar(16) DEFAULT NULL,
  `goodsTotal` varchar(16) DEFAULT NULL,
  `goodsIn` varchar(16) DEFAULT NULL,
  `goodsOut` varchar(16) DEFAULT NULL,
  `goodsCurrent` varchar(16) DEFAULT NULL,
  `goodsInPass` varchar(16) DEFAULT NULL,
  `goodsOutPass` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_goods_group
-- ----------------------------
INSERT INTO `t_pcs_goods_group` VALUES ('1', 'HTZ01', '0', '0', '1', '0', '0', '-120', '-120', '120', '-120', '-120', '-120');
INSERT INTO `t_pcs_goods_group` VALUES ('2', 'HTZ02', '0', '0', '2', '0', '0', '-40', '-40', '40', '-40', '-40', '-40');
INSERT INTO `t_pcs_goods_group` VALUES ('3', 'HTZ03', '0', '0', '3', '0', '0', '-80', '-80', '80', '-80', '-80', '-80');
INSERT INTO `t_pcs_goods_group` VALUES ('4', 'HTZ04', '0', '0', '4', '0.0', '0.0', '0.0', '0.0', '0', '0.0', '0.0', '0.0');
INSERT INTO `t_pcs_goods_group` VALUES ('5', 'HTZ05', '0', '0', '5', '0.0', '0.0', '0.0', '0.0', '0', '0.0', '0.0', '0.0');

-- ----------------------------
-- Table structure for t_pcs_guest
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_guest`;
CREATE TABLE `t_pcs_guest` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(30) DEFAULT NULL,
  `password` varchar(30) DEFAULT NULL,
  `editTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `editUserId` int(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_guest
-- ----------------------------
INSERT INTO `t_pcs_guest` VALUES ('1', 'guest', 'guest', '2015-01-09 15:02:41', null);

-- ----------------------------
-- Table structure for t_pcs_inspect_agent
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_inspect_agent`;
CREATE TABLE `t_pcs_inspect_agent` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(15) DEFAULT NULL,
  `name` varchar(60) DEFAULT NULL,
  `contactName` varchar(60) DEFAULT NULL,
  `contactEmail` varchar(30) DEFAULT NULL,
  `contactPhone` varchar(30) DEFAULT NULL,
  `editTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `editUserId` int(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_inspect_agent
-- ----------------------------
INSERT INTO `t_pcs_inspect_agent` VALUES ('1', '申检', '上海海运质检中心', null, null, null, '2015-01-09 15:03:47', null);
INSERT INTO `t_pcs_inspect_agent` VALUES ('2', '南化', '南京化工物资监督处', '2', '2', '2', '2015-01-09 15:03:47', null);
INSERT INTO `t_pcs_inspect_agent` VALUES ('3', '苏检', '江苏省质监局', '23423', '233', '234234', '2015-01-09 15:03:47', null);
INSERT INTO `t_pcs_inspect_agent` VALUES ('4', '全球', '全球化学品检定公司', '22112', '121212', '12', '2015-01-09 15:03:47', null);

-- ----------------------------
-- Table structure for t_pcs_intention
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_intention`;
CREATE TABLE `t_pcs_intention` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(16) DEFAULT NULL,
  `title` varchar(64) DEFAULT NULL,
  `type` int(10) unsigned DEFAULT NULL,
  `clientId` int(11) unsigned DEFAULT NULL,
  `productId` int(11) unsigned DEFAULT NULL,
  `quantity` varchar(16) DEFAULT NULL,
  `totalPrice` varchar(16) DEFAULT NULL,
  `description` text,
  `salesUserId` int(20) DEFAULT NULL,
  `status` int(11) unsigned zerofill DEFAULT NULL,
  `createUserId` int(20) DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT NULL,
  `editUserId` int(20) DEFAULT NULL,
  `editTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lossRate` varchar(255) DEFAULT NULL,
  `otherPrice` varchar(255) DEFAULT NULL,
  `overtimePrice` varchar(255) DEFAULT NULL,
  `passPrice` varchar(255) DEFAULT NULL,
  `period` varchar(255) DEFAULT NULL,
  `portSecurityPrice` varchar(255) DEFAULT NULL,
  `portServicePrice` varchar(255) DEFAULT NULL,
  `storagePrice` varchar(255) DEFAULT NULL,
  `clientGroupId` varchar(255) DEFAULT NULL,
  `workId` int(11) unsigned DEFAULT '0',
  `unitPrice` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_intention
-- ----------------------------
INSERT INTO `t_pcs_intention` VALUES ('6', 'YX14YZA006', '2014-11-02中石油包量合同', '2', '2', '2', '1321', '123213', ' ', '0', '00000000004', '0', '2014-12-03 01:08:58', '0', '2014-12-23 14:13:16', null, null, null, null, null, null, null, null, null, '0', null);
INSERT INTO `t_pcs_intention` VALUES ('12', 'YX14YZC001', '中石油全年合同14005-1', '1', '2', '2', '434', '49911', '   方法', '0', '00000000004', '0', null, '0', '2015-01-04 13:36:30', '1', '1', '1', '1', '1', '1', '1', '112', null, '0', null);
INSERT INTO `t_pcs_intention` VALUES ('14', 'YX14YZA011', '5的方法大幅度上升', '1', '1', '1', '123', '111', '到底是谁地方都是', '0', '00000000004', '1', '2014-12-05 03:41:29', '0', '2014-12-28 14:47:13', null, null, null, null, null, null, null, null, null, '0', null);
INSERT INTO `t_pcs_intention` VALUES ('15', 'YX14YZA004', '中石油甲醇包罐合同-15-023', '1', '2', '1', '3200', '38400', '  巍峨', '0', '00000000000', '0', '2014-12-05 01:52:14', '4', '2015-01-20 21:22:35', '7', '123', '12', null, '32', '12', '21', '12', null, '0', null);
INSERT INTO `t_pcs_intention` VALUES ('16', 'YX14YZC002', 'AAAA', '1', '3', '2', '1123', '111111123', '  32', '0', '00000000002', '0', '2014-12-05 05:13:59', '0', '2015-01-05 20:24:28', null, null, null, null, null, null, null, null, null, '0', null);
INSERT INTO `t_pcs_intention` VALUES ('17', 'YX14YZA004', 'CCCC', '1', '4', '4', '13', '333333', '3333', '0', '00000000004', '1', '2014-12-05 05:22:29', '0', '2014-12-19 11:14:40', null, null, null, null, null, null, null, null, null, '0', null);
INSERT INTO `t_pcs_intention` VALUES ('18', 'YX14YZA005', '标题', '1', '2', '1', null, null, '描述', '0', '00000000004', '1', '2014-12-06 15:23:19', '0', '2014-12-19 11:21:14', null, null, null, null, null, null, null, null, null, '0', null);
INSERT INTO `t_pcs_intention` VALUES ('19', 'YX14YZA006', 'wew', '4', '1', '1', '1', '1', '     1232', '0', '00000000004', '0', '2014-12-24 12:47:07', '0', '2015-01-04 15:02:41', null, null, null, null, null, null, null, null, null, '0', null);
INSERT INTO `t_pcs_intention` VALUES ('20', 'YX14YZA007', 'eewrew', '1', '1', '1', '1', '1', 'gggg', '0', '00000000004', '1', '2014-12-24 12:49:40', '0', '2014-12-24 12:49:58', null, null, null, null, null, null, null, null, null, '0', null);
INSERT INTO `t_pcs_intention` VALUES ('21', 'YX14YZA008', '13132', '1', '1', '1', '11', '11', '123213', '0', '00000000004', '1', '2014-12-24 12:51:38', '0', '2014-12-24 12:51:50', null, null, null, null, null, null, null, null, null, '0', null);
INSERT INTO `t_pcs_intention` VALUES ('22', 'YX14YZA009', '1111', '1', '1', '1', '111', '111', '111', '0', '00000000004', '1', '2014-12-26 19:16:30', '0', '2014-12-31 11:10:48', '1111', '11', '11', '11', '11', '11', '22', '11', null, '0', null);
INSERT INTO `t_pcs_intention` VALUES ('23', 'YX14YZA010', '', '1', '1', '1', '', '', '', '0', '00000000004', '1', '2014-12-28 16:56:29', '0', '2014-12-28 16:56:38', null, null, null, null, null, null, null, null, null, '0', null);
INSERT INTO `t_pcs_intention` VALUES ('24', 'YX14YZA011', '3222', '1', '1', '1', '23', '4880492', '1212', '0', '00000000004', '0', '2014-12-28 17:12:42', '0', '2015-01-04 13:39:17', '12', '1111', '12', '21', '100', '12', '3', '212111', null, '0', null);
INSERT INTO `t_pcs_intention` VALUES ('25', 'YX15YZA001', '中石油汽油包罐合同-15-018', '1', '2', '4', '2400', '28812', '没有备注', '0', '00000000002', '1', '2015-01-04 15:03:12', '4', '2015-01-20 21:23:31', '12', '2', '12', '12', '32', '12', '12', '12', null, '0', null);
INSERT INTO `t_pcs_intention` VALUES ('26', 'YX15YZB001', '苏州石油订单意向', '2', '3', '1', '4000', '80000', '包量合同', '0', '00000000002', '0', '2015-01-05 09:49:39', '0', '2015-01-05 11:04:57', '10', '10', '10', '10', '60', '10', '101', '20', null, '0', null);
INSERT INTO `t_pcs_intention` VALUES ('27', 'YX15YZC001', '无锡石化乙二醇全年合同-15-032', '3', '1', '2', '4000', '128000', '', '0', '00000000000', '1', '2015-01-06 16:08:17', '4', '2015-01-20 21:21:31', '4', '542', '24', '', '33', '24', '32', '32', null, '0', null);
INSERT INTO `t_pcs_intention` VALUES ('28', 'YX15YZA002', '上海家化甲醇包罐意向-15-031', '1', '3', '1', '6000', '72032', '', '0', '00000000000', '1', '2015-01-06 16:09:09', '4', '2015-01-20 21:19:36', '6', '', '43', '32', '65', '43', '23', '12', null, '0', null);
INSERT INTO `t_pcs_intention` VALUES ('29', 'YX15YZA003', 'ljhijh', '1', '1', '1', '', '', '', '0', '00000000004', '0', '2015-01-06 16:09:22', '0', '2015-01-06 16:09:57', '', '', '', '', '', '', '', '', null, '0', null);
INSERT INTO `t_pcs_intention` VALUES ('30', 'YX15YZC002', '合同意向1', '3', '4', '2', '1', '212', '123123', '0', '00000000004', '1', '2015-01-06 16:47:04', '0', '2015-01-08 12:00:57', '123', '3', '123', '3', '123', '12', '123', '212', null, '0', null);
INSERT INTO `t_pcs_intention` VALUES ('31', 'YX15YZA004', '', '1', '0', '1', '', '', '', '0', '00000000004', '1', '2015-01-09 14:04:32', '0', '2015-01-09 14:04:37', '', '', '', '', '', '', '', '', null, '0', null);
INSERT INTO `t_pcs_intention` VALUES ('32', 'YX15YZB002', '测试12', '2', '6', '2', '20', '2400', '无', '0', '00000000004', '4', '2015-01-13 10:03:24', '0', '2015-01-13 10:03:40', '', '', '', '0', '30', '', '', '120', null, '0', null);
INSERT INTO `t_pcs_intention` VALUES ('33', 'YX15YZC003', '中石化甲醇全年合同-15-023', '3', '6', '1', '20', '400', '', '0', '00000000002', '4', '2015-01-13 10:04:18', '4', '2015-01-20 21:20:11', '5', '23', '34', '', '20', '432', '24', '20', null, '0', null);
INSERT INTO `t_pcs_intention` VALUES ('34', 'YX15YZB003', '中石化甲醇包罐意向-15-012', '1', '6', '1', '123', '1476', '', '0', '00000000000', '0', '2015-01-13 10:06:45', '4', '2015-01-20 21:18:53', '5', '', '55', '', '30', '23', '1', '12', null, '0', null);
INSERT INTO `t_pcs_intention` VALUES ('35', 'YX15YZB004', '中石油包量合同-15-01', '2', '2', '2', '10000', '100000', '', '0', '00000000002', '1', '2015-01-13 13:47:42', '0', '2015-01-13 13:47:52', '0.2', '', '12', '', '60', '10', '10', '10', null, '0', null);
INSERT INTO `t_pcs_intention` VALUES ('37', 'YX15YZA005', '中石化甲醇包罐合同-15-051', '1', '6', '1', '2000', '64000', '', '0', '00000000000', '4', '2015-01-14 09:49:01', '4', '2015-01-20 21:20:49', '5', '', '12', '', '43', '23', '123', '32', null, '0', null);
INSERT INTO `t_pcs_intention` VALUES ('40', 'YX15YZB005', '中石化包量合同-15-10', '2', '4', '2', '4000', '32012', '修改数量和单价', '0', '00000000004', '4', '2015-01-19 10:33:15', '4', '2015-01-19 10:40:01', '0.98', '', '12', '12', '30', '20', '8', '8', null, '0', null);
INSERT INTO `t_pcs_intention` VALUES ('41', 'YX15YZC004', '上海家化全年合同-15-03', '3', '3', '2', '800', '8010', '', '0', '00000000004', '4', '2015-01-19 10:34:18', '0', '2015-01-19 10:37:31', '0.95', '', '12', '10', '30', '20', '10', '10', null, '0', null);
INSERT INTO `t_pcs_intention` VALUES ('42', 'YX15YZA006', '123', '1', '2', '1', '123', '15252', '3123', '0', '00000000004', '4', '2015-01-19 19:50:35', '0', '2015-01-19 19:51:31', '123', '123', '123', '123', '12', '12', '123', '123', null, '0', null);
INSERT INTO `t_pcs_intention` VALUES ('43', 'YX15YZA007', '标题', '1', '2', '1', '123', '15252', '123', '0', '00000000004', '4', '2015-01-19 19:51:21', '0', '2015-01-19 19:51:29', '123', '123', '123', '123', '123', '123', '123', '123', null, '0', null);
INSERT INTO `t_pcs_intention` VALUES ('44', 'YX15YZB006', '中石油包量合同-15-22', '2', '2', '2', '1000', '10000', '', '0', '00000000002', '4', '2015-01-19 19:54:45', '4', '2015-01-19 19:55:19', '98', '', '12', '0', '30', '20', '16', '10', null, '0', null);
INSERT INTO `t_pcs_intention` VALUES ('45', 'YX15YZA008', '中石油包罐合同-15-001', '1', '2', '2', '2000', '20020', '', '0', '00000000002', '4', '2015-01-20 18:34:52', '4', '2015-01-20 18:35:14', '8', '', '8', '20', '30', '44', '21', '10', null, '0', null);

-- ----------------------------
-- Table structure for t_pcs_intention_work
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_intention_work`;
CREATE TABLE `t_pcs_intention_work` (
  `intentionId` int(10) unsigned NOT NULL,
  `submitUserId` varchar(64) DEFAULT NULL,
  `submitTime` timestamp NULL DEFAULT NULL,
  `submitComment` text,
  `confirmUserId` varchar(64) DEFAULT NULL,
  `confirmTime` timestamp NULL DEFAULT NULL,
  `confirmComment` text,
  PRIMARY KEY (`intentionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_intention_work
-- ----------------------------

-- ----------------------------
-- Table structure for t_pcs_invoice
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_invoice`;
CREATE TABLE `t_pcs_invoice` (
  `id` int(20) NOT NULL,
  `amount` varchar(15) DEFAULT NULL,
  `status` int(7) DEFAULT NULL,
  `deliveryPlanId` int(20) DEFAULT NULL,
  `ladingId` int(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='包含船发跟车发:\r\ntype:0船发，1车发';

-- ----------------------------
-- Records of t_pcs_invoice
-- ----------------------------
INSERT INTO `t_pcs_invoice` VALUES ('3', '332.0', '0', '3', '5');

-- ----------------------------
-- Table structure for t_pcs_invoice_car
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_invoice_car`;
CREATE TABLE `t_pcs_invoice_car` (
  `id` int(20) NOT NULL,
  `type` int(7) DEFAULT NULL,
  `amount` varchar(15) DEFAULT NULL,
  `status` int(7) DEFAULT NULL,
  `measureType` int(7) DEFAULT NULL,
  `createTime` bigint(20) DEFAULT NULL,
  `createUserId` int(20) DEFAULT NULL,
  `motorcadeId` int(20) DEFAULT NULL,
  `invoiceId` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_invoice_car
-- ----------------------------

-- ----------------------------
-- Table structure for t_pcs_invoice_ship
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_invoice_ship`;
CREATE TABLE `t_pcs_invoice_ship` (
  `id` int(20) NOT NULL,
  `amount` varchar(15) DEFAULT NULL,
  `status` int(7) DEFAULT NULL,
  `measureType` int(7) DEFAULT NULL,
  `createTime` bigint(20) DEFAULT NULL,
  `createUserId` int(20) DEFAULT NULL,
  `invoiceId` int(20) DEFAULT NULL,
  `arrivalId` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_invoice_ship
-- ----------------------------

-- ----------------------------
-- Table structure for t_pcs_job_check
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_job_check`;
CREATE TABLE `t_pcs_job_check` (
  `ID` int(20) DEFAULT NULL,
  `job` int(7) DEFAULT NULL,
  `result` text,
  `solve` text,
  `createTime` bigint(20) DEFAULT NULL,
  `createUserId` int(20) DEFAULT NULL,
  `transportId` int(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_job_check
-- ----------------------------
INSERT INTO `t_pcs_job_check` VALUES ('1', '1', '正常', '正常', '1421683200', null, '1');
INSERT INTO `t_pcs_job_check` VALUES ('2', '2', '正常', '正常', '1421683200', null, '1');
INSERT INTO `t_pcs_job_check` VALUES ('3', '3', '正常', '正常', '1421683200', null, '1');
INSERT INTO `t_pcs_job_check` VALUES ('4', '4', '正常', '正常', '1421769600', null, '1');
INSERT INTO `t_pcs_job_check` VALUES ('5', '1', '正常', '正常', '1421769600', null, '7');
INSERT INTO `t_pcs_job_check` VALUES ('6', '2', '正常', '正常', '1422201600', null, '7');
INSERT INTO `t_pcs_job_check` VALUES ('7', '3', '正常', '正常', '1421769600', null, '7');
INSERT INTO `t_pcs_job_check` VALUES ('8', '4', '正常', '正常', '1421683200', null, '7');
INSERT INTO `t_pcs_job_check` VALUES ('9', '1', null, null, null, null, '5');
INSERT INTO `t_pcs_job_check` VALUES ('10', '2', null, null, null, null, '5');
INSERT INTO `t_pcs_job_check` VALUES ('11', '3', null, null, null, null, '5');
INSERT INTO `t_pcs_job_check` VALUES ('12', '4', null, null, null, null, '5');

-- ----------------------------
-- Table structure for t_pcs_lading
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_lading`;
CREATE TABLE `t_pcs_lading` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(16) DEFAULT NULL,
  `clientId` int(10) unsigned DEFAULT NULL,
  `productId` int(10) unsigned DEFAULT NULL,
  `type` int(10) unsigned DEFAULT NULL,
  `receiveClientId` int(10) unsigned DEFAULT NULL,
  `goodsTotal` varchar(16) DEFAULT NULL,
  `goodsPass` varchar(16) DEFAULT NULL,
  `goodsDelivery` varchar(16) DEFAULT NULL,
  `startTime` timestamp NULL DEFAULT NULL,
  `endTime` timestamp NULL DEFAULT NULL,
  `status` int(10) unsigned DEFAULT NULL,
  `description` text,
  `createUserId` int(10) unsigned DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `reviewUserId` int(10) unsigned DEFAULT NULL,
  `reviewTime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_lading
-- ----------------------------
INSERT INTO `t_pcs_lading` VALUES ('1', 'ZSY2015', '2', '1', '1', '3', '201.0', '201.0', '0', '2014-12-11 00:00:00', null, '1', '', '4', '2015-01-20 16:13:38', '0', null);
INSERT INTO `t_pcs_lading` VALUES ('2', 'JC-001', '2', '1', '1', '3', '111.0', '111.0', '0', '2014-12-15 00:00:00', null, '1', '', '4', '2015-01-20 16:42:26', '0', null);
INSERT INTO `t_pcs_lading` VALUES ('3', 'JC-002', '3', '1', '1', '4', '160', '120', '0', '2014-12-16 00:00:00', null, '0', '', '4', '2015-01-20 16:44:12', '0', null);
INSERT INTO `t_pcs_lading` VALUES ('4', 'JC-003', '4', '1', '1', '6', '80', '80', '0', '2014-12-16 00:00:00', null, '0', '', '4', '2015-01-20 16:48:27', '0', null);
INSERT INTO `t_pcs_lading` VALUES ('5', 'TD-15-001', '2', '1', '1', '3', '100', '100', '0', '2014-12-09 00:00:00', null, '0', '', '4', '2015-01-20 19:17:20', '0', null);

-- ----------------------------
-- Table structure for t_pcs_measure
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_measure`;
CREATE TABLE `t_pcs_measure` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `plateId` int(11) DEFAULT NULL,
  `workTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `meterStart` varchar(15) DEFAULT NULL,
  `meterEnd` varchar(15) DEFAULT NULL,
  `ladingNum` varchar(15) DEFAULT NULL,
  `actualNum` varchar(15) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `parkId` int(11) DEFAULT NULL,
  `tankId` int(11) DEFAULT NULL,
  `trainId` int(11) DEFAULT NULL,
  `tandId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_measure
-- ----------------------------
INSERT INTO `t_pcs_measure` VALUES ('1', '7', '2015-01-14 13:11:43', '1', '24', null, '23', null, '2', null, '7', null);

-- ----------------------------
-- Table structure for t_pcs_motorcade
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_motorcade`;
CREATE TABLE `t_pcs_motorcade` (
  `id` int(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_motorcade
-- ----------------------------

-- ----------------------------
-- Table structure for t_pcs_office_grade
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_office_grade`;
CREATE TABLE `t_pcs_office_grade` (
  `key` int(7) unsigned NOT NULL,
  `value` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_office_grade
-- ----------------------------
INSERT INTO `t_pcs_office_grade` VALUES ('1', '普通');
INSERT INTO `t_pcs_office_grade` VALUES ('2', '特B');
INSERT INTO `t_pcs_office_grade` VALUES ('3', '特C');
INSERT INTO `t_pcs_office_grade` VALUES ('4', '封存');

-- ----------------------------
-- Table structure for t_pcs_park
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_park`;
CREATE TABLE `t_pcs_park` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_park
-- ----------------------------
INSERT INTO `t_pcs_park` VALUES ('1', 'no01');
INSERT INTO `t_pcs_park` VALUES ('2', 'no02');
INSERT INTO `t_pcs_park` VALUES ('3', 'no03');
INSERT INTO `t_pcs_park` VALUES ('4', 'no04');

-- ----------------------------
-- Table structure for t_pcs_pass_status
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_pass_status`;
CREATE TABLE `t_pcs_pass_status` (
  `key` int(7) unsigned NOT NULL,
  `value` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_pass_status
-- ----------------------------
INSERT INTO `t_pcs_pass_status` VALUES ('0', '未放行');
INSERT INTO `t_pcs_pass_status` VALUES ('1', '海关放行');
INSERT INTO `t_pcs_pass_status` VALUES ('2', '已放行');

-- ----------------------------
-- Table structure for t_pcs_product
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_product`;
CREATE TABLE `t_pcs_product` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(15) DEFAULT NULL,
  `name` varchar(60) DEFAULT NULL,
  `type` int(7) unsigned DEFAULT NULL,
  `standardDensity` varchar(15) DEFAULT NULL,
  `volumeRatio` varchar(15) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` int(7) unsigned DEFAULT NULL,
  `editTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `editUserId` int(20) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_product
-- ----------------------------
INSERT INTO `t_pcs_product` VALUES ('1', 'C001', '甲醇', '1', '0.98', '0.45', null, null, '2014-11-30 21:20:35', null, null);
INSERT INTO `t_pcs_product` VALUES ('2', 'C002', '乙二醇', '1', '0.94', '0.67', 'aaaa', null, '2014-12-10 19:29:46', null, null);
INSERT INTO `t_pcs_product` VALUES ('3', 'O001', '93#汽油', '2', '0.67', '0.84', null, null, '2014-11-30 21:21:13', null, null);
INSERT INTO `t_pcs_product` VALUES ('4', 'O002', '94#汽油', '2', '0.86', '0.58', 'aaaa', null, '2014-12-10 19:31:36', null, null);
INSERT INTO `t_pcs_product` VALUES ('5', 'C004', '甲苯', '1', '0.94', '0.56', '', null, '2015-01-08 11:45:37', null, null);
INSERT INTO `t_pcs_product` VALUES ('6', 'C005', '三聚氰胺', '1', '10', '1', '3', null, '2015-01-08 11:46:24', null, null);
INSERT INTO `t_pcs_product` VALUES ('7', 'C003', '乙烷', '1', '11', '0.43', 'zxcvxzcv', null, '2015-01-08 11:45:19', null, null);
INSERT INTO `t_pcs_product` VALUES ('8', 'C006', '氧化二氢', '1', '0.02', '0.01', null, null, '2015-01-08 11:47:21', null, null);
INSERT INTO `t_pcs_product` VALUES ('9', 'O003', '柴油', '2', '0.76', '0.56', null, null, '2015-01-08 12:02:53', null, null);
INSERT INTO `t_pcs_product` VALUES ('10', 'O004', '润滑油', '2', '0.58', '0.48', null, null, '2015-01-08 12:04:42', null, null);

-- ----------------------------
-- Table structure for t_pcs_product_type
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_product_type`;
CREATE TABLE `t_pcs_product_type` (
  `key` int(7) unsigned NOT NULL,
  `value` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_product_type
-- ----------------------------
INSERT INTO `t_pcs_product_type` VALUES ('1', '油品');
INSERT INTO `t_pcs_product_type` VALUES ('2', '化学品');

-- ----------------------------
-- Table structure for t_pcs_qualification
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_qualification`;
CREATE TABLE `t_pcs_qualification` (
  `id` int(7) unsigned NOT NULL,
  `name` varchar(60) DEFAULT NULL,
  `description` text,
  `editTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `editUserId` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_qualification
-- ----------------------------
INSERT INTO `t_pcs_qualification` VALUES ('1', '化学品贸易资格证', '允许从事国家规定的一般化学品的贸易活动', '2015-01-09 15:00:32', '1');
INSERT INTO `t_pcs_qualification` VALUES ('2', '原油行业从业资格证', '允许从事原油行业的贸易、存储、提炼等相关业务', '2015-01-09 15:01:11', '1');

-- ----------------------------
-- Table structure for t_pcs_ship
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_ship`;
CREATE TABLE `t_pcs_ship` (
  `id` int(15) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(15) DEFAULT NULL,
  `name` varchar(60) DEFAULT NULL,
  `type` int(7) unsigned DEFAULT NULL,
  `refCode` varchar(15) DEFAULT NULL,
  `refName` varchar(60) DEFAULT NULL,
  `shipRegistry` varchar(60) DEFAULT NULL,
  `shipLenth` varchar(15) DEFAULT NULL,
  `shipWidth` varchar(15) DEFAULT NULL,
  `shipDraught` varchar(15) DEFAULT NULL,
  `buildYear` int(15) unsigned DEFAULT NULL,
  `loadCapacity` varchar(15) DEFAULT NULL,
  `grossTons` varchar(15) DEFAULT NULL,
  `netTons` varchar(15) DEFAULT NULL,
  `notice` varchar(255) DEFAULT NULL,
  `owner` varchar(60) DEFAULT NULL,
  `manager` varchar(60) DEFAULT NULL,
  `contactName` varchar(60) DEFAULT NULL,
  `contactPhone` varchar(30) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` int(7) unsigned DEFAULT NULL,
  `editUserId` int(20) unsigned DEFAULT NULL,
  `editTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `createTime` datetime DEFAULT NULL,
  `createUserId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_ship
-- ----------------------------
INSERT INTO `t_pcs_ship` VALUES ('1', 'W001', 'East Star II', '0', 'ES', '东星', '新加坡', '542', '135', '72', '2006', '6243', '3146', '1234', null, null, null, null, null, null, null, null, '2014-11-30 21:36:20', null, null);
INSERT INTO `t_pcs_ship` VALUES ('2', 'W002', 'Arab Moon', '0', 'AM', '阿拉伯之月', '沙特', '624', '134', '67', '2004', '5873', '5787', '4352', null, null, null, null, null, null, null, null, '2014-11-30 21:42:20', null, null);
INSERT INTO `t_pcs_ship` VALUES ('3', 'W003', 'New Titanic', '0', 'NT', '新泰坦尼克', '英国', '563', '98', '57', '2007', '4362', '3246', '2356', null, null, null, null, null, null, null, null, '2014-11-30 21:42:25', null, null);
INSERT INTO `t_pcs_ship` VALUES ('4', 'W004', 'Black Pearl', '0', 'BP', '黑珍珠', '巴拿马', '346', '72', '34', '2001', '3713', '2852', '1853', null, null, null, null, null, null, null, null, '2014-11-30 21:42:22', null, null);
INSERT INTO `t_pcs_ship` VALUES ('5', '222', '121', null, '1212', '12', '12', '1212', '12', '1', '21', '212', '12', '21', '12', '21', '00000', '21', '55555', '', null, null, '2014-12-11 14:16:20', null, null);
INSERT INTO `t_pcs_ship` VALUES ('6', '', '', null, '', '', null, null, '', null, null, null, null, '', null, null, null, null, null, '', null, null, '2014-12-22 18:05:47', null, null);

-- ----------------------------
-- Table structure for t_pcs_ship_agent
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_ship_agent`;
CREATE TABLE `t_pcs_ship_agent` (
  `id` int(10) NOT NULL,
  `code` varchar(15) DEFAULT NULL,
  `name` varchar(60) DEFAULT NULL,
  `type` int(7) unsigned DEFAULT NULL,
  `contactName` varchar(60) DEFAULT NULL,
  `contactEmail` varchar(30) DEFAULT NULL,
  `contactPhone` varchar(30) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` int(7) unsigned DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT NULL,
  `editUserId` int(20) unsigned DEFAULT NULL,
  `editTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `createUserId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_ship_agent
-- ----------------------------
INSERT INTO `t_pcs_ship_agent` VALUES ('1', '阿里', '阿里船舶代理公司', null, '吴飞', null, null, null, null, '2014-11-30 21:44:48', null, '2014-11-30 21:45:59', null);
INSERT INTO `t_pcs_ship_agent` VALUES ('2', '青云', '青云海事咨询公司', null, '张海军', null, null, null, null, '2014-11-30 21:45:09', null, '2014-11-30 21:46:12', null);
INSERT INTO `t_pcs_ship_agent` VALUES ('3', '尤克德', '尤克德船务代理公司', null, '刘晨', null, null, null, null, '2014-11-30 21:45:47', null, '2014-11-30 21:46:33', null);

-- ----------------------------
-- Table structure for t_pcs_ship_ref
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_ship_ref`;
CREATE TABLE `t_pcs_ship_ref` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `refName` varchar(60) DEFAULT NULL,
  `shipId` int(20) unsigned DEFAULT NULL,
  `editTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `editUserId` int(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_ship_ref
-- ----------------------------
INSERT INTO `t_pcs_ship_ref` VALUES ('1', '东星', '1', '2015-01-09 16:15:41', null);
INSERT INTO `t_pcs_ship_ref` VALUES ('2', '东方之星', '1', '2015-01-09 16:15:41', null);
INSERT INTO `t_pcs_ship_ref` VALUES ('3', '阿伯之月', '2', '2015-01-09 16:15:41', null);
INSERT INTO `t_pcs_ship_ref` VALUES ('4', '阿拉伯之月', '2', '2015-01-09 16:15:41', null);
INSERT INTO `t_pcs_ship_ref` VALUES ('5', '泰坦', '3', '2015-01-09 16:15:41', null);
INSERT INTO `t_pcs_ship_ref` VALUES ('6', '泰坦尼克', '3', '2015-01-09 16:15:41', null);
INSERT INTO `t_pcs_ship_ref` VALUES ('7', '黑珍珠', '4', '2015-01-09 16:15:41', null);
INSERT INTO `t_pcs_ship_ref` VALUES ('8', '黑玫瑰', '4', '2015-01-09 16:15:41', null);

-- ----------------------------
-- Table structure for t_pcs_status
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_status`;
CREATE TABLE `t_pcs_status` (
  `key` int(7) unsigned NOT NULL,
  `value` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_status
-- ----------------------------
INSERT INTO `t_pcs_status` VALUES ('0', '未提交');
INSERT INTO `t_pcs_status` VALUES ('1', '已提交');
INSERT INTO `t_pcs_status` VALUES ('2', '已通过');
INSERT INTO `t_pcs_status` VALUES ('3', '已退回');
INSERT INTO `t_pcs_status` VALUES ('4', '已删除');
INSERT INTO `t_pcs_status` VALUES ('5', '已失效');

-- ----------------------------
-- Table structure for t_pcs_store
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_store`;
CREATE TABLE `t_pcs_store` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `tankId` int(20) DEFAULT NULL,
  `startLevel` varchar(15) DEFAULT NULL,
  `endLevel` varchar(15) DEFAULT NULL,
  `startWeight` varchar(15) DEFAULT NULL,
  `endWeight` varchar(15) DEFAULT NULL,
  `startTemperature` varchar(15) DEFAULT NULL,
  `endTemperature` varchar(15) DEFAULT NULL,
  `realAmount` varchar(15) DEFAULT NULL,
  `measureAmount` varchar(15) DEFAULT NULL,
  `differAmount` varchar(15) DEFAULT NULL,
  `startHandLevel` varchar(15) DEFAULT NULL,
  `endHandLevel` varchar(15) DEFAULT NULL,
  `startHandWeight` varchar(15) DEFAULT NULL,
  `endHandWeight` varchar(15) DEFAULT NULL,
  `startTime` int(20) DEFAULT NULL,
  `endTime` int(20) DEFAULT NULL,
  `transportId` int(20) DEFAULT NULL,
  `endHand` varchar(255) DEFAULT NULL,
  `startHand` varchar(255) DEFAULT NULL,
  `endDiffer` varchar(255) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `messageType` int(11) DEFAULT NULL,
  `startDiffer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_store
-- ----------------------------
INSERT INTO `t_pcs_store` VALUES ('1', '1', null, null, null, null, null, null, '998', null, null, null, null, null, null, null, null, '1', null, null, null, null, '0', null);
INSERT INTO `t_pcs_store` VALUES ('9', '3', '23', '234', '24', '34', '234', '234', '4234', '23', '23', null, null, null, null, null, null, '3', null, null, null, null, '0', null);
INSERT INTO `t_pcs_store` VALUES ('10', '4', '423', '423', '423', '432', '234', '4234', '342', '42', '3', null, null, null, null, null, null, '3', null, null, null, null, '0', null);
INSERT INTO `t_pcs_store` VALUES ('11', '2', '423', '423', '23', '234', '234', '43', '3423', '34', '2342', null, null, null, null, null, null, '3', null, null, null, null, '0', null);
INSERT INTO `t_pcs_store` VALUES ('12', '4', '43', '423', '234', '23', '4234', '4', '423', '42', '23', null, null, null, null, null, null, '4', null, null, null, null, '0', null);
INSERT INTO `t_pcs_store` VALUES ('13', '5', '42', '4', '23', '42', '423', '234', '423', '34', '234', null, null, null, null, null, null, '4', null, null, null, null, '0', null);
INSERT INTO `t_pcs_store` VALUES ('14', '6', '3423', '23', '4234', '34234', '23', '23423', '423', '23423', '4', null, null, null, null, null, null, '4', null, null, null, null, '0', null);
INSERT INTO `t_pcs_store` VALUES ('15', '3', null, null, null, null, null, null, '792', null, null, null, null, null, null, null, null, '7', null, null, null, null, '0', null);
INSERT INTO `t_pcs_store` VALUES ('16', '1', null, null, null, null, null, null, '1185', null, null, null, null, null, null, null, null, '7', null, null, null, null, '0', null);
INSERT INTO `t_pcs_store` VALUES ('17', null, '34', '234', '24', '42', '234', '2', '234', null, null, '32', '234', null, null, null, null, null, null, null, '', '', '-1', '');
INSERT INTO `t_pcs_store` VALUES ('18', '1', '123', '123', '231', '123', '123', '123', '213', null, null, '123', '3', null, null, null, null, '0', null, null, '12', '123', '0', '213');
INSERT INTO `t_pcs_store` VALUES ('19', '3', '23', '42', '234', '342', '34', '234', '234', '2342', '234', '234', '423', '234', '23', '23', null, '9', null, null, null, null, '0', null);
INSERT INTO `t_pcs_store` VALUES ('20', '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '5', null, null, null, null, null, null);
INSERT INTO `t_pcs_store` VALUES ('21', '2', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '5', null, null, null, null, null, null);
INSERT INTO `t_pcs_store` VALUES ('22', '5', '343', '423', '234', '23', '423', '42', '34', '234', '23', '23', '42', '42', '34', null, null, '14', null, null, null, null, '0', null);
INSERT INTO `t_pcs_store` VALUES ('23', '4', '53', '45', '345', '354', '2', '4234', '234', '234', '42', '345', '45', '53', '3', null, null, '14', null, null, null, null, '0', null);
INSERT INTO `t_pcs_store` VALUES ('24', '3', '34', '345', '45', '34', '342', '23', '234', '234', '34', '45', '3453', '345', '453', null, null, '14', null, null, null, null, '0', null);

-- ----------------------------
-- Table structure for t_pcs_tank
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_tank`;
CREATE TABLE `t_pcs_tank` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(15) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `type` int(7) unsigned DEFAULT NULL,
  `productId` int(10) unsigned DEFAULT NULL,
  `capacityTotal` varchar(15) DEFAULT NULL,
  `capacityCurrent` varchar(15) DEFAULT NULL,
  `capacityFree` varchar(15) DEFAULT NULL,
  `testDensity` varchar(15) DEFAULT NULL,
  `testTemperature` varchar(15) DEFAULT NULL,
  `tankTemperature` varchar(15) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` int(7) unsigned DEFAULT NULL,
  `editTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `editUserId` int(20) unsigned DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_tank
-- ----------------------------
INSERT INTO `t_pcs_tank` VALUES ('1', 'A001', 'A区001号', '1', '1', '1000.00', '200.00', '800.00', null, null, null, '已清洗', null, '2015-01-20 19:28:31', null, null);
INSERT INTO `t_pcs_tank` VALUES ('2', 'A002', 'A区002号', '0', '2', '1000.00', '500.00', '500.00', null, null, null, '已准备好', null, '2015-01-19 21:31:45', null, null);
INSERT INTO `t_pcs_tank` VALUES ('3', 'A003', 'A区003号', '1', '2', '1000.00', '300.00', '700.00', null, null, null, '已清洗', null, '2015-01-19 20:53:44', null, null);
INSERT INTO `t_pcs_tank` VALUES ('4', 'A004', 'A区004号', '0', '1', '1000.00', '700.00', '300.00', null, null, null, '已清洗', null, '2015-01-19 12:06:20', null, null);
INSERT INTO `t_pcs_tank` VALUES ('5', 'B001', 'B区001号', '1', '1', '800.00', '500.00', '300.00', null, null, null, null, null, '2014-12-11 10:44:21', null, null);
INSERT INTO `t_pcs_tank` VALUES ('6', 'B002', 'B区002号', '1', '2', '800.00', '600.00', '200.00', null, null, null, '已准备好', null, '2015-01-20 10:46:26', null, null);
INSERT INTO `t_pcs_tank` VALUES ('7', 'C005', 'C区005号', '0', '1', '1000', '200', null, '10', '10', '10', '这是第二次测试', null, '2015-01-16 15:35:39', null, null);
INSERT INTO `t_pcs_tank` VALUES ('8', 'C005', 'C区005号', '0', '1', '1000', '200', null, '10', '10', '10', '阿萨德发', null, '2014-12-11 14:27:16', null, null);

-- ----------------------------
-- Table structure for t_pcs_tank_type
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_tank_type`;
CREATE TABLE `t_pcs_tank_type` (
  `key` int(7) unsigned NOT NULL,
  `value` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_tank_type
-- ----------------------------
INSERT INTO `t_pcs_tank_type` VALUES ('0', '普通');
INSERT INTO `t_pcs_tank_type` VALUES ('1', '免税');

-- ----------------------------
-- Table structure for t_pcs_tax_type
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_tax_type`;
CREATE TABLE `t_pcs_tax_type` (
  `key` int(7) unsigned NOT NULL,
  `value` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_tax_type
-- ----------------------------
INSERT INTO `t_pcs_tax_type` VALUES ('0', '保税');
INSERT INTO `t_pcs_tax_type` VALUES ('1', '非保税');

-- ----------------------------
-- Table structure for t_pcs_trade_type
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_trade_type`;
CREATE TABLE `t_pcs_trade_type` (
  `key` int(7) unsigned NOT NULL,
  `value` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_trade_type
-- ----------------------------
INSERT INTO `t_pcs_trade_type` VALUES ('1', '内贸');
INSERT INTO `t_pcs_trade_type` VALUES ('2', '外贸');
INSERT INTO `t_pcs_trade_type` VALUES ('3', '保税');

-- ----------------------------
-- Table structure for t_pcs_train
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_train`;
CREATE TABLE `t_pcs_train` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `deliverTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `operator` int(11) DEFAULT NULL,
  `approveAmount` double DEFAULT NULL,
  `actualAmount` double DEFAULT NULL,
  `productId` int(11) DEFAULT NULL,
  `clientId` int(11) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_train
-- ----------------------------
INSERT INTO `t_pcs_train` VALUES ('7', '2015-01-14 13:17:28', '4', '0', '0', '6', '1', null, '42');

-- ----------------------------
-- Table structure for t_pcs_trans
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_trans`;
CREATE TABLE `t_pcs_trans` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `tankId` int(20) DEFAULT NULL,
  `transportId` int(20) DEFAULT NULL,
  `tubeId` int(20) DEFAULT NULL,
  `tubeType` int(7) DEFAULT NULL,
  `parentId` int(20) DEFAULT NULL,
  `workContent` text,
  `workUserId` int(20) DEFAULT NULL,
  `workTime` bigint(20) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `createTime` bigint(20) DEFAULT NULL,
  `createUserId` int(11) DEFAULT NULL,
  `job` int(11) DEFAULT NULL,
  `result` varchar(255) DEFAULT NULL,
  `solve` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_trans
-- ----------------------------
INSERT INTO `t_pcs_trans` VALUES ('1', null, '1', '2', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_trans` VALUES ('2', '1', '1', '12', null, '2', null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_trans` VALUES ('9', null, '3', '3', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_trans` VALUES ('10', null, '3', '15', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_trans` VALUES ('11', null, '3', '16', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_trans` VALUES ('12', null, '3', '14', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_trans` VALUES ('13', null, '4', '2', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_trans` VALUES ('14', null, '4', '15', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_trans` VALUES ('15', null, '4', '16', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_trans` VALUES ('16', null, '4', '17', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_trans` VALUES ('17', '3', '7', '5', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_trans` VALUES ('18', null, '7', '4', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_trans` VALUES ('19', '1', '7', '14', null, '4', null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_trans` VALUES ('20', null, '9', '6', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_trans` VALUES ('21', null, '9', '16', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_trans` VALUES ('22', null, '9', '15', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_trans` VALUES ('23', null, '9', '13', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_trans` VALUES ('24', null, '5', '2', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_trans` VALUES ('25', '2', '5', '11', null, '2', null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_trans` VALUES ('26', '1', '5', '12', null, '2', null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_trans` VALUES ('27', null, '14', '3', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_trans` VALUES ('28', null, '14', '15', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_trans` VALUES ('29', null, '14', '17', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_trans` VALUES ('30', null, '14', '16', null, null, null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for t_pcs_transport_program
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_transport_program`;
CREATE TABLE `t_pcs_transport_program` (
  `id` int(20) NOT NULL,
  `type` int(7) DEFAULT NULL COMMENT '类型：0入库流程，1打回流，2船发流程，3车发流程',
  `flow` text,
  `svg` text,
  `node` text,
  `arrivalId` int(20) DEFAULT NULL,
  `tubeInfo` text,
  `tankInfo` text,
  `dockWork` text,
  `tubeWork` text,
  `powerWork` text,
  `createUserId` int(20) DEFAULT NULL,
  `createTime` bigint(20) DEFAULT NULL,
  `reviewUserId` int(20) DEFAULT NULL,
  `reviewTime` bigint(20) DEFAULT NULL,
  `reviewCraftUserId` int(20) DEFAULT NULL,
  `reviewCraftTime` bigint(20) DEFAULT NULL,
  `trainId` int(11) DEFAULT NULL,
  `pipeTask` text,
  `workTask` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='类型：0入库流程，1打回流，2船发流程，3车发流程';

-- ----------------------------
-- Records of t_pcs_transport_program
-- ----------------------------
INSERT INTO `t_pcs_transport_program` VALUES ('1', '0', '<mxGraphModel>\n  <root>\n    <mxCell id=\"0\"/>\n    <mxCell id=\"1\" parent=\"0\"/>\n    <mxCell id=\"2\" value=\"1#泊位\" vertex=\"1\" parent=\"1\" type=\"berth\">\n      <mxGeometry x=\"20\" y=\"80\" width=\"48\" height=\"48\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"3\" value=\"A区001号\" style=\"shape=cylinder\" vertex=\"1\" type=\"tank\" parent=\"1\" key=\"1\">\n      <mxGeometry x=\"400\" y=\"88\" width=\"48\" height=\"32\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"4\" value=\"大EG1\" style=\"shape=hexagon;\" vertex=\"1\" type=\"bTube\" parent=\"1\" key=\"2\">\n      <mxGeometry x=\"130\" y=\"92\" width=\"48\" height=\"24\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"5\" value=\"L01-2\" style=\"shape=ellipse\" vertex=\"1\" type=\"sTube\" parent=\"1\" key=\"12\">\n      <mxGeometry x=\"250\" y=\"92\" width=\"48\" height=\"24\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"6\" value=\"\" edge=\"1\" parent=\"1\" source=\"2\" target=\"4\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"7\" value=\"\" edge=\"1\" parent=\"1\" source=\"4\" target=\"5\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"8\" value=\"\" edge=\"1\" parent=\"1\" source=\"5\" target=\"3\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n  </root>\n</mxGraphModel>\n', '<div style=\"position: relative;\" oncontextmenu=\"return false;\" class=\"imgContentDivStyle\"><svg style=\"width: 100%; height: 100%; min-width: 450px; min-height: 129px;\"><g><g></g><g><g style=\"cursor: pointer;\"><rect style=\"cursor: pointer;\" stroke-width=\"1\" height=\"48\" width=\"48\" y=\"80\" x=\"20\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></rect></g><g style=\"cursor: pointer;\" text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"107.5\" x=\"44\" text-decoration=\"none\">1#泊位</text></g><g style=\"cursor: move;\"><path style=\"cursor: move;\" d=\"M 400 94 C 400 86 448 86 448 94 L 448 114 C 448 122 400 122 400 114 Z\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></path><path d=\"M 400 94 C 400 100 448 100 448 94 \" stroke-width=\"1\" fill=\"none\" stroke=\"#000000\"></path></g><g style=\"cursor: move;\" text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"107.5\" x=\"424\" text-decoration=\"none\">A区001号</text></g><g style=\"cursor: pointer;\"><path style=\"cursor: pointer;\" d=\"M 142 92 L 166 92 L 178 104 L 166 116 L 142 116 L 130 104 Z\" stroke-linejoin=\"round\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></path></g><g style=\"cursor: pointer;\" text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"107.5\" x=\"154\" text-decoration=\"none\">大EG1</text></g><g style=\"cursor: move;\"><ellipse style=\"cursor: move;\" ry=\"12\" rx=\"24\" cy=\"104\" cx=\"274\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></ellipse></g><g style=\"cursor: move;\" text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"107.5\" x=\"274\" text-decoration=\"none\">L01-2</text></g><g><path stroke-width=\"1\" d=\"M 68 104 L 84 104 C 99 104 99,104 115 104 L 122 104 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 128.882 104 L 119.882 108.5 L 122.132 104 L 119.882 99.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 68 104 L 84 104 C 99 104 99,104 115 104 L 122 104 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g style=\"cursor: move;\"><path style=\"cursor: move;\" stroke-width=\"1\" d=\"M 178 104 L 196 104 C 214 104 214,104 232 104 L 242 104 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 248.882 104 L 239.882 108.5 L 242.132 104 L 239.882 99.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path style=\"cursor: move;\" stroke-width=\"9\" d=\"M 178 104 L 196 104 C 214 104 214,104 232 104 L 242 104 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g><path stroke-width=\"1\" d=\"M 298 104 L 329 104 C 349 104 349,104 369 104 L 392 104 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 398.882 104 L 389.882 108.5 L 392.132 104 L 389.882 99.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 298 104 L 329 104 C 349 104 349,104 369 104 L 392 104 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g></g><g><g style=\"cursor: move;\"><path stroke-dasharray=\"3 3\" stroke-width=\"1\" d=\"M 298 104 L 349 104 L 400 104 \" fill=\"none\" stroke=\"#00FF00\"></path><path stroke-width=\"9\" d=\"M 298 104 L 349 104 L 400 104 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g style=\"cursor: pointer;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"101\" x=\"295\" shape-rendering=\"crispEdges\" fill=\"#0000FF\" stroke=\"black\"></rect></g><g style=\"cursor: row-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"101\" x=\"346\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g><g style=\"cursor: pointer;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"101\" x=\"397\" shape-rendering=\"crispEdges\" fill=\"#0000FF\" stroke=\"black\"></rect></g><g style=\"cursor: default; visibility: hidden;\"><rect stroke-width=\"1\" height=\"4\" width=\"4\" y=\"102\" x=\"347\" shape-rendering=\"crispEdges\" fill=\"yellow\" stroke=\"black\"></rect></g></g></g></svg></div>', null, '1', null, null, null, null, null, null, null, '4', null, '4', null, null, null, null);
INSERT INTO `t_pcs_transport_program` VALUES ('3', '2', '<mxGraphModel>\n  <root>\n    <mxCell id=\"0\"/>\n    <mxCell id=\"1\" parent=\"0\"/>\n    <mxCell id=\"2\" vertex=\"1\" parent=\"1\" type=\"berth\">\n      <mxGeometry x=\"20\" y=\"20\" width=\"48\" height=\"48\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"3\" value=\"A区004号\" style=\"shape=cylinder\" vertex=\"1\" type=\"tank\" parent=\"1\" key=\"4\">\n      <mxGeometry x=\"530\" y=\"4\" width=\"48\" height=\"32\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"4\" value=\"A区003号\" style=\"shape=cylinder\" vertex=\"1\" type=\"tank\" parent=\"1\" key=\"3\">\n      <mxGeometry x=\"530\" y=\"78\" width=\"48\" height=\"32\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"5\" value=\"A区002号\" style=\"shape=cylinder\" vertex=\"1\" type=\"tank\" parent=\"1\" key=\"2\">\n      <mxGeometry x=\"530\" y=\"140\" width=\"48\" height=\"32\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"6\" value=\"L02-1\" style=\"shape=ellipse\" vertex=\"1\" type=\"sTube\" parent=\"1\" key=\"15\">\n      <mxGeometry x=\"350\" y=\"20\" width=\"48\" height=\"24\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"7\" value=\"L02-2\" style=\"shape=ellipse\" vertex=\"1\" type=\"sTube\" parent=\"1\" key=\"16\">\n      <mxGeometry x=\"360\" y=\"70\" width=\"48\" height=\"24\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"8\" value=\"L01-4\" style=\"shape=ellipse\" vertex=\"1\" type=\"sTube\" parent=\"1\" key=\"14\">\n      <mxGeometry x=\"370\" y=\"130\" width=\"48\" height=\"24\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"9\" value=\"大EG2\" style=\"shape=hexagon;\" vertex=\"1\" type=\"bTube\" parent=\"1\" key=\"3\">\n      <mxGeometry x=\"180\" y=\"50\" width=\"48\" height=\"24\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"10\" value=\"\" edge=\"1\" parent=\"1\" source=\"2\" target=\"9\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"11\" value=\"\" edge=\"1\" parent=\"1\" source=\"9\" target=\"6\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"12\" value=\"\" edge=\"1\" parent=\"1\" source=\"9\" target=\"7\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"13\" value=\"\" edge=\"1\" parent=\"1\" source=\"9\" target=\"8\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"14\" value=\"\" edge=\"1\" parent=\"1\" source=\"7\" target=\"4\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"15\" value=\"\" edge=\"1\" parent=\"1\" source=\"8\" target=\"5\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"16\" value=\"\" edge=\"1\" parent=\"1\" source=\"6\" target=\"3\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n  </root>\n</mxGraphModel>\n', '<div style=\"position: relative;\" oncontextmenu=\"return false;\" class=\"imgContentDivStyle\"><svg style=\"width: 100%; height: 100%; min-width: 580px; min-height: 173px;\"><g><g></g><g><g><rect stroke-width=\"1\" height=\"48\" width=\"48\" y=\"20\" x=\"20\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></rect></g><g><path d=\"M 530 10 C 530 2 578 2 578 10 L 578 30 C 578 38 530 38 530 30 Z\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></path><path d=\"M 530 10 C 530 16 578 16 578 10 \" stroke-width=\"1\" fill=\"none\" stroke=\"#000000\"></path></g><g text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"23.5\" x=\"554\" text-decoration=\"none\">A区004号</text></g><g style=\"cursor: move;\"><path style=\"cursor: move;\" d=\"M 530 84 C 530 76 578 76 578 84 L 578 104 C 578 112 530 112 530 104 Z\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></path><path d=\"M 530 84 C 530 90 578 90 578 84 \" stroke-width=\"1\" fill=\"none\" stroke=\"#000000\"></path></g><g style=\"cursor: move;\" text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"97.5\" x=\"554\" text-decoration=\"none\">A区003号</text></g><g><path d=\"M 530 146 C 530 138 578 138 578 146 L 578 166 C 578 174 530 174 530 166 Z\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></path><path d=\"M 530 146 C 530 152 578 152 578 146 \" stroke-width=\"1\" fill=\"none\" stroke=\"#000000\"></path></g><g text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"159.5\" x=\"554\" text-decoration=\"none\">A区002号</text></g><g><ellipse ry=\"12\" rx=\"24\" cy=\"32\" cx=\"374\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></ellipse></g><g text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"35.5\" x=\"374\" text-decoration=\"none\">L02-1</text></g><g><ellipse ry=\"12\" rx=\"24\" cy=\"82\" cx=\"384\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></ellipse></g><g text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"85.5\" x=\"384\" text-decoration=\"none\">L02-2</text></g><g style=\"cursor: move;\"><ellipse style=\"cursor: move;\" ry=\"12\" rx=\"24\" cy=\"142\" cx=\"394\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></ellipse></g><g style=\"cursor: move;\" text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"145.5\" x=\"394\" text-decoration=\"none\">L01-4</text></g><g><path d=\"M 192 50 L 216 50 L 228 62 L 216 74 L 192 74 L 180 62 Z\" stroke-linejoin=\"round\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></path></g><g text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"65.5\" x=\"204\" text-decoration=\"none\">大EG2</text></g><g><path stroke-width=\"1\" d=\"M 68 59 L 104 59 C 124 59 124,59 144 59 L 172 59 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 178.882 59 L 169.882 63.5 L 172.132 59 L 169.882 54.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 68 59 L 104 59 C 124 59 124,59 144 59 L 172 59 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g><path stroke-width=\"1\" d=\"M 204 50 L 204 49 C 204 47 204,47 224 47 L 354 47 C 374 47 374,47 374 46 L 374 52 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 374 45.118 L 378.5 54.118 L 374 51.868 L 369.5 54.118 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 204 50 L 204 49 C 204 47 204,47 224 47 L 354 47 C 374 47 374,47 374 46 L 374 52 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g><path stroke-width=\"1\" d=\"M 228 72 L 274 72 C 294 72 294,72 314 72 L 352 72 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 358.882 72 L 349.882 76.5 L 352.132 72 L 349.882 67.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 228 72 L 274 72 C 294 72 294,72 314 72 L 352 72 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g><path stroke-width=\"1\" d=\"M 204 74 L 204 88 C 204 102 204,102 224 102 L 374 102 C 394 102 394,102 394 116 L 394 122 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 394 128.882 L 389.5 119.882 L 394 122.132 L 398.5 119.882 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 204 74 L 204 88 C 204 102 204,102 224 102 L 374 102 C 394 102 394,102 394 116 L 394 122 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g><path stroke-width=\"1\" d=\"M 408 86 L 449 86 C 469 86 469,86 489 86 L 522 86 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 528.882 86 L 519.882 90.5 L 522.132 86 L 519.882 81.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 408 86 L 449 86 C 469 86 469,86 489 86 L 522 86 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g style=\"cursor: move;\"><path style=\"cursor: move;\" stroke-width=\"1\" d=\"M 418 147 L 454 147 C 474 147 474,147 494 147 L 522 147 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 528.882 147 L 519.882 151.5 L 522.132 147 L 519.882 142.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path style=\"cursor: move;\" stroke-width=\"9\" d=\"M 418 147 L 454 147 C 474 147 474,147 494 147 L 522 147 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g><path stroke-width=\"1\" d=\"M 398 28 L 444 28 C 464 28 464,28 484 28 L 522 28 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 528.882 28 L 519.882 32.5 L 522.132 28 L 519.882 23.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 398 28 L 444 28 C 464 28 464,28 484 28 L 522 28 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g></g><g><g style=\"cursor: move;\" pointer-events=\"none\"><rect stroke-dasharray=\"3 3\" stroke-width=\"1\" height=\"32\" width=\"48\" y=\"140\" x=\"530\" shape-rendering=\"crispEdges\" fill=\"none\" stroke=\"#00FF00\"></rect></g><g style=\"cursor: nw-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"137\" x=\"527\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g><g style=\"cursor: n-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"137\" x=\"551\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g><g style=\"cursor: ne-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"137\" x=\"575\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g><g style=\"cursor: w-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"153\" x=\"527\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g><g style=\"cursor: e-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"153\" x=\"575\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g><g style=\"cursor: sw-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"169\" x=\"527\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g><g style=\"cursor: s-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"169\" x=\"551\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g><g style=\"cursor: se-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"169\" x=\"575\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g></g></g></svg></div>', null, '3', '', '', '是否有物料泄漏', '', '', null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_transport_program` VALUES ('4', '2', '<mxGraphModel>\n  <root>\n    <mxCell id=\"0\"/>\n    <mxCell id=\"1\" parent=\"0\"/>\n    <mxCell id=\"2\" vertex=\"1\" parent=\"1\" type=\"berth\">\n      <mxGeometry x=\"20\" y=\"20\" width=\"48\" height=\"48\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"3\" value=\"A区004号\" style=\"shape=cylinder\" vertex=\"1\" type=\"tank\" parent=\"1\" key=\"4\">\n      <mxGeometry x=\"500\" width=\"48\" height=\"32\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"4\" value=\"B区001号\" style=\"shape=cylinder\" vertex=\"1\" type=\"tank\" parent=\"1\" key=\"5\">\n      <mxGeometry x=\"510\" y=\"60\" width=\"48\" height=\"32\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"5\" value=\"B区002号\" style=\"shape=cylinder\" vertex=\"1\" type=\"tank\" parent=\"1\" key=\"6\">\n      <mxGeometry x=\"510\" y=\"120\" width=\"48\" height=\"32\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"6\" value=\"L02-1\" style=\"shape=ellipse\" vertex=\"1\" type=\"sTube\" parent=\"1\" key=\"15\">\n      <mxGeometry x=\"340\" y=\"10\" width=\"48\" height=\"24\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"7\" value=\"L02-2\" style=\"shape=ellipse\" vertex=\"1\" type=\"sTube\" parent=\"1\" key=\"16\">\n      <mxGeometry x=\"350\" y=\"70\" width=\"48\" height=\"24\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"8\" value=\"L03-1\" style=\"shape=ellipse\" vertex=\"1\" type=\"sTube\" parent=\"1\" key=\"17\">\n      <mxGeometry x=\"350\" y=\"130\" width=\"48\" height=\"24\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"9\" value=\"大EG1\" style=\"shape=hexagon;\" vertex=\"1\" type=\"bTube\" parent=\"1\" key=\"2\">\n      <mxGeometry x=\"190\" y=\"50\" width=\"48\" height=\"24\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"10\" value=\"\" edge=\"1\" parent=\"1\" source=\"2\" target=\"9\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"11\" value=\"\" edge=\"1\" parent=\"1\" source=\"9\" target=\"6\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"12\" value=\"\" edge=\"1\" parent=\"1\" source=\"9\" target=\"7\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"13\" value=\"\" edge=\"1\" parent=\"1\" source=\"9\" target=\"8\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"14\" value=\"\" edge=\"1\" parent=\"1\" source=\"6\" target=\"3\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"15\" value=\"\" edge=\"1\" parent=\"1\" source=\"7\" target=\"4\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"16\" value=\"\" edge=\"1\" parent=\"1\" source=\"8\" target=\"5\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n  </root>\n</mxGraphModel>\n', '<div style=\"position: relative;\" oncontextmenu=\"return false;\" class=\"imgContentDivStyle\"><svg style=\"width: 100%; height: 100%; min-width: 560px; min-height: 155px;\"><g><g></g><g><g><rect stroke-width=\"1\" height=\"48\" width=\"48\" y=\"20\" x=\"20\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></rect></g><g><path d=\"M 500 6 C 500 -2 548 -2 548 6 L 548 26 C 548 34 500 34 500 26 Z\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></path><path d=\"M 500 6 C 500 12 548 12 548 6 \" stroke-width=\"1\" fill=\"none\" stroke=\"#000000\"></path></g><g text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"19.5\" x=\"524\" text-decoration=\"none\">A区004号</text></g><g><path d=\"M 510 66 C 510 58 558 58 558 66 L 558 86 C 558 94 510 94 510 86 Z\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></path><path d=\"M 510 66 C 510 72 558 72 558 66 \" stroke-width=\"1\" fill=\"none\" stroke=\"#000000\"></path></g><g text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"79.5\" x=\"534\" text-decoration=\"none\">B区001号</text></g><g><path d=\"M 510 126 C 510 118 558 118 558 126 L 558 146 C 558 154 510 154 510 146 Z\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></path><path d=\"M 510 126 C 510 132 558 132 558 126 \" stroke-width=\"1\" fill=\"none\" stroke=\"#000000\"></path></g><g text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"139.5\" x=\"534\" text-decoration=\"none\">B区002号</text></g><g><ellipse ry=\"12\" rx=\"24\" cy=\"22\" cx=\"364\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></ellipse></g><g text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"25.5\" x=\"364\" text-decoration=\"none\">L02-1</text></g><g><ellipse ry=\"12\" rx=\"24\" cy=\"82\" cx=\"374\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></ellipse></g><g text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"85.5\" x=\"374\" text-decoration=\"none\">L02-2</text></g><g><ellipse ry=\"12\" rx=\"24\" cy=\"142\" cx=\"374\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></ellipse></g><g text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"145.5\" x=\"374\" text-decoration=\"none\">L03-1</text></g><g><path d=\"M 202 50 L 226 50 L 238 62 L 226 74 L 202 74 L 190 62 Z\" stroke-linejoin=\"round\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></path></g><g text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"65.5\" x=\"214\" text-decoration=\"none\">大EG1</text></g><g><path stroke-width=\"1\" d=\"M 68 59 L 109 59 C 129 59 129,59 149 59 L 182 59 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 188.882 59 L 179.882 63.5 L 182.132 59 L 179.882 54.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 68 59 L 109 59 C 129 59 129,59 149 59 L 182 59 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g><path stroke-width=\"1\" d=\"M 214 50 L 214 46 C 214 42 214,42 234 42 L 344 42 C 364 42 364,42 364 38 L 364 42 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 364 35.118 L 368.5 44.118 L 364 41.868 L 359.5 44.118 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 214 50 L 214 46 C 214 42 214,42 234 42 L 344 42 C 364 42 364,42 364 38 L 364 42 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g><path stroke-width=\"1\" d=\"M 238 72 L 274 72 C 294 72 294,72 314 72 L 342 72 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 348.882 72 L 339.882 76.5 L 342.132 72 L 339.882 67.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 238 72 L 274 72 C 294 72 294,72 314 72 L 342 72 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g><path stroke-width=\"1\" d=\"M 214 74 L 214 88 C 214 102 214,102 234 102 L 354 102 C 374 102 374,102 374 116 L 374 122 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 374 128.882 L 369.5 119.882 L 374 122.132 L 378.5 119.882 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 214 74 L 214 88 C 214 102 214,102 234 102 L 354 102 C 374 102 374,102 374 116 L 374 122 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g><path stroke-width=\"1\" d=\"M 388 21 L 424 21 C 444 21 444,21 464 21 L 492 21 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 498.882 21 L 489.882 25.5 L 492.132 21 L 489.882 16.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 388 21 L 424 21 C 444 21 444,21 464 21 L 492 21 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g><path stroke-width=\"1\" d=\"M 398 81 L 434 81 C 454 81 454,81 474 81 L 502 81 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 508.882 81 L 499.882 85.5 L 502.132 81 L 499.882 76.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 398 81 L 434 81 C 454 81 454,81 474 81 L 502 81 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g><path stroke-width=\"1\" d=\"M 398 141 L 434 141 C 454 141 454,141 474 141 L 502 141 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 508.882 141 L 499.882 145.5 L 502.132 141 L 499.882 136.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 398 141 L 434 141 C 454 141 454,141 474 141 L 502 141 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g></g><g><g style=\"cursor: move;\" pointer-events=\"none\"><rect stroke-dasharray=\"3 3\" stroke-width=\"1\" height=\"32\" width=\"48\" y=\"120\" x=\"510\" shape-rendering=\"crispEdges\" fill=\"none\" stroke=\"#00FF00\"></rect></g><g style=\"cursor: nw-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"117\" x=\"507\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g><g style=\"cursor: n-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"117\" x=\"531\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g><g style=\"cursor: ne-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"117\" x=\"555\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g><g style=\"cursor: w-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"133\" x=\"507\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g><g style=\"cursor: e-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"133\" x=\"555\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g><g style=\"cursor: sw-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"149\" x=\"507\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g><g style=\"cursor: s-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"149\" x=\"531\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g><g style=\"cursor: se-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"149\" x=\"555\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g></g></g></svg></div>', null, '7', '', '', '管压是否稳定', '人员是否清除静电', '检查阀门是否处于正确状态', null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_transport_program` VALUES ('5', '0', '<mxGraphModel>\n  <root>\n    <mxCell id=\"0\"/>\n    <mxCell id=\"1\" parent=\"0\"/>\n    <mxCell id=\"2\" value=\"4#泊位\" vertex=\"1\" parent=\"1\" type=\"berth\">\n      <mxGeometry x=\"20\" y=\"20\" width=\"48\" height=\"48\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"3\" value=\"A区002号\" style=\"shape=cylinder\" vertex=\"1\" type=\"tank\" parent=\"1\" key=\"2\">\n      <mxGeometry x=\"510\" y=\"10\" width=\"48\" height=\"32\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"4\" value=\"A区001号\" style=\"shape=cylinder\" vertex=\"1\" type=\"tank\" parent=\"1\" key=\"1\">\n      <mxGeometry x=\"510\" y=\"60\" width=\"48\" height=\"32\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"5\" value=\"L01-1\" style=\"shape=ellipse\" vertex=\"1\" type=\"sTube\" parent=\"1\" key=\"11\">\n      <mxGeometry x=\"380\" y=\"14\" width=\"48\" height=\"24\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"6\" value=\"L01-2\" style=\"shape=ellipse\" vertex=\"1\" type=\"sTube\" parent=\"1\" key=\"12\">\n      <mxGeometry x=\"380\" y=\"64\" width=\"48\" height=\"24\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"7\" value=\"大EG1\" style=\"shape=hexagon;\" vertex=\"1\" type=\"bTube\" parent=\"1\" key=\"2\">\n      <mxGeometry x=\"210\" y=\"36\" width=\"48\" height=\"24\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"8\" value=\"\" edge=\"1\" parent=\"1\" source=\"2\" target=\"7\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"9\" value=\"\" edge=\"1\" parent=\"1\" source=\"7\" target=\"5\">\n      <mxGeometry relative=\"1\" as=\"geometry\">\n        <Array as=\"points\">\n          <mxPoint x=\"320\" y=\"30\"/>\n        </Array>\n      </mxGeometry>\n    </mxCell>\n    <mxCell id=\"10\" value=\"\" edge=\"1\" parent=\"1\" source=\"7\" target=\"6\">\n      <mxGeometry relative=\"1\" as=\"geometry\">\n        <Array as=\"points\">\n          <mxPoint x=\"320\" y=\"80\"/>\n        </Array>\n      </mxGeometry>\n    </mxCell>\n    <mxCell id=\"11\" value=\"\" edge=\"1\" parent=\"1\" source=\"5\" target=\"3\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"12\" value=\"\" edge=\"1\" parent=\"1\" source=\"6\" target=\"4\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n  </root>\n</mxGraphModel>\n', '<div style=\"position: relative;\" oncontextmenu=\"return false;\" class=\"imgContentDivStyle\"><svg style=\"width: 100%; height: 100%; min-width: 561px; min-height: 93px;\"><g><g></g><g><g style=\"cursor: move;\"><rect style=\"cursor: move;\" stroke-width=\"1\" height=\"48\" width=\"48\" y=\"20\" x=\"20\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></rect></g><g style=\"cursor: move;\" text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"47.5\" x=\"44\" text-decoration=\"none\">4#泊位</text></g><g><path d=\"M 510 16 C 510 8 558 8 558 16 L 558 36 C 558 44 510 44 510 36 Z\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></path><path d=\"M 510 16 C 510 22 558 22 558 16 \" stroke-width=\"1\" fill=\"none\" stroke=\"#000000\"></path></g><g text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"29.5\" x=\"534\" text-decoration=\"none\">A区002号</text></g><g><path d=\"M 510 66 C 510 58 558 58 558 66 L 558 86 C 558 94 510 94 510 86 Z\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></path><path d=\"M 510 66 C 510 72 558 72 558 66 \" stroke-width=\"1\" fill=\"none\" stroke=\"#000000\"></path></g><g text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"79.5\" x=\"534\" text-decoration=\"none\">A区001号</text></g><g><ellipse ry=\"12\" rx=\"24\" cy=\"26\" cx=\"404\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></ellipse></g><g text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"29.5\" x=\"404\" text-decoration=\"none\">L01-1</text></g><g><ellipse ry=\"12\" rx=\"24\" cy=\"76\" cx=\"404\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></ellipse></g><g text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"79.5\" x=\"404\" text-decoration=\"none\">L01-2</text></g><g><path d=\"M 222 36 L 246 36 L 258 48 L 246 60 L 222 60 L 210 48 Z\" stroke-linejoin=\"round\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></path></g><g text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"51.5\" x=\"234\" text-decoration=\"none\">大EG1</text></g><g><path stroke-width=\"1\" d=\"M 68 48 L 119 48 C 139 48 139,48 159 48 L 202 48 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 208.882 48 L 199.882 52.5 L 202.132 48 L 199.882 43.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 68 48 L 119 48 C 139 48 139,48 159 48 L 202 48 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g><path stroke-width=\"1\" d=\"M 234 36 L 234 33 C 234 30 234,30 254 30 L 372 30 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 378.882 30 L 369.882 34.5 L 372.132 30 L 369.882 25.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 234 36 L 234 33 C 234 30 234,30 254 30 L 372 30 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g><path stroke-width=\"1\" d=\"M 234 60 L 234 70 C 234 80 234,80 254 80 L 372 80 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 378.882 80 L 369.882 84.5 L 372.132 80 L 369.882 75.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 234 60 L 234 70 C 234 80 234,80 254 80 L 372 80 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g><path stroke-width=\"1\" d=\"M 428 26 L 449 26 C 469 26 469,26 489 26 L 502 26 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 508.882 26 L 499.882 30.5 L 502.132 26 L 499.882 21.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 428 26 L 449 26 C 469 26 469,26 489 26 L 502 26 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g><path stroke-width=\"1\" d=\"M 428 76 L 449 76 C 469 76 469,76 489 76 L 502 76 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 508.882 76 L 499.882 80.5 L 502.132 76 L 499.882 71.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 428 76 L 449 76 C 469 76 469,76 489 76 L 502 76 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g></g><g></g></g></svg></div>', null, '9', null, null, null, null, null, null, '1421769600', null, null, null, null, null, null, null);
INSERT INTO `t_pcs_transport_program` VALUES ('6', '1', null, null, null, '9', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_transport_program` VALUES ('7', '0', '<mxGraphModel>\n  <root>\n    <mxCell id=\"0\"/>\n    <mxCell id=\"1\" parent=\"0\"/>\n    <mxCell id=\"2\" value=\"5#泊位\" vertex=\"1\" parent=\"1\" type=\"berth\">\n      <mxGeometry x=\"20\" y=\"20\" width=\"48\" height=\"48\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"3\" value=\"A区003号\" style=\"shape=cylinder\" vertex=\"1\" type=\"tank\" parent=\"1\" key=\"3\">\n      <mxGeometry x=\"360\" y=\"20\" width=\"48\" height=\"32\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"4\" value=\"大EG4\" style=\"shape=hexagon;\" vertex=\"1\" type=\"bTube\" parent=\"1\" key=\"5\">\n      <mxGeometry x=\"130\" y=\"20\" width=\"48\" height=\"24\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"5\" value=\"\" edge=\"1\" parent=\"1\" source=\"2\" target=\"4\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"6\" value=\"\" edge=\"1\" parent=\"1\" source=\"4\" target=\"3\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"7\" value=\"A区001号\" style=\"shape=cylinder\" vertex=\"1\" type=\"tank\" parent=\"1\" key=\"1\">\n      <mxGeometry x=\"360\" y=\"88\" width=\"48\" height=\"32\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"8\" value=\"大EG3\" style=\"shape=hexagon;\" vertex=\"1\" type=\"bTube\" parent=\"1\" key=\"4\">\n      <mxGeometry x=\"130\" y=\"88\" width=\"48\" height=\"24\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"9\" value=\"L01-4\" style=\"shape=ellipse\" vertex=\"1\" type=\"sTube\" parent=\"1\" key=\"14\">\n      <mxGeometry x=\"246\" y=\"88\" width=\"48\" height=\"24\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"10\" value=\"\" edge=\"1\" parent=\"1\" source=\"2\" target=\"8\">\n      <mxGeometry relative=\"1\" as=\"geometry\">\n        <Array as=\"points\">\n          <mxPoint x=\"100\" y=\"100\"/>\n        </Array>\n      </mxGeometry>\n    </mxCell>\n    <mxCell id=\"11\" value=\"\" edge=\"1\" parent=\"1\" source=\"8\" target=\"9\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"12\" value=\"\" edge=\"1\" parent=\"1\" source=\"9\" target=\"7\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n  </root>\n</mxGraphModel>\n', '<div style=\"position: relative;\" oncontextmenu=\"return false;\" class=\"imgContentDivStyle\"><svg style=\"width: 100%; height: 100%; min-width: 403px; min-height: 124px;\"><g><g></g><g><g style=\"cursor: pointer;\"><rect style=\"cursor: pointer;\" stroke-width=\"1\" height=\"48\" width=\"48\" y=\"23\" x=\"13\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></rect></g><g style=\"cursor: pointer;\" text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"50.5\" x=\"37\" text-decoration=\"none\">5#泊位</text></g><g style=\"cursor: move;\"><path style=\"cursor: move;\" d=\"M 353 29 C 353 21 401 21 401 29 L 401 49 C 401 57 353 57 353 49 Z\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></path><path d=\"M 353 29 C 353 35 401 35 401 29 \" stroke-width=\"1\" fill=\"none\" stroke=\"#000000\"></path></g><g style=\"cursor: move;\" text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"42.5\" x=\"377\" text-decoration=\"none\">A区003号</text></g><g style=\"cursor: pointer;\"><path style=\"cursor: pointer;\" d=\"M 135 23 L 159 23 L 171 35 L 159 47 L 135 47 L 123 35 Z\" stroke-linejoin=\"round\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></path></g><g style=\"cursor: pointer;\" text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"38.5\" x=\"147\" text-decoration=\"none\">大EG4</text></g><g style=\"cursor: move;\"><path style=\"cursor: move;\" stroke-width=\"1\" d=\"M 61 35 L 77 35 C 92 35 92,35 108 35 L 115 35 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 121.882 35 L 112.882 39.5 L 115.132 35 L 112.882 30.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path style=\"cursor: move;\" stroke-width=\"9\" d=\"M 61 35 L 77 35 C 92 35 92,35 108 35 L 115 35 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g style=\"cursor: move;\"><path style=\"cursor: move;\" stroke-width=\"1\" d=\"M 171 35 L 242 35 C 262 35 262,35 282 35 L 345 35 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 351.882 35 L 342.882 39.5 L 345.132 35 L 342.882 30.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path style=\"cursor: move;\" stroke-width=\"9\" d=\"M 171 35 L 242 35 C 262 35 262,35 282 35 L 345 35 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g style=\"cursor: move;\"><path style=\"cursor: move;\" d=\"M 353 97 C 353 89 401 89 401 97 L 401 117 C 401 125 353 125 353 117 Z\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></path><path d=\"M 353 97 C 353 103 401 103 401 97 \" stroke-width=\"1\" fill=\"none\" stroke=\"#000000\"></path></g><g style=\"cursor: move;\" text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"110.5\" x=\"377\" text-decoration=\"none\">A区001号</text></g><g style=\"cursor: move;\"><path style=\"cursor: move;\" d=\"M 135 91 L 159 91 L 171 103 L 159 115 L 135 115 L 123 103 Z\" stroke-linejoin=\"round\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></path></g><g style=\"cursor: move;\" text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"106.5\" x=\"147\" text-decoration=\"none\">大EG3</text></g><g style=\"cursor: move;\"><ellipse style=\"cursor: move;\" ry=\"12\" rx=\"24\" cy=\"103\" cx=\"263\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></ellipse></g><g style=\"cursor: move;\" text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"106.5\" x=\"263\" text-decoration=\"none\">L01-4</text></g><g style=\"cursor: move;\"><path style=\"cursor: move;\" stroke-width=\"1\" d=\"M 37 71 L 37 87 C 37 103 37,103 57 103 L 115 103 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 121.882 103 L 112.882 107.5 L 115.132 103 L 112.882 98.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path style=\"cursor: move;\" stroke-width=\"9\" d=\"M 37 71 L 37 87 C 37 103 37,103 57 103 L 115 103 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g style=\"cursor: move;\"><path style=\"cursor: move;\" stroke-width=\"1\" d=\"M 171 103 L 188 103 C 205 103 205,103 222 103 L 231 103 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 237.882 103 L 228.882 107.5 L 231.132 103 L 228.882 98.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path style=\"cursor: move;\" stroke-width=\"9\" d=\"M 171 103 L 188 103 C 205 103 205,103 222 103 L 231 103 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g><path stroke-width=\"1\" d=\"M 287 103 L 304 103 C 320 103 320,103 337 103 L 345 103 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 351.882 103 L 342.882 107.5 L 345.132 103 L 342.882 98.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 287 103 L 304 103 C 320 103 320,103 337 103 L 345 103 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g></g><g></g></g></svg></div>', null, '8', null, null, null, null, null, null, '1421769600', '4', '1421769600', '4', '1421683200', null, null, null);
INSERT INTO `t_pcs_transport_program` VALUES ('8', '1', null, null, null, '8', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_transport_program` VALUES ('9', '2', '<mxGraphModel>\n  <root>\n    <mxCell id=\"0\"/>\n    <mxCell id=\"1\" parent=\"0\"/>\n    <mxCell id=\"2\" vertex=\"1\" parent=\"1\" type=\"berth\">\n      <mxGeometry x=\"20\" y=\"20\" width=\"48\" height=\"48\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"3\" value=\"B区001号\" style=\"shape=cylinder\" vertex=\"1\" type=\"tank\" parent=\"1\" key=\"5\">\n      <mxGeometry x=\"450\" y=\"10\" width=\"48\" height=\"32\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"4\" value=\"A区002号\" style=\"shape=cylinder\" vertex=\"1\" type=\"tank\" parent=\"1\" key=\"2\">\n      <mxGeometry x=\"460\" y=\"70\" width=\"48\" height=\"32\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"5\" value=\"A区003号\" style=\"shape=cylinder\" vertex=\"1\" type=\"tank\" parent=\"1\" key=\"3\">\n      <mxGeometry x=\"480\" y=\"140\" width=\"48\" height=\"32\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"6\" value=\"L02-2\" style=\"shape=ellipse\" vertex=\"1\" type=\"sTube\" parent=\"1\" key=\"16\">\n      <mxGeometry x=\"330\" y=\"40\" width=\"48\" height=\"24\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"7\" value=\"L02-1\" style=\"shape=ellipse\" vertex=\"1\" type=\"sTube\" parent=\"1\" key=\"15\">\n      <mxGeometry x=\"330\" y=\"100\" width=\"48\" height=\"24\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"8\" value=\"大EG5\" style=\"shape=hexagon;\" vertex=\"1\" type=\"bTube\" parent=\"1\" key=\"6\">\n      <mxGeometry x=\"180\" y=\"60\" width=\"48\" height=\"24\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"9\" value=\"\" edge=\"1\" parent=\"1\" source=\"2\" target=\"8\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"10\" value=\"\" edge=\"1\" parent=\"1\" source=\"8\" target=\"6\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"11\" value=\"\" edge=\"1\" parent=\"1\" source=\"8\" target=\"7\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"12\" value=\"\" edge=\"1\" parent=\"1\" source=\"6\" target=\"3\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"13\" value=\"\" edge=\"1\" parent=\"1\" source=\"7\" target=\"4\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"14\" value=\"L01-3\" style=\"shape=ellipse\" vertex=\"1\" type=\"sTube\" parent=\"1\" key=\"13\">\n      <mxGeometry x=\"340\" y=\"140\" width=\"48\" height=\"24\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"15\" value=\"\" edge=\"1\" parent=\"1\" source=\"8\" target=\"14\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"16\" value=\"\" edge=\"1\" parent=\"1\" source=\"14\" target=\"5\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n  </root>\n</mxGraphModel>\n', '<div style=\"position: relative;\" oncontextmenu=\"return false;\" class=\"imgContentDivStyle\"><svg style=\"width: 100%; height: 100%; min-width: 530px; min-height: 173px;\"><g><g></g><g><g><rect stroke-width=\"1\" height=\"48\" width=\"48\" y=\"20\" x=\"20\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></rect></g><g><path d=\"M 450 16 C 450 8 498 8 498 16 L 498 36 C 498 44 450 44 450 36 Z\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></path><path d=\"M 450 16 C 450 22 498 22 498 16 \" stroke-width=\"1\" fill=\"none\" stroke=\"#000000\"></path></g><g text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"29.5\" x=\"474\" text-decoration=\"none\">B区001号</text></g><g><path d=\"M 460 76 C 460 68 508 68 508 76 L 508 96 C 508 104 460 104 460 96 Z\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></path><path d=\"M 460 76 C 460 82 508 82 508 76 \" stroke-width=\"1\" fill=\"none\" stroke=\"#000000\"></path></g><g text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"89.5\" x=\"484\" text-decoration=\"none\">A区002号</text></g><g><path d=\"M 480 146 C 480 138 528 138 528 146 L 528 166 C 528 174 480 174 480 166 Z\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></path><path d=\"M 480 146 C 480 152 528 152 528 146 \" stroke-width=\"1\" fill=\"none\" stroke=\"#000000\"></path></g><g text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"159.5\" x=\"504\" text-decoration=\"none\">A区003号</text></g><g><ellipse ry=\"12\" rx=\"24\" cy=\"52\" cx=\"354\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></ellipse></g><g text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"55.5\" x=\"354\" text-decoration=\"none\">L02-2</text></g><g><ellipse ry=\"12\" rx=\"24\" cy=\"112\" cx=\"354\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></ellipse></g><g text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"115.5\" x=\"354\" text-decoration=\"none\">L02-1</text></g><g><path d=\"M 192 60 L 216 60 L 228 72 L 216 84 L 192 84 L 180 72 Z\" stroke-linejoin=\"round\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></path></g><g text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"75.5\" x=\"204\" text-decoration=\"none\">大EG5</text></g><g style=\"cursor: move;\"><path style=\"cursor: move;\" stroke-width=\"1\" d=\"M 68 64 L 104 64 C 124 64 124,64 144 64 L 172 64 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 178.882 64 L 169.882 68.5 L 172.132 64 L 169.882 59.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path style=\"cursor: move;\" stroke-width=\"9\" d=\"M 68 64 L 104 64 C 124 64 124,64 144 64 L 172 64 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g><path stroke-width=\"1\" d=\"M 228 62 L 259 62 C 279 62 279,62 299 62 L 322 62 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 328.882 62 L 319.882 66.5 L 322.132 62 L 319.882 57.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 228 62 L 259 62 C 279 62 279,62 299 62 L 322 62 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g><path stroke-width=\"1\" d=\"M 204 84 L 204 88 C 204 92 204,92 224 92 L 334 92 C 354 92 354,92 354 96 L 354 92 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 354 98.882 L 349.5 89.882 L 354 92.132 L 358.5 89.882 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 204 84 L 204 88 C 204 92 204,92 224 92 L 334 92 C 354 92 354,92 354 96 L 354 92 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g><path stroke-width=\"1\" d=\"M 378 41 L 396 41 C 414 41 414,41 432 41 L 442 41 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 448.882 41 L 439.882 45.5 L 442.132 41 L 439.882 36.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 378 41 L 396 41 C 414 41 414,41 432 41 L 442 41 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g><path stroke-width=\"1\" d=\"M 378 101 L 399 101 C 419 101 419,101 439 101 L 452 101 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 458.882 101 L 449.882 105.5 L 452.132 101 L 449.882 96.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 378 101 L 399 101 C 419 101 419,101 439 101 L 452 101 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g><ellipse ry=\"12\" rx=\"24\" cy=\"152\" cx=\"364\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></ellipse></g><g text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"155.5\" x=\"364\" text-decoration=\"none\">L01-3</text></g><g><path stroke-width=\"1\" d=\"M 204 84 L 204 98 C 204 112 204,112 224 112 L 344 112 C 364 112 364,112 364 126 L 364 132 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 364 138.882 L 359.5 129.882 L 364 132.132 L 368.5 129.882 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 204 84 L 204 98 C 204 112 204,112 224 112 L 344 112 C 364 112 364,112 364 126 L 364 132 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g><path stroke-width=\"1\" d=\"M 388 152 L 414 152 C 434 152 434,152 454 152 L 472 152 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 478.882 152 L 469.882 156.5 L 472.132 152 L 469.882 147.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 388 152 L 414 152 C 434 152 434,152 454 152 L 472 152 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g></g><g><g style=\"cursor: move;\" pointer-events=\"none\"><rect stroke-dasharray=\"3 3\" stroke-width=\"1\" height=\"32\" width=\"48\" y=\"140\" x=\"480\" shape-rendering=\"crispEdges\" fill=\"none\" stroke=\"#00FF00\"></rect></g><g style=\"cursor: nw-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"137\" x=\"477\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g><g style=\"cursor: n-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"137\" x=\"501\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g><g style=\"cursor: ne-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"137\" x=\"525\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g><g style=\"cursor: w-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"153\" x=\"477\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g><g style=\"cursor: e-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"153\" x=\"525\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g><g style=\"cursor: sw-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"169\" x=\"477\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g><g style=\"cursor: s-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"169\" x=\"501\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g><g style=\"cursor: se-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"169\" x=\"525\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g></g></g></svg></div>', null, '5', '', '', '水电费发水电费', '地方', '空空的了', null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_transport_program` VALUES ('10', '0', null, null, null, '11', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_transport_program` VALUES ('11', '1', null, null, null, '11', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_transport_program` VALUES ('12', '0', null, null, null, '12', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_transport_program` VALUES ('13', '1', null, null, null, '12', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_transport_program` VALUES ('14', '2', '<mxGraphModel>\n  <root>\n    <mxCell id=\"0\"/>\n    <mxCell id=\"1\" parent=\"0\"/>\n    <mxCell id=\"2\" vertex=\"1\" parent=\"1\" type=\"berth\">\n      <mxGeometry x=\"20\" y=\"20\" width=\"48\" height=\"48\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"3\" value=\"B区001号\" style=\"shape=cylinder\" vertex=\"1\" type=\"tank\" parent=\"1\" key=\"5\">\n      <mxGeometry x=\"460\" y=\"20\" width=\"48\" height=\"32\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"4\" value=\"A区004号\" style=\"shape=cylinder\" vertex=\"1\" type=\"tank\" parent=\"1\" key=\"4\">\n      <mxGeometry x=\"470\" y=\"80\" width=\"48\" height=\"32\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"5\" value=\"A区003号\" style=\"shape=cylinder\" vertex=\"1\" type=\"tank\" parent=\"1\" key=\"3\">\n      <mxGeometry x=\"470\" y=\"130\" width=\"48\" height=\"32\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"6\" value=\"L02-1\" style=\"shape=ellipse\" vertex=\"1\" type=\"sTube\" parent=\"1\" key=\"15\">\n      <mxGeometry x=\"290\" y=\"30\" width=\"48\" height=\"24\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"7\" value=\"L03-1\" style=\"shape=ellipse\" vertex=\"1\" type=\"sTube\" parent=\"1\" key=\"17\">\n      <mxGeometry x=\"300\" y=\"80\" width=\"48\" height=\"24\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"8\" value=\"L02-2\" style=\"shape=ellipse\" vertex=\"1\" type=\"sTube\" parent=\"1\" key=\"16\">\n      <mxGeometry x=\"300\" y=\"130\" width=\"48\" height=\"24\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"9\" value=\"大EG2\" style=\"shape=hexagon;\" vertex=\"1\" type=\"bTube\" parent=\"1\" key=\"3\">\n      <mxGeometry x=\"130\" y=\"56\" width=\"48\" height=\"24\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"10\" value=\"\" edge=\"1\" parent=\"1\" source=\"2\" target=\"9\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"11\" value=\"\" edge=\"1\" parent=\"1\" source=\"9\" target=\"6\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"12\" value=\"\" edge=\"1\" parent=\"1\" source=\"7\" target=\"4\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"13\" value=\"\" edge=\"1\" parent=\"1\" source=\"6\" target=\"3\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"14\" value=\"\" edge=\"1\" parent=\"1\" source=\"8\" target=\"5\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"15\" value=\"\" edge=\"1\" parent=\"1\" source=\"9\" target=\"7\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n    <mxCell id=\"16\" value=\"\" edge=\"1\" parent=\"1\" source=\"9\" target=\"8\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n    </mxCell>\n  </root>\n</mxGraphModel>\n', '<div style=\"position: relative;\" oncontextmenu=\"return false;\" class=\"imgContentDivStyle\"><svg style=\"width: 100%; height: 100%; min-width: 520px; min-height: 163px;\"><g><g></g><g><g style=\"cursor: move;\"><rect style=\"cursor: move;\" stroke-width=\"1\" height=\"48\" width=\"48\" y=\"20\" x=\"20\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></rect></g><g><path d=\"M 460 26 C 460 18 508 18 508 26 L 508 46 C 508 54 460 54 460 46 Z\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></path><path d=\"M 460 26 C 460 32 508 32 508 26 \" stroke-width=\"1\" fill=\"none\" stroke=\"#000000\"></path></g><g text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"39.5\" x=\"484\" text-decoration=\"none\">B区001号</text></g><g><path d=\"M 470 86 C 470 78 518 78 518 86 L 518 106 C 518 114 470 114 470 106 Z\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></path><path d=\"M 470 86 C 470 92 518 92 518 86 \" stroke-width=\"1\" fill=\"none\" stroke=\"#000000\"></path></g><g text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"99.5\" x=\"494\" text-decoration=\"none\">A区004号</text></g><g><path d=\"M 470 136 C 470 128 518 128 518 136 L 518 156 C 518 164 470 164 470 156 Z\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></path><path d=\"M 470 136 C 470 142 518 142 518 136 \" stroke-width=\"1\" fill=\"none\" stroke=\"#000000\"></path></g><g text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"149.5\" x=\"494\" text-decoration=\"none\">A区003号</text></g><g><ellipse ry=\"12\" rx=\"24\" cy=\"42\" cx=\"314\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></ellipse></g><g text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"45.5\" x=\"314\" text-decoration=\"none\">L02-1</text></g><g style=\"cursor: move;\"><ellipse style=\"cursor: move;\" ry=\"12\" rx=\"24\" cy=\"92\" cx=\"324\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></ellipse></g><g style=\"cursor: move;\" text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"95.5\" x=\"324\" text-decoration=\"none\">L03-1</text></g><g><ellipse ry=\"12\" rx=\"24\" cy=\"142\" cx=\"324\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></ellipse></g><g text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"145.5\" x=\"324\" text-decoration=\"none\">L02-2</text></g><g><path d=\"M 142 56 L 166 56 L 178 68 L 166 80 L 142 80 L 130 68 Z\" stroke-linejoin=\"round\" stroke-width=\"1\" pointer-events=\"all\" fill=\"#FFFFFF\" stroke=\"#000000\"></path></g><g text-anchor=\"middle\" fill=\"#000000\" font-size=\"11px\" font-weight=\"bold\" font-family=\"Arial,Helvetica\" text-decoration=\"none\"><text style=\"pointer-events: all\" y=\"71.5\" x=\"154\" text-decoration=\"none\">大EG2</text></g><g style=\"cursor: move;\"><path style=\"cursor: move;\" stroke-width=\"1\" d=\"M 68 62 L 84 62 C 99 62 99,62 115 62 L 122 62 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 128.882 62 L 119.882 66.5 L 122.132 62 L 119.882 57.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path style=\"cursor: move;\" stroke-width=\"9\" d=\"M 68 62 L 84 62 C 99 62 99,62 115 62 L 122 62 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g><path stroke-width=\"1\" d=\"M 154 56 L 154 56 C 154 55 154,55 174 55 L 294 55 C 314 55 314,55 314 55 L 314 62 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 314 55.118 L 318.5 64.118 L 314 61.868 L 309.5 64.118 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 154 56 L 154 56 C 154 55 154,55 174 55 L 294 55 C 314 55 314,55 314 55 L 314 62 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g><path stroke-width=\"1\" d=\"M 348 92 L 389 92 C 409 92 409,92 429 92 L 462 92 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 468.882 92 L 459.882 96.5 L 462.132 92 L 459.882 87.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 348 92 L 389 92 C 409 92 409,92 429 92 L 462 92 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g style=\"cursor: move;\"><path style=\"cursor: move;\" stroke-width=\"1\" d=\"M 338 41 L 379 41 C 399 41 399,41 419 41 L 452 41 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 458.882 41 L 449.882 45.5 L 452.132 41 L 449.882 36.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path style=\"cursor: move;\" stroke-width=\"9\" d=\"M 338 41 L 379 41 C 399 41 399,41 419 41 L 452 41 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g><path stroke-width=\"1\" d=\"M 348 142 L 389 142 C 409 142 409,142 429 142 L 462 142 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 468.882 142 L 459.882 146.5 L 462.132 142 L 459.882 137.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 348 142 L 389 142 C 409 142 409,142 429 142 L 462 142 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g><path stroke-width=\"1\" d=\"M 178 68 L 219 68 C 239 68 239,68 239 80 L 239 80 C 239 92 239,92 259 92 L 292 92 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 298.882 92 L 289.882 96.5 L 292.132 92 L 289.882 87.5 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path stroke-width=\"9\" d=\"M 178 68 L 219 68 C 239 68 239,68 239 80 L 239 80 C 239 92 239,92 259 92 L 292 92 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g><g style=\"cursor: move;\"><path style=\"cursor: move;\" stroke-width=\"1\" d=\"M 154 80 L 154 93 C 154 105 154,105 174 105 L 304 105 C 324 105 324,105 324 118 L 324 122 \" pointer-events=\"visibleStroke\" fill=\"none\" stroke=\"#000000\"></path><path stroke-width=\"1\" d=\"M 324 128.882 L 319.5 119.882 L 324 122.132 L 328.5 119.882 z\" fill-opacity=\"1\" fill=\"#000000\" stroke-opacity=\"1\" stroke=\"#000000\"></path><path style=\"cursor: move;\" stroke-width=\"9\" d=\"M 154 80 L 154 93 C 154 105 154,105 174 105 L 304 105 C 324 105 324,105 324 118 L 324 122 \" stroke=\"white\" visibility=\"hidden\" fill=\"none\" pointer-events=\"stroke\"></path></g></g><g><g style=\"cursor: move;\" pointer-events=\"none\"><rect stroke-dasharray=\"3 3\" stroke-width=\"1\" height=\"32\" width=\"48\" y=\"130\" x=\"470\" shape-rendering=\"crispEdges\" fill=\"none\" stroke=\"#00FF00\"></rect></g><g style=\"cursor: nw-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"127\" x=\"467\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g><g style=\"cursor: n-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"127\" x=\"491\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g><g style=\"cursor: ne-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"127\" x=\"515\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g><g style=\"cursor: w-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"143\" x=\"467\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g><g style=\"cursor: e-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"143\" x=\"515\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g><g style=\"cursor: sw-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"159\" x=\"467\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g><g style=\"cursor: s-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"159\" x=\"491\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g><g style=\"cursor: se-resize;\"><rect stroke-width=\"1\" height=\"7\" width=\"7\" y=\"159\" x=\"515\" shape-rendering=\"crispEdges\" fill=\"#00FF00\" stroke=\"black\"></rect></g></g></g></svg></div>', null, '13', '', '', '船名:East Star II  货品名:乙二醇 到港时间:2015-01-15', '', '', null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for t_pcs_truck
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_truck`;
CREATE TABLE `t_pcs_truck` (
  `id` int(15) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(15) DEFAULT NULL,
  `name` varchar(60) DEFAULT NULL,
  `type` int(7) unsigned DEFAULT NULL,
  `loadCapacity` varchar(15) DEFAULT NULL,
  `company` varchar(60) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` int(7) unsigned DEFAULT NULL,
  `editUserId` int(20) unsigned DEFAULT NULL,
  `editTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `createTime` datetime DEFAULT NULL,
  `createUserId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_truck
-- ----------------------------
INSERT INTO `t_pcs_truck` VALUES ('1', '浙A-00321', '浙A-00321', null, '200', '东方货运公司', '222', null, null, '2015-01-20 16:58:33', null, null);
INSERT INTO `t_pcs_truck` VALUES ('2', '浙A-30256', '浙A-30256', null, '80', '东方货运公司', '111', null, null, '2015-01-20 16:58:35', null, null);
INSERT INTO `t_pcs_truck` VALUES ('3', '苏C-72341', '苏C-72341', null, '80', '东方货运公司', '333', null, null, '2015-01-20 16:58:36', null, null);
INSERT INTO `t_pcs_truck` VALUES ('4', '苏C-55311', '苏C-55311', null, '80', '东方货运公司', '222', null, null, '2015-01-20 16:58:38', null, null);
INSERT INTO `t_pcs_truck` VALUES ('5', '申H-21325', '申H-21325', null, '200', '申通快递', '1222222', null, null, '2015-01-20 16:58:55', null, null);
INSERT INTO `t_pcs_truck` VALUES ('6', '苏B-12324', '苏B-12324', null, '80', '申通快递', 'dwf', null, null, '2015-01-20 16:58:57', null, null);
INSERT INTO `t_pcs_truck` VALUES ('7', '苏A-54211', '苏A-54211', null, '80', '申通快递', 'dddd', null, null, '2015-01-20 16:59:23', null, null);

-- ----------------------------
-- Table structure for t_pcs_truck_trans
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_truck_trans`;
CREATE TABLE `t_pcs_truck_trans` (
  `truckId` int(20) DEFAULT NULL,
  `createTime` bigint(20) DEFAULT NULL,
  `createUserId` int(20) DEFAULT NULL,
  `motorcadeId` int(20) DEFAULT NULL,
  `approveAmount` varchar(15) DEFAULT NULL,
  `actualAmount` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_truck_trans
-- ----------------------------

-- ----------------------------
-- Table structure for t_pcs_tube
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_tube`;
CREATE TABLE `t_pcs_tube` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `type` int(7) unsigned DEFAULT NULL,
  `name` varchar(15) DEFAULT NULL,
  `productId` int(10) unsigned DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `editUserId` int(20) unsigned DEFAULT NULL,
  `editTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_tube
-- ----------------------------
INSERT INTO `t_pcs_tube` VALUES ('1', '0', '大EG0', '1', null, null, '2015-01-13 17:06:21');
INSERT INTO `t_pcs_tube` VALUES ('2', '0', '大EG1', '1', '已清洗', null, '2015-01-20 11:49:00');
INSERT INTO `t_pcs_tube` VALUES ('3', '0', '大EG2', '1', null, null, '2015-01-13 17:06:33');
INSERT INTO `t_pcs_tube` VALUES ('4', '0', '大EG3', '1', '已清洗', null, '2015-01-20 19:28:54');
INSERT INTO `t_pcs_tube` VALUES ('5', '0', '大EG4', '2', '正常', null, '2015-01-20 19:28:44');
INSERT INTO `t_pcs_tube` VALUES ('6', '0', '大EG5', '2', null, null, '2015-01-13 17:06:45');
INSERT INTO `t_pcs_tube` VALUES ('7', '0', '大EG6', '3', null, null, '2015-01-13 17:06:49');
INSERT INTO `t_pcs_tube` VALUES ('8', '0', '大EG7', '3', null, null, '2015-01-13 17:06:52');
INSERT INTO `t_pcs_tube` VALUES ('9', '0', '大EG8', '4', null, null, '2015-01-13 17:06:58');
INSERT INTO `t_pcs_tube` VALUES ('10', '0', '大EG9', '4', null, null, '2015-01-13 17:07:01');
INSERT INTO `t_pcs_tube` VALUES ('11', '1', 'L01-1', '1', null, null, '2015-01-13 17:07:05');
INSERT INTO `t_pcs_tube` VALUES ('12', '1', 'L01-2', '1', '已清洗', null, '2015-01-20 11:49:07');
INSERT INTO `t_pcs_tube` VALUES ('13', '1', 'L01-3', '1', null, null, '2015-01-13 17:07:10');
INSERT INTO `t_pcs_tube` VALUES ('14', '1', 'L01-4', '1', '已清洗', null, '2015-01-20 19:28:58');
INSERT INTO `t_pcs_tube` VALUES ('15', '1', 'L02-1', '2', null, null, '2015-01-13 17:07:17');
INSERT INTO `t_pcs_tube` VALUES ('16', '1', 'L02-2', '2', null, null, '2015-01-13 17:07:19');
INSERT INTO `t_pcs_tube` VALUES ('17', '1', 'L03-1', '3', null, null, '2015-01-13 17:07:22');
INSERT INTO `t_pcs_tube` VALUES ('18', '1', 'L04-1', '4', null, null, '2015-01-13 17:07:25');
INSERT INTO `t_pcs_tube` VALUES ('19', '1', 'L05-1', null, null, null, '2015-01-13 17:07:29');
INSERT INTO `t_pcs_tube` VALUES ('20', '1', 'L05-2', null, null, null, '2015-01-13 17:07:32');
INSERT INTO `t_pcs_tube` VALUES ('21', '1', 'L06-1', null, null, null, '2015-01-13 17:07:34');
INSERT INTO `t_pcs_tube` VALUES ('22', '1', 'L06-2', null, null, null, '2015-01-13 17:07:37');
INSERT INTO `t_pcs_tube` VALUES ('23', '1', 'L06-3', null, null, null, '2015-01-13 17:07:39');
INSERT INTO `t_pcs_tube` VALUES ('24', '1', 'L06-4', null, null, null, '2015-01-13 17:07:42');

-- ----------------------------
-- Table structure for t_pcs_vehicle_plan
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_vehicle_plan`;
CREATE TABLE `t_pcs_vehicle_plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `trainId` int(11) DEFAULT NULL,
  `deliverNum` double DEFAULT NULL,
  `ladingId` int(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_vehicle_plan
-- ----------------------------
INSERT INTO `t_pcs_vehicle_plan` VALUES ('88', '7', '34', '1');
INSERT INTO `t_pcs_vehicle_plan` VALUES ('89', '7', '23', '1');

-- ----------------------------
-- Table structure for t_pcs_weather
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_weather`;
CREATE TABLE `t_pcs_weather` (
  `key` int(7) unsigned NOT NULL,
  `value` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_weather
-- ----------------------------
INSERT INTO `t_pcs_weather` VALUES ('0', '雾');
INSERT INTO `t_pcs_weather` VALUES ('1', '晴');
INSERT INTO `t_pcs_weather` VALUES ('2', '多云');
INSERT INTO `t_pcs_weather` VALUES ('3', '小雨');
INSERT INTO `t_pcs_weather` VALUES ('4', '中雨');
INSERT INTO `t_pcs_weather` VALUES ('6', '小雪');
INSERT INTO `t_pcs_weather` VALUES ('7', '中雪');
INSERT INTO `t_pcs_weather` VALUES ('8', '大雪');
INSERT INTO `t_pcs_weather` VALUES ('9', '大雾');
INSERT INTO `t_pcs_weather` VALUES ('10', '阵雨');
INSERT INTO `t_pcs_weather` VALUES ('11', '雷阵雨');
INSERT INTO `t_pcs_weather` VALUES ('12', '雨加雪');

-- ----------------------------
-- Table structure for t_pcs_weigh
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_weigh`;
CREATE TABLE `t_pcs_weigh` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `plateId` int(11) DEFAULT NULL,
  `roughWeight` varchar(15) DEFAULT NULL,
  `tare` varchar(15) DEFAULT NULL,
  `netWeight` varchar(15) DEFAULT NULL,
  `tankId` int(11) DEFAULT NULL,
  `parkId` int(11) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `trainId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_weigh
-- ----------------------------
INSERT INTO `t_pcs_weigh` VALUES ('1', '6', '35', '1', '34', '1', '1', null, '7');

-- ----------------------------
-- Table structure for t_pcs_wind_direction
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_wind_direction`;
CREATE TABLE `t_pcs_wind_direction` (
  `key` int(7) unsigned NOT NULL,
  `value` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_wind_direction
-- ----------------------------
INSERT INTO `t_pcs_wind_direction` VALUES ('1', '东');
INSERT INTO `t_pcs_wind_direction` VALUES ('2', '东南');
INSERT INTO `t_pcs_wind_direction` VALUES ('3', '南');
INSERT INTO `t_pcs_wind_direction` VALUES ('4', '西南');
INSERT INTO `t_pcs_wind_direction` VALUES ('5', '西');
INSERT INTO `t_pcs_wind_direction` VALUES ('6', '西北');
INSERT INTO `t_pcs_wind_direction` VALUES ('7', '北');
INSERT INTO `t_pcs_wind_direction` VALUES ('8', '东北');

-- ----------------------------
-- Table structure for t_pcs_wind_power
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_wind_power`;
CREATE TABLE `t_pcs_wind_power` (
  `key` int(7) unsigned NOT NULL,
  `value` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_wind_power
-- ----------------------------
INSERT INTO `t_pcs_wind_power` VALUES ('1', '1级');
INSERT INTO `t_pcs_wind_power` VALUES ('2', '2级');
INSERT INTO `t_pcs_wind_power` VALUES ('3', '3级');
INSERT INTO `t_pcs_wind_power` VALUES ('4', '4级');
INSERT INTO `t_pcs_wind_power` VALUES ('5', '5级');
INSERT INTO `t_pcs_wind_power` VALUES ('6', '6级');
INSERT INTO `t_pcs_wind_power` VALUES ('7', '7级');
INSERT INTO `t_pcs_wind_power` VALUES ('8', '8级');
INSERT INTO `t_pcs_wind_power` VALUES ('9', '9级');
INSERT INTO `t_pcs_wind_power` VALUES ('10', '10级');
INSERT INTO `t_pcs_wind_power` VALUES ('11', '11级');
INSERT INTO `t_pcs_wind_power` VALUES ('12', '12级');
INSERT INTO `t_pcs_wind_power` VALUES ('13', '13级');
INSERT INTO `t_pcs_wind_power` VALUES ('14', '14级');
INSERT INTO `t_pcs_wind_power` VALUES ('15', '15级');
INSERT INTO `t_pcs_wind_power` VALUES ('16', '16级');

-- ----------------------------
-- Table structure for t_pcs_work
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_work`;
CREATE TABLE `t_pcs_work` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `checkTime` bigint(20) DEFAULT NULL,
  `arrivalTime` bigint(20) DEFAULT NULL,
  `openPump` bigint(20) DEFAULT NULL,
  `stopPump` bigint(20) DEFAULT NULL,
  `stayTime` bigint(20) DEFAULT NULL,
  `leaveTime` bigint(20) DEFAULT NULL,
  `tearPipeTime` bigint(20) DEFAULT NULL,
  `arrivalId` int(20) DEFAULT NULL COMMENT '到港ID，主键，自增\r\n            ',
  `workTime` bigint(20) DEFAULT NULL,
  `measureAmount` varchar(15) DEFAULT NULL,
  `tankAmount` varchar(15) DEFAULT NULL,
  `differAmount` varchar(15) DEFAULT NULL,
  `dockCheck` text,
  `evaluate` text,
  `evaluateUserId` int(20) DEFAULT NULL,
  `evaluateTime` bigint(20) DEFAULT NULL,
  `dynamicUserId` int(20) DEFAULT NULL,
  `dockUserId` int(20) DEFAULT NULL,
  `shipClientId` int(20) DEFAULT NULL,
  `unusualLog` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_work
-- ----------------------------
INSERT INTO `t_pcs_work` VALUES ('13', null, null, '1421851840', '1421837740', null, null, null, '5', null, null, null, null, null, '4234', '23', null, null, null, null, null);
INSERT INTO `t_pcs_work` VALUES ('14', null, null, null, null, null, null, null, '11', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_work` VALUES ('15', null, null, null, null, null, null, null, '12', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_pcs_work` VALUES ('16', null, null, '1421909426', '1422355826', null, null, null, '13', null, null, null, null, null, 'fsdfsdf', '24', null, null, null, null, null);

-- ----------------------------
-- Table structure for t_pcs_work_check
-- ----------------------------
DROP TABLE IF EXISTS `t_pcs_work_check`;
CREATE TABLE `t_pcs_work_check` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `checkType` int(7) DEFAULT NULL,
  `checkUserId` int(20) DEFAULT NULL,
  `content` text,
  `checkTime` bigint(20) DEFAULT NULL,
  `transportId` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pcs_work_check
-- ----------------------------
INSERT INTO `t_pcs_work_check` VALUES ('5', '21', '26', '士大夫似的', null, '9');
INSERT INTO `t_pcs_work_check` VALUES ('6', '22', '24', '放水电费', null, '9');
INSERT INTO `t_pcs_work_check` VALUES ('7', '1', null, '大EG1--->L01-1--->A002', null, '5');
INSERT INTO `t_pcs_work_check` VALUES ('8', '1', null, '大EG1--->L01-2--->A001', null, '5');
INSERT INTO `t_pcs_work_check` VALUES ('9', '21', '24', '3453sdf', null, '14');
INSERT INTO `t_pcs_work_check` VALUES ('10', '22', '23', 'sd', null, '14');

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(30) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `companyName` varchar(60) DEFAULT NULL,
  `position` varchar(30) DEFAULT NULL,
  `companyAddress` varchar(255) DEFAULT NULL,
  `department` varchar(30) DEFAULT NULL,
  `mobile` varchar(30) DEFAULT NULL,
  `password` varchar(30) DEFAULT NULL,
  `status` int(7) unsigned zerofill DEFAULT NULL,
  `realName` varchar(30) DEFAULT NULL,
  `sex` int(3) unsigned DEFAULT NULL,
  `idCard` varchar(30) DEFAULT NULL,
  `birthday` timestamp NULL DEFAULT NULL,
  `education` varchar(30) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `verifyCode` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_user
-- ----------------------------
INSERT INTO `t_sys_user` VALUES ('1', 'admin', 'admin@live.com', null, null, null, null, null, 'skycloud', null, '测试人员', null, null, null, null, null, '2014-11-30 10:12:37', null);

-- ----------------------------
-- Table structure for t_test
-- ----------------------------
DROP TABLE IF EXISTS `t_test`;
CREATE TABLE `t_test` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(16) DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT NULL,
  `editTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_test
-- ----------------------------
INSERT INTO `t_test` VALUES ('5', '0001', '0000-00-00 00:00:00', '2014-11-30 11:33:10');
INSERT INTO `t_test` VALUES ('6', '0002', '2014-11-30 11:34:44', '0000-00-00 00:00:00');
INSERT INTO `t_test` VALUES ('7', '0013', '2014-11-30 11:55:22', '2014-11-30 11:57:09');
INSERT INTO `t_test` VALUES ('8', '0021', '2014-11-30 12:05:59', '2014-11-30 12:07:35');
INSERT INTO `t_test` VALUES ('9', '0031', '2014-11-30 12:07:46', '2014-11-30 12:07:46');
INSERT INTO `t_test` VALUES ('10', '0032', '2014-11-30 12:28:10', '2014-11-30 12:30:19');
INSERT INTO `t_test` VALUES ('11', '0042', '2014-11-30 12:32:22', '2014-11-30 12:32:58');
INSERT INTO `t_test` VALUES ('12', '0042', '2014-12-02 20:35:40', '2014-11-30 12:32:58');
INSERT INTO `t_test` VALUES ('13', '0051', '2014-12-02 20:48:50', '2014-12-04 17:36:16');
INSERT INTO `t_test` VALUES ('14', 'dd', '2014-12-25 17:30:57', null);
INSERT INTO `t_test` VALUES ('15', 'sss', '2014-12-25 17:38:10', null);

-- ----------------------------
-- Table structure for t_test2
-- ----------------------------
DROP TABLE IF EXISTS `t_test2`;
CREATE TABLE `t_test2` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(16) DEFAULT 'sssss',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_test2
-- ----------------------------
INSERT INTO `t_test2` VALUES ('1', 'a');
INSERT INTO `t_test2` VALUES ('2', 'b');
INSERT INTO `t_test2` VALUES ('3', '15');
INSERT INTO `t_test2` VALUES ('5', 'sssss');
INSERT INTO `t_test2` VALUES ('6', 'sssss');

-- ----------------------------
-- Procedure structure for createChildLst
-- ----------------------------
DROP PROCEDURE IF EXISTS `createChildLst`;
DELIMITER ;;
CREATE DEFINER=`sop`@`%` PROCEDURE `createChildLst`(IN rootId INT,IN nDepth INT)
BEGIN
	      DECLARE done INT DEFAULT 0;
	      DECLARE b INT;
	      DECLARE cur1 CURSOR FOR SELECT id FROM t_pcs_goods WHERE sourceGoodsId=rootId;
	      DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
	      SET max_sp_recursion_depth=12;
	      INSERT INTO tmpLst VALUES (NULL,rootId,nDepth);
	    
	      OPEN cur1;
	    
	      FETCH cur1 INTO b;
	      WHILE done=0 DO
		      CALL createChildLst(b,nDepth+1);
		      FETCH cur1 INTO b;
	      END WHILE;
	    
	      CLOSE cur1;
    END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for showChildLst
-- ----------------------------
DROP PROCEDURE IF EXISTS `showChildLst`;
DELIMITER ;;
CREATE DEFINER=`sop`@`%` PROCEDURE `showChildLst`(IN p_id INT)
BEGIN
	
	DECLARE rootid INT;
		CREATE TEMPORARY TABLE IF NOT EXISTS tmpLst 
	       (sno INT PRIMARY KEY AUTO_INCREMENT,id INT,depth INT);
	      DELETE FROM tmpLst;
		SELECT rootGoodsId INTO rootid FROM t_pcs_goods WHERE id = p_id ;
	      CALL createChildLst(rootid,1);
	    
	      SELECT g.id,g.sourceGoodsId,g.code,c.name,(SELECT l.code FROM t_pcs_lading l WHERE l.id=gr.ladingId) ladingCode,
	      (SELECT CASE WHEN l.type='1' THEN '转卖提单' ELSE '提货提单' END FROM t_pcs_lading l WHERE l.id=gr.ladingId) ladingType,
	      g.goodsTotal,g.goodsIn,g.goodsOut,g.goodsCurrent,tmpLst.depth lever
		FROM tmpLst,
			t_pcs_goods g LEFT JOIN t_pcs_client c ON g.clientId = c.id 
			LEFT JOIN t_pcs_goods_group gr ON g.goodsGroupId = gr.id WHERE tmpLst.id=g.id 
			ORDER BY tmpLst.sno;
		
    END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for func_splitString
-- ----------------------------
DROP FUNCTION IF EXISTS `func_splitString`;
DELIMITER ;;
CREATE DEFINER=`sop`@`%` FUNCTION `func_splitString`( f_string varchar(1000),f_delimiter varchar(5),f_order int) RETURNS varchar(255) CHARSET utf8
BEGIN
declare result varchar(255) default '';
set result = reverse(substring_index(reverse(substring_index(f_string,f_delimiter,f_order)),f_delimiter,1));
return result;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for getClientName
-- ----------------------------
DROP FUNCTION IF EXISTS `getClientName`;
DELIMITER ;;
CREATE DEFINER=`sop`@`%` FUNCTION `getClientName`(l_id VARCHAR(20)) RETURNS varchar(255) CHARSET utf8
BEGIN
	DECLARE temp VARCHAR(255) DEFAULT '';
	DECLARE name1 VARCHAR(255) DEFAULT '';
	DECLARE done INT; 
	DECLARE	mycursor CURSOR FOR SELECT NAME FROM t_pcs_client WHERE id IN (SELECT clientId FROM t_pcs_out_arrival_plan WHERE arrivalId =l_id) ;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
	OPEN mycursor ;
	REPEAT
		FETCH mycursor INTO temp;
		SET name1 := CONCAT(temp,',',name1);
	UNTIL done END REPEAT;
	CLOSE mycursor ;
	RETURN name1;
    END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `insert_pcs_bill`;
DELIMITER ;;
CREATE TRIGGER `insert_pcs_bill` BEFORE INSERT ON `t_pcs_arrival_bill` FOR EACH ROW begin
    declare billid int;
    SELECT AUTO_INCREMENT into billid FROM INFORMATION_SCHEMA.TABLES    
WHERE TABLE_NAME='t_pcs_arrival_bill';
    
    INSERT INTO t_pcs_arrival_charge(billId,TYPE) VALUES(billid,4) ;
    INSERT INTO t_pcs_arrival_charge(billId,TYPE) VALUES(billid,5) ;
    INSERT INTO t_pcs_arrival_charge(billId,TYPE) VALUES(billid,6) ;
    INSERT INTO t_pcs_arrival_charge(billId,TYPE) VALUES(billid,7) ;
    INSERT INTO t_pcs_arrival_charge(billId,TYPE) VALUES(billid,8) ;
    set new.createTime = now();
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `insert_pcs_arrival_plan`;
DELIMITER ;;
CREATE TRIGGER `insert_pcs_arrival_plan` BEFORE INSERT ON `t_pcs_arrival_plan` FOR EACH ROW begin
    set new.createTime = now();
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `insert_pcs_contract`;
DELIMITER ;;
CREATE TRIGGER `insert_pcs_contract` BEFORE INSERT ON `t_pcs_contract` FOR EACH ROW begin
    set new.createTime = now();
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `insert_pcs_intention`;
DELIMITER ;;
CREATE TRIGGER `insert_pcs_intention` BEFORE INSERT ON `t_pcs_intention` FOR EACH ROW begin
    set new.createTime = now();
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `deleteDeleveryLoading`;
DELIMITER ;;
CREATE TRIGGER `deleteDeleveryLoading` AFTER DELETE ON `t_pcs_invoice` FOR EACH ROW BEGIN
	delete from t_pcs_delivery_loading where loadingId=old.ladingId and deliveryId=old.id ;
    END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `insert_pcs_truck`;
DELIMITER ;;
CREATE TRIGGER `insert_pcs_truck` BEFORE INSERT ON `t_pcs_truck` FOR EACH ROW begin
    set new.createTime = now();
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `create_triger`;
DELIMITER ;;
CREATE TRIGGER `create_triger` BEFORE INSERT ON `t_test` FOR EACH ROW begin
    set new.createTime = now();
end
;;
DELIMITER ;
