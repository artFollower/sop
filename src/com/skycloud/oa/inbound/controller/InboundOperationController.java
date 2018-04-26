package com.skycloud.oa.inbound.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.common.dto.SecurityCodeDto;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.CargoGoodsDto;
import com.skycloud.oa.inbound.dto.InboundOperationDto;
import com.skycloud.oa.inbound.model.Arrival;
import com.skycloud.oa.inbound.model.ArrivalInfo;
import com.skycloud.oa.inbound.model.BerthAssess;
import com.skycloud.oa.inbound.model.BerthProgram;
import com.skycloud.oa.inbound.model.Cargo;
import com.skycloud.oa.inbound.model.Goods;
import com.skycloud.oa.inbound.model.JobCheck;
import com.skycloud.oa.inbound.model.Notification;
import com.skycloud.oa.inbound.model.Store;
import com.skycloud.oa.inbound.model.TransportInfo;
import com.skycloud.oa.inbound.model.TransportProgram;
import com.skycloud.oa.inbound.model.Work;
import com.skycloud.oa.inbound.model.WorkCheck;
import com.skycloud.oa.inbound.service.CargoGoodsService;
import com.skycloud.oa.inbound.service.InboundOperationService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.model.ApproveContent;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.ExcelOutputUtil;
import com.skycloud.oa.utils.OaMsg;
import com.skycloud.oa.utils.ExcelOutputUtil.CallBack;

/**
 * 入库作业
 * @author jhy
 *status 
 */
@Controller
@RequestMapping("/inboundoperation")
public class InboundOperationController {

	@Autowired
	private InboundOperationService inboundOperationService;
	@Autowired
	private CargoGoodsService cargoGoodsService;
	
	/**获取入库所以相关列表list*/
	@RequestMapping(value="/list")
	@ResponseBody
	public Object list(HttpServletRequest request,HttpServletResponse response,@ModelAttribute InboundOperationDto ioDto,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
	     OaMsg oaMsg=null;
	     if(ioDto.getResult()!=16){
		oaMsg=inboundOperationService.getInboundOperationList(ioDto,new PageView(pagesize, page));
	     }else{//result=16,获取带有初始货体的货批
	    	 CargoGoodsDto caDto=new CargoGoodsDto();
	    	 caDto.setArrivalId(ioDto.getId());
	    	 if(ioDto.getProductId()!=null)
	    	 caDto.setProductId(ioDto.getProductId());
	    	 caDto.setTankGoods(true);
	    	 caDto.setIsPredict(2);
	    	 caDto.setGoodsStatus(ioDto.getCargoGoodsStatus());
	    	 oaMsg=cargoGoodsService.getCargoList(caDto, new PageView(pagesize,page),true);
	     }
		return oaMsg;
	}
	
	/**添加与入库作业所有相关的表*/
	@RequestMapping(value="additem")
	@ResponseBody
	public Object  addItem(HttpServletRequest request,HttpServletResponse response,@ModelAttribute InboundOperationDto ioDto) throws OAException{
		OaMsg oaMsg=null;
		oaMsg=inboundOperationService.addInboundOperationItem(ioDto);
		return oaMsg;
	}
	
	@RequestMapping(value="updateInboundInfo")
	@ResponseBody
	public OaMsg updateInboundInfo(@ModelAttribute InboundOperationDto ioDto)throws OAException{
		return inboundOperationService.updateInboundInfo(ioDto);
	}
	
	/**更新到港附加信息(入库计划) */
	@RequestMapping(value="updatearrivalinfo")
	@ResponseBody
	@RequiresPermissions(value="AINBOUNDWORKUPDATE")
	public Object  updateArrivalInfo(HttpServletRequest request,HttpServletResponse response,@ModelAttribute ArrivalInfo arrivalInfo,Integer isTransport) throws OAException{
		OaMsg oaMsg=null;
		arrivalInfo.setCreateTime(System.currentTimeMillis()/1000);
		inboundOperationService.updateArrivalInfo(arrivalInfo);
		Arrival arrival=new Arrival();
		arrival.setId(arrivalInfo.getArrivalId());
		if(arrivalInfo.getStatus()!=null&&arrivalInfo.getStatus()==1){//提交按钮改变状态为靠泊评估
			if(isTransport!=null&&isTransport==2){
				arrival.setStatus(5);
				Work work =new Work();
				work.setArrivalId(arrival.getId());
				work.setStatus(5);
				inboundOperationService.updateWork(work);
			}else{
				arrival.setStatus(3);	
			}
		}
		if(arrivalInfo.getAnchorTime()!=null)
		arrival.setArrivalStartTime(new Timestamp(arrivalInfo.getAnchorTime()*1000));
		oaMsg=inboundOperationService.updateArrival(arrival,null);
		return oaMsg;
	}
	/**更新靠泊评估  status表示评估的状态 0.保存1.提交，2。通过3.不通过*/
	@RequestMapping(value="updateberthassess")
	@ResponseBody
	public Object  updateBerthAssess(HttpServletRequest request,HttpServletResponse response,@ModelAttribute BerthAssess berthAssess,@ModelAttribute SecurityCodeDto scDto) throws OAException{
		OaMsg oaMsg=null;
		oaMsg=inboundOperationService.updateBerthAssess(berthAssess,scDto);
		return oaMsg;
	}
	/**更新靠泊方案  status表示方案状态0.保存1.提交，2。通过3.不通过 */
	@RequestMapping(value="updateberthprogram")
	@ResponseBody
	public Object  updateBerthProgram(HttpServletRequest request,HttpServletResponse response,@ModelAttribute BerthProgram berthProgram,@ModelAttribute SecurityCodeDto scDto) throws OAException{
		OaMsg oaMsg=null;
		oaMsg=inboundOperationService.updateBerthProgram(berthProgram,scDto);
		return oaMsg;
	}
	/**更新接卸方案  */
	@RequestMapping(value="updatetransportprogram")
	@ResponseBody
	public Object  updateTransportProgram(HttpServletRequest request,HttpServletResponse response,@ModelAttribute TransportProgram transportProgram,String tankIds,String tubeIds,Integer workId,@ModelAttribute SecurityCodeDto scDto) throws OAException{
		OaMsg oaMsg=null;
		oaMsg=inboundOperationService.updateTransportProgram(transportProgram,tankIds,scDto,workId);
		return oaMsg;
	}
	
	/**添加工作检查表信息*/
	@RequestMapping(value="addworkcheck")
	@ResponseBody
	public Object  addWorkCheck(HttpServletRequest request,HttpServletResponse response,@ModelAttribute WorkCheck workCheck) throws OAException{
		OaMsg oaMsg=null;
		oaMsg=inboundOperationService.addWorkCheck(workCheck);
		return oaMsg;
	}
	/**更新工作检查表*/
	@RequestMapping(value="updateworkcheck")
	@ResponseBody
	public Object  updateWorkCheck(HttpServletRequest request,HttpServletResponse response,@ModelAttribute WorkCheck workCheck) throws OAException{
		OaMsg oaMsg=null;
		oaMsg=inboundOperationService.updateWorkCheck(workCheck);
		return oaMsg;
	}
	/**生成岗位检查信息*/
	@RequestMapping(value="addworkchecklist")
	@ResponseBody
	public Object  updateWorkCheckList(HttpServletRequest request,HttpServletResponse response,String id) throws OAException{
		OaMsg oaMsg=null;
		oaMsg=inboundOperationService.addWorkCheckList(Integer.valueOf(id));//管线作业检查表添加
		return oaMsg;
	}
	/**更新岗位检查表*/
	@RequestMapping(value="updatejobcheck")
	@ResponseBody
	public Object  updateJobCheck(HttpServletRequest request,HttpServletResponse response,@ModelAttribute JobCheck jobCheck) throws OAException{
		OaMsg oaMsg=null;
		oaMsg=inboundOperationService.updateJobCheck(jobCheck);
		return oaMsg;
	}
	/**更新到港作业信息表*/
	@RequestMapping(value="updatework")
	@ResponseBody
	public Object  updateWork(HttpServletRequest request,HttpServletResponse response,String arrivalId,@ModelAttribute Work work,String notificationList) throws OAException{
		OaMsg oaMsg=null;
		if(notificationList!=null){
		List<Notification> mNotificationList= new Gson().fromJson(notificationList,new TypeToken<List<Notification>>() {}.getType());
		oaMsg=inboundOperationService.updateWork(arrivalId,work,mNotificationList);
		}else{
			oaMsg=inboundOperationService.updateWork(work);
		}
		return oaMsg;
	}
	/**更新到港表*/
	@RequestMapping(value="updatearrival")
	@ResponseBody
	@RequiresPermissions(value="AINBOUNDUPDATE")
	public Object  updateArrival(HttpServletRequest request,HttpServletResponse response,@ModelAttribute Arrival arrival,String shipRefName) throws OAException{
		OaMsg oaMsg=null;
		oaMsg=inboundOperationService.updateArrival(arrival,shipRefName);
		return oaMsg;
	}
	/**更新存储罐*/
	@RequestMapping(value="updatestorelist")
	@ResponseBody
	public Object  updateStoreList(HttpServletRequest request,HttpServletResponse response,@RequestParam String storelist) throws OAException{
		OaMsg oaMsg=null;
		List<Store> storeList= new Gson().fromJson(storelist,new TypeToken<List<Store>>() {}.getType());
		oaMsg=inboundOperationService.updateStoreList(storeList);
		return oaMsg;
	}
	
	/**更新货批添加货体信息*/
	@RequestMapping(value="updatecargogoodslist")
	@ResponseBody
	public Object  updateCargoList(HttpServletRequest request,HttpServletResponse response,@RequestParam String cargolist,@RequestParam String goodslist,@ModelAttribute Work work,String shipId,@ModelAttribute SecurityCodeDto scDto) throws OAException{
		OaMsg oaMsg=null;
		List<Cargo> cargoList= new Gson().fromJson(cargolist,new TypeToken<List<Cargo>>() {}.getType());
		List<Goods> goodsList= new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().fromJson(goodslist,new TypeToken<List<Goods>>() {}.getType());
		oaMsg=inboundOperationService.updateCargoGoodsList(cargoList,goodsList,work,shipId,scDto);
		return oaMsg;
	}
	
	/**回退到某个流程*/
	@RequestMapping(value="cleantostatus")
	@ResponseBody
	public Object cleanToStatus(HttpServletRequest request,HttpServletResponse response,@ModelAttribute InboundOperationDto ioDto) throws OAException{
		return inboundOperationService.cleanToStatus(ioDto);
	}
	
	/**添加传输方案附属信息*/
	@RequestMapping(value="addtransportinfo")
	@ResponseBody
	public Object addTransportInfo(HttpServletRequest request,HttpServletResponse response,@ModelAttribute TransportInfo transportInfo) throws OAException{
		return inboundOperationService.addTransportInfo(transportInfo);
	}
	/**修改传输方案附属信息*/
	@RequestMapping(value="updatetransportinfo")
	@ResponseBody
	public Object updateTransportInfo(HttpServletRequest request,HttpServletResponse response,@ModelAttribute TransportInfo transportInfo) throws OAException{
		return inboundOperationService.updateTransportInfo(transportInfo);
	}
	
	@RequestMapping(value="getapprovecontent")
	@ResponseBody
	public Object getapprovework(HttpServletRequest request, HttpServletResponse response,@ModelAttribute ApproveContent approveContent,Integer getType) throws OAException{
		   if(getType!=null&&getType==1){
			   User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			approveContent.setUserId(user.getId());
		   }
		return inboundOperationService.getApproveContent(approveContent);
	}
	/**工作流审批*/
	@RequestMapping(value="sendcheck")
	@ResponseBody
	public Object sendcheck(HttpServletRequest request, HttpServletResponse response,String ids,Integer berthassessId,String content,String url,String msgContent) throws OAException{
		return inboundOperationService.sendCheck(ids,berthassessId,content,url,msgContent);
	}
	/**抄送发送消息*/
	@RequestMapping(value="sendcopy")
	@ResponseBody
	public Object sendcopy(HttpServletRequest request, HttpServletResponse response,String ids,Integer berthassessId,String content) throws OAException{
		return inboundOperationService.sendCopy(ids,berthassessId,content);
	}
	
	/**添加一次多次接卸*/
	@RequestMapping(value="addunloading")
	@ResponseBody
	public Object addUnloading(HttpServletRequest request, HttpServletResponse response,InboundOperationDto ioDto) throws OAException{
		return inboundOperationService.addUnloading(ioDto);
	}
	
	/**添加一次多次打循环*/
	@RequestMapping(value="addbackflow")
	@ResponseBody
	public Object addBackFlow(HttpServletRequest request, HttpServletResponse response,InboundOperationDto ioDto) throws OAException{
		return inboundOperationService.addBackFlow(ioDto);
	}
	/**删除一次多次接卸*/
	@RequestMapping(value="deleteunloading")
	@ResponseBody
	public Object deleteUnloading(HttpServletRequest request, HttpServletResponse response,InboundOperationDto ioDto) throws OAException{
		return inboundOperationService.deleteUnloading(ioDto);
	}
	/**删除一次多次接卸*/
	@RequestMapping(value="deletebackflow")
	@ResponseBody
	public Object deletebackflow(HttpServletRequest request, HttpServletResponse response,InboundOperationDto ioDto) throws OAException{
		return inboundOperationService.deleteBackFlow(ioDto);
	}
	
	/**根据arrivalId productId 查询总的储罐收货量*/
	@RequestMapping(value="gettotalgoodstank")
	@ResponseBody
	public Object getTotalGoodsTank(HttpServletRequest request, HttpServletResponse response,InboundOperationDto ioDto) throws OAException{
		return inboundOperationService.getTotalGoodsTank(ioDto);
	}
	/**根据arrivalId 查询货批信息*/
	@RequestMapping(value="getPrintList")
	@ResponseBody
	public OaMsg getPrintList(HttpServletRequest request, HttpServletResponse response,InboundOperationDto ioDto) throws OAException{
		return inboundOperationService.getPrintList(ioDto);
	}
	/**导出入库信息表*/
	@RequestMapping(value = "exportInbound")
	public void exportInbound(HttpServletRequest request,HttpServletResponse response,final InboundOperationDto ioDto) throws OAException {
		ExcelOutputUtil.handleExcelOutput(request, response, new CallBack() {
			@Override
			public HSSFWorkbook getWorkBook(HttpServletRequest request) {
				HSSFWorkbook workbook = null;
				 try {
					workbook=inboundOperationService.exportInbound(request,ioDto);
				} catch (OAException e) {
					e.printStackTrace();
				}
				return workbook;
			}
		});
	}
	
	
}
