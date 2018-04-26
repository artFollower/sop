package com.skycloud.oa.inbound.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.CargoGoodsDto;
import com.skycloud.oa.inbound.model.Goods;
import com.skycloud.oa.inbound.model.GoodsGroup;
import com.skycloud.oa.outbound.dto.LadingDto;

public interface GoodsGroupDao {

	/**
	 * 新建货体组
	 * 
	 * @param goodsGroup
	 * @return
	 * @throws OAException
	 */
	public Serializable addGoodsGroup(GoodsGroup goodsGroup) throws OAException;

	/**
	 * 获取货体组列表
	 * 
	 * @param cgDto
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 */
	public List<Map<String, Object>> getGoodsGroupList(CargoGoodsDto cgDto,
			int start, int limit) throws OAException;

	/**
	 * 更新货体组
	 * 
	 * @param goodsGroup
	 * @throws OAException
	 */
	public void updateGoodsGroup(GoodsGroup goodsGroup) throws OAException;
	/**
	 * 获取code相似的最大数量
	 * 
	 * @param code
	 * @return
	 * @throws OAException
	 */
	public int getTheSameGoodsGroupCount(String code) throws OAException;

	/**
	 * 提单撤销，移除货体组与提单的关联
	 * 
	 * @author yanyufeng
	 * @param LadingId
	 * @throws OAException
	 */
	public void removeLadingFromGoodsGroup(String ladingId) throws OAException;

	/**
	 * 添加多个货体ids到 生成新的货体组并发回货体组id
	 * 
	 * @param ids
	 * @return
	 * @throws OAException
	 */
	public int addGoodsGroupBygoods(String ids) throws OAException;
	/**
	 * 向货体组添加单个货体
	 * @param cgDto
	 * @throws OAException
	 */
	public void updateGoodsToGoodsGroup(int goodsId,int goodsGroupId ) throws OAException;
	/**
	 *向货体组批量添加多个个货体
	 * 
	 * @param ids
	 *  @param goodsGroupId
	 * @return
	 * @throws OAException
	 */
	public void updateGoodsToGoodsGroup(String ids, int goodsGroupId) throws OAException;
	
	public void updateGoodsToGoodsGroup(String ids, int goodsGroupId,int buyClientId,int ladingType) throws OAException;
	/**
	 * 向货体组添加货体组
	 * @param goodsGroupIdChild
	 * @param goodsGroupIdMother
	 * @throws OAException
	 */
	public void addGoodsGroupToGoodsGroup(int goodsGroupIdChild,int goodsGroupIdMother) throws OAException;
	/**
	 * 从货体组goodsgroupid里移除单个货体goodsid  
	 * @param goods
	 * @throws OAException
	 */
	public void removeGoodsFromGoodsGroup(int goodsid,int goodsGroupid) throws OAException;
	/**
	 * 从货体组goodsgroupid里移除多个货体goodsid  
	 * @param goods
	 * @throws OAException
	 */
	public void removeGoodsFromGoodsGroup(String ids,int goodsGroupid) throws OAException;
	/**
	 * 删除货体组
	 * @param goodsGroupId
	 * @throws OAException
	 */
	public void deleteGoodsGroup(int goodsGroupId) throws OAException;
	/**
	 * 删除货体组
	 * 
	 * @param ids
	 * @throws OAException
	 */
	public void deleteGoodsGroup(String ids) throws OAException;

	/**
	 * 计算统计要添加到提单货体组的货体
	 * @param dto
	 * @return
	 * @throws OAException
	 */
	public Map<String, Object>  computeLadingGoods(LadingDto dto)throws OAException;
	
	public void updateGoods(String ids ,int goodsGroupId)throws OAException;
	public void updateGoods(String ids ,int goodsGroupId,int buyClientId,int ladingType)throws OAException;
}
