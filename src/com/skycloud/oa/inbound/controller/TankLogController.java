package com.skycloud.oa.inbound.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.TankLogDto;
import com.skycloud.oa.inbound.model.Store;
import com.skycloud.oa.inbound.model.TankLog;
import com.skycloud.oa.inbound.model.TankLogStore;
import com.skycloud.oa.inbound.service.TankLogService;
import com.skycloud.oa.order.dto.ContractDto;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;

/**
 * 储罐台账
 * 
 * @author jiahy
 * 
 */
@Controller
@RequestMapping("/tanklog")
public class TankLogController {

	@Autowired
	private TankLogService tankLogService;

	/**
	 * 查询储罐台账列表
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param tankLogDto
	 * @param pagesize
	 * @param page
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午2:16:44
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public Object list(
			HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute TankLogDto tankLogDto,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page)throws OAException {
			return tankLogService.getTankLogList(tankLogDto, new PageView(
					pagesize, page));
	}

	
	/**
	 * 查询储罐台账储罐列表
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param tankLogDto
	 * @param pagesize
	 * @param page
	 * @return
	 * @throws OAException
	 * 2015-4-13 下午5:20:50
	 */
	@RequestMapping(value = "tanklist")
	@ResponseBody
	public Object tanklist(
			HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute TankLogDto tankLogDto,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page)throws OAException {
			return tankLogService.getTankList(tankLogDto, new PageView(
					0, 0));
	}
	
	
	/**
	 * 更新储罐台账
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param store
	 * @param mStartTime
	 * @param mEndTime
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午2:16:57
	 */
	@RequestMapping(value = "update")
	@ResponseBody
	public Object update(HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute TankLog tankLog,
			String mStartTime, String mEndTime)throws OAException {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
			try {
				if(!Common.empty(mStartTime)){
					long startTime = sdf.parse(mStartTime).getTime() / 1000;
					tankLog.setStartTime(startTime);
				}
				if(!Common.empty(mEndTime)){
					long endTime = sdf.parse(mEndTime).getTime() / 1000;
					tankLog.setEndTime(endTime);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new OAException(Constant.SYS_CODE_PARAM_NULL, "时间转换失败",e);
			}
			return tankLogService.updateTankLog(tankLog);

	}
	
	/**
	 * 
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param store
	 * @param mStartTime
	 * @param mEndTime
	 * @param type  0 入库接卸  1 船发出库
	 * @param logId
	 * @param logType  1 前尺 2 后尺
	 * @param connectType  1 关联了前尺 2关联了后尺
	 * @return
	 * @throws OAException
	 * 2015-2-11 上午11:21:49
	 */
	@RequestMapping(value = "updateStore")
	@ResponseBody
	public Object updateStore(HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute Store store,
			String mStartTime, String mEndTime,int type,int logId,int connectType,int logType)throws OAException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//			try {
//				long startTime;
//				startTime = sdf.parse(mStartTime).getTime() / 1000;
//				long endTime = sdf.parse(mEndTime).getTime() / 1000;
//				store.setStartTime(startTime);
//				store.setEndTime(endTime);
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				throw new OAException(Constant.SYS_CODE_PARAM_NULL, "时间转换失败",e);
//			}
			return tankLogService.updateStore(store,type,logId,connectType,logType);

	}
	

	
	/**
	 * 取消关联
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param tankLogId
	 * @param type
	 * @return
	 * @throws OAException
	 * 2015-3-20 下午6:40:19
	 */
	@RequestMapping(value = "disconnect")
	@ResponseBody
	public Object disconnect(HttpServletRequest request,
			HttpServletResponse response, int tankLogId,int type)throws OAException {

			return tankLogService.disconnect(tankLogId,type);

	}
	
	
	
	/**
	 * 添加储罐台账
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param store
	 * @param mStartTime
	 * @param mEndTime
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午2:16:57
	 */
	@RequestMapping(value = "add")
	@ResponseBody
	public Object add(HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute TankLog tankLog,
			String mStartTime, String mEndTime)throws OAException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			try {
				if(!Common.empty(mStartTime)){
					long startTime = sdf.parse(mStartTime).getTime() / 1000;
					tankLog.setStartTime(startTime);
				}
				if(!Common.empty(mEndTime)){
					long endTime = sdf.parse(mEndTime).getTime() / 1000;
					tankLog.setEndTime(endTime);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new OAException(Constant.SYS_CODE_PARAM_NULL, "时间转换失败",e);
			}
			return tankLogService.addTankLog(tankLog);

	}
	
	
	
	
	/**
	 * 获得入库关联信息
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param tankId
	 * @param startTime
	 * @return
	 * @throws OAException
	 * 2015-7-24 下午4:46:48
	 */
	@RequestMapping(value = "getinboundconnect")
	@ResponseBody
	public Object getinboundconnect(
			HttpServletRequest request,
			HttpServletResponse response,int tankId, String startTime)throws OAException {
		long mStartTime=0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if(!Common.empty(startTime))
			mStartTime = sdf.parse(startTime).getTime() / 1000;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return tankLogService.getInboundConnectInfo(tankId, mStartTime);
	}
	
	
	
	/**
	 * 获得入库后尺关联列表
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param arrivalId
	 * @return
	 * @throws OAException
	 * 2015-7-26 下午1:15:37
	 */
	@RequestMapping(value = "getinboundBconnect")
	@ResponseBody
	public Object getinboundBconnect(
			HttpServletRequest request,
			HttpServletResponse response,int arrivalId,int tankId)throws OAException {
			return tankLogService.getInboundBConnectInfo(arrivalId,tankId);
	}
	
	/**
	 * 关联前尺则增加一条
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param tanklogStore
	 * @return
	 * @throws OAException
	 * 2015-7-26 下午3:56:18
	 */
	@RequestMapping(value = "addTankLogStore")
	@ResponseBody
	public Object addTankLogStore(
			HttpServletRequest request,
			HttpServletResponse response,TankLogStore tanklogStore)throws OAException {
			return tankLogService.addTankLogStore(tanklogStore);
	}
	
	
	/**
	 * 关联后尺则更新
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param tanklogStore
	 * @return
	 * @throws OAException
	 * 2015-7-26 下午3:56:29
	 */
	@RequestMapping(value = "updateTankLogStore")
	@ResponseBody
	public Object updateTankLogStore(
			HttpServletRequest request,
			HttpServletResponse response,TankLogStore tanklogStore)throws OAException {
			return tankLogService.updateTankLogStore(tanklogStore);
	}
	
	
	/**
	 * 入库取消关联列表信息
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param logId
	 * @return
	 * @throws OAException
	 * 2015-7-27 上午9:56:49
	 */
	@RequestMapping(value = "getinboundDisconnect")
	@ResponseBody
	public Object getinboundDisconnect(
			HttpServletRequest request,
			HttpServletResponse response,int logId,int type)throws OAException {
			return tankLogService.getinboundDisconnect(logId,type);
	}
	
	
	/**
	 * 取消入库关联
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param tanklogStoreId 
	 * @param type 1：log的前尺，2：log的后尺
	 * @param logId 台账id
	 * @return
	 * @throws OAException
	 * 2015-7-27 上午11:13:17
	 */
	@RequestMapping(value = "disconnectInbound")
	@ResponseBody
	public Object disconnectInbound(
			HttpServletRequest request,
			HttpServletResponse response,int tanklogStoreId,int type,int logId)throws OAException {
			return tankLogService.disconnectInbound(tanklogStoreId,type,logId);
	}
	
	
	@RequestMapping(value = "getconnect")
	@ResponseBody
	public Object getconnect(
			HttpServletRequest request,
			HttpServletResponse response,int tankId, String startTime)throws OAException {
		long mStartTime=0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if(!Common.empty(startTime))
			mStartTime = sdf.parse(startTime).getTime() / 1000;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return tankLogService.getConnectInfo(tankId, mStartTime);
	}
	
	@RequestMapping(value = "delete")
	@ResponseBody
	public Object delete(
			HttpServletRequest request,
			HttpServletResponse response,String ids)throws OAException {
			return tankLogService.deleteTankLog(ids);
	}
	
	@RequestMapping(value = "getTankLogStore")
	@ResponseBody
	public Object getTankLogStore(
			HttpServletRequest request,
			HttpServletResponse response,int arrivalId,int productId)throws OAException {
			return tankLogService.getTankLogStore(arrivalId,productId);
	}
	
	
	@RequestMapping(value = "getTankLogStoreSum")
	@ResponseBody
	public Object getTankLogStoreSum(
			HttpServletRequest request,
			HttpServletResponse response,int arrivalId,int productId)throws OAException {
			return tankLogService.getTankLogStoreSum(arrivalId,productId);
	}
	
	
	@RequestMapping(value = "exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,@ModelAttribute TankLogDto tankLogDto) 
	{
		response.setContentType("application/vnd.ms-excel");
		OutputStream fOut = null;
		try 
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			// 进行转码，使其支持中文文件名 type 1-10 是出库用 20开始入库用
			response.setHeader("content-disposition", "attachment;filename=" + sdf.format(new Date()) + ".xls");
			// 产生工作簿对象
//			HSSFWorkbook workbook = invoiceService.exportExcel(request);
			
			OaMsg msg=tankLogService.getTankLogList(tankLogDto, new PageView(
					0, 0));
			
			String[] str={"罐号","货品","开始时间","结束时间","前尺-液位(毫米)","前尺-重量(吨)"
					,"前尺-温度(℃)","前尺-手检尺液位(毫米)","前尺-差异(毫米)","后尺-液位(毫米)","后尺-重量(吨)","后尺-温度(℃)","后尺-手检尺液位(毫米)","后尺-差异(毫米)","机房重量","事件"};
			List<List<Object>> data=new ArrayList<List<Object>>();
			
			for(int i=0;i<msg.getData().size();i++){
				Map<String,Object> map=(Map<String, Object>) msg.getData().get(i);
				List<Object> d=new ArrayList<Object>();
				d.add(map.get("tankCode").toString());
				d.add(map.get("productName").toString());
				d.add(!Common.empty(map.get("mStartTime"))?map.get("mStartTime").toString():"");
				d.add(!Common.empty(map.get("mEndTime"))?map.get("mEndTime").toString():"");
				d.add(!Common.empty(map.get("startLevel"))?map.get("startLevel").toString():"");
				d.add(!Common.empty(map.get("startWeight"))?map.get("startWeight").toString():"");
				d.add(!Common.empty(map.get("startTemperature"))?map.get("startTemperature").toString():"");
				d.add(!Common.empty(map.get("startHandLevel"))?map.get("startHandLevel").toString():"");
				d.add(!Common.empty(map.get("startDiffer"))?map.get("startDiffer").toString():"");
				d.add(!Common.empty(map.get("endLevel"))?map.get("endLevel").toString():"");
				d.add(!Common.empty(map.get("endWeight"))?map.get("endWeight").toString():"");
				d.add(!Common.empty(map.get("endTemperature"))?map.get("endTemperature").toString():"");
				d.add(!Common.empty(map.get("endHandLevel"))?map.get("endHandLevel").toString():"");
				d.add(!Common.empty(map.get("endDiffer"))?map.get("endDiffer").toString():"");
				d.add(!Common.empty(map.get("realAmount"))?map.get("realAmount").toString():"");
				d.add(!Common.empty(map.get("message"))?map.get("message").toString():"");
				data.add(d);
			}
			HSSFWorkbook workbook=Common.getExcel("储罐台账", str, data,new int[]{0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0});
			
			fOut = response.getOutputStream();
			workbook.write(fOut);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				fOut.flush();
				fOut.close();
			} 
			catch (IOException e) 
			{
				
			}
		}
	}
	
	
	
	
	
}
