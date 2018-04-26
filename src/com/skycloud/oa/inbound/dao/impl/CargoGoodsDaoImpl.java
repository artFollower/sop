package com.skycloud.oa.inbound.dao.impl;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.CargoGoodsDao;
import com.skycloud.oa.inbound.dto.CargoGoodsDto;
import com.skycloud.oa.inbound.dto.InboundOperationDto;
import com.skycloud.oa.inbound.model.Cargo;
import com.skycloud.oa.inbound.model.Goods;
import com.skycloud.oa.inbound.model.Cargo;
import com.skycloud.oa.inbound.service.impl.CargoGoodsServiceImpl;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.statistics.dto.StatisticsDto;
import com.skycloud.oa.utils.Common;

@Repository
public class CargoGoodsDaoImpl extends BaseDaoImpl implements CargoGoodsDao {
	private static Logger LOG = Logger.getLogger(CargoGoodsDaoImpl.class);

	/**
	 * 在货批表插入相应数据
	 * 
	 * @author yanyufeng
	 * @param cargo
	 */
	@Override
	public int addIntoCargo(Cargo cargo)throws OAException{
		try {
			return (Integer) save(cargo);
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}

	/**
	 * 添加货体表
	 * 
	 * @param goods
	 * @throws OAException
	 */
	@Override
	public  Serializable addGoods(Goods goods) throws OAException {
		try {
		return	save(goods);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}

	/**
	 * 获取货批列表信息
	 * 
	 * @param agDto
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 */
	public List<Map<String, Object>> getCargoList(CargoGoodsDto agDto,
			int start, int limit) throws OAException {
		try {
			String sql = "select  (select max(inboundNo) from t_pcs_cargo ) inboundNoCount,a.inboundNo,a.No,a.connectNo, a.clearanceClientId,p.name clearanceClientName,a.storageType,(select count(z.id) from t_pcs_goods z where z.cargoId=a.id) goodsCount, a.isDeclareCustom,a.isCustomAgree,a.customLading,a.customLadingCount,a.customPassCount,FROM_UNIXTIME(a.customLadingTime,'%Y-%m-%d') customLadingTime,a.inspectAgentIds,a.inspectAgentNames,a.fileUrl,a.goodsShip,a.goodsPlan,a.id id,a.code code,a.type type,a.taxType,n.value typeName,a.productId productId,b.name productName,a.clientId clientId,c.name clientName,"
					+ " a.contractId contractId,d.code contractCode,d.title contractTitle,d.lossRate ctLossRate,a.taxType taxType,e.value taxTypeValue,a.status status,a.requirement requirement, a.originalArea originalArea,a.cargoAgentId cargoAgentId,"
					+ " f.code cargoAgentCode,a.inspectAgentId inspectAgentId,g.code inspectAgentName,a.certifyAgentId certifyAgentId,h.name certifyAgentName,"
					+ " a.officeGrade officeGrade,k.value officeGradeValue,a.arrivalId arrivalId,DATE_FORMAT(i.arrivalStartTime,'%Y-%m-%d %H:%i:%s') arrivalTime,i.shipId shipId,j.code shipCode,j.name shipName, a.importId importId,"
					+ "a.goodsInspect goodsInspect,a.goodsTank goodsTank,a.goodsShip goodsShip,a.goodsTotal goodsTotal,a.goodsCurrent goodsCurrent,a.passStatus passStatus,m.value passStatusValue,"
					+ " a.goodsInPass goodsInPass,a.goodsOutPass goodsOutPass,a.description description ,a.goodsPlan goodsPlan,o.status cargoStatus,d.lossRate,c.code clientCode,a.passShipinspect  "
					+ " FROM t_pcs_cargo a "
					+ " INNER JOIN t_pcs_product b on a.productId=b.id"
					+ " INNER JOIN t_pcs_client c ON a.clientId=c.id"
					+ " LEFT JOIN t_pcs_contract d ON a.contractId=d.id"
					+ " LEFT JOIN t_pcs_tax_type e ON a.taxType=e.key"
					+ " LEFT JOIN t_pcs_cargo_agent f ON a.cargoAgentId=f.id"
					+ " LEFT JOIN t_pcs_inspect_agent g ON a.inspectAgentId=g.id"
					+ " LEFT JOIN t_pcs_certify_agent h on a.certifyAgentId=h.id"
					+ " LEFT JOIN t_pcs_arrival i ON a.arrivalId=i.id"
					+ " LEFT JOIN t_pcs_ship j ON i.shipId=j.id"
					+ " LEFT JOIN t_pcs_office_grade k ON a.officeGrade=k.key"
					+ " LEFT JOIN t_pcs_pass_status m ON a.passStatus=m.key"
					+ " LEFT JOIN t_pcs_trade_type n on a.taxType=n.key" +
					" LEFT JOIN t_pcs_work o on o.arrivalId=a.arrivalId and o.productId=a.productId and orderNum=0"
					+ " LEFT JOIN t_pcs_client p on p.id=a.clearanceClientId "
					+ " WHERE 1=1 ";
			
			
			if(!Common.isNull(agDto.getPassStatus())){
				if(Integer.parseInt(agDto.getPassStatus())==1){
					
					sql+=" and round(a.goodsOutPass,3)=round(a.goodsTotal,3) ";
				}
				else if(Integer.parseInt(agDto.getPassStatus())==2){
					
					sql+=" and round(a.goodsOutPass,3)<round(a.goodsTotal,3) and a.goodsOutPass!=0 ";
				}
				else if(Integer.parseInt(agDto.getPassStatus())==3){
					
					sql+=" and a.goodsOutPass=0 ";
				}else if(Integer.parseInt(agDto.getPassStatus())==4){
					
					sql+=" and a.goodsTank=a.goodsTotal and a.goodsTotal=a.goodsInspect and d.lossRate<>0 ";
				}
			}
			
			if(!Common.isNull(agDto.getTaxType())&&Integer.parseInt(agDto.getTaxType())!=13){
				sql+=" and a.taxType="+agDto.getTaxType();
			}
			
			if(!Common.isNull(agDto.getArrivalStatus())){ 
				sql+=" and (o.status>="+agDto.getArrivalStatus()+" or a.isPredict=1)";
				
				if(agDto.getArrivalStatus()==9){
					sql+=" and i.type<>3 ";
				}
				
			}
			if (!Common.empty(agDto.getCargoId())) {
				sql += " and a.id=" + agDto.getCargoId();
			}
			if (!Common.empty(agDto.getArrivalcode())) {
				sql += " and a.code like'%" + agDto.getArrivalcode() + "%'";
			}
			if (!Common.empty(agDto.getArrivalId())
					&& !Common.isNull(agDto.getArrivalId())) {
				sql += " and a.arrivalId=" + agDto.getArrivalId();
			}
			if(!Common.empty(agDto.getClientId())&&!Common.isNull(agDto.getClientId())){
				sql+=" and a.clientId="+agDto.getClientId();
			}
			if(!Common.empty(agDto.getProductId())&&!Common.isNull(agDto.getProductId())){
				sql+=" and a.productId="+agDto.getProductId();
			}
			if(!Common.empty(agDto.getClientName())){
				sql+=" and c.name like '%"+agDto.getClientName()+"%'";
			}
			
			if(!Common.isNull(agDto.getCargoStatus())){
				sql+=" and a.status="+agDto.getCargoStatus();
			}
			
			if (!Common.empty(agDto.getShipId())
					&& !Common.isNull(agDto.getShipId())) {
				sql += " and i.shipId=" + agDto.getShipId();
			}
			
			
			if(!Common.empty(agDto.getType())){
				if(agDto.getType()==1){
					sql+=" and i.arrivalStartTime is not null ";
				}
			}
			
			if (!Common.empty(agDto.getTankId())
					&& !Common.isNull(agDto.getTankId())) {
				sql += " and instr( (select GROUP_CONCAT(DISTINCT tankId) from t_pcs_goods where cargoId=a.id),'"+agDto.getTankId()+"')>0 ";
				
			}
			
			
			if(!Common.empty(agDto.getStartTime())&&!"-1".equals(agDto.getStartTime())){
				sql+=" and i.arrivalStartTime>='"+agDto.getStartTime()+"'";
			}
			if(!Common.empty(agDto.getEndTime())&&!"-1".equals(agDto.getEndTime())){
				sql+=" and i.arrivalStartTime<='"+agDto.getEndTime()+" 23:59:59'";
			}
			
			 sql+=" order by if(i.arrivalStartTime,1,0) ASC,i.arrivalStartTime DESC ";
			
//			sql+=" order by"
			
			if (limit != 0) {
				sql += " limit " + start + "," + limit;
			}
			return executeQuery(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}

	/**
	 * 获取货体列表信息
	 * 
	 * @param agDto
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 */
	@Override
	public List<Map<String, Object>> getGoodsList(CargoGoodsDto agDto,
			int start, int limit) throws OAException {
		try {
			String sql = "select UNIX_TIMESTAMP(b.signDate) signDate,z.name goodsLadingClientName,l.clientId goodsLadingClientId,w.period ,a.ladingClientId, a.clearanceClientId,v.name clearanceClientName,(SELECT   round(SUM(t.deliverNum),3) waitAmount FROM  t_pcs_goodslog t WHERE t.type=5 and t.actualNum=0 and  t.actualType=0  AND t.goodsId = a.id  ) goodsWait,a.customInPass,a.customOutPass,n.code rLadingCode,a.customsPassCode ,a.isPredict ,a.id id,a.tankCodes,a.code code,a.contractId contractId,b.code contractCode,a.goodsGroupId goodsGroupId,a.cargoId cargoId,a.clientId clientId,c.name clientName,a.productId productId,f.name productName,a.sourceGoodsId sourceGoodsId, a.rootGoodsId rootGoodsId,"
					+ "a.tankId tankId,d.code tankCode,DATE_FORMAT(a.createTime,'%Y-%m-%d %H:%i:%s') createTime,UNIX_TIMESTAMP(a.createTime) mCreateTime,a.lossRate lossRate,a.goodsInspect goodsInspect,a.goodsTank goodsTank,a.goodsTotal goodsTotal,a.goodsIn goodsIn,a.goodsOut goodsOut,"
					+ "a.goodsInPass goodsInPass,a.goodsOutPass goodsOutPass,a.goodsCurrent goodsCurrent,a.status status,a.description  description,o.name sourceClientName,q.code sLadingCode ,r.name sClientName , s.name ladingClientName,"
					+ "e.id  zid,e.code zcode,e.clientId zclientId,e.productId zproductId,e.ladingId zladingId,e.goodsInspect zgoodsInspect,e.goodsTank zgoodsTank,e.goodsTotal zgoodsTotal,e.goodsIn zgoodsIn,"
					+ "e.goodsOut zgoodsOut,e.goodsCurrent zgoodsCurrent,e.goodsInPass zgoodsInPass,e.goodsOutPass zgoodsOutPass,g.code cargoCode,UNIX_TIMESTAMP(h.arrivalStartTime) tArrivalStartTime ,h.arrivalStartTime,i.name shipName,j.code sourceCode,k.code rootCode,l.createTime ladingCreateTime,l.code ladingCode,l.type ladingType, l.startTime ladingStartTime,l.endTime ladingEndTime,l.isLong,u.code originGoodsCode "
					+ " FROM t_pcs_goods a "
					+ " LEFT JOIN t_pcs_contract b ON a.contractId=b.id"
					+ " INNER JOIN t_pcs_client c ON a.clientId=c.id"
					+ " LEFT JOIN t_pcs_tank d ON a.tankId=d.id"
					+ " LEFT JOIN t_pcs_goods_group e ON a.goodsGroupId=e.id "
					+ " INNER JOIN t_pcs_product f on a.productId=f.id"
					+ " INNER JOIN t_pcs_cargo g on a.cargoId=g.id"
					+ " LEFT JOIN t_pcs_arrival h on h.id=g.arrivalId"
					+ " LEFT JOIN t_pcs_ship i on i.id=h.shipId"
					+ " LEFT JOIN t_pcs_goods j on j.id=a.sourceGoodsId " 
					+ " LEFT JOIN t_pcs_goods k on k.id=a.rootGoodsId " 
					+ " LEFT JOIN t_pcs_lading l on e.ladingId=l.id "
					+ " LEFT JOIN t_pcs_client z on z.id=l.clientId "
					+ " LEFT JOIN t_pcs_goods_group m on k.goodsGroupId=m.id LEFT JOIN t_pcs_lading n on m.ladingId=n.id"
					+ " LEFT JOIN t_pcs_client o on o.id=j.clientId "
					+ " LEFT JOIN t_pcs_goods_group p on j.goodsGroupId=p.id LEFT JOIN t_pcs_lading q on p.ladingId=q.id "
					+" LEFT JOIN t_pcs_client r on r.id=j.clientId "
					+" LEFT JOIN t_pcs_client s on s.id=a.ladingClientId "
					+" LEFT JOIN t_pcs_client v on v.id=a.clearanceClientId "
					+" LEFT JOIN t_pcs_goods u on u.id=k.sourceGoodsId "
					+ " LEFT JOIN t_pcs_contract w ON g.contractId=w.id "
					
					+ " where 1=1 and a.status<>1  ";
			if(!Common.empty(agDto.getCode())){
				sql+=" and REPLACE(a.code,' ','') like '%"+agDto.getCode().replace(" ", "")+"%'";
			}
			if(!Common.empty(agDto.getClientName())){
				sql+=" and c.name like '%"+agDto.getClientName()+"%'";
			}
			if(!Common.isNull(agDto.getGoodsStatus())){
				sql+=" and a.status in("+agDto.getGoodsStatus()+")";
			}
			if (!Common.empty(agDto.getGoodsId())) {
				sql += " and a.id=" + agDto.getGoodsId();
			}
			if (!Common.empty(agDto.getClientId())&&!Common.isNull(agDto.getClientId())) {
				
				if(!Common.empty(agDto.getLadingType())&&!Common.isNull(agDto.getLadingType())){
					if(agDto.getLadingType()==1){
						sql+=" and a.clientId=" + agDto.getClientId()+" and (ISNULL(a.ladingClientId) or a.ladingClientId=0)";
						}else if(agDto.getLadingType()==2){
							sql+=" and ((a.clientId="+ agDto.getClientId()+" and (ISNULL(a.ladingClientId) or a.ladingClientId=0)) or a.ladingClientId="+agDto.getClientId()+")";
						}
				}else {
				sql += " and a.clientId=" + agDto.getClientId();
				}
			}
			if (!Common.empty(agDto.getProductId())&&!Common.isNull(agDto.getProductId())) {
				
				if(agDto.getProductId()!=-1){
					
					sql += " and a.productId=" + agDto.getProductId();
				}
			}
			if (!Common.empty(agDto.getCargoId())) {
				sql += " and a.cargoId=" + agDto.getCargoId();
			}
			if (!Common.empty(agDto.getGoodsGroupId())) {
				sql += " and a.goodsGroupId=" + agDto.getGoodsGroupId();
			}
			if(!Common.empty(agDto.getGoodsIds())){
				sql+=" and a.id in ("+agDto.getGoodsIds()+") ";
			}
			if(agDto.isTankGoods()){//是否是原始货体（不包含预入库货体）
				sql+=" and  ISNULL(a.rootGoodsId) ";
			}
			if(!Common.empty(agDto.getLadingId())){
				sql+=" and (e.ladingId<>"+agDto.getLadingId()+" or ISNULL(e.ladingId))";
			}
			if(!Common.isNull(agDto.getSourceGoodsId())){
				sql+=" and a.sourceGoodsId="+agDto.getSourceGoodsId();
			}
			if(!Common.isNull(agDto.getIsPredict())&&agDto.getIsPredict()==1){
				sql+=" and a.isPredict="+agDto.getIsPredict();
			}else if(!Common.isNull(agDto.getIsPredict())&&agDto.getIsPredict()==2){
				sql+=" and (a.isPredict!=1 or ISNULL(a.isPredict))";
			}
			
			if(!Common.isNull(agDto.getIsAll())){
				
				if(agDto.getIsAll()==1){
					
//					sql+=" and (a.goodsCurrent-COALESCE((SELECT   round(SUM(t.deliverNum),3) waitAmount FROM  t_pcs_goodslog t WHERE t.type=5 and t.actualNum=0 and  t.actualType=0  AND t.goodsId = a.id  ),0) -(a.goodsTotal-a.goodsOutPass))>0 ";
//					sql+=" and (a.goodsCurrent-COALESCE((SELECT   round(SUM(t.deliverNum),3) waitAmount FROM  t_pcs_goodslog t WHERE t.type=5 and t.actualNum=0 and  t.actualType=0  AND t.goodsId = a.id  ),0) )>0 ";
					sql+=" and a.goodsCurrent>0 ";
					
				}
			}
			
			
			if (limit != 0) {
				sql += "order by h.arrivalStartTime DESC limit " + start + "," + limit;
			}
			return executeQuery(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败", e);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}

	/**
	 * 获取货批数量
	 * 
	 * @param agDto
	 * @return
	 * @throws OAException
	 */
	@Override
	public int getCargoCount(CargoGoodsDto agDto) throws OAException {
		try {
			String sql = "select count(*)"
					+ " FROM t_pcs_cargo a "
					+ " INNER JOIN t_pcs_product b on a.productId=b.id"
					+ " INNER JOIN t_pcs_client c ON a.clientId=c.id"
					+ " LEFT JOIN t_pcs_contract d ON a.contractId=d.id"
					+ " LEFT JOIN t_pcs_tax_type e ON a.taxType=e.key"
					+ " LEFT JOIN t_pcs_cargo_agent f ON a.cargoAgentId=f.id"
					+ " LEFT JOIN t_pcs_inspect_agent g ON a.inspectAgentId=g.id"
					+ " LEFT JOIN t_pcs_certify_agent h on a.certifyAgentId=h.id"
					+ " LEFT JOIN t_pcs_arrival i ON a.arrivalId=i.id"
					+ " LEFT JOIN t_pcs_ship j ON i.shipId=j.id"
					+ " LEFT JOIN t_pcs_office_grade k ON a.officeGrade=k.key"
					+ " LEFT JOIN t_pcs_pass_status m ON a.passStatus=m.key" +
					" LEFT JOIN t_pcs_work o on o.arrivalId=a.arrivalId and o.productId=a.productId"
					+ " WHERE  1=1 ";
			if(!Common.isNull(agDto.getPassStatus())){
				if(Integer.parseInt(agDto.getPassStatus())==1){
					
					sql+=" and round(a.goodsOutPass,3)=round(a.goodsTotal,3) ";
				}
				else if(Integer.parseInt(agDto.getPassStatus())==2){
					
					sql+=" and round(a.goodsOutPass,3)<round(a.goodsTotal,3) and a.goodsOutPass!=0 ";
				}
				else if(Integer.parseInt(agDto.getPassStatus())==3){
					
					sql+=" and a.goodsOutPass=0 ";
				}else if(Integer.parseInt(agDto.getPassStatus())==4){
					
					sql+=" and a.goodsTank=a.goodsTotal and a.goodsTotal=a.goodsInspect and d.lossRate<>0 ";
				}
			}
			if(!Common.isNull(agDto.getTaxType())&&Integer.parseInt(agDto.getTaxType())!=13){
				sql+=" and a.taxType="+agDto.getTaxType();
			}
			
			if(!Common.isNull(agDto.getArrivalStatus())){
				sql+=" and (o.status>="+agDto.getArrivalStatus()+" or a.isPredict=1)";
				if(agDto.getArrivalStatus()==9){
					sql+=" and i.type<>3 ";
				}
			}
			if (!Common.empty(agDto.getCargoId())) {
				sql += " and a.id=" + agDto.getCargoId();
			}
			if (!Common.empty(agDto.getArrivalcode())) {
				sql += " and a.code like'%" + agDto.getArrivalcode() + "%'";
			}
			if (!Common.empty(agDto.getArrivalId())
					&& !Common.isNull(agDto.getArrivalId())) {
				sql += " and a.arrivalId=" + agDto.getArrivalId();
			}
			if(!Common.empty(agDto.getClientId())&&!Common.isNull(agDto.getClientId())){
				sql+=" and a.clientId="+agDto.getClientId();
			}
			if(!Common.empty(agDto.getProductId())&&!Common.isNull(agDto.getProductId())){
				sql+=" and a.productId="+agDto.getProductId();
			}
			if(!Common.empty(agDto.getClientName())){
				sql+=" and c.name like '%"+agDto.getClientName()+"%'";
			}
			if(!Common.isNull(agDto.getIsPredict())){
				sql+=" and a.isPredict="+agDto.getIsPredict();
			}
			if(!Common.isNull(agDto.getCargoStatus())){
				sql+=" and a.status="+agDto.getCargoStatus();
			}
			if(!Common.empty(agDto.getType())){
				if(agDto.getType()==1){
					sql+=" and i.arrivalStartTime is not null ";
				}
			}
			if(!Common.empty(agDto.getStartTime())&&!"-1".equals(agDto.getStartTime())){
				sql+=" and i.arrivalStartTime>='"+agDto.getStartTime()+"'";
			}
			if(!Common.empty(agDto.getEndTime())&&!"-1".equals(agDto.getEndTime())){
				sql+=" and i.arrivalStartTime<='"+agDto.getEndTime()+"'";
			}
			if (!Common.empty(agDto.getTankId())
					&& !Common.isNull(agDto.getTankId())) {
				sql += " and instr( (select GROUP_CONCAT(DISTINCT tankId) from t_pcs_goods where cargoId=a.id),'"+agDto.getTankId()+"')>0 ";
				
			}
			if (!Common.empty(agDto.getShipId())
					&& !Common.isNull(agDto.getShipId())) {
				sql += " and i.shipId=" + agDto.getShipId();
			}
			
			
			return (int) getCount(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}

	/**
	 * 获取货体数量
	 * 
	 * @param agDto
	 * @return
	 * @throws OAException
	 */
	@Override
	public int getGoodsCount(CargoGoodsDto agDto) throws OAException {
		try {
			String sql = "select  count(*)"
					+ " FROM t_pcs_goods a "
					+ " LEFT JOIN t_pcs_contract b ON a.contractId=b.id"
					+ " INNER JOIN t_pcs_client c ON a.clientId=c.id"
					+ " LEFT JOIN t_pcs_tank d ON a.tankId=d.id"
					+ " LEFT JOIN t_pcs_goods_group e ON a.goodsGroupId=e.id "
					+ " INNER JOIN t_pcs_product f on a.productId=f.id"
					+ " INNER JOIN t_pcs_cargo g on a.cargoId=g.id"
					+ " LEFT JOIN t_pcs_arrival h on h.id=g.arrivalId"
					+ " LEFT JOIN t_pcs_ship i on i.id=h.shipId"
					+ " LEFT JOIN t_pcs_goods j on j.id=a.sourceGoodsId " 
					+ " LEFT JOIN t_pcs_goods k on k.id=a.rootGoodsId " 
					+ " where 1=1 and a.status<>1  ";
			if(!Common.empty(agDto.getCode())){
				sql+=" and a.code like '%"+agDto.getCode()+"%'";
			}
			if(!Common.empty(agDto.getClientName())){
				sql+=" and c.name like '%"+agDto.getClientName()+"%'";
			}
			if(!Common.isNull(agDto.getGoodsStatus())){
				sql+=" and a.status in("+agDto.getGoodsStatus()+")";
			}
			if (!Common.empty(agDto.getGoodsId())) {
				sql += " and a.id=" + agDto.getGoodsId();
			}
			if (!Common.empty(agDto.getClientId())&&!Common.isNull(agDto.getClientId())) {
				
				if(!Common.empty(agDto.getLadingType())&&!Common.isNull(agDto.getLadingType())){
					if(agDto.getLadingType()==1){
						sql+=" and a.clientId=" + agDto.getClientId()+" and (ISNULL(a.ladingClientId) or a.ladingClientId=0)";
						}else if(agDto.getLadingType()==2){
							sql+=" and ((a.clientId="+ agDto.getClientId()+" and (ISNULL(a.ladingClientId) or a.ladingClientId=0)) or a.ladingClientId="+agDto.getClientId()+")";
						}
				}else{
				sql += " and a.clientId=" + agDto.getClientId();
				}
			}
			if (!Common.empty(agDto.getProductId())&&!Common.isNull(agDto.getProductId())) {
				sql += " and a.productId=" + agDto.getProductId();
			}
			if (!Common.empty(agDto.getCargoId())) {
				sql += " and a.cargoId=" + agDto.getCargoId();
			}
			if (!Common.empty(agDto.getGoodsGroupId())) {
				sql += " and a.goodsGroupId=" + agDto.getGoodsGroupId();
			}
			if(!Common.empty(agDto.getGoodsIds())){
				sql+=" and a.id in ("+agDto.getGoodsIds()+") ";
			}
			if(agDto.isTankGoods()){
				sql+=" and  ISNULL(a.rootGoodsId)";
			}
			if(!Common.empty(agDto.getLadingId())){
				sql+=" and (e.ladingId<>"+agDto.getLadingId()+" or ISNULL(e.ladingId))";
			}
			if(!Common.isNull(agDto.getSourceGoodsId())){
				sql+=" and a.sourceGoodsId="+agDto.getSourceGoodsId();
			}
			if(!Common.isNull(agDto.getIsPredict())){
				sql+=" and a.isPredict="+agDto.getIsPredict();
			}
			
			
				if(!Common.isNull(agDto.getIsAll())){
				
				if(agDto.getIsAll()==1){
					
					sql+=" and (a.goodsCurrent-COALESCE((SELECT   round(SUM(t.deliverNum),3) waitAmount FROM  t_pcs_goodslog t WHERE t.type=5 and t.actualNum=0 and  t.actualType=0  AND t.goodsId = a.id  ),0) -(a.goodsTotal-a.goodsOutPass))>0 ";
				}
			}
			return (int) getCount(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}

	/**
	 * 更新货批
	 * 
	 * @param cargo
	 * @throws OAException
	 */
	@Override
	public void updateCargo(Cargo cargo) throws OAException {
		try {
			String sql = "update t_pcs_cargo set id=id ";
			if (!Common.isNull(cargo.getCode())) {
				sql += " , code=" + "'" + cargo.getCode() + "'";
			}
			if (!Common.isNull(cargo.getProductId())) {
				sql += " , productId=" + cargo.getProductId();
			}
			if (!Common.isNull(cargo.getType())) {
				sql += " , type=" + cargo.getType();
			}
			if (!Common.isNull(cargo.getClientId())) {
				sql += " , clientId=" + cargo.getClientId();
			}
			if (!Common.isNull(cargo.getContractId())) {
				if(cargo.getContractId()==-1){
					sql += " , contractId=0";
				}else{
					
					sql += " , contractId=" + cargo.getContractId();
				}
			}
			if (!Common.isNull(cargo.getTaxType())) {
				sql += " , taxType=" + cargo.getTaxType();
			}
			if (!Common.isNull(cargo.getStatus())) {
				sql += " , status=" + cargo.getStatus();
			}
			if (!Common.isNull(cargo.getRequirement())) {
				sql += " , requirement=" + "'" + cargo.getRequirement() + "'";
			}
			if (!Common.isNull(cargo.getOriginalArea())) {
				sql += " , originalArea=" + "'" + cargo.getOriginalArea() + "'";
			}
			if (!Common.isNull(cargo.getStorageType())) {
				sql += " , storageType=" + "'" + cargo.getStorageType() + "'";
			}
			if (!Common.isNull(cargo.getCargoAgentId())) {
				sql += " , cargoAgentId=" + cargo.getCargoAgentId();
			}
			if (!Common.isNull(cargo.getInspectAgentId())) {
				sql += " , inspectAgentId=" + cargo.getInspectAgentId();
			}
			if (!Common.isNull(cargo.getCertifyAgentId())) {
				sql += " , certifyAgentId=" + cargo.getCertifyAgentId();
			}
			if (!Common.isNull(cargo.getOfficeGrade())) {
				sql += " , officeGrade=" + "'" + cargo.getOfficeGrade() + "'";
			}
			if (!Common.isNull(cargo.getArrivalId())) {
				sql += " ,  arrivalId=" + cargo.getArrivalId();
			}
			if (!Common.isNull(cargo.getImportId())) {
				sql += " ,  importId=" + cargo.getImportId();
			}
			if (!Common.isNull(cargo.getGoodsTotal())) {
				sql += " ,  goodsTotal=" + "'" + cargo.getGoodsTotal() + "'";
			}
			if (!Common.isNull(cargo.getGoodsCurrent())) {
				sql += " ,  goodsCurrent=" + "'" + cargo.getGoodsCurrent()
						+ "'";
			}
			if (!Common.isNull(cargo.getGoodsInspect())) {
				sql += " ,  goodsInspect=" + "'" + cargo.getGoodsInspect()
						+ "'";
			}
			if (!Common.isNull(cargo.getGoodsTank())) {
				sql += " ,  goodsTank=" + "'" + cargo.getGoodsTank()
						+ "'";
			}
			if (!Common.isNull(cargo.getGoodsShip())) {
				sql += " ,  goodsShip=" + "'" + cargo.getGoodsShip()
						+ "'";
			}
			if (!Common.isNull(cargo.getLossRate())) {
				sql += " ,  lossRate=" + "'" + cargo.getLossRate()
						+ "'";
			}
			if (!Common.isNull(cargo.getGoodsCurrent())) {
				sql += " ,  goodsCurrent=" + "'" + cargo.getGoodsCurrent()
						+ "'";
			}
			if (!Common.isNull(cargo.getGoodsPlan())) {
				sql += " ,  goodsPlan=" + "'" + cargo.getGoodsPlan()
						+ "'";
			}
			if (!Common.isNull(cargo.getPassStatus())) {
				sql += " ,  passStatus=" + cargo.getPassStatus();
			}
			if (!Common.isNull(cargo.getGoodsInPass())) {
				sql += " ,  goodsInPass='" + cargo.getGoodsInPass()+"'";
			}
			if (!Common.isNull(cargo.getGoodsOutPass())) {
				sql += " ,  goodsOutPass='" + cargo.getGoodsOutPass()+"'";
			}
			if (!Common.isNull(cargo.getCustomPassCount())) {
				sql += " ,  customPassCount='" + cargo.getCustomPassCount()+"'";
			}
			
			if (!Common.isNull(cargo.getCustomLadingTime())) {
				sql += " ,  customLadingTime=" + cargo.getCustomLadingTime();
			}
			
			if (!Common.isNull(cargo.getDescription())) {
				sql += ", description='"  + cargo.getDescription() + "'";

			}
			if(!Common.isNull(cargo.getIsPredict())){
				sql+=" , isPredict="+cargo.getIsPredict();
			}
			if(!Common.isNull(cargo.getIsInspect())){
				sql+=" , isInspect="+cargo.getIsInspect();
			}
			if(!Common.isNull(cargo.getFileUrl())){
				sql+=" , fileUrl='"+cargo.getFileUrl()+"'";
			}
			if(cargo.getInspectAgentIds()!=null){
				sql+=" , inspectAgentIds='"+cargo.getInspectAgentIds()+"'";
			}
			if(cargo.getInspectAgentNames()!=null){
				sql+=" , inspectAgentNames='"+cargo.getInspectAgentNames()+"'";
			}
			if(cargo.getIsFinish()!=null){
				sql+=" ,isFinish="+cargo.getIsFinish();
			}
			
			if(cargo.getIsCustomAgree()!=null){
				sql+=" ,isCustomAgree="+cargo.getIsCustomAgree();
			}
			if(cargo.getIsDeclareCustom()!=null){
				sql+=" ,isDeclareCustom="+cargo.getIsDeclareCustom();
			}
			if(!Common.empty(cargo.getCustomLading())){
				sql+=" ,customLading='"+cargo.getCustomLading()+"'";
			}
			if(!Common.empty(cargo.getCustomLadingCount())){
				sql+=" ,customLadingCount='"+cargo.getCustomLadingCount()+"'";
			}
			
			if(!Common.isNull(cargo.getClearanceClientId())){
				sql+=" , clearanceClientId="+cargo.getClearanceClientId();
			}
			
			if(!Common.empty(cargo.getPassShipinspect())){
				sql+=" ,passShipinspect='"+cargo.getPassShipinspect()+"'";
			}
			
			if (!Common.isNull(cargo.getId())) {
				sql += " where id=" + cargo.getId();
			}
			executeUpdate(sql);

		} catch (RuntimeException e) {
			LOG.error("dao更新功能");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新失败", e);
		}
	}

	/**
	 * 更新货体
	 * 
	 * @param goods
	 * @throws OAException
	 */
	@Override
	public void updateGoods(Goods goods) throws OAException {
		try {
			String sql = "update t_pcs_goods set id=id";

			if (!Common.isNull(goods.getCode())) {
				sql += " , code=" + "'" + goods.getCode() + "'";
			}
			if (!Common.isNull(goods.getCargoId())) {
				sql += " , cargoId=" + goods.getCargoId();
			}
			if (!Common.isNull(goods.getContractId())) {
				sql += " , contractId=" + goods.getContractId();
			}
			if (!Common.isNull(goods.getGoodsGroupId())) {
				sql += " , goodsGroupId=" + goods.getGoodsGroupId();
			}
			if (!Common.isNull(goods.getClientId())) {
				sql += " , clientId=" + goods.getClientId();
			}
			if (!Common.isNull(goods.getClearanceClientId())) {
				sql += " , clearanceClientId=" + goods.getClearanceClientId();
			}
			if (!Common.isNull(goods.getSourceGoodsId())) {
				sql += " , sourceGoodsId=" + goods.getSourceGoodsId();
			}
			if (!Common.isNull(goods.getRootGoodsId())) {
				sql += " , rootGoodsId=" + goods.getRootGoodsId();
			}
			if (!Common.isNull(goods.getTankId())) {
				if(goods.getTankId()==-1){
					sql += " , tankId=null ";
				}else{
					
					sql += " , tankId=" + goods.getTankId();
				}
			}
			if (!Common.empty(goods.getCreateTime())) {
				sql += " , createTime=" + "'" + goods.getCreateTime() + "'";
			}
			if (!Common.empty(goods.getRecordTime())) {
				sql += " , recordTime=" + "'" + goods.getRecordTime() + "'";
			}
			if (!Common.isNull(goods.getLossRate())) {
				sql += " , lossRate=" + goods.getLossRate();
			}
			if (!Common.isNull(goods.getGoodsInspect())) {
				sql += " , goodsInspect=" + "'" + goods.getGoodsInspect() + "'";
			}
			if (!Common.isNull(goods.getGoodsTank())) {
				sql += " , goodsTank=" + "'" + goods.getGoodsTank() + "'";
			}
			if (!Common.isNull(goods.getGoodsTotal())) {
				sql += " , goodsTotal=" + "'" + goods.getGoodsTotal() + "'";
			}
			if (!Common.isNull(goods.getGoodsIn())) {
				sql += " , goodsIn=" + "'" + goods.getGoodsIn() + "'";
			}
			if (!Common.isNull(goods.getGoodsOut())) {
				sql += " , goodsOut=" + "'" + goods.getGoodsOut() + "'";
			}
			if (!Common.isNull(goods.getGoodsCurrent())) {
				sql += " , goodsCurrent=" + "'" + goods.getGoodsCurrent() + "'";
			}
			if (!Common.isNull(goods.getGoodsInPass())) {
				sql += " , goodsInPass=" + "'" + goods.getGoodsInPass() + "'";
			}
			if(goods.getTankCodes()!=null){
				sql+=" ,tankCodes='"+goods.getTankCodes()+"'";
			}
			if (!Common.isNull(goods.getGoodsOutPass())) {
				sql += " , goodsOutPass=" + "'" + goods.getGoodsOutPass() + "'";
			}
			if (!Common.isNull(goods.getCustomInPass())) {
				sql += " , customInPass=" + "'" + goods.getCustomInPass() + "'";
			}
			if (!Common.isNull(goods.getCustomOutPass())) {
				sql += " , customOutPass=" + "'" + goods.getCustomOutPass() + "'";
			}
			
			if (goods.getStatus()!=null) {
				sql += " , status=" + "'" + goods.getStatus() + "'";
			}
			if (!Common.isNull(goods.getDescription())) {
				sql += " , description=" + "'" + goods.getDescription() + "'";
			}
			if(!Common.isNull(goods.getIsPredict())){
				sql+=" ,isPredict="+goods.getIsPredict();
			}
			if(!Common.isNull(goods.getIsAccountComp())){
				sql+=" ,isAccountComp="+goods.getIsAccountComp();
			}
			if(!Common.isNull(goods.getIsFinish())){
				sql+=" , isFinish="+goods.getIsFinish();
			}
			
			if (!Common.isNull(goods.getId())) {
				sql += " where id=" + goods.getId();
			}
			
			executeUpdate(sql);
		} catch (RuntimeException e) {
			LOG.error("dao更新功能");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新失败", e);
		}

	}

	/**
	 * 获取指定货批与货体的关联数据
	 * 
	 * @param agDto
	 * @return
	 * @throws OAException
	 */
	@Override
	public List<Map<String, Object>> getAppointCargoGoodsData(int id)
			throws OAException {
		try {
			String sql = "SELECT a.id  CGid,b.id GDid, a.goodsTotal CGgoodsTotal,b.goodsTotal GDgoodsTotal,a.goodsInspect CGgoodsInspect,b.goodsInspect GDgoodsInspect,"
					+ " a.goodsTank CGgoodsTank,b.goodsTank GDgoodsTank,a.goodsCurrent CGgoodsCurrent,b.goodsCurrent GDgoodsCurrent,"
					+ " a.goodsInPass CGgoodsInPass,b.goodsInPass GDgoodsInPass,a.goodsOutPass CGgoodsOutPass,b.goodsOutPass GDgoodsOutPass"
					+ " FROM t_pcs_cargo a "
					+ " LEFT JOIN t_pcs_goods b ON a.id=b.cargoId"
					+ " where a.id=" + id;
			return executeQuery(sql);
		} catch (RuntimeException e) {
			LOG.error("dao更新功能");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新失败", e);
		}
	}

	/**
	 * 获得相同合同的货批数量
	 * 
	 * @author yanyufeng
	 * @param code
	 * @return
	 * @throws OAException
	 */
	@Override
	public int getTheSameCargo(String code,boolean isLike) throws OAException {
		String codeSql="";
		if(isLike){
			codeSql = "SELECT COUNT(*) FROM t_pcs_cargo WHERE code like '%"
					+ code + "%'";
		}else{
			codeSql = "SELECT COUNT(*) FROM t_pcs_cargo WHERE code = '"+ code + "'";
		}
		int count = (int) getCount(codeSql);
		return count;
	}

	/**
	 * 货批拆分,获得相同合同的货体数量
	 * 
	 * @param code
	 * @return
	 * @throws OAException
	 */
	public int getTheSameGoods(String code,Integer oldGoodsId) throws OAException {
		String codeSql = "select count(*) from t_pcs_goods where sourceGoodsId="+oldGoodsId+" and  code like '%"
				+ code.substring(0, code.length() - 2) + "%'";
		return (int) getCount(codeSql);
	}
	
	
	/**
	 * 获得相同合同的货体数量
	 * 
	 * @param code
	 * @return
	 * @throws OAException
	 */
	public int getTheSameGoods(String code) throws OAException {
		String codeSql = "select count(*) from t_pcs_goods where code like '%"
				+ code.substring(0, code.length() - 2) + "%'";
		return (int) getCount(codeSql);
	}
	

	@Override
	public void deleteGoods(String ids) throws OAException{
	try{
		String sql=" delete from t_pcs_goods where id in ("+ids+")";
		execute(sql);
		String sql1=" delete from t_pcs_goodslog where goodsId in ("+ids+")";
		execute(sql1);
	} catch (RuntimeException e) {
		LOG.error("dao更新功能");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新失败", e);
	}	
	}
    
	@Override
	public void deleteGoodsByCargoId(Integer id) throws OAException{
	try{
		String sql=" delete from t_pcs_goods where cargoId in ("+id+") and status=2";
		execute(sql);
	} catch (RuntimeException e) {
		LOG.error("dao更新功能");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新失败", e);
	}	
	} 
	@Override
	public int getTheSameGoodsCount(String code) {
		String sql="select count(id) from t_pcs_goods where code like '%"+code+"%'";
		return (int) getCount(sql);
		
	}

	@Override
	public void cleanGoods(int id,boolean istrue)throws OAException {
      try{
    	  String sql=null;
    	  if(istrue){
    		  sql="delete from t_pcs_goods where cargoId in (select b.id from t_pcs_cargo b where b.productId="+id+" ) and (isPredict is null or isPredict!=1) and  ISNULL(rootGoodsId)";
    	  }else{
    		   sql="delete from t_pcs_goods where cargoId in (select b.id from t_pcs_cargo b where b.arrivalId="+id+" ) and (isPredict is null or isPredict!=1) and ISNULL(rootGoodsId)";
    	  }
    	  execute(sql);
    	  if(istrue){
    		  sql="update t_pcs_goods set isPredict=1 where cargoId in (select b.id from t_pcs_cargo b where b.productId="+id+" ) and isPredict=2";
    	  }else{
    		  sql="update t_pcs_goods set isPredict=1 where cargoId in (select b.id from t_pcs_cargo b where b.arrivalId="+id+" ) and isPredict=2";
    	  }
    	  executeUpdate(sql);
      }catch(RuntimeException e){
    	  LOG.error("dao清空货体失败");
    	  throw new OAException(Constant.SYS_CODE_DB_ERR, "dao清空货体失败",e);
      }		
	}

	@Override
	public List<Map<String, Object>> getCargo(StatisticsDto sDto, int start,
			int limit) throws OAException {
		try{
		String sql="select g.name productName,a.id cargoId,a.clientId,e.name clientName,f.name shipName, a.code ,d.shipId,d.arrivalStartTime,a.isInspect,a.goodsTank  from t_pcs_cargo a " +
				" LEFT JOIN t_pcs_arrival d on a.arrivalId=d.id " +
				" LEFT JOIN t_pcs_client e on e.id=a.clientId " +
				" LEFT JOIN t_pcs_ship f on f.id=d.shipId " +
				" INNER JOIN t_pcs_product g on g.id=a.productId " +
//				"  where (select sum(g.actualNum) from t_pcs_goodslog g LEFT JOIN t_pcs_goods h on h.id=g.goodsId LEFT JOIN t_pcs_cargo i on h.cargoId=i.id where i.id=a.id and g.createTime<"+(sDto.getStartTime()+86400)+" and g.type=5) is not null  and (a.goodsTotal<>(select sum(g.actualNum) from t_pcs_goodslog g LEFT JOIN t_pcs_goods h on h.id=g.goodsId LEFT JOIN t_pcs_cargo i on h.cargoId=i.id where i.id=a.id and g.createTime<"+(sDto.getStartTime())+" and g.type=5) or (select sum(g.actualNum) from t_pcs_goodslog g LEFT JOIN t_pcs_goods h on h.id=g.goodsId LEFT JOIN t_pcs_cargo i on h.cargoId=i.id where i.id=a.id and g.createTime<"+(sDto.getStartTime())+" and g.type=5) is null ) ";
				"  where (a.goodsTotal<>(select sum(g.actualNum) from t_pcs_goodslog g LEFT JOIN t_pcs_goods h on h.id=g.goodsId LEFT JOIN t_pcs_cargo i on h.cargoId=i.id where i.id=a.id and g.createTime<"+(sDto.getStartTime()+86400)+" and g.type=5) or (select sum(g.actualNum) from t_pcs_goodslog g LEFT JOIN t_pcs_goods h on h.id=g.goodsId LEFT JOIN t_pcs_cargo i on h.cargoId=i.id where i.id=a.id and g.createTime<"+(sDto.getStartTime()+86400)+" and g.type=5) is null ) ";
				if(!Common.isNull(sDto.getProductId())){
					sql+=" and a.productId="+sDto.getProductId();
				}
				if(!Common.isNull(sDto.getClientId())){
					sql+=" and a.clientId="+sDto.getClientId();
				}
				if(!Common.isNull(sDto.getStartTime())){
					sql+=" and d.arrivalStartTime<'"+(new Timestamp((sDto.getStartTime()+86400)*1000))+"'";
				}
				if(limit!=0){
					sql+=" limit "+start+","+limit;
				}
				return executeQuery(sql);
			} catch (RuntimeException e) {
				LOG.error("查询失败");
				throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败", e);
			}
	}

	@Override
	public int getCargoCount(StatisticsDto sDto) throws OAException {
		try{
			String sql="select count(*)  from t_pcs_cargo a " +
					" LEFT JOIN t_pcs_arrival d on a.arrivalId=d.id " +
					" LEFT JOIN t_pcs_client e on e.id=a.clientId " +
					" LEFT JOIN t_pcs_ship f on f.id=d.shipId " +
//					"  where (select sum(g.actualNum) from t_pcs_goodslog g LEFT JOIN t_pcs_goods h on h.id=g.goodsId LEFT JOIN t_pcs_cargo i on h.cargoId=i.id where i.id=a.id and g.createTime<"+(sDto.getStartTime()+86400)+" and g.type=5) is not null  and (a.goodsTotal<>(select sum(g.actualNum) from t_pcs_goodslog g LEFT JOIN t_pcs_goods h on h.id=g.goodsId LEFT JOIN t_pcs_cargo i on h.cargoId=i.id where i.id=a.id and g.createTime<"+(sDto.getStartTime())+" and g.type=5) or (select sum(g.actualNum) from t_pcs_goodslog g LEFT JOIN t_pcs_goods h on h.id=g.goodsId LEFT JOIN t_pcs_cargo i on h.cargoId=i.id where i.id=a.id and g.createTime<"+(sDto.getStartTime())+" and g.type=5) is null ) ";
					"  where (a.goodsTotal<>(select sum(g.actualNum) from t_pcs_goodslog g LEFT JOIN t_pcs_goods h on h.id=g.goodsId LEFT JOIN t_pcs_cargo i on h.cargoId=i.id where i.id=a.id and g.createTime<"+(sDto.getStartTime()+86400)+" and g.type=5) or (select sum(g.actualNum) from t_pcs_goodslog g LEFT JOIN t_pcs_goods h on h.id=g.goodsId LEFT JOIN t_pcs_cargo i on h.cargoId=i.id where i.id=a.id and g.createTime<"+(sDto.getStartTime()+86400)+" and g.type=5) is null ) ";
					if(!Common.isNull(sDto.getProductId())){
						sql+=" and a.productId="+sDto.getProductId();
					}
					if(!Common.isNull(sDto.getClientId())){
						sql+=" and a.clientId="+sDto.getClientId();
					}
					if(!Common.isNull(sDto.getStartTime())){
						sql+=" and d.arrivalStartTime<'"+(new Timestamp((sDto.getStartTime()+86400)*1000))+"'";
					}
					
		return (int) getCount(sql);
	} catch (RuntimeException e) {
		LOG.error("dao查询失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
	}
	}

	@Override
	public int getGoodsCount(StatisticsDto sDto,boolean isShowAll) throws OAException {
//		String sql="select count(DISTINCT(goodsId))  from t_pcs_goodslog where createTime>= "+sDto.getStartTime() +" and createTime<"+(sDto.getEndTime()+86400);
		
//		String sql="select count(DISTINCT(a.goodsId)) from t_pcs_goodslog a " +
//				"LEFT JOIN t_pcs_goods b on b.id=a.goodsId where a.createTime>= "+sDto.getStartTime() +" and a.createTime<"+(sDto.getEndTime()+86400)+" ";
////		"LEFT JOIN t_pcs_goods b on b.id=a.goodsId where  a.createTime<"+(sDto.getEndTime()+86400)+" ";
//		if(!Common.isNull(sDto.getProductId())){
//			sql+=" and b.productId="+sDto.getProductId();
//		}
//		if(!Common.isNull(sDto.getClientId())){
//			sql+=" and b.clientId="+sDto.getClientId();
//		}
//		if(!Common.empty(sDto.getCode())){
//			sql+=" and REPLACE(b.code,' ','') like '%"+sDto.getCode().replace(" ", "")+"%'";
//		}
		
		String time="createTime";
		if(!Common.isNull(sDto.getShowVirTime())){
			if(sDto.getShowVirTime()==2){
				time="originalTime";
			}
		}
//		String sql="select count(DISTINCT (a.id)) from t_pcs_goods a LEFT JOIN t_pcs_goodslog b on b.goodsId=a.id where  (select (c.surplus+c.goodsSave) goodsSave from t_pcs_goodslog c where c.goodsId=a.id and c.createTime<"+(sDto.getEndTime()+86400)+" ORDER BY c.createTime DESC limit 0,1)>0 ";
		String sql="";
		if(isShowAll){
			 sql="select count(DISTINCT (a.id)) from t_pcs_goods a LEFT JOIN t_pcs_cargo d on d.id=a.cargoId LEFT JOIN t_pcs_goodslog b on b.goodsId=a.id where !isnull(a.id) and a.createTime<FROM_UNIXTIME("+(sDto.getEndTime() + 86400)+")";
			
		}
		else{
			
			sql="select  count(DISTINCT (a.id)) from t_pcs_goods a LEFT JOIN t_pcs_cargo d on d.id=a.cargoId LEFT JOIN t_pcs_goodslog b on b.goodsId=a.id where ((select count(id) from t_pcs_goodslog e where e.goodsId=a.id and e."+time+">"+sDto.getStartTime()+" and e."+time+" < "+(sDto.getEndTime()+86400)+")>0 or (select round(sum(c.goodsChange),3) goodsSave from t_pcs_goodslog c where 	c.type<>8 AND if(c.type=5 and c.actualType=0 ,0,1)=1 and c."+time+" < "+(sDto.getEndTime()+86400)+" and c.goodsId=a.id )<>0 )";
			
		}
		
		
		if(!Common.isNull(sDto.getCargoCode()))
		{
			sql+=" and REPLACE(d.code,' ','') like '%"+sDto.getCargoCode().replace(" ", "")+"%'";
		}
		
		if(!Common.isNull(sDto.getProductId())){
			sql+=" and a.productId="+sDto.getProductId();
		}
		if(!Common.isNull(sDto.getClientId()))
		{
			sql+=" and ((a.clientId="+sDto.getClientId()+" and (ISNULL(a.ladingClientId) or a.ladingClientId=0)) or a.ladingClientId="+sDto.getClientId()+")";
		}
		if(!Common.empty(sDto.getCode())){
			sql+=" and REPLACE(a.code,' ','') like '%"+sDto.getCode().replace(" ", "")+"%'";
		}
		if(!Common.isNull(sDto.getTankId()))
		{
			sql+=" and a.tankId="+sDto.getTankId();
		}
		if(!Common.empty(sDto.getShowVir())){
			
			if(sDto.getShowVir()==1){
				
				sql+=" and d.arrivalId<>0";
				
			}
			if(sDto.getShowVir()==3){
				
				sql+=" and d.arrivalId=0";
				
			}
		}
		sql+=" order by a.createTime ASC ";
		
		return (int) getCount(sql);
	}

	@Override
	public void cleanGoods(int id, Integer productId, boolean istrue)throws OAException {
		 try{
	    	  String sql=null;
	    	  if(istrue){
	    		  sql="delete from t_pcs_goods where cargoId in (select b.id from t_pcs_cargo b where b.productId="+productId+" and b.arrivalId="+id+") and (isPredict is null or isPredict!=1) and  ISNULL(sourceGoodsId)";
	    	  }else{
	    		   sql="delete from t_pcs_goods where cargoId in (select b.id from t_pcs_cargo b where b.arrivalId="+id+" ) and (isPredict is null or isPredict!=1) and ISNULL(sourceGoodsId)";
	    	  }
	    	  execute(sql);
	    	  if(istrue){
	    		  sql="update t_pcs_goods set isPredict=1 where cargoId in (select b.id from t_pcs_cargo b where b.productId="+productId+" and b.arrivalId="+id+" ) and isPredict=2";
	    	  }else{
	    		  sql="update t_pcs_goods set isPredict=1 where cargoId in (select b.id from t_pcs_cargo b where b.arrivalId="+id+" ) and isPredict=2";
	    	  }
	    	  executeUpdate(sql);
	      }catch(RuntimeException e){
	    	  LOG.error("dao清空货体失败");
	    	  throw new OAException(Constant.SYS_CODE_DB_ERR, "dao清空货体失败",e);
	      }	
	}

	@Override
	public void cleanCargo(Integer id) throws OAException {
		try{
	    	  String sql=null;
	    		  sql="update t_pcs_cargo set goodsTank=null where  arrivalId="+id;
	    	  executeUpdate(sql);
	      }catch(RuntimeException e){
	    	  LOG.error("dao清空货体失败");
	    	  throw new OAException(Constant.SYS_CODE_DB_ERR, "dao清空货体失败",e);
	      }	
	}

	@Override
	public void cleanCargo(Integer id, Integer productId) throws OAException {
		try{
	    	  String sql=null;
	    		  sql="update t_pcs_cargo set goodsTank=null where arrivalId="+id+" and productId="+productId;
	    	  executeUpdate(sql);
	      }catch(RuntimeException e){
	    	  LOG.error("dao清空货体失败");
	    	  throw new OAException(Constant.SYS_CODE_DB_ERR, "dao清空货体失败",e);
	      }	
	}


	@Override
	public List<Map<String, Object>>  getGoodsTankCodesByCargoId(int cargoId)
			throws OAException {
		try{  
	    	   String  sql="SELECT (CASE WHEN ISNULL(a.tankCodes) THEN GROUP_CONCAT(b.code) ELSE a.tankCodes END) tankCodes from t_pcs_goods a LEFT JOIN t_pcs_tank b on b.id=a.tankId" 
	    			   +" WHERE (isPredict is null or isPredict!=1)and  ISNULL(sourceGoodsId) and a.cargoId="+cargoId+" GROUP BY a.cargoId";
	    	 return executeQuery(sql);
	      }catch(RuntimeException e){
	    	  LOG.error("dao 获取罐号失败");
	    	  throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取罐号失败",e);
	      }	
	}

	@Override
	public void updateCargoAll(Integer cargoId) throws OAException {
		try{  
	    	  
	    	   String sql="update t_pcs_cargo a ,(select b.cargoId,round(sum(b.goodsCurrent),3) goodsCurrent,round(sum(b.goodsInPass),3) goodsInPass,round(sum(b.goodsOutPass),3) goodsOutPass from sop.t_pcs_goods b where ISNULL(b.goodsGroupId) GROUP BY b.cargoId) b,(select cargoId, round(sum(b.goodsTotal),3) goodsTotal from t_pcs_goods b where ISNULL(b.goodsGroupId) GROUP BY cargoId) c set"+
	    	   " a.goodsTotal=c.goodsTotal,"+
	    	   " a.goodsCurrent=b.goodsCurrent,"+
	    	   " a.goodsInPass=b.goodsInPass,"+
	    	   " a.goodsOutPass=b.goodsOutPass where a.id=b.cargoId and a.id=c.cargoId and a.id="+cargoId;
	    	   executeUpdate(sql);
	      }catch(RuntimeException e){
	    	  LOG.error("更新失败");
	    	  throw new OAException(Constant.SYS_CODE_DB_ERR, "更新失败",e);
	      }	
	}

	@Override
	public List<Map<String, Object>> getGoodsWait(int goodsWaitId) throws OAException {
		try{  
			
			String sql="SELECT   SUM(t.deliverNum) goodsWait FROM t_pcs_goodslog t WHERE t.actualNum = 0  AND t.deliverNum != 0 and t.type=5 and t.actualType=0 AND t.goodsId = "+goodsWaitId;
			
	    	 return executeQuery(sql);
	      }catch(RuntimeException e){
	    	  LOG.error("dao失败");
	    	  throw new OAException(Constant.SYS_CODE_DB_ERR, "dao失败",e);
	      }	
	}

	@Override
	public void deleteCargo(int cargoId) throws OAException {
		// TODO Auto-generated method stub
		try{  
			String sql1="delete from t_pcs_goodslog where goodsId in (select id from t_pcs_goods where cargoId ="+cargoId+")";
			String sql2="delete from t_pcs_goods where cargoId="+cargoId;
			String sql3="delete from t_pcs_cargo where id="+cargoId;
			
	        execute(sql1);
	        execute(sql2);
	        execute(sql3);
	      }catch(RuntimeException e){
	    	  LOG.error("dao失败");
	    	  throw new OAException(Constant.SYS_CODE_DB_ERR, "dao失败",e);
	      }	
	
		
	}

	@Override
	public int checkCargoCode(String code) throws OAException {
		// TODO Auto-generated method stub
		String sql="select count(id) from t_pcs_cargo where code='"+code+"'";
		return (int) getCount(sql);
	}

	@Override
	public Map<String, Object> getTotalGoodsTank(InboundOperationDto ioDto) throws OAException {
        try{  
           if(ioDto.getArrivalId()!=null&&ioDto.getProductId()!=null){
			String sql="select SUM(ROUND(b.goodsTank,3)) totalGoodsTank  from t_pcs_cargo a , t_pcs_goods b where a.id=b.cargoId and (b.isPredict is null or b.isPredict<>1) and  ISNULL(b.sourceGoodsId) and  ISNULL(b.rootGoodsId) "
					+ " and a.arrivalId="+ioDto.getArrivalId()+" and a.productId="+ioDto.getProductId();
	    	 return executeQueryOne(sql);
           }
	      }catch(RuntimeException e){
	    	  LOG.error("dao失败");
	    	  throw new OAException(Constant.SYS_CODE_DB_ERR, "dao失败",e);
	      }
		return null;	
	}

	@Override
	public Map<String, Object> getGoodsLossedCount(Integer goodsId)
			throws OAException {
		String sql="select ABS(COALESCE(sum(goodsChange),0)) lossed from t_pcs_goodslog where goodsId="+goodsId+" and type=10 and deliverNo=1 ";
        try{  
 	    	 return executeQueryOne(sql);
 	      }catch(RuntimeException e){
 	    	  LOG.error("dao失败");
 	    	  throw new OAException(Constant.SYS_CODE_DB_ERR, "dao失败",e);
 	      }
 	}

	@Override
	public List<Map<String, Object>> getClientByCargoId(int cargoId)throws OAException  {
		String sql="";
		if(cargoId!=0){
			
			 sql="select DISTINCT a.clientId,c.code,c.name clientName from t_pcs_goods a LEFT JOIN t_pcs_cargo b on a.cargoId=b.id LEFT JOIN t_pcs_client c on c.id=a.clientId where b.id="+cargoId;
		}else{
			sql="select a.id clientId ,a.name clientName ,a.code  from t_pcs_client a";
		}
        try{  
 	    	 return executeQuery(sql);
 	      }catch(RuntimeException e){
 	    	  LOG.error("dao失败");
 	    	  throw new OAException(Constant.SYS_CODE_DB_ERR, "dao失败",e);
 	      }
 	}

	@Override
	public List<Map<String, Object>> getCargoByClientId(int clientId)throws OAException  {
		
		String sql="";
		if(clientId!=0){
			
			 sql="select DISTINCT b.id cargoId,b.code cargoCode from t_pcs_goods a LEFT JOIN t_pcs_cargo b on a.cargoId=b.id where 	b.code is not null and a.clientId= "+clientId;
		}else{
			sql="select a.id cargoId,a.code cargoCode from t_pcs_cargo a where 	a.code is not null";
		}
		
        try{  
 	    	 return executeQuery(sql);
 	      }catch(RuntimeException e){
 	    	  LOG.error("dao失败");
 	    	  throw new OAException(Constant.SYS_CODE_DB_ERR, "dao失败",e);
 	      }
 	}

	@Override
	public int checkNo(String no) throws OAException {
		

		String sql="select round(max(replace(a.No,'No.','')),0)  from t_pcs_cargo a ";
		return (int) getCount(sql);
		
	}

	@Override
	public List<Map<String, Object>> getCargoInspectTotal(CargoGoodsDto agDto)
			throws OAException {
		try {
			String sql = "select  round(sum(a.goodsInspect),3) goodsTotal FROM t_pcs_cargo a "
					+ " INNER JOIN t_pcs_product b on a.productId=b.id"
					+ " INNER JOIN t_pcs_client c ON a.clientId=c.id"
					+ " LEFT JOIN t_pcs_contract d ON a.contractId=d.id"
					+ " LEFT JOIN t_pcs_tax_type e ON a.taxType=e.key"
					+ " LEFT JOIN t_pcs_cargo_agent f ON a.cargoAgentId=f.id"
					+ " LEFT JOIN t_pcs_inspect_agent g ON a.inspectAgentId=g.id"
					+ " LEFT JOIN t_pcs_certify_agent h on a.certifyAgentId=h.id"
					+ " LEFT JOIN t_pcs_arrival i ON a.arrivalId=i.id"
					+ " LEFT JOIN t_pcs_ship j ON i.shipId=j.id"
					+ " LEFT JOIN t_pcs_office_grade k ON a.officeGrade=k.key"
					+ " LEFT JOIN t_pcs_pass_status m ON a.passStatus=m.key"
					+ " LEFT JOIN t_pcs_trade_type n on a.taxType=n.key" +
					" LEFT JOIN t_pcs_work o on o.arrivalId=a.arrivalId and o.productId=a.productId and orderNum=0"
					+ " LEFT JOIN t_pcs_client p on p.id=a.clearanceClientId "
					+ " WHERE 1=1 ";
			
			
			if(!Common.isNull(agDto.getPassStatus())){
				if(Integer.parseInt(agDto.getPassStatus())==1){
					
					sql+=" and round(a.goodsOutPass,3)=round(a.goodsTotal,3) ";
				}
				else if(Integer.parseInt(agDto.getPassStatus())==2){
					
					sql+=" and round(a.goodsOutPass,3)<round(a.goodsTotal,3) and a.goodsOutPass!=0 ";
				}
				else if(Integer.parseInt(agDto.getPassStatus())==3){
					
					sql+=" and a.goodsOutPass=0 ";
				}else if(Integer.parseInt(agDto.getPassStatus())==4){
					
					sql+=" and a.goodsTank=a.goodsTotal and a.goodsTotal=a.goodsInspect and d.lossRate<>0 ";
				}
			}
			
			if(!Common.isNull(agDto.getTaxType())&&Integer.parseInt(agDto.getTaxType().toString())!=13){
				sql+=" and a.taxType="+agDto.getTaxType();
			}
			
			if(!Common.isNull(agDto.getArrivalStatus())){ 
				sql+=" and (o.status>="+agDto.getArrivalStatus()+" or a.isPredict=1)";
				
				if(agDto.getArrivalStatus()==9){
					sql+=" and i.type<>3 ";
				}
				
			}
			if (!Common.empty(agDto.getCargoId())) {
				sql += " and a.id=" + agDto.getCargoId();
			}
			if (!Common.empty(agDto.getArrivalcode())) {
				sql += " and a.code like'%" + agDto.getArrivalcode() + "%'";
			}
			if (!Common.empty(agDto.getArrivalId())
					&& !Common.isNull(agDto.getArrivalId())) {
				sql += " and a.arrivalId=" + agDto.getArrivalId();
			}
			if(!Common.empty(agDto.getClientId())&&!Common.isNull(agDto.getClientId())){
				sql+=" and a.clientId="+agDto.getClientId();
			}
			if(!Common.empty(agDto.getProductId())&&!Common.isNull(agDto.getProductId())){
				sql+=" and a.productId="+agDto.getProductId();
			}
			if(!Common.empty(agDto.getClientName())){
				sql+=" and c.name like '%"+agDto.getClientName()+"%'";
			}
			
			if(!Common.isNull(agDto.getCargoStatus())){
				sql+=" and a.status="+agDto.getCargoStatus();
			}
			
			if (!Common.empty(agDto.getShipId())
					&& !Common.isNull(agDto.getShipId())) {
				sql += " and i.shipId=" + agDto.getShipId();
			}
			
			
			if(!Common.empty(agDto.getType())){
				if(agDto.getType()==1){
					sql+=" and i.arrivalStartTime is not null ";
				}
			}
			
			if (!Common.empty(agDto.getTankId())
					&& !Common.isNull(agDto.getTankId())) {
				sql += " and instr( (select GROUP_CONCAT(DISTINCT tankId) from t_pcs_goods where cargoId=a.id),'"+agDto.getTankId()+"')>0 ";
				
			}
			
			
			if(!Common.empty(agDto.getStartTime())&&!"-1".equals(agDto.getStartTime())){
				sql+=" and i.arrivalStartTime>='"+agDto.getStartTime()+"'";
			}
			if(!Common.empty(agDto.getEndTime())&&!"-1".equals(agDto.getEndTime())){
				sql+=" and i.arrivalStartTime<='"+agDto.getEndTime()+"'";
			}
			
			
//			sql+=" order by"
			
			return executeQuery(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}




}
