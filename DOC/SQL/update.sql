ALTER TABLE `t_pcs_berth_assess`
MODIFY COLUMN `id`  int(20) NOT NULL AUTO_INCREMENT FIRST ,
ADD PRIMARY KEY (`id`);

INSERT INTO `t_pcs_status` VALUES ('6', '已作废');

ALTER TABLE `t_pcs_arrival_info`
MODIFY COLUMN `id`  int(20) NOT NULL AUTO_INCREMENT FIRST ;

ALTER TABLE `t_pcs_transport_program`
MODIFY COLUMN `id`  int(20) NOT NULL AUTO_INCREMENT FIRST ;

ALTER TABLE `t_pcs_bill`
MODIFY COLUMN `id`  int(11) NOT NULL AUTO_INCREMENT FIRST ;

ALTER TABLE `t_pcs_charge`
MODIFY COLUMN `id`  int(11) NOT NULL AUTO_INCREMENT FIRST ;


ALTER TABLE `t_pcs_job_check`
CHANGE COLUMN `ID` `id`  int(20) NOT NULL AUTO_INCREMENT FIRST ,
ADD PRIMARY KEY (`id`);

ALTER TABLE `t_pcs_delivery_goods_record`
MODIFY COLUMN `id`  int(11) NOT NULL AUTO_INCREMENT FIRST ;

ALTER TABLE `t_pcs_qualification`
MODIFY COLUMN `id`  int(7) UNSIGNED NOT NULL AUTO_INCREMENT FIRST ;

ALTER TABLE `t_pcs_wind_power`
MODIFY COLUMN `key`  int(7) UNSIGNED NOT NULL AUTO_INCREMENT FIRST ;

ALTER TABLE `t_pcs_wind_direction`
MODIFY COLUMN `key`  int(7) UNSIGNED NOT NULL AUTO_INCREMENT FIRST ;

ALTER TABLE `t_pcs_trade_type`
MODIFY COLUMN `key`  int(7) UNSIGNED NOT NULL AUTO_INCREMENT FIRST ;

ALTER TABLE `t_pcs_port`
MODIFY COLUMN `id`  int(11) NOT NULL AUTO_INCREMENT FIRST ;

ALTER TABLE `t_pcs_ship_agent`
MODIFY COLUMN `id`  int(10) NOT NULL AUTO_INCREMENT FIRST ;

/*
MySQL Backup
Source Server Version: 5.5.29
Source Database: sop
Date: 2015/2/6 05:27:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
--  Procedure definition for `getClientName`
-- ----------------------------
DROP FUNCTION IF EXISTS `getClientName`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `getClientName`(l_id VARCHAR(20)) RETURNS varchar(255) CHARSET utf8
BEGIN
	DECLARE temp VARCHAR(255) DEFAULT '';
	DECLARE name1 VARCHAR(255) DEFAULT '';
	DECLARE done INT; 
	DECLARE	mycursor CURSOR FOR SELECT NAME FROM t_pcs_client WHERE id IN (SELECT clientId FROM t_pcs_arrival_plan WHERE arrivalId =l_id) ;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
	OPEN mycursor ;
	REPEAT
		FETCH mycursor INTO temp;
	UNTIL done END REPEAT;
	SET name1 := CONCAT(temp,',',name1);
	CLOSE mycursor ;
	RETURN name1;
    END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `getVehicleDetailData`
-- ----------------------------
DROP PROCEDURE IF EXISTS `getVehicleDetailData`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `getVehicleDetailData`(in l_trainId int)
BEGIN
  DECLARE done INT DEFAULT 0;
  declare pactualNum double ;
  declare pid int ;
  DECLARE pDeliverNum DOUBLE ;
  DECLARE pnum DOUBLE ;
  DECLARE cur1 CURSOR FOR SELECT id,deliverNum,actualNum FROM t_pcs_vehicle_plan WHERE trainId = l_trainId;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
    SELECT   (IFNULL(temp1.total1,0) + IFNULL(temp2.total2,0)) INTO pactualNum FROM
  (SELECT     SUM(actualNum) total1   FROM    t_pcs_measure m   WHERE m.trainId = l_trainId) temp1,
  (SELECT SUM(t.netWeight) total2 FROM t_pcs_weigh t WHERE t.trainId = l_trainId) temp2 ;
		
OPEN cur1;
read_loop: LOOP
    FETCH cur1 INTO pid,pDeliverNum,pnum;
	
    IF done THEN
      LEAVE read_loop;
    END IF;
		if pnum!=null and pnum!='' and pnum!=0 then
			IF pDeliverNum>0 AND pactualNum>=pDeliverNum THEN
			  UPDATE t_pcs_vehicle_plan SET actualNum = pDeliverNum WHERE id=pid ;
			   SET pactualNum=pactualNum-pDeliverNum ;
			ELSEIF pDeliverNum>0 AND pactualNum<pDeliverNum THEN 
			UPDATE t_pcs_vehicle_plan SET actualNum = pactualNum WHERE id=pid ;
			   SET pactualNum=pactualNum-pDeliverNum ;
			END IF;
		end if;
	END LOOP;
	CLOSE cur1;
	SELECT 
  p.id,
  c.name,
  p.ladingId,
  l.code,
  pr.name productName,
  p.deliverNum,
  p.actualNum deliverNum1 
FROM
  t_pcs_vehicle_plan p 
  LEFT JOIN t_pcs_lading l 
    ON p.ladingId = l.id,
  t_pcs_train t,
  t_pcs_client c,
  t_pcs_product pr 
WHERE t.id = p.trainId 
  AND c.id = t.clientId 
  AND t.productId = pr.id 
  AND p.trainId = l_trainId;
 
  END
;;
DELIMITER ;

-- ----------------------------
--  Records 
-- ----------------------------

-- ---------------------
-- 20150317 流量计
-- -------------------
CREATE TABLE t_pcs_measure_record( #流量计读数日表
id INT AUTO_INCREMENT PRIMARY KEY, #id
parkId INT,           #车位id
meterStartTime TIMESTAMP,  #开始时间
productId int,	#货品id
meterEndTime TIMESTAMP,	#结束时间
serialNo VARCHAR(15),	#流水号
TYPE VARCHAR(1),	#类型 1手动 0自动
meterStart DECIMAL(10,2),#小表起
meterEnd DECIMAL(10,2),	#小表止
meterActualNum DECIMAL(10,2),#小表实发
reserveNum DECIMAL(10,2),#预置量
diffNum DECIMAL(10,2),#差异
createTime TIMESTAMP  #创建时间
);

CREATE TABLE t_pcs_history_record(   #流量计累计量历史查询表
id INT AUTO_INCREMENT PRIMARY KEY, #id
parkId INT,	#车位id
cumulant DECIMAL(10,2), #当前累积量
createTime TIMESTAMP  #创建时间
);

DROP TABLE IF EXISTS `t_pcs_shipdeliverymeasure`;

CREATE TABLE `t_pcs_shipdeliverymeasure` (
  `id` int(11) NOT NULL AUTO_INCREMENT,		#id
  `shipId` int(11) DEFAULT NULL,		#船id
  `tankId` int(11) DEFAULT NULL,		#罐号id
  `berthId` int(11) DEFAULT NULL,		#泊位id
  `startTime` varchar(10) DEFAULT NULL,		#开始时间点
  `endTime` varchar(10) DEFAULT NULL,		#结束时间点
  `startLevel` decimal(10,3) DEFAULT NULL,	#前尺
  `endLevel` decimal(10,3) DEFAULT NULL,	#后尺
  `flowmeter` decimal(10,3) DEFAULT NULL,	#流量计
  `metering` decimal(10,3) DEFAULT NULL,	#计量
  `shipAmount` decimal(10,3) DEFAULT NULL,	#船方
  `flowmeterDiff` decimal(10,3) DEFAULT NULL,	#液-计
  `meteringDiff` decimal(10,3) DEFAULT NULL,	#雷-计
  `shipAmountDiff` decimal(10,3) DEFAULT NULL,	#船-计
  `checkUserId` int(11) DEFAULT NULL,		#检尺人
  `realAmount` decimal(10,3) DEFAULT NULL,	#实发量
  `cdate` datetime DEFAULT NULL,		#日期
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,#创建时间
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;


insert into `t_auth_security_resources` (`category`, `description`, `name`, `indentifier`, `status`) values('ACCESS','车位流量日报表','车位质量流量计日报表','PARKDAILYSTATEMENT','0');
insert into `t_auth_security_resources` (`category`, `description`, `name`, `indentifier`, `status`) values('ACCESS','车位流量月报表','车位质量流量计月报表','PARKMONTHLYSTATEMENT','0');
insert into `t_auth_security_resources` (`category`, `description`, `name`, `indentifier`, `status`) values('ACCESS','月度汇总表','车发流量计月度汇总表','VEHICLEMONTHLYSTATEMENT','0');
insert into `t_auth_security_resources` (`category`, `description`, `name`, `indentifier`, `status`) values('ACCESS','累计量历史记录','车发流量计历史记录','VEHICLECUMULANT','0');
insert into `t_auth_security_resources` (`category`, `description`, `name`, `indentifier`, `status`) values('ACCESS','月报总表','鹤位流量计读数月报总表','VEHICLEMONTHLYTOTALSTATEMENT','0');

INSERT INTO `t_auth_security_resources` ( `category`, `description`, `name`, `indentifier`, `status`) VALUES ( 'MENU', '其他通知单', '通知单(入库)', 'MOTHERNOTICE', 0);
INSERT INTO `t_auth_security_resources` ( `category`, `description`, `name`, `indentifier`, `status`) VALUES ('ACCESS', '入库作业搜索', '入库作业搜索(入库)', 'AINBOUNDSEARCH', 0);
INSERT INTO `t_auth_security_resources` ( `category`, `description`, `name`, `indentifier`, `status`) VALUES ( 'ACCESS', '查看货品信息', '查看货品信息(入库)', 'ACHECKINBOUNDGOODSDETAIL', 0);
INSERT INTO `t_auth_security_resources` (`category`, `description`, `name`, `indentifier`, `status`) VALUES ('ACCESS', '回退功能', '回退功能(入库)', 'ABACKSTATUS', 0);
INSERT INTO `t_auth_security_resources` (`category`, `description`, `name`, `indentifier`, `status`) VALUES ('ACCESS', '审核接卸方案全部通过', '审核接卸方案全部通过(入库)', 'AUNLOUNDINGPROGRAMVERIFY', 0);
INSERT INTO `t_auth_security_resources` (`category`, `description`, `name`, `indentifier`, `status`) VALUES ('ACCESS', '审核接卸方案不通过', '审核接卸方案不通过(入库)', 'AUNLOUNDINGPROGRAMVERIFYNO', 0);
INSERT INTO `t_auth_security_resources` (`category`, `description`, `name`, `indentifier`, `status`) VALUES ('ACCESS', '开泵确认动力班', '开泵确认动力班(入库)', 'AOPENPUPMOWER', 0);
INSERT INTO `t_auth_security_resources` (`category`, `description`, `name`, `indentifier`, `status`) VALUES ('ACCESS', '开泵确认码头', '开泵确认码头(入库)', 'AOPENPUPMDOCK', 0);
INSERT INTO `t_auth_security_resources` (`category`, `description`, `name`, `indentifier`, `status`) VALUES ('ACCESS', '开泵确认调度', '开泵确认调度(入库)', 'AOPENPUPMDISPATCH', 0);
INSERT INTO `t_auth_security_resources` (`category`, `description`, `name`, `indentifier`, `status`) VALUES ('ACCESS', '数量确认保存', '数量确认保存', 'AUPLOADINGSAVE', 0);
INSERT INTO `t_auth_security_resources` ( `category`, `description`, `name`, `indentifier`, `status`) VALUES ( 'ACCESS', '查看合同号', '查看合同号', 'AGETCONTRACT', 0);

CREATE TABLE t_pcs_weighBridge(  #地磅称重表
    id INT PRIMARY KEY AUTO_INCREMENT,  #id
    SERIAL VARCHAR(11),	#开单号
    intoTime BIGINT,	#进库时间
    outTime BIGINT,	#出库时间
    inWeigh DECIMAL(7,3),#进库重
    outWeigh DECIMAL(7,3),#出库重
    measureWeigh DECIMAL(8,3),#表反重量
    deliveryNum DECIMAL(8,3),#发运数
    inPort VARCHAR(4),#发货口
    createUserId INT,#作业人id
    description varchar(200), #说明
    status int default 1,#1未发货2入库称重3出库称重4已发货
    crateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP #创建时间
);

ALTER TABLE `sop`.`t_pcs_goodslog`   
  ADD COLUMN `createUserId` INT NULL AFTER `serial`;
  insert into `t_auth_security_resources` (`category`, `description`, `name`, `indentifier`, `status`) values('MENU','地磅称重','地磅称重','WEIGHBRIDGE','0');

  
INSERT INTO `t_auth_security_resources` ( `category`, `description`, `name`, `indentifier`, `status`) VALUES ( 'MENU', '统计报表', '统计报表', 'MSTATISTICS', 0);
INSERT INTO `t_auth_security_resources` ( `category`, `description`, `name`, `indentifier`, `status`) VALUES ( 'MENU', '货批汇总', '货批汇总', 'MCARGOLIST', 0);
INSERT INTO `t_auth_security_resources` ( `category`, `description`, `name`, `indentifier`, `status`) VALUES ( 'MENU', '货体汇总', '货体汇总', 'MGOODSLIST', 0);


-- ----------------------------
--
-- ---------------------------
DROP PROCEDURE IF EXISTS `checkCommitChange`$$

CREATE PROCEDURE `checkCommitChange`(IN l_arrivalId INT,IN l_flag INT)
BEGIN
	DECLARE g_status INT;
	DECLARE d_num DOUBLE ;
	DECLARE a_num DOUBLE ;
	DECLARE g_id INT ;
	DECLARE i_id INT ;
	DECLARE done TINYINT(1) DEFAULT 0;
	DECLARE goodsloginfo CURSOR FOR SELECT i.id,i.deliverNum,i.actualNum,i.goodsId FROM t_pcs_goodslog i WHERE i.deliverType=2 AND i.batchId=l_arrivalId AND i.type=5;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
	SELECT STATUS INTO g_status FROM t_pcs_arrival WHERE id=l_arrivalId ;
	IF g_status=54 THEN
		OPEN goodsloginfo ;
		REPEAT   
			FETCH goodsloginfo INTO i_id,d_num,a_num,g_id ;
			IF done=0 THEN
				UPDATE t_pcs_goods SET goodsCurrent=goodsCurrent+a_num-d_num WHERE id=g_id ;
				UPDATE   t_pcs_lading l 
					SET
					  l.goodsDelivery = goodsDelivery - a_num 
					WHERE l.id = 
					  (SELECT 
					    ladingId 
					  FROM
					    t_pcs_goods_group 
					  WHERE id = 
					    (SELECT 
					      goodsGroupId 
					    FROM
					      t_pcs_goods 
					    WHERE id =g_id));
				UPDATE t_pcs_goodslog SET actualNum=0 WHERE id=i_id ;
			END IF;
			UNTIL done END REPEAT;
		CLOSE goodsloginfo ;
		UPDATE t_pcs_arrival SET STATUS=53 WHERE id=l_arrivalId ;
		IF l_flag=1 THEN
		DELETE FROM t_pcs_approve WHERE refId=l_arrivalId AND modelId=4 ;
		ELSE 
		UPDATE t_pcs_approve SET STATUS=2 WHERE refId=l_arrivalId AND modelId=4 ;
		END IF;
	END IF ;
END

DELIMITER ;

insert into `t_auth_security_resources` (`category`, `description`, `name`, `indentifier`, `status`) values('MENU','仓储费','仓储费','STORAGEFEE','0');


-- 20150326
INSERT INTO `t_auth_security_resources` VALUES ('ACCESS', '查看作业计划的权限（获取通知）', '查看作业计划的权限（入库）', 'ACHECKINBOUNDPLAN', 0);
INSERT INTO `t_auth_security_resources` VALUES ('ACCESS', '获悉接卸准备提交', '获悉接卸准备提交', 'AUPLOADINGCHECK', 0);
INSERT INTO `t_auth_security_resources` VALUES ('ACCESS', '获悉回退消息权限', '获悉回退消息权限', 'AINBOUNDBACKMSG', 0);

 DROP TRIGGER IF EXISTS `update_t_pcs_work`;
DELIMITER ;;
CREATE TRIGGER `update_t_pcs_work` AFTER UPDATE ON `t_pcs_work` FOR EACH ROW BEGIN
  IF new.status!=old.status and new.status=8 THEN 
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'提交了一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的[',e.name,']的接卸准备，请您获悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,CONCAT('#/inboundoperation') FROM t_auth_user a LEFT JOIN t_pcs_arrival b on  b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c  on c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d  on d.id=b.shipRefId LEFT JOIN t_pcs_product e on new.productId=e.id  WHERE  a.id=new.createRUserId ;
INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT   DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 FROM  `t_auth_authorization` u ,  `t_auth_security_resources` t,  `t_auth_resource_assignments` ta WHERE t.indentifier='AUPLOADINGCHECK'  AND ta.sourceId = t.id   AND ta.roleId = u.roleId; 
 ELSEIF new.reviewStatus!=old.reviewStatus and new.reviewStatus=1 THEN 
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'提交了一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的[',e.name,']的数量审核，请您获悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,CONCAT('#/inboundoperation') FROM t_auth_user a LEFT JOIN t_pcs_arrival b on  b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c  on c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d  on d.id=b.shipRefId LEFT JOIN t_pcs_product e on new.productId=e.id  WHERE  a.id=new.createUserId ;
INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT   DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 FROM  `t_auth_authorization` u ,  `t_auth_security_resources` t,  `t_auth_resource_assignments` ta WHERE t.indentifier='AAMOUNTCONFIRMVERIFY'  AND ta.sourceId = t.id   AND ta.roleId = u.roleId; 
 ELSEIF new.reviewStatus!=old.reviewStatus and new.reviewStatus=1 THEN 
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'提交了一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的[',e.name,']的数量审核，请您获悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,CONCAT('#/inboundoperation') FROM t_auth_user a LEFT JOIN t_pcs_arrival b on  b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c  on c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d  on d.id=b.shipRefId LEFT JOIN t_pcs_product e on new.productId=e.id  WHERE  a.id=new.createUserId ;
INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT   DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 FROM  `t_auth_authorization` u ,  `t_auth_security_resources` t,  `t_auth_resource_assignments` ta WHERE t.indentifier='AAMOUNTCONFIRMVERIFY'  AND ta.sourceId = t.id   AND ta.roleId = u.roleId; 
 ELSEIF new.reviewStatus!=old.reviewStatus and new.reviewStatus=2 THEN 
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'提交的一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的[',e.name,']的数量审核已审核通过，请您获悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,CONCAT('#/inboundoperation') FROM t_auth_user a LEFT JOIN t_pcs_arrival b on  b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c  on c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d  on d.id=b.shipRefId LEFT JOIN t_pcs_product e on new.productId=e.id  WHERE  a.id=new.createUserId ;
INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT   DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 FROM  `t_auth_authorization` u ,  `t_auth_security_resources` t,  `t_auth_resource_assignments` ta WHERE t.indentifier='AAMOUNTCONFIRMUPDATE'  AND ta.sourceId = t.id   AND ta.roleId = u.roleId; 
ELSEIF new.reviewStatus!=old.reviewStatus and new.reviewStatus=3 THEN 
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'提交的一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的[',e.name,']的数量审核已审核不通过，请您获悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,CONCAT('#/inboundoperation') FROM t_auth_user a LEFT JOIN t_pcs_arrival b on  b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c  on c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d  on d.id=b.shipRefId LEFT JOIN t_pcs_product e on new.productId=e.id  WHERE  a.id=new.createUserId ;
INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT   DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 FROM  `t_auth_authorization` u ,  `t_auth_security_resources` t,  `t_auth_resource_assignments` ta WHERE t.indentifier='AAMOUNTCONFIRMUPDATE'  AND ta.sourceId = t.id   AND ta.roleId = u.roleId; 
END IF;
  END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `update_pcs_transport_program`;
DELIMITER ;;
CREATE TRIGGER `update_pcs_transport_program` AFTER UPDATE ON `t_pcs_transport_program` FOR EACH ROW BEGIN
  IF new.status!=old.status and new.status=1 THEN 
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'提交了一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的[',e.name,']的',case when new.type=0 then '接卸'  when new.type=1 then '打循环' end,'方案，请您审批。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,CONCAT('#/inboundoperation') FROM t_auth_user a LEFT JOIN t_pcs_arrival b on  b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c  on c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d  on d.id=b.shipRefId LEFT JOIN t_pcs_product e on new.productId=e.id  WHERE  a.id=new.createUserId ;
INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT   DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 FROM  `t_auth_authorization` u ,  `t_auth_security_resources` t,  `t_auth_resource_assignments` ta WHERE t.indentifier in ( 'AUNLOUNDINGPROGRAMVERIFYQUALITY','AUNLOUNDINGPROGRAMVERIFYCRAFT','AUNLOUNDINGPROGRAMVERIFY')  AND ta.sourceId = t.id   AND ta.roleId = u.roleId; 
  ELSEIF new.status!=old.status and new.status=2 then
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'提交的一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的[',e.name,']的',case when new.type=0 then '接卸'  when new.type=1 then '打循环' end,'方案审批已通过，请知悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,CONCAT('#/inboundoperation') FROM t_auth_user a LEFT JOIN t_pcs_arrival b on   b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c on c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d on d.id=b.shipRefId LEFT JOIN t_pcs_product e on new.productId=e.id   WHERE  a.id=new.createUserId  ;
INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT   DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 FROM  `t_auth_authorization` u ,  `t_auth_security_resources` t,  `t_auth_resource_assignments` ta WHERE t.indentifier = 'AUNLOUNDINGPROGRAMUPDATE'  AND ta.sourceId = t.id   AND ta.roleId = u.roleId; 
  ELSEIF new.status!=old.status and new.status=3 THEN
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'提交的一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的[',e.name,']的',case when new.type=0 then '接卸'  when new.type=1 then '打循环' end,'方案审批已退回，请知悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,CONCAT('#/inboundoperation') FROM t_auth_user a LEFT JOIN t_pcs_arrival b on   b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c on c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d on d.id=b.shipRefId LEFT JOIN t_pcs_product e on new.productId=e.id   WHERE  a.id=new.createUserId  ;
INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT   DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 FROM  `t_auth_authorization` u ,  `t_auth_security_resources` t,  `t_auth_resource_assignments` ta WHERE t.indentifier = 'AUNLOUNDINGPROGRAMUPDATE'  AND ta.sourceId = t.id   AND ta.roleId = u.roleId; 
ELSEIF new.status!=old.status and new.status=4 THEN
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'提交的一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的[',e.name,']的',case when new.type=0 then '接卸'  when new.type=1 then '打循环'  end,'方案品质审批已通过，请知悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,CONCAT('#/inboundoperation') FROM t_auth_user a LEFT JOIN t_pcs_arrival b on   b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c on c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d on d.id=b.shipRefId LEFT JOIN t_pcs_product e on new.productId=e.id   WHERE  a.id=new.createUserId  ;
INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT   DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 FROM  `t_auth_authorization` u ,  `t_auth_security_resources` t,  `t_auth_resource_assignments` ta WHERE t.indentifier = 'AUNLOUNDINGPROGRAMUPDATE'  AND ta.sourceId = t.id   AND ta.roleId = u.roleId; 
ELSEIF  new.status!=old.status and new.status=5 THEN
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'提交的一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的[',e.name,']的',case when new.type=0 then '接卸'  when new.type=1 then '打循环' end,'方案工艺审批已通过，请知悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,CONCAT('#/inboundoperation') FROM t_auth_user a LEFT JOIN t_pcs_arrival b on   b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c on c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d on d.id=b.shipRefId LEFT JOIN t_pcs_product e on new.productId=e.id   WHERE  a.id=new.createUserId  ;
INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT   DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 FROM  `t_auth_authorization` u ,  `t_auth_security_resources` t,  `t_auth_resource_assignments` ta WHERE t.indentifier = 'AUNLOUNDINGPROGRAMUPDATE'  AND ta.sourceId = t.id   AND ta.roleId = u.roleId; 
 END IF;
    END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `update_t_pcs_arrival_info`;
DELIMITER ;;
CREATE TRIGGER `update_t_pcs_arrival_info` AFTER UPDATE ON `t_pcs_arrival_info` FOR EACH ROW BEGIN
 IF  new.status!=old.status and new.status=1 THEN 
 INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'提交了一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的作业计划，请您获悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,CONCAT('#/inboundoperation') FROM t_auth_user a LEFT JOIN t_pcs_arrival b on  b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c  on c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d  on d.id=b.shipRefId WHERE  a.id=new.createUserId ;
INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT   DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 FROM  `t_auth_authorization` u ,  `t_auth_security_resources` t,  `t_auth_resource_assignments` ta WHERE t.indentifier = 'ACHECKINBOUNDPLAN'   AND ta.sourceId = t.id   AND ta.roleId = u.roleId; 
ELSEIF old.status=1 and new.status=1 THEN
 INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'修改了一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的作业计划，请您获悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,CONCAT('#/inboundoperation') FROM t_auth_user a LEFT JOIN t_pcs_arrival b on  b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c  on c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d  on d.id=b.shipRefId WHERE  a.id=new.createUserId ;
INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT   DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 FROM  `t_auth_authorization` u ,  `t_auth_security_resources` t,  `t_auth_resource_assignments` ta WHERE t.indentifier = 'ACHECKINBOUNDPLAN'   AND ta.sourceId = t.id   AND ta.roleId = u.roleId; 
 END IF;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `update_pcs_berth_assess`;
DELIMITER ;;
CREATE TRIGGER `update_pcs_berth_assess` AFTER UPDATE ON `t_pcs_berth_assess` FOR EACH ROW BEGIN
   DECLARE aType INT ;
   DECLARE url VARCHAR(60) ;
   DECLARE aStatus INT ;
   DECLARE u_right VARCHAR(30);
   SELECT TYPE,STATUS INTO aType,aStatus FROM t_pcs_arrival WHERE id=new.arrivalId ;
   IF aType=1 THEN
		SET url='#/inboundoperation' ;
		SET u_right = 'AINBOUNDASSESSVERIFY' ;
   ELSEIF aType=2 THEN
		SET url=CONCAT('#/outboundserial/get?status=51&id=',new.arrivalId) ;
		SET u_right = 'AOUTBOUNDASSESSVERIFY' ;
   END IF ;
  IF new.reviewStatus!=old.reviewStatus AND new.reviewStatus=1 THEN 
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'提交了一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的靠泊评估，请您审批。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,url FROM t_auth_user a LEFT JOIN t_pcs_arrival b ON  b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c  ON c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d  ON d.id=b.shipRefId WHERE  a.id=new.createUserId ;
     INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT   DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1  FROM    `t_auth_authorization` u ,   `t_auth_security_resources` t, `t_auth_resource_assignments` ta   WHERE t.indentifier = u_right   AND ta.sourceId = t.id  AND ta.roleId = u.roleId; 
  ELSEIF new.reviewStatus!=old.reviewStatus AND  new.reviewStatus=2 THEN
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT('您提交的一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的靠泊评估审批已通过，请知悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,url FROM t_auth_user a LEFT JOIN t_pcs_arrival b ON   b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c ON c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d ON d.id=b.shipRefId WHERE  a.id=new.createUserId  ;
     INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT new.createUserId,MAX(id),1 FROM t_pcs_message_content ;
  ELSEIF new.reviewStatus!=old.reviewStatus AND  new.reviewStatus=3 THEN
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT('您提交的一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的靠泊评估审批已退回，请知悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,url FROM t_auth_user a LEFT JOIN t_pcs_arrival b ON   b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c ON c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d ON d.id=b.shipRefId WHERE  a.id=new.createUserId  ;
     INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT new.createUserId,MAX(id),1 FROM t_pcs_message_content ;
 END IF;
    END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `update_pcs_berth_program`;
DELIMITER ;;
CREATE TRIGGER `update_pcs_berth_program` AFTER UPDATE ON `t_pcs_berth_program` FOR EACH ROW BEGIN
       DECLARE aType INT ;
   DECLARE url VARCHAR(60) ;
   DECLARE u_right VARCHAR(30);
   SELECT TYPE INTO aType FROM t_pcs_arrival WHERE id=new.arrivalId ;
   IF aType=1 THEN
		SET url='#/inboundoperation' ;
		SET u_right = 'AINBOUNDBERTHINGVERIFY' ;
   ELSEIF aType=2 THEN
		SET url=CONCAT('#/outboundserial/list') ;
		SET u_right = 'AOUTBOUNDBERTHINGVERIFY' ;
   END IF ;
  IF  new.status!=old.status AND new.status=1 THEN 
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'提交了一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的靠泊方案，请您审批。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,url FROM t_auth_user a LEFT JOIN t_pcs_arrival b ON  b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c  ON c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d  ON d.id=b.shipRefId WHERE  a.id=new.createUserId ;
     INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT 
	  DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 
	FROM 
	  `t_auth_authorization` u ,
	  `t_auth_security_resources` t,
	  `t_auth_resource_assignments` ta 
	WHERE t.indentifier = u_right 
	  AND ta.sourceId = t.id 
	  AND ta.roleId = u.roleId; 
  ELSEIF new.status!=old.status AND new.status=2 THEN
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT('您提交的一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的靠泊方案审批已通过，请知悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.reviewUserId,1,url FROM t_auth_user a LEFT JOIN t_pcs_arrival b ON   b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c ON c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d ON d.id=b.shipRefId WHERE  a.id=new.reviewUserId  ;
     INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT new.reviewUserId,MAX(id),1 FROM t_pcs_message_content ;
  ELSEIF new.status!=old.status AND new.status=3 THEN
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT('您提交的一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的靠泊方案审批已退回，请知悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.reviewUserId,1,url FROM t_auth_user a LEFT JOIN t_pcs_arrival b ON   b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c ON c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d ON d.id=b.shipRefId WHERE  a.id=new.reviewUserId  ;
     INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT new.reviewUserId,MAX(id),1 FROM t_pcs_message_content ;
 END IF;
    END
;;
DELIMITER ;

-- --------------------------------
--
-- ------------------------------
insert into `t_auth_security_resources` (`category`, `description`, `name`, `indentifier`, `status`) values('MENU','仓储费','仓储费','STORAGEFEE','0');

ALTER TABLE `sop`.`t_pcs_goodslog`   
  ADD COLUMN `isAccountComp` INT(1) NULL AFTER `createUserId`;
  ALTER TABLE `sop`.`t_pcs_goods`   
  ADD COLUMN `recordTime` VARCHAR(30) NULL AFTER `isAccountComp`;


DROP PROCEDURE IF EXISTS `getStorageCost`;

CREATE PROCEDURE `getStorageCost`(IN l_sTime VARCHAR(20),IN l_eTime VARCHAR(20),IN l_goodsId INT)
BEGIN
	DECLARE i INT DEFAULT 0;
	DECLARE nTime VARCHAR(30);
	DECLARE cou INT ;
	SET cou =TO_DAYS(STR_TO_DATE(l_eTime,'%Y-%m-%d'))-TO_DAYS(STR_TO_DATE(l_sTime,'%Y-%m-%d')) ;
	CREATE TEMPORARY TABLE IF NOT EXISTS temp_goods_info(id INT AUTO_INCREMENT PRIMARY KEY,CODE VARCHAR(30),clientName VARCHAR(40),ladingName VARCHAR(40),l_type VARCHAR(1),l_time VARCHAR(30),currentNum VARCHAR(30),overtimePrice VARCHAR(30),cost VARCHAR(30)) ;
	      DELETE FROM temp_goods_info;
	WHILE i<=cou DO
	SELECT DATE_ADD(l_sTime,INTERVAL i DAY) INTO nTime FROM  DUAL ;
        INSERT INTO temp_goods_info(CODE,clientName,ladingName,l_type,l_time,currentNum,overtimePrice,cost) SELECT   gb.code,c.name clientName,  g.name ladingClientName,  a.type,nTime,(a.goodsSave+a.surplus),(SELECT IFNULL(overtimePrice,0) FROM t_pcs_contract WHERE id=gb.id),((a.goodsSave+a.surplus)*(SELECT IFNULL(overtimePrice,0) FROM t_pcs_contract WHERE id=gb.id))
FROM
  t_pcs_goodslog a 
  LEFT JOIN t_pcs_lading b 
    ON b.id = a.ladingId 
  LEFT JOIN t_pcs_client c 
    ON c.id = a.clientId 
  LEFT JOIN t_pcs_client g 
    ON g.id = a.ladingClientId 
    LEFT JOIN t_pcs_goods gb
    ON a.goodsId = gb.id
WHERE a.type <> 8 
  AND a.goodsId = l_goodsId
  AND a.id=(SELECT MAX(id) FROM t_pcs_goodslog WHERE goodsId=l_goodsId AND DATE_FORMAT(FROM_UNIXTIME(createTime),'%Y-%m-%d')=(SELECT MAX(DATE_FORMAT(FROM_UNIXTIME(createTime),'%Y-%m-%d')) FROM t_pcs_goodslog WHERE goodsId=l_goodsId AND TYPE<>8 AND DATE_FORMAT(FROM_UNIXTIME(createTime),'%Y-%m-%d') <=nTime ));
         SET i=i+1;
     END WHILE;
     SELECT * FROM temp_goods_info;
    END;;
	
insert into `t_auth_security_resources` (`category`, `description`, `name`, `indentifier`, `status`) values('MENU','仓储费','仓储费','STORAGEFEE','0');

ALTER TABLE `sop`.`t_pcs_goods`   
  ADD COLUMN `isAccountComp` INT(1) NULL AFTER `tankCodes`;
  ALTER TABLE `sop`.`t_pcs_goods`   
  ADD COLUMN `recordTime` VARCHAR(30) NULL AFTER `isAccountComp`;

  DELIMITER $$

USE `sop`$$

DROP PROCEDURE IF EXISTS `getStorageCost`$$

CREATE PROCEDURE `getStorageCost`(IN l_sTime VARCHAR(20),IN l_eTime VARCHAR(20),IN l_goodsId INT)
BEGIN
	DECLARE i INT DEFAULT 0;
	DECLARE nTime VARCHAR(30);
	DECLARE cou INT ;
	SET cou =TO_DAYS(STR_TO_DATE(l_eTime,'%Y-%m-%d'))-TO_DAYS(STR_TO_DATE(l_sTime,'%Y-%m-%d')) ;
	CREATE TEMPORARY TABLE IF NOT EXISTS temp_goods_info(id INT AUTO_INCREMENT PRIMARY KEY,CODE VARCHAR(30),clientName VARCHAR(40),ladingName VARCHAR(40),l_type VARCHAR(1),l_time VARCHAR(30),currentNum VARCHAR(30),overtimePrice VARCHAR(30),cost VARCHAR(30)) ;
	      DELETE FROM temp_goods_info;
	WHILE i<=cou DO
	SELECT DATE_ADD(l_sTime,INTERVAL i DAY) INTO nTime FROM  DUAL ;
        INSERT INTO temp_goods_info(CODE,clientName,ladingName,l_type,l_time,currentNum,overtimePrice,cost) SELECT   gb.code,c.name clientName,  g.name ladingClientName,  a.type,nTime,(a.goodsSave+a.surplus),(SELECT IFNULL(overtimePrice,0) FROM t_pcs_contract WHERE id=gb.id),((a.goodsSave+a.surplus)*(SELECT IFNULL(overtimePrice,0) FROM t_pcs_contract WHERE id=gb.id))
FROM
  t_pcs_goodslog a 
  LEFT JOIN t_pcs_lading b 
    ON b.id = a.ladingId 
  LEFT JOIN t_pcs_client c 
    ON c.id = a.clientId 
  LEFT JOIN t_pcs_client g 
    ON g.id = a.ladingClientId 
    LEFT JOIN t_pcs_goods gb
    ON a.goodsId = gb.id
WHERE a.type <> 8 
  AND a.goodsId = l_goodsId
  AND a.id=(SELECT MAX(id) FROM t_pcs_goodslog WHERE goodsId=l_goodsId AND DATE_FORMAT(FROM_UNIXTIME(createTime),'%Y-%m-%d')=(SELECT MAX(DATE_FORMAT(FROM_UNIXTIME(createTime),'%Y-%m-%d')) FROM t_pcs_goodslog WHERE goodsId=l_goodsId AND TYPE<>8 AND DATE_FORMAT(FROM_UNIXTIME(createTime),'%Y-%m-%d') <=nTime ));
         SET i=i+1;
     END WHILE;
     SELECT * FROM temp_goods_info;
    END$$

DELIMITER ;


CREATE TABLE t_pcs_uploadfile_info(
id INT AUTO_INCREMENT PRIMARY KEY,
fileName VARCHAR(100),
description VARCHAR(500),
refId INT,
createUserId INT,
createTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER EVENT `sendMsg` ON SCHEDULE EVERY 1 DAY STARTS '2015-03-27 15:42:06' ON COMPLETION NOT PRESERVE ENABLE DO BEGIN
	DECLARE done TINYINT(1) DEFAULT 0;
	DECLARE l_clientName VARCHAR(30);
	DECLARE l_code VARCHAR(33);
	DECLARE l_days INT ;
	DECLARE l_cou INT ;
	DECLARE l_uid INT ;
      DECLARE mycursor CURSOR FOR SELECT 
	c.name,g.code,(5-(TO_DAYS(DATE_FORMAT(NOW(), '%Y-%m-%d'))-TO_DAYS(DATE_ADD(DATE_FORMAT(a.arrivalTime, '%Y-%m-%d'),
	    INTERVAL (SELECT (c.period-5) FROM t_pcs_contract c  WHERE 1 = c.id) DAY  )))) days
	FROM
	  t_pcs_goods g,
	  t_pcs_client c,
	  t_pcs_cargo o,
	  t_pcs_arrival a,
	  t_pcs_ship s,
	  t_pcs_ship_ref sr 
	WHERE c.id = g.clientId 
	  AND g.cargoId = o.id 
	  AND o.arrivalId = a.id 
	  AND s.id = a.shipId 
	  AND sr.id = a.shipRefId 
	  AND g.isAccountComp<>1
	  AND g.rootGoodsId IS NULL
	  AND  (TO_DAYS(DATE_FORMAT(NOW(), '%Y-%m-%d'))-TO_DAYS(DATE_ADD(DATE_FORMAT(a.arrivalTime, '%Y-%m-%d'),
	  INTERVAL   (SELECT   (c.period-5)  FROM  t_pcs_contract c   WHERE 1 = c.id) DAY  )))>=0 ;
	  SELECT COUNT(1) INTO l_cou FROM t_auth_user u WHERE u.name='系统消息' ;
	  IF l_cou=0 THEN
	  INSERT INTO t_auth_user(PASSWORD,NAME) VALUES('02d3062dc41c96ad5eada2a9bab11058','系统消息') ;END IF;
	  SELECT id INTO l_uid FROM t_auth_user WHERE NAME='系统消息' ;
      OPEN mycursor ;
	REPEAT
		FETCH mycursor INTO l_clientName,l_code,l_days ;
		IF done=0 THEN
			INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) VALUES(CONCAT('客户为 [',l_clientName,']货体编号为：[',l_code,']的货体首期缴费时间还有',l_days,'天就要到期了，请您知悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),l_uid,1,'#/storageFee') ;
     INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT 
	  DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 
	FROM 
	  `t_auth_authorization` u ,
	  `t_auth_security_resources` t,
	  `t_auth_resource_assignments` ta 
	WHERE t.indentifier = 'STORAGEFEE' 
	  AND ta.sourceId = t.id 
	  AND ta.roleId = u.roleId; 
		END IF;
		UNTIL done END REPEAT;
	CLOSE mycursor ;
	END;;

	DELIMITER $$

CREATE EVENT `sendMsg` ON SCHEDULE EVERY 1 DAY STARTS '2015-04-01 09:00:00' ON COMPLETION NOT PRESERVE ENABLE DO BEGIN
	DECLARE done TINYINT(1) DEFAULT 0;
	DECLARE l_clientName VARCHAR(30);
	DECLARE l_code VARCHAR(33);
	DECLARE l_days INT ;
	DECLARE l_cou INT ;
	DECLARE l_uid INT ;
      DECLARE mycursor CURSOR FOR SELECT 
	c.name,g.code,(5-(TO_DAYS(DATE_FORMAT(NOW(), '%Y-%m-%d'))-TO_DAYS(DATE_ADD(DATE_FORMAT(a.arrivalStartTime, '%Y-%m-%d'),
	    INTERVAL (SELECT (c.period) FROM t_pcs_contract c  WHERE g.contractId = c.id) DAY  )))) days
	FROM
	  t_pcs_goods g,
	  t_pcs_client c,
	  t_pcs_cargo o,
	  t_pcs_arrival a,
	  t_pcs_ship s,
	  t_pcs_ship_ref sr 
	WHERE c.id = g.clientId 
	  AND g.cargoId = o.id 
	  AND o.arrivalId = a.id 
	  AND s.id = a.shipId 
	  AND sr.id = a.shipRefId 
	  AND g.isAccountComp IS NULL
	  AND g.rootGoodsId IS NULL
	  AND  (TO_DAYS(DATE_FORMAT(NOW(), '%Y-%m-%d'))-TO_DAYS(DATE_ADD(DATE_FORMAT(a.arrivalStartTime, '%Y-%m-%d'),
	  INTERVAL   (SELECT   (c.period)  FROM  t_pcs_contract c   WHERE 1 = c.id) DAY  ))) BETWEEN 0 AND 5  ;
	  SELECT COUNT(1) INTO l_cou FROM t_auth_user u WHERE u.name='系统消息' ;
	  IF l_cou=0 THEN
	  INSERT INTO t_auth_user(PASSWORD,NAME) VALUES('02d3062dc41c96ad5eada2a9bab11058','系统消息') ;END IF;
	  SELECT id INTO l_uid FROM t_auth_user WHERE NAME='系统消息' ;
      OPEN mycursor ;
	REPEAT
		FETCH mycursor INTO l_clientName,l_code,l_days ;
		IF done=0 THEN
			INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) VALUES(CONCAT('客户为 [',l_clientName,']货体编号为：[',l_code,']的货体首期缴费时间还有',l_days,'天就要到期了，请您知悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),l_uid,1,'#/storageFee') ;
     INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT 
	  DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 
	FROM 
	  `t_auth_authorization` u ,
	  `t_auth_security_resources` t,
	  `t_auth_resource_assignments` ta 
	WHERE t.indentifier = 'STORAGEFEE' 
	  AND ta.sourceId = t.id 
	  AND ta.roleId = u.roleId; 
		END IF;
		UNTIL done END REPEAT;
	CLOSE mycursor ;
	END$$

DELIMITER ;




DELIMITER $$

USE `sop`$$

DROP PROCEDURE IF EXISTS `getStorageCost`$$

CREATE PROCEDURE `getStorageCost`(IN l_sTime VARCHAR(20),IN l_eTime VARCHAR(20),IN l_goodsId INT)
BEGIN
	DECLARE i INT DEFAULT 0;
	DECLARE nTime VARCHAR(30);
	DECLARE cou INT ;
	SET cou =TO_DAYS(STR_TO_DATE(l_eTime,'%Y-%m-%d'))-TO_DAYS(STR_TO_DATE(l_sTime,'%Y-%m-%d')) ;
	CREATE TEMPORARY TABLE IF NOT EXISTS temp_goods_info(id INT AUTO_INCREMENT PRIMARY KEY,CODE VARCHAR(30),clientName VARCHAR(40),ladingName VARCHAR(40),l_type VARCHAR(1),l_time VARCHAR(30),currentNum VARCHAR(30),overtimePrice VARCHAR(30),cost VARCHAR(30)) ;
	      DELETE FROM temp_goods_info;
	WHILE i<=cou DO
	SELECT DATE_ADD(l_sTime,INTERVAL i DAY) INTO nTime FROM  DUAL ;
        INSERT INTO temp_goods_info(CODE,clientName,ladingName,l_type,l_time,currentNum,overtimePrice,cost) SELECT   gb.code,c.name clientName,  g.name ladingClientName,  a.type,nTime,(ROUND((a.goodsSave+a.surplus),3)),(SELECT IFNULL(overtimePrice,0) FROM t_pcs_contract WHERE id=gb.contractId),(ROUND((a.goodsSave+a.surplus)*(SELECT IFNULL(overtimePrice,0) FROM t_pcs_contract WHERE id=gb.contractId),3))
FROM
  t_pcs_goodslog a 
  LEFT JOIN t_pcs_lading b 
    ON b.id = a.ladingId 
  LEFT JOIN t_pcs_client c 
    ON c.id = a.clientId 
  LEFT JOIN t_pcs_client g 
    ON g.id = a.ladingClientId 
    LEFT JOIN t_pcs_goods gb
    ON a.goodsId = gb.id
WHERE a.type <> 8 
  AND a.goodsId = l_goodsId
  AND a.id=(SELECT MAX(id) FROM t_pcs_goodslog WHERE goodsId=l_goodsId AND DATE_FORMAT(FROM_UNIXTIME(createTime),'%Y-%m-%d')=(SELECT MAX(DATE_FORMAT(FROM_UNIXTIME(createTime),'%Y-%m-%d')) FROM t_pcs_goodslog WHERE goodsId=l_goodsId AND TYPE<>8 AND DATE_FORMAT(FROM_UNIXTIME(createTime),'%Y-%m-%d') <=nTime ));
         SET i=i+1;
     END WHILE;
     SELECT * FROM temp_goods_info;
    END$$

DELIMITER ;


INSERT INTO `t_auth_security_resources`(`category`, `description`, `name`, `indentifier`, `status`) VALUES ('MENU', '系统日志', '系统日志', 'MOPERATELOG', 0);

DROP TRIGGER IF EXISTS `update_t_pcs_work`;
DELIMITER ;;
CREATE TRIGGER `update_t_pcs_work` AFTER UPDATE ON `t_pcs_work` FOR EACH ROW BEGIN
  IF new.status!=old.status and new.status=8 and old.status!=9 THEN 
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'提交了一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的[',e.name,']的接卸准备，请您获悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,CONCAT('#/inboundoperation') FROM t_auth_user a LEFT JOIN t_pcs_arrival b on  b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c  on c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d  on d.id=b.shipRefId LEFT JOIN t_pcs_product e on new.productId=e.id  WHERE  a.id=new.createRUserId ;
INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT   DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 FROM  `t_auth_authorization` u ,  `t_auth_security_resources` t,  `t_auth_resource_assignments` ta WHERE t.indentifier='AUPLOADINGCHECK'  AND ta.sourceId = t.id   AND ta.roleId = u.roleId; 
 ELSEIF new.reviewStatus!=old.reviewStatus and new.reviewStatus=1 THEN 
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'提交了一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的[',e.name,']的数量审核，请您获悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,CONCAT('#/inboundoperation') FROM t_auth_user a LEFT JOIN t_pcs_arrival b on  b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c  on c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d  on d.id=b.shipRefId LEFT JOIN t_pcs_product e on new.productId=e.id  WHERE  a.id=new.createUserId ;
INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT   DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 FROM  `t_auth_authorization` u ,  `t_auth_security_resources` t,  `t_auth_resource_assignments` ta WHERE t.indentifier='AAMOUNTCONFIRMVERIFY'  AND ta.sourceId = t.id   AND ta.roleId = u.roleId; 
 ELSEIF new.reviewStatus!=old.reviewStatus and new.reviewStatus=1 THEN 
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'提交了一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的[',e.name,']的数量审核，请您获悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,CONCAT('#/inboundoperation') FROM t_auth_user a LEFT JOIN t_pcs_arrival b on  b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c  on c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d  on d.id=b.shipRefId LEFT JOIN t_pcs_product e on new.productId=e.id  WHERE  a.id=new.createUserId ;
INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT   DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 FROM  `t_auth_authorization` u ,  `t_auth_security_resources` t,  `t_auth_resource_assignments` ta WHERE t.indentifier='AAMOUNTCONFIRMVERIFY'  AND ta.sourceId = t.id   AND ta.roleId = u.roleId; 
 ELSEIF new.reviewStatus!=old.reviewStatus and new.reviewStatus=2 THEN 
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'提交的一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的[',e.name,']的数量审核已审核通过，请您获悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,CONCAT('#/inboundoperation') FROM t_auth_user a LEFT JOIN t_pcs_arrival b on  b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c  on c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d  on d.id=b.shipRefId LEFT JOIN t_pcs_product e on new.productId=e.id  WHERE  a.id=new.createUserId ;
INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT   DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 FROM  `t_auth_authorization` u ,  `t_auth_security_resources` t,  `t_auth_resource_assignments` ta WHERE t.indentifier='AAMOUNTCONFIRMUPDATE'  AND ta.sourceId = t.id   AND ta.roleId = u.roleId; 
ELSEIF new.reviewStatus!=old.reviewStatus and new.reviewStatus=3 THEN 
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'提交的一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的[',e.name,']的数量审核已审核不通过，请您获悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,CONCAT('#/inboundoperation') FROM t_auth_user a LEFT JOIN t_pcs_arrival b on  b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c  on c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d  on d.id=b.shipRefId LEFT JOIN t_pcs_product e on new.productId=e.id  WHERE  a.id=new.createUserId ;
INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT   DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 FROM  `t_auth_authorization` u ,  `t_auth_security_resources` t,  `t_auth_resource_assignments` ta WHERE t.indentifier='AAMOUNTCONFIRMUPDATE'  AND ta.sourceId = t.id   AND ta.roleId = u.roleId; 
END IF;
  END
;;
DELIMITER ;

-- -------------------------------------------------
CREATE TABLE t_pcs_storagefee_record(
id INT AUTO_INCREMENT PRIMARY KEY,
goodsId INT,
storageFee DECIMAL(10,3),
feeType VARCHAR(1),
createUserId INT,
goodsInspect DECIMAL(10,3),
createTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DELIMITER $$

DROP PROCEDURE IF EXISTS `getStorageCost`$$

CREATE  PROCEDURE `getStorageCost`(IN l_sTime VARCHAR(20),IN l_eTime VARCHAR(20),IN l_goodsId INT)
BEGIN
	DECLARE i INT DEFAULT 0;
	DECLARE nTime VARCHAR(30);
	DECLARE g_code VARCHAR(30) ;
	DECLARE g_clientName VARCHAR(30) ;
	DECLARE g_ladingClientName VARCHAR(30) ;
	DECLARE g_currentNum VARCHAR(30) ;
	DECLARE g_overtimePrice VARCHAR(30) ;
	DECLARE g_cost VARCHAR(30) ;
	DECLARE g_curTime VARCHAR(30);
	DECLARE g_cou INT;
	DECLARE cou INT ;
	SET cou =TO_DAYS(STR_TO_DATE(l_eTime,'%Y-%m-%d'))-TO_DAYS(STR_TO_DATE(l_sTime,'%Y-%m-%d')) ;
	CREATE TEMPORARY TABLE IF NOT EXISTS temp_goods_info(id INT AUTO_INCREMENT PRIMARY KEY,CODE VARCHAR(30),clientName VARCHAR(40),ladingName VARCHAR(40),l_time VARCHAR(30),deliverNum VARCHAR(30),exportNum VARCHAR(30),backNum VARCHAR(30),holdNum VARCHAR(40),currentNum VARCHAR(30),overtimePrice VARCHAR(30),cost VARCHAR(30)) ;
	      DELETE FROM temp_goods_info;
	WHILE i<=cou DO
	SELECT DATE_ADD(l_sTime,INTERVAL i DAY) INTO nTime FROM  DUAL ;
	SELECT   gb.code,c.name clientName,g.name ladingClientName,(ROUND((a.goodsSave+a.surplus),3)),(SELECT IFNULL(overtimePrice,0) FROM t_pcs_contract WHERE id=gb.contractId),(ROUND((a.goodsSave+a.surplus)*(SELECT IFNULL(overtimePrice,0) FROM t_pcs_contract WHERE id=gb.contractId),3))INTO g_code,g_clientName,g_ladingClientName,g_currentNum,g_overtimePrice,g_cost
FROM
  t_pcs_goodslog a 
  LEFT JOIN t_pcs_lading b 
    ON b.id = a.ladingId 
  LEFT JOIN t_pcs_client c 
    ON c.id = a.clientId 
  LEFT JOIN t_pcs_client g 
    ON g.id = a.ladingClientId 
    LEFT JOIN t_pcs_goods gb
    ON a.goodsId = gb.id
WHERE a.type <> 8 
  AND a.goodsId = l_goodsId   
  AND a.id=(SELECT MAX(id) FROM t_pcs_goodslog WHERE goodsId=l_goodsId AND DATE_FORMAT(FROM_UNIXTIME(createTime),'%Y-%m-%d')=(SELECT MAX(DATE_FORMAT(FROM_UNIXTIME(createTime),'%Y-%m-%d')) FROM t_pcs_goodslog WHERE goodsId=l_goodsId AND TYPE<>8 AND DATE_FORMAT(FROM_UNIXTIME(createTime),'%Y-%m-%d') <=nTime ));
  SELECT COUNT(1) INTO g_cou FROM t_pcs_goodslog WHERE goodsId=l_goodsId AND DATE_FORMAT(FROM_UNIXTIME(createTime),'%Y-%m-%d')=(SELECT MAX(DATE_FORMAT(FROM_UNIXTIME(createTime),'%Y-%m-%d')) FROM t_pcs_goodslog WHERE goodsId=l_goodsId AND TYPE<>8 AND DATE_FORMAT(FROM_UNIXTIME(createTime),'%Y-%m-%d') =nTime );
  SELECT DATE_FORMAT(FROM_UNIXTIME(createTime),'%Y-%m-%d') INTO g_curTime FROM t_pcs_goodslog WHERE id=(SELECT MAX(id) FROM t_pcs_goodslog WHERE goodsId=l_goodsId AND DATE_FORMAT(FROM_UNIXTIME(createTime),'%Y-%m-%d')=(SELECT MAX(DATE_FORMAT(FROM_UNIXTIME(createTime),'%Y-%m-%d')) FROM t_pcs_goodslog WHERE goodsId=l_goodsId AND TYPE<>8 AND DATE_FORMAT(FROM_UNIXTIME(createTime),'%Y-%m-%d') <=nTime )) ;
  INSERT INTO temp_goods_info(CODE,clientName,ladingName,l_time,deliverNum,exportNum,backNum,holdNum,currentNum,overtimePrice,cost) SELECT g_code,g_clientName,g_ladingClientName,nTime,SUM(deliverNum) deliverNum,SUM(exportNum) exportNum,SUM(backNum) backNum,SUM(holdNum) holdNum,g_currentNum,g_overtimePrice,g_cost FROM (SELECT   nTime,(CASE WHEN a.type=5 AND g_cou>0 THEN IFNULL(ABS(a.goodsChange),0) ELSE 0 END)deliverNum,(CASE WHEN a.type=3 AND g_cou>0 THEN IFNULL(ABS(a.goodsChange),0) ELSE 0 END)exportNum,(CASE WHEN a.type=6 AND g_cou>0 THEN IFNULL(ABS(a.goodsChange),0) ELSE 0 END)backNum,(CASE WHEN a.type=9 AND g_cou>0 THEN IFNULL(ABS(a.goodsChange),0) ELSE 0 END)holdNum
FROM
  t_pcs_goodslog a 
  LEFT JOIN t_pcs_lading b 
    ON b.id = a.ladingId 
  LEFT JOIN t_pcs_client c 
    ON c.id = a.clientId 
  LEFT JOIN t_pcs_client g 
    ON g.id = a.ladingClientId 
    LEFT JOIN t_pcs_goods gb
    ON a.goodsId = gb.id
WHERE a.type <> 8 
  AND a.goodsId = l_goodsId
  AND DATE_FORMAT(FROM_UNIXTIME(a.createTime),'%Y-%m-%d')= g_curTime)temp GROUP BY nTime;
         SET i=i+1;
     END WHILE;
     SELECT * FROM temp_goods_info;
    END$$

DELIMITER ;

DROP TABLE IF EXISTS `t_sys_params`;
CREATE TABLE `t_sys_params` (
  `name` varchar(20) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `key` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_params
-- ----------------------------
INSERT INTO `t_sys_params` VALUES ('创建', 'LOGTYPE', '1');
INSERT INTO `t_sys_params` VALUES ('更新', 'LOGTYPE', '2');
INSERT INTO `t_sys_params` VALUES ('删除', 'LOGTYPE', '3');
INSERT INTO `t_sys_params` VALUES ('审核', 'LOGTYPE', '4');
INSERT INTO `t_sys_params` VALUES ('登陆', 'LOGTYPE', '5');
INSERT INTO `t_sys_params` VALUES ('退出', 'LOGTYPE', '6');
INSERT INTO `t_sys_params` VALUES ('上传', 'LOGTYPE', '7');
INSERT INTO `t_sys_params` VALUES ('用户', 'LOGOBJECT', 'auth_user');
INSERT INTO `t_sys_params` VALUES ('角色', 'LOGOBJECT', 'auth_role');
INSERT INTO `t_sys_params` VALUES ('权限', 'LOGOBJECT', 'auth_power');
INSERT INTO `t_sys_params` VALUES ('港务动态', 'LOGOBJECT', 'esb_harbor');
INSERT INTO `t_sys_params` VALUES ('港务船', 'LOGOBJECT', 'esb_ship');
INSERT INTO `t_sys_params` VALUES ('港务提单', 'LOGOBJECT', 'esb_bill');
INSERT INTO `t_sys_params` VALUES ('港务报文头', 'LOGOBJECT', 'esb_head');

DROP TRIGGER IF EXISTS `update_t_pcs_tank`;
DELIMITER ;;
CREATE TRIGGER `update_t_pcs_tank` AFTER UPDATE ON `t_pcs_tank` FOR EACH ROW BEGIN
 IF  new.description!=old.description OR new.productId!=old.productId THEN 
     INSERT INTO t_pcs_tank_clean_log (tankId,productId,description,editUserId,editTime) values (new.id,new.productId,new.description,new.editUserId,UNIX_TIMESTAMP(now())) ;
END IF;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `update_t_pcs_tube`;
DELIMITER ;;
CREATE TRIGGER `update_t_pcs_tube` AFTER UPDATE ON `t_pcs_tube` FOR EACH ROW BEGIN
 IF  new.description!=old.description OR new.productId!=old.productId THEN 
     INSERT INTO t_pcs_tube_clean_log (tubeId,productId,description,editUserId,editTime) values (new.id,new.productId,new.description,new.editUserId,UNIX_TIMESTAMP(now())) ;
END IF;
END
;;
DELIMITER ;


ALTER TABLE `t_auth_user`
MODIFY COLUMN `photo`  longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL AFTER `status`;

ALTER TABLE `t_pcs_store`
MODIFY COLUMN `differAmount`  decimal(15,3) NULL DEFAULT NULL AFTER `measureAmount`;

DROP PROCEDURE IF EXISTS `getStorageCost`;

CREATE PROCEDURE `getStorageCost`(IN l_sTime VARCHAR(20),IN l_eTime VARCHAR(20),IN l_goodsId INT)
BEGIN
	DECLARE i INT DEFAULT 0;
	DECLARE nTime VARCHAR(30);
	DECLARE g_code VARCHAR(30) ;
	DECLARE g_clientName VARCHAR(30) ;
	DECLARE g_ladingClientName VARCHAR(30) ;
	DECLARE g_currentNum VARCHAR(30) ;
	DECLARE g_overtimePrice VARCHAR(30) ;
	DECLARE g_cost VARCHAR(30) ;
	DECLARE g_curTime VARCHAR(30);
	DECLARE g_cou INT;
	DECLARE cou INT ;
	START TRANSACTION;
	SET cou =TO_DAYS(STR_TO_DATE(l_eTime,'%Y-%m-%d'))-TO_DAYS(STR_TO_DATE(l_sTime,'%Y-%m-%d')) ;
	CREATE TEMPORARY TABLE IF NOT EXISTS temp_goods_info(id INT AUTO_INCREMENT PRIMARY KEY,CODE VARCHAR(30),clientName VARCHAR(40),ladingName VARCHAR(40),l_time VARCHAR(30),deliverNum DECIMAL(10,3),exportNum DECIMAL(10,3),backNum DECIMAL(10,3),holdNum DECIMAL(10,3),currentNum DECIMAL(10,3),overtimePrice DECIMAL(10,3),cost DECIMAL(10,3)) ;
	      DELETE FROM temp_goods_info;
	WHILE i<=cou DO
	SELECT DATE_ADD(l_sTime,INTERVAL i DAY) INTO nTime FROM  DUAL ;
	SELECT   gb.code,c.name clientName,g.name ladingClientName,(ROUND((a.goodsSave+a.surplus),3)),(SELECT CASE WHEN overtimePrice IS NULL OR overtimePrice ='' THEN 0 ELSE overtimePrice END  FROM t_pcs_contract WHERE id=gb.contractId),(ROUND((a.goodsSave+a.surplus)*(SELECT CASE WHEN overtimePrice IS NULL OR overtimePrice ='' THEN 0 ELSE overtimePrice END FROM t_pcs_contract WHERE id=gb.contractId),3))INTO g_code,g_clientName,g_ladingClientName,g_currentNum,g_overtimePrice,g_cost
FROM
  t_pcs_goodslog a 
  LEFT JOIN t_pcs_lading b 
    ON b.id = a.ladingId 
  LEFT JOIN t_pcs_client c 
    ON c.id = a.clientId 
  LEFT JOIN t_pcs_client g 
    ON g.id = a.ladingClientId 
    LEFT JOIN t_pcs_goods gb
    ON a.goodsId = gb.id
WHERE a.type <> 8 
  AND a.goodsId = l_goodsId   
  AND a.id=(SELECT MAX(id) FROM t_pcs_goodslog WHERE goodsId=l_goodsId AND DATE_FORMAT(FROM_UNIXTIME(createTime),'%Y-%m-%d')=(SELECT MAX(DATE_FORMAT(FROM_UNIXTIME(createTime),'%Y-%m-%d')) FROM t_pcs_goodslog WHERE goodsId=l_goodsId AND TYPE<>8 AND DATE_FORMAT(FROM_UNIXTIME(createTime),'%Y-%m-%d') <=nTime ));
  SELECT COUNT(1) INTO g_cou FROM t_pcs_goodslog WHERE goodsId=l_goodsId AND DATE_FORMAT(FROM_UNIXTIME(createTime),'%Y-%m-%d')=(SELECT MAX(DATE_FORMAT(FROM_UNIXTIME(createTime),'%Y-%m-%d')) FROM t_pcs_goodslog WHERE goodsId=l_goodsId AND TYPE<>8 AND DATE_FORMAT(FROM_UNIXTIME(createTime),'%Y-%m-%d') =nTime );
  SELECT DATE_FORMAT(FROM_UNIXTIME(createTime),'%Y-%m-%d') INTO g_curTime FROM t_pcs_goodslog WHERE id=(SELECT MAX(id) FROM t_pcs_goodslog WHERE goodsId=l_goodsId AND DATE_FORMAT(FROM_UNIXTIME(createTime),'%Y-%m-%d')=(SELECT MAX(DATE_FORMAT(FROM_UNIXTIME(createTime),'%Y-%m-%d')) FROM t_pcs_goodslog WHERE goodsId=l_goodsId AND TYPE<>8 AND DATE_FORMAT(FROM_UNIXTIME(createTime),'%Y-%m-%d') <=nTime )) ;
  INSERT INTO temp_goods_info(CODE,clientName,ladingName,l_time,deliverNum,exportNum,backNum,holdNum,currentNum,overtimePrice,cost) SELECT g_code,g_clientName,g_ladingClientName,nTime,SUM(deliverNum) deliverNum,SUM(exportNum) exportNum,SUM(backNum) backNum,SUM(holdNum) holdNum,g_currentNum,IFNULL(g_overtimePrice,0),g_cost FROM (SELECT   nTime,(CASE WHEN a.type=5 AND g_cou>0 THEN IFNULL(ABS(a.goodsChange),0) ELSE 0 END)deliverNum,(CASE WHEN a.type=3 AND g_cou>0 THEN IFNULL(ABS(a.goodsChange),0) ELSE 0 END)exportNum,(CASE WHEN a.type=6 AND g_cou>0 THEN IFNULL(ABS(a.goodsChange),0) ELSE 0 END)backNum,(CASE WHEN a.type=9 AND g_cou>0 THEN IFNULL(ABS(a.goodsChange),0) ELSE 0 END)holdNum
FROM
  t_pcs_goodslog a 
  LEFT JOIN t_pcs_lading b 
    ON b.id = a.ladingId 
  LEFT JOIN t_pcs_client c 
    ON c.id = a.clientId 
  LEFT JOIN t_pcs_client g 
    ON g.id = a.ladingClientId 
    LEFT JOIN t_pcs_goods gb
    ON a.goodsId = gb.id
WHERE a.type <> 8 
  AND a.goodsId = l_goodsId
  AND DATE_FORMAT(FROM_UNIXTIME(a.createTime),'%Y-%m-%d')= g_curTime)temp GROUP BY nTime;
         SET i=i+1;
     END WHILE;
     COMMIT ;
     SELECT * FROM temp_goods_info;
    END;;
    
DROP PROCEDURE `showChildLst`;
CREATE PROCEDURE `showChildLst`(IN p_id INT)
    SQL SECURITY INVOKER
BEGIN
	
	DECLARE rootid INT;
		CREATE TEMPORARY TABLE IF NOT EXISTS tmpLst 
	       (sno INT PRIMARY KEY AUTO_INCREMENT,id INT,depth INT);
	      DELETE FROM tmpLst;
		SELECT rootGoodsId INTO rootid FROM t_pcs_goods WHERE id = p_id ;
	      CALL createChildLst(rootid,1);
	    
	      SELECT g.id,g.sourceGoodsId,g.code,(CASE WHEN g.ladingClientId is not null THEN d.name ELSE c.name END) name,(SELECT l.code FROM t_pcs_lading l WHERE l.id=gr.ladingId) ladingCode,
	      (SELECT CASE WHEN l.type='1' THEN '转卖' ELSE '发货' END FROM t_pcs_lading l WHERE l.id=gr.ladingId) ladingType,
	      g.goodsTotal,g.goodsIn,g.goodsOut,g.goodsCurrent,tmpLst.depth lever
		FROM tmpLst,
			t_pcs_goods g LEFT JOIN t_pcs_client c ON g.clientId = c.id  LEFT JOIN t_pcs_client d on g.ladingClientId=d.id
			LEFT JOIN t_pcs_goods_group gr ON g.goodsGroupId = gr.id WHERE tmpLst.id=g.id 
			ORDER BY tmpLst.sno;
END;

INSERT INTO `t_auth_security_resources` (category,description,name,indentifier,status) VALUES ('ACCESS', '查看作业计划的权限（获取通知）', '查看作业计划的权限（入库）', 'ACHECKINBOUNDPLAN', 0);
INSERT INTO `t_auth_security_resources` (category,description,name,indentifier,status)  VALUES ('ACCESS', '获悉接卸准备提交', '获悉接卸准备提交', 'AUPLOADINGCHECK', 0);
INSERT INTO `t_auth_security_resources` (category,description,name,indentifier,status)  VALUES ('ACCESS', '获悉回退消息权限', '获悉回退消息权限', 'AINBOUNDBACKMSG', 0);

ALTER TABLE `t_pcs_transport_program`
ADD COLUMN `noticeCodeA`  varchar(255) NULL AFTER `pumpInfo`;

ALTER TABLE `t_pcs_transport_program`
ADD COLUMN `noticeCodeB`  varchar(255) NULL  AFTER `noticeCodeA`;

ALTER TABLE `t_pcs_notify`
ADD COLUMN `batchId`  int(11) NULL AFTER `status`;




ALTER TABLE `sop`.`t_pcs_arrival_charge`   
  ADD COLUMN `overTime` DECIMAL(3,1) NULL AFTER `description`;
  
  ALTER TABLE `t_pcs_berth_assess`
MODIFY COLUMN `windDirection`  varchar(25aa) NULL DEFAULT NULL AFTER `weather`;

ALTER TABLE `t_auth_user`
DROP COLUMN `personId`,
DROP COLUMN `persionId`;

ALTER TABLE `t_pcs_berth_assess`
MODIFY COLUMN `weather`  varchar(25) NULL DEFAULT NULL AFTER `arrivalId`,
MODIFY COLUMN `windPower`  varchar(25) NULL DEFAULT NULL AFTER `windDirection`;

DROP PROCEDURE IF EXISTS `updateVehicleDeliverInfoForWeigh`;

CREATE  PROCEDURE `updateVehicleDeliverInfoForWeigh`(IN l_serialNo VARCHAR(30),IN g_dNum DECIMAL(12,3))
BEGIN
	  DECLARE t_status INT ;
	  DECLARE done TINYINT(1) DEFAULT 0;
	  DECLARE g_totalNum DOUBLE ;
	  DECLARE g_deliverNum DOUBLE ;
	  DECLARE g_batchId INT;
	  DECLARE g_id INT;
	  DECLARE g_goodsId INT;
	  DECLARE g_max DOUBLE ;
	  DECLARE mycursor CURSOR FOR SELECT id,deliverNum,goodsId FROM t_pcs_goodslog WHERE deliverType=1 AND batchId=g_batchId AND TYPE=5;
	  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
	  SELECT g_dNum,i.batchId INTO g_totalNum,g_batchId FROM t_pcs_weighbridge wb,t_pcs_goodslog i WHERE i.serial=wb.serial AND i.serial=l_serialNo;
	UPDATE t_pcs_train SET STATUS=43,flag=1 WHERE id=(SELECT batchId FROM t_pcs_goodslog WHERE deliverType=1 AND SERIAL=l_serialNo) ;
	OPEN mycursor ;
	REPEAT   --  分发实发数量
		FETCH mycursor INTO g_id,g_deliverNum,g_goodsId ;
		IF done=0 THEN
			IF g_totalNum-g_deliverNum>=0 THEN
				UPDATE t_pcs_goodslog SET actualNum=g_deliverNum WHERE id = g_id ;
				SET g_totalNum = g_totalNum-g_deliverNum ;
				UPDATE   t_pcs_lading l SET l.goodsDelivery = goodsDelivery + g_deliverNum WHERE l.id = (SELECT ladingId FROM t_pcs_goods_group 
					  WHERE id = (SELECT goodsGroupId FROM t_pcs_goods WHERE id =g_id));
			ELSE 
				UPDATE t_pcs_goods SET goodsCurrent=goodsCurrent+g_deliverNum-g_totalNum  WHERE id=g_goodsId;-- 还回
				UPDATE   t_pcs_goodslog  t SET  actualNum = g_totalNum,surplus = (SELECT  IFNULL(ROUND(g.goodsCurrent - ( g.goodsTotal - (CASE WHEN g.goodsInPass - g.goodsOutPass >= 0 THEN g.goodsOutPass  ELSE g.goodsInPass  END ) ),3),0)FROM t_pcs_goods g WHERE g.id=g_goodsId),goodsChange=-g_totalNum WHERE id=g_id ;	
				UPDATE   t_pcs_lading l SET l.goodsDelivery = goodsDelivery + g_totalNum WHERE l.id = (SELECT ladingId FROM t_pcs_goods_group 
					  WHERE id = (SELECT goodsGroupId FROM t_pcs_goods WHERE id =g_goodsId));
				SET g_totalNum =0 ;
			END IF ;
		END IF;
		UNTIL done END REPEAT;
	CLOSE mycursor ;
    END;

ALTER TABLE `sop`.`t_pcs_goodslog`   
  ADD COLUMN `tankId` INT NULL AFTER `afDiffNum`;

  ALTER TABLE `sop`.`t_pcs_weighbridge`   
  ADD COLUMN `tankId` INT NULL AFTER `actualTareWeight`;

INSERT INTO `t_auth_security_resources` (`category`, `description`, `name`, `indentifier`, `status`) VALUES('MENU','称重信息统计','称重信息统计','WEIGHDAILYSTATEMENT','0');

INSERT INTO t_auth_resource_assignments(roleid,sourceid) SELECT 1,id FROM t_auth_security_resources WHERE id NOT IN (SELECT sourceid FROM t_auth_resource_assignments WHERE roleid = 1);
