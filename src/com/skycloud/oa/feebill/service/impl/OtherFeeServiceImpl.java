/**
 * 
 */
package com.skycloud.oa.feebill.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dao.FeeChargeDao;
import com.skycloud.oa.feebill.dao.OtherFeeCargoDao;
import com.skycloud.oa.feebill.dao.OtherFeeDao;
import com.skycloud.oa.feebill.dto.FeeChargeDto;
import com.skycloud.oa.feebill.dto.OtherFeeCargoDto;
import com.skycloud.oa.feebill.dto.OtherFeeDto;
import com.skycloud.oa.feebill.model.OtherFee;
import com.skycloud.oa.feebill.service.OtherFeeService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.report.utils.FormatUtils;
import com.skycloud.oa.utils.ExcelUtil;
import com.skycloud.oa.utils.ExcelUtil.CallBack;
import com.skycloud.oa.utils.OaMsg;

/**
 *
 * @author jiahy
 * @version 2015年10月21日 下午3:53:45
 */
@Service
public class OtherFeeServiceImpl implements OtherFeeService {
	private static Logger LOG = Logger.getLogger(FeeChargeServiceImpl.class);
	@Autowired
	private OtherFeeDao otherFeeDao;
	@Autowired
	private OtherFeeCargoDao otherFeeCargoDao;
	@Autowired
	private FeeChargeDao feeChargeDao;
	@Override
	public OaMsg getOtherFeeList(OtherFeeDto otherFeeDto, PageView pageView)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(otherFeeDao.getList(otherFeeDto, pageView.getStartRecord(), pageView.getMaxresult()));
			oaMsg.getMap().put(Constant.TOTALRECORD, otherFeeDao.getListCount(otherFeeDto)+"");
		}catch (RuntimeException e){
			LOG.error("service 获取其他费用失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获取其他费用失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_OTHERFEE,type=C.LOG_TYPE.CREATE)
	public OaMsg addOtherFee(OtherFeeDto otherFeeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			int id=otherFeeDao.addOtherFee(new OtherFee(otherFeeDto));
			oaMsg.getMap().put("id", id+"");
			 if(otherFeeDto.getOtherFeeCargos()!=null&&otherFeeDto.getOtherFeeCargos().size()>0){
				 for(int i=0;i<otherFeeDto.getOtherFeeCargos().size();i++){
					 otherFeeDto.getOtherFeeCargos().get(i).setOtherFeeId(id);
					 otherFeeCargoDao.addOtherFeeCargo(otherFeeDto.getOtherFeeCargos().get(i));
				 }
			 }
			 otherFeeDto.getFeecharge().setInitialId(id);
			 if(otherFeeDto.getFeecharge()!=null){
			feeChargeDao.insertfeeChargeCargoLading(feeChargeDao.addFeeCharge(otherFeeDto.getFeecharge()));
			 }
		}catch (RuntimeException e){
			LOG.error("service 添加费用失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 添加费用失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_OTHERFEE,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateOtherFee(OtherFeeDto otherFeeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			 if(otherFeeDto.getOtherFeeCargos()!=null&&otherFeeDto.getOtherFeeCargos().size()>0){
				 for(int i=0;i<otherFeeDto.getOtherFeeCargos().size();i++){
					 if(otherFeeDto.getOtherFeeCargos().get(i).getId()!=null){
						 otherFeeCargoDao.updateOtherFeeCargo(otherFeeDto.getOtherFeeCargos().get(i));
					 }else{
						 otherFeeDto.getOtherFeeCargos().get(i).setOtherFeeId(otherFeeDto.getId());
						 otherFeeCargoDao.addOtherFeeCargo(otherFeeDto.getOtherFeeCargos().get(i));
					 }
				 }
			 }
			otherFeeDao.updateOtherFee(new OtherFee(otherFeeDto));
			if(otherFeeDto.getFeecharge()!=null){
			feeChargeDao.updateFeeCharge(otherFeeDto.getFeecharge());
			}
		}catch (RuntimeException e){
			LOG.error("service 添加费用失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 添加费用失败",e);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getCodeNum(OtherFeeDto otherFeeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			Map<String,Object> code =otherFeeDao.getCodeNum(otherFeeDto);
			int num=0;
			if(code!=null&&code.get("code")!=null){
              num=Integer.valueOf(code.get("code").toString());				
			}
			String codeNum=(num+1)+"";
			switch (codeNum.length()) {
			case 1:
				codeNum="000000"+codeNum;
				break;
			case 2:
				codeNum="00000"+codeNum;
				break;
			case 3:
				codeNum="0000"+codeNum;
				break;
			case 4:
				codeNum="000"+codeNum;
				break;
			case 5:
				codeNum="00"+codeNum;
				break;
			case 6:
				codeNum="0"+codeNum;
				break;
			case 7:
				codeNum=""+codeNum;
				break;
			default:
				break;
			}
			Map<String,String> map=new HashMap<String, String>();
			map.put("codeNum", codeNum);
			oaMsg.getData().add(map);
			
			
		}catch (RuntimeException e){
			LOG.error("service 添加费用失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 添加费用失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_OTHERFEE,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteOtherFee(OtherFeeDto otherFeeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			FeeChargeDto feeChargeDto=new FeeChargeDto();
			feeChargeDto.setInitialId(otherFeeDto.getId());
			feeChargeDto.setFeeType(6);
			List<Map<String,Object>> dataList=feeChargeDao.getFeeChargeList(feeChargeDto,0,0);
			if(dataList.size()>0){
				if(dataList.get(0).get("feebillId")==null||Integer.valueOf(dataList.get(0).get("feebillId").toString())==0){//为空，或为0
					 otherFeeDao.deleteOtherFee(otherFeeDto);
					 FeeChargeDto fd=new FeeChargeDto();
					 fd.setOtherFeeId(otherFeeDto.getId());
					 feeChargeDao.deleteCargoLadingOfFeeCharge(fd);
					 feeChargeDao.deleteFeeCharge(fd);
					 otherFeeCargoDao.deleteOtherFeeCargo(new OtherFeeCargoDto(otherFeeDto.getId()));
				}else{
					oaMsg.setMsg("该结算单已生成账单，无法删除！");
				}
			}else{
				 otherFeeDao.deleteOtherFee(otherFeeDto);
				 FeeChargeDto fd=new FeeChargeDto();
				 fd.setOtherFeeId(otherFeeDto.getId());
				 feeChargeDao.deleteFeeCharge(fd);
				 feeChargeDao.deleteCargoLadingOfFeeCharge(fd);
				 otherFeeCargoDao.deleteOtherFeeCargo(new OtherFeeCargoDto(otherFeeDto.getId()));
			}
		
		}catch (RuntimeException e){
			LOG.error("service 添加费用失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 添加费用失败",e);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getCargoMsg(OtherFeeDto otherFeeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(otherFeeDao.getCargoMsg(otherFeeDto));
		}catch (RuntimeException e){
			LOG.error("service 添加费用失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 添加费用失败",e);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getOtherFeeCargo(OtherFeeDto otherFeeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(otherFeeCargoDao.getList(new OtherFeeCargoDto(otherFeeDto.getId()), 0, 0));
		}catch (RuntimeException e){
			LOG.error("service 添加费用失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 添加费用失败",e);
		}
		return oaMsg;
	}

	@Override
	public OaMsg deleteFeeCargo(OtherFeeCargoDto oDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			otherFeeCargoDao.deleteOtherFeeCargo(oDto);
		}catch (RuntimeException e){
			LOG.error("service 添加费用失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 添加费用失败",e);
		}
		return oaMsg;
	}

	/**
	 * @Title exportExcelList
	 * @Descrption:TODO
	 * @param:@param request
	 * @param:@param eFeeDto
	 * @param:@return
	 * @auhor jiahy
	 * @date 2016年9月23日下午2:12:10
	 * @throws
	 */
	@Override
	public HSSFWorkbook exportExcelList(HttpServletRequest request,final OtherFeeDto eFeeDto) {
		return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/otherFeeList.xls", new CallBack() {
			
			@Override
			public void setSheetValue(HSSFSheet sheet) {
				try {
					List<Object> data=getOtherFeeList(eFeeDto, new PageView(0, 0)).getData();
					HSSFRow itemRow=null;
					int itemRowNum=2;
					int itemColNum=sheet.getRow(1).getLastCellNum();
					Map<String, Object> map=null;
					if(data!=null&&data.size()>0){
						for (int i = 0,len=data.size(); i < len; i++) {
							itemRow=sheet.createRow(itemColNum++);
							itemRow.setHeight(sheet.getRow(1).getHeight());
							for(int j=0;j<itemColNum;j++)
								itemRow.createCell(j).setCellStyle(sheet.getRow(1).getCell(j).getCellStyle());
							map=(Map<String, Object>) data.get(i);
							itemRow.getCell(0).setCellValue(FormatUtils.getStringValue(map.get("code")));
							itemRow.getCell(1).setCellValue(FormatUtils.getStringValue(map.get("accountTime")));
							itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(map.get("clientName")));
							itemRow.getCell(3).setCellValue(FormatUtils.getStringValue(map.get("productName")));
							itemRow.getCell(4).setCellValue(FormatUtils.getStringValue(map.get("unitFee")));
							itemRow.getCell(5).setCellValue(FormatUtils.getStringValue(map.get("feeCount")));
							itemRow.getCell(6).setCellValue(FormatUtils.getStringValue(map.get("totalFee")));
							itemRow.getCell(7).setCellValue(getStatusVal(map.get("status")));
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
	};
}
