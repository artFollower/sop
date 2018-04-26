package com.skycloud.oa.auth.service;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.auth.model.Parties;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 * 组织架构业务逻辑处理化类
 * @ClassName: OrganizationDao 
 * @Description: TODO
 * @author xie
 * @date 2015年1月16日 上午10:20:44
 */
public interface OrganizationService  {
	
	/**
	 * 添加组织架构
	 * @Description: TODO
	 * @param parties
	 * @param msg
	 * @return
	 */
	void create(Parties parties,OaMsg msg);
	
	/**
	 *  查询组织架构
	 * @Description: TODO
	 * @param parties
	 * @param pageView
	 * @param msg
	 * @return
	 */
	void get(Parties parties,PageView pageView,OaMsg msg);
	
	/**
	 * 获取组织架构
	 * @Description: TODO
	 * @param parties
	 * @return
	 */
	List<Map<String, Object>> getOrganization(Parties parties);
	
		
	/**
	 * 修改信息
	 * @Description: TODO
	 * @param parties
	 * @return
	 * @throws OAException
	 */
	void modify(Parties parties,OaMsg msg);
	
	/**
	 * 更新员工信息
	 * @Description: TODO
	 * @author xie
	 * @date 2015年4月8日 下午7:13:56  
	 * @param parties
	 * @param msg
	 */
	void modifyeMPLOYEE(Parties parties,OaMsg msg);
	
	/**
	 * 删除
	 * @Description: TODO
	 * @param ids
	 * @param msg
	 */
	void delete(String ids,OaMsg msg);
	
	/**
	 * 获取关联的用户
	 * @Description: TODO
	 * @param parties
	 * @param msg
	 * @return
	 */
	void getRelationUser(Parties parties,PageView pageView,OaMsg msg);
	
	/**
	 * 获取未关联的用户
	 * @Description: TODO
	 * @param parties
	 * @param msg
	 * @return
	 */
	void getUnRelationUser(Parties parties,PageView pageView,OaMsg msg);
	
	/**
	 * 添加用户到部门
	 * @Description: TODO
	 * @param userIds
	 * @param departmentId
	 * @param msg
	 */
	void relationUser(String userIds,int departmentId,OaMsg msg);
}
