/**
 * 
 */
package com.skycloud.oa.planmanager.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFCreationHelper;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.BerthProgramDao;
import com.skycloud.oa.inbound.dao.TransportProgramDao;
import com.skycloud.oa.inbound.dto.TransportProgramDto;
import com.skycloud.oa.inbound.model.TransportProgram;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.planmanager.dto.PlanManagerDto;
import com.skycloud.oa.planmanager.service.PlanManagerService;
import com.skycloud.oa.report.utils.FormatUtils;
import com.skycloud.oa.utils.ExcelUtil;
import com.skycloud.oa.utils.OaMsg;
import com.skycloud.oa.utils.ExcelUtil.CallBack;
import com.skycloud.oa.utils.ExcelUtil.CallBackSheets;
/**
 *
 * @author jiahy
 * @version 2015年8月14日 下午4:02:16
 */
@Service
public class PlanManagerServiceImpl implements PlanManagerService {
	private static Logger LOG = Logger.getLogger(PlanManagerServiceImpl.class);
	@Autowired
	private BerthProgramDao berthProgramDao;
	@Autowired
	private TransportProgramDao transportProgramDao;
	@Override
	public OaMsg getBerthPlanList(PlanManagerDto planManagerDto,
			PageView pageView) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(berthProgramDao.getBerthProgramList(planManagerDto,pageView.getStartRecord(),pageView.getMaxresult()));
            oaMsg.getMap().put(Constant.TOTALRECORD,berthProgramDao.getBerthProgramCount(planManagerDto)+"");			
		}catch (RuntimeException e){
			LOG.error("service 获得靠泊方案列表失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获得靠泊方案列表失败",e);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getUnloadingPlanList(PlanManagerDto planManagerDto,
			PageView pageView) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(transportProgramDao.getUnloadingPlanList(planManagerDto,pageView.getStartRecord(),pageView.getMaxresult()));
            oaMsg.getMap().put(Constant.TOTALRECORD,transportProgramDao.getUnloadingPlanCount(planManagerDto)+"");	
		}catch (RuntimeException e){
			LOG.error("service 获得接卸方案列表失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获得接卸方案列表失败",e);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getBackFlowPlanList(PlanManagerDto planManagerDto,
			PageView pageView) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(transportProgramDao.getBackFlowList(planManagerDto,pageView.getStartRecord(),pageView.getMaxresult()));
            oaMsg.getMap().put(Constant.TOTALRECORD,transportProgramDao.getBackFlowCount(planManagerDto)+"");	
		}catch (RuntimeException e){
			LOG.error("service 获得倒罐方案列表失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获得倒罐方案列表失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_BACKFLOW_PLAN,type=C.LOG_TYPE.CREATE)
	public OaMsg addBackFlowPlan(TransportProgram transportProgram)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getMap().put("id", transportProgramDao.addBackFlowPlanTransportProgram(transportProgram)+"");
		}catch (RuntimeException e){
			LOG.error("service 获得倒罐方案列表失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获得倒罐方案列表失败",e);
		}
		return oaMsg;
	}

	/**
	 * @Title exportExcel
	 * @Descrption:TODO
	 * @param:@param planManagerDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年6月23日上午10:28:14
	 * @throws
	 */
	@Override
	public HSSFWorkbook exportExcel(HttpServletRequest request,final PlanManagerDto planManagerDto) throws OAException {
		ExcelUtil excelUtil=null;
		if(planManagerDto.getItemType()!=null){
		if(planManagerDto.getItemType()==1){
			excelUtil=new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/berthPlanManager.xls",new CallBack() {
				@Override
				public void setSheetValue(HSSFSheet sheet) {
					try {
					List<Map<String, Object>> dataList=berthProgramDao.getBerthProgramList(planManagerDto, 0, 0);
					if(dataList!=null&&dataList.size()>0){
						HSSFRow  itemRow;
						Map<String, Object> map;
						for(int i=0,len=dataList.size();i<len;i++){
							itemRow=sheet.createRow(i+1);
							itemRow.setHeight(sheet.getRow(0).getHeight());
							for(int j=0;j<9;j++){
								itemRow.createCell(j).setCellStyle(sheet.getRow(0).getCell(j).getCellStyle());
							}
							map=dataList.get(i);
							itemRow.getCell(0).setCellValue(FormatUtils.getStringValue(map.get("createTime")));
							itemRow.getCell(1).setCellValue((((int)map.get("arrivalType")==1||(int)map.get("arrivalType")==3)?"入库":"出库"));
							itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(map.get("shipName")));
							itemRow.getCell(3).setCellValue(FormatUtils.getStringValue(map.get("arrivalTime")));
							itemRow.getCell(4).setCellValue(FormatUtils.getStringValue(map.get("berthName")));
							itemRow.getCell(5).setCellValue(FormatUtils.getStringValue(map.get("productName")));
							itemRow.getCell(6).setCellValue(FormatUtils.getDoubleValue(map.get("count")));
							itemRow.getCell(7).setCellValue(FormatUtils.getDoubleValue(map.get("count_plan")));
							itemRow.getCell(8).setCellValue(getStrStatus(map.get("status")));
						}
						
					}	
					} catch (OAException e) {
						e.printStackTrace();
					}
				}
			});
		}else if(planManagerDto.getItemType()==2){
			excelUtil=new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/transportPlanManager.xls",new CallBack() {
				@Override
				public void setSheetValue(HSSFSheet sheet) {
					try {
					List<Map<String, Object>> dataList=transportProgramDao.getUnloadingPlanList(planManagerDto, 0, 0);
					if(dataList!=null&&dataList.size()>0){
						HSSFRow  itemRow;
						Map<String, Object> map;
						for(int i=0,len=dataList.size();i<len;i++){
							itemRow=sheet.createRow(i+1);
							itemRow.setHeight(sheet.getRow(0).getHeight());
							for(int j=0;j<9;j++){
								itemRow.createCell(j).setCellStyle(sheet.getRow(0).getCell(j).getCellStyle());
							}
							map=dataList.get(i);
							itemRow.getCell(0).setCellValue(FormatUtils.getStringValue(map.get("createTime")));
							itemRow.getCell(1).setCellValue(FormatUtils.getStringValue(map.get("shipName")));
							itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(map.get("arrivalTime")));
							itemRow.getCell(3).setCellValue(FormatUtils.getStringValue(map.get("berthName")));
							itemRow.getCell(4).setCellValue(FormatUtils.getStringValue(map.get("productName")));
							itemRow.getCell(5).setCellValue(FormatUtils.getDoubleValue(map.get("count")));
							itemRow.getCell(6).setCellValue(FormatUtils.getStringValue(map.get("tank")));
							itemRow.getCell(7).setCellValue(FormatUtils.getStringValue(map.get("tube")));
							itemRow.getCell(8).setCellValue(getStrStatus(map.get("status")));
						}
						
					}	
					} catch (OAException e) {
						e.printStackTrace();
					}
				}
			});
		}else if(planManagerDto.getItemType()==3){
			excelUtil=new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/backflowPlanManager.xls",new CallBack() {
				@Override
				public void setSheetValue(HSSFSheet sheet) {
					try {
					List<Map<String, Object>> dataList=transportProgramDao.getBackFlowList(planManagerDto, 0, 0);
					if(dataList!=null&&dataList.size()>0){
						HSSFRow  itemRow;
						Map<String, Object> map;
						for(int i=0,len=dataList.size();i<len;i++){
							itemRow=sheet.createRow(i+1);
							itemRow.setHeight(sheet.getRow(0).getHeight());
							for(int j=0;j<10;j++){
								itemRow.createCell(j).setCellStyle(sheet.getRow(0).getCell(j).getCellStyle());
							}
							map=dataList.get(i);
							itemRow.getCell(0).setCellValue(FormatUtils.getStringValue(map.get("createTime")));
							itemRow.getCell(1).setCellValue(((int)map.get("type")==1?"打循环":"倒罐"));
							itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(map.get("shipName")));
							itemRow.getCell(3).setCellValue(FormatUtils.getStringValue(map.get("arrivalTime")));
							itemRow.getCell(4).setCellValue(FormatUtils.getStringValue(map.get("productName")));
							itemRow.getCell(5).setCellValue(FormatUtils.getDoubleValue(map.get("count")));
							itemRow.getCell(6).setCellValue(FormatUtils.getStringValue(map.get("outTank")));
							itemRow.getCell(7).setCellValue(FormatUtils.getStringValue(map.get("inTank")));
							itemRow.getCell(8).setCellValue(FormatUtils.getStringValue(map.get("pump")));
							itemRow.getCell(9).setCellValue(getStrStatus(map.get("status")));
						}
						
					}	
					} catch (OAException e) {
						e.printStackTrace();
					}
				}
			});
		}
		return excelUtil.getWorkbook();
		}else{
			return null;
		}
	}

public String getStrStatus(Object status){
	String str="";
	if(status!=null){
		if((int)status==0){
			str="未提交";
		}else if((int)status==1){
			str="审核中";
		}else if((int)status==2){
			str="已完成";
		}else if((int)status==4){
			str="品质审核通过";
		}else if((int)status==5){
			str="工艺审核通过";
		}
	}else{
		str="";
	}
	return str;
	
}
@Override
public HSSFWorkbook exportTransportProgram(final HttpServletRequest request,final int transportId) throws OAException {
	return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/transportProgram.xls",new CallBackSheets() {
		
		@Override
		public void setSheetsValue(HSSFWorkbook workbook ) {
			TransportProgramDto tDto=new TransportProgramDto();
			tDto.setId(transportId);
			HSSFSheet sheet=workbook.getSheetAt(0);
			HSSFHeader header = sheet.getHeader();
			header.setLeft("http://192.168.1.243:8080/sop/planmanager/exportTransportProgram?transportId="+transportId);  
			try {
				Map<String, Object> data=transportProgramDao.getTransportProgramById(tDto);
				if(data!=null){
				if(data.get("createTime")!=null){
					String time=data.get("createTime").toString();
					sheet.getRow(1).getCell(0).setCellValue(time.substring(0, 4)+"年"+time.substring(5, 7)+"月"+time.substring(8, 10)+"日");
				}
				sheet.getRow(2).getCell(1).setCellValue(FormatUtils.getStringValue(data.get("shipName")));
				sheet.getRow(2).getCell(4).setCellValue(FormatUtils.getStringValue(data.get("productName")));
				sheet.getRow(3).getCell(1).setCellValue(FormatUtils.getStringValue(data.get("tankInfo")));
				sheet.getRow(3).getCell(4).setCellValue(FormatUtils.getDoubleValue(data.get("count"))+"吨");
				sheet.getRow(4).getCell(1).setCellValue(FormatUtils.getStringValue(data.get("tubeInfo")));
				sheet.getRow(4).getCell(4).setCellValue(FormatUtils.getStringValue(data.get("arrivalTime")));
				sheet.getRow(5).getCell(1).setCellValue(FormatUtils.getStringValue(data.get("berthName")));
				if(data.get("svg")!=null){
					String svg=data.get("svg").toString();
					svg = svg.substring(0,4)+" xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' "+svg.substring(5) ;
					handleSVG(request,sheet,workbook,svg,0,7);
				}
				String target="“"+FormatUtils.getStringValue(data.get("shipName"))+"”接卸：(进货储罐空容"+FormatUtils.getStringValue(data.get("comment"))+"吨，符合要求。)";
				sheet.getRow(16).getCell(0).setCellValue(target);
				sheet.getRow(38).getCell(1).setCellValue(FormatUtils.getStringValue(data.get("createUserName")));
				sheet.getRow(38).getCell(3).setCellValue(FormatUtils.getStringValue(data.get("reviewUserName")));
				sheet.getRow(38).getCell(5).setCellValue(FormatUtils.getStringValue(data.get("reviewCraftUserName")));
				}
			} catch (OAException e) {
				e.printStackTrace();
			}
			
			
		}
	}).getWorkbookWithSheets();
}
public void handleSVG(HttpServletRequest request,HSSFSheet sheet,HSSFWorkbook wb, String str, int col, int row){
	try {
		if(str!=null){
	 String path = request.getSession().getServletContext().getRealPath("/temp.png") ;
     File file = new File(path);  
     FileOutputStream outputStream = null; 
     file.createNewFile();
     outputStream = new FileOutputStream(file);
     byte[] bytes0 = str.getBytes("utf-8");
     PNGTranscoder t = new PNGTranscoder();
     t.addTranscodingHint(PNGTranscoder.KEY_WIDTH, new Float(732)) ;
     t.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, new Float(355)) ;
     TranscoderInput input = new TranscoderInput(new ByteArrayInputStream(bytes0));
     TranscoderOutput output = new TranscoderOutput(outputStream); 
     t.transcode(input, output); 
     
     
     FileInputStream jpeg = new FileInputStream(path);  
     byte[] bytes = IOUtils.toByteArray(jpeg);  
     int pictureIndex = wb.addPicture(bytes, HSSFWorkbook.PICTURE_TYPE_JPEG);  
     jpeg.close();  
       
     HSSFCreationHelper helper = (HSSFCreationHelper) wb.getCreationHelper();  
       
     HSSFClientAnchor clientAnchor = helper.createClientAnchor();  
     clientAnchor.setCol1(col);  
     clientAnchor.setRow1(row);  
       
     HSSFPatriarch patriarch = sheet.createDrawingPatriarch();  
     HSSFPicture picture = patriarch.createPicture(clientAnchor, pictureIndex);  
     picture.resize();
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
}

/**
 * @Title exportChangeTankProgram
 * @Descrption:TODO
 * @param:@param request
 * @param:@param transportId
 * @param:@return
 * @param:@throws OAException
 * @auhor jiahy
 * @date 2017年1月3日上午10:13:32
 * @throws
 */
@Override
public HSSFWorkbook exportChangeTankProgram(final HttpServletRequest request,final int transportId,final int type) throws OAException {
	 
	return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/changeTankProgram.xls", new CallBackSheets() {
		
		@Override
		public void setSheetsValue(HSSFWorkbook workbook) {
		try {
			HSSFSheet sheet =workbook.getSheetAt(0);
			HSSFHeader header = sheet.getHeader();
			header.setLeft("http://192.168.1.243:8080/sop/planmanager/exportChangeTankProgram?transportId="+transportId);  
			Map<String, Object> data=transportProgramDao.getChangeTankProgramById(transportId);
			String programTarget="";
			if(type==1)
				sheet.getRow(0).getCell(0).setCellValue("打 循 环 方 案");
			
			if(data!=null){
				if(data.get("createTime")!=null){
					String time=data.get("createTime").toString();
					sheet.getRow(1).getCell(0).setCellValue(time.substring(0, 4)+"年"+time.substring(5, 7)+"月"+time.substring(8, 10)+"日");
				}
				sheet.getRow(2).getCell(1).setCellValue(FormatUtils.getStringValue(data.get("productName")));
				sheet.getRow(2).getCell(3).setCellValue(FormatUtils.getDoubleValue(data.get("count"))+"吨");
				sheet.getRow(2).getCell(5).setCellValue(FormatUtils.getStringValue(data.get("tube")));
				sheet.getRow(3).getCell(1).setCellValue(FormatUtils.getStringValue(data.get("outTank")));
				sheet.getRow(3).getCell(3).setCellValue(FormatUtils.getStringValue(data.get("inTank")));
				sheet.getRow(3).getCell(5).setCellValue(FormatUtils.getStringValue(data.get("pump")));
				if(data.get("svg")!=null){
					String svg=data.get("svg").toString();
					svg = svg.substring(0,4)+" xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' "+svg.substring(5) ;
					handleSVG(request,sheet,workbook,svg,0,5);
				}
				if(type==1){
					programTarget="打循环目的:";
					if(FormatUtils.getStringValue(data.get("shipName"))!="")
						programTarget+="“"+FormatUtils.getStringValue(data.get("shipName"))+"”卸货结束打循环";
					programTarget+="(罐空容为:"+FormatUtils.getStringValue(data.get("message"))+"吨，符合要求。)";
				}else{
					programTarget="倒罐目的:"+FormatUtils.getStringValue(data.get("transferPurpose"))+"(罐空容为:"+FormatUtils.getStringValue(data.get("message"))+"吨，符合要求。)";
				}
				sheet.getRow(14).getCell(0).setCellValue(programTarget);
				sheet.getRow(29).getCell(1).setCellValue(FormatUtils.getStringValue(data.get("createUserName")));
				sheet.getRow(29).getCell(3).setCellValue(FormatUtils.getStringValue(data.get("reviewUserName")));
				sheet.getRow(29).getCell(5).setCellValue(FormatUtils.getStringValue(data.get("reviewCraftUserName")));
			}
		} catch (OAException e) {
			e.printStackTrace();
		}
			
		}
	}).getWorkbookWithSheets();
}

/**
 * @Title exportBerthProgram
 * @Descrption:TODO
 * @param:@param request
 * @param:@param arrivalId
 * @param:@return
 * @param:@throws OAException
 * @auhor jiahy
 * @date 2017年1月3日上午11:28:49
 * @throws
 */
@Override
public HSSFWorkbook exportBerthProgram(HttpServletRequest request, final int arrivalId)throws OAException {
	return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/berthProgram.xls", new CallBack() {
		
		@Override
		public void setSheetValue(HSSFSheet sheet) {
			try {
			HSSFHeader header = sheet.getHeader();
			header.setLeft("http://192.168.1.243:8080/sop/planmanager/exportBerthProgram?arrivalId="+arrivalId);  
				
				Map<String,Object> data=berthProgramDao.getBerthProgramById(arrivalId);
				if(data!=null){
					sheet.getRow(1).getCell(1).setCellValue(FormatUtils.getStringValue(data.get("shipName")));
					sheet.getRow(1).getCell(3).setCellValue(FormatUtils.getStringValue(data.get("berthName")));
					sheet.getRow(1).getCell(5).setCellValue(FormatUtils.getStringValue(data.get("productName")));
					sheet.getRow(2).getCell(1).setCellValue(FormatUtils.getDoubleValue(data.get("goodsPlan")));
					sheet.getRow(2).getCell(3).setCellValue(FormatUtils.getStringValue(data.get("arrivalTime")));
					sheet.getRow(2).getCell(5).setCellValue(FormatUtils.getStringValue(data.get("windPower")));
					sheet.getRow(4).getCell(1).setCellValue(FormatUtils.getDoubleValue(data.get("length")));
					sheet.getRow(4).getCell(3).setCellValue(FormatUtils.getDoubleValue(data.get("frontDepth")));
					sheet.getRow(4).getCell(5).setCellValue(FormatUtils.getDoubleValue(data.get("limitDisplacement")));
					sheet.getRow(5).getCell(1).setCellValue(FormatUtils.getDoubleValue(data.get("limitLength")));
					sheet.getRow(5).getCell(3).setCellValue(FormatUtils.getDoubleValue(data.get("minLength")));
					sheet.getRow(5).getCell(5).setCellValue(FormatUtils.getDoubleValue(data.get("limitDrought")));
					sheet.getRow(7).getCell(0).setCellValue(FormatUtils.getStringValue(data.get("description")));
					sheet.getRow(9).getCell(1).setCellValue(FormatUtils.getDoubleValue(data.get("shipLenth")));
					sheet.getRow(9).getCell(3).setCellValue(FormatUtils.getDoubleValue(data.get("shipWidth")));
					sheet.getRow(9).getCell(5).setCellValue(FormatUtils.getDoubleValue(data.get("shipArrivalDraught")));
					sheet.getRow(10).getCell(1).setCellValue(FormatUtils.getDoubleValue(data.get("loadCapacity")));
					sheet.getRow(10).getCell(3).setCellValue(FormatUtils.getDoubleValue(data.get("netTons")));
					sheet.getRow(10).getCell(5).setCellValue(FormatUtils.getDoubleValue(data.get("richDraught")));
					sheet.getRow(12).getCell(0).setCellValue(FormatUtils.getStringValue(data.get("safeInfo")));
					sheet.getRow(14).getCell(0).setCellValue(FormatUtils.getStringValue(data.get("comment")));
					sheet.getRow(15).getCell(1).setCellValue(FormatUtils.getStringValue(data.get("createUserName")));
					sheet.getRow(15).getCell(4).setCellValue(FormatUtils.getStringValue(data.get("reviewUserName")));
				}
			} catch (OAException e) {
				e.printStackTrace();
			} 
		}
	}).getWorkbook();
}
}
