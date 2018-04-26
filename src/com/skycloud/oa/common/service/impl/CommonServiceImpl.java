package com.skycloud.oa.common.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFCreationHelper;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.skycloud.oa.common.dao.BaseControllerDao;
import com.skycloud.oa.common.service.CommonService;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.NotifyDao;
import com.skycloud.oa.inbound.dto.NotifyDto;
import com.skycloud.oa.outbound.dao.OutBoundDao;
import com.skycloud.oa.report.utils.FormatUtils;
import com.skycloud.oa.utils.OaMsg;

/**
 * 公共业务逻辑实现类
 * @ClassName: CommonServiceImpl 
 * @Description: TODO
 * @author xie
 * @date 2015年4月2日 下午10:47:01
 */
@Service
public class CommonServiceImpl implements CommonService {
	
	private static Logger LOG = Logger.getLogger(CommonServiceImpl.class);
	@Autowired
	private OutBoundDao shipDeliverWorkDao;
	@Autowired
	private NotifyDao notifyDao;
	@Autowired
	private BaseControllerDao baseControllerDao;
	@Override
	public HSSFWorkbook exportExcel(HttpServletRequest request,String type,String params) {
		try {
			if ("1".equals((String) request.getParameter("type"))) {
				return exportOutArrivalExcel1(request, type, params);
			} else {
				return exportOutArrivalExcel(request, type, params);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return exportErrorExcel();
		}
	}
	public HSSFWorkbook exportErrorExcel(){
		 HSSFWorkbook wb = new HSSFWorkbook();
		 HSSFSheet sheet = wb.createSheet("new   sheet");      
         HSSFCellStyle style = wb.createCellStyle(); // 样式对象      
         style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直      
         style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平      
         HSSFRow row = sheet.createRow((short) 0);   
         handleCell(sheet, row, style,"系统错误，请联系管理员。", 0, 0, 0, 13) ;
		return wb;
	}
	
	public HSSFWorkbook exportOutArrivalExcel1(HttpServletRequest request,String type,String params){
 		 HSSFWorkbook wb = new HSSFWorkbook();   
		try{   
			 HSSFSheet sheet = wb.createSheet("new   sheet");      
	         HSSFCellStyle style = wb.createCellStyle(); // 样式对象      
	         HSSFCellStyle style1 = wb.createCellStyle(); // 样式对象         
	         style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
	         style1.setVerticalAlignment(HSSFCellStyle.ALIGN_LEFT);// 垂直    
	         style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平    
	         style1.setWrapText(true);  
			
			
	        NotifyDto notifyDto=new NotifyDto();
			notifyDto.setCode(request.getParameter("code"));
			
			 if((request.getParameter("code").charAt(0)) == 'P'){
				 notifyDto.setTypes(15+"");
			 }else{
				 notifyDto.setTypes(16+"");
			 }
			
			List<Map<String,Object>> list=notifyDao.getNotifyList(notifyDto, 0, 0);
		    Map<String,Object> map=list.get(0);
		    int state=Integer.valueOf(map.get("state").toString());
		    
		    String content1=" ";
		    String content2=" ";
		    String content3=" ";
            String name1="";
            String name2="";
            String name3="";
            String name4="";
            String name5="";
            String name6="";
            String time1="";
            String time2="";
            String time3="";

		   for (int i = 1; i < list.size(); i++) {
			if(list.get(i).get("contentType").toString().equals("1")){
				content1+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("2")){
					name1+=list.get(i).get("sureUserName").toString();
					time1+=list.get(i).get("sureTime").toString();
				}
				if(list.get(i).get("flag").toString().equals("1")){
					name4+=list.get(i).get("sureUserName").toString();
				}
			}else if(list.get(i).get("contentType").toString().equals("3")){
				content3+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("4")){
					name3+=list.get(i).get("sureUserName").toString();
					time3+=list.get(i).get("sureTime").toString();
				}
			}else if(list.get(i).get("contentType").toString().equals("2")){
				content2+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("3")){
					name2+=list.get(i).get("sureUserName").toString();
					time2+=list.get(i).get("sureTime").toString();
				}
				if(list.get(i).get("flag").toString().equals("1")){
					if(list.get(i).get("content").toString().charAt(0)=='i'){
						name5+=list.get(i).get("sureUserName").toString();	
					}
                    if(list.get(i).get("content").toString().charAt(0)=='j'){
                    	name6+=list.get(i).get("sureUserName").toString();
					}
				}
			}
		}
			
		    HSSFRow rowtitle = sheet.createRow((short) 0); 
			HSSFRow row0 = sheet.createRow((short) 2); 

			
			if((request.getParameter("code").charAt(0)) == 'P'){
				handleCell(sheet, rowtitle, style, "船发作业通知单（码头）", 0, 0, 1,13);
				}else{
					handleCell(sheet, rowtitle, style, "船发作业通知单（库区）", 0, 0, 1,13);	
				}
			handleCell(sheet, row0, style,  "日期:"+map.get("createTime").toString().substring(0,11), 2, 0, 2, 4,0);
            handleCell(sheet, row0, style,  "JL2303-4", 2, 5, 2, 8,5);
            handleCell(sheet, row0, style,  "编号:"+request.getParameter("code").toString().substring(1), 2, 9, 2, 13,9);
             
            HSSFRow row = sheet.createRow((short) 3);      
            HSSFRow row2 = sheet.createRow((short) 4);      
            HSSFRow row3 = sheet.createRow((short) 14);      
            HSSFRow row4 = sheet.createRow((short) 18);      
            HSSFRow row5 = sheet.createRow((short) 32);      
            HSSFRow row6 = sheet.createRow((short) 33);      
           
            handleCell(sheet, row, style, request.getParameter("t1"), 3, 0, 3, 13) ;
            handleCell(sheet, row2, style, request.getParameter("v1"), 4, 0, 13, 13) ;
            handleCell(sheet, row3, style, request.getParameter("t2"), 14, 0, 14, 13) ;
            handleCell(sheet, row4, style, "", 15, 0, 31, 13) ;
            String svg = "" ;
            if(request.getParameter("tpId")!=null&&request.getParameter("v2")!=""){
				Map<String,Object> map1 = shipDeliverWorkDao.getTranspInfoById(request.getParameter("tpId")) ;
                 svg = (String)map1 .get("svg");
                 svg = svg.substring(0,4)+" xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' "+svg.substring(5) ;
                 handleSVG(request, sheet, wb, svg, 0, 15) ;
            }
            
            if(null!=request.getParameter("t3")){
            	handleCell(sheet, row5, style, request.getParameter("t3"), 32, 0, 32, 13) ;
            	handleCell(sheet, row6, style, request.getParameter("v3"), 33, 0, 46, 13) ;
            }
            
            HSSFRow row7 = sheet.createRow((short) 47);
			handleCell(sheet, row7, style, "调度:"+map.get("sureUserName").toString(), 47, 0, 47,13);    

            if((request.getParameter("code").charAt(0)) == 'P'){
            	
            	
            HSSFRow row8 = sheet.createRow((short) 48);
			handleCell(sheet, row8, style1, "三、作业注意事项：", 48, 0, 48	,13);
			
		    HSSFRow row9 = sheet.createRow((short) 49);
			handleCell(sheet, row9, style1,  
					 "1、正确佩戴个人防护用品；\n"
                    +"2、管线、船名是否与作业要求一致；\n"
                    +"3、关注船舶干弦，注意风力变化；\n"
                    +"4、关注作业船舶周边水域情况；\n"
                    +"5、作业期间加强与船方联系，防止发生溢料。\n"
                    +"6、发生异常情况及时与调度联系。", 49, 0, 54,13);
			
			    HSSFRow row10 = sheet.createRow((short) 55);
				handleCell(sheet, row10, style1, "四、作业过程中的风险分析及相关措施：", 55, 0, 55	,13);
					
				HSSFRow row11 = sheet.createRow((short) 56);
				handleCell(sheet, row11, style,  "危害及潜在事件：", 56, 0, 56, 4, 0);
				handleCell(sheet, row11, style,  "主要后果", 56, 5, 56, 8, 5);
				handleCell(sheet, row11, style,  "安全措施", 56, 9, 56, 13, 9);
				
				String[] value={"1、未按规定佩戴劳动防护用品","人身伤害","正确佩戴个人防护用品",
				         "2、使用非防爆工具，产生火花","火灾、爆炸","使用防爆工具",
				         "3、发生异常情况时现场无应急设施","火灾、爆炸、污染环境","按要求正确放置消防及防污染设施",
				         "4、人体静电未释放接触可燃气体","火灾、爆炸","进入作业场所前释放人体静电",
				         "5、作业人员接错管线","发错料，泵、管线损坏","根据作业通知单认真检查管线状态，与调度保持联系、确认。",
				         "6、油品船未布设围油栏","污染环境","按要求",
				         "7、超发引起船上溢料","污染环境","码头保持与船方联系，调度加强监控，防止超发。",
				         "8、管线上阀门丝杆处泄漏","污染环境","使用不锈钢器具及时盛接、回收",
				         "9、软管没有正确连接，扭曲损坏造成物料渗漏","污染环境","正确连接软管，加强检查"
					            };
				
				for (int j = 0; j < 9; j++) {
					HSSFRow rowj = sheet.createRow((short) 57+j);
					handleCell(sheet, rowj, style1,  value[0+j*3], 57+j, 0, 57+j, 4, 0);
					handleCell(sheet, rowj, style1,  value[1+j*3], 57+j, 5, 57+j, 8, 5);
					handleCell(sheet, rowj, style1,  value[2+j*3], 57+j, 9, 57+j, 13, 9);
				}
				if(state==4){
				HSSFRow row21 = sheet.createRow((short) 66);
				handleCell(sheet, row21, style1, "五、作业过程的检查", 66, 0, 66,13);
				
				sheet.setColumnWidth(0,700);
					
				HSSFRow row22 = sheet.createRow((short) 67);
				handleCell(sheet, row22, style1, "作业前检查", 67, 0, 75,0);
				handleCell(sheet, row22, style1,  "1、个人防护用品是否正确佩戴", 67, 1, 67, 6, 1);
				handleCell(sheet, row22, style1,  content1.charAt(content1.lastIndexOf("a")+1)=='1'?"是☑      否☐":"是☐      否☑", 67, 7, 67, 13, 7);
				
				String[] value1={
				         "2、通讯系统是否通畅","b",
				         "3、消防器材是否到位","c",
				         "4、是否释放身体静电","d",
				         "5、确认工艺流程是否正确","e",
				         "6、确认船方是否处于适装状态","f",
				         "7、管线是否与船上接口安全可靠连接","g",
				         "8、装货船舶周边水域是否安全","h",
				         "9、中控室SCADA系统运行是否正常","i"
		                 };
		
		for (int x = 0; x <8; x++) {
			HSSFRow rowx = sheet.createRow((short) 68+x);
			handleCell(sheet, rowx, style1,  value1[x*2], 68+x, 1, 68+x, 6, 1);
			if(x==7){
				handleCell(sheet, rowx, style1,  content1.charAt(content1.lastIndexOf(value1[x*2+1])+1)=='1'?"是☑      否☐":"是☐      否☑", 68+x, 7, 68+x, 11, 7);
				handleCell(sheet, rowx, style1,  "调度确认："+name4, 68+x, 12, 68+x, 13, 12);
			}else{
				handleCell(sheet, rowx, style1,  content1.charAt(content1.lastIndexOf(value1[x*2+1])+1)=='1'?"是☑      否☐":"是☐      否☑", 68+x, 7, 68+x, 13, 7);
			}
			
		}
				
		HSSFRow row20 = sheet.createRow((short) 76);
		handleCell(sheet, row20, style1,  "作业人员:"+name1, 76, 0, 76, 6, 0);
		handleCell(sheet, row20, style1,  "日期:"+time1, 76, 7, 76, 13, 7);
				
				HSSFRow row36 = sheet.createRow((short) 77);
				handleCell(sheet, row36, style1, "作业中检查", 77, 0, 80,0);
				handleCell(sheet, row36, style1,  "1、发货过程是否正常", 77, 1, 77, 6, 1);	
				handleCell(sheet, row36, style1,  content2.charAt(content2.lastIndexOf("j")+1)=='1'?"是☑      否☐":"是☐      否☑", 77, 7, 77, 13, 7);
				
				String[] value2={
				         "2、装货船舶周边水域是否安全","k",
				         "3、是否有物料滴漏","l",
				         "4、是否按要求进行巡检","m"
		                 };
				
				for (int y = 0; y < 3; y++) {
					HSSFRow rowy = sheet.createRow((short) 78+y);
					handleCell(sheet, rowy, style1,  value2[y*2], 78+y, 1, 78+y, 6, 1);
					handleCell(sheet, rowy, style1,  content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='1'?"是☑      否☐":"是☐      否☑", 78+y, 7, 78+y, 13, 7);
				}
				
				HSSFRow row19 = sheet.createRow((short) 81);
				handleCell(sheet, row19, style1,  "作业人员:"+name2, 81, 0, 81, 6, 0);
				handleCell(sheet, row19, style1,  "日期:"+time2, 81, 7, 81, 13, 7);
				
				HSSFRow row39 = sheet.createRow((short) 82);
				handleCell(sheet, row39, style1, "作业后检查", 82, 0, 86,0);
				handleCell(sheet, row39, style1,  "1、阀门是否关闭", 82, 1, 82, 6, 1);
				handleCell(sheet, row39, style1,  content3.charAt(content3.lastIndexOf("n")+1)=='1'?"是☑      否☐":"是☐      否☑", 82, 7, 82, 13, 7);
				
				String[] value3={"2、管线是否拆除复位，并加封盲板","o",
				         "3、管线是否泄压","p",
				         "4、现场是否整理干净","q",
				         "5、记录是否完善","r"
		                 };
  for (int z = 0; z < 4; z++) {
	   HSSFRow rowz = sheet.createRow((short) 83+z);
	   handleCell(sheet, rowz, style1,  value3[z*2], 83+z, 1, 83+z, 6, 1);
	   handleCell(sheet, rowz, style1,  content3.charAt(content3.lastIndexOf(value3[z*2+1])+1)=='1'?"是☑      否☐":"是☐      否☑", 83+z, 7, 83+z, 13, 7);}
    HSSFRow row18 = sheet.createRow((short) 87);
	handleCell(sheet, row18, style1, "作业人员:"+name3, 87, 0, 87, 6, 0);
	handleCell(sheet, row18, style1, "日期:"+time3, 87, 7, 87, 13, 7);
				
				}			
            }else{
            	
            	HSSFRow row8 = sheet.createRow((short) 48);
    			handleCell(sheet, row8, style1, "四、作业注意事项：", 48, 0, 48, 13);
            	HSSFRow row9 = sheet.createRow((short) 49);
    			handleCell(sheet, row9, style1,  
    					  "1、正确佩戴个人防护用品；\n"
                         +"2、发货时储罐应开进口阀\n"
                         +"3、发货时检查储罐呼吸阀是否正常，如有异常及时与调度联系；\n"
                         +"4、作业过程中加强检查，与调度保持联系；\n"
                         +"5、中控室加强对储罐的监控\n"
                         +"6、放气作业结束，放气过程中产生的物料及时回收入库。", 49, 0, 54,13);
    			
    			    HSSFRow row10 = sheet.createRow((short) 55);
    				handleCell(sheet, row10, style1, "五、作业过程中的风险分析及相关措施：", 55, 0, 55	,13);
    					
    				HSSFRow row12 = sheet.createRow((short) 56);
    				handleCell(sheet, row12, style,  "危害或潜在事件", 56, 0, 56, 4, 0);
    				handleCell(sheet, row12, style,  "主要后果", 56, 5, 56, 8, 5);
    				handleCell(sheet, row12, style,  "安全措施", 56, 9, 56, 13, 9);
    				
    				String[] value={"1、个人防护用品不适用或未正确佩戴","人身伤害","正确佩戴合适的个人防护用品",
   				         "2、使用非防爆工具，产生火花","火灾、爆炸","使用防爆工具",
   				         "3、人体静电接触可燃气体","火灾、爆炸","进入作业场所前释放人体静电",
   				         "4、作业前未检查管线状态","发错罐、泵损坏、物料串罐","根据作业通知单认真检查管线状态，与调度保持联系、确认。",
   				         "5、未按要求开启储罐阀门","物料出现品质问题","按要求开启作业罐的阀门",
   				         "6、阀门泄漏","污染环境","使用不锈钢器具及时盛接、回收",
   				         "7、罐呼吸阀故障","造成瘪罐","严格按操作规程执行",
   				         "8、发货过程未及时复查、巡检","发货时阀门、管线泄漏未能及时发现，造成安全隐患。","在发货过程中继续关注设备运行情况，按要求进行巡检。",
   				         "9、压管线压力过高","管线破裂、阀门渗漏、泵坏。","关注压力，开旁路压管线，及时关泵",
   				         "10、结束后管线及时泄压","管线涨压破裂","结束后及时与调度联系，及时开启泄压阀。",
   					            };
   				
   				for (int j = 0; j < 10; j++) {
   					HSSFRow rowj = sheet.createRow((short) 57+j);
   					handleCell(sheet, rowj, style1,  value[0+j*3], 57+j, 0, 57+j, 4, 0);
   					handleCell(sheet, rowj, style1,  value[1+j*3], 57+j, 5, 57+j, 8, 5);
   					handleCell(sheet, rowj, style1,  value[2+j*3], 57+j, 9, 57+j, 13, 9);
   				}
    				
   				if(state==4){
    				HSSFRow row22 = sheet.createRow((short) 67);
    				handleCell(sheet, row10, style1, "六、作业过程的检查", 67, 0, 67,13);
    				
    				sheet.setColumnWidth(0,700);
    				
    				HSSFRow row24 = sheet.createRow((short) 68);
    				handleCell(sheet, row22, style1, "作业前检查", 68, 0, 75,0);
    				handleCell(sheet, row24, style1,  "1、个人防护用品是否正确佩戴", 68, 1, 68, 6, 1);
    				handleCell(sheet, row24, style1,  content1.charAt(content1.lastIndexOf("a")+1)=='1'?"是☑      否☐":"是☐      否☑", 68, 7, 68, 13, 7);
    				
    				String[] value1={
					         "2、通讯系统是否通畅","b",
					         "3、消防器材是否到位","c",
					         "4、是否释放身体静电","d",
					         "5、确认工艺流程是否正确","e",
					         "6、确认相关阀门是否按要求开启、关闭","f",
					         "7、泵是否送电、点动试车","g",
					         "8、中控室SCADA系统参数是否正常","h"
			                 };
			
			for (int x = 0; x <7; x++) {
				HSSFRow rowx = sheet.createRow((short) 69+x);
				handleCell(sheet, rowx, style1,  value1[x*2], 69+x, 1, 69+x, 6, 1);
				if(x==6){
					handleCell(sheet, rowx, style1,  content1.charAt(content1.lastIndexOf(value1[x*2+1])+1)=='1'?"是☑      否☐":"是☐      否☑", 69+x, 7, 69+x, 11, 7);
					handleCell(sheet, rowx, style1,  "调度确认："+name4, 69+x, 12, 69+x, 13, 12);
				}else{
					handleCell(sheet, rowx, style1,  content1.charAt(content1.lastIndexOf(value1[x*2+1])+1)=='1'?"是☑      否☐":"是☐      否☑", 69+x, 7, 69+x, 13, 7);
				}
				
			}
			
			HSSFRow row18 = sheet.createRow((short) 76);
			handleCell(sheet, row18, style1,  "作业人员:"+name1, 76, 0, 76, 6, 0);
			handleCell(sheet, row18, style1,  "日期:"+time1, 76, 7, 76, 13, 7);
    				
    				HSSFRow row32 = sheet.createRow((short) 77);
    				handleCell(sheet, row32, style1, "作业中检查", 77, 0, 82,0);
					handleCell(sheet, row32, style1,  "1、中控室SCADA系统是否正常下降", 77, 1, 77, 6, 1);
					handleCell(sheet, row32, style1,  content2.charAt(content2.lastIndexOf("i")+1)=='1'?"是☑      否☐":"是☐      否☑", 77, 7, 77, 11, 7);
					handleCell(sheet, row32, style1,  "调度确认："+name5, 77, 12, 77, 13, 12);
					
					String[] value2={
					         "2、装货流速是否正常","j",
					         "3、储罐呼吸阀是否正常","k",
					         "4、泵运行是否正常","l",
					         "5、是否有物料滴漏","m",
					         "6、是否按要求进行巡检","n"
			                 };
					
					for (int y = 0; y < 5; y++) {
						HSSFRow rowx = sheet.createRow((short) 78+y);
						handleCell(sheet, rowx, style1,  value2[y*2], 78+y, 1, 78+y, 6, 1);
						if(y==0){
							handleCell(sheet, rowx, style1,  content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='1'?"是☑      否☐":"是☐      否☑", 78+y, 7, 78+y, 11, 7);
							handleCell(sheet, rowx, style1,  "调度确认："+name6, 78+y, 12, 78+y, 13, 12);
						}else{
							handleCell(sheet, rowx, style1,  content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='1'?"是☑      否☐":"是☐      否☑", 78+y, 7, 78+y, 13, 7);	
						}
					}
					
					HSSFRow row17 = sheet.createRow((short) 83);
					handleCell(sheet, row17, style1,  "作业人员:"+name2, 83, 0, 83, 6, 0);
					handleCell(sheet, row17, style1,  "日期:"+time2, 83, 7, 83, 13, 7);
    				
					HSSFRow row38 = sheet.createRow((short) 84);
					handleCell(sheet, row38, style1, "作业后检查", 84, 0, 88,0);;
					handleCell(sheet, row38, style1,  "1、泵是否停止", 84, 1, 84, 6, 1);
					handleCell(sheet, row38, style1,  content3.charAt(content3.lastIndexOf("o")+1)=='1'?"是☑      否☐":"是☐      否☑", 84, 7, 84, 13, 7);
					
					String[] value3={
					         "2、管线是否泄压","p",
					         "3、现场是否整理干净","q",
					         "4、物料是否正确回收并标记入库","r",
					         "5、记录是否完善","s",
			                 };
	   for (int z = 0; z < 4; z++) {
		   HSSFRow rowz = sheet.createRow((short) 85+z);
		   handleCell(sheet, rowz, style1,  value3[z*2], 85+z, 1, 85+z, 6, 1);
		   handleCell(sheet, rowz, style1,  content3.charAt(content3.lastIndexOf(value3[z*2+1])+1)=='1'?"是☑      否☐":"是☐      否☑", 85+z, 7, 85+z, 13, 7);
	 }
				
	    HSSFRow row46 = sheet.createRow((short) 89);
		handleCell(sheet, row46, style1,  "作业人员:"+name3, 89, 0, 89, 6, 0);
		handleCell(sheet, row46, style1,  "日期:"+time3, 89, 7, 89, 13, 7);
   				}
     }      
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wb ;
	}
	public String getFlagInfo(String str){
		if("true".equals(str)){
			return "不适用" ;
		}else{
			return "适用" ;
		}
	}
	
	
	public HSSFWorkbook exportOutArrivalExcel(HttpServletRequest request,String type,String params) throws Exception{
		HSSFWorkbook wb = new HSSFWorkbook();   
		HSSFSheet sheet = wb.createSheet("new   sheet");      
		HSSFCellStyle style = wb.createCellStyle(); // 样式对象      
		HSSFCellStyle style1 = wb.createCellStyle(); // 样式对象  
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);// 垂直  
		style1.setWrapText(true);  
		style1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直      
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
		
		if(request.getParameter("Type")!=null){
			NotifyDto notifyDto=new NotifyDto();
			notifyDto.setTypes(request.getParameter("Type"));
			notifyDto.setCodeF(request.getParameter("code"));
			notifyDto.setKeyWord(request.getParameter("code"));
			notifyDto.setIsList(1);
			SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
			Date date; 
			Date date1; 
			String keyWord="";
			
			if(!request.getParameter("startTime").toString().equals("")){
				date = format.parse(request.getParameter("startTime").toString());
				notifyDto.setStartTime(date.getTime()/1000);
			}
			if(!request.getParameter("endTime").toString().equals("")){
				date1 = format.parse(request.getParameter("endTime").toString());
				notifyDto.setEndTime(date1.getTime()/1000);
			}
			if(!request.getParameter("keyWord").toString().equals("")){
				keyWord = request.getParameter("keyWord").toString();
				notifyDto.setKeyWord(keyWord);
			}
			
			HSSFRow row = sheet.createRow((short) 0);
			handleCell(sheet, row, style, "编号", 0, 0, 0,0,0);
			if("0".equals((String)request.getParameter("Type"))){
				
			handleCell(sheet, row, style, "作业要求", 0, 1, 0, 7, 1);
			
			}else if("1".equals((String)request.getParameter("Type"))){
				
				handleCell(sheet, row, style, "作业任务", 0, 1, 0, 3, 1);
				handleCell(sheet, row, style, "罐号", 0, 4, 0, 5, 4);
				handleCell(sheet, row, style, "管线", 0, 6, 0, 7, 6);
				
			}else if("2".equals((String)request.getParameter("Type"))
					||"3".equals((String)request.getParameter("Type"))){
				
				handleCell(sheet, row, style, "船名", 0, 1, 0, 2, 1);
				handleCell(sheet, row, style, "货品", 0, 3, 0, 4, 3);
				handleCell(sheet, row, style, "数量（吨）", 0, 5, 0, 6, 5);
				handleCell(sheet, row, style, "泊位", 0, 7, 0, 7, 7);
	
			}else if("4".equals((String)request.getParameter("Type"))
					||"5".equals((String)request.getParameter("Type"))){
				
				handleCell(sheet, row, style, "作业要求", 0, 1, 0, 4, 1);
				handleCell(sheet, row, style, "管线号", 0, 5, 0, 5, 5);
				handleCell(sheet, row, style, "物料名", 0, 6, 0, 7, 6);
				
			}else if("6".equals((String)request.getParameter("Type"))
					||"7".equals((String)request.getParameter("Type"))
					||"12".equals((String)request.getParameter("Type"))){
				
				handleCell(sheet, row, style, "作业任务", 0, 1, 0, 3, 1);
				handleCell(sheet, row, style, "作业要求", 0, 4, 0, 7, 4);
				
			}else if("8".equals((String)request.getParameter("Type"))){
				
				handleCell(sheet, row, style, "作业要求", 0, 1, 0, 3, 1);
				handleCell(sheet, row, style, "罐号", 0, 4, 0, 4, 4);
				handleCell(sheet, row, style, "物料名称", 0, 5, 0, 5, 5);
				handleCell(sheet, row, style, "数量", 0, 6, 0, 6, 6);
				handleCell(sheet, row, style, "泵号", 0, 7, 0, 7, 7);
				
			}else if("9".equals((String)request.getParameter("Type"))
					||"17".equals((String)request.getParameter("Type"))){
				
				handleCell(sheet, row, style, "作业任务", 0, 1, 0, 7, 1);
			
			}else if("10".equals((String)request.getParameter("Type"))){
				
				handleCell(sheet, row, style, "作业任务", 0, 1, 0, 3, 1);
				handleCell(sheet, row, style, "罐号", 0, 4, 0, 5, 4);
				handleCell(sheet, row, style, "物料名称", 0, 6, 0, 7, 6);
			
			}else if("11".equals((String)request.getParameter("Type"))){
				
				handleCell(sheet, row, style, "作业任务", 0, 1, 0, 3, 1);
				handleCell(sheet, row, style, "罐号", 0, 4, 0, 5, 4);
				handleCell(sheet, row, style, "物料名称", 0, 6, 0, 7, 6);
			
			}else if("13".equals((String)request.getParameter("Type"))
					||"14".equals((String)request.getParameter("Type"))
					||"15".equals((String)request.getParameter("Type"))
					||"16".equals((String)request.getParameter("Type"))){
				
				handleCell(sheet, row, style, "船名", 0, 1, 0, 1, 1);
				handleCell(sheet, row, style, "货品", 0, 2, 0, 2, 2);
				handleCell(sheet, row, style, "数量（吨）", 0, 3, 0, 3, 3);
				handleCell(sheet, row, style, "泊位", 0, 4, 0, 4, 4);
				handleCell(sheet, row, style, "作业要求", 0, 5, 0, 7, 5);
			
			}
			
			handleCell(sheet, row, style, "创建时间", 0, 8, 0, 9, 8);
			handleCell(sheet, row, style, "创建人", 0, 10, 0, 10, 10);
			handleCell(sheet, row, style, "作业前", 0, 11, 0, 11, 11);
			handleCell(sheet, row, style, "作业后", 0, 12, 0, 12, 12);
			handleCell(sheet, row, style, "状态", 0, 13, 0, 13, 13);
			
		    List<Map<String,Object>> data=notifyDao.getNotifyShipInfoList(notifyDto, 0, 0);
		    		
		    
		   
				for (int i = 0; i < data.size(); i++) {
					
					
					String state=data.get(i).get("state").toString();
					
					if (!state.isEmpty()||state.equals("0")) {
						if (state.equals("0")) {
							state="未发布";
						} else if (state.equals("1")) {
							state="已发布";
						} else if (state.equals("2")) {
							state="作业前完成";
						} else if (state.equals("3")) {
							state="作业中完成";
						} else if (state.equals("4")) {
							state="已完成";
						}
					}
					
					String content=data.get(i).get("content").toString();
					
					String taskRequir=" ";
					if(content.indexOf("\"", content.lastIndexOf("taskRequire")+14)!=-1){
						taskRequir+= content.substring(content.lastIndexOf("taskRequire")+14,content.indexOf("\"", content.lastIndexOf("taskRequire")+14));
					}
					
					String taskMsg=" ";
					if(content.indexOf("\"", content.lastIndexOf("taskMsg")+10)!=-1){
						taskMsg+= content.substring(content.indexOf("taskMsg")+10,content.indexOf("\"", content.indexOf("taskMsg")+10));
					}
					
					String berth=" ";
					if(content.indexOf("泊位")!=-1){
						berth+=content.substring(content.indexOf("泊位")-2,content.indexOf("泊位")+2);
					}
					
					String tubeId=" ";
					if(content.indexOf("\"", content.lastIndexOf("tubeId")+9)!=-1){
						tubeId+= content.substring(content.indexOf("tubeId")+9,content.indexOf("\"", content.indexOf("tubeId")+9));
					}
					
					String productId=" ";
					if(content.indexOf("\"", content.lastIndexOf("productId")+12)!=-1){
						productId+= content.substring(content.indexOf("productId")+12,content.indexOf("\"", content.indexOf("productId")+12));
					}
					
					String goodsNum=" ";
					if(content.indexOf("\"", content.lastIndexOf("goodsNum")+11)!=-1){
						goodsNum+= content.substring(content.indexOf("goodsNum")+11,content.indexOf("\"", content.indexOf("goodsNum")+11));
					}
					
					String pupmId=" ";
					if(content.indexOf("\"", content.lastIndexOf("pupmId")+9)!=-1){
						pupmId+= content.substring(content.indexOf("pupmId")+9,content.indexOf("\"", content.indexOf("pupmId")+9));
					}
					
					String tankId=" ";
					if(content.indexOf("\"", content.lastIndexOf("tankId")+9)!=-1){
						tankId+= content.substring(content.indexOf("tankId")+9,content.indexOf("\"", content.indexOf("tankId")+9));
					}
					
					
					HSSFRow rowfor = sheet.createRow((short) i+1);
					handleCell(sheet, rowfor, style, data.get(i).get("code").toString(), i+1, 0, i+1,0,0);
					
					if("0".equals((String)request.getParameter("Type"))){
						handleCell(sheet, rowfor, style, taskRequir, i+1, 1, i+1, 7, 1);
					}else if("1".equals((String)request.getParameter("Type"))){
								handleCell(sheet, rowfor, style, taskMsg, i+1, 1, i+1, 3, 1);
								handleCell(sheet, rowfor, style, tankId, i+1, 4, i+1, 5, 4);
								handleCell(sheet, rowfor, style, tubeId, i+1, 6, i+1, 7, 6);	
					}else if("2".equals((String)request.getParameter("Type"))
							||"3".equals((String)request.getParameter("Type"))){
						if(taskMsg.length()>3){
							if(taskMsg.substring(taskMsg.indexOf("】")==-1?1:taskMsg.indexOf("】")).indexOf("【")==-1){
                                handleCell(sheet, rowfor, style, "", i+1, 1, i+1, 2, 1);
								handleCell(sheet, rowfor, style, "", i+1, 3, i+1, 4, 3);
								handleCell(sheet, rowfor, style, taskMsg.substring(taskMsg.indexOf("【")+1,taskMsg.indexOf("】")), i+1, 5, i+1, 6, 5);
							}else{
							handleCell(sheet, rowfor, style, taskMsg.substring(taskMsg.indexOf("【")+1,taskMsg.indexOf("】")), i+1, 1, i+1, 2, 1);
								handleCell(sheet, rowfor, style, taskMsg.substring(taskMsg.indexOf("】")+2,taskMsg.lastIndexOf("【")-1), i+1, 3, i+1, 4, 3);
								handleCell(sheet, rowfor, style, taskMsg.substring(taskMsg.lastIndexOf("【")+1,taskMsg.lastIndexOf("】")), i+1, 5, i+1, 6, 5);
							}
						}else{
							handleCell(sheet, rowfor, style, "", i+1, 1, i+1, 2, 1);
							handleCell(sheet, rowfor, style, "", i+1, 3, i+1, 4, 3);
							handleCell(sheet, rowfor, style, "", i+1, 5, i+1, 6, 5);
						}
							handleCell(sheet, rowfor, style, berth, i+1, 7, i+1, 7, 7);
				       }else if("4".equals((String)request.getParameter("Type"))
				    		   ||"5".equals((String)request.getParameter("Type"))){
							
							handleCell(sheet, rowfor, style, taskRequir, i+1, 1, i+1, 4, 1);
							handleCell(sheet, rowfor, style, tubeId, i+1, 5, i+1, 5, 5);
							handleCell(sheet, rowfor, style, productId, i+1, 6, i+1, 7, 6);
						}else if("6".equals((String)request.getParameter("Type"))
								||"7".equals((String)request.getParameter("Type"))
								||"12".equals((String)request.getParameter("Type"))){
							
							handleCell(sheet, rowfor, style, taskMsg, i+1, 1, i+1, 3, 1);
							handleCell(sheet, rowfor, style, taskRequir, i+1, 4, i+1, 7, 4);
						}else if("8".equals((String)request.getParameter("Type"))){
							
							handleCell(sheet, rowfor, style, taskRequir, i+1, 1, i+1, 3, 1);
							handleCell(sheet, rowfor, style, tubeId, i+1, 4, i+1, 4, 4);
							handleCell(sheet, rowfor, style, productId, i+1, 5, i+1, 5, 5);
							handleCell(sheet, rowfor, style, goodsNum, i+1, 6, i+1, 6, 6);
							handleCell(sheet, rowfor, style, pupmId, i+1, 7, i+1, 7, 7);
						
						}else if("9".equals((String)request.getParameter("Type"))
								||"17".equals((String)request.getParameter("Type"))){
							
							handleCell(sheet, rowfor, style, taskMsg, i+1, 1, i+1, 7, 1);
								
						}else if("10".equals((String)request.getParameter("Type"))){
							
							handleCell(sheet, rowfor, style, taskMsg, i+1, 1, i+1, 3, 1);
							handleCell(sheet, rowfor, style, tankId, i+1, 4, i+1, 5, 4);
							handleCell(sheet, rowfor, style, productId, i+1, 6, i+1, 7, 6);
							
						}else if("13".equals((String)request.getParameter("Type"))
								||"14".equals((String)request.getParameter("Type"))
								||"15".equals((String)request.getParameter("Type"))
								||"16".equals((String)request.getParameter("Type"))){
							
							if(taskMsg.substring(taskMsg.indexOf("】")).indexOf("【")==-1){
                                handleCell(sheet, rowfor, style, "", i+1, 1, i+1, 1, 1);
								handleCell(sheet, rowfor, style, "", i+1, 2, i+1, 2, 2);
								handleCell(sheet, rowfor, style, taskMsg.substring(taskMsg.indexOf("【")+1,taskMsg.indexOf("】")), i+1, 3, i+1, 3, 3);
							}else{
								handleCell(sheet, rowfor, style, taskMsg.substring(taskMsg.indexOf("【")+1,taskMsg.indexOf("】")), i+1, 1, i+1, 1, 1);
								handleCell(sheet, rowfor, style, taskMsg.substring(taskMsg.indexOf("】")+2,taskMsg.lastIndexOf("【")-1), i+1, 2, i+1, 2, 2);
								handleCell(sheet, rowfor, style, taskMsg.substring(taskMsg.lastIndexOf("【")+1,taskMsg.lastIndexOf("】")), i+1, 3, i+1, 3, 3);
							}
							handleCell(sheet, rowfor, style, berth, i+1, 4, i+1, 4, 4);
							handleCell(sheet, rowfor, style, taskRequir, i+1, 5, i+1, 7, 5);
							
						}
					
					
					handleCell(sheet, rowfor, style, FormatUtils.getStringValue(data.get(i).get("createTime")), i+1, 8, i+1, 9, 8);
					handleCell(sheet, rowfor, style, FormatUtils.getStringValue(data.get(i).get("sureUserName")), i+1, 10, i+1, 10, 10);
					handleCell(sheet, rowfor, style, FormatUtils.getStringValue(data.get(i).get("strn")), i+1, 11, i+1, 11, 11);
					handleCell(sheet, rowfor, style, FormatUtils.getStringValue(data.get(i).get("endn")), i+1, 12, i+1, 12, 12);
					handleCell(sheet, rowfor, style, state, i+1, 13, i+1, 13, 13);
					
				}
		    
			
		}
		
		 if("15".equals((String)request.getParameter("type"))){
			NotifyDto notifyDto=new NotifyDto();
			notifyDto.setCode(request.getParameter("code"));
			notifyDto.setTypes(0+"");
			//notifyDto.setIsList(1);
		    List<Map<String,Object>> list=notifyDao.getNotifyList(notifyDto, 0,0);
		    
		    Map<String,Object>  map=list.get(0);
		    int state=Integer.valueOf(map.get("state").toString());

            String content1=" ";
            String content2=" ";
            String name1="";
            String name2="";
            String time1="";
            String time2="";
   
for (int i = 1; i < list.size(); i++) {
	if(list.get(i).get("contentType").toString().equals("1")){
		content1+=list.get(i).get("content").toString();
		if(list.get(i).get("state").toString().equals("2")){
			name1+=list.get(i).get("sureUserName").toString();
			time1+=list.get(i).get("sureTime").toString();
		}
	}else if(list.get(i).get("contentType").toString().equals("3")){
		content2+=list.get(i).get("content").toString();
		if(list.get(i).get("state").toString().equals("4")){
			name2+=list.get(i).get("sureUserName").toString();
			time2+=list.get(i).get("sureTime").toString();
		}
		
	}
}
		    
		    String svg1= map.get("firPFDSvg").toString();
		    String svg2=map.get("secPFDSvg").toString();
		    svg1 = svg1.substring(0,4)+" xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' "+svg1.substring(5) ;
		    svg2 = svg2.substring(0,4)+" xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' "+svg2.substring(5) ;
		    
		    HSSFRow rowtitle = sheet.createRow((short) 0); 
			HSSFRow row0 = sheet.createRow((short) 2); 

			handleCell(sheet, rowtitle, style, "配管作业通知单", 0, 0, 1,13);
            handleCell(sheet, row0, style,  "日期:"+map.get("createTime").toString().substring(0,11), 2, 0, 2, 4,0);
            handleCell(sheet, row0, style,  "JL2303-8", 2, 5, 2, 8,5);
			
            handleCell(sheet, row0, style,  "编号:"+request.getParameter("code").toString().substring(1), 2, 9, 2, 13,9);

		    HSSFRow row = sheet.createRow((short) 3);
			handleCell(sheet, row, style, request.getParameter("t1"), 3, 0, 3,13);
			HSSFRow row1 = sheet.createRow((short) 4);
			handleCell(sheet, row1, style, "", 4, 0, 13,13);
			handleSVGother(request, sheet, wb, svg1, 0, 4,880,160);
			
			HSSFRow row2 = sheet.createRow((short) 14);
			handleCell(sheet, row2, style, request.getParameter("t2"), 14, 0, 14,13);
			HSSFRow row3 = sheet.createRow((short) 15);
			handleCell(sheet, row3, style, "", 15, 0, 26,13);
			handleSVGother(request, sheet, wb, svg2, 0, 15,880,160);
			
			HSSFRow row4 = sheet.createRow((short) 27);
			handleCell(sheet, row4, style, request.getParameter("t3"), 27, 0, 27,13);
			HSSFRow row5 = sheet.createRow((short) 28);
			handleCell(sheet, row5, style,  request.getParameter("v3"), 28, 0, 37,13);
			
			HSSFRow row7 = sheet.createRow((short) 38);
			handleCell(sheet, row7, style, "调度:"+map.get("sureUserName").toString(), 38, 0, 38,13);
			
			HSSFRow row6 = sheet.createRow((short) 39);
			handleCell(sheet, row6, style, request.getParameter("t4"), 39, 0, 39,13);
			
			 HSSFRow row9 = sheet.createRow((short) 40);
			 handleCell(sheet, row9, style1,  
					  "1、正确佩戴个人防护用品；\n"
                     +"2、使用防爆工具\n"
                     +"3、接管前确认主管线泄压罐及管线阀门状态\n"
                     +"4、接好后听令泄压\n"
                     +"5、作业过程中做好物料的盛接工作\n"
                     +"6、配管结束后废料回收入库", 40, 0, 45, 13);
			
			HSSFRow row13 = sheet.createRow((short) 46);
			handleCell(sheet, row13, style, request.getParameter("t5"), 46, 0, 46,13);
			
			HSSFRow row14 = sheet.createRow((short) 47);
			handleCell(sheet, row14, style,  "危害及潜在事件：", 47, 0, 47, 4, 0);
			handleCell(sheet, row14, style,  "主要后果", 47, 5, 47, 8, 5);
			handleCell(sheet, row14, style,  "安全措施", 47, 9, 47, 13, 9);
			
			
			String[] value={"1、个人防护用品不适用或未佩戴：","人身伤害","正确佩戴个人防护用品",
			         "2、作业前管线阀门状态未确认：","接错管线，造成串料","及时与调度联系，加强核对",
			         "3、未使用防爆工具","产生火花，有燃烧爆炸隐患","按规定使用防爆工具",
			         "4、管线物料未吹扫净，放气时物料喷溅","人身伤害","放气时人员注意躲避，尽量吹净管线内物料",
			         "5、作业后管线阀门状态未确认：","管线未泄压，或造成串料","完成后及时与调度联系，加强核对",
			         "6、结束后废料未回收入库：","造成污染，或有火灾隐患","及时回收入库"
		            };
	
	for (int j = 0; j < 6; j++) {
		HSSFRow rowj = sheet.createRow((short)48+j);
		handleCell(sheet, rowj, style1,  value[0+j*3], 48+j, 0, 48+j, 4, 0);
		handleCell(sheet, rowj, style1,  value[1+j*3], 48+j, 5, 48+j, 8, 5);
		handleCell(sheet, rowj, style1,  value[2+j*3], 48+j, 9, 48+j, 13, 9);
	}
	if(state==4){		
			HSSFRow row21 = sheet.createRow((short) 54);
			handleCell(sheet, row21, style1, "六、作业过程的检查", 54, 0, 54,13);
			
			 sheet.setColumnWidth(0,700);
			
				HSSFRow row22 = sheet.createRow((short) 55);
				handleCell(sheet, row22, style1, "作业前检查", 55, 0, 62,0);
				handleCell(sheet, row22, style1, "1、个人防护用品是否正确佩戴", 55, 1, 55, 6, 1);
				handleCell(sheet, row22, style1,  content1.charAt(content1.lastIndexOf("b")+1)=='1'?"是":" ", 55, 7, 55, 13, 7);
				
				String[] value1={
						         "2、是否使用防爆工具","c",
						         "3、通讯系统是否通畅","d",
						         "4、消防器材是否到位","e",
						         "5、是否释放身体静电","f",
						         "6、确认工艺流程是否正确","g",
						         "7、确认相关阀门是否按要求开启、关闭","h",
						         "8、是否按调度命令吹尽软管中物料","i"
				                 };
				
				for (int x = 0; x <7; x++) {
					HSSFRow rowx = sheet.createRow((short) 56+x);
					handleCell(sheet, rowx, style1,  value1[x*2], 56+x, 1, 56+x, 6, 1);
					handleCell(sheet, rowx, style1,  content1.charAt(content1.lastIndexOf(value1[x*2+1])+1)=='1'?"是":" ", 56+x, 7, 56+x, 13, 7);
				}
			
				HSSFRow row24 = sheet.createRow((short) 63);
				handleCell(sheet, row24, style1,  "作业人员:"+name1, 63, 0, 63, 6, 0);
				handleCell(sheet, row24, style1,  "日期:"+time1, 63, 7, 63, 13, 7);
			
			
			HSSFRow row23 = sheet.createRow((short) 64);
			handleCell(sheet, row23, style1, "作业后检查", 64, 0, 69,0);
			handleCell(sheet, row23, style1, "1、垫圈是否正确放置", 64, 1, 64, 6, 1);
			handleCell(sheet, row23, style1,  content2.charAt(content2.lastIndexOf("q")+1)=='1'?"是":" ", 64, 7, 64, 13, 7);
			
			String[] value2={
			         "2、螺丝是否上全","q",
			         "3、是否无滴漏","r",
			         "4、现场是否整理干净","s",
			         "5、废料是否回收入库","t",
			         "6、确认相关阀门是否按要求开启、关闭","u"
	                 };
			
			for (int y = 0; y < 5; y++) {
				HSSFRow rowy = sheet.createRow((short) 65+y);
				handleCell(sheet, rowy, style1,  value2[y*2], 65+y, 1, 65+y, 6, 1);
				if(y==4){
					handleCell(sheet, rowy, style1,  content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='1'?"是":(content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='3'?"不适用":" "), 65+y, 7, 65+y, 13, 7);	
				}else{
				handleCell(sheet, rowy, style1,  content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='1'?"是":" ", 65+y, 7, 65+y, 13, 7);
				}
			}
			
			HSSFRow row46 = sheet.createRow((short) 70);
			handleCell(sheet, row46, style1,  "作业人员:"+name2.toString(), 70, 0, 70, 6, 0);
			handleCell(sheet, row46, style1,  "日期:"+time2, 70, 7, 70, 13, 7);
	}
	 }else if("16".equals((String)request.getParameter("type"))){
			
			NotifyDto notifyDto=new NotifyDto();
		
		    notifyDto.setCode(request.getParameter("code"));
		    notifyDto.setTypes(1+"");
		    List<Map<String,Object>> list=notifyDao.getNotifyList(notifyDto, 0, 0);
		    Map<String,Object>  map=list.get(0);
		    int state=Integer.valueOf(map.get("state").toString());
		    
		    String content1=" ";
		    String content2=" ";
		    String content3=" ";
            String name1="";
            String name2="";
            String name3="";
            String name4="";
            String time1="";
            String time2="";
            String time3="";

		   for (int i = 1; i < list.size(); i++) {
			if(list.get(i).get("contentType").toString().equals("1")){
				content1+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("2")){
					name1=list.get(i).get("sureUserName").toString();
					time1=list.get(i).get("sureTime").toString();
				}
			}else if(list.get(i).get("contentType").toString().equals("3")){
				content3+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("4")){
					name3=list.get(i).get("sureUserName").toString();
					time3=list.get(i).get("sureTime").toString();
				}
			}else if(list.get(i).get("contentType").toString().equals("2")){
				content2+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("3")){
					name2=list.get(i).get("sureUserName").toString();
					time2=list.get(i).get("sureTime").toString();
				}
				if(list.get(i).get("flag").toString().equals("1")){
					name4=list.get(i).get("sureUserName").toString();
				}
			}
		}
		    System.out.println(request.getParameter("content"));
		    JSONObject o = JSONObject.parseObject(request.getParameter("content"));
		    
		    HSSFRow rowtitle = sheet.createRow((short) 0); 
			HSSFRow row0 = sheet.createRow((short) 2); 

			handleCell(sheet, rowtitle, style, "苯加热作业通知单", 0, 0, 1,13);
            handleCell(sheet, row0, style,  "日期:"+map.get("createTime").toString().substring(0,11), 2, 0, 2, 4,0);
            handleCell(sheet, row0, style,  "JL2303", 2, 5, 2, 8,5);
            handleCell(sheet, row0, style,  "编号:"+request.getParameter("code").toString().substring(1), 2, 9, 2, 13,9);
            
			HSSFRow row = sheet.createRow((short) 3);  
			handleCell(sheet, row, style, request.getParameter("t1"), 3, 0, 3, 13) ;
			
			HSSFRow row2 = sheet.createRow((short) 4);  
			handleCell(sheet, row2, style, request.getParameter("v1"), 4, 0, 7, 13) ;
			
			HSSFRow row3 = sheet.createRow((short) 8);  
			handleCell(sheet, row3, style, request.getParameter("t2"), 8, 0, 8, 13) ;
			
			HSSFRow row4 = sheet.createRow((short) 9); 
			String str1="罐号："+o.getString("tankId")+" "+getFlagInfo(o.getString("tankCB"));
			handleCell(sheet, row4, style, str1, 9, 0, 9, 6,0) ;
			String str2="管号："+o.getString("tubeId")+" "+getFlagInfo(o.getString("tubeCB"));
			handleCell(sheet, row4, style, str2, 9, 7, 9, 13,7) ;
			
			HSSFRow row5 = sheet.createRow((short) 10);  
			String str3="设置温度："+o.getString("tankT")+"℃";
			handleCell(sheet, row5, style, str3, 10, 0, 10, 6,0) ;
			String str4=o.getString("ctrlNum")+"#号加热控制箱"+getFlagInfo(o.getString("tubeCBA"));
			handleCell(sheet, row5, style, str4, 10, 7, 10,13, 7) ;
			
			HSSFRow row6 = sheet.createRow((short) 11);  
			String str5="加热起始：储罐温度："+o.getString("tankStartT")+"℃  "+getFlagInfo(o.getString("tankStartTCB"));
			handleCell(sheet, row6, style, str5, 11,0, 11, 6,0);
			String str6="管线温度："+o.getString("tubeStartT")+"℃  "+getFlagInfo(o.getString("tubeStartTCB"));
			handleCell(sheet, row6, style,str6, 11,7, 11, 13, 7) ;

			HSSFRow row7 = sheet.createRow((short) 12);  
			String str7="加热时间："+o.getString("startTime")+"到 "+o.getString("endTime");
			handleCell(sheet, row7, style, str7, 12,0, 12, 6,0);
			String str8="共计"+o.getString("hourTime")+"小时";
			handleCell(sheet, row7, style,str8, 12,7, 12,13, 7) ;
			
			
			HSSFRow row10 = sheet.createRow((short) 14);
			handleCell(sheet, row10, style, "三、作业注意事项:", 14, 0, 14,13);

			 HSSFRow row9 = sheet.createRow((short) 15);
			 handleCell(sheet, row9, style1,  
					  "1、正确佩戴个人防护用品;\n"
                    +"2、加热时阀门应正确切换;\n"
                    +"3、作业时检查储罐油气回收装置是否正常，如有异常及时与调度联系;\n"
                    +"4、作业过程中加强检查，与调度保持联系;\n"
                    +"5、中控室加强对储罐温度的监控;\n"
                    +"6、加强管线温度检查", 15, 0, 20, 13);
			 
			    HSSFRow row13 = sheet.createRow((short) 21);
				handleCell(sheet, row13, style, "四、作业过程中的风险分析及相关措施: ", 21, 0, 21,13);
				
				HSSFRow row14 = sheet.createRow((short) 22);
				handleCell(sheet, row14, style,  "危害及潜在事件：", 22, 0, 22, 4, 0);
				handleCell(sheet, row14, style,  "主要后果", 22, 5, 22, 8, 5);
				handleCell(sheet, row14, style,  "安全措施", 22, 9, 22, 13, 9);
				
				
				String[] value={"1、个人防护用品不适用或未佩戴：","人身伤害","正确佩戴个人防护用品",
				         "2、使用非防爆工具，产生火花","火灾、爆炸","及时与调度联系，加强核对",
				         "3、人体静电接触可燃气体","火灾、爆炸","进入作业场所前释放人体静电",
				         "4、未按要求开启阀门","物料无法循环加热","按要求开启阀门",
				         "5、阀门泄漏","污染环境","使用不锈钢器具及时盛接、回收",
				         "6、储罐油气回收装置故障","储罐涨裂","作业中加强检查油气回收装置"
			            };
		
		for (int j = 0; j < 6; j++) {
			HSSFRow rowj = sheet.createRow((short)23+j);
			handleCell(sheet, rowj, style1,  value[0+j*3], 23+j, 0, 23+j, 4, 0);
			handleCell(sheet, rowj, style1,  value[1+j*3], 23+j, 5, 23+j, 8, 5);
			handleCell(sheet, rowj, style1,  value[2+j*3], 23+j, 9, 23+j, 13, 9);
		}
		 if(state==4){
		HSSFRow row21 = sheet.createRow((short) 29);
		handleCell(sheet, row21, style1, "六、作业过程的检查", 29, 0, 29,13);
		
		 sheet.setColumnWidth(0,700);
		
			HSSFRow row22 = sheet.createRow((short) 30);
			handleCell(sheet, row22, style1, "作业前检查", 30, 0, 36,0);
			handleCell(sheet, row22, style1, "1、个人防护用品是否正确佩戴", 30, 1, 30, 6, 1);
			handleCell(sheet, row22, style1,  content1.charAt(content1.lastIndexOf("a")+1)=='1'?"是":" ", 30, 7, 30, 13, 7);
			
			String[] value1={
					         "2、通讯系统是否通畅","b",
					         "3、消防器材是否到位","c",
					         "4、是否释放身体静电","d",
					         "5、确认工艺流程是否正确","e",
					         "6、加热装置设定温度是否正常","f",
					         "7、确认相关阀门是否按要求开启、关闭","g"
			                 };
			
			for (int x = 0; x <6; x++) {
				HSSFRow rowx = sheet.createRow((short) 31+x);
				handleCell(sheet, rowx, style1,  value1[x*2], 31+x, 1, 31+x, 6, 1);
				handleCell(sheet, rowx, style1,  content1.charAt(content1.lastIndexOf(value1[x*2+1])+1)=='1'?"是":" ", 31+x, 7, 31+x, 13, 7);
			}
		
			HSSFRow row24 = sheet.createRow((short) 37);
			handleCell(sheet, row24, style1,  "作业人员:"+name1, 37, 0, 37, 6, 0);
			handleCell(sheet, row24, style1,  "日期:"+time1, 37, 7, 37, 13, 7);
		
		    HSSFRow row23 = sheet.createRow((short) 38);
			handleCell(sheet, row23, style1, "作业中检查", 38, 0, 43,0);
			handleCell(sheet, row23, style1, "1、储罐油气回收装置是否正常", 38, 1, 38, 6, 1);
			handleCell(sheet, row23, style1,  content2.charAt(content2.lastIndexOf("h")+1)=='1'?"是":(content2.charAt(content2.lastIndexOf("h")+1)=='3'?"不适用":" "), 38, 7, 38, 13, 7);
			
			String[] value2={
			         "2、储罐温度是否正常上升","i",
			         "3、现场管线温度是否正常上升","j",
			         "4、加热装置是否运行正常","k",
			         "5、循环泵是否运行正常","l",
			         "6、是否有物料滴漏","m"
	                 };
			
			for (int y = 0; y < 5; y++) {
				HSSFRow rowy = sheet.createRow((short) 39+y);
				handleCell(sheet, rowy, style1,  value2[y*2], 39+y, 1, 39+y, 6, 1);
				if(y==1||y==2){
					if(content2.lastIndexOf(value2[y*2+1])!=-1){
					handleCell(sheet, rowy, style1,  content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='1'?"是":" ", 39+y, 7, 39+y, 13, 7);
					}else{
						handleCell(sheet, rowy, style1,  "", 39+y, 7, 39+y, 13, 12);	
						}
					}else if(y==0){
					if(content2.lastIndexOf(value2[y*2+1])!=-1){
				    handleCell(sheet, rowy, style1,  content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='1'?"是":(content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='3'?"不适用":" "), 39+y, 7, 39+y, 11, 7);
				    handleCell(sheet, rowy, style1,  name4, 39+y, 12, 39+y, 13, 12);
					}else{
					handleCell(sheet, rowy, style1,  "", 39+y, 7, 39+y, 13, 12);	
					}
				}else{
					if(content2.lastIndexOf(value2[y*2+1])!=-1){
					handleCell(sheet, rowy, style1,  content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='1'?"是":(content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='3'?"不适用":" "), 39+y, 7, 39+y, 13, 7);
					}else{
						handleCell(sheet, rowy, style1,  "", 39+y, 7, 39+y, 13, 12);	
						}
					}
			}
		    
	        HSSFRow row26 = sheet.createRow((short) 44);
			handleCell(sheet, row26, style1,  "作业人员:"+name2, 44, 0, 44, 6, 0);
			handleCell(sheet, row26, style1,  "日期:"+time2, 44, 7, 44, 13, 7);
		 
		
			HSSFRow row27 = sheet.createRow((short) 45);
			handleCell(sheet, row27, style1, "作业中后查", 45, 0, 48,0);
			handleCell(sheet, row27, style1,  "阀门是否复位", 45, 1, 45, 6, 1);
			handleCell(sheet, row27, style1,  content3.charAt(content3.lastIndexOf("n")+1)=='1'?"是":" ", 45, 7, 45, 13, 7);

			String[] value3={"2、加热装置是否关闭","o",
						     "3、循环泵是否停止运行","p",
						     "4、现场是否清理干净","q"
				                 };
		   for (int z = 0; z < 3; z++) {
			   HSSFRow rowz = sheet.createRow((short) 46+z);
			   handleCell(sheet, rowz, style1,  value3[z*2], 46+z, 1, 46+z, 6, 1);
			   if(z==1){
				   if(content3.lastIndexOf(value3[z*2+1])!=-1){
				   handleCell(sheet, rowz, style1,  content3.charAt(content3.lastIndexOf(value3[z*2+1])+1)=='1'?"是":(content3.charAt(content3.lastIndexOf(value3[z*2+1])+1)=='3'?"不适用":" "), 46+z, 7, 46+z, 13, 7);
				   }else{
						handleCell(sheet, rowz, style1,  "", 46+z, 7, 46+z, 13, 12);	
						}
				   }else{
					   if(content3.lastIndexOf(value3[z*2+1])!=-1){
				         handleCell(sheet, rowz, style1,  content3.charAt(content3.lastIndexOf(value3[z*2+1])+1)=='1'?"是":" ", 46+z, 7, 46+z, 13, 7);
					   }else{
							handleCell(sheet, rowz, style1,  "", 46+z, 7, 46+z, 13, 12);	
							}
					   }
		}
		   
		    HSSFRow row28 = sheet.createRow((short) 49);
			handleCell(sheet, row28, style1,  "作业人员:"+name3, 49, 0, 49, 6, 0);
			handleCell(sheet, row28, style1,  "日期:"+time3, 49, 7, 49, 13, 7);
		 }	
		
		}else if("17".equals((String)request.getParameter("type"))||"18".equals((String)request.getParameter("type"))){
			
			HSSFRow rowtitle = sheet.createRow((short) 0); 
			HSSFRow rowt0 = sheet.createRow((short) 2); 
			HSSFRow row = sheet.createRow((short) 3);      
			HSSFRow row2 = sheet.createRow((short) 4);      
			HSSFRow row3 = sheet.createRow((short) 5);      
			HSSFRow row4 = sheet.createRow((short) 15);      
			HSSFRow row5 = sheet.createRow((short) 16);  
			HSSFRow row6 = sheet.createRow((short) 21); 
			HSSFRow row7 = sheet.createRow((short) 22); 
			
			NotifyDto notifyDto=new NotifyDto();
			notifyDto.setCode(request.getParameter("code"));
			
		
			if("17".equals((String)request.getParameter("type"))){
				notifyDto.setTypes(4+"");
				handleCell(sheet, rowtitle, style,  "管线清洗作业通知单（码头）", 0, 0, 1, 13);
			}else{
				notifyDto.setTypes(5+"");
				handleCell(sheet, rowtitle, style,  "管线清洗作业通知单（库区）", 0, 0, 1, 13);
			}

		    List<Map<String,Object>> list=notifyDao.getNotifyList(notifyDto, 0, 0);
		    Map<String,Object> map=list.get(0);
		    
		    String svg1= map.get("firPFDSvg").toString();
		    int state=Integer.valueOf(map.get("state").toString());
		    svg1 = svg1.substring(0,4)+" xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' "+svg1.substring(5) ;
		    
		    String content1=" ";
		    String content2=" ";
		    String content3=" ";
            String name1="";
            String name2="";
            String name3="";
            String time1="";
            String time2="";
            String time3="";

		   for (int i = 1; i < list.size(); i++) {
			if(list.get(i).get("contentType").toString().equals("1")){
				content1+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("2")){
					name1=list.get(i).get("sureUserName").toString();
					time1=list.get(i).get("sureTime").toString();
				}
			}else if(list.get(i).get("contentType").toString().equals("3")){
				content3+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("4")){
					name3=list.get(i).get("sureUserName").toString();
					time3=list.get(i).get("sureTime").toString();
				}
			}else if(list.get(i).get("contentType").toString().equals("2")){
				content2+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("3")){
					name2=list.get(i).get("sureUserName").toString();
					time2=list.get(i).get("sureTime").toString();
				}
			}
		}
			handleCell(sheet, rowt0, style1,  "日期:"+map.get("createTime").toString().substring(0,11), 2, 0, 2, 4,0);
	        handleCell(sheet, rowt0, style1,  "JL2303-6", 2, 5, 2, 8,5);
	        handleCell(sheet, rowt0, style1,  "编号:"+request.getParameter("code").toString().substring(1), 2, 9, 2, 13,9);

			
			handleCell(sheet, row, style, "管线号："+request.getParameter("tubeName"), 3, 0, 3, 6,0) ;
			handleCell(sheet, row, style, "物料名称："+request.getParameter("productName"), 3, 7, 3, 13,7) ;
			
			handleCell(sheet, row2, style, request.getParameter("t1"), 4, 0, 4, 13) ;
			handleCell(sheet, row3, style, "", 5, 0, 14, 13) ;
			handleSVG(request, sheet, wb, svg1, 0, 5);
			
			handleCell(sheet, row4, style, request.getParameter("t2"), 15, 0, 15,13);
			handleCell(sheet, row5, style, request.getParameter("v2"), 16, 0, 19,13);
			
			HSSFRow row01 = sheet.createRow((short) 20);
			handleCell(sheet, row01, style, "调度:"+map.get("sureUserName").toString(), 20, 0, 20,13);
			
			handleCell(sheet, row6, style1,  "三、作业注意事项：", 21, 0, 21,13);
			
			handleCell(sheet, row7, style1,  "1. 清洗管线时作业人员要佩戴防护眼镜，使用乳胶手套。\n"
                                            +"2. 在扫线时需佩戴护耳罩，防止放气时的噪声损伤耳朵。\n"
                                            +"3. 工艺管线在放气时人员要站在上风向。\n"
                                            +"4. 在低位排污口放料时需用软管连接后放入铁桶内，且在放料时要加强关注防止物料溢出桶外。\n"
                                            +"5. 清洗产生的废水及时收集后排入污水处理站，严禁排入江内；\n"
                                            +"6、作业过程与调度保持联系，及时汇报现场情况。", 22, 0, 27,13);
			
			HSSFRow row8 = sheet.createRow((short) 28);
			handleCell(sheet, row8, style1, "四、作业中的风险分析：", 28, 0, 28,13);
			
			HSSFRow row9 = sheet.createRow((short) 29);
			handleCell(sheet, row9, style,  "危害及潜在事件：", 29, 0, 29, 4, 0);
			handleCell(sheet, row9, style,  "主要后果", 29, 5, 29, 8, 5);
			handleCell(sheet, row9, style,  "安全措施", 29, 9, 29, 13, 9);
			
			String[] value={"1、未按规定佩戴劳动防护用品","人身伤害","正确佩戴个人防护用品",
			         "2、氮气压力低","扫线球卡堵在管线内","作业前准备好气源",
			         "3、吹扫口接口泄漏","氮气泄漏、物料飞溅","作业前做好扫线管的连接",
			         "4、阀门丝杆处物料泄漏","污染环境","做好检查、及时封堵",
			         "5、管线腐蚀造成物料泄漏","污染环境","做好管线沿线的巡回检查工作",
			         "6、放错扫线球","卡球或球不走","严格执行调度指令，调度做好确认工作",
			         "7、低位放料","污染环境","加强关注防止物料溢出桶外",
			         "8、放气","窒息、噪声","人员要站在上风向、佩戴好护耳罩",
				            };
			
			for (int j = 0; j < 8; j++) {
				HSSFRow rowj = sheet.createRow((short) 30+j);
				handleCell(sheet, rowj, style1,  value[0+j*3], 30+j, 0, 30+j, 4, 0);
				handleCell(sheet, rowj, style1,  value[1+j*3], 30+j, 5, 30+j, 8, 5);
				handleCell(sheet, rowj, style1,  value[2+j*3], 30+j, 9, 30+j, 13, 9);
			}
			if(state==4){
			HSSFRow row18 = sheet.createRow((short) 38);
			handleCell(sheet, row18, style1, "五、作业过程检查：", 38, 0, 38,13);
		
			sheet.setColumnWidth(0,700);
			
			HSSFRow row19 = sheet.createRow((short) 39);
			handleCell(sheet, row19, style1, "作业前检查", 39, 0, 45,0);
			handleCell(sheet, row19, style1,  "1、个人防护用品是否正确佩戴", 39, 1, 39, 6, 1);
			handleCell(sheet, row19, style1, content1.charAt(content1.lastIndexOf("a")+1)=='1'?"是":" ", 39, 7, 39, 13, 7);
			
			String[] value1={
			         "2、通讯系统是否正常","b",
			         "3、管线、阀门是否正常","c",
			         "4、低位排污口盛接铁桶是否到位","d",
			         "5、软管与移动槽车是否已连接并固定","e",
			         "6、扫线球是否放置正确","f",
			         "7、低位排污口是否闷堵","g"
	                 };
			
			for (int x = 0; x <6; x++) {
				HSSFRow rowx = sheet.createRow((short) 40+x);
				handleCell(sheet, rowx, style1,  value1[x*2], 40+x, 1, 40+x, 6, 1);
				if(x>1){
					handleCell(sheet, rowx, style1,  content1.charAt(content1.lastIndexOf(value1[x*2+1])+1)=='1'?"是":(content1.charAt(content1.lastIndexOf(value1[x*2+1])+1)=='3'?"不适用":" "), 40+x, 7, 40+x, 13, 7);
				}else{
					handleCell(sheet, rowx, style1,  content1.charAt(content1.lastIndexOf(value1[x*2+1])+1)=='1'?"是":" ", 40+x, 7, 40+x, 13, 7);
				}
			}
			
			    HSSFRow row46 = sheet.createRow((short) 46);
				handleCell(sheet, row46, style1,  "作业人员:"+name1, 46, 0, 46, 6, 0);
				handleCell(sheet, row46, style1,  "日期:"+time1, 46, 7, 46, 13, 7);
			
			if("17".equals((String)request.getParameter("type"))){
				
				
				HSSFRow row26 = sheet.createRow((short) 47);
				handleCell(sheet, row26, style1, "作业中检查", 47, 0, 51,0);
				
				handleCell(sheet, row26, style1,  "1、扫线球是否正常", 47, 1, 47, 6, 1);
				handleCell(sheet, row26, style1, content2.charAt(content2.lastIndexOf("h")+1)=='1'?"是":(content2.charAt(content2.lastIndexOf("h")+1)=='3'?"不适用":" "), 47, 7, 47, 13, 7);
				

				String[] value2={"2、压力是否正常","i",
				         "3、管线、阀门是否无跑冒滴漏","j",
				         "4、管线低位物料是否已放空并回收","k",
				         "5、管线低位污水是否已放空并回收","l"
		                 };
				
				for (int y = 0; y < 4; y++) {
					HSSFRow rowx = sheet.createRow((short) 48+y);
					handleCell(sheet, rowx, style1,  value2[y*2], 48+y, 1, 48+y, 6, 1);
					if(y==1){
						handleCell(sheet, rowx, style1,  content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='1'?"是":" ", 48+y, 7, 48+y, 13, 7);
					}else{
						handleCell(sheet, rowx, style1,  content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='1'?"是":(content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='3'?"不适用":" "), 48+y, 7, 48+y, 13, 7);
					}
				}
				
				HSSFRow row22 = sheet.createRow((short) 52);
				handleCell(sheet, row22, style1,  "作业人员:"+name2, 52, 0, 52, 6, 0);
				handleCell(sheet, row22, style1,  "日期:"+time2, 52, 7, 52, 13, 7);
				
				HSSFRow row31 = sheet.createRow((short) 53);
				handleCell(sheet, row31, style1, "作业后检查", 53, 0, 60,0);
				
				handleCell(sheet, row31, style1,  "1、管线低位是否已干燥无水", 53, 1, 53, 6, 1);
				handleCell(sheet, row31, style1, content3.charAt(content3.lastIndexOf("m")+1)=='1'?"是":(content2.charAt(content3.lastIndexOf("m")+1)=='3'?"不适用":" "), 53, 7, 53, 13, 7);
				
				String[] value3={"2、管线阀门是否关闭 ","n",
						             "3、吹扫接口是否封堵","o",
							         "4、管线低位是否已封堵","p",
							         "5、槽车内污水是否回收","q",
							         "6、物料是否吊装入库","r",
							         "7、氮气阀门是否关闭","s",
							         "8、现场是否整理干净","t"
					                 };
			   for (int z = 0; z < 7; z++) {
				   HSSFRow rowz = sheet.createRow((short) 54+z);
				   handleCell(sheet, rowz, style1,  value3[z*2], 54+z, 1, 54+z, 6, 1);
				   if(z==2){
					   handleCell(sheet, rowz, style1,  content3.charAt(content3.lastIndexOf(value3[z*2+1])+1)=='1'?"是":(content3.charAt(content3.lastIndexOf(value3[z*2+1])+1)=='3'?"不适用":" "), 54+z, 7, 54+z, 13, 7);
				   }else{
					   handleCell(sheet, rowz, style1,  content3.charAt(content3.lastIndexOf(value3[z*2+1])+1)=='1'?"是":" ", 54+z, 7, 54+z, 13, 7); 
				   }
			 }
				
			    HSSFRow row39 = sheet.createRow((short) 61);
				handleCell(sheet, row39, style1,  "作业人员:"+name3, 61, 0, 61, 6, 0);
				handleCell(sheet, row39, style1,  "日期:"+time3, 61, 7, 61, 13, 7);
				}
				
				
			}else{
				if(state==4){
				
				HSSFRow row26 = sheet.createRow((short) 47);
				handleCell(sheet, row26, style1, "作业中检查", 47, 0, 51,0);
				
				handleCell(sheet, row26, style1,  "1、管线出水是否正常", 47, 1, 47, 6, 1);
				handleCell(sheet, row26, style1, content2.charAt(content2.lastIndexOf("h")+1)=='1'?"是":" ", 47, 7, 47, 13, 7);
				

				String[] value2={"2、压力是否正常","i",
				         "3、扫线球是否完整","j",
				         "4、管线、阀门是否有跑冒滴漏","k",
				         "5、管线低位物料是否已放空并回收","l",
				         "6、管线低位污水是否已放空并回收","v"
		                 };
				
				for (int y = 0; y < 5; y++) {
					HSSFRow rowx = sheet.createRow((short) 48+y);
					handleCell(sheet, rowx, style1,  value2[y*2], 48+y, 1, 48+y, 6, 1);
					handleCell(sheet, rowx, style1,  content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='1'?"是":(content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='3'?"不适用":" "), 48+y, 7, 48+y, 13, 7);
				}
				
				HSSFRow row22 = sheet.createRow((short) 53);
				handleCell(sheet, row22, style1,  "作业人员:"+name2, 53, 0, 53, 6, 0);
				handleCell(sheet, row22, style1,  "日期:"+time2, 53, 7, 53, 13, 7);
					
				HSSFRow row31 = sheet.createRow((short) 54);
				handleCell(sheet, row31, style1, "作业后检查", 54, 0, 62,0);
				
				handleCell(sheet, row31, style1,  "1、管线低位是否已干燥无水", 54, 1, 54, 6, 1);
				handleCell(sheet, row31, style1, content3.charAt(content3.lastIndexOf("m")+1)=='1'?"是":(content2.charAt(content3.lastIndexOf("m")+1)=='3'?"不适用":" "), 54, 7, 54, 13, 7);
				
				String[] value3={"2、管线阀门是否关闭 ","n",
			             "3、吹扫接口是否封堵","o",
				         "4、管线低位是否已封堵","p",
				         "5、槽车内污水是否回收","q",
				         "6、物料是否吊装入库","r",
				         "7、氮气阀门是否关闭","s",
				         "8、现场是否整理干净","t",
				         "9、记录是否完善","u"
		                 };
 for (int z = 0; z < 8; z++) {
	   HSSFRow rowz = sheet.createRow((short) 55+z);
	   handleCell(sheet, rowz, style1,  value3[z*2], 55+z, 1, 55+z, 6, 1);
	   if(z==2){
		   handleCell(sheet, rowz, style1,  content3.charAt(content3.lastIndexOf(value3[z*2+1])+1)=='1'?"是":(content3.charAt(content3.lastIndexOf(value3[z*2+1])+1)=='3'?"不适用":" "), 55+z, 7, 55+z, 13, 7);
	   }else{
		   handleCell(sheet, rowz, style1,  content3.charAt(content3.lastIndexOf(value3[z*2+1])+1)=='1'?"是":" ", 54+z, 7, 54+z, 13, 7); 
	   }
}
	HSSFRow row39 = sheet.createRow((short) 63);
	handleCell(sheet, row39, style1,  "作业人员:"+name3, 63, 0, 63, 6, 0);
	handleCell(sheet, row39, style1,  "日期:"+time3, 63, 7, 63, 13, 7);

				}
			}
		}else if("19".equals((String)request.getParameter("type"))||"20".equals((String)request.getParameter("type"))){
			NotifyDto notifyDto=new NotifyDto();
			notifyDto.setCode(request.getParameter("code"));
			HSSFRow rowtitle = sheet.createRow((short) 0); 
			HSSFRow row0 = sheet.createRow((short) 2); 
			
			if("19".equals((String)request.getParameter("type"))){
				notifyDto.setTypes(6+"");
			}else{
				notifyDto.setTypes(7+"");
			}
			//notifyDto.setIsList(1);
		   List<Map<String,Object>> list=notifyDao.getNotifyList(notifyDto, 0, 0);
		   Map<String,Object> map=list.get(0);
           int state=Integer.valueOf(map.get("state").toString());
		   
		   String content1=" ";
		   String content2=" ";
		   String content3=" ";
           String name1="";
           String name2="";
           String name3="";
           String name4="";
           String time1="";
           String time2="";
           String time3="";

		   for (int i = 1; i < list.size(); i++) {
			if(list.get(i).get("contentType").toString().equals("1")){
				content1+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("2")){
					name1=list.get(i).get("sureUserName").toString();
					time1=list.get(i).get("sureTime").toString();
				}
			}else if(list.get(i).get("contentType").toString().equals("3")){
				content3+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("4")){
					name3=list.get(i).get("sureUserName").toString();
					time3=list.get(i).get("sureTime").toString();
				}
			}else if(list.get(i).get("contentType").toString().equals("2")){
				content2+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("3")){
					name2=list.get(i).get("sureUserName").toString();
					time2=list.get(i).get("sureTime").toString();
				}
				if(list.get(i).get("flag").toString().equals("1")){
					name4=list.get(i).get("sureUserName").toString();
				}
			}
		}
		   
		    String svg1= map.get("firPFDSvg").toString();
		    String svg2=map.get("secPFDSvg").toString();
		    svg1 = svg1.substring(0,4)+" xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' "+svg1.substring(5) ;
		    svg2 = svg2.substring(0,4)+" xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' "+svg2.substring(5) ;
		    
		    if("19".equals((String)request.getParameter("type"))){
				handleCell(sheet, rowtitle, style, "扫线作业通知单（码头）", 0, 0, 1,13);
			}else{
				handleCell(sheet, rowtitle, style, "扫线作业通知单（库区）", 0, 0, 1,13);
			}
		 
			handleCell(sheet, row0, style,  "日期:"+map.get("createTime").toString().substring(0,11), 2, 0, 2, 4,0);
	        handleCell(sheet, row0, style,  "JL2303-10", 2, 5, 2, 8,5);
	        handleCell(sheet, row0, style,  "编号:"+request.getParameter("code").toString().substring(1), 2, 9, 2, 13,9);

			
			HSSFRow row = sheet.createRow((short) 3);
			handleCell(sheet, row, style, request.getParameter("t1"), 3, 0, 3,13);
			HSSFRow row1 = sheet.createRow((short) 4);
			handleCell(sheet, row1, style, request.getParameter("v1"), 4, 0, 13,13);
			
			HSSFRow row2 = sheet.createRow((short) 14);
			handleCell(sheet, row2, style, request.getParameter("t2"), 14, 0, 14,13);
			HSSFRow row3 = sheet.createRow((short) 15);
			handleCell(sheet, row3, style, "", 15, 0, 26,13);
			handleSVG(request, sheet, wb, svg1, 0, 15);
			
			HSSFRow row4 = sheet.createRow((short) 27);
			handleCell(sheet, row4, style, request.getParameter("t3"), 27, 0, 27,13);
			HSSFRow row5 = sheet.createRow((short) 28);
			handleCell(sheet, row5, style, "", 28, 0, 37,13);
			handleSVG(request, sheet, wb, svg2, 0, 28);
			
			HSSFRow row6 = sheet.createRow((short) 38);
			handleCell(sheet, row6, style, request.getParameter("t4"), 38, 0, 38,13);
			HSSFRow row7 = sheet.createRow((short) 39);
			handleCell(sheet, row7, style, request.getParameter("v4"), 39, 0, 47,13);
			

            HSSFRow row01 = sheet.createRow((short) 48);
            handleCell(sheet, row01, style, "调度:"+map.get("sureUserName").toString(), 48, 0, 48,13);
			
			
			HSSFRow row8 = sheet.createRow((short) 49);
			handleCell(sheet, row8, style1,  "五、作业注意事项：", 49, 0, 49,13);
			 if("19".equals((String)request.getParameter("type"))){
				 HSSFRow row9 = sheet.createRow((short) 50);
				 handleCell(sheet, row9, style1,  
						  "1、正确佩戴个人防护用品；\n"
                         +"2、开发球筒时要用集油盘盛接；\n"
                         +"3、球应放置在发球筒内；\n"
                         +"4、球放置时应头部向前；\n"
                         +"5、氮气压力应达到要求才可进行扫线作业；\n"
                         +"6、球发出后要及时与调度联系，并关注管线、氮气压力情况；\n"
                         +"7、结束后管线内余压应在码头前沿通过吹扫口进行释放；\n"
                         +"8、作业过程与调度保持联系，及时汇报现场情况；", 50, 0, 57,13);
				 
				    HSSFRow row10 = sheet.createRow((short) 58);
					handleCell(sheet, row10, style1, "六、作业过程中的风险分析及相关措施：", 58, 0, 58 ,13);
					
					HSSFRow row11 = sheet.createRow((short) 59);
					handleCell(sheet, row11, style,  "危害及潜在事件：", 59, 0, 59, 4, 0);
					handleCell(sheet, row11, style,  "主要后果", 59, 5, 59, 8, 5);
					handleCell(sheet, row11, style,  "安全措施", 59, 9, 59, 13, 9);
					
					String[] value={"1、未按规定佩戴劳动防护用品","人身伤害","正确佩戴个人防护用品",
					         "2、使用非防爆工具，产生火花","火灾、爆炸","使用防爆工具",
					         "3、人体静电接触可燃气体","火灾、爆炸","进入作业场所前释放人体静电",
					         "4、扫线管线错误","损坏设备，影响物料品质","根据作业通知单认真检查管线状态，与调度保持联系、确认。",
					         "5、阀门泄漏","污染环境","使用不锈钢器具及时盛接、回收",
					         "6、氮气管线拆除时未先泄压","人身伤害","严格按操作规程执行"
						           };
					
					for (int j = 0; j < 6; j++) {
						HSSFRow rowj = sheet.createRow((short) 60+j);
						handleCell(sheet, rowj, style1,  value[0+j*3], 60+j, 0, 60+j, 4, 0);
						handleCell(sheet, rowj, style1,  value[1+j*3], 60+j, 5, 60+j, 8, 5);
						handleCell(sheet, rowj, style1,  value[2+j*3], 60+j, 9, 60+j, 13, 9);
					}
					
					if(state==4){
					
					sheet.setColumnWidth(0,700);
					
					
					HSSFRow row18 = sheet.createRow((short) 66);
					handleCell(sheet, row18, style1, "作业前检查", 66, 0, 77,0);
					
					handleCell(sheet, row18, style1,  "1、个人防护用品是否正确佩戴", 66, 1, 66, 6, 1);
					handleCell(sheet, row18, style1, content1.charAt(content1.lastIndexOf("a")+1)=='1'?"是":" ", 66, 7, 66, 13, 7);
					
					String[] value1={
					         "2、通讯系统是否通畅","b",
					         "3、消防器材是否到位","c",
					         "4、是否释放身体静电","d",
					         "5、确认工艺流程是否正确","e",
					         "6、确认相关阀门是否按要求开启、关闭","f",
					         "7、扫线球的使用是否正确","g",
					         "8、扫线球的放置是否正确","h",
					         "9、压力表是否正常","i",
					         "10、收发球指示器动作是否正确","j",
					         "11、收球筒放球后是否盲板","k",
					         "12、氮气压力是否符合要求","l"
					         };
			
			for (int x = 0; x <11; x++) {
				HSSFRow rowx = sheet.createRow((short) 67+x);
				handleCell(sheet, rowx, style1,  value1[x*2], 67+x, 1, 67+x, 6, 1);
				handleCell(sheet, rowx, style1,  content1.charAt(content1.lastIndexOf(value1[x*2+1])+1)=='1'?"是":" ", 67+x, 7, 67+x, 13, 7);
			}
			
			HSSFRow row46 = sheet.createRow((short) 78);
			handleCell(sheet, row46, style1,  "作业人员:"+name1, 78, 0, 78, 6, 0);
			handleCell(sheet, row46, style1,  "日期:"+time1, 78, 7, 78, 13, 7);
					
					
					HSSFRow row30 = sheet.createRow((short) 79);
					handleCell(sheet, row30, style1, "作业中检查", 79, 0, 83,0);
					handleCell(sheet, row30, style1,  "1、扫线球运行是否正常", 79, 1, 79, 6, 1);
					handleCell(sheet, row30, style1,  content2.charAt(content2.lastIndexOf("m")+1)=='1'?"是":" ", 79, 7, 79, 13, 7);
					
					String[] value2={"2、氮气压力是否正常","n",
					         "3、压力表变化是否正常","o",
					         "4、是否有物料滴漏","p",
					         "5、中控室SCADA系统液位是否正常上升","q"
			                 };
					
					for (int y = 0; y < 4; y++) {
						HSSFRow rowx = sheet.createRow((short) 80 +y);
						handleCell(sheet, rowx, style1,  value2[y*2], 80 +y, 1, 80 +y, 6, 1);
						if(y==3){
                            if(content2.lastIndexOf(value2[y*2+1])!=-1){
							handleCell(sheet, rowx, style1,  content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='1'?"是":(content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='3'?"不适用":" "), 80+y, 7, 80+y, 11, 7);
							handleCell(sheet, rowx, style1,  name4, 80+y, 12, 80+y, 13, 12);
                            }else{
                            	handleCell(sheet, rowx, style1,  "", 80+y, 7, 80+y, 13, 12);	
                            }
						}else{
							handleCell(sheet, rowx, style1,  content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='1'?"是":" ", 80+y, 7, 80+y, 13, 7);
						}
					}
					
					HSSFRow row45 = sheet.createRow((short) 84);
					handleCell(sheet, row45, style1,  "作业人员:"+name2, 84, 0, 84, 6, 0);
					handleCell(sheet, row45, style1,  "日期:"+time2, 84, 7, 84, 13, 7);
					
					HSSFRow row36 = sheet.createRow((short) 85);
					handleCell(sheet, row36, style1, "作业后检查", 85, 0, 88,0);
					handleCell(sheet, row36, style1,  "1、氮气阀门是否关闭", 85, 1, 85, 6, 1);
					handleCell(sheet, row36, style1,  content3.charAt(content3.lastIndexOf("r")+1)=='1'?"是":" ", 85, 7, 85, 13, 7);
					
					String[] value3={
					         "2、管线是否泄压","s",
					         "3、现场是否清理干净","t",
					         "4、记录是否完善","u"
			                 };
	   for (int z = 0; z < 3; z++) {
		   HSSFRow rowz = sheet.createRow((short) 86+z);
		   handleCell(sheet, rowz, style1,  value3[z*2], 86+z, 1, 86+z, 6, 1);
		   handleCell(sheet, rowz, style1,  content3.charAt(content3.lastIndexOf(value3[z*2+1])+1)=='1'?"是":" ", 86+z, 7, 86+z, 13, 7);
	 }
					 
					
				    HSSFRow row35 = sheet.createRow((short) 89);
				    handleCell(sheet, row35, style1,  "作业人员:"+name3, 89, 0, 89, 6, 0);
					handleCell(sheet, row35, style1,  "日期:"+time3, 89, 7, 89, 13, 7);
					}		
						
				}else{
					 HSSFRow row9 = sheet.createRow((short) 50);
					 handleCell(sheet, row9, style1,  
							  "1、正确佩戴个人防护用品；\n"
	                         +"2、扫线时储罐阀门应正确切换；\n"
	                         +"3、作业时检查储罐呼吸阀是否正常，如有异常及时与调度联系；\n"
	                         +"4、中控室加强对储罐液位的监控；\n"
	                         +"5、收球桶阀门应处于正确位置；\n"
	                         +"6、扫线时需打开储罐测量孔；\n"
	                         +"7、扫线后管线内余压不得卸入罐内；\n"
	                         +"8、作业过程中加强检查，与调度保持联系。", 50, 0, 57,13);
					 
					    HSSFRow row10 = sheet.createRow((short) 58);
						handleCell(sheet, row10, style1, "六、作业过程中的风险分析及相关措施：", 58, 0, 58	,13);
							
						HSSFRow row11 = sheet.createRow((short) 59);
						handleCell(sheet, row11, style,  "危害及潜在事件：", 59, 0, 59, 4, 0);
						handleCell(sheet, row11, style,  "主要后果", 59, 5, 59, 8, 5);
						handleCell(sheet, row11, style,  "安全措施", 59, 9, 59, 13, 9);
						
						String[] value={"1、未按规定佩戴劳动防护用品","人身伤害","正确佩戴个人防护用品",
						         "2、使用非防爆工具，产生火花","火灾、爆炸","使用防爆工具",
						         "3、人体静电接触可燃气体","火灾、爆炸","进入作业场所前释放人体静电",
						         "4、未按要求开启储罐阀门","无法扫线","按要求开启作业罐的阀门",
						         "5、阀门泄漏","污染环境","使用不锈钢器具及时盛接、回收",
						         "6、罐呼吸阀故障","储罐涨裂","作业中加强检查呼吸阀"
							            };
						
						for (int j = 0; j < 6; j++) {
							HSSFRow rowj = sheet.createRow((short) 60+j);
							handleCell(sheet, rowj, style1,  value[0+j*3], 60+j, 0, 60+j, 4, 0);
							handleCell(sheet, rowj, style1,  value[1+j*3], 60+j, 5, 60+j, 8, 5);
							handleCell(sheet, rowj, style1,  value[2+j*3], 60+j, 9, 60+j, 13, 9);
						}
						
						if(state==4){
						
						sheet.setColumnWidth(0,700);
						
						HSSFRow row18 = sheet.createRow((short) 66);
						handleCell(sheet, row18, style1, "作业前检查", 66, 0, 71,0);
						handleCell(sheet, row18, style1,  "1、个人防护用品是否正确佩戴", 66, 1, 66, 6, 1);
						handleCell(sheet, row18, style1, content1.charAt(content1.lastIndexOf("a")+1)=='1'?"是":" ", 66, 7, 66, 13, 7);
						
						String[] value1={
						         "2、通讯系统是否正常","b",
						         "3、消防器材是否到位","c",
						         "4、是否释放身体静电","d",
						         "5、确认工艺流程是否正确","e",
						         "6、确认相关阀门是否按要求开启、关闭","f"
				                 };
				
				for (int x = 0; x <5; x++) {
					HSSFRow rowx = sheet.createRow((short) 67+x);
					handleCell(sheet, rowx, style1,  value1[x*2], 67+x, 1, 67+x, 6, 1);
					handleCell(sheet, rowx, style1,  content1.charAt(content1.lastIndexOf(value1[x*2+1])+1)=='1'?"是":" ", 67+x, 7, 67+x, 13, 7);
				}
						
				        HSSFRow row46 = sheet.createRow((short) 72);
				        handleCell(sheet, row46, style1,  "作业人员:"+name1, 72, 0, 72, 6, 0);
						handleCell(sheet, row46, style1,  "日期:"+time1, 72, 7, 72, 13, 7);
						
						HSSFRow row24 = sheet.createRow((short) 73);
						handleCell(sheet, row24, style1, "作业中检查", 73, 0, 76,0);
					    handleCell(sheet, row24, style1,  "1、中控室SCADA系统液位是否正常上升", 73, 1, 73, 6, 1);
					    if(content2.lastIndexOf("g")!=-1){
						handleCell(sheet, row24, style1,  content2.charAt(content2.lastIndexOf("g")+1)=='1'?"是":(content2.charAt(content2.lastIndexOf("g")+1)=='3'?"不适用":" "), 73, 7, 73, 11, 7);
						handleCell(sheet, row24, style1,  name4, 73, 12, 73, 13, 12);
					    }else{
					    	handleCell(sheet, row24, style1,  "", 73, 12, 73, 13, 12);
					    }
						String[] value2={"2、储罐呼吸阀是否正常","h",
						         "3、是否有物料滴漏","i",
						         "4、扫线球是否收到","j",	
				                 };
						
						for (int y = 0; y < 3; y++) {
							HSSFRow rowx = sheet.createRow((short) 74+y);
							handleCell(sheet, rowx, style1,  value2[y*2], 74+y, 1, 74+y, 6, 1);
							handleCell(sheet, rowx, style1,  content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='1'?"是":" ", 74+y, 7, 74+y, 13, 7);
						}
						
						HSSFRow row45 = sheet.createRow((short) 77);
				        handleCell(sheet, row45, style1,  "作业人员:"+name2, 77, 0, 77, 6, 0);
						handleCell(sheet, row45, style1,  "日期:"+time2, 77, 7, 77, 13, 7);
						
					    HSSFRow row28 = sheet.createRow((short) 78);
						handleCell(sheet, row28, style1, "作业后检查", 78, 0, 83,0);
						handleCell(sheet, row28, style1,  "1、阀门是否关闭", 78, 1, 78, 6, 1);
						handleCell(sheet, row28, style1,  content3.charAt(content3.lastIndexOf("k")+1)=='1'?"是":" ", 78, 7, 78, 13, 7);
						
						String[] value3={
						         "2、管线是否泄压","l",
						         "3、扫线球是否完整","m",
						         "4、现场是否清理干净","n",
						         "5、物料是否已回收","o",
						         "6、记录是否完善","p"
				                 };
		   for (int z = 0; z < 5; z++) {
			   HSSFRow rowz = sheet.createRow((short) 79+z);
			   handleCell(sheet, rowz, style1,  value3[z*2], 79+z, 1, 79+z, 6, 1);
			   handleCell(sheet, rowz, style1,  content3.charAt(content3.lastIndexOf(value3[z*2+1])+1)=='1'?"是":" ", 79+z, 7, 79+z, 13, 7);
		 }
						
					
		    HSSFRow row44 = sheet.createRow((short) 84);
			handleCell(sheet, row44, style1,  "作业人员:"+name3, 84, 0, 84, 6, 0);
			handleCell(sheet, row44, style1,  "日期:"+time3, 84, 7, 84, 13, 7);
				}
						}
					
			
		}else if("21".equals((String)request.getParameter("type"))){
			NotifyDto notifyDto=new NotifyDto();
			notifyDto.setCode(request.getParameter("code"));
			notifyDto.setTypes(8+"");
			//notifyDto.setIsList(1);
		    List<Map<String,Object>> list=notifyDao.getNotifyList(notifyDto, 0, 0);
		    Map<String,Object> map=list.get(0);
		    String svg1= map.get("firPFDSvg").toString();
		    int state=Integer.valueOf(map.get("state").toString());
		    svg1 = svg1.substring(0,4)+" xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' "+svg1.substring(5) ;
			
		    String content1=" ";
		    String content2=" ";
		    String content3=" ";
            String name1="";
            String name2="";
            String name3="";
            String time1="";
            String time2="";
            String time3="";

		   for (int i = 1; i < list.size(); i++) {
			if(list.get(i).get("contentType").toString().equals("1")){
				content1+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("2")){
					name1+=list.get(i).get("sureUserName").toString();
					time1+=list.get(i).get("sureTime").toString();
				}
			}else if(list.get(i).get("contentType").toString().equals("3")){
				content3+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("4")){
					name3+=list.get(i).get("sureUserName").toString();
					time3+=list.get(i).get("sureTime").toString();
				}
			}else if(list.get(i).get("contentType").toString().equals("2")){
				content2+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("3")){
					name2+=list.get(i).get("sureUserName").toString();
					time2+=list.get(i).get("sureTime").toString();
				}
			}
		}

		    
			JSONObject o = JSONObject.parseObject(request.getParameter("content"));
			

            HSSFRow rowtitle = sheet.createRow((short) 0); 
			HSSFRow row0 = sheet.createRow((short) 2);
			handleCell(sheet, rowtitle, style, "清罐作业通知单", 0, 0, 1,13);
            handleCell(sheet, row0, style,  "日期:"+map.get("createTime").toString().substring(0,11), 2, 0, 2, 6,0);
			handleCell(sheet, row0, style,  "编号:"+request.getParameter("code").toString().substring(1), 2, 7, 2, 13,7);
			
//			handleCell(sheet, row0, style,  "日期:"+map.get("createTime").toString().substring(0,11), 2, 0, 2, 4,0);
//            handleCell(sheet, row0, style,  "JL2303-8", 2, 5, 2, 8,5);
//            handleCell(sheet, row0, style,  "编号:"+request.getParameter("code").toString().substring(1), 2, 9, 2, 13,9);

			HSSFRow row = sheet.createRow((short) 3);
			String str1="罐号："+o.getString("tankId");
			handleCell(sheet, row, style, str1, 3, 0, 3, 3,0);
			String str2="物料名称："+o.getString("productId");
			handleCell(sheet, row, style, str2, 3, 4, 3, 6,4);
			String str3="数量："+o.getString("goodsNum");
			handleCell(sheet, row, style, str3, 3, 7, 3, 9,7);
			String str4="泵号："+o.getString("pupmId");
			handleCell(sheet, row, style, str4, 3, 10, 3, 13,10);
			HSSFRow row1 = sheet.createRow((short) 4);
			handleCell(sheet, row1, style, request.getParameter("t1"), 4, 0, 4,13);
			HSSFRow row2 = sheet.createRow((short) 5);
			handleCell(sheet, row2, style,"", 5, 0, 13,13);
			handleSVG(request, sheet, wb, svg1, 0, 5);
			
			HSSFRow row3 = sheet.createRow((short) 14);
			handleCell(sheet, row3, style, request.getParameter("t2"), 14, 0, 14,13);
			HSSFRow row4 = sheet.createRow((short) 15);
			handleCell(sheet, row4, style,request.getParameter("v2") , 15, 0, 23,13);
			

            HSSFRow row7 = sheet.createRow((short) 24);
            handleCell(sheet, row7, style, "调度:"+map.get("sureUserName").toString(), 24, 0, 24,13);
			 
            
			
			HSSFRow row5 = sheet.createRow((short) 25);
			handleCell(sheet, row5, style1, "三、注意事项：", 25, 0, 25	,13);
			
			HSSFRow row6 = sheet.createRow((short) 26);
			handleCell(sheet, row6, style1,  
					 "1、罐区污水阀开启并确认罐区雨水阀关闭；\n"
                    +"2、相关管线物料已吹尽，低位已放尽并且与罐体已经脱开\n"
                    +"3、正确佩戴个人防护用品\n"
                    +"4、使用防爆工具；\n"
                    +"5、清罐清出的垃圾必须放入危险固体废物箱内\n"
                    +"6、作业过程中按规定做好罐内有毒气体的检测工作\n"
                    +"7、作业过程中必须安排好监护人员", 26, 0, 32,13);
			
			HSSFRow row9 = sheet.createRow((short) 33);
			handleCell(sheet, row9, style1, "四、作业中的风险分析：", 33, 0, 33, 13);
			
			HSSFRow row8 = sheet.createRow((short) 34);
			handleCell(sheet, row8, style,  "危害及潜在事件：", 34, 0, 34, 4, 0);
			handleCell(sheet, row8, style,  "主要后果", 34, 5, 34, 8, 5);
			handleCell(sheet, row8, style,  "安全措施", 34, 9, 34, 13, 9);
			
			String[] value={"1、未正确佩戴个人防护用品","人身伤害","严格按照规定佩戴个人防护用品",
			         "2、雨水阀门未及时关闭造成污水外排","造成水体污染","严格执行规定，关闭雨水阀门",
			         "3、人体静电未释放","产生静电，可能导致火灾","进入作业场所前释放人体静电",
			         "4、使用非防爆工具，产生火花","火灾爆炸","使用防爆工具",
			         "5、管线未与罐体脱开，物料进入罐内","人身伤害","清罐前确认管线与罐体断开",
			         "6、未对储罐进行有毒气体检测","人身伤害","作业前严格按规定办理手续、做好检测",
			         "7、作业前未将氮封装置和储罐脱开","人员窒息","作业前将氮封装置和储罐脱开",
			         "8、未对储罐进行可燃气体检测","爆炸","作业前严格按规定办理手续、做好检测",
			         "9、照明没有接安全电压造成漏电","人身伤害","按规定接安全电压"
				            };
			
			for (int j = 0; j < 9; j++) {
				HSSFRow rowj = sheet.createRow((short) 35+j);
				handleCell(sheet, rowj, style1,  value[0+j*3], 35+j, 0, 35+j, 4, 0);
				handleCell(sheet, rowj, style1,  value[1+j*3], 35+j, 5, 35+j, 8, 5);
				handleCell(sheet, rowj, style1,  value[2+j*3], 35+j, 9, 35+j, 13, 9);
			}
			
			if(state==4){
			sheet.setColumnWidth(0,700);
			
			HSSFRow row18 = sheet.createRow((short) 44);
			handleCell(sheet, row18, style1, "作业前检查", 44, 0, 53,0);
			
			handleCell(sheet, row18, style1,  "1、个人防护用品是否正确佩戴", 44, 1, 44, 6, 1);
			handleCell(sheet, row18, style1, content1.charAt(content1.lastIndexOf("a")+1)=='1'?"是":" ", 44, 7, 44, 13, 7);
			
			String[] value1={
			         "2、通讯系统是否通畅","b",
			         "3、消防器材是否到位","c",
			         "4、是否释放身体静电","d",
			         "5、确认管线是否吹尽并与储罐断开","e",
			         "6、确认罐区污水阀是否开启","f",
			         "7、确认罐区雨水阀是否关闭","g",
			         "8、是否使用分离槽","h",
			         "9、若有氮封装置，是否与储罐脱开","i",
			         "10、使用的泵的型号是否正确","j"
	                 };
	
	for (int x = 0; x <9; x++) {
		HSSFRow rowx = sheet.createRow((short) 45+x);
		handleCell(sheet, rowx, style1,  value1[x*2], 45+x, 1, 45+x, 6, 1);
		if(x==7||x==6){
			handleCell(sheet, rowx, style1,  content1.charAt(content1.lastIndexOf(value1[x*2+1])+1)=='1'?"是":(content1.charAt(content1.lastIndexOf(value1[x*2+1])+1)=='3'?"不适用":" "), 45+x, 7, 45+x, 13, 7);
		}else{
			handleCell(sheet, rowx, style1,  content1.charAt(content1.lastIndexOf(value1[x*2+1])+1)=='1'?"是":" ", 45+x, 7, 45+x, 13, 7);	
		}
	}
	
	    HSSFRow row23 = sheet.createRow((short) 54);
		handleCell(sheet, row23, style1,  "作业人员:"+name1, 54, 0, 54, 6, 0);
		handleCell(sheet, row23, style1,  "日期:"+time1, 54, 7, 54, 13, 7);
			
		HSSFRow row28 = sheet.createRow((short) 55);
		handleCell(sheet, row28, style1, "作业中检查", 55, 0, 58,0);
		handleCell(sheet, row28, style1,  "1、泵运行是否正常", 55, 1, 55, 6, 1);
		handleCell(sheet, row28, style1,  content2.charAt(content2.lastIndexOf("k")+1)=='1'?"是":" ", 55, 7, 55, 13, 7);
		
		String[] value2={
		         "2、是否有物料滴漏","l",
		         "3、现场监护人员是否在岗","m",
		         "4、是否按要求进行巡检","n"
                };
		
		for (int y = 0; y < 3; y++) {
			HSSFRow rowx = sheet.createRow((short) 56+y);
			handleCell(sheet, rowx, style1,  value2[y*2], 56+y, 1, 56+y, 6, 1);
			handleCell(sheet, rowx, style1,  content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='1'?"是":" ", 56+y, 7, 56+y, 13, 7);
		}
		
		HSSFRow row22 = sheet.createRow((short) 59);
		handleCell(sheet, row22, style1,  "作业人员:"+name2, 59, 0, 59, 6, 0);
		handleCell(sheet, row22, style1,  "日期:"+time2, 59, 7, 59, 13, 7);
			
		HSSFRow row32 = sheet.createRow((short) 60);
		handleCell(sheet, row32, style1, "作业后检查", 60, 0, 65,0);
		
		handleCell(sheet, row32, style1,  "1、泵和管线是否拆除", 60, 1, 60, 6, 1);
		handleCell(sheet, row32, style1,  content3.charAt(content3.lastIndexOf("o")+1)=='1'?"是":" ", 60, 7, 60, 13, 7);
		
		String[] value3={
		         "2、管线是否正确泄压","p",
		         "3、现场是否整理干净","q",
		         "4、物料是否入库","r",
		         "5、人孔是否打开","s",
		         "6、通风扇是否安装","t",
		         "7、记录是否完善","u",
		         "8、罐区雨污水阀是否复位","v"
                };
       for (int z = 0; z < 7; z++) {
               HSSFRow rowz = sheet.createRow((short) 61+z);
               handleCell(sheet, rowz, style1,  value3[z*2], 61+z, 1, 61+z, 6, 1);
               handleCell(sheet, rowz, style1,  content3.charAt(content3.lastIndexOf(value3[z*2+1])+1)=='1'?"是":" ", 61+z, 7, 61+z, 13, 7);
                                   }
        HSSFRow row21 = sheet.createRow((short) 68);
		handleCell(sheet, row21, style1,  "作业人员:"+name3, 68, 0, 68, 6, 0);
		handleCell(sheet, row21, style1,  "日期:"+time3, 68, 7, 68, 13, 7);
		}
			
		}else if("22".equals((String)request.getParameter("type"))){
			NotifyDto notifyDto=new NotifyDto();
			notifyDto.setCode(request.getParameter("code"));
			notifyDto.setTypes(9+"");
			//notifyDto.setIsList(1);
		    List<Map<String,Object>> list=notifyDao.getNotifyList(notifyDto, 0, 0);
		    Map<String,Object> map=list.get(0);
		    String svg1= map.get("secPFDSvg").toString();
		    int state=Integer.valueOf(map.get("state").toString());
		    svg1 = svg1.substring(0,4)+" xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' "+svg1.substring(5) ;
			
		    String content1=" ";
		    String content2=" ";
		    String content3=" ";
            String name1="";
            String name2="";
            String name3="";
            String name4="";
            String time1="";
            String time2="";
            String time3="";

		   for (int i = 1; i < list.size(); i++) {
			if(list.get(i).get("contentType").toString().equals("1")){
				content1+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("2")){
					name1+=list.get(i).get("sureUserName").toString();
					time1+=list.get(i).get("sureTime").toString();
				}
			}else if(list.get(i).get("contentType").toString().equals("3")){
				content3+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("4")){
					name3+=list.get(i).get("sureUserName").toString();
					time3+=list.get(i).get("sureTime").toString();
				}
			}else if(list.get(i).get("contentType").toString().equals("2")){
				content2+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("3")){
					name2+=list.get(i).get("sureUserName").toString();
					time2+=list.get(i).get("sureTime").toString();
				}
				if(list.get(i).get("flag").toString().equals("1")){
					name4+=list.get(i).get("sureUserName").toString();
				}
			}
		}
		    
		    HSSFRow rowtitle = sheet.createRow((short) 0); 
			HSSFRow row0 = sheet.createRow((short) 2); 
			
			handleCell(sheet, rowtitle, style, "储罐放水作业通知单", 0, 0, 1,13);
			
			handleCell(sheet, row0, style,  "日期:"+map.get("createTime").toString().substring(0,11), 2, 0, 2, 4,0);
	        handleCell(sheet, row0, style,  "JL2303-5", 2, 5, 2, 8,5);
	        handleCell(sheet, row0, style,  "编号:"+request.getParameter("code").toString().substring(1), 2, 9, 2, 13,9);

			
			HSSFRow row = sheet.createRow((short) 3);
			handleCell(sheet, row, style, request.getParameter("t1"), 3, 0, 3,13);
			HSSFRow row1 = sheet.createRow((short) 4);
			handleCell(sheet, row1, style, request.getParameter("v1"), 4, 0, 13,13);
			
			HSSFRow row2 = sheet.createRow((short) 14);
			handleCell(sheet, row2, style, request.getParameter("t2"), 14, 0, 14,13);
			
			HSSFRow row3 = sheet.createRow((short) 15);
			handleCell(sheet, row3, style, "", 15, 0, 26,13);
			handleSVG(request, sheet, wb, svg1, 0, 15);
			
			HSSFRow row7 = sheet.createRow((short) 27); 
			handleCell(sheet, row7, style, "调度:"+map.get("sureUserName").toString(), 27, 0, 27,13);
			
			 HSSFRow row9 = sheet.createRow((short) 28);
			 handleCell(sheet, row9, style1, "三、作业注意事项：", 28, 0, 28,13);
			
			 HSSFRow row4 = sheet.createRow((short) 29);
			 handleCell(sheet, row4, style1,  
					  "1、正确佩戴个人防护用品；\n"
                     +"2、消防器材到位；\n"
                     +"3、确认雨水阀处关闭状态、污水阀处开启状态；\n"
                     +"4、监护人员到位，双人确认，作业期间不得离开现场；\n"
                     +"5、中控室加强对储罐的监控；\n"
                     +"6、如出现油花立即关闭阀门，结束放水。", 29, 0, 34,13);
			 
			    HSSFRow row10 = sheet.createRow((short) 35);
				handleCell(sheet, row10, style1, "四、作业中的风险分析：", 35, 0, 35,13);
					
				HSSFRow row11 = sheet.createRow((short) 36);
				handleCell(sheet, row11, style,  "危害及潜在事件：", 36, 0, 36, 4, 0);
				handleCell(sheet, row11, style,  "主要后果", 36, 5, 36, 8, 5);
				handleCell(sheet, row11, style,  "安全措施", 36, 9, 36, 13, 9);
				
				String[] value={"1、未按规定佩戴劳动防护用品","人身伤害","正确佩戴个人防护用品",
				         "2、使用非防爆工具，产生火花","火灾、爆炸","使用防爆工具",
				         "3、人体静电接触可燃气体","着火，爆炸","进入作业场所前释放人体静电",
				         "4、阀门泄漏","污染环境","使用不锈钢器具及时盛接、回收",
				         "5、污水排入雨水沟","污染","做好检查、双人确认、排入污水井",
				         "6、污水误入雨水系统","污染环境","放水前动力班确认雨污水阀门状态",
				         "7、污水中带出的油气挥发","人体中毒","正确使用劳动防护用品",
				         "8、物料溢出","污染环境，造成漏料","作业中加强监控、双人确认",
				         "9、现场人员过度放水将物料带出","溢料","作业中加强监控、双人确认",
				         "10、盲板未封堵好造成物料滴漏","污染环境","封堵好盲板、双人确认"
					            };
				
				for (int j = 0; j < 10; j++) {
					HSSFRow rowj = sheet.createRow((short) 37+j);
					handleCell(sheet, rowj, style1,  value[0+j*3], 37+j, 0, 37+j, 4, 0);
					handleCell(sheet, rowj, style1,  value[1+j*3], 37+j, 5, 37+j, 8, 5);
					handleCell(sheet, rowj, style1,  value[2+j*3], 37+j, 9, 37+j, 13, 9);
				}
				if(state==4){
				
				sheet.setColumnWidth(0,700);
				
				HSSFRow row18 = sheet.createRow((short) 47);
				handleCell(sheet, row18, style1, "作业前检查", 47, 0, 52,0);
				handleCell(sheet, row18, style1,  "1、个人防护用品是否正确佩戴", 47, 1, 47, 6, 1);
				handleCell(sheet, row18, style1, content1.charAt(content1.lastIndexOf("a")+1)=='1'?"是":" ", 47, 7, 47, 13, 7);
				
				String[] value1={
				         "2、通讯系统是否通畅","b",
				         "3、消防器材是否到位","c",
				         "4、是否释放身体静电","d",
				         "5、确认工艺流程是否正确","e",
				         "6、确认相关阀门是否按要求开启、关闭","f"
		                 };
		
		for (int x = 0; x <5; x++) {
			HSSFRow rowx = sheet.createRow((short) 48+x);
			handleCell(sheet, rowx, style1,  value1[x*2], 48+x, 1, 48+x, 6, 1);
			handleCell(sheet, rowx, style1,  content1.charAt(content1.lastIndexOf(value1[x*2+1])+1)=='1'?"是":" ", 48+x, 7, 48+x, 13, 7);
		}

        HSSFRow row22 = sheet.createRow((short) 53);
        handleCell(sheet, row22, style1,  "作业人员:"+name1, 53, 0, 53, 6, 0);
        handleCell(sheet, row22, style1,  "日期:"+time1, 53, 7, 53, 13, 7);
				
				HSSFRow row24 = sheet.createRow((short) 54);
				handleCell(sheet, row24, style1, "作业中检查", 54, 0, 57,0);
			
				handleCell(sheet, row24, style1,  "1、中控室SCADA系统液位是否正常下降", 54, 1, 54, 6, 1);
				if(content2.lastIndexOf("g")!=-1){
				handleCell(sheet, row24, style1,  content2.charAt(content2.lastIndexOf("g")+1)=='1'?"是":" ", 54, 7, 54, 11, 7);
				handleCell(sheet, row24, style1,  name4, 54, 12, 54, 13, 12);
				}else{
					handleCell(sheet, row24, style1,  "", 7, 12, 54, 13, 12);
				}
				
				HSSFRow row25 = sheet.createRow((short) 55);
				handleCell(sheet, row25, style1,  "2、是否有物料溢出", 55, 1, 55, 6, 1);
				handleCell(sheet, row25, style1,  content2.charAt(content2.lastIndexOf("h")+1)=='1'?"是":" ", 55, 7, 55, 13, 7);
			 
				HSSFRow row26 = sheet.createRow((short) 56);
				handleCell(sheet, row26, style1,  "3、是否有物料排入雨水沟", 56, 1, 56, 6, 1);
				handleCell(sheet, row26, style1, content2.charAt(content2.lastIndexOf("i")+1)=='1'?"否":(content2.charAt(content2.lastIndexOf("i")+1)=='3'?"不适用":" "), 56, 7, 56, 13, 7);
				
				HSSFRow row27 = sheet.createRow((short) 57);
				handleCell(sheet, row27, style1,  "4、放水至有油沫等杂物时即停止作业", 57, 1, 57, 6, 1);
				handleCell(sheet, row27, style1,  content2.charAt(content2.lastIndexOf("j")+1)=='1'?"是":" ", 57, 7, 57, 13, 7);
				
				HSSFRow row21 = sheet.createRow((short) 58);
		        handleCell(sheet, row21, style1,  "作业人员:"+name2, 58, 0, 58, 6, 0);
		        handleCell(sheet, row21, style1,  "日期:"+time2, 58, 7, 58, 13, 7);
				
				HSSFRow row28 = sheet.createRow((short) 59);
				handleCell(sheet, row28, style1, "作业后检查", 59, 0, 63,0);
				handleCell(sheet, row28, style1,  "1、排污口阀门是否关闭", 59, 1, 59, 6, 1);
				handleCell(sheet, row28, style1,  content3.charAt(content3.lastIndexOf("k")+1)=='1'?"是":" ", 59, 7, 59, 13, 7);
				
				String[] value3={"2、排污口盲板是否正确封堵","l",
				         "3、现场是否整理干净","m",
				         "4、记录是否完善","n",
				         "5、雨污水阀门是否复位","o"
		                 };
  for (int z = 0; z < 4; z++) {
	   HSSFRow rowz = sheet.createRow((short) 60+z);
	   handleCell(sheet, rowz, style1,  value3[z*2], 60+z, 1, 60+z, 6, 1);
	   handleCell(sheet, rowz, style1,  content3.charAt(content3.lastIndexOf(value3[z*2+1])+1)=='1'?"是":" ", 60+z, 7, 60+z, 13, 7);
}
  
  HSSFRow row20 = sheet.createRow((short) 64);
  handleCell(sheet, row20, style1,  "作业人员:"+name3, 64, 0, 64, 6, 0);
  handleCell(sheet, row20, style1,  "日期:"+time3, 64, 7, 64, 13, 7);   
				}
				
           }else if("23".equals((String)request.getParameter("type"))){
			
			
			NotifyDto notifyDto=new NotifyDto();
			notifyDto.setCode(request.getParameter("code"));
			notifyDto.setTypes(10+"");
		    List<Map<String,Object>> list=notifyDao.getNotifyList(notifyDto, 0, 0);
		    Map<String,Object> map=list.get(0);
		    int state=Integer.valueOf(map.get("state").toString());
		    
		    String content1=" ";
		    String content2=" ";
		    String content3=" ";
            String name1="";
            String name2="";
            String name3="";
            String name4="";
            String name5="";
            String name6="";
            String name7="";
            String name8="";
            
            String time1="";
            String time2="";
            String time3="";

		   for (int i = 1; i < list.size(); i++) {
			if(list.get(i).get("contentType").toString().equals("1")){
				content1+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("2")){
					name1+=list.get(i).get("sureUserName").toString();
					time1+=list.get(i).get("sureTime").toString();
				}
				if(list.get(i).get("flag").toString().equals("1")){
					if(list.get(i).get("content").toString().charAt(0)=='f')
					{
						name4+=list.get(i).get("sureUserName").toString();
					}
					if(list.get(i).get("content").toString().charAt(0)=='g')
					{
						name5+=list.get(i).get("sureUserName").toString();
					}
					if(list.get(i).get("content").toString().charAt(0)=='h')
					{
						name6+=list.get(i).get("sureUserName").toString();
					}
				}
			}else if(list.get(i).get("contentType").toString().equals("3")){
				content3+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("4")){
					name3+=list.get(i).get("sureUserName").toString();
					time3+=list.get(i).get("sureTime").toString();
				}
				if(list.get(i).get("flag").toString().equals("1")){
					if(list.get(i).get("content").toString().charAt(0)=='l')
					{
						name4+=list.get(i).get("sureUserName").toString();
					}
					if(list.get(i).get("content").toString().charAt(0)=='m')
					{
						name5+=list.get(i).get("sureUserName").toString();
					}
				}
			}else if(list.get(i).get("contentType").toString().equals("2")){
				content2+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("3")){
					name2+=list.get(i).get("sureUserName").toString();
					time2+=list.get(i).get("sureTime").toString();
				}
			}
		}

		    HSSFRow rowtitle = sheet.createRow((short) 0); 
			HSSFRow row0 = sheet.createRow((short) 2); 

			handleCell(sheet, rowtitle, style, "储罐开人孔作业通知单", 0, 0, 1,13);
			
			handleCell(sheet, row0, style,  "日期:"+map.get("createTime").toString().substring(0,11), 2, 0, 2, 4,0);
            handleCell(sheet, row0, style,  "JL2303-12", 2, 5, 2, 8,5);
            handleCell(sheet, row0, style,  "编号:"+request.getParameter("code").toString().substring(1), 2, 9, 2, 13,9);

			JSONObject o = JSONObject.parseObject(request.getParameter("content"));
			HSSFRow row = sheet.createRow((short) 3);
			String str1="罐号："+o.getString("tankId");
			handleCell(sheet, row, style, str1, 3, 0, 3, 6,0);
			String str2="物料名称："+o.getString("productId");
			handleCell(sheet, row, style, str2, 3, 7, 3, 13,7);
			HSSFRow row1 = sheet.createRow((short) 4);
			handleCell(sheet, row1, style, request.getParameter("t1"), 4, 0, 4,13);
			HSSFRow row2 = sheet.createRow((short) 5);
			handleCell(sheet, row2, style, request.getParameter("v1"), 5, 0, 13,13);
			HSSFRow row3 = sheet.createRow((short) 14);
			handleCell(sheet, row3, style, request.getParameter("t2"), 14, 0, 14,13);
			HSSFRow row4 = sheet.createRow((short) 15);
			String str3="SCADA系统雷达液位： "+o.getString("scadaLevel")+"mm";
			handleCell(sheet, row4, style, str3, 15, 0, 15, 6,0);
			String str4="计量手尺液位： "+o.getString("handLevel")+"mm";
			handleCell(sheet, row4, style, str4, 15, 7, 15, 13,7);
			
			HSSFRow row7 = sheet.createRow((short) 16);
			handleCell(sheet, row7, style, "调度:"+map.get("sureUserName").toString(), 16, 0, 16,13);    

			HSSFRow row8 = sheet.createRow((short) 17);
			handleCell(sheet, row8, style1, "三、作业注意事项：", 17, 0, 17	,13);
			
			 HSSFRow row9 = sheet.createRow((short) 18);
			 handleCell(sheet, row9, style1,  
					  "1、正确佩戴个人防护用品；\n"
                     +"2、消防器材到位；\n"
                     +"3、监护人员到位，双人确认，作业期间不得离开现场；\n"
                     +"4、中控室加强对储罐的监控；", 18, 0, 21,13);
			 
                 
			    HSSFRow row10 = sheet.createRow((short) 22);
				handleCell(sheet, row10, style1, "五、作业中的风险分析：", 22, 0, 22	,13);
					
				HSSFRow row11 = sheet.createRow((short) 23);
				handleCell(sheet, row11, style,  "危害及潜在事件：", 23, 0, 23, 4, 0);
				handleCell(sheet, row11, style,  "主要后果", 23, 5, 23, 8, 5);
				handleCell(sheet, row11, style,  "安全措施", 23, 9, 23, 13, 9);
				
				String[] value={"1、未按规定佩戴劳动防护用品","人身伤害","正确佩戴个人防护用品",
				         "2、使用非防爆工具，产生火花","火灾、爆炸","使用防爆工具",
				         "3、人体静电接触可燃气体","着火，爆炸","进入罐区释放人体静电",
				         "4、储罐内油气挥发","人体中毒","正确使用劳动防护用品",
				         "5、开错储罐人孔","物料泄漏，环境污染","作业前加强核对确认",
				         "6、开人孔时储罐内物料高度高于人孔下沿","污染环境，造成漏料","作业前加强储罐内物料量的确认",
				         "7、人员坠落","人身伤害","正确佩戴劳动防护用品，加强现场监护",
				         "8、螺栓、螺母腐蚀，咬死","用力过度，人身伤害","平时做好螺母、螺栓的保养工作"
					            };
				
				for (int j = 0; j < 8; j++) {
					HSSFRow rowj = sheet.createRow((short) 24+j);
					handleCell(sheet, rowj, style1,  value[0+j*3], 24+j, 0, 24+j, 4, 0);
					handleCell(sheet, rowj, style1,  value[1+j*3], 24+j, 5, 24+j, 8, 5);
					handleCell(sheet, rowj, style1,  value[2+j*3], 24+j, 9, 24+j, 13, 9);
				}
				
				if(state==4){
				HSSFRow row20 = sheet.createRow((short) 32);
				handleCell(sheet, row20, style1, "五、作业过程的检查：", 32, 0, 32	,13);
				
				sheet.setColumnWidth(0,700);
				
				HSSFRow row22 = sheet.createRow((short) 33);
				
				handleCell(sheet, row22, style1, "作业前检查", 33, 0, 40,0);
				handleCell(sheet, row22, style1,  "1、个人防护用品是否正确佩戴", 33, 1, 33, 6, 1);
				handleCell(sheet, row22, style1,  content1.charAt(content1.lastIndexOf("a")+1)=='1'?"是":" ", 33, 7, 33, 13, 7);
				
				String[] value1={
				         "2、消防器材是否到位","b",
				         "3、是否释放身体静电","c",
				         "4、是否使用防爆工具","d",
				         "5、储罐罐号与作业单是否一致","e",
				         "6、确认储罐是否与主管线断开","f",
				         "7、确认储罐尾部管线是否断开","g",
				         "8、储罐液位是否确认低于人孔","h"
		                 };
		
		for (int x = 0; x <7; x++) {
			HSSFRow rowx = sheet.createRow((short) 34+x);
			handleCell(sheet, rowx, style1,  value1[x*2], 34+x, 1, 34+x, 6, 1);
			if(x==4||x==5){
				if(content1.lastIndexOf(value1[x*2+1])!=-1){
			        handleCell(sheet, rowx, style1,  content1.charAt(content1.lastIndexOf(value1[x*2+1])+1)=='1'?"是":(content1.charAt(content1.lastIndexOf(value1[x*2+1])+1)=='3'?"不适用":" "), 45+x, 7, 45+x, 11, 7);
				if(x==4){
					handleCell(sheet, rowx, style1,  name4, 45+x, 12, 45+x, 13, 12);
				}
				if(x==5){
					handleCell(sheet, rowx, style1,  name5, 45+x, 12, 45+x, 13, 12);
				}
			}else{
				    handleCell(sheet, rowx, style1, "", 45+x, 7, 45+x, 13, 12);
			}
			}else{
				if(x==6){
					handleCell(sheet, rowx, style1,  content1.charAt(content1.lastIndexOf(value1[x*2+1])+1)=='1'?"是":" ", 34+x, 7, 34+x, 11, 7);
					handleCell(sheet, rowx, style1,  name6, 45+x, 12, 45+x, 13, 12);
				}else{
					handleCell(sheet, rowx, style1,  content1.charAt(content1.lastIndexOf(value1[x*2+1])+1)=='1'?"是":" ", 34+x, 7, 34+x, 13, 7);
				}
			}
		}
		    HSSFRow row46 = sheet.createRow((short) 41);
			handleCell(sheet, row46, style1,  "作业人员:"+name1, 41, 0, 41, 6, 0);
			handleCell(sheet, row46, style1,  "日期:"+time1, 41, 7, 41, 13, 7);
				
				HSSFRow row30 = sheet.createRow((short) 42);
				handleCell(sheet, row30, style1, "作业中检查", 42, 0, 44,0);
				handleCell(sheet, row30, style1,  "1、现场监护人员是否到位", 42, 1, 42, 6, 1);
				handleCell(sheet, row30, style1,  content2.charAt(content2.lastIndexOf("i")+1)=='1'?"是":" ", 42, 7, 42, 13, 7);
				
				HSSFRow row31 = sheet.createRow((short) 43);
				handleCell(sheet, row31, style1,  "2、螺栓、螺母是否腐蚀，咬死", 43, 1, 43, 6, 1);
				handleCell(sheet, row31, style1,  content2.charAt(content2.lastIndexOf("j")+1)=='1'?"是":" ", 43, 7, 43, 13, 7);
				
				HSSFRow row32 = sheet.createRow((short) 44);
				handleCell(sheet, row32, style1,  "3、罐内是否有残余物料", 44, 1, 44, 6, 1);
				handleCell(sheet, row32, style1,  content2.charAt(content2.lastIndexOf("k")+1)=='1'?"是":" ", 44, 7, 44, 13, 7);
			    
				HSSFRow row40 = sheet.createRow((short) 45);
				handleCell(sheet, row40, style1,  "作业人员:"+name2, 45, 0, 45, 6, 0);
				handleCell(sheet, row40, style1,  "日期:"+time2, 45, 7, 45, 13, 7);
			
				HSSFRow row33 = sheet.createRow((short) 46);
				handleCell(sheet, row33, style1, "作业后检查", 46, 0, 49,0);
				handleCell(sheet, row33, style1,  "1、是否按要求打开人孔", 46, 1, 46, 6, 1);
				if(content2.lastIndexOf("l")!=-1){
				handleCell(sheet, row33, style1,  content3.charAt(content3.lastIndexOf("l")+1)=='1'?"是":" ", 46, 7, 46, 11, 7);
				handleCell(sheet, row33, style1,  name7, 46, 12, 46, 13, 12);
				}else{
					handleCell(sheet, row33, style1,  "", 46, 7, 46, 13, 12);
				}
				
				HSSFRow row34 = sheet.createRow((short) 47);
				handleCell(sheet, row34, style1,  "2、人孔附件是否完好", 47, 1, 47, 6, 1);
				if(content2.lastIndexOf("m")!=-1){
				handleCell(sheet, row34, style1,  content3.charAt(content3.lastIndexOf("m")+1)=='1'?"是":" ", 47, 7, 47, 11, 7);
				handleCell(sheet, row34, style1,  name8, 47, 12, 47, 13, 12);
				}else{
					handleCell(sheet, row33, style1,  "", 47, 7, 47, 13, 12);
				}
				
				HSSFRow row35 = sheet.createRow((short) 48);
				handleCell(sheet, row35, style1,  "3、现场是否整理干净", 48, 1, 48, 6, 1);
				handleCell(sheet, row35, style1,  content3.charAt(content3.lastIndexOf("n")+1)=='1'?"是":" ", 48, 7, 48, 13, 7);
				
                HSSFRow row36 = sheet.createRow((short) 49);
				handleCell(sheet, row36, style1,  "4、记录是否完善", 49, 1, 49, 6, 1);
				handleCell(sheet, row36, style1,  content3.charAt(content3.lastIndexOf("o")+1)=='1'?"是":" ", 49, 7, 49, 13, 7);
				
				HSSFRow row41 = sheet.createRow((short) 50);
				handleCell(sheet, row41, style1,  "作业人员:"+name3, 50, 0, 50, 6, 0);
				handleCell(sheet, row41, style1,  "日期:"+time3, 50, 7, 50, 13, 7);
				}
				
		}else if("24".equals((String)request.getParameter("type"))){
			NotifyDto notifyDto=new NotifyDto();
			notifyDto.setCode(request.getParameter("code"));
			notifyDto.setTypes(11+"");
			//notifyDto.setIsList(1);
		    List<Map<String,Object>> list=notifyDao.getNotifyList(notifyDto, 0, 0);
		    Map<String,Object> map=list.get(0);
            int state=Integer.valueOf(map.get("state").toString());
            String content= map.get("content").toString();
		    String svg1= content.substring(content.indexOf("<svg"), content.indexOf("</svg>")+6);
		   
            
		    String content1=" ";
		    String content2=" ";
		    String content3=" ";
            String name1="";
            String name2="";
            String name3="";
            String name4="";
            String name5="";
            String name6="";
            String name7="";
            String time1="";
            String time2="";
            String time3="";

		   for (int i = 1; i < list.size(); i++) {
			if(list.get(i).get("contentType").toString().equals("1")){
				content1+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("2")){
					name1+=list.get(i).get("sureUserName").toString();
					time1+=list.get(i).get("sureTime").toString();
				}
				if(list.get(i).get("flag").toString().equals("1")){
					if(list.get(i).get("content").toString().charAt(0)=='g')
					{
						name4+=list.get(i).get("sureUserName").toString();
					}
					if(list.get(i).get("content").toString().charAt(0)=='h')
					{
						name5+=list.get(i).get("sureUserName").toString();
					}
				}
			}else if(list.get(i).get("contentType").toString().equals("3")){
				content3+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("4")){
					name3+=list.get(i).get("sureUserName").toString();
					time3+=list.get(i).get("sureTime").toString();
				}
				if(list.get(i).get("flag").toString().equals("1")){
					name6+=list.get(i).get("sureUserName").toString();
				}
			}else if(list.get(i).get("contentType").toString().equals("2")){
				content2+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("3")){
					name2+=list.get(i).get("sureUserName").toString();
					time2+=list.get(i).get("sureTime").toString();
				}
				if(list.get(i).get("flag").toString().equals("1")){
					if(list.get(i).get("content").toString().charAt(0)=='j')
					{
						name6+=list.get(i).get("sureUserName").toString();
					}
					if(list.get(i).get("content").toString().charAt(0)=='n')
					{
						name7+=list.get(i).get("sureUserName").toString();
					}
				}
			}
		}

		    HSSFRow rowtitle = sheet.createRow((short) 0); 
		  	HSSFRow row0 = sheet.createRow((short) 2); 
		    handleCell(sheet, rowtitle, style, "转输作业通知单", 0, 0, 1,13);
		    
		  	handleCell(sheet, row0, style,  "日期:"+map.get("createTime").toString().substring(0,11), 2, 0, 2, 4,0);
            handleCell(sheet, row0, style,  "JL2303-11", 2, 5, 2, 8,5);
            handleCell(sheet, row0, style,  "编号:"+request.getParameter("code").toString().substring(1), 2, 9, 2, 13,9);

			HSSFRow row = sheet.createRow((short) 3);
			handleCell(sheet, row, style, request.getParameter("t1"), 3, 0, 3,13);
			HSSFRow row1 = sheet.createRow((short) 4);
			String str1="物料名称："+request.getParameter("v1");
			handleCell(sheet, row1, style,str1 , 4, 0, 4,13);
			HSSFRow row2 = sheet.createRow((short) 5);
			handleCell(sheet, row2, style, request.getParameter("t2"), 5, 0, 5,13);
			HSSFRow row3 = sheet.createRow((short) 6);
			handleCell(sheet, row3, style, "", 6, 0, 17,13);
			if(svg1!=null){
				if(svg1.indexOf("\\\"")!=-1){
					svg1=svg1.replaceAll("\\\\\"", "\"");
				}
				svg1 = svg1.substring(0,4)+" xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' "+svg1.substring(5) ;
                handleSVG(request, sheet, wb, svg1, 0, 6);
            }
			HSSFRow row4 = sheet.createRow((short) 18);
			handleCell(sheet, row4, style, request.getParameter("t3"), 18, 0, 18,13);
			HSSFRow row5 = sheet.createRow((short) 19);
			handleCell(sheet, row5, style, request.getParameter("v3"), 19, 0, 26,13);
			
			HSSFRow row7 = sheet.createRow((short) 27);
			handleCell(sheet, row7, style, "调度:"+map.get("sureUserName").toString(), 27, 0, 27,13);    

			HSSFRow row8 = sheet.createRow((short) 28);
			handleCell(sheet, row8, style1, "四、作业注意事项：", 28, 0, 28	,13);
			
			HSSFRow row9 = sheet.createRow((short) 29);
			handleCell(sheet, row9, style1,  
					  "1、正确佩戴个人防护用品；\n"
                     +"2、发货时储罐应开进口阀；\n"
                     +"3、发货时检查储罐呼吸阀是否正常，如有异常及时与调度联系；\n"
                     +"4、作业过程中加强检查，与调度保持联系；\n"
                     +"5、中控室加强对储罐的监控\n"
                     +"6、放气作业结束，放气过程中产生的物料及时回收入库", 29, 0, 34,13);
			    
			    HSSFRow row10 = sheet.createRow((short) 35);
			    handleCell(sheet, row10, style1, "五、作业过程中的风险分析及相关措施：", 35, 0, 35	,13);
					
			    HSSFRow row11 = sheet.createRow((short) 36);
				handleCell(sheet, row11, style,  "危害及潜在事件：", 36, 0, 36, 4, 0);
				handleCell(sheet, row11, style,  "主要后果", 36, 5, 36, 8, 5);
				handleCell(sheet, row11, style,  "安全措施", 36, 9, 36, 13, 9);
				
				String[] value={"1、未按规定佩戴劳动防护用品","人身伤害","正确佩戴个人防护用品",
						        "2、使用非防爆工具，产生火花","火灾、爆炸","使用防爆工具",
						        "3、人体静电接触可燃气体	","火灾、爆炸","进入作业场所前释放人体静电",
						        "4、作业前未检查管线状态","发错罐、泵损坏、物料串罐","根据作业通知单认真检查管线状态，与调度保持联系、确认。",
						        "5、未按要求开启储罐阀门","物料出现品质问题","按要求开启作业罐的阀门",
						        "6、阀门泄漏","污染环境","使用不锈钢器具及时盛接、回收",
						        "7、罐呼吸阀故障","造成瘪罐","严格按操作规程执行",
						        "8、发货过程未及时复查、巡检","发货时阀门、管线泄漏未能及时发现，造成安全隐患。","在发货过程中继续关注设备运行情况，按要求进行巡检。",
						        "9、压管线压力过高","管线破裂、阀门渗漏、泵坏。","关注压力，开旁路压管线，及时关泵。",
						        "10、结束后管线及时泄压","管线涨压破裂","结束后及时与调度联系，及时开启泄压阀。"
					            };
				
				for (int j = 0; j < 10; j++) {
					HSSFRow rowj = sheet.createRow((short) 37+j);
					handleCell(sheet, rowj, style1,  value[0+j*3], 37+j, 0, 37+j, 4, 0);
					handleCell(sheet, rowj, style1,  value[1+j*3], 37+j, 5, 37+j, 8, 5);
					handleCell(sheet, rowj, style1,  value[2+j*3], 37+j, 9, 37+j, 13, 9);
				}
				if(state==4){
				HSSFRow row12 = sheet.createRow((short) 46);
			    handleCell(sheet, row12, style1, "六、作业过程的检查", 46, 0, 46	,13);
			    
			    sheet.setColumnWidth(0,700);
			    
				HSSFRow row22 = sheet.createRow((short) 47);
				handleCell(sheet, row22, style1, "作业前检查", 47, 0, 54,0);
				handleCell(sheet, row22, style1, "1、个人防护用品是否正确佩戴", 47, 1, 47, 6, 1);
				handleCell(sheet, row22, style1,  content1.charAt(content1.lastIndexOf("a")+1)=='1'?"是":" ", 47, 7, 47, 13, 7);
				
				String[] value1={
						         "2、通讯系统是否通畅","b",
						         "3、消防器材是否到位","c",
						         "4、是否释放身体静电","d",
						         "5、确认工艺流程是否正确","e",
						         "6、确认相关阀门是否按要求开启、关闭","f",
						         "7、中控室SCADA系统参数是否正常","g",
						         "8、确认接收方相关阀门是否按要求开启","h"
				                 };
				
				for (int x = 0; x <7; x++) {
					HSSFRow rowx = sheet.createRow((short) 48+x);
					handleCell(sheet, rowx, style1,  value1[x*2], 48+x, 1, 48+x, 6, 1);
					if(x==5||x==6){
                        if(content2.lastIndexOf(value1[x*2+1])!=-1){
                        handleCell(sheet, rowx, style1,  content1.charAt(content1.lastIndexOf(value1[x*2+1])+1)=='1'?"是":" ", 48+x, 7, 48+x, 11, 7);
						if(x==5){
							handleCell(sheet, rowx, style1,  name4, 48+x, 12, 48+x, 13, 12);
						}else{
							handleCell(sheet, rowx, style1,  name5, 48+x, 12, 48+x, 13, 12);
						}
                        }else{
                        	handleCell(sheet, rowx, style1,  "", 48+x, 7, 48+x, 13, 7);
                        }
					}else{
						handleCell(sheet, rowx, style1,  content1.charAt(content1.lastIndexOf(value1[x*2+1])+1)=='1'?"是":" ", 48+x, 7, 48+x, 13, 7);
					}
				}
				
				HSSFRow row41 = sheet.createRow((short) 55);
				handleCell(sheet, row41, style1,  "作业人员:"+name1, 55, 0, 55, 6, 0);
				handleCell(sheet, row41, style1,  "日期:"+time1, 55, 7, 55, 13, 7);
			    
				HSSFRow row23 = sheet.createRow((short) 56);
				handleCell(sheet, row23, style1, "作业中检查", 56, 0, 61,0);
				handleCell(sheet, row23, style1, "1、储罐呼吸阀是否正常", 56, 1, 56, 6, 1);
				handleCell(sheet, row23, style1,  content2.charAt(content2.lastIndexOf("i")+1)=='1'?"是":" ", 56, 7, 56, 13, 7);
				
				String[] value2={
				         "2、中控室SCADA系统是否正常下降","j",
				         "3、泵运行是否正常","k",
				         "4、是否有物料滴漏","l",
				         "5、是否按要求进行巡检","m",
				         "6、确认接收方收货是否正常","n"
		                 };
				
				for (int y = 0; y < 5; y++) {
					HSSFRow rowx = sheet.createRow((short) 57+y);
					handleCell(sheet, rowx, style1,  value2[y*2], 57+y, 1, 57+y, 6, 1);
					if(y==4||y==0){
						if(content2.lastIndexOf(value2[y*2+1])!=-1){
						handleCell(sheet, rowx, style1,  content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='1'?"是":" ", 57+y, 7, 57+y, 11, 7);
						if(y==0){
							handleCell(sheet, rowx, style1,   name6, 57+y, 12, 57+y, 13, 12);
						}else{
							handleCell(sheet, rowx, style1,   name7, 57+y, 12, 57+y, 13, 12);
						}
						}else{
							handleCell(sheet, rowx, style1,   "", 57+y, 7, 57+y, 13, 7);
						}
					}else{
						handleCell(sheet, rowx, style1,  content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='1'?"是":" ", 57+y, 7, 57+y, 13, 7);
					}
				}
			    
				HSSFRow row40 = sheet.createRow((short) 62);
				handleCell(sheet, row40, style1,  "作业人员:"+name2, 62, 0, 62, 6, 0);
				handleCell(sheet, row40, style1,  "日期:"+time2, 62, 7, 62, 13, 7);
			    
				HSSFRow row24 = sheet.createRow((short) 63);
				handleCell(sheet, row24, style1, "作业后检查", 63, 0, 68,0);
				handleCell(sheet, row24, style1, "1、泵是否停止", 63, 1, 63, 6, 1);
				handleCell(sheet, row24, style1,  content3.charAt(content3.lastIndexOf("o")+1)=='1'?"是":" ", 63, 7, 63, 13, 7);
				

				String[] value3={
							         "2、管线是否泄压","p",
							         "3、接收方侧管线是否吹扫","q",
							         "4、与接收方相连管线是否已拆除","r",
							         "5、现场是否整理干净","s",
							         "6、记录是否完善","t"
					                 };
			   for (int z = 0; z < 5; z++) {
				   HSSFRow rowz = sheet.createRow((short) 64+z);
				   handleCell(sheet, rowz, style1,  value3[z*2], 64+z, 1, 64+z, 6, 1);
				   if(z==1||z==2){
					   handleCell(sheet, rowz, style1,  content3.charAt(content3.lastIndexOf(value3[z*2+1])+1)=='1'?"是":(content3.charAt(content3.lastIndexOf(value3[z*2+1])+1)=='3'?"不适用":" "), 64+z, 7, 64+z, 13, 7);
				   }else{
					   handleCell(sheet, rowz, style1,  content3.charAt(content3.lastIndexOf(value3[z*2+1])+1)=='1'?"是":" ", 64+z, 7, 64+z, 13, 7);  
				   }
			}
			
			   HSSFRow row42 = sheet.createRow((short) 69);
			   handleCell(sheet, row42, style1,  "作业人员:"+name3, 69, 0, 69, 6, 0);
			   handleCell(sheet, row42, style1,  "日期:"+time3, 69, 7, 69, 13, 7);
			    }
			    
		}else if("25".equals((String)request.getParameter("type"))){
			NotifyDto notifyDto=new NotifyDto();
			notifyDto.setCode(request.getParameter("code"));
			notifyDto.setTypes(12+"");
			//notifyDto.setIsList(1);
		    List<Map<String,Object>> list=notifyDao.getNotifyList(notifyDto, 0, 0);
		    Map<String,Object> map=list.get(0);
		    int state=Integer.valueOf(map.get("state").toString());
		    String content= map.get("content").toString();
		    String svg1= content.substring(content.indexOf("<svg"), content.indexOf("</svg>")+6);
	        String content1=" ";
		    String content2=" ";
		    String content3=" ";
            String name1="";
            String name2="";
            String name3="";
            String name4="";
            String name5="";
            String name6="";
            String time1="";
            String time2="";
            String time3="";

		   for (int i = 1; i < list.size(); i++) {
			if(list.get(i).get("contentType").toString().equals("1")){
				content1+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("2")){
					name1+=list.get(i).get("sureUserName").toString();
					time1+=list.get(i).get("sureTime").toString();
				}
				if(list.get(i).get("flag").toString().equals("1")){
					name4+=list.get(i).get("sureUserName").toString();
				}
			}else if(list.get(i).get("contentType").toString().equals("3")){
				content3+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("4")){
					name3+=list.get(i).get("sureUserName").toString();
					time3+=list.get(i).get("sureTime").toString();
				}
			}else if(list.get(i).get("contentType").toString().equals("2")){
				content2+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("3")){
					name2+=list.get(i).get("sureUserName").toString();
					time2+=list.get(i).get("sureTime").toString();
				}
				if(list.get(i).get("flag").toString().equals("1")){
					if(list.get(i).get("content").toString().charAt(0)=='t'){
						name5+=list.get(i).get("sureUserName").toString();	
					}
                    if(list.get(i).get("content").toString().charAt(0)=='u'){
                    	name6+=list.get(i).get("sureUserName").toString();
					}
				}
			}
		}	    
    HSSFRow rowtitle = sheet.createRow((short) 0); 
	HSSFRow row0 = sheet.createRow((short) 2); 

	handleCell(sheet, rowtitle, style, "倒罐作业通知单", 0, 0, 1,13);
	
	handleCell(sheet, row0, style,  "日期:"+map.get("createTime").toString().substring(0,11), 2, 0, 2, 4,0);
    handleCell(sheet, row0, style,  "JL2303-3", 2, 5, 2, 8,5);
    handleCell(sheet, row0, style,  "编号:"+request.getParameter("code").toString().substring(1), 2, 9, 2, 13,9);
		    
			HSSFRow row = sheet.createRow((short) 3);
			handleCell(sheet, row, style, request.getParameter("t1"), 3, 0, 3,13);
			HSSFRow row1 = sheet.createRow((short) 4);
			handleCell(sheet, row1, style, request.getParameter("v1"), 4, 0, 13,13);
			
			HSSFRow row2 = sheet.createRow((short) 14);
			handleCell(sheet, row2, style, request.getParameter("t2"), 14, 0, 14,13);
			HSSFRow row3 = sheet.createRow((short) 15);
			handleCell(sheet, row3, style, "", 15, 0, 26,13);
			if(svg1!=null){
				svg1=svg1.replaceAll("\\\\\"", "\"");
                svg1 = svg1.substring(0,4)+" xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' "+svg1.substring(5);
                handleSVG(request, sheet, wb, svg1, 0, 15);
            }
		 	
			HSSFRow row4 = sheet.createRow((short) 27);
			handleCell(sheet, row4, style, request.getParameter("t3"), 27, 0, 27,13);
			HSSFRow row5 = sheet.createRow((short) 28);
			handleCell(sheet, row5, style, request.getParameter("v3"), 28, 0, 37,13);
			
			HSSFRow row7 = sheet.createRow((short) 38);
			handleCell(sheet, row7, style, "调度:"+map.get("sureUserName").toString(), 38, 0, 38,13);    

			
			HSSFRow row8 = sheet.createRow((short) 39);
			handleCell(sheet, row8, style1, "四、作业注意事项：", 39, 0, 39	,13);
			
			 HSSFRow row9 = sheet.createRow((short) 40);
			 handleCell(sheet, row9, style1,  
					  "1、正确佩戴劳保用品；\n"
                     +"2、使用防爆工具；\n"
                     +"3、使用防爆工具；\n"
                     +"4、作业中对管线的压力情况加强检查及时汇报调度；\n"
                     +"5、作业过程中做好对呼吸阀的检查工作\n"
                     +"6、倒罐结束后严格按照调度命令拆除管线", 40, 0, 45,13);
			 
			    HSSFRow row10 = sheet.createRow((short) 46);
			    handleCell(sheet, row10, style1, "五、作业过程中的风险分析及相关措施：", 46, 0, 46	,13);
					
			    HSSFRow row11 = sheet.createRow((short) 47);
				handleCell(sheet, row11, style,  "危害及潜在事件：", 47, 0, 47, 4, 0);
				handleCell(sheet, row11, style,  "主要后果", 47, 5, 47, 8, 5);
				handleCell(sheet, row11, style,  "安全措施", 47, 9, 47, 13, 9);
				
				String[] value={"1、个人防护用品不适用或未正确佩戴","人身伤害","正确佩戴使用的防护用品",
				         "2、未使用防爆工具","火灾爆发","严格按规定使用防爆工具",
				         "3、作业前管线未确认","开错阀门，造成串料","及时与调度联系，加强核对",
				         "4、作业过程中呼吸阀异常造成储罐憋压或抽瘪","损坏储罐，引发火灾爆炸","按要求做好呼吸阀的检查",
				         "5、打回流过程未及时复查，巡检","打回流时阀门，管线泄漏未能及时发现，造成安全隐患","在打回流过程中及时关注管线压力，按要求进行巡检",
				         "6、液位低引起泵异常振动引起泵或管线损坏","物料泄漏","加强作业过程中的巡回检查"
					      };
				
				for (int j = 0; j < 6; j++) {
					HSSFRow rowj = sheet.createRow((short) 48+j);
					handleCell(sheet, rowj, style1,  value[0+j*3], 48+j, 0, 48+j, 4, 0);
					handleCell(sheet, rowj, style1,  value[1+j*3], 48+j, 5, 48+j, 8, 5);
					handleCell(sheet, rowj, style1,  value[2+j*3], 48+j, 9, 48+j, 13, 9);
				}
				if(state==4){
				HSSFRow row12 = sheet.createRow((short) 54);
			    handleCell(sheet, row12, style1, "六、作业过程的检查", 54, 0, 54	,13);
			    
			    sheet.setColumnWidth(0,700);
			    
				HSSFRow row22 = sheet.createRow((short) 55);
				handleCell(sheet, row22, style1, "作业前检查", 55, 0, 64,0);
				handleCell(sheet, row22, style1,  "1.高位报警和联锁是否测试正常", 55, 1, 55, 6, 1);
				handleCell(sheet, row22, style1,  content1.charAt(content1.lastIndexOf("b")+1)=='1'?"是":" ", 55, 7, 55, 11, 7);
				handleCell(sheet, row22, style1,  name4, 55, 12, 55, 13, 12);
				
				String[] value1={
				         "2.个人防护用品是否正确佩戴","c",
				         "3.现场流程是否和调度指令一致","d",
				         "4.储罐,管线法兰处是否无渗漏","e",
				         "5.是否释放身体静电","f",
				         "6.使用软管的品种是否匹配","g",
				         "7.垫圈是否正确放置","h",
				         "8.螺丝是否上全无滴漏","i",
				         "9.是否排污口倒空","j",
				         "10.若是排污口倒空，储罐阀门及泄压阀是否关闭","k",
				          };
				
				for (int x = 0; x <9; x++) {
					HSSFRow rowx = sheet.createRow((short) 56+x);
					handleCell(sheet, rowx, style1,  value1[x*2], 56+x, 1, 56+x, 6, 1);
					if(x==8){
						handleCell(sheet, rowx, style1,  content1.charAt(content1.lastIndexOf(value1[x*2+1])+1)=='1'?"是":(content1.charAt(content1.lastIndexOf(value1[x*2+1]))=='3'?"不适用":" "), 56+x, 7, 56+x, 13, 7);
					}else{
						handleCell(sheet, rowx, style1,  content1.charAt(content1.lastIndexOf(value1[x*2+1])+1)=='1'?"是":" ", 56+x, 7, 56+x, 13, 7);
					}
					
					HSSFRow row40 = sheet.createRow((short) 65);
					handleCell(sheet, row40, style1,  "作业人员:"+name1, 65, 0, 65, 6, 0);
					handleCell(sheet, row40, style1,  "日期:"+time1, 65, 7, 65, 13, 7);
				
					HSSFRow row23 = sheet.createRow((short) 66);
					handleCell(sheet, row23, style1, "作业中检查", 66, 0, 71,0);
					handleCell(sheet, row23, style1,  "1.管线压力是否正常", 66, 1, 66, 6, 1);
					handleCell(sheet, row23, style1,  content2.charAt(content2.lastIndexOf("p")+1)=='1'?"是":" ", 66, 7, 66, 13, 7);
					
					String[] value2={
					         "2.软管是否正常无颤动","q",
					         "3.呼吸阀是否工作正常","r",
					         "4.泵运行是否正常","s",
					         "5.中控室SCADA系统液位是否正常","t",
					         "6.中控室SCADA系统流量是否正常","u"
			                 };
					
					for (int y = 0; y < 5; y++) {
						HSSFRow rowy = sheet.createRow((short) 67+y);
						handleCell(sheet, rowy, style1,  value2[y*2], 67+y, 1, 67+y, 6, 1);
						if(y==3){
							handleCell(sheet, rowy, style1,  content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='1'?"是":(content2.charAt(content2.lastIndexOf(value2[y*2+1]))=='3'?"不适用":" "), 67+y, 7, 67+y, 11, 7);
							handleCell(sheet, rowy, style1,  name5, 67+y, 12, 67+y, 13, 12);
						}else{
							if(y==4){
								handleCell(sheet, rowy, style1,  content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='1'?"是":" ", 67+y, 7, 67+y, 11, 7);
								handleCell(sheet, rowy, style1,  name6, 67+y, 12, 67+y, 13, 12);
							}else{
								handleCell(sheet, rowy, style1,  content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='1'?"是":" ", 67+y, 7, 67+y, 13, 7);
							}
						}
					}
					
					HSSFRow row42 = sheet.createRow((short) 72);
					handleCell(sheet, row42, style1,  "作业人员:"+name2, 72, 0, 72, 6, 0);
					handleCell(sheet, row42, style1,  "日期:"+time2, 72, 7, 72, 13, 7);
				
					HSSFRow row24 = sheet.createRow((short) 73);
					handleCell(sheet, row24, style1, "作业中后查", 73, 0, 77,0);
					handleCell(sheet, row24, style1,  "1.结束后是否按调度命令泄压", 73, 1, 73, 6, 1);
					handleCell(sheet, row24, style1,  content3.charAt(content3.lastIndexOf("v")+1)=='1'?"是":" ", 73, 7, 73, 13, 7);
					
					String[] value3={
					         "2.作业结束现场是否整理干净","w",
					         "3.作业结束阀门，管线，储罐是否正常","x",
					         "4.拆管物料是否回收","y",
					         "5.记录是否完善","z"
						                 };
				   for (int z = 0; z < 4; z++) {
					   HSSFRow rowz = sheet.createRow((short) 74+z);
					   handleCell(sheet, rowz, style1,  value3[z*2], 74+z, 1, 74+z, 6, 1);
					   handleCell(sheet, rowz, style1,  content3.charAt(content3.lastIndexOf(value3[z*2+1])+1)=='1'?"是":" ", 74+z, 7, 74+z, 13, 7);
				 }
				
				HSSFRow row43 = sheet.createRow((short) 78);
				handleCell(sheet, row43, style1,  "作业人员:"+name3, 78, 0, 78, 6, 0);
				handleCell(sheet, row43, style1,  "日期:"+time3, 78, 7, 78, 13, 7);
				}
				}	
				
			
			
		}else if("26".equals((String)request.getParameter("type"))||"27".equals((String)request.getParameter("type"))){
			NotifyDto notifyDto=new NotifyDto();
			notifyDto.setCode(request.getParameter("code"));
			notifyDto.setTypes("13,14");
			List<Map<String,Object>> list=notifyDao.getNotifyList(notifyDto, 0, 0);
			Map<String,Object> map=list.get(0);
			int state=Integer.valueOf(map.get("state").toString());
			  String content1=" ";
			    String content2=" ";
			    String content3=" ";
	            String name1="";
	            String name2="";
	            String name3="";
	            String name4="";
	            String name5="";
	            String name6="";
	            String time1="";
	            String time2="";
	            String time3="";

			   for (int i = 1; i < list.size(); i++) {
				if(list.get(i).get("contentType").toString().equals("1")){
					content1+=list.get(i).get("content").toString();
					if(list.get(i).get("state").toString().equals("2")){
						name1+=list.get(i).get("sureUserName").toString();
						time1+=list.get(i).get("sureTime").toString();
					}
					if(list.get(i).get("flag").toString().equals("1")){
						name4+=list.get(i).get("sureUserName").toString();
					}
				}else if(list.get(i).get("contentType").toString().equals("3")){
					content3+=list.get(i).get("content").toString();
					if(list.get(i).get("state").toString().equals("4")){
						name3+=list.get(i).get("sureUserName").toString();
						time3+=list.get(i).get("sureTime").toString();
					}
					if(list.get(i).get("flag").toString().equals("1")){
						if(list.get(i).get("content").toString().charAt(0)=='s'){
							name5+=list.get(i).get("sureUserName").toString();	
						}
	                    if(list.get(i).get("content").toString().charAt(0)=='t'){
	                    	name6+=list.get(i).get("sureUserName").toString();
						}
					}
				}else if(list.get(i).get("contentType").toString().equals("2")){
					content2+=list.get(i).get("content").toString();
					if(list.get(i).get("state").toString().equals("3")){
						name2+=list.get(i).get("sureUserName").toString();
						time2+=list.get(i).get("sureTime").toString();
					}
				}
			}
			/*NotifyDto notifyDto=new NotifyDto();
			notifyDto.setCode(request.getParameter("code"));
			notifyDto.setTypes("13,14");
			 List<Map<String,Object>> list=notifyDao.getNotifyList(notifyDto, 0, 0);
			 Map<String,Object> map=list.get(0);
			 int state=Integer.valueOf(map.get("state").toString());
			    
			    String content1=" ";
			    String content2=" ";
			    String content3=" ";
	            String name1="";
	            String name2="";
	            String name3="";
	            String name4="";
	            String name5="";
	            String name6="";
	            String time1="";
	            String time2="";
	            String time3="";

			   for (int i = 1; i < list.size(); i++) {
				if(list.get(i).get("contentType").toString().equals("1")){
					content1+=list.get(i).get("content").toString();
					if(list.get(i).get("state").toString().equals("2")){
						name1+=list.get(i).get("sureUserName").toString();
						time1+=list.get(i).get("sureTime").toString();
					}
					if(list.get(i).get("flag").toString().equals("1")){
						name4+=list.get(i).get("sureUserName").toString();
					}
				}else if(list.get(i).get("contentType").toString().equals("3")){
					content3+=list.get(i).get("content").toString();
					if(list.get(i).get("state").toString().equals("4")){
						name3+=list.get(i).get("sureUserName").toString();
						time3+=list.get(i).get("sureTime").toString();
					}
					if(list.get(i).get("flag").toString().equals("1")){
						if(list.get(i).get("content").toString().charAt(0)=='s'){
							name5+=list.get(i).get("sureUserName").toString();	
						}
	                    if(list.get(i).get("content").toString().charAt(0)=='t'){
	                    	name6+=list.get(i).get("sureUserName").toString();
						}
					}
				}else if(list.get(i).get("contentType").toString().equals("2")){
					content2+=list.get(i).get("content").toString();
					if(list.get(i).get("state").toString().equals("3")){
						name2+=list.get(i).get("sureUserName").toString();
						time2+=list.get(i).get("sureTime").toString();
					}
				}
			}*/
			   String path=null;
			   if("26".equals((String)request.getParameter("type"))){
				       path =request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/transportProgramNotify(Wharf).xls"; 
					}else{
					   path =request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/transportProgramNotify(Power).xls"; 
					}
		    File fi=new File(path); 
	        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fi));  
	        wb = new HSSFWorkbook(fs); 
	        sheet = wb.getSheetAt(0);   
	        if(map.get("createTime")!=null){
				String time=map.get("createTime").toString();
				sheet.getRow(1).getCell(1).setCellValue("日期:"+time.substring(0, 4)+"年"+time.substring(5, 7)+"月"+time.substring(8, 10)+"日");
			}
	        sheet.getRow(1).getCell(3).setCellValue("编号："+request.getParameter("code").toString().substring(1));
	        sheet.getRow(2).getCell(0).setCellValue("一、"+request.getParameter("t1")+"："+request.getParameter("v1"));
	        String svg = "" ;
            if(request.getParameter("v2")!=null&&request.getParameter("v2")!=""){
            	 Map<String,Object> map1 = shipDeliverWorkDao.getTranspInfoById(request.getParameter("v2")) ;
                 svg = (String)map1.get("svg") ;
                 if(svg!=null&&svg.length()>20){
                 svg = svg.substring(0,4)+" xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' "+svg.substring(5) ;
                 handleSVGother(request, sheet, wb,svg , 0, 4,700,320);
                 }
            }
            sheet.getRow(5).getCell(0).setCellValue("三、"+request.getParameter("t3")+"："+request.getParameter("v3"));
            sheet.getRow(20).getCell(3).setCellValue("调度确认："+map.get("sureUserName").toString());
          
            if("26".equals((String)request.getParameter("type"))){
            sheet.getRow(31).getCell(5).setCellValue("调度或码头操作工确认："+name4);
            sheet.getRow(39).getCell(5).setCellValue(name5);
            String[] value1={"b","c","d","e","f","g","h","i","j","k","l","m","n","o"};
            for (int  x= 0; x < value1.length; x++) {
            	if(content1.lastIndexOf(value1[x])!=-1){
            		sheet.getRow(21+x).getCell(3).setCellValue(content1.charAt(content1.lastIndexOf(value1[x])+1)=='1'?"是":(content1.charAt(content1.lastIndexOf(value1[x])+1)=='3'?"不适用":" "));
            	}
            }
            String[] value2={"p","q","r"};
            for (int y = 0; y < value2.length; y++) {
            	if(content2.lastIndexOf(value2[y])!=-1){
            		sheet.getRow(35+y).getCell(3).setCellValue(content2.charAt(content2.lastIndexOf(value2[y])+1)=='1'?"是":(content2.charAt(content2.lastIndexOf(value2[y])+1)=='3'?"不适用":" "));
            	}
            }
            String[] value3={"s","t","u","v","w","x","y"};
            for (int z = 0; z < value3.length; z++) {
            	if(content3.lastIndexOf(value3[z])!=-1){
            		sheet.getRow(38+z).getCell(3).setCellValue(content3.charAt(content3.lastIndexOf(value3[z])+1)=='1'?"是":(content3.charAt(content3.lastIndexOf(value3[z])+1)=='3'?"不适用":" "));
            	}
            }
            sheet.getRow(45).getCell(1).setCellValue("作业人员:"+name3);
            sheet.getRow(45).getCell(3).setCellValue( "日期:"+time3);
            }else{
            	 String[] value1={"a","b","c","d","e","f"};
                 for (int  x= 0; x < value1.length; x++) {
                 	if(content1.lastIndexOf(value1[x])!=-1){
                 		sheet.getRow(20+x).getCell(2).setCellValue(content1.charAt(content1.lastIndexOf(value1[x])+1)=='1'?"是":(content1.charAt(content1.lastIndexOf(value1[x])+1)=='3'?"不适用":" "));
                 	}
                 }
                 String[] value2={"h","i","j","k","l"};
                 for (int y = 0; y < value2.length; y++) {
                 	if(content2.lastIndexOf(value2[y])!=-1){
                 		sheet.getRow(26+y).getCell(2).setCellValue(content2.charAt(content2.lastIndexOf(value2[y])+1)=='1'?"是":(content2.charAt(content2.lastIndexOf(value2[y])+1)=='3'?"不适用":" "));
                 	}
                 }
                 String[] value3={"m","n","o","p"};
                 for (int z = 0; z < value3.length; z++) {
                 	if(content3.lastIndexOf(value3[z])!=-1){
                 		sheet.getRow(31+z).getCell(2).setCellValue(content3.charAt(content3.lastIndexOf(value3[z])+1)=='1'?"是":(content3.charAt(content3.lastIndexOf(value3[z])+1)=='3'?"不适用":" "));
                 	}
                 }
                 sheet.getRow(36).getCell(1).setCellValue("作业人员:"+name3);
                 sheet.getRow(36).getCell(3).setCellValue( "日期:"+time3);
            	
            }
           
		    /*
			    HSSFRow rowtitle = sheet.createRow((short) 0); 
				HSSFRow row0 = sheet.createRow((short) 2); 

				if("26".equals((String)request.getParameter("type"))){
				handleCell(sheet, rowtitle, style, "接卸作业通知单（码头）", 0, 0, 1,13);
				}else{
				handleCell(sheet, rowtitle, style, "接卸作业通知单（动力班）", 0, 0, 1,13);	
				}
				
	            handleCell(sheet, row0, style,  "日期:"+map.get("createTime").toString().substring(0,11), 2, 0, 2, 6,0);
				handleCell(sheet, row0, style,  "编号:"+request.getParameter("code").toString().substring(1), 2, 7, 2, 13,7);

			   
			  
			
			HSSFRow row = sheet.createRow((short) 3);
			handleCell(sheet, row, style, request.getParameter("t1"), 3, 0, 3,13);
			HSSFRow row1 = sheet.createRow((short) 4);
			
			handleCell(sheet, row1, style, request.getParameter("v1"), 4, 0, 13,13);
			
			HSSFRow row2 = sheet.createRow((short) 14);
			handleCell(sheet, row2, style, request.getParameter("t2"), 14, 0, 14,13);
			HSSFRow row3 = sheet.createRow((short) 15);
			handleCell(sheet, row3, style, "", 15, 0, 33,13);
			String svg = "" ;
            if(request.getParameter("v2")!=null&&request.getParameter("v2")!=""){
            	 Map<String,Object> map1 = shipDeliverWorkDao.getTranspInfoById(request.getParameter("v2")) ;
                 svg = (String)map1.get("svg") ;
                 if(svg!=null&&svg.length()>20){
                 svg = svg.substring(0,4)+" xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' "+svg.substring(5) ;
                 handleSVGother(request, sheet, wb,svg , 0, 15,880,320);
                 }
            }
			
			HSSFRow row4 = sheet.createRow((short) 34);
			handleCell(sheet, row4, style, request.getParameter("t3"), 34, 0, 34,13);
			HSSFRow row5 = sheet.createRow((short) 35);
			handleCell(sheet, row5, style, request.getParameter("v3"), 35, 0, 37,13);
			
			HSSFRow row7 = sheet.createRow((short) 38);
			handleCell(sheet, row7, style, "调度:"+map.get("sureUserName").toString(), 38, 0, 38,13);    
			
			HSSFRow row6 = sheet.createRow((short) 39);
			handleCell(sheet, row6, style, "四、作业注意事项：", 39, 0, 39,13);
			
			if("26".equals((String)request.getParameter("type"))){
				
				 HSSFRow row9 = sheet.createRow((short) 40);
				 handleCell(sheet, row9, style1,  
						  "1、正确佩戴个人防护用品；\n"
                         +"2、加强船岸检查；\n"
                         +"3、关注船舶干弦；\n"
                         +"4、注意风力变化及关注潮水及缆绳情况。", 40, 0, 43,13);
				 
				    HSSFRow row10 = sheet.createRow((short) 44);
					handleCell(sheet, row10, style1, "五、作业中的风险分析：", 44, 0, 44	,13);
						
					HSSFRow row11 = sheet.createRow((short) 45);
					handleCell(sheet, row11, style,  "危害及潜在事件：", 45, 0, 45, 4, 0);
					handleCell(sheet, row11, style,  "主要后果", 45, 5, 45, 8, 5);
					handleCell(sheet, row11, style,  "安全措施", 45, 9, 45, 13, 9);
					
					String[] value={"1、未按规定佩戴劳动防护用品","人身伤害","正确佩戴个人防护用品",
					         "2、带缆作业时未穿戴救生衣","人员坠落江里，造成人员伤害","严格执行规定穿戴救生衣",
					         "3、起重机故障造成软管损坏，引起物料泄漏","污染环境","按照操作规程、加强检查",
					         "4、未正确进行接管作业","物料渗漏污染水体或发生火灾、影响货物品质","按照规程正确操作，加强核对及确认",
					         "5、作业前未与调度及船方及时沟通","阀门未正确开启就开始卸货，造成管线涨压泄漏或船泵故障，而造成事故","按照规程正确操作，加强核对及确认",
					         "6、作业过程中人员脱岗，软管未及时调整而造成挤压、拉断，引起物料泄漏","污染环境、引发火灾爆炸","严格执行劳动纪律，通过检查加强现场监管",
					         "7、压力超过工作压力，导致软管破裂，引起物料泄漏","污染环境、引发火灾爆炸","现场值班人员加强检查，调度人员通过SCADA系统进行监控",
					         "8、缆绳未及时调整，导致船舶移位或缆绳断裂，拉断软管，引起物料泄漏","污染环境、引发火灾爆炸","加强现场值班监管",
					         "9、结束后未及时关闭相关阀门和软管未封盲板","物料渗漏污染水体或发生火灾","按照规程正确操作，加强核对及确认",
					         "10、作业结束未及时清理管线内物料，吊装软管时物料洒落","污染环境、人身伤害","作业结束及时清理物料"
						            };
					
					for (int j = 0; j < 10; j++) {
						HSSFRow rowj = sheet.createRow((short) 46+j);
						handleCell(sheet, rowj, style1,  value[0+j*3], 46+j, 0, 46+j, 4, 0);
						handleCell(sheet, rowj, style1,  value[1+j*3], 46+j, 5, 46+j, 8, 5);
						handleCell(sheet, rowj, style1,  value[2+j*3], 46+j, 9, 46+j, 13, 9);
					}
					if(state==4){
					sheet.setColumnWidth(0,700);
					
					HSSFRow row22 = sheet.createRow((short) 56);
					handleCell(sheet, row22, style1, "作业前检查", 56, 0, 69,0);
					handleCell(sheet, row22, style1,  "1、软管是否适合拟装卸货物，其长度是否满足当日潮位差的要求", 56, 1, 56, 6, 1);
					handleCell(sheet, row22, style1,  content1.charAt(content1.lastIndexOf("b")+1)=='1'?"是":" ", 56, 7, 56, 13, 7);
					
					String[] value1={
					         "2、软管是否做过压力测试，其额定的工作压力是否满足作业压力要求","c",
					         "3、与作业相关的区域是否实施动火及施工管制的检查","d",
					         "4、放置消防器材，检查消防系统是否处于随时使用状态","e",
					         "5、通讯系统是否正常","f",
					         "6、人员保护器材检查是否良好可用","g",
					         "7、检查作业船舶周围水域环境是否良好","h",
					         "8、检查防污设施，须布设围油栏的，通知围油栏公司进行布设","i",
					         "9、检查码头围堰与船舶甲板排水口是否有效堵塞并保持常关","j",
					         "10、检查作业船舶停靠位置是否正确","k",
					         "11、按照《船/岸检查表》的内容与船方进行各项检查并签字。商定卸货程序","l",
					         "12、检查装卸物料接口处围油堰的所有孔是否已堵塞","m",
					         "13、检查码头作业人员是否已触摸人体静电释放装置，泄放人体静电","n",
					         "14、船岸相关管线及阀门开启是否正确","o"
			                 };
			
			for (int x = 0; x <13; x++) {
				HSSFRow rowx = sheet.createRow((short) 57+x);
				handleCell(sheet, rowx, style1,  value1[x*2], 57+x, 1, 57+x, 6, 1);
				if(x==6){
				    handleCell(sheet, rowx, style1,  content1.charAt(content1.lastIndexOf(value1[x*2+1])+1)=='1'?"是":(content1.charAt(content1.lastIndexOf(value1[x*2+1]))=='3'?"不适用":" "), 57+x, 7, 57+x, 13, 7);	
				}else{
					if(x==9){
						handleCell(sheet, rowx, style1,  content1.charAt(content1.lastIndexOf(value1[x*2+1])+1)=='1'?"是":" ", 57+x, 7, 57+x, 11, 7);	
						handleCell(sheet, rowx, style1,  name4, 57+x, 12, 57+x, 13, 12);
					}else{
						handleCell(sheet, rowx, style1,  content1.charAt(content1.lastIndexOf(value1[x*2+1])+1)=='1'?"是":" ", 57+x, 7, 57+x, 13, 7);
					}
				}
			}
				
			HSSFRow row21 = sheet.createRow((short) 70);
			handleCell(sheet, row21, style1,  "作业人员:"+name1, 70, 0, 70, 6, 0);
			handleCell(sheet, row21, style1,  "日期:"+time1, 70, 7, 70, 13, 7);
					
                    HSSFRow row36 = sheet.createRow((short) 71);
					handleCell(sheet, row36, style1, "作业中检查", 71, 0, 73,0);
					handleCell(sheet, row36, style1,  "1、卸货压力是否正常", 71, 1, 71, 6, 1);
					handleCell(sheet, row36, style1,  content2.charAt(content2.lastIndexOf("p")+1)=='1'?"是":" ", 71, 7, 71, 13, 7);
					
					HSSFRow row37 = sheet.createRow((short) 72);
					handleCell(sheet, row37, style1,  "2、相关管线及阀门是否有跑冒滴漏", 72, 1, 72, 6, 1);
					handleCell(sheet, row37, style1,  content2.charAt(content2.lastIndexOf("q")+1)=='1'?"是":" ", 72, 7, 72, 13, 7);
					
					HSSFRow row38 = sheet.createRow((short) 73);
					handleCell(sheet, row38, style1,  "3、软管及缆绳是否及时调整", 73, 1, 73, 6, 1);
					handleCell(sheet, row38, style1,  content2.charAt(content2.lastIndexOf("r")+1)=='1'?"是":" ", 73, 7, 73, 13, 7);
					
					HSSFRow row20 = sheet.createRow((short) 74);
					handleCell(sheet, row20, style1,  "作业人员:"+name2, 74, 0, 74, 6, 0);
					handleCell(sheet, row20, style1,  "日期:"+time2, 74, 7, 74, 13, 7);
					
					HSSFRow row39 = sheet.createRow((short) 75);
					handleCell(sheet, row39, style1, "作业后检查", 75, 0, 80,0);
					handleCell(sheet, row39, style1,  "1、船方管线是否经氮气吹扫（包括软管）", 75, 1, 75, 6, 1);
					if(content3.lastIndexOf("s")!=-1){
					handleCell(sheet, row39, style1,  content3.charAt(content3.lastIndexOf("s")+1)=='1'?"是":(content3.charAt(content3.lastIndexOf("s"))=='3'?"不适用":" "), 75, 7, 75, 11, 7);
					handleCell(sheet, row39, style1,  name5, 75, 12, 75, 13, 12);
					}else{
						handleCell(sheet, row39, style1,  name5, 75, 7, 75, 13, 12);
					}
					
					
					String[] value3={"2、是否进行过打回流操作或压管线操作","t",
					         "3、管线阀门是否已经关闭 （包括吹扫口）","v",
					         "4、软管是否已经正确封堵盲板并及时复位","u",
					         "5、物料是否及时回收入库并正确标识","w",
					         "6、现场是否整理干净 ","x",
					         "7、记录是否完善","y"
			                 };
	   for (int z = 0; z < 6; z++) {
		   HSSFRow rowz = sheet.createRow((short) 76+z);
		   handleCell(sheet, rowz, style1,  value3[z*2], 76+z, 1, 76+z, 6, 1);
		   if(z==0){
			   if(content3.lastIndexOf(value3[z*2+1])!=-1){
			   handleCell(sheet, rowz, style1,  content3.charAt(content3.lastIndexOf(value3[z*2+1])+1)=='1'?"是":" ", 76+z, 7, 76+z, 11, 7);
			   handleCell(sheet, rowz, style1,  name6, 76+z, 12, 76+z, 13, 12);
			   }else{
			   handleCell(sheet, rowz, style1,  "", 76+z, 7, 76+z, 13, 12);   
			   }
		   }else{
			   handleCell(sheet, rowz, style1,  content3.charAt(content3.lastIndexOf(value3[z*2+1])+1)=='1'?"是":" ", 76+z, 7, 76+z, 13, 7);
		   }
	 }
					
	   HSSFRow row24 = sheet.createRow((short) 81);
	   handleCell(sheet, row24, style1,  "作业人员:"+name3, 81, 0, 81, 6, 0);
	   handleCell(sheet, row24, style1,  "日期:"+time3, 81, 7, 81, 13, 7);
					}
					
				}else{
					
					HSSFRow row9 = sheet.createRow((short) 40);
					handleCell(sheet, row9, style1,  
							  "1、正确佩戴个人防护用品；\n"
	                         +"2、储罐应开进口阀，泄压阀应关闭；\n"
	                         +"3、卸货时检查罐呼吸阀是否正常，如有异常及时与调度联系；\n"
	                         +"4、卸货过程中加强检查，及时与调度联系。", 40, 0, 43,13);	
					
					HSSFRow row10 = sheet.createRow((short) 44);
					handleCell(sheet, row10, style1, "五、作业中的风险分析：", 44, 0, 44	,13);
						
					HSSFRow row11 = sheet.createRow((short) 45);
					handleCell(sheet, row11, style,  "危害及潜在事件：", 45, 0, 45, 4, 0);
					handleCell(sheet, row11, style,  "主要后果", 45, 5, 45, 8, 5);
					handleCell(sheet, row11, style,  "安全措施", 45, 9, 45, 13, 9);
					
					String[] value={"1、未按规定佩戴劳动防护用品","人身伤害","正确佩戴个人防护用品",
					         "2、管道腐蚀、垫片损坏造成物料泄漏","污染环境、引发火灾爆炸","加强巡检、系统报警时及时查明原因",
					         "3、未正确启闭阀门，造成管线憋压损坏引起物料泄漏","污染环境、引发火灾爆炸","根据调度作业通知单正确启闭阀门，加强核对",
					         "4、开错阀门造成物料串罐","造成冒罐引发火灾爆炸","根据调度作业通知单正确启闭阀门，加强核对",
					         "5、未正确开启储罐阀门和关闭相关的泄压阀","阀门渗漏及泄压阀被异物堵塞","正确开启相关阀门及关闭相关的泄压阀",
					         "6、进料时未及时复查、进货过程中未保持巡查","进料时阀门和管线泄漏未能及时发现，而造成事故","在进料后进行相关储罐、管线的复检，并在进货时保持巡查",
					         "7、上罐检查时人员跌到、滑落、碰撞致伤","人身伤害","上罐时手扶扶梯",
					         "8、作业过程中呼吸阀异常造成储罐憋压","储罐胀裂物料泄漏","按规定在作业过程中对呼吸进行检查",
					         "9、结束后未及时关闭相关储罐阀门和及时开启相关的泄压阀","物料串罐或管线涨压","结束后及时与调度联系，关闭相关的储罐阀门，及时开启相应的泄压阀"
						            };
					
					for (int j = 0; j < 9; j++) {
						HSSFRow rowj = sheet.createRow((short) 46+j);
						handleCell(sheet, rowj, style1,  value[0+j*3], 46+j, 0, 46+j, 4, 0);
						handleCell(sheet, rowj, style1,  value[1+j*3], 46+j, 5, 46+j, 8, 5);
						handleCell(sheet, rowj, style1,  value[2+j*3], 46+j, 9, 46+j, 13, 9);
					}
					if(state==4){
					sheet.setColumnWidth(0,700);
					
					HSSFRow row21 = sheet.createRow((short) 55);
					handleCell(sheet, row21, style1, "作业前检查", 55, 0, 59,0);
					handleCell(sheet, row21, style1,  "1、高位报警及阀门连锁测试是否正常", 55, 1, 55, 6, 1);
					handleCell(sheet, row21, style1,  content1.charAt(content1.lastIndexOf("a")+1)=='1'?"是":" ", 55, 7, 55, 11, 7);
					handleCell(sheet, row21, style1,  name4, 55, 12, 55, 13, 12);
					
					String[] value1={
					         "2、消防泵处于正常备用状态，检查确认消防设备、设施处于正常运行状态","b",
					         "3、确认消防管线内消防水压力处于稳压状态，所有阀位正确","c",
					         "4、通讯系统（对讲机、电话）处于良好状态，巡检工具准备完好","d",
					         "5、相关储罐及管线连接是否正确","e",
					         "6、相关储罐及管线的阀门开启是否正确","f"
			                 };
			
			for (int x = 0; x <5; x++) {
				HSSFRow rowx = sheet.createRow((short) 56+x);
				handleCell(sheet, rowx, style1,  value1[x*2], 56+x, 1, 56+x, 6, 1);
				handleCell(sheet, rowx, style1,  content1.charAt(content1.lastIndexOf(value1[x*2+1])+1)=='1'?"是":" ", 56+x, 7, 56+x, 13, 7);
			}
					
			HSSFRow row12 = sheet.createRow((short) 61);
			handleCell(sheet, row12, style1,  "作业人员:"+name1, 61, 0, 61, 6, 0);
			handleCell(sheet, row12, style1,  "日期:"+time1, 61, 7, 61, 13, 7);
					
					HSSFRow row27 = sheet.createRow((short) 62);
					handleCell(sheet, row27, style1, "作业中检查", 62, 0, 66,0);
					handleCell(sheet, row27, style1,  "1、对罐区管线、低位排污、配管站、储罐呼吸阀等主要部位进行检查是否正常", 62, 1, 62, 6, 1);
					handleCell(sheet, row27, style1,   content2.charAt(content2.lastIndexOf("h")+1)=='1'?"是":" ", 62, 7, 62, 13, 7);
					
					String[] value2={"2、相关管线及阀门是否有跑冒滴漏","i",
					         "3、新封罐，进货后是否每隔半小时对人孔及排污口处进行检查？是否正常","j",
					         "4、相关管线上的压力表显示压力是否正常","k",
					         "5、如浮顶罐首次进料，浮盘是否已正常起升","l"
			                 };
					
					for (int y = 0; y < 4; y++) {
						HSSFRow rowx = sheet.createRow((short) 63+y);
						handleCell(sheet, rowx, style1,  value2[y*2], 63+y, 1, 63+y, 6, 1);
						if(y==0){
							handleCell(sheet, rowx, style1,  content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='1'?"是":" ", 63+y, 7, 63+y, 13, 7);	
						}else{
							handleCell(sheet, rowx, style1,  content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='1'?"是":(content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='3'?"不适用":" "), 63+y, 7, 63+y, 13, 7);
						}
						
					}
					
					HSSFRow row13 = sheet.createRow((short) 67);
					handleCell(sheet, row13, style1,  "作业人员:"+name2, 67, 0, 67, 6, 0);
					handleCell(sheet, row13, style1,  "日期:"+time2, 67, 7, 67, 13, 7);
					
					HSSFRow row32 = sheet.createRow((short) 68);
					handleCell(sheet, row32, style1, "作业后检查", 68, 0, 71,0);
					handleCell(sheet, row32, style1,  "1、储罐是否已结束处理", 68, 1, 68, 6, 1);
					handleCell(sheet, row32, style1,  content3.charAt(content3.lastIndexOf("m")+1)=='1'?"是":" ", 68, 7, 68, 13, 7);
					
					HSSFRow row33 = sheet.createRow((short) 69);
					handleCell(sheet, row33, style1,  "2、相关的泄压阀是否已打开", 69, 1, 69, 6, 1);
					handleCell(sheet, row33, style1,  content3.charAt(content3.lastIndexOf("n")+1)=='1'?"是":" ", 69, 7, 69, 13, 7);
					
					HSSFRow row34 = sheet.createRow((short) 70);
					handleCell(sheet, row34, style1,  "3、储罐人孔等法兰连接处是否无物料滴漏", 70, 1, 70, 6, 1);
					handleCell(sheet, row34, style1,  content3.charAt(content3.lastIndexOf("o")+1)=='1'?"是":" ", 70, 7, 70, 13, 7);
					
					HSSFRow row35 = sheet.createRow((short) 71);
					handleCell(sheet, row35, style1,  "4、记录是否完善", 71, 1, 71, 6, 1);
					handleCell(sheet, row35, style1,  content3.charAt(content3.lastIndexOf("p")+1)=='1'?"是":" ", 71, 7, 71, 13, 7);
					
					HSSFRow row14 = sheet.createRow((short) 72);
					handleCell(sheet, row14, style1,  "作业人员:"+name3, 72, 0, 72, 6, 0);
					handleCell(sheet, row14, style1,  "日期:"+time3, 72, 7, 72, 13, 7);
					}
					
				}
			*/
			
		}
		else if("28".equals((String)request.getParameter("type"))){
			 
			NotifyDto notifyDto=new NotifyDto();
			notifyDto.setCode(request.getParameter("code"));
			notifyDto.setTypes("2,3");
			//notifyDto.setIsList(1);
		    List<Map<String,Object>> list=notifyDao.getNotifyList(notifyDto, 0, 0);
		    Map<String,Object> map=list.get(0);
		    int state=Integer.valueOf(map.get("state").toString());
		    String content1=" ";
		    String content2=" ";
		    String content3=" ";
            String name1="";
            String name2="";
            String name3="";
            String name4="";
            String name5="";
            String name6="";
            String time1="";
            String time2="";
            String time3="";

		   for (int i = 1; i < list.size(); i++) {
			if(list.get(i).get("contentType").toString().equals("1")){
				content1+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("2")){
					name1+=list.get(i).get("sureUserName").toString();
					time1+=list.get(i).get("sureTime").toString();
				}
				if(list.get(i).get("flag").toString().equals("1")){
					name4+=list.get(i).get("sureUserName").toString();
				}
			}else if(list.get(i).get("contentType").toString().equals("3")){
				content3+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("4")){
					name3+=list.get(i).get("sureUserName").toString();
					time3+=list.get(i).get("sureTime").toString();
				}
			}else if(list.get(i).get("contentType").toString().equals("2")){
				content2+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("3")){
					name2+=list.get(i).get("sureUserName").toString();
					time2+=list.get(i).get("sureTime").toString();
				}
				if(list.get(i).get("flag").toString().equals("1")){
					if(list.get(i).get("content").toString().charAt(0)=='e'){
						name5+=list.get(i).get("sureUserName").toString();
					}else{
						name6+=list.get(i).get("sureUserName").toString();
					}
				}
			}
		}
			
			HSSFRow rowtitle = sheet.createRow((short) 0); 
			HSSFRow row0 = sheet.createRow((short) 2); 
     
			 if((request.getParameter("code").charAt(0)) == 'C'){
				 handleCell(sheet, rowtitle, style, "打循环作业通知单（码头）", 0, 0, 1,13);
			    }else{
			     handleCell(sheet, rowtitle, style, "打循环作业通知单（库区）", 0, 0, 1,13);	
			    }
			
			 handleCell(sheet, row0, style,  "日期:"+map.get("createTime").toString().substring(0,11), 2, 0, 2, 4,0);
	         handleCell(sheet, row0, style,  "JL2303-2", 2, 5, 2, 8,5);
	         handleCell(sheet, row0, style,  "编号:"+request.getParameter("code").toString().substring(1), 2, 9, 2, 13,9);

			
			HSSFRow row = sheet.createRow((short) 3);
			handleCell(sheet, row, style, request.getParameter("t1"), 3, 0, 3,13);
			HSSFRow row1 = sheet.createRow((short) 4);
			handleCell(sheet, row1, style, request.getParameter("v1"), 4, 0, 13,13);
			
			HSSFRow row2 = sheet.createRow((short) 14);
			handleCell(sheet, row2, style, request.getParameter("t2"), 14, 0, 14,13);
			HSSFRow row3 = sheet.createRow((short) 15);
			handleCell(sheet, row3, style, "", 15, 0, 31,13);
			String svg = "" ;
            if(request.getParameter("v2")!=null&&request.getParameter("v2")!=""){
            	 Map<String,Object> map1 = shipDeliverWorkDao.getTranspInfoById(request.getParameter("v2")) ;
                 svg = (String)map1.get("svg") ;
                 if(svg!=null&&svg.length()>20){
                 svg = svg.substring(0,4)+" xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' "+svg.substring(5) ;
                 handleSVG(request, sheet, wb, svg, 0, 15);
                 }
            }else if(request.getParameter("code")!=null&&request.getParameter("code")!=""){
            	if(map.get("firPFDSvg")!=null){
   		        String svg1= map.get("firPFDSvg").toString();
    		    svg1 = svg1.substring(0,4)+" xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' "+svg1.substring(5) ;
    		    handleSVG(request, sheet, wb, svg1, 0, 15);
    		    }
            }
            
            HSSFRow row7 = sheet.createRow((short) 32);
			handleCell(sheet, row7, style, "调度:"+map.get("sureUserName").toString(), 32, 0, 32,13);
            
            HSSFRow row8 = sheet.createRow((short) 33);
            handleCell(sheet, row8, style1, "三、作业注意事项：", 33, 0, 33, 13);
            
            HSSFRow row9 = sheet.createRow((short) 34);
			handleCell(sheet, row9, style1,  
		     		  "1、正确佩戴个人防护用品；\n"
                     +"2、注意管线压力变化；\n"
                     +"3、检查管线阀门状态是否正确\n"
                     +"4、做好储罐附件及管线沿线的检查", 34, 0, 37,13);
			
			    HSSFRow row10 = sheet.createRow((short) 38);
				handleCell(sheet, row10, style1, "四、作业中的风险分析：", 38, 0, 38	,13);
					
				HSSFRow row11 = sheet.createRow((short) 39);
				handleCell(sheet, row11, style,  "危害及潜在事件：", 39, 0, 39, 4, 0);
				handleCell(sheet, row11, style,  "主要后果", 39, 5, 39, 8, 5);
				handleCell(sheet, row11, style,  "安全措施", 39, 9, 39, 13, 9);
				
				String[] value={"1、未按规定佩戴劳动防护用品","人身伤害","正确佩戴个人防护用品",
				         "2、接错管线","影响品质、管线涨压","调度做好核对确认工作",
				         "3、吹扫口阀未关好或未封好","物料泄漏","开阀门前做好检查工作",
				         "4、阀门不严密造成物料泄漏","物料泄漏","做好巡回检查",
				         "5、管线腐蚀穿孔","物料泄漏","做好巡回检查",
				         "6、开错储罐阀门","发错物料、影响品质","认真按作业单进行操作，调度加强核对",
				         "7、呼吸阀故障","储罐损坏","作业过程中对呼吸阀工作情况进行检查",
				         "8、SCADA系统液位异常","无法监控液位、流量","和计量人员做好核对工作",
				         "9、泵运行故障","引发事故","做好保养、作业中加强检查",
				         "10、管线内气阻造成泵压力异常升高","损坏泵及管线引发事故","加强操作人员加强现场观察，有异常时及时汇报调度"
					            };
				
				for (int j = 0; j < 10; j++) {
					HSSFRow rowj = sheet.createRow((short) 40+j);
					handleCell(sheet, rowj, style1,  value[0+j*3], 40+j, 0, 40+j, 4, 0);
					handleCell(sheet, rowj, style1,  value[1+j*3], 40+j, 5, 40+j, 8, 5);
					handleCell(sheet, rowj, style1,  value[2+j*3], 40+j, 9, 40+j, 13, 9);
				}
				if(state==4){
				sheet.setColumnWidth(0,700);
				
				HSSFRow row22 = sheet.createRow((short) 50);
				handleCell(sheet, row22, style1, "作业前检查", 50, 0, 53,0);
				handleCell(sheet, row22, style1,  "1、个人防护用品是否正确佩戴", 50, 1, 50, 6, 1);
				handleCell(sheet, row22, style1,  content1.charAt(content1.lastIndexOf("a")+1)=='1'?"是":" ", 50, 7, 50, 13, 7);
				
				String[] value1={
				         "2、工艺流程是否正确","b",
				         "3、管线、阀门是否正常","c",
				         "4、SCADA系统是否正常","d"
				         };
		
		for (int x = 0; x <3; x++) {
			HSSFRow rowx = sheet.createRow((short) 51+x);
			handleCell(sheet, rowx, style1,  value1[x*2], 51+x, 1, 51+x, 6, 1);
			if(x==2){
				if(content1.lastIndexOf(value1[x*2+1])!=-1){
				handleCell(sheet, rowx, style1,  content1.charAt(content1.lastIndexOf(value1[x*2+1])+1)=='1'?"是":" ", 51+x, 7, 51+x, 11, 7);
				handleCell(sheet, rowx, style1,  name4, 51+x, 12, 51+x, 13, 12);
				}else{
				handleCell(sheet, rowx, style1,  "", 51+x, 7, 51+x, 13, 7);	
				}
			}else{
				handleCell(sheet, rowx, style1,  content1.charAt(content1.lastIndexOf(value1[x*2+1])+1)=='1'?"是":" ", 51+x, 7, 51+x, 13, 7);
			}
		}
				
				HSSFRow row46 = sheet.createRow((short) 54);
				handleCell(sheet, row46, style1,  "作业人员:"+name1, 54, 0, 54, 6, 0);
				handleCell(sheet, row46, style1,  "日期:"+time1, 54, 7, 54, 13, 7);
				
				
				HSSFRow row26 = sheet.createRow((short) 55);
				handleCell(sheet, row26, style1, "作业中检查", 55, 0, 58,0);
				handleCell(sheet, row26, style1,  "1、中控室SCADA系统液位是否正常", 55, 1, 55, 6, 1);
				if(content2.lastIndexOf("e")!=-1){
				handleCell(sheet, row26, style1,  content2.charAt(content2.lastIndexOf("e")+1)=='1'?"是":" ", 55, 7, 55, 11, 7);
				handleCell(sheet, row26, style1,  name5, 55, 12, 55, 13, 12);
				}else{
					handleCell(sheet, row26, style1,  "", 55, 7, 55, 13, 12);
				}
				
				String[] value2={
				         "2、中控室SCADA系统流量是否正常","f",
				         "3、泵运行是否正常","g",
				         "4、是否有物料滴漏","h"
		                 };
				
				for (int y = 0; y < 3; y++) {
					HSSFRow rowx = sheet.createRow((short) 56+y);
					handleCell(sheet, rowx, style1,  value2[y*2], 56+y, 1, 56+y, 6, 1);
					if(y==2){
						handleCell(sheet, rowx, style1,  content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='1'?"是":(content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='3'?"不适用":" "), 56+y, 7, 56+y, 13, 7);
					}else{
						if(y==0){
							if(content2.lastIndexOf(value2[y*2+1])!=-1){
						        handleCell(sheet, rowx, style1,  content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='1'?"是":" ", 56+y, 7, 56+y, 11, 7);	
						        handleCell(sheet, rowx, style1,  name6, 56+y, 12, 56+y, 13, 12);
							}else{
								handleCell(sheet, rowx, style1,  "", 56+y, 7, 56+y, 13, 12);	
							}
						}else{
						handleCell(sheet, rowx, style1,  content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='1'?"是":" ", 56+y, 7, 56+y, 13, 7);	
						}
					}
				}
				}
				HSSFRow row21 = sheet.createRow((short) 59);
				handleCell(sheet, row21, style1,  "作业人员:"+name2, 59, 0, 59, 6, 0);
				handleCell(sheet, row21, style1,  "日期:"+time2, 59, 7, 59, 13, 7);
				
				HSSFRow row30 = sheet.createRow((short) 60);
				handleCell(sheet, row30, style1, "作业后检查", 60, 0, 64,0);
				handleCell(sheet, row30, style1,  "1、管线是否泄压", 60, 1, 60, 6, 1);
				handleCell(sheet, row30, style1,  content3.charAt(content3.lastIndexOf("i")+1)=='1'?"是":" ", 60, 7,60, 13, 7);
				
				String[] value3={"2、吹扫接口是否封堵","j",
				         "3、物料是否正确回收并标记入库","k",
				         "4、现场是否整理干净","l",
				         "5、记录是否完善","m"
		                 };
  for (int z = 0; z < 4; z++) {
	   HSSFRow rowz = sheet.createRow((short) 61+z);
	   handleCell(sheet, rowz, style1,  value3[z*2], 61+z, 1, 61+z, 6, 1);
	   handleCell(sheet, rowz, style1,  content3.charAt(content3.lastIndexOf(value3[z*2+1])+1)=='1'?"是":" ", 61+z, 7, 61+z, 13, 7);
  }
				
    HSSFRow row23 = sheet.createRow((short) 65);
	handleCell(sheet, row23, style1,  "作业人员:"+name3, 65, 0, 65, 6, 0);
	handleCell(sheet, row23, style1,  "日期:"+time3, 65, 7, 65, 13, 7);
		}else if("31".equals((String)request.getParameter("type"))){
			
			NotifyDto notifyDto=new NotifyDto();
			notifyDto.setCode(request.getParameter("code"));
			notifyDto.setTypes(17+"");
		    List<Map<String,Object>> list=notifyDao.getNotifyList(notifyDto, 0, 0);
		    Map<String,Object> map=list.get(0);
		    int state=Integer.valueOf(map.get("state").toString());
		    
		    String content1=" ";
		    String content2=" ";
		    String content3=" ";
            String name1="";
            String name2="";
            String name3="";
            String name4="";
            String time1="";
            String time2="";
            String time3="";

		   for (int i = 1; i < list.size(); i++) {
			if(list.get(i).get("contentType").toString().equals("1")){
				content1+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("2")){
					name1+=list.get(i).get("sureUserName").toString();
					time1+=list.get(i).get("sureTime").toString();
				}
			}else if(list.get(i).get("contentType").toString().equals("3")){
				content3+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("4")){
					name3+=list.get(i).get("sureUserName").toString();
					time3+=list.get(i).get("sureTime").toString();
				}
			}else if(list.get(i).get("contentType").toString().equals("2")){
				content2+=list.get(i).get("content").toString();
				if(list.get(i).get("state").toString().equals("3")){
					name2+=list.get(i).get("sureUserName").toString();
					time2+=list.get(i).get("sureTime").toString();
				}
				if(list.get(i).get("flag").toString().equals("1")){
					name4+=list.get(i).get("sureUserName").toString();
				}
			}
		}
		   
		    HSSFRow rowtitle = sheet.createRow((short) 0); 
			HSSFRow row0 = sheet.createRow((short) 2); 

			handleCell(sheet, rowtitle, style, "车发换罐作业通知单", 0, 0, 1,13);
			handleCell(sheet, row0, style,  "日期:"+map.get("createTime").toString().substring(0,11), 2, 0, 2, 4,0);
            handleCell(sheet, row0, style,  "JL2303-1", 2, 5, 2, 8,5);
            handleCell(sheet, row0, style,  "编号:"+request.getParameter("code").toString().substring(1), 2, 9, 2, 13,9);
			    HSSFRow row = sheet.createRow((short) 3);      
	            HSSFRow row2 = sheet.createRow((short) 4);      
	            HSSFRow row3 = sheet.createRow((short) 14);      
	            HSSFRow row4 = sheet.createRow((short) 15);   
	            handleCell(sheet, row, style, request.getParameter("t1"), 3, 0, 3, 13) ;
	            handleCell(sheet, row2, style, request.getParameter("v1"), 4, 0, 13, 13) ;
	            handleCell(sheet, row3, style, request.getParameter("t2"), 14, 0, 14, 13) ;
	            handleCell(sheet, row4, style, "", 15, 0, 26, 13) ;
	            handleSVG(request, sheet, wb,request.getParameter("v2") , 0, 15);
	            
	            HSSFRow row7 = sheet.createRow((short) 27);
				handleCell(sheet, row7, style, "调度:"+map.get("sureUserName").toString(), 27, 0, 27,13);
	            
	            HSSFRow row5 = sheet.createRow((short) 28);
				handleCell(sheet, row5, style1, "三、作业注意事项：", 28, 0, 28	,13);
				
				HSSFRow row9 = sheet.createRow((short) 29);
				handleCell(sheet, row9, style1,  
						  "1、正确佩戴防护用品；\n"
                         +"2、第一车注意可能有气，适当少发；\n"
                         +"3、关注泵运行情况.\n"
                         +"4、如油品车发换罐，应检查是否有水。\n"
                         +"5、管线内有气，防止发货时鹤管跳动。", 29, 0, 33,13);
				
				    HSSFRow row10 = sheet.createRow((short) 34);
					handleCell(sheet, row10, style1, "四、作业中的风险分析：", 34, 0, 34	,13);
						
					HSSFRow row11 = sheet.createRow((short) 35);
					handleCell(sheet, row11, style,  "危害及潜在事件：", 35, 0, 35, 4, 0);
					handleCell(sheet, row11, style,  "主要后果", 35, 5, 35, 8, 5);
					handleCell(sheet, row11, style,  "安全措施", 35, 9, 35, 13, 9);
					 
					String[] value={"1、未按规定佩戴劳动防护用品","人身伤害","正确佩戴个人防护用品",
					         "2、进出口阀门开错","杂质、水带出影响品质","根据储罐阀门标识开进口阀发货",
					         "3、吹扫口阀未关严造成物料泄漏","污染环境、危害健康","关闭阀门，做好检查",
					         "4、闷盖未封好造成物料泄漏","物料渗漏污染水体或发生火灾、影响货物品质","封堵好闷盖，做好检查",
					         "5、阀门丝杆处物料泄漏","污染环境、危害健康","加强检查、及时维修",
					         "6、管线腐蚀造成物料泄漏","污 染环境、危害健康","加强检查、及时维修",
					         "7、开错储罐","发错物料","根据提单罐号加强核对",
					         "8、呼吸阀故障造成胀罐","造成储罐损坏","做好维护、加强作业时的检查",
					         "9、装车系统故障","引发事故","做好维护、加强作业时的检查",
					         "10、发货量超过车辆装载量","造成溢料","加强核对、正确安放防溢开关",
					         "11、泵运行时发生故障","引发事故","做好保养、加强巡回检查",
					         "12、未打开泵回流阀","在不作业时管线憋压","打开回流阀、加强巡回检查",
					         "13、泵放气时物料喷溅","污染环境、危害健康","按规程操作，及时盛接",
					         "14、鹤管锁紧装置未锁死","鹤管抖动造成物料喷溅","锁紧鹤管加强观察" 
						     };
					
					for (int j = 0; j < 14; j++) {
						HSSFRow rowj = sheet.createRow((short) 36+j);
						handleCell(sheet, rowj, style1,  value[0+j*3], 36+j, 0, 36+j, 4, 0);
						handleCell(sheet, rowj, style1,  value[1+j*3], 36+j, 5, 36+j, 8, 5);
						handleCell(sheet, rowj, style1,  value[2+j*3], 36+j, 9, 36+j, 13, 9);
					}
					
					
					if(state==4){
					HSSFRow row6 = sheet.createRow((short) 50);
					handleCell(sheet, row6, style1, "五、作业过程检查：", 50, 0, 50	,13);
					
					sheet.setColumnWidth(0,700);
					
					HSSFRow row27 = sheet.createRow((short) 51);
					handleCell(sheet, row27, style1, "作业前检查", 51, 0, 55,0);
					handleCell(sheet, row27, style1,  "1、个人防护用品是否正确佩戴", 51, 1, 51, 6, 1);
					handleCell(sheet, row27, style1,   content1.charAt(content1.lastIndexOf("b")+1)=='1'?"是":" ", 51, 7, 51, 13, 7);
					
					String[] value1={
					         "2、工艺流程是否正确","c",
					         "3、管线、阀门是否正常","d",
					         "4、装车系统是否正常","e",
					         "5、是否有跑冒滴漏","f"
			                 };
			
			for (int x = 0; x <4; x++) {
				HSSFRow rowx = sheet.createRow((short) 52+x);
				handleCell(sheet, rowx, style1,  value1[x*2], 52+x, 1, 52+x, 6, 1);
				handleCell(sheet, rowx, style1,  content1.charAt(content1.lastIndexOf(value1[x*2+1])+1)=='1'?"是":" ", 52+x, 7, 52+x, 13, 7);
			}
					
			        HSSFRow row20 = sheet.createRow((short) 56);
			     	handleCell(sheet, row20, style1,  "作业人员:"+name1, 56, 0, 56, 6, 0);
				    handleCell(sheet, row20, style1,  "日期:"+time1, 56, 7, 56, 13, 7);
					
				    HSSFRow row31 = sheet.createRow((short) 57);
					handleCell(sheet, row31, style1, "作业中检查", 57, 0, 61,0);
					handleCell(sheet, row31, style1,  "1、中控室SCADA系统液位是否正常下降", 57, 1, 57, 6, 1);
					handleCell(sheet, row31, style1,  content2.charAt(content2.lastIndexOf("g")+1)=='1'?"是":" ", 57, 7, 57, 11, 7);
					handleCell(sheet, row31, style1,  name4, 57, 12, 57, 13, 12);
					
					
					String[] value2={
							 "2、放气时是否有物料溢出并盛接","h",
							 "3、泵运行是否正常","i",
					         "4、装车系统是否正常","j",
					         "5、油气回收是否正常","k"
			                 };
					
					for (int y = 0; y < 4; y++) {
						HSSFRow rowx = sheet.createRow((short) 58+y);
						handleCell(sheet, rowx, style1,  value2[y*2], 58+y, 1, 58+y, 6, 1);
						if(y==3){
							handleCell(sheet, rowx, style1,  content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='1'?"是":(content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='3'?"不适用":" "), 58+y, 7, 58+y, 13, 7);
						}else{
							handleCell(sheet, rowx, style1,  content2.charAt(content2.lastIndexOf(value2[y*2+1])+1)=='1'?"是":" ", 58+y, 7, 58+y, 13, 7);	
						}
					}
					
					HSSFRow row23 = sheet.createRow((short) 62);
					handleCell(sheet, row23, style1,  "作业人员:"+name2, 62, 0, 62, 6, 0);
					handleCell(sheet, row23, style1,  "日期:"+time2, 62, 7, 62, 13, 7);
					
					HSSFRow row36 = sheet.createRow((short) 63);
					handleCell(sheet, row36, style1, "作业后检查", 63, 0, 67,0);
					handleCell(sheet, row36, style1,  "1、第一车是否有水", 63, 1, 63, 6, 1);
					handleCell(sheet, row36, style1,   content3.charAt(content3.lastIndexOf("l")+1)=='1'?"是":(content3.charAt(content3.lastIndexOf("l")+1)=='3'?"不适用":" "), 63, 7, 63, 13, 7);
					
					String[] value3={"2、是否超发","m",
					         "3、鹤管、梯子是否收起","n",
					         "4、垫木、禁止启动牌、静电夹是否归位","o",
					         "5、记录是否完善","p"
			                 };
	   for (int z = 0; z < 4; z++) {
		   HSSFRow rowz = sheet.createRow((short) 64+z);
		   handleCell(sheet, rowz, style1,  value3[z*2], 64+z, 1, 64+z, 6, 1);
		   if(z==3){
			   handleCell(sheet, rowz, style1,  content3.charAt(content3.lastIndexOf(value3[z*2+1])+1)=='1'?"是":(content3.charAt(content3.lastIndexOf(value3[z*2+1])+1)=='3'?"不适用":" "), 64+z, 7, 64+z, 13, 7);
		   }else{
			   handleCell(sheet, rowz, style1,  content3.charAt(content3.lastIndexOf(value3[z*2+1])+1)=='1'?"是":" ", 64+z, 7, 64+z, 13, 7); 
		   }
	}
	   
	    HSSFRow row46 = sheet.createRow((short) 68);
		handleCell(sheet, row46, style1,  "作业人员:"+name3, 68, 0, 68, 6, 0);
		handleCell(sheet, row46, style1,  "日期:"+time3, 68, 7, 68, 13, 7);
					
					}			
		}
		return wb ;
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
         t.addTranscodingHint(PNGTranscoder.KEY_WIDTH, new Float(880)) ;
         t.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, new Float(200)) ;
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
	public void handleSVGother(HttpServletRequest request,HSSFSheet sheet,HSSFWorkbook wb, String str, int col, int row,float width,float hight){
		try {
			if(str!=null){
		 String path = request.getSession().getServletContext().getRealPath("/temp.png") ;
         File file = new File(path);  
         FileOutputStream outputStream = null; 
         file.createNewFile();
         outputStream = new FileOutputStream(file);
         byte[] bytes0 = str.getBytes("utf-8");
         PNGTranscoder t = new PNGTranscoder();
         t.addTranscodingHint(PNGTranscoder.KEY_WIDTH, new Float(width)) ;
         t.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, new Float(hight)) ;
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
	public void handleCell(HSSFSheet sheet,HSSFRow row,HSSFCellStyle style,String str,int startRow,int startCol,int endRow,int endCol){
		sheet.addMergedRegion(new Region(startRow,(short)startCol, endRow, (short)endCol));      
		HSSFCell ce = row.createCell((short) 0);      
		ce.setCellValue(str); // 表格的第一行第一列显示的数据      
		ce.setCellStyle(style); // 样式，居中      
	}
	public void handleCell(HSSFSheet sheet,HSSFRow row,HSSFCellStyle style,Double str,int startRow,int startCol,int endRow,int endCol){
		sheet.addMergedRegion(new Region(startRow,(short)startCol, endRow, (short)endCol));      
		HSSFCell ce = row.createCell((short) 0);      
		ce.setCellValue(str); // 表格的第一行第一列显示的数据      
		ce.setCellStyle(style); // 样式，居中      
	}
	public void handleCell(HSSFSheet sheet,HSSFRow row,HSSFCellStyle style,String str,int startRow,int startCol,int endRow,int endCol,int cellNum){
	sheet.addMergedRegion(new Region(startRow,(short)startCol, endRow, (short)endCol));      
		HSSFCell ce = row.createCell((short) cellNum);      
		ce.setCellValue(str); // 表格的第一行第一列显示的数据      
		ce.setCellStyle(style); // 样式，居中      
}
	public void handleCell(HSSFSheet sheet,HSSFRow row,HSSFCellStyle style,Double str,int startRow,int startCol,int endRow,int endCol,int cellNum){
		sheet.addMergedRegion(new Region(startRow,(short)startCol, endRow, (short)endCol));      
			HSSFCell ce = row.createCell((short) cellNum);      
			ce.setCellValue(str); // 表格的第一行第一列显示的数据      
			ce.setCellStyle(style); // 样式，居中      
	}
	@Override
	public void getParams(OaMsg msg){
		try {
			msg.getData().addAll(baseControllerDao.getParams());
		} catch (OAException e) {
			LOG.error("查询基本参数错误",e);
		}
	}
}