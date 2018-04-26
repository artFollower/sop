/**
 * 
 */
package com.skycloud.oa.feebill.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dto.AccountBillLogDto;
import com.skycloud.oa.feebill.model.AccountBillLog;

/**
 *
 * @author jiahy
 * @version 2015年6月17日 下午10:01:06
 */
public interface AccountBillLogDao {

	public List<Map<String,Object>> getAccountBillLogList(AccountBillLogDto accountBillLogDto,int start,int limit) throws OAException;
	
	public int getAccountBillLogListCount(AccountBillLogDto accountBillLogDto)throws OAException;
	
	public int addAccountBillLog(AccountBillLog accountBillLog) throws OAException;
	
	public void updateAccountBillLog(AccountBillLog accountBillLog) throws OAException;
	
	public void deleteAccountBillLog(AccountBillLogDto accountBillLogDto) throws OAException;
}
