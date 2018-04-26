/**
 * 
 */
package com.skycloud.oa.feebill.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
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
import com.skycloud.oa.feebill.dao.AccountBillLogDao;
import com.skycloud.oa.feebill.dao.FeeBillDao;
import com.skycloud.oa.feebill.dao.FeeChargeDao;
import com.skycloud.oa.feebill.dto.AccountBillLogDto;
import com.skycloud.oa.feebill.dto.FeeBillDto;
import com.skycloud.oa.feebill.dto.FeeChargeDto;
import com.skycloud.oa.feebill.model.FeeBill;
import com.skycloud.oa.feebill.service.FeeBillService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.report.utils.FormatUtils;
import com.skycloud.oa.system.dao.ApproveContentDao;
import com.skycloud.oa.system.dto.ApproveContentDto;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.ExcelUtil;
import com.skycloud.oa.utils.ExcelUtil.CallBack;
import com.skycloud.oa.utils.OaMsg;
/**
 *商务部账单
 * @author jiahy
 * @version 2015年6月14日 下午6:07:50
 */
@Service
public class FeeBillServiceImpl implements FeeBillService {
	private static Logger LOG = Logger.getLogger(FeeBillServiceImpl.class);
	@Autowired
	private FeeBillDao feeBillDao;
	@Autowired
	private FeeChargeDao feeChargeDao;
	@Autowired
	private AccountBillLogDao accountBillLogDao;
	@Autowired
	private ApproveContentDao approveContentDao;
	@Override
	public OaMsg getFeeBillList(FeeBillDto feeBillDto, PageView pageView)throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			List<Map<String, Object>> data=feeBillDao.getFeeBillList(feeBillDto,pageView.getStartRecord(),pageView.getMaxresult());
			for(Map<String, Object> map:data){
				map.putAll(feeBillDao.getCargoCodesByfeebillId(Integer.valueOf(map.get("id").toString())));
				map.putAll(feeBillDao.getUpLoadFilesByfeebillId(Integer.valueOf(map.get("id").toString()),2));
			}
			oaMsg.getData().addAll(data);
			oaMsg.getMap().put(Constant.TOTALRECORD,feeBillDao.getFeeBillCount(feeBillDto)+"");
		}catch (RuntimeException e){
			LOG.error("service 获得账单列表失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获得账单列表失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_FEEBILL,type=C.LOG_TYPE.CREATE)
	public OaMsg addFeeBill(FeeBill feeBill) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getMap().put("id",feeBillDao.addFeeBill(feeBill)+"");
		}catch (RuntimeException e){
			LOG.error("service 添加账单失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 添加账单失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_FEEBILL,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateFeeBill(FeeBill feeBill) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			feeBillDao.updateFeeBill(feeBill);
			FeeChargeDto fDto=new FeeChargeDto();
			fDto.setFeebillId(feeBill.getId());
			List<Map<String, Object>> data=feeChargeDao.getFeeChargeList(fDto);
			if(data!=null&&data.size()>0){
                for (int i = 0; i < data.size(); i++) {
					feeChargeDao.updateInitialFeeAndExceedFeeStatus(Integer.valueOf(data.get(i).get("id").toString()));
				}				
			}
		}catch (RuntimeException e){
			LOG.error("service 更新账单失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 更新账单失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_FEEBILL,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteFeeBill(FeeBillDto feeBillDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			feeBillDao.deleteFeeBill(feeBillDto);
			feeBillDao.backFeeStatus(feeBillDto);
			FeeChargeDto fd=new FeeChargeDto();
			fd.setFeebillId(feeBillDto.getId());
			feeChargeDao.backFeeChargeFeeHead(fd);//返回费用项之前的抬头
			feeChargeDao.cleanFeeCharge(fd);
			AccountBillLogDto acDto=new AccountBillLogDto();
			acDto.setFeebillId(feeBillDto.getId());
			accountBillLogDao.deleteAccountBillLog(acDto);
		}catch (RuntimeException e){
			LOG.error("service 删除账单失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 删除账单失败",e);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getCodeNum(FeeBillDto feeBillDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			List<Map<String,Object>> code =feeBillDao.getCodeNum(feeBillDto);
			int num=0;
			if(code!=null&&code.size()==1&&code.get(0).get("code")!=null){
              num=Integer.valueOf(code.get(0).get("code").toString());				
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
			LOG.error("service 获取账单号失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获取账单号失败",e);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getTotalFee(FeeBillDto feeBillDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(feeBillDao.getTotalFee(feeBillDto));
		}catch (RuntimeException e){
			LOG.error("service 更新账单失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 更新账单失败",e);
		}
		return oaMsg;
	}

	/**
	 * @Title rollback
	 * @Descrption:TODO
	 * @param:@param feeBillDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年3月18日上午9:47:28
	 * @throws
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_FEEBILL,type=C.LOG_TYPE.REBACK)
	public OaMsg rollback(FeeBillDto feeBillDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			FeeBill feeBill=new FeeBill();
			feeBill.setId(feeBillDto.getId());
			feeBill.setStatus(feeBillDto.getStatus());
			if(feeBillDto.getStatus()==0){//未提交状态
				feeBill.setBillingCode("");
				feeBill.setBillingStatus(-1);
				feeBill.setBillingTime(-1L);
				feeBill.setUnTaxFee(0+"");
				feeBill.setTaxRate(0+"");
				feeBill.setTaxFee(0+"");
				feeBill.setReviewTime(-1L);
				feeBill.setReviewUserId(-1);
//				feeBill.setFundsStatus(0);
			//删除审核信息
			ApproveContentDto acDto= new ApproveContentDto();
			 acDto.setTypeId(feeBillDto.getId());
			 acDto.setType(5);
			 approveContentDao.cleanApproveContent(acDto);	
//			//删除到账信息
//			 AccountBillLogDto abDto= new AccountBillLogDto();
//			 abDto.setFeebillId(feeBillDto.getId());
//			accountBillLogDao.deleteAccountBillLog(abDto);	
			}else if(feeBillDto.getStatus()==2){
				feeBill.setBillingStatus(0);
				feeBill.setFundsStatus(0);
			}
			feeBillDao.updateFeeBill(feeBill);
		}catch (RuntimeException e){
			LOG.error("service 回退账单失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 回退账单失败",e);
		}
		return oaMsg;
	}

	/**
	 * @Title exportDetailFee
	 * @Descrption:TODO
	 * @param:@param request
	 * @param:@param feeBillDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年7月26日下午3:31:48
	 * @throws
	 */
	@Override
	public HSSFWorkbook exportDetailFee(HttpServletRequest request, final FeeBillDto feeBillDto) throws OAException {
		return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/feebill.xls",new CallBack() {
			@Override
			public void setSheetValue(HSSFSheet sheet) {
			try {
				//TODO 获取结算单id以及结算单类型
				List<Map<String, Object>> data=feeBillDao.getBillFeeList(feeBillDto);
				Map<String, Object> feeMapData=new HashMap<String, Object>();
				List<Map<String, Object>> feeChargeData=new ArrayList<Map<String, Object>>();
				FeeBillDto fDto=new FeeBillDto();
				fDto.setFeebillId(feeBillDto.getId());
				Integer itemRows=0,feeChargeLen=0;
				Double totalFee=0D;
				HSSFRow itemFeeRow;
 				HSSFCellStyle[] itemDownCellStyle={sheet.getRow(10).getCell(0).getCellStyle(),sheet.getRow(10).getCell(1).getCellStyle(),
 						sheet.getRow(10).getCell(2).getCellStyle(),sheet.getRow(10).getCell(3).getCellStyle()};
				//根据不同结算单处理不同的 显示 情况
				if(data!=null&&data.size()>0){
					String reviewUserName= getStringValue(data.get(0).get("reviewUserName"));
					String feeHeadName=getStringValue(data.get(0).get("feeHeadName"));
					for(int i=0,len=data.size();i<len;i++){
						fDto.setFeeType(Integer.valueOf(data.get(i).get("feeType").toString()));
						fDto.setId(Integer.valueOf(data.get(i).get("initialId").toString())!=0?Integer.valueOf(data.get(i).get("initialId").toString()):Integer.valueOf(data.get(i).get("exceedId").toString()));
						fDto.setInitialType(Integer.valueOf(data.get(i).get("initialType").toString()));
						totalFee=0D;
						if(fDto.getFeeType()==5){
							sheet.getRow(3).getCell(2).setCellValue("提单号：");
						}
						//TODO 获取每个结算单数据
						feeMapData=getItemFee(fDto);
						if (i != 0) {
							for (int n = 0; n < 11; n++) {
								itemFeeRow = sheet.createRow(itemRows + n);
								itemFeeRow.setHeight(sheet.getRow(0).getHeight());
								for (int m = 0; m < 4; m++) {
									itemFeeRow.createCell(m);
									if (n < 8) {
										if (n==7||(n != 7 && (m == 0 || m == 2)))
											itemFeeRow.getCell(m).setCellValue(sheet.getRow(n).getCell(m).getStringCellValue());
										itemFeeRow.getCell(m).setCellStyle(sheet.getRow(n).getCell(m).getCellStyle());
									} else if (n == 8) {
										if (m == 0)
											itemFeeRow.getCell(m).setCellValue("开始日期：");
										if (m == 2)
											itemFeeRow.getCell(m).setCellValue("截止日期：");
										itemFeeRow.getCell(m).setCellStyle(sheet.getRow(1).getCell(m).getCellStyle());
									} else if (n == 9) {
										if (m == 0)
											itemFeeRow.getCell(m).setCellValue("备注：");
										itemFeeRow.getCell(m).setCellStyle(sheet.getRow(1).getCell(m).getCellStyle());
									} else if (n == 10) {
										if (m == 0)
											itemFeeRow.getCell(m).setCellValue("制单：");
										if (m == 2)
											itemFeeRow.getCell(m).setCellValue("审核：");
										itemFeeRow.getCell(m).setCellStyle(itemDownCellStyle[m]);
									}
								}
								if (n == 0) {
									sheet.addMergedRegion(new Region(itemRows + n, (short) 0, itemRows + n, (short) 3));
								} else if (n == 9||n==2) {
									sheet.addMergedRegion(new Region(itemRows + n, (short) 1, itemRows + n, (short) 3));
									sheet.addMergedRegion(new Region(itemRows + n, (short) 1, itemRows + n, (short) 3));
								}
							}
						}
				    //TODO 结算单基本内容
					sheet.getRow(itemRows+1).getCell(1).setCellValue(getStringValue(feeMapData.get("code")));//结算单编号
					sheet.getRow(itemRows+1).getCell(3).setCellValue(handleTime(feeMapData.get("accountTime")));//结算日期
					sheet.getRow(itemRows+2).getCell(1).setCellValue(feeHeadName);//结算日期
					sheet.getRow(itemRows+3).getCell(1).setCellValue(getStringValue(feeMapData.get("clientName")));//单位
					sheet.getRow(itemRows+3).getCell(3).setCellValue(getStringValue(feeMapData.get("contractCode")));//合同号/提单号
					sheet.getRow(itemRows+4).getCell(1).setCellValue(getStringValue(feeMapData.get("productName")));//货品名称
					sheet.getRow(itemRows+4).getCell(3).setCellValue(getStringValue(feeMapData.get("cargoCode")));//货批号
					sheet.getRow(itemRows+5).getCell(1).setCellValue(getStringValue(feeMapData.get("shipName")));//船信
					sheet.getRow(itemRows+5).getCell(3).setCellValue(handleTime(feeMapData.get("arrivalTime")));//到港时间
					sheet.getRow(itemRows+6).getCell(1).setCellValue(getStringValue(feeMapData.get("periodTime")));//周期
					sheet.getRow(itemRows+6).getCell(3).setCellValue(getDoubleValue(feeMapData.get("goodsInspect")));//数量
					
					//TODO 每个结算单的费用项
					feeChargeData=(List<Map<String, Object>>) feeMapData.get("feeChargeMsg");
					if(feeChargeData!=null&&feeChargeData.size()>0){
						feeChargeLen=feeChargeData.size();	
						sheet.shiftRows(itemRows+8,itemRows+10,feeChargeLen+1,true,false);//a-b行向下移动1行
						for(int j=0;j<=feeChargeLen;j++){
							 HSSFRow itemRow= sheet.createRow(itemRows+8+j);
							 itemRow.setHeight(sheet.getRow(itemRows+7).getHeight());
							 if(j!=feeChargeLen){
							 itemRow.createCell(0).setCellValue(getFeeChargeStr(Integer.valueOf(feeChargeData.get(j).get("feeType").toString())));
							 itemRow.createCell(1).setCellValue(getDoubleValue(feeChargeData.get(j).get("unitFee"),2));
							 itemRow.createCell(2).setCellValue(getDoubleValue(feeChargeData.get(j).get("feeCount"),3));
							 itemRow.createCell(3).setCellValue(getDoubleValue(feeChargeData.get(j).get("totalFee"),2));
							 totalFee=Common.add(totalFee,getDoubleValue(feeChargeData.get(j).get("totalFee")),2);
							 }else{
								 itemRow.createCell(0).setCellValue("总计");
								 itemRow.createCell(1).setCellValue(" ");
								 itemRow.createCell(2).setCellValue(" ");
								 itemRow.createCell(3).setCellValue(getDoubleValue(totalFee));
							 }
							 itemRow.getCell(0).setCellStyle(sheet.getRow(itemRows+6).getCell(0).getCellStyle());
							 itemRow.getCell(1).setCellStyle(sheet.getRow(itemRows+6).getCell(1).getCellStyle());
							 itemRow.getCell(2).setCellStyle(sheet.getRow(itemRows+6).getCell(2).getCellStyle());
							 itemRow.getCell(3).setCellStyle(sheet.getRow(itemRows+6).getCell(3).getCellStyle());
						}
					}
					sheet.getRow(itemRows+feeChargeLen+9).getCell(1).setCellValue(handleTime(feeMapData.get("startTime")));//开始日期
					sheet.getRow(itemRows+feeChargeLen+9).getCell(3).setCellValue(handleTime(feeMapData.get("endTime")));//截止日期
					sheet.getRow(itemRows+feeChargeLen+10).getCell(1).setCellValue(getStringValue(feeMapData.get("description")));//备注
					sheet.getRow(itemRows+feeChargeLen+11).getCell(1).setCellValue(getStringValue(feeMapData.get("createUserName")));//制定人
					sheet.getRow(itemRows+feeChargeLen+11).getCell(3).setCellValue(reviewUserName);//审核人
					itemRows+=feeChargeLen+12;
				 }
				}
				//合计
				itemFeeRow = sheet.createRow(itemRows);
				itemFeeRow.setHeight(sheet.getRow(0).getHeight());
				for(int i=0;i<4;i++)
				itemFeeRow.createCell(i).setCellStyle(sheet.getRow(0).getCell(i).getCellStyle());
				sheet.addMergedRegion(new Region(itemRows, (short) 0, itemRows, (short) 3));
				itemFeeRow = sheet.createRow(++itemRows);
				itemFeeRow.setHeight(sheet.getRow(0).getHeight());
				for(int i=0;i<4;i++)
				itemFeeRow.createCell(i).setCellStyle(sheet.getRow(0).getCell(i).getCellStyle());
				sheet.addMergedRegion(new Region(itemRows, (short) 0, itemRows, (short) 3));
				itemFeeRow.getCell(0).setCellValue("费用项统计");
			
				itemFeeRow = sheet.createRow(++itemRows);
				itemFeeRow.setHeight(sheet.getRow(0).getHeight()); 
				String[]  feeChargeTitle={"费用类型","单价","合计金额（元）","扣损后合计金额（元）"};
				for(int i=0;i<4;i++){
					itemFeeRow.createCell(i).setCellStyle(sheet.getRow(6).getCell(i).getCellStyle());
					itemFeeRow.getCell(i).setCellValue(feeChargeTitle[i]);
				}
				
				List<Map<String, Object>> groupFeeChargeData=feeBillDao.getGroupFeeChargeList(feeBillDto);
				for(int i=0,len=groupFeeChargeData.size();i<len;i++){
					itemFeeRow = sheet.createRow(++itemRows);
					itemFeeRow.setHeight(sheet.getRow(0).getHeight()); 
					itemFeeRow.createCell(0).setCellValue(getFeeChargeStr(Integer.valueOf(groupFeeChargeData.get(i).get("feeType").toString())));
					itemFeeRow.createCell(1).setCellValue(getDoubleValue(groupFeeChargeData.get(i).get("unitFee"),2));
					itemFeeRow.createCell(2).setCellValue(getDoubleValue(groupFeeChargeData.get(i).get("totalFee"),2));
					itemFeeRow.createCell(3).setCellValue(getDoubleValue(groupFeeChargeData.get(i).get("discountFee"),2));
					for(int j=0;j<4;j++){
						if(i==len-1){
						itemFeeRow.getCell(j).setCellStyle(itemDownCellStyle[j]);	
						}else{
							itemFeeRow.getCell(j).setCellStyle(sheet.getRow(6).getCell(j).getCellStyle());
						}
					}
				}
				
			} catch (OAException e) {
				e.printStackTrace();
			}
			}
		}).getWorkbook();
	}

	/**
	 * 获取不同结算单的费用信息
	 * @Title getItemFee
	 * @Descrption:TODO
	 * @param:@param fDto
	 * @param:@return
	 * @return:Map<String,Object>
	 * @auhor jiahy
	 * @date 2016年7月27日上午10:13:46
	 * @throws
	 */
	protected Map<String, Object> getItemFee(FeeBillDto fDto) throws OAException{
		Map<String, Object> map=feeBillDao.getFeeMsg(fDto);
		map.put("feeChargeMsg", feeBillDao.getFeeChargeList(fDto));
		return map;
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
		default:
			break;
		}
		return "";
	}
}
