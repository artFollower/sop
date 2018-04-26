package com.skycloud.oa.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;

/**
 * 持久化基础类，支持所有的CRUD操作
* @ClassName: BaseDao 
* @Description: TODO
* @author xie
* @date 2014年11月29日 下午12:20:49 
*
 */
public interface BaseDao {

	/**
	 * 保存实体
	 * @Description: TODO
	 * @param obj 需要保存的实体
	 * @return
	 * @throws OAException
	 */
	public Serializable save(Object obj) throws OAException;

	/**
	 * 删除实体
	 * @Description: TODO
	 * @param obj 需要删除的实体
	 * @throws OAException
	 */
	public void delete(Object obj) throws OAException;

	/**
	 * 更新实体
	 * @Description: TODO
	 * @param obj 需要更新的实体
	 * @throws OAException
	 */
	public void update(Object obj) throws OAException;
	
	/**
	 * 查询实体
	 * @Description: TODO
	 * @param clazz 实体
	 * @param entityId 实体主键
	 * @return
	 * @throws OAException
	 */
	public Object load(Class<?> clazz, Serializable entityId) throws OAException;

	/**
	 * 执行存储过程。只有传入参数。没有返回结果
	 * @Description: TODO
	 * @param sql
	 * @return
	 * @throws OAException
	 */
	public List<Map<String, Object>> executeProcedure(String sql) throws OAException;

	/**
	 * 执行存储过程
	 * @Description: TODO
	 * @param sql
	 * @return
	 * @throws OAException
	 */
	public Map<String, Object> executeProcedureOne(String sql) throws OAException;

	/**
	 * 查询
	 * @Description: TODO
	 * @param sql 查询的sql语句
	 * @return List<Map<String, Object>>
	 * @throws OAException
	 */
	public List<Map<String, Object>> executeQuery(String sql) throws OAException;

	/**
	 * 执行修改操作
	 * @Description: TODO
	 * @param sql
	 * @return
	 * @throws OAException
	 */
	public int executeUpdate(String sql) throws OAException;

	/**
	 * 执行sql
	 * @Description: TODO
	 * @param sql
	 * @return
	 * @throws OAException
	 */
	public Map<String, Object> execute(String sql) throws OAException;

	/**
	 * 查询单条记录
	 * @Description: TODO
	 * @param sql
	 * @return
	 * @throws OAException
	 */
	public Map<String, Object> executeQueryOne(String sql) throws OAException;

	/**
	 * 保存
	 * @Description: TODO
	 * @param sql
	 * @return
	 * @throws OAException
	 */
	public int insert(String sql) throws OAException;

	/**
	 * 获取记录总数
	 * @Description: TODO
	 * @param sql
	 * @return
	 */
	public long getCount(String sql);
}
