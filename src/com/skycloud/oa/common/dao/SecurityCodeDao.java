package com.skycloud.oa.common.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.common.model.SecurityCode;
import com.skycloud.oa.exception.OAException;

/**
 * 验证码操作持久化类
 * @ClassName: SecurityCodeDao 
 * @Description: TODO
 * @author xie
 * @date 2016年1月14日 下午5:55:47
 */
public interface SecurityCodeDao {

	/**
	 * 保存验证码
	 * @Description: TODO
	 * @author xie
	 * @date 2016年1月14日 下午5:56:19  
	 * @param user
	 * @return
	 */
	boolean create(SecurityCode code);
	
	/**
	 * 查询验证码
	 * @Description: TODO
	 * @author xie
	 * @date 2016年1月14日 下午5:57:21  
	 * @param code
	 * @return
	 */
	List<Map<String, Object>> get(SecurityCode code);
	
	/**
	 * 删除验证码
	 * @Description: TODO
	 * @author xie
	 * @date 2016年4月5日 下午5:49:20  
	 * @param code
	 * @return
	 */
	boolean delete(SecurityCode code);
	
	/**
	 * 删除使用过的验证码
	 * @param code
	 * @return
	 */
	boolean deleteUse(SecurityCode code);
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	List<Map<String, Object>> getPermissionOpenIds(String key,String userId) throws OAException;
	

	
}
