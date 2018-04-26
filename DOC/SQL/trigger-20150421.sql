/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.5.19 : Database - sop
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/* Trigger structure for table `t_pcs_approve` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `insert_pcs_approve` */$$

/*!50003 CREATE */ /*!50017  */ /*!50003 TRIGGER `insert_pcs_approve` BEFORE INSERT ON `t_pcs_approve` FOR EACH ROW BEGIN
	IF new.modelId=1 AND new.status=1 THEN 
	    INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(NAME ,'提交了一份车发开票单，请您审批。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.checkUserId,1,CONCAT('#/outboundtruckserial/get?status=',(SELECT STATUS FROM t_pcs_train WHERE id=new.refId),'&id=',(SELECT id FROM t_pcs_train WHERE id=new.refId)) FROM t_auth_user WHERE id=new.checkUserId ;
	    INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT 
	  DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1
	FROM
	  `t_auth_authorization` u ,
	  `t_auth_security_resources` t,
	  `t_auth_resource_assignments` ta 
	WHERE t.indentifier = 'ABLLINGVERIFY' 
	  AND ta.sourceId = t.id 
	  AND ta.roleId = u.roleId; 
	ELSEIF new.modelId=2 AND new.status=1 THEN
	    INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(NAME ,'提交了一份车发作业单，请您审批。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.checkUserId,1,CONCAT('#/outboundtruckserial/get?status=',(SELECT STATUS FROM t_pcs_train WHERE id=new.refId),'&id=',(SELECT id FROM t_pcs_train WHERE id=new.refId)) FROM t_auth_user WHERE id=new.checkUserId ;
	    INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT 
	  DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1
	FROM
	  `t_auth_authorization` u ,
	  `t_auth_security_resources` t,
	  `t_auth_resource_assignments` ta 
	WHERE t.indentifier = 'ACARDELIVERYWORKVERIFY' 
	  AND ta.sourceId = t.id 
	  AND ta.roleId = u.roleId; 
	ELSEIF new.modelId=3 AND new.status=1 THEN
	    INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(NAME ,'提交了一份车发数据确认单，请您审批。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.checkUserId,1,CONCAT('#/outboundtruckserial/get?status=',(SELECT STATUS FROM t_pcs_train WHERE id=new.refId),'&id=',(SELECT id FROM t_pcs_train WHERE id=new.refId)) FROM t_auth_user WHERE id=new.checkUserId ;
	    INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT 
	  DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1
	FROM
	  `t_auth_authorization` u ,
	  `t_auth_security_resources` t,
	  `t_auth_resource_assignments` ta 
	WHERE t.indentifier = 'ACARAMOUNTVERIFY' 
	  AND ta.sourceId = t.id 
	  AND ta.roleId = u.roleId; 
	ELSEIF new.modelId=4 AND new.status=1 THEN
	    INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(NAME ,'提交了一份船发数据确认单，请您审批。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.checkUserId,1,CONCAT('#/outboundserial/get?status=',(SELECT STATUS FROM t_pcs_arrival WHERE id=new.refId),'&id=',(SELECT id FROM t_pcs_arrival WHERE id=new.refId)) FROM t_auth_user WHERE id=new.checkUserId ;
	    INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT 
	  DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1
	FROM
	  `t_auth_authorization` u ,
	  `t_auth_security_resources` t,
	  `t_auth_resource_assignments` ta 
	WHERE t.indentifier = 'AOUTBOUNDAMOUNTVERIFY'
	  AND ta.sourceId = t.id 
	  AND ta.roleId = u.roleId; 
	END IF;
    END */$$


DELIMITER ;

/* Trigger structure for table `t_pcs_approve` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `update_pcs_approve` */$$

/*!50003 CREATE */ /*!50017  */ /*!50003 TRIGGER `update_pcs_approve` AFTER UPDATE ON `t_pcs_approve` FOR EACH ROW BEGIN
    IF new.modelId=1 THEN
	IF new.status=1 THEN 
	    INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(NAME ,'提交了一份车发开票，货主为【',(SELECT c.name FROM t_pcs_train t LEFT JOIN t_pcs_client c ON t.clientId=c.id WHERE t.id=new.refId),'】，货品为【',(SELECT p.name FROM t_pcs_train t LEFT JOIN t_pcs_product p ON p.id=t.productId WHERE t.id=new.refId),'】，发货量为【',(SELECT SUM(deliverNum) FROM t_pcs_goodslog WHERE deliverType = 1 AND batchId = new.refId),'】，请您审批。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.checkUserId,1,CONCAT('#/outboundtruckserial/get?status=',(SELECT STATUS FROM t_pcs_train WHERE id=new.refId),'&id=',(SELECT id FROM t_pcs_train WHERE id=new.refId)) FROM t_auth_user WHERE id=new.checkUserId ;
	    INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT 
	  DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1
	FROM
	  `t_auth_authorization` u ,
	  `t_auth_security_resources` t,
	  `t_auth_resource_assignments` ta 
	WHERE t.indentifier = 'ABLLINGVERIFY' 
	  AND ta.sourceId = t.id 
	  AND ta.roleId = u.roleId;
	 ELSEIF new.status=2 THEN
	    INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT('您提交的车发开票，货主为【',(SELECT c.name FROM t_pcs_train t LEFT JOIN t_pcs_client c ON t.clientId=c.id WHERE t.id=new.refId),'】，货品为【',(SELECT p.name FROM t_pcs_train t LEFT JOIN t_pcs_product p ON p.id=t.productId WHERE t.id=new.refId),'】，发货量为【',(SELECT SUM(deliverNum) FROM t_pcs_goodslog WHERE deliverType = 1 AND batchId = new.refId),'】 已审批已通过，请知悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.checkUserId,1,CONCAT('#/outboundtruckserial/get?status=',(SELECT STATUS FROM t_pcs_train WHERE id=new.refId),'&id=',(SELECT id FROM t_pcs_train WHERE id=new.refId)) FROM t_auth_user WHERE id=new.checkUserId ;
	    INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT new.checkUserId,MAX(id),1 FROM t_pcs_message_content ;
	 ELSEIF new.status=3 THEN
	    INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT('您提交的车发开票，货主为【',(SELECT c.name FROM t_pcs_train t LEFT JOIN t_pcs_client c ON t.clientId=c.id WHERE t.id=new.refId),'】，货品为【',(SELECT p.name FROM t_pcs_train t LEFT JOIN t_pcs_product p ON p.id=t.productId WHERE t.id=new.refId),'】，发货量为【',(SELECT t.approveAmount FROM t_pcs_train t WHERE t.id=new.refId),'】 已被退回，请知悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.checkUserId,1,CONCAT('#/outboundtruckserial/get?status=',(SELECT STATUS FROM t_pcs_train WHERE id=new.refId),'&id=',(SELECT id FROM t_pcs_train WHERE id=new.refId)) FROM t_auth_user WHERE id=new.checkUserId ;
	    INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT new.checkUserId,MAX(id),1 FROM t_pcs_message_content ;
	END IF;
    ELSEIF new.modelId=2 THEN
    IF new.status=1 AND new.status!=old.status THEN 
	    INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(NAME ,'提交了一份车发作业，,货主为【',(SELECT c.name FROM t_pcs_train t LEFT JOIN t_pcs_client c ON t.clientId=c.id WHERE t.id=new.refId),'】，货品为【',(SELECT p.name FROM t_pcs_train t LEFT JOIN t_pcs_product p ON p.id=t.productId WHERE t.id=new.refId),'】，发货量为【',(SELECT t.approveAmount FROM t_pcs_train t WHERE t.id=new.refId),'】请您审批。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.checkUserId,1,CONCAT('#/outboundtruckserial/get?status=',(SELECT STATUS FROM t_pcs_train WHERE id=new.refId),'&id=',(SELECT id FROM t_pcs_train WHERE id=new.refId)) FROM t_auth_user WHERE id=new.checkUserId ;
	    INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT 
	  DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1
	FROM
	  `t_auth_authorization` u ,
	  `t_auth_security_resources` t,
	  `t_auth_resource_assignments` ta 
	WHERE t.indentifier = 'ACARDELIVERYWORKVERIFY' 
	  AND ta.sourceId = t.id 
	  AND ta.roleId = u.roleId;
	 ELSEIF new.status=2 AND new.status!=old.status THEN
	    INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT('您提交的车发作业审批，货主为【',(SELECT c.name FROM t_pcs_train t LEFT JOIN t_pcs_client c ON t.clientId=c.id WHERE t.id=new.refId),'】，货品为【',(SELECT p.name FROM t_pcs_train t LEFT JOIN t_pcs_product p ON p.id=t.productId WHERE t.id=new.refId),'】，发货量为【',(SELECT t.approveAmount FROM t_pcs_train t WHERE t.id=new.refId),'】已通过，请知悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.checkUserId,1,CONCAT('#/outboundtruckserial/get?status=',(SELECT STATUS FROM t_pcs_train WHERE id=new.refId),'&id=',(SELECT id FROM t_pcs_train WHERE id=new.refId)) FROM t_auth_user WHERE id=new.checkUserId ;
	    INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT new.checkUserId,MAX(id),1 FROM t_pcs_message_content ;
	 ELSEIF new.status=3 AND new.status!=old.status THEN
	    INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT('您提交的车发作业，货主为【',(SELECT c.name FROM t_pcs_train t LEFT JOIN t_pcs_client c ON t.clientId=c.id WHERE t.id=new.refId),'】，货品为【',(SELECT p.name FROM t_pcs_train t LEFT JOIN t_pcs_product p ON p.id=t.productId WHERE t.id=new.refId),'】，发货量为【',(SELECT t.approveAmount FROM t_pcs_train t WHERE t.id=new.refId),'】已被退回，请知悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.checkUserId,1,CONCAT('#/outboundtruckserial/get?status=',(SELECT STATUS FROM t_pcs_train WHERE id=new.refId),'&id=',(SELECT id FROM t_pcs_train WHERE id=new.refId)) FROM t_auth_user WHERE id=new.checkUserId ;
	    INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT new.checkUserId,MAX(id),1 FROM t_pcs_message_content ;
	END IF;
    ELSEIF new.modelId=3 THEN
    IF new.status=1 AND new.status!=old.status THEN 
	    INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(NAME ,'提交了份车发确认数据，货主为【',(SELECT c.name FROM t_pcs_train t LEFT JOIN t_pcs_client c ON t.clientId=c.id WHERE t.id=new.refId),'】，货品为【',(SELECT p.name FROM t_pcs_train t LEFT JOIN t_pcs_product p ON p.id=t.productId WHERE t.id=new.refId),'】，发货量为【',(SELECT t.approveAmount FROM t_pcs_train t WHERE t.id=new.refId),'】，请您审批。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.checkUserId,1,CONCAT('#/outboundtruckserial/get?status=',(SELECT STATUS FROM t_pcs_train WHERE id=new.refId),'&id=',(SELECT id FROM t_pcs_train WHERE id=new.refId)) FROM t_auth_user WHERE id=new.checkUserId ;
	    INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT 
	  DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1
	FROM
	  `t_auth_authorization` u ,
	  `t_auth_security_resources` t,
	  `t_auth_resource_assignments` ta 
	WHERE t.indentifier = 'ACARAMOUNTVERIFY' 
	  AND ta.sourceId = t.id 
	  AND ta.roleId = u.roleId; 
	 ELSEIF new.status=2 AND new.status!=old.status THEN
	    INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT('您提交的车发确认数据，货主为【',(SELECT c.name FROM t_pcs_train t LEFT JOIN t_pcs_client c ON t.clientId=c.id WHERE t.id=new.refId),'】，货品为【',(SELECT p.name FROM t_pcs_train t LEFT JOIN t_pcs_product p ON p.id=t.productId WHERE t.id=new.refId),'】，发货量为【',(SELECT t.approveAmount FROM t_pcs_train t WHERE t.id=new.refId),'】审批已通过，请知悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.checkUserId,1,CONCAT('#/outboundtruckserial/get?status=',(SELECT STATUS FROM t_pcs_train WHERE id=new.refId),'&id=',(SELECT id FROM t_pcs_train WHERE id=new.refId)) FROM t_auth_user WHERE id=new.checkUserId ;
	    INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT new.checkUserId,MAX(id),1 FROM t_pcs_message_content ;
	 ELSEIF new.status=3 AND new.status!=old.status THEN
	    INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT('您提交的车发确认数据，货主为【',(SELECT c.name FROM t_pcs_train t LEFT JOIN t_pcs_client c ON t.clientId=c.id WHERE t.id=new.refId),'】，货品为【',(SELECT p.name FROM t_pcs_train t LEFT JOIN t_pcs_product p ON p.id=t.productId WHERE t.id=new.refId),'】，发货量为【',(SELECT t.approveAmount FROM t_pcs_train t WHERE t.id=new.refId),'】已被退回，请知悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.checkUserId,1,CONCAT('#/outboundtruckserial/get?status=',(SELECT STATUS FROM t_pcs_train WHERE id=new.refId),'&id=',(SELECT id FROM t_pcs_train WHERE id=new.refId)) FROM t_auth_user WHERE id=new.checkUserId ;
	    INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT new.checkUserId,MAX(id),1 FROM t_pcs_message_content ;
	END IF;
    ELSEIF new.modelId=4 THEN
    IF new.status=1 AND new.status!=old.status THEN 
	    INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(NAME ,'提交了一份船发确认数量，船名为【',(SELECT s.name FROM t_pcs_arrival a LEFT JOIN t_pcs_ship s ON a.shipId=s.id WHERE a.id=new.refId),'】-【',(SELECT s.refName FROM t_pcs_arrival a LEFT JOIN t_pcs_ship_ref s ON s.id=a.shipRefId WHERE a.id=new.refId),'】，请您审批。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.checkUserId,1,CONCAT('#/outboundserial/get?status=',(SELECT STATUS FROM t_pcs_arrival WHERE id=new.refId),'&id=',(SELECT id FROM t_pcs_arrival WHERE id=new.refId)) FROM t_auth_user WHERE id=new.checkUserId ;
	    INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT 
	  DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1
	FROM
	  `t_auth_authorization` u ,
	  `t_auth_security_resources` t,
	  `t_auth_resource_assignments` ta 
	WHERE t.indentifier = 'AOUTBOUNDAMOUNTVERIFY'
	  AND ta.sourceId = t.id 
	  AND ta.roleId = u.roleId;
	 ELSEIF new.status=2 AND new.status!=old.status THEN
	    INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT('您提交的船发确认数据，船名为【',(SELECT s.name FROM t_pcs_arrival a LEFT JOIN t_pcs_ship s ON a.shipId=s.id WHERE a.id=new.refId),'】-【',(SELECT s.refName FROM t_pcs_arrival a LEFT JOIN t_pcs_ship_ref s ON s.id=a.shipRefId WHERE a.id=new.refId),'】，发货量为【',(SELECT IFNULL(SUM(goodsTotal),0) FROM t_pcs_arrival_plan t WHERE t.arrivalId=new.refId),'】审批已通过，请知悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.checkUserId,1,CONCAT('#/outboundserial/get?status=',(SELECT STATUS FROM t_pcs_arrival WHERE id=new.refId),'&id=',(SELECT id FROM t_pcs_arrival WHERE id=new.refId)) FROM t_auth_user WHERE id=new.checkUserId ;
	    INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT new.checkUserId,MAX(id),1 FROM t_pcs_message_content ;
	 ELSEIF new.status=3 AND new.status!=old.status THEN
	    INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT('您提交的船发确认数据，船名为【',(SELECT s.name FROM t_pcs_arrival a LEFT JOIN t_pcs_ship s ON a.shipId=s.id WHERE a.id=new.refId),'】-【',(SELECT s.refName FROM t_pcs_arrival a LEFT JOIN t_pcs_ship_ref s ON s.id=a.shipRefId WHERE a.id=new.refId),'】，发货量为【',(SELECT IFNULL(SUM(goodsTotal),0) FROM t_pcs_arrival_plan t WHERE t.arrivalId=new.refId),'】已被退回，请知悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.checkUserId,1,CONCAT('#/outboundserial/get?status=',(SELECT STATUS FROM t_pcs_arrival WHERE id=new.refId),'&id=',(SELECT id FROM t_pcs_arrival WHERE id=new.refId)) FROM t_auth_user WHERE id=new.checkUserId ;
	    INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT new.checkUserId,MAX(id),1 FROM t_pcs_message_content ;
	END IF;
    END IF;
	 
    END */$$


DELIMITER ;

/* Trigger structure for table `t_pcs_arrival` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `insertArrival` */$$

/*!50003 CREATE */ /*!50017  */ /*!50003 TRIGGER `insertArrival` BEFORE INSERT ON `t_pcs_arrival` FOR EACH ROW BEGIN
	DECLARE aType INT ;
	DECLARE url VARCHAR(60) ;
	SELECT TYPE INTO aType FROM t_pcs_arrival WHERE id=new.id ;
	IF aType=1 THEN
	 INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'添加了[',b.refName,']的到港信息，请知悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,'#/arrival' FROM t_auth_user a LEFT JOIN t_pcs_ship_ref b ON b.id=new.shipRefId WHERE a.id=new.createUserId ;
	    INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT 
	  DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1
	FROM
	  `t_auth_authorization` u ,
	  `t_auth_security_resources` t,
	  `t_auth_resource_assignments` ta 
	WHERE t.indentifier = 'MARRIVALPLAN' 
	  AND ta.sourceId = t.id 
	  AND ta.roleId = u.roleId; 
	ELSE
	INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'添加了[',b.refName,']的到港信息，请知悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,'#/shipArrival' FROM t_auth_user a LEFT JOIN t_pcs_ship_ref b ON b.id=new.shipRefId WHERE a.id=new.createUserId ;
	    INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT 
	  DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 
	FROM
	  `t_auth_authorization` u ,
	  `t_auth_security_resources` t, 
	  `t_auth_resource_assignments` ta 
	WHERE t.indentifier = 'MOUTBOUNDPLAN' 
	  AND ta.sourceId = t.id 
	  AND ta.roleId = u.roleId; 
	END IF ;
END */$$


DELIMITER ;

/* Trigger structure for table `t_pcs_arrival` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `sureArrival` */$$

/*!50003 CREATE */ /*!50017  */ /*!50003 TRIGGER `sureArrival` AFTER UPDATE ON `t_pcs_arrival` FOR EACH ROW begin
    IF  new.status>old.status and new.status=2 THEN 
	    INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'确认了[',b.refName,']的到港信息，请知悉。'),UNIX_TIMESTAMP(date_format(NOW(),'%Y-%m-%d %H:%i:%s')),new.reviewArrivalUserId,1,concat('#/inboundoperation') FROM t_auth_user a LEFT JOIN t_pcs_ship_ref b on b.id=new.shipRefId WHERE a.id=new.reviewArrivalUserId ;
	    insert into t_pcs_message_get(getUserId,messageId,status) select 4,max(id),1 from t_pcs_message_content ;
    ELSEIF new.status!=old.status and new.status=10 THEN 
	    INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'确认了[',b.refName,']的货批数量，请点击查看。'),UNIX_TIMESTAMP(date_format(NOW(),'%Y-%m-%d %H:%i:%s')),new.reviewUserId,1,concat('#/storage') FROM t_auth_user a LEFT JOIN t_pcs_ship_ref b on b.id=new.shipRefId WHERE a.id=new.reviewUserId ;
	    insert into t_pcs_message_get(getUserId,messageId,status) select 4,max(id),1 from t_pcs_message_content ;	      
 END IF; 
end */$$


DELIMITER ;

/* Trigger structure for table `t_pcs_arrival_bill` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `insert_pcs_bill` */$$

/*!50003 CREATE */ /*!50017  */ /*!50003 TRIGGER `insert_pcs_bill` BEFORE INSERT ON `t_pcs_arrival_bill` FOR EACH ROW begin
    DECLARE billid INT;
    SELECT AUTO_INCREMENT INTO billid FROM INFORMATION_SCHEMA.TABLES    
	WHERE TABLE_NAME='t_pcs_arrival_bill' ;
    INSERT INTO t_pcs_arrival_charge(billId) VALUES(billid) ;
    set new.createTime = now();
end */$$


DELIMITER ;

/* Trigger structure for table `t_pcs_arrival_info` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `update_t_pcs_arrival_info` */$$

/*!50003 CREATE */ /*!50017  */ /*!50003 TRIGGER `update_t_pcs_arrival_info` BEFORE UPDATE ON `t_pcs_arrival_info` FOR EACH ROW BEGIN
	IF  new.status!=old.status and new.status=1 THEN 
 INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'提交了一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的作业计划，请您获悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,CONCAT('#/inboundoperation/get?arrivalId=',new.arrivalId,'&state=',1) FROM t_auth_user a LEFT JOIN t_pcs_arrival b on  b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c  on c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d  on d.id=b.shipRefId WHERE  a.id=new.createUserId ;
INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT   DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 FROM  `t_auth_authorization` u ,  `t_auth_security_resources` t,  `t_auth_resource_assignments` ta WHERE t.indentifier = 'ACHECKINBOUNDPLAN'   AND ta.sourceId = t.id   AND ta.roleId = u.roleId; 
ELSEIF old.status=1 and new.status=1 THEN
 INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'修改了一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的作业计划，请您获悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,CONCAT('#/inboundoperation/get?arrivalId=',new.arrivalId,'&state=',1) FROM t_auth_user a LEFT JOIN t_pcs_arrival b on  b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c  on c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d  on d.id=b.shipRefId WHERE  a.id=new.createUserId ;
INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT   DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 FROM  `t_auth_authorization` u ,  `t_auth_security_resources` t,  `t_auth_resource_assignments` ta WHERE t.indentifier = 'ACHECKINBOUNDPLAN'   AND ta.sourceId = t.id   AND ta.roleId = u.roleId; 
 END IF;
END */$$


DELIMITER ;

/* Trigger structure for table `t_pcs_arrival_plan` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `insert_pcs_arrival_plan` */$$

/*!50003 CREATE */ /*!50017  */ /*!50003 TRIGGER `insert_pcs_arrival_plan` BEFORE INSERT ON `t_pcs_arrival_plan` FOR EACH ROW begin
    set new.createTime = now();
end */$$


DELIMITER ;

/* Trigger structure for table `t_pcs_berth_assess` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `insert_pcs_berth_assess` */$$

/*!50003 CREATE */ /*!50017  */ /*!50003 TRIGGER `insert_pcs_berth_assess` BEFORE INSERT ON `t_pcs_berth_assess` FOR EACH ROW BEGIN
DECLARE aType INT ;
   DECLARE url VARCHAR(60) ;
   DECLARE u_right VARCHAR(30);
      DECLARE aStatus INT ;
   SELECT TYPE,STATUS INTO aType,aStatus FROM t_pcs_arrival WHERE id=new.arrivalId ;
   IF aType=1 THEN
		SET url=CONCAT('#/inboundoperation/get?arrivalId=',new.arrivalId,'&state=',2) ;
		SET u_right = 'AINBOUNDASSESSVERIFY' ;
   ELSEIF aType=2 THEN
		SET url=CONCAT('#/outboundserial/get?status=51&id=',new.arrivalId) ;
		SET u_right = 'AOUTBOUNDASSESSVERIFY' ;
   END IF ;
  IF new.reviewStatus=1 THEN 
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'提交了一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的靠泊评估，请您审批。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,url FROM t_auth_user a LEFT JOIN t_pcs_arrival b ON  b.id=new.arrivalId  
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
    END IF;
    END */$$


DELIMITER ;

/* Trigger structure for table `t_pcs_berth_assess` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `update_pcs_berth_assess` */$$

/*!50003 CREATE */ /*!50017  */ /*!50003 TRIGGER `update_pcs_berth_assess` AFTER UPDATE ON `t_pcs_berth_assess` FOR EACH ROW BEGIN
  DECLARE aType INT ;
   DECLARE url VARCHAR(60) ;
   DECLARE aStatus INT ;
   DECLARE u_right VARCHAR(30);
   SELECT TYPE,STATUS INTO aType,aStatus FROM t_pcs_arrival WHERE id=new.arrivalId ;
   IF aType=1 THEN
		SET url=CONCAT('#/inboundoperation/get?arrivalId=',new.arrivalId,'&state=',2);
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
    END */$$


DELIMITER ;

/* Trigger structure for table `t_pcs_berth_program` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `insert_pcs_berth_program` */$$

/*!50003 CREATE */ /*!50017  */ /*!50003 TRIGGER `insert_pcs_berth_program` BEFORE INSERT ON `t_pcs_berth_program` FOR EACH ROW BEGIN
DECLARE aType INT ;
   DECLARE url VARCHAR(60) ;
   DECLARE u_right VARCHAR(30);
      DECLARE aStatus INT ;
   SELECT TYPE,STATUS INTO aType,aStatus FROM t_pcs_arrival WHERE id=new.arrivalId ;
   IF aType=1 THEN
		SET url=CONCAT('#/inboundoperation/get?arrivalId=',new.arrivalId)  ;
		SET u_right = 'AINBOUNDBERTHINGVERIFY' ;
   ELSEIF aType=2 THEN
		SET url=CONCAT('#/outboundserial/get?status=51&id=',new.arrivalId) ;
		SET u_right = 'AOUTBOUNDBERTHINGVERIFY' ;
   END IF ;
  IF new.status=1 THEN 
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
    END IF;
    END */$$


DELIMITER ;

/* Trigger structure for table `t_pcs_berth_program` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `update_pcs_berth_program` */$$

/*!50003 CREATE */ /*!50017  */ /*!50003 TRIGGER `update_pcs_berth_program` AFTER UPDATE ON `t_pcs_berth_program` FOR EACH ROW BEGIN
       DECLARE aType INT ;
   DECLARE url VARCHAR(60) ;
   DECLARE u_right VARCHAR(30);
   SELECT TYPE INTO aType FROM t_pcs_arrival WHERE id=new.arrivalId ;
   IF aType=1 THEN
		SET url=CONCAT('#/inboundoperation/get?arrivalId=',new.arrivalId,'&state=',2) ;
		SET u_right = 'AINBOUNDBERTHINGVERIFY' ;
   ELSEIF aType=2 THEN
		SET url=CONCAT('#/outboundserial/get?status=51&id=',new.arrivalId) ;
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
    END */$$


DELIMITER ;

/* Trigger structure for table `t_pcs_client` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `update_pcs_client` */$$

/*!50003 CREATE */ /*!50017  */ /*!50003 TRIGGER `update_pcs_client` AFTER UPDATE ON `t_pcs_client` FOR EACH ROW BEGIN
	INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'在[',DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'),']更新了客户的基本信息。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.editUserId,2,CONCAT('#/baseinfo/list?id=',new.id) FROM t_auth_user a WHERE a.id=new.editUserId ;
	INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT 4,MAX(id),1 FROM t_pcs_message_content ;
    END */$$


DELIMITER ;

/* Trigger structure for table `t_pcs_contract` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `insert_pcs_contract` */$$

/*!50003 CREATE */ /*!50017  */ /*!50003 TRIGGER `insert_pcs_contract` BEFORE INSERT ON `t_pcs_contract` FOR EACH ROW BEGIN
    DECLARE msgContentId INT;
    IF new.status=1 THEN 
	    INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(NAME ,'提交了一份[',new.code,'][',new.title,']合同，请您审批。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,CONCAT('#/contractGet?id=',new.id) FROM t_auth_user WHERE id=new.createUserId ;
	    INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT 
  DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1
FROM
  `t_auth_authorization` u ,
  `t_auth_security_resources` t,
  `t_auth_resource_assignments` ta 
WHERE t.indentifier = 'ACONTRACTVERIFY' 
  AND ta.sourceId = t.id 
  AND ta.roleId = u.roleId ;
	END IF;
    SET new.createTime = NOW();
END */$$


DELIMITER ;

/* Trigger structure for table `t_pcs_contract` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `update_pcs_contract` */$$

/*!50003 CREATE */ /*!50017  */ /*!50003 TRIGGER `update_pcs_contract` BEFORE UPDATE ON `t_pcs_contract` FOR EACH ROW begin
    declare msgContentId int;
    IF new.status=1 THEN 
	    INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(NAME ,'提交了一份[',new.code,'][',new.title,']合同，请您审批。'),UNIX_TIMESTAMP(date_format(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,concat('#/contractGet?id=',new.id) FROM t_auth_user WHERE id=new.createUserId ;
	    insert into t_pcs_message_get(getUserId,messageId,status) select 4,max(id),1 from t_pcs_message_content ;
	END IF;
    IF new.status=2 THEN 
	    INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(NAME ,'[',new.code,'][',new.title,']合同已通过审批，请知悉。'),UNIX_TIMESTAMP(date_format(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,concat('#/contractGet?id=',new.id) FROM t_auth_user WHERE id=new.createUserId ;
	    insert into t_pcs_message_get(getUserId,messageId,status) select 4,max(id),1 from t_pcs_message_content ;
	END IF;
    IF new.status=3 THEN 
	    INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(NAME ,'[',new.code,'][',new.title,']合同的审批被退回，请知悉。'),UNIX_TIMESTAMP(date_format(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,concat('#/contractGet?id=',new.id) FROM t_auth_user WHERE id=new.createUserId ;
	    insert into t_pcs_message_get(getUserId,messageId,status) select 4,max(id),1 from t_pcs_message_content ;
	END IF;
    set new.createTime = now();
end */$$


DELIMITER ;

/* Trigger structure for table `t_pcs_intention` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `insert_pcs_intention` */$$

/*!50003 CREATE */ /*!50017  */ /*!50003 TRIGGER `insert_pcs_intention` BEFORE INSERT ON `t_pcs_intention` FOR EACH ROW begin
    set new.createTime = now();
end */$$


DELIMITER ;

/* Trigger structure for table `t_pcs_lading` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `insertLading` */$$

/*!50003 CREATE */ /*!50017  */ /*!50003 TRIGGER `insertLading` BEFORE INSERT ON `t_pcs_lading` FOR EACH ROW BEGIN
   
	    INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'添加了发货单位为[',c.name,']提货单位为[',d.name,']的提单，请点击查看。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,CONCAT('#/ladingEdit?id=',new.id) FROM t_auth_user a  LEFT JOIN t_pcs_client c ON c.id=new.clientId  LEFT JOIN t_pcs_client d ON d.id=new.receiveClientId WHERE a.id=new.createUserId ;
	    INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT 
	  DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 
	FROM 
	  `t_auth_authorization` u ,
	  `t_auth_security_resources` t,
	  `t_auth_resource_assignments` ta 
	WHERE t.indentifier = 'MLADING' 
	  AND ta.sourceId = t.id 
	  AND ta.roleId = u.roleId; 
END */$$


DELIMITER ;

/* Trigger structure for table `t_pcs_tank` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `update_t_pcs_tank` */$$

/*!50003 CREATE */ /*!50017  */ /*!50003 TRIGGER `update_t_pcs_tank` AFTER UPDATE ON `t_pcs_tank` FOR EACH ROW BEGIN
 IF  new.description!=old.description OR new.productId!=old.productId THEN 
     INSERT INTO t_pcs_tank_clean_log (tankId,productId,description,editUserId,editTime) values (new.id,new.productId,new.description,new.editUserId,UNIX_TIMESTAMP(now())) ;
END IF;
END */$$


DELIMITER ;

/* Trigger structure for table `t_pcs_transport_program` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `update_pcs_transport_program` */$$

/*!50003 CREATE */ /*!50017  */ /*!50003 TRIGGER `update_pcs_transport_program` AFTER UPDATE ON `t_pcs_transport_program` FOR EACH ROW BEGIN
  IF new.status!=old.status and new.status=1 THEN 
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'提交了一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的[',e.name,']的',case when new.type=0 then '接卸'  when new.type=1 then '打循环' end,'方案，请您审批。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,CONCAT('#/inboundoperation/get?arrivalId=',new.arrivalId,'&productId=',new.productId,'&state=',case when new.type=0 then 3  when new.type=1 then 5 end)  FROM t_auth_user a LEFT JOIN t_pcs_arrival b on  b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c  on c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d  on d.id=b.shipRefId LEFT JOIN t_pcs_product e on new.productId=e.id  WHERE  a.id=new.createUserId ;
INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT   DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 FROM  `t_auth_authorization` u ,  `t_auth_security_resources` t,  `t_auth_resource_assignments` ta WHERE t.indentifier in ( 'AUNLOUNDINGPROGRAMVERIFYQUALITY','AUNLOUNDINGPROGRAMVERIFYCRAFT','AUNLOUNDINGPROGRAMVERIFY')  AND ta.sourceId = t.id   AND ta.roleId = u.roleId; 
  ELSEIF new.status!=old.status and new.status=2 then
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'提交的一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的[',e.name,']的',case when new.type=0 then '接卸'  when new.type=1 then '打循环' end,'方案审批已通过，请知悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,CONCAT('#/inboundoperation/get?arrivalId=',new.arrivalId,'&productId=',new.productId,'&state=',case when new.type=0 then 3  when new.type=1 then 5 end)  FROM t_auth_user a LEFT JOIN t_pcs_arrival b on   b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c on c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d on d.id=b.shipRefId LEFT JOIN t_pcs_product e on new.productId=e.id   WHERE  a.id=new.createUserId  ;
INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT   DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 FROM  `t_auth_authorization` u ,  `t_auth_security_resources` t,  `t_auth_resource_assignments` ta WHERE t.indentifier = 'AUNLOUNDINGPROGRAMUPDATE'  AND ta.sourceId = t.id   AND ta.roleId = u.roleId; 
  ELSEIF new.status!=old.status and new.status=3 THEN
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'提交的一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的[',e.name,']的',case when new.type=0 then '接卸'  when new.type=1 then '打循环' end,'方案审批已退回，请知悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,CONCAT('#/inboundoperation/get?arrivalId=',new.arrivalId,'&productId=',new.productId,'&state=',case when new.type=0 then 3  when new.type=1 then 5 end)  FROM t_auth_user a LEFT JOIN t_pcs_arrival b on   b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c on c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d on d.id=b.shipRefId LEFT JOIN t_pcs_product e on new.productId=e.id   WHERE  a.id=new.createUserId  ;
INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT   DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 FROM  `t_auth_authorization` u ,  `t_auth_security_resources` t,  `t_auth_resource_assignments` ta WHERE t.indentifier = 'AUNLOUNDINGPROGRAMUPDATE'  AND ta.sourceId = t.id   AND ta.roleId = u.roleId; 
ELSEIF new.status!=old.status and new.status=4 THEN
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'提交的一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的[',e.name,']的',case when new.type=0 then '接卸'  when new.type=1 then '打循环'  end,'方案品质审批已通过，请知悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,CONCAT('#/inboundoperation/get?arrivalId=',new.arrivalId,'&productId=',new.productId,'&state=',case when new.type=0 then 3  when new.type=1 then 5 end)  FROM t_auth_user a LEFT JOIN t_pcs_arrival b on   b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c on c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d on d.id=b.shipRefId LEFT JOIN t_pcs_product e on new.productId=e.id   WHERE  a.id=new.createUserId  ;
INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT   DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 FROM  `t_auth_authorization` u ,  `t_auth_security_resources` t,  `t_auth_resource_assignments` ta WHERE t.indentifier = 'AUNLOUNDINGPROGRAMUPDATE'  AND ta.sourceId = t.id   AND ta.roleId = u.roleId; 
ELSEIF  new.status!=old.status and new.status=5 THEN
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'提交的一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的[',e.name,']的',case when new.type=0 then '接卸'  when new.type=1 then '打循环' end,'方案工艺审批已通过，请知悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,CONCAT('#/inboundoperation/get?arrivalId=',new.arrivalId,'&productId=',new.productId,'&state=',case when new.type=0 then 3  when new.type=1 then 5 end)  FROM t_auth_user a LEFT JOIN t_pcs_arrival b on   b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c on c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d on d.id=b.shipRefId LEFT JOIN t_pcs_product e on new.productId=e.id   WHERE  a.id=new.createUserId  ;
INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT   DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 FROM  `t_auth_authorization` u ,  `t_auth_security_resources` t,  `t_auth_resource_assignments` ta WHERE t.indentifier = 'AUNLOUNDINGPROGRAMUPDATE'  AND ta.sourceId = t.id   AND ta.roleId = u.roleId; 
 END IF;
    END */$$


DELIMITER ;

/* Trigger structure for table `t_pcs_truck` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `insert_pcs_truck` */$$

/*!50003 CREATE */ /*!50017  */ /*!50003 TRIGGER `insert_pcs_truck` BEFORE INSERT ON `t_pcs_truck` FOR EACH ROW begin
    set new.createTime = now();
end */$$


DELIMITER ;

/* Trigger structure for table `t_pcs_tube` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `update_t_pcs_tube` */$$

/*!50003 CREATE */ /*!50017  */ /*!50003 TRIGGER `update_t_pcs_tube` AFTER UPDATE ON `t_pcs_tube` FOR EACH ROW BEGIN
 IF  new.description!=old.description OR new.productId!=old.productId THEN 
     INSERT INTO t_pcs_tube_clean_log (tubeId,productId,description,editUserId,editTime) values (new.id,new.productId,new.description,new.editUserId,UNIX_TIMESTAMP(now())) ;
END IF;
END */$$


DELIMITER ;

/* Trigger structure for table `t_pcs_work` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `update_t_pcs_work` */$$

/*!50003 CREATE */ /*!50017  */ /*!50003 TRIGGER `update_t_pcs_work` AFTER UPDATE ON `t_pcs_work` FOR EACH ROW BEGIN
  IF new.status!=old.status and new.status=8 and old.status!=9 THEN 
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'提交了一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的[',e.name,']的接卸准备，请您获悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,CONCAT('#/inboundoperation/get?arrivalId=',new.arrivalId,'&productId=',new.productId,'&state=',4) FROM t_auth_user a LEFT JOIN t_pcs_arrival b on  b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c  on c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d  on d.id=b.shipRefId LEFT JOIN t_pcs_product e on new.productId=e.id  WHERE  a.id=new.createRUserId ;
INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT   DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 FROM  `t_auth_authorization` u ,  `t_auth_security_resources` t,  `t_auth_resource_assignments` ta WHERE t.indentifier='AUPLOADINGCHECK'  AND ta.sourceId = t.id   AND ta.roleId = u.roleId; 
 ELSEIF new.reviewStatus!=old.reviewStatus and new.reviewStatus=1 THEN 
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'提交了一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的[',e.name,']的数量审核，请您获悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,CONCAT('#/inboundoperation/get?arrivalId=',new.arrivalId,'&productId=',new.productId,'&state=',6) FROM t_auth_user a LEFT JOIN t_pcs_arrival b on  b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c  on c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d  on d.id=b.shipRefId LEFT JOIN t_pcs_product e on new.productId=e.id  WHERE  a.id=new.createUserId ;
INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT   DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 FROM  `t_auth_authorization` u ,  `t_auth_security_resources` t,  `t_auth_resource_assignments` ta WHERE t.indentifier='AAMOUNTCONFIRMVERIFY'  AND ta.sourceId = t.id   AND ta.roleId = u.roleId; 
 ELSEIF new.reviewStatus!=old.reviewStatus and new.reviewStatus=2 THEN 
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'提交的一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的[',e.name,']的数量审核已审核通过，请您获悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,CONCAT('#/inboundoperation/get?arrivalId=',new.arrivalId,'&productId=',new.productId,'&state=',7) FROM t_auth_user a LEFT JOIN t_pcs_arrival b on  b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c  on c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d  on d.id=b.shipRefId LEFT JOIN t_pcs_product e on new.productId=e.id  WHERE  a.id=new.createUserId ;
INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT   DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 FROM  `t_auth_authorization` u ,  `t_auth_security_resources` t,  `t_auth_resource_assignments` ta WHERE t.indentifier='AAMOUNTCONFIRMUPDATE'  AND ta.sourceId = t.id   AND ta.roleId = u.roleId; 
ELSEIF new.reviewStatus!=old.reviewStatus and new.reviewStatus=3 THEN 
     INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'提交的一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的船的[',e.name,']的数量审核已审核不通过，请您获悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),new.createUserId,1,CONCAT('#/inboundoperation/get?arrivalId=',new.arrivalId,'&productId=',new.productId,'&state=',6) FROM t_auth_user a LEFT JOIN t_pcs_arrival b on  b.id=new.arrivalId  
LEFT JOIN t_pcs_ship c  on c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d  on d.id=b.shipRefId LEFT JOIN t_pcs_product e on new.productId=e.id  WHERE  a.id=new.createUserId ;
INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT   DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1 FROM  `t_auth_authorization` u ,  `t_auth_security_resources` t,  `t_auth_resource_assignments` ta WHERE t.indentifier='AAMOUNTCONFIRMUPDATE'  AND ta.sourceId = t.id   AND ta.roleId = u.roleId; 
END IF;
  END */$$


DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

DROP PROCEDURE IF EXISTS `showChildLst`;

CREATE  PROCEDURE `showChildLst`(IN p_id INT)
    SQL SECURITY INVOKER
BEGIN
	
	DECLARE rootid INT;
		CREATE TEMPORARY TABLE IF NOT EXISTS tmpLst 
	       (sno INT PRIMARY KEY AUTO_INCREMENT,id INT,depth INT);
	      DELETE FROM tmpLst;
		SELECT CASE WHEN rootGoodsId=0 THEN sourceGoodsId ELSE (SELECT sourceGoodsId FROM t_pcs_goods WHERE id=rootGoodsId) END  INTO rootid FROM t_pcs_goods WHERE id = p_id ;
	      CALL createChildLst(rootid,1);
	    
	      SELECT g.id,g.sourceGoodsId,g.code,(CASE WHEN g.ladingClientId IS NOT NULL THEN d.name ELSE c.name END) NAME,(SELECT l.code FROM t_pcs_lading l WHERE l.id=gr.ladingId) ladingCode,
	      (SELECT CASE WHEN l.type='1' THEN '转卖' ELSE '发货' END FROM t_pcs_lading l WHERE l.id=gr.ladingId) ladingType,
	      g.goodsTotal,g.goodsIn,g.goodsOut,g.goodsCurrent,tmpLst.depth lever
		FROM tmpLst,
			t_pcs_goods g LEFT JOIN t_pcs_client c ON g.clientId = c.id  LEFT JOIN t_pcs_client d ON g.ladingClientId=d.id
			LEFT JOIN t_pcs_goods_group gr ON g.goodsGroupId = gr.id WHERE tmpLst.id=g.id 
			ORDER BY tmpLst.sno;
		
    END;;
    
    
    
    
    
    
    DELIMITER $$

USE `sop`$$

DROP TRIGGER /*!50032 IF EXISTS */ `setNotifyCode`$$

CREATE
    /*!50017 DEFINER = 'root'@'localhost' */
    TRIGGER `setNotifyCode` BEFORE INSERT ON `t_pcs_notify` 
    FOR EACH ROW BEGIN
	DECLARE g_code VARCHAR(30);
	IF new.type=15 THEN
	SELECT LPAD(MAX(SUBSTR(n.code,2))+1,7,0) INTO g_code FROM t_pcs_notify n WHERE n.type=15 ;
		IF g_code IS NULL THEN 
		SET new.code = 'P0000001' ;
		ELSE 
			SET new.code  = CONCAT('P',g_code) ;
		END IF ;
	ELSEIF new.type=16 THEN
	SELECT LPAD(MAX(SUBSTR(n.code,2))+1,7,0) INTO g_code FROM t_pcs_notify n WHERE n.type=16 ;
		IF g_code IS NULL THEN 
		SET new.code = 'Q0000001' ;
		ELSE 
			SET new.code  = CONCAT('Q',g_code) ;
		END IF ;
	ELSEIF new.type=17 THEN
		SELECT LPAD(MAX(SUBSTR(n.code,2))+1,7,0) INTO g_code FROM t_pcs_notify n WHERE n.type=17 ;
		IF g_code IS NULL THEN 
		SET new.code = 'R0000001' ;
		ELSE 
			SET new.code  = CONCAT('R',g_code) ;
		END IF ; 
	END IF ;
    END;
$$

DELIMITER ;