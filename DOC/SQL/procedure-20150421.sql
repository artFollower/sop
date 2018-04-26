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
/* Procedure structure for procedure `changeGoodsLog` */

/*!50003 DROP PROCEDURE IF EXISTS  `changeGoodsLog` */;

DELIMITER $$

/*!50003 CREATE  PROCEDURE `changeGoodsLog`(IN p_id INT,IN p_batchId INT,IN p_deliverType INT,IN p_deliverNum DOUBLE,IN p_ladingEvidence VARCHAR(40),IN p_ladingClientId INT,IN p_ladingCode VARCHAR(40),in p_vsId int)
BEGIN
	DECLARE g_status INT ;
	DECLARE g_pre_deliverNum DOUBLE ;
	DECLARE g_pre_actualNum DOUBLE ;
	DECLARE g_goodsId INT ;
	DECLARE g_shipId INT ;
	declare g_cou int ;
	start transaction ;
	SELECT deliverNum,actualNum,goodsId INTO g_pre_deliverNum,g_pre_actualNum,g_goodsId FROM t_pcs_goodslog WHERE id=p_id ;
	IF p_deliverType =1 THEN
		SELECT STATUS INTO g_status FROM t_pcs_train WHERE id=p_batchId AND STATUS !=44;
		IF g_status=43 THEN
			UPDATE t_pcs_goods SET goodsCurrent=round((goodsCurrent+g_pre_actualNum-p_deliverNum),3) WHERE id=g_goodsId ;
			UPDATE t_pcs_train SET STATUS=42,plateId=p_vsId WHERE id=p_batchId ;
			UPDATE   t_pcs_lading l 
				SET
				  l.goodsDelivery = goodsDelivery - g_pre_actualNum 
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
				    WHERE id =g_goodsId));
			UPDATE t_pcs_goodslog SET actualNum=0 WHERE id=p_id ;
			UPDATE t_pcs_approve SET STATUS=0 WHERE modelId=3 AND refId=p_batchId ;
			
		ELSE
			UPDATE t_pcs_train SET plateId=p_vsId WHERE id=p_batchId ;
			UPDATE t_pcs_goods SET goodsCurrent=ROUND((goodsCurrent+g_pre_deliverNum-p_deliverNum),3) WHERE id=g_goodsId ;
		END IF ;
	ELSE 
		SELECT STATUS,shipId INTO g_status,g_shipId FROM t_pcs_arrival WHERE id=p_batchId ;
		if g_shipId=p_vsId then
			IF g_status=54 THEN
			UPDATE t_pcs_goods SET goodsCurrent=goodsCurrent+g_pre_actualNum-p_deliverNum WHERE id=g_goodsId ;
			UPDATE t_pcs_arrival SET STATUS=53 WHERE id=p_batchId ;
				UPDATE   t_pcs_lading l 
					SET
					  l.goodsDelivery = goodsDelivery - g_pre_actualNum 
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
					    WHERE id =g_goodsId));
				UPDATE t_pcs_goodslog SET actualNum=0 WHERE id=p_id ;
				UPDATE t_pcs_approve SET STATUS=0 WHERE modelId=4 AND refId=p_batchId ;
			ELSE
				UPDATE t_pcs_goods SET goodsCurrent=goodsCurrent+g_pre_deliverNum-p_deliverNum WHERE id=g_goodsId ;
			END IF ;
			else 
			select count(1) into g_cou from t_pcs_arrival where shipId=p_vsId and type=2 and status in (50,51,52,53) ;
			if g_cou>0 then
			select max(id) into p_batchId from t_pcs_arrival where shipId=p_vsId and type=2 and status in (50,51,52,53) ;
			end if ;
			commit ;
		end if ;
	END IF ;
	UPDATE t_pcs_goodslog g SET g.deliverNum=p_deliverNum,g.goodsChange=-p_deliverNum,g.ladingEvidence=p_ladingEvidence,g.ladingClientId=p_ladingClientId,g.ladingCode=p_ladingCode,g.batchId=p_batchId,g.vehicleShipId=p_vsId WHERE id=p_id ;
	commit ;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `checkCommitChange` */

/*!50003 DROP PROCEDURE IF EXISTS  `checkCommitChange` */;

DELIMITER $$

/*!50003 CREATE  PROCEDURE `checkCommitChange`(IN l_arrivalId INT,IN l_flag INT)
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
	START TRANSACTION;
	IF g_status=54 THEN
		OPEN goodsloginfo ;
		REPEAT   
			FETCH goodsloginfo INTO i_id,d_num,a_num,g_id ;
			IF done=0 THEN
				UPDATE t_pcs_goods SET goodsCurrent=round((goodsCurrent+a_num-d_num),3) WHERE id=g_id ;
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
		UPDATE t_pcs_approve SET `status`=1,`comment`='',reviewTime=NULL,reviewUserId=NULL WHERE refId=l_arrivalId AND modelId=4 ;
		END IF;
		COMMIT ;
	END IF ;
END */$$
DELIMITER ;

/* Procedure structure for procedure `createChildLst` */

/*!50003 DROP PROCEDURE IF EXISTS  `createChildLst` */;

DELIMITER $$

/*!50003 CREATE  PROCEDURE `createChildLst`(IN rootId INT,IN nDepth INT)
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
    END */$$
DELIMITER ;

/* Procedure structure for procedure `deleteGoodsLog` */

/*!50003 DROP PROCEDURE IF EXISTS  `deleteGoodsLog` */;

DELIMITER $$

/*!50003 CREATE  PROCEDURE `deleteGoodsLog`(IN p_id INT)
BEGIN
	DECLARE g_status INT ;
	DECLARE g_pre_deliverNum DOUBLE ;
	DECLARE g_pre_actualNum DOUBLE ;
	DECLARE g_goodsId INT ;
	DECLARE g_deliverType INT ;
	DECLARE g_batchId INT ;
	SELECT deliverNum,actualNum,goodsId,deliverType,batchId INTO g_pre_deliverNum,g_pre_actualNum,g_goodsId,g_deliverType,g_batchId FROM t_pcs_goodslog WHERE id=p_id ;
	IF g_deliverType =1 THEN
		SELECT STATUS INTO g_status FROM t_pcs_train WHERE id=g_batchId AND STATUS !=44;
		IF g_status=43 THEN
			UPDATE t_pcs_goods SET goodsCurrent=goodsCurrent+g_pre_actualNum WHERE id=g_goodsId ;
			UPDATE t_pcs_train SET STATUS=42 WHERE id=g_batchId ;
			UPDATE   t_pcs_lading l 
				SET
				  l.goodsDelivery = ROUND((goodsDelivery - g_pre_actualNum),3) 
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
				    WHERE id =g_goodsId));
			UPDATE t_pcs_approve SET STATUS=0 WHERE modelId=3 AND refId=g_batchId ;
		ELSE
			UPDATE t_pcs_goods SET goodsCurrent=ROUND((goodsCurrent+g_pre_deliverNum),3) WHERE id=g_goodsId ;
		END IF ;
	ELSE 
		SELECT STATUS INTO g_status FROM t_pcs_arrival WHERE id=g_batchId ;
		IF g_status=54 THEN
			UPDATE t_pcs_goods SET goodsCurrent=goodsCurrent+g_pre_actualNum WHERE id=g_goodsId ;
			UPDATE t_pcs_arrival SET STATUS=53 WHERE id=g_batchId ;
			UPDATE   t_pcs_lading l 
				SET
				  l.goodsDelivery = ROUND((goodsDelivery - g_pre_actualNum),3) 
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
				    WHERE id =g_goodsId));
			UPDATE t_pcs_approve SET STATUS=0 WHERE modelId=4 AND refId=g_batchId ;
		ELSE
			UPDATE t_pcs_goods SET goodsCurrent=round((goodsCurrent+g_pre_deliverNum),3) WHERE id=g_goodsId ;
		END IF ;
	END IF ;
	UPDATE t_pcs_goodslog SET TYPE=8 WHERE id=p_id ;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `deliverVehicleRevoke` */

/*!50003 DROP PROCEDURE IF EXISTS  `deliverVehicleRevoke` */;

DELIMITER $$

/*!50003 CREATE  PROCEDURE `deliverVehicleRevoke`(IN l_trainId INT)
BEGIN
  
  DECLARE t_status INT ;
  DECLARE done TINYINT(1) DEFAULT 0;
  DECLARE g_deliverNum DOUBLE;
  DECLARE g_actualNum DOUBLE;
  DECLARE g_goodsId INT;
  DECLARE g_Id INT;
  DECLARE mycursor CURSOR FOR SELECT id,deliverNum,actualNum,goodsId FROM t_pcs_goodslog WHERE deliverType=1 AND batchId=l_trainId ;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
  SELECT STATUS INTO t_status FROM t_pcs_train WHERE id=l_trainId ;
  IF t_status!=43 THEN
	OPEN mycursor ;
	REPEAT
		FETCH mycursor INTO g_id,g_deliverNum,g_actualNum,g_goodsId ;
		IF done=0 THEN
			UPDATE t_pcs_goods g SET g.goodsCurrent=g.goodsCurrent+g_deliverNum WHERE id=g_goodsId ;
			UPDATE t_pcs_goodslog SET TYPE=7 WHERE id=g_id ;
		END IF;
		UNTIL done END REPEAT;
	CLOSE mycursor ;
  ELSE
	OPEN mycursor ;
	REPEAT
		FETCH mycursor INTO g_id,g_deliverNum,g_actualNum,g_goodsId ;
		IF done=0 THEN
		UPDATE t_pcs_goods g SET g.goodsCurrent=ROUND((g.goodsCurrent+g_actualNum),3) WHERE id=g_goodsId ;
		UPDATE   t_pcs_lading l 
				SET
				  l.goodsDelivery = ROUND((goodsDelivery - g_actualNum),3) 
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
				    WHERE id =g_goodsId));
		UPDATE t_pcs_goodslog SET TYPE=7 WHERE id=g_id ;
		END IF;
		UNTIL done END REPEAT;
	CLOSE mycursor ;
  END IF;
  UPDATE t_pcs_train SET STATUS=44 WHERE id=l_trainId ;
  END */$$
DELIMITER ;

/* Procedure structure for procedure `getDeliverInfo` */

/*!50003 DROP PROCEDURE IF EXISTS  `getDeliverInfo` */;

DELIMITER $$

/*!50003 CREATE  PROCEDURE `getDeliverInfo`(IN l_type INT,IN l_batchId INT)
BEGIN
	  DECLARE t_status INT ;
	  DECLARE done TINYINT(1) DEFAULT 0;
	  DECLARE g_totalNum DOUBLE ;
	  DECLARE g_deliverNum DOUBLE ;
	  DECLARE g_id INT;
	  DECLARE g_max DOUBLE ;
	  DECLARE mycursor CURSOR FOR SELECT id,deliverNum FROM t_pcs_goodslog WHERE deliverType=l_type AND batchId=l_batchId AND TYPE=5;
	  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
	  SELECT SUM(actualNum) INTO g_max FROM t_pcs_goodslog WHERE deliverType=l_type AND batchId=l_batchId ;
	  IF l_type=1 THEN
		SELECT IFNULL((SELECT MAX(w.netWeight) FROM t_pcs_weigh w WHERE w.trainId=t.id),0)+IFNULL((SELECT MAX(actualNum) FROM t_pcs_measure m WHERE m.trainId=t.id),0) INTO g_totalNum FROM t_pcs_train t WHERE id= l_batchId ;
	  ELSE
		SELECT SUM(IFNULL(ABS(realAmount),0)) INTO g_totalNum FROM t_pcs_store WHERE transportId=(SELECT id FROM t_pcs_transport_program WHERE arrivalId=l_batchId) ;
	  END IF;
	  
	IF g_max IS NULL OR g_max<=0 THEN 
	OPEN mycursor ;
	REPEAT   --  分发实发数量
		FETCH mycursor INTO g_id,g_deliverNum ;
		IF done=0 THEN
			IF g_totalNum-g_deliverNum>=0 THEN
				UPDATE t_pcs_goodslog SET actualNum=g_deliverNum WHERE id = g_id ;
				SET g_totalNum = g_totalNum-g_deliverNum ;
			ELSE 
				UPDATE t_pcs_goodslog SET actualNum = g_totalNum WHERE id = g_id ;
				SET g_totalNum =0 ;
			
			END IF ;
		END IF;
		UNTIL done END REPEAT;
	CLOSE mycursor ;
	END IF ;
SELECT 
  i.id,
  i.batchId,
  i.goodsId,
  i.deliverNum,
  c.name ladingClientName,
  IFNULL((SELECT 
    CODE 
  FROM
    t_pcs_lading 
  WHERE id = 
    (SELECT 
      ladingId 
    FROM
      t_pcs_goods_group 
    WHERE id = g.goodsGroupId)),'') ladingCode,
    p.name productName,
    i.deliverNum,
    g.code goodsCode,
    (g.goodsCurrent-(g.goodsTotal-(CASE WHEN g.goodsInPass-g.goodsOutPass>=0 THEN g.goodsOutPass ELSE g.goodsInPass END))) goodsCurrent,
    i.actualNum
FROM
  t_pcs_goodslog i 
  LEFT JOIN t_pcs_client c 
    ON c.id = i.ladingClientId LEFT JOIN t_pcs_goods g ON g.id=i.goodsId,
  t_pcs_product p
WHERE i.deliverType = l_type 
  AND i.batchId = l_batchId
  AND g.id = i.goodsId 
  AND p.id=g.productId
  AND i.type=5;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `getDeliverInvoiceInfo` */

/*!50003 DROP PROCEDURE IF EXISTS  `getDeliverInvoiceInfo` */;

DELIMITER $$

/*!50003 CREATE  PROCEDURE `getDeliverInvoiceInfo`(IN l_serialNo VARCHAR(30))
BEGIN
	  DECLARE t_status INT ;
	  DECLARE done TINYINT(1) DEFAULT 0;
	  DECLARE g_totalNum DOUBLE ;
	  DECLARE g_deliverNum DOUBLE ;
	  DECLARE g_batchId INT;
	  DECLARE g_id INT;
	  DECLARE g_max DOUBLE ;
	  DECLARE mycursor CURSOR FOR SELECT id,deliverNum FROM t_pcs_goodslog WHERE deliverType=1 AND batchId=g_batchId AND TYPE=5;
	  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
	  SELECT wb.deliveryNum,i.batchId INTO g_totalNum,g_batchId FROM t_pcs_weighbridge wb,t_pcs_goodslog i WHERE i.serial=wb.serial AND i.serial=l_serialNo;
	OPEN mycursor ;
	REPEAT   --  分发实发数量
		FETCH mycursor INTO g_id,g_deliverNum ;
		IF done=0 THEN
			IF g_totalNum-g_deliverNum>=0 THEN
				UPDATE t_pcs_goodslog SET tempDeliverNum=g_deliverNum WHERE id = g_id ;
				SET g_totalNum = g_totalNum-g_deliverNum ;
			ELSE 
				UPDATE t_pcs_goodslog SET tempDeliverNum = g_totalNum WHERE id = g_id ;
				SET g_totalNum =0 ;
			
			END IF ;
		END IF;
		UNTIL done END REPEAT;
	CLOSE mycursor ;
SELECT 
  i.id,
  i.batchId,
  i.goodsId,
  (CASE WHEN i.deliverType=1 THEN (SELECT NAME FROM t_pcs_truck WHERE id=i.vehicleShipId) ELSE (SELECT NAME FROM t_pcs_ship WHERE id=i.vehicleShipId) END)vsName,
  i.serial,
  i.deliverNum,
  c.name ladingClientName,
  IFNULL((SELECT 
    CODE 
  FROM
    t_pcs_lading 
  WHERE id = 
    (SELECT 
      ladingId 
    FROM
      t_pcs_goods_group 
    WHERE id = g.goodsGroupId)),'') ladingCode,
    p.name productName,
    i.deliverNum,
    g.code goodsCode,
    (g.goodsCurrent-(g.goodsTotal-(CASE WHEN g.goodsInPass-g.goodsOutPass>=0 THEN g.goodsOutPass ELSE g.goodsInPass END))) goodsCurrent,
    i.tempDeliverNum,
    DATE_FORMAT(FROM_UNIXTIME(i.createTime),'%Y/%m/%d') createTime,
    (SELECT u.name FROM t_auth_user u WHERE u.id=i.createUserId) userName
FROM
  t_pcs_goodslog i 
  LEFT JOIN t_pcs_client c 
    ON c.id = i.ladingClientId LEFT JOIN t_pcs_goods g ON g.id=i.goodsId,
  t_pcs_product p
WHERE i.deliverType = 1 
  AND i.batchId = g_batchId
  AND g.id = i.goodsId 
  AND p.id=g.productId
  AND i.type=5;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `getStorageCost` */

/*!50003 DROP PROCEDURE IF EXISTS  `getStorageCost` */;

DELIMITER $$

/*!50003 CREATE  PROCEDURE `getStorageCost`(IN l_sTime VARCHAR(20),IN l_eTime VARCHAR(20),IN l_goodsId INT)
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
	SELECT   gb.code,c.name clientName,g.name ladingClientName,(ROUND((a.goodsSave+a.surplus),3)),(SELECT case when overtimePrice is null or overtimePrice ='' then 0 else overtimePrice end  FROM t_pcs_contract WHERE id=gb.contractId),(ROUND((a.goodsSave+a.surplus)*(SELECT CASE WHEN overtimePrice IS NULL OR overtimePrice ='' THEN 0 ELSE overtimePrice END FROM t_pcs_contract WHERE id=gb.contractId),3))INTO g_code,g_clientName,g_ladingClientName,g_currentNum,g_overtimePrice,g_cost
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
  INSERT INTO temp_goods_info(CODE,clientName,ladingName,l_time,deliverNum,exportNum,backNum,holdNum,currentNum,overtimePrice,cost) SELECT g_code,g_clientName,g_ladingClientName,nTime,SUM(deliverNum) deliverNum,SUM(exportNum) exportNum,SUM(backNum) backNum,SUM(holdNum) holdNum,g_currentNum,ifnull(g_overtimePrice,0),g_cost FROM (SELECT   nTime,(CASE WHEN a.type=5 AND g_cou>0 THEN IFNULL(ABS(a.goodsChange),0) ELSE 0 END)deliverNum,(CASE WHEN a.type=3 AND g_cou>0 THEN IFNULL(ABS(a.goodsChange),0) ELSE 0 END)exportNum,(CASE WHEN a.type=6 AND g_cou>0 THEN IFNULL(ABS(a.goodsChange),0) ELSE 0 END)backNum,(CASE WHEN a.type=9 AND g_cou>0 THEN IFNULL(ABS(a.goodsChange),0) ELSE 0 END)holdNum
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
     commit ;
     SELECT * FROM temp_goods_info;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `getTotalStorageCost` */

/*!50003 DROP PROCEDURE IF EXISTS  `getTotalStorageCost` */;

DELIMITER $$

/*!50003 CREATE  PROCEDURE `getTotalStorageCost`(IN l_sTime VARCHAR(20),IN l_eTime VARCHAR(20),in flagStr varchar(30))
begin
	DECLARE g_id INT ;
	DECLARE done TINYINT(1) DEFAULT 0 ;
	DECLARE mycursor CURSOR FOR SELECT id FROM t_pcs_goods g where g.rootGoodsId is not null;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
	START TRANSACTION;
	CREATE TEMPORARY TABLE IF NOT EXISTS temp_goods_cost_info(id INT AUTO_INCREMENT PRIMARY KEY,flagStr varchar(30),goodsId int,totalCost decimal(12,3)) ;
	OPEN mycursor ;
	REPEAT   --  分发实发数量
		FETCH mycursor INTO g_id ;
		IF done=0 THEN
			CALL getStorageCost(l_sTime,l_eTime,g_id) ;
			insert into temp_goods_cost_info(flagStr,goodsId,totalCost) select flagStr,g_id,sum(cost) from temp_goods_info ;
		END IF;
		UNTIL done END REPEAT;
	CLOSE mycursor ;
	commit;
end */$$
DELIMITER ;

/* Procedure structure for procedure `getVehicleDetailData` */

/*!50003 DROP PROCEDURE IF EXISTS  `getVehicleDetailData` */;

DELIMITER $$

/*!50003 CREATE  PROCEDURE `getVehicleDetailData`(in l_trainId int)
BEGIN
  DECLARE done INT DEFAULT 0;
  declare pactualNum double ;
  declare pid int ;
  DECLARE pDeliverNum DOUBLE ;
  DECLARE cur1 CURSOR FOR SELECT id,deliverNum FROM t_pcs_vehicle_plan WHERE trainId = l_trainId;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
    SELECT 
  (IFNULL(temp1.total1,0) + IFNULL(temp2.total2,0)) INTO pactualNum 
FROM
  (SELECT 
    SUM(actualNum) total1 
  FROM
    t_pcs_measure m 
  WHERE m.trainId = l_trainId) temp1,
  (SELECT SUM(t.netWeight) total2 FROM t_pcs_weigh t WHERE t.trainId = l_trainId) temp2 ;
		
	OPEN cur1;
read_loop: LOOP
    FETCH cur1 INTO pid,pDeliverNum;
	
    IF done THEN
      LEAVE read_loop;
    END IF;
		IF pDeliverNum>0 and pactualNum>=pDeliverNum THEN
		  UPDATE t_pcs_vehicle_plan SET actualNum = pDeliverNum WHERE id=pid ;
		   SET pactualNum=pactualNum-pDeliverNum ;
		ELSEIF pDeliverNum>0 AND pactualNum<pDeliverNum THEN 
		UPDATE t_pcs_vehicle_plan SET actualNum = pactualNum WHERE id=pid ;
		   SET pactualNum=pactualNum-pDeliverNum ;
		END IF;
	END LOOP;
	CLOSE cur1;
	SELECT 
  p.id,
  c.name,
  p.ladingId,
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
 
  END */$$
DELIMITER ;

/* Procedure structure for procedure `showChildLst` */

/*!50003 DROP PROCEDURE IF EXISTS  `showChildLst` */;

DELIMITER $$

/*!50003 CREATE  PROCEDURE `showChildLst`(IN p_id INT)
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
		
    END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
