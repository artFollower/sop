ALTER TABLE `t_pcs_berth_assess`
MODIFY COLUMN `weather`  varchar(25) NULL DEFAULT NULL AFTER `arrivalId`,
MODIFY COLUMN `windPower`  varchar(25) NULL DEFAULT NULL AFTER `windDirection`;


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
    END$$

DELIMITER ;

ALTER TABLE `sop`.`t_pcs_goodslog`   
  ADD COLUMN `tankId` INT NULL AFTER `afDiffNum`;

  ALTER TABLE `sop`.`t_pcs_weighbridge`   
  ADD COLUMN `tankId` INT NULL AFTER `actualTareWeight`;

INSERT INTO `t_auth_security_resources` (`category`, `description`, `name`, `indentifier`, `status`) VALUES('MENU','称重信息统计','称重信息统计','WEIGHDAILYSTATEMENT','0');