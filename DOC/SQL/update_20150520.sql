ALTER TABLE `sop`.`t_pcs_weighbridge`   
  ADD COLUMN `type` INT NULL  COMMENT '1称重2计量' AFTER `tankId`;
DROP TABLE IF EXISTS `weight`;

CREATE TABLE `weight` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `Status` int(11) DEFAULT NULL COMMENT '发油状态',
  `Bakn2` int(11) DEFAULT NULL COMMENT '返回的鹤位',
  `Notify` varchar(11) DEFAULT NULL COMMENT '通知单号',
  `LSID` varchar(12) DEFAULT NULL COMMENT '提交的流水表的流水号',
  `THZT` varchar(2) DEFAULT NULL COMMENT '提货状态',
  `HZ` varchar(6) DEFAULT NULL COMMENT '货主编号',
  `HPZ` varchar(2) DEFAULT NULL COMMENT '品种编号',
  `Gname` varchar(20) DEFAULT NULL COMMENT '品种名称',
  `HYS` varchar(6) DEFAULT NULL COMMENT '原始货主编号',
  `TankNo` varchar(12) DEFAULT NULL COMMENT '罐号',
  `HPH` varchar(16) DEFAULT NULL COMMENT '货批号',
  `XID` varchar(16) DEFAULT NULL COMMENT '原始号',
  `DID` varchar(16) DEFAULT NULL COMMENT '调拨号',
  `BBH` varchar(6) DEFAULT NULL COMMENT '客户编号，伙伴编号',
  `BHPH` varchar(16) DEFAULT NULL,
  `YGZ` varchar(2) DEFAULT NULL COMMENT '工具种类（车船另等）',
  `YGID` varchar(32) DEFAULT NULL COMMENT '工具识别',
  `YDB` float DEFAULT NULL COMMENT '调拨量（开票数量）',
  `M_NUM` float DEFAULT NULL COMMENT '计划发送量',
  `YPZ` float DEFAULT NULL COMMENT '皮重',
  `YMZ` float DEFAULT NULL COMMENT '毛重',
  `YYL` float DEFAULT NULL COMMENT '实际运量',
  `TTDS` varchar(64) DEFAULT NULL COMMENT '提货通知单串号',
  `JBH` varchar(6) DEFAULT NULL COMMENT '开票人编号（具体部门信息）',
  `JSR` varchar(10) DEFAULT NULL COMMENT '开票人',
  `JQM` varchar(16) DEFAULT NULL COMMENT '开票机器名称',
  `JIP` varchar(16) DEFAULT NULL COMMENT '开票机器地址',
  `FHH` varchar(6) DEFAULT NULL COMMENT '发货编号',
  `FHR` varchar(10) DEFAULT NULL COMMENT '发货人',
  `FHJM` varchar(16) DEFAULT NULL COMMENT '发货机器名称',
  `FHIP` varchar(16) DEFAULT NULL COMMENT '发货机器地址',
  `FINTIME` datetime DEFAULT NULL COMMENT '进库时间',
  `FOUTTIME` datetime DEFAULT NULL COMMENT '出库时间',
  `FHGH` varchar(4) DEFAULT NULL COMMENT '发货罐号，如果上面的罐号不空可对照',
  `SMD` float DEFAULT NULL COMMENT '返回的视密度（流量表）',
  `BMD` float DEFAULT NULL COMMENT '返回的标密度（流量表）',
  `VCF20` float DEFAULT NULL COMMENT '返回的视密度（流量表）',
  `VT` float DEFAULT NULL COMMENT '返回的表观体积（流量表）',
  `V20` float DEFAULT NULL COMMENT '返回的标准体积（流量表）',
  `Bakn1` float DEFAULT NULL COMMENT '返回的重量值（流量表写）',
  `BakW` float DEFAULT NULL COMMENT '曾最大载重量',
  `flag` varchar(1) DEFAULT NULL COMMENT '装车标志',
  `PLB` varchar(2) DEFAULT NULL COMMENT '类别',
  `PID` varchar(32) DEFAULT NULL COMMENT '编号',
  `TLB` varchar(2) DEFAULT NULL COMMENT '类别',
  `TID` varchar(16) DEFAULT NULL,
  `DTIME` datetime DEFAULT NULL COMMENT '取货时间',
  `LSTIME` datetime DEFAULT NULL COMMENT '最后修改时间',
  `SM` varchar(32) DEFAULT NULL COMMENT '交易说明，进流水',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP PROCEDURE IF EXISTS `updateVehicleDeliverInfoForWeigh`;
DELIMITER $$
CREATE PROCEDURE `updateVehicleDeliverInfoForWeigh`(IN l_serialNo VARCHAR(30),IN g_dNum DECIMAL(12,3))
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
	  IF g_dNum=0 THEN -- 计量数据
	  SELECT wb.deliveryNum,i.batchId INTO g_totalNum,g_batchId FROM t_pcs_weighbridge wb,t_pcs_goodslog i WHERE i.serial=wb.serial AND i.serial=l_serialNo;
	  ELSE -- 称重数据
	  SELECT g_dNum,i.batchId INTO g_totalNum,g_batchId FROM t_pcs_weighbridge wb,t_pcs_goodslog i WHERE i.serial=wb.serial AND i.serial=l_serialNo;
	  END IF;
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



DROP TRIGGER /*!50032 IF EXISTS */ `insert_pcs_goodslog`;
DELIMITER $$

CREATE
    TRIGGER `insert_pcs_goodslog` BEFORE INSERT ON `t_pcs_goodslog` 
    FOR EACH ROW BEGIN
    DECLARE pCode VARCHAR(20);
    DECLARE tCode VARCHAR(20) ;
    SELECT 
      p.code INTO pCode
    FROM
      t_pcs_product p 
    WHERE p.id = 
      (SELECT 
        productId 
      FROM
        t_pcs_goods 
      WHERE id = new.goodsId);
      SELECT t.code INTO tCode FROM t_pcs_tank t WHERE t.id=new.tankId;
    INSERT INTO weight(Notify,THZT,HPZ,YDB,TankNo,flag) VALUES(new.serial,'KD',pCode,new.deliverNum,tCode,'*');
END;
$$

DELIMITER ;



DROP TRIGGER /*!50032 IF EXISTS */ `update_weight`;
DELIMITER $$
CREATE
    TRIGGER `update_weight` AFTER UPDATE ON `weight` 
    FOR EACH ROW BEGIN
	DECLARE tId INT ;
	SELECT id INTO tId FROM t_pcs_tank WHERE `code`=old.TankNo ;
	IF new.Bakn1 IS NOT NULL AND new.Bakn1>0 THEN 
		INSERT INTO t_pcs_weighbridge(deliveryNum,measureWeigh,`serial`,inPort,`status`,tankId,createUserId,`type`) VALUES(new.Bakn1,new.Bakn1,new.Notify,new.Bakn2,3,tId,0,2) ;
	END IF ;
    END;
$$

DELIMITER ;
