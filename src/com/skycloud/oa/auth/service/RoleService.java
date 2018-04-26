/**
 * 
 */
package com.skycloud.oa.auth.service;

import com.skycloud.oa.auth.model.Role;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;



/**
 * 角色管理业务逻辑处理接口类
 * @ClassName: RoleService 
 * @Description: TODO
 * @author xie
 * @date 2014年12月29日 上午11:24:33
 */
public interface RoleService {
	
	/**
	 * 添加角色
	 * @Description: TODO
	 * @author xie
	 * @param role
	 * @param msg
	 */
	void create(Role role,OaMsg msg);
	
	/**
	 * 查询角色
	 * @Description: TODO
	 * @author xie
	 * @param role
	 * @param pageView
	 * @param msg
	 */
	void get(Role role,PageView pageView,OaMsg msg);
	
	/**
	 * 查看角色下所有的用户
	 * @param role
	 * @param pageView
	 * @param msg
	 */
	void getUser(Role role,PageView pageView,OaMsg msg);
	
	/**
	 * 更新角色信息
	 * @Description: TODO
	 * @author xie
	 * @param role
	 * @param msg
	 */
	void update(Role role,OaMsg msg);
	
	/**
	 * 删除角色
	 * @Description: TODO
	 * @author xie
	 * @param ids
	 * @param msg
	 */
	void delete(String ids,OaMsg msg);
	
	
	/**
	 * 检查
	 * @Description: TODO
	 * @author xie
	 * @param role
	 * @param msg
	 */
	void check(Role role,OaMsg msg);
	
	/**
	 * 获取角色已分配的权限
	 * @Description: TODO
	 * @author xie
	 * @param role
	 * @param msg
	 */
	void getGrantPermission(Role role,OaMsg msg);
	
	/**
	 * 获取未分配给角色的权限
	 * @Description: TODO
	 * @author xie
	 * @param role
	 * @param msg
	 */
	void getUnGrantPermission(Role role,OaMsg msg);
	
	/**
	 * 授权
	 * @Description: TODO
	 * @author xie
	 * @param roleId
	 * @param permissionIds
	 * @param msg
	 */
	void grantPermission(String roleId, String permissionIds,OaMsg msg);
	
	/**
	 * 回收权限
	 * @Description: TODO
	 * @author xie
	 * @param ids
	 * @param msg
	 */
	void recoverPermission(String ids,OaMsg msg);
}
