package com.skycloud.oa.inbound.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.ArrivalDao;
import com.skycloud.oa.inbound.dao.ArrivalForeshowDao;
import com.skycloud.oa.inbound.dao.ArrivalInfoDao;
import com.skycloud.oa.inbound.dao.ArrivalPlanDao;
import com.skycloud.oa.inbound.dao.CargoGoodsDao;
import com.skycloud.oa.inbound.dao.InboundArrivalDao;
import com.skycloud.oa.inbound.dao.InitialFeeDao;
import com.skycloud.oa.inbound.dao.WorkDao;
import com.skycloud.oa.inbound.dto.ArrivalDto;
import com.skycloud.oa.inbound.dto.ArrivalPlanDto;
import com.skycloud.oa.inbound.dto.CargoGoodsDto;
import com.skycloud.oa.inbound.dto.InboundOperationDto;
import com.skycloud.oa.inbound.dto.InsertArrivalPlanDto;
import com.skycloud.oa.inbound.dto.InsertSingleArrivalPlanDto;
import com.skycloud.oa.inbound.model.Arrival;
import com.skycloud.oa.inbound.model.ArrivalForeshow;
import com.skycloud.oa.inbound.model.ArrivalInfo;
import com.skycloud.oa.inbound.model.ArrivalPlan;
import com.skycloud.oa.inbound.model.ArrivalWork;
import com.skycloud.oa.inbound.model.Cargo;
import com.skycloud.oa.inbound.model.Goods;
import com.skycloud.oa.inbound.model.InitialFee;
import com.skycloud.oa.inbound.model.Work;
import com.skycloud.oa.inbound.service.ArrivalForeshowService;
import com.skycloud.oa.inbound.service.ArrivalPlanService;
import com.skycloud.oa.inbound.service.InboundOperationService;
import com.skycloud.oa.inbound.service.InitialFeeService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dao.GoodsLogDao;
import com.skycloud.oa.outbound.dao.ShipArrivalDao;
import com.skycloud.oa.outbound.model.GoodsLog;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;

@Service
public class ArrivalForeshowServiceImpl implements ArrivalForeshowService {
	private static Logger LOG = Logger.getLogger(ArrivalForeshowServiceImpl.class);

	
	@Autowired
	private ArrivalForeshowDao arrivalForeshowDao;
	
	@Autowired
	private ArrivalDao arrivalDao;
	

	
	@Autowired
	private ShipArrivalDao shipArrivalDao;
	
	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_ARRIVALFORESHOW,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteArrivalForeshow(String id) throws OAException {
		
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始删除到港信息");
			arrivalForeshowDao.deleteArrivalForeshow(id);
			LOG.debug("删除成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("删除成功");
		} catch (RuntimeException re) {
			LOG.error("删除失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "删除失败",re);
		}
		return oaMsg;
	}

	

	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_ARRIVALFORESHOW,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateArrivalForeshow(ArrivalForeshow arrivalForeshow) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			arrivalForeshowDao.updateArrivalForeshow(arrivalForeshow);
			LOG.debug("更新成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("更新成功");
		} catch (RuntimeException o) {
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("系统繁忙,请稍后再试");
			LOG.error("更新失败", o);
		}
			return oaMsg;
	}

	@Override
	public OaMsg getArrivalForeshowList(ArrivalDto arrivalDto, PageView pageView)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		int count=0;
		try {
			LOG.debug("开始查询到港信息");
			List<Map<String,Object>> arrivalList=arrivalForeshowDao.getArrivalForeshowList(arrivalDto,pageView.getStartRecord(), pageView.getMaxresult());
//			for(int i=0;i<arrivalList.size();i++){
//				CargoGoodsDto cgDto=new CargoGoodsDto();
//				cgDto.setArrivalId((Integer) arrivalList.get(i).get("id"));
//				List<Map<String,Object>> cargoList=cargoGoodsDao.getCargoList(cgDto, 0, 0);
//				List<Map<String,Object>> arrivalPlanList=arrivalPlanDao.getArrivalPlanList((int) arrivalList.get(i).get("id"), 0, 0);
//				arrivalList.get(i).put("sureList", cargoList);
//				arrivalList.get(i).put("planList", arrivalPlanList);
//			}
			count=arrivalForeshowDao.getArrivalForeshowListCount(arrivalDto);
//			pageView.setTotalrecord(count);
			LOG.debug("查询成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("查询成功");
			oaMsg.getData().addAll(arrivalList);
			Map<String, String> map = new HashMap<String, String>();
			map.put("currentPage", pageView.getCurrentpage()+"");
			map.put("totalpage", pageView.getTotalpage()+"");
			map.put("totalRecord", count + "");
			oaMsg.setMap(map);
		} catch (RuntimeException re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
		return oaMsg;
	}



	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_ARRIVALFORESHOW,type=C.LOG_TYPE.CREATE)
	public OaMsg addArrivalForeshow(ArrivalForeshow arrivalForeshow)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			arrivalForeshowDao.addIntoArrivalForeshow(arrivalForeshow);
			LOG.debug("添加成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("添加成功");
		} catch (RuntimeException o) {
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("系统繁忙,请稍后再试");
			LOG.error("添加失败", o);
		}
			return oaMsg;
	}



	@Override
	public OaMsg checkForeshow(int arrivalId) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			int count=arrivalForeshowDao.checkForeshow(arrivalId);
			Map<String, String> map = new HashMap<String, String>();
			map.put("count",count+"");
			oaMsg.setMap(map);
			LOG.debug("添加成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("添加成功");
		} catch (RuntimeException o) {
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("系统繁忙,请稍后再试");
			LOG.error("添加失败", o);
		}
			return oaMsg;
	}



	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_ARRIVALFORESHOW,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateForeshowBySQL(int arrivalId) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			ArrivalDto dto=new ArrivalDto();
			dto.setId(arrivalId);
			Map<String,Object> arrivalInfo=arrivalDao.getArrivalList(dto,0,0).get(0);
			if(!Common.empty(arrivalInfo)){
				if("1".equals(arrivalInfo.get("type").toString())||"3".equals(arrivalInfo.get("type").toString())){
					arrivalForeshowDao.updateForeshowBySQL(arrivalId,user.getId());
					
				}else if ("2".equals(arrivalInfo.get("type").toString())){
					arrivalForeshowDao.updateOutForeshowBySQL(arrivalId,user.getId());
				}
			}
			LOG.debug("更新成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("更新成功");
		} catch (RuntimeException o) {
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("系统繁忙,请稍后再试");
			LOG.error("更新失败", o);
		}
			return oaMsg;
	}



	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_ARRIVALFORESHOW,type=C.LOG_TYPE.CREATE)
	public OaMsg addForeshowBySQL(int arrivalId) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			
			
			arrivalForeshowDao.addForeshowBySQL(arrivalId,user.getId());
			LOG.debug("添加成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("添加成功");
		} catch (RuntimeException o) {
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("系统繁忙,请稍后再试");
			LOG.error("添加失败", o);
		}
			return oaMsg;
	}



	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_ARRIVALFORESHOW,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateForeshow(ArrivalForeshow arrivalForeshow)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			arrivalForeshow.setEditUserId(user.getId());
			
			arrivalForeshowDao.updateArrivalForeshow(arrivalForeshow);
			LOG.debug("更新成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("更新成功");
		} catch (RuntimeException o) {
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("系统繁忙,请稍后再试");
			LOG.error("更新失败", o);
		}
			return oaMsg;
	}



	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_ARRIVALFORESHOW,type=C.LOG_TYPE.CREATE)
	public OaMsg addForeshow(ArrivalForeshow arrivalForeshow)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			arrivalForeshow.setEditUserId(user.getId());
			
			arrivalForeshowDao.addIntoArrivalForeshow(arrivalForeshow);
			LOG.debug("添加成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("添加成功");
		} catch (RuntimeException o) {
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("系统繁忙,请稍后再试");
			LOG.error("添加失败", o);
		}
			return oaMsg;
	}



	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_ARRIVALFORESHOW,type=C.LOG_TYPE.DELETE)
	public OaMsg zfArrivalForeshow(String id) throws OAException {
		
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始作废船期预告");
			arrivalForeshowDao.zfArrivalForeshow(id);
			LOG.debug("作废成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("作废成功");
		} catch (RuntimeException re) {
			LOG.error("作废失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "作废失败",re);
		}
		return oaMsg;
	}



	@Override
	public OaMsg getOutArrivalCargo(int arrivalId) throws OAException {
		
		OaMsg oaMsg = new OaMsg();
		try {
			LOG.debug("开始-------get--------------");
			oaMsg.getData().addAll(shipArrivalDao.getArrivalPlan(""+arrivalId)) ;
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("插入成功");
		} catch (Exception re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}



}
