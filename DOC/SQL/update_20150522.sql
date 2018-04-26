/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.6.21-log : Database - sop
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/* Procedure structure for procedure `getDeliverInfo` */

DROP PROCEDURE IF EXISTS  `getDeliverInfo` ;

DELIMITER $$

CREATE  PROCEDURE `getDeliverInfo`(IN l_type INT,IN l_batchId INT)
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
	
    END */$$
DELIMITER ;

/* Procedure structure for procedure `updateGoodslogAfUpNum` */

DROP PROCEDURE IF EXISTS  `updateGoodslogAfUpNum` ;

DELIMITER $$

CREATE  PROCEDURE `updateGoodslogAfUpNum`(IN l_batchId int,IN l_rough DECIMAL(12,3),IN l_tare DECIMAL(12,3))
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
    END $$
DELIMITER ;

/* Procedure structure for procedure `updateVehicleDeliverInfoForWeigh` */

DROP PROCEDURE IF EXISTS  `updateVehicleDeliverInfoForWeigh` ;

DELIMITER $$

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
				UPDATE   t_pcs_goodslog  t SET  actualNum = g_totalNum,goodsChange=-g_totalNum WHERE id=g_id ;	-- ,surplus = (SELECT  IFNULL(ROUND(g.goodsCurrent - ( g.goodsTotal - (CASE WHEN g.goodsInPass - g.goodsOutPass >= 0 THEN g.goodsOutPass  ELSE g.goodsInPass  END ) ),3),0)FROM t_pcs_goods g WHERE g.id=g_goodsId)
				update t_pcs_goodslog set surplus=surplus+g_deliverNum-g_totalNum where id>=g_id and goodsId=g_goodsId ;
				UPDATE   t_pcs_lading l SET l.goodsDelivery = goodsDelivery + g_totalNum WHERE l.id = (SELECT ladingId FROM t_pcs_goods_group 
					  WHERE id = (SELECT goodsGroupId FROM t_pcs_goods WHERE id =g_goodsId));
				SET g_totalNum =0 ;
			END IF ;
		END IF;
		UNTIL done END REPEAT;
	CLOSE mycursor ;
    END $$
DELIMITER ;


