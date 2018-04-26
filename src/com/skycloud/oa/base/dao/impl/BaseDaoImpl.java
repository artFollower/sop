package com.skycloud.oa.base.dao.impl;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.BaseDao;
import com.skycloud.oa.base.dto.FieldVO;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.outbound.dto.GoodsDto;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.DateTimeUtil;
import com.skycloud.oa.utils.HibernateUtils;

/**
 * CRUD操作基类
* @ClassName: BaseDaoImp 
* @Description: 
* @author xie
* @date 2014年11月29日 下午2:42:58 
*
 */
@Repository
public class BaseDaoImpl implements BaseDao {

	public Logger logger = Logger.getLogger(BaseDaoImpl.class);

	@Resource(name = "hibernateTemplate")
	public HibernateTemplate hibernateTemplate;

	/**
	 * 拿到连接
	 * @Description: 
	 * @return
	 */
	public Session getSession() {
		Session session = HibernateUtils.getCurrentSession();
		if (session == null) {
			session = HibernateUtils.openSession(hibernateTemplate.getSessionFactory());
			session.beginTransaction();
			logger.debug("-------------------transcation  start!--------------");
		}
		return session;

	}

	/**
	 * 清除连接
	 * @Description: 
	 */
	public void clear() {
		getSession().clear();
	}

	@Override
	public Serializable save(Object obj) throws OAException {
		Serializable serializable = null;
		try {

			serializable = getSession().save(obj);

		} catch (Throwable ex) {

			wrapException(ex);

		}
		return serializable;
	}

	@Override
	public void delete(Object obj) throws OAException {

		try {

			getSession().delete(obj);

		} catch (Throwable ex) {

			wrapException(ex);
		}
	}

	@Override
	public void update(Object obj) throws OAException {

		try {

			getSession().update(obj);

		} catch (Throwable ex) {

			wrapException(ex);
		}
	}
	
	@Override
	public Object load(Class<?> clazz, Serializable entityId) throws OAException {
		logger.debug("执行查询,entityId:" + entityId);
		if (entityId == null)
			throw new OAException(Constant.SYS_CODE_DB_ERR, "传入的实体id不能为空");

		return getSession().load(clazz, entityId);

	}

	@Override
	public Map<String, Object> executeProcedureOne(String sql) throws OAException {
		logger.debug("执行查询,sql:" + sql);
		List<Map<String, Object>> rows = executeProcedure(sql);
		if (rows.size() == 0) {
			return new HashMap<String, Object>();
		} else if (rows.size() == 1) {
			return rows.get(0);
		} else {
			throw new OAException(Constant.SYS_CODE_DB_ERR, "得到唯一对象不唯一", null);
		}
	}

	@Override
	public List<Map<String, Object>> executeProcedure(final String sql) throws OAException {
		logger.debug("执行查询,sql:" + sql);
		Session session = getSession();
		return session.doReturningWork(new ReturningWork<List<Map<String, Object>>>() {

			@Override
			public List<Map<String, Object>> execute(Connection conn) throws SQLException {
				
				List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
				CallableStatement cstmt = null;
				ResultSet rs = null;
				conn.setAutoCommit(false);
				cstmt = conn.prepareCall(sql);
				cstmt.execute();
				rs = cstmt.getResultSet();
				doMapResult(rs, rows);
				return rows;
			}
		});
	}

	@Override
	public Map<String, Object> executeQueryOne(String sql) throws OAException {
		logger.debug("执行查询,sql:" + sql);
		List<Map<String, Object>> rows = executeQuery(sql);
		if (rows.size() == 0) {
			return new HashMap<String, Object>();
		} else if (rows.size() == 1) {
			return rows.get(0);
		} else {
			throw new OAException(Constant.SYS_CODE_DB_ERR, "得到唯一对象不唯一", null);
		}
	}
	
	public List<Map<String, Object>> executeQuery(final String sql) throws OAException {

		logger.debug("执行查询,sql:" + sql);

		Session session = getSession();

		return session.doReturningWork(new ReturningWork<List<Map<String, Object>>>() {

			@Override
			public List<Map<String, Object>> execute(Connection conn) throws SQLException {
				
				List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				doMapResult(rs, rows);
				return rows;
			}
		});

	}
	public GoodsDto executeProce(final String sql) throws OAException {
		
		logger.debug("执行查询,sql:" + sql);
		
		Session session = getSession();
		
		return session.doReturningWork(new ReturningWork<GoodsDto>() {
			
			@SuppressWarnings("unused")
			@Override
			public GoodsDto execute(Connection conn) throws SQLException {
				
				GoodsDto goodsDto = new GoodsDto() ;
				CallableStatement cstmt = null;
				ResultSet rs = null;
				conn.setAutoCommit(false);
				cstmt = conn.prepareCall(sql);
				cstmt.execute();
				rs = cstmt.getResultSet();
				GoodsDto modInfo = new GoodsDto();
				GoodsDto preModule = null;
				Map<Integer, GoodsDto> map = new HashMap<Integer, GoodsDto>();
				int flag = 1 ;
				while(rs.next()){
					if (flag++ == 1) {
						modInfo.setId(rs.getInt("id"));
						modInfo.setLevel(rs.getString("lever"));
						modInfo.setCode(rs.getString("code"));
						modInfo.setGoodsCurrent(rs.getString("goodsCurrent"));
						modInfo.setName(rs.getString("name"));
						modInfo.setGoodsIn(rs.getString("goodsIn"));
						modInfo.setGoodsOut(rs.getString("goodsOut"));
						modInfo.setGoodsTotal(rs.getString("goodsTotal"));
						modInfo.setLadingCode(rs.getString("ladingCode"));
						modInfo.setLadingType(rs.getString("ladingType"));
						modInfo.setSourceGoodsId(rs.getInt("sourceGoodsId"));
						map.put(modInfo.getId(), modInfo);
						preModule = modInfo;
					} else {
						GoodsDto currentModule = new GoodsDto();
						currentModule.setId(rs.getInt("id"));
						currentModule.setName(rs.getString("name"));
						currentModule.setLevel(rs.getString("lever"));
						currentModule.setCode(rs.getString("code"));
						currentModule.setGoodsCurrent(rs.getString("goodsCurrent"));
						currentModule.setGoodsIn(rs.getString("goodsIn"));
						currentModule.setGoodsOut(rs.getString("goodsOut"));
						currentModule.setGoodsTotal(rs.getString("goodsTotal"));
						currentModule.setLadingCode(rs.getString("ladingCode"));
						currentModule.setLadingType(rs.getString("ladingType"));
						currentModule.setSourceGoodsId(rs.getInt("sourceGoodsId"));
						map.put(currentModule.getId(), currentModule);
						if (preModule.getId() == currentModule.getSourceGoodsId()) {
							preModule.getChildren().add(currentModule);
							preModule = currentModule;
						} else {
							if (map.get(currentModule.getSourceGoodsId()) != null) {
								preModule = map.get(currentModule.getSourceGoodsId());
								preModule.getChildren().add(currentModule);
								preModule = currentModule;
							}
						}
					}
				}
				return modInfo;
			}
		});
		
	}

	public int executeUpdate(final String sql) throws OAException {
		logger.debug("执行更新,sql:" + sql);
		Session session = getSession();
		return session.doReturningWork(new ReturningWork<Integer>() {

			@Override
			public Integer execute(Connection conn) throws SQLException {
				
				Statement stmt = conn.createStatement();
				stmt.setEscapeProcessing(false);
				return stmt.executeUpdate(sql);
			}
		});
	}

	public Map<String, Object> execute(final String sql) throws OAException {
		logger.debug("执行更新,sql:" + sql);
		Session session = getSession();

		return session.doReturningWork(new ReturningWork<Map<String, Object>>() {

			@Override
			public Map<String, Object> execute(Connection conn) throws SQLException {
				
				Map<String, Object> infos = new HashMap<String, Object>();
				Statement pstmt = conn.createStatement();
				pstmt.setEscapeProcessing(false);
				boolean result = pstmt.execute(sql);
				int num = 0;
				if (result) {
					ResultSet rs = pstmt.executeQuery(sql);
					if (rs.last()) {
						num = rs.getRow();
					}
				} else {
					num = pstmt.getUpdateCount();
				}
				infos.put("sucess", num);
				return infos;
			}
		});
	}

	public int insert(final String sql) throws OAException {
		logger.debug("执行插入,sql:" + sql);
		Session session = getSession();
		return session.doReturningWork(new ReturningWork<Integer>() {

			@Override
			public Integer execute(Connection conn) throws SQLException {
				
				int id = 0;
				Statement stmt = conn.createStatement();
				stmt.setEscapeProcessing(false);
				int rows = stmt.executeUpdate(sql);
				if (rows > 0) {
					ResultSet rs = stmt.executeQuery("SELECT last_insert_id()");
					if (rs.next()) {
						id = rs.getInt(1);
					}
				}
				return id;
			}
		});
	}

	/**
	 * 获取记录总数
	 * 
	 * @return 记录总数
	 * @throws ChkException
	 */
	public long getCount(final String sql) {
		logger.debug("执行统计,sql:" + sql);
		Session session = getSession();
		return session.doReturningWork(new ReturningWork<Long>() {

			@Override
			public Long execute(Connection conn) throws SQLException {
				
				long count = 0;
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					count = rs.getLong(1); 
				}
				return count;
			}
		});
	}

	/**
	 * 异常处理
	 * 
	 * @Description: 
	 * @param ex
	 * @return void
	 * @throws OAException
	 */
	protected static void wrapException(Throwable ex) throws OAException {
		ex.printStackTrace();
		wrapException("", ex);
	}

	/**
	 * 
	 * @Description: 
	 * @param message
	 * @param ex
	 * @return void
	 * @throws OAException
	 */
	protected static void wrapException(String message, Throwable ex) throws OAException {
		throw new OAException(Constant.SYS_CODE_DB_ERR, message, ex);
	}
	/**
	 * 将查询出来的数据映射到集合中
	 * 
	 * @Description: 
	 * @param rs
	 * @param rows
	 * @throws SQLException
	 */
	private void doMapResult(ResultSet rs, List<Map<String, Object>> rows) throws SQLException {
		if (!Common.empty(rs)) {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			List<FieldVO> fields = new ArrayList<FieldVO>(columnCount);
			FieldVO field = null;
			for (int i = 1; i <= columnCount; i++) {
				field = new FieldVO();
				field.setName(rsmd.getColumnLabel(i));
				field.setInt(rsmd.getColumnTypeName(i).contains("INT"));
				field.setDate(rsmd.getColumnTypeName(i).contains("DATE"));
				field.setDate(rsmd.getColumnTypeName(i).contains("DATETIME"));
				field.setDouble(rsmd.getColumnTypeName(i).contains("DOUBLE"));
				fields.add(field);
			}
			rsmd = null;
			Map<String, Object> row = null;
			while (rs.next()) {
				row = new HashMap<String, Object>(columnCount);
				for (FieldVO obj : fields) {
					if (obj.isInt()) {
//						row.put(obj.getName().toLowerCase(), rs.getInt(obj.getName()));
						row.put(obj.getName(), rs.getInt(obj.getName()));
					} else if (obj.isDate()) {
//						row.put(obj.getName().toLowerCase(), DateTimeUtil.getShortDateTimeString(DateTimeUtil.getShortDateTime(rs.getString(obj.getName()))));
						row.put(obj.getName(), DateTimeUtil.getShortDateTimeString(DateTimeUtil.getShortDateTime(rs.getString(obj.getName()))));
					} else if (obj.isDouble()) {
//						row.put(obj.getName().toLowerCase(), rs.getDouble(obj.getName()));
						row.put(obj.getName(), rs.getDouble(obj.getName()));
					} else {
//						row.put(obj.getName().toLowerCase(), rs.getString(obj.getName()));
						row.put(obj.getName(), rs.getString(obj.getName()));
					}
				}
				rows.add(row);
			}
		}
	}

}
