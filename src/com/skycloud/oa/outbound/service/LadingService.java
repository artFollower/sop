package com.skycloud.oa.outbound.service;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.CargoGoodsDto;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dto.GoodsDto;
import com.skycloud.oa.outbound.dto.LadingDto;
import com.skycloud.oa.outbound.model.Lading;
import com.skycloud.oa.utils.OaMsg;

public interface LadingService {

	/**
	 * 新建提单
	 * @author yanyufeng
	 * @param lading
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg addLading(Lading lading) throws OAException;
	
	/**
	 * 删除提单
	 * @author yanyufeng
	 * @param ladingId
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg deleteLading(String ladingId)throws OAException;
	
	/**
	 * 更新提单
	 * @author yanyufeng
	 * @param lading
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg updateLading(Lading lading)throws OAException;
	
	/**
	 * 获取提单列表
	 * @author yanyufeng
	 * @param ladingDto
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg getLadingList(LadingDto ladingDto,PageView pageView)throws OAException;
	
	
	/**
	 * 获取提单下的货体
	 * @author yanyufeng
	 * @param ladingId
	 * @param oaMsg
	 * @return
	 */
	public abstract OaMsg getLadingGoods(int ladingId,OaMsg oaMsg);
	/**
	 * 新建提单的货体组
	 * @param ladingDto
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg addLadingGoodsGroup(LadingDto ladingDto) throws OAException;
	
	/**
	 * 更新货体
	 * @author yanyufeng
	 * @param ladingDto
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午3:25:55
	 */
	public abstract OaMsg updateGoods(LadingDto ladingDto) throws OAException;
	/**
	 * 更新提单的货体组
	 * @param ladingDto
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg updateLadingGoodsGroup(LadingDto ladingDto) throws OAException;
	/**
	 * 从提单还原货体，并删除货体
	 * @param ids
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg removeLadingGoods(String ids) throws OAException;
	
	/**
	 * 余量退回
	 * @param ids
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg backLading(String ladingId) throws OAException;

	/**
	 * 提单扣损
	 * @author yanyufeng
	 * @param ladingId
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg lossLading(String ladingId)throws OAException;

	/**
	 * 撤销单个货体（退回给原始货体）
	 * @author yanyufeng
	 * @param id
	 * @param ladingId 
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg removeGood(String id, String ladingId)throws OAException;
	
	/**
	 * 获得树状结构
	 * @author yanyufeng
	 * @param goodsDto
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg getGoodsTree(GoodsDto goodsDto)throws OAException;
	
	/**
	 * 货体放行
	 * @author yanyufeng
	 * @param ladingId
	 * @param goodsId
	 * @param count
	 * @param contractId 
	 * @param goodsTotal 
	 * @return
	 * @throws OAException
	 * 2015-2-27 下午6:07:43
	 */
	public abstract OaMsg goodsPass(String ladingId,String goodsId,String count, String contractId, String goodsTotal)throws OAException;
	
	/**
	 * 查询货体流水
	 * @author yanyufeng
	 * @param goodsId
	 * @return
	 * @throws OAException
	 * 2015-2-27 下午8:01:25
	 */
	public abstract OaMsg getGoodsLS(String goodsId)throws OAException;

	/**
	 * 更新提单文件
	 * @author yanyufeng
	 * @param id
	 * @param url
	 * 2015-3-27 下午4:11:03
	 */
	public abstract OaMsg updateLading(int id, String url)throws OAException;

	public abstract OaMsg getCargoTree(String cargoId)throws OAException;

	/**
	 * @author yanyufeng
	 * @param goodsId
	 * @return
	 * 2015-9-3 下午8:17:50
	 */
	public abstract Object getGoods(CargoGoodsDto cargoGoodsDto)throws OAException;

	/**
	 * 校验提单号
	 * @author yanyufeng
	 * @param code
	 * @param clientId 
	 * @return
	 * @throws OAException
	 * 2015-9-11 下午2:32:12
	 */
	public abstract Object checkCode(String code, int clientId)throws OAException;

	public abstract Object updateLadingNo(String id, String no)throws OAException;

	/**
	 * 提单冲销
	 * @author yanyufeng
	 * @param ladingId
	 * @return
	 * @throws OAException
	 * 2016-2-18 下午3:10:39
	 */
	public abstract Object againstLading(String ladingId)throws OAException;
	
}
