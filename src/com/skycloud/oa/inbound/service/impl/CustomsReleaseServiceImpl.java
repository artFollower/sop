/**
 * 
 */
package com.skycloud.oa.inbound.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.CustomsReleaseDao;
import com.skycloud.oa.inbound.dto.CustomsReleaseDto;
import com.skycloud.oa.inbound.model.CustomsRelease;
import com.skycloud.oa.inbound.service.CustomsReleaseService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.ExcelUtil;
import com.skycloud.oa.utils.OaMsg;
import com.skycloud.oa.utils.ExcelUtil.CallBack;

/**
 *
 * @author jiahy
 * @version 2015年12月10日 下午2:58:26
 */
@Service
public class CustomsReleaseServiceImpl implements CustomsReleaseService {
	private static Logger LOG = Logger.getLogger(CustomsReleaseServiceImpl.class);
	@Autowired
	private CustomsReleaseDao customsReleaseDao;
	
	@Override
	public OaMsg getCustomsReleaseList(CustomsReleaseDto crDto,
			PageView pageView) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			List<Map<String,Object>> data=customsReleaseDao.getCustomsReleaseList(crDto, pageView.getStartRecord(),pageView.getMaxresult());
			if(data!=null&&crDto.getId()!=null){
				for(int i=0;i<data.size();i++){
					data.get(i).put("hasBasePass", customsReleaseDao.getHasPass(Integer.valueOf(data.get(i).get("productId").toString()),Integer.valueOf(data.get(i).get("arrivalId").toString()),1).get("num"));
					data.get(i).put("hasBondedPass", customsReleaseDao.getHasPass(Integer.valueOf(data.get(i).get("productId").toString()),Integer.valueOf(data.get(i).get("arrivalId").toString()),2).get("num"));
					data.get(i).put("baseCargoMsg", customsReleaseDao.getCargoMsg(
							new CustomsReleaseDto(Integer.valueOf(data.get(i).get("productId").toString()),Integer.valueOf(data.get(i).get("arrivalId").toString()),1)));
					data.get(i).put("bondedCargoMsg", customsReleaseDao.getCargoMsg(
							new CustomsReleaseDto(Integer.valueOf(data.get(i).get("productId").toString()),Integer.valueOf(data.get(i).get("arrivalId").toString()),2)));
				}
			}
			oaMsg.getData().addAll(data);
			oaMsg.getMap().put(Constant.TOTALRECORD,customsReleaseDao.getCustomsReleaseCount(crDto)+"");
		}catch (RuntimeException e){
			LOG.error("service 获取卸货海关放行量列表失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获取卸货海关放行量列表失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_CUSTOMSRELEASE,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateCustomsRelease(CustomsRelease customsRelease)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			customsReleaseDao.updateCustomsRelease(customsRelease);
		}catch (RuntimeException e){
			LOG.error("service 更新卸货海关放行量失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 更新卸货海关放行量失败",e);
		}
		return oaMsg;
	}

	@Override
	public HSSFWorkbook exportExcel(HttpServletRequest request, final CustomsReleaseDto crDto) throws OAException {
		return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/customsRelease.xls", new CallBack() {
			@Override
			public void setSheetValue(HSSFSheet sheet) 
			{
				try 
				{
					List<Map<String,Object>> data=customsReleaseDao.getCustomsReleaseList(crDto, 0,0);
					if(data!=null){
						for(int i=0;i<data.size();i++){
							data.get(i).put("cargoMsg", customsReleaseDao.getCargoMsg(
									new CustomsReleaseDto(Integer.valueOf(data.get(i).get("productId").toString()),Integer.valueOf(data.get(i).get("arrivalId").toString()))));
						}
					}
					if(data!=null&&data.size()>0){
					sheet.shiftRows(2, 2, data.size(),true,false);
					HSSFRow itemRow;
					int item=2;
					Map<String, Map<String,Integer>> region=new HashMap<String, Map<String,Integer>>();
					 List<String> lsids = new ArrayList<String>();// arrivalId
					 String cargoNames="";
					 Double cargoPass=0d;
					for(Map<String,Object> map : data){
						itemRow=sheet.createRow(item++);
						itemRow.setHeight(sheet.getRow(1).getHeight());
						for(int i=0;i<13;i++){
								itemRow.createCell(i);
								itemRow.getCell(i).setCellStyle(sheet.getRow(1).getCell(i).getCellStyle());				
						}

						itemRow.getCell(0).setCellValue(getStringValue(map.get("inboundTime")));
						itemRow.getCell(1).setCellValue(getStringValue(map.get("shipName"))+"/"+getStringValue(map.get("shipRefName")));
						itemRow.getCell(2).setCellValue(getStringValue(map.get("productName")));
						itemRow.getCell(3).setCellValue(getStringValue(map.get("tankName")));
						itemRow.getCell(4).setCellValue(getDoubleValue(map.get("hasCustomsPass")));
						itemRow.getCell(5).setCellValue(getDoubleValue(map.get("beforeInboundTank")));
						itemRow.getCell(6).setCellValue(getDoubleValue(map.get("afterInboundTank")));
						itemRow.getCell(7).setCellType(HSSFCell.CELL_TYPE_FORMULA);
						itemRow.getCell(8).setCellType(HSSFCell.CELL_TYPE_FORMULA);
						itemRow.getCell(9).setCellType(HSSFCell.CELL_TYPE_FORMULA);
						itemRow.getCell(7).setCellFormula("G"+item+"-F"+item);
						itemRow.getCell(8).setCellFormula("E"+item);
						itemRow.getCell(9).setCellFormula("H"+item+"-E"+item);
						
						if(map.get("inboundCount")!=null&&Double.valueOf(map.get("inboundCount").toString())!=0){
							itemRow.getCell(7).setCellValue(getDoubleValue(map.get("inboundCount")));
							itemRow.getCell(8).setCellValue(getDoubleValue(map.get("outBoundCount")));
							itemRow.getCell(9).setCellValue(getDoubleValue(map.get("unOutBoundCount")));
						}else{
							itemRow.getCell(7).setCellValue(Common.sub(map.get("afterInboundTank"), map.get("beforeInboundTank"), 3));
							itemRow.getCell(8).setCellValue(0);
							itemRow.getCell(9).setCellValue(Common.sub(map.get("afterInboundTank"), map.get("beforeInboundTank"), 3));
						}
						itemRow.getCell(10).setCellValue(getStringValue(map.get("description")));
						if(lsids.contains(getStringValue(map.get("arrivalId")))){
							itemRow.getCell(11).setCellValue("");
							itemRow.getCell(12).setCellValue("");
							region.get(getStringValue(map.get("arrivalId"))).put("end",item-1);
						}else{
							Map<String,Integer> mapRg=new HashMap<String, Integer>();
				    		mapRg.put("start", item-1);
				    		mapRg.put("end", item-1);
				    		region.put(getStringValue(map.get("arrivalId")), mapRg);
				    		lsids.add(getStringValue(map.get("arrivalId")));
				    		if(map.get("cargoMsg")!=null){
				    		List<Map<String,Object>> cargoMsg= (List<Map<String,Object>>)map.get("cargoMsg");
				    		if(cargoMsg.size()>0){
				    			cargoNames="";
				    			for(int j=0;j<cargoMsg.size();j++){
				    				if(j==(cargoMsg.size()-1)){
				    				cargoNames+=cargoMsg.get(j).get("cargoCode").toString();
				    				}else{
				    					cargoNames+=cargoMsg.get(j).get("cargoCode").toString()+"/\n";
				    				}
				    				cargoPass=Common.add(cargoPass, cargoMsg.get(j).get("cargoPass"), 3);
				    			}		
				    			itemRow.getCell(11).setCellValue(cargoNames);
								itemRow.getCell(12).setCellValue(cargoPass);	
				    		}
				    		}
						}
					}		
					sheet.getRow(data.size()+2).getCell(4).setCellType(HSSFCell.CELL_TYPE_FORMULA);
					sheet.getRow(data.size()+2).getCell(4).setCellFormula("ROUND(SUM(E03:E"+(data.size()+2)+"),3)");
					sheet.getRow(data.size()+2).getCell(7).setCellType(HSSFCell.CELL_TYPE_FORMULA);
					sheet.getRow(data.size()+2).getCell(7).setCellFormula("ROUND(SUM(H03:H"+(data.size()+2)+"),3)");
					sheet.getRow(data.size()+2).getCell(8).setCellType(HSSFCell.CELL_TYPE_FORMULA);
					sheet.getRow(data.size()+2).getCell(8).setCellFormula("ROUND(SUM(I03:I"+(data.size()+2)+"),3)");
					sheet.getRow(data.size()+2).getCell(9).setCellType(HSSFCell.CELL_TYPE_FORMULA);
					sheet.getRow(data.size()+2).getCell(9).setCellFormula("ROUND(SUM(J03:J"+(data.size()+2)+"),3)");
					sheet.setForceFormulaRecalculation(true);
					if(region!=null){
						for(int j=0;j<lsids.size();j++){
							sheet.addMergedRegion(new Region(region.get(lsids.get(j)).get("start"),(short)11, region.get(lsids.get(j)).get("end"), (short)11));    
							sheet.addMergedRegion(new Region(region.get(lsids.get(j)).get("start"),(short)12, region.get(lsids.get(j)).get("end"), (short)12));    
						}}
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
		}).getWorkbook();
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

	/**
	 * @Title getTotalNum
	 * @Descrption:TODO
	 * @param:@param crDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年7月11日上午8:22:11
	 * @throws
	 */
	@Override
	public OaMsg getTotalNum(CustomsReleaseDto crDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		 oaMsg.getData().add(customsReleaseDao.getTotalNum(crDto));
		}catch (RuntimeException e){
			LOG.error("service 获取统计信息失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获取统计信息失败",e);
		}
		return oaMsg;
	}
}
