/**
 * 
 * @Project:sop
 * @Title:ReportServiceImpl.java
 * @Package:com.skycloud.oa.report.service.impl
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年10月26日 上午10:29:27
 * @Version:SkyCloud版权所有
 */
package com.skycloud.oa.report.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jdom.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dao.VehicleDeliveryStatementDao;
import com.skycloud.oa.outbound.dto.VehicleDeliveryStatementDto;
import com.skycloud.oa.report.dao.ExportDao;
import com.skycloud.oa.report.dao.ReportDao;
import com.skycloud.oa.report.dao.ThroughtMsgDao;
import com.skycloud.oa.report.dto.ExportDto;
import com.skycloud.oa.report.dto.ReportDto;
import com.skycloud.oa.report.model.ThroughtMsg;
import com.skycloud.oa.report.service.ReportDataService;
import com.skycloud.oa.report.service.ReportService;
import com.skycloud.oa.report.utils.DateUtils;
import com.skycloud.oa.report.utils.FormatUtils;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.ExcelUtil;
import com.skycloud.oa.utils.ExcelUtil.CallBack;
import com.skycloud.oa.utils.OaMsg;
@Service
public class ReportServiceImpl implements ReportService 
{
	private static Logger logger = Logger.getLogger(ReportServiceImpl.class);
	
	@Autowired
	private ReportDao reportDao;
	@Autowired
	private ReportDataService reDataService;
	@Autowired
	private ExportDao exportDao;
	@Autowired
	private VehicleDeliveryStatementDao vehicleDeliveryStatementDao ;
	@Autowired
	private ThroughtMsgDao throughtMsgDao;
	@Override
	public OaMsg list(ReportDto report, PageView pageView) 
 {
		OaMsg oaMsg = new OaMsg();
		try {
			if (report.getModule() == 1) {
				oaMsg.getData().addAll(reportDao.list(report, pageView));
				oaMsg.getMap().put(Constant.TOTALRECORD, reportDao.getCount(report) + "");
			} else if (report.getModule() == 2) {
				oaMsg.getData().addAll(reportDao.list(report, pageView));
			} else if (report.getModule() == 3) {
				oaMsg.getData().addAll(reportDao.list(report, pageView));
			} else if (report.getModule() == 4) {
				List<Map<String, Object>> ndata = new ArrayList<Map<String, Object>>();
				String startTime = report.getStartTime();
				String endTime = report.getEndTime();
				String year = startTime.substring(0, 4);
				int month = Integer.valueOf(startTime.substring(5, 7));
				String startTimeOfYear = startTime.substring(0, 4) + "-01-01";
				ExportDto export = new ExportDto();
				export.setStartTime(startTime);
				export.setEndTime(endTime);
				Map<String, Object> data, maxNumData, map;
				String[] productName = { "甲醇", "甲醇", "甲醇", "甲醇", "乙二醇", "乙二醇", "乙二醇", "乙二醇", "乙二醇", "汽油", "柴油", "苯",
						"正构烷烃", "直链烷基苯" };
				String[] tankType = { "保税非包罐", "外贸", "内贸", "保税包罐", "保税非包罐", "外贸", "内贸", "保税包罐", "SABIC包罐", "", "", "",
						"", "" };
				for (int i = 0; i < 14; i++) {
					map = new HashMap<String, Object>();
					map.put("productName", productName[i]);
					map.put("tankType", tankType[i]);
					map.put("maxNum", 0);
					map.put("inboundNum", 0);
					map.put("totalInboundNum", 0);
					map.put("turnRate", 0);
					map.put("totalTurnRate", 0);
					ndata.add(map);
				}

				Double accRate = 0D;
				// 甲醇保税（不含1241 Methanex Asia Pacific Ltd，1627 SAUDI BASIC
				// INDUSTRIES CORPORATION ）
				export.setProductId(4);
				export.setNoClientId("1241,1627");//
				export.setTaxTypes("3"); // 1内贸 2外贸 3 保税
				export.setTaxType(2);
				export.setStorageType(2);
				data = exportDao.findTurnRate(export);
				maxNumData = exportDao.getMaxNumOfTank(export);

				ndata.get(0).put("maxNum", maxNumData.get("tankNum"));
				ndata.get(0).put("inboundNum", data.get("totalAmount"));

				accRate = Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2);
				export.setStartTime(startTimeOfYear);
				data = exportDao.findTurnRate(export);
				ndata.get(0).put("totalInboundNum", data.get("totalAmount"));
				// 累计周转率
				ndata.get(0).put("turnRate", accRate);
				for (int i = 1; i < month; i++) {
					export.setStartTime(year + "-" + (i < 10 ? ("0" + i) : i) + "-01");
					export.setEndTime(DateUtils.getLastDayOfMonth(year + "-" + (i < 10 ? ("0" + i) : i)));
					data = exportDao.findTurnRate(export);
					maxNumData = exportDao.getMaxNumOfTank(export);
					accRate = Common.add(accRate, Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2), 2);
				}
				ndata.get(0).put("totalTurnRate", accRate);
				export.setEndTime(endTime);

				// 甲醇非保税
				export.setStartTime(startTime);
				export.setTaxTypes("2");
				export.setTaxType(1);
				export.setStorageType(null);
				data = exportDao.findTurnRate(export);
				maxNumData = exportDao.getMaxNumOfTank(export);
				ndata.get(1).put("maxNum", maxNumData.get("tankNum"));
				ndata.get(1).put("inboundNum", data.get("totalAmount"));

				accRate = Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2);
				export.setStartTime(startTimeOfYear);
				data = exportDao.findTurnRate(export);
				ndata.get(1).put("totalInboundNum", data.get("totalAmount"));

				ndata.get(1).put("turnRate", accRate);
				for (int i = 1; i < month; i++) {
					export.setStartTime(year + "-" + (i < 10 ? ("0" + i) : i) + "-01");
					export.setEndTime(DateUtils.getLastDayOfMonth(year + "-" + (i < 10 ? ("0" + i) : i)));
					data = exportDao.findTurnRate(export);
					maxNumData = exportDao.getMaxNumOfTank(export);
					accRate = Common.add(accRate, Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2), 2);
				}
				ndata.get(1).put("totalTurnRate", accRate);
				export.setEndTime(endTime);

				// 甲醇内贸
				export.setStartTime(startTime);
				export.setTaxTypes("1");// 甲醇非保税是外贸+内贸
				export.setTaxType(0);
				export.setStorageType(null);
				data = exportDao.findTurnRate(export);
				maxNumData = exportDao.getMaxNumOfTank(export);
				ndata.get(2).put("maxNum", maxNumData.get("tankNum"));
				ndata.get(2).put("inboundNum", data.get("totalAmount"));
				accRate = Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2);

				export.setStartTime(startTimeOfYear);
				data = exportDao.findTurnRate(export);
				ndata.get(2).put("totalInboundNum", data.get("totalAmount"));

				ndata.get(2).put("turnRate", accRate);
				for (int i = 1; i < month; i++) {
					export.setStartTime(year + "-" + (i < 10 ? ("0" + i) : i) + "-01");
					export.setEndTime(DateUtils.getLastDayOfMonth(year + "-" + (i < 10 ? ("0" + i) : i)));
					data = exportDao.findTurnRate(export);
					maxNumData = exportDao.getMaxNumOfTank(export);
					accRate = Common.add(accRate, Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2), 2);
				}
				ndata.get(2).put("totalTurnRate", accRate);
				export.setEndTime(endTime);

				// 甲醇包罐
				export.setStartTime(startTime);
				export.setNoClientId(null);
				export.setYesClientId(null);
				export.setTaxTypes("3");
				export.setTaxType(3);
				export.setStorageType(3);
				data = exportDao.findTurnRate(export);
				maxNumData = exportDao.getMaxNumOfTank(export);
				ndata.get(3).put("maxNum", maxNumData.get("tankNum"));
				ndata.get(3).put("inboundNum", data.get("totalAmount"));
				accRate = Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2);

				export.setStartTime(startTimeOfYear);
				data = exportDao.findTurnRate(export);
				ndata.get(3).put("totalInboundNum", data.get("totalAmount"));

				ndata.get(3).put("turnRate", accRate);
				for (int i = 1; i < month; i++) {
					export.setStartTime(year + "-" + (i < 10 ? ("0" + i) : i) + "-01");
					export.setEndTime(DateUtils.getLastDayOfMonth(year + "-" + (i < 10 ? ("0" + i) : i)));
					data = exportDao.findTurnRate(export);
					maxNumData = exportDao.getMaxNumOfTank(export);
					accRate = Common.add(accRate, Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2), 2);
				}
				ndata.get(3).put("totalTurnRate", accRate);
				export.setEndTime(endTime);

				// 乙二醇保税
				export.setStartTime(startTime);
				export.setProductId(2);
				export.setNoClientId("1241,1627");
				export.setYesClientId(null);
				export.setTaxTypes("3"); // 1内贸 2外贸 3 保税
				export.setTaxType(2);
				export.setStorageType(2);
				data = exportDao.findTurnRate(export);
				maxNumData = exportDao.getMaxNumOfTank(export);
				ndata.get(4).put("maxNum", maxNumData.get("tankNum"));
				ndata.get(4).put("inboundNum", data.get("totalAmount"));
				accRate = Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2);

				export.setStartTime(startTimeOfYear);
				data = exportDao.findTurnRate(export);
				ndata.get(4).put("totalInboundNum", data.get("totalAmount"));

				ndata.get(4).put("turnRate", accRate);
				for (int i = 1; i < month; i++) {
					export.setStartTime(year + "-" + (i < 10 ? ("0" + i) : i) + "-01");
					export.setEndTime(DateUtils.getLastDayOfMonth(year + "-" + (i < 10 ? ("0" + i) : i)));
					data = exportDao.findTurnRate(export);
					maxNumData = exportDao.getMaxNumOfTank(export);
					accRate = Common.add(accRate, Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2), 2);
				}
				ndata.get(4).put("totalTurnRate", accRate);
				export.setEndTime(endTime);

				// 乙二醇非保税
				export.setStartTime(startTime);
				export.setTaxTypes("2");
				export.setTaxType(1);
				export.setStorageType(null);
				data = exportDao.findTurnRate(export);
				maxNumData = exportDao.getMaxNumOfTank(export);
				ndata.get(5).put("maxNum", maxNumData.get("tankNum"));
				ndata.get(5).put("inboundNum", data.get("totalAmount"));
				accRate = Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2);

				export.setStartTime(startTimeOfYear);
				data = exportDao.findTurnRate(export);
				ndata.get(5).put("totalInboundNum", data.get("totalAmount"));

				ndata.get(5).put("turnRate", accRate);
				for (int i = 1; i < month; i++) {
					export.setStartTime(year + "-" + (i < 10 ? ("0" + i) : i) + "-01");
					export.setEndTime(DateUtils.getLastDayOfMonth(year + "-" + (i < 10 ? ("0" + i) : i)));
					data = exportDao.findTurnRate(export);
					maxNumData = exportDao.getMaxNumOfTank(export);
					accRate = Common.add(accRate, Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2), 2);
				}
				ndata.get(5).put("totalTurnRate", accRate);
				export.setEndTime(endTime);

				// 乙二醇内贸
				export.setStartTime(startTime);
				export.setTaxTypes("1");
				export.setTaxType(0);
				export.setStorageType(null);
				data = exportDao.findTurnRate(export);
				maxNumData = exportDao.getMaxNumOfTank(export);
				ndata.get(6).put("maxNum", maxNumData.get("tankNum"));
				ndata.get(6).put("inboundNum", data.get("totalAmount"));
				accRate = Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2);

				export.setStartTime(startTimeOfYear);
				data = exportDao.findTurnRate(export);
				ndata.get(6).put("totalInboundNum", data.get("totalAmount"));

				ndata.get(6).put("turnRate", accRate);
				for (int i = 1; i < month; i++) {
					export.setStartTime(year + "-" + (i < 10 ? ("0" + i) : i) + "-01");
					export.setEndTime(DateUtils.getLastDayOfMonth(year + "-" + (i < 10 ? ("0" + i) : i)));
					data = exportDao.findTurnRate(export);
					maxNumData = exportDao.getMaxNumOfTank(export);
					accRate = Common.add(accRate, Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2), 2);
				}
				ndata.get(6).put("totalTurnRate", accRate);
				export.setEndTime(endTime);
				// 乙二醇包罐
				export.setStartTime(startTime);
				export.setNoClientId(null);
				export.setYesClientId(null);
				export.setTaxTypes("3");
				export.setTaxType(3);
				export.setStorageType(3);
				data = exportDao.findTurnRate(export);
				maxNumData = exportDao.getMaxNumOfTank(export);
				ndata.get(7).put("maxNum", maxNumData.get("tankNum"));
				ndata.get(7).put("inboundNum", data.get("totalAmount"));
				accRate = Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2);

				export.setStartTime(startTimeOfYear);
				data = exportDao.findTurnRate(export);
				ndata.get(7).put("totalInboundNum", data.get("totalAmount"));

				ndata.get(7).put("turnRate", accRate);
				for (int i = 1; i < month; i++) {
					export.setStartTime(year + "-" + (i < 10 ? ("0" + i) : i) + "-01");
					export.setEndTime(DateUtils.getLastDayOfMonth(year + "-" + (i < 10 ? ("0" + i) : i)));
					data = exportDao.findTurnRate(export);
					maxNumData = exportDao.getMaxNumOfTank(export);
					accRate = Common.add(accRate, Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2), 2);
				}
				ndata.get(7).put("totalTurnRate", accRate);
				export.setEndTime(endTime);
//				// 乙二醇包罐
//				export.setStartTime(startTime);
//				export.setNoClientId(null);
//				export.setYesClientId("1241,1627");
//				export.setTaxTypes("3");
//				export.setTaxType(3);
//				data = exportDao.findTurnRate(export);
//				maxNumData = exportDao.getMaxNumOfTank(export);
//				ndata.get(8).put("maxNum", maxNumData.get("tankNum"));
//				ndata.get(8).put("inboundNum", data.get("totalAmount"));
//				accRate = Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2);
//
//				export.setStartTime(startTimeOfYear);
//				data = exportDao.findTurnRate(export);
//				ndata.get(8).put("totalInboundNum", data.get("totalAmount"));
//
//				ndata.get(8).put("turnRate", accRate);
//				for (int i = 1; i < month; i++) {
//					export.setStartTime(year + "-" + (i < 10 ? ("0" + i) : i) + "-01");
//					export.setEndTime(DateUtils.getLastDayOfMonth(year + "-" + (i < 10 ? ("0" + i) : i)));
//					data = exportDao.findTurnRate(export);
//					maxNumData = exportDao.getMaxNumOfTank(export);
//					accRate = Common.add(accRate, Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2), 2);
//				}
//				ndata.get(8).put("totalTurnRate", accRate);
//				export.setEndTime(endTime);

				// 汽油
				export.setStartTime(startTime);
				export.setProductId(1);
				export.setYesClientId(null);
				export.setTaxTypes(null);
				export.setTaxType(null);
				export.setStorageType(null);
				data = exportDao.findTurnRate(export);
				maxNumData = exportDao.getMaxNumOfTank(export);
				ndata.get(9).put("maxNum", maxNumData.get("tankNum"));
				ndata.get(9).put("inboundNum", data.get("totalAmount"));
				accRate = Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2);

				export.setStartTime(startTimeOfYear);
				data = exportDao.findTurnRate(export);
				ndata.get(9).put("totalInboundNum", data.get("totalAmount"));

				ndata.get(9).put("turnRate", accRate);
				for (int i = 1; i < month; i++) {
					export.setStartTime(year + "-" + (i < 10 ? ("0" + i) : i) + "-01");
					export.setEndTime(DateUtils.getLastDayOfMonth(year + "-" + (i < 10 ? ("0" + i) : i)));
					data = exportDao.findTurnRate(export);
					maxNumData = exportDao.getMaxNumOfTank(export);
					accRate = Common.add(accRate, Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2), 2);
				}
				ndata.get(9).put("totalTurnRate", accRate);
				export.setEndTime(endTime);

				// 柴油
				export.setStartTime(startTime);
				export.setProductId(3);
				data = exportDao.findTurnRate(export);
				maxNumData = exportDao.getMaxNumOfTank(export);
				ndata.get(10).put("maxNum", maxNumData.get("tankNum"));
				ndata.get(10).put("inboundNum", data.get("totalAmount"));
				accRate = Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2);

				export.setStartTime(startTimeOfYear);
				data = exportDao.findTurnRate(export);
				ndata.get(10).put("totalInboundNum", data.get("totalAmount"));

				ndata.get(10).put("turnRate", accRate);
				for (int i = 1; i < month; i++) {
					export.setStartTime(year + "-" + (i < 10 ? ("0" + i) : i) + "-01");
					export.setEndTime(DateUtils.getLastDayOfMonth(year + "-" + (i < 10 ? ("0" + i) : i)));
					data = exportDao.findTurnRate(export);
					maxNumData = exportDao.getMaxNumOfTank(export);
					accRate = Common.add(accRate, Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2), 2);
				}
				ndata.get(10).put("totalTurnRate", accRate);
				export.setEndTime(endTime);

				// 苯
				export.setStartTime(startTime);
				export.setProductId(19);
				data = exportDao.findTurnRate(export);
				maxNumData = exportDao.getMaxNumOfTank(export);
				ndata.get(11).put("maxNum", maxNumData.get("tankNum"));
				ndata.get(11).put("inboundNum", data.get("totalAmount"));
				accRate = Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2);

				export.setStartTime(startTimeOfYear);
				data = exportDao.findTurnRate(export);
				ndata.get(11).put("totalInboundNum", data.get("totalAmount"));

				ndata.get(11).put("turnRate", accRate);
				for (int i = 1; i < month; i++) {
					export.setStartTime(year + "-" + (i < 10 ? ("0" + i) : i) + "-01");
					export.setEndTime(DateUtils.getLastDayOfMonth(year + "-" + (i < 10 ? ("0" + i) : i)));
					data = exportDao.findTurnRate(export);
					maxNumData = exportDao.getMaxNumOfTank(export);
					accRate = Common.add(accRate, Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2), 2);
				}
				ndata.get(11).put("totalTurnRate", accRate);
				export.setEndTime(endTime);

				// 正构烷烃
				export.setStartTime(startTime);
				export.setProductId(17);
				data = exportDao.findTurnRate(export);
				maxNumData = exportDao.getMaxNumOfTank(export);
				ndata.get(12).put("maxNum", maxNumData.get("tankNum"));
				ndata.get(12).put("inboundNum", data.get("totalAmount"));
				accRate = Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2);

				export.setStartTime(startTimeOfYear);
				data = exportDao.findTurnRate(export);
				ndata.get(12).put("totalInboundNum", data.get("totalAmount"));

				ndata.get(12).put("turnRate", accRate);
				for (int i = 1; i < month; i++) {
					export.setStartTime(year + "-" + (i < 10 ? ("0" + i) : i) + "-01");
					export.setEndTime(DateUtils.getLastDayOfMonth(year + "-" + (i < 10 ? ("0" + i) : i)));
					data = exportDao.findTurnRate(export);
					maxNumData = exportDao.getMaxNumOfTank(export);
					accRate = Common.add(accRate, Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2), 2);
				}
				ndata.get(12).put("totalTurnRate", accRate);
				export.setEndTime(endTime);

				// 直链烷基苯
				export.setStartTime(startTime);
				export.setProductId(18);
				data = exportDao.findTurnRate(export);
				maxNumData = exportDao.getMaxNumOfTank(export);
				ndata.get(13).put("maxNum", maxNumData.get("tankNum"));
				ndata.get(13).put("inboundNum", data.get("totalAmount"));
				accRate = Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2);

				export.setStartTime(startTimeOfYear);
				data = exportDao.findTurnRate(export);
				ndata.get(13).put("totalInboundNum", data.get("totalAmount"));

				ndata.get(13).put("turnRate", accRate);
				for (int i = 1; i < month; i++) {
					export.setStartTime(year + "-" + (i < 10 ? ("0" + i) : i) + "-01");
					export.setEndTime(DateUtils.getLastDayOfMonth(year + "-" + (i < 10 ? ("0" + i) : i)));
					data = exportDao.findTurnRate(export);
					maxNumData = exportDao.getMaxNumOfTank(export);
					accRate = Common.add(accRate, Common.div(data.get("totalAmount"), maxNumData.get("tankNum"), 2), 2);
				}
				ndata.get(13).put("totalTurnRate", accRate);
				oaMsg.getData().addAll(ndata);
			} else if (report.getModule() == 5) {
				Map<String, Object> mapThroughtMsg=throughtMsgDao.getThroughtMsg(DateUtils.getLongTime(report.getStartTime()+" 00:00:00"));
				if(mapThroughtMsg!=null&&mapThroughtMsg.get("id")!=null){
					oaMsg.getMap().put("id",mapThroughtMsg.get("id").toString());
					oaMsg.getMap().put("content",mapThroughtMsg.get("content").toString());
				}
				List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
				boolean isYear = "2016".equals(report.getStartTime().substring(0, 4));
				Map<String, Object> map, mapA, mapB, mapC, mapD;
				String startTime = report.getStartTime();
				String endTime = report.getEndTime();
				ExportDto export = new ExportDto();
				ExportDto exportLastYear = new ExportDto();
				ExportDto exportLastAcc = new ExportDto();// 上累
				export.setStartTime(startTime);
				export.setEndTime(endTime);
				exportLastAcc.setStartTime(startTime);
				exportLastAcc.setAddUp(1);
				if (startTime.substring(5, 7).equals("01")) {
					exportLastAcc.setEndTime((Integer.valueOf(startTime.substring(0, 4)) - 1) + "-01-01");
				} else {
					exportLastAcc.setEndTime(DateUtils.getLastDayOfLastMonth(endTime));
				}
				exportLastYear.setStartTime(DateUtils.getLastYearTime(report.getStartTime()));
				exportLastYear.setEndTime(DateUtils.getLastDayOfMonth(DateUtils.getLastYearTime(report.getEndTime())));

				int[] inProType = { 2, 4, 19, 17, 1, 3 };// 入库接卸
				int[] inProTypePass = { 13, 15, 4, 14, 14 };// 入库通过接卸
				int[] inClientPass = { 0, 0, 0, 59, 4027 };// 入库通过接卸货主
				int[] outShipProType = { 2, 4, 18, 1, 3 };// 船舶发货
				int[] outPasShipProType = { 13, 14 };// 通过发货
				int[] outPasShipClient = { 0, 4027 };// 通过发货货主
				int[] outTruckProType = { 2, 4, 1, 3 };// 车发发货
				int[] pro = { 19, 17, 4,18 };// 转输输出
				String[] tabs = { "一、码头吞吐量", "1、进货总量", "（1）本公司接卸量", "其中：乙二醇 ", " 甲醇", "苯", "正构烷烃", " 汽油", " 柴油",
						"（2）通过单位接卸量", "其中：LPG", "VCM", "甲醇", "BP基础油	", "华东基础油", "2、发货总量", "（1）本公司发货量", "其中：乙二醇", "甲醇",
						"直链烷基", "汽油", "柴油", "（2）通过单位发货量	", "其中：LPG", " 华东基础油", "二、灌装站发货总量", "其中：乙二醇 ", " 甲醇", "汽油",
						"柴油", "三、转输", "（1） 输入", "其中：直链烷基苯", "（2） 输出", "其中：苯", "正构烷烃", "甲醇","直链烷基苯" };
				for (int i = 0; i < 38; i++) {
					map = new HashMap<String, Object>();
					map.put("id", i + 1);
					map.put("tab", tabs[i]);
					map.put("totalNum", 0);// 吞吐量/车发量
					map.put("lastYTotalNum", 0);// 去年同期
					map.put("numRate", 0);// 重量增长率
					map.put("count", 0);// 车次
					map.put("lastYCount", 0);// 去年同期车次
					map.put("countRate", 0);// 车次增长率
					map.put("accLastMNum", 0);// 上累吞吐量
					map.put("accLastMCount", 0);// 上累车船次
					map.put("accTotalNum", 0);// 累计吞吐量/车发量
					map.put("accLastYTotalNum", 0);// 累计去年同期
					map.put("accNumRate", 0);// 累计重量增长率
					map.put("accCount", 0);// 累计车次
					data.add(map);
				}
				// 接卸
				for (int a = 0, len = inProType.length; a < len; a++) {
					export.setProductId(inProType[a]);
					export.setType(1);
					export.setAddUp(0);
					mapA = exportDao.queryInThrough(export);// 入库

					exportLastYear.setProductId(inProType[a]);
					exportLastYear.setType(1);
					exportLastYear.setAddUp(0);
					if (isYear) {
						exportLastYear.setIsYearType(1);
						mapB = exportDao.queryThroughtRateOf2015(exportLastYear);
						exportLastYear.setAddUp(1);
						mapD = exportDao.queryThroughtRateOf2015(exportLastYear);
					} else {
						mapB = exportDao.queryInThrough(exportLastYear);// 去年同期
						exportLastYear.setAddUp(1);
						mapD = exportDao.queryInThrough(exportLastYear);// 去年入库-累计
					}

					exportLastAcc.setProductId(inProType[a]);
					exportLastAcc.setType(1);
					mapC = exportDao.queryInThrough(exportLastAcc);// 入库-上累计

					data = initData(3 + a, data, mapA, mapB, mapC, mapD);
					data = handleData(3 + a, data);
					data = initData(2, data, mapA, mapB, mapC, mapD);
				}
				data = handleData(2, data);

				// 入库通过
				for (int b = 0, len = inProTypePass.length; b < len; b++) {
					export.setProductId(inProTypePass[b]);
					export.setClientId(inClientPass[b]);
					export.setType(0);
					export.setAddUp(0);
					mapA = exportDao.queryInThrough(export);// 入库通过

					exportLastYear.setProductId(inProTypePass[b]);
					exportLastYear.setClientId(inClientPass[b]);
					exportLastYear.setType(0);
					exportLastYear.setAddUp(0);
					if (isYear) {
						exportLastYear.setIsYearType(2);
						mapB = exportDao.queryThroughtRateOf2015(exportLastYear);
						exportLastYear.setAddUp(1);
						mapD = exportDao.queryThroughtRateOf2015(exportLastYear);
					} else {
						mapB = exportDao.queryInThrough(exportLastYear);// 去年入库
						exportLastYear.setAddUp(1);
						mapD = exportDao.queryInThrough(exportLastYear);// 去年入库通过-累计
					}

					exportLastAcc.setProductId(inProTypePass[b]);
					exportLastAcc.setClientId(inClientPass[b]);
					exportLastAcc.setType(0);
					mapC = exportDao.queryInThrough(exportLastAcc);// 入库通过-上累计

					data = initData(10 + b, data, mapA, mapB, mapC, mapD);
					data = handleData(10 + b, data);
					data = initData(9, data, mapA, mapB, mapC, mapD);
				}
				data = handleData(9, data);
				export.setClientId(0);
				exportLastYear.setClientId(0);
				exportLastAcc.setClientId(0);
				// 船发出库
				for (int c = 0, len = outShipProType.length; c < len; c++) {
					export.setProductId(outShipProType[c]);
					export.setType(1);
					export.setAddUp(0);
					mapA = exportDao.queryOutThrough(export);

					exportLastYear.setProductId(outShipProType[c]);
					exportLastYear.setType(1);
					exportLastYear.setAddUp(0);
					if (isYear) {
						exportLastYear.setIsYearType(3);
						mapB = exportDao.queryThroughtRateOf2015(exportLastYear);
						exportLastYear.setAddUp(1);
						mapD = exportDao.queryThroughtRateOf2015(exportLastYear);
					} else {
						mapB = exportDao.queryOutThrough(exportLastYear);
						exportLastYear.setAddUp(1);
						mapD = exportDao.queryOutThrough(exportLastYear);
					}

					exportLastAcc.setProductId(outShipProType[c]);
					exportLastAcc.setType(1);
					mapC = exportDao.queryOutThrough(exportLastAcc);

					data = initData(17 + c, data, mapA, mapB, mapC, mapD);
					data = handleData(17 + c, data);
					data = initData(16, data, mapA, mapB, mapC, mapD);
				}
				data = handleData(16, data);

				// 出库-通过
				for (int d = 0, len = outPasShipProType.length; d < len; d++) {
					export.setProductId(outPasShipProType[d]);
					export.setClientId(outPasShipClient[d]);
					export.setType(0);
					export.setAddUp(0);
					mapA = exportDao.queryOutThrough(export);

					exportLastYear.setProductId(outPasShipProType[d]);
					exportLastYear.setClientId(outPasShipClient[d]);
					exportLastYear.setType(0);
					exportLastYear.setAddUp(0);
					if (isYear) {
						exportLastYear.setIsYearType(4);
						mapB = exportDao.queryThroughtRateOf2015(exportLastYear);
						exportLastYear.setAddUp(1);
						mapD = exportDao.queryThroughtRateOf2015(exportLastYear);
					} else {
						mapB = exportDao.queryOutThrough(exportLastYear);
						exportLastYear.setAddUp(1);
						mapD = exportDao.queryOutThrough(exportLastYear);
					}

					exportLastAcc.setProductId(outPasShipProType[d]);
					exportLastAcc.setClientId(outPasShipClient[d]);
					exportLastAcc.setType(0);
					mapC = exportDao.queryOutThrough(exportLastAcc);

					data = initData(23 + d, data, mapA, mapB, mapC, mapD);
					data = handleData(23 + d, data);
					data = initData(22, data, mapA, mapB, mapC, mapD);
				}
				data = handleData(22, data);

				export.setClientId(0);
				exportLastYear.setClientId(0);
				exportLastAcc.setClientId(0);
				// 出库-车发
				for (int e = 0, len = outTruckProType.length; e < len; e++) {
					export.setProductId(outTruckProType[e]);
					export.setAddUp(0);
					mapA = exportDao.queryOutCar(export);

					exportLastYear.setProductId(outTruckProType[e]);
					exportLastYear.setAddUp(0);
					if (isYear) {
						exportLastYear.setIsYearType(5);
						mapB = exportDao.queryThroughtRateOf2015(exportLastYear);
						exportLastYear.setAddUp(1);
						mapD = exportDao.queryThroughtRateOf2015(exportLastYear);
					} else {
						mapB = exportDao.queryOutCar(exportLastYear);
						exportLastYear.setAddUp(1);
						mapD = exportDao.queryOutCar(exportLastYear);
					}

					exportLastAcc.setProductId(outTruckProType[e]);
					mapC = exportDao.queryOutCar(exportLastAcc);

					data = initData(26 + e, data, mapA, mapB, mapC, mapD);
					data = handleData(26 + e, data);
					data = initData(25, data, mapA, mapB, mapC, mapD);
				}
				data = handleData(25, data);

				// 转输输入
				export.setProductId(18);
				export.setAddUp(0);
				mapA = exportDao.queryInput(export);

				exportLastYear.setProductId(18);
				exportLastYear.setAddUp(0);
				if (isYear) {
					exportLastYear.setIsYearType(6);
					mapB = exportDao.queryThroughtRateOf2015(exportLastYear);
					exportLastYear.setAddUp(1);
					mapD = exportDao.queryThroughtRateOf2015(exportLastYear);
				} else {
					mapB = exportDao.queryInput(exportLastYear);
					exportLastYear.setAddUp(1);
					mapD = exportDao.queryInput(exportLastYear);
				}

				exportLastAcc.setProductId(18);
				mapC = exportDao.queryInput(exportLastAcc);

				data = initData(31, data, mapA, mapB, mapC, mapD);
				data = handleData(31, data);
				data = initData(32, data, mapA, mapB, mapC, mapD);
				data = handleData(32, data);

				// 转输-输出
				for (int e = 0, len = pro.length; e < len; e++) {
					export.setProductId(pro[e]);
					export.setAddUp(0);
					mapA = exportDao.queryOutput(export);// 转输-输出

					exportLastYear.setProductId(pro[e]);
					exportLastYear.setAddUp(0);
					if (isYear) {
						exportLastYear.setIsYearType(7);
						mapB = exportDao.queryThroughtRateOf2015(exportLastYear);
						exportLastYear.setAddUp(1);
						mapD = exportDao.queryThroughtRateOf2015(exportLastYear);
					} else {
						mapB = exportDao.queryOutput(exportLastYear);// 去年转输-输出
						exportLastYear.setAddUp(1);
						mapD = exportDao.queryOutput(exportLastYear);// 去年转输-输出-累计
					}

					exportLastAcc.setProductId(pro[e]);
					mapC = exportDao.queryOutput(exportLastAcc);// 转输-输出-累计

					data = initData(34 + e, data, mapA, mapB, mapC, mapD);
					data = handleData(34 + e, data);
					data = initData(33, data, mapA, mapB, mapC, mapD);
				}
				data = handleData(33, data);

				data = addData(1, data, data.get(2), data.get(9));
				data = handleData(1, data);
				data = addData(15, data, data.get(16), data.get(22));
				data = handleData(15, data);
				data = addData(30, data, data.get(31), data.get(33));
				data = handleData(30, data);
				data = addData(0, data, data.get(1), data.get(15));
				data = handleData(0, data);
				oaMsg.getData().addAll(data);

			} else if (report.getModule() == 6) {

			} else if (report.getModule() == 7) {

			} else if (report.getModule() == 8) {

			} else if (report.getModule() == 9) {
				Map<String, Object> map = new HashMap<String, Object>();
				List<Object> data = new ArrayList<Object>();
				List<Map<String, Object>> dataList;
				VehicleDeliveryStatementDto vqDto = new VehicleDeliveryStatementDto();
				vqDto.setStartTime(DateUtils.getLongTime(report.getStartTime() + " 00:00:00"));
				vqDto.setEndTime(DateUtils.getLongTime(report.getEndTime() + " 23:59:59"));
				List<Map<String, Object>> productTypeList = vehicleDeliveryStatementDao.getProductNameTypeMsg(vqDto);
				map.put("productNames", productTypeList);
				if (productTypeList != null && productTypeList.size() > 0) {
					for (int i = 0, len = productTypeList.size(); i <= len; i++) {
						if (i < len) {
							vqDto.setProductId(Integer.valueOf(productTypeList.get(i).get("productId").toString()));
							dataList = vehicleDeliveryStatementDao.getOutBoundDataList(vqDto);
							data.add(getDataTruckInportMap(getTruckInportMap(), dataList));
						}
					}
				}
				map.put("data", data);
				oaMsg.getData().add(map);
			}else if(report.getModule()==16){
				report.setStartTime(DateUtils.getFirstDayOfMonth(report.getStartTime()));
				report.setEndTime(DateUtils.getLastDayOfMonth(report.getEndTime()));
				oaMsg.getData().addAll(reportDao.getPumpShedRotation(report));
				System.out.println(oaMsg.getData());
			}
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (Exception re) {
			logger.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	public List<Map<String,Object>> initData(int item,List<Map<String,Object>> data,Map<String,Object> mapA,Map<String,Object> mapB,Map<String,Object> mapC,Map<String,Object> mapD){
		data.get(item).put("totalNum",Common.add(data.get(item).get("totalNum"), mapA.get("totalNum"), 3));
		data.get(item).put("lastYTotalNum",Common.add(data.get(item).get("lastYTotalNum"), mapB.get("totalNum"), 3));
		data.get(item).put("count",Common.add(data.get(item).get("count"), mapA.get("shipNum"), 0));
		data.get(item).put("lastYCount",Common.add(data.get(item).get("lastYCount"), mapB.get("shipNum"), 0));
		
		data.get(item).put("accLastMNum",Common.add(data.get(item).get("accLastMNum"), mapC.get("totalNum"), 3));
		data.get(item).put("accLastMCount",Common.add(data.get(item).get("accLastMCount"), mapC.get("shipNum"), 0));
		data.get(item).put("accLastYTotalNum",Common.add(data.get(item).get("accLastYTotalNum"), mapD.get("totalNum"), 3));
		return data;
	}
	
	public List<Map<String,Object>> handleData(int item,List<Map<String,Object>> data){
		data.get(item).put("numRate", Common.div(Common.sub(data.get(item).get("totalNum"), data.get(item).get("lastYTotalNum"), 3),data.get(item).get("lastYTotalNum"), 2));
		data.get(item).put("countRate", Common.div(Common.sub(data.get(item).get("count"), data.get(item).get("lastYCount"), 3), data.get(item).get("lastYCount"), 2));
	
		data.get(item).put("accTotalNum", Common.add(data.get(item).get("totalNum"),data.get(item).get("accLastMNum"), 3));
		data.get(item).put("accNumRate",Common.div(Common.sub(data.get(item).get("accTotalNum"),data.get(item).get("accLastYTotalNum"), 3),data.get(item).get("accLastYTotalNum"), 2));
		data.get(item).put("accCount", Common.add(data.get(item).get("count"),data.get(item).get("accLastMCount"), 0));
		
		return data;
	}
	
	public List<Map<String,Object>> addData(int item,List<Map<String,Object>> data,Map<String,Object> map1,Map<String,Object> map2){
		data.get(item).put("totalNum",Common.add(map1.get("totalNum"), map2.get("totalNum"), 3));
		data.get(item).put("lastYTotalNum",Common.add(map1.get("lastYTotalNum"), map2.get("lastYTotalNum"), 3));
		data.get(item).put("count",Common.add(map1.get("count"), map2.get("count"), 0));
		data.get(item).put("lastYCount",Common.add(map1.get("lastYCount"), map2.get("lastYCount"), 0));
		
		data.get(item).put("accLastMNum",Common.add(map1.get("accLastMNum"), map2.get("accLastMNum"), 3));
		data.get(item).put("accLastMCount",Common.add(map1.get("accLastMCount"), map2.get("accLastMCount"), 0));
		data.get(item).put("accLastYTotalNum",Common.add(map1.get("accLastYTotalNum"), map2.get("accLastYTotalNum"), 3));
		return data;
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
	};
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
	};
	
	/**
	 * @Title updateTankLogState
	 * @Descrption:TODO
	 * @param:@param reportDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年8月19日上午10:06:34
	 * @throws
	 */
	@Override
	public OaMsg updateTankLogState(ReportDto reportDto) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			
		} catch (Exception re) {
			logger.error("service获取船舶出库列表失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
	
    //导出管道运输汇总表
	@Override
	public HSSFWorkbook exportYearPipe(HttpServletRequest request,final ReportDto report) throws OAException{
		ExcelUtil excelUtil = new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/yearpipe.xls", new CallBack() {
			@Override
			public void setSheetValue(HSSFSheet sheet) {
				try {
				String year=report.getStatisYear();
				String topmsg=sheet.getRow(0).getCell(0).getStringCellValue();
				topmsg=topmsg.replace("2014", year);
				sheet.getRow(0).getCell(0).setCellValue(topmsg);
				report.setProductId(4);
				//甲醇的进港
				OaMsg oamsg1=reDataService.getYearPipeInboundData(report);
				Map<String,Object> map1=(Map<String, Object>) oamsg1.getData().get(0);
				Double sum1=0d;
					for(int i=4;i<16;i++){
						String item=(i-3)>9?(i-3)+"":"0"+(i-3);
						sheet.getRow(i).getCell(1).setCellValue(getDoubleValue(map1.get(item)));
						sum1=Common.add(sum1, getDoubleValue(map1.get(item)),3);
					}
				sheet.getRow(16).getCell(1).setCellValue(sum1);	
			
				//VCM的进港
				report.setProductId(15);
				OaMsg oamsg2=reDataService.getYearPipeInboundData(report);
				Map<String,Object> map2=(Map<String, Object>) oamsg2.getData().get(0);
				Double sum2=0d;
					for(int i=4;i<16;i++){
						String item=(i-3)>9?(i-3)+"":"0"+(i-3);
						sheet.getRow(i).getCell(2).setCellValue(getDoubleValue(map2.get(item)));
						sum2=Common.add(sum2, getDoubleValue(map2.get(item)),3);
					}
				sheet.getRow(16).getCell(2).setCellValue(sum2);	
				
				//润滑油基础油的进港 BP厂
				report.setProductId(14);
				report.setClientId(59);
				OaMsg oamsg3=reDataService.getYearPipeInboundData(report);
				Map<String,Object> map3=(Map<String, Object>) oamsg3.getData().get(0);
				Double sum3=0d;
					for(int i=4;i<16;i++){
						String item=(i-3)>9?(i-3)+"":"0"+(i-3);
						sheet.getRow(i).getCell(3).setCellValue(getDoubleValue(map3.get(item)));
						sum3=Common.add(sum3, getDoubleValue(map3.get(item)),3);
					}
				sheet.getRow(16).getCell(3).setCellValue(sum3);	
				
				//润滑油基础油的进港 华东厂
				report.setProductId(14);
				report.setClientId(4027);
				OaMsg oamsg4=reDataService.getYearPipeInboundData(report);
				Map<String,Object> map4=(Map<String, Object>) oamsg4.getData().get(0);
				Double sum4=0d;
					for(int i=4;i<16;i++){
						String item=(i-3)>9?(i-3)+"":"0"+(i-3);
						sheet.getRow(i).getCell(4).setCellValue(getDoubleValue(map4.get(item)));
						sum4=Common.add(sum4, getDoubleValue(map4.get(item)),3);
					}
				sheet.getRow(16).getCell(4).setCellValue(sum4);	
				
				//润滑油基础油的出港 华东厂
				report.setProductId(14);
				report.setClientId(4027);
				OaMsg oamsg5=reDataService.getYearPipeOutboundData(report);
				Map<String,Object> map5=(Map<String, Object>) oamsg5.getData().get(0);
				Double sum5=0d;
					for(int i=4;i<16;i++){
						String item=(i-3)>9?(i-3)+"":"0"+(i-3);
						sheet.getRow(i).getCell(5).setCellValue(getDoubleValue(map5.get(item)));
						sum5=Common.add(sum5, getDoubleValue(map5.get(item)),3);
					}
				sheet.getRow(16).getCell(5).setCellValue(sum5);	
				
				
				//丙烷/丁烷的进港
				report.setProductId(13);
				report.setClientId(0);
				OaMsg oamsg6=reDataService.getYearPipeInboundData(report);
				Map<String,Object> map6=(Map<String, Object>) oamsg6.getData().get(0);
				Double sum6=0d;
					for(int i=4;i<16;i++){
						String item=(i-3)>9?(i-3)+"":"0"+(i-3);
						sheet.getRow(i).getCell(6).setCellValue(getDoubleValue(map6.get(item)));
						sum6=Common.add(sum6, getDoubleValue(map6.get(item)),3);
					}
				sheet.getRow(16).getCell(6).setCellValue(sum6);	
				
				//丙烷/丁烷的出港
				report.setProductId(13);
				report.setClientId(0);
				OaMsg oamsg7=reDataService.getYearPipeOutboundData(report);
				Map<String,Object> map7=(Map<String, Object>) oamsg7.getData().get(0);
				Double sum7=0d;
					for(int i=4;i<16;i++){
						String item=(i-3)>9?(i-3)+"":"0"+(i-3);
						sheet.getRow(i).getCell(7).setCellValue(getDoubleValue(map7.get(item)));
						sum7=Common.add(sum7, getDoubleValue(map7.get(item)),3);
					}
				sheet.getRow(16).getCell(7).setCellValue(sum7);	
				//其优势 苯入港
				report.setProductId(19);
				report.setClientId(0);
				OaMsg oamsg8=reDataService.getYearPipeInboundData(report);
				Map<String,Object> map8=(Map<String, Object>) oamsg8.getData().get(0);
				Double sum8=0d;
					for(int i=4;i<16;i++){
						String item=(i-3)>9?(i-3)+"":"0"+(i-3);
						sheet.getRow(i).getCell(8).setCellValue(getDoubleValue(map8.get(item)));
						sum8=Common.add(sum8, getDoubleValue(map8.get(item)),3);
					}
				sheet.getRow(16).getCell(8).setCellValue(sum8);	
				//正构烷烃入港
				report.setProductId(17);
				report.setClientId(0);
				OaMsg oamsg9=reDataService.getYearPipeInboundData(report);
				Map<String,Object> map9=(Map<String, Object>) oamsg9.getData().get(0);
				Double sum9=0d;
					for(int i=4;i<16;i++){
						String item=(i-3)>9?(i-3)+"":"0"+(i-3);
						sheet.getRow(i).getCell(9).setCellValue(getDoubleValue(map9.get(item)));
						sum9=Common.add(sum9, getDoubleValue(map9.get(item)),3);
					}
				sheet.getRow(16).getCell(9).setCellValue(sum9);	
				
				//直链烷基苯出港
				report.setProductId(18);
				report.setClientId(0);
				OaMsg oamsg10=reDataService.getYearPipeOutboundData(report);
				Map<String,Object> map10=(Map<String, Object>) oamsg10.getData().get(0);
				Double sum10=0d;
					for(int i=4;i<16;i++){
						String item=(i-3)>9?(i-3)+"":"0"+(i-3);
						sheet.getRow(i).getCell(10).setCellValue(getDoubleValue(map10.get(item)));
						sum10=Common.add(sum10, getDoubleValue(map10.get(item)),3);
					}
				sheet.getRow(16).getCell(10).setCellValue(sum10);	
				} catch (OAException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		return excelUtil.getWorkbook();
	}
	private String getStringValue(Object object){
		if(object==null||object.toString().equals("null")){
			return "";
		}else{
			return object.toString();
		}
	}
	
	private String getStringValue(Object object,Boolean isPercent){
		String str=getStringValue(object);
		if(isPercent&&"".equals(str)){
			return "0%";
		}else{
			return str;
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

	@Override
	public HSSFWorkbook exportMonthBerth(HttpServletRequest request, final ReportDto report) throws OAException {
		ExcelUtil excelUtil = new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/monthberth.xls", new CallBack() {
			@Override
			public void setSheetValue(HSSFSheet sheet) {
				try {
					String startTime=report.getStartTime();
					String endTime=report.getEndTime();
					sheet.getRow(0).getCell(0).setCellValue(startTime.replace("-", "月")+"到"+endTime.replace("-", "月")+"泊位利用率");
					OaMsg oamsg= reDataService.getMonthBerth(report);
					Map<String,Object> map=(Map<String, Object>)oamsg.getData().get(0);	
					Double a=0d;
					Double b=0d;
					Double c,d,e;
					for(int i=1;i<8;i++){
						a=0D;
						b=0D;
						c=0D;
						d=0D;
						e=0D;
					sheet.getRow(i+1).getCell(1).setCellValue(getStringValue(map.get(i+"乙二醇"),true));
					sheet.getRow(i+1).getCell(2).setCellValue(getStringValue(map.get(i+"甲醇"),true));
					sheet.getRow(i+1).getCell(3).setCellValue(getStringValue(map.get(i+"苯"),true));
					sheet.getRow(i+1).getCell(4).setCellValue(getStringValue(map.get(i+"正构烷烃"),true));
					sheet.getRow(i+1).getCell(5).setCellValue(getStringValue(map.get(i+"直链烷基苯"),true));
					
					if(map.get(i+"0#普通柴油")!=null){a=Double.valueOf(map.get(i+"0#普通柴油").toString().replace("%", "")); }
					if(map.get(i+"0#车用柴油V")!=null){b=Double.valueOf(map.get(i+"0#车用柴油V").toString().replace("%", "")); }
					if(map.get(i+"丙烷/丁烷")!=null){c=Double.valueOf(map.get(i+"丙烷/丁烷").toString().replace("%", "")); }
					if(map.get(i+"丙烷")!=null){d=Double.valueOf(map.get(i+"丙烷").toString().replace("%", "")); }
					if(map.get(i+"丁烷")!=null){e=Double.valueOf(map.get(i+"丁烷").toString().replace("%", "")); }
					
					sheet.getRow(i+1).getCell(6).setCellValue(getStringValue(Common.add(a, b,2)+"%" ,true));
					/*sheet.getRow(i+1).getCell(7).setCellValue(getStringValue(map.get(i+"93#车用汽油V"),true));*/
					sheet.getRow(i+1).getCell(7).setCellValue(getStringValue(map.get(i+"92#车用汽油V"),true));
					sheet.getRow(i+1).getCell(8).setCellValue(getStringValue(Common.add(Common.add(c, d, 2), e, 2)+"%",true));
					sheet.getRow(i+1).getCell(9).setCellValue(getStringValue(map.get(i+"VCM"),true));
					sheet.getRow(i+1).getCell(10).setCellValue(getStringValue(map.get(i+"BP厂"),true));
					sheet.getRow(i+1).getCell(11).setCellValue(getStringValue(map.get(i+"华东厂"),true));
					sheet.getRow(i+1).getCell(12).setCellValue(getStringValue(map.get(i+""),true));
					}
				} catch (OAException e) {
					e.printStackTrace();
				}
			}
		});
		return excelUtil.getWorkbook();
	}

	/**
	 * @Title addorupdateThroughMsg
	 * @Descrption:TODO
	 * @param:@param throughtMsg
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年12月23日上午10:50:21
	 * @throws
	 */
	@Override
	public OaMsg addorupdateThroughMsg(ThroughtMsg throughtMsg)throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			if(throughtMsg.getId()!=null)
				throughtMsgDao.updateThroughtMsg(throughtMsg);
			else
				throughtMsg.setId(throughtMsgDao.addThroughtMsg(throughtMsg));
			oaMsg.getMap().put("id", throughtMsg.getId()+"");
		} catch (RuntimeException e) {
			logger.error("service添加更新生产指标", e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	/**
	 * @Title getBerthDetailList
	 * @Descrption:TODO
	 * @param:@param report
	 * @param:@param pageView
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2017年1月3日下午5:30:11
	 * @throws
	 */
	@Override
	public OaMsg getBerthDetailList(ReportDto report, PageView pageView)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(reportDao.getBerthDetailList(report,pageView.getStartRecord(),pageView.getMaxresult()));
			oaMsg.getMap().put(Constant.TOTALRECORD, reportDao.getBerthDetailCount(report)+"");
		} catch (RuntimeException e) {
			logger.error("service获取泊位利用率详情列表失败", e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
}