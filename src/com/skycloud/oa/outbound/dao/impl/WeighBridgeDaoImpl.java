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
import com.skycloud.oa.outbound.dao.WeighBridgeDao;
import com.skycloud.oa.outbound.dto.WeighBridgeDto;
import com.skycloud.oa.outbound.model.GoodsLog;
import com.skycloud.oa.outbound.model.WeighBridge;
import com.skycloud.oa.utils.Common;

/**
 * 
 * <p>出库管理---车发称重</p>
 * @ClassName:WeighBridgeDaoImpl
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年5月27日 下午10:34:21
 *
 */
@Component
public class WeighBridgeDaoImpl extends BaseDaoImpl implements WeighBridgeDao 
{
	/**
	 * 记录日志
	 */
	private static Logger logger = Logger.getLogger(WeighBridgeDaoImpl.class);
	
	/**
	 * 查询称重信息
	 * @Title:WeighBridgeDaoImpl
	 * @Description:
	 * @param servalNo
	 * @return
	 * @throws Exception
	 * @Date:2015年5月27日 下午10:15:52
	 * @return:Map<String,Object> 
	 * @throws
	 */
	@Override
	public Map<String, Object> queryGoodsLogBySerial(String servalNo) throws Exception
	{
		StringBuffer sb = new StringBuffer() ;
sb.append("	SELECT	");
sb.append("	a.id,a.ladingEvidence,a.actualType,a.storageInfo,a.remark,a.deliverType,a.isCleanInvoice,	");
sb.append("	round(a.deliverNum, 3) AS deliverNum,round(a.actualNum, 3) AS actualNum,	");
sb.append("	b.productId,c.name productName,c.oils oils,c.fontColor,	");
sb.append("	b.CODE code,h.STATUS AS cancelStatus,d.CODE cargoCode,	");
sb.append("	i.NAME ladingClientName,i.CODE ladingClientCode,a.tankName tank,s.id tankId,");
sb.append("	b.tankId tankCode,t.status outBoundStatus,	");
sb.append("	( CASE WHEN a.deliverType=1 THEN j.code ELSE k.refName END) vsName,	");
sb.append("	CONCAT(f.name,'/',g.refName,'/',DATE_FORMAT( e.arrivalStartTime, '%Y-%m-%d')) shipInfo,	");
sb.append("	 m.name yuanshihuoti,	");
sb.append("	( CASE WHEN !ISNULL(b.rootGoodsId) and b.rootGoodsId!=0  THEN o.code WHEN ISNULL(p.code)  THEN d.code ELSE p.code END) yuanhao,	");
sb.append("	p.code diaohao,a.deliverType,r.name createUserName	");
sb.append("	from t_pcs_goodslog a	");
sb.append("	LEFT JOIN t_pcs_goods b ON a.goodsId=b.id	");
sb.append("	LEFT JOIN t_pcs_product c ON c.id=b.productId	");
sb.append("	LEFT JOIN t_pcs_cargo d ON d.id=b.cargoId	");
sb.append("	LEFT JOIN t_pcs_arrival e ON e.id=d.arrivalId	");
sb.append("	LEFT JOIN t_pcs_ship f ON e.shipId=f.id	");
sb.append("	LEFT JOIN t_pcs_ship_ref g ON g.id=e.shipRefId	");
sb.append("	LEFT JOIN t_pcs_train h ON h.id=a.batchId ");
sb.append("	LEFT JOIN t_pcs_arrival t ON t.id=a.batchId ");
sb.append("	LEFT JOIN t_pcs_client i ON i.id=a.ladingClientId	");
sb.append("	LEFT JOIN t_pcs_truck j ON j.id=a.vehicleShipId	");
sb.append("	LEFT JOIN t_pcs_ship_ref k ON k.id=a.vehicleShipId	");
sb.append("	LEFT JOIN t_pcs_goods n ON n.id=b.rootGoodsId	");
sb.append("	LEFT JOIN t_pcs_client l ON l.id=n.clientId	");
sb.append("	LEFT JOIN t_pcs_client m ON m.id=d.clientId	");
sb.append("	LEFT JOIN t_pcs_goods_group q ON n.goodsGroupId=q.id	");
sb.append("	LEFT JOIN t_pcs_lading o ON o.id=q.ladingId	");
sb.append("	LEFT JOIN t_pcs_lading p ON p.id=a.ladingId	");
sb.append("	LEFT JOIN t_auth_user r ON r.id=a.createUserId 	");
sb.append("	LEFT JOIN t_pcs_tank s ON s.code=a.tankName ");
sb.append("	where a.type=5 ");
sb.append("	and a.serial ='"+servalNo.trim()+"'");
return executeQueryOne(sb.toString());
	}

	/**
	 * 
	 * @Title:queryWeighBridgeBySerial
	 * @Description:
	 * @param servalNo
	 * @return
	 * @throws Exception
	 * @see
	 */
	@Override
	public Map<String, Object> queryWeighBridgeBySerial(String servalNo) throws Exception 
	{
		StringBuffer sb=  new StringBuffer() ;
		sb.append("SELECT t.*,FROM_UNIXTIME(t.intoTime) intoTime1,FROM_UNIXTIME(t.outTime) outTime1,") ;
		sb.append("(SELECT code FROM t_pcs_tank WHERE id = tankId) tankName,");
		sb.append("(SELECT a.name FROM t_auth_user a WHERE a.id = inStockPersonId) inStockPerson,");
		sb.append("(SELECT b.name FROM t_auth_user b WHERE b.id = outStockPersonId) outStockPerson ");
		sb.append("FROM t_pcs_weighbridge t where 1=1 ");
		if(!Common.empty(servalNo))
		{
			sb.append(" and t.SERIAL= '").append(servalNo).append("' ");
		}
		
		logger.info("出库管理-车发称重-发货情况(WeighBridgeDaoImpl)："+sb.toString());
		
		return executeQueryOne(sb.toString());
	}

	/**
	 * 
	 * @Title:WeighBridgeDaoImpl
	 * @Description:
	 * @param weighBridge
	 * @throws Exception
	 * @Date:2015年5月27日 下午10:17:11
	 * @return :void 
	 * @throws
	 */
	@Override
	public void up(WeighBridge weighBridge) throws OAException 
	{
		String sql ="update t_pcs_weighbridge set id=id ";
		if(weighBridge.getCreateTime()!=null){
			sql+=" ,createTime="+weighBridge.getCreateTime();
		}
		if(weighBridge.getCreateUserId()!=0){
			sql+=" ,createUserId="+weighBridge.getCreateUserId();
		}
		if(weighBridge.getDeliveryNum()!=null){
			sql+=" , deliveryNum="+weighBridge.getDeliveryNum();
		}
		if(weighBridge.getDescription()!=null){
			sql+=",description='"+weighBridge.getDescription()+"'";
		}
		if(weighBridge.getInPort()!=null){
			sql+=",inPort='"+weighBridge.getInPort()+"'";
		}
		if(weighBridge.getInWeigh()!=null){
			sql+=",inWeigh="+weighBridge.getInWeigh();
		}
		if(weighBridge.getOutWeigh()!=null){
			sql+=",outWeigh="+weighBridge.getOutWeigh();
		}
		if(weighBridge.getIntoTime()!=null&&weighBridge.getIntoTime()!=-1){
			sql+=" ,intoTime="+weighBridge.getIntoTime();
		}
		if(weighBridge.getMeasureWeigh()!=null){
			sql+=", measureWeigh="+weighBridge.getMeasureWeigh();
		}
		if(weighBridge.getOutTime()!=null&&weighBridge.getOutTime()!=-1){
			sql+=", outTime="+weighBridge.getOutTime();
		}
		if(weighBridge.getStatus()!=0){
			sql+=", status="+weighBridge.getStatus();
		}
		if(weighBridge.getActualRoughWeight()!=null){
			sql+=" , actualRoughWeight="+weighBridge.getActualRoughWeight();
		}
		if(weighBridge.getActualTareWeight()!=null){
			sql+=",actualTareWeight="+weighBridge.getActualTareWeight();
		}
		if(weighBridge.getTankId()!=0){
			sql+=",tankId="+weighBridge.getTankId();
		}
		if(weighBridge.getType()!=0){
			sql+=",type="+weighBridge.getType();
		}
		if(weighBridge.getInStockPersonId()!=0){
			sql+=", inStockPersonId="+weighBridge.getInStockPersonId();
		}
		if(weighBridge.getOutStockPersonId()!=0){
			sql+=", outStockPersonId="+weighBridge.getOutStockPersonId();
		}
		if(weighBridge.getInip()!=null){
			sql+=", inip='"+weighBridge.getInip()+"'";
		}
		
		sql+=" where id="+weighBridge.getId();
		
		executeUpdate(sql);
		
		logger.info("出库管理-车发出库-插入记录："+weighBridge.toString());
	}

	/**
	 * 
	 * @Title:updateActualNum
	 * @Description:
	 * @param weighBridge
	 * @param trainId
	 * @throws Exception
	 * @see
	 */
	public void updateActualNum(WeighBridge weighBridge)throws Exception
	{   
		
		String sql="update t_pcs_goodslog set afUpNum=ROUND("+weighBridge.getDeliveryNum()+",3) ,afDiffNum=ROUND(("+weighBridge.getDeliveryNum()+"-IFNULL(actualNum,0)),3)  where type=5 and serial='"+weighBridge.getSerial()+"'";
		executeUpdate(sql);
		
	}
	
	/**
	 * 出库管理-车发称重-添加记录
	 * @Title:addWeighBridge
	 * @Description:
	 * @param weighBridge
	 * @return
	 * @throws Exception
	 * @see
	 */
	@Override
	public int addWeighBridge(WeighBridge weighBridge) throws OAException
	{ 
		try{
		Serializable s = save(weighBridge) ;
		return Integer.parseInt(s.toString());
		}catch(OAException e) {
			e.printStackTrace();
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新货体当前存量失败", e);
		}
	}

	/**
	 * 
	 * @Title:getDeliverInvoiceInfo
	 * @Description:
	 * @param serialNo
	 * @return
	 * @throws Exception
	 * @see
	 */
	@Override
	public List<Map<String, Object>> getDeliverInvoiceInfo(String serialNo,int morePrint) throws Exception 
	{
		StringBuffer sb = new StringBuffer() ;
		
			sb.append(" SELECT a.serial,a.tankName,a.ladingEvidence,i.tankId,a.actualType,");
			sb.append(" c.name productName,c.oils, h.name ladingClientName, d.code cargoCode, j.code diaohao,");
			sb.append(" ( CASE WHEN !ISNULL(b.rootGoodsId) and b.rootGoodsId!=0  THEN m.code WHEN ISNULL(j.code)  THEN d.code ELSE j.code END) yuanhao,");
			sb.append(" concat(f.name,'/',g.refName,'/',DATE_FORMAT(e.arrivalStartTime,'%Y-%m-%d')) shipInfo,d.customLading,FROM_UNIXTIME(d.customLadingTime,'%Y-%m-%d') customLadingTime,");
			sb.append(" FROM_UNIXTIME(a.createTime) createTime, (CASE WHEN a.deliverType=1 THEN o.code ELSE n.refName END) vsName,");
			sb.append(" a.deliverNum deliverNum,a.actualNum actualNum, p.name userName,i.inWeigh,i.outWeigh,i.actualTareWeight,i.actualRoughWeight,a.storageInfo,a.remark");
			sb.append(" from t_pcs_goodslog a");
			sb.append(" INNER JOIN t_pcs_goods b ON a.goodsId=b.id	");
			sb.append(" INNER JOIN t_pcs_product c ON 	c.id=b.productId");
			sb.append(" INNER JOIN t_pcs_cargo d ON d.id=b.cargoId");	
			sb.append(" LEFT JOIN t_pcs_arrival e on e.id=d.arrivalId");
			sb.append(" LEFT JOIN t_pcs_ship f ON f.id=e.shipId");
			sb.append(" LEFT JOIN t_pcs_ship_ref g ON g.id=e.shipRefId	");
			sb.append(" INNER JOIN t_pcs_client h on h.id=a.ladingClientId");
			sb.append(" LEFT JOIN t_pcs_weighbridge i	on i.serial=a.serial");
			sb.append(" LEFT JOIN t_pcs_lading j on j.id=a.ladingId");
			sb.append(" LEFT JOIN t_pcs_goods k ON k.id=b.rootGoodsId	");
			sb.append(" LEFT JOIN t_pcs_goods_group l ON k.goodsGroupId=l.id");
			sb.append(" LEFT JOIN t_pcs_lading m ON m.id=l.ladingId	");
			sb.append(" LEFT JOIN t_pcs_ship_ref n ON n.id=a.vehicleShipId");
			sb.append(" LEFT JOIN t_pcs_truck o ON o.id=a.vehicleShipId");
			sb.append(" LEFT JOIN t_auth_user p ON p.id=i.createUserId");
			
		   if(!Common.empty(morePrint)&&morePrint==1){
			   sb.append(" WHERE a.type=5 and a.serial='"+serialNo+"'");
		   }else{
			   sb.append(",t_pcs_goodslog q where a.type=5 and  a.batchId=q.batchId and a.deliverType=q.deliverType and q.serial='"+serialNo+"'");
		   }
		
		
		logger.info("getDeliverInvoiceInfo："+sb.toString());
		
		return executeQuery(sb.toString());
	}

	/**
	 * 
	 * @Title:updateTrainStatusByWeighSerial
	 * @Description:
	 * @param serial
	 * @param dNum
	 * @throws Exception
	 * @see
	 */
	@Override
	public void updateTrainStatusByWeighSerial(String serial,BigDecimal dNum,Long outTime) throws OAException 
	{
		String sql = "call updateVehicleDeliverInfoForWeigh('"+serial+"',"+dNum+","+outTime+")" ;
		
		logger.info("开票号："+serial+"--------"+"dNum："+dNum);
		logger.info("出库管理-车发称重："+sql);
		
		executeProcedure(sql) ;
	}

	/**
	 * 修改车发称重创建者
	 * @Title:addCreateUserId
	 * @Description:
	 * @param id
	 * @param serial
	 * @throws Exception
	 * @see
	 */
	@Override
	public void addCreateUserId(int id,String serial) throws Exception 
	{
		String sql = "update t_pcs_weighbridge set createUserId="+id+" where serial='"+serial+"'" ;
		
		logger.info("修改车发称重创建者："+sql);
		
		executeUpdate(sql) ;
	}

	/**
	 * 查询地磅信息
	 * @Title:findPlat
	 * @Description:
	 * @param weighBridge
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public List<Map<String, Object>> findPlat(WeighBridgeDto weighBridge) throws OAException 
	{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT t.`platIp`,t.`platUser` ");
		sql.append("FROM t_pcs_platform_scale t ");
		sql.append("where 1=1 ");
		if(!Common.empty(weighBridge.getUserId()))
		{
			sql.append("and t.platUser = ").append(weighBridge.getUserId()).append(" ");
		}
		
		logger.info("查询地磅信息："+sql.toString());
		logger.info("查询地磅信息参数："+weighBridge.getUserId());
		
		return executeQuery(sql.toString());
	}

	/**
	 * 更新地磅的状态
	 * @Title:updatePlat
	 * @Description:
	 * @param weighBridge
	 * @throws OAException
	 * @see 
	 */
	@Override
	public void updatePlat(WeighBridgeDto weighBridge) throws OAException 
	{
		StringBuffer sql = new StringBuffer();
		sql.append("insert into t_pcs_platform_scale(platIp,platUser) values('")
		   .append(weighBridge.getSelectIp())
		   .append("','")
		   .append(weighBridge.getUserId())
		   .append("') ");
		sql.append("ON DUPLICATE KEY update platIp = '")
		   .append(weighBridge.getSelectIp())
		   .append("' ");
		
		logger.info("更新地磅的状态："+sql.toString());
		logger.info("更新地磅的状态参数："+weighBridge);
		
		executeUpdate(sql.toString());
	}

	/**
	 * 更新地磅的状态
	 * @Title:updatePlatNot
	 * @Description:
	 * @param weighBridge
	 * @throws OAException
	 * @see 
	 */
	@Override
	public void updatePlatNot(WeighBridgeDto weighBridge) throws OAException 
	{
		StringBuffer sql = new StringBuffer();
		sql.append("update t_pcs_platform_scale t set t.platStatus = '0' ");
		sql.append("where 1=1 ");
		if(!Common.empty(weighBridge.getUserId()))
		{
			sql.append("and t.platUser = ").append(weighBridge.getUserId()).append(" ");
		}
		if(!Common.empty(weighBridge.getNotSelectIp()))
		{
			sql.append("and t.platIp = '").append(weighBridge.getNotSelectIp()).append("' ");
		}
		
		logger.info("更新地磅的状态111111111111111："+sql.toString());
		logger.info("更新地磅的状态参数111111111111："+weighBridge);
		
		executeUpdate(sql.toString());
	}

	/**
	 * 出库管理-车发称重-修改罐号和发运口
	 * @Title:updateTankId
	 * @Description:
	 * @param weighBridge
	 * @throws OAException
	 * @see 
	 */
	@Override
	public void updateTankId(WeighBridge weighBridge) throws OAException 
	{
		StringBuffer sql = new StringBuffer();
		sql.append("update t_pcs_weighbridge t set ");
		//出库时间
		if(!Common.empty(weighBridge.getOutTime()))
		{
			sql.append(" t.outTime = ").append(weighBridge.getOutTime()).append(", ");
		}
		//发货口
		if(!Common.empty(weighBridge.getInPort()))
		{
			sql.append(" t.inPort = '").append(weighBridge.getInPort()).append("', ");
		}
		//罐号
		if(!Common.isNull(weighBridge.getTankId()))
		{
			sql.append(" t.tankId = ").append(weighBridge.getTankId()).append(", ");
		}
		//状态
		if(!Common.isNull(weighBridge.getStatus()))
		{
			sql.append(" t.status = ").append(weighBridge.getStatus()).append(", ");
		}
		//出库称重人
		if(!Common.isNull(weighBridge.getOutStockPersonId()))
		{
			sql.append(" t.outStockPersonId = ").append(weighBridge.getOutStockPersonId()).append(" ");
		}
		//描述
		if(!Common.empty(weighBridge.getDescription()))
		{
			sql.append(" t.description = '").append(weighBridge.getDescription()).append("', ");
		}
		sql.append(" where 1=1 ");
		if(!Common.isNull(weighBridge.getSerial()))
		{
			sql.append(" and t.`serial` = '").append(weighBridge.getSerial()).append("' ");
		}
		
		logger.info("出库管理-车发称重-修改罐号和发运口SQL："+sql.toString());
		
		executeUpdate(sql.toString());
	}

	/**
	 * 更新货品颜色值
	 * @Title:updateColor
	 * @Description:
	 * @param weighBridge
	 * @throws OAException
	 * @see 
	 */
	@Override
	public void updateColor(WeighBridgeDto weighBridge) throws OAException 
	{
		StringBuffer sql = new StringBuffer();
		sql.append("update t_pcs_product t set ");
		if(!Common.empty(weighBridge.getFontColor()))
		{
			sql.append(" t.fontColor = '").append(weighBridge.getFontColor()).append("' ");
		}
		sql.append("where 1=1 ");
		if(!Common.isNull(weighBridge.getProductId()))
		{
			sql.append(" and t.id = ").append(weighBridge.getProductId()).append(" ");
		}
		
		logger.info("出库管理-车发称重-修改货品颜色值："+sql.toString());
		
		executeUpdate(sql.toString());
	}

	/**
	 * 联单时根据第一个单号查询其他
	 * @Title:findTicket
	 * @Description:
	 * @param ticketNo
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public List<Map<String, Object>> findTicket(String ticketNo,String deliverType) throws OAException 
	{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT i.id,i.batchId,i.goodsId, i.serial,p.`oils`,i.`actualNum`,i.deliverNum ,i.deliverType,w.id weightBridgeId,")
		.append( "w.inPort,w.measureWeigh,IFNULL(w.status,0) status,i.isCleanInvoice,g.goodsCurrent ")
		.append( " FROM t_pcs_goodslog i LEFT JOIN t_pcs_weighbridge w ON w.serial=i.serial,t_pcs_goods g,t_pcs_product p,")
		.append(" ( select gl.batchId,gl.deliverType from t_pcs_goodslog gl where gl.type=5 and gl.serial='"+ticketNo+"' ) tb ")
		.append("WHERE i.goodsId = g.id AND g.productId = p.id  ")
		.append("AND i.batchId =tb.batchId AND i.deliverType=tb.deliverType AND i.type=5 ");
		if(!Common.empty(deliverType)){
		sql.append( "and i.deliverType in("+deliverType+")");
		}
		return executeQuery(sql.toString());
	}

	/**
	 * 修改出库管理-车发称重-发运数、入库重和出库重
	 * @Title:updateData
	 * @Description:
	 * @param weighBridge
	 * @throws OAException
	 * @see 
	 */
	@Override
	public void updateData(WeighBridge weighBridge) throws OAException 
	{
		StringBuffer sql = new StringBuffer();
		sql.append("update t_pcs_weighbridge set ");
		//发运数
		if(!Common.empty(weighBridge.getDeliveryNum()))
		{
			sql.append(" deliveryNum =").append(weighBridge.getDeliveryNum()).append(",");
		}
		//进库重
		if(!Common.empty(weighBridge.getInWeigh()))
		{
			sql.append(" inWeigh =").append(weighBridge.getInWeigh()).append(", ");
		}
		//出库重
		if(!Common.empty(weighBridge.getOutWeigh()))
		{
			sql.append(" outWeigh =").append(weighBridge.getOutWeigh()).append(" ");
		}
		sql.append(" where 1=1 ");
		//开单号
		if(!Common.empty(weighBridge.getSerial()))
		{
			sql.append(" and serial = '").append(weighBridge.getSerial()).append("' ");
		}
		
		logger.info("更新发运数、入库重和出库重："+sql.toString());
		
		executeUpdate(sql.toString());
	}

	/**
	 * 查询联单的开单总数
	 * @Title:queryTotal
	 * @Description:
	 * @param serialNo
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public List<Map<String, Object>> queryTotal(String serialNo,String deliverType) throws OAException 
	{
		if(!Common.empty(serialNo)&&!Common.empty(deliverType))
		{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT round(SUM(i.deliverNum),3) totalNum FROM t_pcs_goodslog i");
		
			sql.append(" where i.type=5 and  i.deliverType="+deliverType+" i.batchId = (SELECT batchId FROM t_pcs_goodslog gl WHERE gl.serial = '")
			   .append(serialNo)
			   .append("') ");
		
		return executeQuery(sql.toString());
		}
		return null;
	}

	@Override
	public void updateGoodsCurrentAndWeighBridge(GoodsLog goodslog)
			throws OAException {
		try{
		   if(!Common.empty(goodslog.getGoodsId())&&!Common.empty(goodslog.getSerial())){//保证goodsId存在，serial存在   tempDeliverNum 为之前的actualNum, actualNum为当前的actualNum
			   String sql1="update t_pcs_goods set goodsCurrent=ROUND(goodsCurrent-"+goodslog.getActualNum()+"+"+goodslog.getTempDeliverNum()+",3) where id="+goodslog.getGoodsId();
			   executeUpdate(sql1);
			   String sql2="update t_pcs_weighbridge set outWeigh="+goodslog.getActualNum();
              if(goodslog.getCreateTime()!=0&&goodslog.getCreateTime()!=-1){
            	  sql2+=",outTime="+goodslog.getCreateTime();
              }  
              sql2+=" where serial='"+goodslog.getSerial()+"'";
			   executeUpdate(sql2);
			   String sql3 = "UPDATE t_pcs_lading l SET l.goodsDelivery=round(goodsDelivery+"+goodslog.getActualNum()+"-"+goodslog.getTempDeliverNum()+",3)  WHERE l.id=(SELECT ladingId FROM t_pcs_goods_group WHERE id=(SELECT goodsGroupId FROM t_pcs_goods WHERE id="+goodslog.getGoodsId()+"))" ;
			   executeUpdate(sql3);
		   }
		}catch(OAException e) {
			e.printStackTrace();
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新货体当前存量失败", e);
		}
	}

	@Override
	public void updateWeight(WeighBridge w2) throws OAException {
		String sql ="update t_fm_etl_weight  set fOutTime=FROM_UNIXTIME("+w2.getOutTime()+") where notify="+w2.getSerial();
		executeUpdate(sql);
	}

	/**
	 * @Title getOutboundReviewStatus
	 * @Descrption:TODO
	 * @param:@param servalNo
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年6月21日上午8:29:35
	 * @throws
	 */
	@Override
	public Map<String, Object> getOutboundReviewStatus(String servalNo) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append(" SELECT IFNULL(b.`status`,-1) reviewStatus FROM t_pcs_goodslog a ")
			.append(" LEFT JOIN t_pcs_approve b	ON b.modelId=4 and b.refId=a.batchId ")
			.append(" WHERE 1=1 and a.deliverType!=1 and a.type=5 ");
			if(!Common.isNull(servalNo)){
				sql.append(" and a.serial='").append(servalNo).append("'");
			}else{
				sql.append(" and a.id=-1 ");
			}
			sql.append(" limit 0,1");	
			return executeProcedureOne(sql.toString());
		}catch(RuntimeException e) {
			e.printStackTrace();
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取船舶出库数量确认审核状态失败", e);
		}
	}

}
