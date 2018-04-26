package com.skycloud.oa.inbound.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.CargoGoodsDto;
import com.skycloud.oa.inbound.dto.InboundOperationDto;
import com.skycloud.oa.inbound.model.Arrival;
import com.skycloud.oa.inbound.model.Cargo;
import com.skycloud.oa.inbound.model.Goods;
import com.skycloud.oa.inbound.model.Cargo;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.statistics.dto.StatisticsDto;

public interface CargoGoodsDao {
	/**
	 * 查询同一个提单下的货体数量
	 * 
	 * @author yanyufeng
	 * @param cargo
	 */
	public int getTheSameGoodsCount(String code);
	/**
	 * 在货批表插入相应数据
	 * 
	 * @author yanyufeng
	 * @param cargo
	 */
	public int addIntoCargo(Cargo cargo)throws OAException;

	/**
	 * 添加货体表
	 * 
	 * @param goods
	 * @throws OAException
	 */
	public Serializable addGoods(Goods goods) throws OAException;

	/**
	 * 获取货批列表信息
	 * 
	 * @param agDto
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 */
	public List<Map<String, Object>> getCargoList(CargoGoodsDto agDto,
			int start, int limit) throws OAException;

	/**
	 * 获取货体列表信息
	 * 
	 * @param agDto
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 */
	public List<Map<String, Object>> getGoodsList(CargoGoodsDto agDto,
			int start, int limit) throws OAException;
	
	/**
	 * 获取货批数量
	 * 
	 * @param agDto
	 * @return
	 * @throws OAException
	 */
	public int getCargoCount(CargoGoodsDto agDto) throws OAException;

	/**
	 * 获取货体数量
	 * 
	 * @param agDto
	 * @return
	 * @throws OAException
	 */
	public int getGoodsCount(CargoGoodsDto agDto) throws OAException;

	/**
	 * 更新货批
	 * 
	 * @param cargo
	 * @throws OAException
	 */
	public void updateCargo(Cargo cargo) throws OAException;

	/**
	 * 更新货体
	 * 
	 * @param goods
	 * @throws OAException
	 */
	public void updateGoods(Goods goods) throws OAException;

	/**
	 * 获取指定货批与货体的关联数据
	 * 
	 * @param agDto
	 * @return
	 * @throws OAException
	 */
	public List<Map<String, Object>> getAppointCargoGoodsData(int id)
			throws OAException;

	/**
	 * 获得相同合同的货批数量
	 * 
	 * @author yanyufeng
	 * @param code
	 * @return
	 * @throws OAException
	 */
	public int getTheSameCargo(String code,boolean isLike) throws OAException;

	/**
	 * 获得相同合同的货体数量
	 * 
	 * @param code
	 * @return
	 * @throws OAException
	 */
	public int getTheSameGoods(String code) throws OAException;
	
	
	/**
	 * 货体拆分，获得相同合同的货体数量
	 * 
	 * @param code
	 * @return
	 * @throws OAException
	 */
	public int getTheSameGoods(String code,Integer oldGoodsId) throws OAException;
	

	/**
	 * 删除货体
	 * @param ids
	 */
	public void deleteGoods(String ids) throws OAException;
	/**根据货批删除货体
	 * @param cargoId
	 */
	public void deleteGoodsByCargoId(Integer cargoId)throws OAException;
	

	
	/**
	 * 获取货批汇总
	 * @author Administrator
	 * @param sDto
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 * 2015-3-23 下午4:22:00
	 */
	public List<Map<String, Object>> getCargo(StatisticsDto sDto,
			int start, int limit) throws OAException;
	
	/**
	 * 获取货批汇总数量
	 * @author Administrator
	 * @param sDto
	 * @return
	 * @throws OAException
	 * 2015-3-23 下午4:22:46
	 */
	public int getCargoCount(StatisticsDto sDto)throws OAException;
	
	/**
	 * 获取货体汇总数量
	 * @author yanyufeng
	 * @param sDto
	 * @param isShowAll 
	 * @return
	 * @throws OAException
	 * 2015-3-24 下午8:09:35
	 */
	public int getGoodsCount(StatisticsDto sDto, boolean isShowAll)throws OAException;
	/**
	 *根据arrivalId清空
	 *@author jiahy
	 * @param id
	 * @param b 
	 */
	public void cleanGoods(int id, boolean b)throws OAException;
	/**
	 *根据arrivalId清空productid
	 *@author jiahy
	 * @param id
	 * @param productId
	 * @param b
	 */
	public void cleanGoods(int id, Integer productId, boolean b)throws OAException;
	/**
	 *根据arrivalId清空productid
	 *@author jiahy
	 * @param id
	 * @param productId
	 * @param b
	 */
	public void cleanCargo(Integer id)throws OAException;
	/**
	 *根据arrivalId清空productid
	 *@author jiahy
	 * @param id
	 * @param productId
	 * @param b
	 */
	public void cleanCargo(Integer id, Integer productId)throws OAException;
	
	
	public List<Map<String, Object>>  getGoodsTankCodesByCargoId(int cargoId)throws OAException;
	public void updateCargoAll(Integer cargoId)throws OAException;
	/**
	 * 查询货体待提量
	 * @author yanyufeng
	 * @param goodsWaitId
	 * @return
	 * @throws OAException
	 * 2015-9-23 下午3:18:03
	 */
	public List<Map<String, Object>> getGoodsWait(int goodsWaitId)throws OAException;
	
	
	/**
	 * 删除货批
	 * @author yanyufeng
	 * @param cargoId
	 * @throws OAException
	 * 2015-10-12 下午6:20:23
	 */
	public void deleteCargo(int cargoId)throws OAException;
	
	
	/**
	 * 
	 * 校验货批号
	 * @author yanyufeng
	 * @param code
	 * @return
	 * @throws OAException
	 * 2015-10-14 下午4:58:01
	 */
	public int checkCargoCode(String code)throws OAException;
	/**
	 *@author jiahy
	 * @param ioDto
	 * @return
	 */
	public Map<String, Object> getTotalGoodsTank(InboundOperationDto ioDto)throws OAException;
	
	/**
	 * 获得货体已经扣损的量
	 * @author yanyufeng
	 * @param goodsId
	 * @return
	 * @throws OAException
	 * 2016-1-6 下午12:46:06
	 */
	public Map<String, Object> getGoodsLossedCount(Integer goodsId)throws OAException;
	public List<Map<String, Object>> getClientByCargoId(int cargoId)throws OAException;
	public List<Map<String, Object>> getCargoByClientId(int clientId)throws OAException;
	public int checkNo(String no)throws OAException;
	public List<Map<String, Object>> getCargoInspectTotal(CargoGoodsDto cargoDto)throws OAException;
	

}
