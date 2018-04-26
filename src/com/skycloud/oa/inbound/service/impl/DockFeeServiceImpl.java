package com.skycloud.oa.inbound.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dao.DockFeeChargeDao;
import com.skycloud.oa.feebill.dto.DockFeeChargeDto;
import com.skycloud.oa.feebill.model.DockFeeCharge;
import com.skycloud.oa.feebill.model.FeeCharge;
import com.skycloud.oa.inbound.dao.DockFeeDao;
import com.skycloud.oa.inbound.dto.DockFeeDto;
import com.skycloud.oa.inbound.dto.ExceedFeeLogDto;
import com.skycloud.oa.inbound.model.DockFee;
import com.skycloud.oa.inbound.service.DockFeeService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.report.utils.FormatUtils;
import com.skycloud.oa.utils.ExcelUtil;
import com.skycloud.oa.utils.OaMsg;
import com.skycloud.oa.utils.ExcelUtil.CallBack;
/**
 *码头规费
 * @author 作者:jiahy
 * @version 时间：2015年3月16日 下午2:24:34
 */
@Service
public class DockFeeServiceImpl implements DockFeeService {
	private static Logger LOG = Logger.getLogger(DockFeeServiceImpl.class);
    @Autowired
    private DockFeeDao dockFeeDao;
    @Autowired
    private DockFeeChargeDao dockFeeChargeDao;
	@Override
	public OaMsg getDockFeeList(DockFeeDto dFeeDto, PageView pageView) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			List<Map<String, Object>> dataList=dockFeeDao.getDockFeeList(dFeeDto,pageView.getStartRecord(),pageView.getMaxresult());
			if(dataList!=null&&dataList.size()>0){
				for(int i=0,len=dataList.size();i<len;i++)
					dataList.get(i).putAll(dockFeeDao.getDockFeePrice(Integer.valueOf(dataList.get(i).get("id").toString())));
			}
			
			oaMsg.getData().addAll(dataList);
			oaMsg.getMap().put(Constant.TOTALRECORD,dockFeeDao.getDockFeeCount(dFeeDto)+"");
		}catch (RuntimeException e){
			LOG.error("service 获取码头规费结算单列表失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获取码头规费结算单列表失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_DOCKFEE,type=C.LOG_TYPE.UPDATE)
	public OaMsg addOrUpdateDockFee(DockFeeDto dFeeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			DockFee dockFee=dFeeDto.getDockfee();
			Integer dockFeeId=0;
			if(dockFee.getId()!=null){
				dockFeeId=dockFee.getId();
				dockFeeDao.updateDockFee(dockFee);
			}else{
				dockFeeId=dockFeeDao.addDockFee(dockFee);
			}
			List<DockFeeCharge> feeChargeList=dFeeDto.getFeeChargeList();
			if(feeChargeList!=null&&feeChargeList.size()>0){
				for(DockFeeCharge feeCharge:feeChargeList){
					feeCharge.setDockFeeId(dockFeeId);
				if(feeCharge.getId()!=null){
					dockFeeChargeDao.updateDockFeeCharge(feeCharge);
				}else{
					dockFeeChargeDao.addDockFeeCharge(feeCharge);
				}
				}
			}
			oaMsg.getMap().put("id", dockFeeId+"");
		}catch (RuntimeException e){
			LOG.error("service 添加或更新码头规费结算单失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 添加或更新码头规费结算单失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_DOCKFEE,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteDockFee(DockFeeDto dFeeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		List<Map<String,Object>> dataList= 	dockFeeDao.getDockFeeChargeList(dFeeDto);
		Integer type=0;
		int size=0;
		if(dataList!=null&&dataList.size()>0){
			size=dataList.size();
			for(int i=0;i<size;i++){
			if(dataList.get(i).get("feebillId")!=null&&Integer.valueOf(dataList.get(i).get("feebillId").toString())!=0){//为空，或为0
				type=1;
				break;
			}
			}
		}	
		if(type==1){
		oaMsg.setMsg("该结算单已生成账单，无法删除！");
		}else{
		dockFeeDao.deleteDockFee(dFeeDto);
		dockFeeChargeDao.deleteDockFeeCharge(new DockFeeChargeDto(dFeeDto.getId()));
		}
		}catch (RuntimeException e){
			LOG.error("service 删除码头规费结算单失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 删除码头规费结算单失败",e);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getCodeNum(DockFeeDto dFeeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getMap().put("codeNum", dockFeeDao.getCodeNum(dFeeDto).get("codeNum").toString());
		}catch (RuntimeException e){
			LOG.error("service 获取结算单编号失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获取结算单编号失败",e);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getArrivalList(DockFeeDto dFeeDto, PageView pageView) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(dockFeeDao.getArrivalList(dFeeDto,pageView.getStartRecord(),pageView.getMaxresult()));
			oaMsg.getMap().put(Constant.TOTALRECORD,dockFeeDao.getArrivalListCount(dFeeDto)+"");
		}catch (RuntimeException e){
			LOG.error("service 获取码头规费结算单列表失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获取码头规费结算单列表失败",e);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getDockFeeMsg(DockFeeDto dFeeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		Map<String,Object> data=dockFeeDao.getDockFeeMsg(dFeeDto);
         data.put("feecharge", dockFeeDao.getDockFeeChargeList(dFeeDto));    
		oaMsg.getData().add(data);
		}catch (RuntimeException e){
			LOG.error("service 获取码头规费结算单列表失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获取码头规费结算单详细信息失败",e);
		}
		return oaMsg;
	}

	@Override
	public HSSFWorkbook exportExcel(HttpServletRequest request, final DockFeeDto dFeeDto) throws OAException {
		return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/dockFee.xls", new CallBack() {
			
			@Override
			public void setSheetValue(HSSFSheet sheet) {
				
				try {
					List<Map<String,Object>> data=dockFeeDao.getDockFeeExcelList(dFeeDto);
					String startTime= new Timestamp(dFeeDto.getStartTime()*1000).toString();
					 sheet.getRow(0).getCell(0).setCellValue(startTime.substring(0, 4)+"年"+startTime.substring(5, 7)+"月份船舶停泊费结算清单");
					 sheet.getRow(1).getCell(0).setCellValue(FormatUtils.getStringValue(dFeeDto.getClientName())+":");
					 if(data!=null){
						 int size=data.size();
					 sheet.shiftRows(4, 5, size-1);
					 int item=0;
					 Map<String,Object> map;
					 HSSFRow itemRow;
					 for(int i=0;i<size;i++){
						item=i+3;
						if(i!=0){
						itemRow=sheet.createRow(item);
						itemRow.setHeight(sheet.getRow(2).getHeight());
						}else{
							itemRow=sheet.getRow(item);
						}
						for(int m=0;m<11;m++)
						{
							itemRow.createCell(m);
						itemRow.getCell(m).setCellStyle(sheet.getRow(2).getCell(m).getCellStyle());
						}
					   map= (Map<String, Object>) data.get(i); 
					   itemRow.getCell(0).setCellValue(FormatUtils.getDoubleValue(i+1));
						 itemRow.getCell(1).setCellValue(FormatUtils.getStringValue(map.get("shipName"))+"/"+FormatUtils.getStringValue(map.get("refName")));
						 itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(map.get("arrivalTime")));//靠泊日期
						 itemRow.getCell(3).setCellValue(FormatUtils.getDoubleValue(map.get("netTons")));//净吨
						 itemRow.getCell(4).setCellValue(FormatUtils.getDoubleValue(map.get("stayDay")));//在港天数
						 if(FormatUtils.getDoubleValue(map.get("tradeType"))==1){//内贸
							 itemRow.getCell(5).setCellValue(0.25);//单价
						 }else{
							 itemRow.getCell(5).setCellValue(0.08);//单价
						 }
//						 itemRow.getCell(6).setCellValue(FormatUtils.getDoubleValue(map.get("berthFee")));//靠泊费
						 itemRow.getCell(7).setCellValue(FormatUtils.getDoubleValue(map.get("waterFee")));//淡水费
						 itemRow.getCell(6).setCellType(HSSFCell.CELL_TYPE_FORMULA);
						 itemRow.getCell(6).setCellFormula("D"+(item+1)+"*E"+(item+1)+"*F"+(item+1));
						 itemRow.getCell(8).setCellType(HSSFCell.CELL_TYPE_FORMULA);
						 itemRow.getCell(8).setCellFormula("SUM(G"+(item+1)+",H"+(item+1)+")");
						 itemRow.getCell(9).setCellValue(FormatUtils.getStringValue(map.get("arrivalType")));//到港类型
					 }
					 itemRow=sheet.getRow(size+3);
					 itemRow.setHeight(sheet.getRow(2).getHeight());
					 itemRow.getCell(6).setCellType(HSSFCell.CELL_TYPE_FORMULA);
					 itemRow.getCell(6).setCellFormula("SUM(G4:G"+(size+3)+")");
					 itemRow.getCell(7).setCellType(HSSFCell.CELL_TYPE_FORMULA);
					 itemRow.getCell(7).setCellFormula("SUM(H4:H"+(size+3)+")");
					 itemRow.getCell(8).setCellType(HSSFCell.CELL_TYPE_FORMULA);
					 itemRow.getCell(8).setCellFormula("SUM(I4:I"+(size+3)+")");
					 sheet.getRow(size+4).setHeight(sheet.getRow(2).getHeight());
					 sheet.getRow(size+4).getCell(8).setCellValue(dFeeDto.getNowTime());
					 }
					 sheet.setForceFormulaRecalculation(true);
				} catch (OAException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).getWorkbook();
		
	}

	/**
	 * @Title exportListExcel
	 * @Descrption:TODO
	 * @param:@param request
	 * @param:@param dFeeDto
	 * @param:@return
	 * @param:@throws OAE
	 * @auhor jiahy
	 * @date 2016年12月15日上午8:28:39
	 * @throws
	 */
	@Override
	public HSSFWorkbook exportListExcel(HttpServletRequest request,final DockFeeDto dFeeDto) {
		return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/dockListFee.xls",new CallBack() {
			@Override
			public void setSheetValue(HSSFSheet sheet) {
			try {
				List<Map<String, Object>> data=dockFeeDao.getDockFeeExcelList(dFeeDto);
				HSSFRow itemRow=null;
				Integer item=2;
				Map<String, Object> map=null;
				if(data!=null&&data.size()>0){
					sheet.shiftRows(2,2, data.size());
					for(int i=0,len=data.size();i<len;i++){
						item=i+2;
						itemRow=sheet.createRow(item);
						itemRow.setHeight(sheet.getRow(1).getHeight());
						for(int j=0;j<13;j++)
							itemRow.createCell(j).setCellStyle(sheet.getRow(1).getCell(j).getCellStyle());;
						map=data.get(i);	
						itemRow.getCell(0).setCellValue(i+1);
						itemRow.getCell(1).setCellValue(FormatUtils.getStringValue(map.get("clientName")));
						itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(map.get("boundMsg")));
						itemRow.getCell(3).setCellValue(FormatUtils.getStringValue(map.get("arrivalTime")));
						itemRow.getCell(4).setCellValue(FormatUtils.getDoubleValue(map.get("netTons")));
						itemRow.getCell(5).setCellValue(FormatUtils.getDoubleValue(map.get("stayDay")));
						 if(FormatUtils.getDoubleValue(map.get("tradeType"))==1)//内贸
							 itemRow.getCell(6).setCellValue(0.25);//单价
						 else
							 itemRow.getCell(6).setCellValue(0.08);//单价
						 itemRow.getCell(10).setCellValue(FormatUtils.getStringValue(map.get("arrivalType")));
						 itemRow.getCell(11).setCellValue(FormatUtils.getStringValue(map.get("billTypeStr")));
						itemRow.getCell(8).setCellValue(FormatUtils.getDoubleValue(map.get("waterFee")));
						 itemRow.getCell(7).setCellType(HSSFCell.CELL_TYPE_FORMULA);
						 itemRow.getCell(7).setCellFormula("E"+(item+1)+"*F"+(item+1)+"*G"+(item+1));
						 itemRow.getCell(9).setCellType(HSSFCell.CELL_TYPE_FORMULA);
						 itemRow.getCell(9).setCellFormula("SUM(H"+(item+1)+",I"+(item+1)+")");
					}
					 itemRow=sheet.getRow(data.size()+2);
					 itemRow.setHeight(sheet.getRow(2).getHeight());
					 itemRow.getCell(7).setCellType(HSSFCell.CELL_TYPE_FORMULA);
					 itemRow.getCell(7).setCellFormula("SUM(H3:H"+(data.size()+2)+")");
					 itemRow.getCell(8).setCellType(HSSFCell.CELL_TYPE_FORMULA);
					 itemRow.getCell(8).setCellFormula("SUM(I3:I"+(data.size()+2)+")");
					 itemRow.getCell(9).setCellType(HSSFCell.CELL_TYPE_FORMULA);
					 itemRow.getCell(9).setCellFormula("SUM(J3:J"+(data.size()+2)+")");
				}
			} catch (OAException e) {
				e.printStackTrace();
			}
			}
		}).getWorkbook();
	}

	@Override
	public OaMsg copy(DockFeeCharge dockFeeCharge) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
					dockFeeChargeDao.addDockFeeCharge(dockFeeCharge);
		}catch (RuntimeException e){
			LOG.error("service 添加费用项失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 添加费用项失败",e);
		}
		return oaMsg;
	}
	
}
