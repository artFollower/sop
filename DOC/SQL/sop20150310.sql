DELIMITER $$

USE `sop`$$

DROP PROCEDURE IF EXISTS `deliverVehicleRevoke`$$

CREATE  PROCEDURE `deliverVehicleRevoke`(IN l_trainId INT)
BEGIN
  
  DECLARE t_status INT ;
  DECLARE done TINYINT(1) DEFAULT 0;
  DECLARE g_deliverNum DOUBLE;
  DECLARE g_actualNum DOUBLE;
  DECLARE g_goodsId INT;
  DECLARE g_Id INT;
  DECLARE mycursor CURSOR FOR SELECT id,deliverNum,actualNum,goodsId FROM t_pcs_goodslog WHERE deliverType=1 AND TYPE=5 AND batchId=l_trainId ;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
  SELECT STATUS INTO t_status FROM t_pcs_train WHERE id=l_trainId ;
  IF t_status!=43 THEN
	OPEN mycursor ;
	REPEAT
		FETCH mycursor INTO g_id,g_deliverNum,g_actualNum,g_goodsId ;
		IF done=0 THEN
			UPDATE t_pcs_goods g SET g.goodsCurrent=g.goodsCurrent+g_deliverNum WHERE id=g_goodsId ;
			UPDATE t_pcs_goodslog SET TYPE=8 WHERE id=g_id ;
		END IF;
		UNTIL done END REPEAT;
	CLOSE mycursor ;
  ELSE
	OPEN mycursor ;
	REPEAT
		FETCH mycursor INTO g_id,g_deliverNum,g_actualNum,g_goodsId ;
		IF done=0 THEN
		UPDATE t_pcs_goods g SET g.goodsCurrent=g.goodsCurrent+g_actualNum WHERE id=g_goodsId ;
		UPDATE   t_pcs_lading l 
				SET
				  l.goodsDelivery = goodsDelivery - g_actualNum 
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
		UPDATE t_pcs_goodslog SET TYPE=8 WHERE id=g_id ;
		END IF;
		UNTIL done END REPEAT;
	CLOSE mycursor ;
  END IF;
  UPDATE t_pcs_train SET STATUS=44 WHERE id=l_trainId ;
  END$$

DELIMITER ;