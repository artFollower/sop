package com.skycloud.oa.realm;

import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasAuthenticationException;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.cas.CasToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.StringUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.TicketValidationException;
import org.jasig.cas.client.validation.TicketValidator;
import org.springframework.beans.factory.annotation.Autowired;

import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.auth.service.UserService;
import com.skycloud.oa.auth.service.UserSessionService;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.utils.Common;

public class ShiroCASRealm extends CasRealm
{

	private final static Logger LOG = Logger.getLogger(ShiroCASRealm.class);

	Set<String> permissions = null;// 权限
	Set<String> roles = null;// 角色

	@Autowired
	private UserService userService;
	
	@Autowired
    private UserSessionService userSessionService;

	public ShiroCASRealm()
	{
		setCredentialsMatcher(new AllowAllCredentialsMatcher());
		// setAuthenticationTokenClass(UsernamePasswordToken.class);
	}

	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException
	{

		CasToken casToken = (CasToken) token;
		if (token == null)
			return null;
		String ticket = (String) casToken.getCredentials();
		if (!StringUtils.hasText(ticket))
			return null;
		String password = "";
		if (ticket.equals(Constant.OWEN_TICKET)) {
			User user = userService.getByAccount(casToken.getPrincipal().toString());
			password = user.getPassword();
			user.setPassword("");
			user.setSalt("");
			userSessionService.kickSession(user.getAccount());
			if(!Common.empty(user.getSessionTime())) {
				SecurityUtils.getSubject().getSession().setTimeout(Long.parseLong(user.getSessionTime()));
			}
			return new SimpleAuthenticationInfo(user, password,user.getName());
		} else {
			TicketValidator ticketValidator = ensureTicketValidator();
			try {
				Assertion casAssertion = ticketValidator.validate(ticket,
						getCasService());
				AttributePrincipal casPrincipal = casAssertion.getPrincipal();
				String name = casPrincipal.getName();
				User user = userService.getByAccount(name);
				password = user.getPassword();
				user.setPassword("");
				user.setSalt("");
				userSessionService.kickSession(user.getAccount());
				if(!Common.empty(user.getSessionTime())) {
					SecurityUtils.getSubject().getSession().setTimeout(Long.parseLong(user.getSessionTime()));
				}
				return new SimpleAuthenticationInfo(user, password,name);
			} catch (TicketValidationException e) {
				e.printStackTrace();
				throw new CasAuthenticationException((new StringBuilder())
						.append("Unable to validate ticket [").append(ticket)
						.append("]").toString(), e);
			}
		}
	}

	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals)
	{
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		try {
			User user = (User) principals.getPrimaryPrincipal();
			if (!Common.empty(user)) {
				if (Common.empty(info.getRoles()) || info.getRoles().isEmpty()) {
					if (!Common.empty(roles)) {
						info.setRoles(roles);
					} else {
						info.setRoles(userService.getUserRole(user));
					}
				}
				if (Common.empty(info.getStringPermissions())
						|| info.getStringPermissions().isEmpty()) {
					if (!Common.empty(permissions)) {
						info.setStringPermissions(permissions);
					} else {
						info.setStringPermissions(userService
								.getUserPermission(user));
					}
				}
				LOG.debug("初始化用户权限成功,用户:" + user.getAccount());
			}

		} catch (Exception e) {
			LOG.error("初始化用户权限失败", e);
		}
		return info;
	}

}