package com.skycloud.oa.outbound.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.outbound.dao.LadingDao;
import com.skycloud.oa.outbound.dto.GoodsDto;
import com.skycloud.oa.outbound.dto.LadingDto;
import com.skycloud.oa.outbound.model.Lading;
import com.skycloud.oa.utils.Common;
@Component
public class LadingDaoImpl extends BaseDaoImpl implements LadingDao {
	private static Logger LOG = Logger.getLogger(LadingDaoImpl.class);
	@Override
	public int addLading(Lading lading)throws OAException {
		try {
			Serializable s= save(lading);
			return Integer.parseInt(s.toString());
		} catch (OAException e) {
			e.printStackTrace();
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}

	@Override
	public void deleteLading(String LadingId)throws OAException {
		String sql="UPDATE t_pcs_lading set status=3 where id in("+LadingId+")";
		try {
			execute(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}

	@Override
	public void updateLading(Lading lading)throws OAException {
		String sql="update t_pcs_lading set id=id";
		if(!Common.empty(lading.getCode())){
			sql+=" ,code='"+lading.getCode()+"'";
		}
		if(!Common.isNull(lading.getClientId())){
			sql+=" ,clientId="+lading.getClientId();
		}
		if(!Common.isNull(lading.getProductId())){
			sql+=" ,productId="+lading.getProductId();
		}
		if(!Common.isNull(lading.getType())){
			sql+=" ,type="+lading.getType();
		}
		if(!Common.isNull(lading.getReceiveClientId())){
			sql+=" ,receiveClientId="+lading.getReceiveClientId();
		}
		if(!Common.empty(lading.getGoodsTotal())){
			sql+=" ,goodsTotal='"+lading.getGoodsTotal()+"'";
		}
		if(!Common.empty(lading.getGoodsPass())){
			sql+=" ,goodsPass='"+lading.getGoodsPass()+"'";
		}
		if(!Common.empty(lading.getGoodsDelivery())){
			sql+=" ,goodsDelivery='"+lading.getGoodsDelivery()+"'";
		}
		if(!Common.empty(lading.getStartTime())){
			sql+=" ,startTime='"+lading.getStartTime()+"'";
		}
		if(!Common.empty(lading.getEndTime())){
			sql+=" ,endTime='"+lading.getEndTime()+"'";
		}
		if(!Common.isNull(lading.getStatus())){
			sql+=" ,status='"+lading.getStatus()+"'";
		}
		if(!Common.empty(lading.getDescription())){
			sql+=" ,description='"+lading.getDescription()+"'";
		}
		if(!Common.isNull(lading.getCreateUserId())){
			sql+=" ,createUserId='"+lading.getCreateUserId()+"'";
		}
		if(!Common.empty(lading.getCreateTime())){
			sql+=" ,createTime='"+lading.getCreateTime()+"'";
		}
		if(!Common.isNull(lading.getReviewUserId())){
			sql+=" ,reviewUserId='"+lading.getReviewUserId()+"'";
		}
		if(!Common.empty(lading.getReviewTime())){
			sql+=" ,reviewTime='"+lading.getReviewTime()+"'";
		}
		if(!Common.empty(lading.getGoodsOut())){
			sql+=" ,goodsOut='"+lading.getGoodsOut()+"'";
		}
		
		if(!Common.empty(lading.getCustomsPassCode())){
			sql+=" ,customsPassCode='"+lading.getCustomsPassCode()+"'";
		}
		if(!Common.empty(lading.getFileUrl())){
			sql+=" ,fileUrl='"+lading.getFileUrl()+"'";
		}
		if(lading.getIsFinish()!=-1){
			sql+=" ,isFinish="+lading.getIsFinish();
		}
		
		if(lading.getIsLong()!=null){
			
			if(lading.getIsLong()==1){
				
				sql+=" ,isLong="+lading.getIsLong()+",endTime=null";
			}else{
				sql+=" ,isLong="+lading.getIsLong();
			}
			
		}
		
		sql+=" where id="+lading.getId();
		
		try {
			execute(sql);
//			update(lading);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
		
	}

	@Override
	public List<Map<String, Object>> getLadingList(LadingDto ladingDto,int start,int limit)throws OAException {
		String sql="select DATE_FORMAT(tpl.endTime,'%Y-%m-%d %H:%i:%s') mEndTime,tt.goodsWait,tt1.goodsLoss,tpl.*,tpc.name clientName,tpc.code clientCode,tpp.name productName,tsu.name createUserName, " +
				"tsu2.name reviewUserName,tpgg.code goodsGroupCode,tpgg.id goodsGroupId,tpc2.name receiveClientName " +
				" from t_pcs_lading tpl INNER  JOIN t_pcs_client tpc on tpc.id=tpl.clientId " +
				" INNER JOIN t_pcs_product tpp on tpl.productId=tpp.id " +
				" INNER JOIN t_auth_user tsu on tsu.id=tpl.createUserId " +
				" LEFT JOIN t_auth_user tsu2 on tsu2.id=tpl.reviewUserId " +
				" LEFT JOIN t_pcs_goods_group tpgg on tpgg.ladingId=tpl.id " +
				" INNER JOIN t_pcs_client tpc2 on tpc2.id=tpl.receiveClientId LEFT JOIN (SELECT c.ladingId, round(SUM(a.deliverNum), 3) goodsWait "
					+" from t_pcs_goodslog a ,t_pcs_goods b ,t_pcs_goods_group c  "
					+" where a.actualNum = 0 AND a.deliverNum != 0 AND a.type = 5 AND a.actualType = 0 "
					+" AND a.goodsId = b.id and c.id=b.goodsGroupId group by c.ladingId) tt on tt.ladingId=tpl.id " +
					" LEFT JOIN  (SELECT c.ladingId, -round(SUM(a.goodsChange), 3) goodsLoss "
					+" from t_pcs_goodslog a ,t_pcs_goods b ,t_pcs_goods_group c  "
					+" where a.type=10 and a.deliverNo=1 "
					+" AND a.goodsId = b.id and c.id=b.goodsGroupId group by c.ladingId) tt1 on tt1.ladingId=tpl.id where 1=1";
		if(!Common.isNull(ladingDto.getId())){
			sql+=" and tpl.id="+ladingDto.getId();
		}
		if(!Common.isNull(ladingDto.getClientId())){
			sql+=" and tpl.clientId="+ladingDto.getClientId();
		}
		if(!Common.isNull(ladingDto.getBuyClientId())){
			sql+=" and tpl.receiveClientId="+ladingDto.getBuyClientId();
		}
		
		if(!Common.empty(ladingDto.getCode())){
			sql+=" and tpl.code like '%"+ladingDto.getCode()+"%'";
		}
		if(!Common.isNull(ladingDto.getProductId())){
			sql+=" and tpl.productId = "+ladingDto.getProductId();
		}
		if(!Common.empty(ladingDto.getStatus())){
			
			//锁定
			if("1".equals(ladingDto.getStatus())){
				sql+=" and tpl.goodsPass=0 and tpl.goodsTotal<>0 ";
				}
			//激活(部分)
			else if("2".equals(ladingDto.getStatus())){
				sql+=" and round(tpl.goodsTotal-tpl.goodsPass,3)>0 and tpl.goodsPass<>0";
			}
			//激活
			else if("3".equals(ladingDto.getStatus())){
				sql+=" and round(tpl.goodsTotal-tpl.goodsPass,3)=0 ";
			}else if("4".equals(ladingDto.getStatus())){
				sql+=" and tpl.type=2 and tpl.endTime<=now() and round((tpl.goodsTotal-tpl.goodsOut-tpl.goodsDelivery-COALESCE(tt1.goodsLoss,0)),3)>0 ";
			}
			
			
//			//过期
//			if("3".equals(ladingDto.getStatus())){
//				sql+=" and tpl.type=2 and tpl.endTime<=now() and round((tpl.goodsTotal-tpl.goodsOut-tpl.goodsDelivery-COALESCE(tt1.goodsLoss,0)),3)>0 ";
//			}
//			//终止
//			else if("2".equals(ladingDto.getStatus())){
//				
//				sql+=" and round((tpl.goodsTotal-tpl.goodsOut-tpl.goodsDelivery-COALESCE(tt1.goodsLoss,0)),3)=0 and tpl.status=1 ";
//			}
//			//中止
//			else if("4".equals(ladingDto.getStatus())){
//				sql+=" and tpl.status=2 ";
//			}
//			//激活
//			else if("1".equals(ladingDto.getStatus())){
//				sql+=" and ((tpl.type=2 and (tpl.endTime>=now() or isnull(tpl.endTime))) or tpl.type=1) and tpl.status=1 and round((tpl.goodsTotal-tpl.goodsOut-tpl.goodsDelivery-COALESCE(tt1.goodsLoss,0)),3)>0 ";
//				}
			
		}
		if(!Common.isNull(ladingDto.getType())){
			sql+=" and tpl.type="+ladingDto.getType();
		}
		if(!Common.empty(ladingDto.getStartTime())){
			sql+=" and tpl.startTime>='"+ladingDto.getStartTime()+"'";
		}
		if(!Common.empty(ladingDto.getEndTime())){
			sql+=" and tpl.endTime<='"+ladingDto.getEndTime()+"'";
		}
		if(!Common.empty(ladingDto.getClientName())){
			sql+=" and tpc.name like '%"+ladingDto.getClientName()+"%'";
		}
		if(!Common.isNull(limit)){
			sql+=" order by tpl.createTime DESC limit "+start+","+limit;
		}else{
			sql+=" order by tpl.createTime DESC";
		}
		try {
			return executeQuery(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
		
	}
	
	
	@Override
	public List<Map<String, Object>> getLadingByGoodsId(LadingDto ladingDto)throws OAException {
		String sql="select tpl.*,tpc.name clientName,tpp.name productName,tsu.name createUserName, " +
				"tsu2.name reviewUserName,tpgg.code goodsGroupCode,tpgg.id goodsGroupId,tpc2.name receiveClientName " +
				" from t_pcs_lading tpl INNER JOIN t_pcs_client tpc on tpc.id=tpl.clientId " +
				" INNER JOIN t_pcs_product tpp on tpl.productId=tpp.id " +
				" INNER JOIN t_auth_user tsu on tsu.id=tpl.createUserId " +
				" LEFT JOIN t_auth_user tsu2 on tsu2.id=tpl.reviewUserId " +
				" LEFT JOIN t_pcs_goods_group tpgg on tpgg.ladingId=tpl.id " +
				" INNER JOIN t_pcs_client tpc2 on tpc2.id=tpl.receiveClientId " +
				" LEFT JOIN t_pcs_goods tpg on tpg.goodsGroupId=tpgg.id where 1=1";
		if(!Common.isNull(ladingDto.getGoodsId())){
			sql+=" and tpg.id="+ladingDto.getGoodsId();
		}
		
		try {
			return executeQuery(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
		
	}

	@Override
	public List<Map<String, Object>> getGoodsListByLadingId(int ladingId)throws OAException {
		String sql="select (SELECT   round(SUM(t.deliverNum),3) waitAmount FROM  t_pcs_goodslog t WHERE t.actualNum = 0   AND t.deliverNum != 0 and t.type=5 and t.actualType=0  AND t.goodsId = tpg.id  ) goodsWait, d.arrivalStartTime,e.name shipName,a.code cargoCode,b.code sourceCode,c.code rootCode,g.code rLadingCode,i.code sLadingCode ,tpg.*,tpc.name clientName,tpp.name productName,tpt.code tankCode,tpc1.code contractCode,tpc1.title contractTitle from t_pcs_goods tpg " +
				" INNER JOIN t_pcs_client tpc on tpc.id=tpg.clientId " +
				" INNER JOIN t_pcs_product tpp on tpp.id=tpg.productId " +
				" LEFT JOIN t_pcs_tank tpt on tpt.id=tpg.tankId " +
				" LEFT JOIN t_pcs_contract tpc1 on tpc1.id=tpg.contractId " +
				" INNER JOIN t_pcs_cargo a on a.id=tpg.cargoId " +
				" LEFT JOIN t_pcs_goods b on b.id=tpg.sourceGoodsId " +
				" LEFT JOIN t_pcs_goods c on c.id=tpg.rootGoodsId " +
				" LEFT JOIN t_pcs_arrival d on d.id=a.arrivalId"+
				" LEFT JOIN t_pcs_ship e on e.id=d.shipId"+
				" LEFT JOIN t_pcs_goods_group f on c.goodsGroupId=f.id LEFT JOIN t_pcs_lading g on f.ladingId=g.id"+
				" LEFT JOIN t_pcs_goods_group h on b.goodsGroupId=h.id LEFT JOIN t_pcs_lading i on h.ladingId=i.id"+
				
				" where tpg.goodsGroupId in (select tpgg.id from t_pcs_goods_group tpgg where ladingId="+ladingId+")";
		try {
			return executeQuery(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}

	@Override
	public int getLadingListCount(LadingDto ladingDto)throws OAException {
		String sql="select count(*) " +
				" from t_pcs_lading tpl INNER JOIN t_pcs_client tpc on tpc.id=tpl.clientId " +
				" INNER JOIN t_pcs_product tpp on tpl.productId=tpp.id " +
				" INNER JOIN t_auth_user tsu on tsu.id=tpl.createUserId " +
				" LEFT JOIN t_auth_user tsu2 on tsu2.id=tpl.reviewUserId " +
				" LEFT JOIN t_pcs_goods_group tpgg on tpgg.ladingId=tpl.id " +
				" INNER JOIN t_pcs_client tpc2 on tpc2.id=tpl.receiveClientId LEFT JOIN (SELECT c.ladingId, round(SUM(a.deliverNum), 3) goodsWait "
					+" from t_pcs_goodslog a ,t_pcs_goods b ,t_pcs_goods_group c  "
					+" where a.actualNum = 0 AND a.deliverNum != 0 AND a.type = 5 AND a.actualType = 0 "
					+" AND a.goodsId = b.id and c.id=b.goodsGroupId group by c.ladingId) tt on tt.ladingId=tpl.id " +
					" LEFT JOIN  (SELECT c.ladingId, -round(SUM(a.goodsChange), 3) goodsLoss "
					+" from t_pcs_goodslog a ,t_pcs_goods b ,t_pcs_goods_group c  "
					+" where a.type=10 and a.deliverNo=1 "
					+" AND a.goodsId = b.id and c.id=b.goodsGroupId group by c.ladingId) tt1 on tt1.ladingId=tpl.id where 1=1";
		if(!Common.isNull(ladingDto.getId())){
			sql+=" and tpl.id="+ladingDto.getId();
		}
		if(!Common.isNull(ladingDto.getClientId())){
			sql+=" and tpl.clientId="+ladingDto.getClientId();
		}
		if(!Common.empty(ladingDto.getCode())){
			sql+=" and tpl.code like '%"+ladingDto.getCode()+"%'";
		}
		if(!Common.isNull(ladingDto.getBuyClientId())){
			sql+=" and tpl.receiveClientId="+ladingDto.getBuyClientId();
		}
		if(!Common.isNull(ladingDto.getProductId())){
			sql+=" and tpl.productId = "+ladingDto.getProductId();
		}
		if(!Common.empty(ladingDto.getStatus())){
			
			//锁定
			if("1".equals(ladingDto.getStatus())){
				sql+=" and tpl.goodsPass=0 and tpl.goodsTotal<>0 ";
				}
			//激活(部分)
			else if("2".equals(ladingDto.getStatus())){
				sql+=" and round(tpl.goodsTotal-tpl.goodsPass,3)>0 and tpl.goodsPass<>0";
			}
			//激活
			else if("3".equals(ladingDto.getStatus())){
				sql+=" and round(tpl.goodsTotal-tpl.goodsPass,3)=0 ";
			}else if("4".equals(ladingDto.getStatus())){
				sql+=" and tpl.type=2 and tpl.endTime<=now() and round((tpl.goodsTotal-tpl.goodsOut-tpl.goodsDelivery-COALESCE(tt1.goodsLoss,0)),3)>0 ";
			}
			
			
//			//过期
//			if("3".equals(ladingDto.getStatus())){
//				sql+=" and tpl.type=2 and tpl.endTime<=now() and round((tpl.goodsTotal-tpl.goodsOut-tpl.goodsDelivery-COALESCE(tt1.goodsLoss,0)),3)>0 ";
//			}
//			//终止
//			else if("2".equals(ladingDto.getStatus())){
//				
//				sql+=" and round((tpl.goodsTotal-tpl.goodsOut-tpl.goodsDelivery-COALESCE(tt1.goodsLoss,0)),3)=0 and tpl.status=1 ";
//			}
//			//中止
//			else if("4".equals(ladingDto.getStatus())){
//				sql+=" and tpl.status=2 ";
//			}
//			//激活
//			else if("1".equals(ladingDto.getStatus())){
//				sql+=" and ((tpl.type=2 and (tpl.endTime>=now() or isnull(tpl.endTime))) or tpl.type=1) and tpl.status=1 and round((tpl.goodsTotal-tpl.goodsOut-tpl.goodsDelivery-COALESCE(tt1.goodsLoss,0)),3)>0 ";
//				}
			
		}
		if(!Common.isNull(ladingDto.getType())){
			sql+=" and tpl.type="+ladingDto.getType();
		}
		if(!Common.empty(ladingDto.getStartTime())){
			sql+=" and tpl.startTime>='"+ladingDto.getStartTime()+"'";
		}
		if(!Common.empty(ladingDto.getEndTime())){
			sql+=" and tpl.endTime<='"+ladingDto.getEndTime()+"'";
		}
		if(!Common.empty(ladingDto.getClientName())){
			sql+=" and tpc.name like '%"+ladingDto.getClientName()+"%'";
		}
		return (int) getCount(sql);
	}

	@Override
	public void updateLadingStatus(String ladingId,int status)throws OAException {
		String sql="update t_pcs_lading set status="+status+" where id="+ladingId;
		try {
			execute(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
			
		}
	}

	@Override
	public List<Map<String,Object>> getTreeGoodsList(String rIds,GoodsDto goodsDto)throws OAException {
		try {
			
//			String sql="select (CASE WHEN d.sourceGoodId = NULL THEN (CASE WHEN d.goodsInspect = 0 THEN d.goodsTank ELSE d.goodsInspect END) ELSE d.goodsTotal END) AS goodsInspect,d.*,d.sourceGoodsId parentId,e.name productName,(CASE WHEN d.ladingClientId IS NOT NULL THEN j.name ELSE f.name END) NAME,i.id ladingId,  i.code ladingCode,(case when ISNULL(i.startTime) then i.endTime else i.startTime end) ladingTime,(SELECT CASE WHEN l.type='1' THEN '转卖' ELSE '发货' END FROM t_pcs_lading l WHERE l.id=h.ladingId) ladingType from t_pcs_goods d LEFT JOIN t_pcs_product e ON e.id = d.productId LEFT JOIN t_pcs_client f ON f.id = d.clientId LEFT JOIN t_pcs_goods_group h on h.id=d.goodsGroupId LEFT JOIN t_pcs_lading i on i.id=h.ladingId LEFT JOIN t_pcs_client j on j.id=d.ladingClientId  where d.status<>1 and d.status<>2 and  d.id="+goodsDto.getId();
			String sql="";
			if(!Common.empty(rIds)){
				sql="select (CASE WHEN d.sourceGoodId = NULL THEN (CASE WHEN d.goodsInspect = 0 THEN d.goodsTank ELSE d.goodsInspect END) ELSE d.goodsTotal END) AS goodsInspect,d.*,d.sourceGoodsId parentId ,(select round(SUM(t.deliverNum),3) waitAmount FROM  t_pcs_goodslog t WHERE t.actualNum = 0   AND t.deliverNum != 0 and t.type=5 and t.actualType=0  AND t.goodsId = d.id ) goodsWait   ,e.name productName,(CASE WHEN d.ladingClientId IS NOT NULL THEN j.name ELSE f.name END) NAME,i.id ladingId,  i.code ladingCode,(case when ISNULL(i.startTime) then i.endTime else i.startTime end) ladingTime,(SELECT CASE WHEN l.type='1' THEN '转卖' ELSE '发货' END FROM t_pcs_lading l WHERE l.id=h.ladingId) ladingType from t_pcs_goods d LEFT JOIN t_pcs_product e ON e.id = d.productId LEFT JOIN t_pcs_client f ON f.id = d.clientId LEFT JOIN t_pcs_goods_group h on h.id=d.goodsGroupId LEFT JOIN t_pcs_lading i on i.id=h.ladingId LEFT JOIN t_pcs_client j on j.id=d.ladingClientId  where d.status<>1 and d.status<>2  AND ( (d.rootGoodsId in ("+rIds+")) or (d.id in ("+rIds+")) or (d.id=(select DISTINCT sourceGoodsId from t_pcs_goods where id in ("+rIds+"))))";
			}
			else{
				sql="select (CASE WHEN d.sourceGoodId = NULL THEN (CASE WHEN d.goodsInspect = 0 THEN d.goodsTank ELSE d.goodsInspect END) ELSE d.goodsTotal END) AS goodsInspect,d.*,d.sourceGoodsId parentId ,(select round(SUM(t.deliverNum),3) waitAmount FROM  t_pcs_goodslog t WHERE t.actualNum = 0   AND t.deliverNum != 0  and t.type=5 and t.actualType=0 AND t.goodsId = d.id ) goodsWait   ,e.name productName,(CASE WHEN d.ladingClientId IS NOT NULL THEN j.name ELSE f.name END) NAME,i.id ladingId,  i.code ladingCode,(case when ISNULL(i.startTime) then i.endTime else i.startTime end) ladingTime,(SELECT CASE WHEN l.type='1' THEN '转卖' ELSE '发货' END FROM t_pcs_lading l WHERE l.id=h.ladingId) ladingType from t_pcs_goods d LEFT JOIN t_pcs_product e ON e.id = d.productId LEFT JOIN t_pcs_client f ON f.id = d.clientId LEFT JOIN t_pcs_goods_group h on h.id=d.goodsGroupId LEFT JOIN t_pcs_lading i on i.id=h.ladingId LEFT JOIN t_pcs_client j on j.id=d.ladingClientId  where d.status<>1 and d.status<>2  AND d.id="+goodsDto.getId();
				
				
			}
			
			return executeQuery(sql);
		} catch (OAException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}

	@Override
	public List<Map<String, Object>> getGoodsInvoice(String goodsId)
			throws OAException {
		String sql="select a.*,d.code ladingCode,g.name clientName,CASE WHEN b.goodsInPass IS NULL THEN b.goodsOutPass WHEN b.goodsOutPass IS NULL THEN b.goodsInPass WHEN CAST(b.goodsInPass AS UNSIGNED) < CAST(b.goodsOutPass AS UNSIGNED) THEN b.goodsInPass ELSE b.goodsOutPass END pass," +
				"b.goodsTotal,CASE a.deliverType WHEN '1' then e.code  WHEN '2' then f.code END carshipCode" +
				" from t_pcs_invoice a LEFT JOIN t_pcs_goods b on b.id=a.goodsId " +
				"LEFT JOIN t_pcs_goods_group c on c.id=b.goodsGroupId " +
				"LEFT JOIN t_pcs_lading d on d.id=c.ladingId " +
				"LEFT JOIN t_pcs_truck e on e.id=a.batchId "+ 
				"LEFT JOIN t_pcs_ship f on f.id=a.batchId " +
				"LEFT JOIN t_pcs_client g on g.id=b.clientId where a.goodsId="+goodsId+" order by a.createTime ASC";
		try {
			return executeQuery(sql);
		} catch (OAException e) {
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
		
	}

	@Override
	public List<Map<String, Object>> getTreeCargoList(String cargoId)throws OAException {
		String sql="select UNIX_TIMESTAMP(l.arrivalStartTime) tArrivalStartTime,m.period, " +
				" (CASE WHEN d.sourceGoodsId = NULL THEN (CASE WHEN d.goodsInspect = 0 THEN d.goodsTank ELSE d.goodsInspect END) ELSE d.goodsTotal END) AS goodsInspect,d.*,d.sourceGoodsId parentId , " +
				" (select round(SUM(t.deliverNum),3) waitAmount FROM  t_pcs_goodslog t WHERE t.actualNum = 0   AND t.deliverNum != 0  and t.type=5 and t.actualType=0 AND t.goodsId = d.id ) goodsWait   , " +
				"(select ABS(round(SUM(t1.goodsChange),3)) goodsLoss from t_pcs_goodslog t1 where t1.deliverNo=1 and t1.type=10 and t1.goodsId=d.id) goodsLoss," +
				" e.name productName,(CASE WHEN d.ladingClientId IS NOT NULL THEN j.name ELSE f.name END) NAME,i.id ladingId,  i.code ladingCode,(case when ISNULL(i.startTime) then i.endTime else i.startTime end) ladingTime,(SELECT CASE WHEN l.type='1' THEN '转卖' ELSE '发货' END FROM t_pcs_lading l WHERE l.id=h.ladingId) ladingType from t_pcs_goods d LEFT JOIN t_pcs_product e ON e.id = d.productId LEFT JOIN t_pcs_client f ON f.id = d.clientId LEFT JOIN t_pcs_goods_group h on h.id=d.goodsGroupId LEFT JOIN t_pcs_lading i on i.id=h.ladingId LEFT JOIN t_pcs_client j on j.id=d.ladingClientId LEFT JOIN t_pcs_cargo k on k.id=d.cargoId LEFT JOIN t_pcs_arrival l on l.id=k.arrivalId LEFT JOIN t_pcs_contract m on m.id=k.contractId where d.status<>1 and d.status<>2 and d.cargoId="+cargoId;
		try {
			return executeQuery(sql);
		} catch (OAException e) {
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}

	@Override
	public List<Map<String, Object>> checkCode(String code, int clientId) throws OAException {
		String sql="select count(id) count from t_pcs_lading where code='"+code+"' and clientId="+clientId +" and status<>2 ";
		try {
			return executeQuery(sql);
		} catch (OAException e) {
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
		
	}

	@Override
	public List<Map<String, Object>> getTreeRootIds(GoodsDto goodsDto)
			throws OAException {
		String sql="select (case when ISNULL(x.goodsGroupId) then (select GROUP_CONCAT(id) from t_pcs_goods where sourceGoodsId=x.id ) when x.rootGoodsId=0 then (select GROUP_CONCAT(id) from t_pcs_goods where sourceGoodsId=x.sourceGoodsId ) else (select GROUP_CONCAT(b.id) from t_pcs_goods a , t_pcs_goods b where a.sourceGoodsId=b.sourceGoodsId and a.id=x.rootGoodsId) end ) rootIds from t_pcs_goods x where x.id="+goodsDto.getId();
		try {
			return executeQuery(sql);
		} catch (OAException e) {
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
		
	}

	@Override
	public Map<String, Object> getGoodsWaitByLadingId(String ladingId)
			throws OAException {
		try {
		String sql=" SELECT round(SUM(a.deliverNum), 3) goodsWait "
					+" from t_pcs_goodslog a ,t_pcs_goods b ,t_pcs_goods_group c  "
					+" where a.actualNum = 0 AND a.deliverNum != 0 AND a.type = 5 AND a.actualType = 0 "
					+" AND a.goodsId = b.id and c.id=b.goodsGroupId and  c.ladingId="+ladingId;
		return executeQueryOne(sql);
		} catch (OAException e) {
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}

	@Override
	public void repairLading(String ladingId) throws OAException {
		

		try {
		String sql="UPDATE sop.t_pcs_lading a,(select  b.goodsGroupId ,sum(b.goodsTotal) goodsTotal, sum(b.goodsOutPass) goodsOutPass,sum(b.goodsOut) goodsOut from sop.t_pcs_goods b GROUP BY b.goodsGroupId) b,"
		 +" sop.t_pcs_goods_group c "
		+" SET a.goodsPass = b.goodsOutPass, "
		 +" a.goodsOut = b.goodsOut, "
		+" a.goodsTotal=b.goodsTotal "
		+" WHERE b.goodsGroupId = c.id "
		+" AND c.ladingId = a.id and a.id="+ladingId;
		
		
		executeUpdate(sql);
		} catch (OAException e) {
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	
		
	}

	@Override
	public Map<String, Object> getGoodsLossByLadingId(String ladingId)
			throws OAException {
		try {
		String sql=" SELECT -round(SUM(a.goodsChange), 3) goodsLoss "
					+" from t_pcs_goodslog a ,t_pcs_goods b ,t_pcs_goods_group c  "
					+" where a.type=10 and a.deliverNo=1 "
					+" AND a.goodsId = b.id and c.id=b.goodsGroupId and  c.ladingId="+ladingId;
		return executeQueryOne(sql);
		} catch (OAException e) {
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}

	@Override
	public void updateladingNo(String id, String no) throws OAException {
		String sql="update t_pcs_lading set No='"+no+"' where id="+id;
		try {
			execute(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void realDeleteLading(String ladingId) throws OAException {
		String sql="delete from t_pcs_lading  where id in("+ladingId+")";
		try {
			execute(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}

	@Override
	public List<Map<String, Object>> getCheckEndTimeLadingIds(int id) throws OAException {
		

		String sql="select GROUP_CONCAT( DISTINCT d.ladingId) ladingIds from t_pcs_goods b LEFT JOIN t_pcs_goods c on c.id=b.sourceGoodsId LEFT JOIN t_pcs_goods_group d on d.id=c.goodsGroupId LEFT JOIN t_pcs_lading f on f.id=d.ladingId LEFT JOIN t_pcs_goods_group e on e.id=b.goodsGroupId where f.type=2 and f.isLong<>1 and e.ladingId="+id;
		try {
		return executeQuery(sql);
		} catch (OAException e) {
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
		
	}

	@Override
	public List<Map<String, Object>> getCheckEndTime(String ids) throws OAException {
		

		String sql="select min(UNIX_TIMESTAMP(a.endTime))  endTime,sum(a.isLong) isLong,count(a.id) count from t_pcs_lading a where a.id in ("+ids+")";
		try {
		return executeQuery(sql);
		} catch (OAException e) {
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
		
	}

	@Override
	public Map<String, Object> checkNo(String no) throws OAException {
		

		String sql="select max(REPLACE(a.`No`,'No.','')) No from t_pcs_lading a where a.No like '%"+no+"%' ";
		return executeQuery(sql).get(0);
		
	}

}
