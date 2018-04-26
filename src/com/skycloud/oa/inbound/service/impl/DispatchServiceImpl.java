package com.skycloud.oa.inbound.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.util.logging.resources.logging;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.ArrivalDao;
import com.skycloud.oa.inbound.dao.DispatchDao;
import com.skycloud.oa.inbound.dao.DutySysDao;
import com.skycloud.oa.inbound.dto.DispatchConnectDto;
import com.skycloud.oa.inbound.dto.DispatchDto;
import com.skycloud.oa.inbound.model.Arrival;
import com.skycloud.oa.inbound.model.Dispatch;
import com.skycloud.oa.inbound.model.DutySys;
import com.skycloud.oa.inbound.service.DispatchService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

@Service
public class DispatchServiceImpl implements DispatchService {
	private static Logger LOG = Logger.getLogger(DispatchServiceImpl.class);
	@Autowired
	private DispatchDao dispatchDao;
	@Autowired
	private ArrivalDao arrivalDao;
	@Autowired
	private DutySysDao dutySysDao;
	

	@Override
	@Log(object=C.LOG_OBJECT.PCS_OPERATIONLOG,type=C.LOG_TYPE.UPDATE)
	public OaMsg addDispatch(DispatchConnectDto dispatchConnectDto) throws OAException {
				OaMsg msg=new OaMsg();
				try {
					LOG.debug("开始添加");
					
					Dispatch dispatch=new Dispatch();
					dispatch.setsWeather(dispatchConnectDto.getsWeather());
					dispatch.setsWindDirection(dispatchConnectDto.getsWindDirection());
					dispatch.setsWindPower(dispatchConnectDto.getsWindPower());
					dispatch.setTime(dispatchConnectDto.getTime());
					
					int id=dispatchDao.addIntoDispatch(dispatch);
					
					
					String[] sysName={"SCADA系统","可燃气体报警系统","视频监控系统","周界警报系统","发货泵信号检查"};
					String[] sysParName={"码头管线压力检查","罐顶压力检查","流量计状态检查","电动阀状态检查","紧急切断阀状态检查"};
					
					for(int i=0;i<5;i++){
						DutySys dutySys=new DutySys();
						dutySys.setDispatchId(id);
						dutySys.setSysName(sysName[i]);
						dutySys.setType(1);
						dutySysDao.addDutySys(dutySys);
					}
					for(int i=0;i<5;i++){
						DutySys dutySys=new DutySys();
						dutySys.setDispatchId(id);
						dutySys.setSysName(sysParName[i]);
						dutySys.setType(2);
						dutySysDao.addDutySys(dutySys);
					}
					
					for(int i=0;i<5;i++){
						DutySys dutySys=new DutySys();
						dutySys.setDispatchId(id);
						dutySys.setSysName(sysName[i]);
						dutySys.setType(3);
						dutySysDao.addDutySys(dutySys);
					}
					for(int i=0;i<5;i++){
						DutySys dutySys=new DutySys();
						dutySys.setDispatchId(id);
						dutySys.setSysName(sysParName[i]);
						dutySys.setType(4);
						dutySysDao.addDutySys(dutySys);
					}
					
					
					
					dispatchDao.addDispatchConnect(id,dispatchConnectDto);
					
//					Arrival arrival= new Arrival();
//					arrival.setDispatchId(id);
//					arrival.setId(ArrivalId);
//					arrivalDao.updateArrival(arrival);
					msg.setCode(Constant.SYS_CODE_SUCCESS);
					msg.setMsg("添加成功");
					Map<String,String> idmap=new HashMap<String, String>();
					idmap.put("id", id+"");
					msg.setMap(idmap);
				} catch (RuntimeException e) {
					msg.setCode(Constant.SYS_CODE_DB_ERR);
					msg.setMsg("添加失败");
					LOG.debug("添加失败",e);
					throw new OAException(Constant.SYS_CODE_DB_ERR, "异常",e);
				}
				return msg;
	}


	@Override
	@Log(object=C.LOG_OBJECT.PCS_OPERATIONLOG,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateDispatch(DispatchConnectDto dispatchConnectDto) throws OAException {
		OaMsg msg=new OaMsg();
		try {
			
			Dispatch dispatch=new Dispatch();
			dispatch.setsWeather(dispatchConnectDto.getsWeather());
			dispatch.setsWindDirection(dispatchConnectDto.getsWindDirection());
			dispatch.setsWindPower(dispatchConnectDto.getsWindPower());
			dispatch.setTime(dispatchConnectDto.getTime());
			dispatch.setId(dispatchConnectDto.getId());
			dispatch.setsTemperature(dispatchConnectDto.getsTemperature());
			dispatchDao.updateDispatch(dispatch);
			
			dispatchDao.deleteDispatchConnect(dispatchConnectDto.getId());
			
			dispatchDao.addDispatchConnect(dispatchConnectDto.getId(),dispatchConnectDto);
			
			
			msg.setCode(Constant.SYS_CODE_SUCCESS);
			msg.setMsg("更新成功");
		} catch (RuntimeException e) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("更新失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "异常",e);
		}
		return msg;
	}


	@Override
	public OaMsg getDispatchList(DispatchDto dispatchDto, PageView pageView)
			throws OAException {
		OaMsg msg=new OaMsg();
		try {
			msg.setCode(Constant.SYS_CODE_SUCCESS);
			msg.getData().addAll(dispatchDao.getDispatchList(dispatchDto,pageView.getStartRecord(),pageView.getMaxresult()));
			msg.getMap().put(Constant.TOTALRECORD, dispatchDao.getDispatchCount(dispatchDto)+"");
			
		} catch (RuntimeException e) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("更新失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "异常",e);
		}
		return msg;
	}


	@Override
	@Log(object=C.LOG_OBJECT.PCS_OPERATIONLOG,type=C.LOG_TYPE.UPDATE)
	public OaMsg deleteDispatch(DispatchDto dispatchDto) throws OAException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public OaMsg getDispatchConnect(DispatchDto dispatchDto) throws OAException {
		OaMsg msg=new OaMsg();
		try {
			msg.setCode(Constant.SYS_CODE_SUCCESS);
			msg.getData().addAll(dispatchDao.getDispatchConnectList(dispatchDto));
			
		} catch (RuntimeException e) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("更新失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "异常",e);
		}
		return msg;
	}

}
