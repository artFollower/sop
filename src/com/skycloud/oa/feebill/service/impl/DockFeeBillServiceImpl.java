/**
 * 
 */
package com.skycloud.oa.feebill.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.From;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
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
import com.skycloud.oa.feebill.dao.AccountBillLogDao;
import com.skycloud.oa.feebill.dao.DockFeeBillDao;
import com.skycloud.oa.feebill.dao.DockFeeChargeDao;
import com.skycloud.oa.feebill.dto.AccountBillLogDto;
import com.skycloud.oa.feebill.dto.DockFeeBillDto;
import com.skycloud.oa.feebill.dto.DockFeeChargeDto;
import com.skycloud.oa.feebill.dto.FeeChargeDto;
import com.skycloud.oa.feebill.model.AccountBillLog;
import com.skycloud.oa.feebill.model.DockFeeBill;
import com.skycloud.oa.feebill.model.DockFeeCharge;
import com.skycloud.oa.feebill.model.FeeBill;
import com.skycloud.oa.feebill.service.DockFeeBillService;
import com.skycloud.oa.inbound.dao.DockFeeDao;
import com.skycloud.oa.inbound.dto.DockFeeDto;
import com.skycloud.oa.inbound.service.DockFeeService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.report.utils.FormatUtils;
import com.skycloud.oa.system.dto.ApproveContentDto;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.ExcelUtil;
import com.skycloud.oa.utils.OaMsg;
import com.skycloud.oa.utils.ExcelUtil.CallBack;

/**
 *
 * @author jiahy
 * @version 2015年6月14日 下午6:07:50
 */
@Service
public class DockFeeBillServiceImpl implements DockFeeBillService {
	private static Logger LOG = Logger.getLogger(DockFeeBillServiceImpl.class);
    @Autowired
    private DockFeeBillDao dockFeeBillDao;
    @Autowired
    private DockFeeChargeDao dockFeeChargeDao;
    @Autowired
	private AccountBillLogDao accountBillLogDao;
    @Autowired
    private DockFeeDao dockFeeDao;
	@Override
	public OaMsg getDockFeeBillList(DockFeeBillDto dDto, PageView pageView) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(dockFeeBillDao.getDockFeeBillList(dDto,pageView.getStartRecord(),pageView.getMaxresult()));
			oaMsg.getMap().put(Constant.TOTALRECORD,dockFeeBillDao.getDockFeeBillCount(dDto)+"");
		}catch (RuntimeException e){
			LOG.error("service 获得账单列表失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获得账单列表失败",e);
		}
		return oaMsg;
	}
   
	@Override
	public OaMsg getDockFeeBillMsg(DockFeeBillDto dDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			Map<String, Object> data=dockFeeBillDao.getDockFeeBillMsg(dDto);
			DockFeeChargeDto dfDto=new DockFeeChargeDto();
			dfDto.setFeebillId(dDto.getId());
			data.put("feeChargeList", dockFeeChargeDao.getDockFeeChargeList(dfDto, 0, 0));
			oaMsg.getData().add(data);
		}catch (RuntimeException e){
			LOG.error("service 获得账单明细失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获得账单明细失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_DOCKFEEBILL,type=C.LOG_TYPE.CREATE)
	public OaMsg addDockFeeBill(DockFeeBillDto ndfDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			DockFeeBill dfbill=ndfDto.getDockfeebill();
			List<DockFeeCharge> feeChargeList=ndfDto.getFeechargeList();
			List<AccountBillLog> accountBillLogList=ndfDto.getAccountBillList();
			if(accountBillLogList!=null&&accountBillLogList.size()>0){
				dfbill.setFundsStatus(2);
			}
			int dfbillId=0;
			if(dfbill.getId()==null||dfbill.getId()==0){
				dfbillId=dockFeeBillDao.addDockFeeBill(dfbill);
			}else{
				dfbillId=dfbill.getId();
				dockFeeBillDao.updateDockFeeBill(dfbill);
			}
			if(feeChargeList!=null&&feeChargeList.size()>0){
				for(DockFeeCharge dc:feeChargeList){
					dc.setFeebillId(dfbillId);
					dockFeeChargeDao.updateDockFeeCharge(dc);
				}
				}
			if(accountBillLogList!=null&&accountBillLogList.size()>0){
				for(AccountBillLog abL:accountBillLogList){
					abL.setDockfeebillId(dfbillId);
					if(Common.empty(abL.getAccountTime())){
						
						abL.setAccountTime(new Date().getTime()/1000);
					}
					abL.setAccountUserId(user.getId());
					if(abL.getId()==null||abL.getId()==0){
						accountBillLogDao.addAccountBillLog(abL);
					}else{
						accountBillLogDao.updateAccountBillLog(abL);
					}
				}
			}
			dockFeeBillDao.updateDockFeeStatus(dfbillId);
			oaMsg.getMap().put("id", dfbillId+"");
		}catch (RuntimeException e){
			LOG.error("service 添加账单失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 添加账单失败",e);
		}
		return oaMsg;
	}
	
	@Override
	@Log(object=C.LOG_OBJECT.PCS_DOCKFEEBILL,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateDockFeeBill(DockFeeBill dockFeeBill) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			dockFeeBillDao.updateDockFeeBill(dockFeeBill);
		}catch (RuntimeException e){
			LOG.error("service 获取结算单编号失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获取结算单编号失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_DOCKFEEBILL,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteDockFeeBill(DockFeeBillDto dockFeeBillDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			dockFeeBillDao.deleteFeeBill(dockFeeBillDto);
			DockFeeBillDto dDto=new DockFeeBillDto();
			dDto.setFeebillId(dockFeeBillDto.getId());
			dockFeeBillDao.cleanFeeStatus(dockFeeBillDto.getId());
			dockFeeChargeDao.cleanDockFeeCharge(dDto);
			AccountBillLogDto acDto=new AccountBillLogDto();
			acDto.setDockfeebillId(dockFeeBillDto.getId());
			accountBillLogDao.deleteAccountBillLog(acDto);
			
		}catch (RuntimeException e){
			LOG.error("service 删除账单失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 删除账单失败",e);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getCodeNum(DockFeeBillDto dockFeeBillDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getMap().put("codeNum", dockFeeBillDao.getCodeNum(dockFeeBillDto).get("codeNum").toString());
		}catch (RuntimeException e){
			LOG.error("service 获取结算单编号失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获取结算单编号失败",e);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getTotalFee(DockFeeBillDto dockFeeBillDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(dockFeeBillDao.getTotalFee(dockFeeBillDto));
		}catch (RuntimeException e){
			LOG.error("service 更新账单失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 更新账单失败",e);
		}
		return oaMsg;
	}

	@Override
	public OaMsg removeFeecharge(DockFeeBillDto ndfDto)throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			DockFeeBill dfbill=ndfDto.getDockfeebill();
			List<DockFeeCharge> feeChargeList=ndfDto.getFeechargeList();
			List<AccountBillLog> accountBillLogList=ndfDto.getAccountBillList();
			int dfbillId=0;
			if(dfbill.getId()==null||dfbill.getId()==0){
				dfbillId=dockFeeBillDao.addDockFeeBill(dfbill);
			}else{
				dfbillId=dfbill.getId();
				dockFeeBillDao.updateDockFeeBill(dfbill);
			}
			DockFeeBillDto dfdto=new DockFeeBillDto();
			dfdto.setId(dfbillId);
			Map<String, Object> data=dockFeeBillDao.getDockFeeBillMsg(dfdto);
			if(feeChargeList!=null&&feeChargeList.size()>0){
				DockFeeBillDto dfBillDto=new DockFeeBillDto();
				dfBillDto.setFeebillId(ndfDto.getId());
				for(DockFeeCharge dc:feeChargeList){
					dfBillDto.setId(dc.getId());
					dockFeeChargeDao.cleanDockFeeCharge(dfBillDto);
					dockFeeChargeDao.checkAndChangeFeeStatus(dfBillDto);
				}
				}
			
			
			if(!Common.empty(data.get("fundsStatus"))&&Integer.parseInt(data.get("fundsStatus").toString())==2){
				
				if(accountBillLogList!=null&&accountBillLogList.size()>0){
					for(AccountBillLog abL:accountBillLogList){
						abL.setDockfeebillId(dfbillId);
						if(abL.getId()==null||abL.getId()==0){
							accountBillLogDao.addAccountBillLog(abL);
						}else{
							accountBillLogDao.updateAccountBillLog(abL);
						}
					}
				}
			}
			oaMsg.getMap().put("id", dfbillId+"");
		}catch (RuntimeException e){
			LOG.error("service 移除账单费用项失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 移除账单费用项失败",e);
		}
		return oaMsg;
	}

	@Override
	public OaMsg makeFeeBill(DockFeeChargeDto  dfcDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			List<Map<String, Object>> feeChargeList=null;
			DockFeeBill itemFeeBill=null;
			DockFeeCharge itemFeeCharge=null;
			List<Map<String, Object>> feeHeadList=null;
			AccountBillLog aBillLog=null;
			Integer feeBillId=0;
			
			Integer mbillType=dfcDto.getBillType();
			
			//TODO 1.查询本段时间内的船代列表--增值税--停泊费
			if(Common.empty(mbillType)||mbillType==2||mbillType==0){
				dfcDto.setBillType(2);
				dfcDto.setFeeType(1);
				feeHeadList= dockFeeChargeDao.getFeeHeadList(dfcDto, 0, 0);
				//TODO 2.根据不同船代查询本段时间内的费用项组3.根据费用项组生成组装账单
				if(feeHeadList!=null&&feeHeadList.size()>0){
					for(int i=0,len=feeHeadList.size();i<len;i++){
						if(feeHeadList.get(i).get("clientName")!=null){
							dfcDto.setClientName(FormatUtils.getStringValue(feeHeadList.get(i).get("clientName")));
							feeChargeList=dockFeeChargeDao.getDockFeeChargeList(dfcDto,0,0);
							itemFeeBill=new DockFeeBill();
							itemFeeBill.setCode(dockFeeBillDao.getCodeNum(null).get("codeNum").toString());
							itemFeeBill.setCreateUserId(user.getId());
							itemFeeBill.setCreateTime(System.currentTimeMillis()/1000);
							itemFeeBill.setReviewUserId(user.getId());
							itemFeeBill.setReviewTime(System.currentTimeMillis()/1000);
							itemFeeBill.setStatus(2);
							itemFeeBill.setFeeHead(FormatUtils.getStringValue(feeHeadList.get(i).get("clientName")));
							itemFeeBill.setFeeType(1);
							itemFeeBill.setBillType(2);
							if(!Common.empty(dfcDto.getBillTime())&&dfcDto.getBillTime()!=-1){
								itemFeeBill.setAccountTime(dfcDto.getBillTime());
								
							}else{
								itemFeeBill.setAccountTime(System.currentTimeMillis()/1000);
								
							}
							itemFeeBill.setTotalFee(FormatUtils.getStringValue(feeHeadList.get(i).get("totalFee")));
							feeBillId=dockFeeBillDao.addDockFeeBill(itemFeeBill);
							for(int j=0,itemLen=feeChargeList.size();j<itemLen;j++){
								itemFeeCharge=new DockFeeCharge();
								itemFeeCharge.setId(Integer.valueOf(feeChargeList.get(j).get("id").toString()));
								itemFeeCharge.setFeebillId(feeBillId);
								dockFeeChargeDao.updateDockFeeCharge(itemFeeCharge);
							}
							dockFeeBillDao.updateDockFeeStatus(feeBillId);
						}	
					}
				}
			}
			//TODO 1.查询本段时间内的船代列表--增值税--淡水费
			if(Common.empty(mbillType)||mbillType==2||mbillType==0){
				
				dfcDto.setBillType(2);
				dfcDto.setFeeType(2);
				feeHeadList= dockFeeChargeDao.getFeeHeadList(dfcDto, 0, 0);
				//TODO 2.根据不同船代查询本段时间内的费用项组3.根据费用项组生成组装账单
				if(feeHeadList!=null&&feeHeadList.size()>0){
					for(int i=0,len=feeHeadList.size();i<len;i++){
						if(feeHeadList.get(i).get("clientName")!=null){
							dfcDto.setClientName(FormatUtils.getStringValue(feeHeadList.get(i).get("clientName")));
							feeChargeList=dockFeeChargeDao.getDockFeeChargeList(dfcDto,0,0);
							itemFeeBill=new DockFeeBill();
							itemFeeBill.setCode(dockFeeBillDao.getCodeNum(null).get("codeNum").toString());
							itemFeeBill.setCreateUserId(user.getId());
							itemFeeBill.setCreateTime(System.currentTimeMillis()/1000);
							itemFeeBill.setReviewUserId(user.getId());
							itemFeeBill.setReviewTime(System.currentTimeMillis()/1000);
							itemFeeBill.setStatus(2);
							itemFeeBill.setFeeHead(FormatUtils.getStringValue(feeHeadList.get(i).get("clientName")));
							itemFeeBill.setFeeType(2);
							itemFeeBill.setBillType(2);
							if(!Common.empty(dfcDto.getBillTime())&&dfcDto.getBillTime()!=-1){
								itemFeeBill.setAccountTime(dfcDto.getBillTime());
								
							}else{
								itemFeeBill.setAccountTime(System.currentTimeMillis()/1000);
								
							}
							itemFeeBill.setTotalFee(FormatUtils.getStringValue(feeHeadList.get(i).get("totalFee")));
							feeBillId=dockFeeBillDao.addDockFeeBill(itemFeeBill);
							for(int j=0,itemLen=feeChargeList.size();j<itemLen;j++){
								itemFeeCharge=new DockFeeCharge();
								itemFeeCharge.setId(Integer.valueOf(feeChargeList.get(j).get("id").toString()));
								itemFeeCharge.setFeebillId(feeBillId);
								dockFeeChargeDao.updateDockFeeCharge(itemFeeCharge);
							}
							dockFeeBillDao.updateDockFeeStatus(feeBillId);
						}	
					}
				}
			}
			 //手撕发票--靠泊费
			if(Common.empty(mbillType)||mbillType==1||mbillType==0){
				
				dfcDto.setFeeType(1);
				dfcDto.setBillType(1);
				feeHeadList= dockFeeChargeDao.getFeeHeadList(dfcDto, 0, 0);
				//TODO 2.根据不同船代查询本段时间内的费用项组3.根据费用项组生成组装账单
				if(feeHeadList!=null&&feeHeadList.size()>0){
					for(int i=0,len=feeHeadList.size();i<len;i++){
						if(feeHeadList.get(i).get("clientName")!=null){
							dfcDto.setClientName(FormatUtils.getStringValue(feeHeadList.get(i).get("clientName")));
							feeChargeList=dockFeeChargeDao.getDockFeeChargeList(dfcDto,0,0);
							itemFeeBill=new DockFeeBill();
							itemFeeBill.setCode(dockFeeBillDao.getCodeNum(null).get("codeNum").toString());
							itemFeeBill.setCreateUserId(user.getId());
							itemFeeBill.setCreateTime(System.currentTimeMillis()/1000);
							itemFeeBill.setReviewUserId(user.getId());
							itemFeeBill.setReviewTime(System.currentTimeMillis()/1000);
							itemFeeBill.setStatus(2);
							itemFeeBill.setFeeHead(FormatUtils.getStringValue(feeHeadList.get(i).get("clientName")));
							itemFeeBill.setFeeType(1);
							itemFeeBill.setBillType(1);
							if(!Common.empty(dfcDto.getBillTime())&&dfcDto.getBillTime()!=-1){
								itemFeeBill.setAccountTime(dfcDto.getBillTime());
								
							}else{
								itemFeeBill.setAccountTime(System.currentTimeMillis()/1000);
								
							}
							itemFeeBill.setTotalFee(FormatUtils.getStringValue(feeHeadList.get(i).get("totalFee")));
							itemFeeBill.setFundsStatus(2);
							feeBillId=dockFeeBillDao.addDockFeeBill(itemFeeBill);
							
							LOG.debug(FormatUtils.getStringValue(feeHeadList.get(i).get("totalFee")));
							aBillLog=new AccountBillLog();
							aBillLog.setAccountFee(FormatUtils.getStringValue(feeHeadList.get(i).get("totalFee")));
							aBillLog.setAccountTime(System.currentTimeMillis()/1000);
							aBillLog.setDockfeebillId(feeBillId);
							aBillLog.setAccountUserId(user.getId());
							accountBillLogDao.addAccountBillLog(aBillLog);
							for(int j=0,itemLen=feeChargeList.size();j<itemLen;j++){
								itemFeeCharge=new DockFeeCharge();
								itemFeeCharge.setId(Integer.valueOf(feeChargeList.get(j).get("id").toString()));
								itemFeeCharge.setFeebillId(feeBillId);
								dockFeeChargeDao.updateDockFeeCharge(itemFeeCharge);
							}
							dockFeeBillDao.updateDockFeeStatus(feeBillId);
						}	
					}
				}
			}
			//手撕发票--淡水费
			if(Common.empty(mbillType)||mbillType==1||mbillType==0){
				
				dfcDto.setFeeType(2);
				dfcDto.setBillType(1);
				feeHeadList= dockFeeChargeDao.getFeeHeadList(dfcDto, 0, 0);
				//TODO 2.根据不同船代查询本段时间内的费用项组3.根据费用项组生成组装账单
				if(feeHeadList!=null&&feeHeadList.size()>0){
					for(int i=0,len=feeHeadList.size();i<len;i++){
						if(feeHeadList.get(i).get("clientName")!=null){
							dfcDto.setClientName(FormatUtils.getStringValue(feeHeadList.get(i).get("clientName")));
							feeChargeList=dockFeeChargeDao.getDockFeeChargeList(dfcDto,0,0);
							itemFeeBill=new DockFeeBill();
							itemFeeBill.setCode(dockFeeBillDao.getCodeNum(null).get("codeNum").toString());
							itemFeeBill.setCreateUserId(user.getId());
							itemFeeBill.setCreateTime(System.currentTimeMillis()/1000);
							itemFeeBill.setReviewUserId(user.getId());
							itemFeeBill.setReviewTime(System.currentTimeMillis()/1000);
							itemFeeBill.setStatus(2);
							itemFeeBill.setFeeHead(FormatUtils.getStringValue(feeHeadList.get(i).get("clientName")));
							itemFeeBill.setFeeType(2);
							itemFeeBill.setBillType(1);
							itemFeeBill.setFundsStatus(2);
							if(!Common.empty(dfcDto.getBillTime())&&dfcDto.getBillTime()!=-1){
								itemFeeBill.setAccountTime(dfcDto.getBillTime());
								
							}else{
								itemFeeBill.setAccountTime(System.currentTimeMillis()/1000);
								
							}
							itemFeeBill.setTotalFee(FormatUtils.getStringValue(feeHeadList.get(i).get("totalFee")));
							feeBillId=dockFeeBillDao.addDockFeeBill(itemFeeBill);
							
							LOG.debug(FormatUtils.getStringValue(feeHeadList.get(i).get("totalFee")));
							aBillLog=new AccountBillLog();
							aBillLog.setAccountFee(FormatUtils.getStringValue(feeHeadList.get(i).get("totalFee")));
							aBillLog.setAccountTime(System.currentTimeMillis()/1000);
							aBillLog.setDockfeebillId(feeBillId);
							aBillLog.setAccountUserId(user.getId());
							accountBillLogDao.addAccountBillLog(aBillLog);
							for(int j=0,itemLen=feeChargeList.size();j<itemLen;j++){
								itemFeeCharge=new DockFeeCharge();
								itemFeeCharge.setId(Integer.valueOf(feeChargeList.get(j).get("id").toString()));
								itemFeeCharge.setFeebillId(feeBillId);
								dockFeeChargeDao.updateDockFeeCharge(itemFeeCharge);
							}
							dockFeeBillDao.updateDockFeeStatus(feeBillId);
						}	
					}
				}
			}
	}catch (RuntimeException e){
		LOG.error("service 一键生成失败",e);
		oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		throw new OAException(Constant.SYS_CODE_DB_ERR, "service 一键生成失败",e);
	}
	return oaMsg;
	}
 
	@Override
	public OaMsg reviewFeeBill(DockFeeChargeDto dfcDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			dfcDto.setTradeType(1);
			DockFeeBill itemFeeBill=null;
			List<Map<String, Object>> dataList=	dockFeeChargeDao.getFeeBillIdByTime(dfcDto);
			if(dataList!=null&&dataList.size()>0){
				for(int i=0,len=dataList.size();i<len;i++){
				itemFeeBill=new DockFeeBill();
				itemFeeBill.setId(Integer.valueOf(dataList.get(i).get("feebillId").toString()));
				itemFeeBill.setReviewUserId(user.getId());
				itemFeeBill.setReviewTime(System.currentTimeMillis()/1000);
				itemFeeBill.setStatus(2);
				dockFeeBillDao.updateDockFeeBill(itemFeeBill);
				}
			}
		}catch (RuntimeException e){
		LOG.error("service 一键审批失败",e);
		oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		throw new OAException(Constant.SYS_CODE_DB_ERR, "service 一键审批失败",e);
	}
	return oaMsg;
	}
	
	@Override
	public OaMsg rollBack(DockFeeBillDto dfBillDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			DockFeeBill feeBill=new DockFeeBill();
			feeBill.setId(dfBillDto.getId());
			feeBill.setStatus(dfBillDto.getStatus());
			if(dfBillDto.getStatus()==0){//未提交状态
				feeBill.setBillingCode("");
				feeBill.setBillingStatus(-1);
				feeBill.setBillingTime(-1L);
				feeBill.setUnTaxFee(0+"");
				feeBill.setTaxRate(0+"");
				feeBill.setTaxFee(0+"");
				feeBill.setReviewTime(-1L);
				feeBill.setReviewUserId(-1);
			}
			 dockFeeBillDao.updateDockFeeBill(feeBill);
			
	}catch (RuntimeException e){
		LOG.error("service回退失败",e);
		oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		throw new OAException(Constant.SYS_CODE_DB_ERR, "service回退失败",e);
	}
	return oaMsg;
	}
	
	@Override
	public HSSFWorkbook exportExcel(HttpServletRequest request,final DockFeeBillDto dockFeeBillDto) throws OAException {
			return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/dockfeebilllist.xls",new CallBack() {
				@Override
				public void setSheetValue(HSSFSheet sheet) {
					try {
						List<Map<String, Object>> data=dockFeeBillDao.getDockFeeBillExcelList(dockFeeBillDto);
						if(data!=null&&data.size()>0){
							Map<String, Object> map=null;
							List<Map<String,Integer>> region=new ArrayList<Map<String,Integer>>();
							Map<String, Integer> regMap=new HashMap<String, Integer>();
						    List<String> lsids =new ArrayList<String>();// 账单id
						    DecimalFormat df =new DecimalFormat("######0.00");
						    Integer itemRowNum=2;
						    HSSFRow itemRow=null;
							int  j=0;
							String id=null;
							for(int i=0,len=data.size();i<=len;i++){
								itemRow=sheet.createRow(itemRowNum);
								itemRow.setHeight(sheet.getRow(1).getHeight());
								for(j=0;j<21;j++)
									itemRow.createCell(j).setCellStyle(sheet.getRow(1).getCell(j).getCellStyle());
								if(i!=len){
								map=data.get(i);
								itemRow.getCell(4).setCellValue(FormatUtils.getStringValue(map.get("feeType")));
								itemRow.getCell(5).setCellValue(FormatUtils.getDoubleValue(map.get("unitFee")));
								itemRow.getCell(6).setCellValue(FormatUtils.getDoubleValue(map.get("feeCount")));
								itemRow.getCell(7).setCellValue(FormatUtils.getDoubleValue(map.get("totalFee")));
								itemRow.getCell(8).setCellValue(FormatUtils.getDoubleValue(map.get("discountFee")));
								itemRow.getCell(9).setCellValue(FormatUtils.getStringValue(map.get("shipName")));
								itemRow.getCell(10).setCellValue(FormatUtils.getStringValue(map.get("arrivalTime")));
								 id=map.get("id").toString();
						    	if(lsids.contains(id)){
						    		itemRow.getCell(0).setCellValue("");
									itemRow.getCell(1).setCellValue("");
									itemRow.getCell(2).setCellValue("");
									itemRow.getCell(3).setCellValue("");
									itemRow.getCell(11).setCellValue("");
									itemRow.getCell(12).setCellValue("");
									itemRow.getCell(13).setCellValue("");
									itemRow.getCell(14).setCellValue("");
									itemRow.getCell(15).setCellValue("");
									itemRow.getCell(16).setCellValue("");
									itemRow.getCell(17).setCellValue("");
									itemRow.getCell(18).setCellValue("");
									itemRow.getCell(19).setCellValue("");
									itemRow.getCell(20).setCellValue("");
						    	}else{
						    		if(regMap.get("start")==null)
						    			regMap.put("start", itemRowNum);
						    		if(itemRowNum-regMap.get("start")>2){
						    			regMap.put("end", itemRowNum-1);
						    			region.add(regMap);
						    			regMap=new HashMap<String,Integer>();
						    			regMap.put("start", itemRowNum);
						    		}
						    		lsids.add(id);
						    		itemRow.getCell(0).setCellValue(FormatUtils.getStringValue(map.get("code")));
									itemRow.getCell(1).setCellValue(FormatUtils.getStringValue(map.get("accountTime")));
									itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(map.get("feeHead")));
									itemRow.getCell(3).setCellValue(FormatUtils.getStringValue(map.get("tradeType")));
									
									itemRow.getCell(11).setCellValue(FormatUtils.getStringValue(map.get("fundsTime")));
									itemRow.getCell(12).setCellValue(FormatUtils.getStringValue(map.get("billingCode")));
									itemRow.getCell(13).setCellValue(FormatUtils.getStringValue(map.get("billingTime")));
									itemRow.getCell(14).setCellValue(FormatUtils.getDoubleValue(map.get("allTotalFee")));
									itemRow.getCell(15).setCellValue(FormatUtils.getDoubleValue(map.get("accountFee")));
									itemRow.getCell(16).setCellValue(Common.sub(map.get("allTotalFee"),map.get("accountFee"), 2));
									itemRow.getCell(17).setCellValue(FormatUtils.getDoubleValue(map.get("unTaxFee")));
									itemRow.getCell(18).setCellValue(FormatUtils.getDoubleValue(map.get("taxRate")));
									itemRow.getCell(19).setCellValue(FormatUtils.getDoubleValue(map.get("taxFee")));
									itemRow.getCell(20).setCellValue(FormatUtils.getStringValue(map.get("description")));
						    		}
						    	}else{
						    		regMap=new HashMap<String, Integer>();
						    		if(Common.sub((itemRowNum-1), regMap.get("start"), 0)>1){
						    			regMap.put("end", itemRowNum-1);
						    			region.add(regMap);
						    		}
						    		itemRow.getCell(7).setCellFormula("SUM(H3:H"+itemRowNum+")");
						    		itemRow.getCell(8).setCellFormula("SUM(I3:I"+itemRowNum+")");
						    		itemRow.getCell(14).setCellFormula("SUM(O3:O"+itemRowNum+")");
						    		itemRow.getCell(15).setCellFormula("SUM(P3:P"+itemRowNum+")");
						    		itemRow.getCell(16).setCellFormula("SUM(Q3:Q"+itemRowNum+")");
						    		itemRow.getCell(17).setCellFormula("SUM(R3:R"+itemRowNum+")");
						    		itemRow.getCell(19).setCellFormula("SUM(T3:T"+itemRowNum+")");
						    	}
						    	itemRowNum++;
							}
							
							if(region!=null){
								for(j=0;j<region.size();j++){
									sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)0, region.get(j).get("end"), (short)0));
									sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)1, region.get(j).get("end"), (short)1));
									sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)2, region.get(j).get("end"), (short)2));
									sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)3, region.get(j).get("end"), (short)3));
									sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)11, region.get(j).get("end"), (short)11)); 
									sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)12, region.get(j).get("end"), (short)12)); 
									sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)13, region.get(j).get("end"), (short)13)); 
									sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)14, region.get(j).get("end"), (short)14));    
									sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)15, region.get(j).get("end"), (short)15));    
									sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)16, region.get(j).get("end"), (short)16));    
									sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)17, region.get(j).get("end"), (short)17));    
									sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)18, region.get(j).get("end"), (short)18));    
									sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)19, region.get(j).get("end"), (short)19)); 
									sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)20, region.get(j).get("end"), (short)20)); 
								}}
							
						}
					} catch (OAException e) {
						e.printStackTrace();
					}
				}
				
			}).getWorkbook();
	}

	
	@Override
	public HSSFWorkbook exportExcel1(HttpServletRequest request,final DockFeeBillDto dockFeeBillDto) throws OAException {
			return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/dockfeebilllist1.xls",new CallBack() {
				@Override
				public void setSheetValue(HSSFSheet sheet) {
					try {
						List<Map<String, Object>> data=dockFeeBillDao.getDockFeeBillExcelList1(dockFeeBillDto);
						if(data!=null&&data.size()>0){
							Map<String, Object> map=null;
							List<Map<String,Integer>> region=new ArrayList<Map<String,Integer>>();
							Map<String, Integer> regMap=new HashMap<String, Integer>();
						    List<String> lsids =new ArrayList<String>();// 账单id
						    DecimalFormat df =new DecimalFormat("######0.00");
						    Integer itemRowNum=2;
						    HSSFRow itemRow=null;
							int  j=0;
							String id=null;
							for(int i=0,len=data.size();i<=len;i++){
								itemRow=sheet.createRow(itemRowNum);
								itemRow.setHeight(sheet.getRow(1).getHeight());
								for(j=0;j<14;j++)
									itemRow.createCell(j).setCellStyle(sheet.getRow(1).getCell(j).getCellStyle());
								if(i!=len){
								map=data.get(i);
						    		itemRow.getCell(0).setCellValue(FormatUtils.getStringValue(map.get("code")));
									itemRow.getCell(1).setCellValue(FormatUtils.getStringValue(map.get("accountTime")));
									itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(map.get("feeHead")));
									itemRow.getCell(3).setCellValue(FormatUtils.getStringValue(map.get("tradeType")));
									
									itemRow.getCell(4).setCellValue(FormatUtils.getStringValue(map.get("fundsTime")));
									itemRow.getCell(5).setCellValue(FormatUtils.getStringValue(map.get("billingCode")));
									itemRow.getCell(6).setCellValue(FormatUtils.getStringValue(map.get("billingTime")));
									itemRow.getCell(7).setCellValue(FormatUtils.getDoubleValue(map.get("allTotalFee")));
									itemRow.getCell(8).setCellValue(FormatUtils.getDoubleValue(map.get("accountFee")));
									itemRow.getCell(9).setCellValue(Common.sub(map.get("allTotalFee"),map.get("accountFee"), 2));
									itemRow.getCell(10).setCellValue(FormatUtils.getDoubleValue(map.get("unTaxFee")));
									itemRow.getCell(11).setCellValue(FormatUtils.getDoubleValue(map.get("taxRate")));
									itemRow.getCell(12).setCellValue(FormatUtils.getDoubleValue(map.get("taxFee")));
									itemRow.getCell(13).setCellValue(FormatUtils.getStringValue(map.get("description")));
						    	}else{
						    		regMap=new HashMap<String, Integer>();
						    		if(Common.sub((itemRowNum-1), regMap.get("start"), 0)>1){
						    			regMap.put("end", itemRowNum-1);
						    			region.add(regMap);
						    		}
						    		itemRow.getCell(7).setCellFormula("SUM(H3:H"+itemRowNum+")");
						    		itemRow.getCell(8).setCellFormula("SUM(I3:I"+itemRowNum+")");
						    		itemRow.getCell(9).setCellFormula("SUM(J3:J"+itemRowNum+")");
						    		itemRow.getCell(10).setCellFormula("SUM(K3:K"+itemRowNum+")");
						    		itemRow.getCell(12).setCellFormula("SUM(M3:M"+itemRowNum+")");
						    	}
						    	itemRowNum++;
							}
							
							if(region!=null){
								for(j=0;j<region.size();j++){
									sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)0, region.get(j).get("end"), (short)0));
									sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)1, region.get(j).get("end"), (short)1));
									sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)2, region.get(j).get("end"), (short)2));
									sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)3, region.get(j).get("end"), (short)3));
									sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)4, region.get(j).get("end"), (short)4));
									sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)5, region.get(j).get("end"), (short)5));
									sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)6, region.get(j).get("end"), (short)6));
									sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)7, region.get(j).get("end"), (short)7));
									sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)8, region.get(j).get("end"), (short)8));
									sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)9, region.get(j).get("end"), (short)9));
									sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)10, region.get(j).get("end"), (short)10));
									sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)11, region.get(j).get("end"), (short)11)); 
									sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)12, region.get(j).get("end"), (short)12)); 
									sheet.addMergedRegion(new Region(region.get(j).get("start"),(short)13, region.get(j).get("end"), (short)13)); 
								}}
							
						}
					} catch (OAException e) {
						e.printStackTrace();
					}
				}
				
			}).getWorkbook();
	}
	
	
	@Override
	public HSSFWorkbook exportDetailFee(HttpServletRequest request, final int id)throws OAException {
		return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/dockfeebillmsg.xls",new CallBack() {
			@Override
			public void setSheetValue(HSSFSheet sheet) {
				try {
					List<Map<String, Object>> data=dockFeeBillDao.getdockFeeMsgByFeeBillId(id);
					DockFeeDto dFeeDto=new DockFeeDto();
					DockFeeChargeDto dfcDto=new DockFeeChargeDto();
					Map<String,Object> itemDockFee=new HashMap<String,Object>();
					List<Map<String, Object>> dockFeeChargeList=new ArrayList<Map<String,Object>>();
					Map<String, Object> itemFeeCharge=null;
					HSSFRow itemRow=null;
					HSSFCellStyle[] hCStyle={sheet.getRow(7).getCell(0).getCellStyle(),sheet.getRow(7).getCell(1).getCellStyle(),
							sheet.getRow(7).getCell(2).getCellStyle(),sheet.getRow(7).getCell(3).getCellStyle(),
							sheet.getRow(7).getCell(4).getCellStyle(),sheet.getRow(7).getCell(5).getCellStyle()};
					int itemNum=0;
					if(data!=null&&data.size()>0){
						for(int i=0,len=data.size();i<len;i++){
							if(i!=0){
								for(int j=0;j<8;j++){
									itemRow=sheet.createRow(i*8+j+itemNum);
									itemRow.setHeight(sheet.getRow(j).getHeight());
									for(int m=0;m<6;m++)
										itemRow.createCell(m).setCellStyle(sheet.getRow(j).getCell(m).getCellStyle());
									
									if(j==0){
									 itemRow.getCell(0).setCellValue(sheet.getRow(j).getCell(0).getStringCellValue());
									 sheet.addMergedRegion(new Region(i*8+j+itemNum, (short) 0, i*8+j+itemNum, (short) 5));
									}else if(j==1){
										 itemRow.getCell(0).setCellValue(sheet.getRow(j).getCell(0).getStringCellValue());
										 itemRow.getCell(3).setCellValue(sheet.getRow(j).getCell(3).getStringCellValue());
										 sheet.addMergedRegion(new Region(i*8+j+itemNum, (short) 1,i*8+j+itemNum, (short) 2));
										 sheet.addMergedRegion(new Region(i*8+j+itemNum, (short) 4,i*8+j+itemNum, (short) 5));
									}else if(j>1&&j<5){
										 itemRow.getCell(0).setCellValue(sheet.getRow(j).getCell(0).getStringCellValue());
										 itemRow.getCell(2).setCellValue(sheet.getRow(j).getCell(2).getStringCellValue());
										 itemRow.getCell(4).setCellValue(sheet.getRow(j).getCell(4).getStringCellValue());
									}else if(j==5||j==6){
										if(j==5){
										 itemRow.getCell(0).setCellValue("项目");
										 itemRow.getCell(1).setCellValue("公式");
										 itemRow.getCell(2).setCellValue("");
										 itemRow.getCell(3).setCellValue("");
										 itemRow.getCell(4).setCellValue("金额");
										 itemRow.getCell(5).setCellValue("实收金额");
										}
										 sheet.addMergedRegion(new Region(i*8+j+itemNum, (short) 1, i*8+j+itemNum, (short) 3));
									}else if(j==7){
										itemRow.getCell(0).setCellValue("制定人");
										itemRow.getCell(2).setCellValue("审核人");
									}
									
								}
							}
							dFeeDto.setId(Integer.valueOf(data.get(i).get("dockFeeId").toString()));
							itemDockFee=(Map<String, Object>) dockFeeDao.getDockFeeMsg(dFeeDto);
							
							sheet.getRow(i*8+1+itemNum).getCell(1).setCellValue(FormatUtils.getStringValue(itemDockFee.get("code")));
							sheet.getRow(i*8+1+itemNum).getCell(4).setCellValue(FormatUtils.getStringValue(itemDockFee.get("accountTimeStr")).substring(0, 10));
							
							sheet.getRow(i*8+2+itemNum).getCell(1).setCellValue(FormatUtils.getStringValue(itemDockFee.get("clientName")));
							sheet.getRow(i*8+2+itemNum).getCell(3).setCellValue(FormatUtils.getStringValue(itemDockFee.get("contractName")));
							sheet.getRow(i*8+2+itemNum).getCell(5).setCellValue(FormatUtils.getStringValue(itemDockFee.get("productName")));
							
							sheet.getRow(i*8+3+itemNum).getCell(1).setCellValue(FormatUtils.getStringValue(itemDockFee.get("shipName"))+"/"+FormatUtils.getStringValue(itemDockFee.get("refName")));
							sheet.getRow(i*8+3+itemNum).getCell(3).setCellValue(FormatUtils.getStringValue(itemDockFee.get("arrivalTime")));
							sheet.getRow(i*8+3+itemNum).getCell(5).setCellValue( FormatUtils.getStringValue(itemDockFee.get("billCode")));
							
							if(itemDockFee.get("tradeType")!=null)
							sheet.getRow(i*8+4+itemNum).getCell(1).setCellValue((Integer.valueOf(itemDockFee.get("tradeType").toString())==1?"外贸账单":"内贸账单"));
							if(itemDockFee.get("accountType")!=null)
							sheet.getRow(i*8+4+itemNum).getCell(3).setCellValue((Integer.valueOf(itemDockFee.get("accountType").toString())==1?"现结":"月结"));
							if(itemDockFee.get("billType")!=null)
							sheet.getRow(i*8+4+itemNum).getCell(5).setCellValue((Integer.valueOf(itemDockFee.get("billType").toString())==1?"手撕发票":"增值税发票"));
							
							sheet.getRow(i*8+7+itemNum).getCell(1).setCellValue( FormatUtils.getStringValue(itemDockFee.get("createUserName")));
							sheet.getRow(i*8+7+itemNum).getCell(3).setCellValue( FormatUtils.getStringValue(data.get(i).get("reviewUserName")));
							
							dfcDto.setDockFeeId(dFeeDto.getId());
							dfcDto.setFeebillId(id);
							dockFeeChargeList=dockFeeChargeDao.getDockFeeChargeList(dfcDto, 0, 0);
							for(int n=0;n<dockFeeChargeList.size();n++){
								 itemFeeCharge=dockFeeChargeList.get(n);
								if(n!=0){
									sheet.shiftRows(i*8+7+itemNum, i*8+7+itemNum, 1,true,false);
									itemNum++;
									sheet.createRow(i*8+6+itemNum).setHeight(sheet.getRow(0).getHeight());
									for(int m=0;m<6;m++)
										sheet.getRow(i*8+6+itemNum).createCell(m).setCellStyle(sheet.getRow(6).getCell(m).getCellStyle());
									sheet.addMergedRegion(new Region(i*8+6+itemNum, (short) 1, i*8+6+itemNum, (short) 3));
								}
									if(Integer.valueOf(itemFeeCharge.get("feeType").toString())==1){//靠泊费
										sheet.getRow(i*8+6+itemNum).getCell(0).setCellValue("靠泊费");
										if(Integer.valueOf(itemDockFee.get("tradeType").toString())==1){
											sheet.getRow(i*8+6+itemNum).getCell(1).setCellValue("净吨（吨）（"+FormatUtils.getDoubleValue(itemDockFee.get("netTons"))
													+"） ×单价（0.25） ×在港天数（"+(itemDockFee.get("stayDay")!=null?Integer.valueOf(itemDockFee.get("stayDay").toString()):0)+"）");
										}else{
											sheet.getRow(i*8+6+itemNum).getCell(1).setCellValue("净吨（吨）（"+FormatUtils.getDoubleValue(itemDockFee.get("netTons"))
													+"） ×单价（0.08） ×在港天数（"+(itemDockFee.get("stayDay")!=null?Integer.valueOf(itemDockFee.get("stayDay").toString()):0)+"）");
										}
									}else if(Integer.valueOf(itemFeeCharge.get("feeType").toString())==2){
										sheet.getRow(i*8+6+itemNum).getCell(0).setCellValue("淡水费");
										sheet.getRow(i*8+6+itemNum).getCell(1).setCellValue("单价（１６.00）×数量"+FormatUtils.getDoubleValue(itemDockFee.get("waterWeigh").toString())+"吨");
									}
										sheet.getRow(i*8+6+itemNum).getCell(4).setCellValue(FormatUtils.getDoubleValue(itemFeeCharge.get("totalFee")));
										sheet.getRow(i*8+6+itemNum).getCell(5).setCellValue(FormatUtils.getDoubleValue(itemFeeCharge.get("discountFee")));
							}
						}
						itemNum+=data.size()*8;
						itemRow=sheet.createRow(itemNum);
						itemRow.setHeight(sheet.getRow(0).getHeight());
						for(int i=0;i<6;i++)
							itemRow.createCell(i).setCellStyle(sheet.getRow(0).getCell(i).getCellStyle());
						sheet.addMergedRegion(new Region(itemNum, (short) 0, itemNum, (short) 5));
						itemRow.getCell(0).setCellValue("费用项统计");
						
						itemRow = sheet.createRow(++itemNum);
						itemRow.setHeight(sheet.getRow(0).getHeight()); 
						String[]  feeChargeTitle={"费用类型","","合计金额（元）","","扣损后合计金额（元）",""};
						for(int i=0;i<6;i++){
							itemRow.createCell(i).setCellStyle(sheet.getRow(5).getCell(i).getCellStyle());
							itemRow.getCell(i).setCellValue(feeChargeTitle[i]);
						}
						sheet.addMergedRegion(new Region(itemNum, (short) 0, itemNum, (short) 1));
						sheet.addMergedRegion(new Region(itemNum, (short) 2, itemNum, (short) 3));
						sheet.addMergedRegion(new Region(itemNum, (short) 4, itemNum, (short) 5));
						List<Map<String, Object>> groupFeeChargeData=dockFeeBillDao.getGroupFeeChargeList(id);
						if(groupFeeChargeData!=null&&groupFeeChargeData.size()>0){
						for(int i=0,len=groupFeeChargeData.size();i<len;i++){
							itemRow = sheet.createRow(++itemNum);
							itemRow.setHeight(sheet.getRow(0).getHeight()); 
							itemRow.createCell(0).setCellValue(FormatUtils.getStringValue(groupFeeChargeData.get(i).get("feeType")));
							itemRow.createCell(1).setCellValue("");
							itemRow.createCell(2).setCellValue(FormatUtils.getDoubleValue(groupFeeChargeData.get(i).get("totalFee")));
							itemRow.createCell(3).setCellValue("");
							itemRow.createCell(4).setCellValue(FormatUtils.getDoubleValue(groupFeeChargeData.get(i).get("discountFee")));
							itemRow.createCell(5).setCellValue("");
							if(i!=len-1){
								for(int j=0;j<6;j++)
								itemRow.getCell(j).setCellStyle(sheet.getRow(3).getCell(j).getCellStyle());
							}else{
								for(int j=0;j<6;j++)
								itemRow.getCell(j).setCellStyle(hCStyle[j]);
							}
							sheet.addMergedRegion(new Region(itemNum, (short) 0, itemNum, (short) 1));
							sheet.addMergedRegion(new Region(itemNum, (short) 2, itemNum, (short) 3));
							sheet.addMergedRegion(new Region(itemNum, (short) 4, itemNum, (short) 5));
						}
						}
						
					}
					
				} catch (OAException e) {
					e.printStackTrace();
				}
			}
		}).getWorkbook();
	}



}
