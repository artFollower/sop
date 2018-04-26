package com.skycloud.oa.system.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.common.dao.BaseControllerDao;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dao.ShipDao;
import com.skycloud.oa.system.dto.ShipDto;
import com.skycloud.oa.system.dto.ShipInfoDtoList;
import com.skycloud.oa.system.model.Ship;
import com.skycloud.oa.system.model.ShipRef;
import com.skycloud.oa.system.service.ShipService;
import com.skycloud.oa.utils.OaMsg;

@Service
public class ShipServiceImpl implements ShipService {
	private static Logger LOG = Logger.getLogger(ShipServiceImpl.class);

	@Autowired
	private ShipDao shipDao;
	@Autowired
	private BaseControllerDao baseControllerDao;
	@Override
	public OaMsg getShipList(ShipDto sDto, PageView pageView)
			throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(shipDao.getShipList(sDto, pageView.getStartRecord(), pageView.getMaxresult()));
			int count=shipDao.getShipListCount(sDto);
			Map<String, String> map = new HashMap<String, String>();
			map.put("currentPage", pageView.getCurrentpage()+"");
			map.put("totalpage", pageView.getTotalpage()+"");
			map.put("totalRecord", count + "");
			oaMsg.setMap(map);
		} catch (RuntimeException re) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}
	
	@Override
	public OaMsg deleteShipRefInfo(String shipRefId) {
		// TODO Auto-generated method stub
		OaMsg oaMsg = new OaMsg();
		try {
			shipDao.deleteShipRefInfo(shipRefId);
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (OAException re) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getShipRefList(String shipId) {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.getData().addAll(shipDao.getShipREfList(shipId));
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (OAException re) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
	
	
	@Override
	@Log(object=C.LOG_OBJECT.PCS_SHIP,type=C.LOG_TYPE.CREATE)
	public OaMsg addShip(ShipInfoDtoList  shipInfoDtoList) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			Ship ship = shipInfoDtoList.getShip() ;
			ship.setEditUserId(user.getId()) ;
			
			String shipId = shipDao.addShip(shipInfoDtoList.getShip());
			if(shipInfoDtoList.getShipRefInfoList().size()>0){
				for(int i = 0 ;i<shipInfoDtoList.getShipRefInfoList().size();i++){
					ShipRef shipRef = shipInfoDtoList.getShipRefInfoList().get(i) ;
					shipRef.setShipId(Integer.parseInt(shipId)) ;
					shipDao.saveShipRef(shipRef) ;
				}
			}
		} catch (RuntimeException re) {
			LOG.error("添加失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("更新失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_SHIP,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateShip(ShipInfoDtoList  shipInfoDtoList) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			Ship ship = shipInfoDtoList.getShip() ;
			ship.setEditUserId(user.getId()) ;
			shipDao.updateShip(ship);
			baseControllerDao.sendSystemMessage("船舶", user.getId(),"BASEINFO",4) ;//发送消息给baseinfo权限的人
			if(shipInfoDtoList.getShipRefInfoList()!=null&&shipInfoDtoList.getShipRefInfoList().size()>0){
				for(int i = 0 ;i<shipInfoDtoList.getShipRefInfoList().size();i++){
					ShipRef shipRef = shipInfoDtoList.getShipRefInfoList().get(i) ;
					shipRef.setShipId(shipInfoDtoList.getShip().getId()) ;
					if(shipRef.getId()>0){
						shipDao.updateShipRef(shipRef) ;
					}else{
						shipDao.saveShipRef(shipRef) ;
					}
				}
			}
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (RuntimeException re) {
			LOG.error("更新失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("更新失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_SHIP,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteShip(String ship) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			shipDao.deleteShip(ship);
		} catch (RuntimeException re) {
			LOG.error("删除失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("删除失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

}
