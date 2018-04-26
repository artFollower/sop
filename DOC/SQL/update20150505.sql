ALTER TABLE `sop`.`t_pcs_weighbridge`   
  CHANGE `deliveryNum` `deliveryNum` DECIMAL(9,2) NULL,
  CHANGE `inWeigh` `inWeigh` DECIMAL(9,2) NULL,
  CHANGE `measureWeigh` `measureWeigh` DECIMAL(9,2) NULL,
  CHANGE `outWeigh` `outWeigh` DECIMAL(9,2) NULL,
  ADD COLUMN `actualRoughWeight` DECIMAL(9,2) NULL  COMMENT '实际皮重' AFTER `status`,
  ADD COLUMN `actualTareWeight` DECIMAL(9,2) NULL  COMMENT '实际毛重' AFTER `actualRoughWeight`;
ALTER TABLE `sop`.`t_pcs_measure`   
  ADD COLUMN `serial` VARCHAR(22) NULL AFTER `parkId`;
ALTER TABLE `sop`.`t_pcs_train`   
  ADD COLUMN `isChange` INT(1) DEFAULT 0  NULL  COMMENT '1已做调整 0 未作调整' AFTER `flag`;

ALTER TABLE `sop`.`t_pcs_measure`   
  DROP COLUMN `plateId`, 
  DROP COLUMN `ladingNum`, 
  DROP COLUMN `remark`, 
  DROP COLUMN `parkId`, 
  DROP COLUMN `tandId`, 
  CHANGE `meterStart` `meterStart` DECIMAL(12,3) NULL  COMMENT '小表起',
  CHANGE `meterEnd` `meterEnd` DECIMAL(12,3) NULL  COMMENT '小表止',
  CHANGE `actualNum` `deliverNum` DECIMAL(12,3) NULL  COMMENT '发货量',
  ADD COLUMN `actualMeterStart` DECIMAL(12,3) NULL  COMMENT '调整后的小表起' AFTER `trainId`,
  ADD COLUMN `actualMeterEnd` DECIMAL(12,3) NULL  COMMENT '调整后的小表止' AFTER `actualMeterStart`,
  ADD COLUMN `actualDeliverNum` DECIMAL(12,3) NULL  COMMENT '调整后的实发量' AFTER `actualMeterEnd`;

ALTER TABLE `sop`.`t_pcs_train`   
  ADD COLUMN `flag` INT(1) NULL  COMMENT '1称重计量  2 流量计量' AFTER `plateId`;


DELIMITER $$

USE `sop`$$

DROP PROCEDURE IF EXISTS `showChildLst`$$

CREATE  PROCEDURE `showChildLst`(IN p_id INT)
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
	      g.goodsTotal,g.goodsIn,g.goodsOut,g.goodsCurrent,tmpLst.depth lever
		FROM tmpLst,
			t_pcs_goods g LEFT JOIN t_pcs_client c ON g.clientId = c.id  LEFT JOIN t_pcs_client d ON g.ladingClientId=d.id
			LEFT JOIN t_pcs_goods_group gr ON g.goodsGroupId = gr.id WHERE tmpLst.id=g.id 
			ORDER BY tmpLst.sno;
		
    END$$

DELIMITER ;



DELIMITER $$

USE `sop`$$

DROP PROCEDURE IF EXISTS `updateVehicleDeliverInfoForWeigh`$$

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
				UPDATE   t_pcs_lading l SET l.goodsDelivery = goodsDelivery - g_deliverNum WHERE l.id = (SELECT ladingId FROM t_pcs_goods_group 
					  WHERE id = (SELECT goodsGroupId FROM t_pcs_goods WHERE id =g_id));
			ELSE 
				UPDATE t_pcs_goodslog SET actualNum = g_totalNum WHERE id = g_id ;
				UPDATE t_pcs_goods SET goodsCurrent=goodsCurrent+g_deliverNum-g_totalNum  WHERE id=g_goodsId;-- 还回
				UPDATE   t_pcs_lading l SET l.goodsDelivery = goodsDelivery - g_totalNum WHERE l.id = (SELECT ladingId FROM t_pcs_goods_group 
					  WHERE id = (SELECT goodsGroupId FROM t_pcs_goods WHERE id =g_id));
				SET g_totalNum =0 ;
			END IF ;
		END IF;
		UNTIL done END REPEAT;
	CLOSE mycursor ;
	
    END$$

DELIMITER ;





DELIMITER $$

USE `sop`$$

DROP PROCEDURE IF EXISTS `updateVehicleDeliverInfoForMeasure`$$

CREATE  PROCEDURE `updateVehicleDeliverInfoForMeasure`(IN l_serialNo VARCHAR(30),IN l_meterEnd DECIMAL(10,3),IN l_meterStart DECIMAL(10,3))
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
    END$$

DELIMITER ;





DELIMITER $$

USE `sop`$$

DROP PROCEDURE IF EXISTS `getDeliverInfo`$$

CREATE  PROCEDURE `getDeliverInfo`(IN l_type INT,IN l_batchId INT)
BEGIN
	  DECLARE t_status INT ;
	  DECLARE done TINYINT(1) DEFAULT 0;
	  DECLARE g_totalNum DOUBLE ;
	  DECLARE g_deliverNum DOUBLE ;
	  DECLARE g_id INT;
	  DECLARE g_max DOUBLE ;
	  DECLARE g_flag INT;
	  DECLARE g_afUpNum DECIMAL(10,3);-- ????
	  DECLARE g_afUpDiffNum DECIMAL(10,3);-- ??????
	  DECLARE mycursor CURSOR FOR SELECT id,deliverNum FROM t_pcs_goodslog WHERE deliverType=l_type AND batchId=l_batchId AND TYPE=5;
	  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
	  START TRANSACTION;
	  SELECT SUM(actualNum) INTO g_max FROM t_pcs_goodslog WHERE deliverType=l_type AND batchId=l_batchId ;
	  IF l_type=2 THEN     --  ??
		SELECT SUM(IFNULL(ABS(realAmount),0)) INTO g_totalNum FROM t_pcs_store WHERE transportId=(SELECT id FROM t_pcs_transport_program WHERE arrivalId=l_batchId) ;
	IF g_max IS NULL OR g_max<=0 THEN 
	OPEN mycursor ;
	REPEAT   --  ??????
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
		SELECT flag INTO g_flag FROM t_pcs_train WHERE id=l_batchId ;
		IF g_flag=1 THEN 
			SELECT CASE WHEN i.tempDeliverNum IS NOT NULL THEN tempDeliverNum ELSE(wb.actualRoughWeight-wb.actualTareWeight)END ,CASE WHEN i.tempDeliverNum IS NOT NULL THEN i.tempDeliverNum-i.actualNum ELSE (wb.actualRoughWeight-wb.actualTareWeight-i.actualNum) END INTO g_afUpNum,g_afUpDiffNum FROM t_pcs_weighbridge wb,t_pcs_goodslog i,t_pcs_train t WHERE t.id=l_batchId AND i.batchId=t.id AND i.deliverType=1 AND i.serial=wb.serial;
		ELSE 
			SELECT CASE WHEN i.tempDeliverNum IS NOT NULL THEN tempDeliverNum ELSE (m.actualMeterEnd-m.actualDeliverNum) END ,CASE WHEN i.tempDeliverNum IS NOT NULL THEN i.tempDeliverNum-i.actualNum ELSE(m.actualMeterEnd-m.actualDeliverNum-i.actualNum) END INTO g_afUpNum,g_afUpDiffNum FROM t_pcs_measure m,t_pcs_train t,t_pcs_goodslog i WHERE t.id = m.trainId AND t.id = l_batchId AND i.batchId=t.id AND i.deliverType=1;
		END IF;
		SELECT 	  t.isChange,i.id,	  i.batchId,	  i.goodsId,	  i.deliverNum,	  c.name ladingClientName, IFNULL((SELECT l.code FROM t_pcs_lading l
	  WHERE l.id = (SELECT ladingId FROM t_pcs_goods_group WHERE id = g.goodsGroupId)),'') ladingCode,p.name productName,
	    i.deliverNum,g.code goodsCode,(g.goodsCurrent-(g.goodsTotal-(CASE WHEN g.goodsInPass-g.goodsOutPass>=0 THEN g.goodsOutPass ELSE g.goodsInPass END))) goodsCurrent,
	    i.actualNum,g_afUpNum,g_afUpDiffNum FROM t_pcs_goodslog i LEFT JOIN t_pcs_client c ON c.id = i.ladingClientId LEFT JOIN t_pcs_goods g ON g.id=i.goodsId LEFT JOIN t_pcs_train t ON t.id=i.batchId,
	  t_pcs_product p WHERE i.deliverType = l_type  AND i.batchId = l_batchId AND g.id = i.goodsId AND p.id=g.productId AND i.type=5;
	 END IF;
	
    END$$

DELIMITER ;