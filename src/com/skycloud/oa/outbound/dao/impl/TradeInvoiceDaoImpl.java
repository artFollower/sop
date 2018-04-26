/**
 * @Title:TradeInvoiceDaoImpl.java
 * @Package com.skycloud.oa.outbound.dao.impl
 * @Description TODO
 * @autor jiahy
 * @date 2016年4月8日上午8:56:44
 * @version V1.0
 */
package com.skycloud.oa.outbound.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.outbound.dao.TradeInvoiceDao;
import com.skycloud.oa.outbound.dto.TradeInvoiceDto;
import com.skycloud.oa.utils.Common;

/**
 * @ClassName TradeInvoiceDaoImpl
 * @Description TODO
 * @author jiahy
 * @date 2016年4月8日上午8:56:44
 */
@Component
public class TradeInvoiceDaoImpl extends BaseDaoImpl  implements TradeInvoiceDao {
	private static Logger LOG = Logger.getLogger(LadingDaoImpl.class);
	/**
	 * @Title getCargoList
	 * @Descrption:TODO
	 * @param:@param tIDto
	 * @param:@return
	 * @auhor jiahy
	 * @date 2016年4月8日上午8:56:44
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> getGoodsList(TradeInvoiceDto tIDto) throws OAException{
		try {
			StringBuilder sql=new StringBuilder();
			sql.append(" select a.id ,a.code  from t_pcs_goods a where 1=1");
			if(!Common.isNull(tIDto.getClientId())){
				sql.append(" and a.clientId=").append(tIDto.getClientId());
			}
			if(!Common.isNull(tIDto.getProductId())){
				sql.append(" and a.productId=").append(tIDto.getProductId());
			}
			sql.append(" order by a.code asc ");
			return executeQuery(sql.toString());
		} catch (RuntimeException e) {
			LOG.error("dao获取货体列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取货体列表失败", e);
		}
	}
	/**
	 * @Title getOutboundList
	 * @Descrption:TODO
	 * @param:@param tIDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年4月8日下午1:47:06
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> getOutboundList(TradeInvoiceDto tIDto, int start, int limit) throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
			sql.append(" SELECT a.id id,a.batchId batchId,a.serial serial,a.deliverType deliverType,a.actualNum,a.goodsId, ")
			.append(" a.ladingEvidence ladingEvidence,a.tankName tankName,c.name productName, ")
			.append(" a.deliverNum deliverNum,d.code ladingCode,e.name createUserName,b.code goodsCode, ")
			.append(" a.storageInfo remark,h.code cargoCode,f.name clientName,from_unixtime(a.createTime) createTime, ")
			.append(" from_unixtime(a.invoiceTime) invoiceTime, (CASE WHEN a.deliverType = 1 THEN p.code ELSE o.refName END) vsName, ")
			.append(" j.name  shangjihuoti,(case WHEN !ISNULL(b.rootGoodsId) and b.rootGoodsId!=0 THEN l.name ELSE  m.name END)  yuanshihuoti,m.name  ladingClientName, ")
			.append(" (CASE WHEN !ISNULL(b.rootGoodsId) and b.rootGoodsId!=0 THEN r.code WHEN ISNULL(d.code) THEN h.code ELSE d.code END ) yuanhao,")
			.append(" d.code  diaohao")
			.append(" from t_pcs_goodslog a  ")
			.append(" LEFT JOIN t_pcs_goods b on a.goodsId=b.id ")
			.append(" LEFT JOIN t_pcs_product c on b.productId=c.id ")
			.append(" LEFT JOIN t_pcs_lading d on a.ladingId=d.id ")
			.append(" LEFT JOIN t_auth_user e on a.createUserId=e.id ")
			.append(" LEFT JOIN t_pcs_client f on f.id=b.clientId ")
			.append(" LEFT JOIN t_pcs_cargo h on h.id=b.cargoId ")
			.append(" LEFT JOIN t_pcs_goods i on b.sourceGoodsId=i.id ")
			.append(" LEFT JOIN t_pcs_client j on j.id=i.clientId ")
			.append(" LEFT JOIN t_pcs_goods k on b.rootGoodsId=k.id ")
			.append(" LEFT JOIN t_pcs_client l on l.id=k.clientId ")
			.append(" LEFT JOIN t_pcs_client m on m.id=a.ladingClientId ")
			.append(" LEFT JOIN t_pcs_arrival n on n.id=a.batchId ")
			.append(" LEFT JOIN t_pcs_ship_ref o on o.id=a.vehicleShipId ")
			.append(" LEFT JOIN t_pcs_truck p on p.id=a.vehicleShipId ")
			.append(" LEFT JOIN t_pcs_goods_group q on q.id=k.goodsGroupId ")
			.append(" LEFT JOIN t_pcs_lading r on r.id=q.ladingId ")
			.append(" WHERE a.type=5 and a.actualType=1 ");
			if(!Common.isNull(tIDto.getGoodsId())){
				sql.append(" and a.goodsId=").append(tIDto.getGoodsId());
			}
			if(!Common.isNull(tIDto.getSerial())){
				sql.append(" and a.serial like '%").append(tIDto.getSerial()).append("%'");
			}
			if(!Common.isNull(tIDto.getStartTime())&&tIDto.getStartTime()!=-1){
				sql.append(" and a.createTime>=").append(tIDto.getStartTime());
			}
			if(!Common.isNull(tIDto.getEndTime())&&tIDto.getEndTime()!=-1){
				sql.append(" and a.createTime<=").append(tIDto.getEndTime());
			}
			sql.append(" ORDER BY a.createTime DESC ");
			if(limit!=0)
			{
			sql.append(" LIMIT ").append(start).append(",").append(limit);
			}
			return executeQuery(sql.toString());
		} catch (RuntimeException e) {
			LOG.error("dao获取发货列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取发货列表失败", e);
		}
	}
	/**
	 * @Title getOutboundListCount
	 * @Descrption:TODO
	 * @param:@param tIDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年4月8日下午2:04:43
	 * @throws
	 */
	@Override
	public int getOutboundListCount(TradeInvoiceDto tIDto) throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
			sql.append(" SELECT count(1)   from t_pcs_goodslog a  ")
			.append(" WHERE a.type=5 and a.actualType=1 ");
			if(!Common.isNull(tIDto.getGoodsId())){
				sql.append(" and a.goodsId=").append(tIDto.getGoodsId());
			}
			if(!Common.isNull(tIDto.getSerial())){
				sql.append(" and a.serial like '%").append(tIDto.getSerial()).append("%'");
			}
			if(!Common.isNull(tIDto.getStartTime())&&tIDto.getStartTime()!=-1){
				sql.append(" and a.createTime>=").append(tIDto.getStartTime());
			}
			if(!Common.isNull(tIDto.getEndTime())&&tIDto.getEndTime()!=-1){
				sql.append(" and a.createTime<=").append(tIDto.getEndTime());
			}
			return (int) getCount(sql.toString());
	} catch (RuntimeException e) {
		LOG.error("dao获取发货列表数量失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取发货列表数量失败", e);
	}
	}
	/**
	 * @Title tradeInvoice
	 * @Descrption:TODO
	 * @param:@param string
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年4月11日上午9:06:48
	 * @throws
	 */
	@Override
	public void tradeInvoice(String id,String remark) throws OAException {
		try{
			
			StringBuilder sql=new StringBuilder();
			sql.append(" call tradeInvoice(").append(Integer.valueOf(id)).append(",'").append(remark).append("')");
			executeProcedure(sql.toString());			
		} catch (RuntimeException e) {
		LOG.error("dao发货冲销失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao发货冲销失败", e);
	}
	}
	/**
	 * @Title getTradeInvoiceLogList
	 * @Descrption:TODO
	 * @param:@param start
	 * @param:@param limit
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年4月11日下午3:15:16
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> getTradeInvoiceLogList(int start, int limit) throws OAException {
			try{
			
				StringBuilder sql=new StringBuilder();
				sql.append(" SELECT a.id id,a.batchId batchId,a.serial serial,a.deliverType deliverType,a.actualNum,a.goodsId, ")
				.append(" a.ladingEvidence ladingEvidence,a.tankName tankName,c.name productName,a.deliverNo, ")
				.append(" a.deliverNum deliverNum,d.code ladingCode,e.name createUserName,b.code goodsCode, ")
				.append(" a.storageInfo remark,h.code cargoCode,f.name clientName,from_unixtime(a.createTime) createTime, ")
				.append(" from_unixtime(a.invoiceTime) invoiceTime, (CASE WHEN a.deliverType = 1 THEN p.code ELSE o.refName END) vsName, ")
				.append(" j.name  shangjihuoti,(case WHEN !ISNULL(b.rootGoodsId) and b.rootGoodsId!=0 THEN l.name ELSE  m.name END)  yuanshihuoti,m.name  ladingClientName, ")
				.append(" (CASE WHEN !ISNULL(b.rootGoodsId) and b.rootGoodsId!=0 THEN r.code WHEN ISNULL(d.code) THEN h.code ELSE d.code END ) yuanhao,")
				.append(" d.code  diaohao")
				.append(" from t_pcs_goodslog a  ")
				.append(" LEFT JOIN t_pcs_goods b on a.goodsId=b.id ")
				.append(" LEFT JOIN t_pcs_product c on b.productId=c.id ")
				.append(" LEFT JOIN t_pcs_lading d on a.ladingId=d.id ")
				.append(" LEFT JOIN t_auth_user e on a.createUserId=e.id ")
				.append(" LEFT JOIN t_pcs_client f on f.id=b.clientId ")
				.append(" LEFT JOIN t_pcs_cargo h on h.id=b.cargoId ")
				.append(" LEFT JOIN t_pcs_goods i on b.sourceGoodsId=i.id ")
				.append(" LEFT JOIN t_pcs_client j on j.id=i.clientId ")
				.append(" LEFT JOIN t_pcs_goods k on b.rootGoodsId=k.id ")
				.append(" LEFT JOIN t_pcs_client l on l.id=k.clientId ")
				.append(" LEFT JOIN t_pcs_client m on m.id=a.ladingClientId ")
				.append(" LEFT JOIN t_pcs_arrival n on n.id=a.batchId ")
				.append(" LEFT JOIN t_pcs_ship_ref o on o.id=a.vehicleShipId ")
				.append(" LEFT JOIN t_pcs_truck p on p.id=a.vehicleShipId ")
				.append(" LEFT JOIN t_pcs_goods_group q on q.id=k.goodsGroupId ")
				.append(" LEFT JOIN t_pcs_lading r on r.id=q.ladingId ")
				.append(" WHERE a.type=8 and a.actualType=1 ");
				
				sql.append(" ORDER BY a.createTime DESC ");
				if(limit!=0)
				{
				sql.append(" LIMIT ").append(start).append(",").append(limit);
				}
				return executeQuery(sql.toString());		
		} catch (RuntimeException e) {
		LOG.error("dao发货冲销失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao发货冲销失败", e);
		}
	}
	/**
	 * @Title getTradeInvoiceLogListCount
	 * @Descrption:TODO
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年4月11日下午3:15:16
	 * @throws
	 */
	@Override
	public int getTradeInvoiceLogListCount() throws OAException {
try{
			
	StringBuilder sql=new StringBuilder();
	sql.append(" SELECT count(1)   from t_pcs_goodslog a  WHERE a.type=8 and a.actualType=1 ");
	return (int) getCount(sql.toString());			
		} catch (RuntimeException e) {
		LOG.error("dao发货冲销失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao发货冲销失败", e);
		}
	}

}
