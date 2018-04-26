package com.skycloud.oa.inbound.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.ExceedFeeDto;
import com.skycloud.oa.inbound.model.ExceedFee;

/**
 *超期费dao
 * @author 作者:jiahy
 * @version 时间：2015年5月11日 下午10:34:32
 */
public interface ExceedFeeDao {


/**
 *获取超期费的货批列表
 *@author jiahy
 * @param eFeeDto
 * @param startRecord
 * @param maxresult
 * @return
 * @throws OAException
 */
public List<Map<String, Object>> getExceedFeeCargoList(ExceedFeeDto eFeeDto,int startRecord, int maxresult) throws OAException;

/**
 *获取超期费的货批的数量
 *@author jiahy
 * @param eFeeDto
 * @return
 * @throws OAException
 */
public int getExceedFeeCargoCount(ExceedFeeDto eFeeDto) throws OAException;

/**
 *获取超期费的提单列表
 *@author jiahy
 * @param eFeeDto
 * @param startRecord
 * @param maxresult
 * @return
 * @throws OAException
 */
public List<Map<String, Object>> getExceedFeeLadingList(ExceedFeeDto eFeeDto, int startRecord, int maxresult) throws OAException;

/**
 *获取超期费的提单数量
 *@author jiahy
 * @param eFeeDto
 * @return
 * @throws OAException
 */
public int getExceedFeeLadingCount(ExceedFeeDto eFeeDto) throws OAException;
	
	
/**
 *获取超期费列表
 *@author jiahy
 * @param eFeeDto
 * @param start
 * @param limit
 * @return
 * @throws OAException
 */
public List<Map<String, Object>> getExceedFeeList(ExceedFeeDto eFeeDto ,int start,int limit) throws OAException;	

/**
 *获取超期费列表数量
 *@author jiahy
 * @param eFeeDto
 * @return
 * @throws OAException
 */
public int getExceedFeeCount(ExceedFeeDto eFeeDto) throws OAException;

/**
 *添加超期费
 *@author jiahy
 * @param exceedFee
 * @return
 * @throws OAException
 */
public int addExceedFee(ExceedFee exceedFee) throws OAException;

/**
 *更新超期费
 *@author jiahy
 * @param exceedFee
 * @throws OAException
 */
public void updateExceedFee(ExceedFee exceedFee) throws OAException;

/**
 *删除超期费
 *@author jiahy
 * @param eFeeDto
 * @throws OAException
 */
public void deleteExceedFee(ExceedFeeDto eFeeDto) throws OAException;

/**
 *获取超期费编号
 *@author jiahy
 * @param eFeeDto
 * @return
 * @throws OAException
 */
public Map<String,Object> getCodeNum(ExceedFeeDto eFeeDto) throws OAException;

/**
 *获取超期货批每天记录
 *@author jiahy
 * @param eFeeDto
 * @param startRecord
 * @param maxresult
 * @return
 */
public List<Map<String, Object>> getExceedFeeCargoItemList(ExceedFeeDto eFeeDto) throws OAException;

/**
 *获取超期提单每天记录
 *@author jiahy
 * @param eFeeDto
 * @return
 * @throws OAException
 */
public List<Map<String, Object>> getExceedFeeLadingItemList(ExceedFeeDto eFeeDto) throws OAException;

/**
 * 根据超期结算单id，获取入库船信
 *@author jiahy
 * @param exceedId
 * @return
 * @throws OAException
 */
public List<Map<String,Object>> getInboundMsgListByExceedId(int exceedId) throws OAException;

/**
 * 结清超期货批
 *@author jiahy
 * @param eFeeDto
 */
public void cleanLading(ExceedFeeDto eFeeDto) throws OAException;

/**
 * 结清超期提单
 *@author jiahy
 * @param eFeeDto
 */
public void cleanCargo(ExceedFeeDto eFeeDto) throws OAException;

/**
 *@author jiahy
 * @param valueOf
 */
public List<Map<String,Object>> getInboundMsgListByLadingId(int ladingId) throws OAException;
/**
 * 根据货批号获取入库船信
 *@author jiahy
 * @param cargoId
 * @return
 * @throws OAException
 */
public List<Map<String, Object>> getInboundMsgListByCargoId(int cargoId) throws OAException;

/**
 *@author jiahy
 * @param id
 */
public void backStatus(Integer id) throws OAException;

/**
 * @Title getExceedFeeMsg
 * @Descrption:TODO
 * @param:@param eDto
 * @param:@return
 * @return:Map<String,Object>
 * @auhor jiahy
 * @date 2016年8月15日下午7:21:50
 * @throws
 */
public Map<String, Object> getExceedFeeMsg(ExceedFeeDto eDto) throws OAException;
}
