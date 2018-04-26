package com.skycloud.oa.common.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.common.dao.BaseControllerDao;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dto.CargoAgentDto;
import com.skycloud.oa.system.model.Truck;
import com.skycloud.oa.utils.Common;

@Component
public class BaseControllerDaoImpl extends BaseDaoImpl implements BaseControllerDao 
{
	/**
	 * 记录日志
	 */
	private static Logger logger = Logger.getLogger(BaseControllerDaoImpl.class);
	
	public List<Map<String, Object>> getVehiclePlateList(String vehiclePlate)throws OAException{
		String sql="SELECT id,code FROM t_pcs_truck where 1=1";
		if(!Common.empty(vehiclePlate)){
			sql = sql +" and code like '%"+vehiclePlate+"%'" ;
		}
		return executeQuery(sql);
	}


	@Override
	public List<Map<String, Object>> getArrivalShipInfo() throws OAException {
		
		String sql = "SELECT a.id, (case when a.type=4 then '*' else CONCAT(s.name,'/',sr.refName,'[',DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d'),']') end ) name FROM t_pcs_arrival a,t_pcs_ship s,t_pcs_ship_ref sr WHERE a.shipId=s.id AND a.shipRefId=sr.id AND (a.type=1 or a.type=3) order by a.arrivalStartTime desc" ;
		return executeQuery(sql) ;
	}


	@Override
	public List<Map<String,Object>>  getWeather() throws OAException {
		
		String sql = "SELECT * FROM t_pcs_weather" ;
		return executeQuery(sql);
	}


	@Override
	public List<Map<String, Object>> getTrans(String arrivalId)
			throws OAException {
		
		String sql = "SELECT DISTINCT t.tubeId,tb.name FROM t_pcs_trans t LEFT JOIN t_pcs_tube tb ON t.tubeId=tb.id WHERE t.transportId in (SELECT id FROM t_pcs_transport_program tpg WHERE tpg.arrivalId="+arrivalId+")" ;
		return executeQuery(sql);
	}


	@Override
	public List<Map<String, Object>> getUser() throws OAException {
		
		String sql = "select id,name from t_auth_user" ;
		return executeQuery(sql);
	}


	@Override
	public List<Map<String,Object>>  getWindDirection() throws OAException {
		
		String sql = "SELECT * FROM t_pcs_wind_direction" ;
		return executeQuery(sql);
	}


	@Override
	public List<Map<String,Object>>  getWindPower() throws OAException {
		
		String sql = "SELECT * FROM t_pcs_wind_power" ;
		return executeQuery(sql);
	}

	/**
	 * 根据船舶名称查询船信息
	 * @Title:getShipName
	 * @Description:
	 * @param shipName
	 * @return
	 * @throws OAException
	 * @see
	 */
	@Override
	public List<Map<String, Object>> getShipName(String shipName) throws OAException 
	{
		String sql="SELECT id,name FROM t_pcs_ship where 1=1";
		if(!Common.empty(shipName))
		{
			sql = sql +" and name like '%"+shipName+"%'" ;
		}
		
		return executeQuery(sql);
	}
	
	/**
	 * 根据货品ID查询船舶名称
	 * @Title:getCanMakeInvoiceShipName
	 * @Description:
	 * @param productId
	 * @return
	 * @throws OAException
	 * @see
	 */
	@Override
	public List<Map<String, Object>> getCanMakeInvoiceShipName(String productId,String ladingCode,Integer isNoTransport) throws OAException 
	{
		StringBuffer sql = new StringBuffer();
sql.append(" SELECT  DISTINCT a.id , a.shipId, a.shipRefId, a.arrivalStartTime,d.loadCapacity, ");
sql.append(" CONCAT(b.refName,'/',d.name,'/',DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d')) shipInfo ");
sql.append("  FROM t_pcs_arrival a LEFT JOIN t_pcs_ship d ON  a.shipId=d.id LEFT JOIN t_pcs_ship_ref b ON  a.shipRefId=b.id LEFT JOIN t_pcs_arrival_plan c ON  c.arrivalId=a.id ");
sql.append(" WHERE  a.type=2 and c.status=2 and a.status>=50 and a.status<54 ");
if(!Common.empty(productId))
{
sql.append(" AND a.productId ="+productId );
}
if(!Common.empty(ladingCode)){
	sql.append(" AND c.ladingCode ='"+ladingCode+"' ");
}
if(isNoTransport!=null&&isNoTransport==1){
	sql.append(" AND b.refName!='转输'");
}
sql.append(" ORDER BY a.arrivalStartTime DESC  "); 
		
	return executeQuery(sql.toString());
	}
	
	/**
	 * 获取车牌号码
	 * @Title:getCanMakeInvoiceTruck
	 * @Description:
	 * @return
	 * @throws OAException
	 * @see
	 */
	@Override
	public List<Map<String, Object>> getCanMakeInvoiceTruck() throws OAException 
	{
		String sql="SELECT t.id,t.code as name FROM t_pcs_truck t order by t.id ";
		return executeQuery(sql);
	}


	@Override
	public List<Map<String, Object>> getParkList(String parkName,String ids)
			throws OAException {
		
		String sql="SELECT id,name FROM t_pcs_park where 1=1";
		if(!Common.empty(parkName)){
			sql = sql +" and name like '%"+parkName+"%'" ;
		}
		if(!Common.empty(ids)){
			sql = sql +" and id not in ("+ids+")" ;
		}
		return executeQuery(sql);
	}


	@Override
	public List<Map<String, Object>> getTankName(String tankName)
			throws OAException {
		
		String sql = "select t.*,p.name productName from t_pcs_tank t left join t_pcs_product p on p.id=t.productId where 1=1" ;
		if(!Common.empty(tankName)){
			sql = sql +" and name code '"+tankName+"'" ;
		}
		return executeQuery(sql);
	}


	@Override
	public List<Map<String, Object>> getBerthByName(String berthName)
			throws OAException {
		
		String sql = "select id,name from t_pcs_berth where 1=1" ;
		if(!Common.empty(berthName)){
			sql = sql +" and name like '"+berthName+"'" ;
		}
		return executeQuery(sql);
	}


	@Override
	public List<Map<String, Object>> getVehiclePlateByTrainId(String trainId)
			throws OAException {
		String sql = "SELECT t.id,t.code FROM t_pcs_batch_vehicle_info vi LEFT JOIN t_pcs_truck t ON vi.plateId = t.id WHERE vi.trainId ="+trainId ;
		return executeQuery(sql);
	}


	@Override
	public List<Map<String, Object>> getParkName(String parkName)
			throws OAException {
		
		String sql = "select id,name from t_pcs_park where 1=1" ;
		if(!Common.empty(parkName)){
			sql = sql +" and name like '"+parkName+"'" ;
		}
		return executeQuery(sql);
	}


	@Override
	public List<Map<String, Object>> getShipChineseName(String shipId)
			throws OAException {
		String sql="SELECT id,refName FROM t_pcs_ship_ref where 1=1";
		if(!Common.empty(shipId)){
			sql = sql +" and shipId ="+shipId ;
		}
		return	executeQuery(sql);
		 
	}

	@Override
	public List<Map<String, Object>> getLadingCodeList(String id,String code,String productId)
			throws OAException {
		
		if(code==null){
			code = "" ;
		}
		String sql="SELECT id,code,type,goodsTotal,(ifnull(goodsPass,0)-ifnull(goodsDelivery,0)-ifnull(goodsOut,0)) surplus  FROM t_pcs_lading where status=1 and code like '%"+code+"%'" ;
		if(!Common.empty(id)){
			sql =sql + " and receiveClientId=="+id ;
		}
		if(!Common.empty(productId)){
			sql =sql + " and productId="+productId ;
		}
		return executeQuery(sql);
	}

	@Override
	public List<Map<String, Object>> getClientNameList(String clientName)
			throws OAException {
		
		StringBuffer sb = new StringBuffer() ;
		sb.append("	SELECT DISTINCT 	");
		sb.append("	  c.id,	");
		sb.append("	  c.name,	");
		sb.append("	  c.code 	");
		sb.append("	FROM	");
		sb.append("	  t_pcs_client c 	");
		sb.append("	   where 1=1 and isnull(c.status) ");
		if(!Common.empty(clientName))
		{
			sb.append("	 and c.name like '%"+clientName+"%'" );
		}
		
		logger.info("客户名称getClientNameList："+sb.toString());
		
		return executeQuery(sb.toString());
	}
	
	
	
	@Override
	public List<Map<String, Object>> getHistoryClientName() throws OAException 
	{
		StringBuffer sb = new StringBuffer() ;
		sb.append("	SELECT DISTINCT c.id,c.name,c.code FROM t_pcs_client c ,t_pcs_goodslog g WHERE  c.id=g.ladingClientId");
		
		logger.info("查询货主getHistoryClientName："+sb.toString());
		
		return executeQuery(sb.toString());
	}
	
	
	@Override
	public List<Map<String, Object>> getClientNameByProductId(String clientName,String productId) 
			throws OAException {
		
		StringBuffer sb = new StringBuffer() ;
		sb.append("	SELECT distinct c.id,c.name,c.code FROM t_pcs_client c ,t_pcs_goods g WHERE isnull(c.status) and ((g.clientId=c.id AND (ISNULL(g.ladingClientId) OR g.ladingClientId=0)) OR g.ladingClientId=c.id) and IFNULL(g.goodsCurrent,0)>0 and g.productId="+productId);
		sb.append("	order by  CONVERT(c.name USING gbk) asc");
		
		logger.info("查询客户名称getClientNameByProductId："+sb.toString());
		
		return executeQuery(sb.toString());
	}

	@Override
	public List<Map<String, Object>> getProductNameList(String productName)
			throws OAException {
		
		String sql="SELECT id,name FROM t_pcs_product where 1=1";
		if(!Common.empty(productName)){
			sql = sql +" and name like '%"+productName+"%'" ;
		}
		return executeQuery(sql);
	}


	@Override
	public List<Map<String, Object>> getTankCode(Integer productId) throws OAException {
		String sql="select a.id,a.code,a.productId,b.name productName from t_pcs_tank a LEFT JOIN t_pcs_product b on a.productId=b.id where  !ISNULL(a.key) and a.key!='' ";
		if(!Common.isNull(productId)){
			 sql+=" and b.id="+productId ;
		}
		return executeQuery(sql);
		
	}
	
	
	@Override
	public List<Map<String, Object>> getCargoAgentList(CargoAgentDto caDto,int start,int limit) throws OAException {
		try {
		String sql="select * from t_pcs_cargo_agent where 1=1 ";
		if(!Common.empty(caDto.getId())){
			sql+="and id="+caDto.getId();
		}
		if(!Common.empty(caDto.getCode())){
			sql+="and code= '"+caDto.getCode()+"'";
		}
		if(!Common.empty(caDto.getName())){
			sql+="and name= '"+caDto.getName()+"'";
		}
		if(limit!=0){
			sql+=" limit "+start+" , "+limit;
		}
		
		
		return executeQuery(sql);
	} catch (RuntimeException e) {
		throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
	}
	}


	@Override
	public void sendSystemMessage(String upInfo,int userId,String right,int item) throws OAException {
		
		String sql1 = "INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) SELECT CONCAT(a.name ,'在[',DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'),']更新了"+upInfo+"的基本信息。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),"+userId+",2,'#/baseinfo/list?item="+item+"' FROM t_auth_user a WHERE a.id="+userId ;
		String sql2 = "INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS) SELECT "
						+" DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),1  " 
						+" FROM " 
						+"   `t_auth_authorization` u , " 
						+"   `t_auth_security_resources` t,  " 
						+"   `t_auth_resource_assignments` ta  " 
						+" WHERE t.indentifier ='"+right 
						+"'   AND ta.sourceId = t.id  " 
						+"   AND ta.roleId = u.roleId";
		executeUpdate(sql1);
		executeUpdate(sql2);
	}


	@Override
	public List<Map<String, Object>> getParams() throws OAException
	{
		return executeQuery("SELECT * FROM `t_sys_params`");
	}


	/**
	 * 查询船信
	 * @Title:queryShipInfo
	 * @Description:
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public List<Map<String, Object>> queryShipInfo() throws OAException 
	{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT r.`id` id,CONCAT(r.`refName`, '(', p.`name`, ')') name ");
		sql.append("FROM t_pcs_ship_ref r LEFT JOIN t_pcs_ship p ON r.`shipId` = p.`id` ");
		sql.append("WHERE p.`name` IS NOT NULL ");
		
		logger.info("查询流量计台帐中船名："+sql.toString());
		
		return executeQuery(sql.toString());
	}

	/**
	 * 系统管理-基础信息-客户资料-添加客户编号总数
	 * @Title:queryClientCount
	 * @Description:
	 * @param queryStr
	 * @return
	 * @throws OAException
	 * @see
	 */
	@Override
	public List<Map<String, Object>> queryClientCount(String queryStr) throws OAException 
	{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) nums FROM t_pcs_client t WHERE 1=1 ");
		if(!Common.empty(queryStr))
		{
			sql.append(" and t.code like '").append(queryStr).append("%' ");
		}
		
		logger.info("查询系统管理-基础信息-客户资料-添加客户编号总数："+sql.toString());
		
		return executeQuery(sql.toString());
	}

	/**
	 * 查询某辆车的核载量
	 * @Title:queryOneCar
	 * @Description:
	 * @param carNo
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public List<Map<String, Object>> queryOneCar(String carNo) throws OAException 
	{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT t.`id`,t.`code`,t.`name`,t.`loadCapacity`,t.maxLoadCapacity FROM t_pcs_truck t WHERE 1=1 ");
		if(!Common.empty(carNo))
		{
			sql.append(" and t.code = '").append(carNo).append("' ");
		}
		
		logger.info("查询某辆车的核载量："+sql.toString());
		
		return executeQuery(sql.toString());
	}

	/**
	 * 更新某辆车的核载量
	 * @Title:updateOneCar
	 * @Description:
	 * @param carNo
	 * @param carAmount
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public void updateOneCar(Truck truck) throws OAException 
	{
		StringBuffer sql = new StringBuffer();
		sql.append("update t_pcs_truck t set t.loadCapacity = '").append(truck.getLoadCapacity()).append("' ");
		sql.append(" WHERE 1=1 ");
		if(!Common.empty(truck.getCode()))
		{
			sql.append(" and t.code = '").append(truck.getCode()).append("' ");
		}
		
		logger.info("更新某辆车的核载量："+sql.toString());
		
		executeUpdate(sql.toString());
	}


	@Override
	public List<Map<String, Object>> getCargoList() throws OAException {
	try {
		return executeQuery("select id,code from t_pcs_cargo where code is not null");
	} catch (RuntimeException e) {
		logger.error("dao查询失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
	}
	}
	
	
}
