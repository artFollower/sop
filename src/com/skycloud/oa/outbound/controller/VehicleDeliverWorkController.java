package com.skycloud.oa.outbound.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.model.TransportProgram;
import com.skycloud.oa.inbound.model.WorkCheck;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dto.GoodsLogDto;
import com.skycloud.oa.outbound.dto.VehicleDeliverWorkQueryDto;
import com.skycloud.oa.outbound.dto.VehicleDeliverWorkQueryDtoList;
import com.skycloud.oa.outbound.model.BatchVehicleInfo;
import com.skycloud.oa.outbound.model.Train;
import com.skycloud.oa.outbound.model.VehiclePlan;
import com.skycloud.oa.outbound.model.WeighBridge;
import com.skycloud.oa.outbound.service.VehicleDeliverWorkService;
import com.skycloud.oa.utils.OaMsg;

/**
 * 
 * <p>出库管理---车发出库</p>
 * @ClassName:VehicleDeliverWorkController
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年5月28日 下午1:29:17
 *
 */
@Controller
@RequestMapping("/outboundtruckserial")
public class VehicleDeliverWorkController 
{
	@Autowired
	private VehicleDeliverWorkService vehicleDeliverWorkService ;
	/**
	 * 保存车发车次基本信息
	 * @param train
	 * @return
	 * @throws OAException 
	 */
	@RequestMapping(value = "saveTrain")
	@ResponseBody
	public OaMsg saveTrain(String train) throws OAException
	{
		Train d = new Gson().fromJson(train, Train.class) ;
		return vehicleDeliverWorkService.saveTrain(d);
	}
	
	/**
	 * @throws OAException 
	 * 查询出库列表
	 * @Title:VehicleDeliverWorkController
	 * @Description:
	 * @param vehicleDeliverWorkQueryDto
	 * @param pagesize
	 * @param page
	 * @return
	 * @Date:2015年5月28日 下午1:30:08
	 * @return:OaMsg 
	 * @throws
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public OaMsg list(@ModelAttribute VehicleDeliverWorkQueryDto vehicleDeliverWorkQueryDto,
					   @RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
					   @RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException 
	{
		return vehicleDeliverWorkService.list(vehicleDeliverWorkQueryDto, new PageView(pagesize,page)) ;
	}
	
	/**
	 * @throws OAException 
	 * 车发撤销
	 * @Title:VehicleDeliverWorkController
	 * @Description:
	 * @param ids
	 * @return
	 * @Date:2015年5月28日 下午1:58:12
	 * @return:OaMsg 
	 * @throws
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public OaMsg delete(String ids) throws OAException 
	{
		return vehicleDeliverWorkService.delete(ids) ;
	}
	
	
	/**
	 * 车发撤销
	 * @param vehicleDeliverWorkQueryDto
	 * @return
	 * @throws OAException 
	 */
	@RequestMapping(value = "deleteVehicleInfo")
	@ResponseBody
	public OaMsg deleteVehicleInfo(String id) throws OAException {
		return vehicleDeliverWorkService.deleteVehicleInfo(id) ;
	}
	
	/**
	 * 保存车发计划信息
	 * @param vehiclePlan
	 * @return
	 * @throws OAException 
	 */
	@RequestMapping(value = "saveVehiclePlan")
	@ResponseBody
	public OaMsg saveVehiclePlan(String vehiclePlan) throws OAException{
		VehiclePlan d = new Gson().fromJson(vehiclePlan, VehiclePlan.class) ;
		return vehicleDeliverWorkService.saveVehiclePlan(d) ;
	}
	
	/**
	 * 保存车次车辆装载信息
	 * @param batchVehicleInfo
	 * @return
	 * @throws OAException 
	 */
	@RequestMapping(value = "saveBatchVehicleInfo")
	@ResponseBody
	public OaMsg saveBatchVehicleInfo(String batchVehicleInfo) throws OAException{
		BatchVehicleInfo d = new Gson().fromJson(batchVehicleInfo, BatchVehicleInfo.class) ;
		return vehicleDeliverWorkService.saveBatchVehicleInfo(d) ;
	}
	
	
	/**
	 * 查询提单
	 * @param vehicleDeliverWorkQueryDto
	 * @return
	 * @throws OAException 
	 */
	@RequestMapping(value = "searchLading")
	@ResponseBody
	public OaMsg searchLading(String vehicleDeliverWorkQueryDto) throws OAException{
		VehicleDeliverWorkQueryDto d = new Gson().fromJson(vehicleDeliverWorkQueryDto, VehicleDeliverWorkQueryDto.class) ;
		return vehicleDeliverWorkService.searchLading(d) ;
	}
	
	
	/**
	 * 通过计划id删除账单
	 * @param planId
	 * @return
	 * @throws OAException 
	 */
	@RequestMapping(value = "deleteBillInfo")
	@ResponseBody
	public OaMsg deleteBillInfo(String planId) throws OAException{
		return vehicleDeliverWorkService.deleteBillInfo(planId) ;
	}
	
	
	/**
	 * 获取车发信息
	 * @param plateId
	 * @param trainId
	 * @return
	 * @throws OAException 
	 */
	@RequestMapping(value = "getVehicleDeliver")
	@ResponseBody
	public OaMsg getVehicleDeliver(String plateId,String trainId) throws OAException{
		return vehicleDeliverWorkService.getVehicleDeliver(plateId,trainId) ;
	}
	
	/**
	 * 保存和修改称重计量
	 * @param workPlanDao
	 * @param trainId
	 * @return
	 * @throws OAException 
	 */
	@RequestMapping(value = "saveWeighBridge")
	@ResponseBody
	public OaMsg saveWeighBridge(String weighBridge,GoodsLogDto glDto) throws OAException{
		WeighBridge wb = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().fromJson(weighBridge, WeighBridge.class) ;
		return vehicleDeliverWorkService.saveWeighBridge(wb,glDto) ;
	}
	
	/**
	 * 添加车发信息
	 * @param vehicleInfo
	 * @param trainId
	 * @return
	 * @throws OAException 
	 */
	@RequestMapping(value = "addVehicleInfo")
	@ResponseBody
	public OaMsg addVehicleInfo(String vehicleInfo,String trainId) throws OAException{
		VehicleDeliverWorkQueryDtoList d = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().fromJson(vehicleInfo, VehicleDeliverWorkQueryDtoList.class) ;
		return vehicleDeliverWorkService.addVehicleInfo(d,trainId) ;
	}
	
	/**
	 * 通过id查询车发信息
	 * @param id
	 * @return
	 * @throws OAException 
	 */
	@RequestMapping(value = "getTrainInfoById")
	@ResponseBody
	public OaMsg getTrainInfoById(String id,String serial) throws OAException
	{
		return vehicleDeliverWorkService.getTrainInfoById(id,serial) ;
	}
	
	/**
	 * 查询发货通知单内容
	 * @param trainId
	 * @return
	 * @throws OAException 
	 */
	@RequestMapping(value = "getTruckInvoiceMsg")
	@ResponseBody
	public OaMsg getTruckInvoiceMsg(String trainId) throws OAException{
		return vehicleDeliverWorkService.getTruckInvoiceMsg(trainId) ;
	}
	
	/**
	 * 获取车发作业信息
	 * @param trainId
	 * @return
	 * @throws OAException 
	 */
	@RequestMapping(value = "getTruckReadyMsg")
	@ResponseBody
	public OaMsg getTruckReadyMsg(String trainId) throws OAException{
		return vehicleDeliverWorkService.getTruckReadyMsg(trainId) ;
	}
	
	/**
	 * 查询数量确认页面内容
	 * @param trainId
	 * @return
	 * @throws OAException 
	 */
	@RequestMapping(value = "getCheckMountMsg")
	@ResponseBody
	public OaMsg getCheckMountMsg(String trainId) throws OAException{
		return vehicleDeliverWorkService.getCheckMountMsg(trainId) ;
	}

	/**
	 * 保存配管通知单内容
	 * @param tp
	 * @param trainId
	 * @return
	 * @throws OAException 
	 */
	@RequestMapping(value = "savePipeNotifyInfo")
	@ResponseBody
	public OaMsg savePipeNotifyInfo(String tp,String trainId,String workCheck) throws OAException{
		TransportProgram t = new Gson().fromJson(tp, TransportProgram.class) ;
		WorkCheck wk = new Gson().fromJson(workCheck, WorkCheck.class) ;
		return vehicleDeliverWorkService.savePipeNotifyInfo(t,trainId,wk) ;
	}


	
	/**
	 * 车发确认数据
	 * @param vehiclePlanList
	 * @param trainId
	 * @return
	 * @throws OAException 
	 */
	@RequestMapping(value = "confirmData")
	@ResponseBody
	public OaMsg confirmData(String vehiclePlanList ,String trainId) throws OAException{
		VehicleDeliverWorkQueryDtoList d = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().fromJson(vehiclePlanList, VehicleDeliverWorkQueryDtoList.class) ;
		return vehicleDeliverWorkService.confirmData(d,trainId) ;
	}
	/**导出出库信息表*/
	@RequestMapping(value = "exportTruckExcel")
	public void exportInbound(HttpServletRequest request,HttpServletResponse response,VehicleDeliverWorkQueryDto vDto) throws OAException {
		response.setContentType("application/vnd.ms-excel");
		OutputStream fOut = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//进行转码
			response.setHeader("content-disposition", "attachment;filename=" + sdf.format(new Date()) + ".xls");
			
			HSSFWorkbook workbook = vehicleDeliverWorkService.exportTruckExcel(request,vDto);
			fOut = response.getOutputStream();
			workbook.write(fOut);
		} catch (Exception e) {

		} finally {
			try {
				fOut.flush();
				fOut.close();
			} catch (IOException e) {
			}
		}
	}
	
	
	
}
