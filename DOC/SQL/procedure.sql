/*
MySQL Backup
Source Server Version: 5.6.21
Source Database: sop
Date: 2015/6/8 23:57:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
--  Procedure definition for `analyse_contract`
-- ----------------------------
DROP PROCEDURE IF EXISTS `analyse_contract`;
DELIMITER ;;
CREATE  PROCEDURE `analyse_contract`(IN `startTime` timestamp,IN `endTime` timestamp)
BEGIN
	#Routine body goes here...
/*合同货品分析*/
INSERT t_bas_contract (
	typeId,
	intentCount,
	totalCount,
	totalGoods,
	totalValue,
	turnRate,
	type,
	time
) SELECT
	a.productId AS typeId,
	(
		SELECT
			count(b.id)
		FROM
			t_pcs_intention b
		WHERE
			b.createTime >= startTime
		AND b.createTime < endTime
		AND b.productId = a.productId
	) AS intentCount,
	count(a.id) AS totalCount,
	sum(a.quantity) AS totalGoods,
	sum(a.totalPrice) AS totalValue,
	(
		SELECT
			count(c.intentionId)
		FROM
			t_pcs_contract c
		WHERE
			c.createTime >= startTime
		AND c.createTime < endTime
		AND c. STATUS IN (2, 5)
		AND c.productId = a.productId
		AND c.intentionId != 0
	) / (
		SELECT
			count(b.id)
		FROM
			t_pcs_intention b
		WHERE
			b.createTime >= startTime
		AND b.createTime < endTime
		AND b.productId = a.productId
	) AS turnRate,
	1 AS type,
	startTime AS time
FROM
	t_pcs_contract a
WHERE
	a.createTime >= startTime
AND a.createTime < endTime
AND a. STATUS IN (2, 5)
GROUP BY
	a.productId;

/*合同类型分析*/
INSERT t_bas_contract (
	typeId,
	intentCount,
	totalCount,
	totalGoods,
	totalValue,
	turnRate,
	type,
	time
) SELECT
	a.type AS typeId,
	(
		SELECT
			count(b.id)
		FROM
			t_pcs_intention b
		WHERE
			b.createTime >= startTime
		AND b.createTime < endTime
		AND b.type = a.type
	) AS intentCount,
	count(a.id) AS totalCount,
	sum(a.quantity) AS totalGoods,
	sum(a.totalPrice) AS totalValue,
	(
		SELECT
			count(c.intentionId)
		FROM
			t_pcs_contract c
		WHERE
			c.createTime >= startTime
		AND c.createTime < endTime
		AND c. STATUS IN (2, 5)
		AND c.type = a.type
		AND c.intentionId != 0
	) / (
		SELECT
			count(b.id)
		FROM
			t_pcs_intention b
		WHERE
			b.createTime >= startTime
		AND b.createTime < endTime
		AND b.type = a.type
	) AS turnRate,
	2 AS type,
	startTime AS time
FROM
	t_pcs_contract a
WHERE
	a.createTime >= startTime
AND a.createTime < endTime
AND a. STATUS IN (2, 5)
GROUP BY
	a.type;

/*合同贸易类型分析*/
INSERT t_bas_contract (
	typeId,
	intentCount,
	totalCount,
	totalGoods,
	totalValue,
	turnRate,
	type,
	time
) SELECT
	a.taxType AS typeId,
	NULL AS intentCount,
	count(a.id) AS totalCount,
	sum(a.quantity) AS totalGoods,
	sum(a.totalPrice) AS totalValue,
	NULL AS turnRate,
	3 AS type,
	startTime AS time
FROM
	t_pcs_contract a
WHERE
	a.createTime >= startTime
AND a.createTime < endTime
AND a. STATUS IN (2, 5)
GROUP BY
	a.taxType;

/*跟单人员分析*/
INSERT t_bas_contract (
	typeId,
	intentCount,
	totalCount,
	totalGoods,
	totalValue,
	turnRate,
	type,
	time
) SELECT
	a.createUserId AS typeId,
	(
		SELECT
			count(c.id)
		FROM
			t_pcs_intention c
		WHERE
			c.`status` IN (2, 5)
		AND c.createTime >= startTime
		AND c.createTime < endTime
		AND c.createUserId = a.createUserId
	) AS intentCount,
	count(b.id) AS totalCount,
	sum(b.quantity) AS totalGoods,
	sum(b.totalPrice) as totalValue,
	(
		count(b.id) / (
			SELECT
				count(c.id)
			FROM
				t_pcs_intention c
			WHERE
				c.`status` IN (2, 5)
			AND c.createTime >= startTime
			AND c.createTime < endTime
			AND c.createUserId = a.createUserId
		)
	) AS turnRate,
	4 AS type,
	startTime AS time
FROM
	t_pcs_intention a
LEFT JOIN t_pcs_contract b ON b.intentionId = a.id
WHERE
	a.`status` IN (2, 5)
AND a.createTime >= startTime
AND a.createTime < endTime
GROUP BY
	a.createUserId;
END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `changeGoodsLog`
-- ----------------------------
DROP PROCEDURE IF EXISTS `changeGoodsLog`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `changeGoodsLog`(IN p_id INT,IN p_batchId INT,IN p_deliverType INT,IN p_deliverNum DOUBLE,IN p_ladingEvidence VARCHAR(40),IN p_ladingClientId INT,IN p_ladingCode VARCHAR(40),in p_vsId int,in p_tankName varchar(40))
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
		IF g_status=43 THEN --  数量已确认
			UPDATE t_pcs_goods SET goodsCurrent=round((goodsCurrent+g_pre_actualNum-p_deliverNum),3) WHERE id=g_goodsId ;-- 更新货体量
			UPDATE t_pcs_train SET STATUS=42,plateId=p_vsId WHERE id=p_batchId ; -- 更新到确认前状态
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
			IF g_status=54 THEN --  数量已确认
			UPDATE t_pcs_goods SET goodsCurrent=goodsCurrent+g_pre_actualNum-p_deliverNum WHERE id=g_goodsId ; -- 更新货体数量
			UPDATE t_pcs_arrival SET STATUS=53 WHERE id=p_batchId ; -- 更新状态 需重新确认
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
	UPDATE t_pcs_goodslog g SET g.deliverNum=p_deliverNum,g.goodsChange=-p_deliverNum,g.ladingEvidence=p_ladingEvidence,g.ladingClientId=p_ladingClientId,g.ladingCode=p_ladingCode,g.batchId=p_batchId,g.vehicleShipId=p_vsId,tankName=p_tankName WHERE id=p_id ;
	commit ;
    END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `checkClient`
-- ----------------------------
DROP FUNCTION IF EXISTS `checkClient`;
DELIMITER ;;
CREATE  FUNCTION `checkClient`(`s_userId` integer,`s_clientIds` varchar(64)) RETURNS int(11)
BEGIN
DECLARE g_clientId integer;
declare g_clientGroupId integer;
declare g_clientlist VARCHAR(255);

select clientId into g_clientId from t_auth_user where id=s_userId;
select clientGroupId into g_clientGroupId from t_auth_user where id=s_userId;

if (!ISNULL(g_clientGroupId)) then (select group_concat(id) from t_pcs_client where clientGroupId=g_clientGroupId ORDER BY id asc into g_clientlist);
elseif(!ISNULL(g_clientId)) then set g_clientlist=g_clientId;
else return 1;end if;

if LOCATE(s_clientIds,g_clientlist)!=0 then return 1;
else return 0;end if;

END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `checkCommitChange`
-- ----------------------------
DROP PROCEDURE IF EXISTS `checkCommitChange`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `checkCommitChange`(IN l_arrivalId INT,IN l_flag INT)
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
END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `checkNull`
-- ----------------------------
DROP FUNCTION IF EXISTS `checkNull`;
DELIMITER ;;
CREATE  FUNCTION `checkNull`(`obj` varchar(64)) RETURNS int(11)
BEGIN
	#Routine body goes here...
	if(ISNULL(obj)) then return 0;
else RETURN 1;end if;
END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `createChildLst`
-- ----------------------------
DROP PROCEDURE IF EXISTS `createChildLst`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `createChildLst`(IN rootId INT,IN nDepth INT)
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
--  Procedure definition for `deleteGoodsLog`
-- ----------------------------
DROP PROCEDURE IF EXISTS `deleteGoodsLog`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteGoodsLog`(IN p_id INT)
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
    END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `deliverVehicleRevoke`
-- ----------------------------
DROP PROCEDURE IF EXISTS `deliverVehicleRevoke`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `deliverVehicleRevoke`(IN l_trainId INT)
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
  END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `func_splitString`
-- ----------------------------
DROP FUNCTION IF EXISTS `func_splitString`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `func_splitString`(f_string varchar(1000),f_delimiter varchar(5),f_order int) RETURNS varchar(255) CHARSET utf8
BEGIN
declare result varchar(255) default '';
set result = reverse(substring_index(reverse(substring_index(f_string,f_delimiter,f_order)),f_delimiter,1));
return result;
END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `getChildList`
-- ----------------------------
DROP FUNCTION IF EXISTS `getChildList`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getChildList`(rootId INT) RETURNS varchar(1000) CHARSET utf8
BEGIN
DECLARE sTemp VARCHAR(1000);
DECLARE sTempChd VARCHAR(1000);

SET sTemp = '0';
SET sTempChd =cast(rootId as CHAR);

WHILE sTempChd is not null DO
SET sTemp = concat(sTemp,',',sTempChd);
SELECT group_concat(id) INTO sTempChd FROM t_pcs_goods where FIND_IN_SET(sourceGoodsId,sTempChd)>0;
END WHILE;
RETURN sTemp;
END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `getClientName`
-- ----------------------------
DROP FUNCTION IF EXISTS `getClientName`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getClientName`(l_id VARCHAR(20)) RETURNS varchar(255) CHARSET utf8
BEGIN
DECLARE temp VARCHAR(255) DEFAULT '';
	DECLARE name1 VARCHAR(255) DEFAULT '';
	DECLARE done TINYINT(1) DEFAULT 0;
	DECLARE	mycursor CURSOR FOR SELECT NAME FROM t_pcs_client WHERE id IN (SELECT clientId FROM t_pcs_arrival_plan WHERE arrivalId =l_id) ;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
	OPEN mycursor ;
	
	REPEAT
		FETCH mycursor INTO temp;
		IF done=0 THEN
			SET name1 := CONCAT(temp,',',name1);
		END IF; 
	UNTIL done END REPEAT;
	CLOSE mycursor ;
	RETURN name1;
    END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `getClients`
-- ----------------------------
DROP PROCEDURE IF EXISTS `getClients`;
DELIMITER ;;
CREATE  PROCEDURE `getClients`(in userId int,in clients varchar(255))
BEGIN
	#Routine body goes here...
	
	SELECT * FROM t_pcs_client WHERE id in(clients);

END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `getCurStock`
-- ----------------------------
DROP PROCEDURE IF EXISTS `getCurStock`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getCurStock`(
  IN l_sTime VARCHAR (20),
  IN l_eTime VARCHAR (20),
  IN l_productId VARCHAR(8),
  IN l_client INT
)
BEGIN
  DECLARE i INT DEFAULT 0 ;
  DECLARE nTime VARCHAR (30) ;
  DECLARE g_currentNum VARCHAR (30) ;
  DECLARE cou INT ;
  START TRANSACTION ;
  IF l_sTime = '' 
  OR l_sTime IS NULL 
  THEN SET l_sTime = DATE_ADD(
    CURDATE(),
    INTERVAL - DAY(CURDATE()) + 1 DAY
  ) ;
  END IF ;
  IF l_eTime = '' 
  OR l_eTime IS NULL 
  THEN SET l_eTime = CURDATE() ;
  END IF ;
  SET cou = TO_DAYS(STR_TO_DATE(l_eTime, '%Y-%m-%d')) - TO_DAYS(STR_TO_DATE(l_sTime, '%Y-%m-%d')) ;
  CREATE TEMPORARY TABLE IF NOT EXISTS temp_cur_stock (
    id INT AUTO_INCREMENT PRIMARY KEY,
    l_time VARCHAR (30),
    currentNum DECIMAL (10, 3)
  ) ;
  DELETE 
  FROM
    temp_cur_stock ;
  WHILE
    i <= cou DO 
    SELECT 
      DATE_ADD(l_sTime, INTERVAL i DAY) INTO nTime 
    FROM
      DUAL ;
    	INSERT INTO temp_cur_stock (l_time, currentNum) 
    SELECT 
      nTime,
      IFNULL(ROUND((SUM(a.goodsSave) + SUM(a.surplus)), 3),0)
    FROM
      t_pcs_goodslog a 
      LEFT JOIN t_pcs_lading b 
        ON b.id = a.ladingId 
      LEFT JOIN t_pcs_client c 
        ON c.id = a.clientId 
      LEFT JOIN t_pcs_client g 
        ON g.id = a.ladingClientId,
      t_pcs_goods gb 
    WHERE a.type <> 8 
      AND a.goodsId = gb.id 
      AND gb.productId = l_productId 
      AND a.id IN 
      (SELECT 
        MAX(i.id) 
        FROM
          t_pcs_goodslog i,
          t_pcs_goods g 
        WHERE i.goodsId = g.id 
          AND g.productId = l_productId 
          AND g.clientId = l_client 
          AND i.type <> 8 
          AND DATE_FORMAT(
            FROM_UNIXTIME(i.createTime),
            '%Y-%m-%d'
          ) <= nTime GROUP BY i.goodsId) ;
    SET i = i + 1 ;
  END WHILE ;
  COMMIT ;
  SELECT 
    * 
  FROM
    temp_cur_stock ;
END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `getCurStock1`
-- ----------------------------
DROP PROCEDURE IF EXISTS `getCurStock1`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getCurStock1`(
  IN l_sTime VARCHAR (20),
  IN l_eTime VARCHAR (20),
  IN l_cargoId INT
)
BEGIN
  DECLARE i INT DEFAULT 0 ;
  DECLARE nTime VARCHAR (30) ;
  DECLARE g_currentNum VARCHAR (30) ;
  DECLARE cou INT ;
  START TRANSACTION ;
  IF l_sTime = '' 
  OR l_sTime IS NULL 
  THEN SET l_sTime = DATE_ADD(
    CURDATE(),
    INTERVAL - DAY(CURDATE()) + 1 DAY
  ) ;
  END IF ;
  IF l_eTime = '' 
  OR l_eTime IS NULL 
  THEN SET l_eTime = CURDATE() ;
  END IF ;
  SET cou = TO_DAYS(STR_TO_DATE(l_eTime, '%Y-%m-%d')) - TO_DAYS(STR_TO_DATE(l_sTime, '%Y-%m-%d')) ;
  CREATE TEMPORARY TABLE IF NOT EXISTS temp_cur_stock (
    id INT AUTO_INCREMENT PRIMARY KEY,
    l_time VARCHAR (30),
    currentNum DECIMAL (10, 3)
  ) ;
  DELETE 
  FROM
    temp_cur_stock ;
  WHILE
    i <= cou DO 
    SELECT 
      DATE_ADD(l_sTime, INTERVAL i DAY) INTO nTime 
    FROM
      DUAL ;
   INSERT INTO temp_cur_stock (l_time, currentNum) 
    SELECT 
      nTime,
      ROUND((SUM(a.goodsSave) + SUM(a.surplus)), 3) 
    FROM
      t_pcs_goodslog a
    WHERE a.id IN 
      (SELECT 
        MAX(i.id) 
      FROM
        t_pcs_goodslog i,
      t_pcs_goods gb ,
      t_pcs_cargo cg
      WHERE i.goodsId = gb.id 
      AND gb.cargoId = cg.id
      AND cg.id=l_cargoId
      AND i.type <> 8 
       -- AND g.productId = l_productId 
        AND DATE_FORMAT(
          FROM_UNIXTIME(i.createTime),
          '%Y-%m-%d'
        ) = 
        (SELECT 
          MAX(
            DATE_FORMAT(
              FROM_UNIXTIME(i.createTime),
              '%Y-%m-%d'
            )
          ) 
        FROM
          t_pcs_goodslog i
        WHERE i.type <> 8 
          AND DATE_FORMAT(
            FROM_UNIXTIME(i.createTime),
            '%Y-%m-%d'
          ) <= nTime) GROUP BY gb.id ) GROUP BY nTime;
    SET i = i + 1 ;
  END WHILE ;
  COMMIT ;
  SELECT 
    * 
  FROM
    temp_cur_stock ;
END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `getCurStockByCargoId`
-- ----------------------------
DROP PROCEDURE IF EXISTS `getCurStockByCargoId`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getCurStockByCargoId`(
  IN l_sTime VARCHAR (20),
  IN l_eTime VARCHAR (20),
  IN l_cargoId INT
)
BEGIN
  DECLARE i INT DEFAULT 0 ;
  DECLARE nTime VARCHAR (30) ;
  DECLARE g_currentNum VARCHAR (30) ;
  DECLARE cou INT ;
  START TRANSACTION ;
  IF l_sTime = '' 
  OR l_sTime IS NULL 
  THEN SET l_sTime = DATE_ADD(
    CURDATE(),
    INTERVAL - DAY(CURDATE()) + 1 DAY
  ) ;
  END IF ;
  IF l_eTime = '' 
  OR l_eTime IS NULL 
  THEN SET l_eTime = CURDATE() ;
  END IF ;
  SET cou = TO_DAYS(STR_TO_DATE(l_eTime, '%Y-%m-%d')) - TO_DAYS(STR_TO_DATE(l_sTime, '%Y-%m-%d')) ;
  CREATE TEMPORARY TABLE IF NOT EXISTS temp_cur_stock (
    id INT AUTO_INCREMENT PRIMARY KEY,
    l_time VARCHAR (30),
    currentNum DECIMAL (20, 3)
  ) ;
  DELETE 
  FROM
    temp_cur_stock ;
  WHILE
    i <= cou DO 
    SELECT 
      DATE_ADD(l_sTime, INTERVAL i DAY) INTO nTime 
    FROM
      DUAL ;
   INSERT INTO temp_cur_stock (l_time, currentNum) 
    SELECT 
      nTime,
      IFNULL(ROUND((SUM(a.goodsSave) + SUM(a.surplus)), 3),0)
    FROM
      t_pcs_goodslog a
    WHERE a.id IN 
      (SELECT 
        MAX(i.id) 
        FROM
           t_pcs_goodslog i,
      t_pcs_goods gb ,
      t_pcs_cargo cg
      WHERE i.goodsId = gb.id 
      AND gb.cargoId = cg.id
      AND cg.id=l_cargoId
      AND i.type <> 8
          AND DATE_FORMAT(
            FROM_UNIXTIME(i.createTime),
            '%Y-%m-%d'
          ) <= nTime GROUP BY gb.id) ;
    SET i = i + 1 ;
  END WHILE ;
  COMMIT ;
  SELECT 
    * 
  FROM
    temp_cur_stock ;
END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `getDeliverInfo`
-- ----------------------------
DROP PROCEDURE IF EXISTS `getDeliverInfo`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getDeliverInfo`(IN l_type INT,IN l_batchId INT)
BEGIN
	  DECLARE t_status INT ;
	  DECLARE done TINYINT(1) DEFAULT 0;
	  DECLARE g_totalNum DOUBLE ;
	  DECLARE g_deliverNum DOUBLE ;
	  DECLARE g_id INT;
	  DECLARE g_max DOUBLE ;
	  DECLARE g_flag INT;
	  DECLARE g_isChange INT;
	  DECLARE g_afUpNum DECIMAL(10,3);-- 调整后量
	  DECLARE g_afUpDiffNum DECIMAL(10,3);-- 调整后差异量
	  DECLARE mycursor CURSOR FOR SELECT id,deliverNum FROM t_pcs_goodslog WHERE deliverType=l_type AND batchId=l_batchId AND TYPE=5;
	  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
	  START TRANSACTION;
	  SELECT SUM(actualNum) INTO g_max FROM t_pcs_goodslog WHERE deliverType=l_type AND batchId=l_batchId ;
	  IF l_type=2 THEN     --  船发
		SELECT SUM(IFNULL(ABS(realAmount),0)) INTO g_totalNum FROM t_pcs_store WHERE transportId=(SELECT id FROM t_pcs_transport_program WHERE arrivalId=l_batchId) ;
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
	COMMIT;
	SELECT 	  i.id,	  i.batchId,	  i.goodsId,	  i.deliverNum,	  c.name ladingClientName, IFNULL((SELECT l.code FROM t_pcs_lading l 
	  WHERE l.id = (SELECT ladingId FROM t_pcs_goods_group WHERE id = g.goodsGroupId)),'') ladingCode,p.name productName,
	    i.deliverNum,g.code goodsCode,(g.goodsCurrent-(g.goodsTotal-(CASE WHEN g.goodsInPass-g.goodsOutPass>=0 THEN g.goodsOutPass ELSE g.goodsInPass END))) goodsCurrent,
	    i.actualNum FROM t_pcs_goodslog i LEFT JOIN t_pcs_client c ON c.id = i.ladingClientId LEFT JOIN t_pcs_goods g ON g.id=i.goodsId,
	  t_pcs_product p WHERE i.deliverType = l_type  AND i.batchId = l_batchId AND g.id = i.goodsId AND p.id=g.productId AND i.type=5;
	ELSE   
		SELECT flag,isChange INTO g_flag,g_isChange FROM t_pcs_train WHERE id=l_batchId ;
		
		SELECT 	  t.isChange,i.id,	  i.batchId,	  i.goodsId,	  i.deliverNum,	  c.name ladingClientName, IFNULL((SELECT l.code FROM t_pcs_lading l
	  WHERE l.id = (SELECT ladingId FROM t_pcs_goods_group WHERE id = g.goodsGroupId)),'') ladingCode,p.name productName,
	    i.deliverNum,g.code goodsCode,(g.goodsCurrent-(g.goodsTotal-(CASE WHEN g.goodsInPass-g.goodsOutPass>=0 THEN g.goodsOutPass ELSE g.goodsInPass END))) goodsCurrent,
	    i.actualNum,IFNULL(i.afUpNum,0) g_afUpNum,IFNULL(i.afDiffNum,0) g_afUpDiffNum FROM t_pcs_goodslog i LEFT JOIN t_pcs_client c ON c.id = i.ladingClientId LEFT JOIN t_pcs_goods g ON g.id=i.goodsId LEFT JOIN t_pcs_train t ON t.id=i.batchId,
	  t_pcs_product p WHERE i.deliverType = l_type  AND i.batchId = l_batchId AND g.id = i.goodsId AND p.id=g.productId AND i.type=5;
	 END IF;
	
    END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `getDeliverInvoiceInfo`
-- ----------------------------
DROP PROCEDURE IF EXISTS `getDeliverInvoiceInfo`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getDeliverInvoiceInfo`(IN l_serialNo VARCHAR(30))
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
    END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `getStorageCost`
-- ----------------------------
DROP PROCEDURE IF EXISTS `getStorageCost`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getStorageCost`(IN l_sTime VARCHAR(20),IN l_eTime VARCHAR(20),IN l_goodsId INT)
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
    END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `getStorageCostForCargo`
-- ----------------------------
DROP PROCEDURE IF EXISTS `getStorageCostForCargo`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getStorageCostForCargo`( IN l_sTime VARCHAR(20),IN l_eTime VARCHAR(20),IN l_cargoId INT)
BEGIN
  DECLARE i INT DEFAULT 0 ;
  DECLARE nTime VARCHAR (30) ;
  DECLARE g_currentNum DECIMAL (12, 3);
  DECLARE cou INT ;
   DECLARE g_cou INT ;
  START TRANSACTION ;
  IF l_sTime = '' 
  OR l_sTime IS NULL 
  THEN SET l_sTime = DATE_ADD(
    CURDATE(),
    INTERVAL - DAY(CURDATE()) + 1 DAY
  ) ;
  END IF ;
  IF l_eTime = '' 
  OR l_eTime IS NULL 
  THEN SET l_eTime = CURDATE() ;
  END IF ;
  SET cou = TO_DAYS(STR_TO_DATE(l_eTime, '%Y-%m-%d')) - TO_DAYS(STR_TO_DATE(l_sTime, '%Y-%m-%d')) ;
  CREATE TEMPORARY TABLE IF NOT EXISTS temp_cur_stock (
    id INT AUTO_INCREMENT PRIMARY KEY,
    l_time VARCHAR (30),
    deliverNum DECIMAL (10, 3),
    exportNum DECIMAL (10, 3),
    backNum DECIMAL (10, 3),
    holdNum DECIMAL (10, 3),
    importNum DECIMAL (10, 3),
    currentNum DECIMAL (10, 3)
  ) ;
  DELETE 
  FROM
    temp_cur_stock ;
  WHILE
    i <= cou DO 
    SELECT 
      DATE_ADD(l_sTime, INTERVAL i DAY) INTO nTime 
    FROM
      DUAL ;
-- 是否有货体变化
SELECT 
        COUNT(1) INTO g_cou
        FROM
           t_pcs_goodslog i,
      t_pcs_goods gb ,
      t_pcs_cargo cg
      WHERE i.goodsId = gb.id 
      AND gb.cargoId = cg.id
      AND cg.id=l_cargoId
      AND gb.rootGoodsId IS NULL
      AND i.type <> 8
          AND DATE_FORMAT(
            FROM_UNIXTIME(i.createTime),
            '%Y-%m-%d'
          ) = nTime GROUP BY gb.id ;  
      
      
   -- 查询结存量 
    SELECT 
      IFNULL((SUM(a.goodsSave) + SUM(a.surplus)),0) INTO g_currentNum
    FROM
      t_pcs_goodslog a
    WHERE a.id IN 
      (SELECT 
        MAX(i.id) 
        FROM
           t_pcs_goodslog i,
      t_pcs_goods gb ,
      t_pcs_cargo cg
      WHERE i.goodsId = gb.id 
      AND gb.cargoId = cg.id
      AND cg.id=l_cargoId
      AND gb.rootGoodsId IS NULL
      AND i.type <> 8
          AND DATE_FORMAT(
            FROM_UNIXTIME(i.createTime),
            '%Y-%m-%d'
          ) <= nTime GROUP BY gb.id) ;
          
     IF g_cou>0 THEN
      INSERT INTO temp_cur_stock(l_time,currentNum,deliverNum,exportNum,importNum,backNum,holdNum) SELECT nTime,g_currentNum,IFNULL(SUM(deliverNum),0),IFNULL(SUM(exportNum),0),IFNULL(SUM(importNum),0),IFNULL(SUM(backNum),0),IFNULL(SUM(holdNum),0) FROM (SELECT 
        SUM((CASE WHEN i.type=5 THEN IFNULL(ABS(i.goodsChange),0) ELSE 0 END))deliverNum,-- 发货
        SUM((CASE WHEN i.type=3 THEN IFNULL(ABS(i.goodsChange),0) ELSE 0 END))exportNum, -- 调出
        SUM((CASE WHEN i.type=2 THEN IFNULL(ABS(i.goodsChange),0) ELSE 0 END))importNum, -- 调入
        SUM((CASE WHEN i.type=6 THEN IFNULL(ABS(i.goodsChange),0) ELSE 0 END))backNum, -- 退回
        SUM((CASE WHEN i.type=9 THEN IFNULL(ABS(i.goodsChange),0) ELSE 0 END))holdNum  -- 扣损
        FROM
           t_pcs_goodslog i,
      t_pcs_goods gb ,
      t_pcs_cargo cg
      WHERE i.goodsId = gb.id 
      AND gb.cargoId = cg.id
      AND cg.id=l_cargoId
      AND gb.rootGoodsId IS NULL
      AND i.type <> 8
          AND DATE_FORMAT(
            FROM_UNIXTIME(i.createTime),
            '%Y-%m-%d'
          ) <= nTime GROUP BY gb.id)temp ;
      ELSE 
	INSERT INTO temp_cur_stock(l_time,currentNum,deliverNum,exportNum,importNum,backNum,holdNum) VALUES(nTime,g_currentNum,0,0,0,0,0) ;
      END IF;
    SET i = i + 1 ;
  END WHILE ;
  COMMIT ;
  SELECT 
    * 
  FROM
    temp_cur_stock ;
    END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `getStorageCostForLading`
-- ----------------------------
DROP PROCEDURE IF EXISTS `getStorageCostForLading`;
DELIMITER ;;
CREATE  PROCEDURE `getStorageCostForLading`( IN l_sTime VARCHAR(20),IN l_eTime VARCHAR(20),IN l_ladingId INT)
BEGIN
  DECLARE i INT DEFAULT 0 ;
  DECLARE nTime VARCHAR (30) ;
  DECLARE g_currentNum DECIMAL (12, 3);
  DECLARE cou INT ;
  DECLARE g_cou INT ;
  START TRANSACTION ;
  IF l_sTime = '' 
  OR l_sTime IS NULL 
  THEN SET l_sTime = DATE_ADD(
    CURDATE(),
    INTERVAL - DAY(CURDATE()) + 1 DAY
  ) ;
  END IF ;
  IF l_eTime = '' 
  OR l_eTime IS NULL 
  THEN SET l_eTime = CURDATE() ;
  END IF ;
  SET cou = TO_DAYS(STR_TO_DATE(l_eTime, '%Y-%m-%d')) - TO_DAYS(STR_TO_DATE(l_sTime, '%Y-%m-%d')) ;
  CREATE TEMPORARY TABLE IF NOT EXISTS temp_cur_stock (
    id INT AUTO_INCREMENT PRIMARY KEY,
    l_time VARCHAR (30),
    deliverNum DECIMAL (10, 3),
    exportNum DECIMAL (10, 3),
    backNum DECIMAL (10, 3),
    holdNum DECIMAL (10, 3),
    importNum DECIMAL (10, 3),
    currentNum DECIMAL (10, 3)
  ) ;
  DELETE 
  FROM
    temp_cur_stock ;
  WHILE
    i <= cou DO 
    SELECT 
      DATE_ADD(l_sTime, INTERVAL i DAY) INTO nTime 
    FROM
      DUAL ;
-- 查是否有货体变化数据
      SELECT 
        COUNT(1) INTO g_cou
        FROM
           t_pcs_goodslog i,
      t_pcs_goods gb,
      t_pcs_goods_group gg,
      t_pcs_lading l
      WHERE i.goodsId = gb.id
      AND gb.goodsGroupId=gg.id
      AND l.id=gg.ladingId
      AND l.id=l_ladingId
      AND i.type <> 8
          AND DATE_FORMAT(
            FROM_UNIXTIME(i.createTime),
            '%Y-%m-%d'
          ) = nTime GROUP BY gb.id ;
  --  查结存量
    SELECT 
      IFNULL(SUM(a.goodsSave) + SUM(a.surplus),0) INTO g_currentNum
    FROM
      t_pcs_goodslog a
    WHERE a.id IN 
      (SELECT 
        MAX(i.id) 
        FROM
           t_pcs_goodslog i,
      t_pcs_goods gb,
      t_pcs_goods_group gg,
      t_pcs_lading l
      WHERE i.goodsId = gb.id
      AND gb.goodsGroupId=gg.id
      AND l.id=gg.ladingId
      AND l.id=l_ladingId
      AND i.type <> 8
          AND DATE_FORMAT(
            FROM_UNIXTIME(i.createTime),
            '%Y-%m-%d'
          ) <= nTime GROUP BY gb.id) ;
      IF g_cou>0 THEN
      INSERT INTO temp_cur_stock(l_time,currentNum,deliverNum,exportNum,importNum,backNum,holdNum) SELECT nTime,g_currentNum,IFNULL(SUM(deliverNum),0),IFNULL(SUM(exportNum),0),IFNULL(SUM(importNum),0),IFNULL(SUM(backNum),0),IFNULL(SUM(holdNum),0) FROM (SELECT 
        SUM((CASE WHEN i.type=5 THEN IFNULL(ABS(i.goodsChange),0) ELSE 0 END))deliverNum,-- 发货
        SUM((CASE WHEN i.type=3 THEN IFNULL(ABS(i.goodsChange),0) ELSE 0 END))exportNum, -- 调出
        SUM((CASE WHEN i.type=2 THEN IFNULL(ABS(i.goodsChange),0) ELSE 0 END))importNum, -- 调入
        SUM((CASE WHEN i.type=6 THEN IFNULL(ABS(i.goodsChange),0) ELSE 0 END))backNum, -- 退回
        SUM((CASE WHEN i.type=9 THEN IFNULL(ABS(i.goodsChange),0) ELSE 0 END))holdNum  -- 扣损
        FROM
           t_pcs_goodslog i,
      t_pcs_goods gb,
      t_pcs_goods_group gg,
      t_pcs_lading l
      WHERE i.goodsId = gb.id
      AND gb.goodsGroupId=gg.id
      AND l.id=gg.ladingId
      AND l.id=l_ladingId
      AND i.type <> 8
          AND DATE_FORMAT(
            FROM_UNIXTIME(i.createTime),
            '%Y-%m-%d'
          ) = nTime GROUP BY gb.id) temp ;
      ELSE 
	INSERT INTO temp_cur_stock(l_time,currentNum,deliverNum,exportNum,importNum,backNum,holdNum) VALUES(nTime,g_currentNum,0,0,0,0,0) ;
      END IF; 
    SET i = i + 1 ;
  END WHILE ;
  COMMIT ;
  SELECT 
    * 
  FROM
    temp_cur_stock ;
    END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `getTotalStorageCost`
-- ----------------------------
DROP PROCEDURE IF EXISTS `getTotalStorageCost`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getTotalStorageCost`(IN l_sTime VARCHAR(20),IN l_eTime VARCHAR(20),in flagStr varchar(30))
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
end
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `getTransName`
-- ----------------------------
DROP FUNCTION IF EXISTS `getTransName`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getTransName`(l_id VARCHAR(20)) RETURNS varchar(255) CHARSET utf8
BEGIN
DECLARE temp VARCHAR(255) DEFAULT '';
	DECLARE name1 VARCHAR(255) DEFAULT '';
	DECLARE done TINYINT(1) DEFAULT 0;
	DECLARE	mycursor CURSOR FOR SELECT tb.name FROM t_pcs_trans t LEFT JOIN t_pcs_tube tb ON t.tubeId=tb.id WHERE t.transportId=l_id ;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
	OPEN mycursor ;
	
	REPEAT
		FETCH mycursor INTO temp;
		IF done=0 THEN
			SET name1 := CONCAT(temp,',',name1);
		END IF; 
	UNTIL done END REPEAT;
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
CREATE DEFINER=`root`@`localhost` PROCEDURE `getVehicleDetailData`(in l_trainId int)
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
 
  END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `insertDate`
-- ----------------------------
DROP PROCEDURE IF EXISTS `insertDate`;
DELIMITER ;;
CREATE  PROCEDURE `insertDate`()
BEGIN
       DECLARE i INT DEFAULT 1;
       DECLARE dates VARCHAR(8);
       WHILE i<18250 DO
       SELECT DATE_FORMAT(DATE_ADD('2015-03-31',INTERVAL i DAY),'%Y%m%d') INTO dates FROM DUAL ;
       INSERT INTO t_chart_date(statisDate) VALUES (dates) ;
       SET i = i+1 ;
       END WHILE;
    END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `showChildLst`
-- ----------------------------
DROP PROCEDURE IF EXISTS `showChildLst`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `showChildLst`(IN p_id INT)
    SQL SECURITY INVOKER
BEGIN
	
	DECLARE rootid INT;
		CREATE TEMPORARY TABLE IF NOT EXISTS tmpLst 
	       (sno INT PRIMARY KEY AUTO_INCREMENT,id INT,depth INT);
	      DELETE FROM tmpLst;
		SELECT CASE WHEN rootGoodsId=0 THEN sourceGoodsId WHEN g.rootGoodsId IS NULL THEN g.id ELSE (SELECT sourceGoodsId FROM t_pcs_goods WHERE id=g.rootGoodsId)  END  INTO rootid FROM t_pcs_goods g WHERE id = p_id ;
	      CALL createChildLst(rootid,1);
	    
	      SELECT g.id,g.sourceGoodsId,g.sourceGoodsId parentId,g.code,(CASE WHEN g.ladingClientId IS NOT NULL THEN d.name ELSE c.name END) NAME,(SELECT l.code FROM t_pcs_lading l WHERE l.id=gr.ladingId) ladingCode,
	      (SELECT CASE WHEN l.type='1' THEN '转卖' ELSE '发货' END FROM t_pcs_lading l WHERE l.id=gr.ladingId) ladingType,
	      g.goodsTotal,g.goodsIn,g.goodsOut,g.goodsCurrent,tmpLst.depth lever,(	CASE WHEN g.sourceGoodId = NULL THEN (CASE WHEN g.goodsInspect = 0 THEN g.goodsTank ELSE g.goodsInspect END) ELSE g.goodsTotal END) AS goodsInspect,g.goodsTank
		FROM tmpLst,
			t_pcs_goods g LEFT JOIN t_pcs_client c ON g.clientId = c.id  LEFT JOIN t_pcs_client d ON g.ladingClientId=d.id
			LEFT JOIN t_pcs_goods_group gr ON g.goodsGroupId = gr.id WHERE tmpLst.id=g.id 
			ORDER BY tmpLst.sno;
		
    END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `sp_bill_info_tab`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_bill_info_tab`;
DELIMITER ;;
CREATE  PROCEDURE `sp_bill_info_tab`(IN `clientId` varchar(255),
  IN `productId` integer,
  IN `startDate` VARCHAR (20),
  IN `endDate` VARCHAR (20),
  IN `startPage` INT,
  IN `maxPage` INT,IN `userId` integer)
BEGIN
  DECLARE my_sql VARCHAR (1000) ;
  SET my_sql = 'SELECT tpl.code,tpl.type, DATE_FORMAT(tpl.startTime, "%Y-%m-%d") startTime,DATE_FORMAT(tpl.endTime, "%Y-%m-%d") endTime,tpc.name clientName,tpp.name productName,tsu.name createUserName,tpl.goodsTotal,tpl.goodsPass,tpl.goodsDelivery,tsu2.name reviewUserName,tpgg.code goodsGroupCode,tpgg.id goodsGroupId,tpc2.name receiveClientName from t_pcs_lading tpl LEFT JOIN t_pcs_client tpc ON tpc.id = tpl.clientId  LEFT JOIN t_pcs_product tpp ON tpl.productId = tpp.id LEFT JOIN t_auth_user tsu ON tsu.id = tpl.createUserId LEFT JOIN t_auth_user tsu2 ON tsu2.id = tpl.reviewUserId LEFT JOIN t_pcs_goods_group tpgg ON tpgg.ladingId = tpl.id LEFT JOIN t_pcs_client tpc2 ON tpc2.id = tpl.receiveClientId WHERE 1 = 1 ' ;
if checkClient(userId,clientId)=1 then
  SET @ms = my_sql ;
  IF  (clientId IS NOT NULL
  AND clientId <> '')
  THEN SET @ms = CONCAT(
    @ms,' AND tpl.clientId IN (',
    clientId,
    ')'
  ) ;
  END IF ;
  IF (productId IS NOT NULL 
  AND productId <> -1)
  THEN SET @ms = CONCAT(
    @ms,' AND tpl.productId ',
    '=',
    productId
  ) ;
  END IF ;
   IF (startDate IS NOT NULL 
  AND startDate <> '')
  THEN SET @ms = CONCAT(
    @ms,
    ' AND  DATE_FORMAT(tpl.startTime, "% Y% m % d") >= "',
    startDate,
     '"'
  ) ;
  END IF ;
  IF (endDate <> ''
  AND endDate IS NOT NULL)
  THEN SET @ms = CONCAT(
    @ms,
    ' AND  DATE_FORMAT(tpl.endTime, "% Y% m % d") <= "',
    endDate,
    '"'
  ) ;
  end if;
  SET @ms = CONCAT(
    @ms,
    ' ORDER BY tpl.id DESC LIMIT ',
    startPage ,',' , maxPage
  ) ;
  PREPARE stmt FROM @ms ;
  EXECUTE stmt ;
  DEALLOCATE PREPARE stmt ;
  END if;
END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `sp_bill_info_tab_result`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_bill_info_tab_result`;
DELIMITER ;;
CREATE  PROCEDURE `sp_bill_info_tab_result`(IN `clientId` varchar(255),
IN `productId` integer,
 IN `startDate` VARCHAR(20),
IN `endDate` VARCHAR(20),IN `userId` integer)
BEGIN
  DECLARE my_sql VARCHAR (1000) ;
  SET my_sql = ' select count(1) from t_pcs_lading tpl LEFT JOIN t_pcs_client tpc ON tpc.id = tpl.clientId  LEFT JOIN t_pcs_product tpp ON tpl.productId = tpp.id LEFT JOIN t_auth_user tsu ON tsu.id = tpl.createUserId LEFT JOIN t_auth_user tsu2 ON tsu2.id = tpl.reviewUserId LEFT JOIN t_pcs_goods_group tpgg ON tpgg.ladingId = tpl.id LEFT JOIN t_pcs_client tpc2 ON tpc2.id = tpl.receiveClientId WHERE 1 = 1 ';
if checkClient(userId,clientId)=1 then
  SET @ms = my_sql ;
  IF  (clientId IS NOT NULL
  AND clientId <> '')
  THEN SET @ms = CONCAT(
    @ms,' AND tpl.clientId IN (',
    clientId,
    ')'
  ) ;
  END IF ;
  IF (productId IS NOT NULL 
  AND productId <> -1)
  THEN SET @ms = CONCAT(
    @ms,' AND tpl.productId ',
    '=',
    productId
  ) ;
  END IF ;
   IF (startDate IS NOT NULL 
  AND startDate <> '')
  THEN SET @ms = CONCAT(
    @ms,
    ' AND  DATE_FORMAT(tpl.startTime, "% Y% m % d") >= "',
    startDate,
     '"'
  ) ;
  END IF ;
  IF (endDate <> ''
  AND endDate IS NOT NULL)
  THEN SET @ms = CONCAT(
    @ms,
    ' AND  DATE_FORMAT(tpl.endTime, "% Y% m % d") <= "',
    endDate,
    '"'
  ) ;
  end if;
  PREPARE stmt FROM @ms ;
  EXECUTE stmt ;
  DEALLOCATE PREPARE stmt ;
  END if;
END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `sp_cargoship`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_cargoship`;
DELIMITER ;;
CREATE  PROCEDURE `sp_cargoship`(IN `s_userId` integer,IN `s_shipId` integer,IN `s_productId` integer,IN `s_clientIds` varchar(64),IN `s_start` integer,IN `s_limit` integer)
BEGIN
	#Routine body goes here...
  DECLARE my_sql VARCHAR (2000) ;
  SET my_sql = 'select a.goodsShip,a.goodsPlan,a.id id,a.code code,a.type type,n.value typeName,a.productId productId,b.name productName,a.clientId clientId,c.name clientName,
					 a.contractId contractId,d.code contractCode,d.title contractTitle,d.lossRate ctLossRate,a.taxType taxType,e.value taxTypeValue,a.status status,a.requirement requirement, a.originalArea originalArea,a.cargoAgentId cargoAgentId,
					 f.code cargoAgentCode,a.inspectAgentId inspectAgentId,g.code inspectAgentName,a.certifyAgentId certifyAgentId,h.name certifyAgentName,
					 a.officeGrade officeGrade,k.value officeGradeValue,a.arrivalId arrivalId,DATE_FORMAT(i.arrivalStartTime,"%Y-%m-%d %H:%i:%s") arrivalTime,i.shipId shipId,j.code shipCode,j.name shipName, a.importId importId,
					a.lossRate lossRate,a.goodsInspect goodsInspect,a.goodsTank goodsTank,a.goodsShip goodsShip,a.goodsTotal goodsTotal,a.goodsCurrent goodsCurrent,a.passStatus passStatus,m.value passStatusValue,
					 a.goodsInPass goodsInPass,a.goodsOutPass goodsOutPass,a.description description ,a.goodsPlan goodsPlan,o.status cargoStatus,o.arrivalTime inboundTime ,p.refName shipRefName 
					 FROM t_pcs_cargo a 
					 LEFT JOIN t_pcs_product b on a.productId=b.id
					 LEFT JOIN t_pcs_client c ON a.clientId=c.id
					 LEFT JOIN t_pcs_contract d ON a.contractId=d.id
					 LEFT JOIN t_pcs_tax_type e ON a.taxType=e.key
					 LEFT JOIN t_pcs_cargo_agent f ON a.cargoAgentId=f.id
					 LEFT JOIN t_pcs_inspect_agent g ON a.inspectAgentId=g.id
					 LEFT JOIN t_pcs_certify_agent h on a.certifyAgentId=h.id
					 LEFT JOIN t_pcs_arrival i ON a.arrivalId=i.id
					 LEFT JOIN t_pcs_ship j ON i.shipId=j.id
					 LEFT JOIN t_pcs_office_grade k ON a.officeGrade=k.key
					 LEFT JOIN t_pcs_pass_status m ON a.passStatus=m.key
					 LEFT JOIN t_pcs_trade_type n on a.type=n.key
					 LEFT JOIN t_pcs_work o on o.arrivalId=a.arrivalId and o.productId=a.productId
					 LEFT JOIN t_pcs_ship_ref p on p.id=i.shipRefId
					 WHERE 1=1 ' ;


if checkClient(s_userId,s_clientIds)=1 then

  SET @ms = my_sql ;
IF  (s_shipId <> -1)
  THEN SET @ms = CONCAT(
    @ms,' and j.id=',
    s_shipId
  ) ;
  END IF ;


IF  (s_productId <> -1)
  THEN SET @ms = CONCAT(
    @ms,' and a.productId=',
    s_productId
  ) ;
  END IF ;

  IF (s_clientIds <> '')
  THEN SET @ms = CONCAT(
    @ms,
    ' AND  a.clientId in(',s_clientIds,')'
  ) ;
  end if;

IF  (s_limit <> 0)
  THEN
  SET @ms = CONCAT(
    @ms,
    ' order by i.arrivalStartTime DESC limit ',s_start ,',',s_limit
  ) ;
end if;
  PREPARE stmt FROM @ms ;
  EXECUTE stmt ;
  DEALLOCATE PREPARE stmt ;

end if;



END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `sp_cargoship_count`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_cargoship_count`;
DELIMITER ;;
CREATE  PROCEDURE `sp_cargoship_count`(IN `s_userId` integer,IN `s_shipId` integer,IN `s_productId` integer,IN `s_clientIds` varchar(64))
BEGIN
	#Routine body goes here...
  DECLARE my_sql VARCHAR (2000) ;
  SET my_sql = 'select count(a.id) FROM t_pcs_cargo a 
					 LEFT JOIN t_pcs_product b on a.productId=b.id
					 LEFT JOIN t_pcs_client c ON a.clientId=c.id
					 LEFT JOIN t_pcs_contract d ON a.contractId=d.id
					 LEFT JOIN t_pcs_tax_type e ON a.taxType=e.key
					 LEFT JOIN t_pcs_cargo_agent f ON a.cargoAgentId=f.id
					 LEFT JOIN t_pcs_inspect_agent g ON a.inspectAgentId=g.id
					 LEFT JOIN t_pcs_certify_agent h on a.certifyAgentId=h.id
					 LEFT JOIN t_pcs_arrival i ON a.arrivalId=i.id
					 LEFT JOIN t_pcs_ship j ON i.shipId=j.id
					 LEFT JOIN t_pcs_office_grade k ON a.officeGrade=k.key
					 LEFT JOIN t_pcs_pass_status m ON a.passStatus=m.key
					 LEFT JOIN t_pcs_trade_type n on a.type=n.key
					 LEFT JOIN t_pcs_work o on o.arrivalId=a.arrivalId and o.productId=a.productId
					 LEFT JOIN t_pcs_ship_ref p on p.id=i.shipRefId
					 WHERE 1=1 ' ;


if checkClient(s_userId,s_clientIds)=1 then

  SET @ms = my_sql ;
IF  (s_shipId <> -1)
  THEN SET @ms = CONCAT(
    @ms,' and j.id=',
    s_shipId
  ) ;
  END IF ;


IF  (s_productId <> -1)
  THEN SET @ms = CONCAT(
    @ms,' and a.productId=',
    s_productId
  ) ;
  END IF ;

  IF (s_clientIds <> '')
  THEN SET @ms = CONCAT(
    @ms,
    ' AND  a.clientId in(',s_clientIds,')'
  ) ;
  end if;

  PREPARE stmt FROM @ms ;
  EXECUTE stmt ;
  DEALLOCATE PREPARE stmt ;

end if;



END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `sp_cargo_statistics`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_cargo_statistics`;
DELIMITER ;;
CREATE  PROCEDURE `sp_cargo_statistics`(IN `s_userId` integer,IN `s_clientIds` varchar(64),IN `s_productId` integer,IN `s_startTime` varchar(64),IN `s_endTime` varchar(64),IN `s_start` integer,IN `s_limit` integer)
BEGIN
	#Routine body goes here...
  DECLARE my_sql VARCHAR (2000) ;
DECLARE d_time BIGINT ;
  SET my_sql = 'select ';
if(s_endTime<>-1) then set d_time=s_endTime+86400;
else set d_time=s_startTime+86400;end if;

if checkClient(s_userId,s_clientIds)=1 then

  SET @ms = my_sql ;

SET @ms = CONCAT(
    @ms,' g.name productName,a.id cargoId,a.clientId,e.name clientName,f.name shipName,a.code ,d.shipId,d.arrivalStartTime,a.isInspect,a.goodsTank,tab.goodsOld,tab.goodsSend  from t_pcs_cargo a  LEFT JOIN t_pcs_arrival d on a.arrivalId=d.id  LEFT JOIN t_pcs_client e on e.id=a.clientId  LEFT JOIN t_pcs_ship f on f.id=d.shipId  LEFT JOIN t_pcs_product g on g.id=a.productId  LEFT OUTER JOIN (SELECT ot.goodsSend,tt.goodsOld,ot.cargoId FROM (SELECT COALESCE(SUM(gl.actualNum),0) AS goodsSend,g.cargoId FROM t_pcs_goods g,t_pcs_goodslog gl WHERE g.id = gl.goodsId AND g.`status` <> 1 AND gl.type <> 8 AND gl.createTime <',d_time,'  GROUP BY g.cargoId) ot LEFT OUTER JOIN (SELECT COALESCE(SUM(gl.actualNum),0) AS goodsOld,g.cargoId FROM t_pcs_goods g,t_pcs_goodslog gl WHERE g.id = gl.goodsId AND g.`status` <> 1 AND gl.type <> 8 AND gl.createTime <',s_startTime,'  GROUP BY g.cargoId) tt ON ot.cargoId = tt.cargoId) tab on tab.cargoId = a.id  where (a.goodsTotal<>(select sum(g.actualNum) from t_pcs_goodslog g LEFT JOIN t_pcs_goods h on h.id=g.goodsId LEFT JOIN t_pcs_cargo i on h.cargoId=i.id where i.id=a.id and g.createTime<',d_time,' and g.type=5) or (select sum(g.actualNum) from t_pcs_goodslog g LEFT JOIN t_pcs_goods h on h.id=g.goodsId LEFT JOIN t_pcs_cargo i on h.cargoId=i.id where i.id=a.id and g.createTime<',d_time,' and g.type=5) is null ) ' 

  ) ;

IF  (s_productId <> -1)
  THEN SET @ms = CONCAT(
    @ms,' and a.productId=',
    s_productId
  ) ;
  END IF ;
SET @ms = CONCAT(
    @ms,' and d.arrivalStartTime<"',
    from_unixtime(d_time),'"'
  ) ;

  IF (s_clientIds <> '')
  THEN SET @ms = CONCAT(
    @ms,
    ' AND  a.clientId in(',s_clientIds,')'
  ) ;
  end if;


IF  (s_limit <> 0)
  THEN
  SET @ms = CONCAT(
    @ms,
    ' order by d.arrivalStartTime DESC limit ',s_start ,',',s_limit
  ) ;
end if;
  PREPARE stmt FROM @ms ;
  EXECUTE stmt ;
  DEALLOCATE PREPARE stmt ;

end if;



END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `sp_cargo_statistics_count`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_cargo_statistics_count`;
DELIMITER ;;
CREATE  PROCEDURE `sp_cargo_statistics_count`(IN `s_userId` integer,IN `s_clientIds` varchar(64),IN `s_productId` integer,IN `s_startTime` varchar(64),IN `s_endTime` varchar(64))
BEGIN
	#Routine body goes here...
  DECLARE my_sql VARCHAR (2000) ;
DECLARE d_time BIGINT ;
  SET my_sql = 'select ';
if(s_endTime<>-1) then set d_time=s_endTime+86400;
else set d_time=s_startTime+86400;end if;

if checkClient(s_userId,s_clientIds)=1 then

  SET @ms = my_sql ;

SET @ms = CONCAT(
    @ms,' count(a.id)  from t_pcs_cargo a  LEFT JOIN t_pcs_arrival d on a.arrivalId=d.id  LEFT JOIN t_pcs_client e on e.id=a.clientId  LEFT JOIN t_pcs_ship f on f.id=d.shipId  LEFT JOIN t_pcs_product g on g.id=a.productId  LEFT OUTER JOIN (SELECT ot.goodsSend,tt.goodsOld,ot.cargoId FROM (SELECT COALESCE(SUM(gl.actualNum),0) AS goodsSend,g.cargoId FROM t_pcs_goods g,t_pcs_goodslog gl WHERE g.id = gl.goodsId AND g.`status` <> 1 AND gl.type <> 8 AND gl.createTime <',d_time,'  GROUP BY g.cargoId) ot LEFT OUTER JOIN (SELECT COALESCE(SUM(gl.actualNum),0) AS goodsOld,g.cargoId FROM t_pcs_goods g,t_pcs_goodslog gl WHERE g.id = gl.goodsId AND g.`status` <> 1 AND gl.type <> 8 AND gl.createTime <',s_startTime,'  GROUP BY g.cargoId) tt ON ot.cargoId = tt.cargoId) tab on tab.cargoId = a.id  where (a.goodsTotal<>(select sum(g.actualNum) from t_pcs_goodslog g LEFT JOIN t_pcs_goods h on h.id=g.goodsId LEFT JOIN t_pcs_cargo i on h.cargoId=i.id where i.id=a.id and g.createTime<',d_time,' and g.type=5) or (select sum(g.actualNum) from t_pcs_goodslog g LEFT JOIN t_pcs_goods h on h.id=g.goodsId LEFT JOIN t_pcs_cargo i on h.cargoId=i.id where i.id=a.id and g.createTime<',d_time,' and g.type=5) is null ) ' 

  ) ;

IF  (s_productId <> -1)
  THEN SET @ms = CONCAT(
    @ms,' and a.productId=',
    s_productId
  ) ;
  END IF ;
SET @ms = CONCAT(
    @ms,' and d.arrivalStartTime<"',
    from_unixtime(d_time),'"'
  ) ;

  IF (s_clientIds <> '')
  THEN SET @ms = CONCAT(
    @ms,
    ' AND  a.clientId in(',s_clientIds,')'
  ) ;
  end if;



  PREPARE stmt FROM @ms ;
  EXECUTE stmt ;
  DEALLOCATE PREPARE stmt ;

end if;



END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `sp_contract`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_contract`;
DELIMITER ;;
CREATE  PROCEDURE `sp_contract`(IN `s_userId` integer,IN `s_id` integer,IN `s_code` varchar(64),IN `s_title` varchar(64),IN `s_clientIds` varchar(64),IN `s_productId` integer,IN `s_type` integer,IN `s_status` varchar(64),IN `s_startTime` timestamp,IN `s_endTime` timestamp,IN `s_start` integer,IN `s_limit` integer)
BEGIN
	#Routine body goes here...
  DECLARE my_sql VARCHAR (1000) ;
  SET my_sql = 'SELECT *,DATE_FORMAT(createTime,"%Y-%m-%d %H:%i:%s") mCreateTime,DATE_FORMAT(editTime,"%Y-%m-%d %H:%i:%s") mEditTime,DATE_FORMAT(signDate,"%Y-%m-%d %H:%i:%s") mSignDate from view_contract where 1=1 ' ;
if checkClient(s_userId,s_clientIds)=1 then

  SET @ms = my_sql ;
  IF  (s_id <> -1)
  THEN SET @ms = CONCAT(
    @ms,' and id=',
    s_id
  ) ;
  END IF ;

  IF (s_code <> '' )
  THEN SET @ms = CONCAT(
    @ms,' AND code like ',
    '"%',
    s_code,'%"'
  ) ;
  END IF ;
  IF (s_title <> '' )
  THEN SET @ms = CONCAT(
    @ms,' AND title like ',
    '"%',
    s_title,'%"'
  ) ;
  END IF ;
  IF (s_clientIds <> '')
  THEN SET @ms = CONCAT(
    @ms,
    ' AND  clientId in(',s_clientIds,')'
  ) ;
  end if;
IF  (s_productId <> -1)
  THEN SET @ms = CONCAT(
    @ms,' and productId=',
    s_productId
  ) ;
  END IF ;
IF  (s_type <> -1)
  THEN SET @ms = CONCAT(
    @ms,' and type=',
    s_type
  ) ;
  END IF ;

 SET @ms = CONCAT(
    @ms,
    ' AND  status in(',s_status,')'
  ) ;

 SET @ms = CONCAT(
    @ms,
    ' AND  signDate between "',s_startTime,'" and "',s_endTime,'"'
  ) ;
IF  (s_limit <> 0)
  THEN
  SET @ms = CONCAT(
    @ms,
    ' order by signDate DESC limit ',s_start ,',',s_limit
  ) ;
end if;
  PREPARE stmt FROM @ms ;
  EXECUTE stmt ;
  DEALLOCATE PREPARE stmt ;

end if;








END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `sp_contract_count`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_contract_count`;
DELIMITER ;;
CREATE  PROCEDURE `sp_contract_count`(IN `s_userId` integer,IN `s_id` integer,IN `s_code` varchar(64),IN `s_title` varchar(64),IN `s_clientIds` varchar(64),IN `s_productId` integer,IN `s_type` integer,IN `s_status` varchar(64),IN `s_startTime` timestamp,IN `s_endTime` timestamp)
BEGIN
	#Routine body goes here...
  DECLARE my_sql VARCHAR (1000) ;
  SET my_sql = 'SELECT count(id) from view_contract where 1=1 ' ;
if checkClient(s_userId,s_clientIds)=1 then

  SET @ms = my_sql ;
  IF  (s_id <> -1)
  THEN SET @ms = CONCAT(
    @ms,' and id=',
    s_id
  ) ;
  END IF ;

  IF (s_code <> '' )
  THEN SET @ms = CONCAT(
    @ms,' AND code like ',
    '"%',
    s_code,'%"'
  ) ;
  END IF ;
  IF (s_title <> '' )
  THEN SET @ms = CONCAT(
    @ms,' AND title like ',
    '"%',
    s_title,'%"'
  ) ;
  END IF ;
  IF (s_clientIds <> '')
  THEN SET @ms = CONCAT(
    @ms,
    ' AND  clientId in(',s_clientIds,')'
  ) ;
  end if;
IF  (s_productId <> -1)
  THEN SET @ms = CONCAT(
    @ms,' and productId=',
    s_productId
  ) ;
  END IF ;
IF  (s_type <> -1)
  THEN SET @ms = CONCAT(
    @ms,' and type=',
    s_type
  ) ;
  END IF ;

 SET @ms = CONCAT(
    @ms,
    ' AND  status in(',s_status,')'
  ) ;

 SET @ms = CONCAT(
    @ms,
    ' AND  signDate between "',s_startTime,'" and "',s_endTime,'"'
  ) ;


  PREPARE stmt FROM @ms ;
  EXECUTE stmt ;
  DEALLOCATE PREPARE stmt ;

end if;








END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `sp_goods`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_goods`;
DELIMITER ;;
CREATE  PROCEDURE `sp_goods`(IN `s_userId` integer,IN `s_code` varchar(64),IN `s_cargoCode` varchar(64),IN `s_goodsId` integer,IN `s_clientIds` varchar(64),IN `s_productId` integer,IN `s_startTime` varchar(64),IN `s_endTime` varchar(64),IN `s_start` integer,IN `s_limit` integer)
BEGIN
	#Routine body goes here...
  DECLARE my_sql VARCHAR (2000) ;
  SET my_sql = 'select a.customsPassCode ,a.isPredict ,a.id id,a.tankCodes,a.code code,a.contractId contractId,b.code contractCode,a.goodsGroupId goodsGroupId,a.cargoId cargoId,a.clientId clientId,c.name clientName,a.productId productId,f.name productName,a.sourceGoodsId sourceGoodsId, a.rootGoodsId rootGoodsId,
					a.tankId tankId,d.code tankCode,DATE_FORMAT(a.createTime,"%Y-%m-%d %H:%i:%s") createTime,a.lossRate lossRate,a.goodsInspect goodsInspect,a.goodsTank goodsTank,a.goodsTotal goodsTotal,a.goodsIn goodsIn,a.goodsOut goodsOut,
					a.goodsInPass goodsInPass,a.goodsOutPass goodsOutPass,a.goodsCurrent goodsCurrent,a.status status,a.description  description,
					e.id  zid,e.code zcode,e.clientId zclientId,e.productId zproductId,e.ladingId zladingId,e.goodsInspect zgoodsInspect,e.goodsTank zgoodsTank,e.goodsTotal zgoodsTotal,e.goodsIn zgoodsIn,
					e.goodsOut zgoodsOut,e.goodsCurrent zgoodsCurrent,e.goodsInPass zgoodsInPass,e.goodsOutPass zgoodsOutPass,g.code cargoCode,h.arrivalStartTime,i.name shipName,j.code sourceCode,k.code rootCode
					 FROM t_pcs_goods a 
					 LEFT JOIN t_pcs_contract b ON a.contractId=b.id
					 LEFT JOIN t_pcs_client c ON a.clientId=c.id
					 LEFT JOIN t_pcs_tank d ON a.tankId=d.id
					 LEFT JOIN t_pcs_goods_group e ON a.goodsGroupId=e.id 
					 LEFT JOIN t_pcs_product f on a.productId=f.id
					 LEFT JOIN t_pcs_cargo g on a.cargoId=g.id
					 LEFT JOIN t_pcs_arrival h on h.id=g.arrivalId
					 LEFT JOIN t_pcs_ship i on i.id=h.shipId
					 LEFT JOIN t_pcs_goods j on j.id=a.sourceGoodsId  
					 LEFT JOIN t_pcs_goods k on k.id=a.rootGoodsId  
					 where 1=1 and a.status<>1 ' ;


if checkClient(s_userId,s_clientIds)=1 then

  SET @ms = my_sql ;

IF (s_code <> '' )
  THEN SET @ms = CONCAT(
    @ms,' AND a.code like ',
    '"%',
    s_code,'%"'
  ) ;
  END IF ;

IF (s_cargoCode <> '' )
  THEN SET @ms = CONCAT(
    @ms,' AND g.code like ',
    '"%',
    s_cargoCode,'%"'
  ) ;
  END IF ;

IF (s_cargoCode <> '' )
  THEN SET @ms = CONCAT(
    @ms,' AND g.code like ',
    '"%',
    s_cargoCode,'%"'
  ) ;
  END IF ;

  IF  (s_goodsId <> -1)
  THEN SET @ms = CONCAT(
    @ms,' and a.id=',
    s_goodsId
  ) ;
  END IF ;

  IF (s_clientIds <> '')
  THEN SET @ms = CONCAT(
    @ms,
    ' AND  a.clientId in(',s_clientIds,')'
  ) ;
  end if;
IF  (s_productId <> -1)
  THEN SET @ms = CONCAT(
    @ms,' and a.productId=',
    s_productId
  ) ;
  END IF ;

IF (s_startTime <> '' )
  THEN SET @ms = CONCAT(
    @ms,' AND h.arrivalStartTime >= "',
    s_startTime,'"'
  ) ;
  END IF ;
 
IF (s_endTime <> '' )
  THEN SET @ms = CONCAT(
    @ms,' AND h.arrivalStartTime <= "',
    s_endTime,'"'
  ) ;
  END IF ;

IF  (s_limit <> 0)
  THEN
  SET @ms = CONCAT(
    @ms,
    ' order by h.arrivalStartTime DESC limit ',s_start ,',',s_limit
  ) ;
end if;
  PREPARE stmt FROM @ms ;
  EXECUTE stmt ;
  DEALLOCATE PREPARE stmt ;

end if;



END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `sp_goods_count`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_goods_count`;
DELIMITER ;;
CREATE  PROCEDURE `sp_goods_count`(IN `s_userId` integer,IN `s_code` varchar(64),IN `s_cargoCode` varchar(64),IN `s_goodsId` integer,IN `s_clientIds` varchar(64),IN `s_productId` integer,IN `s_startTime` varchar(64),IN `s_endTime` varchar(64))
BEGIN
	#Routine body goes here...
  DECLARE my_sql VARCHAR (1000) ;
  SET my_sql = 'select count(a.id) FROM t_pcs_goods a 
					 LEFT JOIN t_pcs_contract b ON a.contractId=b.id
					 LEFT JOIN t_pcs_client c ON a.clientId=c.id
					 LEFT JOIN t_pcs_tank d ON a.tankId=d.id
					 LEFT JOIN t_pcs_goods_group e ON a.goodsGroupId=e.id 
					 LEFT JOIN t_pcs_product f on a.productId=f.id
					 LEFT JOIN t_pcs_cargo g on a.cargoId=g.id
					 LEFT JOIN t_pcs_arrival h on h.id=g.arrivalId
					 LEFT JOIN t_pcs_ship i on i.id=h.shipId
					 LEFT JOIN t_pcs_goods j on j.id=a.sourceGoodsId  
					 LEFT JOIN t_pcs_goods k on k.id=a.rootGoodsId  
					 where 1=1 and a.status<>1 ' ;


if checkClient(s_userId,s_clientIds)=1 then

  SET @ms = my_sql ;

IF (s_code <> '' )
  THEN SET @ms = CONCAT(
    @ms,' AND a.code like ',
    '"%',
    s_code,'%"'
  ) ;
  END IF ;

IF (s_cargoCode <> '' )
  THEN SET @ms = CONCAT(
    @ms,' AND g.code like ',
    '"%',
    s_cargoCode,'%"'
  ) ;
  END IF ;

IF (s_cargoCode <> '' )
  THEN SET @ms = CONCAT(
    @ms,' AND g.code like ',
    '"%',
    s_cargoCode,'%"'
  ) ;
  END IF ;

  IF  (s_goodsId <> -1)
  THEN SET @ms = CONCAT(
    @ms,' and a.id=',
    s_goodsId
  ) ;
  END IF ;

  IF (s_clientIds <> '')
  THEN SET @ms = CONCAT(
    @ms,
    ' AND  a.clientId in(',s_clientIds,')'
  ) ;
  end if;
IF  (s_productId <> -1)
  THEN SET @ms = CONCAT(
    @ms,' and a.productId=',
    s_productId
  ) ;
  END IF ;

IF (s_startTime <> '' )
  THEN SET @ms = CONCAT(
    @ms,' AND h.arrivalStartTime >= "',
    s_startTime,'"'
  ) ;
  END IF ;
 
IF (s_endTime <> '' )
  THEN SET @ms = CONCAT(
    @ms,' AND h.arrivalStartTime <= "',
    s_endTime,'"'
  ) ;
  END IF ;


  PREPARE stmt FROM @ms ;
  EXECUTE stmt ;
  DEALLOCATE PREPARE stmt ;

end if;



END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `sp_goods_dynamic_stock_tab`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_goods_dynamic_stock_tab`;
DELIMITER ;;
CREATE  PROCEDURE `sp_goods_dynamic_stock_tab`(IN `clientId` varchar(255),IN `productId` integer,IN `startDate` varchar(20),IN `endDate` varchar(20),IN `startPage` int,IN `maxPage` int,IN `userId` integer)
BEGIN
	DECLARE my_sql VARCHAR (2000) ;
  SET my_sql = 'select a.id id,a.code code,a.productId productId,b.name productName,
                a.clientId clientId,c.name clientName,FROM_UNIXTIME(o.arrivalTime,"%Y-%m-%d") inboundTime
								FROM t_pcs_cargo a
								LEFT JOIN t_pcs_product b on a.productId=b.id
								LEFT JOIN t_pcs_client c ON a.clientId=c.id
								LEFT JOIN t_pcs_contract d ON a.contractId=d.id
								LEFT JOIN t_pcs_tax_type e ON a.taxType=e.key
								LEFT JOIN t_pcs_cargo_agent f ON a.cargoAgentId=f.id
								LEFT JOIN t_pcs_inspect_agent g ON a.inspectAgentId=g.id
								LEFT JOIN t_pcs_certify_agent h on a.certifyAgentId=h.id
								LEFT JOIN t_pcs_arrival i ON a.arrivalId=i.id
								LEFT JOIN t_pcs_ship j ON i.shipId=j.id
								LEFT JOIN t_pcs_office_grade k ON a.officeGrade=k.key
								LEFT JOIN t_pcs_pass_status m ON a.passStatus=m.key
								LEFT JOIN t_pcs_trade_type n on a.type=n.key
							  LEFT JOIN t_pcs_work o on o.arrivalId=a.arrivalId and o.productId=a.productId
								WHERE 1=1 ' ;
if checkClient(userId,clientId)=1 then
  SET @ms = my_sql ;
  IF  (clientId IS NOT NULL
  AND clientId <> '')
  THEN SET @ms = CONCAT(
    @ms,' AND a.clientId in (',
    clientId,
    ')'
  ) ;
  END IF ;
  IF (productId IS NOT NULL 
  AND productId <> -1)
  THEN SET @ms = CONCAT(
    @ms,' AND a.productId ',
    '=',
    productId
  ) ;
  END IF ;
   IF (startDate IS NOT NULL 
  AND startDate <> '')
  THEN SET @ms = CONCAT(
    @ms,
    ' and FROM_UNIXTIME(o.arrivalTime,"%Y%m%d") >= "',
    startDate,
     '"'
  ) ;
  END IF ;
  IF (endDate <> ''
  AND endDate IS NOT NULL)
  THEN SET @ms = CONCAT(
    @ms,
    ' and FROM_UNIXTIME(o.arrivalTime,"%Y%m%d") <= "',
    endDate,
    '"'
  ) ;
  end if;
  SET @ms = CONCAT(
    @ms,
    ' order by o.arrivalTime desc LIMIT ',
    startPage ,',' , maxPage
  ) ;
  PREPARE stmt FROM @ms ;
  EXECUTE stmt ;
  DEALLOCATE PREPARE stmt ;
  END if;
END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `sp_goods_dynamic_stock_tab_result`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_goods_dynamic_stock_tab_result`;
DELIMITER ;;
CREATE  PROCEDURE `sp_goods_dynamic_stock_tab_result`(IN `clientId` varchar(255),IN `productId` integer,IN `startDate` varchar(20),IN `endDate` varchar(20),IN `userId` integer)
BEGIN
	DECLARE my_sql VARCHAR (2000) ;
  SET my_sql = 'select count(1)
								FROM t_pcs_cargo a
								LEFT JOIN t_pcs_product b on a.productId=b.id
								LEFT JOIN t_pcs_client c ON a.clientId=c.id
								LEFT JOIN t_pcs_contract d ON a.contractId=d.id
								LEFT JOIN t_pcs_tax_type e ON a.taxType=e.key
								LEFT JOIN t_pcs_cargo_agent f ON a.cargoAgentId=f.id
								LEFT JOIN t_pcs_inspect_agent g ON a.inspectAgentId=g.id
								LEFT JOIN t_pcs_certify_agent h on a.certifyAgentId=h.id
								LEFT JOIN t_pcs_arrival i ON a.arrivalId=i.id
								LEFT JOIN t_pcs_ship j ON i.shipId=j.id
								LEFT JOIN t_pcs_office_grade k ON a.officeGrade=k.key
								LEFT JOIN t_pcs_pass_status m ON a.passStatus=m.key
								LEFT JOIN t_pcs_trade_type n on a.type=n.key
							  LEFT JOIN t_pcs_work o on o.arrivalId=a.arrivalId and o.productId=a.productId
								WHERE 1=1 ' ;
if checkClient(userId,clientId)=1 then
  SET @ms = my_sql ;
  IF  (clientId IS NOT NULL
  AND clientId <> '')
  THEN SET @ms = CONCAT(
    @ms,' AND a.clientId in (',
    clientId,
    ')'
  ) ;
  END IF ;
  IF (productId IS NOT NULL 
  AND productId <> -1)
  THEN SET @ms = CONCAT(
    @ms,' AND a.productId ',
    '=',
    productId
  ) ;
  END IF ;
   IF (startDate IS NOT NULL 
  AND startDate <> '')
  THEN SET @ms = CONCAT(
    @ms,
    ' and FROM_UNIXTIME(o.arrivalTime,"%Y%m%d") >= "',
    startDate,
     '"'
  ) ;
  END IF ;
  IF (endDate <> ''
  AND endDate IS NOT NULL)
  THEN SET @ms = CONCAT(
    @ms,
    ' and FROM_UNIXTIME(o.arrivalTime,"%Y%m%d") <= "',
    endDate,
    '"'
  ) ;
  end if;
  PREPARE stmt FROM @ms ;
  EXECUTE stmt ;
  DEALLOCATE PREPARE stmt ;
  END if;
END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `sp_goods_dynamic_tab`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_goods_dynamic_tab`;
DELIMITER ;;
CREATE  PROCEDURE `sp_goods_dynamic_tab`(in `groupId` varchar(255),
in `productId` int,
 in `statisDate` varchar(8),in `startPage` int,
in `maxPage` int)
BEGIN
DECLARE my_sql VARCHAR (1000) ;
SET my_sql = 'select 
  t1.code,
  t2.name as productName,t3.name clientName,
  t0.type as operation,
  t0.goodsChange,
  FROM_UNIXTIME(
    t0.createTime,
    "%Y-%m-%d %h:%i:%s"
  ) as createTime,
  t0.deliverNo,
  t4.code as stockCode,
  t0.type 
from
  t_pcs_goods t 
  left join t_pcs_goodslog t0 
    on t.id = t0.goodsId 
  left join t_pcs_cargo t1 
    on t.cargoId = t1.id 
  left join t_pcs_product t2 
    on t.productId = t2.id 
  left join t_pcs_client t3 
    on t.clientId = t3.id 
  left join t_pcs_lading t4 
    on t0.ladingId = t4.id 
where t0.type = 5 ';
SET @ms = my_sql ;
IF (groupId IS NOT NULL 
  AND groupId <> '' )
  THEN SET @ms = CONCAT(
    @ms,' AND t1.id ',
    '=',
    groupId
  ) ;
  END IF ;
IF (productId IS NOT NULL 
  AND productId <> -1 )
  THEN SET @ms = CONCAT(
    @ms,' AND t.productId ',
    '=',
    productId
  ) ;
  END IF ;
IF (statisDate IS NOT NULL 
  AND statisDate <> '' )
  THEN SET @ms = CONCAT(
    @ms,' and DATE_FORMAT(FROM_UNIXTIME(t0.createTime),"%Y%m%d") ',
    '=',
    statisDate
  ) ;
  END IF ;
SET @ms = CONCAT(
    @ms,
    ' ORDER BY t.id limit ',
    startPage ,',' , maxPage
  ) ;
  PREPARE stmt FROM @ms ;
  EXECUTE stmt ;
  DEALLOCATE PREPARE stmt ;
    END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `sp_goods_dynamic_tab_result`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_goods_dynamic_tab_result`;
DELIMITER ;;
CREATE  PROCEDURE `sp_goods_dynamic_tab_result`(IN `groupId` varchar(255),
 IN `productId` INT, IN `statisDate` VARCHAR(8))
BEGIN
DECLARE my_sql VARCHAR (1000) ;
SET my_sql = 'select 
  count(1)
from
  t_pcs_goods t 
  left join t_pcs_goodslog t0 
    on t.id = t0.goodsId 
  left join t_pcs_cargo t1 
    on t.cargoId = t1.id 
  left join t_pcs_product t2 
    on t.productId = t2.id 
  left join t_pcs_client t3 
    on t.clientId = t3.id 
  left join t_pcs_lading t4 
    on t0.ladingId = t4.id 
where t0.type = 5 ';
SET @ms = my_sql ;
IF (groupId IS NOT NULL 
  AND groupId <> '' )
  THEN SET @ms = CONCAT(
    @ms,' AND t1.id ',
    '=',
    groupId
  ) ;
  END IF ;
IF (productId IS NOT NULL 
  AND productId <> -1 )
  THEN SET @ms = CONCAT(
    @ms,' AND t.productId ',
    '=',
    productId
  ) ;
  END IF ;
IF (statisDate IS NOT NULL 
  AND statisDate <> '' )
  THEN SET @ms = CONCAT(
    @ms,' and DATE_FORMAT(FROM_UNIXTIME(t0.createTime),"%Y%m%d") ',
    '=',
    statisDate
  ) ;
  END IF ;
  PREPARE stmt FROM @ms ;
  EXECUTE stmt ;
  DEALLOCATE PREPARE stmt ;
    END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `sp_goods_statistics`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_goods_statistics`;
DELIMITER ;;
CREATE  PROCEDURE `sp_goods_statistics`(IN `s_userId` integer,IN `s_clientIds` varchar(64),IN `s_productId` integer,IN `s_startTime` varchar(64),IN `s_endTime` varchar(64),IN `s_start` integer,IN `s_limit` integer)
BEGIN
	#Routine body goes here...
  DECLARE my_sql VARCHAR (2000) ;
DECLARE d_time BIGINT ;
  SET my_sql = 'select DISTINCT(a.goodsId) from t_pcs_goodslog a LEFT JOIN t_pcs_goods b on b.id=a.goodsId where ' ;


if checkClient(s_userId,s_clientIds)=1 then
set d_time=s_endTime+86400;
  SET @ms = my_sql ;

	SET @ms = CONCAT(
    @ms,'a.createTime>=',s_startTime,' and a.createTime<',d_time
  ) ;

IF  (s_productId <> -1)
  THEN SET @ms = CONCAT(
    @ms,' and b.productId=',
    s_productId
  ) ;
  END IF ;


  IF (s_clientIds <> '')
  THEN SET @ms = CONCAT(
    @ms,
    ' AND  b.clientId in(',s_clientIds,')'
  ) ;
  end if;


IF  (s_limit <> 0)
  THEN
  SET @ms = CONCAT(
    @ms,
    ' order by a.createTime ASC limit ',s_start ,',',s_limit
  ) ;
end if;
  PREPARE stmt FROM @ms ;
  EXECUTE stmt ;
  DEALLOCATE PREPARE stmt ;

end if;



END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `sp_goods_statistics_count`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_goods_statistics_count`;
DELIMITER ;;
CREATE  PROCEDURE `sp_goods_statistics_count`(IN `s_userId` integer,IN `s_clientIds` varchar(64),IN `s_productId` integer,IN `s_startTime` varchar(64),IN `s_endTime` varchar(64))
BEGIN
	#Routine body goes here...
  DECLARE my_sql VARCHAR (2000) ;
DECLARE d_time BIGINT ;
  SET my_sql = 'select DISTINCT(a.goodsId) from t_pcs_goodslog a LEFT JOIN t_pcs_goods b on b.id=a.goodsId where ' ;


if checkClient(s_userId,s_clientIds)=1 then
set d_time=s_endTime+86400;
  SET @ms = my_sql ;

	SET @ms = CONCAT(
    @ms,'a.createTime>=',s_startTime,' and a.createTime<',d_time
  ) ;

IF  (s_productId <> -1)
  THEN SET @ms = CONCAT(
    @ms,' and b.productId=',
    s_productId
  ) ;
  END IF ;


  IF (s_clientIds <> '')
  THEN SET @ms = CONCAT(
    @ms,
    ' AND  b.clientId in(',s_clientIds,')'
  ) ;
  end if;



  PREPARE stmt FROM @ms ;
  EXECUTE stmt ;
  DEALLOCATE PREPARE stmt ;

end if;



END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `sp_out_store_info_tab`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_out_store_info_tab`;
DELIMITER ;;
CREATE  PROCEDURE `sp_out_store_info_tab`(in `clientId` varchar(255),in `productId` int,in `startPage` int,in `maxPage` int,IN `userId` integer)
BEGIN
DECLARE my_sql VARCHAR (2000) ;
  SET my_sql = 'select 
  t3.code as itemId,
  t1.name as productName,
  ABS(t0.goodsChange) as goodsOutPass,
  t.createTime,
  CASE
    t0.deliverType 
    WHEN "1" 
    then t5.code 
    WHEN "2" 
    then t6.code 
  END carshipCode,
  CASE
    WHEN t0.deliverType = 1 
    THEN 
    (SELECT 
      s.value 
    FROM
      t_pcs_train t,
      t_pcs_arrival_status s 
    WHERE t.status = s.key 
      AND t.id = t0.batchId) 
    WHEN t0.deliverType = 2 
    THEN 
    (SELECT 
      s.value 
    FROM
      t_pcs_arrival a,
      t_pcs_arrival_status s 
    WHERE a.status = s.key 
      AND a.id = t0.batchId) 
  END type,
  t4.code as tankId 
FROM
  t_pcs_goods t 
  LEFT JOIN t_pcs_goodslog t0 
    on t.id = t0.goodsId 
  LEFT JOIN t_pcs_product t1 
    on t.productId = t1.id 
  LEFT JOIN t_pcs_client t2 
    on t.clientId = t2.id 
  LEFT JOIN t_pcs_lading t3 
    on t0.ladingId = t3.id 
  LEFT JOIN t_pcs_tank t4 
    ON t.tankId = t4.id 
  LEFT JOIN t_pcs_truck t5 
    on t0.batchId = t5.id 
  LEFT JOIN t_pcs_ship t6 
    on t0.vehicleShipId = t6.id 
where t0.type = 5 ';
if checkClient(userId,clientId)=1 then
  SET @ms = my_sql ;
IF  (clientId IS NOT NULL
  AND clientId <> '')
  THEN SET @ms = CONCAT(
    @ms,' AND t.clientId IN (',
    clientId,
    ')'
  ) ;
  END IF ;
  IF (productId IS NOT NULL 
  AND productId <> -1)
  THEN SET @ms = CONCAT(
    @ms,' AND t.productId ',
    '=',
    productId
  ) ;
  END IF ;
  SET @ms = CONCAT(
    @ms,
    ' ORDER BY t.id desc LIMIT ',
    startPage ,',' , maxPage
  ) ;
  PREPARE stmt FROM @ms ;
  EXECUTE stmt ;
  DEALLOCATE PREPARE stmt ;
  END if;
 END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `sp_out_store_info_tab_result`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_out_store_info_tab_result`;
DELIMITER ;;
CREATE  PROCEDURE `sp_out_store_info_tab_result`(IN `clientId` varchar(255),IN `productId` INT,IN `userId` integer)
BEGIN
    
   DECLARE my_sql VARCHAR (2000) ;
  SET my_sql = 'select 
  count(1)
FROM
  t_pcs_goods t 
  LEFT JOIN t_pcs_goodslog t0 
    on t.id = t0.goodsId 
  LEFT JOIN t_pcs_product t1 
    on t.productId = t1.id 
  LEFT JOIN t_pcs_client t2 
    on t.clientId = t2.id 
  LEFT JOIN t_pcs_lading t3 
    on t0.ladingId = t3.id 
  LEFT JOIN t_pcs_tank t4 
    ON t.tankId = t4.id 
  LEFT JOIN t_pcs_truck t5 
    on t0.batchId = t5.id 
  LEFT JOIN t_pcs_ship t6 
    on t0.vehicleShipId = t6.id 
where t0.type = 5 ';
if checkClient(userId,clientId)=1 then
  SET @ms = my_sql ;
IF  (clientId IS NOT NULL
  AND clientId <> '')
  THEN SET @ms = CONCAT(
    @ms,' AND t.clientId IN (',
    clientId,
    ')'
  ) ;
  END IF ;
  IF (productId IS NOT NULL 
  AND productId <> -1)
  THEN SET @ms = CONCAT(
    @ms,' AND t.productId ',
    '=',
    productId
  ) ;
  END IF ;
  PREPARE stmt FROM @ms ;
  EXECUTE stmt ;
  DEALLOCATE PREPARE stmt ;
  END if;
 END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `sp_store_dynamic_out_stock`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_store_dynamic_out_stock`;
DELIMITER ;;
CREATE  PROCEDURE `sp_store_dynamic_out_stock`(IN `clientId` varchar(255),
IN `productId` INT,
IN `startDate` VARCHAR(20),
IN `endDate` VARCHAR(20),IN `userId` integer)
BEGIN
DECLARE my_sql VARCHAR (1000) ;
  SET my_sql = 'SELECT 
  DATE_FORMAT(c.statisDate, "%Y-%m-%d") AS statisDate,
  (
    CASE
      WHEN tt.outStock IS NULL 
      THEN 0 
      ELSE tt.outStock 
    END
  ) AS outStock,
  (
    CASE
      WHEN tt.type IS NULL 
      THEN 0 
      ELSE tt.type 
    END
  ) AS type 
FROM
  t_chart_date c 
  LEFT OUTER JOIN 
    (SELECT 
      DATE_FORMAT(
        FROM_UNIXTIME(t.createTime),
        "%Y%m%d"
      ) AS createTime,
      ABS(
        ROUND(IFNULL(SUM(t.goodsChange), 0), 3)
      ) AS outStock,
      IFNULL(COUNT(t.type), 0) AS type 
    FROM
      t_pcs_goodslog t 
      LEFT JOIN t_pcs_goods t0 
        ON t.goodsId = t0.id 
      LEFT JOIN t_pcs_product t1 
        ON t0.`productId` = t1.`id` 
      LEFT JOIN t_pcs_client t2 
        ON t0.`clientId` = t2.`id` 
    WHERE t.`type` = 5 ' ;
if checkClient(userId,clientId)=1 then
  SET @ms = my_sql ;
  IF  (clientId IS NOT NULL
  AND clientId <> '')
  THEN SET @ms = CONCAT(
    @ms,' and t0.clientId in (',
    clientId,
    ')'
  ) ;
  END IF ;
  IF (productId IS NOT NULL 
  AND productId <> -1)
  THEN SET @ms = CONCAT(
    @ms,' and t0.productId ',
    '=',
    productId
  ) ;
  END IF ;
   IF (startDate IS NOT NULL 
  AND startDate <> '')
  THEN SET @ms = CONCAT(
    @ms,
    ' and DATE_FORMAT(
        FROM_UNIXTIME(t.createTime),
        "%Y%m%d"
      ) >= "',
    startDate,
     '"'
  ) ;
  END IF ;
  IF (endDate <> ''
  AND endDate IS NOT NULL)
  THEN SET @ms = CONCAT(
    @ms,
    ' and DATE_FORMAT(
        FROM_UNIXTIME(t.createTime),
        "%Y%m%d"
      ) <= "',
    endDate,
    '"'
  ) ;
  end if;
  SET @ms = CONCAT(
    @ms,
    ' GROUP BY DATE_FORMAT(FROM_UNIXTIME(t.createTime),"%Y%m%d") ORDER BY t.`createTime`) tt ',
    ' ON c.statisDate = tt.createTime where 1 = 1 '
  ) ;
IF (startDate IS NOT NULL 
  AND startDate <> '')
  THEN SET @ms = CONCAT(
    @ms,
    ' and c.statisDate >= "',
    startDate,
     '"'
  ) ;
  END IF ;
  IF (endDate <> ''
  AND endDate IS NOT NULL)
  THEN SET @ms = CONCAT(
    @ms,
    ' and c.statisDate <= "',
    endDate,
    '"'
  ) ;
  end if;
  PREPARE stmt FROM @ms ;
  EXECUTE stmt ;
  DEALLOCATE PREPARE stmt ;
  END if;
END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `sp_store_dynamic_putin_amount`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_store_dynamic_putin_amount`;
DELIMITER ;;
CREATE  PROCEDURE `sp_store_dynamic_putin_amount`(IN `clientId` varchar(255),IN `productId` INT,IN `startDate` varchar(20),IN `endDate` varchar(20),IN `userId` integer)
BEGIN
DECLARE my_sql VARCHAR (1000) ;
  SET my_sql = 'SELECT 
  DATE_FORMAT(c.statisDate, "%Y-%m-%d") AS statisDate,
  (
    CASE
      WHEN tt.outStock IS NULL 
      THEN 0 
      ELSE tt.outStock 
    END
  ) AS outStock,
  (
    CASE
      WHEN tt.type IS NULL 
      THEN 0 
      ELSE tt.type 
    END
  ) AS type 
FROM
  t_chart_date c 
  LEFT OUTER JOIN 
    (SELECT 
      DATE_FORMAT(
        FROM_UNIXTIME(t.createTime),
        "%Y%m%d"
      ) AS createTime,
      ABS(
        ROUND(IFNULL(SUM(t.goodsChange), 0), 3)
      ) AS outStock,
      IFNULL(COUNT(t.type), 0) AS type 
    FROM
      t_pcs_goodslog t 
      LEFT JOIN t_pcs_goods t0 
        ON t.goodsId = t0.id 
      LEFT JOIN t_pcs_product t1 
        ON t0.`productId` = t1.`id` 
      LEFT JOIN t_pcs_client t2 
        ON t0.`clientId` = t2.`id` 
    WHERE t.`type` = 2 ' ;
if checkClient(userId,clientId)=1 then
  SET @ms = my_sql ;
  IF  (clientId IS NOT NULL
  AND clientId <> '')
  THEN SET @ms = CONCAT(
    @ms,' and t0.clientId in (',
    clientId,
    ')'
  ) ;
  END IF ;
  IF (productId IS NOT NULL 
  AND productId <> -1)
  THEN SET @ms = CONCAT(
    @ms,' and t0.productId ',
    '=',
    productId
  ) ;
  END IF ;
   IF (startDate IS NOT NULL 
  AND startDate <> '')
  THEN SET @ms = CONCAT(
    @ms,
    ' and DATE_FORMAT(
        FROM_UNIXTIME(t.createTime),
        "%Y%m%d"
      ) >= "',
    startDate,
     '"'
  ) ;
  END IF ;
  IF (endDate <> ''
  AND endDate IS NOT NULL)
  THEN SET @ms = CONCAT(
    @ms,
    ' and DATE_FORMAT(
        FROM_UNIXTIME(t.createTime),
        "%Y%m%d"
      ) <= "',
    endDate,
    '"'
  ) ;
  end if;
  SET @ms = CONCAT(
    @ms,
    ' GROUP BY DATE_FORMAT(FROM_UNIXTIME(t.createTime),"%Y%m%d") ORDER BY t.`createTime`) tt ',
    ' ON c.statisDate = tt.createTime where 1 = 1 '
  ) ;
IF (startDate IS NOT NULL 
  AND startDate <> '')
  THEN SET @ms = CONCAT(
    @ms,
    ' and c.statisDate >= "',
    startDate,
     '"'
  ) ;
  END IF ;
  IF (endDate <> ''
  AND endDate IS NOT NULL)
  THEN SET @ms = CONCAT(
    @ms,
    ' and c.statisDate <= "',
    endDate,
    '"'
  ) ;
  end if;
  PREPARE stmt FROM @ms ;
  EXECUTE stmt ;
  DEALLOCATE PREPARE stmt ;
  END if;
END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `sp_store_dynamic_stock_amount`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_store_dynamic_stock_amount`;
DELIMITER ;;
CREATE  PROCEDURE `sp_store_dynamic_stock_amount`(IN `clientId` varchar(255),IN `productId` INT,IN `startDate` varchar(20),IN `endDate` varchar(20),IN `userId` integer)
BEGIN
DECLARE my_sql VARCHAR (1000) ;
  SET my_sql = 'SELECT 
  DATE_FORMAT(c.statisDate, "%Y-%m-%d") AS statisDate,
  (
    CASE
      WHEN tt.outStock IS NULL 
      THEN 0 
      ELSE tt.outStock 
    END
  ) AS outStock,
  (
    CASE
      WHEN tt.type IS NULL 
      THEN 0 
      ELSE tt.type 
    END
  ) AS type 
FROM
  t_chart_date c 
  LEFT OUTER JOIN 
    (SELECT 
      DATE_FORMAT(
        FROM_UNIXTIME(t.createTime),
        "%Y%m%d"
      ) AS createTime,
      ABS(
        ROUND(IFNULL(SUM(t.goodsChange), 0), 3)
      ) AS outStock,
      IFNULL(COUNT(t.type), 0) AS type 
    FROM
      t_pcs_goodslog t 
      LEFT JOIN t_pcs_goods t0 
        ON t.goodsId = t0.id 
      LEFT JOIN t_pcs_product t1 
        ON t0.`productId` = t1.`id` 
      LEFT JOIN t_pcs_client t2 
        ON t0.`clientId` = t2.`id` 
    WHERE t.`type` = 1 ' ;
if checkClient(userId,clientId)=1 then
  SET @ms = my_sql ;
  IF  (clientId IS NOT NULL
  AND clientId <> '')
  THEN SET @ms = CONCAT(
    @ms,' and t0.clientId in (',
    clientId,
    ')'
  ) ;
  END IF ;
  IF (productId IS NOT NULL 
  AND productId <> -1)
  THEN SET @ms = CONCAT(
    @ms,' and t0.productId ',
    '=',
    productId
  ) ;
  END IF ;
   IF (startDate IS NOT NULL 
  AND startDate <> '')
  THEN SET @ms = CONCAT(
    @ms,
    ' and DATE_FORMAT(
        FROM_UNIXTIME(t.createTime),
        "%Y%m%d"
      ) >= "',
    startDate,
     '"'
  ) ;
  END IF ;
  IF (endDate <> ''
  AND endDate IS NOT NULL)
  THEN SET @ms = CONCAT(
    @ms,
    ' and DATE_FORMAT(
        FROM_UNIXTIME(t.createTime),
        "%Y%m%d"
      ) <= "',
    endDate,
    '"'
  ) ;
  end if;
  SET @ms = CONCAT(
    @ms,
    ' GROUP BY DATE_FORMAT(FROM_UNIXTIME(t.createTime),"%Y%m%d") ORDER BY t.`createTime`) tt ',
    ' ON c.statisDate = tt.createTime where 1 = 1 '
  ) ;
IF (startDate IS NOT NULL 
  AND startDate <> '')
  THEN SET @ms = CONCAT(
    @ms,
    ' and c.statisDate >= "',
    startDate,
     '"'
  ) ;
  END IF ;
  IF (endDate <> ''
  AND endDate IS NOT NULL)
  THEN SET @ms = CONCAT(
    @ms,
    ' and c.statisDate <= "',
    endDate,
    '"'
  ) ;
  end if;
  PREPARE stmt FROM @ms ;
  EXECUTE stmt ;
  DEALLOCATE PREPARE stmt ;
  END if;
END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `sp_store_dynamic_tab`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_store_dynamic_tab`;
DELIMITER ;;
CREATE  PROCEDURE `sp_store_dynamic_tab`(in `clientId` int,
				     in `productId` int,
				     in `statisDate` varchar(8),
				     in `beginPage` int,
				     in `maxPage` int)
BEGIN
select 
  t0.cargoId,
  t2.name as clientName,
  t.type as operation,
  t.goodsChange,
  FROM_UNIXTIME(
    t.createTime,
    '%Y-%m-%d %h:%i:%s'
  ) as createTime,
  t.deliverNo,
  t3.code,
  t.type,
  t4.code stockCode 
FROM
  t_pcs_goodslog t 
  LEFT JOIN t_pcs_goods t0 
    ON t.goodsId = t0.id 
  LEFT JOIN t_pcs_product t1 
    ON t0.productId = t1.id 
  LEFT JOIN t_pcs_client t2 
    ON t.clientId = t2.id 
  LEFT JOIN t_pcs_lading t3 
    ON t.ladingId = t3.id 
  LEFT JOIN t_pcs_cargo t4 
    on t0.cargoId = t4.id 
where t.type = 5 
  and t0.clientId in (clientId) 
  and t0.productId = productId 
  and FROM_UNIXTIME(t.createTime, '%Y%m%d') = statisDate 
  ORDER BY t.id
  limit beginPage,maxPage;
	
    END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `sp_store_dynamic_tab_result`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_store_dynamic_tab_result`;
DELIMITER ;;
CREATE  PROCEDURE `sp_store_dynamic_tab_result`(IN `clientId` INT,
				     IN `productId` INT,
				     IN `statisDate` VARCHAR(8))
BEGIN
	
select 
  count(1) 
FROM
  t_pcs_goodslog t 
  LEFT JOIN t_pcs_goods t0 
    ON t.goodsId = t0.id 
  LEFT JOIN t_pcs_product t1 
    ON t0.productId = t1.id 
  LEFT JOIN t_pcs_client t2 
    ON t.clientId = t2.id 
  LEFT JOIN t_pcs_lading t3 
    ON t.ladingId = t3.id 
  LEFT JOIN t_pcs_cargo t4 
    ON t0.cargoId = t4.id 
WHERE t.type = 5 
  AND t0.clientId IN (clientId) 
  AND t0.productId = productId 
  AND FROM_UNIXTIME(t.createTime, '%Y%m%d') = statisDate;
    END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `sp_tank_chart`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_tank_chart`;
DELIMITER ;;
CREATE  PROCEDURE `sp_tank_chart`(IN `productId` int,IN `tankId` varchar(20))
BEGIN
	DECLARE my_sql VARCHAR (1000) ;
  SET my_sql = 'select t2.`code` AS productId,ifnull(t2.name,"未使用") AS productName,COUNT(t1.code) tankNum
                FROM t_pcs_tank t1 left join t_pcs_product t2 on t1.`productId` = t2.`id`
                where 1=1 ' ;
  SET @ms = my_sql ;
  IF (productId IS NOT NULL 
  AND productId <> -1)
  THEN SET @ms = CONCAT(
    @ms,' AND t1.productId ',
    '=',
    productId
  ) ;
  END IF ;
   IF (tankId IS NOT NULL 
  AND tankId <> '')
  THEN SET @ms = CONCAT(
    @ms,
    ' and t1.id = "',
    tankId,
     '"'
  ) ;
  END IF ;
  SET @ms = CONCAT(
    @ms,
    ' GROUP BY t2.code ',
     'ORDER BY t2.id '
  ) ;
  PREPARE stmt FROM @ms ;
  EXECUTE stmt ;
  DEALLOCATE PREPARE stmt ;
END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `sp_tank_tab`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_tank_tab`;
DELIMITER ;;
CREATE  PROCEDURE `sp_tank_tab`(IN `productId`  int,IN `tankId` varchar(20),IN `startPage`  int,IN `maxPage`  int)
BEGIN
	DECLARE my_sql VARCHAR (1000) ;
  SET my_sql = 'select t2.`id` id ,t1.`id`,t1.code as code,t1.`capacityTotal`,t1.`testDensity`,t2.`name`
                FROM t_pcs_tank t1 left join t_pcs_product t2 on t1.`productId` = t2.`id`
                where 1=1 ' ;
  SET @ms = my_sql ;
  IF (productId IS NOT NULL 
  AND productId <> -1)
  THEN SET @ms = CONCAT(
    @ms,' AND t1.productId ',
    '=',
    productId
  ) ;
  END IF ;
   IF (tankId IS NOT NULL 
  AND tankId <> '')
  THEN SET @ms = CONCAT(
    @ms,
    ' and t1.id = "',
    tankId,
     '"'
  ) ;
  END IF ;
  SET @ms = CONCAT(
    @ms,
    ' ORDER BY t1.id DESC LIMIT ',
    startPage ,',' , maxPage
  ) ;
  PREPARE stmt FROM @ms ;
  EXECUTE stmt ;
  DEALLOCATE PREPARE stmt ;
END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `sp_tank_tab_result`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_tank_tab_result`;
DELIMITER ;;
CREATE  PROCEDURE `sp_tank_tab_result`(IN `productId` int,IN `tankId` varchar(20))
BEGIN
	DECLARE my_sql VARCHAR (1000) ;
  SET my_sql = 'select count(1)
                FROM t_pcs_tank t1 left join t_pcs_product t2 on t1.`productId` = t2.`id`
                where 1=1 ' ;
  SET @ms = my_sql ;
  IF (productId IS NOT NULL 
  AND productId <> -1)
  THEN SET @ms = CONCAT(
    @ms,' AND t1.productId ',
    '=',
    productId
  ) ;
  END IF ;
   IF (tankId IS NOT NULL 
  AND tankId <> '')
  THEN SET @ms = CONCAT(
    @ms,
    ' and t1.id = "',
    tankId,
     '"'
  ) ;
  END IF ;
  PREPARE stmt FROM @ms ;
  EXECUTE stmt ;
  DEALLOCATE PREPARE stmt ;
END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `updateGoodslogAfUpNum`
-- ----------------------------
DROP PROCEDURE IF EXISTS `updateGoodslogAfUpNum`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateGoodslogAfUpNum`(IN l_batchId int,IN l_rough DECIMAL(12,3),IN l_tare DECIMAL(12,3))
BEGIN
	  DECLARE done TINYINT(1) DEFAULT 0;
	  declare g_deliverNum DECIMAL(12,3);
	  declare g_dNum DECIMAL(12,3)  ;
	  DECLARE g_id INT;
	  DECLARE mycursor CURSOR FOR SELECT id,deliverNum FROM t_pcs_goodslog WHERE deliverType=1 AND batchId=l_batchId AND TYPE=5;
	  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
	  set g_dNum = l_rough-l_tare ;
	OPEN mycursor ;
	REPEAT   --  分发实发数量
		FETCH mycursor INTO g_id,g_deliverNum ;
		IF done=0 THEN
			IF g_dNum-g_deliverNum>=0 THEN
				UPDATE t_pcs_goodslog SET afUpNum=g_deliverNum,afDiffNum=g_deliverNum-actualNum WHERE id = g_id ;
				SET g_dNum = g_dNum-g_deliverNum ;
			ELSE 
				UPDATE t_pcs_goodslog SET afUpNum=g_dNum,afDiffNum=g_dNum-actualNum WHERE id = g_id ;
				SET g_dNum =0 ;
			END IF ;
		END IF;
		UNTIL done END REPEAT;
	CLOSE mycursor ;
    END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `updateVehicleDeliverInfoForMeasure`
-- ----------------------------
DROP PROCEDURE IF EXISTS `updateVehicleDeliverInfoForMeasure`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateVehicleDeliverInfoForMeasure`(IN l_serialNo VARCHAR(30),IN l_meterEnd DECIMAL(10,3),IN l_meterStart DECIMAL(10,3))
BEGIN
	DECLARE g_batchId INT;
	DECLARE g_totalNum DECIMAL(12,3) ;
	DECLARE g_goodsId INT;
	DECLARE g_id INT;
	DECLARE g_deliverNum DECIMAL(12,3) ;
	DECLARE done TINYINT(1) DEFAULT 0;
	DECLARE mycursor CURSOR FOR SELECT id,deliverNum,goodsId FROM t_pcs_goodslog WHERE deliverType=1 AND batchId=g_batchId AND TYPE=5;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
	START TRANSACTION;
	SELECT l_meterEnd-l_meterStart,i.batchId INTO g_totalNum,g_batchId FROM t_pcs_goodslog i WHERE i.serial=l_serialNo;
	UPDATE t_pcs_train SET STATUS=43,flag=2 WHERE id=(SELECT batchId FROM t_pcs_goodslog WHERE deliverType=1 AND SERIAL=new.serial) ;
	OPEN mycursor ;
	REPEAT   --  分发实发数量
		FETCH mycursor INTO g_id,g_deliverNum,g_goodsId ;
		IF done=0 THEN
			IF g_totalNum-g_deliverNum>=0 THEN
				UPDATE t_pcs_goodslog SET actualNum=g_deliverNum WHERE id = g_id ;
				SET g_totalNum = g_totalNum-g_deliverNum ;
			ELSE 
				UPDATE t_pcs_goodslog SET actualNum = g_totalNum WHERE id = g_id ;
				UPDATE t_pcs_goods SET goodsCurrent=goodsCurrent+g_deliverNum-g_totalNum  WHERE id=g_goodsId;-- 还回
				SET g_totalNum =0 ;
			END IF ;
		END IF;
		UNTIL done END REPEAT;
	CLOSE mycursor ;
	COMMIT;
    END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `updateVehicleDeliverInfoForWeigh`
-- ----------------------------
DROP PROCEDURE IF EXISTS `updateVehicleDeliverInfoForWeigh`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateVehicleDeliverInfoForWeigh`(IN l_serialNo VARCHAR(30),IN g_dNum DECIMAL(12,3))
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
				UPDATE   t_pcs_goodslog  t SET  actualNum = g_totalNum,goodsChange=-g_totalNum WHERE id=g_id ;	-- ,surplus = (SELECT  IFNULL(ROUND(g.goodsCurrent - ( g.goodsTotal - (CASE WHEN g.goodsInPass - g.goodsOutPass >= 0 THEN g.goodsOutPass  ELSE g.goodsInPass  END ) ),3),0)FROM t_pcs_goods g WHERE g.id=g_goodsId)
				update t_pcs_goodslog set surplus=surplus+g_deliverNum-g_totalNum where id>=g_id and goodsId=g_goodsId ;
				UPDATE   t_pcs_lading l SET l.goodsDelivery = goodsDelivery + g_totalNum WHERE l.id = (SELECT ladingId FROM t_pcs_goods_group 
					  WHERE id = (SELECT goodsGroupId FROM t_pcs_goods WHERE id =g_goodsId));
				SET g_totalNum =0 ;
			END IF ;
		END IF;
		UNTIL done END REPEAT;
	CLOSE mycursor ;
    END
;;
DELIMITER ;

-- ----------------------------
--  Records 
-- ----------------------------
