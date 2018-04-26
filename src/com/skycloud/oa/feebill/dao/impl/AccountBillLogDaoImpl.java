/**
 * 
 */
package com.skycloud.oa.feebill.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dao.AccountBillLogDao;
import com.skycloud.oa.feebill.dto.AccountBillLogDto;
import com.skycloud.oa.feebill.model.AccountBillLog;

/**
 *到账历史记录
 * @author jiahy
 * @version 2015年6月17日 下午10:04:56
 */
@Component
public class AccountBillLogDaoImpl extends BaseDaoImpl implements AccountBillLogDao {
	
	private static Logger LOG = Logger.getLogger(AccountBillLogDaoImpl.class);

	@Override
	public List<Map<String, Object>> getAccountBillLogList(AccountBillLogDto accountBillLogDto, int start, int limit)
			throws OAException {
		try {
			String sql="select a.*,b.name accountUserName,from_unixtime(a.accountTime) accountTimeStr from t_pcs_account_bill_log a "
					+ "inner join t_auth_user b on a.accountUserId=b.id  where 1=1";
			    if(accountBillLogDto.getFeebillId()!=null){
			    	sql+=" and a.feebillId="+accountBillLogDto.getFeebillId();
			    }
			    if(accountBillLogDto.getDockfeebillId()!=null){
			    	sql+=" and a.dockfeebillId="+accountBillLogDto.getDockfeebillId();
			    }
			   sql+=" order by accountTime asc limit "+start+","+limit;
			
			return executeQuery(sql) ;
		}catch (RuntimeException e) {
			LOG.error("dao获取到账历史记录列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取到账历史记录列表失败", e);
		}
	}
    
	@Override
	public int getAccountBillLogListCount(AccountBillLogDto accountBillLogDto)throws OAException {
		try {
			String sql="select count(*) from t_pcs_account_bill_log a where 1=1";
			 if(accountBillLogDto.getFeebillId()!=null){
			    	sql+=" and a.feebillId="+accountBillLogDto.getFeebillId();
			    }
			 if(accountBillLogDto.getDockfeebillId()!=null){
			    	sql+=" and a.dockfeebillId="+accountBillLogDto.getDockfeebillId();
			    }
			return (int) getCount(sql);
		}catch (RuntimeException e) {
			LOG.error("dao获取到账历史记录列表数量失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取到账历史记录列表数量失败", e);
		}
	}

	
	@Override
	public int addAccountBillLog(AccountBillLog accountBillLog)
			throws OAException {
		try {
			return (Integer)save(accountBillLog);
		}catch (RuntimeException e) {
			LOG.error("dao添加到账历史记录失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加到账历史记录失败", e);
		}
	}

	@Override
	public void updateAccountBillLog(AccountBillLog accountBillLog)
			throws OAException {
		try {
			String sql="update t_pcs_account_bill_log set id=id ";
			
			if(accountBillLog.getFeebillId()!=null){
				sql+=",feebillId="+accountBillLog.getFeebillId();
			}
			if(accountBillLog.getDockfeebillId()!=null){
				sql+=",dockfeebillId="+accountBillLog.getDockfeebillId();
			}
			if(accountBillLog.getAccountUserId()!=null){
				sql+=",accountUserId="+accountBillLog.getAccountUserId();
			}
			if(accountBillLog.getAccountTime()!=null){
				sql+=",accountTime="+accountBillLog.getAccountTime();
			}
			if(accountBillLog.getAccountFee()!=null){
				sql+=",accountFee='"+accountBillLog.getAccountFee()+"'";
			}
			sql+=" where  id="+accountBillLog.getId();
			executeUpdate(sql);
		}catch (RuntimeException e) {
			LOG.error("dao更新到账历史记录失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新到账历史记录失败", e);
		}
	}

	@Override
	public void deleteAccountBillLog(AccountBillLogDto accountBillLogDto)
			throws OAException {
		try {
			String sql="delete from t_pcs_account_bill_log where 1=1 ";
			if(accountBillLogDto.getId()!=null){
				sql+=" and id="+accountBillLogDto.getId();
			}
			if(accountBillLogDto.getFeebillId()!=null){
				sql+=" and feebillId="+accountBillLogDto.getFeebillId();
			}
			 if(accountBillLogDto.getDockfeebillId()!=null){
			    	sql+=" and  dockfeebillId="+accountBillLogDto.getDockfeebillId();
			    }
			 
			 String sql1="update t_pcs_dockfee_bill a set a.fundsStatus=0 where (select count(1) from t_pcs_account_bill_log where dockfeebillId=a.id)=0 ";
//			 String sql2="update t_pcs_dockfee_bill a set a.fundsStatus=1 where (select sum(accountFee) from t_pcs_account_bill_log where dockfeebillId=a.id)<a.totalFee and (select sum(accountFee) from t_pcs_account_bill_log where dockfeebillId=a.id)<>0";
			 
			 System.out.println(sql);
			execute(sql);
			System.out.println(sql1);
			executeUpdate(sql1);
//			System.out.println(sql2);
//			executeUpdate(sql2);
			
		}catch (RuntimeException e) {
			LOG.error("dao删除到账历史记录失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao删除到账历史记录失败", e);
		}
	}

}
