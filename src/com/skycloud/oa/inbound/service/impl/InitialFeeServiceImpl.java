package com.skycloud.oa.inbound.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import com.skycloud.oa.feebill.dao.FeeChargeDao;
import com.skycloud.oa.feebill.dto.FeeChargeDto;
import com.skycloud.oa.feebill.model.DockFeeCharge;
import com.skycloud.oa.feebill.model.FeeCharge;
import com.skycloud.oa.inbound.dao.InitialFeeDao;
import com.skycloud.oa.inbound.dto.InitialFeeDto;
import com.skycloud.oa.inbound.model.InitialFee;
import com.skycloud.oa.inbound.service.InitialFeeService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.report.utils.FormatUtils;
import com.skycloud.oa.utils.ExcelUtil;
import com.skycloud.oa.utils.ExcelUtil.CallBack;
import com.skycloud.oa.utils.OaMsg;
/**
 *其他通知单
 * @author 作者:jiahy
 * @version 时间：2015年3月16日 下午2:24:34
 */
@Service
public class InitialFeeServiceImpl implements InitialFeeService {
	private static Logger LOG = Logger.getLogger(InitialFeeServiceImpl.class);
	@Autowired
	private  InitialFeeDao initialFeeDao;
	@Autowired
	private FeeChargeDao   feeChargeDao;
	@Override
	public OaMsg getInitialFeeList(InitialFeeDto iFeeDto, PageView pageView)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			if(iFeeDto.getType()==null){
				oaMsg.getData().addAll(initialFeeDao.getList(iFeeDto, pageView.getStartRecord(),pageView.getMaxresult()));
				oaMsg.getMap().put(Constant.TOTALRECORD,initialFeeDao.getCount(iFeeDto)+"");
			}else if(iFeeDto.getType()==1||iFeeDto.getType()==5){//入库货批--通过结算
				oaMsg.getData().addAll(initialFeeDao.getInitialFeeList(iFeeDto, pageView.getStartRecord(),pageView.getMaxresult()));
				oaMsg.getMap().put(Constant.TOTALRECORD,initialFeeDao.getInitialFeeCount(iFeeDto)+"");
			}else if(iFeeDto.getType()==2){//包罐合同
				oaMsg.getData().addAll(initialFeeDao.getContractFeeList(iFeeDto, pageView.getStartRecord(),pageView.getMaxresult()));
				oaMsg.getMap().put(Constant.TOTALRECORD,initialFeeDao.getContractFeeCount(iFeeDto)+"");
			}else if(iFeeDto.getType()==3){//出库油品结算
				oaMsg.getData().addAll(initialFeeDao.getOilsFeeList(iFeeDto, pageView.getStartRecord(),pageView.getMaxresult()));
				oaMsg.getMap().put(Constant.TOTALRECORD,initialFeeDao.getOilsFeeCount(iFeeDto)+"");
			}else if(iFeeDto.getType()==4){//出库保安费
				List<Map<String, Object>> data=initialFeeDao.getOutBoundSecurityFeeList(iFeeDto, pageView.getStartRecord(),pageView.getMaxresult());
				for(Map<String, Object> map:data){
					if(map!=null&&map.get("arrivalType")!=null&&Integer.valueOf(map.get("arrivalType").toString())==5){
						map.putAll(initialFeeDao.getOutBoundClientAndNum(Integer.valueOf(map.get("arrivalId").toString())));
					}
				}
				oaMsg.getData().addAll(data);
				
				oaMsg.getMap().put(Constant.TOTALRECORD,initialFeeDao.getOutBoundSecurityFeeCount(iFeeDto)+"");
			}
			
		}catch (RuntimeException e){
			LOG.error("service 获取首期费列表失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获取首期费列表失败",e);
		}
		return oaMsg;
	}
	/**
	 * @Title getInitialFeeMsg
	 * @Descrption:TODO
	 * @param:@param iFeeDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年8月3日下午2:24:06
	 * @throws
	 */
	@Override
	public OaMsg getInitialFeeMsg(InitialFeeDto iFeeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			Map<String, Object> data=initialFeeDao.getInitialFeeMsg(iFeeDto);
			if(iFeeDto.getType()!=null&&iFeeDto.getType()==4&&Integer.valueOf(data.get("arrivalType").toString())==5)
			 data.putAll(initialFeeDao.getOutBoundClientAndNum(Integer.valueOf(data.get("arrivalId").toString())));
			
			if(data!=null&&data.get("id")!=null&&Integer.valueOf(data.get("id").toString())!=0){
				FeeChargeDto fDto=new  FeeChargeDto();
				fDto.setInitialId(Integer.valueOf(data.get("id").toString()));
				data.put("feeChargeList", feeChargeDao.getFeeChargeList(fDto));
			}
			oaMsg.getData().add(data);
		}catch (RuntimeException e){
			LOG.error("service 获取首期费信息失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获取首期费信息失败",e);
		}
		return oaMsg;
	}
	@Override
	@Log(object=C.LOG_OBJECT.PCS_INITIALFEE,type=C.LOG_TYPE.CREATE)
	public OaMsg addInitialFee(InitialFee initialFee) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			initialFeeDao.addInitialFee(initialFee);
		}catch (RuntimeException e){
			LOG.error("service 添加首期费失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 添加首期费失败",e);
		}
		return oaMsg;
	}
	@Override
	@Log(object=C.LOG_OBJECT.PCS_INITIALFEE,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateInitialFee(InitialFee initialFee) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			initialFeeDao.updateInitialFee(initialFee);
		}catch (RuntimeException e){
			LOG.error("service 更新首期费失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 更新首期费失败",e);
		}
		return oaMsg;
	}
	@Override
	@Log(object=C.LOG_OBJECT.PCS_INITIALFEE,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteInitialFee(InitialFeeDto iFeeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			
			FeeChargeDto feeChargeDto=new FeeChargeDto();
			if(iFeeDto.getId()!=null){
				feeChargeDto.setInitialId(iFeeDto.getId());
				List<Map<String,Object>> dataList=feeChargeDao.getFeeChargeList(feeChargeDto,0,0);
				if(dataList.size()>0){
					for(int i=0;i<dataList.size();i++){
						if(dataList.get(0).get("feebillId")!=null&&Integer.valueOf(dataList.get(0).get("feebillId").toString())!=0){
							oaMsg.setMsg("该结算单已生成账单，无法删除！");
						break;
					     }
					}   
					  if(oaMsg.getMsg()==null||oaMsg.getMsg().length()==0){
						  feeChargeDao.deleteCargoLadingOfFeeCharge(feeChargeDto);
						  feeChargeDao.deleteFeeCharge(feeChargeDto);
						  initialFeeDao.deleteInitialFee(iFeeDto);
					  }
				}else{
					initialFeeDao.deleteInitialFee(iFeeDto);
				}
			}else{
				initialFeeDao.deleteInitialFee(iFeeDto);
			}
		}catch (RuntimeException e){
			LOG.error("service 删除首期费失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 删除首期费失败",e);
		}
		return oaMsg;
	}
	@Override
	public OaMsg getCodeNum(InitialFeeDto iFeeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().add(initialFeeDao.getCodeNum(iFeeDto));
		}catch (RuntimeException e){
			LOG.error("service 获取首期费编号失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获取首期费编号失败",e);
		}
		return oaMsg;
	}
	@Override
	@Log(object=C.LOG_OBJECT.PCS_INITIALFEE,type=C.LOG_TYPE.CREATE)
	public OaMsg addContractInitialFee(InitialFee initialFee)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		oaMsg.getMap().put("id", initialFeeDao.addContractInitialFee(initialFee)+"");
		}catch (RuntimeException e){
			LOG.error("service 删除首期费失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 删除首期费失败",e);
		}
		return oaMsg;
	}
	@Override
	public OaMsg getContractCargoList(InitialFeeDto iFeeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		  oaMsg.getData().addAll(initialFeeDao.getContractCargoList(iFeeDto));
		}catch (RuntimeException e){
			LOG.error("service 根据合同号，起止时间获取时间内的已经入库的货批失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 根据合同号，起止时间获取时间内的已经入库的货批失败",e);
		}
		return oaMsg;
	}
	@Override
	public OaMsg checkCargoFee(InitialFeeDto iFeeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getMap().put("result", 1+"");// 0.可以修改，1.生成结算单未生成账单2，已经生成账单
			String str="该货批已经生成";
		Map<String,Object> map=initialFeeDao.checkCargoFee(iFeeDto);
		    if(map.get("count")==null|| Integer.valueOf(map.get("count").toString())==0){
		    	oaMsg.getMap().put("result", 0+"");
		    }else if(map.get("feeType")!=null&&map.get("feebillId")==null){
		    	if("1".equals(map.get("feeType"))){
		    	str+="首期费结算单 ";	
		    	}
		    	if("5".equals(map.get("feeType"))){
		    	str+=" 超期费结算单";	
		    	}
		    	oaMsg.getMap().put("result", 1+"");
		    	oaMsg.getMap().put("msg", str);
		    }else if(map.get("feebillId")!=null){
		    	oaMsg.getMap().put("result", 1+"");
		    	oaMsg.getMap().put("msg", str+"已经生成账单无法修改。");
		    }
	
		}catch (RuntimeException e){
			LOG.error("service 校验货批是否生成了结算单失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 校验货批是否生成了结算单失败",e);
		}
		return oaMsg;
	}
	@Override
	@Log(object=C.LOG_OBJECT.PCS_INITIALFEE,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateCargoFee(InitialFeeDto iFeeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		initialFeeDao.updateCargoFee(iFeeDto);
		}catch (RuntimeException e){
			LOG.error("service 更新货批生成结算单未生成账单的货主和开票抬头失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 更新货批生成结算单未生成账单的货主和开票抬头失败",e);
		}
		return oaMsg;
	}
	@Override
	@Log(object=C.LOG_OBJECT.PCS_INITIALFEE,type=C.LOG_TYPE.REBACK)
	public OaMsg backStatus(InitialFee initialFee) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			initialFeeDao.updateInitialFee(initialFee);
			if(initialFee.getId()!=null)
			initialFeeDao.backStatus(initialFee.getId());
		}catch (RuntimeException e){
			LOG.error("service 回退首期费失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 回退首期费失败",e);
		}
		return oaMsg;
	}
	@Override
	public OaMsg getOutBoundTotalList(InitialFeeDto iFeeDto,PageView pageView) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			if(iFeeDto.getType()==1){
				oaMsg.getData().addAll(initialFeeDao.getOutBoundTotalList(iFeeDto));
			}else if(iFeeDto.getType()==2){
				oaMsg.getData().addAll(initialFeeDao.getOutBoundDetailList(iFeeDto,pageView.getStartRecord(),pageView.getMaxresult()));
				oaMsg.getMap().put(Constant.TOTALRECORD,initialFeeDao.getOutBoundDetailCount(iFeeDto)+"");
			}
		}catch (RuntimeException e){
			LOG.error("service 回退首期费失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 回退首期费失败",e);
		}
		return oaMsg;
	}
	@Override
	@Log(object=C.LOG_OBJECT.PCS_INITIALFEE,type=C.LOG_TYPE.CREATE)
	public OaMsg addOrUpdateInitialFee(InitialFeeDto iFeeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			InitialFee initialFee=iFeeDto.getInitialFee();
			Integer initialFeeId=0;
			if(initialFee.getId()!=null){
				initialFeeId=initialFee.getId();
				initialFeeDao.updateInitialFee(initialFee);
			}else{
				initialFeeId=initialFeeDao.addContractInitialFee(initialFee);
			}
			List<FeeCharge> feeChargeList=iFeeDto.getFeechargelist();
			if(feeChargeList!=null&&feeChargeList.size()>0){
				for(FeeCharge feeCharge:feeChargeList){
					feeCharge.setInitialId(initialFeeId);
				if(feeCharge.getId()!=null){
					feeChargeDao.updateFeeCharge(feeCharge);
				}else{
				feeChargeDao.insertfeeChargeCargoLading(feeChargeDao.addFeeCharge(feeCharge));
				}
				}
			}
			oaMsg.getMap().put("id", initialFeeId+"");
		}catch (RuntimeException e){
			LOG.error("service 添加或更新油品结算单失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 添加或更新油品结算单失败",e);
		}
		return oaMsg;
	}
	@Override
	public OaMsg getOilsFeeMsg(InitialFeeDto iFeeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		Map<String,Object> data=initialFeeDao.getDockFeeMsg(iFeeDto);
		FeeChargeDto feeChargeDto=new FeeChargeDto();
		feeChargeDto.setInitialId(iFeeDto.getId());
         data.put("feecharge", feeChargeDao.getFeeChargeList(feeChargeDto,0,0));    
		oaMsg.getData().add(data);
		}catch (RuntimeException e){
			LOG.error("service 获取油品结算单列表失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获取油品结算单详细信息失败",e);
		}
		return oaMsg;
	}
	@Override
	public OaMsg getOutFeeMsg(InitialFeeDto iFeeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		Map<String,Object> data=initialFeeDao.getOutFeeMsg(iFeeDto);
		FeeChargeDto feeChargeDto=new FeeChargeDto();
		feeChargeDto.setInitialId(iFeeDto.getId());
         data.put("feecharge", feeChargeDao.getFeeChargeList(feeChargeDto,0,0));    
		oaMsg.getData().add(data);
		}catch (RuntimeException e){
			LOG.error("service 获取油品结算单列表失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获取出库结算单详细信息失败",e);
		}
		return oaMsg;
	}
	/**
	 * @Title exportExcel
	 * @Descrption:TODO
	 * @param:@param request
	 * @param:@param iFeeDto
	 * @param:@return
	 * @auhor jiahy
	 * @date 2016年9月21日下午4:16:23
	 * @throws
	 */
	@Override
	public HSSFWorkbook exportExcel(HttpServletRequest request,final InitialFeeDto iFeeDto) {
		String[] initialFeeList={"initialFeeListA.xls","initialFeeListB.xls","initialFeeListC.xls","initialFeeListD.xls","initialFeeListA.xls"};
		return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+("/resource/upload/template/"+initialFeeList[(iFeeDto.getType()-1)]), new CallBack() {
			@Override
			public void setSheetValue(HSSFSheet sheet) {
				try {
					List<Object> data=getInitialFeeList(iFeeDto, new PageView(0, 0)).getData();
				int colLength=sheet.getRow(1).getLastCellNum();
				HSSFRow itemRow=null;
				int itemRowNum=2,itemLen=0,startRow=0,endRow=0;
				String[] clientNamesArray,ladingClientNameArray,tradeTypeArray,ladingEvidenceArray,actualNumArray;
				Map<String, Object> map=null;
					for(int i=0,len=data.size();i<len;i++){
						itemRow=sheet.createRow(itemRowNum);
						itemRow.setHeight(sheet.getRow(1).getHeight());
						for(int j=0;j<colLength;j++){
							itemRow.createCell(j).setCellStyle(sheet.getRow(1).getCell(j).getCellStyle());
						}
						map=(Map<String, Object>) data.get(i);
					   switch (iFeeDto.getType()) {
							case 1:
								itemRow.getCell(0).setCellValue(FormatUtils.getStringValue(map.get("cargoCode")));	
								itemRow.getCell(1).setCellValue(FormatUtils.getStringValue(map.get("code")));
								itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(map.get("accountTime")));
								itemRow.getCell(3).setCellValue(FormatUtils.getStringValue(map.get("shipName")));
								itemRow.getCell(4).setCellValue(FormatUtils.getStringValue(map.get("arrivalTime")));
								itemRow.getCell(5).setCellValue(getContractType(map.get("contractType")));
								itemRow.getCell(6).setCellValue(getTaxTypeVal(map.get("taxType")));
								itemRow.getCell(7).setCellValue(FormatUtils.getStringValue(map.get("clientName")));
								itemRow.getCell(8).setCellValue(FormatUtils.getStringValue(map.get("productName")));
								itemRow.getCell(9).setCellValue(FormatUtils.getDoubleValue(map.get("goodsInspect")));
								itemRow.getCell(10).setCellValue(FormatUtils.getDoubleValue(map.get("totalFee")));
								itemRow.getCell(11).setCellValue(getStatusVal(map.get("status")));
								break;
							case 2:
								itemRow.getCell(0).setCellValue(FormatUtils.getStringValue(map.get("cargoCodes")));	
								itemRow.getCell(1).setCellValue(FormatUtils.getStringValue(map.get("code")));
								itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(map.get("accountTime")));
								itemRow.getCell(3).setCellValue(FormatUtils.getStringValue(map.get("clientName")));
								itemRow.getCell(4).setCellValue(FormatUtils.getStringValue(map.get("productName")));
								itemRow.getCell(5).setCellValue(FormatUtils.getDoubleValue(map.get("totalFee")));
								itemRow.getCell(6).setCellValue(getStatusVal(map.get("status")));
								break;
							case 3:		
								itemRow.getCell(0).setCellValue(FormatUtils.getStringValue(map.get("code")));	
								itemRow.getCell(1).setCellValue(FormatUtils.getStringValue(map.get("accountTime")));
								itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(map.get("clientName")));
								itemRow.getCell(3).setCellValue(FormatUtils.getStringValue(map.get("productName")));
								itemRow.getCell(4).setCellValue(FormatUtils.getDoubleValue(map.get("totalFee")));
								itemRow.getCell(5).setCellValue(getStatusVal(map.get("status")));
								break;
							case 4:
								itemRow.getCell(0).setCellValue(FormatUtils.getStringValue(map.get("code")));	
								itemRow.getCell(1).setCellValue(FormatUtils.getStringValue(map.get("accountTime")));
								itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(map.get("shipNames")));
								itemRow.getCell(3).setCellValue(FormatUtils.getStringValue(map.get("arrivalTime")));
								itemRow.getCell(4).setCellValue(FormatUtils.getStringValue(map.get("productName")));
								
								clientNamesArray=FormatUtils.getStringValue(map.get("clientNames")).split(",");
								ladingClientNameArray=FormatUtils.getStringValue(map.get("ladingClientName")).split(",");
								tradeTypeArray=FormatUtils.getStringValue(map.get("tradeType")).split(",");
								ladingEvidenceArray=FormatUtils.getStringValue(map.get("ladingEvidence")).split(",");
								actualNumArray=FormatUtils.getStringValue(map.get("actualNums")).split(",");
								
								itemRow.getCell(5).setCellValue(clientNamesArray[0]);
								itemRow.getCell(6).setCellValue(ladingClientNameArray[0]);
								itemRow.getCell(7).setCellValue(getTradeTypeVal(tradeTypeArray[0]));
								itemRow.getCell(8).setCellValue(ladingEvidenceArray[0]);
								itemRow.getCell(9).setCellValue(actualNumArray[0]);
								itemRow.getCell(10).setCellValue(FormatUtils.getDoubleValue(map.get("actualNum")));
								itemRow.getCell(11).setCellValue(FormatUtils.getDoubleValue(map.get("totalFee")));
								itemRow.getCell(12).setCellValue(getStatusVal(map.get("status")));
								
								itemLen=clientNamesArray.length;
								startRow=itemRowNum;
								endRow=itemRowNum+itemLen-1;
								for(int n=1;n<itemLen;n++){
									itemRowNum++;
									itemRow=sheet.createRow(itemRowNum);
									itemRow.setHeight(sheet.getRow(1).getHeight());
									for(int j=0;j<colLength;j++){
										itemRow.createCell(j).setCellStyle(sheet.getRow(1).getCell(j).getCellStyle());
									}
									itemRow.getCell(5).setCellValue(clientNamesArray[n]);
									itemRow.getCell(6).setCellValue(ladingClientNameArray[n]);
									itemRow.getCell(7).setCellValue(getTradeTypeVal(tradeTypeArray[n]));
									itemRow.getCell(8).setCellValue(ladingEvidenceArray[n]);
									itemRow.getCell(9).setCellValue(actualNumArray[n]);
								}
								if(itemLen!=1){
								sheet.addMergedRegion(new Region(startRow,(short)0, endRow, (short)0)); 
								sheet.addMergedRegion(new Region(startRow,(short)1, endRow, (short)1)); 
								sheet.addMergedRegion(new Region(startRow,(short)2, endRow, (short)2)); 
								sheet.addMergedRegion(new Region(startRow,(short)3, endRow, (short)3)); 
								sheet.addMergedRegion(new Region(startRow,(short)4, endRow, (short)4)); 
								sheet.addMergedRegion(new Region(startRow,(short)10, endRow, (short)10)); 
								sheet.addMergedRegion(new Region(startRow,(short)11, endRow, (short)11)); 
								sheet.addMergedRegion(new Region(startRow,(short)12, endRow, (short)12)); 
								}
								break;
							case 5:
								itemRow.getCell(0).setCellValue(FormatUtils.getStringValue(map.get("cargoCode")));	
								itemRow.getCell(1).setCellValue(FormatUtils.getStringValue(map.get("code")));
								itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(map.get("accountTime")));
								itemRow.getCell(3).setCellValue(FormatUtils.getStringValue(map.get("shipName")));
								itemRow.getCell(4).setCellValue(FormatUtils.getStringValue(map.get("arrivalTime")));
								itemRow.getCell(5).setCellValue(getContractType(map.get("contractType")));
								itemRow.getCell(6).setCellValue(getTaxTypeVal(map.get("taxType")));
								itemRow.getCell(7).setCellValue(FormatUtils.getStringValue(map.get("clientName")));
								itemRow.getCell(8).setCellValue(FormatUtils.getStringValue(map.get("productName")));
								itemRow.getCell(9).setCellValue(FormatUtils.getDoubleValue(map.get("goodsInspect")));
								itemRow.getCell(10).setCellValue(FormatUtils.getDoubleValue(map.get("totalFee")));
								itemRow.getCell(11).setCellValue(getStatusVal(map.get("status")));
								break;

							default:
								break;
							}
					  itemRowNum++;	
					}
				} catch (OAException e) {
					e.printStackTrace();
				}
			}
		}).getWorkbook();
	}

	private String getContractType(Object item) {
		if (item == null || item.toString().equals("null")) {
			return "";
		} else {
			try {
				int mItem = Integer.valueOf(item.toString());
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
			} catch (NumberFormatException e) {
				return "";
			}
		}
		return "";
	};
	
	private String getTaxTypeVal(Object item){
		if (item == null || item.toString().equals("null")) {
			return "";
		} else {
			try {
				int mItem = Integer.valueOf(item.toString());
				switch (mItem) {
				case 1:
					return "内贸";
				case 2:
					return "外贸";
				case 3:
					return "保税";
				default:
					break;
				}
			} catch (NumberFormatException e) {
				return "";
			}
		}
		return "";
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
	};
	private String getTradeTypeVal(Object item){
		if (item == null || item.toString().equals("null")) {
			return "";
		} else {
			try {
				int mItem = Integer.valueOf(item.toString());
				if(mItem==1){
					return "内贸";
				}else{
					return "外贸";
				}
			} catch (NumberFormatException e) {
				return "";
			}
		}
	};
}
