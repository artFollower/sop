/**
 * 
 * @Project:sop
 * @Title:ExportServiceImpl.java
 * @Package:com.skycloud.oa.report.service.impl
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年11月4日 下午4:12:16
 * @Version:SkyCloud版权所有
 */
package com.skycloud.oa.report.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.service.OperationLogService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.report.dao.ExportDao;
import com.skycloud.oa.report.dao.ReportDao;
import com.skycloud.oa.report.dao.ThroughtMsgDao;
import com.skycloud.oa.report.dto.ExportDto;
import com.skycloud.oa.report.dto.ReportDto;
import com.skycloud.oa.report.service.ExportService;
import com.skycloud.oa.report.service.ReportDataService;
import com.skycloud.oa.report.utils.DateUtils;
import com.skycloud.oa.report.utils.FormatUtils;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.ExcelUtil;
import com.skycloud.oa.utils.ExcelUtil.CallBack;

@Service
public class ExportServiceImpl implements ExportService 
{
	@Autowired
	private ReportDao reportDao;
	@Autowired
	private ExportDao exportDao;
	@Autowired
	private ReportDataService reportDataService;
	@Autowired
	private OperationLogService operationLogService;
	@Autowired
	private ThroughtMsgDao throughtMsgDao;
	/**
	 * 月仓储入库明细列表导出Excel
	 * @Title:exportExcel
	 * @Description:
	 * @param request
	 * @param type
	 * @param params
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public HSSFWorkbook exportMonthStorage(HttpServletRequest request, String type,String params,final ReportDto report) throws OAException 
	{
		return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/monthStorage.xls", new CallBack() {
			@Override
			public void setSheetValue(HSSFSheet sheet) 
			{
				try 
				{
					sheet.setForceFormulaRecalculation(true);
					report.setModule(1);
					List<Map<String,Object>> data = reportDao.list(report,new PageView(0, 0));
					HSSFRow itemRow;
					int item;
					Map<String,Object> map;
					if(data != null &&data.size()> 0){
						int size=data.size();
						for(int i=0;i<size;i++){
							   item=i+2;
								itemRow=sheet.createRow(item);
								itemRow.setHeight(sheet.getRow(1).getHeight());
								for(int m=0;m<8;m++)
								{
									itemRow.createCell(m);
									itemRow.getCell(m).setCellStyle(sheet.getRow(1).getCell(m).getCellStyle());
								}
							 map= (Map<String, Object>) data.get(i);
							 itemRow.getCell(0).setCellValue(FormatUtils.getDoubleValue(i+1));
							 itemRow.getCell(1).setCellValue(FormatUtils.getStringValue(map.get("arrivalTime")));
							 itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(map.get("shipName")));
							 itemRow.getCell(3).setCellValue(FormatUtils.getStringValue(map.get("shipRefName")));
							 itemRow.getCell(4).setCellValue(FormatUtils.getStringValue(map.get("productName")));
							 itemRow.getCell(5).setCellValue(FormatUtils.getStringValue(map.get("berthName")));
							 itemRow.getCell(6).setCellValue(FormatUtils.getStringValue(map.get("tankName")));
							 itemRow.getCell(7).setCellValue(FormatUtils.getDoubleValue(map.get("goodsTotal")));
						}
					}
				} 
				catch (OAException e) 
				{
					e.printStackTrace();
				}
			}
		}).getWorkbook();
	}

	/**
	 * 货物进出港通过量统计导出到Excel
	 * @Title:exportInOut
	 * @Description:
	 * @param request
	 * @param type
	 * @param params
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public HSSFWorkbook exportInOut(HttpServletRequest request,final ReportDto report) throws OAException 
	{
		return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/stockPort.xls", new CallBack() {
			@Override
			public void setSheetValue(HSSFSheet sheet){
				try{
					String title;
					String startTime = report.getStartTime();
					title=startTime.substring(0,4)+"年"+startTime.substring(5,7)+"月份长江石化码头货物进出港通过量统计";
					sheet.getRow(0).getCell(0).setCellValue(title);
					sheet.getRow(1).getCell(0).setCellValue(report.getClientName()+":");
					report.setModule(2);
					List<Map<String,Object>> data = reportDao.list(report, null);
					
					if(data != null && data.size()> 0)
					{
						sheet.shiftRows(4,5,data.size()-1,true,false);
						Map<String,Object> map;
						int item=0;
						for(int i=0,len=data.size();i<len;i++)
						{
							 item=i+3;
							if(i!=0 && i!=data.size())
							{
								HSSFRow itemRow=sheet.createRow(item);
								itemRow.setHeight(sheet.getRow(3).getHeight());
								for(int m=0;m<7;m++)
								{
									itemRow.createCell(m);
									itemRow.getCell(m).setCellStyle(sheet.getRow(3).getCell(m).getCellStyle());
								}
							}
							map= (Map<String, Object>) data.get(i);
							sheet.getRow(item).getCell(0).setCellValue(FormatUtils.getDoubleValue(i+1));
							sheet.getRow(item).getCell(1).setCellValue(FormatUtils.getStringValue(map.get("arrivalTime")));
							sheet.getRow(item).getCell(2).setCellValue(FormatUtils.getStringValue(map.get("shipName")));
							sheet.getRow(item).getCell(3).setCellValue(FormatUtils.getStringValue(map.get("berthName")));
							sheet.getRow(item).getCell(4).setCellValue(FormatUtils.getDoubleValue(map.get("goodsTotal")));
							sheet.getRow(item).getCell(5).setCellValue(FormatUtils.getStringValue(map.get("arrivalType")));
							sheet.getRow(item).getCell(6).setCellValue(FormatUtils.getStringValue(map.get("description")));
						}
						sheet.getRow(item+1).getCell(4).setCellType(HSSFCell.CELL_TYPE_FORMULA);
						sheet.getRow(item+1).getCell(4).setCellFormula("SUM(E4:E"+(item+1)+")");
					}
					sheet.setForceFormulaRecalculation(true);
				} 
				catch (OAException e) 
				{
					e.printStackTrace();
				}
			}
		}).getWorkbook();
	}

	/**
	 * 储罐周转率导出到Excel
	 * @Title:exportTankTurnRate
	 * @Description:
	 * @param request
	 * @param type
	 * @param params
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public HSSFWorkbook exportTankTurnRate(HttpServletRequest request,final ExportDto export) throws OAException 
	{
		return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/twoTankRate.xls", new CallBack() {
			@Override
			public void setSheetValue(HSSFSheet sheet) 
			{
				try{
				sheet.setForceFormulaRecalculation(true);
				String startTime = export.getStartTime();
				String endTime=export.getEndTime();
				String year=startTime.substring(0,4);
				int month=Integer.valueOf(startTime.substring(5,7));
				String startTimeOfYear=startTime.substring(0, 4)+"-01-01";
				sheet.getRow(0).getCell(0).setCellValue(startTime.substring(0, 4)+"年"+startTime.substring(5,7)+"月份储罐周转率");
				Map<String,Object> data,maxNumData;
				Double accRate=0D;
				//甲醇保税非包罐（不含1241 Methanex Asia Pacific Ltd，1627  SAUDI BASIC INDUSTRIES CORPORATION ）
				export.setProductId(4);
				export.setNoClientId("1241,1627");
				export.setYesClientId(null);
				export.setTaxTypes("3"); // 1内贸 2外贸 3 保税
				export.setTaxType(2);
				export.setStorageType(2);
				data=exportDao.findTurnRate(export);
				maxNumData=exportDao.getMaxNumOfTank(export);
				sheet.getRow(2).getCell(2).setCellValue(FormatUtils.getDoubleValue(maxNumData.get("tankNum")));
				sheet.getRow(2).getCell(3).setCellValue(FormatUtils.getDoubleValue(data.get("totalAmount")));
				//累计周转率
				accRate=Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2);
				export.setStartTime(startTimeOfYear);
				data=exportDao.findTurnRate(export);
				sheet.getRow(2).getCell(4).setCellValue(FormatUtils.getDoubleValue(data.get("totalAmount")));
				for(int i=1;i<month;i++){
					export.setStartTime(year+"-"+(i<10?("0"+i):i)+"-01");
					export.setEndTime(DateUtils.getLastDayOfMonth(year+"-"+(i<10?("0"+i):i)));
					data=exportDao.findTurnRate(export);
					maxNumData=exportDao.getMaxNumOfTank(export);
					accRate=Common.add(accRate, Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2), 2);
				}
				sheet.getRow(2).getCell(6).setCellValue(accRate);
				export.setEndTime(endTime);
				
				//甲醇外贸
				export.setStartTime(startTime);
				export.setTaxTypes("2");
				export.setTaxType(1);
				export.setStorageType(null);
				data=exportDao.findTurnRate(export);
				maxNumData=exportDao.getMaxNumOfTank(export);
				sheet.getRow(3).getCell(2).setCellValue(FormatUtils.getDoubleValue(maxNumData.get("tankNum")));
				sheet.getRow(3).getCell(3).setCellValue(FormatUtils.getDoubleValue(data.get("totalAmount")));
				accRate=Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2);
				export.setStartTime(startTimeOfYear);
				data=exportDao.findTurnRate(export);
				sheet.getRow(3).getCell(4).setCellValue(FormatUtils.getDoubleValue(data.get("totalAmount")));
				for(int i=1;i<month;i++){
					export.setStartTime(year+"-"+(i<10?("0"+i):i)+"-01");
					export.setEndTime(DateUtils.getLastDayOfMonth(year+"-"+(i<10?("0"+i):i)));
					data=exportDao.findTurnRate(export);
					maxNumData=exportDao.getMaxNumOfTank(export);
					accRate=Common.add(accRate, Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2), 2);
				}
				sheet.getRow(3).getCell(6).setCellValue(accRate);
				export.setEndTime(endTime);
				
				//甲醇内贸
				export.setStartTime(startTime);
				export.setTaxTypes("1");//甲醇非保税是外贸+内贸
				export.setTaxType(0);
				export.setStorageType(null);
				data=exportDao.findTurnRate(export);
				maxNumData=exportDao.getMaxNumOfTank(export);
				sheet.getRow(4).getCell(2).setCellValue(FormatUtils.getDoubleValue(maxNumData.get("tankNum")));
				sheet.getRow(4).getCell(3).setCellValue(FormatUtils.getDoubleValue(data.get("totalAmount")));
				accRate=Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2);
				export.setStartTime(startTimeOfYear);
				data=exportDao.findTurnRate(export);
				sheet.getRow(4).getCell(4).setCellValue(FormatUtils.getDoubleValue(data.get("totalAmount")));
				for(int i=1;i<month;i++){
					export.setStartTime(year+"-"+(i<10?("0"+i):i)+"-01");
					export.setEndTime(DateUtils.getLastDayOfMonth(year+"-"+(i<10?("0"+i):i)));
					data=exportDao.findTurnRate(export);
					maxNumData=exportDao.getMaxNumOfTank(export);
					accRate=Common.add(accRate, Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2), 2);
				}
				sheet.getRow(4).getCell(6).setCellValue(accRate);
				export.setEndTime(endTime);
				
				//甲醇保税包罐
				export.setStartTime(startTime);
				export.setNoClientId(null);
				export.setYesClientId(null);
				export.setTaxTypes("3");	
				export.setTaxType(3);
				export.setStorageType(3);
				data=exportDao.findTurnRate(export);
				maxNumData=exportDao.getMaxNumOfTank(export);
				sheet.getRow(5).getCell(2).setCellValue(FormatUtils.getDoubleValue(maxNumData.get("tankNum")));
				sheet.getRow(5).getCell(3).setCellValue(FormatUtils.getDoubleValue(data.get("totalAmount")));
				accRate=Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2);
				export.setStartTime(startTimeOfYear);
				data=exportDao.findTurnRate(export);
				sheet.getRow(5).getCell(4).setCellValue(FormatUtils.getDoubleValue(data.get("totalAmount")));
				for(int i=1;i<month;i++){
					export.setStartTime(year+"-"+(i<10?("0"+i):i)+"-01");
					export.setEndTime(DateUtils.getLastDayOfMonth(year+"-"+(i<10?("0"+i):i)));
					data=exportDao.findTurnRate(export);
					maxNumData=exportDao.getMaxNumOfTank(export);
					accRate=Common.add(accRate, Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2), 2);
				}
				sheet.getRow(5).getCell(6).setCellValue(accRate);
				export.setEndTime(endTime);
				
				//乙二醇保税非包罐
				export.setStartTime(startTime);
				export.setProductId(2);
				export.setNoClientId("1241,1627");
				export.setYesClientId(null);
				export.setStorageType(2);
				export.setTaxTypes("3"); // 1内贸 2外贸 3 保税
				export.setTaxType(2);
				data=exportDao.findTurnRate(export);
				maxNumData=exportDao.getMaxNumOfTank(export);
				sheet.getRow(6).getCell(2).setCellValue(FormatUtils.getDoubleValue(maxNumData.get("tankNum")));
				sheet.getRow(6).getCell(3).setCellValue(FormatUtils.getDoubleValue(data.get("totalAmount")));
				accRate=Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2);
				export.setStartTime(startTimeOfYear);
				data=exportDao.findTurnRate(export);
				sheet.getRow(6).getCell(4).setCellValue(FormatUtils.getDoubleValue(data.get("totalAmount")));
				for(int i=1;i<month;i++){
					export.setStartTime(year+"-"+(i<10?("0"+i):i)+"-01");
					export.setEndTime(DateUtils.getLastDayOfMonth(year+"-"+(i<10?("0"+i):i)));
					data=exportDao.findTurnRate(export);
					maxNumData=exportDao.getMaxNumOfTank(export);
					accRate=Common.add(accRate, Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2), 2);
				}
				sheet.getRow(6).getCell(6).setCellValue(accRate);
				export.setEndTime(endTime);
				
				//乙二醇外贸
				export.setStartTime(startTime);
				export.setTaxTypes("2");
				export.setTaxType(1);
				export.setStorageType(null);
				data=exportDao.findTurnRate(export);
				maxNumData=exportDao.getMaxNumOfTank(export);
				sheet.getRow(7).getCell(2).setCellValue(FormatUtils.getDoubleValue(maxNumData.get("tankNum")));
				sheet.getRow(7).getCell(3).setCellValue(FormatUtils.getDoubleValue(data.get("totalAmount")));
				accRate=Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2);
				export.setStartTime(startTimeOfYear);
				data=exportDao.findTurnRate(export);
				sheet.getRow(7).getCell(4).setCellValue(FormatUtils.getDoubleValue(data.get("totalAmount")));
				for(int i=1;i<month;i++){
					export.setStartTime(year+"-"+(i<10?("0"+i):i)+"-01");
					export.setEndTime(DateUtils.getLastDayOfMonth(year+"-"+(i<10?("0"+i):i)));
					data=exportDao.findTurnRate(export);
					maxNumData=exportDao.getMaxNumOfTank(export);
					accRate=Common.add(accRate, Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2), 2);
				}
				sheet.getRow(7).getCell(6).setCellValue(accRate);
				export.setEndTime(endTime);
				
				//乙二醇内贸
				export.setStartTime(startTime);
				export.setTaxTypes("1");
				export.setTaxType(0);
				export.setStorageType(null);
				data=exportDao.findTurnRate(export);
				maxNumData=exportDao.getMaxNumOfTank(export);
				sheet.getRow(8).getCell(2).setCellValue(FormatUtils.getDoubleValue(maxNumData.get("tankNum")));
				sheet.getRow(8).getCell(3).setCellValue(FormatUtils.getDoubleValue(data.get("totalAmount")));
				accRate=Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2);
				export.setStartTime(startTimeOfYear);
				data=exportDao.findTurnRate(export);
				sheet.getRow(8).getCell(4).setCellValue(FormatUtils.getDoubleValue(data.get("totalAmount")));
				for(int i=1;i<month;i++){
					export.setStartTime(year+"-"+(i<10?("0"+i):i)+"-01");
					export.setEndTime(DateUtils.getLastDayOfMonth(year+"-"+(i<10?("0"+i):i)));
					data=exportDao.findTurnRate(export);
					maxNumData=exportDao.getMaxNumOfTank(export);
					accRate=Common.add(accRate, Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2), 2);
				}
				sheet.getRow(8).getCell(6).setCellValue(accRate);
				export.setEndTime(endTime);
				//乙二醇保税包罐
				export.setStartTime(startTime);
				export.setNoClientId(null);
				export.setYesClientId(null);
				export.setStorageType(3);
				export.setTaxTypes("3");
				export.setTaxType(3);
				data=exportDao.findTurnRate(export);
				maxNumData=exportDao.getMaxNumOfTank(export);
				sheet.getRow(9).getCell(2).setCellValue(FormatUtils.getDoubleValue(maxNumData.get("tankNum")));
				sheet.getRow(9).getCell(3).setCellValue(FormatUtils.getDoubleValue(data.get("totalAmount")));
				accRate=Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2);
				export.setStartTime(startTimeOfYear);
				data=exportDao.findTurnRate(export);
				sheet.getRow(9).getCell(4).setCellValue(FormatUtils.getDoubleValue(data.get("totalAmount")));
				for(int i=1;i<month;i++){
					export.setStartTime(year+"-"+(i<10?("0"+i):i)+"-01");
					export.setEndTime(DateUtils.getLastDayOfMonth(year+"-"+(i<10?("0"+i):i)));
					data=exportDao.findTurnRate(export);
					maxNumData=exportDao.getMaxNumOfTank(export);
					accRate=Common.add(accRate, Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2), 2);
				}
				sheet.getRow(9).getCell(6).setCellValue(accRate);
				export.setEndTime(endTime);
				//汽油
				export.setStorageType(null);
				export.setStartTime(startTime);
				export.setProductId(1);
				export.setYesClientId(null);
				export.setTaxTypes(null);
				export.setTaxType(null);
				data=exportDao.findTurnRate(export);
				maxNumData=exportDao.getMaxNumOfTank(export);
				sheet.getRow(10).getCell(2).setCellValue(FormatUtils.getDoubleValue(maxNumData.get("tankNum")));
				sheet.getRow(10).getCell(3).setCellValue(FormatUtils.getDoubleValue(data.get("totalAmount")));
				accRate=Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2);
				export.setStartTime(startTimeOfYear);
				data=exportDao.findTurnRate(export);
				sheet.getRow(10).getCell(4).setCellValue(FormatUtils.getDoubleValue(data.get("totalAmount")));
				for(int i=1;i<month;i++){
					export.setStartTime(year+"-"+(i<10?("0"+i):i)+"-01");
					export.setEndTime(DateUtils.getLastDayOfMonth(year+"-"+(i<10?("0"+i):i)));
					data=exportDao.findTurnRate(export);
					maxNumData=exportDao.getMaxNumOfTank(export);
					accRate=Common.add(accRate, Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2), 2);
				}
				sheet.getRow(10).getCell(6).setCellValue(accRate);
				export.setEndTime(endTime);
				
				//柴油
				export.setStartTime(startTime);
				export.setProductId(3);
				data=exportDao.findTurnRate(export);
				maxNumData=exportDao.getMaxNumOfTank(export);
				sheet.getRow(11).getCell(2).setCellValue(FormatUtils.getDoubleValue(maxNumData.get("tankNum")));
				sheet.getRow(11).getCell(3).setCellValue(FormatUtils.getDoubleValue(data.get("totalAmount")));
				accRate=Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2);
				export.setStartTime(startTimeOfYear);
				data=exportDao.findTurnRate(export);
				sheet.getRow(11).getCell(4).setCellValue(FormatUtils.getDoubleValue(data.get("totalAmount")));
				for(int i=1;i<month;i++){
					export.setStartTime(year+"-"+(i<10?("0"+i):i)+"-01");
					export.setEndTime(DateUtils.getLastDayOfMonth(year+"-"+(i<10?("0"+i):i)));
					data=exportDao.findTurnRate(export);
					maxNumData=exportDao.getMaxNumOfTank(export);
					accRate=Common.add(accRate, Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2), 2);
				}
				sheet.getRow(11).getCell(6).setCellValue(accRate);
				export.setEndTime(endTime);
				
				//苯
				export.setStartTime(startTime);
				export.setProductId(19);
				data=exportDao.findTurnRate(export);
				maxNumData=exportDao.getMaxNumOfTank(export);
				sheet.getRow(12).getCell(2).setCellValue(FormatUtils.getDoubleValue(maxNumData.get("tankNum")));
				sheet.getRow(12).getCell(3).setCellValue(FormatUtils.getDoubleValue(data.get("totalAmount")));
				accRate=Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2);
				export.setStartTime(startTimeOfYear);
				data=exportDao.findTurnRate(export);
				sheet.getRow(12).getCell(4).setCellValue(FormatUtils.getDoubleValue(data.get("totalAmount")));
				for(int i=1;i<month;i++){
					export.setStartTime(year+"-"+(i<10?("0"+i):i)+"-01");
					export.setEndTime(DateUtils.getLastDayOfMonth(year+"-"+(i<10?("0"+i):i)));
					data=exportDao.findTurnRate(export);
					maxNumData=exportDao.getMaxNumOfTank(export);
					accRate=Common.add(accRate, Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2), 2);
				}
				sheet.getRow(12).getCell(6).setCellValue(accRate);
				export.setEndTime(endTime);
				
				//正构烷烃
				export.setStartTime(startTime);
				export.setProductId(17);
				data=exportDao.findTurnRate(export);
				maxNumData=exportDao.getMaxNumOfTank(export);
				sheet.getRow(13).getCell(2).setCellValue(FormatUtils.getDoubleValue(maxNumData.get("tankNum")));
				sheet.getRow(13).getCell(3).setCellValue(FormatUtils.getDoubleValue(data.get("totalAmount")));
				accRate=Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2);
				export.setStartTime(startTimeOfYear);
				data=exportDao.findTurnRate(export);
				sheet.getRow(13).getCell(4).setCellValue(FormatUtils.getDoubleValue(data.get("totalAmount")));
				for(int i=1;i<month;i++){
					export.setStartTime(year+"-"+(i<10?("0"+i):i)+"-01");
					export.setEndTime(DateUtils.getLastDayOfMonth(year+"-"+(i<10?("0"+i):i)));
					data=exportDao.findTurnRate(export);
					maxNumData=exportDao.getMaxNumOfTank(export);
					accRate=Common.add(accRate, Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2), 2);
				}
				sheet.getRow(13).getCell(6).setCellValue(accRate);
				export.setEndTime(endTime);
				
				//直链烷基苯
				export.setStartTime(startTime);
				export.setProductId(18);
				data=exportDao.findTurnRate(export);
				maxNumData=exportDao.getMaxNumOfTank(export);
				sheet.getRow(14).getCell(2).setCellValue(FormatUtils.getDoubleValue(maxNumData.get("tankNum")));
				sheet.getRow(14).getCell(3).setCellValue(FormatUtils.getDoubleValue(data.get("totalAmount")));
				accRate=Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2);
				export.setStartTime(startTimeOfYear);
				data=exportDao.findTurnRate(export);
				sheet.getRow(14).getCell(4).setCellValue(FormatUtils.getDoubleValue(data.get("totalAmount")));
				for(int i=1;i<month;i++){
					export.setStartTime(year+"-"+(i<10?("0"+i):i)+"-01");
					export.setEndTime(DateUtils.getLastDayOfMonth(year+"-"+(i<10?("0"+i):i)));
					data=exportDao.findTurnRate(export);
					maxNumData=exportDao.getMaxNumOfTank(export);
					accRate=Common.add(accRate, Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2), 2);
				}
				sheet.getRow(14).getCell(6).setCellValue(accRate);
				
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}).getWorkbook();
	}

	/**
	 * 码头进出港统计表导出到Excel
	 * @Title:exportDock
	 * @Description:
	 * @param request
	 * @param type
	 * @param params
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public HSSFWorkbook exportDock(HttpServletRequest request, String type,String params,final ExportDto export) throws OAException 
	{
		return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/dockPort.xls", new CallBack() {
			@Override
			public void setSheetValue(HSSFSheet sheet) 
			{
				try{
				sheet.setForceFormulaRecalculation(true);
				String startTime=export.getStartTime();
				String title,total;
				title = startTime.substring(0, 4)+"年"+(startTime.substring(5, 7))+"月份码头进出港统计表";
				total = "01-"+startTime.substring(5, 7)+"月累计";
				sheet.getRow(0).getCell(0).setCellValue(title);
				sheet.getRow(24).getCell(0).setCellValue(total);
				boolean isFirstMonth=startTime.substring(5, 7).equals("01")?true:false;
				int[] chemical = {2,4,19,17,18,1,3,13,15,4,14,14};//化学品
				List<Map<String,Object>> inboundData;//入库数据
				Map<String,Map<String,Object>> inboundDataMap = null;//入库数据泊位
				List<Map<String,Object>> outboundData;//出库数据
				Map<String,Map<String,Object>> outboundDataMap = null;//出库数据泊位
				export.setBerthIds("1,2,3,4,5,6,7");
				export.setTaxType(0);
				int item=0;
				for(int i=0;i<13;i++){
					if(i<12){
						if(i<7){
							export.setIsPass(0);//不是通过
							}else if(i==7||i==8){
								export.setIsPass(1);//是通过
							}else if(i==9){//建滔甲醇
								export.setClientCode("建滔(太仓)化工有限公司");
							}else if(i==10){//BP基础油
								export.setClientCode("碧辟(中国)工业油品有限公司");
							}else if(i==11){//华东基础油
								export.setClientCode("中国石油天然气股份有限公司华东润滑油厂");
							}
							export.setProductId(chemical[i]);
							inboundData=exportDao.getForeignTradeInbound(export);//外贸进港
							outboundData=exportDao.getForeignTradeOutbound(export);//外贸出港
							inboundDataMap=getBerthMap(getModelBerthMap(1), inboundData);
							outboundDataMap=getBerthMap(getModelBerthMap(1), outboundData);
					}else if(i==12&&!isFirstMonth){
						// 进港,出港计划
						inboundData=new ArrayList<Map<String,Object>>();
						outboundData=new ArrayList<Map<String,Object>>();
						export.setIsPass(0);
						export.setClientCode(null);
						export.setProductId(0);
						export.setStartTime(DateUtils.getFirstDayOfYear(export.getStartTime().substring(0, 4)));
						export.setEndTime(DateUtils.getLastDayOfLastMonth(export.getEndTime()));
						inboundData.addAll(exportDao.getForeignTradeInbound(export));
						outboundData.addAll(exportDao.getForeignTradeOutbound(export));
						export.setIsPass(1);
						export.setProductId(13);
						inboundData.addAll(exportDao.getForeignTradeInbound(export));
						outboundData.addAll(exportDao.getForeignTradeOutbound(export));
						export.setProductId(15);
						inboundData.addAll(exportDao.getForeignTradeInbound(export));
						outboundData.addAll(exportDao.getForeignTradeOutbound(export));
						export.setProductId(4);
						export.setClientCode("建滔(太仓)化工有限公司");
						inboundData.addAll(exportDao.getForeignTradeInbound(export));
						outboundData.addAll(exportDao.getForeignTradeOutbound(export));
						export.setProductId(14);
						export.setClientCode("碧辟(中国)工业油品有限公司");
						inboundData.addAll(exportDao.getForeignTradeInbound(export));
						outboundData.addAll(exportDao.getForeignTradeOutbound(export));
						export.setClientCode("中国石油天然气股份有限公司华东润滑油厂");
						inboundData.addAll(exportDao.getForeignTradeInbound(export));
						outboundData.addAll(exportDao.getForeignTradeOutbound(export));
						
						inboundDataMap=getBerthMap(getModelBerthMap(1), inboundData);
						outboundDataMap=getBerthMap(getModelBerthMap(1), outboundData);
					}else if(i==12&&isFirstMonth){
						inboundDataMap=getBerthMap(getModelBerthMap(1), null);
						outboundDataMap=getBerthMap(getModelBerthMap(1), null);
					}
					
					for(int j=0;j<7;j++){
						if(i<5){
							item=8+i;
						}else if(i<7&&i>=5){
							item=9+i;
						}else if(i>=7&&i<12){
							item=10+i;
						}else{
							item=11+i;
						}
						sheet.getRow(item).getCell(1).setCellValue(FormatUtils.getDoubleValue(inboundDataMap.get("1").get("num").toString()));
						sheet.getRow(item).getCell(2).setCellValue(FormatUtils.getDoubleValue(inboundDataMap.get("1").get("count").toString()));
						
						sheet.getRow(item).getCell(3).setCellValue(FormatUtils.getDoubleValue(outboundDataMap.get("1").get("num").toString()));
						sheet.getRow(item).getCell(4).setCellValue(FormatUtils.getDoubleValue(outboundDataMap.get("1").get("count").toString()));
						
						
						sheet.getRow(item).getCell(5).setCellValue(FormatUtils.getDoubleValue(inboundDataMap.get("2").get("num").toString()));
						sheet.getRow(item).getCell(6).setCellValue(FormatUtils.getDoubleValue(inboundDataMap.get("2").get("count").toString()));
						
						sheet.getRow(item).getCell(7).setCellValue(FormatUtils.getDoubleValue(outboundDataMap.get("2").get("num").toString()));
						sheet.getRow(item).getCell(8).setCellValue(FormatUtils.getDoubleValue(outboundDataMap.get("2").get("count").toString()));
						
						sheet.getRow(item).getCell(9).setCellValue(FormatUtils.getDoubleValue(inboundDataMap.get("3").get("num").toString()));
						sheet.getRow(item).getCell(10).setCellValue(FormatUtils.getDoubleValue(inboundDataMap.get("3").get("count").toString()));
						
						sheet.getRow(item).getCell(11).setCellValue(FormatUtils.getDoubleValue(outboundDataMap.get("3").get("num").toString()));
						sheet.getRow(item).getCell(12).setCellValue(FormatUtils.getDoubleValue(outboundDataMap.get("3").get("count").toString()));
						
						
						sheet.getRow(item).getCell(13).setCellValue(FormatUtils.getDoubleValue(inboundDataMap.get("4").get("num").toString()));
						sheet.getRow(item).getCell(14).setCellValue(FormatUtils.getDoubleValue(inboundDataMap.get("4").get("count").toString()));
						
						sheet.getRow(item).getCell(15).setCellValue(FormatUtils.getDoubleValue(outboundDataMap.get("4").get("num").toString()));
						sheet.getRow(item).getCell(16).setCellValue(FormatUtils.getDoubleValue(outboundDataMap.get("4").get("count").toString()));
						
						sheet.getRow(item).getCell(17).setCellValue(FormatUtils.getDoubleValue(inboundDataMap.get("5").get("num").toString()));
						sheet.getRow(item).getCell(18).setCellValue(FormatUtils.getDoubleValue(inboundDataMap.get("5").get("count").toString()));
						
						sheet.getRow(item).getCell(19).setCellValue(FormatUtils.getDoubleValue(outboundDataMap.get("5").get("num").toString()));
						sheet.getRow(item).getCell(20).setCellValue(FormatUtils.getDoubleValue(outboundDataMap.get("5").get("count").toString()));
						
						
						sheet.getRow(item).getCell(21).setCellValue(FormatUtils.getDoubleValue(inboundDataMap.get("6").get("num").toString()));
						sheet.getRow(item).getCell(22).setCellValue(FormatUtils.getDoubleValue(inboundDataMap.get("6").get("count").toString()));
						
						sheet.getRow(item).getCell(23).setCellValue(FormatUtils.getDoubleValue(outboundDataMap.get("6").get("num").toString()));
						sheet.getRow(item).getCell(24).setCellValue(FormatUtils.getDoubleValue(outboundDataMap.get("6").get("count").toString()));
						
						
						sheet.getRow(item).getCell(25).setCellValue(FormatUtils.getDoubleValue(inboundDataMap.get("7").get("num").toString()));
						sheet.getRow(item).getCell(26).setCellValue(FormatUtils.getDoubleValue(inboundDataMap.get("7").get("count").toString()));
						
						sheet.getRow(item).getCell(27).setCellValue(FormatUtils.getDoubleValue(outboundDataMap.get("7").get("num").toString()));
						sheet.getRow(item).getCell(28).setCellValue(FormatUtils.getDoubleValue(outboundDataMap.get("7").get("count").toString()));
					}
					if(i<7)
						sheet.getRow(item).getCell(33).setCellValue(exportDao.getAssemBlyShip(export));
					if(i==12&&!isFirstMonth){
						export.setProductId(0);
						sheet.getRow(i+11).getCell(33).setCellValue(exportDao.getAssemBlyShip(export));
					}
					
				}
				}catch (OAException e) 
				{
					e.printStackTrace();
				}
				
			}
		}).getWorkbook();
	}

	/**
	 * 外贸进出港统计表导出到Excel
	 * @Title:exportTrade
	 * @Description:
	 * @param request
	 * @param type
	 * @param params
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public HSSFWorkbook exportTrade(HttpServletRequest request, String type,String params,final ExportDto export) throws OAException 
	{
		return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/tradePort.xls", new CallBack() {
			@Override // startTime endTime planTank
			public void setSheetValue(HSSFSheet sheet) 
			{   
				
				try{
				boolean isFirstMonth=false;
				String startTime = export.getStartTime();
				String endTime=export.getEndTime();
				sheet.getRow(0).getCell(0).setCellValue(startTime.substring(0, 4)+"年"+(startTime.substring(5, 7))+"月份码头外贸进出港统计表");//标题
				sheet.getRow(19).getCell(0).setCellValue("01-"+startTime.substring(5, 7)+"月累计");//副标题
				if("01".equals(startTime.substring(5, 7))) isFirstMonth=true;
				
				int[] productIds={2,4,19,17,18,13,15,4,14,14};//后三个为通过，前面为正式出入库
				List<Map<String,Object>> inboundData;//入库数据
				Map<String,Map<String,Object>> inboundDataMap = null;//入库数据泊位
				List<Map<String,Object>> outboundData;//出库数据
				Map<String,Map<String,Object>> outboundDataMap = null;//入库数据泊位
				int item=0;
				export.setBerthIds("1,2,6,7");//外贸泊位
				export.setTaxType(1);//外贸
				for(int i=0;i<11;i++){
					if(i<10){
					if(i<5){
					export.setIsPass(0);//不是通过
					}else if(i==5||i==6){
						export.setIsPass(1);//是通过
					}else if(i==7){//建滔甲醇
						export.setClientCode("建滔(太仓)化工有限公司");
					}else if(i==8){//BP基础油
						export.setClientCode("碧辟(中国)工业油品有限公司");
					}else if(i==9){//华东基础油
						export.setClientCode("中国石油天然气股份有限公司华东润滑油厂");
					}
					export.setProductId(productIds[i]);
					inboundData=exportDao.getForeignTradeInbound(export);//外贸进港
					outboundData=exportDao.getForeignTradeOutbound(export);//外贸出港
					inboundDataMap=getBerthMap(getModelBerthMap(0), inboundData);
					outboundDataMap=getBerthMap(getModelBerthMap(0), outboundData);
					
					}else if(!isFirstMonth){//上月累计
						// 进港,出港计划
						inboundData=new ArrayList<Map<String,Object>>();
						outboundData=new ArrayList<Map<String,Object>>();
						export.setIsPass(0);
						export.setClientCode(null);
						export.setProductId(0);
						export.setStartTime(DateUtils.getFirstDayOfYear(export.getStartTime().substring(0, 4)));
						export.setEndTime(DateUtils.getLastDayOfLastMonth(export.getEndTime()));
						inboundData.addAll(exportDao.getForeignTradeInbound(export));
						outboundData.addAll(exportDao.getForeignTradeOutbound(export));
						export.setIsPass(1);
						export.setProductId(13);
						inboundData.addAll(exportDao.getForeignTradeInbound(export));
						outboundData.addAll(exportDao.getForeignTradeOutbound(export));
						export.setProductId(15);
						inboundData.addAll(exportDao.getForeignTradeInbound(export));
						outboundData.addAll(exportDao.getForeignTradeOutbound(export));
						export.setProductId(4);
						export.setClientCode("建滔(太仓)化工有限公司");
						inboundData.addAll(exportDao.getForeignTradeInbound(export));
						outboundData.addAll(exportDao.getForeignTradeOutbound(export));
						export.setProductId(14);
						export.setClientCode("碧辟(中国)工业油品有限公司");
						inboundData.addAll(exportDao.getForeignTradeInbound(export));
						outboundData.addAll(exportDao.getForeignTradeOutbound(export));
						export.setClientCode("中国石油天然气股份有限公司华东润滑油厂");
						inboundData.addAll(exportDao.getForeignTradeInbound(export));
						outboundData.addAll(exportDao.getForeignTradeOutbound(export));
					
						
						inboundDataMap=getBerthMap(getModelBerthMap(0), inboundData);
						outboundDataMap=getBerthMap(getModelBerthMap(0), outboundData);
					}else if(isFirstMonth){
							inboundDataMap=getBerthMap(getModelBerthMap(0), null);
							outboundDataMap=getBerthMap(getModelBerthMap(0), null);
					}
					for(int j=0;j<4;j++){
						item=7+i;
						if(i>9){
						 item=8+i;
						}
						sheet.getRow(item).getCell(1).setCellValue(FormatUtils.getDoubleValue(inboundDataMap.get("1").get("num").toString()));
						sheet.getRow(item).getCell(2).setCellValue(FormatUtils.getDoubleValue(inboundDataMap.get("1").get("count").toString()));
						
						sheet.getRow(item).getCell(3).setCellValue(FormatUtils.getDoubleValue(outboundDataMap.get("1").get("num").toString()));
						sheet.getRow(item).getCell(4).setCellValue(FormatUtils.getDoubleValue(outboundDataMap.get("1").get("count").toString()));
						
						
						sheet.getRow(item).getCell(5).setCellValue(FormatUtils.getDoubleValue(inboundDataMap.get("2").get("num").toString()));
						sheet.getRow(item).getCell(6).setCellValue(FormatUtils.getDoubleValue(inboundDataMap.get("2").get("count").toString()));
						
						sheet.getRow(item).getCell(7).setCellValue(FormatUtils.getDoubleValue(outboundDataMap.get("2").get("num").toString()));
						sheet.getRow(item).getCell(8).setCellValue(FormatUtils.getDoubleValue(outboundDataMap.get("2").get("count").toString()));
						
						
						sheet.getRow(item).getCell(9).setCellValue(FormatUtils.getDoubleValue(inboundDataMap.get("6").get("num").toString()));
						sheet.getRow(item).getCell(10).setCellValue(FormatUtils.getDoubleValue(inboundDataMap.get("6").get("count").toString()));
						
						sheet.getRow(item).getCell(11).setCellValue(FormatUtils.getDoubleValue(outboundDataMap.get("6").get("num").toString()));
						sheet.getRow(item).getCell(12).setCellValue(FormatUtils.getDoubleValue(outboundDataMap.get("6").get("count").toString()));
						
						
						sheet.getRow(item).getCell(13).setCellValue(FormatUtils.getDoubleValue(inboundDataMap.get("7").get("num").toString()));
						sheet.getRow(item).getCell(14).setCellValue(FormatUtils.getDoubleValue(inboundDataMap.get("7").get("count").toString()));
						
						sheet.getRow(item).getCell(15).setCellValue(FormatUtils.getDoubleValue(outboundDataMap.get("7").get("num").toString()));
						sheet.getRow(item).getCell(16).setCellValue(FormatUtils.getDoubleValue(outboundDataMap.get("7").get("count").toString()));

					}
					if(i<5)
						sheet.getRow(i+7).getCell(21).setCellValue(exportDao.getAssemBlyShip(export));
					if(i==10&&!isFirstMonth){
						export.setProductId(0);
						sheet.getRow(i+8).getCell(21).setCellValue(exportDao.getAssemBlyShip(export));
					}
				}
				sheet.setForceFormulaRecalculation(true);
				
				}catch (OAException e) 
				{
					e.printStackTrace();
				}
				
			}
		}).getWorkbook();
	}

	/**
	 * 装车站发货统计表导出到Excel
	 * @Title:exportStation
	 * @Description:
	 * @param request
	 * @param type
	 * @param params
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public HSSFWorkbook exportStation(HttpServletRequest request, String type,String params,final ExportDto export) throws OAException 
	{
		return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/stationDeliver.xls", new CallBack() {
			@Override
			public void setSheetValue(HSSFSheet sheet) 
			{
				sheet.setForceFormulaRecalculation(true);
				String title = sheet.getRow(0).getCell(0).getStringCellValue();
				String date = export.getStatisMonth();
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH)+1;
				if (date != null && !"".equals(date)) {
					date = date.substring(0, 4) + "年" + (date.substring(5, 7)) + "月份装车站发货统计表";
					title = title.replaceAll("2015年11月份装车站发货统计表", date);
				} else {
					date = year + "年" + month + "月份装车站发货统计表";
				}
				
				sheet.getRow(0).getCell(0).setCellValue(date);
				
				List<Map<String, Object>> data;
				String[] station = {"A01","A02","A03","A04","A05","A06","A07","A08","A09","A10","A11","A12","A13","A14","A15","A16"};
				String[] first = {"A17","A18","A19","A20","A21","A22","A23","A24","A25","A26","A27","A28","A29","A30","A31","A32"};
				String[] last = {"A33","A34","A35","A36","A37","A38","A39","A40","A41","A42","A43","A44","A45","A46","A47","A48"};
				int[] productId1 = {2,3,1};
				int[] productId2 = {2};
				int[] productId3 = {4,2};
				
				try 
				{
					String remark = sheet.getRow(33).getCell(2).getStringCellValue();
					String total = "本月装车站发货量 ";
					
					//本月装车站发货量和车数
					for(int a=0;a<1;a++)
					{
						double thisAmount = 0;
						double thisCar = 0;
						export.setInPort("0");
						export.setProductId(0);
						export.setType(0);
						data = exportDao.findStation(export);
						if(data != null && data.size() > 0)
						{
							for(int i=0;i<data.size();i++)
							{
								Map<String,Object> map= (Map<String, Object>) data.get(0);
								thisAmount = FormatUtils.formatNum(FormatUtils.getDoubleValue(map.get("totalAmount")));
								thisCar = FormatUtils.getDoubleValue(map.get("carNum"));
							}
							
							total += thisAmount+" 吨，"+thisCar+" 车 "+" 1-"+date.substring(5, 7)+" 月份累计发货 ";
						}
					}
					
					//1月到本月装车站发货量和车数
					for(int a=0;a<1;a++)
					{
						double thisAmount1 = 0;
						double thisCar1 = 0;
						export.setInPort("0");
						export.setProductId(0);
						export.setType(1);
						data = exportDao.findStation(export);
						if(data != null && data.size() > 0)
						{
							for(int i=0;i<data.size();i++)
							{
								Map<String,Object> map= (Map<String, Object>) data.get(0);
								thisAmount1 = FormatUtils.formatNum(FormatUtils.getDoubleValue(map.get("totalAmount")));
								thisCar1 = FormatUtils.getDoubleValue(map.get("carNum"));
							}
							
							total += thisAmount1 +" 吨 " + thisCar1 + " 车";
						}
					}
					remark = remark.replaceAll("", total);
					sheet.getRow(33).getCell(1).setCellValue(total);
					
					//新装车站（A01-A16车位）
					for(int a=0;a<productId1.length;a++)
					{
						double weight = 0;
						double carAmount = 0;
						export.setProductId(productId1[a]);
						export.setType(0);
						for(int x=0;x<station.length;x++)
						{
							export.setInPort(station[x]);
							data = exportDao.findStation(export);
							if(data != null && data.size() > 0)
							{
								for(int i=0;i<data.size();i++)
								{
									Map<String,Object> map= (Map<String, Object>) data.get(0);
									sheet.getRow(3+a*2).getCell(2+x).setCellValue(FormatUtils.formatNum(FormatUtils.getDoubleValue(map.get("totalAmount"))));
									sheet.getRow(4+a*2).getCell(2+x).setCellValue(FormatUtils.getDoubleValue(map.get("carNum")));
									weight += FormatUtils.getDoubleValue(map.get("totalAmount"));
									carAmount += FormatUtils.getDoubleValue(map.get("carNum"));
								}
							}
						}
						
						sheet.getRow(3+a*2).getCell(19).setCellValue(FormatUtils.formatNum(weight));
						sheet.getRow(4+a*2).getCell(19).setCellValue(carAmount);
					}
					
					//新装车站（A01-A16车位）-上累
					for(int a=0;a<1;a++)
					{
						double weight = 0;
						double carAmount = 0;
						export.setProductId(0);
						export.setType(2);
						for(int x=0;x<station.length;x++)
						{
							export.setInPort(station[x]);
							data = exportDao.findStation(export);
							if(data != null && data.size() > 0)
							{
								for(int i=0;i<data.size();i++)
								{
									Map<String,Object> map= (Map<String, Object>) data.get(0);
									sheet.getRow(9).getCell(2+x).setCellValue(FormatUtils.formatNum(FormatUtils.getDoubleValue(map.get("totalAmount"))));
									sheet.getRow(10).getCell(2+x).setCellValue(FormatUtils.getDoubleValue(map.get("carNum")));
									weight += FormatUtils.getDoubleValue(map.get("totalAmount"));
									carAmount += FormatUtils.getDoubleValue(map.get("carNum"));
								}
							}
						}
						
						sheet.getRow(9).getCell(19).setCellValue(FormatUtils.formatNum(weight));
						sheet.getRow(10).getCell(19).setCellValue(carAmount);
					}
					
					//新装车站（A01-A16车位）-累计
					for(int a=0;a<1;a++)
					{
						double weight = 0;
						double carAmount = 0;
						export.setProductId(0);
						export.setType(1);
						for(int x=0;x<station.length;x++)
						{
							export.setInPort(station[x]);
							data = exportDao.findStation(export);
							if(data != null && data.size() > 0)
							{
								for(int i=0;i<data.size();i++)
								{
									Map<String,Object> map= (Map<String, Object>) data.get(0);
									sheet.getRow(11).getCell(2+x).setCellValue(FormatUtils.formatNum(FormatUtils.getDoubleValue(map.get("totalAmount"))));
									sheet.getRow(12).getCell(2+x).setCellValue(FormatUtils.getDoubleValue(map.get("carNum")));
									weight += FormatUtils.getDoubleValue(map.get("totalAmount"));
									carAmount += FormatUtils.getDoubleValue(map.get("carNum"));
								}
							}
						}
						
						sheet.getRow(11).getCell(19).setCellValue(FormatUtils.formatNum(weight));
						sheet.getRow(12).getCell(19).setCellValue(carAmount);
					}
					
					//新装车站（A17-A32车位）
					for(int a=0;a<productId2.length;a++)
					{
						double fweight = 0;
						double fcar = 0;
						export.setProductId(productId2[a]);
						export.setType(0);
						for(int y=0;y<first.length;y++)
						{
							export.setInPort(first[y]);
							data = exportDao.findStation(export);
							if(data != null && data.size() > 0)
							{
								for(int i=0;i<data.size();i++)
								{
									Map<String,Object> map= (Map<String, Object>) data.get(0);
									sheet.getRow(16).getCell(2+y).setCellValue(FormatUtils.formatNum(FormatUtils.getDoubleValue(map.get("totalAmount"))));
									sheet.getRow(17).getCell(2+y).setCellValue(FormatUtils.getDoubleValue(map.get("carNum")));
									fweight += FormatUtils.getDoubleValue(map.get("totalAmount"));
									fcar += FormatUtils.getDoubleValue(map.get("carNum"));
								}
							}
							sheet.getRow(16).getCell(19).setCellValue(FormatUtils.formatNum(fweight));
							sheet.getRow(17).getCell(19).setCellValue(fcar);
						}
					}
					
					//新装车站（A17-A32车位）-上累
					for(int a=0;a<1;a++)
					{
						double fweight = 0;
						double fcar = 0;
						export.setProductId(0);
						export.setType(2);
						for(int y=0;y<first.length;y++)
						{
							export.setInPort(first[y]);
							data = exportDao.findStation(export);
							if(data != null && data.size() > 0)
							{
								for(int i=0;i<data.size();i++)
								{
									Map<String,Object> map= (Map<String, Object>) data.get(0);
									sheet.getRow(18).getCell(2+y).setCellValue(FormatUtils.formatNum(FormatUtils.getDoubleValue(map.get("totalAmount"))));
									sheet.getRow(19).getCell(2+y).setCellValue(FormatUtils.getDoubleValue(map.get("carNum")));
									fweight += FormatUtils.getDoubleValue(map.get("totalAmount"));
									fcar += FormatUtils.getDoubleValue(map.get("carNum"));
								}
							}
							sheet.getRow(18).getCell(19).setCellValue(FormatUtils.formatNum(fweight));
							sheet.getRow(19).getCell(19).setCellValue(fcar);
						}
					}
					
					//新装车站（A17-A32车位）-累计
					for(int a=0;a<1;a++)
					{
						double fweight = 0;
						double fcar = 0;
						export.setProductId(0);
						export.setType(1);
						for(int y=0;y<first.length;y++)
						{
							export.setInPort(first[y]);
							data = exportDao.findStation(export);
							if(data != null && data.size() > 0)
							{
								for(int i=0;i<data.size();i++)
								{
									Map<String,Object> map= (Map<String, Object>) data.get(0);
									sheet.getRow(20).getCell(2+y).setCellValue(FormatUtils.formatNum(FormatUtils.getDoubleValue(map.get("totalAmount"))));
									sheet.getRow(21).getCell(2+y).setCellValue(FormatUtils.getDoubleValue(map.get("carNum")));
									fweight += FormatUtils.getDoubleValue(map.get("totalAmount"));
									fcar += FormatUtils.getDoubleValue(map.get("carNum"));
								}
							}
							sheet.getRow(20).getCell(19).setCellValue(FormatUtils.formatNum(fweight));
							sheet.getRow(21).getCell(19).setCellValue(fcar);
						}
					}
					
					//新装车站（A33-A48车位）
					for(int b=0;b<productId3.length;b++)
					{
						double lweight = 0;
						double lcar = 0;
						export.setProductId(productId3[b]);
						export.setType(0);
						for(int z=0;z<last.length;z++)
						{
							export.setInPort(last[z]);
							data = exportDao.findStation(export);
							if(data != null && data.size() > 0)
							{
								for(int i=0;i<data.size();i++)
								{
									Map<String,Object> map= (Map<String, Object>) data.get(0);
									sheet.getRow(25+b*2).getCell(2+z).setCellValue(FormatUtils.formatNum(FormatUtils.getDoubleValue(map.get("totalAmount"))));
									sheet.getRow(26+b*2).getCell(2+z).setCellValue(FormatUtils.getDoubleValue(map.get("carNum")));
									lweight += FormatUtils.getDoubleValue(map.get("totalAmount"));
									lcar += FormatUtils.getDoubleValue(map.get("carNum"));
								}
							}
							sheet.getRow(25+b*2).getCell(19).setCellValue(FormatUtils.formatNum(lweight));
							sheet.getRow(26+b*2).getCell(19).setCellValue(lcar);
						}
					}
					
					//新装车站（A33-A48车位）-上累
					for(int b=0;b<1;b++)
					{
						double lweight = 0;
						double lcar = 0;
						export.setProductId(0);
						export.setType(2);
						for(int z=0;z<last.length;z++)
						{
							export.setInPort(last[z]);
							data = exportDao.findStation(export);
							if(data != null && data.size() > 0)
							{
								for(int i=0;i<data.size();i++)
								{
									Map<String,Object> map= (Map<String, Object>) data.get(0);
									sheet.getRow(29).getCell(2+z).setCellValue(FormatUtils.formatNum(FormatUtils.getDoubleValue(map.get("totalAmount"))));
									sheet.getRow(30).getCell(2+z).setCellValue(FormatUtils.getDoubleValue(map.get("carNum")));
									lweight += FormatUtils.getDoubleValue(map.get("totalAmount"));
									lcar += FormatUtils.getDoubleValue(map.get("carNum"));
								}
							}
							sheet.getRow(29).getCell(19).setCellValue(FormatUtils.formatNum(lweight));
							sheet.getRow(30).getCell(19).setCellValue(lcar);
						}
					}
					
					//新装车站（A33-A48车位）-累计
					for(int b=0;b<1;b++)
					{
						double lweight = 0;
						double lcar = 0;
						export.setProductId(0);
						export.setType(1);
						for(int z=0;z<last.length;z++)
						{
							export.setInPort(last[z]);
							data = exportDao.findStation(export);
							if(data != null && data.size() > 0)
							{
								for(int i=0;i<data.size();i++)
								{
									Map<String,Object> map= (Map<String, Object>) data.get(0);
									sheet.getRow(31).getCell(2+z).setCellValue(FormatUtils.formatNum(FormatUtils.getDoubleValue(map.get("totalAmount"))));
									sheet.getRow(32).getCell(2+z).setCellValue(FormatUtils.getDoubleValue(map.get("carNum")));
									lweight += FormatUtils.getDoubleValue(map.get("totalAmount"));
									lcar += FormatUtils.getDoubleValue(map.get("carNum"));
								}
							}
							sheet.getRow(31).getCell(19).setCellValue(FormatUtils.formatNum(lweight));
							sheet.getRow(32).getCell(19).setCellValue(lcar);
						}
					}
					
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}).getWorkbook();
	}

	/**
	 * 通过单位统计表导出到Excel
	 * @Title:exportUnit
	 * @Description:
	 * @param request
	 * @param type
	 * @param params
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public HSSFWorkbook exportUnit(HttpServletRequest request, String type,String params,final ExportDto export) throws OAException 
	{
		return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/unitThrought.xls", new CallBack() {
			@Override
			public void setSheetValue(HSSFSheet sheet) 
			{// startTime,endTime ,isTank
				try 
				{ 
					String date = export.getStartTime();
					boolean isYear ="2016".equals(export.getStartTime().substring(0, 4));
					sheet.getRow(0).getCell(0).setCellValue(date.substring(0, 4)+"年"+(date.substring(5, 7))+"月份通过单位统计表");//标题
					sheet.getRow(2).getCell(10).setCellValue("01-"+date.substring(5, 7)+"月累计");//副标题
					sheet.getRow(3).getCell(10).setCellValue(date.substring(0,4)+"年吞吐量");
					sheet.getRow(3).getCell(11).setCellValue((Integer.valueOf(date.substring(0,4))-1)+"年吞吐量");
					int[] inboundPassProduct={19,17,18,13,15,4,14,14};
					String[] inboundPassClient={"琪优势化工(太仓)有限公司","琪优势化工(太仓)有限公司","琪优势化工(太仓)有限公司",
							"太仓东华能源燃气有限公司","苏州华苏塑料有限公司","建滔(太仓)化工有限公司","碧辟(中国)工业油品有限公司","中国石油天然气股份有限公司华东润滑油厂"};
					int[] outboundPassProduct={18,13,14};
					String[] outboundPassClient={"琪优势化工(太仓)有限公司","太仓东华能源燃气有限公司",null};
					Map<String,Object> inboundMap,inboundMapLastMonth,inboundMapThisYear,inboundMapLastYear;
					Map<String,Object> outboundMap,outboundMapLastMonth,outboundMapThisYear,outboundMapLastYear;
					Map<String, Object> tankNum = null;//储罐最大存储
					String startTime=export.getStartTime();
					String endTime=export.getEndTime();
					String year=startTime.substring(0,4);
					int month=Integer.valueOf(startTime.substring(5,7));
					Map<String,Object> data,maxNumData;
					ExportDto nExportDto=new ExportDto();
					//入库通过
					for(int i=0;i<8;i++){
						
						nExportDto.setProductId(inboundPassProduct[i]);
						nExportDto.setClientCode(inboundPassClient[i]);
						//今年
						nExportDto.setStartTime(startTime);
						nExportDto.setEndTime(endTime);
						//吞吐量
						if(i<3){
							nExportDto.setIsPass(0);
							tankNum=exportDao.getMaxNumOfTank(nExportDto);
						}else{
							nExportDto.setIsPass(1);
						}
						inboundMap=exportDao.getInboundPass(nExportDto);
						//去年同期
						nExportDto.setStartTime(DateUtils.getThisTimeOfLastYear(startTime));
						nExportDto.setEndTime(DateUtils.getThisTimeOfLastYear(endTime));
						if(isYear){
							nExportDto.setIsYearType(1);
							inboundMapLastMonth=exportDao.getUnit2015(nExportDto);
						}else{
							inboundMapLastMonth=exportDao.getInboundPass(nExportDto);
						}
						//今年累计
						nExportDto.setStartTime(startTime.substring(0, 4)+"-01-01");
						nExportDto.setEndTime(endTime);
						inboundMapThisYear=exportDao.getInboundPass(nExportDto);
						//去年累计
						nExportDto.setStartTime((Integer.valueOf(startTime.substring(0, 4))-1)+"-01-01");
						nExportDto.setEndTime(DateUtils.getThisTimeOfLastYear(endTime));
						if(isYear){
							inboundMapLastYear=exportDao.getUnit2015(nExportDto);
						}else{
						inboundMapLastYear=exportDao.getInboundPass(nExportDto);
						}
						if(i<3){
							sheet.getRow(6+i).getCell(2).setCellValue(FormatUtils.getDoubleValue(tankNum.get("tankNum")));
							//TODO 处理通过单位储罐周转率
							Double accRate=0D;
							for(int j=1;j<=month;j++){
								nExportDto.setStartTime(year+"-"+(j<10?("0"+j):j)+"-01");
								nExportDto.setEndTime(DateUtils.getLastDayOfMonth(year+"-"+(j<10?("0"+j):j)));
								data=exportDao.findTurnRate(nExportDto);
								maxNumData=exportDao.getMaxNumOfTank(nExportDto);
								accRate=Common.add(accRate, Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2), 2);
							}
							sheet.getRow(6+i).getCell(7).setCellValue(accRate);
						}
						sheet.getRow(6+i).getCell(3).setCellValue(FormatUtils.getDoubleValue(inboundMap.get("num")));
						sheet.getRow(6+i).getCell(4).setCellValue(FormatUtils.getDoubleValue(inboundMapLastMonth.get("num")));
						sheet.getRow(6+i).getCell(10).setCellValue(FormatUtils.getDoubleValue(inboundMapThisYear.get("num")));
						sheet.getRow(6+i).getCell(11).setCellValue(FormatUtils.getDoubleValue(inboundMapLastYear.get("num")));
						
					}
					//出库通过
					for(int i=0;i<3;i++){
						//吞吐量
						if(i==0){
							nExportDto.setIsPass(0);
						}else{
							nExportDto.setIsPass(1);
						}
						nExportDto.setProductId(outboundPassProduct[i]);
						nExportDto.setClientCode(outboundPassClient[i]);
						//今年
						nExportDto.setStartTime(startTime);
						nExportDto.setEndTime(endTime);
						outboundMap=exportDao.getOutboundPass(nExportDto);
						//去年同期
						nExportDto.setStartTime(DateUtils.getThisTimeOfLastYear(startTime));
						nExportDto.setEndTime(DateUtils.getThisTimeOfLastYear(endTime));
						if(isYear){
							nExportDto.setIsYearType(2);
							outboundMapLastMonth=exportDao.getUnit2015(nExportDto);	
						}else{
						outboundMapLastMonth=exportDao.getOutboundPass(nExportDto);
						}
						//今年累计
						nExportDto.setStartTime(startTime.substring(0, 4)+"-01-01");
						nExportDto.setEndTime(endTime);
						outboundMapThisYear=exportDao.getOutboundPass(nExportDto);
						//去年累计
						nExportDto.setStartTime((Integer.valueOf(startTime.substring(0, 4))-1)+"-01-01");
						nExportDto.setEndTime(DateUtils.getThisTimeOfLastYear(endTime));
						if(isYear){
						outboundMapLastYear=exportDao.getUnit2015(nExportDto);	
						}else{
						outboundMapLastYear=exportDao.getOutboundPass(nExportDto);
						}
						sheet.getRow(15+i).getCell(3).setCellValue(FormatUtils.getDoubleValue(outboundMap.get("num")));
						sheet.getRow(15+i).getCell(4).setCellValue(FormatUtils.getDoubleValue(outboundMapLastMonth.get("num")));
						sheet.getRow(15+i).getCell(10).setCellValue(FormatUtils.getDoubleValue(outboundMapThisYear.get("num")));
						sheet.getRow(15+i).getCell(11).setCellValue(FormatUtils.getDoubleValue(outboundMapLastYear.get("num")));
					}
					sheet.setForceFormulaRecalculation(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}).getWorkbook();
	}

	/**
	 * 年度仓储汇总表导出到Excel
	 * @Title:exportstorage
	 * @Description:
	 * @param request
	 * @param type
	 * @param params
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public HSSFWorkbook exportStorage(HttpServletRequest request, String type,String params,final ExportDto export) throws OAException 
	{
		return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/yearStorage.xls", new CallBack() {
			@Override
			public void setSheetValue(HSSFSheet sheet) 
			{
				sheet.setForceFormulaRecalculation(true);
				
				String title = sheet.getRow(0).getCell(0).getStringCellValue();
				String date = export.getStatisMonth();
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				if(date != null && !"".equals(date))
				{
					date = date.substring(0, 4)+"年度长江石化仓储进出存汇总表";
					title = title.replaceAll("2015年度长江石化仓储进出存汇总表", date);
				}
				else
				{
					date = year + "年度长江石化仓储进出存汇总表";
				}
				
				sheet.getRow(0).getCell(0).setCellValue(date);
				
				List<Map<String, Object>> data;
				List<Map<String, Object>> data1;
				List<Map<String, Object>> data2;
				int[] productId = {4,2,3,1,17,19,20};
				int[] month = {1,2,3,4,5,6,7,8,9,10,11,12};
				String str = "";
				try 
				{
					for(int a=0;a<month.length;a++)
					{
						str = "2015-"+(month[a]<10?"0"+month[a]:month[a]);
						export.setStatisMonth(str);
						for(int b=0;b<productId.length;b++)
						{
							export.setProductId(productId[b]);
							data = exportDao.queryInStorage(export);
							data1 = exportDao.queryOutStorage(export);
							data2 = exportDao.queryCurrentStorage(export);
							//本期入库
							if(data != null && data.size() > 0)
							{
								for(int i=0;i<data.size();i++)
								{
									Map<String,Object> map= (Map<String, Object>) data.get(0);
									sheet.getRow(3+a).getCell(2+4*b).setCellValue(FormatUtils.getDoubleValue(map.get("inAmount")));
								}
							}
							
							//本期出库
							if(data1 != null && data1.size() > 0)
							{
								for(int i=0;i<data1.size();i++)
								{
									Map<String,Object> map= (Map<String, Object>) data1.get(0);
									sheet.getRow(3+a).getCell(3+4*b).setCellValue(FormatUtils.getDoubleValue(map.get("outAmount")));
								}
							}
							
							//期末结存
							if(data2 != null && data2.size() > 0)
							{
								for(int i=0;i<data2.size();i++)
								{
									Map<String,Object> map= (Map<String, Object>) data2.get(0);
									sheet.getRow(3+a).getCell(4+4*b).setCellValue(FormatUtils.getDoubleValue(map.get("curAmount")));
								}
							}
						}
					}
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}).getWorkbook();
	}

	/**
	 * 吞吐量统计表导出到Excel
	 * @Title:exportThroughtRate
	 * @Description:
	 * @param request
	 * @param type
	 * @param params
	 * @param export
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public HSSFWorkbook exportThroughtRate(HttpServletRequest request,String type, String params, final ExportDto export) throws OAException 
	{
		return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/throught.xls", new CallBack() {
			@Override
			public void setSheetValue(HSSFSheet sheet)
			{
				sheet.setForceFormulaRecalculation(true);
				String title,addTitle;
				String startTime = export.getStartTime();
				boolean  isYear="2016".equals(startTime.substring(0,4));//是否是2016年，如果是 去年同期获取2015_throught_rate里的数据
				String endTime =export.getEndTime();
				ExportDto exportLastAcc=new ExportDto();//上累
				exportLastAcc.setStartTime(startTime);
				exportLastAcc.setAddUp(1);
				if(startTime.substring(5, 7).equals("01")) {
					exportLastAcc.setEndTime((Integer.valueOf(startTime.substring(0,4))-1)+"-01-01");
					}else{
					exportLastAcc.setEndTime(DateUtils.getLastDayOfLastMonth(endTime));
					}
				ExportDto exportLastYear=new ExportDto();
				exportLastYear.setStartTime(DateUtils.getLastYearTime(export.getStartTime()));
				exportLastYear.setEndTime(DateUtils.getLastDayOfMonth(DateUtils.getLastYearTime(export.getEndTime())));
				title = startTime.substring(0, 4)+"年"+(startTime.substring(5, 7))+"月份吞吐量统计表";
				addTitle = "01-"+startTime.substring(5, 7)+"月份累计";
				
				sheet.getRow(1).getCell(0).setCellValue(title);
				sheet.getRow(2).getCell(0).setCellValue("时间区间："+export.getStartTime()+"/"+export.getEndTime()+"  单位：吨/船/车");
				sheet.getRow(3).getCell(9).setCellValue(addTitle);
				sheet.getRow(4).getCell(9).setCellValue(startTime.substring(0, 4)+"年吞吐量");
				sheet.getRow(4).getCell(10).setCellValue((Integer.valueOf(startTime.substring(0, 4))-1)+"年吞吐量");
				sheet.getRow(4).getCell(12).setCellValue(startTime.substring(0, 4)+"年船舶数");
			
				try {
					Map<String, Object> throughtMsgMap=throughtMsgDao.getThroughtMsg(DateUtils.getLongTime(export.getStartTime()+" 00:00:00"));
					sheet.getRow(0).getCell(0).setCellValue(FormatUtils.getStringValue(throughtMsgMap.get("content")));
				} catch (OAException e1) {
					e1.printStackTrace();
				}
				
				
				
				int[] inProType = {2,4,19,17,1,3};//入库接卸
				int[] inProTypePass = {13,15,4,14,14};//入库通过接卸
				int[] inClientPass = {0,0,0,59,4027};//入库通过接卸货主
				int[] outShipProType = {2,4,18,1,3};//船舶发货
				int[] outPasShipProType = {13,14};//通过发货
				int[] outPasShipClient = {0,0};//通过发货货主
				int[] outTruckProType = {2,4,1,3};//车发发货
				int[] pro = {19,17,4,18};//转输输出
				Map<String,Object> mapA=null,mapB=null,mapC=null,mapD=null;
				try 
				{
					//接卸
					for(int a=0,len=inProType.length;a<len;a++)
					{
						export.setProductId(inProType[a]);
						export.setType(1);
						export.setAddUp(0);
						mapA=exportDao.queryInThrough(export);//入库
						
						exportLastYear.setProductId(inProType[a]);
						exportLastYear.setType(1);
						exportLastYear.setAddUp(0);
						if(isYear){
							exportLastYear.setIsYearType(1);
							mapB=exportDao.queryThroughtRateOf2015(exportLastYear);
							exportLastYear.setAddUp(1);
							mapD=exportDao.queryThroughtRateOf2015(exportLastYear);
						}else{
							mapB= exportDao.queryInThrough(exportLastYear);//去年同期	
							exportLastYear.setAddUp(1);
							mapD= exportDao.queryInThrough(exportLastYear);//去年入库-累计
						}
						
						exportLastAcc.setProductId(inProType[a]);
						exportLastAcc.setType(1);
						mapC=exportDao.queryInThrough(exportLastAcc);//入库-上累计
						
						
						sheet.getRow(8+a).getCell(1).setCellValue(FormatUtils.getDoubleValue(mapA.get("totalNum")));
						sheet.getRow(8+a).getCell(2).setCellValue(FormatUtils.getDoubleValue(mapB.get("totalNum")));
						sheet.getRow(8+a).getCell(4).setCellValue(FormatUtils.getDoubleValue(mapA.get("shipNum")));
						sheet.getRow(8+a).getCell(5).setCellValue(FormatUtils.getDoubleValue(mapB.get("shipNum")));
						sheet.getRow(8+a).getCell(7).setCellValue(FormatUtils.getDoubleValue(mapC.get("totalNum")));
						sheet.getRow(8+a).getCell(8).setCellValue(FormatUtils.getDoubleValue(mapC.get("shipNum")));
						sheet.getRow(8+a).getCell(10).setCellValue(FormatUtils.getDoubleValue(mapD.get("totalNum")));
					}
					//入库通过
					for(int b=0,len=inProTypePass.length;b<len;b++)
					{
						export.setProductId(inProTypePass[b]);
						export.setClientId(inClientPass[b]);
						export.setType(0);
						export.setAddUp(0);
						mapA=exportDao.queryInThrough(export);//入库通过
						
						exportLastYear.setProductId(inProTypePass[b]);
						exportLastYear.setClientId(inClientPass[b]);
						exportLastYear.setType(0);
						exportLastYear.setAddUp(0);
						if(isYear){
							exportLastYear.setIsYearType(2);
							mapB= exportDao.queryThroughtRateOf2015(exportLastYear);
							exportLastYear.setAddUp(1);
							mapD= exportDao.queryThroughtRateOf2015(exportLastYear);
						}else{
							mapB= exportDao.queryInThrough(exportLastYear);//去年入库
							exportLastYear.setAddUp(1);
							mapD= exportDao.queryInThrough(exportLastYear);//去年入库通过-累计
						}
						
						
						exportLastAcc.setProductId(inProTypePass[b]);
						exportLastAcc.setClientId(inClientPass[b]);
						exportLastAcc.setType(0);
						mapC=exportDao.queryInThrough(exportLastAcc);//入库通过-上累计
						
						
						
						sheet.getRow(15+b).getCell(1).setCellValue(FormatUtils.getDoubleValue(mapA.get("totalNum")));
						sheet.getRow(15+b).getCell(2).setCellValue(FormatUtils.getDoubleValue(mapB.get("totalNum")));
						sheet.getRow(15+b).getCell(4).setCellValue(FormatUtils.getDoubleValue(mapA.get("shipNum")));
						sheet.getRow(15+b).getCell(5).setCellValue(FormatUtils.getDoubleValue(mapB.get("shipNum")));
						sheet.getRow(15+b).getCell(7).setCellValue(FormatUtils.getDoubleValue(mapC.get("totalNum")));
						sheet.getRow(15+b).getCell(8).setCellValue(FormatUtils.getDoubleValue(mapC.get("shipNum")));
						sheet.getRow(15+b).getCell(10).setCellValue(FormatUtils.getDoubleValue(mapD.get("totalNum")));
					}
					export.setClientId(0);
					exportLastYear.setClientId(0);
					exportLastAcc.setClientId(0);
					//船发出库
					for(int c=0,len=outShipProType.length;c<len;c++)
					{
						export.setProductId(outShipProType[c]);
						export.setType(1);
						export.setAddUp(0);
						mapA = exportDao.queryOutThrough(export);
						
						exportLastYear.setProductId(outShipProType[c]);
						exportLastYear.setType(1);
						exportLastYear.setAddUp(0);
						if(isYear){
							exportLastYear.setIsYearType(3);
							mapB=exportDao.queryThroughtRateOf2015(exportLastYear);
							exportLastYear.setAddUp(1);
							mapD=exportDao.queryThroughtRateOf2015(exportLastYear);
						}else{
							mapB= exportDao.queryOutThrough(exportLastYear);
							exportLastYear.setAddUp(1);
							mapD= exportDao.queryOutThrough(exportLastYear);
						}
						
						exportLastAcc.setProductId(outShipProType[c]);
						exportLastAcc.setType(1);
						mapC= exportDao.queryOutThrough(exportLastAcc);
						
						
						
						sheet.getRow(22+c).getCell(1).setCellValue(FormatUtils.getDoubleValue(mapA.get("totalNum")));
						sheet.getRow(22+c).getCell(2).setCellValue(FormatUtils.getDoubleValue(mapB.get("totalNum")));
						sheet.getRow(22+c).getCell(4).setCellValue(FormatUtils.getDoubleValue(mapA.get("shipNum")));
						sheet.getRow(22+c).getCell(5).setCellValue(FormatUtils.getDoubleValue(mapB.get("shipNum")));
						sheet.getRow(22+c).getCell(7).setCellValue(FormatUtils.getDoubleValue(mapC.get("totalNum")));
						sheet.getRow(22+c).getCell(8).setCellValue(FormatUtils.getDoubleValue(mapC.get("shipNum")));
						sheet.getRow(22+c).getCell(10).setCellValue(FormatUtils.getDoubleValue(mapD.get("totalNum")));
					}
					//出库-通过
					for(int d=0,len=outPasShipProType.length;d<len;d++)
					{
						export.setProductId(outPasShipProType[d]);
						export.setClientId(outPasShipClient[d]);
						export.setType(0);
						export.setAddUp(0);
						mapA = exportDao.queryOutThrough(export);
						
						exportLastYear.setProductId(outPasShipProType[d]);
						exportLastYear.setClientId(outPasShipClient[d]);
						exportLastYear.setType(0);
						exportLastYear.setAddUp(0);
						if(isYear){
							exportLastYear.setIsYearType(4);
							mapB=exportDao.queryThroughtRateOf2015(exportLastYear);
							exportLastYear.setAddUp(1);
							mapD=exportDao.queryThroughtRateOf2015(exportLastYear);
						}else{
							mapB= exportDao.queryOutThrough(exportLastYear);
							exportLastYear.setAddUp(1);
							mapD= exportDao.queryOutThrough(exportLastYear);
						}
						
						
						
						exportLastAcc.setProductId(outPasShipProType[d]);
						exportLastAcc.setClientId(outPasShipClient[d]);
						exportLastAcc.setType(0);
						mapC= exportDao.queryOutThrough(exportLastAcc);
						
						
						
						sheet.getRow(28+d).getCell(1).setCellValue(FormatUtils.getDoubleValue(mapA.get("totalNum")));
						sheet.getRow(28+d).getCell(4).setCellValue(FormatUtils.getDoubleValue(mapA.get("shipNum")));
						sheet.getRow(28+d).getCell(2).setCellValue(FormatUtils.getDoubleValue(mapB.get("totalNum")));
						sheet.getRow(28+d).getCell(5).setCellValue(FormatUtils.getDoubleValue(mapB.get("shipNum")));
						sheet.getRow(28+d).getCell(7).setCellValue(FormatUtils.getDoubleValue(mapC.get("totalNum")));
						sheet.getRow(28+d).getCell(8).setCellValue(FormatUtils.getDoubleValue(mapC.get("shipNum")));
						sheet.getRow(28+d).getCell(10).setCellValue(FormatUtils.getDoubleValue(mapD.get("totalNum")));
					}
					export.setClientId(0);
					exportLastYear.setClientId(0);
					exportLastAcc.setClientId(0);
					//出库-车发
					for(int e=0,len=outTruckProType.length;e<len;e++)
					{
						export.setProductId(outTruckProType[e]);
						export.setAddUp(0);
						mapA = exportDao.queryOutCar(export);
						
						exportLastYear.setProductId(outTruckProType[e]);
						exportLastYear.setAddUp(0);
						if(isYear){
							exportLastYear.setIsYearType(5);
							mapB=exportDao.queryThroughtRateOf2015(exportLastYear);
							exportLastYear.setAddUp(1);
							mapD=exportDao.queryThroughtRateOf2015(exportLastYear);
						}else{
							mapB= exportDao.queryOutCar(exportLastYear);
							exportLastYear.setAddUp(1);
							mapD= exportDao.queryOutCar(exportLastYear);
						}
						
						
						exportLastAcc.setProductId(outTruckProType[e]);
						mapC= exportDao.queryOutCar(exportLastAcc);
						
						
						sheet.getRow(31+e).getCell(1).setCellValue(FormatUtils.getDoubleValue(mapA.get("totalNum")));
						sheet.getRow(31+e).getCell(2).setCellValue(FormatUtils.getDoubleValue(mapB.get("totalNum")));
						sheet.getRow(31+e).getCell(4).setCellValue(FormatUtils.getDoubleValue(mapA.get("shipNum")));
						sheet.getRow(31+e).getCell(5).setCellValue(FormatUtils.getDoubleValue(mapB.get("shipNum")));
						sheet.getRow(31+e).getCell(7).setCellValue(FormatUtils.getDoubleValue(mapC.get("totalNum")));
						sheet.getRow(31+e).getCell(8).setCellValue(FormatUtils.getDoubleValue(mapC.get("shipNum")));
						sheet.getRow(31+e).getCell(10).setCellValue(FormatUtils.getDoubleValue(mapD.get("totalNum")));
					}
					//转输输入
					export.setProductId(18);
					export.setAddUp(0);
					mapA = exportDao.queryInput(export);
					
					exportLastYear.setProductId(18);
					exportLastYear.setAddUp(0);
					if(isYear){
						exportLastYear.setIsYearType(6);
						mapB=exportDao.queryThroughtRateOf2015(exportLastYear);
						exportLastYear.setAddUp(1);
						mapD=exportDao.queryThroughtRateOf2015(exportLastYear);
					}else{
						mapB= exportDao.queryInput(exportLastYear);
						exportLastYear.setAddUp(1);
						mapD = exportDao.queryInput(exportLastYear);
					}
					
					exportLastAcc.setProductId(18);
					mapC = exportDao.queryInput(exportLastAcc);
					
					
					
					sheet.getRow(37).getCell(1).setCellValue(FormatUtils.getDoubleValue(mapA.get("totalNum")));
					sheet.getRow(37).getCell(2).setCellValue(FormatUtils.getDoubleValue(mapB.get("totalNum")));
					sheet.getRow(37).getCell(4).setCellValue(FormatUtils.getDoubleValue(mapA.get("shipNum")));
					sheet.getRow(37).getCell(5).setCellValue(FormatUtils.getDoubleValue(mapB.get("shipNum")));
					sheet.getRow(37).getCell(7).setCellValue(FormatUtils.getDoubleValue(mapC.get("totalNum")));
					sheet.getRow(37).getCell(8).setCellValue(FormatUtils.getDoubleValue(mapC.get("shipNum")));
					sheet.getRow(37).getCell(10).setCellValue(FormatUtils.getDoubleValue(mapD.get("totalNum")));
					
					//转输-输出
					for(int e=0,len=pro.length;e<len;e++)
					{
						export.setProductId(pro[e]);
						export.setAddUp(0);
						mapA = exportDao.queryOutput(export);//转输-输出
						
						exportLastYear.setProductId(pro[e]);
						exportLastYear.setAddUp(0);
						if(isYear){
							exportLastYear.setIsYearType(7);
							mapB=exportDao.queryThroughtRateOf2015(exportLastYear);
							exportLastYear.setAddUp(1);
							mapD=exportDao.queryThroughtRateOf2015(exportLastYear);
						}else{
						mapB= exportDao.queryOutput(exportLastYear);//去年转输-输出
						exportLastYear.setAddUp(1);
						mapD= exportDao.queryOutput(exportLastYear);//去年转输-输出-累计
						}
						exportLastAcc.setProductId(pro[e]);
						mapC= exportDao.queryOutput(exportLastAcc);//转输-输出-累计
						
						
						
						sheet.getRow(39+e).getCell(1).setCellValue(FormatUtils.getDoubleValue(mapA.get("totalNum")));
						sheet.getRow(39+e).getCell(2).setCellValue(FormatUtils.getDoubleValue(mapB.get("totalNum")));
						sheet.getRow(39+e).getCell(4).setCellValue(FormatUtils.getDoubleValue(mapA.get("shipNum")));
						sheet.getRow(39+e).getCell(5).setCellValue(FormatUtils.getDoubleValue(mapB.get("shipNum")));
						sheet.getRow(39+e).getCell(7).setCellValue(FormatUtils.getDoubleValue(mapC.get("totalNum")));
						sheet.getRow(39+e).getCell(8).setCellValue(FormatUtils.getDoubleValue(mapC.get("shipNum")));
						sheet.getRow(39+e).getCell(10).setCellValue(FormatUtils.getDoubleValue(mapD.get("totalNum")));
						
					}
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}).getWorkbook();
	}


	/**
	 * 管道运输通过明细表导出到Excel
	 * @Title:exportPipe
	 * @Description:
	 * @param request
	 * @param type
	 * @param params
	 * @param report
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public HSSFWorkbook exportPipe(HttpServletRequest request, String type,String params, final ReportDto report) throws OAException 
	{
		return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/pipeTransport.xls", new CallBack() {
			@Override
			public void setSheetValue(HSSFSheet sheet) 
			{
				try 
				{
					sheet.setForceFormulaRecalculation(true);
					report.setModule(3);
					sheet.getRow(0).getCell(0).setCellValue(report.getStartTime().substring(0, 4)+"年"+report.getStartTime().substring(5, 7)+"月长江石化管道运输通过明细表");
					List<Map<String,Object>> data = reportDao.list(report, null);
					String arrivalDate ,startDay,startTime,endDay,endTime;
					Map<String,Object> map;
					if(data != null && data.size() > 0)
					{
						for(int i=0,len=data.size();i<len;i++)
						{
							int item=i+4;
							if(i!=0 && i!=len)
							{
								HSSFRow itemRow=sheet.createRow(item);
								itemRow.setHeight(sheet.getRow(4).getHeight());
								for(int m=0;m<13;m++)
								{
									itemRow.createCell(m);
									itemRow.getCell(m).setCellStyle(sheet.getRow(4).getCell(m).getCellStyle());
								}
							}
							
							 map= (Map<String, Object>) data.get(i);
							if(FormatUtils.getStringValue(map.get("startTime")).length()==19){
								arrivalDate=map.get("startTime").toString().substring(5,10);
								startDay=map.get("startTime").toString().substring(8,10);
								startTime=map.get("startTime").toString().substring(11,16);
								
							}else{
								arrivalDate="";startDay="";startTime="";
							}
							if(FormatUtils.getStringValue(map.get("leaveTime")).length()==19){
								endDay=map.get("leaveTime").toString().substring(8,10);
								endTime=map.get("leaveTime").toString().substring(11,16);
							}else{
								endDay="";endTime="";
							}
							sheet.getRow(item).getCell(0).setCellValue(FormatUtils.getDoubleValue(i+1));
							sheet.getRow(item).getCell(1).setCellValue(arrivalDate);
							sheet.getRow(item).getCell(2).setCellValue(startDay);
							sheet.getRow(item).getCell(3).setCellValue(startTime);
							sheet.getRow(item).getCell(4).setCellValue(endDay);
							sheet.getRow(item).getCell(5).setCellValue(endTime);
							sheet.getRow(item).getCell(6).setCellValue(FormatUtils.getStringValue(map.get("berthName")));
							sheet.getRow(item).getCell(7).setCellValue(FormatUtils.getStringValue(map.get("cargoCode")));
							sheet.getRow(item).getCell(8).setCellValue(FormatUtils.getStringValue(map.get("clientName")));
							sheet.getRow(item).getCell(9).setCellValue(FormatUtils.getStringValue(map.get("productName")));
							sheet.getRow(item).getCell(10).setCellValue(FormatUtils.getDoubleValue(map.get("goodsPlan")));
							sheet.getRow(item).getCell(11).setCellValue(FormatUtils.getStringValue(map.get("shipName")));
							sheet.getRow(item).getCell(12).setCellValue(FormatUtils.getStringValue(map.get("description")));
						}
					}
				} 
				catch (OAException e) 
				{
					e.printStackTrace();
				}
			}
		}).getWorkbook();
	}
	public Map<String,Map<String,Object>> getModelBerthMap(int type){
		Map<String,Map<String,Object>> map=new HashMap<String,Map<String,Object>>();
		Map<String,Object> itemMap1=new HashMap<String,Object>();
		itemMap1.put("count", 0);
		itemMap1.put("num", 0);
		Map<String,Object> itemMap2=new HashMap<String,Object>();
		itemMap2.put("count", 0);
		itemMap2.put("num", 0);
		Map<String,Object> itemMap3=new HashMap<String,Object>();
		itemMap3.put("count", 0);
		itemMap3.put("num", 0);
		Map<String,Object> itemMap4=new HashMap<String,Object>();
		itemMap4.put("count", 0);
		itemMap4.put("num", 0);
		map.put("1", itemMap1);
		map.put("2", itemMap2);
		map.put("6", itemMap3);
		map.put("7", itemMap4);
		if(type==1){
		Map<String,Object> itemMap5=new HashMap<String,Object>();
		itemMap5.put("count", 0);
		itemMap5.put("num", 0);
		Map<String,Object> itemMap6=new HashMap<String,Object>();
		itemMap6.put("count", 0);
		itemMap6.put("num", 0);
		Map<String,Object> itemMap7=new HashMap<String,Object>();
		itemMap7.put("count", 0);
		itemMap7.put("num", 0);
		map.put("3", itemMap5);
		map.put("4", itemMap6);
		map.put("5", itemMap7);
		}
		
		
		
		
		return map;
	}
	//入库数据泊位
	public Map<String,Map<String,Object>> getBerthMap( Map<String,Map<String,Object>> map,List<Map<String,Object>> dataList){
		String berthId="";
		if(dataList!=null&&dataList.size()>0){
		for(int i=0;i<dataList.size();i++){
			berthId=dataList.get(i).get("berthId").toString();
			map.get(berthId).put("count",Common.add(map.get(berthId).get("count"), dataList.get(i).get("count"), 0));
			map.get(berthId).put("num",Common.add(map.get(berthId).get("num"),dataList.get(i).get("num"), 3));
		}
		}
		return map;
		
	}
    //分流台账导出excel
	@Override
	public HSSFWorkbook outBoundBook(HttpServletRequest request, String type, final ReportDto report) throws OAException {

		return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/outboundbook.xls", new CallBack() {
			@Override
			public void setSheetValue(HSSFSheet sheet) 
			{
			try{
				
			
				if(!Common.isNull(report.getStartTime())&&report.getStartTime().length()>7){
									sheet.getRow(0).getCell(0).setCellValue(report.getStartTime().substring(0, 4)+"年"+report.getStartTime().substring(5, 7)+"月分流台账");					
								}else{
									Calendar rightNow = Calendar.getInstance();
									sheet.getRow(0).getCell(0).setCellValue(rightNow.get(Calendar.YEAR)+"年分流台账");
								}

				
				
				List<Object> data=reportDataService.getOutBoundBook(report,new PageView(0, 0)).getData();
				int item=2,reginItem,itemChildrenSize;
				HSSFRow itemRow = null;
				Map<String, Object> itemMapData;
				List<Map<String, Object>> itemOutFlowData;//多个罐的数据
				List<Map<String, Object>>  itemTubeNames;//多个管线
				List<Map<String, Object>>  itemClientNames;//多个货主
				List<Map<String,Integer>> region=new ArrayList<Map<String,Integer>>();
				Map<String,Integer> regionMap;
				 if(data!=null&&data.size()>0){
					 int size=data.size();
					for(int i=0;i<size;i++){
					 if(i!=0){
						itemRow=sheet.createRow(item);
						itemRow.setHeight(sheet.getRow(2).getHeight());
						for(int j=0;j<36;j++){
							itemRow.createCell(j).setCellStyle(sheet.getRow(2).getCell(j).getCellStyle());
						}
					 }else{
						 itemRow=sheet.getRow(item);
					 }
					 //初始化数据
					 itemMapData=(Map<String, Object>) data.get(i);
					 itemOutFlowData=(List<Map<String, Object>>) itemMapData.get("outFlowData");
					 itemTubeNames=(List<Map<String, Object>>) itemMapData.get("tubeNames");
					 itemClientNames=(List<Map<String, Object>>) itemMapData.get("clientName");
					 itemRow.getCell(0).setCellValue((i+1));//序号
					 itemRow.getCell(1).setCellValue(FormatUtils.getStringValue(itemMapData.get("shipName")));//船名
					 itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(itemMapData.get("productName")));//货品
					 itemRow.getCell(3).setCellValue(FormatUtils.getDoubleValue(itemMapData.get("totalNum")));//开票数
					 itemRow.getCell(5).setCellValue(FormatUtils.getStringValue(itemMapData.get("tubeStatus")));//管线状态
					 itemRow.getCell(6).setCellValue(getMapString(itemTubeNames,"tubeName"));//管线
					 itemRow.getCell(7).setCellValue(FormatUtils.getStringValue(itemMapData.get("berthName")));//泊位
					 itemRow.getCell(8).setCellValue(FormatUtils.getStringValue(itemMapData.get("arrivalTime")));//到港时间
					 itemRow.getCell(9).setCellValue(FormatUtils.getStringValue(itemMapData.get("openPump")));//开泵时间
					 itemRow.getCell(10).setCellValue(FormatUtils.getStringValue(itemMapData.get("stopPump")));//停泵时间
					 itemRow.getCell(11).setCellValue(FormatUtils.getStringValue(itemMapData.get("leaveTime")));//离港时间
					 itemRow.getCell(25).setCellValue(FormatUtils.getStringValue(itemMapData.get("outboundPlace")));//去向
					 itemRow.getCell(26).setCellValue(FormatUtils.getStringValue(itemMapData.get("user1")));//计量人员
					 itemRow.getCell(27).setCellValue(FormatUtils.getStringValue(itemMapData.get("user2")));//复合人员
					 itemRow.getCell(28).setCellValue(getMapString(itemClientNames,"clientName"));//货主单位
					 itemRow.getCell(29).setCellValue(FormatUtils.getStringValue(itemMapData.get("evaluate")));//服务评价
					 itemRow.getCell(30).setCellValue(FormatUtils.getStringValue(itemMapData.get("evaluateUserName")));//评价人
					 itemRow.getCell(31).setCellValue(FormatUtils.getStringValue(itemMapData.get("status")));//审核状态
					 itemRow.getCell(32).setCellValue(FormatUtils.getStringValue(itemMapData.get("comment")));//备注
					 itemRow.getCell(34).setCellType(HSSFCell.CELL_TYPE_FORMULA);
					 itemRow.getCell(35).setCellType(HSSFCell.CELL_TYPE_FORMULA);
					 itemRow.getCell(33).setCellType(HSSFCell.CELL_TYPE_FORMULA);
					 
					 itemRow.getCell(34).setCellFormula("IF(L"+(item+1)+"=\"\",\"\",IF(I"+(item+1)+"=\"\",\"\",(L"+(item+1)+"-I"+(item+1)+")*24))");
					 itemRow.getCell(35).setCellFormula("IF(K"+(item+1)+"=\"\",\"\",IF(J"+(item+1)+"=\"\",\"\",(K"+(item+1)+"-J"+(item+1)+")*24))");
					 reginItem=item;
					 if(itemOutFlowData!=null&&itemOutFlowData.size()>0){
						 itemChildrenSize=itemOutFlowData.size();
						if(itemChildrenSize==1){
							 itemRow.getCell(4).setCellValue(FormatUtils.getStringValue(itemOutFlowData.get(0).get("tankName")));//发货罐号
							 itemRow.getCell(12).setCellValue(FormatUtils.getDoubleValue(itemOutFlowData.get(0).get("startHandLevel")));//计量前尺液位
							 itemRow.getCell(13).setCellValue(FormatUtils.getDoubleValue(itemOutFlowData.get(0).get("endHandLevel")));//计量后尺液位
							 itemRow.getCell(14).setCellValue(FormatUtils.getDoubleValue(itemOutFlowData.get(0).get("startLevel")));//机房前尺液位
							 itemRow.getCell(15).setCellValue(FormatUtils.getDoubleValue(itemOutFlowData.get(0).get("endLevel")));//机房后尺液位
							 itemRow.getCell(16).setCellValue(FormatUtils.getDoubleValue(itemOutFlowData.get(0).get("startHandWeight")));//计量前尺重量
							 itemRow.getCell(17).setCellValue(FormatUtils.getDoubleValue(itemOutFlowData.get(0).get("endHandWeight")));//计量后尺重量
							 itemRow.getCell(18).setCellValue(FormatUtils.getDoubleValue(itemOutFlowData.get(0).get("startWeight")));//机房前尺重量
							 itemRow.getCell(19).setCellValue(FormatUtils.getDoubleValue(itemOutFlowData.get(0).get("endWeight")));//机房后尺重量
							 itemRow.getCell(20).setCellValue(FormatUtils.getDoubleValue(itemOutFlowData.get(0).get("startTemperature")));//前温度
							 itemRow.getCell(21).setCellValue(FormatUtils.getDoubleValue(itemOutFlowData.get(0).get("endTemperature")));//后温度
							 itemRow.getCell(22).setCellType(HSSFCell.CELL_TYPE_FORMULA);
							 itemRow.getCell(23).setCellType(HSSFCell.CELL_TYPE_FORMULA);
							 itemRow.getCell(24).setCellType(HSSFCell.CELL_TYPE_FORMULA);	
							 itemRow.getCell(22).setCellFormula("S"+(item+1)+"-T"+(item+1));
							 itemRow.getCell(23).setCellFormula("Q"+(item+1)+"-R"+(item+1));
							 itemRow.getCell(24).setCellFormula("W"+(item+1)+"-X"+(item+1));
							 itemRow.getCell(33).setCellFormula("IF(AJ"+(item+1)+"=\"\",\"\",W"+(item+1)+"/AJ"+(item+1)+")");
							 
						}else{
							regionMap=new HashMap<String,Integer>();
							regionMap.put("start", item);
							for(int n=0;n<itemChildrenSize;n++){
								if(n!=0){
									item++;
									itemRow=sheet.createRow(item);
									itemRow.setHeight(sheet.getRow(2).getHeight());
									for(int j=0;j<36;j++){
										itemRow.createCell(j).setCellStyle(sheet.getRow(2).getCell(j).getCellStyle());
									}	
							}
								 itemRow.getCell(4).setCellValue(FormatUtils.getStringValue(itemOutFlowData.get(n).get("tankName")));//发货罐号
								 itemRow.getCell(12).setCellValue(FormatUtils.getDoubleValue(itemOutFlowData.get(n).get("startHandLevel")));//计量前尺液位
								 itemRow.getCell(13).setCellValue(FormatUtils.getDoubleValue(itemOutFlowData.get(n).get("endHandLevel")));//计量后尺液位
								 itemRow.getCell(14).setCellValue(FormatUtils.getDoubleValue(itemOutFlowData.get(n).get("startLevel")));//机房前尺液位
								 itemRow.getCell(15).setCellValue(FormatUtils.getDoubleValue(itemOutFlowData.get(n).get("endLevel")));//机房后尺液位
								 itemRow.getCell(16).setCellValue(FormatUtils.getDoubleValue(itemOutFlowData.get(n).get("startHandWeight")));//计量前尺重量
								 itemRow.getCell(17).setCellValue(FormatUtils.getDoubleValue(itemOutFlowData.get(n).get("endHandWeight")));//计量后尺重量
								 itemRow.getCell(18).setCellValue(FormatUtils.getDoubleValue(itemOutFlowData.get(n).get("startWeight")));//机房前尺重量
								 itemRow.getCell(19).setCellValue(FormatUtils.getDoubleValue(itemOutFlowData.get(n).get("endWeight")));//机房后尺重量
								 itemRow.getCell(20).setCellValue(FormatUtils.getDoubleValue(itemOutFlowData.get(n).get("startTemperature")));//前温度
								 itemRow.getCell(21).setCellValue(FormatUtils.getDoubleValue(itemOutFlowData.get(n).get("endTemperature")));//后温度
								 itemRow.getCell(22).setCellType(HSSFCell.CELL_TYPE_FORMULA);
								 itemRow.getCell(23).setCellType(HSSFCell.CELL_TYPE_FORMULA);
								 itemRow.getCell(24).setCellType(HSSFCell.CELL_TYPE_FORMULA);	
								 itemRow.getCell(22).setCellFormula("S"+(item+1)+"-T"+(item+1));
								 itemRow.getCell(23).setCellFormula("Q"+(item+1)+"-R"+(item+1));
								 itemRow.getCell(24).setCellFormula("W"+(item+1)+"-X"+(item+1));
						}
							regionMap.put("end", item);
							sheet.getRow(reginItem).getCell(33).setCellFormula("IF(AJ"+(reginItem+1)+"=\"\",\"\",SUM(W"+(regionMap.get("start")+1)+":W"+(regionMap.get("end")+1)+")/AJ"+(reginItem+1)+")");
							region.add(regionMap);
					 }
					 item++;	
					}
					}
				 }
				 int[] col={0,1,2,3,5,6,7,8,9,10,11,25,26,27,28,29,30,31,32,33,34,35};
				 if(region!=null){
						for(int j=0;j<region.size();j++){
							for(int i=0;i<22;i++){
								sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)col[i], region.get(j).get("end"), (short)col[i]));    
							}
						}}  
				 sheet.setForceFormulaRecalculation(true);
				}catch (OAException e) 
				{
					e.printStackTrace();
				}
			}
		}).getWorkbook();
	
	};
	
	public String  getMapString(List<Map<String,Object>> list,String key){
		String result="";
		if(list!=null&&list.size()>0){
			for(Map<String, Object> map:list){
                  result+=map.get(key).toString()+",";				
			}
			return result.substring(0,result.length()-1);
		}else{
		   return result;	
		}
		
	}
	
	@Override
	public HSSFWorkbook inBoundBook(HttpServletRequest request, String type,
			final ReportDto report) throws OAException {
		return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/inboundbook.xls", new CallBack() {
			@Override
			public void setSheetValue(HSSFSheet sheet) 
			{
			try{
				List<Object> data=operationLogService.getOperationLogList(Long.parseLong(report.getStartTime()), Long.parseLong(report.getEndTime()),new PageView(0,0)).getData();
				int item=2;
				HSSFRow itemRow = null;
				Map<String, Object> itemMapData;
				List<String> tankCode;//多个罐的数据
				List<String> startLevel;//前尺-液位
				List<String> endLevel;//后尺-液位
				List<String> startWeight;//前尺-重量
				List<String> endWeight;//后尺-重量
				List<String> startTemperature;//前尺-温度
				List<String> endTemperature;//后尺-温度
				List<String> realAmount;//机房实收
				List<String> measureAmount;//计量实收
				List<String> differAmount;//实收差异
				List<String> notificationTime;//验证时间
				List<String> notificationNum;//报告号
				List<String> notification;//验证结论
				List<String> notificationUserName;//验证人
				
				 List<Map<String,Integer>> region2=new ArrayList<Map<String,Integer>>();
					
				Map<String,Integer> regionMap;
				Map<String,Integer> regionMap1;
				
				 if(data!=null&&data.size()>0){
					 int size=data.size();
					for(int i=0;i<size;i++){
						List<Map<String,Integer>> region=new ArrayList<Map<String,Integer>>();
						List<Map<String,Integer>> region1=new ArrayList<Map<String,Integer>>();
						
					 if(i!=0){
						itemRow=sheet.createRow(item);
						itemRow.setHeight(sheet.getRow(2).getHeight());
						for(int j=0;j<28;j++){
							itemRow.createCell(j).setCellStyle(sheet.getRow(2).getCell(j).getCellStyle());
						}
					 }else{
						 itemRow=sheet.getRow(item);
					 }
					 //初始化数据
					 itemMapData=(Map<String, Object>) data.get(i);
					 
					 tankCode=(List<String>) itemMapData.get("tankCode");
					 startLevel=(List<String>) itemMapData.get("startLevel");
					 endLevel=(List<String>) itemMapData.get("endLevel");
					 startWeight=(List<String>) itemMapData.get("startWeight");
					 endWeight=(List<String>) itemMapData.get("endWeight");
					 startTemperature=(List<String>) itemMapData.get("startTemperature");
					 endTemperature=(List<String>) itemMapData.get("endTemperature");
					 realAmount=(List<String>) itemMapData.get("realAmount");
					 measureAmount=(List<String>) itemMapData.get("measureAmount");
					 differAmount=(List<String>) itemMapData.get("differAmount");
					 notificationTime=(List<String>) itemMapData.get("notificationTime");
					 notificationNum=(List<String>) itemMapData.get("notificationNum");
					 notification=(List<String>) itemMapData.get("notification");
					 notificationUserName=(List<String>) itemMapData.get("notificationUserName");
					 
					 String arrivalTime = "";
					 String openPump ="";
					 String stopPump = "";
					 String tearPipeTime = "";
					 String leaveTime = "";
					 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					 if(!Common.empty(itemMapData.get("arrivalTime"))&&Long.parseLong(itemMapData.get("arrivalTime").toString())!=0){
						 
						 arrivalTime= sdf.format(new Date(Long.parseLong(itemMapData.get("arrivalTime").toString())*1000));
					 }
					 
					 if(!Common.empty(itemMapData.get("openPump"))&&Long.parseLong(itemMapData.get("openPump").toString())!=0){
						 
						 openPump= sdf.format(new Date(Long.parseLong(itemMapData.get("openPump").toString())*1000));
					 }
					 
					 if(!Common.empty(itemMapData.get("stopPump"))&&Long.parseLong(itemMapData.get("stopPump").toString())!=0){
						 
						 stopPump= sdf.format(new Date(Long.parseLong(itemMapData.get("stopPump").toString())*1000));
					 }
					 
					 if(!Common.empty(itemMapData.get("tearPipeTime"))&&Long.parseLong(itemMapData.get("tearPipeTime").toString())!=0){
						 
						 tearPipeTime= sdf.format(new Date(Long.parseLong(itemMapData.get("tearPipeTime").toString())*1000));
					 }
					 
					 if(!Common.empty(itemMapData.get("leaveTime"))&&Long.parseLong(itemMapData.get("leaveTime").toString())!=0){
						 
						 leaveTime= sdf.format(new Date(Long.parseLong(itemMapData.get("leaveTime").toString())*1000));
					 }
					 
					 itemRow.getCell(0).setCellValue(FormatUtils.getStringValue(itemMapData.get("shipName")));//序号
					 itemRow.getCell(1).setCellValue(FormatUtils.getStringValue(itemMapData.get("productName")));//品名
					 itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(itemMapData.get("tankAmount")));//卸货数量
					 itemRow.getCell(3).setCellValue(FormatUtils.getStringValue(itemMapData.get("tubeName")));//工艺管线
					 itemRow.getCell(4).setCellValue(FormatUtils.getStringValue(itemMapData.get("berthName")));//泊位
					 
						
						
					 itemRow.getCell(5).setCellValue(FormatUtils.getStringValue(arrivalTime));
					 itemRow.getCell(6).setCellValue(FormatUtils.getStringValue(openPump));
					 itemRow.getCell(7).setCellValue(FormatUtils.getStringValue(stopPump));
					 itemRow.getCell(8).setCellValue(FormatUtils.getStringValue(tearPipeTime));
					 itemRow.getCell(9).setCellValue(FormatUtils.getStringValue(leaveTime));
					 
					 itemRow.getCell(20).setCellValue(FormatUtils.getStringValue(itemMapData.get("clientName")));//管线状态
					 itemRow.getCell(21).setCellValue(FormatUtils.getStringValue(itemMapData.get("evaluate")));//管线状态
					 itemRow.getCell(22).setCellValue(FormatUtils.getStringValue(itemMapData.get("evaluateUserName")));//管线状态
					 itemRow.getCell(23).setCellValue(FormatUtils.getStringValue(itemMapData.get("description")));//管线状态
							regionMap=new HashMap<String,Integer>();
							int tankCount=0;
							regionMap.put("start", tankCount+item);
							for(int n=0;n<tankCode.size();n++){
								if(n!=0){
									tankCount+=1;
									itemRow=sheet.createRow(item+tankCount);
									
									itemRow.setHeight(sheet.getRow(2).getHeight());
									for(int j=0;j<28;j++){
										itemRow.createCell(j).setCellStyle(sheet.getRow(2).getCell(j).getCellStyle());
									}	
							}
								
								 itemRow.getCell(10).setCellValue(FormatUtils.getStringValue(tankCode.get(n)));//发货罐号
								 itemRow.getCell(11).setCellValue(FormatUtils.getDoubleValue(startLevel.get(n)));//计量前尺液位
								 itemRow.getCell(12).setCellValue(FormatUtils.getDoubleValue(endLevel.get(n)));//计量后尺液位
								 itemRow.getCell(13).setCellValue(FormatUtils.getDoubleValue(startWeight.get(n)));//机房前尺液位
								 itemRow.getCell(14).setCellValue(FormatUtils.getDoubleValue(endWeight.get(n)));//机房后尺液位
								 itemRow.getCell(15).setCellValue(FormatUtils.getDoubleValue(startTemperature.get(n)));//计量前尺重量
								 itemRow.getCell(16).setCellValue(FormatUtils.getDoubleValue(endTemperature.get(n)));//计量后尺重量
								 itemRow.getCell(17).setCellValue(FormatUtils.getDoubleValue(realAmount.get(n)));//机房前尺重量
								 itemRow.getCell(18).setCellValue(FormatUtils.getDoubleValue(measureAmount.get(n)));//机房后尺重量
								 itemRow.getCell(19).setCellValue(FormatUtils.getDoubleValue(differAmount.get(n)));//前温度
						}
							regionMap.put("end", tankCount+item);
							region.add(regionMap);
							int notifyCount=0;
							regionMap1=new HashMap<String,Integer>();
							regionMap1.put("start", item+notifyCount);
							for(int n=0;n<notificationTime.size();n++){
								if(n!=0){
									notifyCount+=1;
									if(notifyCount<=tankCount){
										
										itemRow=sheet.getRow(item+notifyCount);
									}else{
										
										itemRow=sheet.createRow(item+notifyCount);
									}
									itemRow.setHeight(sheet.getRow(2).getHeight());
									for(int j=0;j<28;j++){
										if(notifyCount<=tankCount){
											itemRow.getCell(j).setCellStyle(sheet.getRow(2).getCell(j).getCellStyle());
											
										}else{
											itemRow.createCell(j).setCellStyle(sheet.getRow(2).getCell(j).getCellStyle());
											
										}
									}	
							}else{
								itemRow=sheet.getRow(item);
							}
								 itemRow.getCell(24).setCellValue(FormatUtils.getStringValue(notificationTime.get(n)));//后温度
								 itemRow.getCell(25).setCellValue(FormatUtils.getStringValue(notificationNum.get(n)));
								 itemRow.getCell(26).setCellValue(FormatUtils.getStringValue(notification.get(n)));
								 itemRow.getCell(27).setCellValue(FormatUtils.getStringValue(notificationUserName.get(n)));	
						}
							regionMap1.put("end", notifyCount+item);
							region1.add(regionMap1);
							
							if(tankCount>notifyCount){
								item+=tankCount;
								region2.add(regionMap);
							}else{
								item+=notifyCount;
								region2.add(regionMap1);
							}
					 item++;	
					}
				 }
				 int[] col={0,1,2,3,4,5,6,7,8,9,20,21,22,23};
				 if(region2!=null){
						for(int j=0;j<region2.size();j++){
							for(int i=0;i<14;i++){
								sheet.addMergedRegion(new Region(region2.get(j).get("start"),(short)col[i], region2.get(j).get("end"), (short)col[i]));    
							}
						}}  
				 sheet.setForceFormulaRecalculation(true);
				}catch (OAException e) 
				{
					e.printStackTrace();
				}
			}
		}).getWorkbook();
	
	}

	/**
	 * @Title exportPumpShedRotation
	 * @Descrption:TODO
	 * @param:@param request
	 * @param:@param report
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年9月9日上午10:16:04
	 * @throws
	 */
	@Override
	public HSSFWorkbook exportPumpShedRotation(HttpServletRequest request, final ReportDto report) throws OAException {
		return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/pumpShedRotation.xls", new CallBack(){

			@Override
			public void setSheetValue(HSSFSheet sheet) {
				try {
					sheet.getRow(0).getCell(0).setCellValue(report.getStartTime().replace('-', '年')+"月份泵棚作业量统计");
				report.setStartTime(DateUtils.getFirstDayOfMonth(report.getStartTime()));
				report.setEndTime(DateUtils.getLastDayOfMonth(report.getEndTime()));
				List<Map<String, Object>> data=reportDao.getPumpShedRotation(report);
				if(data!=null&&data.size()>0){
					for(int i=0,len=data.size();i<len;i++)
						sheet.getRow(2+(i%5)).getCell((i/5)+1).setCellValue(FormatUtils.getDoubleValue(data.get(i).get("actNum")));
				}
				 sheet.setForceFormulaRecalculation(true);
			} catch (OAException e) {
				e.printStackTrace();
			}
				
			}
			
		}).getWorkbook();
	}
	
}