package com.skycloud.oa.auth.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.auth.dao.OrganizationDao;
import com.skycloud.oa.auth.model.Parties;
import com.skycloud.oa.auth.service.OrganizationService;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;

/**
 * 组织架构业务逻辑处理类
 * @ClassName: OrganizationServiceImpl 
 * @Description: TODO
 * @author xie
 * @date 2015年1月16日 上午10:48:51
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

	private static Logger LOG = Logger.getLogger(OrganizationServiceImpl.class);
	
	@Autowired
	private OrganizationDao organizationDao;

	@Override
	@Log(object=C.LOG_OBJECT.AUTH_ORG,type=C.LOG_TYPE.CREATE)
	public void create(Parties parties, OaMsg msg) {
		// TODO Auto-generated method stub
		try{
			parties.setId(organizationDao.create(parties));
			msg.getData().add(parties);
			msg.setMsg("添加成功");
			LOG.debug("组织架构添加成功"+new Gson().toJson(parties));
		}catch(OAException e) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("资源创建失败", e);
		}
	}

	@Override
	public void get(Parties parties,PageView pageView, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			msg.getData().addAll(organizationDao.get(parties, pageView.getStartRecord(), pageView.getMaxresult()));
			if (pageView.getMaxresult() != 0) {
				msg.getMap().put(Constant.TOTALRECORD, organizationDao.count(parties) + "");
			}
		} catch (OAException o) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("资源查询失败", o);
		}
	}

	@Override
	@Log(object=C.LOG_OBJECT.AUTH_ORG,type=C.LOG_TYPE.UPDATE)
	public void modify(Parties parties, OaMsg msg) {
		// TODO Auto-generated method stub
		try{
			if(organizationDao.modify(parties)) {
				msg.setMsg("修改成功");
				LOG.debug("成功修改组织");
			}else {
				msg.setCode(Constant.SYS_CODE_NOT_EXIT);
				msg.setMsg("修改的对象不存在");
				LOG.debug("修改的对象不存在:"+new Gson().toJson(parties));
			}
		}catch(OAException e) {
			LOG.error(e.getMessage(), e);
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
		}
	}

	@Override
	@Log(object=C.LOG_OBJECT.AUTH_ORG,type=C.LOG_TYPE.DELETE)
	public void delete(String ids, OaMsg msg) {
		// TODO Auto-generated method stub
		try{
			if(organizationDao.delete(ids)) {
				msg.setMsg("删除成功");
				LOG.debug("成功删除组织"+ids);
			}else {
				msg.setCode(Constant.SYS_CODE_NOT_EXIT);
				msg.setMsg("删除的对象不存在");
				LOG.debug("删除的对象不存在:"+ids);
			}
		}catch(OAException e) {
			LOG.error(e.getMessage(), e);
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
		}catch(ConstraintViolationException e) {
			LOG.error(e.getMessage(), e);
			msg.setCode(Constant.SYS_CODE_SYS_ERR);
			msg.setMsg("已被关联,不能删除");
		}
	}

	@Override
	public void getRelationUser(Parties parties,PageView pageView, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			msg.getData().addAll(organizationDao.getRelationUser(parties, pageView.getStartRecord(), pageView.getMaxresult()));
			if (pageView.getMaxresult() != 0) {
				msg.getMap().put(Constant.TOTALRECORD, organizationDao.countRelationUser(parties) + "");
			}
		} catch (OAException o) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("资源查询失败", o);
		}
	}

	@Override
	public void getUnRelationUser(Parties parties,PageView pageView, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			msg.getData().addAll(organizationDao.getUnRelationUser(parties, pageView.getStartRecord(), pageView.getMaxresult()));
			if (pageView.getMaxresult() != 0) {
				msg.getMap().put(Constant.TOTALRECORD, organizationDao.countUnRelationUser(parties) + "");
			}
		} catch (OAException o) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("资源查询失败", o);
		}
	}

	@Override
	public List<Map<String, Object>> getOrganization(Parties parties) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> root = new ArrayList<Map<String, Object>>();
		try {
			List<Map<String, Object>> os = organizationDao.get(parties, 0, 0);
			for(Map<String, Object> map : os) {
//				if(map.get("category").toString().equals(C.AUTH.PERTIES_CATEGORY_COMPANY)) {
//					map.put("text", map.get("name"));
//					map.put("children", new ArrayList<Map<String, Object>>());
//					root.add(map);
//				}
				if(!Common.empty(map.get("parentId")) && "0".equals(map.get("parentId").toString())) {
					getOrgTree(map,os,0);//递归调用
					map.put("text", map.get("name"));
					map.put("icon", map.get("fa fa-bank"));
					root.add(map);
				}
			}
//			for(Map<String, Object> map : os) {
//				if(map.get("category").toString().equals(C.AUTH.PERTIES_CATEGORY_DEPARTMENT)) {
//					map.put("text", map.get("name"));
//					for(Map<String, Object> key : root) {
//						if(key.get("id").toString().equals(map.get("parentId").toString())) {
//							@SuppressWarnings("unchecked")
//							List<Map<String, Object>> children =  (List<Map<String, Object>>) key.get("children");
//							children.add(map);
//						}
//					}
//				}
//			}
		} catch (OAException o) {
			LOG.error("获取组织架构失败", o);
		}
		return root;
	}
	
	private void getOrgTree(Map<String, Object> root,List<Map<String, Object>> orgs,int index) {
		for(int i = 0;i<orgs.size();i++) {
			Map<String, Object> org = orgs.get(i);
			if(org.get("id").toString().equals(root.get("id").toString())) {
				continue;
			}
			org.put("text", org.get("name"));
			if(org.get("category").toString().equals(C.AUTH.PERTIES_CATEGORY_DEPARTMENT)) {
				org.put("icon", "fa fa-sitemap");
			}else {
				org.put("icon", "fa fa-home");
			}
			
			if(org.get("parentId").toString().equals(root.get("id").toString())) {
				if(root.containsKey("children")) {//有子节点
					@SuppressWarnings("unchecked")
					Set<Map<String, Object>> children =  (HashSet<Map<String, Object>>) root.get("children");
					children.add(org);
				}else {//无子节点
					Set<Map<String, Object>> children =  new HashSet<Map<String, Object>>();
					children.add(org);
					root.put("children", children);
				}
			}
			if(index < (orgs.size() -1) && i > index) {
				getOrgTree(org,orgs,i);//递归调用
			}
		}
	}

	@Override
	@Log(object=C.LOG_OBJECT.AUTH_ORG,type=C.LOG_TYPE.GRANT)
	public void relationUser(String userIds, int departmentId, OaMsg msg) {
		// TODO Auto-generated method stub
		try{
			if(organizationDao.relationUser(userIds, departmentId)) {
				msg.setMsg("添加成功");
				LOG.debug("添加成功"+userIds);
			}else {
				msg.setCode(Constant.SYS_CODE_NOT_EXIT);
				msg.setMsg("添加失败");
				LOG.debug("添加失败:"+userIds);
			}
		}catch(OAException e) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("部门用户添加失败", e);
		}
	}

	@Override
	@Log(object=C.LOG_OBJECT.AUTH_EMPLOYEE,type=C.LOG_TYPE.UPDATE)
	public void modifyeMPLOYEE(Parties parties, OaMsg msg) {
		// TODO Auto-generated method stub
		try{
			msg.setMsg("用户信息修改成功");
			Parties _parties = new Parties();
			_parties.setCategory(C.AUTH.PERTIES_CATEGORY_EMPLOYEE);
			_parties.setUser(parties.getUser());
			if(organizationDao.get(_parties, 0, 0).size() > 0) {
				if(organizationDao.modifyEmployee(parties)) {
					msg.setMsg("用户信息修改成功");
					LOG.debug("成功修改用户信息");
				}else {
					msg.setCode(Constant.SYS_CODE_NOT_EXIT);
					msg.setMsg("修改的对象不存在");
					LOG.debug("修改的对象不存在:"+new Gson().toJson(parties));
				}
			}else {
				organizationDao.createEmployee(parties);
			}
			
		}catch(OAException e) {
			LOG.error(e.getMessage(), e);
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
		}
	}
	
	
}
