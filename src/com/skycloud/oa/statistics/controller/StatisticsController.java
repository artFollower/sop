package com.skycloud.oa.statistics.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.model.Goods;
import com.skycloud.oa.inbound.service.CargoGoodsService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.model.EveryDayStatics;
import com.skycloud.oa.statistics.dto.StatisticsDto;
import com.skycloud.oa.statistics.service.StatisticsService;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.ExcelOutputUtil;
import com.skycloud.oa.utils.ExcelOutputUtil.CallBack;
import com.skycloud.oa.utils.HibernateUtils;
import com.skycloud.oa.utils.OaMsg;


@Controller
@RequestMapping("/statistics")
public class StatisticsController {
	
	@Autowired
    private StatisticsService statisticsService;
	@Autowired
	private CargoGoodsService cargoService;

	
	/**
	 * 货批汇总列表
	 * @Title cargoList
	 * @Descrption:TODO
	 * @param:@throws OAException
	 * @return:Object
	 * @auhor jiahy
	 * @date 2017年7月10日下午2:22:13
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/cargoList")
	public Object cargoList(@ModelAttribute StatisticsDto statisticsDto,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException
	{
		OaMsg msg = new OaMsg();
		statisticsService.cargoList(statisticsDto,new PageView(pagesize, page),msg);
		return msg;
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/getCargoListTotal")
	public Object getCargoListTotal(@ModelAttribute StatisticsDto statisticsDto ) throws OAException
	{
		OaMsg msg = new OaMsg();
		statisticsService.getCargoListTotal(statisticsDto,msg);
		return msg;
		
	}
	@RequestMapping(value = "/exportCargoListExcel")
	public void exportMonthStorage(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute final StatisticsDto sDto) {
		ExcelOutputUtil.handleExcelOutput(request, response, new CallBack(){

			@Override
			public HSSFWorkbook getWorkBook(HttpServletRequest request) {
				if(sDto.getType()==1){
					
					return statisticsService.exportCargoListExcel(request,sDto);
				}else{
					return statisticsService.exportCargoListExcel2(request,sDto);
				}
			}
		});
	}
	
	
	/**
	 * 查询货批统计
	 * @author yanyufeng
	 * @param messageDto
	 * @param pagesize
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getcargo")
	public Object getcargo(
			@ModelAttribute StatisticsDto statisticsDto,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page)throws OAException {
		PageView pageView = new PageView(pagesize, page);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
			if(!Common.empty(statisticsDto.getsStartTime())){
				long startTime;
				try {
					startTime = sdf.parse(statisticsDto.getsStartTime()).getTime() / 1000;
					statisticsDto.setStartTime(startTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(!Common.empty(statisticsDto.getsEndTime())){
				long endTime;
				try {
					endTime = sdf.parse(statisticsDto.getsEndTime()).getTime() / 1000;
					statisticsDto.setEndTime(endTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		OaMsg msg=statisticsService.getCargo(statisticsDto, pageView);
		return msg;
	}
	
	 
	/**
	 * 查询货批统计
	 * @author yanyufeng
	 * @param messageDto
	 * @param pagesize
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getcargototal")
	public Object getcargototal(
			@ModelAttribute StatisticsDto statisticsDto) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
			if(!Common.empty(statisticsDto.getsStartTime())){
				long startTime;
				try {
					startTime = sdf.parse(statisticsDto.getsStartTime()).getTime() / 1000;
					statisticsDto.setStartTime(startTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			

			if(!Common.empty(statisticsDto.getsEndTime())){
				long endTime;
				try {
					endTime = sdf.parse(statisticsDto.getsEndTime()).getTime() / 1000;
					statisticsDto.setEndTime(endTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		OaMsg msg=new OaMsg();
		try {
			msg = statisticsService.getCargoTotal(statisticsDto);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}
	
	
	
	/**
	 * 查询货批统计
	 * @author yanyufeng
	 * @param messageDto
	 * @param pagesize
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getgoods")
	public Object getgoods(
			@ModelAttribute StatisticsDto statisticsDto,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page)throws OAException {
		PageView pageView = new PageView(pagesize, page);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
			if(!Common.empty(statisticsDto.getsStartTime())){
				long startTime;
				try {
					startTime = sdf.parse(statisticsDto.getsStartTime()).getTime() / 1000;
					statisticsDto.setStartTime(startTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(!Common.empty(statisticsDto.getsEndTime())){
				long endTime;
				try {
					endTime = sdf.parse(statisticsDto.getsEndTime()).getTime() / 1000;
					statisticsDto.setEndTime(endTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		OaMsg msg=statisticsService.getGoods(statisticsDto, pageView,true);
		return msg;
	}
	
	
	
	/**
	 * 查询货批统计
	 * @author yanyufeng
	 * @param messageDto
	 * @param pagesize
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getgoodstotal")
	public Object getgoodstotal(
			@ModelAttribute StatisticsDto statisticsDto) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
			if(!Common.empty(statisticsDto.getsStartTime())){
				long startTime;
				try {
					startTime = sdf.parse(statisticsDto.getsStartTime()).getTime() / 1000;
					statisticsDto.setStartTime(startTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(!Common.empty(statisticsDto.getsEndTime())){
				long endTime;
				try {
					endTime = sdf.parse(statisticsDto.getsEndTime()).getTime() / 1000;
					statisticsDto.setEndTime(endTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			
			
		OaMsg msg=new OaMsg();
		try {
			msg=statisticsService.getGoods(statisticsDto, new PageView(0,0),false);
			
			List<Object> data=msg.getData();
			
			double goodsTotal=0;
			double goodsToday=0;
			for(int i = 0 ; i<data.size();i++){
				Map<String,Object> map=(Map<String, Object>) data.get(i);
				
				Double t19 = Common.fixDouble( Double.valueOf(map.get("goodsSurplus")+""),3);  
				goodsTotal+=Double.parseDouble(t19.toString());
				
				
				Double t17 = Common.fixDouble( Double.valueOf(map.get("goodsToday")+""),3);  
                goodsToday+=Double.parseDouble(t17.toString());
				
				
				
			}
			
			msg.getMap().put("goodsTotal", Common.fixDouble(goodsTotal,3)+"");
			msg.getMap().put("goodsToday", Common.fixDouble(goodsToday,3)+"");
//			
//			msg = statisticsService.getGoodsTotal(statisticsDto);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}
	
	
	
	/**
	 * 海关放行
	 * @author yanyufeng
	 * @param oldGoodsId  原货体id
	 * @param goods  新货体数据（海关放行编号，货体量）
	 * @param type  1 提单里的货体  0 原始货体
	 * @return
	 * 2015-4-10 下午1:28:58
	 */
	@ResponseBody
	@RequestMapping(value = "/custompass")
	public Object custompass(int oldGoodsId,@ModelAttribute Goods goods,int type){
		OaMsg oaMsg = new OaMsg();
		try {
			if(type==1){
				oaMsg = statisticsService.sqlitPassGoods(oldGoodsId, goods);
				
			}else{
				oaMsg = cargoService.sqlitOriginalGoods(oldGoodsId+"", goods);
			}
			Session session = HibernateUtils.getCurrentSession();
			if (session != null) {
				session.getTransaction().commit();
			}
			if(oaMsg.getCode().equals("0000")){
				if(!Common.empty(oaMsg.getMap().get("ladingId"))&&!"0".equals(oaMsg.getMap().get("ladingId"))){
					session.beginTransaction();
					return cargoService.updateGoodsCode(Integer.parseInt(oaMsg.getMap().get("ladingId").toString()));
				}
				else{
					session.beginTransaction();
					return cargoService.updateGoodsCode(Integer.parseInt(oaMsg.getMap().get("cargoId").toString()), "");
				}
			}
			else{
				oaMsg.setCode(Constant.SYS_CODE_NULL_ERR);
				return oaMsg;
			}
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return oaMsg;
		
	}
	
	
	

	@RequestMapping(value = "exportGoodsExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,@ModelAttribute StatisticsDto statisticsDto,String name) 
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
			
			
			
			if(!Common.empty(statisticsDto.getsStartTime())){
				long startTime;
				try {
					startTime = sdf.parse(statisticsDto.getsStartTime()).getTime() / 1000;
					statisticsDto.setStartTime(startTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(!Common.empty(statisticsDto.getsEndTime())){
				long endTime;
				try {
					endTime = sdf.parse(statisticsDto.getsEndTime()).getTime() / 1000;
					statisticsDto.setEndTime(endTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			OaMsg msg=statisticsService.getGoods(statisticsDto, new PageView(0,0),false);
			HSSFWorkbook workbook=statisticsService.exportGoodsExcel(msg,statisticsDto.getType());
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
	
	
	
	@RequestMapping(value = "exportCargoExcel")
	public void exportCargoExcel(HttpServletRequest request, HttpServletResponse response,@ModelAttribute StatisticsDto statisticsDto,String name,int type) 
	{
		response.setContentType("application/vnd.ms-excel");
		OutputStream fOut = null;
		try 
		{
			
			// 产生工作簿对象
//			HSSFWorkbook workbook = invoiceService.exportExcel(request);
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			// 进行转码，使其支持中文文件名 type 1-10 是出库用 20开始入库用
//			response.setHeader("content-disposition", "attachment;filename=" + new String(name.getBytes("gb2312"), "ISO8859-1" ) + ".xls");
			response.setHeader("content-disposition", "attachment;filename=" + sdf.format(new Date()) + ".xls");
			if(!Common.empty(statisticsDto.getsStartTime())){
				long startTime;
				try {
					startTime = sdf.parse(statisticsDto.getsStartTime()).getTime() / 1000;
					statisticsDto.setStartTime(startTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(!Common.empty(statisticsDto.getsEndTime())){
				long endTime;
				try {
					endTime = sdf.parse(statisticsDto.getsEndTime()).getTime() / 1000;
					statisticsDto.setEndTime(endTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		OaMsg msg=statisticsService.getCargo(statisticsDto,  new PageView(0,0));
			
			
			HSSFWorkbook workbook=statisticsService.exportCargoExcel(msg,type,request,statisticsDto);
			fOut = response.getOutputStream();
			workbook.write(fOut);
			
			
		} 
		catch (Exception e) 
		{
		
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
	 * 分类查询日志
	 * @author yanyufeng
	 * @param statisticsDto
	 * @param pagesize
	 * @param page
	 * @return
	 * @throws OAException
	 * 2015-12-21 上午10:35:22
	 */
	@ResponseBody
	@RequestMapping(value = "/getLog")
	public Object getLog(
			@ModelAttribute StatisticsDto statisticsDto,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page)throws OAException {
		PageView pageView = new PageView(pagesize, page);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
			if(!Common.empty(statisticsDto.getsStartTime())){
				long startTime;
				try {
					startTime = sdf.parse(statisticsDto.getsStartTime()).getTime() / 1000;
					statisticsDto.setStartTime(startTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(!Common.empty(statisticsDto.getsEndTime())){
				long endTime;
				try {
					endTime = sdf.parse(statisticsDto.getsEndTime()).getTime() / 1000;
					statisticsDto.setEndTime(endTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		OaMsg msg=statisticsService.getLog(statisticsDto, pageView);
		return msg;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/getPassCargo")
	public Object getPassCargo(
			@ModelAttribute StatisticsDto statisticsDto,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page)throws OAException {
		PageView pageView = new PageView(pagesize, page);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
			if(!Common.empty(statisticsDto.getsStartTime())){
				long startTime;
				try {
					startTime = sdf.parse(statisticsDto.getsStartTime()).getTime() / 1000;
					statisticsDto.setStartTime(startTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(!Common.empty(statisticsDto.getsEndTime())){
				long endTime;
				try {
					endTime = sdf.parse(statisticsDto.getsEndTime()).getTime() / 1000;
					statisticsDto.setEndTime(endTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		OaMsg msg=statisticsService.getPassCargo(statisticsDto, pageView);
		return msg;
	}
	
	/**
	 * 查询货批统计
	 * @author yanyufeng
	 * @param messageDto
	 * @param pagesize
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getEasyInOutLog")
	public Object getEasyInOutLog(
			@ModelAttribute StatisticsDto statisticsDto)throws OAException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
			if(!Common.empty(statisticsDto.getsStartTime())){
				long startTime;
				try {
					startTime = sdf.parse(statisticsDto.getsStartTime()).getTime() / 1000;
					statisticsDto.setStartTime(startTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(!Common.empty(statisticsDto.getsEndTime())){
				long endTime;
				try {
					endTime = sdf.parse(statisticsDto.getsEndTime()).getTime() / 1000;
					statisticsDto.setEndTime(endTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		OaMsg msg=statisticsService.getEasyLog(statisticsDto);
		return msg;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getPassTotal")
	public Object getPassTotal(
			@ModelAttribute StatisticsDto statisticsDto) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(!Common.empty(statisticsDto.getsStartTime())){
			long startTime;
			try {
				startTime = sdf.parse(statisticsDto.getsStartTime()).getTime() / 1000;
				statisticsDto.setStartTime(startTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(!Common.empty(statisticsDto.getsEndTime())){
			long endTime;
			try {  
				endTime = sdf.parse(statisticsDto.getsEndTime()).getTime() / 1000;
				statisticsDto.setEndTime(endTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		OaMsg msg=new OaMsg();
		try {
			msg = statisticsService.getPassTotal(statisticsDto);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/getlogTotal")
	public Object getlogTotal(
			@ModelAttribute StatisticsDto statisticsDto) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(!Common.empty(statisticsDto.getsStartTime())){
			long startTime;
			try {
				startTime = sdf.parse(statisticsDto.getsStartTime()).getTime() / 1000;
				statisticsDto.setStartTime(startTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(!Common.empty(statisticsDto.getsEndTime())){
			long endTime;
			try {  
				endTime = sdf.parse(statisticsDto.getsEndTime()).getTime() / 1000;
				statisticsDto.setEndTime(endTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		OaMsg msg=new OaMsg();
		try {
			msg = statisticsService.getLogTotal(statisticsDto);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}
	
	@RequestMapping(value = "exportLogExcel")
	public void exportLogExcel(HttpServletRequest request, HttpServletResponse response,@ModelAttribute StatisticsDto statisticsDto,String name,int showType) 
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
			
			
			
			if(!Common.empty(statisticsDto.getsStartTime())){
				long startTime;
				try {
					startTime = sdf.parse(statisticsDto.getsStartTime()).getTime() / 1000;
					statisticsDto.setStartTime(startTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(!Common.empty(statisticsDto.getsEndTime())){
				long endTime;
				try {
					endTime = sdf.parse(statisticsDto.getsEndTime()).getTime() / 1000;
					statisticsDto.setEndTime(endTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			OaMsg msg=new OaMsg();
			
			if(showType==3){
				msg=statisticsService.getEasyLog(statisticsDto);
				
			}else if (showType==5){
				msg=statisticsService.getPassCargo(statisticsDto,  new PageView(0,0));
			}else{
				
				msg=statisticsService.getLog(statisticsDto, new PageView(0,0));
			}
			HSSFWorkbook workbook=statisticsService.exportLogExcel(request,msg,showType,statisticsDto);
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
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "/getDayStatics")
	public Object getDayStatics(
			@ModelAttribute EveryDayStatics everyDayStatics) {
		OaMsg msg=new OaMsg();
		try {
			msg = statisticsService.getEveryDayStatics(everyDayStatics);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}
	
	
}
