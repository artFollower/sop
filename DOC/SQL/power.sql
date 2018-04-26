SET FOREIGN_KEY_CHECKS = 0;
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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_auth_security_resources
-- ----------------------------
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '系统管理菜单项', '系统管理', 'MSYSCONFIG', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '用户管理', 'MUSER', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '角色管理', 'MROLE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '权限管理', 'MPERMISSION', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '添加用户的操作权限', '添加用户', 'userAdd', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '修改用户', 'userUpdate', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '删除用户', 'userDelete', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '添加角色', 'roleAdd', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '修改角色', 'roleUpdate', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '删除角色', 'roleDelete', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '添加权限', 'resourceAdd', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '修改权限', 'resourceUpdate', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '删除权限', 'resourceDelete', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '合同管理', 'MCONTRACT', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '意向管理', 'MINTENT', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '入库管理', 'MINBOUND', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '到港计划', 'MARRIVALPLAN', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '入库作业', 'MINBOUNDWORK', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '调度日志', 'MDISPATCHLOG', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '储罐台账', 'MSTORELEFGER', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '入库确认', 'MINSTORE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '出库管理', 'MOUTBOUND', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '提单管理', 'MLADING', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '出港计划', 'MOUTBOUNDPLAN', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '船舶出库', 'MSHIPDELIVERY', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '船发台账', 'MSHIPLEDGER', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '车发出库', 'MCARDELIVERY', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '组织架构', 'MORGANIZATION', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '港务信息', 'MHARBOR', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '消息管理', 'MMESSAGE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '修改入库信息', '修改入库信息', 'AINBOUNDUPDATE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '添加意向', '添加意向', 'AINTENTADD', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '修改意向', '修改意向', 'AINTENTUPDATE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '删除意向', '删除意向', 'AINTENTDELETE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '意向审核', '意向审核', 'AINTENTVERIFY', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '转为合同', '转为合同', 'ACHANGETOCONTRACT', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '添加合同', '添加合同', 'ACONTRACTADD', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '修改合同', '修改合同', 'ACONTRACTUPDATE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '删除合同', '删除合同', 'ACONTRACTDELETE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '合同变更', '合同变更', 'ACHANGECONTRACT', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '入库作业', 'MINBOUNDWORK', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '合同审核', '合同审核', 'ACONTRACTVERIFY', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '作业计划', 'MINBOUNDWORKPLAN', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '添加入港计划', '添加入港计划', 'AARRIVALPLANADD', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '靠泊方案', 'MINBOUNDBERTHING', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '修改入港计划', '修改入港计划', 'AARRIVALPLANUPDATE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '接卸方案', 'MUNLOADINGPROGRAM', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '删除入港计划', '删除入港计划', 'AARRIVALDELETE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '接卸准备', 'MUNLOADINGWORK', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '打回流方案', 'MBACKFLOWPROGRAM', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '货批审批', '货批审批', 'AAPGOODSVERIFY', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '打回流准备', 'MBACKWORK', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '数量审核', 'MAMOUNTCONFIRM', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '修改调度日志', '修改调度日志', 'ADISPATCHLOGUPDATE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '接卸通知单', 'MUNLOADINGNOTICE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '添加值班记录', '添加值班记录', 'ADUTYADD', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '配管通知单', 'MPIPINGNOTICE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '删除值班记录', '删除值班记录', 'ADUTYDELETE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '动力班通知单', 'MPOWERNOTICE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '打回流通知单', 'MBACKFLOWNOTICE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '作业计划修改', 'AINBOUNDWORKUPDATE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '查看靠泊评估', 'AINBOUNDASSESS', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '修改储罐台账', '修改储罐台账', 'ASTORELEDGEUPDATE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '添加靠泊评估', 'AINBOUNDASSESSADD', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '修改入库确认信息', '修改入库确认信息', 'AINSTOREUPDATE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '审核靠泊评估', 'AINBOUNDASSESSVERIFY', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '修改靠泊方案', 'AINBOUNDBERTHINGUPDATE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '审核靠泊方案', 'AINBOUNDBERTHINGVERIFY', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '修改接卸方案', 'AUNLOUNDINGPROGRAMUPDATE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '审核接卸方案品质审核', 'AUNLOUNDINGPROGRAMVERIFYQUALITY', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '审核接卸方案工艺审核', 'AUNLOUNDINGPROGRAMVERIFYCRAFT', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '接卸储罐信息修改', 'AINBOUNDTANKUPDATE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '接卸管线信息修改', 'AINBOUNDTUBEUPDATE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '接卸准备管线检查', 'AUPLOADINGTUBECHECK', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '添加提单', '添加提单', 'ALADINGADD', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '接卸准备确认', 'AUPLOADINGCHECK', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '修改提单', '修改提单', 'ALADINGUPDATE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '删除提单', '删除提单', 'ALADINGDELETE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '船舶接卸', 'AUPLOADING', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '修改接卸准备信息', 'AUPLOADINGUPDATE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '发布消息', '发布消息', 'AMESSAGEADD', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '修改数量确认信息', 'AAMOUNTCONFIRMUPDATE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '审核数量确认信息', 'AAMOUNTCONFIRMVERIFY', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '码头规费', 'MPIERFEE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '修改码头规费', 'APIERFEEUPDATE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '删除码头规费', 'APIERFEEDELETE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '出港计划', 'MOUTBOUND', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '添加出港计划', 'AOUTBOUNDADD', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '修改出港计划', 'AOUTBOUNDUPDATE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '删除出港计划', 'AOUTBOUNDDELETE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '船舶出库', 'MSHIPDELIVERY', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '修改出库计划', 'AOUBOUNDPLANUPDATE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '审核出港靠泊评估', 'AOUTBOUNDASSESSVERIFY', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '修改出港靠泊方案', 'AOUTBOUNDBERTHINGUPDATE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '审核出港靠泊方案', 'AOUTBOUNDBERTHINGVERIFY', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '修改船发作业通知单', 'AOUTBOUNDWORKNOTICE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '修改配管作业通知单', 'AOUTPIPERNOTICE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '修改船发动力班作业通知单', 'AOuTBOUNDPOWERNOTICE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '修改船发作业', 'AOUTBOUNDWORKUPDATE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '审核船发作业', 'AOUTBOUNDWORKVERIFY', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '修改船发数量确认', 'AOUTBOUNDAMOUNTUPDATE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '审核船发数量', 'AOUTBOUNDAMOUNTVERIFY', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '分流台账', 'MSPLITELEFGER', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '添加车发出库', 'ACARDELIVERYADD', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '修改车发出库', 'ACARDELIVERYUPDATE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '删除车发出库', 'ACARDELIVERYDELETE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '添加开票信息', 'ABILLINGUPDATE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '审核开票信息', 'ABLLINGVERIFY', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '称重', 'AWEIGHT', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '计量', 'AMEASURE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '修改车发作业', 'ACARDELIVERYWORKUPDATE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '审核车发作业', 'ACARDELIVERYWORKVERIFY', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '修改车发数量确认信息', 'ACARAMOUNTUPDATE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '审核车发数量确认信息', 'ACARAMOUNTVERIFY', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '入库列表', 'MINBOUNDWORKALL', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('MENU', '', '通知单', 'MNOTICE', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '接卸各岗位最后检查', 'AUPLOADINGTUBEALLCHECK', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '修改货批', '修改货批', 'AAPCARGOMODIFY', '0');
INSERT INTO `t_auth_security_resources`(category,description,name,indentifier,status) VALUES ('ACCESS', '', '添加出港靠泊评估', 'AOUTBOUNDASSESSADD', '0');
SET FOREIGN_KEY_CHECKS = 1;
