package com.skycloud.oa.outbound.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dto.GoodsLogDto;
import com.skycloud.oa.outbound.model.GoodsLog;
import com.skycloud.oa.statistics.dto.StatisticsDto;

/**
 * 
 * <p>出库管理-发货开票</p>
 * @ClassName:GoodsLogDao
 * @Description:
 * @Author:chenwj
 * @Date:2015年5月27日 下午8:56:48
 *
 */
public interface GoodsLogDao 
{
	/**
	 * 获取发货开票列表
	 *@author jiahy
	 * @param invoiceDto
	 * @param pageView
	 * @return
	 * @throws OAException
	 */
	public List<Map<String,Object>> getInvoiceList(GoodsLogDto invoiceDto,PageView pageView)throws OAException;
	/**获取发货开票列表数量
	 *@author jiahy
	 * @param invoiceDto
	 * @return
	 * @throws OAException
	 */
	public int getInvoiceListCount(GoodsLogDto invoiceDto)throws OAException;
	/**
	 * 通过id获取开票信息
	 * @Title:GoodsLogDao
	 * @Description:
	 * @param id
	 * @return
	 * @throws OAException
	 * @Date:2015年5月27日 下午8:58:14
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String,Object>> get(String id) throws OAException;
	
	/**
	 * 
	 * @Title:GoodsLogDao
	 * @Description:
	 * @param goodsLog
	 * @return
	 * @throws OAException
	 * @Date:2015年5月27日 下午8:58:20
	 * @return :int 
	 * @throws
	 */
	public int add(GoodsLog goodsLog) throws OAException;
	
	/**
	 * 检查开票量是否超过可提量
	 * @Title:GoodsLogDao
	 * @Description:
	 * @param goodsLog
	 * @return
	 * @throws OAException
	 * @Date:2015年5月27日 下午8:58:31
	 * @return :int 
	 * @throws
	 */
	public int checkCanDeliveryNum(GoodsLog goodsLog) throws OAException; 
	
	/**
	 * 
	 * @Title:GoodsLogDao
	 * @Description:
	 * @param goodsLog
	 * @return
	 * @throws OAException
	 * @Date:2015年5月27日 下午8:58:46
	 * @return :int 
	 * @throws
	 */
	public int updateGoodsNum(GoodsLog goodsLog) throws OAException;
	
	/**
	 * 
	 * @Title:GoodsLogDao
	 * @Description:
	 * @param ids
	 * @throws OAException
	 * @Date:2015年5月27日 下午8:58:54
	 * @return :void 
	 * @throws
	 */
	public void delete(String ids) throws OAException ;
	
	/**
	 * 
	 * @Title:GoodsLogDao
	 * @Description:
	 * @param goodsLog
	 * @throws OAException
	 * @Date:2015年5月27日 下午8:59:02
	 * @return :void 
	 * @throws
	 */
	public void changeInvoice(GoodsLog goodsLog) throws OAException ;
	
	/**
	 * 
	 * @Title:GoodsLogDao
	 * @Description:
	 * @param goodsLog
	 * @throws OAException
	 * @Date:2015年5月27日 下午8:59:10
	 * @return :void 
	 * @throws
	 */
	public void up(GoodsLog goodsLog) throws OAException;
	
	/**
	 * 
	 * @Title:GoodsLogDao
	 * @Description:
	 * @param goodsLog
	 * @throws OAException
	 * @Date:2015年5月27日 下午8:59:16
	 * @return :void 
	 * @throws
	 */
	public void updateGoodsCurrentByActualNum(GoodsLog goodsLog) throws OAException ;
	
	/**
	 * 
	 * @Title:GoodsLogDao
	 * @Description:
	 * @param goodsLog
	 * @throws OAException
	 * @Date:2015年5月27日 下午8:59:24
	 * @return :void 
	 * @throws
	 */
	public void upChangeValue(GoodsLog goodsLog) throws OAException ;
	
	/**
	 * 
	 * @Title:GoodsLogDao
	 * @Description:
	 * @param goodsLogDto
	 * @throws OAException
	 * @Date:2015年5月27日 下午8:59:32
	 * @return :void 
	 * @throws
	 */
	public void updatePrintType(GoodsLogDto goodsLogDto)throws OAException ;
	
	/**
	 * 查询货体流水表
	 * @author yanyufeng
	 * @param goodsId
	 * @return
	 * @throws OAException
	 * 2015-2-28 下午7:59:38
	 */
	public List<Map<String,Object>> getGoodsLog(String goodsId)throws OAException;
	
	/**
	 * 查询最后的货体记录
	 * @author Administrator
	 * @param goodsId
	 * @param time
	 * @return
	 * @throws OAException
	 * 2015-3-24 上午11:16:08
	 */
	public List<Map<String,Object>> getLastGoodsLog(String goodsId,Long time)throws OAException;
	
	/**
	 * 查询当日货体记录
	 * @author Administrator
	 * @param goodsId
	 * @param time
	 * @return
	 * @throws OAException
	 * 2015-3-24 上午11:16:08
	 */
	public List<Map<String,Object>> getTodayLog(String goodsId,Long time)throws OAException;
	
	/**
	 * 根据时间段查询货体日志
	 * @author yanyufeng
	 * @param showVirTime 
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws OAException
	 * 2015-3-24 下午5:33:01
	 */
	public List<Map<String,Object>> getGoodsLog(Integer showVirTime, String goodsId,Long startTime,Long endTime,int type)throws OAException;
	
	/**
	 * 查询一段时间内有过操作的货体
	 * @author yanyufeng
	 * @param startTime
	 * @param endTime
	 * @param limit 
	 * @param start 
	 * @return
	 * @throws OAException
	 * 2015-3-24 下午5:50:24
	 */
	public List<Map<String,Object>> getGoods(StatisticsDto sDto, int start, int limit,boolean isAll)throws OAException;
	
	/**
	 * 删除某一货体的所有日志
	 * @author yanyufeng
	 * @param goodsIds
	 * @throws OAException
	 * 2015-4-10 下午5:30:31
	 */
	public void deleteGoodsLog(String goodsIds)throws OAException;
	/**
	 *回退清空goodslog
	 *@author jiahy
	 * @throws OAException
	 */
	public void cleanToStatus(int id, Integer productId)throws OAException;
	
	/**
	 *回退清空goodslog
	 *@author jiahy
	 * @throws OAException
	 */
	public void cleanToStatus(int id)throws OAException;
	
	/**
	 * 得到实发数
	 * @author yanyufeng
	 * @param goodsId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws OAException
	 * 2015-4-20 下午2:54:11
	 */
	public List<Map<String,Object>> getActualNum(int showVirTime,int goodsId,long startTime,long endTime)throws OAException;
	
	/**
	 * 查询待提量
	 * @Title:GoodsLogDao
	 * @Description:
	 * @param goods
	 * @return
	 * @throws OAException
	 * @Date:2015年9月21日 下午10:42:57
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public Map<String,Object> queryWaitAmount(Integer goodsId) throws OAException;

	/**
	 * 校验是否存在联单可能
	 *@author jiahy
	 * @param glDto
	 * @return
	 */
	public Map<String,Object> checkIsKupono(GoodsLogDto glDto) throws OAException;

	/**
	 * 同步流转图的goodsLog
	 *@author jiahy
	 * @param goodsLog
	 */
	public void synGoodsLog(GoodsLog goodsLog) throws OAException;

	/** 
	 * 将一个发货通知单拆为两个联单
	 *@author jiahy
	 * @param glDto
	 */
	public int splitDeliverGoods(GoodsLogDto glDto) throws OAException;

	/**
	 * 查询需要拆分的货体日志
	 * @author yanyufeng
	 * @param string
	 * @return
	 * @throws OAException
	 * 2015-10-21 下午4:37:59
	 */
	public List<Map<String, Object>> getGoodsGraftingLog(String string)throws OAException;

	/**
	 *@author jiahy
	 * @param invoiceDto
	 * @return
	 */
	public List<Map<String, Object>> getPrintList(GoodsLogDto invoiceDto)throws OAException;

	/**
	 * 通知单号 serial
	 * batchId 
	 *@author jiahy
	 * @param serial
	 * @param id
	 */
	public void addFlowMeter(String serial, Integer batchId,Integer isLiandan)throws OAException;

	/**
	 * 修改提单调入量
	 * @author yanyufeng
	 * @param goodsTotal
	 * @param id
	 * @throws OAException
	 * 2015-11-12 下午5:50:33
	 */
	public void updateGoodsInOut(String goodsTotal, Integer id)throws OAException;
	public List<Map<String,Object>> getSurplus(int ShowVirTime, int goodsId, Long startTime, Long endTime)throws OAException;
	
	/**
	 * 同步出库goodslog结算量
	 * @param goodsLogDto
	 * @throws OAException
	 */
	public void syncGoodsLogGoodsSave(GoodsLogDto goodsLogDto) throws OAException;
	/**
	 * @param goodsLog
	 * @throws OAException
	 */
	public void syncGoodsLogOutTime(GoodsLog goodsLog)throws OAException;
	/**
	 * 如果不通过 afDiffNumafUpNum 发回为null
	 * @param goodsLog
	 * @throws OAException
	 */
	public void rebackGoodsLog(GoodsLog goodsLog)throws OAException;
	/**
	 * @Title invoiceQuery
	 * @Descrption:TODO
	 * @param:@param invoiceDto
	 * @param:@return
	 * @param:@throws OAException
	 * @return:List<Map<String,Object>>
	 * @auhor jiahy
	 * @date 2016年2月27日下午2:19:29
	 * @throws
	 */
	public List<Map<String, Object>> invoiceQuery(GoodsLogDto invoiceDto,int start,int limit)throws OAException;
	/**
	 * @Title invoiceQueryCount
	 * @Descrption:TODO
	 * @param:@param invoiceDto
	 * @param:@return
	 * @return:String
	 * @auhor jiahy
	 * @date 2016年2月27日下午2:19:34
	 * @throws
	 */
	public int invoiceQueryCount(GoodsLogDto invoiceDto) throws OAException;
	public void deleteGoodsLogByNextGoodsId(String nextGoodsId)throws OAException;
	/**
	 * @Title getTotalNum
	 * @Descrption:TODO
	 * @param:@param invoiceDto
	 * @param:@return
	 * @return:Collection<? extends Object>
	 * @auhor jiahy
	 * @date 2016年3月6日下午1:41:46
	 * @throws
	 */
	public Map<String, Object> getTotalNum(GoodsLogDto invoiceDto) throws OAException;
	/**
	 * 获取通知单号
	 * @Title getGoodsLogSerial
	 * @Descrption:TODO
	 * @param:@param goodslogId
	 * @param:@return
	 * @return:String
	 * @auhor jiahy
	 * @date 2016年5月6日上午10:46:46
	 * @throws
	 */
	public Map<String, Object> getGoodsLogSerial(Integer goodslogId) throws OAException;
	/**
	 * 添加发货开票
	 * @Title insertDeliverGoods
	 * @Descrption:TODO
	 * @param:@param goodsLog
	 * @param:@return
	 * @return:Integer
	 * @auhor jiahy
	 * @date 2016年5月7日下午3:56:07
	 * @throws
	 */
	public Map<String, Object> insertDeliverGoods(GoodsLog goodsLog) throws OAException;
	/**
	 * 根据通知单号获取要读取到流量计的通知单信息
	 * @Title getFlowMeterList
	 * @Descrption:TODO
	 * @param:@param serial
	 * @param:@return
	 * @return:List<Map<String,Object>>
	 * @auhor jiahy
	 * @date 2016年5月9日上午8:27:51
	 * @throws
	 */
	public List<Map<String, Object>> getFlowMeterList(String serial) throws OAException;
	public  Map<String, Object> getFlowMeterSerial(int id) throws OAException; 
	/**
	 * @return 
	 * @Title syncFlowMeter
	 * @Descrption:TODO
	 * @param:@param string
	 * @param:@param valueOf
	 * @param:@param valueOf2
	 * @return:void
	 * @auhor jiahy
	 * @date 2016年5月9日上午10:03:26
	 * @throws
	 */
	public List<Map<String, Object>> syncFlowMeter(String serial, Double measureWeigh, Integer inPort) throws OAException;
	/**
	 * 根据通知单号获取要读取到流量计的通知单信息
	 * @Title getFlowMeterList
	 * @Descrption:TODO
	 * @param:@param serial
	 * @param:@return
	 * @return:List<Map<String,Object>>
	 * @auhor jiahy
	 * @date 2016年5月9日上午8:27:51
	 * @throws
	 */
	public Map<String, Object> getFlowMeterBySerial(String serial) throws OAException;
	/**
	 * @Title searchLading
	 * @Descrption:TODO
	 * @param:@param invoiceDto
	 * @param:@return
	 * @return:List<Map<String,Object>>
	 * @auhor jiahy
	 * @date 2016年6月13日上午9:38:37
	 * @throws
	 */
	public List<Map<String, Object>> searchLading(GoodsLogDto invoiceDto) throws OAException;
	/**
	 * @Title validateGoodsNum
	 * @Descrption:TODO
	 * @param:@param goodsLog
	 * @param:@return
	 * @return:boolean
	 * @auhor jiahy
	 * @date 2017年1月6日上午10:54:04
	 * @throws
	 */
	public Map<String, Object> getGoodsCurrent(int goodsId) throws OAException;
	/**
	 * @Title checkIsChangeDeliveryNum
	 * @Descrption:TODO
	 * @param:@param goodsLog
	 * @param:@return
	 * @return:boolean
	 * @auhor jiahy
	 * @date 2017年9月21日下午4:19:06
	 * @throws
	 */
	public Map<String, Object> checkIsChangeDeliveryNum(GoodsLog goodsLog)  throws OAException;

	
}
