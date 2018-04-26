DROP PROCEDURE IF EXISTS `getStorageCostForCargo`;
DELIMITER $$

CREATE PROCEDURE `getStorageCostForCargo`( IN l_sTime VARCHAR(20),IN l_eTime VARCHAR(20),IN l_cargoId INT)
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
-- �Ƿ��л���仯
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
      
      
   -- ��ѯ����� 
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
        SUM((CASE WHEN i.type=5 THEN IFNULL(ABS(i.goodsChange),0) ELSE 0 END))deliverNum,-- ����
        SUM((CASE WHEN i.type=3 THEN IFNULL(ABS(i.goodsChange),0) ELSE 0 END))exportNum, -- ����
        SUM((CASE WHEN i.type=2 THEN IFNULL(ABS(i.goodsChange),0) ELSE 0 END))importNum, -- ����
        SUM((CASE WHEN i.type=6 THEN IFNULL(ABS(i.goodsChange),0) ELSE 0 END))backNum, -- �˻�
        SUM((CASE WHEN i.type=9 THEN IFNULL(ABS(i.goodsChange),0) ELSE 0 END))holdNum  -- ����
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
    END$$

DELIMITER ;




DROP PROCEDURE IF EXISTS `getStorageCostForLading`;
DELIMITER $$
CREATE PROCEDURE `getStorageCostForLading`( IN l_sTime VARCHAR(20),IN l_eTime VARCHAR(20),IN l_ladingId INT)
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
-- ���Ƿ��л���仯���
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
  --  ������
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
        SUM((CASE WHEN i.type=5 THEN IFNULL(ABS(i.goodsChange),0) ELSE 0 END))deliverNum,-- ����
        SUM((CASE WHEN i.type=3 THEN IFNULL(ABS(i.goodsChange),0) ELSE 0 END))exportNum, -- ����
        SUM((CASE WHEN i.type=2 THEN IFNULL(ABS(i.goodsChange),0) ELSE 0 END))importNum, -- ����
        SUM((CASE WHEN i.type=6 THEN IFNULL(ABS(i.goodsChange),0) ELSE 0 END))backNum, -- �˻�
        SUM((CASE WHEN i.type=9 THEN IFNULL(ABS(i.goodsChange),0) ELSE 0 END))holdNum  -- ����
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
    END$$

DELIMITER ;

ALTER TABLE t_pcs_goodslog ADD INDEX goods_id_index(goodsId);