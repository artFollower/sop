package com.skycloud.oa.realm;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.auth.service.UserService;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.utils.Common;

public class ShiroDbRealm extends AuthorizingRealm {

	private static Logger LOG = Logger.getLogger(ShiroDbRealm.class);

	@Autowired
	private UserService userService;

	/**
	 * 初始化用户权限
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		try{
			User user = (User) principals.getPrimaryPrincipal();
//			String loginName = (String) principals.getRealmNames().iterator().next();
			if(!Common.empty(user)) {
//				User user = userService.getByAccount(loginName);
				if(Common.empty(info.getRoles()) || info.getRoles().isEmpty()) {
					info.setRoles(userService.getUserRole(user));
				}
				if(Common.empty(info.getStringPermissions()) || info.getStringPermissions().isEmpty()) {
					info.setStringPermissions(userService.getUserPermission(user));
				}
				LOG.debug("初始化用户权限成功,用户:"+user.getAccount());
			}
			
//			if(!Common.empty(info.getStringPermissions()) && !info.getStringPermissions().isEmpty()
//					&& !Common.empty(info.getRoles()) && !info.getRoles().isEmpty()) {
//				LOG.debug("用户权限已初始化:");
//			}else {
//				String loginName = (String) principals.getRealmNames().iterator().next();
//				User user = userService.getByAccount(loginName);
//				if(Common.empty(info.getRoles()) || info.getRoles().isEmpty()) {
//					info.setRoles(userService.getUserRole(user));
//				}
//				if(Common.empty(info.getStringPermissions()) || info.getStringPermissions().isEmpty()) {
//					info.setStringPermissions(userService.getUserPermission(user));
//				}
//				LOG.debug("初始化用户权限成功,用户:"+loginName);
//			}
		}catch(Exception e) {
			LOG.error("初始化用户权限失败",e);
		}
		return info;
	}

	/**
	 * 认证回调函数,登录时调用.
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		try {
			LOG.debug("验证:" + new Gson().toJson(token));
			User user = userService.getByAccount(token.getUsername());
			if (!Common.empty(user)) {
				SecurityUtils.getSubject().getSession().setAttribute(Constant.SESSION_KEY, user);
				SimpleAuthenticationInfo result = new SimpleAuthenticationInfo(user,user.getPassword(), user.getAccount());
//				SimpleAuthenticationInfo result = new SimpleAuthenticationInfo(token.getUsername(),user.getPassword(), user.getAccount());
				return result;
			} else {
				return null;
			}
		} catch (Exception e) {
			LOG.error("验证失败", e);
			return null;
		}
	}
}