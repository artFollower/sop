ALTER TABLE `sop`.`t_pcs_weighbridge`   
  ADD COLUMN `type` INT NULL  COMMENT '1����2����' AFTER `tankId`;
DROP TABLE IF EXISTS `weight`;

CREATE TABLE `weight` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `Status` int(11) DEFAULT NULL COMMENT '����״̬',
  `Bakn2` int(11) DEFAULT NULL COMMENT '���صĺ�λ',
  `Notify` varchar(11) DEFAULT NULL COMMENT '֪ͨ����',
  `LSID` varchar(12) DEFAULT NULL COMMENT '�ύ����ˮ�����ˮ��',
  `THZT` varchar(2) DEFAULT NULL COMMENT '���״̬',
  `HZ` varchar(6) DEFAULT NULL COMMENT '�������',
  `HPZ` varchar(2) DEFAULT NULL COMMENT 'Ʒ�ֱ��',
  `Gname` varchar(20) DEFAULT NULL COMMENT 'Ʒ������',
  `HYS` varchar(6) DEFAULT NULL COMMENT 'ԭʼ�������',
  `TankNo` varchar(12) DEFAULT NULL COMMENT '�޺�',
  `HPH` varchar(16) DEFAULT NULL COMMENT '������',
  `XID` varchar(16) DEFAULT NULL COMMENT 'ԭʼ��',
  `DID` varchar(16) DEFAULT NULL COMMENT '������',
  `BBH` varchar(6) DEFAULT NULL COMMENT '�ͻ���ţ������',
  `BHPH` varchar(16) DEFAULT NULL,
  `YGZ` varchar(2) DEFAULT NULL COMMENT '�������ࣨ������ȣ�',
  `YGID` varchar(32) DEFAULT NULL COMMENT '����ʶ��',
  `YDB` float DEFAULT NULL COMMENT '����������Ʊ������',
  `M_NUM` float DEFAULT NULL COMMENT '�ƻ�������',
  `YPZ` float DEFAULT NULL COMMENT 'Ƥ��',
  `YMZ` float DEFAULT NULL COMMENT 'ë��',
  `YYL` float DEFAULT NULL COMMENT 'ʵ������',
  `TTDS` varchar(64) DEFAULT NULL COMMENT '���֪ͨ������',
  `JBH` varchar(6) DEFAULT NULL COMMENT '��Ʊ�˱�ţ����岿����Ϣ��',
  `JSR` varchar(10) DEFAULT NULL COMMENT '��Ʊ��',
  `JQM` varchar(16) DEFAULT NULL COMMENT '��Ʊ��������',
  `JIP` varchar(16) DEFAULT NULL COMMENT '��Ʊ������ַ',
  `FHH` varchar(6) DEFAULT NULL COMMENT '�������',
  `FHR` varchar(10) DEFAULT NULL COMMENT '������',
  `FHJM` varchar(16) DEFAULT NULL COMMENT '������������',
  `FHIP` varchar(16) DEFAULT NULL COMMENT '����������ַ',
  `FINTIME` datetime DEFAULT NULL COMMENT '����ʱ��',
  `FOUTTIME` datetime DEFAULT NULL COMMENT '����ʱ��',
  `FHGH` varchar(4) DEFAULT NULL COMMENT '�����޺ţ��������Ĺ޺Ų��տɶ���',
  `SMD` float DEFAULT NULL COMMENT '���ص����ܶȣ�������',
  `BMD` float DEFAULT NULL COMMENT '���صı��ܶȣ�������',
  `VCF20` float DEFAULT NULL COMMENT '���ص����ܶȣ�������',
  `VT` float DEFAULT NULL COMMENT '���صı�������������',
  `V20` float DEFAULT NULL COMMENT '���صı�׼�����������',
  `Bakn1` float DEFAULT NULL COMMENT '���ص�����ֵ��������д��',
  `BakW` float DEFAULT NULL COMMENT '�����������',
  `flag` varchar(1) DEFAULT NULL COMMENT 'װ����־',
  `PLB` varchar(2) DEFAULT NULL COMMENT '���',
  `PID` varchar(32) DEFAULT NULL COMMENT '���',
  `TLB` varchar(2) DEFAULT NULL COMMENT '���',
  `TID` varchar(16) DEFAULT NULL,
  `DTIME` datetime DEFAULT NULL COMMENT 'ȡ��ʱ��',
  `LSTIME` datetime DEFAULT NULL COMMENT '����޸�ʱ��',
  `SM` varchar(32) DEFAULT NULL COMMENT '����˵��������ˮ',
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
	  IF g_dNum=0 THEN -- ��������
	  SELECT wb.deliveryNum,i.batchId INTO g_totalNum,g_batchId FROM t_pcs_weighbridge wb,t_pcs_goodslog i WHERE i.serial=wb.serial AND i.serial=l_serialNo;
	  ELSE -- ��������
	  SELECT g_dNum,i.batchId INTO g_totalNum,g_batchId FROM t_pcs_weighbridge wb,t_pcs_goodslog i WHERE i.serial=wb.serial AND i.serial=l_serialNo;
	  END IF;
	UPDATE t_pcs_train SET STATUS=43,flag=1 WHERE id=(SELECT batchId FROM t_pcs_goodslog WHERE deliverType=1 AND SERIAL=l_serialNo) ;
	OPEN mycursor ;
	REPEAT   --  �ַ�ʵ������
		FETCH mycursor INTO g_id,g_deliverNum,g_goodsId ;
		IF done=0 THEN
			IF g_totalNum-g_deliverNum>=0 THEN
				UPDATE t_pcs_goodslog SET actualNum=g_deliverNum WHERE id = g_id ;
				SET g_totalNum = g_totalNum-g_deliverNum ;
				UPDATE   t_pcs_lading l SET l.goodsDelivery = goodsDelivery + g_deliverNum WHERE l.id = (SELECT ladingId FROM t_pcs_goods_group 
					  WHERE id = (SELECT goodsGroupId FROM t_pcs_goods WHERE id =g_id));
			ELSE 
				UPDATE t_pcs_goods SET goodsCurrent=goodsCurrent+g_deliverNum-g_totalNum  WHERE id=g_goodsId;-- ����
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
