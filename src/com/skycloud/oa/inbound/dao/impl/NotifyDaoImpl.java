package com.skycloud.oa.inbound.dao.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.NotifyDao;
import com.skycloud.oa.inbound.dto.NotifyDto;
import com.skycloud.oa.inbound.model.Notify;
import com.skycloud.oa.utils.Common;

/**
 * 其他通知单
 * 
 * @author 作者:jiahy
 * @version 时间：2015年3月16日 下午2:23:48
 */
@Component
public class NotifyDaoImpl extends BaseDaoImpl implements NotifyDao {
	private static Logger LOG = Logger.getLogger(NotifyDaoImpl.class);

	@Override
	public List<Map<String, Object>> getNotifyList(NotifyDto notifyDto,
			int start, int limit) throws OAException {
		try{
		StringBuilder sql=new StringBuilder();
			 sql.append("select a.id,a.code,a.type,a.contentType,a.content,a.createUserId,b.name createUserName,")
			 .append("(SELECT e.name FROM t_pcs_notify w LEFT JOIN t_auth_user  e ON e.id=w.sureUserId WHERE w.CODE = a.CODE AND w.contentType = 1 AND w.flag = 0 LIMIT 0,1) strn,")
			 .append("(SELECT e.name FROM t_pcs_notify w LEFT JOIN t_auth_user  e ON e.id=w.sureUserId WHERE w.CODE = a.CODE AND w.contentType = 3 AND w.flag = 0 LIMIT 0,1) endn,")
			 .append("from_unixtime(a.createTime) createTime,a.sureUserId,c.name sureUserName,from_unixtime(a.sureTime) sureTime,")
			 .append("a.state,a.status,a.flag,a.batchId,a.description,a.firPFDSvg,a.secPFDSvg  ")
			 .append(" from t_pcs_notify a ")
			 .append(" LEFT JOIN t_auth_user b on a.createUserId=b.id ")
			 .append(" LEFT JOIN t_auth_user c on a.sureUserId=c.id")
			 .append(" where 1=1");
			if (null != notifyDto.getId())
				sql.append(" and a.id=").append(notifyDto.getId());

			if (!Common.isNull(notifyDto.getCode()))
				sql.append(" and a.code='").append(notifyDto.getCode()).append("'");

			if (!Common.isNull(notifyDto.getCodeF()))
				sql.append(" and a.code like '%").append(notifyDto.getCodeF()).append("%'");
			if (!Common.isNull(notifyDto.getKeyWord()))
				sql.append(" and a.content like '%").append(notifyDto.getKeyWord()).append("%'");
			
			if (null != notifyDto.getTypes()) {
				if (null != notifyDto.getIsList() && 1 == notifyDto.getIsList())
					sql.append(" and a.code is not null and a.type in (")
					.append(notifyDto.getTypes())
					.append(") and a.contentType=0");
				else
					sql.append(" and a.type in (").append(notifyDto.getTypes()).append(")");
			}
			if (null != notifyDto.getStartTime())
				sql.append(" and a.createTime>=").append(notifyDto.getStartTime());

			if (null != notifyDto.getEndTime())
				sql.append(" and a.createTime<=").append(notifyDto.getEndTime());

			if (null != notifyDto.getCreateTime())
				sql.append(" and a.code is not null  and a.contentType=0 and a.createTime=")
				.append(notifyDto.getCreateTime());

			sql.append(" order by a.code desc");
			if (limit != 0)
				sql.append(" limit ").append(start + ",").append(limit);
			  
		 return executeQuery(sql.toString());
		 }catch(RuntimeException e){
		LOG.error("dao查询通知单失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询通知单失败", e);
		 } 
	 }
	@Override
	public List<Map<String, Object>> getNotifyShipInfoList(NotifyDto notifyDto, int start, int limit) throws OAException {
		// TODO Auto-generated method stub
		//获取通知单类型
		String typeString=notifyDto.getTypes();
		
		//所有需要船信通知单列表，A类和B类
		List<String> allShipInfoA=Arrays.asList("2","11","13","15","12");
		List<String> allShipInfoB=Arrays.asList("3","14","16");
		
		try {
			String sql = "select a.id,a.code,a.type,a.contentType,a.content,a.createUserId,b.name createUserName,from_unixtime(a.createTime) createTime,"
					+"(SELECT e.name FROM t_pcs_notify w LEFT JOIN t_auth_user  e ON e.id=w.sureUserId WHERE w.CODE = a.CODE AND w.contentType = 1 AND w.flag = 0 LIMIT 0,1) strn,"
					+"(SELECT e.name FROM t_pcs_notify w LEFT JOIN t_auth_user  e ON e.id=w.sureUserId WHERE w.CODE = a.CODE AND w.contentType = 3 AND w.flag = 0 LIMIT 0,1) endn,"
					+ " a.sureUserId,c.name sureUserName,from_unixtime(a.sureTime) sureTime,a.state,a.status,a.flag,a.batchId,a.description,a.firPFDSvg,a.secPFDSvg ";


			if (null != typeString && allShipInfoA.indexOf(typeString) != -1) {
				sql = sql + ",from_unixtime(d.openPumpTime) openPumpTime,from_unixtime(d.stopPumpTime) stopPumpTime, CONCAT(f.name, '/',g.refName, '/', e.arrivalStartTime) shipInfo, d.noticeCodeA code "
						+ " ,e.type arrivalType,from_unixtime(w.openPump) openPump,from_unixtime(w.stopPump) stopPump "
						+ " from t_pcs_notify a "
						+ " LEFT JOIN t_auth_user b on a.createUserId=b.id "
						+ " LEFT JOIN t_auth_user c on a.sureUserId=c.id"
						+ " LEFT JOIN t_pcs_transport_program d ON d.noticeCodeA = a.code"
						+ " LEFT JOIN t_pcs_arrival e ON e.id = d.arrivalId"
						+ " LEFT JOIN t_pcs_ship f ON f.id = e.shipId"
						+ " LEFT JOIN t_pcs_ship_ref g ON g.id = e.shipRefId";//增加
				if("13".indexOf(typeString)!=-1){
					sql+= " LEFT JOIN t_pcs_work w ON w.arrivalId = e.id and d.productId=w.productId and d.orderNum=w.orderNum  ";
				}else{
					sql+= " LEFT JOIN t_pcs_work w ON w.arrivalId = e.id and w.orderNum=0 ";//增加
				}
						sql+= " where 1=1";
			} else if (null != typeString
					&& allShipInfoB.indexOf(typeString) != -1) {
				sql = sql + ",from_unixtime(d.openPumpTime) openPumpTime,from_unixtime(d.stopPumpTime) stopPumpTime, CONCAT(f.name, '/',g.refName, '/', e.arrivalStartTime) shipInfo, d.noticeCodeB code "
						+ " from t_pcs_notify a "
						+ " LEFT JOIN t_auth_user b on a.createUserId=b.id "
						+ " LEFT JOIN t_auth_user c on a.sureUserId=c.id"
						+ " LEFT JOIN t_pcs_transport_program d ON d.noticeCodeB = a.code"
						+ " LEFT JOIN t_pcs_arrival e ON e.id = d.arrivalId"
						+ " LEFT JOIN t_pcs_ship f ON f.id = e.shipId"
						+ " LEFT JOIN t_pcs_ship_ref g ON g.id = e.shipRefId"//增加
						+ " where 1=1";
			} else {
				sql = sql + " from t_pcs_notify a "
						+ " LEFT JOIN t_auth_user b on a.createUserId=b.id "
						+ " LEFT JOIN t_auth_user c on a.sureUserId=c.id"
						+ " where 1=1";
			}
			if (null != notifyDto.getId()) {
				sql += " and a.id=" + notifyDto.getId();
			}
			if (!Common.isNull(notifyDto.getCode())) {
				sql += " and a.code='" + notifyDto.getCode() + "'";
			}
			if (!Common.isNull(notifyDto.getCodeF())) {
				sql += " and a.code like '%" + notifyDto.getCodeF() + "%'";
			}
			if (!Common.isNull(notifyDto.getKeyWord())) {
				sql += " and a.content like '%" + notifyDto.getKeyWord() + "%'";
			}
			if (null != typeString) {
				if (null != notifyDto.getIsList() && 1 == notifyDto.getIsList()) {
					sql += " and a.code is not null and a.type in ("
							+ notifyDto.getTypes() + ") and a.contentType=0";
				} else {
					sql += " and a.type in (" + notifyDto.getTypes() + ")";
				}
			}
			if (null != notifyDto.getStartTime()) {
				sql += " and a.createTime>=" + notifyDto.getStartTime();
			}
			if (null != notifyDto.getEndTime()) {
				sql += " and a.createTime<=" + notifyDto.getEndTime();
			}
			if (null != notifyDto.getCreateTime()) {
				sql += " and a.code is not null  and a.contentType=0 and a.createTime="
						+ notifyDto.getCreateTime();
			}
			sql += " order by a.code desc";
			if (limit != 0) {
				sql += " limit " + start + "," + limit;
			}
			return executeQuery(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询通知单失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询通知单失败", e);
		}
	}
	@Override
	public int getNotifyCount(NotifyDto notifyDto) throws OAException {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select count(1) from t_pcs_notify a  where 1=1");
			if (null != notifyDto.getId()) 
				sql.append(" and a.id=").append(notifyDto.getId());
			
			if (!Common.isNull(notifyDto.getCode())) 
				sql.append(" and a.code='").append(notifyDto.getCode()).append("'");
			
			if (!Common.isNull(notifyDto.getCodeF())) 
				sql.append(" and a.code like '%").append(notifyDto.getCodeF()).append("%'");
			
			if (null != notifyDto.getTypes()) {
				if (null != notifyDto.getIsList() && 1 == notifyDto.getIsList()) 
					sql.append(" and a.code is not null and a.type in (").append(notifyDto.getTypes())
							.append(") and a.contentType=0");
				 else 
					sql.append(" and a.type in (").append(notifyDto.getTypes()).append(")");
				
			}
			if (null != notifyDto.getStartTime()) 
				sql.append(" and a.createTime>=").append(notifyDto.getStartTime());
			
			if (null != notifyDto.getEndTime()) 
				sql.append(" and a.createTime<=").append(notifyDto.getEndTime());
			
			if (null != notifyDto.getCreateTime()) 
				sql.append(" and code!=null and a.contentType=0 and a.createTime=").append(notifyDto.getCreateTime());
			
			return (int) getCount(sql.toString());
		} catch (RuntimeException e) {
			LOG.error("dao获取通知单数量失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取通知单数量失败", e);
		}
	}
	
	@Override
	public void addNotify(Notify notify) throws OAException {
		try {
			save(notify);
		} catch (RuntimeException e) {
			LOG.error("dao添加通知单失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加通知单失败", e);
		}
	}

	@Override
	public void updateNotify(Notify notify) throws OAException {
		try {
			String sql = "update t_pcs_notify set id=id";
			if (null != notify.getId()) {
				sql += " ,id=" + notify.getId();
			}
			if (null != notify.getCode()) {
				sql += " ,code='" + notify.getCode() + "'";
			}
			if (null != notify.getType()) {
				sql += " ,type=" + notify.getType();
			}
			if (null != notify.getContentType()) {
				sql += " ,contentType=" + notify.getContentType();
			}
			if (null != notify.getContent()) {
				sql += " ,content='" + notify.getContent() + "'";
			}
			if (null != notify.getState()) {
				sql += " ,state=" + notify.getState();
			}
			if (null != notify.getStatus()) {
				sql += " ,status=" + notify.getStatus();
			}
			if (null != notify.getFlag()) {
				sql += " ,flag=" + notify.getFlag();
			}
			if (null != notify.getCreateUserId()) {
				sql += " ,createUserId=" + notify.getCreateUserId();
			}
			if (null != notify.getCreateTime()) {
				sql += " ,createTime=" + notify.getCreateTime();
			}
			if (null != notify.getSureUserId()) {
				sql += " ,sureUserId=" + notify.getSureUserId();
			}
			if (null != notify.getSureTime()) {
				sql += " ,sureTime=" + notify.getSureTime();
			}
			if (null != notify.getDescription()) {
				sql += " ,descrition='" + notify.getDescription() + "'";
			}
			if (null != notify.getFirPFDSvg()) {
				sql += ", firPFDSvg='" + notify.getFirPFDSvg() + "'";
			}
			if (null != notify.getSecPFDSvg()) {
				sql += ", secPFDSvg='" + notify.getSecPFDSvg() + "'";
			}
			sql += " where id=" + notify.getId();
			executeUpdate(sql);
		} catch (RuntimeException e) {
			LOG.error("dao更新通知单失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新通知单失败", e);
		}
	}

	@Override
	public void deleteNotify(NotifyDto notifyDto) throws OAException {
		try {
			String sql = "delete from t_pcs_notify where 1=1 ";
			if (notifyDto.getCodes() != null) {
				sql += " and code in(" + notifyDto.getCodes() + ")";
			}
			if (notifyDto.getTransportId() != null) {
				sql += " and FIND_IN_SET(`code`,(select  CONCAT(a.noticeCodeA,',',a.noticeCodeB) AS CODE from t_pcs_transport_program a where a.id="
						+ notifyDto.getTransportId()
						+ " and !ISNULL(a.noticeCodeA))) and !ISNULL(code)";
			}
			execute(sql);
		} catch (RuntimeException e) {
			LOG.error("dao删除通知单失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao删除通知单失败", e);
		}
	}

	

	@Override
	public void resetNotify(NotifyDto notifyDto) throws OAException {
		try {
			String sql = "update t_pcs_notify set id=id,sureTime=null,sureUserId=null,state=0,status=0 where code='"
					+ notifyDto.getCode() + "' and contentType=0";
			execute(sql);
			sql = "delete from t_pcs_notify where code='" + notifyDto.getCode()
					+ "' and contentType!=0";
			execute(sql);
		} catch (RuntimeException e) {
			LOG.error("dao删除通知单失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao删除通知单失败", e);
		}
	}

	@Override
	public void cleanNotify(int id, boolean b) throws OAException {
		try {

			if(b){
				String sql="delete a from t_pcs_notify a,t_pcs_transport_program b WHERE a.code=b.noticeCodeA and b.arrivalId="+id;

				execute(sql);

				String sql1="delete a from t_pcs_notify a,t_pcs_transport_program b WHERE a.code=b.noticeCodeB and b.arrivalId="+id;

				execute(sql1);

			}else{
				String sql="delete a from t_pcs_notify a,t_pcs_transport_program b WHERE a.code=b.noticeCodeA and b.id="+id;

				execute(sql);
				String sql1="delete a from t_pcs_notify a,t_pcs_transport_program b WHERE a.code=b.noticeCodeB and b.id="+id;
				execute(sql1);
//				String sql="delete from t_pcs_notify WHERE FIND_IN_SET(`code`,(select  CONCAT(a.noticeCodeA,',',a.noticeCodeB) AS CODE from t_pcs_transport_program a where a.id="+id+" and !ISNULL(a.noticeCodeA))) and !ISNULL(code)";
//				execute(sql);
			}
		} catch (RuntimeException e) {
			LOG.error("dao删除通知单失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao删除通知单失败", e);
		}
	}

	@Override
	public void cleanNotify(Integer arrivalId, Integer productId,
			Integer orderNum, boolean b) throws OAException {
		try {
			if (b) {// 打循环，接卸
				String sql = "delete from t_pcs_notify WHERE FIND_IN_SET(`code`,(select  CONCAT(a.noticeCodeA,',',a.noticeCodeB) AS CODE from t_pcs_transport_program a where a.arrivalId="
						+ arrivalId
						+ " and a.productId="
						+ productId
						+ " and a.orderNum="
						+ orderNum
						+ " and a.type=0 and !ISNULL(a.noticeCodeA))) and !ISNULL(code)";
				execute(sql);
				sql = "delete from t_pcs_notify WHERE FIND_IN_SET(`code`,(select  CONCAT(a.noticeCodeA,',',a.noticeCodeB) AS CODE from t_pcs_transport_program a where a.arrivalId="
						+ arrivalId
						+ " and a.productId="
						+ productId
						+ " and a.orderNum="
						+ orderNum
						+ " and a.type=1 and !ISNULL(a.noticeCodeA))) and !ISNULL(code)";
				execute(sql);
			} else {// 打循环
				String sql = "delete from t_pcs_notify WHERE FIND_IN_SET(`code`,(select  CONCAT(a.noticeCodeA,',',a.noticeCodeB) AS CODE from t_pcs_transport_program a where a.arrivalId="
						+ arrivalId
						+ " and a.productId="
						+ productId
						+ " and a.orderNum="
						+ orderNum
						+ " and a.type=1 and !ISNULL(a.noticeCodeA))) and !ISNULL(code)";
				execute(sql);
			}
		} catch (RuntimeException e) {
			LOG.error("dao删除通知单失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao删除通知单失败", e);
		}
	}

	@Override
	public Map<String, Object> getNotifyCode() throws OAException {
		// TODO Auto-generated method stub
		String sql = "SELECT CONCAT('R',LPAD(MAX(SUBSTR(n.code,2))+1,7,0)) code FROM t_pcs_notify n WHERE n.type=17";
		return executeQueryOne(sql);
	}

	@Override
	public Map<String, Object> getNotifyNum(NotifyDto notifyDto)
			throws OAException {
		try {
			String sql = "select LPAD(SUBSTR(IFNULL(MAX(code),'A0000000'),2,8)+1,7,0) codeNum from t_pcs_notify a where 1=1  and a.type in (" + notifyDto.getTypes()+ ")  and a.contentType=0 ";
			return executeQueryOne(sql);
		} catch (RuntimeException e) {
			LOG.error("dao删除通知单失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao删除通知单失败", e);
		}
	}

	@Override
	public void updateNotifyState(Notify notify) throws OAException {
		try {
			if (notify.getCode() != null && notify.getState() != null) {
				String sql = "update t_pcs_notify set state="
						+ notify.getState() + " where code='"
						+ notify.getCode() + "' and contentType=0";
				executeUpdate(sql);
			}
		} catch (RuntimeException e) {
			LOG.error("dao删除通知单失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao删除通知单失败", e);
		}
	}
	/**
	 * @throws OAException 
	 * @Title checkNotify
	 * @Descrption:TODO
	 * @param:@param notify
	 * @param:@return
	 * @auhor jiahy
	 * @date 2018年1月9日上午11:02:20
	 * @throws
	 */
	@Override
	public Map<String, Object> checkNotify(Notify notify) {
		 StringBuffer sql =new StringBuffer();
		 sql.append("SELECT count(1) num,id from t_pcs_notify where code ='").append(notify.getCode()).append("' and contentType = 0");
		try {
			return executeQueryOne(sql.toString());
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
