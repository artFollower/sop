/**
 * 
 */
package com.skycloud.oa.feebill.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dto.AccountBillLogDto;
import com.skycloud.oa.feebill.model.AccountBillLog;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 *到账历史记录
 * @author jiahy
 * @version 2015年6月17日 下午10:25:52
 */
public interface AccountBillLogService {

	/**
	 *@author jiahy
	 * @param accountBillLogDto
	 * @param pageView
	 * @return
	 */
	OaMsg getAccountBillLogList(AccountBillLogDto accountBillLogDto,
			PageView pageView)throws OAException;

	/**
	 *@author jiahy
	 * @param accountBillLog
	 * @return
	 */
	OaMsg addAccountBillLog(AccountBillLog accountBillLog)throws OAException;

	/**
	 *@author jiahy
	 * @param accountBillLog
	 * @return
	 */
	OaMsg updateAccountBillLog(AccountBillLog accountBillLog)throws OAException;

	/**
	 *@author jiahy
	 * @param accountBillLogDto
	 * @return
	 */
	OaMsg deleteAccountBillLog(AccountBillLogDto accountBillLogDto)throws OAException;

}
