/**
 * 
 */
package com.skycloud.oa.auth.service;

import java.util.Set;

import com.skycloud.oa.auth.dto.UserDto;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;



/**
 * 用户管理业务逻辑处理接口类
 * @ClassName: UserService 
 * @Description: TODO
 * @author xie
 * @date 2014年12月26日 上午10:38:01
 */
public interface UserService {
	
	/**
	 * 添加用户
	 * @Description: TODO
	 * @author xie
	 * @param user 用户实体
	 * @return
	 * @throws OAException
	 */
	void create(User user,OaMsg msg);
	
	/**
	 * 查询用户
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 */
	void get(UserDto user,PageView pageView,OaMsg msg);
	
	/**
	 * 登录查询
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @return
	 * @throws OAException
	 */
	void login(User user,String rememberMe,OaMsg msg);
	
	/**
	 * 
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @return
	 * @throws OAException
	 */
	void update(User user,OaMsg msg);
	
	/**
	 * 
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @return
	 * @throws OAException
	 */
	void delete(String ids,OaMsg msg);
	
	/**
	 * 重置密码
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @return
	 * @throws OAException
	 */
	void resetPassword(User user,OaMsg msg);
	
	/**
	 * 修改密码
	 * @Description: TODO
	 * @author xie
	 * @date 2015年4月8日 下午2:23:28  
	 * @param oldPswd
	 * @param paswd
	 * @param msg
	 */
	void changePassword(UserDto user,String oldPswd,OaMsg msg);
	
	/**
	 * 修改状态
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @return
	 * @throws OAException
	 */
	void changeStatus(User user,OaMsg msg);
	
	/**
	 * 检查字段
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @param msg
	 */
	void check(User user,OaMsg msg);
	
	/**
	 * 获取用户已分配的角色
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @return
	 * @throws OAException
	 */
	void getGrantRole(User user,OaMsg msg);
	
	/**
	 * 获取未分配给用户的角色
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @return
	 * @throws OAException
	 */
	void getUnGrantRole(User user,OaMsg msg);
	
	/**
	 * 授权
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @return
	 * @throws OAException
	 */
	void grantAuthority(String userId,String roleIds,OaMsg msg);
	
	/**
	 * 回收权限
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @return
	 * @throws OAException
	 */
	void recoverAuthority(String ids,OaMsg msg);
	
	/**
	 * 根据登录账号查找用户
	 * @Description: TODO
	 * @author xie
	 * @param account
	 * @return
	 */
	User getByAccount(String account);
	
	/**
	 * 获取用户的角色
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @return
	 */
	Set<String> getUserRole(User user);
	
	/**
	 * 获取用户的权限
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @return
	 */
	Set<String> getUserPermission(User user);
	
	/**
	 * 上传头像
	 * @Description: TODO
	 * @author xie
	 * @date 2015年4月21日 下午7:24:41  
	 * @param ids
	 * @param msg
	 */
	void uploadPhoto(User user,OaMsg msg);
	
	/**
	 * 根据用户名修改密码
	 * @Description: TODO
	 * @author xie
	 * @date 2015年10月9日 下午4:21:29  
	 * @param user
	 * @param msg
	 */
	void updatePasswordByAccount(User user,OaMsg msg);

	/**
	 * 根据权限获取用户
	 * @Title getUserByPermission
	 * @Descrption:TODO
	 * @param:@param user
	 * @param:@return
	 * @return:Object
	 * @auhor jiahy
	 * @date 2016年5月22日下午1:57:48
	 * @throws
	 */
	Object getUserByPermission(UserDto user);
}
