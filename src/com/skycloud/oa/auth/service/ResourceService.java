/**
 * 
 */
package com.skycloud.oa.auth.service;

import com.skycloud.oa.auth.model.SecurityResources;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;



/**
 * 资源管理业务逻辑处理接口类
 * @ClassName: ResourceService 
 * @Description: TODO
 * @author xie
 * @date 2014年12月29日 下午2:16:51
 */
public interface ResourceService {
	
	/**
	 * 添加资源
	 * @Description: TODO
	 * @author xie
	 * @param resource
	 * @param msg
	 */
	void create(SecurityResources resource,OaMsg msg);
	
	/**
	 * 查询资源
	 * @Description: TODO
	 * @author xie
	 * @param resource
	 * @param pageView
	 * @param msg
	 */
	void get(SecurityResources resource,PageView pageView,OaMsg msg);
	
	/**
	 * 更新资源
	 * @Description: TODO
	 * @author xie
	 * @param resource
	 * @param msg
	 */
	void update(SecurityResources resource,OaMsg msg);
	
	/**
	 * 移动节点
	 * @param resource
	 * @param msg
	 */
	void move(SecurityResources resource,OaMsg msg);
	
	/**
	 * 删除资源信息
	 * @Description: TODO
	 * @author xie
	 * @param ids
	 * @param msg
	 */
	void delete(String ids,OaMsg msg);
	
	
	/**
	 * 检查是否存在
	 * @Description: TODO
	 * @author xie
	 * @param resource
	 * @param msg
	 */
	void exit(SecurityResources resource,OaMsg msg);
}
