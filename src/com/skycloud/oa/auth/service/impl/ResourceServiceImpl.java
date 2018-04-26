package com.skycloud.oa.auth.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.auth.dao.ResourceDao;
import com.skycloud.oa.auth.model.SecurityResources;
import com.skycloud.oa.auth.service.ResourceService;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 * 资源管理业务逻辑实现类
 * @ClassName: ResourceServiceImpl 
 * @Description: TODO
 * @author xie
 * @date 2014年12月29日 下午2:18:54
 */
@Service
public class ResourceServiceImpl implements ResourceService {

	private static Logger LOG = Logger.getLogger(ResourceServiceImpl.class);
	
	@Autowired
	private ResourceDao resourceDao;

	@Override
	@Log(object=C.LOG_OBJECT.AUTH_POWER,type=C.LOG_TYPE.CREATE)
	public void create(SecurityResources resource, OaMsg msg) {
		// TODO Auto-generated method stub
		try{
			resource.setId(resourceDao.create(resource));
			msg.getData().add(resource);
			msg.setMsg("添加成功");
			LOG.debug("资源添加成功"+new Gson().toJson(resource));
		}catch(OAException e) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("资源创建失败", e);
		}
	}

	@Override
	public void get(SecurityResources resource, PageView pageView, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			msg.getData().addAll(resourceDao.get(resource, pageView.getStartRecord(), pageView.getMaxresult()));
			if (pageView.getMaxresult() != 0) {
				msg.getMap().put(Constant.TOTALRECORD, resourceDao.count(resource) + "");
			}
		} catch (OAException o) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("资源查询失败", o);
		}
	}

	@Override
	@Log(object=C.LOG_OBJECT.AUTH_POWER,type=C.LOG_TYPE.UPDATE)
	public void update(SecurityResources resource, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			
			if (resourceDao.modify(resource)) {
				msg.setMsg("资源信息修改成功");
				LOG.debug("成功角色资源修改"+ new Gson().toJson(resource));
			} else {
				msg.setCode(Constant.SYS_CODE_NOT_EXIT);
				msg.setMsg("资源不存在");
				LOG.debug("资源不存在:" +  new Gson().toJson(resource));
			}
		} catch (OAException e) {
			LOG.error(e.getMessage(), e);
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
		}
	}

	@Override
	@Log(object=C.LOG_OBJECT.AUTH_POWER,type=C.LOG_TYPE.DELETE)
	public void delete(String ids, OaMsg msg) {
		// TODO Auto-generated method stub
		try{
			if(resourceDao.delete(ids)) {
				msg.setMsg("删除成功");
				LOG.debug("成功删除资源"+ids);
			}else {
				msg.setCode(Constant.SYS_CODE_NOT_EXIT);
				msg.setMsg("删除的资源不存在");
				LOG.debug("删除的资源不存在:"+ids);
			}
		}catch(OAException e) {
			LOG.error(e.getMessage(), e);
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
		}catch(Exception e) {
			LOG.error(e.getMessage(), e);
			msg.setCode(Constant.SYS_CODE_SYS_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
		}
	}

	@Override
	public void exit(SecurityResources resource, OaMsg msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(SecurityResources resource, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			resourceDao.move(resource);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage(), e);
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
		}
	}
	
}
