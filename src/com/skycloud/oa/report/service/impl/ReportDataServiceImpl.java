/**
 * 
 */
package com.skycloud.oa.report.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dao.OutBoundDao;
import com.skycloud.oa.report.dao.ReportDao;
import com.skycloud.oa.report.dto.ReportDto;
import com.skycloud.oa.report.service.ReportDataService;
import com.skycloud.oa.report.utils.DateUtils;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;

/**
 *
 * @author jiahy
 * @version 2015年11月10日 上午8:46:42
 */
@Service
public class ReportDataServiceImpl implements ReportDataService {
	private static Logger LOG = Logger.getLogger(ReportServiceImpl.class);
	@Autowired
	private ReportDao reportDao;
	@Autowired
	private OutBoundDao shipDeliverWorkDao;
	@Override
	public OaMsg getYearPipeInboundData(ReportDto report) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("01", 0);
			map.put("02", 0);
			map.put("03", 0);
			map.put("04", 0);
			map.put("05", 0);
			map.put("06", 0);
			map.put("07", 0);
			map.put("08", 0);
			map.put("09", 0);
			map.put("10", 0);
			map.put("11", 0);
			map.put("12", 0);
			List<Map<String,Object>> mdata=reportDao.getYearPipeInboundData(report);
			if(mdata!=null){
				int size=mdata.size();
				for(int i=0;i<size;i++){
					map.put(mdata.get(i).get("date").toString(), Common.add(map.get(mdata.get(i).get("date").toString()), mdata.get(i).get("goodsPlan"),3));
				}
			}
			
			oaMsg.getData().add(map);
		} catch (Exception re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
		
		
	}
	@Override
	public OaMsg getYearPipeOutboundData(ReportDto report) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("01", 0);
			map.put("02", 0);
			map.put("03", 0);
			map.put("04", 0);
			map.put("05", 0);
			map.put("06", 0);
			map.put("07", 0);
			map.put("08", 0);
			map.put("09", 0);
			map.put("10", 0);
			map.put("11", 0);
			map.put("12", 0);
			List<Map<String,Object>> mdata=reportDao.getYearPipeOutboundData(report);
			if(mdata!=null){
				int size=mdata.size();
				for(int i=0;i<size;i++){
					map.put(mdata.get(i).get("date").toString(), Common.add(map.get(mdata.get(i).get("date").toString()), mdata.get(i).get("goodsPlan"),3));
				}
			}
			
			oaMsg.getData().add(map);
		} catch (Exception re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
	@Override
	public OaMsg getMonthBerth(ReportDto report) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("1", 0);
			map.put("2", 0);
			map.put("3", 0);
			map.put("4", 0);
			map.put("5", 0);
			map.put("6", 0);
			map.put("7", 0);
			List<String> listStr=new ArrayList<String>();
			listStr.add("1");
			listStr.add("2");
			listStr.add("3");
			listStr.add("4");
			listStr.add("5");
			listStr.add("6");
			listStr.add("7");
		   List<Map<String,Object>> data=reportDao.getMonthBerth(report);
		   List<Map<String,Object>> mdata=reportDao.getSpecialMonthBerth(report);
		   String itemstra;
		   for(int j=0;j<mdata.size();j++){
			   if(mdata.get(j).get("clientName").equals("中国石油天然气股份有限公司华东润滑油厂")||mdata.get(j).get("clientName").equals("满洲里大阳石化产品有限公司")){
                 itemstra= mdata.get(j).get("berthId").toString()+"华东厂";
				 if(!listStr.contains(itemstra)){
					 listStr.add(itemstra);
					 map.put(itemstra, mdata.get(j).get("count"));
				 }  
			   }else if(mdata.get(j).get("clientName").equals("碧辟(中国)工业油品有限公司")){
				   itemstra= mdata.get(j).get("berthId").toString()+"BP厂";
					 if(!listStr.contains(itemstra)){
						 listStr.add(itemstra);
						 map.put(itemstra, mdata.get(j).get("count"));
					 }  
			   }
		   }
		   
		   if(data!=null&&data.size()>0){
			   String itemstr;
		    for(int i=0;i<data.size();i++){
		    	if(data.get(i).get("productName")!=null){
		    	itemstr=data.get(i).get("berthId").toString()+data.get(i).get("productName").toString();
		    	if(!listStr.contains(itemstr)){
		    	    listStr.add(itemstr);	
		    		map.put(itemstr, data.get(i).get("count"));//1乙二醇
		    		map.put(data.get(i).get("berthId").toString(), Common.add(map.get(data.get(i).get("berthId").toString()), data.get(i).get("count"),3));	
		    	}
		    }
		   }
		    DecimalFormat df = new DecimalFormat("0.00");
		    for(int j=0;j<listStr.size();j++){
		    	     map.put(listStr.get(j), df.format(Common.div(Common.mul(map.get(listStr.get(j)), 100,3),DateUtils.getHoursOfMonth(report.getStartTime(),report.getEndTime()), 2))+"%");
		    }
		   }
			oaMsg.getData().add(map);
		} catch (Exception re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
	@Override
	public OaMsg getYearPipe(ReportDto report) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();
		report.setProductId(4);
		data.add(0, (Map<String, Object>) getYearPipeInboundData(report).getData().get(0));
		report.setProductId(15);
		data.add(1, (Map<String, Object>) getYearPipeInboundData(report).getData().get(0));
		report.setProductId(14);
		report.setClientId(59);
		data.add(2, (Map<String, Object>) getYearPipeInboundData(report).getData().get(0));
		report.setProductId(14);
		report.setClientId(4027);
		data.add(3, (Map<String, Object>) getYearPipeInboundData(report).getData().get(0));
		data.add(4, (Map<String, Object>) getYearPipeOutboundData(report).getData().get(0));
		report.setProductId(13);
		report.setClientId(0);
		data.add(5, (Map<String, Object>) getYearPipeInboundData(report).getData().get(0));
		data.add(6, (Map<String, Object>) getYearPipeOutboundData(report).getData().get(0));
		report.setProductId(19);
		data.add(7, (Map<String, Object>) getYearPipeInboundData(report).getData().get(0));
		report.setProductId(17);
		data.add(8, (Map<String, Object>) getYearPipeInboundData(report).getData().get(0));
		report.setProductId(18);
		data.add(9, (Map<String, Object>) getYearPipeOutboundData(report).getData().get(0));
		oaMsg.getData().addAll(data);
	} catch (Exception re) {
		LOG.error("查询失败", re);
		oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
	}
	return oaMsg;
		
	}
	@Override
	public OaMsg getProductionTarget(ReportDto report) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		Map<String,Object> data=new HashMap<String,Object>();
		data.put("year", report.getStatisYear());
		data.put("month", report.getStatisMonth());
		report.setType(1);
		Map<String,Object> inMap=reportDao.getInboundArrival(report);
		Map<String,Object> outMap=reportDao.getOutbountArrival(report);
		data.put("numa", Common.add(outMap.get("num"), inMap.get("num"),3));//本月码头吞吐量
		data.put("numb", "");//同比减少 
		data.put("numc", Common.add(outMap.get("count"), inMap.get("count"),3));//作业船舶
		data.put("numd", "");//同比减少
	 	data.put("nume", inMap.get("num"));//船舶进货量
	 	data.put("numf", inMap.get("count"));//艘
	 	data.put("numg", outMap.get("num"));//船舶发货量
	 	data.put("numh", outMap.get("count"));//艘
	 	Map<String,Object> truckMap=reportDao.getTruckData(report);
	 	data.put("numi", truckMap.get("num"));//装车站发货量
	 	data.put("numj", truckMap.get("count"));//车次
	 	report.setType(2);
	 	Map<String,Object> inPassMap=reportDao.getInboundArrival(report);
		Map<String,Object> outPassMap=reportDao.getOutbountArrival(report);
	 	data.put("numk", inPassMap.get("num"));//通过单位码头船舶进货量
	 	data.put("numl", inPassMap.get("count"));//艘
	 	data.put("numm", outPassMap.get("num"));//船舶发货量
	 	data.put("numn", outPassMap.get("count"));//艘
	 	report.setType(3);
	 	Map<String,Object> inZSMap=reportDao.getInboundArrival(report);
		Map<String,Object> outZSMap=reportDao.getOutbountArrival(report);
		data.put("numo", Common.add(inZSMap.get("num"), outZSMap.get("num"),3));//转输
	 	data.put("nump", Common.add(inZSMap.get("count"), outZSMap.get("count"),3));//次
	 	data.put("numq", inZSMap.get("num"));//输入
	 	data.put("numr", inZSMap.get("count"));//次
	 	data.put("nums", outZSMap.get("num"));//输出
	 	data.put("numt", outZSMap.get("count"));//次
	 	
	 	
	 	
	 	data.put("numu", outZSMap.get("count"));//月度平均储存量
	 	data.put("numv", outZSMap.get("count"));//占最大储存量
	 	
	 	report.setProductName("甲醇");
	 	Map<String,Object> productInboundNum1=reportDao.getInboundTankNum(report);
	 	Map<String,Object> productOutboundNum1=reportDao.getOutboundTankNum(report);
	 	data.put("numw", outZSMap.get("count"));//甲 醇：保税罐储存量
	 	data.put("numx", outZSMap.get("count"));//非保税罐储存量
	 	data.put("numy", outZSMap.get("count"));//乙二醇：保税罐储存量
	 	data.put("numz", outZSMap.get("count"));//非保税罐储存量
	 	data.put("numaa", outZSMap.get("count"));//成品油：汽油储存量
	 	data.put("numab", outZSMap.get("count"));//0#柴油储存量
	 	
	 	
	 	
		report.setStatisMonth(null);
	 	report.setType(1);
	 	Map<String,Object> inAllMap=reportDao.getInboundArrival(report);
		Map<String,Object> outAllMap=reportDao.getOutbountArrival(report);
	 	
		data.put("numac", Common.add(inAllMap.get("num"), outAllMap.get("num"),3));//全年累计吞吐量
		data.put("numad", "");//同比减少
		data.put("numae", Common.add(inAllMap.get("count"), outAllMap.get("count"),3));//总作业船舶艘
		data.put("numaf", "");//同比减少
		data.put("numag", inAllMap.get("num"));//船舶进货量
		data.put("numah", inAllMap.get("count"));//艘
		data.put("numai", outAllMap.get("num"));//船舶发货量
		data.put("numaj", outAllMap.get("count"));//艘
		
		
		Map<String,Object> truckAllMap=reportDao.getTruckData(report);
		data.put("numak", truckAllMap.get("num"));//车发量
		data.put("numal", truckAllMap.get("count"));//车次
		
		report.setType(2);
	 	Map<String,Object> inPassAllMap=reportDao.getInboundArrival(report);
		Map<String,Object> outPassAllMap=reportDao.getOutbountArrival(report);
		
		data.put("numam", Common.add(inPassAllMap.get("num"), outPassAllMap.get("num"),3));//累计通过单位吞吐量
		data.put("numan", Common.add(inPassAllMap.get("count"), outPassAllMap.get("count"),3));//艘
		data.put("numao", inPassAllMap.get("num"));//通过单位船舶进货量
		data.put("numap", inPassAllMap.get("count"));//艘
		data.put("numaq", outPassAllMap.get("num"));//船舶发货量
		data.put("numar",  outPassAllMap.get("count"));//艘
		
		report.setType(3);
	 	Map<String,Object> inZSAllMap=reportDao.getInboundArrival(report);
		Map<String,Object> outZSAllMap=reportDao.getOutbountArrival(report);
		
		data.put("numas", Common.add(inZSAllMap.get("num"), outZSAllMap.get("num"),3));//累计转输
		data.put("numat",  Common.add(inZSAllMap.get("count"), outZSAllMap.get("count"),3));//次
		data.put("numau", inZSAllMap.get("num"));//输入
		data.put("numav", inZSAllMap.get("count"));//次
		data.put("numaw", outZSAllMap.get("num"));//输出
		data.put("numax", outZSAllMap.get("count"));//次
		
		oaMsg.getData().add(data);
	} catch (Exception re) {
		LOG.error("查询失败", re);
		oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
	}
	return oaMsg;
	}
      // type=0 获取上年 type=1 获取上月
     public static ReportDto getUpTime(ReportDto reportDto,Integer type){
    	 if(type==1){
    		 Integer month=Integer.valueOf(reportDto.getStatisMonth())-1;
    		 if(month==0){
    			 reportDto.setStatisYear(""+(Integer.valueOf(reportDto.getStatisYear())-1));
    			 reportDto.setStatisMonth("12");
    		 }else{
    			 reportDto.setStatisMonth(month<10?("0"+month):(""+month));
    		 }
    	 }else{
    		 reportDto.setStatisYear(""+(Integer.valueOf(reportDto.getStatisYear())-1));
    	 }
    	 return reportDto;
     }
	@Override
	public OaMsg getOutBoundBook(ReportDto report,PageView pageView) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		List<Map<String,Object>> shipData=reportDao.getOutArrivalBook(report,pageView.getStartRecord(),pageView.getMaxresult());
	     if(shipData!=null&&shipData.size()>0){
	     for(int i=0;i<shipData.size();i++){
	    	 shipData.get(i).put("outFlowData", shipDeliverWorkDao.getOutFlowDataByArrivalId(Integer.valueOf(shipData.get(i).get("arrivalId").toString())));
	    	 shipData.get(i).put("tubeNames", shipDeliverWorkDao.getTubeNamesByArrivalId(Integer.valueOf(shipData.get(i).get("arrivalId").toString())));
	    	 shipData.get(i).put("clientName", shipDeliverWorkDao.getClientNameByArrivalId(Integer.valueOf(shipData.get(i).get("arrivalId").toString())));
	    	 shipData.get(i).put("outboundPlace",reportDao.getOutBoundPlace(Integer.valueOf(shipData.get(i).get("arrivalId").toString())).get("place")); 
	     }	
	     }
		oaMsg.getData().addAll(shipData);
		oaMsg.getMap().put(Constant.TOTALRECORD,reportDao.getOutArrivalBookCount(report)+"");
		
		} catch (Exception re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
	
	
	
}
