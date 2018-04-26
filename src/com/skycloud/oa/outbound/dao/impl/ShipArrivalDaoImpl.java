package com.skycloud.oa.outbound.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.ArrivalPlanDto;
import com.skycloud.oa.inbound.model.Arrival;
import com.skycloud.oa.inbound.model.ArrivalPlan;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dao.ShipArrivalDao;
import com.skycloud.oa.outbound.dto.ShipArrivalDto;
import com.skycloud.oa.utils.Common;

/**
 * 
 * <p>出库管理---出港计划</p>
 * @ClassName:ShipArrivalDaoImpl
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年5月28日 上午11:23:54
 *
 */
@Component
public class ShipArrivalDaoImpl extends BaseDaoImpl implements ShipArrivalDao 
{
	/**
	 * 记录日志
	 */
	private static Logger logger = Logger.getLogger(ShipArrivalDaoImpl.class);
	
	@Override
	public void delete(String ids) throws OAException 
	{   
		String sql = "update t_pcs_arrival set type=9  where id in ("+ids+")" ;
		executeUpdate(sql) ;
	}

	/**
	 * 查询出港信息列表
	 * @Title:list
	 * @Description:
	 * @param shipArrivalDto
	 * @param pageView
	 * @return
	 * @throws OAException
	 * @see
	 */
	@Override
	public List<Map<String, Object>> list(ShipArrivalDto shipArrivalDto,PageView pageView) throws OAException 
	{   
		StringBuilder sql=new StringBuilder();
		
				sql.append("SELECT a.id id ,a.type type,a.status,DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') startTime,DATE_FORMAT(a.arrivalEndTime,'%Y-%m-%d') endTime,")
				.append("(select GROUP_CONCAT(j.actualType) from t_pcs_goodslog j where j.batchId=a.id and j.deliverType!=1 and j.type=5 ) actualType,")
				.append("  i.`name` productName,a.shipId shipId,b.name shipName,b.shipLenth,b.shipDraught,b.loadCapacity,b.shipWidth,a.shipRefId shipRefId,c.refName refName ")
				.append(" from t_pcs_arrival a LEFT JOIN t_pcs_ship b ON a.shipId = b.id LEFT JOIN t_pcs_ship_ref c ON a.shipRefId = c.id ")
				.append(" LEFT JOIN t_pcs_arrival_plan d ON d.arrivalId=a.id  ")
				.append(" LEFT JOIN t_pcs_product i on i.id=a.productId ");
				if(shipArrivalDto.getIsWarning()!=null&&shipArrivalDto.getIsWarning()==1){
				 sql.append(",( SELECT DISTINCT a.arrivalId from t_pcs_arrival_plan a LEFT JOIN t_pcs_goods d ON d.id=a.goodsId, ")
				.append(" (SELECT GROUP_CONCAT(b.ladingEvidence) ladingEvidence,b.batchId,b.goodsId  from t_pcs_goodslog b  ")
				.append("  WHERE  b.deliverType=2 and b.type=5  group BY b.batchId,b.goodsId )c ")
				.append(" WHERE a.type=2 and a.arrivalId=c.batchId AND a.goodsId=c.goodsId AND !ISNULL(d.id)  ")
				.append(" AND FIND_IN_SET(a.ladingCode,c.ladingEvidence)=0 ) m");
				}
				sql.append(" where 1=1 ");
		          if(shipArrivalDto.getType()!=null&&shipArrivalDto.getType()==1){
	                 sql.append(" and a.type=9");	        	  
		          }else{
	                    sql.append(" and (a.type=2 or a.type=5)");		        	  
		          }
		          if(shipArrivalDto.getIsShowAll()!=null&&shipArrivalDto.getIsShowAll()==0){
		        	  sql.append(" and a.status!=54 ");	 
		          }
		          if(shipArrivalDto.getIsWarning()!=null&&shipArrivalDto.getIsWarning()==1){
		        	  sql.append(" and a.id=m.arrivalId");
		          }
		         if(!Common.isNull(shipArrivalDto.getProductId())){
		        	 sql.append(" and a.productId=").append(shipArrivalDto.getProductId());
		         } 
		         if(!Common.isNull(shipArrivalDto.getLadingClientId())){
		        	  sql.append(" and d.ladingClientId=").append(shipArrivalDto.getLadingClientId());
		         }
		         if(!Common.isNull(shipArrivalDto.getLadingEvidence())){
		        	 sql.append(" and d.ladingCode='").append(shipArrivalDto.getLadingEvidence()).append("'");
		         }
				if(!Common.empty(shipArrivalDto.getShipName()))
				{
					sql.append(" and ( b.name like '%").append(shipArrivalDto.getShipName())
					.append("%' or c.refName like '%").append(shipArrivalDto.getShipName()).append("%')");
				}
				if(!Common.empty(shipArrivalDto.getStartTime())&&shipArrivalDto.getStartTime()!=-1){
					sql.append(" and UNIX_TIMESTAMP(a.arrivalStartTime) >=").append(shipArrivalDto.getStartTime());
				}
				if(!Common.empty(shipArrivalDto.getEndTime())&&shipArrivalDto.getEndTime()!=-1){
					sql.append(" and UNIX_TIMESTAMP(a.arrivalStartTime) <=").append(shipArrivalDto.getEndTime());
				}
       		sql.append(" GROUP BY a.id  order by id desc ");
		       if(pageView.getMaxresult()!=0){
		    	   sql.append(" limit ").append(pageView.getStartRecord()).append(",").append(pageView.getMaxresult());
		       }
		
		return executeQuery(sql.toString());
	}
	
	/**
	 * 查询出港信息列表总条数
	 * @Title:getCount
	 * @Description:
	 * @param shipArrivalDto
	 * @return
	 * @throws OAException
	 * @see
	 */
	@Override
	public int getCount(ShipArrivalDto shipArrivalDto) throws OAException {
		
		String sql = "SELECT count(DISTINCT a.id) FROM t_pcs_arrival a  left join t_pcs_ship b on a.shipId=b.id "
				+ "LEFT JOIN t_pcs_ship_ref c ON a.shipRefId = c.id  LEFT JOIN t_pcs_arrival_plan d ON d.arrivalId=a.id ";
		if(shipArrivalDto.getIsWarning()!=null&&shipArrivalDto.getIsWarning()==1){
			 sql+=",( SELECT DISTINCT a.arrivalId from t_pcs_arrival_plan a LEFT JOIN t_pcs_goods d ON d.id=a.goodsId, "+
			 " (SELECT GROUP_CONCAT(b.ladingEvidence) ladingEvidence,b.batchId,b.goodsId  from t_pcs_goodslog b  "+
			 "  WHERE  b.deliverType=2 and b.type=5  group BY b.batchId,b.goodsId )c "+
			 " WHERE a.type=2 and a.arrivalId=c.batchId AND a.goodsId=c.goodsId AND !ISNULL(d.id) "+
			 " AND FIND_IN_SET(a.ladingCode,c.ladingEvidence)=0 ) m" ;
			}
		sql+=" where 1=1 " ;
		 if(shipArrivalDto.getType()!=null&&shipArrivalDto.getType()==1){
             sql+=" and a.type=9";	        	  
          }else{
                sql+=" and (a.type=2 or a.type=5)";		        	  
          }
		 if(shipArrivalDto.getIsShowAll()!=null&&shipArrivalDto.getIsShowAll()==0){
       	  sql+=" and a.status!=54 ";	 
         }
		 if(shipArrivalDto.getIsWarning()!=null&&shipArrivalDto.getIsWarning()==1){
       	  sql+=" and a.id=m.arrivalId";
         }
		 if(!Common.isNull(shipArrivalDto.getProductId())){
        	 sql+=" and a.productId="+shipArrivalDto.getProductId();
         } 
         if(!Common.isNull(shipArrivalDto.getLadingClientId())){
        	  sql+=" and d.ladingClientId="+shipArrivalDto.getLadingClientId();
         }
         if(!Common.isNull(shipArrivalDto.getLadingEvidence())){
        	 sql+=" and d.ladingCode='"+shipArrivalDto.getLadingEvidence()+"'";
         }
		if(!Common.empty(shipArrivalDto.getShipName()))
		{
			sql+=" and ( b.name like '%"+shipArrivalDto.getShipName()+"%' or c.refName like '%"
		+shipArrivalDto.getShipName()+"%')";
		}
		if(!Common.empty(shipArrivalDto.getStartTime())&&shipArrivalDto.getStartTime()!=-1){
			sql+= " and UNIX_TIMESTAMP(a.arrivalStartTime) >="+shipArrivalDto.getStartTime();
		}
		if(!Common.empty(shipArrivalDto.getEndTime())&&shipArrivalDto.getEndTime()!=-1){
			sql+= " and UNIX_TIMESTAMP(a.arrivalStartTime) <="+shipArrivalDto.getEndTime();
		}
		return (int)getCount(sql);
	}
	
	@Override
	public List<Map<String, Object>> get(String id) throws OAException 
	{
		StringBuffer sql = new StringBuffer() ;
			sql.append(" SELECT a.id, a.type, a.`status`, a.shipId, a.shipRefId, a.description, ")
			.append(" a.createUserId, a.productId productId,a.reviewUserId,from_unixtime(a.createTime) createTime, ")
			.append(" DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') startTime,DATE_FORMAT(a.arrivalEndTime,'%Y-%m-%d') endTime, ")
			.append(" FROM_UNIXTIME(a.reviewTime) reviewTime, b.`name`  shipName,c.refName shipRefName,d.`name` productName, ")
			.append(" e.`name` createUserName,f.`name` reviewUserName ")
			.append(" FROM t_pcs_arrival a ")
			.append(" LEFT JOIN t_pcs_ship b ON b.id = a.shipId ")
			.append(" LEFT JOIN t_pcs_ship_ref c on c.id=a.shipRefId ")
			.append(" LEFT JOIN t_pcs_product d ON a.productId =d.id ")
			.append(" LEFT JOIN t_auth_user e ON e.id = a.createUserId ")
			.append(" LEFT JOIN t_auth_user f ON f.id = a.reviewUserId ")
			.append(" WHERE a.id =").append(id);
		return executeQuery(sql.toString());
	}
	
	@Override
	public void deleteShipPlanById(String arrivalPlanId) throws OAException {
		String sql = "delete from t_pcs_arrival_plan where id="+arrivalPlanId ;
		executeUpdate(sql) ;
	}

	@Override
	public void updateShipPlanStatus(String arrivalPlanId,Integer status) throws OAException {
		String sql = "update t_pcs_arrival_plan set status="+status+" where id="+arrivalPlanId;
		executeUpdate(sql) ;
	}
	public void updateArrivalStatus(String arrivalId) throws OAException{
		String sql = "update t_pcs_arrival set status = 50 where id="+arrivalId ;
		executeUpdate(sql) ;
	}
	@Override
	public List<Map<String, Object>> getArrivalPlan(String id) throws OAException {
		StringBuffer sb = new StringBuffer() ;
			sb.append("	SELECT a.id id,a.tradeType tradeType,i.value   tradeTypeValue,a.cargoId cargoId,b.code cargoCode,	");
			sb.append("	(case when a.type=2 THEN CONCAT(d.name,'/',e.refName,'/',DATE_FORMAT(c.arrivalStartTime,'%Y-%m-%d')) ELSE a.requirement END ) inboundMsg,	");
			sb.append("	a.clientId,f.name clientName,a.clearanceClientId,g.name clearanceClientName,a.ladingClientId,h.name ladingClientName,m.name productName, 	");
			sb.append("	a.status,IFNULL(l.actualType,-1) actualType,a.flow,a.isVerification,a.tankCodes,a.ladingCode,a.goodsTotal,a.goodsId,j.code goodsCode,a.actualLadingClientId,k.name actualLadingClientName ");
			sb.append("	FROM t_pcs_arrival_plan a	");
			sb.append("	LEFT JOIN t_pcs_cargo b on b.id=a.cargoId	");
			sb.append("	LEFT JOIN t_pcs_arrival c on c.id=b.arrivalId	");
			sb.append("	LEFT JOIN t_pcs_ship d on d.id=c.shipId	");
			sb.append("	LEFT JOIN t_pcs_ship_ref e on e.id=c.shipRefId	");
			sb.append("	LEFT JOIN t_pcs_client f on f.id=a.clientId	");
            sb.append("	LEFT JOIN t_pcs_client g on g.id=a.clearanceClientId	");
            sb.append("	LEFT JOIN t_pcs_client h on h.id=a.ladingClientId	");
            sb.append("	LEFT JOIN t_pcs_trade_type i on i.key=a.tradeType	");
            sb.append("	LEFT JOIN t_pcs_goods j on j.id=a.goodsId	");
            sb.append("	LEFT JOIN t_pcs_client k on k.id=a.actualLadingClientId	");
            sb.append("	LEFT JOIN t_pcs_product m on m.id=j.productId	");
            sb.append(" LEFT JOIN t_pcs_goodslog l on l.goodsId=a.goodsId and l.batchId=a.arrivalId and l.deliverType!=1 and l.type=5 ");
			sb.append("	where a.arrivalId="+id);
			sb.append(" group by a.id");
		return executeQuery(sb.toString());
	}
	
	@Override
	public List<Map<String, Object>> getGoodsList(int clientId, int productId) throws OAException {
		StringBuffer sb = new StringBuffer() ;
		sb.append("	SELECT 	");
		sb.append("	  g.id,	");
		sb.append("	  (SELECT id FROM t_pcs_lading WHERE id=gg.ladingId) lid,	");
		sb.append("	  (SELECT CODE FROM t_pcs_lading WHERE id=gg.ladingId) lcode,	");
		sb.append("	  g.code gcode,	");
		sb.append("	  t.code tcode,	");
		sb.append("	  round((g.goodsCurrent-(g.goodsTotal-(case when g.goodsInPass-g.goodsOutPass>=0 then g.goodsOutPass else g.goodsInPass end))),3) goodsCurrent 	");
		sb.append("	FROM	");
		sb.append("	  t_pcs_goods g 	");
		sb.append("	  LEFT JOIN t_pcs_goods_group gg ON g.goodsGroupId = gg.id 	");
		sb.append("	  LEFT JOIN t_pcs_tank t ON g.tankid = t.id	");
		sb.append("	WHERE (g.goodsCurrent-(g.goodsTotal-(case when g.goodsInPass-g.goodsOutPass>=0 then g.goodsOutPass else g.goodsInPass end)))>0 ");
		if(!Common.isNull(productId)){
			sb.append(" and g.productId="+productId) ;
		}
		if(!Common.isNull(clientId)){
			sb.append(" and ((g.clientId = "+clientId+" AND (ISNULL(g.ladingClientId) OR g.ladingClientId = 0))OR ladingClientId="+clientId+")") ;
		}
		return executeQuery(sb.toString()) ;
	}

	@Override
	public int add(Arrival arrival) throws OAException {
		Serializable s = save(arrival) ;
		return Integer.parseInt(s.toString());
	}

	@Override
	public int add(ArrivalPlan arrivalPlan) throws OAException {
		Serializable s = save(arrivalPlan) ;
		return Integer.parseInt(s.toString()) ;
	}

	
	public List<Map<String, Object>> getArrivalIdByShipId(String shipId)throws OAException{
		String sql = "select max(id) id from t_pcs_arrival where shipRefId="+shipId +" and type=2 and status in (0,50,51,52,53)" ;
		return executeQuery(sql) ;
	}
	
	
	@Override
	public void updateArrival(Arrival arrival) throws OAException {
		update(arrival) ;
	}

	@Override
	public void updatePlan(ArrivalPlan arrivalPlan) throws OAException {
		update(arrivalPlan) ;
	}

	@Override
	public List<Map<String, Object>> getGoodsMsg(ArrivalPlanDto aDto) throws OAException {
		try{
			if(aDto.getClientId()!=0&&aDto.getProductId()!=0){
				StringBuffer sql = new StringBuffer() ;
					sql.append(" SELECT a.id ,a.code goodsCode,b.id cargoId,b.code cargoCode,b.taxType taxType,  ");
					sql.append("  CONCAT(e.name,'/',f.refName,'/',DATE_FORMAT(d.arrivalStartTime,'%Y-%m-%d')) inboundMsg,");
					sql.append("  IFNULL(g.code,'')  tankCodes,n.name shangjihuozhu,DATE_FORMAT(i.startTime,'%Y-%m-%d') startTime, ");
					sql.append(" ROUND(a.goodsTotal-a.goodsOutPass,3) goodsSave, a.goodsTotal goodsTotal, a.goodsCurrent goodsCurrent, ");
					sql.append(" ROUND(SUM(m.deliverNum),3) deliverNum,DATE_FORMAT(i.endTime,'%Y-%m-%d') endTime,i.type ladingType, ");
					sql.append(" i.code diaohao,(CASE WHEN !ISNULL(a.rootGoodsId) and a.rootGoodsId!=0 THEN l.code WHEN ISNULL(i.code) THEN b.code ELSE i.code END ) yuanhao ");
					sql.append(" from t_pcs_goods a ");
					sql.append(" LEFT JOIN t_pcs_cargo b on a.cargoId=b.id ");
					sql.append(" LEFT JOIN t_pcs_arrival d on d.id=b.arrivalId  ");
					sql.append(" LEFT JOIN t_pcs_ship e on e.id=d.shipId  ");
					sql.append(" LEFT JOIN t_pcs_ship_ref f on f.id=d.shipRefId ");
					sql.append(" LEFT JOIN t_pcs_tank g on a.tankId=g.id  ");
					sql.append(" LEFT JOIN t_pcs_goods_group h on h.id=a.goodsGroupId ");
					sql.append(" LEFT JOIN t_pcs_lading i on i.id=h.ladingId ");
					sql.append(" LEFT JOIN t_pcs_client n ON n.id=i.clientId");
					sql.append(" LEFT JOIN t_pcs_goods j on j.id=a.rootGoodsId ");
					sql.append(" LEFT JOIN t_pcs_goods_group k on k.id=j.goodsGroupId ");
					sql.append(" LEFT JOIN t_pcs_lading l on l.id=k.ladingId ");
					sql.append(" LEFT JOIN t_pcs_goodslog m on m.goodsId=a.id and m.type=5 and m.actualNum=0 and m.actualType=0 ");
					sql.append(" where 1=1  ");
					if(aDto.getGoodsId()!=null){
						sql.append(" and a.id="+aDto.getGoodsId());	
						}else{
					sql.append(" and a.goodsCurrent>0 ");
					sql.append(" and   (( a.clientId="+aDto.getClientId()+" and (ISNULL(a.ladingClientId) or a.ladingClientId=0))  or a.ladingClientId="+aDto.getClientId()+") ");
					sql.append(" and a.productId="+aDto.getProductId());
						}
					sql.append(" GROUP BY a.id ORDER BY b.code ASC ");		
			return executeQuery(sql.toString());
			}
		}catch(RuntimeException e){
			logger.error("dao根据货主查询货批失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao根据货主查询货批失败", e);
		}
		return null;
	}

	@Override
	public Map<String, Object> getInvoiceByArrivalId(String id)
			throws OAException {
		try{
			String sql=" select count(1) count from t_pcs_goodslog a where a.type=5 and a.deliverType=2 and a.batchId="+id;
			return executeQueryOne(sql);
		}catch(RuntimeException e){
			logger.error("dao查询开票数量失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询开票数量失败", e);
		}
	}

	@Override
	public Map<String, Object> getArrivalPlanAmount(Integer goodsId)  throws OAException{
		try{
			if(goodsId!=null){
			
			String sql="SELECT ROUND(SUM(IFNULL(a.goodsTotal,0)),3) amount from t_pcs_arrival_plan a  "
                         +" LEFT JOIN t_pcs_arrival c ON a.arrivalId=c.id  "
						 +" LEFT JOIN t_pcs_goodslog b ON a.arrivalId=b.batchId and a.goodsId=b.goodsId  "
						 +" and  b.type=5 and b.deliverType>1 and a.ladingCode=b.ladingEvidence "
						 +" where a.type=2 and c.type=2 and ISNULL(b.id) and a.goodsId= "+goodsId
						 +" GROUP BY a.goodsId ";
			return executeQueryOne(sql);
			}
		}catch(RuntimeException e){
			logger.error("dao根据goodsId,查询该货体出港计划还未生成开票的计划量的和失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao根据goodsId,查询该货体出港计划还未生成开票的计划量的和失败", e);
		}
		return null;
	}

	@Override
	public Map<String, Object> getClearanceClientMsg(Integer goodsId) throws OAException {
		
		try{
			if(goodsId!=null){
			String sql="SELECT e.name clearanceClientName ,f.clearanceClientId "
							 +" from t_pcs_client e ,(SELECT (case WHEN ISNULL(a.rootGoodsId) AND ISNULL(a.sourceGoodsId) THEN "    
							 +" a.clearanceClientId "
							+"  WHEN !ISNULL(a.rootGoodsId) AND a.rootGoodsId=0 THEN "
							+" d.clearanceClientId "
							 +" WHEN a.rootGoodsId<>0 and a.sourceGoodsId<>0 THEN "
							+" c.clearanceClientId "
							 +" end "
							+" ) clearanceClientId "
							+" from t_pcs_goods a  "
							+" LEFT JOIN t_pcs_goods b on a.rootGoodsId=b.id "
							+" LEFT JOIN t_pcs_goods c on b.sourceGoodsId=c.id "
							+" LEFT JOIN t_pcs_goods d on a.sourceGoodsId=d.id "
							+" WHERE a.id="+goodsId+") f where f.clearanceClientId=e.id ";
			return executeQueryOne(sql);
			}
		}catch(RuntimeException e){
			logger.error("dao根据goodsId,查询该货体出港计划还未生成开票的计划量的和失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao根据goodsId,查询该货体出港计划还未生成开票的计划量的和失败", e);
		}
		return null;
	}

	/**
	 * @Title getIsEqual
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年10月28日上午11:07:19
	 * @throws
	 */
	@Override
	public Integer getIsEqual(Integer arrivalId) throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
			sql.append(" SELECT count(1)")
			.append("  FROM t_pcs_arrival_plan a ")
			.append(" ,(SELECT GROUP_CONCAT(b.ladingEvidence) ladingEvidence,b.batchId,b.goodsId  from t_pcs_goodslog b  ")
			.append(" WHERE  b.deliverType=2 and b.type=5 and b.batchId=")
			.append(arrivalId).append(" group BY b.batchId,b.goodsId) h  ")
			.append(" WHERE a.arrivalId=h.batchId and a.goodsId=h.goodsId  AND FIND_IN_SET(a.ladingCode,h.ladingEvidence)=0 ")
			.append(" and a.arrivalId=").append(arrivalId);
			return (int) getCount(sql.toString());
		} catch (RuntimeException e) {
			logger.error("dao查询计划与开票提单号是否一致失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao根据goodsId,查询该货体出港计划还未生成开票的计划量的和失败", e);
		}
	}

	/**
	 * @Title getDifPlanAndInvoiceMsg
	 * @Descrption:TODO
	 * @param:@param arrivalPlanDto
	 * @param:@return
	 * @auhor jiahy
	 * @date 2016年10月31日上午9:53:43
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> getDifPlanAndInvoiceMsg( ArrivalPlanDto arrivalPlanDto)throws OAException {
	try {
		StringBuilder sql=new StringBuilder();
			sql.append(" SELECT a.id,a.arrivalId, CONCAT(f.`name`,'/',g.refName,'/',DATE_FORMAT(e.arrivalStartTime,'%Y-%m-%d')) inboundMsg,a.goodsTotal, ")
			.append(" c.`code` goodsCode,d.code cargoCode, a.ladingCode,h.ladingEvidence,h.serial,h.deliverNum ")
			.append("  FROM t_pcs_arrival_plan a ")
			.append(" LEFT JOIN t_pcs_goods c ON c.id=a.goodsId ")
			.append(" LEFT JOIN t_pcs_cargo d ON d.id=c.cargoId ")
			.append(" LEFT JOIN t_pcs_arrival e ON e.id=d.arrivalId ")
			.append(" LEFT JOIN t_pcs_ship f ON f.id=e.shipId ")
			.append(" LEFT JOIN t_pcs_ship_ref g ON g.id=e.shipRefId, ")
			.append(" (SELECT GROUP_CONCAT(b.ladingEvidence) ladingEvidence,b.batchId,b.goodsId,b.serial,b.deliverNum from t_pcs_goodslog b  ")
			.append(" WHERE  b.deliverType=2 and b.type=5 and b.batchId=")
			.append(arrivalPlanDto.getArrivalId()).append(" group BY b.batchId,b.goodsId) h  ")
			.append(" WHERE a.arrivalId=h.batchId and a.goodsId=h.goodsId AND FIND_IN_SET(a.ladingCode,h.ladingEvidence)=0 ")
			.append(" and a.arrivalId=").append(arrivalPlanDto.getArrivalId());
		
		
		return executeQuery(sql.toString());
	} catch (RuntimeException e) {
		logger.error("dao获取不同计划开票数据失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取不同计划开票数据失败", e);
	}
	}

	/**
	 * @Title updateArrivalPlan
	 * @Descrption:TODO
	 * @param:@param arrivalPlan
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年10月31日下午3:29:41
	 * @throws
	 */
	@Override
	public void updateArrivalPlan(ArrivalPlan arrivalPlan) throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
				sql.append(" update t_pcs_arrival_plan set id=id ");
				if(!Common.isNull(arrivalPlan.getLadingCode()))
					sql.append(" ,ladingCode='").append(arrivalPlan.getLadingCode()).append("'");
				if(!Common.isNull(arrivalPlan.getId()))
					sql.append(" where id=").append(arrivalPlan.getId());
				else 
					sql.append(" where id=0 ");
			executeUpdate(sql.toString());
		} catch (RuntimeException e) {
			logger.error("dao更新到港计划失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新到港计划失败", e);
		}
	}
	
	
	
}
