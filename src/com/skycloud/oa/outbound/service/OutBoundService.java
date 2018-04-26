package com.skycloud.oa.outbound.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.skycloud.oa.common.dto.SecurityCodeDto;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.model.ArrivalInfo;
import com.skycloud.oa.inbound.model.BerthAssess;
import com.skycloud.oa.inbound.model.BerthProgram;
import com.skycloud.oa.inbound.model.Store;
import com.skycloud.oa.inbound.model.TransportProgram;
import com.skycloud.oa.inbound.model.WorkCheck;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dto.ShipArrivalDto;
import com.skycloud.oa.outbound.dto.ShipDeliverWorkQueryDto;
import com.skycloud.oa.outbound.model.UploadFileInfo;
import com.skycloud.oa.utils.OaMsg;

/**
 * 
 * <p>出库管理-船舶出库</p>
 * @ClassName:ShipDeliverWorkService
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年9月18日 上午11:16:40
 *
 */
public interface OutBoundService
{
	/**
	 * 获取船发出库数据
	 * @Title getOutBoundList
	 * @Descrption:TODO
	 * @param:@param arrivalDto
	 * @param:@param pageView
	 * @param:@return
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年3月11日上午9:27:07
	 * @throws
	 */
	public OaMsg getOutBoundList(ShipArrivalDto arrivalDto, PageView pageView) throws OAException;
	
	/**
	 * 更新船发列表信息
	 * @Title upBaseInfo
	 * @Descrption:TODO
	 * @param:@param shipArrivalDto
	 * @param:@return
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年3月11日下午2:39:29
	 * @throws
	 */
	public OaMsg updateBaseInfo(ShipArrivalDto shipArrivalDto) throws OAException ;
	/**
	 * 获取船发列表基本信息
	 * @Title getBaseGoodsInfo
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @param:@throws OAException
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年6月12日下午4:09:49
	 * @throws
	 */
	public OaMsg getBaseGoodsInfo(String arrivalId) throws OAException ;
	/**
	 * 通过id获取到港计划信息
	 * @Title getArrivalById
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年6月12日下午4:10:36
	 * @throws
	 */
	public OaMsg getArrivalById(String arrivalId) throws OAException;
	/**
	 * 通过到港id获取作业计划
	 * @Title getArrivalInfoById
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年3月14日下午1:46:08
	 * @throws
	 */
	public OaMsg getArrivalInfoById(String arrivalId)throws OAException ;
	/**
	 * 保存或更新作业计划
	 * @Title addOrUpdateArrivalInfo
	 * @Descrption:TODO
	 * @param:@param arrivalInfo
	 * @param:@return
	 * @return:Object
	 * @auhor jiahy
	 * @date 2016年3月15日上午8:38:21
	 * @throws
	 */
	public OaMsg addOrUpdateArrivalInfo(ArrivalInfo arrivalInfo,Integer isTransport) throws OAException;
	
	/**通过id获取靠泊方案信息
	 * @Title getBerthProgramByArrivalId
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年3月15日上午9:29:00
	 * @throws
	 */
	public OaMsg getBerthProgramByArrivalId(String arrivalId) throws OAException;
	/**
	 * @param scDto 
	 * @Title addOrUpdateBerthProgram
	 * @Descrption:TODO
	 * @param:@param berthProgram
	 * @param:@return
	 * @return:Object
	 * @auhor jiahy
	 * @date 2016年3月16日上午11:53:23
	 * @throws
	 */
	public OaMsg addOrUpdateBerthProgram(BerthProgram berthProgram, SecurityCodeDto scDto) throws OAException;

	/**
	 * @Title getBerthAssess
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年3月16日下午1:22:44
	 * @throws
	 */
	public OaMsg getBerthAssess(String arrivalId)throws OAException ;//获取靠泊评估信息
	/**
	 * @param scDto 
	 * @Title addOrUpdateBerthAssess
	 * @Descrption:TODO
	 * @param:@param berthAssess
	 * @param:@return
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年3月16日下午2:43:05
	 * @throws
	 */
	public OaMsg addOrUpdateBerthAssess(BerthAssess berthAssess, SecurityCodeDto scDto) throws OAException;
	/**
	 * @Title getDeliverReadyMsg
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年3月17日上午9:55:52
	 * @throws
	 */
	public OaMsg getDeliverReadyMsg(String arrivalId) throws OAException;
	/**保存更新工艺流程
	 * @Title addOrUpdateTransportProgram
	 * @Descrption:TODO
	 * @param:@param transportProgram
	 * @param:@return
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年3月23日下午3:47:50
	 * @throws
	 */
	public OaMsg addOrUpdateTransportProgram(TransportProgram transportProgram) throws OAException;
	/**
	 *  获取船舶出库发货信息
	 * @Title getAmountAffirmMsg
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @param:@throws OAException
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年3月24日下午2:04:18
	 * @throws
	 */
	public OaMsg getAmountAffirmMsg(String arrivalId) throws OAException;//查询发货详细信息
	
	/**
	 * @param scDto 
	 * 船舶出库数量确认
	 * @Title confirmData
	 * @Descrption:TODO
	 * @param:@param data
	 * @param:@return
	 * @param:@throws OAException
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年3月24日下午4:18:09
	 * @throws
	 */
	public OaMsg confirmData(ShipDeliverWorkQueryDto data, SecurityCodeDto scDto) throws OAException;//数据确认
	
	/**
	 * 获取某天分流台账信息
	 * @Title getShipFlowList
	 * @Descrption:TODO
	 * @param:@param startTime
	 * @param:@param endTime
	 * @param:@return
	 * @param:@throws OAException
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年6月1日下午8:49:27
	 * @throws
	 */
	public OaMsg getShipFlowList(long startTime,long endTime) throws OAException;//查询分流台账
	/**
	 * 获取分流台账信息
	 * @Title getShipFlowInfo
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @param:@throws OAException
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年6月1日下午8:48:18
	 * @throws
	 */
	public OaMsg getShipFlowInfo(Integer arrivalId) throws OAException ;//获取分流台账编辑界面信息
	/**
	 * 保存分流台账记录
	 * @Title saveShipFlow
	 * @Descrption:TODO
	 * @param:@param storeList
	 * @param:@return
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年6月1日下午10:18:25
	 * @throws
	 */
	public OaMsg saveShipFlow(ShipDeliverWorkQueryDto storeList) ;//保存分流台账编辑界面信息
	
	/**
	 * @Title queryDockNotifyInfo
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @return:Object
	 * @auhor jiahy
	 * @date 2016年6月12日下午4:11:27
	 * @throws
	 */
	public OaMsg queryDockNotifyInfo(String arrivalId) throws OAException;

	/**
	 * @Title getLogIsHave
	 * @Descrption:TODO
	 * @param:@param mStartTime
	 * @param:@param mEndTime
	 * @param:@return
	 * @return:Object
	 * @auhor jiahy
	 * @date 2016年6月12日下午4:11:57
	 * @throws
	 */
	public OaMsg getLogIsHave(long mStartTime, long mEndTime);
	/**
	 * @param type 
	 * @Title getFileList
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @return:Object
	 * @auhor jiahy
	 * @date 2016年6月28日上午8:47:06
	 * @throws
	 */
	public OaMsg getFileList(String arrivalId, Integer type);
	/**
	 * @Title deleteUploadFile
	 * @Descrption:TODO
	 * @param:@param id
	 * @param:@return
	 * @return:Object
	 * @auhor jiahy
	 * @date 2016年6月12日下午4:12:12
	 * @throws
	 */
	public OaMsg deleteUploadFile(String id);

	/**
	 * @Title saveUploadInfo
	 * @Descrption:TODO
	 * @param:@param uploadFileInfo
	 * @return:void
	 * @auhor jiahy
	 * @date 2016年6月12日下午4:12:35
	 * @throws
	 */
	public OaMsg saveUploadInfo(UploadFileInfo uploadFileInfo);
	/**
	 * @Title exportOutbound
	 * @Descrption:TODO
	 * @param:@param request
	 * @param:@param startTime
	 * @param:@param endTime
	 * @param:@return
	 * @return:HSSFWorkbook
	 * @auhor jiahy
	 * @date 2016年5月3日上午10:38:27
	 * @throws
	 */
	public HSSFWorkbook exportOutbound(HttpServletRequest request, String startTime, String endTime) throws OAException;

	



	
}
