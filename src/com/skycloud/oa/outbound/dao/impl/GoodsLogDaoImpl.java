package com.skycloud.oa.outbound.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dao.GoodsLogDao;
import com.skycloud.oa.outbound.dto.GoodsLogDto;
import com.skycloud.oa.outbound.model.GoodsLog;
import com.skycloud.oa.statistics.dto.StatisticsDto;
import com.skycloud.oa.utils.Common;

/**
 * 
 * <p>出库管理---发货开票</p>
 * @ClassName:GoodsLogDaoImpl
 * @Description:
 * @Author:chenwj
 * @Date:2015年5月27日 下午9:00:40
 *
 */
@Component
public class GoodsLogDaoImpl extends BaseDaoImpl implements GoodsLogDao 
{
	/**
	 * 记录日志
	 */
	private static Logger logger = Logger.getLogger(GoodsLogDaoImpl.class);
	
	
	
	/**
	 * 
	 * @Title:changeInvoice
	 * @Description:
	 * @param goodsLog
	 * @throws OAException
	 * @see
	 */
	public void changeInvoice(GoodsLog goodsLog)throws OAException
	{
		
		String sql=" update t_pcs_goodslog set vehicleShipId="+goodsLog.getVehicleShipId()+" where type=5 and batchId="+goodsLog.getBatchId();
		executeUpdate(sql);
		if(goodsLog.getDeliverType()==1){
		String sql1=" update t_pcs_train set plateId="+goodsLog.getVehicleShipId()+" where id="+goodsLog.getBatchId();
		executeUpdate(sql1);
		}
	}
	
	

	/**
	 * 通过id获取开票信息
	 * @Title:get
	 * @Description:
	 * @param id
	 * @return
	 * @throws OAException
	 * @see
	 */
	@Override
	public List<Map<String, Object>> get(String id) throws OAException 
	{
		String sql ="SELECT a.id,a.batchId,a.tankName,a.deliverType,a.vehicleShipId,a.ladingEvidence,a.ladingClientId,a.serial,o.code cargoCode,a.remark,a.createUserId createUserId,t.name createUserName, "
					+" from_unixtime(a.invoiceTime) createTime ,round(a.deliverNum,3) deliverNum,round(a.actualNum,3) actualNum,b.code goodsCode,b.productId,c.name productName, "
					+" d.code clientCode,d.name clientName,e.name ladingClientName,(case when a.deliverType=1 THEN g.name else CONCAT(f.refName,'/',DATE_FORMAT(s.arrivalStartTime,'%Y-%m-%d')) END ) vsName, "
					+" i.name shangjihuozhu,(case when ISNULL(k.name) THEN d.name else k.name END ) yuanshihuozhu, "
					+" (CASE WHEN !ISNULL(b.rootGoodsId) and b.rootGoodsId!=0 THEN n.code WHEN ISNULL(l.code) THEN o.code ELSE l.code END )  yuanhao, "
					+" l.code diaohao,CONCAT(q.NAME,'/',r.refName,'  ',DATE_FORMAT(p.arrivalStartTime,'%Y-%m-%d')) shipInfo, "
					+" ROUND(b.goodsCurrent-(b.goodsTotal-b.goodsInPass),3) goodsCurrent,a.storageInfo,a.actualType "
					+" from t_pcs_goodslog a "
					+" LEFT JOIN t_pcs_goods b on a.goodsId=b.id "
					+" LEFT JOIN t_pcs_product c on c.id=b.productId "
					+" LEFT JOIN t_pcs_arrival s on a.batchId=s.id and s.type=2"
					+" LEFT JOIN t_pcs_client d on d.id=b.clientId "
					+" LEFT JOIN t_pcs_client e on e.id=a.ladingClientId "
					+" LEFT JOIN t_pcs_ship_ref f on f.id=a.vehicleShipId "
					+" LEFT JOIN t_pcs_truck g on g.id=a.vehicleShipId "
					+" LEFT JOIN t_pcs_goods h on h.id=b.sourceGoodId "
					+" LEFT JOIN t_pcs_client i on i.id=h.clientId "
					+" LEFT JOIN t_pcs_goods j on j.id=b.rootGoodsId "
					+" LEFT JOIN t_pcs_lading l on l.id=a.ladingId "
					+" LEFT JOIN t_pcs_goods_group m on m.id=j.goodsGroupId "
					+" LEFT JOIN t_pcs_lading n on n.id=m.ladingId "
					+" LEFT JOIN t_pcs_cargo o on o.id=b.cargoId "
					+" LEFT JOIN t_pcs_client k on k.id=o.clientId "
					+" LEFT JOIN t_pcs_arrival p on p.id=o.arrivalId "
					+" LEFT JOIN t_pcs_ship q on q.id=p.shipId "
					+" LEFT JOIN t_pcs_ship_ref r on r.id=p.shipRefId "
					+" LEFT JOIN t_auth_user t on t.id=a.createUserId "
					+" where a.id="+id;
					return executeQuery(sql) ;
	}

	/**
	 * 出库管理-发货开票-添加开票
	 * @Title:add
	 * @Description:
	 * @param goodsLog
	 * @return
	 * @throws OAException
	 * @see
	 */
	@Override
	public int add(GoodsLog goodsLog) throws OAException 
	{
		Serializable s = save(goodsLog) ;
		return Integer.parseInt(s.toString()) ;
	}

	/**
	 * 出库管理-发货开票-更新当前存量
	 * @Title:updateGoodsNum
	 * @Description:
	 * @param goodsLog
	 * @return
	 * @throws OAException
	 * @see
	 */
	@Override
	public int updateGoodsNum(GoodsLog goodsLog) throws OAException 
	{
		StringBuffer sb = new StringBuffer() ;
		sb.append("	UPDATE 	");
		sb.append("	  t_pcs_goods g 	");
		sb.append("	SET	");
		sb.append("	  g.goodsCurrent = ROUND(g.goodsCurrent - "+goodsLog.getDeliverNum()+",3)");
		sb.append("	WHERE g.id ="+goodsLog.getGoodsId());
		
		return executeUpdate(sb.toString());
	}

	/**
	 * 出库管理-发货开票-开票列表撤销操作
	 * @Title:delete
	 * @Description:
	 * @param ids
	 * @throws OAException
	 * @see
	 */
	@Override
	public void delete(String ids) throws OAException 
	{
		String sql = "call deleteGoodsLog("+ids+")" ;
		
		logger.info("出库管理-发货开票-开票列表撤销："+sql);
		
		executeProcedure(sql);
	}

	/**
	 * 判断可提量不足
	 * @Title:checkCanDeliveryNum
	 * @Description:
	 * @param goodsLog
	 * @return
	 * @throws OAException
	 * @see
	 */
	@Override
	public int checkCanDeliveryNum(GoodsLog goodsLog) throws OAException 
	{
		String sql = "select count(1) from t_pcs_goods g where id="+goodsLog.getGoodsId()+" and (round(g.goodsCurrent-(g.goodsTotal-g.goodsOutPass),3))-"+goodsLog.getDeliverNum()+">=0" ;
		
		logger.info("判断可提量不足的SQL语句："+sql);
		
		return (int)getCount(sql) ;
	}
	
	/**
	 * 出库管理-发货开票-更新开票记录
	 * @Title:up
	 * @Description:
	 * @param goodsLog
	 * @throws OAException
	 * @see
	 */
	@Override
	public void up(GoodsLog goodsLog) throws OAException 
	{
		StringBuffer sb = new StringBuffer() ;
		sb.append("update t_pcs_goodslog set id=id ") ;
		if(!Common.empty(goodsLog.getDeliverNum())&&goodsLog.getDeliverNum()!=0)
		{
			sb.append(",deliverNum="+goodsLog.getDeliverNum()) ;
		}
		if(!Common.empty(goodsLog.getBatchId())&&goodsLog.getBatchId()!=0){
			sb.append(",batchId="+goodsLog.getBatchId());
		}
		if(!Common.empty(goodsLog.getActualNum())&&goodsLog.getActualNum()!=0)
		{
			sb.append(",actualNum="+goodsLog.getActualNum()) ;
		}
		if(goodsLog.getCreateTime()!=0&&goodsLog.getCreateTime()!=-1){
			sb.append(",createTime="+goodsLog.getCreateTime()) ;
			sb.append(",originalTime="+goodsLog.getCreateTime()) ;
			
		}
		if(!Common.empty(goodsLog.getStorageInfo())){
			sb.append(",storageInfo='"+goodsLog.getStorageInfo()+"'") ;
		}
		if(!Common.empty(goodsLog.getVehicleShipId())&&goodsLog.getVehicleShipId()!=0){
			sb.append(",vehicleShipId="+goodsLog.getVehicleShipId()) ;
		}
		if(!Common.empty(goodsLog.getLadingClientId())&&goodsLog.getLadingClientId()!=0)
		{
			sb.append(",ladingClientId="+goodsLog.getLadingClientId()) ;
		}
		if(!Common.empty(goodsLog.getLadingEvidence()))
		{
			sb.append(",ladingEvidence='"+goodsLog.getLadingEvidence()+"'") ;
		}
		if(!Common.empty(goodsLog.getGoodsChange())&&goodsLog.getGoodsChange()!=0)
		{
			sb.append(",goodsChange="+goodsLog.getGoodsChange()) ;
		}
		if(!Common.empty(goodsLog.getDeliverNo()))
		{
			sb.append(",deliverNo="+goodsLog.getDeliverNo()+"'") ;
		}
		if(!Common.empty(goodsLog.getType())&&goodsLog.getType()!=0)
		{
			sb.append(",type="+goodsLog.getType()) ;
		}
		if(!Common.empty(goodsLog.getClientId())&&goodsLog.getClientId()!=0)
		{
			sb.append(",clientId="+goodsLog.getClientId()) ;
		}
		if(!Common.empty(goodsLog.getGoodsId())&&goodsLog.getGoodsId()!=0)
		{
			sb.append(",goodsId="+goodsLog.getGoodsId()) ;
		}
		if(!Common.empty(goodsLog.getGoodsSave())&&goodsLog.getGoodsSave()!=0)
		{
			sb.append(",goodsSave="+goodsLog.getGoodsSave()) ;
		}
		if(!Common.empty(goodsLog.getLadingId())&&goodsLog.getLadingId()!=0)
		{
			sb.append(",ladingId="+goodsLog.getLadingId()) ;
		}
		if(!Common.empty(goodsLog.getSurplus())&&goodsLog.getSurplus()!=0)
		{
			sb.append(",surplus="+goodsLog.getSurplus()) ;
		}
		if(!Common.empty(goodsLog.getTempDeliverNum()))
		{
			sb.append(",tempDeliverNum="+goodsLog.getTempDeliverNum()) ;
		}
		if(!Common.empty(goodsLog.getAfUpNum()))
		{
			sb.append(",afUpNum="+goodsLog.getAfUpNum()) ;
		}
		if(!Common.empty(goodsLog.getAfDiffNum()))
		{
			sb.append(",afDiffNum= round("+goodsLog.getAfDiffNum()+",3) ") ;
		}
		if(goodsLog.getActualType()!=null&&goodsLog.getActualType()!=0){
			sb.append(",actualType="+goodsLog.getActualType());
		}
		if(goodsLog.getInvoiceTime()!=null&&goodsLog.getInvoiceTime()!=-1){
			sb.append(",invoiceTime="+goodsLog.getInvoiceTime());
		}
		if(goodsLog.getTankName()!=null){
			sb.append(",tankName='"+goodsLog.getTankName()+"'");
		}
		if(goodsLog.getRemark()!=null){
            sb.append(",remark='"+goodsLog.getRemark()+"'");			
 		}
		if(goodsLog.getIsCleanInvoice()!=null){
			sb.append(",isCleanInvoice="+goodsLog.getIsCleanInvoice());
		}
		if(!Common.isNull(goodsLog.getId()))
		{
			sb.append(" where id="+goodsLog.getId()) ;
		}
		
		
		executeUpdate(sb.toString()) ;
	}
	
	/**
	 * 更新记录
	 * @Title:updateGoodsCurrentByActualNum
	 * @Description:
	 * @param goodsLog
	 * @throws OAException
	 * @see
	 */
	@Override
	public void updateGoodsCurrentByActualNum(GoodsLog goodsLog) throws OAException 
	{
		String sql = "update t_pcs_goods set goodsCurrent=round(goodsCurrent-"+goodsLog.getActualNum()+",3)  where id="+goodsLog.getGoodsId() ;
		String sql2 = "UPDATE t_pcs_lading l SET l.goodsDelivery=round(goodsDelivery+"+goodsLog.getActualNum()+",3)  WHERE l.id=(SELECT ladingId FROM t_pcs_goods_group WHERE id=(SELECT goodsGroupId FROM t_pcs_goods WHERE id="+goodsLog.getGoodsId()+"))" ;
		executeUpdate(sql) ;
		executeUpdate(sql2) ;
		StringBuffer sb = new StringBuffer() ;
		sb.append("UPDATE t_pcs_goodslog t LEFT JOIN t_pcs_goods s ON t.`goodsId` = s.id ");
		sb.append("SET t.surplus =round((s.goodsCurrent-(s.goodsTotal-s.goodsOutPass)),3),");
		sb.append("t.`goodsSave`=round(s.goodsTotal-s.goodsOutPass,3),");
		sb.append("t.goodsChange = - ").append(goodsLog.getActualNum());
		sb.append(" ,t.actualNum =").append(goodsLog.getActualNum());
		if(goodsLog.getCreateTime()!=0&&goodsLog.getCreateTime()!=-1){
		sb.append(",t.createTime="+goodsLog.getCreateTime());
		}
		sb.append(",t.actualType=1 ");
		sb.append(" where 1=1 ");
		if(!Common.isNull(goodsLog.getId()))
		{
			sb.append(" and t.id = ").append(goodsLog.getId()).append(" ");
		}else{
			sb.append(" and t.id =0 ");
		}
		
		logger.info("更新t_pcs_goods表："+sql);
		logger.info("更新t_pcs_lading表："+sql2);
		logger.info("更新t_pcs_goodslog表："+sb.toString());
		
		executeUpdate(sb.toString()) ;
	}
	
	/**
	 * 
	 * @Title:upChangeValue
	 * @Description:
	 * @param goodsLog
	 * @throws OAException
	 * @see
	 */
	@Override
	public void upChangeValue(GoodsLog goodsLog)throws OAException 
	{
		String sql = "update t_pcs_goods set goodsCurrent=ROUND(goodsCurrent+"+goodsLog.getActualNum()+"-"+goodsLog.getAfUpNum()+",3)  where id="+goodsLog.getGoodsId() ;
		String sql2 = "UPDATE t_pcs_lading l SET l.goodsDelivery=round((goodsDelivery-"+goodsLog.getActualNum()+"+"+goodsLog.getAfUpNum()+"),3) WHERE l.id=(SELECT ladingId FROM t_pcs_goods_group WHERE id=(SELECT goodsGroupId FROM t_pcs_goods WHERE id="+goodsLog.getGoodsId()+"))" ;
		String sql3 = "update t_pcs_train set isChange=0 where id=(select batchId from t_pcs_goodslog where id="+goodsLog.getId()+")" ;
		String sql4 = "update t_pcs_goodslog set afUpNum=null,afDiffNum=null,actualNum="+goodsLog.getAfUpNum()+",goodsChange=-"+goodsLog.getAfUpNum()+" ,surplus =ROUND(surplus+"+goodsLog.getActualNum()+"-"+goodsLog.getAfUpNum()+",3),actualType=1 where id="+goodsLog.getId();
		executeUpdate(sql);
		executeUpdate(sql2);
		executeUpdate(sql3);
		executeUpdate(sql4);
		logger.info("更新t_pcs_goods当前存量："+sql);
		logger.info("更新t_pcs_lading实发量："+sql2);
		logger.info("更新t_pcs_train是否修改状态："+sql3);
		logger.info("更新t_pcs_goodslog实发量、变化量,当前存量：："+sql4);
	}

	/**
	 * 
	 * @Title:getGoodsLog
	 * @Description:
	 * @param goodsId
	 * @return
	 * @throws OAException
	 * @see
	 */
	@Override
	public List<Map<String, Object>> getGoodsLog(String goodsId) throws OAException 
	{
		String sql="select a.*,FROM_UNIXTIME(a.createTime) mCreateTime,FROM_UNIXTIME(a.originalTime) mOriginalTime,j.customLading ,l.name arrivalShip,b.code ladingCode1,h.code nextLadingCode,g.name ladingClientName,c.name clientName,(case when a.deliverType=1 then (select code from t_pcs_truck where id=a.vehicleShipId) else (SELECT sr.refName FROM t_pcs_ship_ref sr WHERE sr.id=a.vehicleShipId) end) carshipCode from t_pcs_goodslog a " +
				"LEFT JOIN t_pcs_lading b on b.id=a.ladingId " +
				"LEFT JOIN t_pcs_client c on c.id=a.clientId "+
				"LEFT JOIN t_pcs_client g on g.id=a.ladingClientId " +
				"LEFT JOIN t_pcs_lading h on h.id=a.nextLadingId " +
				"LEFT JOIN t_pcs_goods i on i.id=a.goodsId "+
				"LEFT JOIN t_pcs_cargo j on j.id=i.cargoId "+
				"LEFT JOIN t_pcs_arrival k on k.id=j.arrivalId "+
				"LEFT JOIN t_pcs_ship l on l.id=k.shipId "+
				"where a.type<>8 and a.goodsId="+goodsId+" order by a.originalTime ASC";
		
		
		return executeQuery(sql);
	}
	
	/**
	 * 
	 * @Title:getLastGoodsLog
	 * @Description:
	 * @param goodsId
	 * @param time
	 * @return
	 * @throws OAException
	 * @see
	 */
	@Override
	public List<Map<String, Object>> getLastGoodsLog(String goodsId, Long time) throws OAException 
	{
		String sql="select a.*,b.code ladingCode1,g.name ladingClientName,c.name clientName,(case when a.deliverType=1 then (select code from t_pcs_truck where id=a.vehicleShipId) else (SELECT sr.refName FROM t_pcs_ship_ref sr WHERE sr.id=a.vehicleShipId) end) carshipCode from t_pcs_goodslog a " +
				"LEFT JOIN t_pcs_lading b on b.id=a.ladingId " +
				"LEFT JOIN t_pcs_client c on c.id=a.clientId "+
				//"LEFT JOIN t_pcs_truck e on e.id=a.batchId "+
				//"LEFT JOIN t_pcs_ship_ref f on f.id=a.batchId " +
				"LEFT JOIN t_pcs_client g on g.id=a.ladingClientId " +
				"where a.type<>8 and a.goodsId="+goodsId+" and a.createTime<"+(time+86400)+"  order by a.createTime DESC limit 0,1";
		return executeQuery(sql);
	}
	
	/**
	 * 
	 * @Title:getGoodsLog
	 * @Description:
	 * @param goodsId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws OAException
	 * @see
	 */
	@Override
	public List<Map<String, Object>> getGoodsLog(Integer showVirTime,String goodsId,Long startTime, Long endTime,int type) throws OAException 
	{
		
		String time="createTime";
			if(showVirTime==2){
				time="originalTime";
			}
		
		String sql="select a.*,b.code ladingCode,g.name ladingClientName,c.name clientName,(case when a.deliverType=1 then (select code from t_pcs_truck where id=a.vehicleShipId) else (SELECT sr.refName FROM t_pcs_ship_ref sr WHERE sr.id=a.vehicleShipId) end) carshipCode from t_pcs_goodslog a " +
				"LEFT JOIN t_pcs_lading b on b.id=a.ladingId " +
				"LEFT JOIN t_pcs_client c on c.id=a.clientId "+
				//"LEFT JOIN t_pcs_truck e on e.id=a.vehicle "+ 
				//"LEFT JOIN t_pcs_ship_ref f on f.id=a.batchId " +
				"LEFT JOIN t_pcs_client g on g.id=a.ladingClientId " +
				"where a.type<>8 " ;
				if(type!=0){
					sql+=" and a.type="+type;
				}
				sql+=" and a.goodsId="+goodsId+" and a."+time+">="+startTime+" and  a."+time+"<"+endTime+"  order by a."+time+" DESC";
		
		
		return executeQuery(sql);
	}
	
	/**
	 * 
	 * @Title:getGoods
	 * @Description:
	 * @param sDto
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 * @see
	 */
	@Override
	public List<Map<String, Object>> getGoods(StatisticsDto sDto, int start, int limit,boolean isAll)
			throws OAException {
//		String sql="select DISTINCT(a.goodsId),b.isFinish from t_pcs_goodslog a " +
//				"LEFT JOIN t_pcs_goods b on b.id=a.goodsId LEFT JOIN t_pcs_cargo c on c.id=b.cargoId LEFT JOIN t_pcs_arrival d on d.id=c.arrivalId where a.createTime>= "+sDto.getStartTime() +" and a.createTime<"+(sDto.getEndTime()+86400)+" ";
//		"LEFT JOIN t_pcs_goods b on b.id=a.goodsId where a.createTime<"+(sDto.getEndTime()+86400)+" ";
		String time="createTime";
		if(!Common.isNull(sDto.getShowVirTime())){
			if(sDto.getShowVirTime()==2){
				time="originalTime";
			}
		}
		String sql="";
		if(isAll){
			sql="select DISTINCT a.id goodsId,a.isFinish from t_pcs_goods a  LEFT JOIN t_pcs_cargo d on d.id=a.cargoId LEFT JOIN t_pcs_goodslog b on b.goodsId=a.id where !isnull(a.id) and a.createTime<FROM_UNIXTIME("+(sDto.getEndTime() + 86400)+")";
			
		}else{
			
		sql="select DISTINCT a.id goodsId,a.isFinish from t_pcs_goods a LEFT JOIN t_pcs_cargo d on d.id=a.cargoId LEFT JOIN t_pcs_goodslog b on b.goodsId=a.id where ((select count(id) from t_pcs_goodslog e where e.goodsId=a.id and e."+time+">"+sDto.getStartTime()+" and e."+time+" < "+(sDto.getEndTime()+86400)+")>0 or (select round(sum(c.goodsChange),3) goodsSave from t_pcs_goodslog c where c.type<>8 AND if(c.type=5 and c.actualType=0 ,0,1)=1 and c."+time+" < "+(sDto.getEndTime()+86400)+" and c.goodsId=a.id )<>0 )";
		}
		
		if(!Common.isNull(sDto.getCargoCode()))
		{
			sql+=" and REPLACE(d.code,' ','') like '%"+sDto.getCargoCode().replace(" ", "")+"%'";
		}
		if(!Common.isNull(sDto.getProductId()))
		{
			sql+=" and a.productId="+sDto.getProductId();
		}
		if(!Common.isNull(sDto.getClientId()))
		{
			sql+=" and ((a.clientId="+sDto.getClientId()+" and (ISNULL(a.ladingClientId) or a.ladingClientId=0)) or a.ladingClientId="+sDto.getClientId()+")";
		}
		if(!Common.empty(sDto.getCode()))
		{
			sql+=" and REPLACE(a.code,' ','') like '%"+sDto.getCode().replace(" ", "")+"%'";
		}
		if(!Common.empty(sDto.getShowVir())){
			
			if(sDto.getShowVir()==1){
				
				sql+=" and d.arrivalId <>0 ";
				
			}
			if(sDto.getShowVir()==3){
				
				sql+=" and d.arrivalId =0 ";
				
			}
		}
		
		if(!Common.isNull(sDto.getTankId()))
		{
			sql+=" and a.tankId="+sDto.getTankId();
		}
		
		if(sDto.getOrder()!=null)
		{
			switch (sDto.getOrder()) 
			{
				case 0:
					sql+=" order by a.createTime DESC ";
					break;

				case 1:
					sql+=" order by a.createTime ASC ";
					break;
				case 2:
					sql+=" order by a.tankId DESC ";
					break;
				case 3:
					sql+=" order by a.tankId ASC ";
					break;
			}
		}else{
			sql+=" order by a.createTime DESC ";
		}
		
		
		
		if(limit!=0)
		{
			sql+=" limit "+start+","+limit;
		}
		
		return executeQuery(sql);
	}
	
	/**
	 * 
	 * @Title:updatePrintType
	 * @Description:
	 * @param goodsLogDto
	 * @throws OAException
	 * @see
	 */
	@SuppressWarnings("unused")
	public void updatePrintType(GoodsLogDto goodsLogDto)throws OAException
	{
		StringBuffer sb = new StringBuffer() ;
		sb.append("	UPDATE 	t_pcs_goodslog g set printType = 1 where deliverType="+goodsLogDto.getDeliverType()+" and  vehicleShipId=(select case when 1="+goodsLogDto.getDeliverType()+" then (select id from t_pcs_truck where code='"+goodsLogDto.getVsName()+"') else (select id from t_pcs_ship_ref where refName='"+goodsLogDto.getVsName()+"') end )") ;
		executeUpdate(sb.toString()) ;
	}
	
	/**
	 * 
	 * @Title:cleanToStatus
	 * @Description:
	 * @param id
	 * @param productId
	 * @throws OAException
	 * @see
	 */
	@Override
	public void cleanToStatus(int id, Integer productId) throws OAException 
	{
		try 
		{
			String sql="DELETE FROM	t_pcs_goodslog WHERE goodsId IN (	SELECT t.id	FROM(	SELECT a.id FROM t_pcs_goods a WHERE a.cargoId IN ( SELECT 	b.id FROM t_pcs_cargo b WHERE"
							+" b.productId ="+productId+" AND b.arrivalId ="+id+" ) AND ( a.isPredict IS NULL OR a.isPredict < 1 ) AND ISNULL(a.rootGoodsId) ) AS t )";
		    execute(sql);	
		}
		catch (RuntimeException e) 
		{
			throw new OAException(Constant.SYS_CODE_DB_ERR,"daogoodslog回退失败",e);
		}	
	}
	
	/**
	 * 
	 * @Title:cleanToStatus
	 * @Description:
	 * @param id
	 * @throws OAException
	 * @see
	 */
	@Override
	public void cleanToStatus(int id) throws OAException 
	{
		try 
		{
			String sql="DELETE FROM	t_pcs_goodslog WHERE goodsId IN (	SELECT t.id	FROM(	SELECT a.id FROM t_pcs_goods a WHERE a.cargoId IN ( SELECT 	b.id FROM t_pcs_cargo b WHERE"
					+" b.arrivalId ="+id+" ) AND ( a.isPredict IS NULL OR a.isPredict < 1 ) AND ISNULL(a.rootGoodsId) ) AS t )";
		    execute(sql);	
		} 
		catch (RuntimeException e) 
		{
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
		}	
	}
	
	/**
	 * 
	 * @Title:deleteGoodsLog
	 * @Description:
	 * @param goodsIds
	 * @throws OAException
	 * @see
	 */
	@Override
	public void deleteGoodsLog(String goodsIds) throws OAException 
	{
		try
		{
			String sql="delete from t_pcs_goodslog where goodsId in ("+goodsIds+")";
			execute(sql);
		} 
		catch (RuntimeException e) 
		{
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao删除失败", e);
		}	
	}
	
	/**
	 * 
	 * @Title:getTodayLog
	 * @Description:
	 * @param goodsId
	 * @param time
	 * @return
	 * @throws OAException
	 * @see
	 */
	@Override
	public List<Map<String, Object>> getTodayLog(String goodsId, Long time) throws OAException 
	{
		String sql="select a.*,b.code ladingCode,g.name ladingClientName,c.name clientName,(case when a.deliverType=1 then (select code from t_pcs_truck where id=a.vehicleShipId) else (SELECT sr.refName FROM t_pcs_ship_ref sr WHERE sr.id=a.vehicleShipId) end) carshipCode from t_pcs_goodslog a " +
				"LEFT JOIN t_pcs_lading b on b.id=a.ladingId " +
				"LEFT JOIN t_pcs_client c on c.id=a.clientId "+
				"LEFT JOIN t_pcs_client g on g.id=a.ladingClientId " +
				"where a.type<>8 and a.type=5 and a.goodsId="+goodsId+" and a.createTime<"+(time+86400)+" and a.createTime>="+time+"  order by a.createTime DESC ";
		return executeQuery(sql);
	}
	
	/**
	 * 
	 * @Title:getActualNum
	 * @Description:
	 * @param goodsId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws OAException
	 * @see
	 */ 
	@Override
	public List<Map<String, Object>> getActualNum(int showVirTime,int goodsId, long startTime,long endTime) throws OAException 
	{
		
		String time="createTime";
			if(showVirTime==2){
				time="originalTime";
			}
		
		String sql=" select COALESCE(SUM(a.actualNum),0) as goodsSend from t_pcs_goodslog a ,t_pcs_goods b where a.goodsId=b.id and b.`status`<>1 and a.type=5 "; 
		if(!Common.isNull(goodsId))
		{
			sql+=" and b.id="+goodsId;
		}
		if(!Common.isNull(startTime))
		{
			sql+=" and a."+time+">="+startTime;
		}
		if(!Common.isNull(endTime))
		{
			sql+=" and a."+time+"<"+endTime;
		}
		
		return executeQuery(sql);
	}

	/**
	 * 查询待提量
	 * @Title:queryWaitAmount
	 * @Description:
	 * @param goods
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public Map<String, Object> queryWaitAmount(Integer goodsId) throws OAException 
	{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ROUND(SUM(t.deliverNum),3) waitAmount FROM t_pcs_goodslog t LEFT JOIN t_pcs_goods s  ");
		sql.append("ON s.`id` = t.`goodsId` ");
		sql.append("where t.`actualNum` = 0 AND t.`deliverNum` != 0 AND t.type=5 and t.actualType=0 ");
		//车船ID
		if(!Common.isNull(goodsId))
		{
			sql.append(" and t.goodsId = ").append(goodsId).append(" ");
		}
		
		
		return executeQueryOne(sql.toString());
	}

	
	/**
	 * 获取发货开票列表
	 *@author jiahy
	 * @param invoiceDto
	 * @param pageView
	 * @return
	 * @throws OAException
	 */
	@Override
	public List<Map<String, Object>> getInvoiceList(GoodsLogDto invoiceDto,
			PageView pageView) throws OAException {
		try{   
			
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT a.id id,a.batchId batchId,a.serial serial,a.deliverType deliverType,a.actualType, ");
			sql.append(" a.ladingEvidence ladingEvidence,a.tankName tankName,c.name productName, ");
			sql.append(" a.deliverNum deliverNum,d.code ladingCode,e.name createUserName,b.code code, ");
			sql.append(" a.storageInfo remark,h.code  cargoCode,a.actualNum,a.deliverNo, ");
			sql.append(" (SELECT i.status from t_pcs_weighbridge i where a.serial=i.serial LIMIT 0,1) status, ");
			sql.append(" f.code clientCode,f.name clientName,from_unixtime(a.createTime) createTime,from_unixtime(a.invoiceTime) invoiceTime, ");
			sql.append(" (CASE WHEN a.deliverType = 1 THEN ( SELECT CODE FROM t_pcs_truck WHERE id = a.vehicleShipId ) ");
			sql.append(" ELSE ( SELECT CONCAT(sp.name,'/',sr.refName) FROM t_pcs_ship_ref sr ,t_pcs_ship sp WHERE sr.id = a.vehicleShipId and sp.id=sr.shipId limit 0,1 ) END) vsName, ");
			sql.append(" j.name  shangjihuoti,(case WHEN !ISNULL(b.rootGoodsId) and b.rootGoodsId!=0 THEN l.name ELSE  m.name END)  yuanshihuoti,m.name  ladingClientName, ");
			sql.append(" (CASE WHEN !ISNULL(b.rootGoodsId) and b.rootGoodsId!=0 THEN r.code WHEN ISNULL(d.code) THEN h.code ELSE d.code END ) yuanhao,");
			sql.append(" d.code  diaohao,");
			sql.append(" (CASE WHEN a.actualType = 0 THEN '待提' ELSE '已提' END ) waitAmount, ");
			sql.append(" DATE_FORMAT(n.arrivalStartTime,'%Y-%m-%d') shipArrivalTime ");
			sql.append(" from t_pcs_goodslog a  ");
			sql.append(" LEFT JOIN t_pcs_goods b on a.goodsId=b.id ");
			sql.append(" LEFT JOIN t_pcs_product c on b.productId=c.id ");
			sql.append(" LEFT JOIN t_pcs_lading d on a.ladingId=d.id ");
			sql.append(" LEFT JOIN t_auth_user e on a.createUserId=e.id ");
			sql.append(" LEFT JOIN t_pcs_client f on f.id=b.clientId ");
			sql.append(" LEFT JOIN t_pcs_cargo h on h.id=b.cargoId ");
			sql.append(" LEFT JOIN t_pcs_goods i on b.sourceGoodsId=i.id ");
			sql.append(" LEFT JOIN t_pcs_client j on j.id=i.clientId ");
			sql.append(" LEFT JOIN t_pcs_goods k on b.rootGoodsId=k.id ");
			sql.append(" LEFT JOIN t_pcs_client l on l.id=k.clientId ");
			sql.append(" LEFT JOIN t_pcs_client m on m.id=a.ladingClientId ");
			sql.append(" LEFT JOIN t_pcs_arrival n on n.id=a.batchId ");
			sql.append(" LEFT JOIN t_pcs_ship_ref o on o.id=a.vehicleShipId ");
			sql.append(" LEFT JOIN t_pcs_ship s on s.id=o.shipId ");
			sql.append(" LEFT JOIN t_pcs_truck p on p.id=a.vehicleShipId ");
			sql.append(" LEFT JOIN t_pcs_goods_group q on q.id=k.goodsGroupId ");
			sql.append(" LEFT JOIN t_pcs_lading r on r.id=q.ladingId ");
			sql.append(" WHERE 1=1 ");
			if(!Common.isNull(invoiceDto.getType())&&invoiceDto.getType()==8){
				sql.append(" and a.type=8");
			}else{
				sql.append(" and a.type=5 ");
			}
			if(invoiceDto.getCancelType()!=null&&invoiceDto.getCancelType()!=-1){
				if(invoiceDto.getCancelType()==0)
					sql.append(" and a.actualType=0");
				else
					sql.append(" and a.actualType=1");
			}
			
			if(!Common.empty(invoiceDto.getClientId())&&invoiceDto.getClientId()!=0)
			{
			sql.append(" and b.clientId="+invoiceDto.getClientId() ) ; 
			}
			if(!Common.empty(invoiceDto.getProductId())&&invoiceDto.getProductId()!=0)
			{
			sql.append(" and b.productId="+invoiceDto.getProductId() ) ; 
			}
			if(!Common.empty(invoiceDto.getVsName()))
			{
			sql.append("  and (( p.name like '%"+invoiceDto.getVsName()+"%' and a.deliverType=1) or (o.refName like '%"+
			invoiceDto.getVsName()+"%' and a.deliverType!=1) or ( s.name like '%"+invoiceDto.getVsName()+"%' and a.deliverType!=1) )" ) ; 
			}
			if(!Common.empty(invoiceDto.getSerial()))
			{
			sql.append("  and a.serial like '%"+invoiceDto.getSerial()+"%'" ) ; 
			}
			if(!Common.empty(invoiceDto.getVehicleShipId())&&invoiceDto.getVehicleShipId()!=0)
			{
			sql.append("  and a.vehicleShipId="+invoiceDto.getVehicleShipId() ) ;
			}
			if(!Common.empty(invoiceDto.getDeliverType())&&invoiceDto.getDeliverType()!=0)
			{
			sql.append("  and a.deliverType="+invoiceDto.getDeliverType() ) ; 
			}
			if(!Common.empty(invoiceDto.getLadingEvidence())){
				sql.append(" and a.ladingEvidence ='"+invoiceDto.getLadingEvidence()+"'");
			}
			if(!Common.empty(invoiceDto.getPrintType()))
			{
			sql.append("  and (a.printType!=1 or a.printType is null)" ) ;
			}
			//根据ID查询
			if(!Common.isNull(invoiceDto.getId()))
			{
			sql.append("  and a.id ="+invoiceDto.getId() ) ;
			}
			if(!Common.empty(invoiceDto.getBatchId()))
			{
			sql.append("  and a.batchId="+invoiceDto.getBatchId() ) ;
			}
			if(invoiceDto.getActualType()!=null&&invoiceDto.getActualType()!=-1){
				if(invoiceDto.getActualType()==0){
					sql.append(" and a.actualNum=0 and a.actualType=0 ");
				}else{
					sql.append(" and a.actualType=1 ");
				}
			}
			sql.append(" ORDER BY a.id DESC ");
			if(pageView.getMaxresult()!=0)
			{
			sql.append(" LIMIT "+pageView.getStartRecord()+","+pageView.getMaxresult());
			}
			return executeQuery(sql.toString());
		}catch (RuntimeException e){
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao获取发货开票列表失败",e);
		}
	}
	/**获取发货开票列表数量
	 *@author jiahy
	 * @param invoiceDto
	 * @return
	 * @throws OAException
	 */
	@Override
	public int getInvoiceListCount(GoodsLogDto invoiceDto) throws OAException {
		try{
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT count(1) ");
			sql.append(" from t_pcs_goodslog a  ");
			if(!Common.empty(invoiceDto.getProductId())&&invoiceDto.getProductId()!=0)
			{
			sql.append(" LEFT JOIN t_pcs_goods b on a.goodsId=b.id ");
			}
			if(!Common.empty(invoiceDto.getVsName()))
			{
			sql.append(" LEFT JOIN t_pcs_ship_ref o on o.id=a.vehicleShipId ");
			sql.append(" LEFT JOIN t_pcs_ship s on s.id=o.shipId ");
			sql.append(" LEFT JOIN t_pcs_truck p on p.id=a.vehicleShipId ");
			}
			sql.append(" WHERE 1=1 ");
			
			if(!Common.isNull(invoiceDto.getType())&&invoiceDto.getType()==8){
				sql.append(" and a.type=8 ");
			}else{
				sql.append(" and a.type=5 ");
			}
			if(invoiceDto.getCancelType()!=null&&invoiceDto.getCancelType()!=-1){
				if(invoiceDto.getCancelType()==0)
					sql.append(" and a.actualType=0");
				else
					sql.append(" and a.actualType=1");
			}
			if(!Common.empty(invoiceDto.getClientId())&&invoiceDto.getClientId()!=0)
			{
			sql.append(" and b.clientId="+invoiceDto.getClientId() ) ; 
			}
			if(!Common.empty(invoiceDto.getProductId())&&invoiceDto.getProductId()!=0)
			{
			sql.append(" and b.productId="+invoiceDto.getProductId() ) ; 
			}
			if(!Common.empty(invoiceDto.getVsName()))
			{
			sql.append("  and  ( (p.name like '%"+invoiceDto.getVsName()+"%' AND a.deliverType=1) or (o.refName like '%"+
			invoiceDto.getVsName()+"%' AND a.deliverType!=1)  or (s.name like '%"+invoiceDto.getVsName()+"%' AND a.deliverType!=1 ))" ) ; 
			}
			if(!Common.empty(invoiceDto.getSerial()))
			{
			sql.append("  and a.serial like '%"+invoiceDto.getSerial()+"%'" ) ; 
			}
			if(!Common.empty(invoiceDto.getVehicleShipId())&&invoiceDto.getVehicleShipId()!=0)
			{
			sql.append("  and a.vehicleShipId="+invoiceDto.getVehicleShipId() ) ;
			}
			if(!Common.empty(invoiceDto.getLadingEvidence())){
				sql.append(" and a.ladingEvidence ='"+invoiceDto.getLadingEvidence()+"'");
			}
			if(!Common.empty(invoiceDto.getDeliverType())&&invoiceDto.getDeliverType()!=0)
			{
			sql.append("  and a.deliverType="+invoiceDto.getDeliverType() ) ; 
			}
			if(!Common.empty(invoiceDto.getPrintType()))
			{
			sql.append("  and (a.printType!=1 or a.printType is null)" ) ;
			}
			//根据ID查询
			if(!Common.isNull(invoiceDto.getId()))
			{
			sql.append("  and a.id ="+invoiceDto.getId() ) ;
			}
			if(!Common.empty(invoiceDto.getBatchId()))
			{
			sql.append("  and a.batchId="+invoiceDto.getBatchId() ) ;
			}
			
			if(invoiceDto.getActualType()!=null&&invoiceDto.getActualType()!=-1){
				if(invoiceDto.getActualType()==0){
					sql.append(" and a.actualNum=0 and a.actualType=0 ");
				}else{
					sql.append(" and a.actualType=1 ");
				}
			}
			return (int) getCount(sql.toString());
		}catch (RuntimeException e){
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao获取发货开票列表数量失败",e);
		}
	}

	@Override
	public Map<String,Object> checkIsKupono(GoodsLogDto glDto) throws OAException {
		try {
			if(glDto.getDeliverType()!=null){
				String sql="";
			if(glDto.getDeliverType()==1){//车发
				sql="SELECT COUNT(a.id) count,round(sum(a.deliverNum),3) deliverNum,GROUP_CONCAT(a.storageInfo) storageInfos from t_pcs_goodslog a "
						+" LEFT JOIN t_pcs_truck b ON a.vehicleShipId=b.id "
						+" WHERE a.deliverType=1 and a.serial LIKE '%"+glDto.getSerial()+"%' and a.type=5 and a.actualType=0 and b.name='"+glDto.getVsName()+"'";
			}else if(glDto.getDeliverType()==2){
				sql="SELECT COUNT(a.id) count,round(sum(a.deliverNum),3) deliverNum from t_pcs_goodslog a  "
						+" WHERE  a.deliverType=2  and a.serial LIKE '%"+glDto.getSerial()+"%' and a.type=5 and a.actualType=0 and a.batchId="+glDto.getBatchId();
			}else if(glDto.getDeliverType()==3){
				sql="select count(a.id) count from t_pcs_goodslog a LEFT JOIN t_pcs_ship_ref b ON a.vehicleShipId=b.id "
						+ " WHERE a.deliverType=3  and a.serial LIKE '%"+glDto.getSerial()+"%' and a.type=5 and a.actualType=0 and b.refName='GS' and a.batchId="+glDto.getBatchId();
			}
			return executeQueryOne(sql);
			}
		} catch (RuntimeException e) {
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao获取发货开票校验是否开联单失败",e);
		}
		return null;
		
	}
    
	/**
	 * 同步流转图的goodsLog
	 *@author jiahy
	 * @param goodsLog
	 */
	@Override
	public void synGoodsLog(GoodsLog goodsLog) throws OAException {
		//  id， goodsId,actualNum 之前实发量,afUpNum 修改量
		if(goodsLog.getId()!=0&&goodsLog.getGoodsId()!=0&&BigDecimal.valueOf(goodsLog.getActualNum())!=goodsLog.getAfUpNum()){
			String  sql =" update t_pcs_goodslog set surplus=round((surplus-"+goodsLog.getAfUpNum()+"+"+goodsLog.getActualNum()+"),3) where goodsId="+goodsLog.getGoodsId()
					+" and type <>8 and createTime>="+goodsLog.getCreateTime()+" and id<>"+goodsLog.getId();
			executeUpdate(sql);
		}
	}
	
	/** 
	 * 将一个发货通知单拆为两个联单
	 *@author jiahy
	 * @param glDto
	 */
	@Override
	public int splitDeliverGoods(GoodsLogDto glDto) throws OAException {
		try{
			if(glDto.getGoodsLogId()!=null && glDto.getSplitNum()!=null &&glDto.getSplitNum()!=0 && glDto.getSplitGoodsId()!=null){
			String sql="call splitDeliverGoods("+glDto.getGoodsLogId()+","+glDto.getSplitGoodsId()+","+glDto.getSplitNum()+")";
		  return Integer.valueOf(executeProcedureOne(sql).get("result").toString());
			}else{
			return 0;
			}
		}catch (RuntimeException e){
			logger.debug("dao将一个发货通知单拆为两个联单失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao将一个发货通知单拆为两个联单失败",e);
		}
       		
	}

	@Override
	public List<Map<String, Object>> getGoodsGraftingLog(String goodsId)
			throws OAException {
		String sql="select a.*,b.code ladingCode1,h.code nextLadingCode,g.name ladingClientName,c.name clientName,(case when a.deliverType=1 then (select code from t_pcs_truck where id=a.vehicleShipId) else (SELECT sr.refName FROM t_pcs_ship_ref sr WHERE sr.id=a.vehicleShipId) end) carshipCode from t_pcs_goodslog a " +
				"LEFT JOIN t_pcs_lading b on b.id=a.ladingId " +
				"LEFT JOIN t_pcs_client c on c.id=a.clientId "+
				"LEFT JOIN t_pcs_client g on g.id=a.ladingClientId " +
				"LEFT JOIN t_pcs_lading h on h.id=a.nextLadingId " +
				"where (a.type=9 or a.type=5 or (a.type=3 and a.goodsChange<0)) and a.goodsId="+goodsId+" order by a.createTime ASC";
		
		
		return executeQuery(sql);
	}

	@Override
	public List<Map<String, Object>> getPrintList(GoodsLogDto invoiceDto)
			throws OAException {
		try{
			String sql="SELECT "
		+" a.serial serial,f.name clientName,h.code cargoCode,m.name ladingClientName,a.ladingEvidence,c.name productName, "
		+" round(a.deliverNum,3) deliverNum,a.tankName,CONCAT(s.`name`,'/',t.refName,'/',DATE_FORMAT(n.arrivalStartTime,'%Y-%m-%d')) shipInfo, "
		+" (CASE WHEN a.deliverType = 1 THEN p.code ELSE CONCAT(x.name,'/',o.refName) END) vsName,h.customLading,FROM_UNIXTIME(h.customLadingTime,'%Y-%m-%d') customLadingTime,"
		+ "(CASE WHEN !ISNULL(b.rootGoodsId) and b.rootGoodsId!=0 THEN u.code WHEN ISNULL(r.code) THEN h.code ELSE r.code END ) yuanhao,"
		+" e.name createUserName ,FROM_UNIXTIME(a.invoiceTime) invoiceTime ,r.code diaohao,(CASE WHEN a.deliverType = 1 THEN p.loadCapacity ELSE x.loadCapacity END) loadCapacity,a.storageInfo,a.remark "
		+" FROM	t_pcs_goodslog a "
		+" LEFT JOIN t_pcs_goods b ON a.goodsId = b.id "
		+" LEFT JOIN t_pcs_product c ON b.productId = c.id "
		+" LEFT JOIN t_auth_user e ON a.createUserId = e.id "
		+" LEFT JOIN t_pcs_client f ON f.id = b.clientId "
		+" LEFT JOIN t_pcs_cargo h ON h.id = b.cargoId "
		+" LEFT JOIN t_pcs_client m ON m.id = a.ladingClientId "
		+" LEFT JOIN t_pcs_arrival n ON n.id = h.arrivalId "
		+" LEFT JOIN t_pcs_ship s on s.id=n.shipId "
		+" LEFT JOIN t_pcs_ship_ref t on t.id=n.shipRefId "
		+" LEFT JOIN t_pcs_ship_ref o ON o.id = a.vehicleShipId "
		+" LEFT JOIN t_pcs_ship x ON x.id=o.shipId "
		+" LEFT JOIN t_pcs_truck p ON p.id = a.vehicleShipId "
		+" LEFT JOIN t_pcs_lading r ON r.id = a.ladingId "
		+" LEFT JOIN t_pcs_goods v on v.id=b.rootGoodsId "
		+ " LEFT JOIN t_pcs_goods_group w on t.id=v.goodsGroupId "
		+ " LEFT JOIN t_pcs_lading u on u.id=w.ladingId "
		+" WHERE a.type = 5  and a.deliverType="+invoiceDto.getDeliverType();
		if(Integer.valueOf(invoiceDto.getPrintType())==0){
			sql+=" and a.serial='"+invoiceDto.getSerial()+"'";
		}else if(Integer.valueOf(invoiceDto.getPrintType())==1){
			sql+=" and a.batchId=(select batchId from t_pcs_goodslog where serial='"+invoiceDto.getSerial()+"' and type=5 limit 0,1) ";
		}
		sql+=" and a.batchId!=0  ORDER BY a.id ASC ";
			return executeQuery(sql);
			
		}catch (RuntimeException e){
			logger.debug("dao获取开票打印信息失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao获取开票打印信息失败",e);
		}
	}

	@Override
	public void addFlowMeter(String serial, Integer batchId,Integer isLianDan) throws OAException {
		try{
              if(!Common.isNull(serial)&&!Common.isNull(batchId)){
            	  String sql="call addWeighAboutFlowMeter("+serial+","+batchId+","+isLianDan+")";
            	  executeProcedureOne(sql);
              }			
		}catch(RuntimeException e){
			logger.debug("dao油品同步流量计失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao油品同步流量计失败",e);
		}
		
		
		
		
	}

	@Override
	public void updateGoodsInOut(String goodsTotal, Integer id)
			throws OAException {

			String sql=" update t_pcs_goodslog a set a.goodsChange ="+goodsTotal+" ,goodsSave="+goodsTotal+" where a.goodsId="+id+" and a.type=2 and a.goodsChange>0";
			
			String sql1="update t_pcs_goodslog a set a.goodsChange=-"+goodsTotal+" where a.nextGoodsId="+id+" and a.type=3 and a.goodsChange<0";
			
			String sql2="call repairLog("+id+")";
			
			String sql3="call repairLog((select sourceGoodsId from t_pcs_goods where id="+id+"))";
			
			
			executeUpdate(sql);
			executeUpdate(sql1);
			executeProcedure(sql2);
			executeProcedure(sql3);
			
	}



	@Override
	public List<Map<String, Object>> getSurplus(int ShowVirTime,int goodsId, Long startTime, Long endTime) throws OAException {
		String time="createTime";
		if(ShowVirTime==2){
			time="originalTime";
		}
		String sql="";
		if(!Common.isNull(endTime)&&!Common.isNull(goodsId))
		{
			sql=" select COALESCE(SUM(a.goodsChange),0) as goodsSurplus,(select ABS(SUM(d.goodsChange)) from t_pcs_goodslog d where d.goodsId="+goodsId+" and d.type=10 and d.deliverNo=1 and d."+time+"<"+endTime+" ) as goodsLoss,(select b.goodsSave from t_pcs_goodslog b where b.goodsId="+goodsId+" and  b."+time+"<"+endTime+" order by b."+time+" DESC limit 0,1 ) as  goodsSave from t_pcs_goodslog a ,t_pcs_goods b where a.goodsId=b.id and b.`status`<>1 and a.type<>8 AND if(a.type=5 and a.actualType=0 ,0,1)=1 "; 
			
		}else{
			
			sql=" select COALESCE(SUM(a.goodsChange),0) as goodsSurplus from t_pcs_goodslog a ,t_pcs_goods b where a.goodsId=b.id and b.`status`<>1 and a.type<>8 AND if(a.type=5 and a.actualType=0 ,0,1)=1 "; 
		}
		
		
		
		if(!Common.isNull(goodsId))
		{
			sql+=" and b.id="+goodsId;
		}
		if(!Common.isNull(startTime))
		{
			sql+=" and a."+time+">="+startTime;
		}
		if(!Common.isNull(endTime))
		{
			sql+=" and a."+time+"<"+endTime;
		}
		
		return executeQuery(sql);
	}



	@Override
	public void syncGoodsLogGoodsSave(GoodsLogDto goodsLogDto) throws OAException {
		try{
			
			String sql ="call syncOutBoundGoodsLog("+goodsLogDto.getSerial()+","+goodsLogDto.getChoiceTime()+")";
			
			executeProcedure(sql);
			
		}catch(RuntimeException e){
			logger.debug("dao失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao油品同步流量计失败",e);
		}
	}



	@Override
	public void syncGoodsLogOutTime(GoodsLog goodsLog) throws OAException {
try{
			
			String sql ="update t_pcs_goodslog set createTime="+goodsLog.getCreateTime()+",originalTime="+goodsLog.getCreateTime()+"  where serial='"+goodsLog.getSerial()+"' and type=5";
			
			executeUpdate(sql);
			
		}catch(RuntimeException e){
			logger.debug("dao失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao油品同步流量计失败",e);
		}
	}



	@Override
	public void rebackGoodsLog(GoodsLog goodsLog) throws OAException {
             try{
			
			String sql ="update t_pcs_goodslog set afUpNum=null,afDiffNum=null where id="+goodsLog.getId();
			
			executeUpdate(sql);
			
		}catch(RuntimeException e){
			logger.debug("dao失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao油品同步流量计失败",e);
		}
	}



	@Override
	public List<Map<String, Object>> invoiceQuery(GoodsLogDto invoiceDto,int start,int limit) throws OAException {
		 try{
			StringBuilder sql=new StringBuilder();	
			 
			sql.append(" SELECT a.id id, a.serial serial, (CASE WHEN a.deliverType = 1 THEN ")
				.append(" ( SELECT name FROM t_pcs_truck WHERE id = a.vehicleShipId ) ELSE ( SELECT sr.refName FROM ")
				.append(" t_pcs_ship_ref sr WHERE sr.id = a.vehicleShipId ) END ) vsName, FROM_UNIXTIME(a.invoiceTime) invoiceTime, ")
				.append(" c.`name` productName,f.`name` clientName,m.`name` ladingClientName, ")
				.append(" h.id cargoId, h.`code` cargoCode,b.id goodsId ,b.`code` goodsCode, ")
				.append(" ( CASE WHEN ! ISNULL(b.rootGoodsId) AND b.rootGoodsId != 0 THEN ")
				.append(" r.code WHEN ISNULL(d.code) THEN h.code ELSE d.code END ) yuanhao, ")
				.append(" d.code diaohao,a.ladingEvidence ladingEvidence,ROUND(IFNULL(a.deliverNum,0),3) deliverNum,")
				.append(" ROUND(IFNULL(a.actualNum,0),3) actualNum,(CASE WHEN a.deliverType=1 THEN s.inPort ELSE t.name END) inPort, ")
				.append(" FROM_UNIXTIME(a.createTime) createTime,ROUND(IFNULL(s.measureWeigh,0),3) measureWeigh,a.deliverType,a.actualType status ")
				.append(" FROM t_pcs_goodslog a ")
				.append(" LEFT JOIN t_pcs_goods b ON a.goodsId = b.id ")
				.append(" LEFT JOIN t_pcs_product c ON b.productId = c.id ")
				.append(" LEFT JOIN t_pcs_lading d ON a.ladingId = d.id ")
				.append(" LEFT JOIN t_auth_user e ON a.createUserId = e.id ")
				.append(" LEFT JOIN t_pcs_client f ON f.id = b.clientId ")
				.append(" LEFT JOIN t_pcs_cargo h ON h.id = b.cargoId ")
				.append(" LEFT JOIN t_pcs_goods i ON b.sourceGoodsId = i.id ")
				.append(" LEFT JOIN t_pcs_client j ON j.id = i.clientId ")
				.append(" LEFT JOIN t_pcs_goods k ON b.rootGoodsId = k.id ")
				.append(" LEFT JOIN t_pcs_client l ON l.id = k.clientId ")
				.append(" LEFT JOIN t_pcs_client m ON m.id = a.ladingClientId ")
				.append(" LEFT JOIN t_pcs_arrival n ON n.id = a.batchId ")
				.append(" LEFT JOIN t_pcs_ship_ref o ON o.id = a.vehicleShipId ")
				.append(" LEFT JOIN t_pcs_truck p ON p.id = a.vehicleShipId ")
				.append(" LEFT JOIN t_pcs_goods_group q ON q.id = k.goodsGroupId ")
				.append(" LEFT JOIN t_pcs_lading r ON r.id = q.ladingId ")
				.append(" LEFT JOIN t_pcs_weighbridge s on s.serial=a.serial  AND a.deliverType=1 ")
				.append(" LEFT JOIN t_pcs_berth t on t.id=n.berthId ")
				.append(" WHERE a.type=5 ");
			if(!Common.empty(invoiceDto.getProductId())&&invoiceDto.getProductId()!=0)
			{
			sql.append(" and b.productId="+invoiceDto.getProductId() ) ; 
			}
			if(!Common.empty(invoiceDto.getVsName()))
			{
			sql.append("  and ((p.code like '%"+invoiceDto.getVsName()+"%')or( o.refName like '%"+invoiceDto.getVsName()+"%'))" ) ; 
			}	
			if(!Common.isNull(invoiceDto.getCargoCode())){
				sql.append(" and h.code like '%"+invoiceDto.getCargoCode()+"%'");
			}
			
			if(!Common.empty(invoiceDto.getSerial()))
			{
			sql.append("  and a.serial like '%"+invoiceDto.getSerial()+"%'" ) ; 
			}
			if(!Common.empty(invoiceDto.getDeliverType())&&invoiceDto.getDeliverType()!=0)
			{
			sql.append("  and a.deliverType="+invoiceDto.getDeliverType() ) ; 
			}
			if(!Common.empty(invoiceDto.getLadingEvidence())){
				sql.append(" and a.ladingEvidence ='"+invoiceDto.getLadingEvidence()+"'");
			}
			if(!Common.isNull(invoiceDto.getTimeType())&&invoiceDto.getTimeType()==1){
			if(invoiceDto.getStartTime()!=null&&invoiceDto.getStartTime()!=-1){
				sql.append(" and a.invoiceTime >=").append(invoiceDto.getStartTime());
			}
			if(invoiceDto.getEndTime()!=null&&invoiceDto.getEndTime()!=-1){
				sql.append(" and a.invoiceTime<=").append(invoiceDto.getEndTime());
			}
			}else{
				if(invoiceDto.getStartTime()!=null&&invoiceDto.getStartTime()!=-1){
					sql.append(" and a.createTime >=").append(invoiceDto.getStartTime());
				}
				if(invoiceDto.getEndTime()!=null&&invoiceDto.getEndTime()!=-1){
					sql.append(" and a.createTime<=").append(invoiceDto.getEndTime());
				}
			}
			if(invoiceDto.getStatus()!=null&&invoiceDto.getStatus()!=-1){
				sql.append(" and a.actualType=").append(invoiceDto.getStatus());
			}
			sql.append(" order by a.id desc limit ").append(start).append(",").append(limit);
				return executeQuery(sql.toString());
			}catch(RuntimeException e){
				logger.debug("dao失败");
				throw new OAException(Constant.SYS_CODE_DB_ERR,"dao油品同步流量计失败",e);
			}
	}



	@Override
	public int invoiceQueryCount(GoodsLogDto invoiceDto) throws OAException {
		 try{
			 StringBuilder sql=new StringBuilder(); 
			 sql.append(" SELECT count(1) ");
				sql.append(" from t_pcs_goodslog a  ");
				if(!Common.empty(invoiceDto.getProductId())&&invoiceDto.getProductId()!=0||!Common.isNull(invoiceDto.getCargoCode()))
				{
				sql.append(" LEFT JOIN t_pcs_goods b on a.goodsId=b.id ");
				}
				if(!Common.empty(invoiceDto.getVsName()))
				{
				sql.append(" LEFT JOIN t_pcs_ship_ref o on o.id=a.vehicleShipId ");
				
				sql.append(" LEFT JOIN t_pcs_truck p on p.id=a.vehicleShipId ");
				}
				if(!Common.isNull(invoiceDto.getCargoCode())){
					sql.append(" LEFT JOIN t_pcs_cargo h ON h.id = b.cargoId ");
				}
				
				
				sql.append(" WHERE a.type=5 ");
				if(!Common.empty(invoiceDto.getProductId())&&invoiceDto.getProductId()!=0)
				{
				sql.append(" and b.productId="+invoiceDto.getProductId() ) ; 
				}
				if(!Common.empty(invoiceDto.getDeliverType())&&invoiceDto.getDeliverType()!=0)
				{
				sql.append("  and a.deliverType="+invoiceDto.getDeliverType() ) ; 
				}
				if(!Common.empty(invoiceDto.getVsName()))
				{
				sql.append("  and ((p.code like '%"+invoiceDto.getVsName()+"%')or( o.refName like '%"+invoiceDto.getVsName()+"%'))" ) ; 
				}	
				if(!Common.empty(invoiceDto.getSerial()))
				{
				sql.append("  and a.serial like '%"+invoiceDto.getSerial()+"%'" ) ; 
				}
				if(!Common.isNull(invoiceDto.getCargoCode())){
					sql.append(" and h.code like '%"+invoiceDto.getCargoCode()+"%'");
				}
				if(!Common.empty(invoiceDto.getLadingEvidence())){
					sql.append(" and a.ladingEvidence ='"+invoiceDto.getLadingEvidence()+"'");
				}
				if(!Common.isNull(invoiceDto.getTimeType())&&invoiceDto.getTimeType()==1){
					if(invoiceDto.getStartTime()!=null&&invoiceDto.getStartTime()!=-1){
						sql.append(" and a.invoiceTime >=").append(invoiceDto.getStartTime());
					}
					if(invoiceDto.getEndTime()!=null&&invoiceDto.getEndTime()!=-1){
						sql.append(" and a.invoiceTime<=").append(invoiceDto.getEndTime());
					}
					}else{
						if(invoiceDto.getStartTime()!=null&&invoiceDto.getStartTime()!=-1){
							sql.append(" and a.createTime >=").append(invoiceDto.getStartTime());
						}
						if(invoiceDto.getEndTime()!=null&&invoiceDto.getEndTime()!=-1){
							sql.append(" and a.createTime<=").append(invoiceDto.getEndTime());
						}
					}
				if(invoiceDto.getStatus()!=null&&invoiceDto.getStatus()!=-1){
					sql.append(" and a.actualType=").append(invoiceDto.getStatus());
				}
				return (int) getCount(sql.toString());
			}catch(RuntimeException e){
				logger.debug("dao失败");
				throw new OAException(Constant.SYS_CODE_DB_ERR,"dao油品同步流量计失败",e);
			}
	}



	@Override
	public void deleteGoodsLogByNextGoodsId(String nextGoodsId)throws OAException {
		try
		{
			String sql="delete from t_pcs_goodslog where nextGoodsId in ("+nextGoodsId+")";
			execute(sql);
		} 
		catch (RuntimeException e) 
		{
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao删除失败", e);
		}	
	}



	@Override
	public Map<String, Object> getTotalNum(GoodsLogDto invoiceDto) throws OAException {
		 try{
				StringBuilder sql=new StringBuilder();	
				 
				sql.append(" SELECT SUM(ROUND(a.deliverNum,3)) deliverNum,SUM(ROUND(a.actualNum,3)) totalNum,COUNT(DISTINCT a.batchId) countNum,SUM(ROUND(IFNULL(s.measureWeigh,0),3)) measureWeigh ")
					.append(" FROM t_pcs_goodslog a ")
					.append(" LEFT JOIN t_pcs_goods b ON a.goodsId = b.id ")
					.append(" LEFT JOIN t_pcs_cargo h ON h.id = b.cargoId ")
					.append(" LEFT JOIN t_pcs_ship_ref o ON o.id = a.vehicleShipId ")
					.append(" LEFT JOIN t_pcs_truck p ON p.id = a.vehicleShipId ")
					.append(" LEFT JOIN t_pcs_weighbridge s on s.serial=a.serial  AND a.deliverType=1")
					.append(" WHERE a.type=5 ");
				if(!Common.empty(invoiceDto.getProductId())&&invoiceDto.getProductId()!=0)
				{
				sql.append(" and b.productId="+invoiceDto.getProductId() ) ; 
				}
				if(!Common.empty(invoiceDto.getVsName()))
				{
				sql.append("  and ((p.code like '%"+invoiceDto.getVsName()+"%')or( o.refName like '%"+invoiceDto.getVsName()+"%'))" ) ; 
				}	
				if(!Common.empty(invoiceDto.getSerial()))
				{
				sql.append("  and a.serial like '%"+invoiceDto.getSerial()+"%'" ) ; 
				}
				if(!Common.isNull(invoiceDto.getCargoCode())){
					sql.append(" and h.code like '%"+invoiceDto.getCargoCode()+"%'");
				}
				if(!Common.empty(invoiceDto.getDeliverType())&&invoiceDto.getDeliverType()!=0)
				{
				sql.append("  and a.deliverType="+invoiceDto.getDeliverType() ) ; 
				}
				if(!Common.empty(invoiceDto.getLadingEvidence())){
					sql.append(" and a.ladingEvidence ='"+invoiceDto.getLadingEvidence()+"'");
				}
				if(!Common.isNull(invoiceDto.getTimeType())&&invoiceDto.getTimeType()==1){
					if(invoiceDto.getStartTime()!=null&&invoiceDto.getStartTime()!=-1){
						sql.append(" and a.invoiceTime >=").append(invoiceDto.getStartTime());
					}
					if(invoiceDto.getEndTime()!=null&&invoiceDto.getEndTime()!=-1){
						sql.append(" and a.invoiceTime<=").append(invoiceDto.getEndTime());
					}
					}else{
						if(invoiceDto.getStartTime()!=null&&invoiceDto.getStartTime()!=-1){
							sql.append(" and a.createTime >=").append(invoiceDto.getStartTime());
						}
						if(invoiceDto.getEndTime()!=null&&invoiceDto.getEndTime()!=-1){
							sql.append(" and a.createTime<=").append(invoiceDto.getEndTime());
						}
					}
				if(invoiceDto.getStatus()!=null&&invoiceDto.getStatus()!=-1){
					sql.append(" and a.actualType=").append(invoiceDto.getStatus());
				}
					return executeQueryOne(sql.toString());
				}catch(RuntimeException e){
					logger.debug("dao失败");
					throw new OAException(Constant.SYS_CODE_DB_ERR,"dao油品同步流量计失败",e);
				}
	}



	/**
	 * @Title getGoodsLogSerial
	 * @Descrption:TODO
	 * @param:@param goodslogId
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年5月6日上午10:49:33
	 * @throws
	 */
	@Override
	public Map<String, Object> getGoodsLogSerial(Integer goodslogId) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append(" select serial,deliverType from t_pcs_goodslog where id=").append(goodslogId);
            return executeQueryOne(sql.toString());			
	}catch(RuntimeException e){
		logger.debug("dao失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR,"dao油品同步流量计失败",e);
	}
	}



	/**
	 * @Title insertDeliverGoods
	 * @Descrption:TODO
	 * @param:@param goodsLog
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年5月7日下午3:56:55
	 * @throws
	 */
	@Override
	public Map<String, Object> insertDeliverGoods(GoodsLog goodsLog) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append(" call proc_goodslogByDeliverGoodsInsert(").append(goodsLog.getBatchId()).append(",")
			.append(goodsLog.getClientId()).append(",")
			.append(goodsLog.getCreateTime()).append(",'").append(goodsLog.getDeliverNo()).append("',")
			.append(goodsLog.getDeliverNum()).append(",").append(goodsLog.getDeliverType()).append(",")
			.append(goodsLog.getGoodsChange()).append(",").append(goodsLog.getGoodsId()).append(",")
			.append(goodsLog.getGoodsSave()).append(",").append(goodsLog.getLadingClientId()).append(",'")
			.append(goodsLog.getLadingEvidence()).append("',").append(goodsLog.getLadingId()).append(",'")
			.append(goodsLog.getSerial()).append("','").append(goodsLog.getStorageInfo()).append("',")
			.append(goodsLog.getSurplus()).append(",").append(goodsLog.getType()).append(",")
			.append(goodsLog.getVehicleShipId()).append(",").append(goodsLog.getCreateUserId()).append(",")
			.append(goodsLog.getLadingType()).append(",'").append(goodsLog.getTankName()).append("',")
			.append(goodsLog.getActualType()).append(",").append(goodsLog.getInvoiceTime()).append(",")
			.append(goodsLog.getIsCleanInvoice()).append(",'").append(goodsLog.getRemark()).append("')");
			return executeProcedureOne(sql.toString());
	}catch(RuntimeException e){
		logger.debug("dao添加流量计信息失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR,"dao添加流量计信息失败",e);
	}
	}



	/**
	 * @Title getFlowMeterList
	 * @Descrption:TODO
	 * @param:@param serial
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年5月9日上午8:28:48
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> getFlowMeterList(String serial) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append(" SELECT min(a.serial) serial,ROUND(SUM(a.deliverNum),3) deliverNum,c.oils oilType,f.`key` tankName, ")
			.append(" c.`code` productCode,min(a.createTime) createTime  ")
			.append(" from t_pcs_goodslog a  ")
			.append(" LEFT JOIN t_pcs_goods d on d.id=a.goodsId ")
			.append(" LEFT JOIN t_pcs_product c on c.id=d.productId ")
			.append(" LEFT JOIN t_pcs_tank f on f.`name`=a.tankName, ")
			.append(" (SELECT b.batchId batchId  FROM t_pcs_goodslog b  ")
			.append(" where b.serial=").append(serial).append(" and b.deliverType=1 and b.type=5 ) e ")
			.append(" WHERE a.batchId=e.batchId    ")
			.append(" and a.type=5 and a.deliverType=1 GROUP BY a.storageInfo ");
			return executeQuery(sql.toString());
	}catch(RuntimeException e){
		logger.debug("dao添加流量计信息失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR,"dao添加流量计信息失败",e);
	}
	}

	/**
	 * @Title getFlowMeterSerial
	 * @Descrption:TODO
	 * @param:@param serial
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年5月9日上午8:28:48
	 * @throws
	 */
	@Override
	public  Map<String, Object>  getFlowMeterSerial(int id) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append(" SELECT min(a.serial) serial from t_pcs_goodslog a , ")
			.append(" (SELECT b.batchId ,b.storageInfo  FROM t_pcs_goodslog b  ")
			.append(" where b.id=").append(id).append(" and b.deliverType=1 and b.type=5 ) e ")
			.append(" WHERE a.batchId=e.batchId    ")
			.append(" and a.type=5 and a.deliverType=1 and a.storageInfo = e.storageInfo ");
			return executeQueryOne(sql.toString());
	}catch(RuntimeException e){
		logger.debug("dao添加流量计信息失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR,"dao添加流量计信息失败",e);
	}
	}

	/**
	 * @Title syncFlowMeter
	 * @Descrption:TODO
	 * @param:@param serial
	 * @param:@param measureWeigh
	 * @param:@param inPort
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年5月9日上午10:04:36
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> syncFlowMeter(String serial, Double measureWeigh, Integer inPort) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append(" call proc_weighBridgeUpdate('").append(serial).append("',").append(measureWeigh).append(",'A").append((String.format("%02d",inPort))).append("')");
			logger.debug(sql.toString());
			return executeProcedure(sql.toString());
		}catch(RuntimeException e){
			logger.debug("dao添加流量计信息失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao添加流量计信息失败",e);
		}
	}



	/**
	 * @Title getFlowMeterBySerial
	 * @Descrption:TODO
	 * @param:@param serial
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年5月25日上午9:22:02
	 * @throws
	 */
	@Override
	public Map<String, Object> getFlowMeterBySerial(String serial) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append(" SELECT min(a.serial) serial,ROUND(SUM(a.deliverNum),3) deliverNum,c.oils oilType,f.`key` tankName, ")
			.append(" c.`code` productCode,min(a.createTime) createTime  ")
			.append(" from t_pcs_goodslog a  ")
			.append(" LEFT JOIN t_pcs_goods d on d.id=a.goodsId ")
			.append(" LEFT JOIN t_pcs_product c on c.id=d.productId ")
			.append(" LEFT JOIN t_pcs_tank f on f.`name`=a.tankName, ")
			.append(" (SELECT b.batchId batchId,b.storageInfo  FROM t_pcs_goodslog b  ")
			.append(" where b.serial=").append(serial).append(" and b.deliverType=1 and b.type=5 ) e ")
			.append(" WHERE a.batchId=e.batchId and e.storageInfo =a.storageInfo  ")
			.append(" and a.type=5 and a.deliverType=1  ");
			return executeQueryOne(sql.toString());
	}catch(RuntimeException e){
		logger.debug("dao添加流量计信息失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR,"dao添加流量计信息失败",e);
	}
	}



	/**
	 * @Title searchLading
	 * @Descrption:TODO
	 * @param:@param invoiceDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年6月13日上午9:38:52
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> searchLading(GoodsLogDto invoiceDto) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			
		sql.append(" SELECT  a.id ,a.code goodsCode,b.code cargoCode,b.goodsTotal cargoTotal,d.code tankCode,a.sourceGoodsId,a.rootGoodsId, ")
		.append(" (CASE WHEN !ISNULL(a.rootGoodsId) and a.rootGoodsId!=0 THEN g.code WHEN ISNULL(i.code) THEN b.code ELSE i.code END ) yuanhao, ")
		.append(" i.code diaohao,round((a.goodsTotal-a.goodsOutPass),3) fengliang,")
		.append(" round((a.goodsCurrent-(a.goodsTotal-a.goodsOutPass)),3) goodsCurrent, ")
		.append(" a.goodsTotal goodsTotal,a.goodsInPass goodsInPass,i.id ladingId,")
		.append(" i.type ladingType,DATE_FORMAT(i.endTime,'%Y-%m-%d') endTime,DATE_FORMAT(i.startTime,'%Y-%m-%d') startTime,k.period,UNIX_TIMESTAMP(j.arrivalStartTime) tArrivalStartTime,a.goodsCurrent currentNum ")
		.append(" from t_pcs_goods a ")
		.append(" LEFT JOIN t_pcs_cargo b on a.cargoId=b.id ")
		.append(" LEFT JOIN t_pcs_client c on c.id=b.clientId ")
		.append(" LEFT JOIN t_pcs_tank d on d.id=a.tankId ")
		.append(" LEFT JOIN t_pcs_goods e on e.id=a.rootGoodsId ")
		.append(" LEFT JOIN t_pcs_goods_group f on f.id=e.goodsGroupId ")
		.append(" LEFT JOIN t_pcs_lading g on g.id=f.ladingId ")
		.append(" LEFT JOIN t_pcs_goods_group h on h.id=a.goodsGroupId ")
		.append(" LEFT JOIN t_pcs_lading i on i.id=h.ladingId  ")
		.append(" LEFT JOIN t_pcs_arrival j on j.id=b.arrivalId")
		.append(" LEFT JOIN t_pcs_contract k on k.id=b.contractId")
		.append(" where 1=1 ");
		if(!Common.empty(invoiceDto.getClientId()))
		{
			sql.append("  and ((a.clientId=").append(invoiceDto.getClientId())
			.append(" and (ISNULL(a.ladingClientId) or a.ladingClientId=0)) or a.ladingClientId=")
			.append(invoiceDto.getClientId()).append(")"); 
		}
		if(!Common.empty(invoiceDto.getProductId()))
		{
			sql.append(" and a.productId=").append(invoiceDto.getProductId()); 
		}
		if(invoiceDto.getZeroFlag()!=1)
		{
			sql.append(" and round((a.goodsCurrent-(a.goodsTotal-a.goodsOutPass)),3)>0");
		}
		sql.append(" order by b.code,a.code asc ");
		return executeQuery(sql.toString());
		}catch(RuntimeException e){
			logger.debug("dao查询提单信息失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询提单信息失败",e);
		}
	}

	/**
	 * @Title validateGoodsNum
	 * @Descrption:TODO
	 * @param:@param goodsLog
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2017年1月6日上午10:54:25
	 * @throws
	 */
	@Override
	public Map<String, Object> getGoodsCurrent(int goodsId) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append("select round((a.goodsCurrent-(a.goodsTotal-a.goodsOutPass)),3) goodsCurrent from t_pcs_goods a where a.id=").append(goodsId);
			return executeQueryOne(sql.toString());
		}catch(RuntimeException e){
			logger.debug("dao校验货体数量失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao校验货体数量失败",e);
		}
	}



	/**
	 * @throws OAException 
	 * @Title checkIsChangeDeliveryNum
	 * @Descrption:TODO
	 * @param:@param goodsLog
	 * @param:@return
	 * @auhor jiahy
	 * @date 2017年9月21日下午4:19:34
	 * @throws
	 */
	@Override
	public Map<String, Object> checkIsChangeDeliveryNum(GoodsLog goodsLog) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append("select IF(a.deliverNum-").append(goodsLog.getDeliverNum())
			.append(" = 0,0,1) isChange from t_pcs_goodslog a where a.id=").append(goodsLog.getId());
			return executeQueryOne(sql.toString());
		}catch(RuntimeException e){
			logger.debug("dao校验是否修改开票量失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao校验是否修改开票量失败",e);
		}
	}




   
	
}