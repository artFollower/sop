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
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.ArrivalDto;
import com.skycloud.oa.inbound.dto.ArrivalPlanDto;
import com.skycloud.oa.inbound.dto.CargoGoodsDto;
import com.skycloud.oa.inbound.dto.InsertArrivalPlanDto;
import com.skycloud.oa.inbound.dto.InsertSingleArrivalPlanDto;
import com.skycloud.oa.inbound.model.Arrival;
import com.skycloud.oa.inbound.model.ArrivalInfo;
import com.skycloud.oa.inbound.model.Cargo;
import com.skycloud.oa.inbound.service.ArrivalPlanService;
import com.skycloud.oa.inbound.service.CargoGoodsService;
import com.skycloud.oa.inbound.service.InboundOperationService;
import com.skycloud.oa.order.dto.ContractDto;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.HibernateUtils;
import com.skycloud.oa.utils.OaMsg;

/**
 * 到港计划
 * 
 * @ClassName: ArrivalPlanController
 * @Description: TODO
 * @author xie
 * @date 2014年12月2日 下午4:07:40
 */
@Controller
@RequestMapping("/arrivalPlan")
public class ArrivalPlanController {

	@Autowired
	private ArrivalPlanService arrivalPlanService;
	
	@Autowired
	private CargoGoodsService cargoGoodsService;
	
	@Autowired
	private InboundOperationService inboundOperationService;
	

	/**
	 * 查询到港计划
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param arrivalDto
	 * @param pageSize
	 * @param currentPage
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public OaMsg list(HttpServletRequest request, HttpServletResponse response, @ModelAttribute ArrivalDto arrivalDto,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize, @RequestParam(defaultValue = "0", required = false, value = "page") int page)
			throws OAException {
		OaMsg msg = arrivalPlanService.getArrivalList(arrivalDto, new PageView(pagesize, page));
		return msg;
	}
	
	@RequestMapping(value = "getArrivalInfo")
	@ResponseBody
	public OaMsg getArrivalInfo(HttpServletRequest request, HttpServletResponse response, @ModelAttribute ArrivalDto arrivalDto)
			throws OAException {
		OaMsg msg = arrivalPlanService.getArrivalInfo(arrivalDto);
		return msg;
	}
	
	@RequestMapping(value = "updatearrivalinfo")
	@ResponseBody
	public OaMsg updatearrivalInfo(HttpServletRequest request, HttpServletResponse response, @ModelAttribute ArrivalInfo arrivalInfo)
			throws OAException {
		OaMsg msg = arrivalPlanService.updateArrivalInfo(arrivalInfo);
		return msg;
	}
	
	
	@RequestMapping(value = "exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,@ModelAttribute ArrivalDto arrivalDto) 
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
			
			OaMsg msg=arrivalPlanService.getArrivalList(arrivalDto, new PageView(0,0));
			
			String[] str={"船名","中文名","船期","船长(米)","船宽(米)","吃水(米)","载重(吨)","理货","未关联合同数","状态"};
			
			
			List<List<Object>> data=new ArrayList<List<Object>>();
			
			for(int i=0;i<msg.getData().size();i++){
				Map<String,Object> map=(Map<String, Object>) msg.getData().get(i);
				List<Object> d=new ArrayList<Object>();
				d.add(map.get("shipName").toString());
				
				String shipRefName="";
				if(Integer.parseInt(map.get("type").toString())==3){
					shipRefName= map.get("shipRefName").toString()+"  (通过)";
				}else{
					if(!Common.empty(map.get("shipRefName"))){
						
						shipRefName= map.get("shipRefName").toString();
					}
				}
				d.add(shipRefName);
				
				String time="";
				if(!Common.empty(map.get("mEndTime"))){
					time=map.get("mArrivalTime").toString()+" 至   "+map.get("mEndTime").toString();
				}else{
					time=map.get("mArrivalTime").toString();
				}
				
				d.add(time);
				d.add(Common.empty(map.get("shipLenth"))?"":map.get("shipLenth").toString());
				d.add(Common.empty(map.get("shipWidth"))?"":map.get("shipWidth").toString());
				d.add(Common.empty(map.get("shipDraught"))?"":map.get("shipDraught").toString());
				d.add(Common.empty(map.get("loadCapacity"))?"":map.get("loadCapacity").toString());
				
				String isTrim="";
				if(!Common.empty(map.get("isTrim"))){
					if(Integer.parseInt(map.get("isTrim").toString())==1){
						isTrim="已理货";
					}
				}
				
				d.add(isTrim);
				d.add(map.get("unContract").toString());
				
				String status="";
				if(!Common.empty(map.get("status"))){
					if(Integer.parseInt(map.get("status").toString())>1){
						status="已确认";
					}else{
						status="待确认";
					}
				}
				
				
				d.add(status);
				
				data.add(d);
			}
			
			HSSFWorkbook workbook=Common.getExcel("入港计划", str, data,new int[]{0,0,0,1,1,1,1,0,1,0});
			
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
	
	
	
	
	/**
	 * 获得未确认的货批
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param arrivalId
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "getplan")
	@ResponseBody
	public OaMsg getplan(HttpServletRequest request, HttpServletResponse response, @ModelAttribute ArrivalPlanDto aDto)
			throws OAException {
		OaMsg msg=new OaMsg();
		msg = arrivalPlanService.getArrivalPlanList(aDto,msg);
		return msg;
	}
	
	/**
	 * 获得未确认的货批
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param arrivalId
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "updateNo")
	@ResponseBody
	public OaMsg updateNo(HttpServletRequest request, HttpServletResponse response,String planId,String No)
			throws OAException {
		OaMsg msg=new OaMsg();
		msg = arrivalPlanService.updateNo(planId,No);
		return msg;
	}
	
	
	
	@RequestMapping(value = "updateCargoNo")
	@ResponseBody
	public OaMsg updateCargoNo(HttpServletRequest request, HttpServletResponse response,String cargoId,String No)
			throws OAException {
		OaMsg msg=new OaMsg();
		msg = arrivalPlanService.updateCargoNo(cargoId,No);
		return msg;
	}
	
	
	@RequestMapping(value = "updateCargoinboundNo")
	@ResponseBody
	public OaMsg updateCargoinboundNo(HttpServletRequest request, HttpServletResponse response,String cargoId,String inboundNo)
			throws OAException {
		OaMsg msg=new OaMsg();
		msg = arrivalPlanService.updateCargoInboundNo(cargoId,inboundNo);
		return msg;
	}
	
	
	/**
	 * 获得已确认的货批
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param arrivalId
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "getsure")
	@ResponseBody
	public OaMsg getsure(HttpServletRequest request, HttpServletResponse response, String arrivalId)
			throws OAException {
		OaMsg msg=new OaMsg();
		msg = arrivalPlanService.getArrivalSureList(Integer.parseInt(arrivalId), msg);
		return msg;
	}


	
	
	/**
	 * 添加计划
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param arrivalPlanDto
	 * @param arrivalTime
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public Object save(HttpServletRequest request, HttpServletResponse response, String arrivalPlanDto) throws OAException {
		InsertArrivalPlanDto _arrivalPlanDto = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().fromJson(arrivalPlanDto, InsertArrivalPlanDto.class);
		User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		for(int i=0;i<_arrivalPlanDto.getArrivalPlanList().size();i++){
			_arrivalPlanDto.getArrivalPlanList().get(i).setCreateUserId(user.getId());
		}
		_arrivalPlanDto.getArrival().setCreateUserId(user.getId());
		_arrivalPlanDto.getArrival().setCreateTime(Long.parseLong(new Date().getTime()/1000+""));
		return arrivalPlanService.addArrivalPlan(_arrivalPlanDto);
	}
	
	/**
	 * 确认
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param arrival
	 * @param arrivalId
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "confirm")
	@ResponseBody
	public Object confirm(HttpServletRequest request, HttpServletResponse response, String ids) throws OAException {
		OaMsg msg = arrivalPlanService.addCargo(ids);
		return msg;
	}

	@RequestMapping(value = "confirmArrival")
	@ResponseBody
	public Object confirmArrival(HttpServletRequest request, HttpServletResponse response, int arrivalId) throws OAException {
		
		Arrival arrival=new Arrival();
		arrival.setId(arrivalId);
		arrival.setStatus(2);
		User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		arrival.setReviewArrivalUserId(user.getId());
		arrival.setReviewArrivalTime(new Date().getTime()/1000);
		OaMsg oaMsg=inboundOperationService.updateArrival(arrival,null);
		
		return oaMsg;
	}
	
	
	@RequestMapping(value = "addCargo")
	@ResponseBody
	public Object addCargo(HttpServletRequest request, HttpServletResponse response, Cargo cargo,Integer tankId) throws OAException {
		OaMsg msg = arrivalPlanService.addCargo(cargo,tankId);
		return msg;
	}
	
	@RequestMapping(value = "updateCargo")
	@ResponseBody
	public Object updateCargo(HttpServletRequest request, HttpServletResponse response, Cargo cargo,Integer tankId,String sCustomLadingTime) throws OAException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(!Common.empty(sCustomLadingTime)){
			long customLadingTime;
			try {
				customLadingTime = sdf.parse(sCustomLadingTime).getTime() / 1000;
				cargo.setCustomLadingTime(customLadingTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		OaMsg msg = arrivalPlanService.updateCargo(cargo);
		return msg;
	}
	
	

	/**
	 * 删除到港计划
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param ids
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "deletePlan")
	@ResponseBody
	public Object deletePlan(HttpServletRequest request, HttpServletResponse response, String ids) throws OAException {
		return arrivalPlanService.deleteArrivalPlan(ids);
	}
	
	/**
	 * 删除到港计划
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public Object delete(HttpServletRequest request, HttpServletResponse response, String id) throws OAException {
		return arrivalPlanService.deleteArrival(id);
	}
	
	/**
	 * 更新到港信息
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param arrival
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "update")
	@ResponseBody
	public Object update(HttpServletRequest request, HttpServletResponse response, Arrival arrival,String shipArrivalDraught,int isCreateInfo) throws OAException {
		return arrivalPlanService.updateArrival(arrival,shipArrivalDraught,isCreateInfo);
	}
	
	/**
	 * 添加货批
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param singleArrivalPlanDto
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "saveGood")
	@ResponseBody
	public Object saveGood(HttpServletRequest request, HttpServletResponse response,InsertSingleArrivalPlanDto singleArrivalPlanDto) throws OAException {
		User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		singleArrivalPlanDto.setCreateUserId(user.getId());
		return arrivalPlanService.addSingleArrivalPlan(singleArrivalPlanDto);
	}
	
	/**
	 * 预入库
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param tankId
	 * @param goodsId
	 * @param cargoId
	 * @param goodsTotal
	 * @return
	 * @throws OAException
	 * 2015-3-5 下午2:07:27
	 */
	@RequestMapping(value = "predict")
	@ResponseBody
	public Object predict(HttpServletRequest request, HttpServletResponse response,String tankId,String goodsId,String cargoId,String goodsTotal) throws OAException {
		OaMsg oaMsg=new OaMsg();
		cargoGoodsService.predictGoods(goodsId, cargoId, goodsTotal,tankId);
		Session session = HibernateUtils.getCurrentSession();
		if (session != null) {
			session.getTransaction().commit();
		}
		if(oaMsg.getCode().equals("0000")){
			session.beginTransaction();
			return cargoGoodsService.updateGoodsCode(Integer.parseInt(cargoId), "");
		}
		else{
			oaMsg.setCode(Constant.SYS_CODE_NULL_ERR);
			return oaMsg;
		}
	}
	
	
	
	
	/**
	 * 搜索货体
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param ladingDto
	 * @param method
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "serchGoods")
	@ResponseBody
	public Object serchGoods(HttpServletRequest request, HttpServletResponse response,@ModelAttribute CargoGoodsDto cargoDto) throws OAException{
		cargoDto.setTankGoods(true);
		return cargoGoodsService.getPredictGoods(cargoDto, new PageView(0,0));
	}
	
	
	/**
	 * 删除预入库货体
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param ids
	 * @return
	 * @throws OAException
	 * 2015-3-5 上午11:27:54
	 */
	@RequestMapping(value = "deletePredict")
	@ResponseBody
	@Log(object=C.LOG_OBJECT.PCS_PREDICTGOODS,type=C.LOG_TYPE.DELETE)
	public Object deletePredict(HttpServletRequest request, HttpServletResponse response,String ids) throws OAException{
		
		return cargoGoodsService.deleteGoods(ids);
	}
	
	/**
	 * 货批拆分
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param cargoId 货批id
	 * @param goodsIdList 要拆分的货体id
	 * @param goodsCount  要拆分的货体对应的数量
	 * @param cargoCount  要拆分出的货批数量
	 * @param isCreateGoods  是否同步生成货体
	 * @return 
	 * @throws OAException
	 * 2015-3-5 上午11:37:08
	 */
	@RequestMapping(value = "sqlitCargo")
	@ResponseBody
	@Log(object=C.LOG_OBJECT.PCS_CARGO,type=C.LOG_TYPE.SPLIT)
	public Object sqlitCargo(HttpServletRequest request, HttpServletResponse response,String clientId,String cargoId,String goodsIdList,String goodsCount,String cargoCount,int isCreateGoods) throws OAException{
		
		return cargoGoodsService.sqlitCargo(clientId,cargoId,goodsIdList,goodsCount,cargoCount,isCreateGoods);
	}
	
	
	
}
