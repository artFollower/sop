package com.skycloud.oa.inbound.service;

import java.util.List;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.CargoDto;
import com.skycloud.oa.inbound.dto.CargoGoodsDto;
import com.skycloud.oa.inbound.model.Cargo;
import com.skycloud.oa.inbound.model.Goods;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 * 货批与货体
 * 
 * @author jiahy
 *
 */
public interface CargoGoodsService {
	/**
	 * 根据条件获取货批列表
	 * 
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg getCargoList(CargoGoodsDto agDto, PageView pageView,boolean isGetGood)
			throws OAException;

	/**
	 * 根据条件获取货体列表
	 * 
	 * @param agDto
	 * @param pageView
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg getGoodsList(CargoGoodsDto agDto, PageView pageView)
			throws OAException;

	/**
	 * 获取单个货批的详情信息（包括货体）
	 * 
	 * @param agDto
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg getCargoGoodsDetail(CargoGoodsDto agDto)
			throws OAException;

	/**
	 * 拆分货体，生成新的货体，更新被拆分的货体
	 * @param code 
	 * 
	 * @param goodsnew
	 * @param goodsold
	 * @param buyClientId 
	 * @param ladingType 
	 * @param type 
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg splitGoods(int ladingId,String code, List<Goods> goodsnew, List<Goods> goodsold, String buyClientId, int ladingType)
			throws OAException;

	/**
	 * 拆分货体，生成新的货体，更新被拆分的货体，关联现有提单货体组
	 * @param code 
	 * @param goodGroupId 
	 * 
	 * @param goodsnew
	 * @param goodsold
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg splitupdateGoods(String id,String code, List<Goods> goodsNewList, List<Goods> goodsOldList, String goodGroupId)
			throws OAException;

	
	/**
	 * 合并货体，将多个货体合并成货体组
	 * 
	 * @param goodsfir
	 * @param goodssec
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg mergeGoods(List<Goods> ltgoods) throws OAException;
	
	/**
	 * 合并货体，将多个原始货体合并成新原始货体
	 * @author yanyufeng
	 * @param ids
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg mergeGoodsForNewGood(String ids) throws OAException;
	
	/**
	 * 原始货体拆分，全部生成新的原始货体
	 * @author yanyufeng
	 * @param oldGoodsId
	 * @param newGoodsList
	 * @return
	 * @throws OAException
	 * 2015-3-2 上午11:21:21
	 */
	public abstract OaMsg sqlitOriginalGoods(String oldGoodsId,Goods newGoods)throws OAException;
	
	
	/**
	 * 原始货体拆分，减量
	 * @author yanyufeng
	 * @param oldGoodsId
	 * @param newGoodsList
	 * @return
	 * @throws OAException
	 * 2015-3-2 上午11:21:21
	 */
	public abstract OaMsg sqlitOriginalGoods2(String oldGoodsId,Goods newGoods)throws OAException;
	
	
	
	/**
	 * 货体管理--货体拆分
	 * @author yanyufeng
	 * @param oldGoodsId
	 * @param goods
	 * @return
	 * 2015-4-10 下午1:30:44
	 */
	public abstract OaMsg sqlitPassGoods(int oldGoodsId, Goods goods) throws OAException;

	/**
	 * 合并预入库货体
	 * @author yanyufeng
	 * @param predictGoodsId
	 * @param goodsId
	 * @return
	 * @throws OAException
	 * 2015-3-3 下午9:05:49
	 */
	public abstract OaMsg mergePredict(String predictGoodsId,String goodsId)throws OAException;
	
	/**
	 * 预入库货体
	 * @author yanyufeng
	 * @param cargoId
	 * @param goodsTotal
	 * @param tankId 
	 * @return
	 * @throws OAException
	 * 2015-3-3 下午3:44:38
	 */
	public abstract OaMsg predictGoods(String goodsId,String cargoId,String goodsTotal, String tankId )throws OAException;
	
	
	/**
	 * 更新货体代码
	 * @author yanyufeng
	 * @param code
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg updateGoodsCode(int cargoId,String code) throws OAException;
	

	/**
	 * 更新提单里货体代码
	 * @author yanyufeng
	 * @param ladingId
	 * @return
	 * @throws OAException
	 * 2015-4-10 下午5:17:58
	 */
	public abstract Object updateGoodsCode(int ladingId)throws OAException;

	/**
	 * 更新货批表（如果修改了与货体相关修改货体）
	 * 
	 * @param cargo
	 * @param ischangegoods是否修改货体
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg updateCargo(CargoDto cargoDto	, boolean ischangegoods)
			throws OAException;

	/**
	 * 更新货体表（如果修改了相关货批的修改货批）
	 * 
	 * @param goods
	 * @param ischangecargo是否自动修改货批
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg updateGoods(Goods goods, boolean ischangecargo,boolean isconfirm)
			throws OAException;

	/**
	 * 查询预入库货体
	 * @author yanyufeng
	 * @param cargoDto
	 * @param pageView
	 * @return
	 * @throws OAException
	 * 2015-3-4 下午4:57:48
	 */
	public abstract Object getPredictGoods(CargoGoodsDto cargoDto,
			PageView pageView)throws OAException;

	/**
	 * 删除货体
	 * @author yanyufeng
	 * @param ids
	 * @return
	 * @throws OAException
	 * 2015-3-4 下午4:57:56
	 */
	public abstract Object deleteGoods(String ids)throws OAException;

	/**
	 * 货批拆分
	 * @author yanyufeng
	 * @param cargoId 货批id
	 * @param goodsIdList 要拆分的货体id
	 * @param goodsCount  要拆分的货体对应的数量
	 * @param cargoCount  要拆分出的货批数量
	 * @param clientId 
	 * @param isCreateGoods  是否同步生成货体
	 * @return
	 * 2015-3-5 上午11:40:02
	 */
	public abstract Object sqlitCargo(String clientId,String cargoId, String goodsIdList,
			String goodsCount, String cargoCount, int isCreateGoods)throws OAException;

	/**
	 * 查询原始货体列表
	 * @author yanyufeng
	 * @param cargoDto
	 * @param pageView
	 * @return
	 * @throws OAException
	 * 2015-3-11 下午1:03:59
	 */
	public abstract Object getOriginalGoodsList(CargoGoodsDto cargoDto,
			PageView pageView)throws OAException;
	
	
	/**
	 * 根据id查询货体
	 * @author yanyufeng
	 * @param id
	 * @return
	 * @throws OAException
	 * 2015-6-2 下午8:47:21
	 */
	public abstract Object getGoodsById(CargoGoodsDto cargoDto)throws OAException;

	/**
	 * 更新货批
	 * @author yanyufeng
	 * @param cargo
	 * @throws OAException
	 * 2015-7-29 下午4:52:05
	 */
	public abstract void updateCargo(Cargo cargo)throws OAException;

	/**
	 * 更新货体
	 * @author yanyufeng
	 * @param goods
	 * @throws OAException
	 * 2015-8-2 下午4:46:01
	 */
	public abstract void updateGoods(Goods goods)throws OAException;

	/**
	 * 变更货体入库量
	 * @author yanyufeng
	 * @param goodsId
	 * @param goodsTotal
	 * @param isPredict 
	 * @return 
	 * @throws OAException
	 * 2015-9-16 下午5:18:30
	 */
	public abstract OaMsg editGoodsTotal(Integer goodsId, String goodsTotal, Integer isPredict)throws OAException;

	/**
	 * 根据客户查找货体
	 * @author yanyufeng
	 * @param cargoDto
	 * @param pageView
	 * @return
	 * @throws OAException
	 * 2015-12-3 下午8:50:43
	 */
	public abstract OaMsg clientGoods(CargoGoodsDto cargoDto, PageView pageView)throws OAException;

	/**
	 * 货体扣损
	 * @author yanyufeng
	 * @param goodsId
	 * @param lossCount
	 * @return
	 * 2015-12-22 上午11:34:33
	 */
	public abstract OaMsg goodsLoss(Integer goodsId, String lossCount)throws OAException;

	public abstract OaMsg getCargoByClientId(int clientId)throws OAException;

	public abstract OaMsg getClientByCargoId(int cargoId)throws OAException;

	public abstract OaMsg clientGoodstotal(CargoGoodsDto cargoDto)throws OAException;

	public abstract Object getInspectTotal(CargoGoodsDto cargoDto)throws OAException;

	public abstract Object deleteCargo(int cargoId)throws OAException;
	
	

}
