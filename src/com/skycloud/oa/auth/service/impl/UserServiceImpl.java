package com.skycloud.oa.auth.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.cas.CasToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.auth.dao.UserDao;
import com.skycloud.oa.auth.dto.UserDto;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.auth.service.UserService;
import com.skycloud.oa.auth.service.UserSessionService;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;

/**
 * 用户管理业务逻辑实现类
 * 
 * @ClassName: UserServiceImpl
 * @Description: TODO
 * @author xie
 * @date 2014年12月26日 上午10:42:34
 */
@Service
public class UserServiceImpl implements UserService {

	private static Logger LOG = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;
	
	@Autowired
    private UserSessionService userSessionService;

	@Override
	@Log(object = C.LOG_OBJECT.AUTH_USER, type = C.LOG_TYPE.CREATE)
	public void create(User user, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			List<Map<String, Object>> userList = userDao.login(user);
			if (Common.empty(userList) || userList.size() == 0) {
				user.setCreateDate(System.currentTimeMillis()/1000);
				user.setCreater((User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal());
				int userId = userDao.create(user);
				user.setId(userId);
				msg.getData().add(user);
				msg.setMsg("用户添加成功");
				LOG.debug("用户添加成功:" + new Gson().toJson(user));
			} else {
				LOG.debug("用户已存在:" + new Gson().toJson(user));
				msg.setCode(Constant.SYS_CODE_EXIT);
				Map<String, Object> map = userList.get(0);
				if(!Common.empty(map.get("account")) && user.getAccount().equals(map.get("account").toString())) {
					msg.setMsg("该账号已被使用");
					return;
				}
				if(!Common.empty(map.get("phone")) && user.getPhone().equals(map.get("phone").toString())) {
					msg.setMsg("该手机号已被使用");
					return;
				}
				if(!Common.empty(map.get("email")) && user.getEmail().equals(map.get("email").toString())) {
					msg.setMsg("该邮箱已被使用");
					return;
				}
				msg.setMsg("用户已存在");
			}
		} catch (OAException o) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("用户创建失败", o);
		}
	}

	@Override
	public void get(UserDto user, PageView pageView, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			List<Map<String, Object>> users = userDao.get(user,
					pageView.getStartRecord(), pageView.getMaxresult());
			for (Map<String, Object> map : users) {
				if (!Common.empty(map.get("roles"))) {
					map.put("roles", spiltString(map.get("roles").toString()));
				}
				if (!Common.empty(map.get("deps"))) {
					map.put("deps", spiltString(map.get("deps").toString()));
				}
			}
			msg.getData().addAll(users);
			if (pageView.getMaxresult() != 0) {
				msg.getMap()
						.put(Constant.TOTALRECORD, userDao.count(user) + "");
			}
		} catch (OAException o) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("用户查询失败", o);
		}
	}

	private List<Map<String, Object>> spiltString(String strs) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			for (String str : strs.split(",")) {
				Map<String, Object> map = new HashMap<String, Object>();
				String[] s = str.split("\\|#\\|");
				map.put("id", s[0]);
				map.put("name", s[1]);
				list.add(map);
			}
		} catch (Exception e) {
			LOG.error("解析用户信息失败[" + strs + "]");
		}
		return list;
	}

	@Override
	@Log(object = C.LOG_OBJECT.AUTH_USER, type = C.LOG_TYPE.LOGIN)
	public void login(User user, String rememberMe, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			List<Map<String, Object>> list = userDao.getByAccount(user.getAccount());
			if (!Common.empty(list) && list.size() > 0) {
				Map<String, Object> map = list.get(0);
				
				if(Common.MD5Encrypt(user.getPassword(),
						map.get("salt").toString(), Constant.ENCRYPTTIME).equals(map.get("password").toString())) {
					
				}else {
					
				}

				try {
					Gson gson = new Gson();
					User loginUser = gson.fromJson(gson.toJson(map), User.class);
					if (loginUser.getStatus() != 0) {
						msg.setCode(Constant.SYS_CODE_DISABLED);
						msg.setMsg("用户已禁用.");
						LOG.debug("用户已禁用");
					} else if(!Common.MD5Encrypt(user.getPassword(),loginUser.getSalt(), Constant.ENCRYPTTIME).equals(loginUser.getPassword())) {
						msg.setCode(Constant.SYS_CODE_UNAUTHORIZE);
						msg.setMsg("密码错误。");
					}else {
						CasToken token = new CasToken(Constant.OWEN_TICKET);
						token.setUserId(user.getAccount());
						
						/*UsernamePasswordToken token = new UsernamePasswordToken();
						token.setUsername(user.getAccount());
						token.setPassword(Common.MD5Encrypt(user.getPassword(),
								map.get("salt").toString(), Constant.ENCRYPTTIME)
								.toCharArray());
						token.setRememberMe((!Common.empty(rememberMe) && "1"
								.equals(rememberMe)) ? true : false);*/
						
						SecurityUtils.getSubject().login(token);
						
						if(!Common.empty(loginUser.getSessionTime())) {
							SecurityUtils.getSubject().getSession().setTimeout(Long.parseLong(loginUser.getSessionTime()));
						}
						userSessionService.kickSession(user.getAccount());
						msg.setMsg("登录成功.");
						msg.getData().add(loginUser);
						LOG.debug("登录成功");
					}
				} catch (UnknownAccountException e) {
					msg.setCode(Constant.SYS_CODE_UNAUTHORIZE);
					msg.setMsg("账号不存在.");
					LOG.error(e.getMessage(), e);
				} catch (LockedAccountException e) {
					msg.setCode(Constant.SYS_CODE_UNAUTHORIZE);
					LOG.error(e.getMessage(), e);
					msg.setMsg("该用户已禁用，请联系管理员。");
				} catch (AuthenticationException e) {
					msg.setCode(Constant.SYS_CODE_UNAUTHORIZE);
					LOG.error(e.getMessage(), e);
					msg.setMsg("密码错误。");
				} catch (Exception e) {
					msg.setCode(Constant.SYS_CODE_UNAUTHORIZE);
					LOG.error(e.getMessage(), e);
					msg.setMsg("登录失败。");
				}
			} else {
				msg.setCode(Constant.SYS_CODE_UNAUTHORIZE);
				msg.setMsg("账号不存在.");
			}
		} catch (OAException e) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("用户登录失败", e);
		}
	}

	@Override
	@Log(object = C.LOG_OBJECT.AUTH_USER, type = C.LOG_TYPE.UPDATE)
	public void update(User user, OaMsg msg) {
		// TODO Auto-generated method stub
		try {

			if (userDao.modify(user)) {
				msg.setMsg("用户信息修改成功");
				LOG.debug("成功用户信息修改" + new Gson().toJson(user));
			} else {
				msg.setCode(Constant.SYS_CODE_NOT_EXIT);
				msg.setMsg("用户不存在");
				LOG.debug("用户不存在:" + new Gson().toJson(user));
			}
		} catch (OAException e) {
			LOG.error(e.getMessage(), e);
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			msg.setCode(Constant.SYS_CODE_SYS_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
		}
	}

	@Override
	@Log(object = C.LOG_OBJECT.AUTH_USER, type = C.LOG_TYPE.DELETE)
	public void delete(String ids, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			if (userDao.delete(ids)) {
				msg.setMsg("删除成功");
				LOG.debug("成功删除用户" + ids);
			} else {
				msg.setCode(Constant.SYS_CODE_NOT_EXIT);
				msg.setMsg("删除的用户不存在");
				LOG.debug("删除的用户不存在:" + ids);
			}
		} catch (OAException e) {
			LOG.error(e.getMessage(), e);
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			msg.setCode(Constant.SYS_CODE_SYS_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
		}
	}

	@Override
	public void resetPassword(User user, OaMsg msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void changeStatus(User user, OaMsg msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void check(User user, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			List<Map<String, Object>> userList = userDao.login(user);
			if (!Common.empty(userList) && userList.size() > 0) {
				msg.setCode(Constant.SYS_CODE_EXIT);
				msg.setMsg("已存在");
				LOG.debug("检查用户信息>>用户已存在:" + new Gson().toJson(user));
			}
		} catch (OAException o) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("检查用户信息", o);
		}

	}

	@Override
	public void getGrantRole(User user, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			List<Map<String, Object>> list = userDao.getGrantRole(user);
			msg.getData().addAll(list);
		} catch (OAException o) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("检查用户信息", o);
		}
	}

	@Override
	public void getUnGrantRole(User user, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			List<Map<String, Object>> list = userDao.getUnGrantRole(user);
			msg.getData().addAll(list);
		} catch (OAException o) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("检查用户信息", o);
		}
	}

	@Override
	@Log(object = C.LOG_OBJECT.AUTH_USER, type = C.LOG_TYPE.GRANT)
	public void grantAuthority(String userId, String roleIds, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			if (userDao.grantAuthority(userId, roleIds)) {
				msg.setMsg("分配角色成功");
			} else {
				msg.setCode(Constant.SYS_CODE_NOT_EXIT);
				msg.setMsg("分配角色失败");
			}
		} catch (OAException e) {
			LOG.error(e.getMessage(), e);
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			msg.setCode(Constant.SYS_CODE_SYS_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
		}
	}

	@Override
	@Log(object = C.LOG_OBJECT.AUTH_USER, type = C.LOG_TYPE.RECOVER)
	public void recoverAuthority(String ids, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			if (userDao.recoverAuthority(ids)) {
				msg.setMsg("角色回收成功");
			} else {
				msg.setCode(Constant.SYS_CODE_NOT_EXIT);
				msg.setMsg("回收的角色不存在");
			}
		} catch (OAException e) {
			LOG.error(e.getMessage(), e);
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			msg.setCode(Constant.SYS_CODE_SYS_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
		}
	}

	@Override
	public User getByAccount(String account) {
		// TODO Auto-generated method stub
		try {
			List<Map<String, Object>> list = userDao.getByAccount(account);
			if (!Common.empty(list) && list.size() > 0) {
				Map<String, Object> map = list.get(0);
				Gson gson = new Gson();
				User user = gson.fromJson(gson.toJson(map), User.class);
				return user;
			} else {
				return null;
			}
		} catch (OAException e) {
			LOG.error("查找用户失败", e);
			return null;
		} catch (Exception e) {
			LOG.error("查找用户失败", e);
			return null;
		}
	}

	@Override
	public Set<String> getUserRole(User user) {
		// TODO Auto-generated method stub
		try {
			Set<String> userRole = new HashSet<String>();
			List<Map<String, Object>> list = userDao.getUserRole(user);
			if (!Common.empty(list) && list.size() > 0) {
				for (Map<String, Object> map : list) {
					userRole.add(map.get("type").toString());
				}
				return userRole;
			} else {
				return null;
			}
		} catch (OAException e) {
			LOG.error("查询用户权限失败", e);
			return null;
		} catch (Exception e) {
			LOG.error("查询用户权限失败", e);
			return null;
		}
	}

	@Override
	public Set<String> getUserPermission(User user) {
		// TODO Auto-generated method stub
		try {
			Set<String> userPermission = new HashSet<String>();
			List<Map<String, Object>> list = userDao.getUserPermission(user);
			if (!Common.empty(list) && list.size() > 0) {
				for (Map<String, Object> map : list) {
					userPermission.add(map.get("indentifier").toString());
				}
				return userPermission;
			} else {
				return null;
			}
		} catch (OAException e) {
			LOG.error("查询用户权限失败", e);
			return null;
		} catch (Exception e) {
			LOG.error("查询用户权限失败", e);
			return null;
		}
	}

	@Override
	public void changePassword(UserDto user, String oldPswd, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			List<Map<String, Object>> list = userDao.get(user, 0, 0);
			if (Common.empty(list) || list.size() == 0) {
				msg.setCode(Constant.SYS_CODE_NOT_EXIT);
				msg.setMsg("用户不存在");
			} else {
				Map<String, Object> map = list.get(0);
				String salt = map.get("salt").toString();
				if(!Common.empty(oldPswd)) {
					if (map.get("password")
							.toString()
							.equals(Common.MD5Encrypt(oldPswd, salt,
									Constant.ENCRYPTTIME))) {
						user.setPassword(Common.MD5Encrypt(user.getPassword(),
								salt, Constant.ENCRYPTTIME));
						if (userDao.resetPassword(user)) {
							msg.setMsg("密码修改成功");
						} else {
							msg.setCode(Constant.SYS_CODE_DB_ERR);
							msg.setMsg("密码修改失败");
						}
					} else {
						msg.setCode(Constant.SYS_CODE_PARAM_ERROR);
						msg.setMsg("原密码错误");
					}
				}else {
					user.setPassword(Common.MD5Encrypt(user.getPassword(),
							salt, Constant.ENCRYPTTIME));
					if (userDao.resetPassword(user)) {
						msg.setMsg("密码修改成功");
					} else {
						msg.setCode(Constant.SYS_CODE_DB_ERR);
						msg.setMsg("密码修改失败");
					}
				}
			}
		} catch (OAException e) {
			LOG.error("查询用户权限失败", e);
		} catch (Exception e) {
			LOG.error("查询用户权限失败", e);
		}
	}

	@Override
	public void uploadPhoto(User user, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			userDao.modify(user);
			msg.setMsg("照片上传成功");
		} catch (Exception e) {
			LOG.error("查询用户权限失败", e);
			msg.setCode(Constant.SYS_CODE_PARAM_ERROR);
			msg.setMsg("照片上传失败");
		}
	}

	@Override
	public void updatePasswordByAccount(User user, OaMsg msg)
	{
		// TODO Auto-generated method stub
		//获取用户
		try {
			List<Map<String,Object>> list = userDao.getByAccount(user.getAccount());
			if(list.size() == 0) {
				msg.setCode(Constant.SYS_CODE_PARAM_ERROR);
				msg.setMsg("用户不存在");
			}else {
				Map<String, Object> map = list.get(0);
				String salt = map.get("salt").toString();
				UserDto udto = new UserDto();
				udto.setId(Integer.parseInt(map.get("id").toString()));
				udto.setPassword(Common.MD5Encrypt(user.getPassword(),salt, Constant.ENCRYPTTIME));
				if (userDao.resetPassword(udto)) {
					msg.setMsg("密码修改成功");
				} else {
					msg.setCode(Constant.SYS_CODE_DB_ERR);
					msg.setMsg("密码修改失败");
				}
			}
		} catch (OAException e) {
			// TODO Auto-generated catch block
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("密码修改失败");
		}
	}

	/**
	 * @Title getUserByPermission
	 * @Descrption:TODO
	 * @param:@param user
	 * @param:@return
	 * @auhor jiahy
	 * @date 2016年5月22日下午1:58:15
	 * @throws
	 */
	@Override
	public Object getUserByPermission(UserDto user) {
		OaMsg oaMsg=new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(userDao.getUserByPermission(user));
	} catch (OAException e) {
		// TODO Auto-generated catch block
		oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		oaMsg.setMsg("密码修改失败");
	}
		return oaMsg;
	}

}
