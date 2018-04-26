package com.skycloud.oa.outbound.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.outbound.dto.GoodsDto;
import com.skycloud.oa.outbound.dto.LadingDto;
import com.skycloud.oa.outbound.model.Lading;

public interface LadingDao {

	/**
	 * 新建提单
	 * @author yanyufeng
	 * @param lading
	 * @return
	 */
	public int addLading(Lading lading)throws OAException;
	
	/**
	 * 删除提单
	 * @author yanyufeng
	 * @param LadingId
	 */
	public void deleteLading(String LadingId)throws OAException;
	
	/**
	 * 修改提单
	 * @author yanyufeng
	 * @param lading
	 */
	public void updateLading(Lading lading)throws OAException;
	
	/**
	 * 获取提单
	 * @author yanyufeng
	 * @param ladingDto
	 * @return
	 */
	public List<Map<String,Object>> getLadingList(LadingDto ladingDto,int start,int limit)throws OAException;
	
	public List<Map<String,Object>> getLadingByGoodsId(LadingDto ladingDto)throws OAException;
	
	/**
	 * 获取提单数量
	 * @author yanyufeng
	 * @param ladingDto
	 * @return
	 */
	public int getLadingListCount(LadingDto ladingDto)throws OAException;
	
	/**
	 * 根据提单id查询该提单所有货体
	 * @author yanyufeng
	 * @param ladingId
	 * @return
	 */
	public List<Map<String,Object>> getGoodsListByLadingId(int ladingId)throws OAException;

	/**
	 * 改变提单状态
	 * @author yanyufeng
	 * @param ladingId
	 */
	public void updateLadingStatus(String ladingId,int status)throws OAException;
	
	public List<Map<String,Object>> getTreeGoodsList(String rIds,GoodsDto goodsDto)throws OAException;
	
	/**
	 * 查询货体流水
	 * @author yanyufeng
	 * @param goodsId
	 * @return
	 * @throws OAException
	 * 2015-2-27 下午8:10:13
	 */
	public List<Map<String,Object>> getGoodsInvoice(String goodsId)throws OAException;

	public List<Map<String,Object>> getTreeCargoList(String cargoId)throws OAException;

	/**
	 * 校验提单代码
	 * @author yanyufeng
	 * @param code
	 * @param clientId 
	 * @return
	 * @throws OAException
	 * 2015-9-11 下午2:43:17
	 */
	public List<Map<String,Object>> checkCode(String code, int clientId)throws OAException;

	/**
	 * 查找货体流转图的所有原号
	 * @author yanyufeng
	 * @param goodsDto
	 * @return
	 * @throws OAException
	 * 2015-9-24 下午2:30:28
	 */
	public List<Map<String, Object>> getTreeRootIds(GoodsDto goodsDto)throws OAException;

	/**
	 *@author jiahy
	 * @param string
	 * @return
	 */
	public Map<String, Object>  getGoodsWaitByLadingId(String ladingId) throws OAException;

	/**
	 * 修复提单
	 * @author yanyufeng
	 * @param ladingId
	 * @throws OAException
	 * 2015-12-22 下午4:52:40
	 */
	public void repairLading(String ladingId)throws OAException;
	
	
	public Map<String, Object>  getGoodsLossByLadingId(String ladingId) throws OAException;

	public void updateladingNo(String id, String no)throws OAException;

	public void realDeleteLading(String ladingId)throws OAException;

	public List<Map<String, Object>> getCheckEndTimeLadingIds(int id)throws OAException;

	public List<Map<String, Object>> getCheckEndTime(String ids)throws OAException;

	public  Map<String, Object> checkNo(String no)throws OAException;

}
