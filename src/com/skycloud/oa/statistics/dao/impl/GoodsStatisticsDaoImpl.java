package com.skycloud.oa.statistics.dao.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.outbound.model.EveryDayStatics;
import com.skycloud.oa.statistics.dao.GoodsStatisticsDao;
import com.skycloud.oa.statistics.dto.StatisticsDto;
import com.skycloud.oa.utils.Common;

@Repository
public class GoodsStatisticsDaoImpl extends BaseDaoImpl implements
		GoodsStatisticsDao
{

	private static Logger LOG = Logger.getLogger(GoodsStatisticsDaoImpl.class);

	@Override
	public List<Map<String, Object>> get(StatisticsDto sDto, int start,
			int limit) throws OAException
	{
		try {
			
			
			String time="createTime";
			if(!Common.isNull(sDto.getShowVirTime())){
				if(sDto.getShowVirTime()==2){
					time="originalTime";
				}
			}
			
			
			StringBuffer sql = new StringBuffer();
			sql.append("select a.connectNo confirmNo,a.No planNo,a.inboundNo,a.goodsInspect,g.name productName,a.id cargoId,a.clientId,a.storageType,e.name clientName,f.name shipName,");
			sql.append("a.code ,d.shipId,d.arrivalStartTime,a.isInspect,gggt.goodsTotal,a.goodsTank,");
			

			sql.append("tab.goodsOld,tab.goodsSend,tgs.goodsSave");
			sql.append("  from t_pcs_cargo a ");
//			sql.append(" LEFT JOIN t_pcs_arrival_plan p on p.arrivalId=a.arrivalId and p.cargoId=a.id ");
			
			sql.append(" LEFT JOIN t_pcs_arrival d on a.arrivalId=d.id ");
			sql.append(" LEFT JOIN t_pcs_client e on e.id=a.clientId ");
			sql.append(" LEFT JOIN t_pcs_ship f on f.id=d.shipId ");
//			sql.append(" LEFT JOIN t_pcs_work z on z.arrivalId=d.id and z.`status`>=9 ");
			
			sql.append(" INNER JOIN t_pcs_product g on g.id=a.productId ");

			sql.append(" LEFT OUTER JOIN (select round(sum(tttt.goodsSave),3) goodsSave ,tttt.cargoId from (SELECT max(id),goodsSave,cargoId FROM ( SELECT gl.id,gl.goodsSave, g.cargoId,gl.goodsId FROM t_pcs_goods g, t_pcs_goodslog gl ");
			sql.append("WHERE g.id = gl.goodsId AND g.`status` <> 1 and gl."+time+"< "+(sDto.getEndTime() + 86400)+"  ORDER BY gl.id DESC) t GROUP BY t.cargoId,t.goodsId) tttt group by tttt.cargoId) tgs on tgs.cargoId = a.id ");
			
			sql.append(" LEFT OUTER JOIN (SELECT ot.goodsSend,tt.goodsOld,ot.cargoId FROM ");
			sql.append("(SELECT COALESCE(SUM(gl.actualNum),0) AS goodsSend,g.cargoId FROM t_pcs_goods g,t_pcs_goodslog gl ");
			sql.append("WHERE g.id = gl.goodsId AND g.`status` <> 1 AND gl.type = 5 AND gl."+time+" < ");
			sql.append(sDto.getEndTime() + 86400);
			sql.append(" GROUP BY g.cargoId) ot LEFT OUTER JOIN (SELECT COALESCE(SUM(gl.actualNum),0) AS goodsOld,g.cargoId ");
			sql.append("FROM t_pcs_goods g,t_pcs_goodslog gl WHERE g.id = gl.goodsId AND g.`status` <> 1 AND gl.type = 5 AND ");
			sql.append("gl."+time+" < ");
			sql.append(sDto.getStartTime());
			sql.append("  GROUP BY g.cargoId) tt ON ot.cargoId = tt.cargoId) tab on tab.cargoId = a.id");
//			sql.append(" LEFT OUTER JOIN ( select sum(a1.goodsChange) goodsTotal,b.cargoId from t_pcs_goodslog a1 ,t_pcs_goods b where a1.goodsId=b.id and (a1.type=1 or a1.type=4) and a1.createTime<"+sDto.getStartTime() + 86400+" group by b.cargoId) gggt on gggt.cargoId=a.id");
			sql.append(" LEFT OUTER JOIN ( select sum(a1.goodsChange) goodsTotal,b.cargoId from t_pcs_goodslog a1 ,t_pcs_goods b where a1.goodsId=b.id and (a1.type=1 or a1.type=4 or a1.type=10 or a1.type=7) and a1."+time+"<");
			sql.append(sDto.getEndTime() + 86400);
			sql.append(" group by b.cargoId) gggt on gggt.cargoId=a.id");
			
			sql.append("  where 1=1  and (!isnull(a.type) or a.isPredict=1) ");
//			sql.append(" (a.goodsTotal<>(select sum(g.actualNum) from t_pcs_goodslog g LEFT JOIN t_pcs_goods h");
//			sql.append(" on h.id=g.goodsId LEFT JOIN t_pcs_cargo i on h.cargoId=i.id where i.id=a.id and g.createTime<");
//			sql.append(sDto.getStartTime() + 86400);
//			sql.append(" and g.type=5) or (select sum(g.actualNum) from t_pcs_goodslog g LEFT JOIN t_pcs_goods h on ");
//			sql.append("h.id=g.goodsId LEFT JOIN t_pcs_cargo i on h.cargoId=i.id where i.id=a.id and g.createTime<");
//			sql.append(sDto.getStartTime() + 86400);
//			sql.append(" and g.type=5) is null ) ");
			if (!Common.isNull(sDto.getProductId())) {
				sql.append(" and a.productId=" + sDto.getProductId());
			}
			if (!Common.isNull(sDto.getClientId())) {
				sql.append(" and a.clientId=" + sDto.getClientId());
			}
//			if (!Common.isNull(sDto.getStartTime())) {
//				sql.append(" and (select createTime from t_pcs_goods where cargoId=a.id limit 0,1)<'"
//						+ (new Timestamp((sDto.getStartTime() + 86400) * 1000))
//						+ "'");
//			}
			
			
			if(!Common.empty(sDto.getIsFinish())){
				//未清盘
				if(sDto.getIsFinish()==0){
					sql.append(" and (round((COALESCE(tab.goodsSend,0)-COALESCE(tab.goodsOld,0)),3)>0 or round((COALESCE(gggt.goodsTotal,0)-COALESCE(tab.goodsSend,0)),3)<>0)");
				}
			}
			
			if(!Common.empty(sDto.getCode())){
				sql.append(" and a.code like '%"+sDto.getCode()+"%' ");
			}
			
			
			if(!Common.empty(sDto.getShowVir())){
				
				if(sDto.getShowVir()==1){
					
					sql.append(" and a.arrivalId <>0 ");
					
				}
				
				if(sDto.getShowVir()==3){
					
					sql.append(" and a.arrivalId =0 ");
					
				}
				
				
			}
			
			sql.append(" order by d.arrivalStartTime DESC ");
			if (limit != 0) {
				sql.append(" limit " + start + "," + limit);
			}
			
			
			
			return executeQuery(sql.toString());
		} catch (RuntimeException e) {
			LOG.error("查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败", e);
		}
	}

	@Override
	public int count(StatisticsDto sDto) throws OAException
	{
		try {
			String time="createTime";
			if(!Common.isNull(sDto.getShowVirTime())){
				if(sDto.getShowVirTime()==2){
					time="originalTime";
				}
			}
			
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*)");
			sql.append("  from t_pcs_cargo a ");
			sql.append(" LEFT JOIN t_pcs_arrival d on a.arrivalId=d.id ");
			sql.append(" LEFT JOIN t_pcs_client e on e.id=a.clientId ");
			sql.append(" LEFT JOIN t_pcs_ship f on f.id=d.shipId ");
//			sql.append(" LEFT JOIN t_pcs_work z on z.arrivalId=d.id and z.`status`>=9 ");
			
			sql.append(" INNER JOIN t_pcs_product g on g.id=a.productId ");

			sql.append(" LEFT OUTER JOIN (SELECT max(id),goodsSave,cargoId FROM ( SELECT gl.id,gl.goodsSave, g.cargoId FROM t_pcs_goods g, t_pcs_goodslog gl ");
			sql.append("WHERE g.id = gl.goodsId AND g.`status` <> 1 and gl."+time+"< "+(sDto.getEndTime() + 86400)+"  ORDER BY gl.id DESC) t GROUP BY t.cargoId) tgs on tgs.cargoId = a.id ");
			
			sql.append(" LEFT OUTER JOIN (SELECT ot.goodsSend,tt.goodsOld,ot.cargoId FROM ");
			sql.append("(SELECT COALESCE(SUM(gl.actualNum),0) AS goodsSend,g.cargoId FROM t_pcs_goods g,t_pcs_goodslog gl ");
			sql.append("WHERE g.id = gl.goodsId AND g.`status` <> 1 AND gl.type = 5 AND gl."+time+" < ");
			sql.append(sDto.getEndTime() + 86400);
			sql.append(" GROUP BY g.cargoId) ot LEFT OUTER JOIN (SELECT COALESCE(SUM(gl.actualNum),0) AS goodsOld,g.cargoId ");
			sql.append("FROM t_pcs_goods g,t_pcs_goodslog gl WHERE g.id = gl.goodsId AND g.`status` <> 1 AND gl.type = 5 AND ");
			sql.append("gl."+time+" < ");
			sql.append(sDto.getStartTime());
			sql.append("  GROUP BY g.cargoId) tt ON ot.cargoId = tt.cargoId) tab on tab.cargoId = a.id");
//			sql.append(" LEFT OUTER JOIN ( select sum(a1.goodsChange) goodsTotal,b.cargoId from t_pcs_goodslog a1 ,t_pcs_goods b where a1.goodsId=b.id and (a1.type=1 or a1.type=4) and a1.createTime<"+sDto.getStartTime() + 86400+" group by b.cargoId) gggt on gggt.cargoId=a.id");
			sql.append(" LEFT OUTER JOIN ( select sum(a1.goodsChange) goodsTotal,b.cargoId from t_pcs_goodslog a1 ,t_pcs_goods b where a1.goodsId=b.id and (a1.type=1 or a1.type=4 or a1.type=10 or a1.type=7) and a1."+time+"<");
			sql.append(sDto.getEndTime() + 86400);
			sql.append(" group by b.cargoId) gggt on gggt.cargoId=a.id");
			sql.append("  where 1=1  and (!isnull(a.type) or a.isPredict=1) ");
//			sql.append("(a.goodsTotal<>(select sum(g.actualNum) from t_pcs_goodslog g LEFT JOIN t_pcs_goods h");
//			sql.append(" on h.id=g.goodsId LEFT JOIN t_pcs_cargo i on h.cargoId=i.id where i.id=a.id and g.createTime<");
//			sql.append(sDto.getStartTime() + 86400);
//			sql.append(" and g.type=5) or (select sum(g.actualNum) from t_pcs_goodslog g LEFT JOIN t_pcs_goods h on ");
//			sql.append("h.id=g.goodsId LEFT JOIN t_pcs_cargo i on h.cargoId=i.id where i.id=a.id and g.createTime<");
//			sql.append(sDto.getStartTime() + 86400);
//			sql.append(" and g.type=5) is null ) ");
			if (!Common.isNull(sDto.getProductId())) {
				sql.append(" and a.productId=" + sDto.getProductId());
			}
			if (!Common.isNull(sDto.getClientId())) {
				sql.append(" and a.clientId=" + sDto.getClientId());
			}
//			if (!Common.isNull(sDto.getStartTime())) {
//				sql.append(" and (select createTime from t_pcs_goods where cargoId=a.id limit 0,1)<'"
//						+ (new Timestamp((sDto.getStartTime() + 86400) * 1000))
//						+ "'");
//			}
			
			if(!Common.empty(sDto.getIsFinish())){
				//未清盘
				if(sDto.getIsFinish()==0){
					sql.append(" and (round((COALESCE(tab.goodsSend,0)-COALESCE(tab.goodsOld,0)),3)>0 or round((COALESCE(gggt.goodsTotal,0)-COALESCE(tab.goodsSend,0)),3)<>0)");
				}
			}
			
			if(!Common.empty(sDto.getShowVir())){
				
				if(sDto.getShowVir()==1){
					
					sql.append(" and a.arrivalId <>0 ");
					
				}
				if(sDto.getShowVir()==3){
					
					sql.append(" and a.arrivalId =0 ");
					
				}
			}
			if(!Common.empty(sDto.getCode())){
				sql.append(" and a.code like '%"+sDto.getCode()+"%' ");
			}
			return (int) getCount(sql.toString());
		
			
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}

	@Override
	public List<Map<String, Object>> getcargoTotal(StatisticsDto sDto)
			throws OAException {
		try {
			
			String time="createTime";
			if(!Common.isNull(sDto.getShowVirTime())){
				if(sDto.getShowVirTime()==2){
					time="originalTime";
				}
			}
			
			StringBuffer sql = new StringBuffer();
			sql.append("select sum(gggt.goodsTotal) goodsTotal,");
			sql.append("sum(tab.goodsSend) goodsSend,sum(tgs.goodsSave) goodsSave,sum(tab.goodsOld) goodsOld ");
			sql.append("  from t_pcs_cargo a ");
			sql.append(" LEFT JOIN t_pcs_arrival d on a.arrivalId=d.id ");
			sql.append(" LEFT JOIN t_pcs_client e on e.id=a.clientId ");
			sql.append(" LEFT JOIN t_pcs_ship f on f.id=d.shipId ");
//			sql.append(" LEFT JOIN t_pcs_work z on z.arrivalId=d.id and z.`status`>=9 ");
			
			sql.append(" INNER JOIN t_pcs_product g on g.id=a.productId ");
//			sql.append(" LEFT OUTER JOIN (select round(sum(b.goodsTotal),3) goodsTotal,b.cargoId from t_pcs_goods b where ISNULL(b.goodsGroupId) group by b.cargoId) gtt on gtt.cargoId=a.id ");

			sql.append(" LEFT OUTER JOIN (SELECT max(id),goodsSave,cargoId FROM ( SELECT gl.id,gl.goodsSave, g.cargoId FROM t_pcs_goods g, t_pcs_goodslog gl ");
			sql.append("WHERE g.id = gl.goodsId AND g.`status` <> 1 and gl."+time+"< "+sDto.getEndTime() + 86400+"  ORDER BY gl.id DESC) t GROUP BY t.cargoId) tgs on tgs.cargoId = a.id ");
			
			sql.append(" LEFT OUTER JOIN (SELECT ot.goodsSend,tt.goodsOld,ot.cargoId FROM ");
			sql.append("(SELECT COALESCE(SUM(gl.actualNum),0) AS goodsSend,g.cargoId FROM t_pcs_goods g,t_pcs_goodslog gl ");
			sql.append("WHERE g.id = gl.goodsId AND g.`status` <> 1 AND gl.type = 5 AND gl."+time+" < ");
			sql.append(sDto.getEndTime() + 86400);
			sql.append(" GROUP BY g.cargoId) ot LEFT OUTER JOIN (SELECT COALESCE(SUM(gl.actualNum),0) AS goodsOld,g.cargoId ");
			sql.append("FROM t_pcs_goods g,t_pcs_goodslog gl WHERE g.id = gl.goodsId AND g.`status` <> 1 AND gl.type = 5 AND ");
			sql.append("gl."+time+" < ");
			sql.append(sDto.getStartTime());
			sql.append("  GROUP BY g.cargoId) tt ON ot.cargoId = tt.cargoId) tab on tab.cargoId = a.id");

			sql.append(" LEFT OUTER JOIN ( select sum(a1.goodsChange) goodsTotal,b.cargoId from t_pcs_goodslog a1 ,t_pcs_goods b where a1.goodsId=b.id and (a1.type=1 or a1.type=4 or a1.type=10 or a1.type=7) and a1."+time+"<");
			sql.append(sDto.getEndTime() + 86400);
			sql.append(" group by b.cargoId) gggt on gggt.cargoId=a.id");
			
			

			sql.append("  where 1=1  and (!isnull(a.type) or a.isPredict=1) ");
//			sql.append(" (a.goodsTotal<>(select sum(g.actualNum) from t_pcs_goodslog g LEFT JOIN t_pcs_goods h");
//			sql.append(" on h.id=g.goodsId LEFT JOIN t_pcs_cargo i on h.cargoId=i.id where i.id=a.id and g.createTime<");
//			sql.append(sDto.getStartTime() + 86400);
//			sql.append(" and g.type=5) or (select sum(g.actualNum) from t_pcs_goodslog g LEFT JOIN t_pcs_goods h on ");
//			sql.append("h.id=g.goodsId LEFT JOIN t_pcs_cargo i on h.cargoId=i.id where i.id=a.id and g.createTime<");
//			sql.append(sDto.getStartTime() + 86400);
//			sql.append(" and g.type=5) is null ) ");
			if (!Common.isNull(sDto.getProductId())) {
				sql.append(" and a.productId=" + sDto.getProductId());
			}
			if (!Common.isNull(sDto.getClientId())) {
				sql.append(" and a.clientId=" + sDto.getClientId());
			}
//			if (!Common.isNull(sDto.getStartTime())) {
//				sql.append(" and (select createTime from t_pcs_goods where cargoId=a.id limit 0,1)<'"
//						+ (new Timestamp((sDto.getStartTime() + 86400) * 1000))
//						+ "'");
//			}
			if(!Common.empty(sDto.getCode())){
				sql.append(" and a.code like '%"+sDto.getCode()+"%' ");
			}
			
			if(!Common.empty(sDto.getShowVir())){
				
				if(sDto.getShowVir()==1){
					
					sql.append(" and a.arrivalId <>0 ");
					
				}
				if(sDto.getShowVir()==3){
					
					sql.append(" and a.arrivalId =0 ");
					
				}
			}
			
			sql.append(" order by d.arrivalStartTime DESC ");
			
			System.out.println(sql);
			
			return executeQuery(sql.toString());
		} catch (RuntimeException e) {
			LOG.error("查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败", e);
		}
	}

	@Override
	public List<Map<String, Object>> getLog(StatisticsDto statisticsDto,
			int start, int limit) throws OAException {
			String sql="";
			String time="createTime";
			if(!Common.isNull(statisticsDto.getShowVirTime())){
				if(statisticsDto.getShowVirTime()==2){
					time="originalTime";
				}
			}
		if(!Common.isNull(statisticsDto.getType())&&statisticsDto.getType()==1){
			String s="";
			String s1="";
			if (!Common.isNull(statisticsDto.getStartTime())) {
				 s=" and z."+time+">"
						+ statisticsDto.getStartTime();
				
			}
			
			if (!Common.isNull(statisticsDto.getEndTime())) {
				 s1=" and z."+time+"<"
						+ (statisticsDto.getEndTime()+86400);
				
			}
				sql="select (select  sum(z.goodsChange) from t_pcs_goodslog z where z.goodsId=a.goodsId " +s+s1+
						"  and (z.type=7 or z.type=1 or z.type=4  or (z.type=10 and ifnull(z.deliverNo,0)<>1) ) ) mGoodsChange,FROM_UNIXTIME(a.createTime) mtime,q.name goodsLadingClientName,p.name ladingInClientName, l.code cargoCode,n.name shipName,i.id goodsId,j.name productName,a.*,b.code ladingCode1,h.code nextLadingCode,g.name ladingClientName,k.name clientName,(case when a.deliverType=1 then (select code from t_pcs_truck where id=a.vehicleShipId) else (SELECT sr.refName FROM t_pcs_ship_ref sr WHERE sr.id=a.vehicleShipId) end) carshipCode from t_pcs_goodslog a " +
						"LEFT JOIN t_pcs_lading b on b.id=a.ladingId " +
						"LEFT JOIN t_pcs_client c on c.id=a.clientId "+
						"LEFT JOIN t_pcs_client g on g.id=a.ladingClientId " +
						"LEFT JOIN t_pcs_lading h on h.id=a.nextLadingId " +
						"LEFT JOIN t_pcs_goods i on i.id=a.goodsId "+
						"LEFT JOIN t_pcs_product j on j.id=i.productId "+
						"LEFT JOIN t_pcs_client k on k.id=i.clientId "+
						"LEFT JOIN t_pcs_cargo l on l.id=i.cargoId "+
						"LEFT JOIN t_pcs_arrival m on m.id=l.arrivalId "+
						"LEFT JOIN t_pcs_ship n on m.shipId=n.id "+
						" LEFT JOIN t_pcs_client p on p.id=b.clientId "+
						"LEFT JOIN t_pcs_client q on q.id=b.receiveClientId " +
						"where (a.type=1 or a.type=7 or a.type=4 or (a.type=10 and ifnull(a.deliverNo,0)<>1) )  and isnull(i.sourceGoodsId) and isnull(i.rootGoodsId) and a.goodsChange<>0 ";
				
				
				
				if (!Common.isNull(statisticsDto.getClientId())&&statisticsDto.getType()!=22) {
					sql+=" and case when isnull(b.id) then i.clientId=" + statisticsDto.getClientId() +" else b.receiveClientId=" + statisticsDto.getClientId() +" end ";
					
				}
				
				if(!Common.isNull(statisticsDto.getGoodsIds())&&statisticsDto.getType()!=22){
					sql+=" and goodsId in ("+statisticsDto.getGoodsIds()+")";
					
					if (!Common.isNull(statisticsDto.getEndTime())) {
						sql+=" and a."+time+"<"
								+ (statisticsDto.getEndTime()+86400);
						
					}
					
					
				}else{
					if(!Common.isNull(statisticsDto.getGoodsIds())){
						sql+=" and goodsId in ("+statisticsDto.getGoodsIds()+")";
					}
						if (!Common.isNull(statisticsDto.getStartTime())) {
							sql+=" and a."+time+">"
									+ statisticsDto.getStartTime();
							
						}
						
						if (!Common.isNull(statisticsDto.getEndTime())) {
							sql+=" and a."+time+"<"
									+ (statisticsDto.getEndTime()+86400);
							
						}
				}

				if(!Common.isNull(statisticsDto.getProductId())){
					sql+= " and j.id="+statisticsDto.getProductId(); 
				}
				
				if(!Common.empty(statisticsDto.getLadingCode())){
					
					if(!Common.isNull(statisticsDto.getType())){
						if(statisticsDto.getType()!=21&&statisticsDto.getType()!=22){
					
					if(statisticsDto.getIsDim()==1){
						sql+=" and (b.code like '%"+statisticsDto.getLadingCode()+"%' or a.ladingEvidence like '%"+statisticsDto.getLadingCode()+"%')";
						
					}else{
						sql+=" and (b.code ='"+statisticsDto.getLadingCode()+"' or a.ladingEvidence ='"+statisticsDto.getLadingCode()+"')";
						
					}
						}
					}else{

						
						if(statisticsDto.getIsDim()==1){
							sql+=" and (b.code like '%"+statisticsDto.getLadingCode()+"%' or a.ladingEvidence like '%"+statisticsDto.getLadingCode()+"%')";
							
						}else{
							sql+=" and (b.code ='"+statisticsDto.getLadingCode()+"' or a.ladingEvidence ='"+statisticsDto.getLadingCode()+"')";
							
						}
							
					}
					
				}
				
				if(!Common.empty(statisticsDto.getCargoCode())){
					sql+=" and l.code like '%"+statisticsDto.getCargoCode()+"%'";
				}
				
				if(!Common.empty(statisticsDto.getGoodsCode())){
					
					if(!Common.isNull(statisticsDto.getType())){
						if(statisticsDto.getType()!=21&&statisticsDto.getType()!=22){
							sql+=" and i.code like '%"+statisticsDto.getGoodsCode()+"%'";
							
							}
						}else{
							
							sql+=" and i.code like '%"+statisticsDto.getGoodsCode()+"%'";
						}
					
				}
				
				
				sql+=" group by a.goodsId order by ";
				if(!Common.isNull(statisticsDto.getType())){
					if(statisticsDto.getType()==21){
						sql+=" i.cargoId, a.goodsId, ";
						}
					}
				sql+="  a.originalTime ASC ";
				if (limit != 0) {
					sql+=" limit " + start + "," + limit;
				}
				
			
		}else{
			
			sql="select q.name goodsLadingClientName,p.name ladingInClientName,FROM_UNIXTIME(a.createTime) mtime, l.code cargoCode,n.name shipName,i.id goodsId,j.name productName,a.*,b.code ladingCode1,h.code nextLadingCode,g.name ladingClientName,k.name clientName,(case when a.deliverType=1 then (select code from t_pcs_truck where id=a.vehicleShipId) else (SELECT sr.refName FROM t_pcs_ship_ref sr WHERE sr.id=a.vehicleShipId) end) carshipCode ";
			if(Common.isNull(statisticsDto.getType())||statisticsDto.getType()==5 ||statisticsDto.getType()==22||statisticsDto.getType()==51||statisticsDto.getType()==52){
				sql+=" ,r.inWeigh,r.outWeigh,r.actualRoughWeight,r.actualTareWeight,s.code tankName1  ";
			}
					sql+=" from t_pcs_goodslog a " +
					"LEFT JOIN t_pcs_lading b on b.id=a.ladingId " +
					"LEFT JOIN t_pcs_client c on c.id=a.clientId "+
					"LEFT JOIN t_pcs_client g on g.id=a.ladingClientId " +
					"LEFT JOIN t_pcs_lading h on h.id=a.nextLadingId " +
					"LEFT JOIN t_pcs_goods i on i.id=a.goodsId "+
					"LEFT JOIN t_pcs_product j on j.id=i.productId "+
					"LEFT JOIN t_pcs_client k on k.id=i.clientId "+
					"LEFT JOIN t_pcs_cargo l on l.id=i.cargoId "+
					"LEFT JOIN t_pcs_arrival m on m.id=l.arrivalId "+
					"LEFT JOIN t_pcs_ship n on m.shipId=n.id "+
					" LEFT JOIN t_pcs_client p on p.id=b.clientId "+
					"LEFT JOIN t_pcs_client q on q.id=b.receiveClientId ";
//					" LEFT JOIN t_pcs_weighbridge r on r.serial=a.serial ";
//					"LEFT JOIN t_pcs_client q on q.id=b.receiveClientId ";
					if(Common.isNull(statisticsDto.getType())||statisticsDto.getType()==5 ||statisticsDto.getType()==22||statisticsDto.getType()==51||statisticsDto.getType()==52){
						sql+=" LEFT JOIN t_pcs_weighbridge r on r.serial=a.serial " +
								"LEFT JOIN t_pcs_tank s on s.id=r.tankId ";
					}
					
					sql+="where a.type<>8 ";
			
			if(!Common.isNull(statisticsDto.getType())){
				if(statisticsDto.getType()==21){
					sql+=" and ((a.type=5 and a.actualType=1 ) or a.type=2 or a.type=3 or a.type=4 or  a.type=1 or a.type=7 or (a.type=10 and a.deliverNo=1) or a.type=10  )";
				}else if(statisticsDto.getType()==12){
					sql+=" and  (a.type=7 or a.type=1 or a.type=4  or (a.type=10 and ifnull(a.deliverNo,0)<>1) ) and a.goodsChange<>0 ";
				}
				else if(statisticsDto.getType()==20){
					sql+=" and a.type=5 and a.actualType=0 ";
				}else if(statisticsDto.getType()==5 ||statisticsDto.getType()==22){
					sql+=" and a.type=5 and a.actualType=1 ";
				}else if(statisticsDto.getType()==4){
					sql+=" and (a.type=4 or a.type=10) ";
				}else if(statisticsDto.getType()==51){
					sql+=" and a.type=5 and a.actualType=1 and a.deliverType=1 ";
				}else if(statisticsDto.getType()==52){
					sql+=" and a.type=5 and a.actualType=1 and a.deliverType=2 ";
				}else if(statisticsDto.getType()==53){
					sql+=" and a.type=5 and a.actualType=1 and a.deliverType=3 ";
				}else if(statisticsDto.getType()==10){
					sql+=" and a.type=10 and a.deliverNo=1  ";
				}else{
					
					sql+=" and a.type= "+statisticsDto.getType();
				}
				
			}
//			if (!Common.isNull(statisticsDto.getClientId())) {
//				sql+=" and i.clientId=" + statisticsDto.getClientId();
//			}
			
			
			
			if (!Common.isNull(statisticsDto.getClientId())&&statisticsDto.getType()!=22) {
				sql+=" and case when isnull(b.id) then i.clientId=" + statisticsDto.getClientId() +" else b.receiveClientId=" + statisticsDto.getClientId() +" end ";
				
			}
			
			if(!Common.isNull(statisticsDto.getGoodsIds())&&statisticsDto.getType()!=22){
				sql+=" and goodsId in ("+statisticsDto.getGoodsIds()+")";
				
				if (!Common.isNull(statisticsDto.getEndTime())) {
					sql+=" and a."+time+"<"
							+ (statisticsDto.getEndTime()+86400);
					
				}
				
				
			}else{
				if(!Common.isNull(statisticsDto.getGoodsIds())){
					sql+=" and goodsId in ("+statisticsDto.getGoodsIds()+")";
				}
					if (!Common.isNull(statisticsDto.getStartTime())) {
						sql+=" and a."+time+">"
								+ statisticsDto.getStartTime();
						
					}
					
					if (!Common.isNull(statisticsDto.getEndTime())) {
						sql+=" and a."+time+"<"
								+ (statisticsDto.getEndTime()+86400);
						
					}
			}

			if(!Common.isNull(statisticsDto.getProductId())){
				sql+= " and j.id="+statisticsDto.getProductId(); 
			}
			
			if(!Common.empty(statisticsDto.getLadingCode())){
				
				if(!Common.isNull(statisticsDto.getType())){
					if(statisticsDto.getType()!=21&&statisticsDto.getType()!=22){
				
				if(statisticsDto.getIsDim()==1){
					sql+=" and (b.code like '%"+statisticsDto.getLadingCode()+"%' or a.ladingEvidence like '%"+statisticsDto.getLadingCode()+"%')";
					
				}else{
					sql+=" and (b.code ='"+statisticsDto.getLadingCode()+"' or a.ladingEvidence ='"+statisticsDto.getLadingCode()+"')";
					
				}
					}
				}else{

					
					if(statisticsDto.getIsDim()==1){
						sql+=" and (b.code like '%"+statisticsDto.getLadingCode()+"%' or a.ladingEvidence like '%"+statisticsDto.getLadingCode()+"%')";
						
					}else{
						sql+=" and (b.code ='"+statisticsDto.getLadingCode()+"' or a.ladingEvidence ='"+statisticsDto.getLadingCode()+"')";
						
					}
						
				}
				
			}
			
			if(!Common.empty(statisticsDto.getCargoCode())){
				sql+=" and l.code like '%"+statisticsDto.getCargoCode()+"%'";
			}
			
			if(!Common.empty(statisticsDto.getGoodsCode())){
				
				if(!Common.isNull(statisticsDto.getType())){
					if(statisticsDto.getType()!=21&&statisticsDto.getType()!=22){
						sql+=" and i.code like '%"+statisticsDto.getGoodsCode()+"%'";
						
						}
					}else{
						
						sql+=" and i.code like '%"+statisticsDto.getGoodsCode()+"%'";
					}
				
			}
			
			
			sql+=" order by ";
			if(!Common.isNull(statisticsDto.getType())){
				if(statisticsDto.getType()==21){
					sql+=" i.cargoId, a.goodsId, ";
					}
				}
			sql+="  a.originalTime ASC ";
			if (limit != 0) {
				sql+=" limit " + start + "," + limit;
			}
			
		}
			
		
		
			
			 
			
			return executeQuery(sql.toString());
			
			
			
	}

	@Override
	public int logCount(StatisticsDto statisticsDto) throws OAException {
		
		String sql="";
		String time="createTime";
		if(!Common.isNull(statisticsDto.getShowVirTime())){
			if(statisticsDto.getShowVirTime()==2){
				time="originalTime";
			}
		}
		if(!Common.isNull(statisticsDto.getType())&&statisticsDto.getType()==1){
			
			
			 sql ="select count(1) from (select a.id from t_pcs_goodslog a "+
						"LEFT JOIN t_pcs_lading b on b.id=a.ladingId " +
						"LEFT JOIN t_pcs_client c on c.id=a.clientId "+
						"LEFT JOIN t_pcs_client g on g.id=a.ladingClientId " +
						"LEFT JOIN t_pcs_lading h on h.id=a.nextLadingId " +
						"LEFT JOIN t_pcs_goods i on i.id=a.goodsId "+
						"LEFT JOIN t_pcs_product j on j.id=i.productId "+
						"LEFT JOIN t_pcs_client k on k.id=i.clientId "+
						"LEFT JOIN t_pcs_cargo l on l.id=i.cargoId "+
						"LEFT JOIN t_pcs_arrival m on m.id=l.arrivalId "+
						"LEFT JOIN t_pcs_ship n on m.shipId=n.id "+
						" where (a.type=1 or a.type=7 or a.type=4 or (a.type=10 and ifnull(a.deliverNo,0)<>1) )  and isnull(i.sourceGoodsId) and isnull(i.rootGoodsId) and a.goodsChange<>0  ";
			
			
			
			if (!Common.isNull(statisticsDto.getClientId())&&statisticsDto.getType()!=22) {
				sql+=" and case when isnull(b.id) then i.clientId=" + statisticsDto.getClientId() +" else b.receiveClientId=" + statisticsDto.getClientId() +" end ";
				
			}
			
			if(!Common.isNull(statisticsDto.getGoodsIds())&&statisticsDto.getType()!=22){
				sql+=" and goodsId in ("+statisticsDto.getGoodsIds()+")";
				
				if (!Common.isNull(statisticsDto.getEndTime())) {
					sql+=" and a."+time+"<"
							+ (statisticsDto.getEndTime()+86400);
					
				}
				
				
			}else{
				if(!Common.isNull(statisticsDto.getGoodsIds())){
					sql+=" and goodsId in ("+statisticsDto.getGoodsIds()+")";
				}
					if (!Common.isNull(statisticsDto.getStartTime())) {
						sql+=" and a."+time+">"
								+ statisticsDto.getStartTime();
						
					}
					
					if (!Common.isNull(statisticsDto.getEndTime())) {
						sql+=" and a."+time+"<"
								+ (statisticsDto.getEndTime()+86400);
						
					}
			}

			if(!Common.isNull(statisticsDto.getProductId())){
				sql+= " and j.id="+statisticsDto.getProductId(); 
			}
			
			if(!Common.empty(statisticsDto.getLadingCode())){
				
				if(!Common.isNull(statisticsDto.getType())){
					if(statisticsDto.getType()!=21&&statisticsDto.getType()!=22){
				
				if(statisticsDto.getIsDim()==1){
					sql+=" and (b.code like '%"+statisticsDto.getLadingCode()+"%' or a.ladingEvidence like '%"+statisticsDto.getLadingCode()+"%')";
					
				}else{
					sql+=" and (b.code ='"+statisticsDto.getLadingCode()+"' or a.ladingEvidence ='"+statisticsDto.getLadingCode()+"')";
					
				}
					}
				}else{

					
					if(statisticsDto.getIsDim()==1){
						sql+=" and (b.code like '%"+statisticsDto.getLadingCode()+"%' or a.ladingEvidence like '%"+statisticsDto.getLadingCode()+"%')";
						
					}else{
						sql+=" and (b.code ='"+statisticsDto.getLadingCode()+"' or a.ladingEvidence ='"+statisticsDto.getLadingCode()+"')";
						
					}
						
				}
				
			}
			
			if(!Common.empty(statisticsDto.getCargoCode())){
				sql+=" and l.code like '%"+statisticsDto.getCargoCode()+"%'";
			}
			
			if(!Common.empty(statisticsDto.getGoodsCode())){
				
				if(!Common.isNull(statisticsDto.getType())){
					if(statisticsDto.getType()!=21&&statisticsDto.getType()!=22){
						sql+=" and i.code like '%"+statisticsDto.getGoodsCode()+"%'";
						
						}
					}else{
						
						sql+=" and i.code like '%"+statisticsDto.getGoodsCode()+"%'";
					}
				
			}
			
			
			sql+=" group by a.goodsId order by ";
			if(!Common.isNull(statisticsDto.getType())){
				if(statisticsDto.getType()==21){
					sql+=" i.cargoId, a.goodsId, ";
					}
				}
			sql+="  a.originalTime ASC ) a";
			
		
	}else{
		
		
		
		
		
		 sql ="select count(a.id) from t_pcs_goodslog a "+
				"LEFT JOIN t_pcs_lading b on b.id=a.ladingId " +
				"LEFT JOIN t_pcs_client c on c.id=a.clientId "+
				"LEFT JOIN t_pcs_client g on g.id=a.ladingClientId " +
				"LEFT JOIN t_pcs_lading h on h.id=a.nextLadingId " +
				"LEFT JOIN t_pcs_goods i on i.id=a.goodsId "+
				"LEFT JOIN t_pcs_product j on j.id=i.productId "+
				"LEFT JOIN t_pcs_client k on k.id=i.clientId "+
				"LEFT JOIN t_pcs_cargo l on l.id=i.cargoId "+
				"LEFT JOIN t_pcs_arrival m on m.id=l.arrivalId "+
				"LEFT JOIN t_pcs_ship n on m.shipId=n.id "+
				" where a.type<>8 ";
		
		
		if(!Common.isNull(statisticsDto.getType())){
			if(statisticsDto.getType()==21 ){
				sql+=" and ((a.type=5 and a.actualType=1 ) or a.type=2 or a.type=4 or a.type=3 or a.type=1 or a.type=7 or (a.type=10 and a.deliverNo=1) or a.type=10 ) ";
			}else if(statisticsDto.getType()==12){
				sql+=" and  (a.type=7 or a.type=1 or a.type=4  or (a.type=10 and ifnull(a.deliverNo,0)<>1) ) and a.goodsChange<>0 ";
			}
			else if(statisticsDto.getType()==20){
				sql+=" and a.type=5 and a.actualType=0 ";
			}else if(statisticsDto.getType()==5 ||statisticsDto.getType()==22){
				sql+=" and a.type=5 and a.actualType=1 ";
			}else if(statisticsDto.getType()==4){
				sql+=" and (a.type=4 or a.type=10) ";
			}else if(statisticsDto.getType()==51){
				sql+=" and a.type=5 and a.actualType=1 and a.deliverType=1 ";
			}else if(statisticsDto.getType()==52){
				sql+=" and a.type=5 and a.actualType=1 and a.deliverType=2 ";
			}else if(statisticsDto.getType()==53){
				sql+=" and a.type=5 and a.actualType=1 and a.deliverType=3 ";
			}else if(statisticsDto.getType()==10){
				sql+=" and a.type=10 and a.deliverNo=1  ";
			}else{
				
				sql+=" and a.type= "+statisticsDto.getType();
			}
			
		}
//		if (!Common.isNull(statisticsDto.getClientId())) {
//			sql+=" and i.clientId=" + statisticsDto.getClientId();
//		}
		
		if (!Common.isNull(statisticsDto.getClientId())&&statisticsDto.getType()!=22) {
//			sql+=" and (i.clientId=" + statisticsDto.getClientId() +" or i.ladingClientId="+statisticsDto.getClientId()+") ";

			sql+=" and case when isnull(b.id) then i.clientId=" + statisticsDto.getClientId() +" else b.receiveClientId=" + statisticsDto.getClientId() +" end ";
			
		
		}
		
		if(!Common.isNull(statisticsDto.getGoodsIds())&&statisticsDto.getType()!=22){
			sql+=" and goodsId in ("+statisticsDto.getGoodsIds()+")";
			
			if (!Common.isNull(statisticsDto.getEndTime())) {
				sql+=" and a."+time+"<"
						+ (statisticsDto.getEndTime()+86400);
				
			}
			
			
		}else{
			
			if(!Common.isNull(statisticsDto.getGoodsIds())){
				sql+=" and goodsId in ("+statisticsDto.getGoodsIds()+")";
			}
			
				if (!Common.isNull(statisticsDto.getStartTime())) {
					sql+=" and a."+time+">"
							+ statisticsDto.getStartTime();
					
				}
				
				if (!Common.isNull(statisticsDto.getEndTime())) {
					sql+=" and a."+time+"<"
							+ (statisticsDto.getEndTime()+86400);
					
				}
		}

		if(!Common.isNull(statisticsDto.getProductId())){
			sql+= " and j.id="+statisticsDto.getProductId(); 
		}
		
		if(!Common.empty(statisticsDto.getLadingCode())){
			
			if(!Common.isNull(statisticsDto.getType())){
				if(statisticsDto.getType()!=21&&statisticsDto.getType()!=22){
			
			if(statisticsDto.getIsDim()==1){
				sql+=" and (b.code like '%"+statisticsDto.getLadingCode()+"%' or a.ladingEvidence like '%"+statisticsDto.getLadingCode()+"%')";
				
			}else{
				sql+=" and (b.code ='"+statisticsDto.getLadingCode()+"' or a.ladingEvidence ='"+statisticsDto.getLadingCode()+"')";
				
			}
				}
			}else{

				
				if(statisticsDto.getIsDim()==1){
					sql+=" and (b.code like '%"+statisticsDto.getLadingCode()+"%' or a.ladingEvidence like '%"+statisticsDto.getLadingCode()+"%')";
					
				}else{
					sql+=" and (b.code ='"+statisticsDto.getLadingCode()+"' or a.ladingEvidence ='"+statisticsDto.getLadingCode()+"')";
					
				}
					
			}
			
		}
		
		if(!Common.empty(statisticsDto.getCargoCode())){
			sql+=" and l.code like '%"+statisticsDto.getCargoCode()+"%'";
		}
		
		if(!Common.empty(statisticsDto.getGoodsCode())){
			
			if(!Common.isNull(statisticsDto.getType())){
				if(statisticsDto.getType()!=21&&statisticsDto.getType()!=22){
					sql+=" and i.code like '%"+statisticsDto.getGoodsCode()+"%'";
					
					}
				}else{
					
					sql+=" and i.code like '%"+statisticsDto.getGoodsCode()+"%'";
				}
			
		}
	}
		
		return (int) getCount(sql);
		
		
		
}

	@Override
	public List<Map<String, Object>> getLogTotal(StatisticsDto statisticsDto)
			throws OAException {
		
		String sql="";
		String time="createTime";
		if(!Common.isNull(statisticsDto.getShowVirTime())){
			if(statisticsDto.getShowVirTime()==2){
				time="originalTime";
			}
		}
//		if(statisticsDto.getType()==21){
//			sql="select round(sum(goodsCurrent),3) logTotal from t_pcs_goods a  where 1=1";
//			
//			if(!Common.isNull(statisticsDto.getProductId())){
//				sql+= " and a.productId="+statisticsDto.getProductId(); 
//			}
//			if (!Common.isNull(statisticsDto.getClientId())) {
//				sql+=" and ((a.clientId=" + statisticsDto.getClientId() +" and (ISNULL(a.ladingClientId) or a.ladingClientId=0)) or a.ladingClientId="+statisticsDto.getClientId()+") ";
//				
//			}
//			if(!Common.isNull(statisticsDto.getGoodsIds())){
//				sql+=" and a.id in ("+statisticsDto.getGoodsIds()+")";
//			}
//			
//		}else{
		if(statisticsDto.getType()==21){
			sql="select round(sum(a.goodsChange),3) logTotal from t_pcs_goodslog a ";
		}else{
			
//			sql="select ABS(round(sum(a.goodsChange),3)) logTotal from t_pcs_goodslog a " ;
			sql="select round(sum(a.goodsChange),3) logTotal from t_pcs_goodslog a " ;
		}
					sql+="LEFT JOIN t_pcs_lading b on b.id=a.ladingId " +
					"LEFT JOIN t_pcs_client c on c.id=a.clientId "+
					"LEFT JOIN t_pcs_client g on g.id=a.ladingClientId " +
					"LEFT JOIN t_pcs_lading h on h.id=a.nextLadingId " +
					"LEFT JOIN t_pcs_goods i on i.id=a.goodsId "+
					"LEFT JOIN t_pcs_product j on j.id=i.productId "+
					"LEFT JOIN t_pcs_client k on k.id=i.clientId "+
					" LEFT JOIN t_pcs_cargo l on l.id=i.cargoId "+
					"where a.type<>8 ";
			
			if(!Common.isNull(statisticsDto.getType())){
				if(statisticsDto.getType()==21){
					sql+=" and ((a.type=5 and a.actualType=1 ) or a.type=10 or a.type=2 or a.type=3 or a.type=1 or a.type=7 or (a.type=10 and a.deliverNo=1)) ";
				}
				else if(statisticsDto.getType()==20){
					sql+=" and a.type=5 and a.actualType=0 ";
				}else if(statisticsDto.getType()==5||statisticsDto.getType()==22){
					sql+=" and a.type=5 and a.actualType=1 ";
				}else if(statisticsDto.getType()==4){
					sql+=" and (a.type=4 or a.type=10) ";
				}else if(statisticsDto.getType()==51){
					sql+=" and a.type=5 and a.actualType=1 and a.deliverType=1 ";
				}else if(statisticsDto.getType()==52){
					sql+=" and a.type=5 and a.actualType=1 and a.deliverType=2 ";
				}else if(statisticsDto.getType()==53){
					sql+=" and a.type=5 and a.actualType=1 and a.deliverType=3 ";
				}else if(statisticsDto.getType()==1||statisticsDto.getType()==12){
					sql+=" and (a.type=7 or a.type=1 or a.type=4 or (a.type=10 and ifnull(a.deliverNo,0)<>1)) ";
				}else if(statisticsDto.getType()==10){
					sql+=" and a.type=10 and a.deliverNo=1  ";
				}else{
					
					sql+=" and a.type= "+statisticsDto.getType();
				}
				
			}
//			if (!Common.isNull(statisticsDto.getClientId())) {
//				sql+=" and i.clientId=" + statisticsDto.getClientId();
//			}
			
			if (!Common.isNull(statisticsDto.getClientId())&&statisticsDto.getType()!=22) {
//				sql+=" and (i.clientId=" + statisticsDto.getClientId() +" or i.ladingClientId="+statisticsDto.getClientId()+") ";

				sql+=" and case when isnull(b.id) then i.clientId=" + statisticsDto.getClientId() +" else b.receiveClientId=" + statisticsDto.getClientId() +" end ";
				
			
			}
			
			if(!Common.isNull(statisticsDto.getGoodsIds())&&statisticsDto.getType()!=22){
				sql+=" and goodsId in ("+statisticsDto.getGoodsIds()+")";
				
				if (!Common.isNull(statisticsDto.getEndTime())) {
					sql+=" and a."+time+"<"
							+ (statisticsDto.getEndTime()+86400);
					
				}
				
				
			}else{
				
				if(!Common.isNull(statisticsDto.getGoodsIds())){
					sql+=" and goodsId in ("+statisticsDto.getGoodsIds()+")";
				}
					if (!Common.isNull(statisticsDto.getStartTime())) {
						sql+=" and a."+time+">"
								+ statisticsDto.getStartTime();
						
					}
					
					if (!Common.isNull(statisticsDto.getEndTime())) {
						sql+=" and a."+time+"<"
								+ (statisticsDto.getEndTime()+86400);
						
					}
			}

			if(!Common.isNull(statisticsDto.getProductId())){
				sql+= " and j.id="+statisticsDto.getProductId(); 
			}
			
			
			if(!Common.empty(statisticsDto.getLadingCode())){
				
				if(!Common.isNull(statisticsDto.getType())){
					if(statisticsDto.getType()!=21&&statisticsDto.getType()!=22){
				
				if(statisticsDto.getIsDim()==1){
					sql+=" and (b.code like '%"+statisticsDto.getLadingCode()+"%' or a.ladingEvidence like '%"+statisticsDto.getLadingCode()+"%')";
					
				}else{
					sql+=" and (b.code ='"+statisticsDto.getLadingCode()+"' or a.ladingEvidence ='"+statisticsDto.getLadingCode()+"')";
					
				}
					}
				}else{

					
					if(statisticsDto.getIsDim()==1){
						sql+=" and (b.code like '%"+statisticsDto.getLadingCode()+"%' or a.ladingEvidence like '%"+statisticsDto.getLadingCode()+"%')";
						
					}else{
						sql+=" and (b.code ='"+statisticsDto.getLadingCode()+"' or a.ladingEvidence ='"+statisticsDto.getLadingCode()+"')";
						
					}
						
				}
				
			}
			
			if(!Common.empty(statisticsDto.getCargoCode())){
				sql+=" and l.code like '%"+statisticsDto.getCargoCode()+"%'";
			}
			
			if(!Common.empty(statisticsDto.getGoodsCode())){
				
				if(!Common.isNull(statisticsDto.getType())){
					if(statisticsDto.getType()!=21&&statisticsDto.getType()!=22){
						sql+=" and i.code like '%"+statisticsDto.getGoodsCode()+"%'";
						
						}
					}else{
						
						sql+=" and i.code like '%"+statisticsDto.getGoodsCode()+"%'";
					}
				
			}
			
			sql+=" order by a.originalTime ASC ";
			
//		}
		
		return executeQuery(sql.toString());
		
}

	@Override
	public List<Map<String, Object>> getLogGoodsIds(StatisticsDto statisticsDto)throws OAException {
		
		
		String time="createTime";
		if(!Common.isNull(statisticsDto.getShowVirTime())){
			if(statisticsDto.getShowVirTime()==2){
				time="originalTime";
			}
		}
		String sql="";
		
		if(statisticsDto.getType()==22){
			
			 sql="select GROUP_CONCAT(DISTINCT a.goodsId) goodsId from t_pcs_goodslog a " +
					"LEFT JOIN t_pcs_goods i on i.id=a.goodsId LEFT JOIN t_pcs_cargo c on c.id =i.cargoId LEFT JOIN t_pcs_goods_group d on d.id=i.goodsGroupId LEFT JOIN t_pcs_lading e on e.id=d.ladingId "+
					" where 1=1  ";
			
		}else{
			
			sql="select DISTINCT a.goodsId goodsId from t_pcs_goodslog a " +
					"LEFT JOIN t_pcs_goods i on i.id=a.goodsId LEFT JOIN t_pcs_cargo c on c.id =i.cargoId LEFT JOIN t_pcs_goods_group d on d.id=i.goodsGroupId LEFT JOIN t_pcs_lading e on e.id=d.ladingId "+
					" where 1=1  ";
		}
		
		
				
		if (!Common.isNull(statisticsDto.getClientId())) {
//			sql+=" and ((i.clientId=" + statisticsDto.getClientId() +" and (ISNULL(i.ladingClientId) or i.ladingClientId=0)) or i.ladingClientId="+statisticsDto.getClientId()+") ";
			sql+=" and (i.clientId=" + statisticsDto.getClientId() +" or i.ladingClientId="+statisticsDto.getClientId()+") ";
			
		}
		
		
		if (!Common.isNull(statisticsDto.getStartTime())) {
			if (!Common.isNull(statisticsDto.getEndTime())) {
				
				if(statisticsDto.getType()!=22){
					
					sql+=" and ((a."+time+">"
							+ statisticsDto.getStartTime();
					sql+=" and a."+time+"<"
							+ (statisticsDto.getEndTime()+86400);
					
					sql+=" ) or ( select round(sum(c.goodsChange),3) goodsSave from t_pcs_goodslog c where c.type<>8 AND if(c.type=5 and c.actualType=0 ,0,1)=1 and c.goodsId=a.goodsId and  c."+time+"<"+(statisticsDto.getEndTime()+86400)+" )>0 )";
					
				}
				
			}
					
		}
		
					
		

		if(!Common.isNull(statisticsDto.getProductId())){
			sql+= " and i.productId="+statisticsDto.getProductId(); 
		}
		
		if(!Common.empty(statisticsDto.getLadingCode())){
			if(statisticsDto.getIsDim()==1){
				sql+=" and e.code like '%"+statisticsDto.getLadingCode()+"%' ";
				
			}else{
				sql+=" and e.code ='"+statisticsDto.getLadingCode()+"' ";
				
			}
			
		}
		
		if(!Common.empty(statisticsDto.getCargoCode())){
			sql+=" and c.code like '%"+statisticsDto.getCargoCode()+"%'";
		}
		
		if(!Common.empty(statisticsDto.getGoodsCode())){
			sql+=" and i.code like '%"+statisticsDto.getGoodsCode()+"%'";
		}
		
		
		
		
		return executeQuery(sql.toString());
		
	}

	@Override
	public List<Map<String, Object>> getGoodsTotal(StatisticsDto sDto)
			throws OAException {
		
		String sql="";
			
		sql="select round(sum(a.goodsCurrent),3) goodsTotal from t_pcs_goods a LEFT JOIN t_pcs_cargo d on d.id=a.cargoId  where ((select count(id) from t_pcs_goodslog e where e.goodsId=a.id and e.createTime>"+sDto.getStartTime()+" and e.createTime < "+(sDto.getEndTime()+86400)+")>0 or (select round(sum(c.goodsChange),3) goodsSave from t_pcs_goodslog c where c.type<>8 AND if(c.type=5 and c.actualType=0 ,0,1)=1 and c.goodsId=a.id and c.createTime<"+(sDto.getEndTime()+86400)+" )>0 )";
		
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
			sql+=" and a.clientId="+sDto.getClientId()+" or a.ladingClientId="+sDto.getClientId();
		}
		if(!Common.empty(sDto.getCode()))
		{
			sql+=" and REPLACE(a.code,' ','') like '%"+sDto.getCode().replace(" ", "")+"%'";
		}

		
		return executeQuery(sql);
	
		
	}

	@Override
	public List<Map<String, Object>> getDownGoodsId(String ids)
			throws OAException {
		// TODO Auto-generated method stub
		String sql="call func_getGoodsAllChildren('"+ids+"')";
		
		return executeQuery(sql);
	}

	@Override
	public List<Map<String, Object>> getPassCargo(StatisticsDto statisticsDto,
			int start, int limit) throws OAException {
		try {
			String sql="select *,(select DISTINCT g.billingStatus from  t_pcs_fee_charge f LEFT JOIN t_pcs_fee_bill g ON g.id = f.feebillId where f.cargoId = a.id "+
"AND f.feeType = 8 "+
") billingStatus,e.name productName,c.name clientName,d.name shipName from t_pcs_cargo a LEFT JOIN t_pcs_arrival b   on a.arrivalId=b.id " +
					" LEFT JOIN t_pcs_client c on c.id=a.clientId " +
					" LEFT JOIN t_pcs_ship d on d.id=b.shipId " +
					" LEFT JOIN t_pcs_product e on e.id=a.productId  " +
//					" LEFT JOIN t_pcs_fee_charge f on f.cargoId=a.id and f.feeType=8 LEFT JOIN t_pcs_fee_bill g on g.id=f.feebillId " +
					" where b.type=3 ";
			
			if(!Common.empty(statisticsDto.getStartTime())){
				sql+=" and UNIX_TIMESTAMP(b.arrivalStartTime)>='"+statisticsDto.getStartTime()+"'";
			}
			if(!Common.empty(statisticsDto.getEndTime())){
				sql+=" and UNIX_TIMESTAMP(b.arrivalStartTime)<='"+(statisticsDto.getEndTime()+86400)+"'";
			}
			
			if(!Common.isNull(statisticsDto.getClientId()))
			{
				sql+=" and a.clientId="+statisticsDto.getClientId();
			}
			if(!Common.isNull(statisticsDto.getProductId()))
			{
				sql+=" and a.productId="+statisticsDto.getProductId();
			}
			
			sql+=" order by b.arrivalStartTime DESC ";
			if (limit != 0) {
				sql+=" limit " + start + "," + limit;
			}
			
			return executeQuery(sql.toString());
		} catch (RuntimeException e) {
			LOG.error("查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败", e);
		}
	}

	@Override
	public int getPassCargoCount(StatisticsDto statisticsDto)
			throws OAException {
		try {
			String sql="select count(1) from t_pcs_cargo a LEFT JOIN t_pcs_arrival b   on a.arrivalId=b.id " +
					" LEFT JOIN t_pcs_client c on c.id=a.clientId " +
					" LEFT JOIN t_pcs_ship d on d.id=b.shipId " +
					" LEFT JOIN t_pcs_product e on e.id=a.productId  where b.type=3 ";
			
			if(!Common.empty(statisticsDto.getStartTime())){
				sql+=" and UNIX_TIMESTAMP(b.arrivalStartTime)>='"+statisticsDto.getStartTime()+"'";
			}
			if(!Common.empty(statisticsDto.getEndTime())){
				sql+=" and UNIX_TIMESTAMP(b.arrivalStartTime)<='"+(statisticsDto.getEndTime()+86400)+"'";
			}
			
			if(!Common.isNull(statisticsDto.getClientId()))
			{
				sql+=" and a.clientId="+statisticsDto.getClientId();
			}
			if(!Common.isNull(statisticsDto.getProductId()))
			{
				sql+=" and a.productId="+statisticsDto.getProductId();
			}
			
			return (int) getCount(sql.toString());
		} catch (RuntimeException e) {
			LOG.error("查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败", e);
		}
	}

	@Override
	public List<Map<String, Object>> getPassTotal(StatisticsDto statisticsDto)
			throws OAException {
		try {
			String sql="select round(sum(a.goodsPlan),3) planTotal,round(sum(a.goodsInspect),3) inspectTotal from t_pcs_cargo a LEFT JOIN t_pcs_arrival b   on a.arrivalId=b.id " +
					" LEFT JOIN t_pcs_client c on c.id=a.clientId " +
					" LEFT JOIN t_pcs_ship d on d.id=b.shipId " +
					" LEFT JOIN t_pcs_product e on e.id=a.productId  where b.type=3 ";
			
			if(!Common.empty(statisticsDto.getStartTime())){
				sql+=" and UNIX_TIMESTAMP(b.arrivalStartTime)>='"+statisticsDto.getStartTime()+"'";
			}
			if(!Common.empty(statisticsDto.getEndTime())){
				sql+=" and UNIX_TIMESTAMP(b.arrivalStartTime)<='"+(statisticsDto.getEndTime()+86400)+"'";
				}
			
			if(!Common.isNull(statisticsDto.getClientId()))
			{
				sql+=" and a.clientId="+statisticsDto.getClientId();
			}
			if(!Common.isNull(statisticsDto.getProductId()))
			{
				sql+=" and a.productId="+statisticsDto.getProductId();
			}
			
			return executeQuery(sql.toString());
		} catch (RuntimeException e) {
			LOG.error("查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败", e);
		}
	}

	@Override
	public List<Map<String, Object>> getPassKPTotal(StatisticsDto statisticsDto)
			throws OAException {
		try {
			String sql="select sum(z.KPTotal) KPTotal from (select round(sum( DISTINCT a.goodsInspect),3) KPTotal from t_pcs_cargo a LEFT JOIN t_pcs_arrival b   on a.arrivalId=b.id " +
					" LEFT JOIN t_pcs_client c on c.id=a.clientId " +
					" LEFT JOIN t_pcs_ship d on d.id=b.shipId " +
					" LEFT JOIN t_pcs_fee_charge f on f.cargoId=a.id and f.feeType=8 LEFT JOIN t_pcs_fee_bill g on g.id=f.feebillId " +
					
					" LEFT JOIN t_pcs_product e on e.id=a.productId  where b.type=3 and g.billingStatus=1";
			
			if(!Common.empty(statisticsDto.getStartTime())){
				sql+=" and UNIX_TIMESTAMP(b.arrivalStartTime)>='"+statisticsDto.getStartTime()+"'";
			}
			if(!Common.empty(statisticsDto.getEndTime())){
				sql+=" and UNIX_TIMESTAMP(b.arrivalStartTime)<='"+statisticsDto.getEndTime()+"'";
			}
			
			if(!Common.isNull(statisticsDto.getClientId()))
			{
				sql+=" and a.clientId="+statisticsDto.getClientId();
			}
			if(!Common.isNull(statisticsDto.getProductId()))
			{
				sql+=" and a.productId="+statisticsDto.getProductId();
			}
			
			sql+=" group by a.id) z";
			
			return executeQuery(sql.toString());
		} catch (RuntimeException e) {
			LOG.error("查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败", e);
		}
	}

	@Override
	public int addEveryDayStatics(EveryDayStatics everyDayStatics)
			throws OAException {
		try {
			return (Integer) save(everyDayStatics);
		} catch (Exception e) {
			throw new OAException(Constant.SYS_CODE_DB_ERR, "添加失败", e);
		}

	}

	@Override
	public List<Map<String, Object>> getEveryDayStatics(
			EveryDayStatics everyDayStatics) throws OAException {
		try {
			String sql="select * from t_pcs_everyday_statics where 1=1 ";
			
			if(!Common.empty(everyDayStatics.getType())){
				sql+=" and type="+everyDayStatics.getType();
			}
			if(!Common.empty(everyDayStatics.getTime())){
				sql+=" and time='"+everyDayStatics.getTime()+"'";
			}
			
			sql+=" order by id DESC limit 0,20";
			
			return executeQuery(sql.toString());
		} catch (RuntimeException e) {
			LOG.error("查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败", e);
		}
	}

	
	@Override
	public List<Map<String, Object>> cargoList(StatisticsDto statisticsDto,
			int start, int limit) {
		try {
		String time="createTime";
		if(!Common.isNull(statisticsDto.getShowVirTime())){
			if(statisticsDto.getShowVirTime()==2){
				time="originalTime";
			}
		}
		 StringBuffer sql = new StringBuffer();
		 sql.append(" SELECT a.id,c.`name` clientName,a.`code` cargoCode,d.`name` productName ,e.`name` shipName, ")
		 .append(" f.refName shipRefName,DATE_FORMAT(b.arrivalStartTime,'%Y-%m-%d') arrivalTime,a.connectNo, ")
		 .append(" a.`No` planNo,a.inboundNo,a.storageType,a.goodsInspect, ")
		 .append(" IFNULL((SELECT ROUND(SUM(l.goodsChange),3) FROM t_pcs_goodslog l ,t_pcs_goods g  ") 
		 .append(" WHERE l.goodsId=g.id AND g.cargoId=a.id AND l.type IN(1,7,10) ")
		 .append(" AND l.").append(time)
		 .append("<=").append(statisticsDto.getEndTime())
		 .append("),0) AS goodsTotal,")
		 .append(" IFNULL((SELECT SUM(ROUND(l.actualNum,3)) FROM t_pcs_goodslog l , ")
		 .append(" t_pcs_goods g WHERE g.cargoId=a.id AND l.goodsId=g.id ")
		 .append(" AND l.type = 5 AND l.").append(time)
		 .append("<").append(statisticsDto.getStartTime())
		 .append(" ),0) AS goodsDeliverBefore,")
		 .append(" IFNULL((SELECT SUM(ROUND(l.actualNum,3)) FROM t_pcs_goodslog l ,")
		 .append(" t_pcs_goods g WHERE g.cargoId=a.id AND l.goodsId=g.id ")
		 .append(" AND l.type = 5 AND l.").append(time)
		 .append("<=").append(statisticsDto.getEndTime())
		 .append(" ),0) AS goodsCurrent ")
		 .append(" FROM t_pcs_cargo a ")
		 .append(" LEFT OUTER JOIN t_pcs_arrival b ON a.arrivalId = b.id ")
		 .append(" LEFT OUTER JOIN t_pcs_client c ON c.id = a.clientId ")
		 .append(" LEFT OUTER JOIN t_pcs_product d ON d.id = a.productId ")
		 .append(" LEFT OUTER JOIN t_pcs_ship e ON e.id = b.shipId ")
		 .append(" LEFT OUTER JOIN t_pcs_ship_ref f ON f.id=b.shipRefId ")
		 .append(" WHERE (!isnull(a.type) or a.isPredict=1) ");
			 
		 	if (!Common.isNull(statisticsDto.getProductId())) {
				sql.append(" and a.productId=").append(statisticsDto.getProductId());
			}
			if (!Common.isNull(statisticsDto.getClientId())) {
				sql.append(" and a.clientId=").append(statisticsDto.getClientId());
			}
			if(!Common.empty(statisticsDto.getCargoCode())){
				sql.append(" and a.code like '%").append(statisticsDto.getCargoCode()).append("%' ");
			}
			if(!Common.empty(statisticsDto.getGoodsCode())){
				sql.append(" and a.id = (select cargoId from t_pcs_goods where code like '%")
				.append(statisticsDto.getGoodsCode()).append("%' limit 1)");
			}
			if(!Common.empty(statisticsDto.getIsFinish())){
				if(statisticsDto.getIsFinish() == 0){//未清盘(本段时间有发货，货主还有结存量)
					sql.append(" and ( ")
					 //时间段内发货量不为0
					 .append("IFNULL((SELECT SUM(ROUND(l.actualNum,3)) FROM t_pcs_goodslog l ,")
					 .append(" t_pcs_goods g WHERE g.cargoId=a.id AND l.goodsId=g.id AND l.type = 5 AND l.")
					 .append(time).append("<=").append(statisticsDto.getEndTime()).append(" AND l.")
					 .append(time).append(">").append(statisticsDto.getStartTime()).append("),0) ")
					 //---不等于
					 .append(" != 0 ")
					  //---或者
					 .append(" OR ")
					  //---总发货量
					 .append(" IFNULL((SELECT SUM(ROUND(l.actualNum,3)) FROM t_pcs_goodslog l ,")
					 .append(" t_pcs_goods g WHERE g.cargoId=a.id AND l.goodsId=g.id AND l.type = 5 ")
					 .append(" AND l.").append(time).append(" <=").append(statisticsDto.getEndTime()).append(" ),0)")
					  //---不等于
					 .append(" != ")
					  //---货批总量(说明还有存量)
					 .append(" IFNULL((SELECT ROUND(SUM(l.goodsChange),3) FROM t_pcs_goodslog l ,t_pcs_goods g  ") 
					 .append(" WHERE l.goodsId=g.id AND g.cargoId=a.id AND l.type IN(1,7,10) ")
					 .append(" AND l.").append(time).append("<=").append(statisticsDto.getEndTime()).append("),0)")
					 //---
					 .append(")");
				}
			}
			
			
			if(!Common.empty(statisticsDto.getShowVir())){
				if(statisticsDto.getShowVir() == 1){//主库
					sql.append(" and b.id>0 and b.arrivalStartTime<=FROM_UNIXTIME(").append(statisticsDto.getEndTime()).append(")");
				}else if (statisticsDto.getShowVir() == 2) {//主副库
					sql.append(" and ( b.id = 0 or b.arrivalStartTime<=FROM_UNIXTIME(").append(statisticsDto.getEndTime()).append(")) ");
				}else if(statisticsDto.getShowVir() == 3) {
					sql.append(" and b.id = 0 ");
				}
			}else{
				sql.append(" and ( b.id=0 or b.arrivalStartTime<=FROM_UNIXTIME(").append(statisticsDto.getEndTime()).append(")) ");
			}
			
			
			sql.append(" ORDER BY a.id DESC ");
			 
			 if(limit>0)
				 sql.append(" limit ").append(start).append(" , ").append(limit);
			 
			return executeQuery(sql.toString());
		} catch (OAException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Title getGoodsSave
	 * @Descrption:TODO
	 * @param:@param statisticsDto
	 * @param:@return
	 * @auhor jiahy
	 * @date 2017年7月11日下午5:32:28
	 * @throws
	 */
	@Override
	public Map<String, Object> getGoodsSave(StatisticsDto statisticsDto) {
		try {
			String time="createTime";
			if(!Common.isNull(statisticsDto.getShowVirTime())){
				if(statisticsDto.getShowVirTime()==2){
					time="originalTime";
				}
			}
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT SUM(ROUND(tt.goodsSave,3)) goodsSave FROM ( SELECT  t.goodsSave  FROM (  ")
			.append(" SELECT gl.goodsSave,gl.goodsId FROM t_pcs_goods g, t_pcs_goodslog gl ")  
			.append(" WHERE g.id = gl.goodsId AND g.`status` <> 1 and gl.").append(time)
			.append("<= ").append(statisticsDto.getEndTime())
			.append(" AND g.cargoId = ").append(statisticsDto.getCargoId())
			.append(" ORDER BY gl.id DESC ) t GROUP BY t.goodsId ) tt ");
			return executeQueryOne(sql.toString());
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Title cargoListCount
	 * @Descrption:TODO
	 * @param:@param statisticsDto
	 * @param:@return
	 * @auhor jiahy
	 * @date 2017年7月11日下午6:09:28
	 * @throws
	 */
	@Override
	public int cargoListCount(StatisticsDto statisticsDto) {
		String time="createTime";
		if(!Common.isNull(statisticsDto.getShowVirTime())){
			if(statisticsDto.getShowVirTime()==2){
				time="originalTime";
			}
		}
		 StringBuffer sql = new StringBuffer();
		 sql.append(" SELECT COUNT(1) FROM t_pcs_cargo a ")
		 .append(" LEFT OUTER JOIN t_pcs_arrival b ON a.arrivalId = b.id ")
		 .append(" WHERE (!isnull(a.type) or a.isPredict=1) ");
			 
		 	if (!Common.isNull(statisticsDto.getProductId())) {
				sql.append(" and a.productId=").append(statisticsDto.getProductId());
			}
			if (!Common.isNull(statisticsDto.getClientId())) {
				sql.append(" and a.clientId=").append(statisticsDto.getClientId());
			}
			if(!Common.empty(statisticsDto.getCargoCode())){
				sql.append(" and a.code like '%").append(statisticsDto.getCargoCode()).append("%' ");
			}
			if(!Common.empty(statisticsDto.getGoodsCode())){
				sql.append(" and a.id = (select cargoId from t_pcs_goods where code like '%")
				.append(statisticsDto.getGoodsCode()).append("%' limit 1)");
			}
			
			if(!Common.empty(statisticsDto.getIsFinish())){
				if(statisticsDto.getIsFinish() == 0){//未清盘(本段时间有发货，货主还有结存量)
					sql.append(" and ( ")
					 //总发货量
					 .append("IFNULL((SELECT SUM(ROUND(l.actualNum,3)) FROM t_pcs_goodslog l ,")
					 .append(" t_pcs_goods g WHERE g.cargoId=a.id AND l.goodsId=g.id AND l.type = 5 AND l.")
					 .append(time).append("<=").append(statisticsDto.getEndTime()).append(" AND l.")
					 .append(time).append(">").append(statisticsDto.getStartTime()).append("),0) ")
					 //---不等于
					 .append(" != 0 ")
					  //---或者
					 .append(" OR ")
					  //---总发货量
					 .append(" IFNULL((SELECT SUM(ROUND(l.actualNum,3)) FROM t_pcs_goodslog l ,")
					 .append(" t_pcs_goods g WHERE g.cargoId=a.id AND l.goodsId=g.id AND l.type = 5 ")
					 .append(" AND l.").append(time).append(" <=").append(statisticsDto.getEndTime()).append(" ),0)")
					  //---不等于
					 .append(" != ")
					  //---货批总量(说明还有存量)
					 .append(" IFNULL((SELECT ROUND(SUM(l.goodsChange),3) FROM t_pcs_goodslog l ,t_pcs_goods g  ") 
					 .append(" WHERE l.goodsId=g.id AND g.cargoId=a.id AND l.type IN(1,7,10) ")
					 .append(" AND l.").append(time).append("<=").append(statisticsDto.getEndTime()).append("),0)")
					 //---
					 .append(")");
				}
			}
			
			if(!Common.empty(statisticsDto.getShowVir())){
				if(statisticsDto.getShowVir() == 1){//主库
					sql.append(" and b.id>0 and b.arrivalStartTime<=FROM_UNIXTIME(").append(statisticsDto.getEndTime()).append(")");
				}else if (statisticsDto.getShowVir() == 2) {//主副库
					sql.append(" and ( b.id = 0 or b.arrivalStartTime<=FROM_UNIXTIME(").append(statisticsDto.getEndTime()).append(")) ");
				}else if(statisticsDto.getShowVir() == 3) {
					sql.append(" and b.id = 0 ");
				}
			}else{
				sql.append(" and ( b.id=0 or b.arrivalStartTime<=FROM_UNIXTIME(").append(statisticsDto.getEndTime()).append(")) ");
			}
		return (int) getCount(sql.toString());
	}

	/**
	 * @Title getCargoListTotal
	 * @Descrption:TODO
	 * @param:@param statisticsDto
	 * @param:@return
	 * @auhor jiahy
	 * @date 2017年7月13日下午5:36:24
	 * @throws
	 */
	@Override
	public Map<String, Object> getCargoListTotal(StatisticsDto statisticsDto) {
		 try {
		String time="createTime";
		if(!Common.isNull(statisticsDto.getShowVirTime())){
			if(statisticsDto.getShowVirTime()==2){
				time="originalTime";
			}
		}
		
		 StringBuffer sql = new StringBuffer();
		 
		 sql.append("SELECT SUM(IFNULL((SELECT ROUND(SUM(l.goodsChange),3) FROM t_pcs_goodslog l ,t_pcs_goods g  ") 
		 .append(" WHERE l.goodsId=g.id AND g.cargoId=a.id AND l.type IN(1,7,10) ")
		 .append(" AND l.").append(time)
		 .append("<=").append(statisticsDto.getEndTime())
		 .append("),0)) AS goodsTotal,")
		 .append(" SUM(IFNULL((SELECT SUM(ROUND(l.actualNum,3)) FROM t_pcs_goodslog l , ")
		 .append(" t_pcs_goods g WHERE g.cargoId=a.id AND l.goodsId=g.id ")
		 .append(" AND l.type = 5 AND l.").append(time)
		 .append("<=").append(statisticsDto.getEndTime())
		 .append(" AND l.").append(time)
		 .append(">=").append(statisticsDto.getStartTime())
		 .append(" ),0)) AS goodsBen,")
		 .append(" SUM(IFNULL((SELECT SUM(ROUND(l.actualNum,3)) FROM t_pcs_goodslog l ,")
		 .append(" t_pcs_goods g WHERE g.cargoId=a.id AND l.goodsId=g.id ")
		 .append(" AND l.type = 5 AND l.").append(time)
		 .append("<=").append(statisticsDto.getEndTime())
		 .append(" ),0)) AS goodsCurrent ")
		 .append(" FROM t_pcs_cargo a ")
		 .append(" LEFT OUTER JOIN t_pcs_arrival b ON a.arrivalId = b.id ")
		 .append(" WHERE (!isnull(a.type) or a.isPredict=1) ");
			 
		 	if (!Common.isNull(statisticsDto.getProductId())) {
				sql.append(" and a.productId=").append(statisticsDto.getProductId());
			}
			if (!Common.isNull(statisticsDto.getClientId())) {
				sql.append(" and a.clientId=").append(statisticsDto.getClientId());
			}
			if(!Common.empty(statisticsDto.getCargoCode())){
				sql.append(" and a.code like '%").append(statisticsDto.getCargoCode()).append("%' ");
			}
			if(!Common.empty(statisticsDto.getGoodsCode())){
				sql.append(" and a.id = (select cargoId from t_pcs_goods where code like '%")
				.append(statisticsDto.getGoodsCode()).append("%' limit 1)");
			}
			if(!Common.empty(statisticsDto.getIsFinish())){
				if(statisticsDto.getIsFinish() == 0){//未清盘(本段时间有发货，货主还有结存量)
					sql.append(" and ( ")
					 //时间段内发货量不为0
					 .append("IFNULL((SELECT SUM(ROUND(l.actualNum,3)) FROM t_pcs_goodslog l ,")
					 .append(" t_pcs_goods g WHERE g.cargoId=a.id AND l.goodsId=g.id AND l.type = 5 AND l.")
					 .append(time).append("<=").append(statisticsDto.getEndTime()).append(" AND l.")
					 .append(time).append(">").append(statisticsDto.getStartTime()).append("),0) ")
					 //---不等于
					 .append(" != 0 ")
					  //---或者
					 .append(" OR ")
					  //---总发货量
					 .append(" IFNULL((SELECT SUM(ROUND(l.actualNum,3)) FROM t_pcs_goodslog l ,")
					 .append(" t_pcs_goods g WHERE g.cargoId=a.id AND l.goodsId=g.id AND l.type = 5 ")
					 .append(" AND l.").append(time).append(" <=").append(statisticsDto.getEndTime()).append(" ),0)")
					  //---不等于
					 .append(" != ")
					  //---货批总量(说明还有存量)
					 .append(" IFNULL((SELECT ROUND(SUM(l.goodsChange),3) FROM t_pcs_goodslog l ,t_pcs_goods g  ") 
					 .append(" WHERE l.goodsId=g.id AND g.cargoId=a.id AND l.type IN(1,7,10) ")
					 .append(" AND l.").append(time).append("<=").append(statisticsDto.getEndTime()).append("),0)")
					 //---
					 .append(")");
				}
			}
			
			if(!Common.empty(statisticsDto.getShowVir())){
				if(statisticsDto.getShowVir() == 1){//主库
					sql.append(" and b.id>0 and b.arrivalStartTime<=FROM_UNIXTIME(").append(statisticsDto.getEndTime()).append(")");
				}else if (statisticsDto.getShowVir() == 2) {//主副库
					sql.append(" and ( b.id = 0 or b.arrivalStartTime<=FROM_UNIXTIME(").append(statisticsDto.getEndTime()).append(")) ");
				}else if(statisticsDto.getShowVir() == 3) {
					sql.append(" and b.id = 0 ");
				}
			}else{
				sql.append(" and ( b.id=0 or b.arrivalStartTime<=FROM_UNIXTIME(").append(statisticsDto.getEndTime()).append(")) ");
			}
		 
		
			return executeQueryOne(sql.toString());
		} catch (OAException e) {
			e.printStackTrace();
		}
		return null;
	}
		

}
