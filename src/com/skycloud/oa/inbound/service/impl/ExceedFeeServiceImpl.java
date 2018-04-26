
package com.skycloud.oa.inbound.service.impl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.Region;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dao.ExceedCleanLogDao;
import com.skycloud.oa.feebill.dao.FeeChargeDao;
import com.skycloud.oa.feebill.dto.ExceedCleanLogDto;
import com.skycloud.oa.feebill.dto.FeeChargeDto;
import com.skycloud.oa.feebill.model.ExceedCleanLog;
import com.skycloud.oa.inbound.dao.CargoGoodsDao;
import com.skycloud.oa.inbound.dao.ExceedFeeDao;
import com.skycloud.oa.inbound.dao.ExceedFeeLogDao;
import com.skycloud.oa.inbound.dto.CargoDto;
import com.skycloud.oa.inbound.dto.CargoGoodsDto;
import com.skycloud.oa.inbound.dto.ExceedFeeDto;
import com.skycloud.oa.inbound.dto.ExceedFeeLogDto;
import com.skycloud.oa.inbound.model.ExceedFee;
import com.skycloud.oa.inbound.model.ExceedFeeLog;
import com.skycloud.oa.inbound.service.ExceedFeeService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.report.utils.FormatUtils;
import com.skycloud.oa.statistics.dto.StatisticsDto;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.ExcelUtil;
import com.skycloud.oa.utils.OaMsg;
import com.skycloud.oa.utils.ExcelUtil.CallBack;
import com.sun.xml.internal.bind.v2.model.core.ID;
/**
 *超期费service
 * @author 作者:jiahy
 * @version 时间：2015年3月16日 下午2:24:34
 */
@Service
public class ExceedFeeServiceImpl implements ExceedFeeService {
	private static Logger LOG = Logger.getLogger(ExceedFeeServiceImpl.class);
	@Autowired
	private  ExceedFeeDao exceedFeeDao;
	@Autowired
	private FeeChargeDao feeChargeDao;
	@Autowired
	private ExceedFeeLogDao exceedFeeLogDao;
	@Autowired
	private CargoGoodsDao cargoGoodsDao;
	@Autowired
	private ExceedCleanLogDao exceedCleanLogDao;
	//获取每天超期费列表
	@Override
	public OaMsg getExceedFeeCargoItemList(ExceedFeeDto eFeeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			DecimalFormat    df3   = new DecimalFormat("######0.000");
		List<Map<String,Object>> data=exceedFeeDao.getExceedFeeCargoItemList(eFeeDto);
		    List<Map<String,Object>> reData=new ArrayList<Map<String,Object>>();//保存记录
		    List<Map<String,Object>> refData=new ArrayList<Map<String,Object>>();//保存记录
		    Double currentNum=0d;
		    
		    if(data!=null&&data.size()>0){
		    	Map<String,Object> map=new HashMap<String, Object>();
		    	map.put("id", 0);
		    	map.put("type", data.get(0).get("type"));
		    	map.put("l_time",data.get(0).get("l_time"));
		    	map.put("truckCode", data.get(0).get("truckCode"));
		    	map.put("l_ladingId", data.get(0).get("l_ladingId"));
		    	map.put("l_ladingCode", data.get(0).get("l_ladingCode"));
		    	map.put("operateNum", data.get(0).get("operateNum"));
		    	map.put("clientName", data.get(0).get("clientName"));
		    	map.put("contactClientName", data.get(0).get("contactClientName"));
		    	map.put("currentNum", data.get(0).get("currentNum"));
		    	map.put("col", 1);
		    	currentNum=Double.valueOf(data.get(0).get("currentNum").toString());
		    	reData.add(map);
		    	int j=1;
		    	for(int i=0;i<data.size()-1;i++){
		    		//比较两个相邻时间  =0 =1 >1
		    	int col=compareTime(data.get(i+1).get("l_time").toString(),data.get(i).get("l_time").toString());
		    	 if(col==2){
		    		 reData.get(j-1).put("isCurrent", 1);//确定结存量
		    	 }else if(col>2){
		    		 reData.get(j-1).put("isCurrent", 1);//确定结存量
		    		 Map<String,Object> map1=new HashMap<String, Object>();
				    	map1.put("id", j);
				    	map1.put("type", 0);
				    	map1.put("l_time",getMidTimeStr(data.get(i+1).get("l_time").toString(),data.get(i).get("l_time").toString(),1));
				    	map1.put("truckCode",null);
				    	map1.put("l_ladingId",null);
				    	map1.put("l_ladingCode",null);
				    	map1.put("operateNum",null);
				    	map1.put("clientName",null);
				    	map1.put("currentNum", df3.format(currentNum));
				    	map1.put("isCurrent", 1);
				    	map1.put("col", col-2);
				    	reData.add(map1);
				    	j++;
		    	 }else if(col==1){//如果相同上一条数据不算天数
		    		 reData.get(j-1).put("isCurrent", 0);//确定结存量 
		    	 }	
		    	 
		    	 
		    	 
		    	 
		    	 Map<String,Object> map1=new HashMap<String, Object>();
			    	map1.put("id", j);
			    	map1.put("type", data.get(i+1).get("type"));
			    	map1.put("l_time",data.get(i+1).get("l_time"));
			    	map1.put("truckCode", data.get(i+1).get("truckCode"));
			    	map1.put("l_ladingId", data.get(i+1).get("l_ladingId"));
			    	map1.put("l_ladingCode", data.get(i+1).get("l_ladingCode"));
			    	map1.put("operateNum", data.get(i+1).get("operateNum"));
			    	map1.put("clientName", data.get(i+1).get("clientName"));
			    	map1.put("contactClientName", data.get(i+1).get("contactClientName"));
			    	currentNum=Common.sub(currentNum,Double.valueOf(data.get(i+1).get("operateNum").toString()),3);
			    	map1.put("currentNum", df3.format(currentNum));
			    	map1.put("col", 1);
			    	reData.add(map1);
			    	j++;
		    	}
		    	if(currentNum>0){
		    		reData.get(j-1).put("isCurrent", 1);//确定结存量
		    	}
		    	if(currentNum>0&&eFeeDto.getEndTime()!=null&&compareTime(new Timestamp(eFeeDto.getEndTime()*1000).toString().substring(0, 10),data.get(data.size()-1).get("l_time").toString())>2){
		    		 Map<String,Object> map1=new HashMap<String, Object>();
				    	map1.put("id", j);
				    	map1.put("type", 0);
				    	map1.put("l_time",getMidTimeStr(new Timestamp(eFeeDto.getEndTime()*1000).toString().substring(0, 10),data.get(data.size()-1).get("l_time").toString(),3));
				    	map1.put("truckCode",null);
				    	map1.put("l_ladingId",null);
				    	map1.put("l_ladingCode",null);
				    	map1.put("operateNum",null);
				    	map1.put("clientName", null);
				    	map1.put("currentNum", df3.format(currentNum));
				    	map1.put("col",compareTime(new Timestamp(eFeeDto.getEndTime()*1000).toString().substring(0, 10),data.get(data.size()-1).get("l_time").toString())-1);
				    	map1.put("isCurrent",1);
				    	reData.add(map1);
		    	}else if(currentNum>0&&eFeeDto.getEndTime()!=null&&compareTime(new Timestamp(eFeeDto.getEndTime()*1000).toString().substring(0, 10),data.get(data.size()-1).get("l_time").toString())==2){
		    		 Map<String,Object> map1=new HashMap<String, Object>();
				    	map1.put("id", j);
				    	map1.put("type", 0);
				    	map1.put("l_time",new Timestamp(eFeeDto.getEndTime()*1000).toString().substring(0, 10));
				    	map1.put("truckCode",null);
				    	map1.put("l_ladingId",null);
				    	map1.put("l_ladingCode",null);
				    	map1.put("operateNum",null);
				    	map1.put("clientName", null);
				    	map1.put("currentNum", df3.format(currentNum));
				    	map1.put("isCurrent", 1);
				    	map1.put("col", 1);
				    	reData.add(map1);
		    	}
		    if(eFeeDto.getStartTime()!=null&&compareTime(new Timestamp(eFeeDto.getStartTime()*1000).toString().substring(0, 10), data.get(0).get("l_time").toString())<=1){
		    	refData.addAll(reData);
		    }else if(eFeeDto.getStartTime()!=null){
		    	for(int i=1;i<reData.size();i++){
		    		if(getNewTime(new Timestamp(eFeeDto.getStartTime()*1000).toString().substring(0, 10),reData.get(i).get("l_time").toString())!=null){
		    			String nTime=getNewTime(new Timestamp(eFeeDto.getStartTime()*1000).toString().substring(0, 10),reData.get(i).get("l_time").toString());
		    			reData.get(i).put("l_time",nTime);
		    			reData.get(i).put("col",compareTime(nTime));
		    			refData.addAll(reData.subList(i, reData.size()));
		    			break;
		    		}
		    	}
		    }else{
		    	refData.addAll(reData);
		    }}
		    oaMsg.getData().addAll(refData);
		}catch (RuntimeException e){
			LOG.error("service 获取每天货批超期费列表失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获取超期费货批列表失败",e);
		}
		return oaMsg;
	}
	//获取超期货批列表
	@Override
	public OaMsg getExceedFeeCargoList(ExceedFeeDto eFeeDto, PageView pageView)throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(exceedFeeDao.getExceedFeeCargoList(eFeeDto, pageView.getStartRecord(),pageView.getMaxresult()));
			oaMsg.getMap().put(Constant.TOTALRECORD,exceedFeeDao.getExceedFeeCargoCount(eFeeDto)+"");
		}catch (RuntimeException e){
			LOG.error("service 获取货批超期费列表失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获取超期费货批列表失败",e);
		}
		return oaMsg;
	}
	@Override
	public OaMsg getExceedFeeLadingItemList(ExceedFeeDto eFeeDto)throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			DecimalFormat    df3   = new DecimalFormat("######0.000");
		List<Map<String,Object>> data=exceedFeeDao.getExceedFeeLadingItemList(eFeeDto);
		    List<Map<String,Object>> reData=new ArrayList<Map<String,Object>>();//保存记录
		    List<Map<String,Object>> refData=new ArrayList<Map<String,Object>>();//保存记录
		    Double currentNum=0d;
		    if(data!=null&&data.size()>0){
		    	Map<String,Object> map=new HashMap<String, Object>();
		    	map.put("id", 0);
		    	map.put("type", data.get(0).get("type"));
		    	map.put("l_time",data.get(0).get("l_time"));
		    	map.put("truckCode", data.get(0).get("truckCode"));
		    	map.put("l_ladingId", data.get(0).get("l_ladingId"));
		    	map.put("l_ladingCode", data.get(0).get("l_ladingCode"));
		    	map.put("operateNum", data.get(0).get("operateNum"));
		    	map.put("clientName", data.get(0).get("clientName"));
		    	map.put("contactClientName", data.get(0).get("contactClientName"));
		    	map.put("currentNum", data.get(0).get("currentNum"));//结存量
		    	map.put("col", 1);//天数
		    	currentNum=Double.valueOf(data.get(0).get("currentNum").toString());
		    	reData.add(map);
		    	int j=1;
		    	int nItem=0;
		    	for(int i=0;i<data.size()-1;i++){
		    		//比较两个相邻时间  =1 相同天算为一天 =2 相差一天算2天 >2
		    	int col=compareTime(data.get(i+1).get("l_time").toString(),data.get(i).get("l_time").toString());
		    	
		    	 if(col==2){//eg: 2015-10-11，2015-10-12
		    		 reData.get(j-1).put("isCurrent", 1);//确定结存量
		    	 }else if(col>2){//eg:2015-10-11，2015-10-14
		    		 reData.get(j-1).put("isCurrent", 1);//确定结存量
		    		 //两个时间中间的 eg:2015-10-11，2015-10-14  -->2015-10-12/2015-10-13
		    		 Map<String,Object> map1=new HashMap<String, Object>();
				    	map1.put("id", j);
				    	map1.put("type", 0);
				    	map1.put("l_time",getMidTimeStr(data.get(i+1).get("l_time").toString(),data.get(i).get("l_time").toString(),1));
				    	map1.put("truckCode",null);
				    	map1.put("l_ladingId",null);
				    	map1.put("l_ladingCode",null);
				    	map1.put("operateNum",null);
				    	map1.put("clientName",null);
				    	map1.put("currentNum", df3.format(currentNum));
				    	map1.put("isCurrent", 1);
				    	map1.put("col", col-2);
				    	reData.add(map1);
				    	j++;
		    	 }else if(col==1){//如果相同上一条数据不算天数
		    		 reData.get(j-1).put("isCurrent", 0);//确定结存量 
		    	 }	
		    	 
		    	 Map<String,Object> map1=new HashMap<String, Object>();
			    	map1.put("id", j);
			    	map1.put("type", data.get(i+1).get("type"));
			    	map1.put("l_time",data.get(i+1).get("l_time"));
			    	map1.put("truckCode", data.get(i+1).get("truckCode"));
			    	map1.put("l_ladingId", data.get(i+1).get("l_ladingId"));
			    	map1.put("l_ladingCode", data.get(i+1).get("l_ladingCode"));
			    	map1.put("operateNum", data.get(i+1).get("operateNum"));
			    	map1.put("clientName", data.get(i+1).get("clientName"));
			    	map1.put("contactClientName", data.get(i+1).get("contactClientName"));
			    	currentNum=Common.sub(currentNum,Double.valueOf(data.get(i+1).get("operateNum").toString()),3);
			    	map1.put("currentNum", df3.format(currentNum));
			    	if(currentNum>0){
			    	map1.put("col", 1);
			    	}
			    	
			    	if(data.get(i+1).get("type")!=null&&Integer.valueOf(data.get(i+1).get("type").toString())==4){//起计日期
			    		 nItem=j;
			    		 map1.put("col", 1);
			    		 map1.put("isCurrent", 1); //算为一天
			    	 }
			    	
			    	reData.add(map1);
			    	j++;
		    	}
		    	//处理最后一单
		    	if(currentNum>0){
		    	reData.get(j-1).put("isCurrent", 1);//确定结存量
		    	}
		    	//处理起计日期之前的不算天数
		    	if(nItem!=0){
		    	for(int m=0;m<nItem;m++){
		    		reData.get(m).put("isCurrent",0);
		    	}
		    	}
		    	//截至日期与最后一天做对比
		    	if(currentNum>0&&eFeeDto.getEndTime()!=null&&compareTime(new Timestamp(eFeeDto.getEndTime()*1000).toString().substring(0, 10),data.get(data.size()-1).get("l_time").toString())>2){
		    		 Map<String,Object> map1=new HashMap<String, Object>();
				    	map1.put("id", j);
				    	map1.put("type", 0);
				    	map1.put("l_time",getMidTimeStr(new Timestamp(eFeeDto.getEndTime()*1000).toString().substring(0, 10),data.get(data.size()-1).get("l_time").toString(),3));
				    	map1.put("truckCode",null);
				    	map1.put("l_ladingId",null);
				    	map1.put("l_ladingCode",null);
				    	map1.put("operateNum",null);
				    	map1.put("clientName", null);
				    	map1.put("currentNum", df3.format(currentNum));
				    	map1.put("col",compareTime(new Timestamp(eFeeDto.getEndTime()*1000).toString().substring(0, 10),data.get(data.size()-1).get("l_time").toString())-1);
				    	map1.put("isCurrent",1);
				    	reData.add(map1);
		    	}else if(currentNum>0&&eFeeDto.getEndTime()!=null&&compareTime(new Timestamp(eFeeDto.getEndTime()*1000).toString().substring(0, 10),data.get(data.size()-1).get("l_time").toString())==2){
		    		 Map<String,Object> map1=new HashMap<String, Object>();
				    	map1.put("id", j);
				    	map1.put("type", 0);
				    	map1.put("l_time",new Timestamp(eFeeDto.getEndTime()*1000).toString().substring(0, 10));
				    	map1.put("truckCode",null);
				    	map1.put("l_ladingId",null);
				    	map1.put("l_ladingCode",null);
				    	map1.put("operateNum",null);
				    	map1.put("clientName", null);
				    	map1.put("currentNum", df3.format(currentNum));
				    	map1.put("isCurrent", 1);
				    	map1.put("col", 1);
				    	reData.add(map1);
		    	}else if(currentNum>0&&eFeeDto.getEndTime()!=null&&compareTime(new Timestamp(eFeeDto.getEndTime()*1000).toString().substring(0, 10),reData.get(nItem).get("l_time").toString())<1){
		    		reData.get(nItem).put("isCurrent",0);//如果止记日期小于提单起计日期，不计算
		    	}
		    
		    if(eFeeDto.getStartTime()!=null&&compareTime(new Timestamp(eFeeDto.getStartTime()*1000).toString().substring(0, 10), data.get(0).get("l_time").toString())<=1){
		    	refData.addAll(reData);
		    }else if(eFeeDto.getStartTime()!=null){
		    	for(int i=1;i<reData.size();i++){
		    		if(getNewTime(new Timestamp(eFeeDto.getStartTime()*1000).toString().substring(0, 10),reData.get(i).get("l_time").toString())!=null){
		    			String nTime=getNewTime(new Timestamp(eFeeDto.getStartTime()*1000).toString().substring(0, 10),reData.get(i).get("l_time").toString());
		    			reData.get(i).put("l_time",nTime);
		    			reData.get(i).put("col",compareTime(nTime));
		    			
		    			refData.addAll(reData.subList(i, reData.size()));
		    			break;
		    		}
		    	}
		    }else{
		    	refData.addAll(reData);
		    } }
		    oaMsg.getData().addAll(refData);
		}catch (RuntimeException e){
			LOG.error("service 获取每天提单超期费列表失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获取超期费货批列表失败",e);
		}
		return oaMsg;
	}
	/**
	 *@author jiahy
	 * @param substring
	 * @param string
	 * @return
	 */
	private String getNewTime(String firTime, String secTime) {
		if(secTime.contains("至")){
			String[] times=secTime.split("至");
			if(compareTime(firTime, times[0])>=1&&compareTime(firTime, times[1])<1){
				return firTime+"至"+times[1];
			}else if(compareTime(firTime, times[0])>=1&&compareTime(firTime, times[1])==1){
				return firTime;
			}else{
				return null;
			}
		}else{
			if(compareTime(firTime, secTime)==1){
				return firTime;
			}else{
				return null;
			}
		}
		
		
	}
	/**
	 *@author jiahy
	 * @param string
	 * @param string2
	 * type 1--不要头不要尾，2--要头不要尾，3--要尾不要头，4头尾都要
	 * @return
	 */
	private String getMidTimeStr(String endTime, String startTime,int type) {
		 Calendar c = Calendar.getInstance();
		    Date date=null;
		    try {
		    date = new SimpleDateFormat("yyyy-MM-dd").parse(endTime);
		    } catch (ParseException e) {
		    e.printStackTrace();
		    }
		    c.setTime(date);
		    int day=c.get(Calendar.DATE);
		    if(type==1||type==2){
		    c.set(Calendar.DATE,day-1);
		    }
		    String dayAfter=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		    Calendar c2 = Calendar.getInstance();
		    Date date2=null;
		    try {
		    date2 = new SimpleDateFormat("yy-MM-dd").parse(startTime);
		    } catch (ParseException e) {
		    e.printStackTrace();
		    }
		    c2.setTime(date2);
		    int day2=c2.get(Calendar.DATE);
		    if(type==1||type==3){
		    	c2.set(Calendar.DATE,day2+1);
		    }
		    String dayBefore=new SimpleDateFormat("yyyy-MM-dd").format(c2.getTime());
		    
		if(dayAfter.equals(dayBefore)){
			return dayAfter;
		}else{
			return dayBefore+"至"+dayAfter;
		}
	}
	/**
	 *@author jiahy
	 * @param string
	 * @param string2
	 * @return
	 */
	private int compareTime(String endTime, String startTime) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
        long to = 0;  
        try {  
            to = df.parse(startTime).getTime();  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        long from = 0;  
        try {  
            from = df.parse(endTime).getTime();  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
		return (int) (((from - to) / (1000 * 60 * 60 * 24))+1);
	}
	private int compareTime(String time){
		if(time.contains("至")){
			return compareTime(time.split("至")[1], time.split("至")[0]);
		}else{
			return 1;
		}
	}

	@Override
	public OaMsg getExceedFeeLadingList(ExceedFeeDto eFeeDto, PageView pageView)throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			List<Map<String,Object>> data=exceedFeeDao.getExceedFeeLadingList(eFeeDto, pageView.getStartRecord(),pageView.getMaxresult());
			for(int i=0;i<data.size();i++){
				if(data.get(i).get("ladingId")!=null){
					data.get(i).put("inboundMsg", exceedFeeDao.getInboundMsgListByLadingId(Integer.valueOf(data.get(i).get("ladingId").toString())));
				}
			}
			oaMsg.getData().addAll(data);
			oaMsg.getMap().put(Constant.TOTALRECORD,exceedFeeDao.getExceedFeeLadingCount(eFeeDto)+"");
		}catch (RuntimeException e){
			LOG.error("service 获取超期费列表失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获取超期费提单列表失败",e);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getExceedFeeList(ExceedFeeDto eFeeDto, PageView pageView)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			List<Map<String,Object>> data=exceedFeeDao.getExceedFeeList(eFeeDto, pageView.getStartRecord(),pageView.getMaxresult());
			if(data!=null&&data.size()>0){
				for(int i=0;i<data.size();i++){
					if(data.get(i).get("ladingId")!=null){
						data.get(i).put("inboundMsg", exceedFeeDao.getInboundMsgListByLadingId(Integer.valueOf(data.get(i).get("ladingId").toString())));
					}
				}
			}
			oaMsg.getData().addAll(data);
			oaMsg.getMap().put(Constant.TOTALRECORD,exceedFeeDao.getExceedFeeCount(eFeeDto)+"");
		}catch (RuntimeException e){
			LOG.error("service 获取超期费列表失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获取超期费列表失败",e);
		}
		return oaMsg;
	}
	@Override
	@Log(object=C.LOG_OBJECT.PCS_EXCEEDFEE,type=C.LOG_TYPE.CREATE)
	public OaMsg addExceedFee(ExceedFee exceedFee) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getMap().put("id",exceedFeeDao.addExceedFee(exceedFee)+"");
		}catch (RuntimeException e){
			LOG.error("service 添加超期费失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 添加超期费失败",e);
		}
		return oaMsg;
	}
	@Override
	public OaMsg getExceedFeeMsg(ExceedFeeDto eDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			Map<String, Object> msg=exceedFeeDao.getExceedFeeMsg(eDto);
			FeeChargeDto feeChargeDto=new FeeChargeDto();
			feeChargeDto.setExceedId(eDto.getId());
			msg.put("feeCharge", feeChargeDao.getFeeChargeList(feeChargeDto));
			oaMsg.getData().add(msg);
		}catch (RuntimeException e){
			LOG.error("service 添加超期费失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 添加超期费失败",e);
		}
		return oaMsg;
	}
	@Override
	@Log(object=C.LOG_OBJECT.PCS_EXCEEDFEE,type=C.LOG_TYPE.SAVEORUPDATE)
	public OaMsg addOrUpdateExceedFee(ExceedFeeDto eFeeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			ExceedFee exceedFee= eFeeDto.getExceedFee();
			Integer id=0;
			if(exceedFee.getId()!=null){
				id=exceedFee.getId();
				exceedFeeDao.updateExceedFee(exceedFee);
			}else{
				id=exceedFeeDao.addExceedFee(exceedFee);
			}
			
			if(eFeeDto.getFeeCharge().getId()!=null){
				feeChargeDao.updateFeeCharge(eFeeDto.getFeeCharge());
			}else{
			eFeeDto.getFeeCharge().setExceedId(id);
			feeChargeDao.insertfeeChargeCargoLading(feeChargeDao.addFeeCharge(eFeeDto.getFeeCharge()));
			}
			oaMsg.getMap().put("id",id+"");
		}catch (RuntimeException e){
			LOG.error("service 添加或更新超期费结算单失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 添加或更新超期费结算单失败",e);
		}
		return oaMsg;
	}
	@Override
	@Log(object=C.LOG_OBJECT.PCS_EXCEEDFEE,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateExceedFee(ExceedFee exceedFee) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			exceedFeeDao.updateExceedFee(exceedFee);
		}catch (RuntimeException e){
			LOG.error("service 更新超期费失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 更新超期费失败",e);
		}
		return oaMsg;
	}
	@Override
	@Log(object=C.LOG_OBJECT.PCS_EXCEEDFEE,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteExceedFee(ExceedFeeDto eFeeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			FeeChargeDto feeChargeDto=new FeeChargeDto();
			feeChargeDto.setExceedId(eFeeDto.getId());
			List<Map<String,Object>> dataList=feeChargeDao.getFeeChargeList(feeChargeDto,0,0);
			if(dataList.size()>0){
				if(dataList.get(0).get("feebillId")==null||Integer.valueOf(dataList.get(0).get("feebillId").toString())==0){//为空，或为0
					feeChargeDao.deleteCargoLadingOfFeeCharge(feeChargeDto);
					feeChargeDao.deleteFeeCharge(feeChargeDto);
					exceedFeeDao.deleteExceedFee(eFeeDto);
					exceedFeeLogDao.deleteExceedFeeLog(new ExceedFeeLogDto(eFeeDto.getId(),null));
				}else{
					oaMsg.setMsg("该结算单已生成账单，无法删除！");
				}
			}else{
				exceedFeeDao.deleteExceedFee(eFeeDto);
			}
		}catch (RuntimeException e){
			LOG.error("service 删除超期费失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 删除超期费失败",e);
		}
		return oaMsg;
	}
	@Override
	public OaMsg getCodeNum(ExceedFeeDto eFeeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().add(exceedFeeDao.getCodeNum(eFeeDto));
		}catch (RuntimeException e){
			LOG.error("service 获取超期费编号失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获取超期费编号失败",e);
		}
		return oaMsg;
	}
	@Override
	public OaMsg cleanCargo(ExceedFeeDto eFeeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			exceedFeeDao.cleanCargo(eFeeDto);
	}catch (RuntimeException e){
		LOG.error("service 结清超期货批费用失败",e);
		oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		throw new OAException(Constant.SYS_CODE_DB_ERR, "service 结清超期货批费用失败",e);
	}
	return oaMsg;
	}
	@Override
	public OaMsg cleanLading(ExceedFeeDto eFeeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			exceedFeeDao.cleanLading(eFeeDto);
		}catch (RuntimeException e){
			LOG.error("service 结清超期提单费用失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 结清超期提单费用失败",e);
		}
		return oaMsg;
	}
	@Override
	public OaMsg getLadingTurnList(ExceedFeeDto eFeeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			DecimalFormat    df3   = new DecimalFormat("######0.000");
			List<Map<String,Object>> data=exceedFeeDao.getExceedFeeLadingItemList(eFeeDto);
			    List<Map<String,Object>> reData=new ArrayList<Map<String,Object>>();//保存记录
			    if(data.size()>0){
			    	Double currentNum=Double.valueOf(data.get(0).get("currentNum").toString());
			    	for(int i=0;i<data.size();i++){
			    		Map<String,Object> map=new HashMap<String, Object>();
				    	map.put("id", 0);
				    	map.put("type", data.get(i).get("type"));
				    	map.put("l_time",data.get(i).get("l_time"));
				    	map.put("truckCode", data.get(i).get("truckCode"));
				    	map.put("l_ladingId", data.get(i).get("l_ladingId"));
				    	map.put("l_ladingCode", data.get(i).get("l_ladingCode"));
				    	if(Integer.valueOf(data.get(i).get("type").toString())==1){
				    		map.put("operateNum", data.get(i).get("currentNum"));
				    	}else{
				    		map.put("operateNum", data.get(i).get("operateNum"));
				    	}
				    	map.put("clientName", data.get(i).get("clientName"));
				    	map.put("contactClientName", data.get(i).get("contactClientName"));
				    	currentNum=Common.sub(currentNum,Double.valueOf(data.get(i).get("operateNum").toString()),3);
				    	map.put("currentNum", df3.format(currentNum));
				    	reData.add(map);
			    	}
			    }
			    oaMsg.getData().addAll(reData);
		}catch (RuntimeException e){
			LOG.error("service 结清超期提单费用失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 结清超期提单费用失败",e);
		}
		return oaMsg;
	}
	@Override
	public OaMsg getCargoTurnList(ExceedFeeDto eFeeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			DecimalFormat    df3   = new DecimalFormat("######0.000");
			List<Map<String,Object>> data=exceedFeeDao.getExceedFeeCargoItemList(eFeeDto);
			 List<Map<String,Object>> reData=new ArrayList<Map<String,Object>>();//保存记录
			 if(data.size()>0){
			    	Double currentNum=Double.valueOf(data.get(0).get("currentNum").toString());
			    	for(int i=0;i<data.size();i++){
			    		Map<String,Object> map=new HashMap<String, Object>();
				    	map.put("id", 0);
				    	map.put("type", data.get(i).get("type"));
				    	map.put("l_time",data.get(i).get("l_time"));
				    	map.put("truckCode", data.get(i).get("truckCode"));
				    	map.put("l_ladingId", data.get(i).get("l_ladingId"));
				    	map.put("l_ladingCode", data.get(i).get("l_ladingCode"));
				    	if(Integer.valueOf(data.get(i).get("type").toString())==1){
				    		map.put("operateNum", data.get(i).get("currentNum"));
				    	}else{
				    		map.put("operateNum", data.get(i).get("operateNum"));
				    	}
				    	map.put("clientName", data.get(i).get("clientName"));
				    	map.put("contactClientName", data.get(i).get("contactClientName"));
				    	currentNum=Common.sub(currentNum,Double.valueOf(data.get(i).get("operateNum").toString()),3);
				    	map.put("currentNum", df3.format(currentNum));
				    	reData.add(map);
			    	}
			    }
			    oaMsg.getData().addAll(reData);
		}catch (RuntimeException e){
			LOG.error("service 结清超期提单费用失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 结清超期提单费用失败",e);
		}
		return oaMsg;
	}
	@Override
	@Log(object=C.LOG_OBJECT.PCS_EXCEEDFEE,type=C.LOG_TYPE.REBACK)
	public OaMsg backStatus(ExceedFee exceedFee) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			exceedFeeDao.updateExceedFee(exceedFee);
			exceedFeeDao.backStatus(exceedFee.getId());
			ExceedFeeDto eFeeDto=new ExceedFeeDto();
			eFeeDto.setIsFinish(0);
			if(exceedFee.getType()!=null&&exceedFee.getType()==1){
				eFeeDto.setCargoId(exceedFee.getCargoId());
				exceedFeeDao.cleanCargo(eFeeDto);
			}else{
				eFeeDto.setLadingId(exceedFee.getLadingId());
				exceedFeeDao.cleanLading(eFeeDto);
			}
			
		}catch (RuntimeException e){
			LOG.error("service 更新超期费失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 更新超期费失败",e);
		}
		return oaMsg;
	}
	@Override
	public OaMsg getExceedFeeLog(ExceedFeeLogDto elDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(exceedFeeLogDao.getExceedFeeLogList(elDto));
		}catch (RuntimeException e){
			LOG.error("service 更新超期费失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获取已经生成提单的每天提单记录失败",e);
		}
		return oaMsg;
	}
	@Override
	public OaMsg addExceedFeeLogs(List<ExceedFeeLog> efLogsList) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			if(efLogsList!=null&&efLogsList.size()>0){
				StringBuilder ids=new StringBuilder("0");
				for(ExceedFeeLog efLog : efLogsList){
					if(efLog.getId()!=null){
						ids.append(","+efLog.getId());
						exceedFeeLogDao.updateExceedFeeLog(efLog);
					}else{
						ids.append(","+exceedFeeLogDao.addExceedFeeLog(efLog));				
					}
				}
			exceedFeeLogDao.deleteExceedFeeLog(new ExceedFeeLogDto(efLogsList.get(0).getExceedId(),ids.toString()));	
				
			}
		}catch (RuntimeException e){
			LOG.error("servic添加超期提单每日记录失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "servic添加超期提单每日记录失败",e);
		}
		return oaMsg;
	}
	@Override
	public OaMsg getLadingTurnLst(ExceedFeeDto eFeeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			DecimalFormat    df3   = new DecimalFormat("######0.000");
			List<Map<String,Object>> data=exceedFeeDao.getExceedFeeLadingItemList(eFeeDto);
			    List<Map<String,Object>> reData=new ArrayList<Map<String,Object>>();//保存记录
			    if(data.size()>0){
			    	Double currentNum=Double.valueOf(data.get(0).get("currentNum").toString());
			    	for(int i=0;i<data.size();i++){
			    		Map<String,Object> map=new HashMap<String, Object>();
				    	map.put("id", 0);
				    	map.put("type", data.get(i).get("type"));
				    	map.put("l_time",data.get(i).get("l_time"));
				    	map.put("c_time",data.get(i).get("c_time"));
				    	map.put("truckCode", data.get(i).get("truckCode"));
				    	map.put("l_ladingId", data.get(i).get("l_ladingId"));
				    	map.put("l_ladingCode", data.get(i).get("l_ladingCode"));
				    	if(Integer.valueOf(data.get(i).get("type").toString())==1){
				    		map.put("operateNum", data.get(i).get("currentNum"));
				    	}else{
				    		map.put("operateNum", data.get(i).get("operateNum"));
				    	}
				    	map.put("clientName", data.get(i).get("clientName"));
				    	map.put("contactClientName", data.get(i).get("contactClientName"));
				    	currentNum=Common.sub(currentNum,Double.valueOf(data.get(i).get("operateNum").toString()),3);
				    	map.put("currentNum", df3.format(currentNum));
				    	
				    	if(Integer.parseInt(data.get(i).get("type").toString())!=4){
				    		
				    		reData.add(map);
				    	}
				    	
			    	}
			    }
			    oaMsg.getData().addAll(reData);
		}catch (RuntimeException e){
			LOG.error("service 结清超期提单费用失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 结清超期提单费用失败",e);
		}
		return oaMsg;
	}
	@Override
	public HSSFWorkbook exportLogExcel(HttpServletRequest request, OaMsg msg,
			final ExceedFeeDto eFeeDto,final int sType) throws OAException {
		final List<Object> data=msg.getData();
	    	return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/ladingTurn.xls", new CallBack() {
	    			@Override
	    			public void setSheetValue(HSSFSheet sheet){
	    			int item=3;
					HSSFRow itemRow = null;
					Map<String, Object> itemMapData;
					itemRow=sheet.getRow(1);
					 itemMapData=(Map<String, Object>) data.get(0);
					 
					 if(sType!=2&&sType!=3){
						 CargoGoodsDto agDto=new CargoGoodsDto();
						 agDto.setCargoId(eFeeDto.getCargoId());
						 try {
							 Map<String, Object> cargoInfo =cargoGoodsDao.getCargoList(agDto, 0, 0).get(0);
							 itemRow.getCell(0).setCellValue(FormatUtils.getStringValue("入库船舶:"+cargoInfo.get("shipName").toString()+"     货批:"+cargoInfo.get("code").toString()+"     货主:"+cargoInfo.get("clientName").toString()));//序号
						 } catch (OAException e) {
							 // TODO Auto-generated catch block
							 e.printStackTrace();
						 }
						 
					 }else{
						 
						 itemRow.getCell(0).setCellValue(FormatUtils.getStringValue("货主:"+itemMapData.get("clientName").toString()));//序号
					 }
						 
						 
					 if(data!=null&&data.size()>0){
						 int size=data.size();
						for(int i=0;i<size;i++){
						 if(i!=0){
							itemRow=sheet.createRow(i+item);
							itemRow.setHeight(sheet.getRow(3).getHeight());
							for(int j=0;j<7;j++)
								itemRow.createCell(j).setCellStyle(sheet.getRow(3).getCell(j).getCellStyle());
						 }else{
							 itemRow=sheet.getRow(item);
						 }
						 //初始化数据
						 itemMapData=(Map<String, Object>) data.get(i);
						 itemRow.getCell(0).setCellValue(FormatUtils.getStringValue(itemMapData.get("l_time")));
						 itemRow.getCell(1).setCellValue(FormatUtils.getStringValue(itemMapData.get("contactClientName")));
						 String st="";
						 if(Integer.parseInt(itemMapData.get("type").toString())==1){
							 if(sType!=1){
								 st="调入";
							 }else{
								 st="入库";
							 }
						 }else if(Integer.parseInt(itemMapData.get("type").toString())==2){
							 st="提货";
						 }else if(Integer.parseInt(itemMapData.get("type").toString())==3){
							 st="货权转移";
						 }else if(Integer.parseInt(itemMapData.get("type").toString())==4){
							 st="起计";
						 }else if(Integer.parseInt(itemMapData.get("type").toString())==5){
							 st="扣损";
						 }
						 itemRow.getCell(2).setCellValue(st);
						 itemRow.getCell(3).setCellValue(FormatUtils.getStringValue(itemMapData.get("l_ladingCode")));
						 itemRow.getCell(4).setCellValue(FormatUtils.getStringValue(itemMapData.get("truckCode")));
						 double oN=0;
						 if(Integer.parseInt(itemMapData.get("type").toString())==1){
							 oN=Common.fixDouble(Double.parseDouble(itemMapData.get("operateNum").toString()),3);
						 }else{
							 oN=Common.fixDouble(-Double.parseDouble(itemMapData.get("operateNum").toString()),3);
						 }
						 itemRow.getCell(5).setCellValue(oN);
						 itemRow.getCell(6).setCellValue(Common.fixDouble(Double.parseDouble(itemMapData.get("currentNum").toString()),3));
						}
						itemRow=sheet.createRow(3+size);
						itemRow.setHeight(sheet.getRow(3).getHeight());
						for(int j=0;j<7;j++){
							itemRow.createCell(j).setCellStyle(sheet.getRow(3).getCell(j).getCellStyle());
						}
						 itemRow=sheet.createRow(4+size);
						 itemRow.setHeight(sheet.getRow(3).getHeight());
							for(int j=0;j<7;j++){
								itemRow.createCell(j).setCellStyle(sheet.getRow(3).getCell(j).getCellStyle());
							}
						 itemRow.getCell(0).setCellValue(FormatUtils.getStringValue("统计时间:"));//序号
							itemRow.getCell(1).setCellValue(new Date().toLocaleString());  
					 }
					 sheet.setForceFormulaRecalculation(true);
	    			}
	    		}).getWorkbook();
	}
	/**
	 * @Title exportExcelList
	 * @Descrption:TODO
	 * @param:@param request
	 * @param:@param eFeeDto
	 * @param:@return
	 * @auhor jiahy
	 * @date 2016年9月23日上午10:23:00
	 * @throws
	 */
	@Override
	public HSSFWorkbook exportExcelList(HttpServletRequest request,final ExceedFeeDto eFeeDto) {
		return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/exceedFeeList.xls", new CallBack() {
			@Override
			public void setSheetValue(HSSFSheet sheet) {
				try {
				List<Object> data=getExceedFeeList(eFeeDto,new PageView(0, 0)).getData();
				HSSFRow itemRow=null;
				Map<String, Object> map;
				List<Map<String, Object>> itemList;
				int itemRowNum=2,startRow=0,endRow=0;
				int itemColLen=sheet.getRow(1).getLastCellNum();
					if(data!=null&&data.size()>0){
						for(int i=0,len=data.size();i<len;i++){
							itemRow=sheet.createRow(itemRowNum);
							itemRow.setHeight(sheet.getRow(1).getHeight());
							for(int j=0;j<itemColLen;j++)
								itemRow.createCell(j).setCellStyle(sheet.getRow(1).getCell(j).getCellStyle());
							map=(Map<String, Object>) data.get(i);
							itemRow.getCell(0).setCellValue(FormatUtils.getStringValue(map.get("code")));
							itemRow.getCell(1).setCellValue(FormatUtils.getStringValue(map.get("accountTime")));
							itemRow.getCell(4).setCellValue(map.get("ladingCode")==null?FormatUtils.getStringValue(map.get("receiveClientName")):FormatUtils.getStringValue(map.get("clientName")));
							itemRow.getCell(5).setCellValue(FormatUtils.getStringValue(map.get("productName")));
							itemRow.getCell(6).setCellValue(map.get("ladingCode")==null?"":FormatUtils.getStringValue(map.get("sendClientName")));
							itemRow.getCell(7).setCellValue(FormatUtils.getStringValue(map.get("ladingCode")));
							itemRow.getCell(8).setCellValue(FormatUtils.getStringValue(map.get("exceedFee")));
							itemRow.getCell(9).setCellValue(FormatUtils.getStringValue(map.get("exceedTotalFee")));
							itemRow.getCell(10).setCellValue(FormatUtils.getStringValue(map.get("startTime")));
							itemRow.getCell(11).setCellValue(FormatUtils.getStringValue(map.get("endTime")));
							itemRow.getCell(12).setCellValue(getStatusVal(map.get("status")));
							if(map.get("cargoCode")!=null){
							itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(map.get("cargoCode")));
							itemRow.getCell(3).setCellValue("");
							}else if(map.get("inboundMsg")!=null){
								itemList=(List<Map<String, Object>>) map.get("inboundMsg");
								if(itemList!=null&&itemList.size()>0){
									itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(itemList.get(0).get("cargoCode")));
									itemRow.getCell(3).setCellValue(FormatUtils.getDoubleValue(itemList.get(0).get("goodsTotal")));
									startRow=itemRowNum;
									endRow=itemRowNum+itemList.size()-1;
									for(int n=1;n<itemList.size();n++){
										itemRowNum++;
										itemRow=sheet.createRow(itemRowNum);
										itemRow.setHeight(sheet.getRow(1).getHeight());
										for(int j=0;j<itemColLen;j++)
											itemRow.createCell(j).setCellStyle(sheet.getRow(1).getCell(j).getCellStyle());
										itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(itemList.get(n).get("cargoCode")));
										itemRow.getCell(3).setCellValue(FormatUtils.getDoubleValue(itemList.get(n).get("goodsTotal")));
									}
								}
								if(startRow!=endRow){
								sheet.addMergedRegion(new Region(startRow,(short)0, endRow, (short)0)); 
								sheet.addMergedRegion(new Region(startRow,(short)1, endRow, (short)1)); 
								sheet.addMergedRegion(new Region(startRow,(short)4, endRow, (short)4)); 
								sheet.addMergedRegion(new Region(startRow,(short)5, endRow, (short)5)); 
								sheet.addMergedRegion(new Region(startRow,(short)6, endRow, (short)6)); 
								sheet.addMergedRegion(new Region(startRow,(short)7, endRow, (short)7)); 
								sheet.addMergedRegion(new Region(startRow,(short)8, endRow, (short)8)); 
								sheet.addMergedRegion(new Region(startRow,(short)9, endRow, (short)9)); 
								sheet.addMergedRegion(new Region(startRow,(short)10, endRow, (short)10)); 
								sheet.addMergedRegion(new Region(startRow,(short)11, endRow, (short)11)); 
								sheet.addMergedRegion(new Region(startRow,(short)12, endRow, (short)12));
								}
							}
							itemRowNum++;
						}
					}
				} catch (OAException e) {
					e.printStackTrace();
				}
				
			}
		}).getWorkbook();
	};
	private String getStatusVal(Object item){
		if (item == null || item.toString().equals("null")) {
			return "";
		} else {
			try {
				int mItem = Integer.valueOf(item.toString());
				switch (mItem) {
				case 0:
					return "未提交";
				case 1:
					return "已提交";
				case 2:
					return "已生成账单";
				case 3:
					return "已开票";
				case 4:
					return "已完成";
				default:
					break;
				}
			} catch (NumberFormatException e) {
				return "";
			}
		}
		return "";
	}
	/**
	 * @throws OAException 
	 * @Title addExceedCleanLog
	 * @Descrption:TODO
	 * @param:@param exceedCleanLog
	 * @param:@return
	 * @auhor jiahy
	 * @date 2017年3月25日上午9:27:35
	 * @throws
	 */
	@Override
	public OaMsg addExceedCleanLog(ExceedCleanLog exceedCleanLog) throws OAException {
		OaMsg oaMsg=new OaMsg();
		User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		exceedCleanLog.setCreateUserId(user.getId());
		oaMsg.getMap().put("id", exceedCleanLogDao.addLog(exceedCleanLog)+"");
		return oaMsg;
	}
	/**
	 * @Title getExceedCleanLog
	 * @Descrption:TODO
	 * @param:@param eclDto
	 * @param:@return
	 * @auhor jiahy
	 * @date 2017年3月25日上午9:27:35
	 * @throws
	 */
	@Override
	public OaMsg getExceedCleanLog(ExceedCleanLogDto eclDto) throws OAException  {
		OaMsg oaMsg=new OaMsg();
		oaMsg.getData().addAll(exceedCleanLogDao.getLogList(eclDto));
		return oaMsg;
	}
	/**
	 * @Title deleteExceedCleanLog
	 * @Descrption:TODO
	 * @param:@param id
	 * @param:@return
	 * @auhor jiahy
	 * @date 2017年3月25日上午9:27:35
	 * @throws
	 */
	@Override
	public OaMsg deleteExceedCleanLog(int id) throws OAException  {
		OaMsg oaMsg=new OaMsg();
		exceedCleanLogDao.deleteLog(id);
		return oaMsg;
	};
	
}
