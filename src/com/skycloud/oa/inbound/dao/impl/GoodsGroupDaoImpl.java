package com.skycloud.oa.inbound.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.GoodsGroupDao;
import com.skycloud.oa.inbound.dto.CargoGoodsDto;
import com.skycloud.oa.inbound.model.Goods;
import com.skycloud.oa.inbound.model.GoodsGroup;
import com.skycloud.oa.outbound.dto.LadingDto;
import com.skycloud.oa.utils.Common;

@Repository
public class GoodsGroupDaoImpl extends BaseDaoImpl implements GoodsGroupDao {
	private static Logger LOG = Logger.getLogger(GoodsGroupDaoImpl.class);

	/**
	 * 新建货体组
	 * 
	 * @param goodsGroup
	 * @return
	 * @throws OAException
	 */
	@Override
	public Serializable addGoodsGroup(GoodsGroup goodsGroup) throws OAException {

		try {
			
			
			return save(goodsGroup);

		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}

	}

	/**
	 * 获取货体组列表
	 * 
	 * @param cgDto
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 */
	@Override
	public List<Map<String, Object>> getGoodsGroupList(CargoGoodsDto cgDto,
			int start, int limit) throws OAException {
		try {
			String sql = "select * from t_pcs_goods_group where 1=1";
			if (!Common.isNull(cgDto.getGoodsGroupId())) {
				sql += " and id=" + cgDto.getGoodsGroupId();
			}
			if (!Common.isNull(cgDto.getClientId())) {
				sql += " and clientId=" + cgDto.getClientId();
			}
			if (!Common.isNull(cgDto.getProductId())) {
				sql += " and productId=" + cgDto.getProductId();
			}
			if (limit != 0) {
				sql += " limit=" + start + "," + limit;
			}
			return executeQuery(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}

	/**
	 * 更新货体组
	 * 
	 * @param goodsGroup
	 * @throws OAException
	 */
	@Override
	public void updateGoodsGroup(GoodsGroup goodsGroup) throws OAException {

		try {
			String sql = "update t_pcs_goods_group set id=id";
			if (!Common.isNull(goodsGroup.getCode())) {
				sql += " ,code=" + "'" + goodsGroup.getCode() + "'";
			}
			if (!Common.isNull(goodsGroup.getClientid())) {
				sql += " , clientId=" + goodsGroup.getClientid();
			}
			if (!Common.isNull(goodsGroup.getProductid())) {
				sql += " ,=productId" + goodsGroup.getProductid();
			}
			if (!Common.isNull(goodsGroup.getLadingid())) {
				sql += " ,ladingId=" + goodsGroup.getLadingid();
			}
			if (!Common.isNull(goodsGroup.getGoodsInspect())) {
				sql += " ,goodsInspect=" + "'" + goodsGroup.getGoodsInspect()
						+ "'";
			}
			if (!Common.isNull(goodsGroup.getGoodsTank())) {
				sql += " ,goodsTank=" + "'" + goodsGroup.getGoodsTank() + "'";
			}
			if (!Common.isNull(goodsGroup.getGoodsTotal())) {
				sql += " ,goodsTotal=" + "'" + goodsGroup.getGoodsTotal() + "'";
			}
			if (!Common.isNull(goodsGroup.getGoodsIn())) {
				sql += " ,goodsIn=" + "'" + goodsGroup.getGoodsIn() + "'";
			}
			if (!Common.isNull(goodsGroup.getGoodsOut())) {
				sql += " ,goodsOut=" + "'" + goodsGroup.getGoodsOut() + "'";
			}
			if (!Common.isNull(goodsGroup.getGoodsCurrent())) {
				sql += " ,goodsCurrent=" + "'" + goodsGroup.getGoodsCurrent()
						+ "'";
			}
			if (!Common.isNull(goodsGroup.getGoodsInPass())) {
				sql += " ,goodsInPass=" + "'" + goodsGroup.getGoodsInPass()
						+ "'";
			}
			if (!Common.isNull(goodsGroup.getGoodsOutPass())) {
				sql += " ,goodsOutPass=" + "'" + goodsGroup.getGoodsOutPass()
						+ "'";
			}
			sql += " where id=" + goodsGroup.getId();
			executeUpdate(sql);
		} catch (RuntimeException e) {
			LOG.error("dao更新失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新失败", e);
		}
	}
	/**
	 * 获取code相似的最大数量
	 * 
	 * @param code
	 * @return
	 * @throws OAException
	 */
	@Override
	public int getTheSameGoodsGroupCount(String code) throws OAException {
	try{	String codeSql = "select count(*) from t_pcs_goods_group where code like '%"
				+ code + "%'";
		return (int) getCount(codeSql);
	} catch (RuntimeException e) {
		LOG.error("dao查询失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
	}
	}

	/**
	 * 提单撤销，移除货体组与提单的关联
	 * 
	 * @author yanyufeng
	 * @param LadingId
	 * @throws OAException
	 */
	@Override
	public void removeLadingFromGoodsGroup(String ladingId) throws OAException {
		String sql = "update t_pcs_goods_group set ladingId=0 where ladingId in("+ladingId+")";
		execute(sql);
	}

	/**
	 * 添加多个货体ids到 生成新的货体组并发回货体组id
	 * 
	 * @param ids
	 * @return
	 * @throws OAException
	 */
	@Override
	public int addGoodsGroupBygoods(String ids) throws OAException {
		try{
			String sql = "INSERT INTO t_pcs_goods_group (clientId,productId,goodsInspect,goodsTank,goodsTotal,goodsIn,goodsOut,goodsCurrent,goodsInPass,goodsOutPass  )"
				+ " SELECT DISTINCT clientId, productId,SUM(goodsInspect),SUM(goodsTank),SUM(goodsTotal),SUM(goodsTotal),0,SUM(goodsCurrent),SUM(goodsInPass),"
				+ "SUM(goodsOutPass) FROM t_pcs_goods where id in( "
				+ ids
				+ " )";
		return insert(sql);
	} catch (RuntimeException e) {
		LOG.error("dao添加失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加失败", e);
	}
	}
	
	/**
	 * 向货体组添加单个货体
	 * 
	 * @param ids
	 *  @param goodsGroupId
	 * @return
	 * @throws OAException
	 */
	@Override
	public void updateGoodsToGoodsGroup(int goodsId,int goodsGroupId ) throws OAException {
		try{
			//默认被添加的货体不存在在其他货体组里面
			String sql="update t_pcs_goods_group a, t_pcs_goods b SET b.goodsGroupId=a.id ,a.clientId=b.clientId,a.productId=b.productId,a.goodsCurrent=round(b.goodsCurrent+a.goodsCurrent,3),a.goodsIn=round(b.goodsIn+a.goodsIn,3),"
					+ "a.goodsInPass=round(a.goodsInPass+b.goodsInPass,3),a.goodsInspect=round(a.goodsInspect+b.goodsInspect,3),a.goodsOut=0,a.goodsOutPass=round(a.goodsOutPass+b.goodsOutPass,3),"
					+ "a.goodsTank=round(a.goodsTank+b.goodsTank,3),a.goodsTotal=round(a.goodsTotal+b.goodsTotal,3) WHERE 1=1 and  a.id= "+goodsGroupId+" and b.id="+goodsId;
           executeUpdate(sql);
		} catch (RuntimeException e) {
		LOG.error("dao添加失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加失败", e);
	}
	}
	/**
	 * 向货体组批量添加多个个货体
	 * 
	 * @param ids
	 *  @param goodsGroupId
	 * @return
	 * @throws OAException
	 */
	@Override
	public void updateGoodsToGoodsGroup(String ids,int goodsGroupId) throws OAException {
		updateGoodsToGoodsGroup(ids, goodsGroupId,0,0);
	}
	
	@Override
	public void updateGoodsToGoodsGroup(String ids, int goodsGroupId,
			int buyClientId, int ladingType) throws OAException {
		try{
			String sql = "UPDATE t_pcs_goods_group a,(SELECT DISTINCT clientId,productId,SUM(goodsInspect) goodsInspect,SUM(goodsTank) goodsTank,SUM(goodsTotal) goodsTotal,SUM(goodsTotal) goodsIn,"
					+ "SUM(goodsCurrent) goodsCurrent,SUM(goodsInPass) goodsInPass,SUM(goodsOutPass) goodsOutPass FROM t_pcs_goods where id IN ("+ids+")) "
					+ "b set a.clientId=b.clientId,a.productId=b.productId,a.goodsInspect=b.goodsInspect,a.goodsTank=b.goodsTank,a.goodsTotal=b.goodsTotal,a.goodsIn=b.goodsIn,"
					+ "a.goodsCurrent=b.goodsCurrent,a.goodsInPass=b.goodsInPass,a.goodsOutPass=b.goodsOutPass WHERE a.id="+goodsGroupId;
		int i = executeUpdate(sql);
		LOG.debug("======================================= " + i );
    if(buyClientId!=0&&ladingType!=0){
		if(ladingType==1){
			LOG.debug("货体转卖");
			sql="UPDATE t_pcs_goods set goodsGroupId="+goodsGroupId+" , clientId="+buyClientId+" where id in("+ids+")";
		}else{
			LOG.debug("提货权转卖");
			sql="UPDATE t_pcs_goods set goodsGroupId="+goodsGroupId+" , ladingClientId="+buyClientId+" where id in("+ids+")";
		}
		}else{
			sql="UPDATE t_pcs_goods set goodsGroupId="+goodsGroupId+" where id in("+ids+")";
		}
    executeUpdate(sql);
	} catch (RuntimeException e) {
		LOG.error("dao添加失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加失败", e);
	}
		
	}
	
	/**
	 * 向货体组添加货体组
	 * @param goodsGroupIdChild
	 * @param goodsGroupIdMother
	 * @throws OAException
	 */
	@Override
	public void addGoodsGroupToGoodsGroup(int goodsGroupIdChild,
			int goodsGroupIdMother) throws OAException {
		try{
			String sql="update t_pcs_goods_group a, t_pcs_goods_group b SET a.goodsCurrent=round(b.goodsCurrent+a.goodsCurrent,3),a.goodsIn=round(b.goodsIn+a.goodsIn,3),a.goodsInPass=round(a.goodsInPass+b.goodsInPass,3),"
					+ "a.goodsInspect=round(a.goodsInspect+b.goodsInspect,3),a.goodsOutPass=round(a.goodsOutPass+b.goodsOutPass,3),a.goodsTank=round(a.goodsTank+b.goodsTank,3),"
					+ "a.goodsTotal=round(a.goodsTotal+b.goodsTotal,3) WHERE a.id="+goodsGroupIdMother+" and b.id="+goodsGroupIdChild;
			executeUpdate(sql);
			
		} catch (RuntimeException e) {
			LOG.error("dao添加失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加失败", e);
		}
	}
	/**
	 * 从货体组goodsgroupid里移除单个货体goodsid  
	 * @param goods
	 * @throws OAException
	 */
	@Override
	public void removeGoodsFromGoodsGroup(int goodsid ,int goodsGroupid) throws OAException {
		try{
			String sql="update t_pcs_goods a, t_pcs_goods_group b SET a.goodsGroupId=0,b.goodsCurrent=round(b.goodsCurrent-a.goodsCurrent,3),"
					+ "b.goodsIn=round(b.goodsIn-a.goodsIn,3),b.goodsInPass=round(b.goodsInPass-a.goodsInPass,3),b.goodsInspect=round(b.goodsInspect-a.goodsInspect,3),b.goodsOut=round(b.goodsOut+a.goodsTotal,3),"
					+ "b.goodsOutPass=round(b.goodsOutPass-a.goodsOutPass,3),b.goodsTank=round(b.goodsTank-a.goodsTank,3),b.goodsTotal=round(b.goodsTotal-a.goodsTotal,3) "
					+ "WHERE a.id="+goodsid+" and b.id="+goodsGroupid;
			
			executeUpdate(sql);
			
		} catch (RuntimeException e) {
			LOG.error("dao添加失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加失败", e);
		}
	}
	/**
	 * 从货体组goodsgroupid里移除多个货体ids  
	 * @param goods
	 * @throws OAException
	 */
	@Override
	public void removeGoodsFromGoodsGroup(String ids, int goodsGroupid)
			throws OAException {
		try{
			String sql="UPDATE t_pcs_goods_group a,(SELECT DISTINCT SUM(goodsInspect) goodsInspect,	SUM(goodsTank) goodsTank,SUM(goodsTotal) goodsTotal,	"
					+ "SUM(goodsTotal) goodsIn,	SUM(goodsCurrent) goodsCurrent,SUM(goodsInPass) goodsInPass,SUM(goodsOutPass) goodsOutPass FROM t_pcs_goods where id IN ("+ids+")) b set"
					+ "	a.goodsInspect=round(a.goodsInspect-b.goodsInspect,3),a.goodsTank=round(a.goodsTank-b.goodsTank,3),a.goodsTotal=round(a.goodsTotal-b.goodsTotal,3),a.goodsOut=b.goodsIn,"
					+ "	a.goodsCurrent=round(a.goodsCurrent-b.goodsCurrent,3),a.goodsInPass=round(a.goodsInPass-b.goodsInPass,3),	a.goodsOutPass=round(a.goodsOutPass-b.goodsOutPass,3) WHERE a.id="+goodsGroupid;
			executeUpdate(sql);
            sql="UPDATE t_pcs_goods set goodsGroupId=0 where id in("+ids+")";	
            executeUpdate(sql);
		} catch (RuntimeException e) {
			LOG.error("dao添加失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加失败", e);
		}
	}
	/**
	 * 删除货体组
	 * @param goodsGroupId
	 * @throws OAException
	 */
	@Override
	public void deleteGoodsGroup(int goodsGroupId) throws OAException {
		try{
			GoodsGroup goodsGroup=new GoodsGroup();
			goodsGroup.setId(goodsGroupId);
			delete(goodsGroup);
			
		} catch (RuntimeException e) {
			LOG.error("dao删除失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao删除失败", e);
		}
	}
	/**
	 * 删除多个货体组
	 * 
	 * @param ids
	 * @throws OAException
	 */
	@Override
	public void deleteGoodsGroup(String ids) throws OAException {
		try{
			String sql="delete from t_pcs_goods_group where id in ("+ids+")";
			execute(sql);
		} catch (RuntimeException e) {
			LOG.error("dao删除失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao删除失败", e);
		}
	}
	/**
	 * 计算统计要添加到提单货体组的货体
	 * @param dto
	 * @return
	 * @throws OAException
	 */
	public Map<String, Object>  computeLadingGoods(LadingDto dto)throws OAException {
		try{
			String sql="SELECT DISTINCT clientId,productId,SUM(goodsInspect) goodsInspect,SUM(goodsTank) goodsTank,SUM(goodsTotal) goodsTotal,SUM(goodsTotal) goodsIn,"
					+ "SUM(goodsCurrent) goodsCurrent,SUM(goodsInPass) goodsInPass,SUM(goodsOutPass) goodsOutPass FROM t_pcs_goods where id IN ("+dto.getGoodsIds()+")";
			return executeQuery(sql).get(0);
		} catch (RuntimeException e) {
			LOG.error("dao删除失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao删除失败", e);
		}
	}

	@Override
	public void updateGoods(String ids, int goodsGroupId) throws OAException {
		updateGoods(ids, goodsGroupId, 0, 0);
	}
	@Override
   public void updateGoods(String ids, int goodsGroupId,int buyClientId, int ladingType) throws OAException {
   try{	String sql="";
	if(buyClientId!=0&&ladingType!=0){
		if(ladingType==1){
			LOG.debug("货体转卖");
			sql="UPDATE t_pcs_goods set contractId=0, goodsGroupId="+goodsGroupId+" , clientId="+buyClientId+" where id in("+ids+")";
		}else{
			LOG.debug("提货权转卖");
			sql="UPDATE t_pcs_goods set goodsGroupId="+goodsGroupId+" , ladingClientId="+buyClientId+" where id in("+ids+")";
		}
		}else{
			sql="UPDATE t_pcs_goods set goodsGroupId="+goodsGroupId+" where id in("+ids+")";
		}
    executeUpdate(sql);
} catch (RuntimeException e) {
	LOG.error("dao删除失败");
	throw new OAException(Constant.SYS_CODE_DB_ERR, "dao删除失败", e);
}
}
	
}
