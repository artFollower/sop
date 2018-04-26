package com.skycloud.oa.inbound.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
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
import com.skycloud.oa.inbound.model.ArrivalForeshow;
import com.skycloud.oa.inbound.model.ArrivalInfo;
import com.skycloud.oa.inbound.model.Cargo;
import com.skycloud.oa.inbound.service.ArrivalForeshowService;
import com.skycloud.oa.inbound.service.ArrivalPlanService;
import com.skycloud.oa.inbound.service.CargoGoodsService;
import com.skycloud.oa.order.dto.ContractDto;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.report.utils.FormatUtils;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.ExcelUtil;
import com.skycloud.oa.utils.HibernateUtils;
import com.skycloud.oa.utils.OaMsg;
import com.skycloud.oa.utils.ExcelUtil.CallBack;

/**
 * 到港计划
 * 
 * @ClassName: ArrivalPlanController
 * @Description: TODO
 * @author xie
 * @date 2014年12月2日 下午4:07:40
 */
@Controller
@RequestMapping("/arrivalForeshow")
public class ArrivalForeshowController {

	@Autowired
	private ArrivalForeshowService arrivalForeshowService;
	
	@Autowired
	private CargoGoodsService cargoGoodsService;

	
	/**
	 * 船期预报列表
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param arrivalDto
	 * @param pagesize
	 * @param page
	 * @return
	 * @throws OAException
	 * 2016-3-11 下午2:56:03
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public OaMsg list(HttpServletRequest request, HttpServletResponse response, @ModelAttribute ArrivalDto arrivalDto,
			@RequestParam(defaultValue = "10", required = false, value = "pagesize") int pagesize, @RequestParam(defaultValue = "0", required = false, value = "page") int page)
			throws OAException {
		OaMsg msg = arrivalForeshowService.getArrivalForeshowList(arrivalDto, new PageView(0, 0));
		return msg;
	}
	
	@RequestMapping(value = "getOutArrivalCargo")
	@ResponseBody
	public OaMsg getOutArrivalCargo(HttpServletRequest request, HttpServletResponse response, int arrivalId)
			throws OAException {
		OaMsg msg = arrivalForeshowService.getOutArrivalCargo(arrivalId);
		return msg;
	}
	@RequestMapping(value = "checkForeshow")
	@ResponseBody
	public OaMsg checkForeshow(HttpServletRequest request, HttpServletResponse response, int id)
			throws OAException {
		OaMsg msg = arrivalForeshowService.checkForeshow(id);
		return msg;
	}
	
	@RequestMapping(value = "updateForeshowBySQL")
	@ResponseBody
	public OaMsg updateForeshowBySQL(HttpServletRequest request, HttpServletResponse response, int arrivalId)
			throws OAException {
		OaMsg msg = arrivalForeshowService.updateForeshowBySQL(arrivalId);
		return msg;
	}
	/**
	 * SQL添加船期预告
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param arrivalId
	 * @return
	 * @throws OAException
	 * 2016-5-16 下午2:17:03
	 */
	@RequestMapping(value = "addForeshowBySQL")
	@ResponseBody
	public OaMsg addForeshowBySQL(HttpServletRequest request, HttpServletResponse response, int arrivalId)
			throws OAException {
		OaMsg msg = arrivalForeshowService.addForeshowBySQL(arrivalId);
		return msg;
	}
	
	/**
	 * 关联超期预告
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param id
	 * @param arrivalId
	 * @return
	 * @throws OAException
	 * 2016-5-16 下午2:16:56
	 */
	@RequestMapping(value = "connectForeshow")
	@ResponseBody
	public OaMsg connectForeshow(HttpServletRequest request, HttpServletResponse response,int id, int arrivalId)
			throws OAException {
		
		ArrivalForeshow arrival=new ArrivalForeshow();
		arrival.setId(id);
		arrival.setArrivalId(arrivalId);
		OaMsg msg=arrivalForeshowService.updateForeshow(arrival);
		 msg = arrivalForeshowService.updateForeshowBySQL(arrivalId);
		return msg;
	}
	@RequestMapping(value = "updateForeshow")
	@ResponseBody
	public OaMsg updateForeshow(HttpServletRequest request, HttpServletResponse response, @ModelAttribute ArrivalForeshow arrivalForeshow )
			throws OAException {
		OaMsg msg = arrivalForeshowService.updateForeshow(arrivalForeshow);
		return msg;
	}
	
	
	/**
	 * 添加船期预告
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param arrivalForeshow
	 * @return
	 * @throws OAException
	 * 2016-5-16 下午2:16:45
	 */
	@RequestMapping(value = "addForeshow")
	@ResponseBody
	public OaMsg addForeshow(HttpServletRequest request, HttpServletResponse response, @ModelAttribute ArrivalForeshow arrivalForeshow )
			throws OAException {
		OaMsg msg = arrivalForeshowService.addForeshow(arrivalForeshow);
		return msg;
	}
	

	/**
	 * 删除船期预告
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws OAException
	 * 2016-5-16 下午2:16:37
	 */
	@RequestMapping(value = "deleteForeshow")
	@ResponseBody
	public OaMsg deleteForeshow(HttpServletRequest request, HttpServletResponse response,String id )
			throws OAException {
		OaMsg msg = arrivalForeshowService.deleteArrivalForeshow(id);
		return msg;
	}
	
	
	/**
	 * 作废船期预告
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws OAException
	 * 2016-5-16 下午2:16:30
	 */
	@RequestMapping(value = "zfForeshow")
	@ResponseBody
	public OaMsg zfForeshow(HttpServletRequest request, HttpServletResponse response,String id )
			throws OAException {
		OaMsg msg = arrivalForeshowService.zfArrivalForeshow(id);
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
			
			OaMsg msg = arrivalForeshowService.getArrivalForeshowList(arrivalDto, new PageView(0, 0));
			
			
			
			
			final List<Object> data=msg.getData();

        	


    		ExcelUtil excelUtil=null;
    		excelUtil=new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/arrivalForeshow.xls", new CallBack() {
    			@Override
    			public void setSheetValue(HSSFSheet sheet) 
    			{
    			int item=2,reginItem,itemChildrenSize;
				HSSFRow itemRow = null;
				Map<String, Object> map;
//				 map=(Map<String, Object>) data.get(0);
				
				 if(data!=null&&data.size()>0){
					 int size=data.size();
					for(int i=0;i<size;i++){
						
					 if(i!=0){
						itemRow=sheet.createRow(i+item);
						itemRow.setHeight(sheet.getRow(1).getHeight());
						for(int j=0;j<30;j++){
							itemRow.createCell(j).setCellStyle(sheet.getRow(1).getCell(j).getCellStyle());
						}
					 }else{
						 itemRow=sheet.getRow(item);
					 }
					 //初始化数据
					 map=(Map<String, Object>) data.get(i);
					 
					
					 
//					 itemRow.getCell(0).setCellValue(FormatUtils.getStringValue(i+1));//序号
//					 itemRow.getCell(1).setCellValue(FormatUtils.getStringValue(itemMapData.get("sCjTime")));
//					 itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(itemMapData.get("sTcTime")));
//					 itemRow.getCell(3).setCellValue(FormatUtils.getStringValue(itemMapData.get("sNorTime")));
//					 itemRow.getCell(4).setCellValue(FormatUtils.getStringValue(itemMapData.get("ladingCode1")));
//					 itemRow.getCell(5).setCellValue(FormatUtils.getStringValue(itemMapData.get("ladingEvidence")));
//					 itemRow.getCell(6).setCellValue(FormatUtils.getStringValue(itemMapData.get("carshipCode")));
//					 itemRow.getCell(6).setCellValue(FormatUtils.getStringValue(itemMapData.get("carshipCode")));
//					 itemRow.getCell(7).setCellValue(FormatUtils.getStringValue(new Date(Long.parseLong(itemMapData.get("createTime").toString())*1000).toLocaleString()));
//					 itemRow.getCell(8).setCellValue(Common.fixDouble(Double.parseDouble(itemMapData.get("deliverNum").toString()),3)+"");  
//					 sumD+=Common.fixDouble(Double.parseDouble(itemMapData.get("deliverNum").toString()),3);
//					 itemRow.getCell(9).setCellValue(Common.fixDouble(-Double.parseDouble(itemMapData.get("goodsChange").toString()),3)+"");  
//					 sumC+=	Common.fixDouble(-Double.parseDouble(itemMapData.get("goodsChange").toString()),3);
					 
					 itemRow.getCell(0).setCellValue(FormatUtils.getStringValue(i+1));//序号
					 itemRow.getCell(1).setCellValue(FormatUtils.getStringValue(Common.empty(map.get("sCjTime"))?"":map.get("sCjTime").toString()));
					 itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(Common.empty(map.get("sTcTime"))?"":map.get("sTcTime").toString()));
					 itemRow.getCell(3).setCellValue(FormatUtils.getStringValue(Common.empty(map.get("sNorTime"))?"":map.get("sNorTime").toString()));
					 String sa="";
					 if(!Common.empty(map.get("sAnchorTime"))){
						 sa=map.get("sAnchorTime").toString().split(" ")[0];
					 }
					 itemRow.getCell(4).setCellValue(FormatUtils.getStringValue(sa));
					 
					 String sa1="";
					 if(!Common.empty(map.get("sAnchorTime"))){
						 sa1=map.get("sAnchorTime").toString().split(" ")[1];
					 }
					 itemRow.getCell(5).setCellValue(FormatUtils.getStringValue(sa1));
					 itemRow.getCell(6).setCellValue(FormatUtils.getStringValue(Common.empty(map.get("sPumpOpenTime"))?"":map.get("sPumpOpenTime").toString()));
					 itemRow.getCell(7).setCellValue(FormatUtils.getStringValue(Common.empty(map.get("sPumpStopTime"))?"":map.get("sPumpStopTime").toString()));
					 itemRow.getCell(8).setCellValue(FormatUtils.getStringValue(Common.empty(map.get("workTime"))?"":map.get("workTime").toString()));
					 itemRow.getCell(9).setCellValue(FormatUtils.getStringValue(Common.empty(map.get("sLeaveTime"))?"":map.get("sLeaveTime").toString()));
					 itemRow.getCell(10).setCellValue(FormatUtils.getStringValue(Common.empty(map.get("berthName"))?"":map.get("berthName").toString()));
					 itemRow.getCell(11).setCellValue(FormatUtils.getStringValue(Common.empty(map.get("shipName"))?"":map.get("shipRefName").toString()+"/"+map.get("shipName").toString()));
					 itemRow.getCell(12).setCellValue(FormatUtils.getStringValue(Common.empty(map.get("productNames"))?"":map.get("productNames").toString()));
					 itemRow.getCell(13).setCellValue(FormatUtils.getStringValue(Common.empty(map.get("count"))?"":map.get("count").toString()));
					 itemRow.getCell(14).setCellValue(FormatUtils.getStringValue(Common.empty(map.get("sShipLenth"))?"":map.get("sShipLenth").toString()));
					 itemRow.getCell(15).setCellValue(FormatUtils.getStringValue(Common.empty(map.get("shipArrivalDraught"))?"":map.get("shipArrivalDraught").toString()));
					 itemRow.getCell(16).setCellValue(FormatUtils.getStringValue(Common.empty(map.get("shipAgentName"))?"":map.get("shipAgentName").toString()));
					 itemRow.getCell(17).setCellValue(FormatUtils.getStringValue(Common.empty(map.get("cargoAgentNames"))?"":map.get("cargoAgentNames").toString()));
					 itemRow.getCell(18).setCellValue(FormatUtils.getStringValue(Common.empty(map.get("port"))?"":map.get("port").toString()));
					 itemRow.getCell(19).setCellValue(FormatUtils.getStringValue(Common.empty(map.get("unloadNotify"))?"":map.get("unloadNotify").toString()));
					 String s1="";
					 if(!Common.empty(map.get("isCustomAgree"))){
						 if(Integer.parseInt(map.get("isCustomAgree").toString())==0){
							 s1="否";
						 }else if(Integer.parseInt(map.get("isCustomAgree").toString())==1){
							 s1="是";
						 }
					 }
					 itemRow.getCell(20).setCellValue(FormatUtils.getStringValue(s1));
					 
					 itemRow.getCell(21).setCellValue(FormatUtils.getStringValue(Common.empty(map.get("note"))?"":map.get("note").toString()));
					 itemRow.getCell(22).setCellValue(FormatUtils.getStringValue(Common.empty(map.get("portNum"))?"":map.get("portNum").toString()));
					 itemRow.getCell(23).setCellValue(FormatUtils.getStringValue(Common.empty(map.get("shipInfo"))?"":map.get("shipInfo").toString()));
					 itemRow.getCell(24).setCellValue(FormatUtils.getStringValue(Common.empty(map.get("clientNames"))?"":map.get("clientNames").toString()));
					 itemRow.getCell(25).setCellValue(FormatUtils.getStringValue(Common.empty(map.get("report"))?"":map.get("report").toString()));
					 itemRow.getCell(26).setCellValue(FormatUtils.getStringValue(Common.empty(map.get("sLastLeaveTime"))?"":map.get("sLastLeaveTime").toString()));
					 itemRow.getCell(27).setCellValue(FormatUtils.getStringValue(Common.empty(map.get("sTearPipeTime"))?"":map.get("sTearPipeTime").toString()));
					 itemRow.getCell(28).setCellValue(FormatUtils.getStringValue(Common.empty(map.get("repatriateTime"))?"":map.get("repatriateTime").toString()));
					 itemRow.getCell(29).setCellValue(FormatUtils.getStringValue(Common.empty(map.get("overTime"))?"":map.get("overTime").toString()));
					 
					 
					 
					 
					}
					
					
					
					
//					itemRow=sheet.createRow(2+size);
//					itemRow.setHeight(sheet.getRow(2).getHeight());
//					for(int j=0;j<10;j++){
//						itemRow.createCell(j).setCellStyle(sheet.getRow(3).getCell(j).getCellStyle());
//					}
					
//					 itemRow.getCell(0).setCellValue(FormatUtils.getStringValue("统计时间:"));//序号
//						itemRow.getCell(1).setCellValue(statisticsDto.getsStartTime()+" 到 "+statisticsDto.getsEndTime());  
				 }
				 
//    				if (region.size()>region1.size()){
//    						region2=region;
//    					}else{
//    						region2=region1;
//    					}
//    				 
				 sheet.setForceFormulaRecalculation(true);
    			}
    		});
    		
//    		return excelUtil.getWorkbook();
    	
    	
        	
        	
        
        	
        
			
			
			
			
			
			
			
//			
//			
//			String[] str={"长江口时间","太仓锚地时间","NOR发出时间","预计靠泊日期","预计靠泊时间","预计开泵时间","预计停泵时间","预计作业时间(小时)","预计离港时间","泊位","船名","物料名称","数量(吨)","船长(米)","吃水(米)","船舶代理","货物代理","港序","接卸通知单","海关是否同意卸货","备注","本年度航次","船舶信息表","货主","申报","允许最大在港时间(以卸货速度150t/h计算)","拆管时间","速遣时间(小时)","超期时间(小时)"};
//			
//			
//			List<List<Object>> data=new ArrayList<List<Object>>();
//			
//			for(int i=0;i<msg.getData().size();i++){
//				Map<String,Object> map=(Map<String, Object>) msg.getData().get(i);
//				List<Object> d=new ArrayList<Object>();
//				d.add(Common.empty(map.get("sCjTime"))?"":map.get("sCjTime").toString());
//				d.add(Common.empty(map.get("sTcTime"))?"":map.get("sTcTime").toString());
//				d.add(Common.empty(map.get("sNorTime"))?"":map.get("sNorTime").toString());
//				String sa="";
//				if(!Common.empty(map.get("sAnchorTime"))){
//					sa=map.get("sAnchorTime").toString().split(" ")[0];
//				}
//				d.add(sa);
//				
//				String sa1="";
//				if(!Common.empty(map.get("sAnchorTime"))){
//					sa1=map.get("sAnchorTime").toString().split(" ")[1];
//				}
//				d.add(sa1);
//				d.add(Common.empty(map.get("sPumpOpenTime"))?"":map.get("sPumpOpenTime").toString());
//				d.add(Common.empty(map.get("sPumpStopTime"))?"":map.get("sPumpStopTime").toString());
//				d.add(Common.empty(map.get("workTime"))?"":map.get("workTime").toString());
//				d.add(Common.empty(map.get("sLeaveTime"))?"":map.get("sLeaveTime").toString());
//				d.add(Common.empty(map.get("berthName"))?"":map.get("berthName").toString());
//				d.add(Common.empty(map.get("shipName"))?"":map.get("shipRefName").toString()+"/"+map.get("shipName").toString());
//				d.add(Common.empty(map.get("productNames"))?"":map.get("productNames").toString());
//				d.add(Common.empty(map.get("count"))?"":map.get("count").toString());
//				d.add(Common.empty(map.get("sShipLenth"))?"":map.get("sShipLenth").toString());
//				d.add(Common.empty(map.get("shipArrivalDraught"))?"":map.get("shipArrivalDraught").toString());
//				d.add(Common.empty(map.get("shipAgentName"))?"":map.get("shipAgentName").toString());
//				d.add(Common.empty(map.get("cargoAgentNames"))?"":map.get("cargoAgentNames").toString());
//				d.add(Common.empty(map.get("port"))?"":map.get("port").toString());
//				d.add(Common.empty(map.get("unloadNotify"))?"":map.get("unloadNotify").toString());
//				String s1="";
//				if(!Common.empty(map.get("isCustomAgree"))){
//					if(Integer.parseInt(map.get("isCustomAgree").toString())==0){
//						s1="否";
//					}else if(Integer.parseInt(map.get("isCustomAgree").toString())==1){
//						s1="是";
//					}
//				}
//				d.add(s1);
//				
//				d.add(Common.empty(map.get("note"))?"":map.get("note").toString());
//				d.add(Common.empty(map.get("portNum"))?"":map.get("portNum").toString());
//				d.add(Common.empty(map.get("shipInfo"))?"":map.get("shipInfo").toString());
//				d.add(Common.empty(map.get("clientNames"))?"":map.get("clientNames").toString());
//				d.add(Common.empty(map.get("report"))?"":map.get("report").toString());
//				d.add(Common.empty(map.get("sLastLeaveTime"))?"":map.get("sLastLeaveTime").toString());
//				d.add(Common.empty(map.get("sTearPipeTime"))?"":map.get("sTearPipeTime").toString());
//				d.add(Common.empty(map.get("repatriateTime"))?"":map.get("repatriateTime").toString());
//				d.add(Common.empty(map.get("overTime"))?"":map.get("overTime").toString());
//				
//				data.add(d);
//			}
			
//			HSSFWorkbook workbook=Common.getExcel("船期预报", str, data,new int[]{0,0,0,0,0,0,0,1,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
			HSSFWorkbook workbook=excelUtil.getWorkbook();
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
