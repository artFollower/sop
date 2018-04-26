package com.skycloud.oa.inbound.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.ArrivalPlanDto;
import com.skycloud.oa.inbound.dto.DispatchConnectDto;
import com.skycloud.oa.inbound.dto.DispatchDto;
import com.skycloud.oa.inbound.model.Dispatch;
import com.skycloud.oa.inbound.model.DutyRecord;
import com.skycloud.oa.inbound.model.DutySys;
import com.skycloud.oa.inbound.model.DutyWeather;
import com.skycloud.oa.inbound.service.ArrivalPlanService;
import com.skycloud.oa.inbound.service.DispatchService;
import com.skycloud.oa.inbound.service.OperationLogService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 * 代理控制类
 * @author yanyufeng
 *
 */
@Controller
@RequestMapping("/inboundserial")
public class InboundSerialController {

	@Autowired
	private ArrivalPlanService arrivalPlanService;
	@Autowired
	private OperationLogService operationLogService;
	@Autowired
	private DispatchService dispatchService;
	@RequestMapping(value = "list")
	@ResponseBody
	public Object list(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute ArrivalPlanDto arrivalPlanDto)throws OAException{
		arrivalPlanDto.setStatus(2);
		return  arrivalPlanService.getArrivalPlanList(arrivalPlanDto, new PageView(0,0));
	}

	@RequestMapping(value = "get")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response,
			String page){
	return new ModelAndView("inbound/inbound_tab").addObject("page", page);
	}
	/**
	 * 查询调度日志列表
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午2:42:42
	 */
	@RequestMapping(value="loglist")
	@ResponseBody
	public Object loglist(HttpServletRequest request, HttpServletResponse response,String startTime,String endTime)throws OAException{
		long mStartTime=Long.parseLong(startTime);
		long mEndTime=Long.parseLong(endTime);
			return operationLogService.getOperationLogList(mStartTime, mEndTime,new PageView(0,0));
		
	}
	
	
	/**
	 * 增加调度日志基本信息
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param dispatch
	 * @param arrivalId
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午3:04:14
	 */
	@RequestMapping(value="addDispatch")
	@ResponseBody
	public OaMsg addDispatch(HttpServletRequest request, HttpServletResponse response,@ModelAttribute DispatchConnectDto dispatchConnectDto)throws OAException{
			return dispatchService.addDispatch(dispatchConnectDto);
	}
	
	
	/**
	 * 修改调度日志基本信息
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param dispatch
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午3:04:30
	 */
	@RequestMapping(value="updateDispatch")
	@ResponseBody
	public OaMsg updateDispatch(HttpServletRequest request, HttpServletResponse response,@ModelAttribute DispatchConnectDto dispatchConnectDto)throws OAException{
			return dispatchService.updateDispatch(dispatchConnectDto);
		
	}
	
	@RequestMapping(value="getDispatchConnect")
	@ResponseBody
	public OaMsg getDispatchConnect(HttpServletRequest request, HttpServletResponse response,DispatchDto dispatchDto)throws OAException{
			return dispatchService.getDispatchConnect(dispatchDto);
		
	}
	
	
	/**
	 * 查询调度日志基本信息
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午3:01:31
	 */
	@RequestMapping(value="loginfo")
	@ResponseBody
	public Object loginfo(HttpServletRequest request, HttpServletResponse response,String startTime,String endTime)throws OAException{
		long mStartTime=Long.parseLong(startTime);
		long mEndTime=Long.parseLong(endTime);;
			return operationLogService.getLogInfo(mStartTime, mEndTime);
	}
	
	/**
	 * 查询时间段内的调度日志
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午3:01:42
	 */
	@RequestMapping(value="loghave")
	@ResponseBody
	public Object loghave(HttpServletRequest request, HttpServletResponse response,String startTime,String endTime)throws OAException{
		long mStartTime=Long.parseLong(startTime);
		long mEndTime=Long.parseLong(endTime);;
			return operationLogService.getLogIsHave(mStartTime, mEndTime);
	}
	
	/**
	 * 查询作业检查信息
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param transportId
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午3:02:47
	 */
	@RequestMapping(value="logmsglist")
	@ResponseBody
	Object testLogMsgList(HttpServletRequest request, HttpServletResponse response,String transportId,String types)throws OAException{
			return operationLogService.getLogWorkCheck(transportId,types);
	}
	
	
	
	/**
	 * 增加调度值班记录
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param dutyRecord
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午3:02:58
	 */
	@RequestMapping(value="addlogduty")
	@ResponseBody
	Object addlogduty(HttpServletRequest request, HttpServletResponse response,@ModelAttribute DutyRecord dutyRecord)throws OAException{
		OaMsg oaMsg;
		User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		dutyRecord.setCreateUserId(user.getId());
		oaMsg = operationLogService.addDutyRecord(dutyRecord);
		return oaMsg;
	}
	
	/**添加调度日志天气预报记录
	 * 
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param dutyWeather
	 * @return
	 * @throws OAException
	 * 2015-3-16 下午1:45:41
	 */
	@RequestMapping(value="adddutyweather")
	@ResponseBody
	Object adddutyweather(HttpServletRequest request, HttpServletResponse response,@ModelAttribute DutyWeather dutyWeather)throws OAException{
		OaMsg oaMsg;
			oaMsg = operationLogService.addDutyWeather(dutyWeather);
			return oaMsg;
	}
	
	
	/**
	 * 删除调度值班记录
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午3:03:13
	 */
	@RequestMapping(value="delectlogduty")
	@ResponseBody
	Object delectlogduty(HttpServletRequest request, HttpServletResponse response,String id)throws OAException{
			return operationLogService.delectDutyRecord(id);
	}
	
	@RequestMapping(value="delectdutyweather")
	@ResponseBody
	Object delectdutyweather(HttpServletRequest request, HttpServletResponse response,String id)throws OAException{
			return operationLogService.delectDutyWeather(id);
	}
	
	/**
	 * 更新值班记录
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param dutyRecord
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午3:03:25
	 */
	@RequestMapping(value="updatelogduty")
	@ResponseBody
	Object updatelogduty(HttpServletRequest request, HttpServletResponse response,@ModelAttribute DutyRecord dutyRecord)throws OAException{
			return operationLogService.updateDutyRecord(dutyRecord);
	}
	
	/**
	 * 查询调度值班记录
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param disptachId
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午3:03:35
	 */
	@RequestMapping(value="getlogduty")
	@ResponseBody
	Object getlogduty(HttpServletRequest request, HttpServletResponse response, String dispatchId)throws OAException{
			return operationLogService.getDutyRecord(dispatchId);
	}
	/**
	 * 查询调度日志天气预报记录
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param dispatchId
	 * @return
	 * @throws OAException
	 * 2015-3-16 下午1:51:21
	 */
	@RequestMapping(value="getdutyweather")
	@ResponseBody
	Object getdutyweather(HttpServletRequest request, HttpServletResponse response, String dispatchId)throws OAException{
			return operationLogService.getDutyWeather(dispatchId);
	}
	
	@RequestMapping(value="getdutySys")
	@ResponseBody
	Object getdutySys(HttpServletRequest request, HttpServletResponse response, String dispatchId,int type)throws OAException{
			return operationLogService.getdutySys(dispatchId,type);
	}
	
	@RequestMapping(value="updatedutySys")
	@ResponseBody
	Object updatedutySys(HttpServletRequest request, HttpServletResponse response,@ModelAttribute DutySys dutySys)throws OAException{
			return operationLogService.updatedutySys(dutySys);
	}
	/**
	 * 删除验证记录
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws OAException
	 * 2015-2-26 下午5:12:29
	 */
	@RequestMapping(value="deletenotify")
	@ResponseBody
	public Object deletenotify(HttpServletRequest request,HttpServletResponse response,String id) throws OAException{
		return operationLogService.deleteNotify(id);
	}
	
	/**
	 * 查询验证记录
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param workId
	 * @return
	 * @throws OAException
	 * 2015-2-26 下午5:12:40
	 */
	@RequestMapping(value="getnotify")
	@ResponseBody
	public Object getnotify(HttpServletRequest request,HttpServletResponse response,String workId) throws OAException{
		return operationLogService.getNotify(Integer.parseInt(workId));
	}
}
