/**
 * 
 */
package com.skycloud.oa.feebill.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dao.AccountBillLogDao;
import com.skycloud.oa.feebill.dto.AccountBillLogDto;
import com.skycloud.oa.feebill.model.AccountBillLog;
import com.skycloud.oa.feebill.service.AccountBillLogService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 *
 * @author jiahy
 * @version 2015年6月17日 下午10:27:39
 */
@Service
public class AccountBillLogServiceImpl implements AccountBillLogService {
	private static Logger LOG = Logger.getLogger(AccountBillLogServiceImpl.class);
@Autowired
private AccountBillLogDao accountBillLogDao;
	
	@Override
	public OaMsg getAccountBillLogList(AccountBillLogDto accountBillLogDto,
			PageView pageView) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(accountBillLogDao.getAccountBillLogList(accountBillLogDto, pageView.getStartRecord(),pageView.getMaxresult()));
			oaMsg.getMap().put(Constant.TOTALRECORD,accountBillLogDao.getAccountBillLogListCount(accountBillLogDto)+"");
		}catch (RuntimeException e){
			LOG.error("service 获取到账历史记录失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获取到账历史记录失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_ACCOUNTBILLLOG,type=C.LOG_TYPE.CREATE)
	public OaMsg addAccountBillLog(AccountBillLog accountBillLog)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			accountBillLogDao.addAccountBillLog(accountBillLog);
		}catch (RuntimeException e){
			LOG.error("service 添加到账历史记录失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 添加到账历史记录失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_ACCOUNTBILLLOG,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateAccountBillLog(AccountBillLog accountBillLog)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			accountBillLogDao.updateAccountBillLog(accountBillLog);	
		}catch (RuntimeException e){
			LOG.error("service 更新到账历史记录失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 更新到账历史记录失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_ACCOUNTBILLLOG,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteAccountBillLog(AccountBillLogDto accountBillLogDto)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			accountBillLogDao.deleteAccountBillLog(accountBillLogDto);
		}catch (RuntimeException e){
			LOG.error("service 删除到账历史记录失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 删除到账历史记录失败",e);
		}
		return oaMsg;
	}

}
