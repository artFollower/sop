package com.skycloud.oa.common.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.common.service.ExcelService;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dao.FeeBillDao;
import com.skycloud.oa.feebill.dao.FeeChargeDao;
import com.skycloud.oa.feebill.dao.OtherFeeCargoDao;
import com.skycloud.oa.feebill.dao.OtherFeeDao;
import com.skycloud.oa.feebill.dto.FeeBillDto;
import com.skycloud.oa.feebill.dto.FeeChargeDto;
import com.skycloud.oa.feebill.dto.OtherFeeCargoDto;
import com.skycloud.oa.feebill.dto.OtherFeeDto;
import com.skycloud.oa.inbound.dao.CargoGoodsDao;
import com.skycloud.oa.inbound.dao.ExceedFeeDao;
import com.skycloud.oa.inbound.dao.InitialFeeDao;
import com.skycloud.oa.inbound.dto.ExceedFeeDto;
import com.skycloud.oa.inbound.dto.ExceedFeeLogDto;
import com.skycloud.oa.inbound.dto.InitialFeeDto;
import com.skycloud.oa.inbound.service.ExceedFeeService;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.ExcelUtil;
import com.skycloud.oa.utils.ExcelUtil.CallBack;

/**
 * 公共业务逻辑实现类
 * @ClassName: CommonServiceImpl 
 * @Description: TODO
 * @author xie
 * @date 2015年4月2日 下午10:47:01
 */
@Service
public class ExcelServiceImpl implements ExcelService {
	@Autowired
	private ExceedFeeDao exceedFeeDao;
	@Autowired
	private FeeChargeDao feeChargeDao;
	@Autowired
	private InitialFeeDao initialFeeDao;
	@Autowired
	private ExceedFeeService exceedFeeService;
	@Autowired
	private FeeBillDao feeBillDao;
	@Autowired
	private CargoGoodsDao cargoGoodsDao;
	@Autowired
	private OtherFeeDao otherFeeDao;
	@Autowired
	private OtherFeeCargoDao otherFeeCargoDao;
	@Override
	public HSSFWorkbook exportExcel(final HttpServletRequest request, String type,
			String params) {
		ExcelUtil excelUtil=null;
		
		if("50".equals((String)request.getParameter("type"))){//首期费结算单
			excelUtil=new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/initialfee.xls", new CallBack() {
				@Override
				public void setSheetValue(HSSFSheet sheet) {
					try {
						InitialFeeDto iFeeDto=new InitialFeeDto();
					iFeeDto.setId(Integer.valueOf(request.getParameter("id")));
					iFeeDto.setType(Integer.valueOf(request.getParameter("initialType")));
					Map<String, Object> data=initialFeeDao.getInitialFeeMsg(iFeeDto);
					
					sheet.getRow(1).getCell(1).setCellValue(getStringValue(data.get("code")));
					sheet.getRow(1).getCell(3).setCellValue(handleTime(data.get("accountTime")));
					sheet.getRow(2).getCell(1).setCellValue(getStringValue(data.get("clientName")));
					sheet.getRow(2).getCell(3).setCellValue(getStringValue(data.get("contractCode")));
					sheet.getRow(3).getCell(1).setCellValue(getStringValue(data.get("productName")));
					sheet.getRow(3).getCell(3).setCellValue(getStringValue(data.get("cargoCode")));
					sheet.getRow(4).getCell(1).setCellValue(getStringValue(data.get("shipName"))+"-"+getStringValue(data.get("shipRefName")));
					sheet.getRow(4).getCell(3).setCellValue(handleTime(data.get("arrivalTime")));
					if(iFeeDto.getType()==2||iFeeDto.getType()==3){
						sheet.getRow(5).getCell(0).setCellValue("周期：");
						sheet.getRow(5).getCell(1).setCellValue(getStringValue(data.get("startTime"))+"/"+getStringValue(data.get("endTime")));
					}else{
						sheet.getRow(5).getCell(1).setCellValue(getStringValue(data.get("periodTime")));						
					}
					sheet.getRow(5).getCell(3).setCellValue(getDoubleValue(data.get("goodsInspect")));
					sheet.getRow(6).getCell(0).setCellValue("费用类型");
					sheet.getRow(7).getCell(1).setCellValue(getStringValue(data.get("description")));
					 sheet.getRow(8).getCell(1).setCellValue(getStringValue(data.get("createUserName")));
					FeeChargeDto feeChargeDto=new FeeChargeDto();
					feeChargeDto.setInitialId(Integer.valueOf(data.get("id").toString()));
				    List<Map<String,Object>>  feeChargelist=feeChargeDao.getFeeChargeList(feeChargeDto);
					int i=0;
					DecimalFormat    df3   = new DecimalFormat("######0.00");
					Double totalFee=0D;
					 sheet.shiftRows(7, sheet.getLastRowNum(),feeChargelist.size()+1,true,false);//a-b行向下移动1行
					 for(;i<=feeChargelist.size();i++){
						 int item=i+7;
						
						 HSSFRow itemRow= sheet.createRow(item);
						 itemRow.setHeight(sheet.getRow(6).getHeight());
						 if(i!=feeChargelist.size()){
						 Map<String,Object> itemData=feeChargelist.get(i);
						 itemRow.createCell(0).setCellValue(getFeeChargeStr(Integer.valueOf(itemData.get("feeType").toString())));
						 itemRow.createCell(1).setCellValue(getDoubleValue(itemData.get("unitFee"),2));
						 itemRow.createCell(2).setCellValue(getDoubleValue(itemData.get("feeCount"),3));
						 itemRow.createCell(3).setCellValue(getDoubleValue(itemData.get("totalFee"),2));
						 totalFee=Common.add(totalFee,getDoubleValue(itemData.get("totalFee")),2);
						 }else{
							 itemRow.createCell(0).setCellValue("总计");
							 itemRow.createCell(1).setCellValue(" ");
							 itemRow.createCell(2).setCellValue(" ");
							 itemRow.createCell(3).setCellValue(getDoubleValue(df3.format(totalFee)));
						 }
						 itemRow.getCell(0).setCellStyle(sheet.getRow(6).getCell(0).getCellStyle());
						 itemRow.getCell(1).setCellStyle(sheet.getRow(6).getCell(1).getCellStyle());
						 itemRow.getCell(2).setCellStyle(sheet.getRow(6).getCell(2).getCellStyle());
						 itemRow.getCell(3).setCellStyle(sheet.getRow(6).getCell(3).getCellStyle());
					 }
					} catch (OAException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
		}else if("51".equals((String)request.getParameter("type"))){//超期费结算单
		 excelUtil=new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/exceedfee.xls",new CallBack() {
			@Override
			public void setSheetValue(HSSFSheet sheet) {
				ExceedFeeDto eFeeDto=new ExceedFeeDto();
				eFeeDto.setId(Integer.valueOf(request.getParameter("id")));
				try {
					Map<String, Object> data=exceedFeeDao.getExceedFeeList(eFeeDto, 0, 1).get(0);
					sheet.getRow(1).getCell(1).setCellValue(getStringValue(data.get("code")));
					sheet.getRow(1).getCell(3).setCellValue(handleTime(data.get("accountTime")));
					sheet.getRow(2).getCell(1).setCellValue(getStringValue(data.get("clientName")));
					sheet.getRow(2).getCell(3).setCellValue(getStringValue(data.get("ladingCode")));
					sheet.getRow(3).getCell(1).setCellValue(getStringValue(data.get("productName")));
					List<Map<String,Object>> mdata=null;
					if(data.get("cargoCode")==null){
						 mdata=	exceedFeeDao.getInboundMsgListByExceedId(Integer.valueOf(request.getParameter("id")));
					}else{
						mdata=	exceedFeeDao.getInboundMsgListByCargoId(Integer.valueOf(data.get("cargoId").toString()));
					}
						if(mdata!=null&&mdata.size()>0){
							String cargoCode="";
							String shipName="";
							String arrivalTime="";
							String periodTime="";
							for(int i=0;i<mdata.size();i++){
								Map<String,Object> map=mdata.get(i);
								if(i==mdata.size()-1){
									cargoCode+=	map.get("cargoCode")==null?"":map.get("cargoCode").toString();
									shipName+=(map.get("inboundMsg").toString().split("/")[0]+"-"+map.get("inboundMsg").toString().split("/")[1]);
									arrivalTime+=map.get("inboundMsg").toString().split("/")[2];
									periodTime+=map.get("periodTime")==null?"":map.get("periodTime").toString();
								}else{
									cargoCode+=	map.get("cargoCode")==null?"":map.get("cargoCode").toString()+"/";
									shipName+=map.get("inboundMsg").toString().split("/")[0]+"-"+map.get("inboundMsg").toString().split("/")[1]+"/";
									arrivalTime+=map.get("inboundMsg").toString().split("/")[2]+"/";
									periodTime+=map.get("periodTime")==null?"":map.get("periodTime").toString()+"/";
								}
							}
							sheet.getRow(3).getCell(3).setCellValue(cargoCode);
							sheet.getRow(4).getCell(1).setCellValue(shipName);
							sheet.getRow(4).getCell(3).setCellValue(arrivalTime);
							sheet.getRow(5).getCell(1).setCellValue(periodTime);
						}else{
							sheet.getRow(3).getCell(3).setCellValue(" ");
							sheet.getRow(4).getCell(1).setCellValue(" ");
							sheet.getRow(4).getCell(3).setCellValue(" ");
							sheet.getRow(5).getCell(1).setCellValue(" ");
						}
					FeeChargeDto feeChargeDto=new FeeChargeDto();
					feeChargeDto.setExceedId(Integer.valueOf(data.get("id").toString()));
				    List<Map<String,Object>>  feeChargelist=feeChargeDao.getFeeChargeList(feeChargeDto, 0, 20);
					 if(feeChargelist!=null&&feeChargelist.size()>0){
						 Map<String,Object> itemData=feeChargelist.get(0);
						sheet.getRow(6).getCell(1).setCellValue(getDoubleValue(itemData.get("unitFee"),2));
						sheet.getRow(6).getCell(3).setCellValue(getDoubleValue(itemData.get("totalFee"),2));
					 }
					sheet.getRow(7).getCell(1).setCellValue(handleTime(data.get("startTime")));
					sheet.getRow(7).getCell(3).setCellValue(handleTime(data.get("endTime")));
					sheet.getRow(8).getCell(1).setCellValue(getStringValue(data.get("description")));
					sheet.getRow(9).getCell(1).setCellValue(getStringValue(data.get("createUserName")));
					
				} catch (OAException e) {
					e.printStackTrace();
				}
			}
		});
		 
		}else if("53".equals(request.getParameter("type"))){//超期费每日结算详情
			excelUtil=new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/exceedeveryfee.xls", new CallBack() {
				
				@Override
				public void setSheetValue(HSSFSheet sheet) {
					 try {	
					ExceedFeeDto eFeeDto=new ExceedFeeDto();
					Double unitFee=0D; //费率
//					Integer ladingId=0;
					List<Map<String, Object>> dataList=null;
					Map<String, Object> data=null;
					Integer exceedType=Integer.valueOf(request.getParameter("exceedType"));
					eFeeDto.setId(Integer.valueOf(request.getParameter("id")));
					dataList=exceedFeeDao.getExceedFeeList(eFeeDto, 0, 1);
					if(dataList!=null&&dataList.size()>0)
						data=dataList.get(0);
					if(data!=null){
//					if(data.get("ladingId")!=null&&Integer.valueOf(data.get("ladingId").toString())!=0){
//						ladingId=Integer.valueOf(data.get("ladingId").toString());
//					}else if(data.get("cargoId")!=null&&Integer.valueOf(data.get("cargoId").toString())!=0){
//						ladingId=Integer.valueOf(data.get("cargoId").toString());
//					}
					
					List<Object> itemDayData =exceedFeeService.getExceedFeeLog(new ExceedFeeLogDto(eFeeDto.getId(), null)).getData();
					
					FeeChargeDto feeChargeDto=new FeeChargeDto();
					feeChargeDto.setExceedId(Integer.valueOf(data.get("id").toString()));
				    List<Map<String, Object>>  feeCharge=feeChargeDao.getFeeChargeList(feeChargeDto, 0,0);
				    
				    if(feeCharge!=null&&feeCharge.size()>0&&feeCharge.get(0).get("unitFee")!=null){
				     unitFee=Double.valueOf(feeCharge.get(0).get("unitFee").toString());
				    }
				    String msg=" 货品: ";
				    msg+=getStringValue(data.get("productName"))+"  货批:";
				    if(exceedType==2){//转卖提单超期费
				    List<Map<String, Object>>	arrayJson=	exceedFeeDao.getInboundMsgListByExceedId(Integer.valueOf(request.getParameter("id")));
				    	 String cargoCode="";
				    	 if(arrayJson!=null){
				    	 for(int m=0;m<arrayJson.size();m++){
				    		 Map<String,Object> map=arrayJson.get(m);
				    		if(m==arrayJson.size()-1){
				    			cargoCode+=map.get("cargoCode").toString();
				    		}else{
				    			cargoCode+=map.get("cargoCode").toString()+"/";
				    		}
				    	}}
				    	 msg+=cargoCode+"  调号:";
				    	 sheet.getRow(1).getCell(3).setCellValue(cargoCode);
				    }else{//货批超期费
				    	msg+=getStringValue(data.get("cargoCode"))+"  船信:"+feeCharge.get(0).get("inboundMsg").toString();
				    	sheet.getRow(1).getCell(3).setCellValue(getStringValue(data.get("cargoCode")));
				    }
				    msg+=getStringValue(data.get("ladingCode"))+"  日期:"+ handleTime(data.get("startTime"))+" 至  "+handleTime(data.get("endTime")) +
				    		"   计"+compareTime(handleTime(data.get("endTime")),handleTime(data.get("startTime")))+"天     费率 (元/吨天)："+unitFee+"     结算单编号:"+getStringValue(""+data.get("code").toString());
				    sheet.getRow(1).getCell(0).setCellValue(msg);
				    sheet.getRow(5).getCell(1).setCellValue(getStringValue(data.get("clientName")));
				    if(itemDayData!=null&&itemDayData.size()>0){
				    sheet.shiftRows(4, sheet.getLastRowNum(), itemDayData.size(),true,false);
				    DecimalFormat    df   = new DecimalFormat("######0.00");
				    Double totalFee=0d;
				    Double leftNum=0d;
				    int colDay=0;
				    Map<String,Object> map;
				    int i=0;
				    for(;i<=itemDayData.size();i++){
				    	int item=i+4;
				    	if(i!=itemDayData.size()){
				    		HSSFRow itemRow=sheet.createRow(item);
				    		itemRow.setHeight(sheet.getRow(2).getHeight());
				    		map= (Map<String, Object>) itemDayData.get(i);
				    		itemRow.createCell(0).setCellValue(i+1);
				    		itemRow.createCell(1).setCellValue(getStringValue(map.get("contactClientName")));
				    		 if(map.get("isCurrent")!=null&&Integer.valueOf(map.get("isCurrent").toString())==1){
				    			 itemRow.createCell(2).setCellValue("超期费");
								}else{
									itemRow.createCell(2).setCellValue("");
								}
				    		 if(map.get("type")!=null&&Integer.valueOf(map.get("type").toString())==0){
				    			 itemRow.createCell(3).setCellValue("");
								}else if(map.get("type")!=null&&Integer.valueOf(map.get("type").toString())==1){
									if(exceedType==1){
										 itemRow.createCell(3).setCellValue("入库");
									}else{
										 itemRow.createCell(3).setCellValue("调入");
									}
								}else if(map.get("type")!=null&&Integer.valueOf(map.get("type").toString())==2){
									 itemRow.createCell(3).setCellValue("提货");
								}else if(map.get("type")!=null&&Integer.valueOf(map.get("type").toString())==3){
									 itemRow.createCell(3).setCellValue("货权转移");
								}else if(map.get("type")!=null&&Integer.valueOf(map.get("type").toString())==4){
									 itemRow.createCell(3).setCellValue("起计");
								}else if(map.get("type")!=null&&Integer.valueOf(map.get("type").toString())==5){
									 itemRow.createCell(3).setCellValue("扣损");
								}else{
									itemRow.createCell(3).setCellValue("");
								}
				    		 itemRow.createCell(4).setCellValue(getStringValue(map.get("l_ladingCode")));
				    		 itemRow.createCell(5).setCellValue(getStringValue(map.get("truckCode")));
				    		 itemRow.createCell(6).setCellValue(getStringValue(map.get("l_time")));
				    		 
				    		 
				    		   if(map.get("type")!=null&&Integer.valueOf(map.get("type").toString())==1){
				    			 itemRow.createCell(7).setCellValue(getDoubleValue(map.get("currentNum"),3));
								}else if(map.get("type")!=null&&(Integer.valueOf(map.get("type").toString())==2||Integer.valueOf(map.get("type").toString())==3||Integer.valueOf(map.get("type").toString())==5)){
									itemRow.createCell(7).setCellValue(-getDoubleValue(map.get("operateNum"),3));
								}else{
									itemRow.createCell(7).setCellValue("");
								}
				    		   itemRow.createCell(8).setCellValue(getDoubleValue(map.get("currentNum")));
				    		   leftNum=getDoubleValue(map.get("currentNum"));
							    if(map.get("isCurrent")!=null&&Integer.valueOf(map.get("isCurrent").toString())==1){
							    	Double  fee=Common.mul(unitFee, getDoubleValue(map.get("currentNum")),2);
							    	itemRow.createCell(9).setCellValue(fee);
							    	itemRow.createCell(10).setCellValue(getDoubleValue(map.get("col")));
							    	Double itemfee=Common.mul(fee,getDoubleValue(map.get("col")),2);
							    	itemRow.createCell(11).setCellValue(itemfee);
							    	totalFee=Common.add(totalFee, itemfee,2);
							    	colDay+=Integer.valueOf(map.get("col").toString());
							    }else{
							    	itemRow.createCell(9).setCellValue("");
							    	itemRow.createCell(10).setCellValue("");
							    	itemRow.createCell(11).setCellValue("");
									}
							    for(int j=0;j<12;j++){
							    	itemRow.getCell(j).setCellStyle(sheet.getRow(3).getCell(j).getCellStyle());
							    }
				    	}else{
				    		sheet.getRow(item).getCell(10).setCellValue(colDay);
				    		//校验金额是否一致
				    		if(Common.sub(getDoubleValue(data.get("exceedTotalFee")),totalFee,2)==0){
				    			sheet.getRow(item).getCell(11).setCellValue(totalFee);
				    		}else{
				    			sheet.getRow(item).getCell(11).setCellValue(getDoubleValue(data.get("exceedTotalFee"))+"计算金额与之前的不一致，请重新结算");
				    		}
				    	}
				    }
				    if(exceedType==2){
					    sheet.getRow(sheet.getLastRowNum()).getCell(2).setCellValue("货主从    "+getStringValue(data.get("sendClientName"))+" 调入： "+getDoubleValue(data.get("ladingGoodsTotal"))+"吨 "+ "    调取： "+Common.sub(getDoubleValue(data.get("ladingGoodsTotal")),leftNum,3)+"吨      剩余 ："+leftNum+"吨" );
					    }else{
					    	sheet.getRow(sheet.getLastRowNum()).getCell(2).setCellValue("货主入库:  "+getDoubleValue(data.get("cargoGoodsTotal"))+"吨 "
						    		+ "    调取： "+Common.sub(getDoubleValue(data.get("cargoGoodsTotal")),leftNum,3)+"吨      剩余 ："+leftNum+"吨" );	
					    }
				    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String file_name=sdf.format(new Date());
				    sheet.getRow(sheet.getLastRowNum()).getCell(11).setCellValue(file_name);
				    sheet.removeRow(sheet.getRow(3));
				    sheet.shiftRows(4, sheet.getLastRowNum(), -1,true,false);
				    }
					}
					 } catch (OAException e) {
						e.printStackTrace();
					}
				}
			});
		}else if("52".equals(request.getParameter("type"))){//账单开票列表
			excelUtil=new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/feebilllist.xls",new CallBack() {
				@Override
				public void setSheetValue(HSSFSheet sheet) {
				try{	
					 FeeBillDto feebillDto=new FeeBillDto();
					if (request.getParameter("startTime") != null)
						feebillDto.setStartTime(Long.valueOf(request.getParameter("startTime")));
					if (request.getParameter("endTime") != null)
						feebillDto.setEndTime(Long.valueOf(request.getParameter("endTime")));
					if (request.getParameter("billingStartTime") != null)
						feebillDto.setBillingStartTime(Long.valueOf(request.getParameter("billingStartTime")));
					if (request.getParameter("billingEndTime") != null)
						feebillDto.setBillingEndTime(Long.valueOf(request.getParameter("billingEndTime")));
					if (request.getParameter("feeTypes") != null)
						feebillDto.setFeeTypes(String.valueOf(request.getParameter("feeTypes")));
					if (request.getParameter("cargoId") != null)
						feebillDto.setCargoId(Integer.valueOf(request.getParameter("cargoId")));
					if (request.getParameter("cargoCode") != null)
						feebillDto.setCargoCode(String.valueOf(request.getParameter("cargoCode")));
					if (request.getParameter("productId") != null)
						feebillDto.setProductId(Integer.valueOf(request.getParameter("productId")));
					if (request.getParameter("clientId") != null)
						feebillDto.setClientId(Integer.valueOf(request.getParameter("clientId")));
					if (request.getParameter("feeHead") != null)
						feebillDto.setFeeHead(String.valueOf(request.getParameter("feeHead")));
					if (request.getParameter("code") != null)
						feebillDto.setCode(String.valueOf(request.getParameter("code")));
					if(request.getParameter("billStatus")!=null)
						feebillDto.setBillStatus(Integer.valueOf(request.getParameter("billStatus")));
					if(request.getParameter("totalFee")!=null)
						feebillDto.setTotalFee(request.getParameter("totalFee"));
					List<Map<String,Object>> data=feeBillDao.getFeeBillExelList(feebillDto);
					sheet.shiftRows(3, 3, data.size()-1,true,false);
					int i=0;
				    Double totalFee=0d;// 总金额
				    Double itemTotalFee=0d;//单个费用项总金额
				    Double leftTotalFee=0d;//已到账金额
				    Double unTotalTaxFee=0d;//未税金额
				    Double totalTaxFee=0d;//税额
				    List<Map<String,Integer>> region=new ArrayList<Map<String,Integer>>();
				    List<String> lsids = new ArrayList<String>();// 账单id
				    DecimalFormat    df   = new DecimalFormat("######0.00");
					for(;i<=data.size();i++){
						int item=i+2;
						 if(i!=data.size()){
							if(i!=0){
							HSSFRow itemRow=sheet.createRow(item);
				    		itemRow.setHeight(sheet.getRow(2).getHeight());
							for(int m=0;m<24;m++){
								System.out.println(m);
								itemRow.createCell(m);
								itemRow.getCell(m).setCellStyle(sheet.getRow(2).getCell(m).getCellStyle());
							}
							}
							
							Map<String,Object> map= (Map<String, Object>) data.get(i);
							sheet.getRow(item).getCell(0).setCellValue(getStringValue(map.get("code")));
							sheet.getRow(item).getCell(1).setCellValue(handleTime(map.get("accountTime")));
							sheet.getRow(item).getCell(2).setCellValue(getStringValue(map.get("feeHeadName")));
							
							if(map.get("cargoName")==null&&Integer.valueOf(map.get("exceedId").toString())!=0){
						    	 List<Map<String,Object>> listData=	exceedFeeDao.getInboundMsgListByExceedId(Integer.valueOf(map.get("exceedId").toString()));
						    	 String types="";String cargoNames="";String contractTypes="";	
						    	 if(listData!=null){
						    	 for(int j=0;j<listData.size();j++){
						    		 Map<String,Object> nmap=listData.get(j);
						    		 if(j==listData.size()-1){
						    			 types+=(nmap.get("type")==null?"否":(Integer.valueOf(nmap.get("type").toString())==3?"是":"否"));
							    		 cargoNames+=nmap.get("cargoCode").toString();
							    		 contractTypes+=getContractType(nmap.get("storageType")); 
						    		 }else{
						    		 types+=(nmap.get("type")==null?"否":(Integer.valueOf(nmap.get("type").toString())==3?"是":"否"))+"/";
						    		 cargoNames+=nmap.get("cargoCode").toString()+"/";
						    		 contractTypes+=getContractType(nmap.get("storageType"))+"/";
						    		 }
						    	 } }
						    	sheet.getRow(item).getCell(3).setCellValue(types);
								sheet.getRow(item).getCell(4).setCellValue(cargoNames);
					    	    sheet.getRow(item).getCell(13).setCellValue(contractTypes);
						    	}else{
						    		sheet.getRow(item).getCell(3).setCellValue(map.get("type")==null?"否":(Integer.valueOf(map.get("type").toString())==3?"是":"否"));
									
						    	    if((Integer.valueOf(map.get("feeType").toString())==3||Integer.valueOf(map.get("feeType").toString())==4)&&Integer.valueOf(map.get("initialId").toString())!=0){//如果是固定费 合同性质单独处理
						    	    Map<String,Object> cargoCodes=	feeBillDao.getCargoCodesByInitialId(Integer.valueOf(map.get("initialId").toString()));
						    	    sheet.getRow(item).getCell(4).setCellValue(getStringValue(cargoCodes.get("cargoCodes")));
						    	    	sheet.getRow(item).getCell(13).setCellValue(getContractType(3));
						    	    }else if(Integer.valueOf(map.get("feeType").toString())==6){
						    	    	
						    	    	
						    	    	
						    	    }else{
						    	    	sheet.getRow(item).getCell(4).setCellValue(getStringValue(map.get("cargoName")));
							    	    sheet.getRow(item).getCell(13).setCellValue(getContractType(map.get("storageType")));
						    	    }
						    	}
							
							sheet.getRow(item).getCell(5).setCellValue(getStringValue(map.get("productName")));
							sheet.getRow(item).getCell(6).setCellValue(getDoubleValue(map.get("unitFee"),2));
							
							if(map.get("feeCount")!=null&&Integer.valueOf(map.get("feeType").toString())!=5){
								sheet.getRow(item).getCell(7).setCellValue(getDoubleValue(map.get("feeCount")));
					    	}
							sheet.getRow(item).getCell(8).setCellValue(getDoubleValue(map.get("itemTotalFee"),2));
							itemTotalFee=Common.add(itemTotalFee, getDoubleValue(map.get("itemTotalFee")),2);
							sheet.getRow(item).getCell(9).setCellValue(handleTime(map.get("fundsTime")));
							sheet.getRow(item).getCell(10).setCellValue(getFeeChargeStr(Integer.valueOf(map.get("feeType").toString())));
							sheet.getRow(item).getCell(11).setCellValue(getStringValue(map.get("billingCode")));
							sheet.getRow(item).getCell(12).setCellValue(handleTime(map.get("billingTime")));
							sheet.getRow(item).getCell(24).setCellValue(getStringValue(map.get("description")));
							sheet.getRow(item).getCell(17).setCellValue(getDoubleValue(getDoubleValue(map.get("itemTotalFee"),2)/(1+getDoubleValue(map.get("taxRate"))),2));
							sheet.getRow(item).getCell(19).setCellValue(getDoubleValue(getDoubleValue(map.get("itemTotalFee"),2)-getDoubleValue(getDoubleValue(map.get("itemTotalFee"),2)/(1+getDoubleValue(map.get("taxRate"))),2),2));
							sheet.getRow(item).getCell(20).setCellValue(getDoubleValue(map.get("discountFee"),2));
							
							String id=map.get("feebillId").toString();
					    	if(lsids.contains(id)){
					    		System.out.println("0a");
					    		sheet.getRow(item).getCell(14).setCellValue("");
								sheet.getRow(item).getCell(15).setCellValue("");
								sheet.getRow(item).getCell(16).setCellValue("");
								sheet.getRow(item).getCell(21).setCellValue("");
								sheet.getRow(item).getCell(18).setCellValue("");
								sheet.getRow(item).getCell(22).setCellValue("");
								System.out.println("1a");
					    	}else{
					    		System.out.println("2a");
					    		Map<String,Integer> mapRg=new HashMap<String, Integer>();
					    		Integer num=Integer.valueOf(map.get("feeNum").toString());
					    		mapRg.put("start", item);
					    		mapRg.put("end",item+num-1);
					    		region.add(mapRg);
					    		lsids.add(id);
					    		sheet.getRow(item).getCell(14).setCellValue(getDoubleValue(map.get("totalFee"),2));
								sheet.getRow(item).getCell(15).setCellValue(getDoubleValue(map.get("leftTotalFee"),2));
								sheet.getRow(item).getCell(16).setCellValue(Common.sub(getDoubleValue(map.get("totalFee")), getDoubleValue(map.get("leftTotalFee")),2));
								sheet.getRow(item).getCell(21).setCellValue(getDoubleValue(map.get("unTaxFee"),2));
								sheet.getRow(item).getCell(18).setCellValue(getDoubleValue(map.get("taxRate")));
								sheet.getRow(item).getCell(22).setCellValue(getDoubleValue(map.get("taxFee"),2));
					    		totalFee=Common.add(totalFee, getDoubleValue(map.get("totalFee")),2);
								leftTotalFee=Common.add(leftTotalFee, getDoubleValue(map.get("leftTotalFee")),2);
								unTotalTaxFee=Common.add(unTotalTaxFee, getDoubleValue(map.get("unTaxFee")),2);
								totalTaxFee=Common.add(totalTaxFee, getDoubleValue(map.get("taxFee")),2);
					    	}
					    	sheet.getRow(item).getCell(23).setCellValue(handleTime(map.get("fundsTime")));
							
						}else{
							sheet.getRow(item).getCell(8).setCellValue(getDoubleValue(itemTotalFee,2));
							sheet.getRow(item).getCell(14).setCellValue(getDoubleValue(totalFee));
							sheet.getRow(item).getCell(15).setCellValue(getDoubleValue(leftTotalFee));
							sheet.getRow(item).getCell(16).setCellValue(Common.sub(totalFee, leftTotalFee,2));
						
							sheet.getRow(item).getCell(17).setCellValue(getDoubleValue(unTotalTaxFee,2));
							sheet.getRow(item).getCell(19).setCellValue(getDoubleValue(totalTaxFee,2));
						}
					}
					if(region!=null){
					for(int j=0;j<region.size();j++){
						sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)14, region.get(j).get("end"), (short)14));    
						sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)15, region.get(j).get("end"), (short)15));    
						sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)16, region.get(j).get("end"), (short)16));    
						sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)21, region.get(j).get("end"), (short)21));    
						sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)18, region.get(j).get("end"), (short)18));    
						sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)22, region.get(j).get("end"), (short)22));    
					}}
				 } catch (OAException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
		}else if("54".equals(request.getParameter("type"))){//货批账单开票列表
			excelUtil=new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/cargolist.xls", new CallBack() {
				
				@Override
				public void setSheetValue(HSSFSheet sheet) {
					try{
						 FeeBillDto feebillDto=new FeeBillDto();
							if (request.getParameter("startTime") != null)
								feebillDto.setStartTime(Long.valueOf(request.getParameter("startTime")));
							if (request.getParameter("endTime") != null)
								feebillDto.setEndTime(Long.valueOf(request.getParameter("endTime")));
							if (request.getParameter("billingStartTime") != null)
								feebillDto.setBillingStartTime(Long.valueOf(request.getParameter("billingStartTime")));
							if (request.getParameter("billingEndTime") != null)
								feebillDto.setBillingEndTime(Long.valueOf(request.getParameter("billingEndTime")));
							if (request.getParameter("feeTypes") != null)
								feebillDto.setFeeTypes(String.valueOf(request.getParameter("feeTypes")));
							if (request.getParameter("cargoId") != null)
								feebillDto.setCargoId(Integer.valueOf(request.getParameter("cargoId")));
							if (request.getParameter("cargoCode") != null)
								feebillDto.setCargoCode(String.valueOf(request.getParameter("cargoCode")));
							if (request.getParameter("productId") != null)
								feebillDto.setProductId(Integer.valueOf(request.getParameter("productId")));
							if (request.getParameter("clientId") != null)
								feebillDto.setClientId(Integer.valueOf(request.getParameter("clientId")));
							if (request.getParameter("feeHead") != null)
								feebillDto.setFeeHead(String.valueOf(request.getParameter("feeHead")));
							if (request.getParameter("code") != null)
								feebillDto.setCode(String.valueOf(request.getParameter("code")));
							if(request.getParameter("billStatus")!=null)
								feebillDto.setBillStatus(Integer.valueOf(request.getParameter("billStatus")));
							if(request.getParameter("totalFee")!=null)
								feebillDto.setTotalFee(request.getParameter("totalFee"));
						
						List<Map<String,Object>> data=feeBillDao.getCargoFeeBillList(feebillDto);
					if(data!=null&&data.size()>0){
						sheet.shiftRows(3, 3, data.size()-2,true,false);
						for(int i=0;i<data.size();i++){
							int item=i+2;
									if(i!=0&&i!=data.size()-1){
									HSSFRow itemRow=sheet.createRow(item);
						    		itemRow.setHeight(sheet.getRow(2).getHeight());
									for(int m=0;m<18;m++){
										itemRow.createCell(m);
										itemRow.getCell(m).setCellStyle(sheet.getRow(2).getCell(m).getCellStyle());
									}
									}
									Map<String,Object> map= (Map<String, Object>) data.get(i);
							sheet.getRow(item).getCell(0).setCellValue(getStringValue(map.get("cargoCode")));
							sheet.getRow(item).getCell(1).setCellValue(getStringValue(map.get("shipName")));
							sheet.getRow(item).getCell(2).setCellValue(getStringValue(map.get("shipRefname")));
							sheet.getRow(item).getCell(3).setCellValue(handleTime(map.get("arrivalTime")));
							sheet.getRow(item).getCell(4).setCellValue(getStringValue(map.get("productName")));
							sheet.getRow(item).getCell(5).setCellValue(getContractType(map.get("storageType")));
							sheet.getRow(item).getCell(6).setCellValue(getDoubleValue(map.get("goodsTank")));
							sheet.getRow(item).getCell(7).setCellValue(getStringValue(map.get("customLading")));
							sheet.getRow(item).getCell(8).setCellValue(getDoubleValue(map.get("customLadingCount")));
							sheet.getRow(item).getCell(9).setCellValue(getDoubleValue(map.get("goodsInspect")));
							sheet.getRow(item).getCell(10).setCellValue(getStringValue(map.get("clientName")));
							sheet.getRow(item).getCell(11).setCellValue(getStringValue(map.get("clientGroupName")));
							sheet.getRow(item).getCell(12).setCellValue(getStringValue(map.get("")));
							sheet.getRow(item).getCell(13).setCellValue(handleTime(map.get("accountTime")));
							sheet.getRow(item).getCell(14).setCellValue(getStringValue(map.get("feeBillCode")));
							if(map.get("id")!=null){
						    	 List<Map<String, Object>> tankCodesList=cargoGoodsDao.getGoodsTankCodesByCargoId(Integer.valueOf(map.get("id").toString()));
						    	 if(tankCodesList!=null&&tankCodesList.size()>0&&tankCodesList.get(0).get("tankCodes")!=null){
						    	sheet.getRow(item).getCell(15).setCellValue(getStringValue(tankCodesList.get(0).get("tankCodes")));
						    	 }
						    	}
							sheet.getRow(item).getCell(16).setCellValue(getStringValue(""));
							sheet.getRow(item).getCell(17).setCellValue(getStringValue(map.get("originalArea")));
							
							 }
						
					}	
						
					}catch(OAException e){
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
		}else if("55".equals(request.getParameter("type"))){
			excelUtil=new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/initialfee.xls", new CallBack() {
				@Override
				public void setSheetValue(HSSFSheet sheet) {
					try {
						InitialFeeDto iFeeDto=new InitialFeeDto();
					iFeeDto.setId(Integer.valueOf(request.getParameter("id")));
					Map<String, Object> data=null;
					
					data=initialFeeDao.getOutFeeMsg(iFeeDto);
					
					sheet.getRow(1).getCell(1).setCellValue(getStringValue(data.get("code")));
					sheet.getRow(1).getCell(3).setCellValue(handleTime(data.get("accountTimeStr")));
					sheet.getRow(2).getCell(1).setCellValue(getStringValue(data.get("ladingClientNames")));
//					sheet.getRow(2).getCell(3).setCellValue(getStringValue(data.get("contractCode")));
					sheet.getRow(3).getCell(1).setCellValue(getStringValue(data.get("productName")));
//					sheet.getRow(3).getCell(3).setCellValue(getStringValue(data.get("cargoCode")));
					sheet.getRow(4).getCell(1).setCellValue(getStringValue(data.get("shipName"))+"-"+getStringValue(data.get("shipRefName")));
					sheet.getRow(4).getCell(3).setCellValue(handleTime(data.get("arrivalTime")));
//					sheet.getRow(5).getCell(1).setCellValue(getStringValue(data.get("periodTime")));
//					sheet.getRow(5).getCell(3).setCellValue(getDoubleValue(data.get("goodsInspect")));
					sheet.getRow(6).getCell(0).setCellValue("费用类型");
					sheet.getRow(7).getCell(1).setCellValue(getStringValue(data.get("description")));
					 sheet.getRow(8).getCell(1).setCellValue(getStringValue(data.get("createUserName")));
					FeeChargeDto feeChargeDto=new FeeChargeDto();
					feeChargeDto.setInitialId(Integer.valueOf(data.get("id").toString()));
				    List<Map<String,Object>>  feeChargelist=feeChargeDao.getFeeChargeList(feeChargeDto, 0, 20);
					int i=0;
					DecimalFormat    df3   = new DecimalFormat("######0.00");
					Double totalFee=0D;
					 sheet.shiftRows(7, sheet.getLastRowNum(),feeChargelist.size()+1,true,false);//a-b行向下移动1行
					 for(;i<=feeChargelist.size();i++){
						 int item=i+7;
						
						 HSSFRow itemRow= sheet.createRow(item);
						 itemRow.setHeight(sheet.getRow(6).getHeight());
						 if(i!=feeChargelist.size()){
						 Map<String,Object> itemData=feeChargelist.get(i);
						 itemRow.createCell(0).setCellValue(getFeeChargeStr(Integer.valueOf(itemData.get("feeType").toString())));
						 itemRow.createCell(1).setCellValue(getDoubleValue(itemData.get("unitFee"),2));
						 itemRow.createCell(2).setCellValue(getDoubleValue(itemData.get("feeCount"),3));
						 itemRow.createCell(3).setCellValue(getDoubleValue(itemData.get("totalFee"),2));
						 totalFee=Common.add(totalFee,getDoubleValue(itemData.get("totalFee")),2);
						 }else{
							 itemRow.createCell(0).setCellValue("总计");
							 itemRow.createCell(1).setCellValue(" ");
							 itemRow.createCell(2).setCellValue(" ");
							 itemRow.createCell(3).setCellValue(getDoubleValue(df3.format(totalFee)));
						 }
						 itemRow.getCell(0).setCellStyle(sheet.getRow(6).getCell(0).getCellStyle());
//						 itemRow.getCell(0).setCellType(sheet.getRow(6).getCell(0).getCellType());
						 itemRow.getCell(1).setCellStyle(sheet.getRow(6).getCell(1).getCellStyle());
//						 itemRow.getCell(1).setCellType(sheet.getRow(6).getCell(1).getCellType());
						 itemRow.getCell(2).setCellStyle(sheet.getRow(6).getCell(2).getCellStyle());
//						 itemRow.getCell(2).setCellType(sheet.getRow(6).getCell(2).getCellType());
						 itemRow.getCell(3).setCellStyle(sheet.getRow(6).getCell(3).getCellStyle());
//						 itemRow.getCell(3).setCellType(sheet.getRow(6).getCell(3).getCellType());
					 }
					} catch (OAException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}else if("56".equals(request.getParameter("type"))){
			excelUtil=new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/initialfee.xls", new CallBack() {

				@Override
				public void setSheetValue(HSSFSheet sheet) {
					try {
						OtherFeeDto iFeeDto=new OtherFeeDto();
					iFeeDto.setId(Integer.valueOf(request.getParameter("id")));
					Map<String, Object> data=null;
					
					data=otherFeeDao.getList(iFeeDto, 0, 0).get(0);
					List<Map<String, Object>> cargoList=otherFeeCargoDao.getList(new OtherFeeCargoDto(iFeeDto.getId()), 0, 0);
					String cargoString="";
					if(cargoList!=null&&cargoList.size()>0){
					for(Map<String, Object> map:cargoList){
						cargoString+=map.get("cargoCode").toString()+",";
					}}
					if(cargoString.length()>0)
					cargoString=cargoString.substring(0, cargoString.length()-1);
					sheet.getRow(1).getCell(1).setCellValue(getStringValue(data.get("code")));
					sheet.getRow(1).getCell(3).setCellValue(handleTime(data.get("accountTime")));
					sheet.getRow(2).getCell(1).setCellValue(getStringValue(data.get("clientName")));
					sheet.getRow(3).getCell(1).setCellValue(getStringValue(data.get("productName")));
					sheet.getRow(3).getCell(3).setCellValue(cargoString);
					sheet.getRow(5).getCell(3).setCellValue(getDoubleValue(data.get("feeCount"),3));
					sheet.getRow(6).getCell(0).setCellValue("费用类型");
					sheet.getRow(7).getCell(1).setCellValue(getStringValue(data.get("description")));
					 sheet.getRow(8).getCell(1).setCellValue(getStringValue(data.get("createUserName")));
					DecimalFormat    df3   = new DecimalFormat("######0.00");
					Double totalFee=0D;
					 sheet.shiftRows(7, sheet.getLastRowNum(),1,true,false);//a-b行向下移动1行
					 for(int i=0;i<=1;i++){
						 int item=i+7;
						
						 HSSFRow itemRow= sheet.createRow(item);
						 itemRow.setHeight(sheet.getRow(6).getHeight());
						 if(i!=1){
						 itemRow.createCell(0).setCellValue("其他费");
						 itemRow.createCell(1).setCellValue(getDoubleValue(data.get("unitFee"),2));
						 itemRow.createCell(2).setCellValue(getDoubleValue(data.get("feeCount"),3));
						 itemRow.createCell(3).setCellValue(getDoubleValue(data.get("totalFee"),2));
						 totalFee=Common.add(totalFee,getDoubleValue(data.get("totalFee")),2);
						 }else{
							 itemRow.createCell(0).setCellValue("总计");
							 itemRow.createCell(1).setCellValue(" ");
							 itemRow.createCell(2).setCellValue(" ");
							 itemRow.createCell(3).setCellValue(getDoubleValue(df3.format(totalFee)));
						 }
						 itemRow.getCell(0).setCellStyle(sheet.getRow(6).getCell(0).getCellStyle());
						 itemRow.getCell(1).setCellStyle(sheet.getRow(6).getCell(1).getCellStyle());
						 itemRow.getCell(2).setCellStyle(sheet.getRow(6).getCell(2).getCellStyle());
						 itemRow.getCell(3).setCellStyle(sheet.getRow(6).getCell(3).getCellStyle());
					 }
					} catch (OAException e) {
						e.printStackTrace();
					}
				}
			});
		}
		
		return excelUtil.getWorkbook();
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
	
	private Double getDoubleValue(Object object,Integer point){
	return new BigDecimal(getDoubleValue(object)).setScale(point, BigDecimal.ROUND_HALF_UP).doubleValue() ;
	}
	
	
	private String handleTime(Object object) {
		if(object==null){
			return "";
		}else if(object.toString().length()>=10){
			return object.toString().substring(0,10);
		}else {
			return "";
		}
	}
	private String getFeeChargeStr(Integer item) {
		switch (item) {
		case 1:
			return "首期费";
       case 2:
			return "保安费";
	case 3:
			return "包罐费";
	case 4:
		   return "超量费";
	case 5:
		 return "超期费";
	case 6:
		 return "其他费";
	case 8:
		 return "通过费";
//	case 9:
//		 return "油品费";
//	case 10:
//		 return "LAB";
		default:
			break;
		}
		return "";
	}
	private String getContractType(Object item){
		if(item==null||item.toString().equals("null")){
			return "";
		}else{
			try{
		int mItem=Integer.valueOf(item.toString());
		switch (mItem) {
		case 1:
			return "一般";
       case 2:
			return "保税";
	case 3:
			return "包罐";
	case 4:
		   return "临租";
	case 5:
		 return "通过";
		default:
			break;
		}
			}
			catch(NumberFormatException e)  
			{    
			   return "";
			} }
			return "";
		}
	private Long getLongTime(String handleTime) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(handleTime).getTime()/1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1l;
	}
	
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
}
