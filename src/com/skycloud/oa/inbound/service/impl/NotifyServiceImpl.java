package com.skycloud.oa.inbound.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpRequest;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFCreationHelper;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.Region;
import org.codehaus.jackson.io.JsonStringEncoder;
import org.quartz.ListenerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.NotifyDao;
import com.skycloud.oa.inbound.dao.TransportProgramDao;
import com.skycloud.oa.inbound.dto.NotifyDto;
import com.skycloud.oa.inbound.dto.TransportProgramDto;
import com.skycloud.oa.inbound.model.Notify;
import com.skycloud.oa.inbound.model.TransportProgram;
import com.skycloud.oa.inbound.service.NotifyService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.report.utils.FormatUtils;
import com.skycloud.oa.utils.ExcelUtil;
import com.skycloud.oa.utils.ExcelUtil.CallBackSheets;
import com.skycloud.oa.utils.OaMsg;
/**
 *其他通知单
 * @author 作者:jiahy
 * @version 时间：2015年3月16日 下午2:24:34
 */
@Service
public class NotifyServiceImpl implements NotifyService {
	private static Logger LOG = Logger.getLogger(NotifyServiceImpl.class);
	@Autowired
	private  NotifyDao notifyDao;
	@Autowired
	private TransportProgramDao transportProgramDao;
	
	@Override
	public OaMsg getNotifyList(NotifyDto notifyDto, PageView pageView)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			List<Map<String, Object>> data = null;
				if("2,3,12,11,13,14,15,16".indexOf(notifyDto.getTypes())!=-1){
				data=notifyDao.getNotifyShipInfoList(notifyDto, pageView.getStartRecord(),pageView.getMaxresult());
			}else{
				data=notifyDao.getNotifyList(notifyDto, pageView.getStartRecord(),pageView.getMaxresult());
			}
			oaMsg.getData().addAll(data);
			oaMsg.getMap().put(Constant.TOTALRECORD, notifyDao.getNotifyCount(notifyDto)+"");
			return oaMsg;
		} catch (RuntimeException e) {
			LOG.error("service通知单查询失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service通知单查询失败",e);
		}
	}
	
	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_NOTIFY,type=C.LOG_TYPE.CREATE)
	public OaMsg addNotify(Notify notify) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			//TODO 查询通知单是否生成
			if(0 == notify.getContentType()) {
				Map<String, Object> checkNum = notifyDao.checkNotify(notify);
				if(Integer.valueOf(checkNum.get("num").toString()) == 0) {
					notifyDao.addNotify(notify);					
				}else {
					notify.setId(Integer.valueOf(checkNum.get("id").toString()));
					notifyDao.updateNotify(notify);
				}
			}else {
				notifyDao.addNotify(notify);
			}
			//更新流程状态
			if(notify.getState()!=null&&notify.getContentType()!=0){
				Notify notify1=new Notify();
				notify1.setCode(notify.getCode());
				notify1.setState(notify.getState());
				notifyDao.updateNotifyState(notify1);
			}
			
			return oaMsg;
		} catch (RuntimeException e) {
			LOG.error("service通知单添加失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service通知单添加失败",e);
		}
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_NOTIFY,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateNotify(Notify notify) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			notifyDao.updateNotify(notify);
            //更新流程状态
			if(notify.getState()!=null&&notify.getContentType()!=0){
				Notify notify1=new Notify();
				notify1.setCode(notify.getCode());
				notify1.setState(notify.getState());
				notifyDao.updateNotifyState(notify1);
			}
			
			return oaMsg;
		} catch (RuntimeException e) {
			LOG.error("service通知单更新失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service通知单更新失败",e);
		}
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_NOTIFY,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteNotify(NotifyDto notifyDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			notifyDao.deleteNotify(notifyDto);
			return oaMsg;
		} catch (RuntimeException e) {
			LOG.error("service通知单删除失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service通知单删除失败",e);
		}
	}

	@Override
	public OaMsg getCodeNum(NotifyDto notifyDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().add(notifyDao.getNotifyNum(notifyDto));
			return oaMsg;
		} catch (RuntimeException e) {
			LOG.error("service查询货体编号失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR,"service查询货体编号失败",e);
		}
		
	}

	@Override
	public OaMsg resetNotify(NotifyDto notifyDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			notifyDao.resetNotify(notifyDto);
			return oaMsg;
		} catch (RuntimeException e) {
			LOG.error("service通知单重置失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service通知单重置失败",e);
		}
	}

	/**
	 * @Title exportItemExcel
	 * @Descrption:TODO
	 * @param:@param notifyDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2017年1月10日上午11:17:28
	 * @throws
	 */
	@Override
	public HSSFWorkbook exportItemExcel(final HttpServletRequest request,final NotifyDto notifyDto) throws OAException {
		 if(13==Integer.valueOf(notifyDto.getTypes())){
		return  new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/notifyDock.xls",
				new CallBackSheets() {
					@Override
					public void setSheetsValue(HSSFWorkbook workbook) {
						try {
					List<Map<String, Object>> data=notifyDao.getNotifyShipInfoList(notifyDto, 0, 0);
					Map<String, Object> svg=null;
					if(notifyDto.getTransportId()!=null){
					TransportProgramDto tDto=new TransportProgramDto();
					tDto.setId(notifyDto.getTransportId());
					svg=transportProgramDao.getTransportProgramById(tDto);
					}
					HSSFSheet sheet=workbook.getSheetAt(0);
					Map<String, Object> itemMap=null;
					int[] code = {0x2610, 0x2611};// 是，否
					String yesStr=new String(code,1,1);
					String noStr=new String(code,0,1);
					List<Map<String, Object>> inCheckList=new ArrayList<Map<String,Object>>();
					Map<String, Object> inCheck=null;
					 List<String> choicelList=null;
					 String[] choice=null;
							if(data!=null&&data.size()>0){
								for(int i=0,len=data.size();i<len;i++){
									itemMap=data.get(i);
									if(Integer.valueOf(itemMap.get("contentType").toString())==0){
										String time=itemMap.get("createTime").toString();
										sheet.getRow(1).getCell(0).setCellValue(time.substring(0, 4)+"年"+time.substring(5, 7)+"月"+time.substring(8, 10)+"日");
										sheet.getRow(1).getCell(4).setCellValue(FormatUtils.getStringValue(itemMap.get("code")).substring(1,itemMap.get("code").toString().length()));
										sheet.getRow(3).getCell(0).setCellValue(notifyDto.getTaskMsg());
										sheet.getRow(7).getCell(0).setCellValue(notifyDto.getTaskRequire());
										if(svg!=null){
											String svgStr=svg.get("svg").toString();
											svgStr = svgStr.substring(0,4)+" xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' "+svgStr.substring(5) ;
											handleSVG(request,sheet,workbook,svgStr,0,5);	
										}
									}else if(Integer.valueOf(itemMap.get("contentType").toString())==1){
										if(Integer.valueOf(itemMap.get("flag").toString())==0){
											choice=itemMap.get("content").toString().split(",");
											 choicelList=Arrays.asList(choice);
											 if(choicelList.contains("b1"))
												 sheet.getRow(23).getCell(3).setCellValue("是"+yesStr+"      否"+noStr);
											 else
												 sheet.getRow(23).getCell(3).setCellValue("是"+yesStr+"      否"+yesStr);
											 if(choicelList.contains("c1"))
												 sheet.getRow(24).getCell(3).setCellValue("是"+yesStr+"      否"+noStr);
											 else
												 sheet.getRow(24).getCell(3).setCellValue("是"+yesStr+"      否"+yesStr);
											 if(choicelList.contains("d1"))
												 sheet.getRow(25).getCell(3).setCellValue("是"+yesStr+"      否"+noStr);
											 else
												 sheet.getRow(25).getCell(3).setCellValue("是"+yesStr+"      否"+yesStr);
											 if(choicelList.contains("e1"))
												 sheet.getRow(26).getCell(3).setCellValue("是"+yesStr+"      否"+noStr);
											 else
												 sheet.getRow(26).getCell(3).setCellValue("是"+yesStr+"      否"+yesStr);
											 if(choicelList.contains("f1"))
												 sheet.getRow(27).getCell(3).setCellValue("是"+yesStr+"      否"+noStr);
											 else
												 sheet.getRow(27).getCell(3).setCellValue("是"+yesStr+"      否"+yesStr);
											 if(choicelList.contains("g1"))
												 sheet.getRow(28).getCell(3).setCellValue("是"+yesStr+"      否"+noStr);
											 else
												 sheet.getRow(28).getCell(3).setCellValue("是"+yesStr+"      否"+yesStr);
											 if(choicelList.contains("h1"))
												 sheet.getRow(29).getCell(3).setCellValue("是"+yesStr+"      否"+noStr);
											 else
												 sheet.getRow(29).getCell(3).setCellValue("是"+yesStr+"      否"+yesStr);
											 if(choicelList.contains("i1"))
												 sheet.getRow(30).getCell(3).setCellValue("是"+yesStr+"      否"+noStr+"      不适用"+noStr);
											 else if(choicelList.contains("i3"))
												 sheet.getRow(30).getCell(3).setCellValue("是"+noStr+"      否"+noStr+"      不适用"+yesStr);
											 else
												 sheet.getRow(30).getCell(3).setCellValue("是"+yesStr+"      否"+yesStr+"      不适用"+noStr);
											 if(choicelList.contains("j1"))
												 sheet.getRow(31).getCell(3).setCellValue("是"+yesStr+"      否"+noStr);
											 else
												 sheet.getRow(31).getCell(3).setCellValue("是"+yesStr+"      否"+yesStr);
											 if(choicelList.contains("k1"))
												 sheet.getRow(32).getCell(3).setCellValue("是"+yesStr+"      否"+noStr);
											 else
												 sheet.getRow(32).getCell(3).setCellValue("是"+yesStr+"      否"+yesStr);
											 if(choicelList.contains("m1"))
												 sheet.getRow(34).getCell(3).setCellValue("是"+yesStr+"      否"+noStr);
											 else
												 sheet.getRow(34).getCell(3).setCellValue("是"+yesStr+"      否"+yesStr);
											 if(choicelList.contains("n1"))
												 sheet.getRow(35).getCell(3).setCellValue("是"+yesStr+"      否"+noStr);
											 else
												 sheet.getRow(35).getCell(3).setCellValue("是"+yesStr+"      否"+yesStr);
											 if(choicelList.contains("o1"))
												 sheet.getRow(36).getCell(3).setCellValue("是"+yesStr+"      否"+noStr);
											 else
												 sheet.getRow(36).getCell(3).setCellValue("是"+yesStr+"      否"+yesStr);
											 sheet.getRow(37).getCell(1).setCellValue("作业人员："+FormatUtils.getStringValue(itemMap.get("sureUserName")));
											 sheet.getRow(37).getCell(3).setCellValue("日期："+FormatUtils.getStringValue(itemMap.get("sureTime")));
										}else{
											if(itemMap.get("content").toString().equals("l1"))
												 sheet.getRow(33).getCell(3).setCellValue("是"+yesStr+"     否"+noStr);
											else
												 sheet.getRow(33).getCell(3).setCellValue("是"+noStr+"      否"+yesStr);
											sheet.getRow(33).getCell(5).setCellValue("调度或码头操作工确认:"+FormatUtils.getStringValue(itemMap.get("sureUserName")));
										}
									}else if(Integer.valueOf(itemMap.get("contentType").toString())==2){
										if(Integer.valueOf(itemMap.get("flag").toString())==0){
											choice=itemMap.get("content").toString().split(",");
											 choicelList=Arrays.asList(choice);
											 if(choicelList.contains("p1"))
												 sheet.getRow(38).getCell(3).setCellValue("是"+yesStr+"      否"+noStr);
											 else
												 sheet.getRow(38).getCell(3).setCellValue("是"+noStr+"      否"+yesStr);
											 if(choicelList.contains("q1"))
												 sheet.getRow(39).getCell(3).setCellValue("是"+yesStr+"      否"+noStr);
											 else
												 sheet.getRow(39).getCell(3).setCellValue("是"+noStr+"      否"+yesStr);
											 if(choicelList.contains("r1"))
												 sheet.getRow(40).getCell(3).setCellValue("是"+yesStr+"      否"+noStr);
											 else
												 sheet.getRow(40).getCell(3).setCellValue("是"+noStr+"      否"+yesStr);
											 inCheck=new HashMap<String, Object>();
											 inCheck.put("name", itemMap.get("sureUserName"));
											 inCheck.put("time", FormatUtils.getStringValue(itemMap.get("sureTime")));
											 inCheckList.add(inCheck);
											 sheet.getRow(41).getCell(1).setCellValue("作业人员："+FormatUtils.getStringValue(itemMap.get("sureUserName")));
											 sheet.getRow(41).getCell(3).setCellValue("日期："+FormatUtils.getStringValue(itemMap.get("sureTime")));
										}
									}else if(Integer.valueOf(itemMap.get("contentType").toString())==3){
										if(Integer.valueOf(itemMap.get("flag").toString())==0){
											choice=itemMap.get("content").toString().split(",");
											 choicelList=Arrays.asList(choice);
										 if(choicelList.contains("u1"))
											 sheet.getRow(44).getCell(3).setCellValue("是"+yesStr+"      否"+noStr);
										 else
											 sheet.getRow(44).getCell(3).setCellValue("是"+noStr+"      否"+yesStr);
										 if(choicelList.contains("v1"))
											 sheet.getRow(45).getCell(3).setCellValue("是"+yesStr+"      否"+noStr);
										 else
											 sheet.getRow(45).getCell(3).setCellValue("是"+noStr+"      否"+yesStr);
										 if(choicelList.contains("w1"))
											 sheet.getRow(46).getCell(3).setCellValue("是"+yesStr+"      否"+noStr);
										 else
											 sheet.getRow(46).getCell(3).setCellValue("是"+noStr+"      否"+yesStr);
										 if(choicelList.contains("x1"))
											 sheet.getRow(47).getCell(3).setCellValue("是"+yesStr+"      否"+noStr);
										 else
											 sheet.getRow(47).getCell(3).setCellValue("是"+noStr+"      否"+yesStr);
										 if(choicelList.contains("y1"))
											 sheet.getRow(48).getCell(3).setCellValue("是"+yesStr+"      否"+noStr);
										 else
											 sheet.getRow(48).getCell(3).setCellValue("是"+noStr+"      否"+yesStr);
										 sheet.getRow(49).getCell(1).setCellValue("作业人员："+FormatUtils.getStringValue(itemMap.get("sureUserName")));
										 sheet.getRow(49).getCell(3).setCellValue("日期："+FormatUtils.getStringValue(itemMap.get("sureTime")));
										}else{
											if(itemMap.get("content").toString().equals("t1"))
												 sheet.getRow(42).getCell(3).setCellValue("是"+yesStr+"      否"+noStr);
											else if(itemMap.get("content").toString().equals("s1"))
												 sheet.getRow(43).getCell(3).setCellValue("是"+yesStr+"     否"+noStr);
											else{
												sheet.getRow(42).getCell(3).setCellValue("是"+noStr+"      否"+yesStr);
												 sheet.getRow(43).getCell(3).setCellValue("是"+noStr+"      否"+yesStr);
											}
											sheet.getRow(42).getCell(5).setCellValue("调度或码头操作工确认:"+FormatUtils.getStringValue(itemMap.get("sureUserName")));
											sheet.getRow(43).getCell(5).setCellValue("调度或码头操作工确认:"+FormatUtils.getStringValue(itemMap.get("sureUserName")));
										}
									}
									
								}
								if(inCheckList.size()>1){
									sheet.shiftRows(42, 49, inCheckList.size()-1);
									sheet.getRow(41).getCell(1).setCellValue("作业人员："+FormatUtils.getStringValue(inCheckList.get(0).get("name")));
									 sheet.getRow(41).getCell(3).setCellValue("日期："+FormatUtils.getStringValue(inCheckList.get(0).get("time")));
									for(int j=1,inLen=inCheckList.size();j<inLen;j++){
										sheet.createRow(41+j).setHeight(sheet.getRow(41).getHeight());
										sheet.getRow(41+j).createCell(0).setCellStyle(sheet.getRow(41).getCell(0).getCellStyle());
										sheet.getRow(41+j).createCell(1).setCellStyle(sheet.getRow(41).getCell(1).getCellStyle());
										sheet.getRow(41+j).createCell(2).setCellStyle(sheet.getRow(41).getCell(2).getCellStyle());
										sheet.getRow(41+j).createCell(3).setCellStyle(sheet.getRow(41).getCell(3).getCellStyle());
										sheet.getRow(41+j).createCell(4).setCellStyle(sheet.getRow(41).getCell(4).getCellStyle());
										sheet.getRow(41+j).createCell(5).setCellStyle(sheet.getRow(41).getCell(5).getCellStyle());
										sheet.addMergedRegion(new Region(41+j,(short)1, 41+j, (short)2));
										sheet.addMergedRegion(new Region(41+j,(short)3, 41+j, (short)5));
										sheet.getRow(41+j).getCell(1).setCellValue("作业人员："+FormatUtils.getStringValue(inCheckList.get(j).get("name")));
										 sheet.getRow(41+j).getCell(3).setCellValue("日期："+FormatUtils.getStringValue(inCheckList.get(j).get("time")));
									}
									sheet.addMergedRegion(new Region(38,(short)0, 40+inCheckList.size(), (short)0));
								}
							}
						} catch (OAException e) {
							e.printStackTrace();
						}
					}
				}).getWorkbookWithSheets();
		 }else if(14==Integer.valueOf(notifyDto.getTypes())){
			 return  new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/notifyDynamic.xls",
						new CallBackSheets() {
							@Override
							public void setSheetsValue(HSSFWorkbook workbook) {
								try {
							List<Map<String, Object>> data=notifyDao.getNotifyShipInfoList(notifyDto, 0, 0);
							Map<String, Object> svg=null;
							if(notifyDto.getTransportId()!=null){
							TransportProgramDto tDto=new TransportProgramDto();
							tDto.setId(notifyDto.getTransportId());
							svg=transportProgramDao.getTransportProgramById(tDto);
							}
							HSSFSheet sheet=workbook.getSheetAt(0);
							Map<String, Object> itemMap=null;
							int[] code = {0x2610, 0x2611};// 是，否
							String yesStr=new String(code,1,1);
							String noStr=new String(code,0,1);
							List<Map<String, Object>> inCheckList=new ArrayList<Map<String,Object>>();
							Map<String, Object> inCheck=null;
							 List<String> choicelList=null;
							 String[] choice=null;
									if(data!=null&&data.size()>0){
										for(int i=0,len=data.size();i<len;i++){
											itemMap=data.get(i);
											if(Integer.valueOf(itemMap.get("contentType").toString())==0){
												String time=itemMap.get("createTime").toString();
												sheet.getRow(1).getCell(0).setCellValue(time.substring(0, 4)+"年"+time.substring(5, 7)+"月"+time.substring(8, 10)+"日");
												sheet.getRow(1).getCell(3).setCellValue(FormatUtils.getStringValue(itemMap.get("code")).substring(1,itemMap.get("code").toString().length()));
												sheet.getRow(3).getCell(0).setCellValue(notifyDto.getTaskMsg());
												sheet.getRow(7).getCell(0).setCellValue(notifyDto.getTaskRequire());
												if(svg!=null){
													String svgStr=svg.get("svg").toString();
													svgStr = svgStr.substring(0,4)+" xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' "+svgStr.substring(5) ;
													handleSVG(request,sheet,workbook,svgStr,0,5);	
												}
											}else if(Integer.valueOf(itemMap.get("contentType").toString())==1){
												if(Integer.valueOf(itemMap.get("flag").toString())==0){
													choice=itemMap.get("content").toString().split(",");
													 choicelList=Arrays.asList(choice);
													 if(choicelList.contains("b1"))
														 sheet.getRow(23).getCell(2).setCellValue("是"+yesStr+"      否"+noStr);
													 else
														 sheet.getRow(23).getCell(2).setCellValue("是"+yesStr+"      否"+yesStr);
													 if(choicelList.contains("c1"))
														 sheet.getRow(24).getCell(2).setCellValue("是"+yesStr+"      否"+noStr);
													 else
														 sheet.getRow(24).getCell(2).setCellValue("是"+yesStr+"      否"+yesStr);
													 if(choicelList.contains("d1"))
														 sheet.getRow(25).getCell(2).setCellValue("是"+yesStr+"      否"+noStr);
													 else
														 sheet.getRow(25).getCell(2).setCellValue("是"+yesStr+"      否"+yesStr);
													 if(choicelList.contains("e1"))
														 sheet.getRow(26).getCell(2).setCellValue("是"+yesStr+"      否"+noStr);
													 else
														 sheet.getRow(26).getCell(2).setCellValue("是"+yesStr+"      否"+yesStr);
													 if(choicelList.contains("f1"))
														 sheet.getRow(27).getCell(2).setCellValue("是"+yesStr+"      否"+noStr);
													 else
														 sheet.getRow(27).getCell(2).setCellValue("是"+yesStr+"      否"+yesStr);
													 sheet.getRow(28).getCell(1).setCellValue("作业人员："+FormatUtils.getStringValue(itemMap.get("sureUserName")));
													 sheet.getRow(28).getCell(2).setCellValue("日期："+FormatUtils.getStringValue(itemMap.get("sureTime")));
												}else{
													if(itemMap.get("content").toString().equals("a1"))
														 sheet.getRow(22).getCell(2).setCellValue("是"+yesStr+"      否"+noStr);
													else
														 sheet.getRow(22).getCell(2).setCellValue("是"+noStr+"      否"+yesStr);
													sheet.getRow(22).getCell(3).setCellValue("调度确认:"+FormatUtils.getStringValue(itemMap.get("sureUserName")));
												}
											}else if(Integer.valueOf(itemMap.get("contentType").toString())==2){
												if(Integer.valueOf(itemMap.get("flag").toString())==0){
													choice=itemMap.get("content").toString().split(",");
													 choicelList=Arrays.asList(choice);
													 if(choicelList.contains("h1"))
														 sheet.getRow(29).getCell(2).setCellValue("是"+yesStr+"      否"+noStr);
													 else
														 sheet.getRow(29).getCell(2).setCellValue("是"+noStr+"      否"+yesStr);
													 if(choicelList.contains("i1"))
														 sheet.getRow(30).getCell(2).setCellValue("是"+yesStr+"      否"+noStr);
													 else
														 sheet.getRow(30).getCell(2).setCellValue("是"+noStr+"      否"+yesStr);
													 if(choicelList.contains("j1"))
														 sheet.getRow(31).getCell(2).setCellValue("是"+yesStr+"      否"+noStr+"      不适用"+noStr);
													 else if(choicelList.contains("j3"))
														 sheet.getRow(31).getCell(2).setCellValue("是"+noStr+"      否"+noStr+"      不适用"+yesStr);
													 else
														 sheet.getRow(31).getCell(2).setCellValue("是"+noStr+"      否"+yesStr+"      不适用"+noStr);
													 if(choicelList.contains("k1"))
														 sheet.getRow(32).getCell(2).setCellValue("是"+yesStr+"      否"+noStr+"      不适用"+noStr);
													 else if(choicelList.contains("k3"))
														 sheet.getRow(32).getCell(2).setCellValue("是"+noStr+"      否"+noStr+"      不适用"+yesStr);
													 else
														 sheet.getRow(32).getCell(2).setCellValue("是"+noStr+"      否"+yesStr+"      不适用"+noStr);
													 if(choicelList.contains("l1"))
														 sheet.getRow(33).getCell(2).setCellValue("是"+yesStr+"      否"+noStr+"      不适用"+noStr);
													 else if(choicelList.contains("l3"))
														 sheet.getRow(33).getCell(2).setCellValue("是"+noStr+"      否"+noStr+"      不适用"+yesStr);
													 else
														 sheet.getRow(33).getCell(2).setCellValue("是"+noStr+"      否"+yesStr+"      不适用"+noStr);
													 inCheck=new HashMap<String, Object>();
													 inCheck.put("name", itemMap.get("sureUserName"));
													 inCheck.put("time", FormatUtils.getStringValue(itemMap.get("sureTime")));
													 inCheckList.add(inCheck);
													 sheet.getRow(34).getCell(1).setCellValue("作业人员："+FormatUtils.getStringValue(itemMap.get("sureUserName")));
													 sheet.getRow(34).getCell(2).setCellValue("日期："+FormatUtils.getStringValue(itemMap.get("sureTime")));
												}
											}else if(Integer.valueOf(itemMap.get("contentType").toString())==3){
												choice=itemMap.get("content").toString().split(",");
												 choicelList=Arrays.asList(choice);
												 if(choicelList.contains("m1"))
													 sheet.getRow(35).getCell(2).setCellValue("是"+yesStr+"      否"+noStr);
												 else
													 sheet.getRow(35).getCell(2).setCellValue("是"+noStr+"      否"+yesStr);
												 if(choicelList.contains("n1"))
													 sheet.getRow(36).getCell(2).setCellValue("是"+yesStr+"      否"+noStr);
												 else
													 sheet.getRow(36).getCell(2).setCellValue("是"+noStr+"      否"+yesStr);
												 if(choicelList.contains("o1"))
													 sheet.getRow(37).getCell(2).setCellValue("是"+yesStr+"      否"+noStr);
												 else
													 sheet.getRow(37).getCell(2).setCellValue("是"+noStr+"      否"+yesStr);
												 if(choicelList.contains("p1"))
													 sheet.getRow(38).getCell(2).setCellValue("是"+yesStr+"      否"+noStr);
												 else
													 sheet.getRow(38).getCell(2).setCellValue("是"+noStr+"      否"+yesStr);
												 sheet.getRow(39).getCell(1).setCellValue("作业人员："+FormatUtils.getStringValue(itemMap.get("sureUserName")));
												 sheet.getRow(39).getCell(2).setCellValue("日期："+FormatUtils.getStringValue(itemMap.get("sureTime")));
											}
											
										}
										if(inCheckList.size()>1){
											sheet.shiftRows(35, 39, inCheckList.size()-1);
											sheet.getRow(34).getCell(1).setCellValue("作业人员："+FormatUtils.getStringValue(inCheckList.get(0).get("name")));
											 sheet.getRow(34).getCell(2).setCellValue("日期："+FormatUtils.getStringValue(inCheckList.get(0).get("time")));
											for(int j=1,inLen=inCheckList.size();j<inLen;j++){
												sheet.createRow(34+j).setHeight(sheet.getRow(34).getHeight());
												sheet.getRow(34+j).createCell(0).setCellStyle(sheet.getRow(34).getCell(0).getCellStyle());
												sheet.getRow(34+j).createCell(1).setCellStyle(sheet.getRow(34).getCell(1).getCellStyle());
												sheet.getRow(34+j).createCell(2).setCellStyle(sheet.getRow(34).getCell(2).getCellStyle());
												sheet.getRow(34+j).createCell(3).setCellStyle(sheet.getRow(34).getCell(3).getCellStyle());
												sheet.addMergedRegion(new Region(34+j,(short)2, 34+j, (short)3));
												sheet.getRow(34+j).getCell(1).setCellValue("作业人员："+FormatUtils.getStringValue(inCheckList.get(j).get("name")));
												 sheet.getRow(34+j).getCell(2).setCellValue("日期："+FormatUtils.getStringValue(inCheckList.get(j).get("time")));
											}
											sheet.addMergedRegion(new Region(29,(short)0, 33+inCheckList.size(), (short)0));
										}
									}
								} catch (OAException e) {
									e.printStackTrace();
								}
							}
						}).getWorkbookWithSheets();
		 }else{
			 return null;
		 }
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
	     t.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, new Float(555)) ;
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
}
