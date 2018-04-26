package com.skycloud.oa.inbound.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.StoreDao;
import com.skycloud.oa.inbound.dao.TankLogDao;
import com.skycloud.oa.inbound.dao.WorkDao;
import com.skycloud.oa.inbound.dto.TankLogDto;
import com.skycloud.oa.inbound.model.Store;
import com.skycloud.oa.inbound.model.TankLog;
import com.skycloud.oa.inbound.model.TankLogStore;
import com.skycloud.oa.inbound.service.TankLogService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;

@Service
public class TankLogServiceImpl implements TankLogService {
	private static Logger LOG = Logger.getLogger(TankLogServiceImpl.class);
@Autowired
private StoreDao storeDao;
@Autowired
private TankLogDao tankLogDao;
@Autowired
private WorkDao workDao;
	
	
	@Override
	public OaMsg getStoreList(TankLogDto tankLogDto,PageView pageView) throws OAException {
		OaMsg oaMsg=new OaMsg();
		int count=0;
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		try {
			oaMsg.getData().addAll(storeDao.getStoreListByTank(tankLogDto,pageView.getStartRecord(), pageView.getMaxresult()));
			count=storeDao.getStoreListCount(tankLogDto);
			oaMsg.setMsg("查询成功");
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("currentPage", pageView.getCurrentpage()+"");
			map.put("totalpage", pageView.getTotalpage()+"");
			map.put("totalRecord", count + "");
			oaMsg.setMap(map);
			return oaMsg;
		} catch (RuntimeException  re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
	}



	
	
	@Override
	@Log(object=C.LOG_OBJECT.PCS_OPERATIONLOG,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateStore(Store store, int type, int logId, int connectType, int logType) throws OAException {
			OaMsg oaMsg=new OaMsg();
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			try {
				
//				float startHandWeight=Common.empty(store.getStartHandWeight())?0:Float.parseFloat(store.getStartHandWeight());
//				float endHandWeight=Common.empty(store.getEndHandWeight())?0:Float.parseFloat(store.getEndHandWeight());
//				
//				store.setMeasureAmount((endHandWeight-startHandWeight)+"");
				double measureAmount=Common.empty(store.getMeasureAmount())?0:Double.parseDouble(store.getMeasureAmount());
				double realAmount=Common.empty(store.getRealAmount())?0:Double.parseDouble(store.getRealAmount());
				store.setDifferAmount((measureAmount-realAmount)+"");
				storeDao.updateStore(store,type,logId,connectType,logType); 
				oaMsg.setMsg("更新成功");
				return oaMsg;
			} catch (RuntimeException  re) {
				LOG.error("更新失败",re);
				oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
				throw new OAException(Constant.SYS_CODE_DB_ERR, "更新失败",re);
			}
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_TANKLOG,type=C.LOG_TYPE.CREATE)
	public OaMsg addTankLog(TankLog tankLog) throws OAException {
		OaMsg oaMsg=new OaMsg();
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		try {
			int id=tankLogDao.addTankLog(tankLog);
			oaMsg.setMsg("添加成功");
			Map<String,String> map=new HashMap<String,String>();
			map.put("id", id+"");
			oaMsg.setMap(map);
			return oaMsg;
		} catch (RuntimeException  re) {
			LOG.error("添加失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "添加失败",re);
		}
	}


	@Override
	@Log(object=C.LOG_OBJECT.PCS_TANKLOG,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateTankLog(TankLog tankLog) throws OAException {
		OaMsg oaMsg=new OaMsg();
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		try {
			
//			float startHandWeight=Common.empty(store.getStartHandWeight())?0:Float.parseFloat(store.getStartHandWeight());
//			float endHandWeight=Common.empty(store.getEndHandWeight())?0:Float.parseFloat(store.getEndHandWeight());
//			
//			store.setMeasureAmount((endHandWeight-startHandWeight)+"");
			double measureAmount=Common.empty(tankLog.getMeasureAmount())?0:Double.parseDouble(tankLog.getMeasureAmount());
			double realAmount=Common.empty(tankLog.getRealAmount())?0:Double.parseDouble(tankLog.getRealAmount());
			tankLog.setDifferAmount(Common.fixDouble(measureAmount-realAmount,3)+"");
			tankLogDao.updateTankLog(tankLog);
			oaMsg.setMsg("更新成功");
			return oaMsg;
		} catch (RuntimeException  re) {
			LOG.error("更新失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "更新失败",re);
		}
	}


	@Override
	public OaMsg getTankLogList(TankLogDto tankLogDto, PageView pageView)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		int count=0;
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		try {
			
			if(!Common.empty(tankLogDto.getYear())){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
				try {
					long startTime = sdf.parse(tankLogDto.getYear()).getTime() / 1000;
					long endTime=sdf.parse((Integer.parseInt(tankLogDto.getYear())+1)+"").getTime() / 1000;
					tankLogDto.setStartTime(startTime);
					tankLogDto.setEndTime(endTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			oaMsg.getData().addAll(tankLogDao.getTankLogListByTank(tankLogDto,pageView.getStartRecord(), pageView.getMaxresult()));
			count=tankLogDao.getTankLogListCount(tankLogDto);
			oaMsg.setMsg("查询成功");
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("currentPage", pageView.getCurrentpage()+"");
			map.put("totalpage", pageView.getTotalpage()+"");
			map.put("totalRecord", count + "");
			oaMsg.setMap(map);
			return oaMsg;
		} catch (RuntimeException  re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
	}

	
	@Override
	public Object getTankList(TankLogDto tankLogDto, PageView pageView)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		int count=0;
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		try {
			
			if(!Common.empty(tankLogDto.getYear())){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
				try {
					long startTime = sdf.parse(tankLogDto.getYear()).getTime() / 1000;
					long endTime=sdf.parse((Integer.parseInt(tankLogDto.getYear())+1)+"").getTime() / 1000;
					tankLogDto.setStartTime(startTime);
					tankLogDto.setEndTime(endTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			oaMsg.getData().addAll(tankLogDao.getTankListByTank(tankLogDto,pageView.getStartRecord(), pageView.getMaxresult()));
			count=tankLogDao.getTankListCount(tankLogDto);
			oaMsg.setMsg("查询成功");
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("currentPage", pageView.getCurrentpage()+"");
			map.put("totalpage", pageView.getTotalpage()+"");
			map.put("totalRecord", count + "");
			oaMsg.setMap(map);
			return oaMsg;
		} catch (RuntimeException  re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
	}
	

	@Override
	public OaMsg getConnectInfo(int tankId, long startTime) throws OAException {
		
		OaMsg oaMsg=new OaMsg();
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		try {
			oaMsg.getData().addAll(workDao.getConnectInfo(tankId, startTime));
			oaMsg.setMsg("查询成功");
			return oaMsg;
		} catch (RuntimeException  re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
	}


	@Override
	@Log(object=C.LOG_OBJECT.PCS_TANKLOG,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteTankLog(String ids) throws OAException {

		OaMsg oaMsg=new OaMsg();
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		try {
			tankLogDao.deleteTankLog(ids);
			oaMsg.setMsg("删除成功");
			return oaMsg;
		} catch (RuntimeException  re) {
			LOG.error("删除失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "删除失败",re);
		}
	}


	@Override
	@Log(object=C.LOG_OBJECT.PCS_TANKLOG,type=C.LOG_TYPE.UPDATE)
	public Object disconnect(int tankLogId, int type) throws OAException {
		OaMsg oaMsg=new OaMsg();
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		try {
			tankLogDao.disconnect(tankLogId,type);
			oaMsg.setMsg("取消成功");
			return oaMsg;
		} catch (RuntimeException  re) {
			LOG.error("取消失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "取消失败",re);
		}
	}





	@Override
	public Object getInboundConnectInfo(int tankId, long mStartTime)throws OAException {
		
		OaMsg oaMsg=new OaMsg();
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		try {
			oaMsg.getData().addAll(workDao.getInboundConnectInfo(tankId, mStartTime));
			oaMsg.setMsg("查询成功");
			return oaMsg;
		} catch (RuntimeException  re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
	}





	@Override
	public Object getInboundBConnectInfo(int arrivalId,int tankId) throws OAException {
		OaMsg oaMsg=new OaMsg();
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		try {
			oaMsg.getData().addAll(workDao.getInboundBConnectInfo(arrivalId,tankId));
			oaMsg.setMsg("查询成功");
			return oaMsg;
		} catch (RuntimeException  re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
	}





	@Override
	public Object addTankLogStore(TankLogStore tanklogStore) throws OAException {
		OaMsg oaMsg=new OaMsg();
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		try {
			int id=tankLogDao.addTankLogStore(tanklogStore);
			oaMsg.setMsg("添加成功");
			Map<String,String> map=new HashMap<String,String>();
			map.put("id", id+"");
			oaMsg.setMap(map);
			return oaMsg;
		} catch (RuntimeException  re) {
			LOG.error("添加失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "添加失败",re);
		}
	}





	@Override
	public Object updateTankLogStore(TankLogStore tanklogStore)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		try {
			
			tankLogDao.updateTankLogStore(tanklogStore); 
			oaMsg.setMsg("更新成功");
			return oaMsg;
		} catch (RuntimeException  re) {
			LOG.error("更新失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "更新失败",re);
		}
}





	@Override
	public Object getinboundDisconnect(int logId,int type) throws OAException {
		OaMsg oaMsg=new OaMsg();
	oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
	try {
		oaMsg.getData().addAll(workDao.getinboundDisconnect(logId,type));
		oaMsg.setMsg("查询成功");
		return oaMsg;
	} catch (RuntimeException  re) {
		LOG.error("查询失败",re);
		oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
	}}





	@Override
	public Object disconnectInbound(int tanklogStoreId, int type, int logId)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		try {
			Map<String, Object> tanklogStore=tankLogDao.getTankLogStore(tanklogStoreId).get(0);
			
			//判断是否是前尺，删除
			if(Integer.parseInt(tanklogStore.get("startLogId").toString())==logId&&Integer.parseInt(tanklogStore.get("startType").toString())==type){
				tankLogDao.deleteTankLogStore(tanklogStoreId);
				oaMsg.setMsg("删除成功");
				return oaMsg;
			}
			//判断是否是后尺，更新
			if(Integer.parseInt(tanklogStore.get("endLogId").toString())==logId&&Integer.parseInt(tanklogStore.get("endType").toString())==type){
				TankLogStore tlogStore=new TankLogStore();
				tlogStore.setId(tanklogStoreId);
				tlogStore.setEndHandLevel("");
				tlogStore.setEndHandWeight("");
				tlogStore.setEndLevel("");
				tlogStore.setEndLogId(-1);
				tlogStore.setEndTemperature("");
				tlogStore.setEndWeight("");
				tlogStore.setEndType(-1);
				tankLogDao.updateTankLogStore(tlogStore);
				oaMsg.setMsg("更新成功");
			}
			
			return oaMsg;
		} catch (RuntimeException  re) {
			LOG.error("失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "失败",re);
		}}





	@Override
	public Object getTankLogStore(int arrivalId, int productId)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
	oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
	try {
		oaMsg.getData().addAll(tankLogDao.getTankLogStore(arrivalId,productId));
		oaMsg.setMsg("查询成功");
		return oaMsg;
	} catch (RuntimeException  re) {
		LOG.error("查询失败",re);
		oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
	}}





	@Override
	public Object getTankLogStoreSum(int arrivalId, int productId)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
	oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
	try {
		oaMsg.getData().addAll(tankLogDao.getTankLogStoreSum(arrivalId,productId));
		oaMsg.setMsg("查询成功");
		return oaMsg;
	} catch (RuntimeException  re) {
		LOG.error("查询失败",re);
		oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
	}
	}



}
