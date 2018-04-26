package com.skycloud.oa.auth.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.auth.dao.RoleDao;
import com.skycloud.oa.auth.model.Role;
import com.skycloud.oa.auth.service.RoleService;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;

/**
 * 角色管理业务逻辑实现类
 * 
 * @ClassName: RoleServiceImpl
 * @Description: TODO
 * @author xie
 * @date 2014年12月29日 上午11:38:44
 */
@Service
public class RoleServiceImpl implements RoleService
{

	private static Logger LOG = Logger.getLogger(RoleServiceImpl.class);

	@Autowired
	private RoleDao roleDao;

	@Override
	@Log(object = C.LOG_OBJECT.AUTH_ROLE, type = C.LOG_TYPE.CREATE)
	public void create(Role role, OaMsg msg)
	{
		// TODO Auto-generated method stub
		try {
			role.setId(roleDao.create(role));
			roleDao.modifyPermission(role);
			msg.getData().add(role);
			msg.setMsg("添加成功");
			LOG.debug("角色添加成功" + new Gson().toJson(role));
		} catch (OAException e) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("角色创建失败", e);
		}
	}

	@Override
	public void get(Role role, PageView pageView, OaMsg msg)
	{
		// TODO Auto-generated method stub
		try {
			msg.getData().addAll(
					roleDao.get(role, pageView.getStartRecord(),
							pageView.getMaxresult()));
			if (pageView.getMaxresult() != 0) {
				msg.getMap()
						.put(Constant.TOTALRECORD, roleDao.count(role) + "");
			}
		} catch (OAException o) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("角色查询失败", o);
		}
	}

	@Override
	@Log(object = C.LOG_OBJECT.AUTH_ROLE, type = C.LOG_TYPE.UPDATE)
	public void update(Role role, OaMsg msg)
	{
		// TODO Auto-generated method stub
		try {

			if (roleDao.modify(role) && roleDao.modifyPermission(role)) {
				msg.setMsg("角色信息修改成功");
				LOG.debug("成功角色信息修改" + new Gson().toJson(role));
			} else {
				msg.setCode(Constant.SYS_CODE_NOT_EXIT);
				msg.setMsg("角色不存在");
				LOG.debug("角色不存在:" + new Gson().toJson(role));
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
	@Log(object = C.LOG_OBJECT.AUTH_ROLE, type = C.LOG_TYPE.DELETE)
	public void delete(String ids, OaMsg msg)
	{
		// TODO Auto-generated method stub
		try {
			if (roleDao.delete(ids)) {
				msg.setMsg("删除成功");
				LOG.debug("成功删除角色" + ids);
			} else {
				msg.setCode(Constant.SYS_CODE_NOT_EXIT);
				msg.setMsg("删除的角色不存在");
				LOG.debug("删除的角色不存在:" + ids);
			}
		} catch (ConstraintViolationException e) {
			LOG.error(e.getMessage(), e);
			msg.setCode(Constant.SYS_CODE_CONFLICT);
			msg.setMsg("该角色已分配");
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
	public void check(Role role, OaMsg msg)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void getGrantPermission(Role role, OaMsg msg)
	{
		// TODO Auto-generated method stub
		try {
//			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> roles = roleDao.getGrantPermission(role);
			getChildren(roles);
			for(int i=0;i<roles.size();i++){
				if(Common.empty(roles.get(i).get("parentId")) || "0".equals(roles.get(i).get("parentId").toString())) {
					msg.getData().add(roles.get(i));
				}
			}
//			for (Map<String, Object> map : roles) {
//				map.put("text", map.get("name"));
//				if (Common.empty(map.get("parentId"))
//						|| "0".equals(map.get("parentId").toString())) {
//
//					for (Map<String, Object> menu : roles) {
//						for (Map<String, Object> access : roles) {
//							if (!Common.empty(access.get("parentId"))
//									&& !"0".equals(menu.get("parentId")
//											.toString())) {
//								if (access.get("parentId").toString()
//										.equals(menu.get("id").toString())) {
//
//									if (menu.containsKey("children")) {// 有子节点
//										@SuppressWarnings("unchecked")
//										Set<Map<String, Object>> children = (HashSet<Map<String, Object>>) menu
//												.get("children");
//										children.add(access);
//									} else {// 无子节点
//										Set<Map<String, Object>> children = new HashSet<Map<String, Object>>();
//										children.add(access);
//										menu.put("children", children);
//									}
//								}
//							}
//
//						}
//
//						if (menu.get("parentId").toString()
//								.equals(map.get("id").toString())) {
//
//							if (map.containsKey("children")) {// 有子节点
//								@SuppressWarnings("unchecked")
//								Set<Map<String, Object>> children = (HashSet<Map<String, Object>>) map
//										.get("children");
//								children.add(menu);
//							} else {// 无子节点
//								Set<Map<String, Object>> children = new HashSet<Map<String, Object>>();
//								children.add(menu);
//								map.put("children", children);
//							}
//						}
//					}
//
//					list.add(map);
//				}
//			}
//			msg.getData().addAll(list);
		} catch (OAException o) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("检查用户信息", o);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void getChildren(List<Map<String, Object>> list) {
		for(int i = 0;i<list.size();i++) {
			Map<String, Object> map = list.get(i);
			map.put("text", map.get("name"));//菜单名称
			if(!Common.empty(map.get("roleId")) && !Common.empty(map.get("roleId").toString()) && !"0".equals(map.get("roleId").toString())) {//已分配了权限
				Map<String,Object> status = new HashMap<String,Object>();
				status.put("selected", true);
				status.put("opened", false);
				map.put("state", status);
			}
			//图标
			if(Common.empty(map.get("category"))) {
				map.put("icon", "fa fa-ling");
			}else if("PLATFORM".equals(map.get("category").toString())) {
				map.put("icon", "fa fa-header");
			}else if("MENU".equals(map.get("category").toString())) {
				map.put("icon", "fa fa-link");
			}else if("ACCESS".equals(map.get("category").toString())) {
				map.put("icon", "fa fa-font");
			}else {
				map.put("icon", "fa fa-link");
			}
			for(int j = 0;j<list.size();j++) {//找出该节点的子节点
				Map<String, Object> node = list.get(j);
				if(!Common.empty(node.get("parentId")) && node.get("parentId").toString().equals(map.get("id").toString())) {
					List<Map<String, Object>> children = null;
					if(map.containsKey("children")) {
						children = (List<Map<String, Object>>) map.get("children");
					}else {
						children = new ArrayList<Map<String, Object>>();
						map.put("children", children);
					}
					children.add(node);
//					map.put("state", null);
				}
			}
		}
	}

	@Override
	public void getUnGrantPermission(Role role, OaMsg msg)
	{
		// TODO Auto-generated method stub
		try {
			msg.getData().addAll(roleDao.getUnGrantPermission(role));
		} catch (OAException o) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("检查用户信息", o);
		}
	}

	@Override
	@Log(object = C.LOG_OBJECT.AUTH_ROLE, type = C.LOG_TYPE.GRANT)
	public void grantPermission(String roleId, String permissionIds, OaMsg msg)
	{
		// TODO Auto-generated method stub
		try {
			if (roleDao.grantPermission(roleId, permissionIds)) {
				msg.setMsg("授权成功");
			} else {
				msg.setCode(Constant.SYS_CODE_NOT_EXIT);
				msg.setMsg("授权失败");
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
	@Log(object = C.LOG_OBJECT.AUTH_ROLE, type = C.LOG_TYPE.RECOVER)
	public void recoverPermission(String ids, OaMsg msg)
	{
		// TODO Auto-generated method stub
		try {
			if (roleDao.recoverPermission(ids)) {
				msg.setMsg("权限回收成功");
			} else {
				msg.setCode(Constant.SYS_CODE_NOT_EXIT);
				msg.setMsg("回收的权限不存在");
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
	public void getUser(Role role, PageView pageView, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			msg.getData().addAll(
					roleDao.getUser(role, pageView.getStartRecord(),
							pageView.getMaxresult()));
			if (pageView.getMaxresult() != 0) {
				msg.getMap()
						.put(Constant.TOTALRECORD, roleDao.countUser(role) + "");
			}
		} catch (OAException o) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("角色查询失败", o);
		}
	}

}
