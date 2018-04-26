package com.skycloud.oa.outbound.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hslf.model.Sheet;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dao.VehicleDeliveryStatementDao;
import com.skycloud.oa.outbound.dto.VehicleDeliveryStatementDto;
import com.skycloud.oa.outbound.service.VehicleDeliveryStatementService;
import com.skycloud.oa.report.utils.DateUtils;
import com.skycloud.oa.utils.ExcelUtil;
import com.skycloud.oa.utils.ExcelUtil.CallBack;
import com.skycloud.oa.utils.ExcelUtil.CallBackSheets;
import com.skycloud.oa.utils.OaMsg;
import com.skycloud.oa.utils.PoiUtil;
import com.sun.jmx.snmp.Timestamp;

/**
 * 
 * <p>台账管理---流量报表</p>
 * @ClassName:VehicleDeliveryStatementServiceImpl
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年5月27日 下午10:51:51
 *
 */
@Service
public class VehicleDeliveryStatementServiceImpl implements VehicleDeliveryStatementService
{
	/**
	 * 记录日志
	 */
	private static Logger LOG = Logger.getLogger(VehicleDeliverWorkServiceImpl.class);
	
	/**
	 * vehicleDeliveryStatementDao
	 */
	@Autowired
	private VehicleDeliveryStatementDao vehicleDeliveryStatementDao ;
	
	/**
	 * 查询车位发货日报表信息
	 * @Title:VehicleDeliveryStatementServiceImpl
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @param pageView
	 * @return
	 * @Date:2015年5月27日 下午10:48:17
	 * @return:OaMsg 
	 * @throws
	 */
	@Override
	public OaMsg queryParkDailyStatement(VehicleDeliveryStatementDto vehicleDeliveryStatementDto,PageView pageView) 
	{
		OaMsg oaMsg = new OaMsg();
		try 
		{
			oaMsg.getData().addAll(vehicleDeliveryStatementDao.queryParkDailyStatement(vehicleDeliveryStatementDto,pageView)) ;
			oaMsg.getMap().put(Constant.TOTALRECORD,  vehicleDeliveryStatementDao.getParkDailyStatementCount(vehicleDeliveryStatementDto)+ "");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} 
		catch (Exception re) 
		{
			LOG.error("插入失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		
		return oaMsg;
	}
	
	/**
	 * 查询车位流量日报表
	 * @Title:VehicleDeliveryStatementServiceImpl
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @param pageView
	 * @return
	 * @Date:2015年5月27日 下午10:48:26
	 * @return:OaMsg 
	 * @throws
	 */
	@Override
	public OaMsg queryWeighDailyStatement(VehicleDeliveryStatementDto vehicleDeliveryStatementDto,PageView pageView) 
	{
		OaMsg oaMsg = new OaMsg();
		try 
		{
			oaMsg.getData().addAll(vehicleDeliveryStatementDao.queryWeighDailyStatement(vehicleDeliveryStatementDto,pageView)) ;

				oaMsg.getMap().put(Constant.TOTALRECORD,  vehicleDeliveryStatementDao.getWeighDailyStatementCount(vehicleDeliveryStatementDto)+ "");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} 
		catch (Exception re) 
		{
			LOG.error("插入失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		
		return oaMsg;
	}
	
	/**
	 * 查询车位发货月报表
	 * @Title:VehicleDeliveryStatementServiceImpl
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @param pageView
	 * @return
	 * @Date:2015年5月27日 下午10:49:09
	 * @return:OaMsg 
	 * @throws
	 */
	@Override
	public OaMsg queryVehicleMonthlyStatement(VehicleDeliveryStatementDto vehicleDeliveryStatementDto,PageView pageView) 
	{
		OaMsg oaMsg = new OaMsg();
		try 
		{
			oaMsg.getData().addAll(vehicleDeliveryStatementDao.queryVehicleMonthlyStatement(vehicleDeliveryStatementDto, pageView)) ;
			int count = vehicleDeliveryStatementDao.getVehicleMonthlyStatementCount(vehicleDeliveryStatementDto) ;
			if (pageView.getMaxresult() != 0) 
			{
				oaMsg.getMap().put(Constant.TOTALRECORD,  count+ "");
			}
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} 
		catch (Exception re) 
		{
			LOG.error("插入失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		
		return oaMsg;
	}

	/**
	 * 查询车位发货历史累计量报表
	 * @Title:VehicleDeliveryStatementServiceImpl
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @param pageView
	 * @return
	 * @Date:2015年5月27日 下午10:50:16
	 * @return:OaMsg 
	 * @throws
	 */
	@Override
	public OaMsg queryVehicleHistoryCumulantStatement(VehicleDeliveryStatementDto vehicleDeliveryStatementDto,PageView pageView) 
	{
		OaMsg oaMsg = new OaMsg();
		try 
		{
			oaMsg.getData().addAll(vehicleDeliveryStatementDao.queryVehicleHistoryCumulantStatement(vehicleDeliveryStatementDto, pageView)) ;
			int count = vehicleDeliveryStatementDao.getVehicleHistoryCumulantStatementCount(vehicleDeliveryStatementDto) ;
			if (pageView.getMaxresult() != 0) 
			{
				oaMsg.getMap().put(Constant.TOTALRECORD,  count+ "");
			}
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} 
		catch (Exception re) 
		{
			LOG.error("插入失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		
		return oaMsg;
	}

	/**
	 * 查询车位流量计月报总表
	 * @Title:VehicleDeliveryStatementServiceImpl
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @param pageView
	 * @return
	 * @Date:2015年5月27日 下午10:50:46
	 * @return:OaMsg 
	 * @throws
	 */
	@Override
	public OaMsg queryVehicleMonthlyTotalStatement(VehicleDeliveryStatementDto vehicleDeliveryStatementDto,PageView pageView) 
	{
		OaMsg oaMsg = new OaMsg();
		try 
		{
			oaMsg.getData().addAll(vehicleDeliveryStatementDao.queryVehicleMonthlyTotalStatement(vehicleDeliveryStatementDto, pageView)) ;
			int count = vehicleDeliveryStatementDao.getVehicleMonthlyTotalStatementCount(vehicleDeliveryStatementDto) ;
			if (pageView.getMaxresult() != 0) 
			{
				oaMsg.getMap().put(Constant.TOTALRECORD,  count+ "");
			}
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} 
		catch (Exception re) 
		{
			LOG.error("插入失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		
		return oaMsg;
	}

	/**
	 * 查询货品月发货量报表
	 * @Title:VehicleDeliveryStatementServiceImpl
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @param pageView
	 * @return
	 * @Date:2015年5月27日 下午10:51:12
	 * @return:OaMsg 
	 * @throws
	 */
	@Override
	public OaMsg queryProductMonthlyStatement(VehicleDeliveryStatementDto vehicleDeliveryStatementDto,PageView pageView) 
	{
		OaMsg oaMsg = new OaMsg();
		try 
		{
			oaMsg.getData().addAll(vehicleDeliveryStatementDao.queryProductMonthlyStatement(vehicleDeliveryStatementDto, pageView)) ;
			int count = vehicleDeliveryStatementDao.getProductMonthlyStatementCount(vehicleDeliveryStatementDto) ;
			if (pageView.getMaxresult() != 0) 
			{
				oaMsg.getMap().put(Constant.TOTALRECORD,  count+ "");
			}
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} 
		catch (Exception re) 
		{
			LOG.error("插入失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		
		return oaMsg;
	}

	@Override
	public OaMsg getTotalNum(VehicleDeliveryStatementDto vsDto)
			throws OAException {
		OaMsg oaMsg = new OaMsg();
		try 
		{
			oaMsg.getData().add(vehicleDeliveryStatementDao.getTotalNum(vsDto)) ;
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} 
		catch (Exception re) 
		{
			LOG.error("插入失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		
		return oaMsg;
	}

	@Override
	public HSSFWorkbook exportExcel(HttpServletRequest request, String type,
			final VehicleDeliveryStatementDto vsDto) {
		ExcelUtil excelUtil=null;
		//导出储品发货统计表
		if(type!=null&&Integer.valueOf(type)==1){
			excelUtil=new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/productWeightData.xls", new CallBackSheets() {
				
				@Override
				public void setSheetsValue(HSSFWorkbook workbook) {
					try{
					HSSFSheet sheet;
				
				List<Map<String,Object>> dateList=handleDateList(vsDto);
				List<Map<String,Object>> productList;
				List<Map<String,Object>> productDataList;
				Map<String,Map<String,Object>> productDataDateMap;//将数据放入map;
				HSSFCellStyle  row0,row1,row2,row3,rowItem0,rowItem1,rowItem2,rowItem3;
				int columns=0,colNum=0, j=0,monthDate=0,colWidth=0;
				//初始sheet名称，按月份
				if(dateList!=null&&dateList.size()>0){
				    for(int i=0;i<dateList.size();i++){
				    	if(i==0){
				    		workbook.setSheetName(0, getStringValue(dateList.get(0).get("dateName")));  
				    	}else{
				    		workbook.createSheet(getStringValue(dateList.get(i).get("dateName")));
				    		PoiUtil.copySheet(workbook.getSheetAt(i), workbook.getSheetAt(0), workbook);
				    	}
				    }	
				   }
				int sheetNum=workbook.getNumberOfSheets();
				
				for(int i=0;i<sheetNum;i++){
					sheet=workbook.getSheet(getStringValue(dateList.get(i).get("dateName")));
					
					sheet.getRow(0).getCell(0).setCellValue(getStringValue(dateList.get(i).get("dateName"))+"车发货品统计表");
					row0=sheet.getRow(0).getCell(2).getCellStyle();
					row1=sheet.getRow(1).getCell(2).getCellStyle();
					row2=sheet.getRow(2).getCell(2).getCellStyle();
					row3=sheet.getRow(3).getCell(2).getCellStyle();
					rowItem0=sheet.getRow(0).getCell(1).getCellStyle();
					rowItem1=sheet.getRow(1).getCell(1).getCellStyle();
					rowItem2=sheet.getRow(2).getCell(1).getCellStyle();
					rowItem3=sheet.getRow(3).getCell(1).getCellStyle();
					colWidth=sheet.getColumnWidth(1);
					monthDate=getMonthDateNum(getStringValue(dateList.get(i).get("date")));
					
					
					productList=vehicleDeliveryStatementDao.getProductNameMsg(getStringValue(dateList.get(i).get("date")));
					
					//设置横向货品名称
					if(productList!=null&&productList.size()>0){
						//保存每日 字段的样式
						columns=productList.size();
						for( j=0;j<=columns;j++){
							if(j==0){
							sheet.getRow(1).getCell(2*j+1).setCellStyle(rowItem1);
							sheet.getRow(1).getCell(2*j+2).setCellStyle(rowItem1);
							sheet.getRow(2).getCell(2*j+1).setCellStyle(rowItem2);
							sheet.getRow(2).getCell(2*j+2).setCellStyle(rowItem2);
							sheet.getRow(3).getCell(2*j+1).setCellStyle(rowItem3);
							sheet.getRow(3).getCell(2*j+2).setCellStyle(rowItem3);
							sheet.getRow(1).getCell(2*j+1).setCellValue(getStringValue(productList.get(j).get("productName")));
							sheet.getRow(2).getCell(2*+1).setCellValue("发货量");
							sheet.getRow(2).getCell(2*j+2).setCellValue("车次");
							}else if(j!=columns){
								sheet.setColumnWidth(2*j+1, colWidth);
								sheet.setColumnWidth(2*j+2, colWidth);
								sheet.getRow(0).createCell(2*j+1).setCellStyle(rowItem0);
								sheet.getRow(0).createCell(2*j+2).setCellStyle(rowItem0);
								sheet.getRow(1).createCell(2*j+1).setCellStyle(rowItem1);
								sheet.getRow(1).createCell(2*j+2).setCellStyle(rowItem1);
								sheet.getRow(2).createCell(2*j+1).setCellStyle(rowItem2);
								sheet.getRow(2).createCell(2*j+2).setCellStyle(rowItem2);
								sheet.getRow(3).createCell(2*j+1).setCellStyle(rowItem3);
								sheet.getRow(3).createCell(2*j+2).setCellStyle(rowItem3);
								sheet.getRow(1).getCell(2*j+1).setCellValue(getStringValue(productList.get(j).get("productName")));
								sheet.getRow(2).getCell(2*j+1).setCellValue("发货量");
								sheet.getRow(2).getCell(2*j+2).setCellValue("车次");
							}else if(j==columns){
								sheet.setColumnWidth(2*j+1, colWidth);
								sheet.setColumnWidth(2*j+2, colWidth);
								sheet.getRow(0).createCell(2*j+1).setCellStyle(rowItem0);
								sheet.getRow(0).createCell(2*j+2).setCellStyle(row0);
								sheet.getRow(1).createCell(2*j+1).setCellStyle(rowItem1);
								sheet.getRow(1).createCell(2*j+2).setCellStyle(row1);
								sheet.getRow(2).createCell(2*j+1).setCellStyle(rowItem1);
								sheet.getRow(2).createCell(2*j+2).setCellStyle(row2);
								sheet.getRow(2).createCell(2*j+1).setCellStyle(rowItem2);
								sheet.getRow(2).createCell(2*j+2).setCellStyle(row2);
								sheet.getRow(3).createCell(2*j+1).setCellStyle(rowItem3);
								sheet.getRow(3).createCell(2*j+2).setCellStyle(row3);
								sheet.getRow(1).getCell(2*j+1).setCellValue("每日 ");
								sheet.getRow(2).getCell(2*j+1).setCellValue("总车次");
								sheet.getRow(2).getCell(2*j+2).setCellValue("总发货量");
								colNum=2*j+2;
							}
						}
						//向下移动
						sheet.shiftRows(3, 3, monthDate,true,false);
						for( j=0;j<monthDate;j++){
							HSSFRow itemRow=sheet.createRow(3+j);
				    		itemRow.setHeight(sheet.getRow(2).getHeight());
							for(int m=0;m<=colNum;m++){
								itemRow.createCell(m);
								itemRow.getCell(m).setCellStyle(sheet.getRow(2).getCell(m).getCellStyle());
								if(m==0){
								 itemRow.getCell(0).setCellValue(j+1);
								}
							}
						}
						sheet.addMergedRegion(new Region(0,(short)0, 0,(short)colNum));
						for(j=0;j<colNum/2;j++){
							sheet.addMergedRegion(new Region(1,(short)(2*j+1),1,(short)(2*j+2)));  
							sheet.getRow(3+monthDate).getCell(2*j+1).setCellType(HSSFCell.CELL_TYPE_FORMULA);
							sheet.getRow(3+monthDate).getCell(2*j+2).setCellType(HSSFCell.CELL_TYPE_FORMULA);
							
							if(j<colNum/2-1){
							productDataList=vehicleDeliveryStatementDao.getProductData(new VehicleDeliveryStatementDto(Integer.valueOf(productList.get(j).get("productId").toString()),getStringValue(dateList.get(i).get("date"))));
							productDataDateMap=getDataDateMap(getDataDateMap(monthDate),productDataList);
							for(int n=1;n<=monthDate;n++){
								sheet.getRow(2+n).getCell(2*j+1).setCellValue(getDoubleValue(productDataDateMap.get(n+"").get("actualNum")));
								sheet.getRow(2+n).getCell(2*j+2).setCellValue(getDoubleValue(productDataDateMap.get(n+"").get("count")));
							}
							sheet.getRow(3+monthDate).getCell(2*j+1).setCellFormula(getSumFormulaB(j,3+monthDate));
							sheet.getRow(3+monthDate).getCell(2*j+2).setCellFormula(getSumFormulaA(j,3+monthDate));
							}else if(j==colNum/2-1){
								sheet.getRow(3+monthDate).getCell(2*j+1).setCellFormula(getSumFormulaB(j,3+monthDate));
								sheet.getRow(3+monthDate).getCell(2*j+2).setCellFormula(getSumFormulaA(j,3+monthDate));
								for(int n=1;n<=monthDate;n++){
									sheet.getRow(2+n).getCell(2*j+1).setCellType(HSSFCell.CELL_TYPE_FORMULA);
									sheet.getRow(2+n).getCell(2*j+2).setCellType(HSSFCell.CELL_TYPE_FORMULA);
									sheet.getRow(2+n).getCell(2*j+1).setCellFormula(getStringFormulaA(j,3+n));
									sheet.getRow(2+n).getCell(2*j+2).setCellFormula(getStringFormulaB(j,3+n));
								}
								
							}
						}
						sheet.setForceFormulaRecalculation(true);
					}

				}		
					} 
					catch (OAException e) 
					{
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
			return excelUtil.getWorkbookWithSheets();
			
		}else if(type!=null&&Integer.valueOf(type)==2){
               excelUtil=new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/inportWeightData.xls", new CallBackSheets() {
				
				@Override
				public void setSheetsValue(HSSFWorkbook workbook) {
					try{
					HSSFSheet sheet;
				String dateTime=vsDto.getYear()+"-"+String.format("%02d", Integer.valueOf(vsDto.getMonth()));
				List<Map<String,Object>> productList=vehicleDeliveryStatementDao.getProductNameMsg(dateTime);
				List<Map<String,Object>> inPortList;
				List<Map<String,Object>> inPortDataList;
				Map<String,Map<String,Object>> inPortDataDateMap;//将数据放入map;
				HSSFCellStyle  row0,row1,row2,row3,rowItem0,rowItem1,rowItem2,rowItem3;
				int columns=0,colNum=0, j=0,monthDate=0,colWidth=0;
				//初始sheet名称，按月份
				if(productList!=null&&productList.size()>0){
				    for(int i=0;i<productList.size();i++){
				    	if(i==0){
				    		workbook.setSheetName(0, getStringValue(productList.get(0).get("productName")));  
				    	}else{
				    		workbook.createSheet(getStringValue(productList.get(i).get("productName")));
				    		PoiUtil.copySheet(workbook.getSheetAt(i), workbook.getSheetAt(0), workbook);
				    	}
				    }	
				   }
				int sheetNum=workbook.getNumberOfSheets();
				
				for(int i=0;i<sheetNum;i++){
					sheet=workbook.getSheet(getStringValue(productList.get(i).get("productName")));
					
					sheet.getRow(0).getCell(0).setCellValue(getStringValue(productList.get(i).get("dateName"))+"车发发货口统计表");
					row0=sheet.getRow(0).getCell(2).getCellStyle();
					row1=sheet.getRow(1).getCell(2).getCellStyle();
					row2=sheet.getRow(2).getCell(2).getCellStyle();
					row3=sheet.getRow(3).getCell(2).getCellStyle();
					rowItem0=sheet.getRow(0).getCell(1).getCellStyle();
					rowItem1=sheet.getRow(1).getCell(1).getCellStyle();
					rowItem2=sheet.getRow(2).getCell(1).getCellStyle();
					rowItem3=sheet.getRow(3).getCell(1).getCellStyle();
					colWidth=sheet.getColumnWidth(1);
					monthDate=getMonthDateNum(dateTime);
					
					inPortList=vehicleDeliveryStatementDao.getInportMsg(dateTime,Integer.valueOf(productList.get(i).get("productId").toString()));
					
					//设置横向货品名称
					if(inPortList!=null&&inPortList.size()>0){
						//保存每日 字段的样式
						columns=inPortList.size();
						for( j=0;j<=columns;j++){
							if(j==0){
							sheet.getRow(1).getCell(2*j+1).setCellStyle(rowItem1);
							sheet.getRow(1).getCell(2*j+2).setCellStyle(rowItem1);
							sheet.getRow(2).getCell(2*j+1).setCellStyle(rowItem2);
							sheet.getRow(2).getCell(2*j+2).setCellStyle(rowItem2);
							sheet.getRow(3).getCell(2*j+1).setCellStyle(rowItem3);
							sheet.getRow(3).getCell(2*j+2).setCellStyle(rowItem3);
							sheet.getRow(1).getCell(2*j+1).setCellValue(getStringValue(inPortList.get(j).get("inport")));
							sheet.getRow(2).getCell(2*+1).setCellValue("发货量");
							sheet.getRow(2).getCell(2*j+2).setCellValue("车次");
							}else if(j!=columns){
								sheet.setColumnWidth(2*j+1, colWidth);
								sheet.setColumnWidth(2*j+2, colWidth);
								sheet.getRow(0).createCell(2*j+1).setCellStyle(rowItem0);
								sheet.getRow(0).createCell(2*j+2).setCellStyle(rowItem0);
								sheet.getRow(1).createCell(2*j+1).setCellStyle(rowItem1);
								sheet.getRow(1).createCell(2*j+2).setCellStyle(rowItem1);
								sheet.getRow(2).createCell(2*j+1).setCellStyle(rowItem2);
								sheet.getRow(2).createCell(2*j+2).setCellStyle(rowItem2);
								sheet.getRow(3).createCell(2*j+1).setCellStyle(rowItem3);
								sheet.getRow(3).createCell(2*j+2).setCellStyle(rowItem3);
								sheet.getRow(1).getCell(2*j+1).setCellValue(getStringValue(inPortList.get(j).get("inport")));
								sheet.getRow(2).getCell(2*j+1).setCellValue("发货量");
								sheet.getRow(2).getCell(2*j+2).setCellValue("车次");
							}else if(j==columns){
								sheet.setColumnWidth(2*j+1, colWidth);
								sheet.setColumnWidth(2*j+2, colWidth);
								sheet.getRow(0).createCell(2*j+1).setCellStyle(rowItem0);
								sheet.getRow(0).createCell(2*j+2).setCellStyle(row0);
								sheet.getRow(1).createCell(2*j+1).setCellStyle(rowItem1);
								sheet.getRow(1).createCell(2*j+2).setCellStyle(row1);
								sheet.getRow(2).createCell(2*j+1).setCellStyle(rowItem1);
								sheet.getRow(2).createCell(2*j+2).setCellStyle(row2);
								sheet.getRow(2).createCell(2*j+1).setCellStyle(rowItem2);
								sheet.getRow(2).createCell(2*j+2).setCellStyle(row2);
								sheet.getRow(3).createCell(2*j+1).setCellStyle(rowItem3);
								sheet.getRow(3).createCell(2*j+2).setCellStyle(row3);
								sheet.getRow(1).getCell(2*j+1).setCellValue("合计 ");
								sheet.getRow(2).getCell(2*j+1).setCellValue("总车次");
								sheet.getRow(2).getCell(2*j+2).setCellValue("总发货量");
								colNum=2*j+2;
							}
						}
						//向下移动
						sheet.shiftRows(3, 3, monthDate,true,false);
						for( j=0;j<monthDate;j++){
							HSSFRow itemRow=sheet.createRow(3+j);
				    		itemRow.setHeight(sheet.getRow(2).getHeight());
							for(int m=0;m<=colNum;m++){
								itemRow.createCell(m);
								itemRow.getCell(m).setCellStyle(sheet.getRow(2).getCell(m).getCellStyle());
								if(m==0){
								 itemRow.getCell(0).setCellValue(j+1);
								}
							}
						}
						sheet.addMergedRegion(new Region(0,(short)0, 0,(short)colNum));
						for(j=0;j<colNum/2;j++){
							sheet.addMergedRegion(new Region(1,(short)(2*j+1),1,(short)(2*j+2)));  
							sheet.getRow(3+monthDate).getCell(2*j+1).setCellType(HSSFCell.CELL_TYPE_FORMULA);
							sheet.getRow(3+monthDate).getCell(2*j+2).setCellType(HSSFCell.CELL_TYPE_FORMULA);
							
							if(j<colNum/2-1){
							inPortDataList=vehicleDeliveryStatementDao.getInportData(new VehicleDeliveryStatementDto(inPortList.get(j).get("inport").toString(),dateTime,Integer.valueOf(productList.get(i).get("productId").toString())));
							inPortDataDateMap=getDataDateMap(getDataDateMap(monthDate),inPortDataList);
							for(int n=1;n<=monthDate;n++){
								sheet.getRow(2+n).getCell(2*j+1).setCellValue(getDoubleValue(inPortDataDateMap.get(n+"").get("actualNum")));
								sheet.getRow(2+n).getCell(2*j+2).setCellValue(getDoubleValue(inPortDataDateMap.get(n+"").get("count")));
							}
							sheet.getRow(3+monthDate).getCell(2*j+1).setCellFormula(getSumFormulaB(j,3+monthDate));
							sheet.getRow(3+monthDate).getCell(2*j+2).setCellFormula(getSumFormulaA(j,3+monthDate));
							}else if(j==colNum/2-1){

								sheet.getRow(3+monthDate).getCell(2*j+1).setCellFormula(getSumFormulaB(j,3+monthDate));
								sheet.getRow(3+monthDate).getCell(2*j+2).setCellFormula(getSumFormulaA(j,3+monthDate));
								for(int n=1;n<=monthDate;n++){
									sheet.getRow(2+n).getCell(2*j+1).setCellType(HSSFCell.CELL_TYPE_FORMULA);
									sheet.getRow(2+n).getCell(2*j+2).setCellType(HSSFCell.CELL_TYPE_FORMULA);
									sheet.getRow(2+n).getCell(2*j+1).setCellFormula(getStringFormulaA(j,3+n));
									sheet.getRow(2+n).getCell(2*j+2).setCellFormula(getStringFormulaB(j,3+n));
								}
								
							}
						}
						sheet.setForceFormulaRecalculation(true);
					}

				}		
					} 
					catch (OAException e) 
					{
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				
			});
			
			return excelUtil.getWorkbookWithSheets();
			
		}else if(type!=null&&Integer.valueOf(type)==3){//导出发货明细表
		excelUtil=new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/weightData.xls", new CallBack() {
			@Override
			public void setSheetValue(HSSFSheet sheet) 
			{
				try 
				{
				List<Map<String,Object>> data=vehicleDeliveryStatementDao.queryWeighDailyStatement(vsDto,new PageView(0, 0));
					if(data!=null&&data.size()>0){
					sheet.shiftRows(2, 2, data.size(),true,false);
					HSSFRow itemRow;
					int item=2;
					for(Map<String,Object> map : data){
						itemRow=sheet.createRow(item++);
						itemRow.setHeight(sheet.getRow(1).getHeight());
						for(int i=0;i<11;i++){
								itemRow.createCell(i);
								itemRow.getCell(i).setCellStyle(sheet.getRow(1).getCell(i).getCellStyle());				
						}
						itemRow.getCell(0).setCellValue(item-2);
						itemRow.getCell(1).setCellValue(getStringValue(map.get("serial")));
						itemRow.getCell(2).setCellValue(getStringValue(map.get("truckName")));
						itemRow.getCell(3).setCellValue(getStringValue(map.get("productName")));
						itemRow.getCell(4).setCellValue(getStringValue(map.get("tankName")));
						itemRow.getCell(5).setCellValue(getStringValue(map.get("inPort")));
						itemRow.getCell(6).setCellValue(getDoubleValue(map.get("deliverNum")));
						itemRow.getCell(7).setCellValue(getDoubleValue(map.get("actualNum")));
						itemRow.getCell(8).setCellValue(getStringValue(map.get("createTime")));
						itemRow.getCell(9).setCellValue(getStringValue(map.get("storageInfo")));
						itemRow.getCell(10).setCellValue(getStringValue(map.get("ladingEvidence")));
					}		
					sheet.getRow(data.size()+2).getCell(6).setCellType(HSSFCell.CELL_TYPE_FORMULA);
					sheet.getRow(data.size()+2).getCell(6).setCellFormula("ROUND(SUM(F03:F"+(data.size()+2)+"),3)");
					sheet.getRow(data.size()+2).getCell(7).setCellType(HSSFCell.CELL_TYPE_FORMULA);
					sheet.getRow(data.size()+2).getCell(7).setCellFormula("ROUND(SUM(G03:G"+(data.size()+2)+"),3)");
					sheet.setForceFormulaRecalculation(true);
					}
					
				} 
				catch (OAException e) 
				{
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		return excelUtil.getWorkbook();
		}else if(type!=null&&Integer.valueOf(type)==4){
			
			excelUtil=new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/tankWeightData.xls", new CallBackSheets() {
				
				@Override
				public void setSheetsValue(HSSFWorkbook workbook) {
					try{
					HSSFSheet sheet;
				String dateTime=vsDto.getYear()+"-"+String.format("%02d", Integer.valueOf(vsDto.getMonth()));
				List<Map<String,Object>> productList=null;
				if(vsDto.getProductId()!=0){
					productList=new ArrayList<Map<String,Object>>();
					Map<String, Object> map=new HashMap<String,Object>();
					map.put("productId", vsDto.getProductId());
					map.put("productName", vehicleDeliveryStatementDao.getProductNameById(vsDto.getProductId()).get("name"));
					productList.add(map);
				}
				else
					productList=vehicleDeliveryStatementDao.getProductNameMsg(dateTime);
				String clientName=null;
				if(vsDto.getClientId()!=null)
					clientName=vehicleDeliveryStatementDao.getClientNameById(vsDto.getClientId()).get("name").toString();
				List<Map<String,Object>> tankList;
				List<Map<String,Object>> tankDataList;
				Map<String,Map<String,Object>> tankDataDateMap;//将数据放入map;
				HSSFCellStyle  row0,row1,row2,row3,rowItem0,rowItem1,rowItem2,rowItem3;
				int columns=0,colNum=0, j=0,monthDate=0,colWidth=0;
				//初始sheet名称，按月份
				if(productList!=null&&productList.size()>0){
				    for(int i=0;i<productList.size();i++){
				    	if(i==0){
				    		workbook.setSheetName(0, getStringValue(productList.get(0).get("productName")));  
				    	}else{
				    		workbook.createSheet(getStringValue(productList.get(i).get("productName")));
				    		PoiUtil.copySheet(workbook.getSheetAt(i), workbook.getSheetAt(0), workbook);
				    	}
				    }	
				   }
				int sheetNum=workbook.getNumberOfSheets();
				
				for(int i=0;i<sheetNum;i++){
					sheet=workbook.getSheet(getStringValue(productList.get(i).get("productName")));
					
					sheet.getRow(0).getCell(0).setCellValue( getStringValue(clientName)+getStringValue(productList.get(i).get("productName"))+"车发储罐统计表");
					row0=sheet.getRow(0).getCell(2).getCellStyle();
					row1=sheet.getRow(1).getCell(2).getCellStyle();
					row2=sheet.getRow(2).getCell(2).getCellStyle();
					row3=sheet.getRow(3).getCell(2).getCellStyle();
					rowItem0=sheet.getRow(0).getCell(1).getCellStyle();
					rowItem1=sheet.getRow(1).getCell(1).getCellStyle();
					rowItem2=sheet.getRow(2).getCell(1).getCellStyle();
					rowItem3=sheet.getRow(3).getCell(1).getCellStyle();
					colWidth=sheet.getColumnWidth(1);
					monthDate=getMonthDateNum(dateTime);
					
					tankList=vehicleDeliveryStatementDao.getTankMsg(dateTime,Integer.valueOf(productList.get(i).get("productId").toString()),vsDto.getClientId());
					
					//设置横向货品名称
					if(tankList!=null&&tankList.size()>0){
						//保存每日 字段的样式
						columns=tankList.size();
						for( j=0;j<=columns;j++){
							if(j==0){
							sheet.getRow(1).getCell(2*j+1).setCellStyle(rowItem1);
							sheet.getRow(1).getCell(2*j+2).setCellStyle(rowItem1);
							sheet.getRow(2).getCell(2*j+1).setCellStyle(rowItem2);
							sheet.getRow(2).getCell(2*j+2).setCellStyle(rowItem2);
							sheet.getRow(3).getCell(2*j+1).setCellStyle(rowItem3);
							sheet.getRow(3).getCell(2*j+2).setCellStyle(rowItem3);
							sheet.getRow(1).getCell(2*j+1).setCellValue(getStringValue(tankList.get(j).get("tankName")));
							sheet.getRow(2).getCell(2*+1).setCellValue("发货量");
							sheet.getRow(2).getCell(2*j+2).setCellValue("车次");
							}else if(j!=columns){
								sheet.setColumnWidth(2*j+1, colWidth);
								sheet.setColumnWidth(2*j+2, colWidth);
								sheet.getRow(0).createCell(2*j+1).setCellStyle(rowItem0);
								sheet.getRow(0).createCell(2*j+2).setCellStyle(rowItem0);
								sheet.getRow(1).createCell(2*j+1).setCellStyle(rowItem1);
								sheet.getRow(1).createCell(2*j+2).setCellStyle(rowItem1);
								sheet.getRow(2).createCell(2*j+1).setCellStyle(rowItem2);
								sheet.getRow(2).createCell(2*j+2).setCellStyle(rowItem2);
								sheet.getRow(3).createCell(2*j+1).setCellStyle(rowItem3);
								sheet.getRow(3).createCell(2*j+2).setCellStyle(rowItem3);
								sheet.getRow(1).getCell(2*j+1).setCellValue(getStringValue(tankList.get(j).get("tankName")));
								sheet.getRow(2).getCell(2*j+1).setCellValue("发货量");
								sheet.getRow(2).getCell(2*j+2).setCellValue("车次");
							}else if(j==columns){
								sheet.setColumnWidth(2*j+1, colWidth);
								sheet.setColumnWidth(2*j+2, colWidth);
								sheet.getRow(0).createCell(2*j+1).setCellStyle(rowItem0);
								sheet.getRow(0).createCell(2*j+2).setCellStyle(row0);
								sheet.getRow(1).createCell(2*j+1).setCellStyle(rowItem1);
								sheet.getRow(1).createCell(2*j+2).setCellStyle(row1);
								sheet.getRow(2).createCell(2*j+1).setCellStyle(rowItem1);
								sheet.getRow(2).createCell(2*j+2).setCellStyle(row2);
								sheet.getRow(2).createCell(2*j+1).setCellStyle(rowItem2);
								sheet.getRow(2).createCell(2*j+2).setCellStyle(row2);
								sheet.getRow(3).createCell(2*j+1).setCellStyle(rowItem3);
								sheet.getRow(3).createCell(2*j+2).setCellStyle(row3);
								sheet.getRow(1).getCell(2*j+1).setCellValue("合计 ");
								sheet.getRow(2).getCell(2*j+1).setCellValue("总车次");
								sheet.getRow(2).getCell(2*j+2).setCellValue("总发货量");
								colNum=2*j+2;
							}
						}
						//向下移动
						sheet.shiftRows(3, 3, monthDate,true,false);
						for( j=0;j<monthDate;j++){
							HSSFRow itemRow=sheet.createRow(3+j);
				    		itemRow.setHeight(sheet.getRow(2).getHeight());
							for(int m=0;m<=colNum;m++){
								itemRow.createCell(m);
								itemRow.getCell(m).setCellStyle(sheet.getRow(2).getCell(m).getCellStyle());
								if(m==0){
								 itemRow.getCell(0).setCellValue(j+1);
								}
							}
						}
						sheet.addMergedRegion(new Region(0,(short)0, 0,(short)colNum));
						for(j=0;j<colNum/2;j++){
							sheet.addMergedRegion(new Region(1,(short)(2*j+1),1,(short)(2*j+2)));  
							sheet.getRow(3+monthDate).getCell(2*j+1).setCellType(HSSFCell.CELL_TYPE_FORMULA);
							sheet.getRow(3+monthDate).getCell(2*j+2).setCellType(HSSFCell.CELL_TYPE_FORMULA);
							
							if(j<colNum/2-1){
								if(vsDto.getClientId()!=null)
									tankDataList=vehicleDeliveryStatementDao.getTankData(new VehicleDeliveryStatementDto(Integer.valueOf(tankList.get(j).get("tankId").toString()),dateTime,Integer.valueOf(productList.get(i).get("productId").toString()),vsDto.getClientId()));
								else
									tankDataList=vehicleDeliveryStatementDao.getTankData(new VehicleDeliveryStatementDto(Integer.valueOf(tankList.get(j).get("tankId").toString()),dateTime,Integer.valueOf(productList.get(i).get("productId").toString())));
							tankDataDateMap=getDataDateMap(getDataDateMap(monthDate),tankDataList);
							for(int n=1;n<=monthDate;n++){
								sheet.getRow(2+n).getCell(2*j+1).setCellValue(getDoubleValue(tankDataDateMap.get(n+"").get("actualNum")));
								sheet.getRow(2+n).getCell(2*j+2).setCellValue(getDoubleValue(tankDataDateMap.get(n+"").get("count")));
							}
							sheet.getRow(3+monthDate).getCell(2*j+1).setCellFormula(getSumFormulaB(j,3+monthDate));
							sheet.getRow(3+monthDate).getCell(2*j+2).setCellFormula(getSumFormulaA(j,3+monthDate));
							}else if(j==colNum/2-1){
								sheet.getRow(3+monthDate).getCell(2*j+1).setCellFormula(getSumFormulaB(j,3+monthDate));
								sheet.getRow(3+monthDate).getCell(2*j+2).setCellFormula(getSumFormulaA(j,3+monthDate));
								for(int n=1;n<=monthDate;n++){
									sheet.getRow(2+n).getCell(2*j+1).setCellType(HSSFCell.CELL_TYPE_FORMULA);
									sheet.getRow(2+n).getCell(2*j+2).setCellType(HSSFCell.CELL_TYPE_FORMULA);
									sheet.getRow(2+n).getCell(2*j+1).setCellFormula(getStringFormulaA(j,3+n));
									sheet.getRow(2+n).getCell(2*j+2).setCellFormula(getStringFormulaB(j,3+n));
								}
								
							}
						}
						sheet.setForceFormulaRecalculation(true);
					}

				}		
					} 
					catch (OAException e) 
					{
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				
			});
			
			return excelUtil.getWorkbookWithSheets();
			
		}else if(type!=null&&Integer.valueOf(type)==5){//全车位统计表
			
			excelUtil=new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/stationDeliver.xls", new CallBack() {
				@Override
				public void setSheetValue(HSSFSheet sheet) 
				{
					try 
					{   
						sheet.setForceFormulaRecalculation(true);
						HSSFCellStyle cellNormal;
						short rowHeight;
						HSSFRichTextString cellValue;
						HSSFRow itemRowa,itemRowb;
						Map<String,Map<String,Object>> datamap;
						List<Map<String,Object>> dataList;
						Long startTime=vsDto.getStartTime();
						 Calendar cal = Calendar.getInstance();
						 cal.setTimeInMillis(startTime*1000);
						 sheet.getRow(0).getCell(0).setCellValue(cal.get(Calendar.YEAR)+"年"+String.format("%02d",(cal.get(Calendar.MONTH)+1))+"月份装车站发货统计表");
						cellNormal=sheet.getRow(5).getCell(1).getCellStyle();
						rowHeight=sheet.getRow(5).getHeight();
						cellValue=sheet.getRow(5).getCell(52).getRichStringCellValue();
					List<Map<String,Object>> productTypeList=vehicleDeliveryStatementDao.getProductNameTypeMsg(vsDto);//获取货品
					if(productTypeList!=null&&productTypeList.size()>0){
						int size=productTypeList.size();
						sheet.shiftRows(3, 6, 2*size);
						
						for(int i=0;i<=size;i++){
							if(i<size){//单个货批
							vsDto.setProductId(Integer.valueOf(productTypeList.get(i).get("productId").toString()));
							dataList=vehicleDeliveryStatementDao.getOutBoundDataList(vsDto);
							datamap=getDataTruckInportMap(getTruckInportMap(),dataList);
							itemRowa=sheet.createRow(2*i+3);	
							itemRowb=sheet.createRow(2*i+4);
							itemRowa.setHeight(rowHeight);
							itemRowb.setHeight(rowHeight);
							for(int j=0;j<54;j++){
								itemRowa.createCell(j);
								itemRowa.getCell(j).setCellStyle(cellNormal);
								itemRowb.createCell(j);
								itemRowb.getCell(j).setCellStyle(cellNormal);
								if(j==0){
									itemRowa.getCell(j).setCellValue(getStringValue(productTypeList.get(i).get("productName")));
									sheet.addMergedRegion(new Region(2*i+3,(short)0,2*i+4,(short)0));  
								}else if(j==1){
									itemRowa.getCell(j).setCellValue("重量");
									itemRowb.getCell(j).setCellValue("车次");
								}else if(j==50){
									itemRowa.getCell(j).setCellValue(getDoubleValue(datamap.get("TZ").get("num")));
									itemRowb.getCell(j).setCellValue(getDoubleValue(datamap.get("TZ").get("count")));
								}else if(j==51){
									itemRowa.getCell(j).setCellType(HSSFCell.CELL_TYPE_FORMULA);
									itemRowb.getCell(j).setCellType(HSSFCell.CELL_TYPE_FORMULA);
									itemRowa.getCell(j).setCellFormula("SUM(C"+(2*i+4)+":AY"+(2*i+4)+")");
									itemRowb.getCell(j).setCellFormula("SUM(C"+(2*i+5)+":AY"+(2*i+5)+")");
								}else if(j==52){//拼装合计
									itemRowa.getCell(j).setCellValue(cellValue);
									itemRowb.getCell(j).setCellValue(vehicleDeliveryStatementDao.getAssemBlyTruck(vsDto));
								}else if(j==53){
									itemRowa.getCell(j).setCellValue(cellValue);
									itemRowb.getCell(j).setCellValue(cellValue);
								}else{
									itemRowa.getCell(j).setCellValue(getDoubleValue(datamap.get("A"+String.format("%02d",(j-1))).get("num")));
									itemRowb.getCell(j).setCellValue(getDoubleValue(datamap.get("A"+String.format("%02d",(j-1))).get("count")));
								}
							}
							}else{//上累
								vsDto.setProductId(0);
								vsDto.setStartTime( DateUtils.getFirstDayOfYear(cal.get(Calendar.YEAR)));
								vsDto.setEndTime(DateUtils.getLastDayOfLastMonth(startTime));
								dataList=vehicleDeliveryStatementDao.getOutBoundDataList(vsDto);
								datamap=getDataTruckInportMap(getTruckInportMap(),dataList);
								itemRowa=sheet.getRow(2*i+3);					
								itemRowb=sheet.getRow(2*i+4);
								for(int j=2;j<53;j++){
									if(j==50){
										itemRowa.getCell(j).setCellValue(getDoubleValue(datamap.get("TZ").get("num")));
										itemRowb.getCell(j).setCellValue(getDoubleValue(datamap.get("TZ").get("count")));
									}else if(j==51){
										
									}else if(j==52){
										itemRowa.getCell(j).setCellValue(cellValue);
										itemRowb.getCell(j).setCellValue(vehicleDeliveryStatementDao.getAssemBlyTruck(vsDto));
									}else{
									itemRowa.getCell(j).setCellValue(getDoubleValue(datamap.get("A"+String.format("%02d",(j-1))).get("num")));
									itemRowb.getCell(j).setCellValue(getDoubleValue(datamap.get("A"+String.format("%02d",(j-1))).get("count")));	
									}
								}
								itemRowa.setHeight((short)0);
								itemRowb.setHeight((short)0);
							}
						}
					}
					} 
					catch (OAException e) 
					{
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			});
			return excelUtil.getWorkbook();
			
			
			
			
		}
		return null;
	}
	private String getStringValue(Object object){
		if(object==null||object.toString().equals("null")){
			return "";
		}else{
			return object.toString();
		}
	}
	
	private Double getDoubleValue(Object object){
		String str=getStringValue(object);
	  try{
	   return 	Double.parseDouble(str);
	  }catch(NumberFormatException ex){
		  return 0d;
	  }
	}
	
	private List<Map<String, Object>> handleDateList(VehicleDeliveryStatementDto vsDto) {
		String year=vsDto.getYear();
		String month=vsDto.getMonth();
		List<Map<String,Object>> dateList=new ArrayList<Map<String,Object>>();
		Map<String,Object> map;
		if(month==null){
			for(int i=0;i<12;i++){
				map=new HashMap<String,Object>();
				map.put("date", year+"-"+String.format("%02d", (i+1)));
				map.put("dateName", year+"年"+String.format("%02d", (i+1))+"月份");
		          dateList.add(map);
			}
		}else{
			map=new HashMap<String,Object>();
			map.put("date", year+"-"+String.format("%02d", Integer.valueOf(month)));
			map.put("dateName", year+"年"+String.format("%02d", Integer.valueOf(month))+"月份");
	          dateList.add(map);
		}
		return dateList;	
	}
	//获取月份天数
	private int getMonthDateNum(String stringValue) {
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM"); //如果写成年月日的形式的话，要写小d，如："yyyy/MM/dd"
		try {
			rightNow.setTime(simpleDate.parse(stringValue.replace("-", "/")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //要计算你想要的月份，改变这里即可
		return rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
	};
	//根据天数获取map
	private Map<String, Map<String, Object>> getDataDateMap(int monthDate) {
		Map<String, Map<String, Object>> map=new HashMap<String, Map<String, Object>>();
		 Map<String, Object> itemmap;
		for(int i=1;i<=monthDate;i++){
			itemmap=new HashMap<String, Object>();
			itemmap.put("count", 0);
			itemmap.put("actualNum", 0);
			map.put(i+"", itemmap);
		}
		return map;
	}
	private Map<String, Map<String, Object>> getDataDateMap(Map<String, Map<String, Object>> map,
			List<Map<String, Object>> productDataList) {
		if(productDataList!=null&&productDataList.size()>0){
			for(int i=0;i<productDataList.size();i++){
			     map.get(Integer.valueOf(productDataList.get(i).get("date").toString())+"").put("count", productDataList.get(i).get("count"));
				 map.get(Integer.valueOf(productDataList.get(i).get("date").toString())+"").put("actualNum", productDataList.get(i).get("actualNum"));
			}
		}
		return map;
	}
	
	//奇数列求和
	private String getStringFormulaA(int j,int row) {
	    if(j>0&&j<13){
		return "SUMPRODUCT(MOD(COLUMN(B"+row+":"+(char)(2*j+65)+row+"),2),B"+row+":"+(char)(2*j+65)+row+")";
	    }else if(j>=13){
	    	return "SUMPRODUCT(MOD(COLUMN(B"+row+":A"+(char)(2*j+39)+row+"),2),B"+row+":A"+(char)(2*j+39)+row+")";
	    }else{
	    	return null;
	    }
	};
	//偶数列求和
		private String getStringFormulaB(int j,int row) {
		    if(j>0&&j<13){
			return "SUMPRODUCT(MOD(COLUMN(B"+row+":"+(char)(2*j+65)+row+")+1,2),B"+row+":"+(char)(2*j+65)+row+")";
		    }else if(j>=13){
		    	return "SUMPRODUCT(MOD(COLUMN(B"+row+":A"+(char)(2*j+39)+row+")+1,2),B"+row+":A"+(char)(2*j+39)+row+")";
		    }else{
		    	return null;
		    }
		};
		private String getSumFormulaA(int j,int row){
			 if(j<12){
					return "SUM("+(char)(2*j+67)+"03:"+(char)(2*j+67)+row+")";
				    }else if(j>=12){
				    	return   "SUM(A"+(char)(2*j+41)+"03:A"+(char)(2*j+41)+row+")"; 
				    }else{
				    	return null;
				    }	
		}
		private String getSumFormulaB(int j,int row){

			 if(j<13){
					return "ROUND(SUM("+(char)(2*j+66)+"03:"+(char)(2*j+66)+row+"),3)";
				    }else if(j>=13){
				    	return "ROUND(SUM(A"+(char)(2*j+40)+"03:A"+(char)(2*j+40)+row+"),3)";
				    }else{
				    	return null;
				    }
		}
		public  Map<String, Map<String, Object>> getTruckInportMap() {
			Map<String, Map<String, Object>> mapdata= new HashMap<String, Map<String, Object>>();
			Map<String,Object> map;
			for(int i=1;i<49;i++){
				map=new HashMap<String,Object>();
				map.put("num", 0);
				map.put("count", 0);
				mapdata.put("A"+String.format("%02d",i), map);
			}
			map=new HashMap<String,Object>();
			map.put("num", 0);
			map.put("count", 0);
			mapdata.put("TZ", map);
			return mapdata;
		}
		private Map<String, Map<String, Object>> getDataTruckInportMap(Map<String, Map<String, Object>> truckInportMap, List<Map<String, Object>> dataList) {
			if(dataList!=null&&dataList.size()>0){
				int size=dataList.size();
				for(int i=0;i<size;i++){
					if(truckInportMap.get(dataList.get(i).get("port"))!=null){
				truckInportMap.get(dataList.get(i).get("port")).put("num", dataList.get(i).get("num"));
				truckInportMap.get(dataList.get(i).get("port")).put("count", dataList.get(i).get("count"));	
					}
				}
			}
			return truckInportMap;
		}
		
}
