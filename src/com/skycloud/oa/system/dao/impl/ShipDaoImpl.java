package com.skycloud.oa.system.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dao.ShipDao;
import com.skycloud.oa.system.dto.ShipDto;
import com.skycloud.oa.system.model.Ship;
import com.skycloud.oa.system.model.ShipRef;
import com.skycloud.oa.utils.Common;

@Repository
public class ShipDaoImpl extends BaseDaoImpl implements ShipDao {
	private static Logger LOG = Logger.getLogger(ShipDaoImpl.class);

	@Override
	public List<Map<String, Object>> getShipList(ShipDto sDto, int start,
			int limit) throws OAException {
		try {
			String sql="";
			if(!Common.empty(sDto.getLetter())&&!("all").equals(sDto.getLetter())){
				 sql = "select a.*,s.name editUserName, (select GROUP_CONCAT(x.refName) from t_pcs_ship_ref x where x.shipId=a.id and x.status=0) shipRefNames from t_pcs_ship a LEFT JOIN t_auth_user s on s.id=a.editUserId,t_sys_pinyin b where 1=1 and isnull(a.status) ";
				
				String up=sDto.getLetter().toUpperCase();
				
				sql+=" and (a.`name` like '"+sDto.getLetter()+"%' or a.`name` like '"+up+"%' or CONV(HEX(left(CONVERT(a.`name` USING gbk),1)),16,10) between b.charBegin and b.charEnd) and b.letter='"+up+"'";
			}else{
				 sql = "select a.*,s.name editUserName,(select GROUP_CONCAT(x.refName) from t_pcs_ship_ref x where x.shipId=a.id and x.status=0) shipRefNames from t_pcs_ship a LEFT JOIN t_auth_user s on s.id=a.editUserId where 1=1 and isnull(a.status) ";
			}
			
			
			if (!Common.empty(sDto.getId())) {
				sql += " and a.id=" + sDto.getId();
			}
			if (!Common.empty(sDto.getCode())) {
				sql += " and (( a.code like '%" + sDto.getCode()
						+ "%' or a.name like '%" + sDto.getName()
						+ "%' or a.refCode like '%" + sDto.getRefCode()
						+ "%' or a.owner like '%" + sDto.getOwner()
						+ "%' or a.contactName like '%" + sDto.getContactName()
						+ "%' or a.manager like '%" + sDto.getManager()+ "%' )";
//						+ "%' or refName like '%" + sDto.getRefName() + "%' )";
				sql+=" or a.id in (select  shipId from t_pcs_ship_ref where refName like '%"+sDto.getRefName()+"%' ) )";
			}
			
//			if(!Common.empty(sDto.getRefName())){
//			}
			sql+=" and a.id>0 ";
			sql += " order by CONVERT( CODE USING gbk ) COLLATE gbk_chinese_ci ASC";
			if (limit != 0) {
				sql += " limit " + start + " , " + limit;
			}
			return executeQuery(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}

	@Override
	public List<Map<String, Object>> getShipREfList(String shipId)
			throws OAException {
		// TODO Auto-generated method stub
		String sql = "select * from t_pcs_ship_ref where status<>1 and shipId=" + shipId;
		return executeQuery(sql);
	}

	@Override
	public void deleteShipRefInfo(String shipRefId) throws OAException {
		// TODO Auto-generated method stub
		String sql = "update   t_pcs_ship_ref set status=1 where id=" + shipRefId;
		executeUpdate(sql);
	}

	public int getShipCount(ShipDto sDto, int start, int limit)
			throws OAException {
		try {
			String sql = "select * from t_pcs_ship where 1=1 and isnull(status) ";
			if (!Common.empty(sDto.getId())) {
				sql += " and id=" + sDto.getId();
			}
			if (!Common.empty(sDto.getCode())) {
				sql += " and ( code like '%" + sDto.getCode() + "%' or name= '%"
						+ sDto.getName() + "%' or refCode= '%"
						+ sDto.getRefCode() + "%' or owner= '%"
						+ sDto.getOwner() + "%' or contactName= '%"
						+ sDto.getContactName() + "%' or manager= '%"
						+ sDto.getManager() + "%' or refName= '%"
						+ sDto.getRefName() + "%' )";
			}
			return (int) getCount(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}

	@Override
	public int getShipListCount(ShipDto sDto) throws OAException {
		try {
			
			String sql="";
			if(!Common.empty(sDto.getLetter())&&!("all").equals(sDto.getLetter())){
				 sql = "select count(a.id) from t_pcs_ship a ,t_sys_pinyin b where 1=1 and isnull(a.status) ";
				
				String up=sDto.getLetter().toUpperCase();
				
				sql+=" and (a.`name` like '"+sDto.getLetter()+"%' or a.`name` like '"+up+"%' or CONV(HEX(left(CONVERT(a.`name` USING gbk),1)),16,10) between b.charBegin and b.charEnd) and b.letter='"+up+"'";
			}else{
				 sql = "select count(a.id) from t_pcs_ship a  where 1=1 and isnull(a.status) ";
			}
			
			
			if (!Common.empty(sDto.getId())) {
				sql += " and a.id=" + sDto.getId();
			}
			if (!Common.empty(sDto.getCode())) {
				sql += " and (( a.code like '%" + sDto.getCode()
						+ "%' or a.name like '%" + sDto.getName()
						+ "%' or a.refCode like '%" + sDto.getRefCode()
						+ "%' or a.owner like '%" + sDto.getOwner()
						+ "%' or a.contactName like '%" + sDto.getContactName()
						+ "%' or a.manager like '%" + sDto.getManager()+ "%' )";
//						+ "%' or refName like '%" + sDto.getRefName() + "%' )";
				sql+=" or a.id in (select  shipId from t_pcs_ship_ref where refName like '%"+sDto.getRefName()+"%' ) )";
			}
			
//			if(!Common.empty(sDto.getRefName())){
//			}
			sql+=" and a.id>0 ";
			sql += " order by CONVERT( CODE USING gbk ) COLLATE gbk_chinese_ci ASC";
			return (int) getCount(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}

	@Override
	public String addShip(Ship ship) throws OAException {
		try {
			Serializable s = save(ship);
			return s.toString();
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}

	@Override
	public void saveShipRef(ShipRef ship) throws OAException {
		// TODO Auto-generated method stub
		save(ship);
	}

	@Override
	public void updateShipRef(ShipRef ship) throws OAException {
		// TODO Auto-generated method stub
		update(ship);
	}

	@Override
	public void updateShip(Ship ship) throws OAException {
		String sql="update t_pcs_ship set id=id ";
		if(!Common.empty(ship.getCode())){
			sql+=" ,code='"+ship.getCode()+"'";
		}
		if(!Common.empty(ship.getName())){
			sql+=" ,name='"+ship.getName()+"'";
		}
		if(!Common.isNull(ship.getType())){
			sql+=" ,type="+ship.getType();
		}
		if(!Common.empty(ship.getShipRegistry())){
			sql+=" ,shipRegistry='"+ship.getShipRegistry()+"'";
		}
		if(!Common.empty(ship.getShipLenth())){
			sql+=" ,shipLenth='"+ship.getShipLenth()+"'";
		}
		if(!Common.empty(ship.getShipWidth())){
			sql+=" ,shipWidth='"+ship.getShipWidth()+"'";
		}
		if(!Common.empty(ship.getShipDraught())){
			sql+=" ,shipDraught='"+ship.getShipDraught()+"'";
		}
		if(!Common.isNull(ship.getBuildYear())){
			sql+=" ,buildYear="+ship.getBuildYear();
		}
		if(!Common.empty(ship.getLoadCapacity())){
			sql+=" ,loadCapacity='"+ship.getLoadCapacity()+"'";
		}
		if(!Common.empty(ship.getGrossTons())){
			sql+=" ,grossTons='"+ship.getGrossTons()+"'";
		}
		if(!Common.empty(ship.getNetTons())){
			sql+=" ,netTons='"+ship.getNetTons()+"'";
		}
		if(!Common.empty(ship.getNotice())){
			sql+=" ,notice='"+ship.getNotice()+"'";
		}
		if(!Common.empty(ship.getOwner())){
			sql+=" ,owner='"+ship.getOwner()+"'";
		}
		if(!Common.empty(ship.getManager())){
			sql+=" ,manager='"+ship.getManager()+"'";
		}
		if(!Common.empty(ship.getContactName())){
			sql+=" ,contactName='"+ship.getContactName()+"'";
		}
		if(!Common.empty(ship.getContactPhone())){
			sql+=" ,contactPhone='"+ship.getContactPhone()+"'";
		}
		if(!Common.empty(ship.getDescription())){
			sql+=" ,description='"+ship.getDescription()+"'";
		}
		if(!Common.isNull(ship.getStatus())){
			sql+=" ,status="+ship.getStatus();
		}
		if(!Common.isNull(ship.getEditUserId())){
			sql+=" ,editUserId="+ship.getEditUserId();
		}
		if(!Common.empty(ship.getEditTime())){
			sql+=" ,editTime='"+ship.getEditTime()+"'";
		}
		if(!Common.empty(ship.getPort())){
			sql+=" ,port='"+ship.getPort()+"'";
		}
		sql+=" where id="+ship.getId();
		try {
//			update(ship);
			execute(sql);
			
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}

	@Override
	public void deleteShip(String ids) throws OAException {
		try {
			String sql = "update t_pcs_ship set status=1 where id in (" + ids + ")";
			execute(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}

}
