package com.skycloud.oa.outbound.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.model.ArrivalInfo;
import com.skycloud.oa.inbound.model.ArrivalPlan;
import com.skycloud.oa.inbound.model.BerthAssess;
import com.skycloud.oa.inbound.model.BerthProgram;
import com.skycloud.oa.inbound.model.Store;
import com.skycloud.oa.inbound.model.TransportProgram;
import com.skycloud.oa.inbound.model.Work;
import com.skycloud.oa.inbound.model.WorkCheck;
import com.skycloud.oa.outbound.dto.ShipArrivalDto;
import com.skycloud.oa.outbound.model.DeliveryShip;
import com.skycloud.oa.outbound.model.UploadFileInfo;

/**
 * 
 * <p>出库管理-船舶出库</p>
 * @ClassName:ShipDeliverWorkDao
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年9月18日 下午2:52:04
 *
 */
public interface OutBoundDao 
{
	
	/**
	 * @Title getOutBoundList
	 * @Descrption:TODO
	 * @param:@param arrivalDto
	 * @param:@param startRecord
	 * @param:@param maxresult
	 * @param:@return
	 * @return:Collection<? extends Object>
	 * @auhor jiahy
	 * @date 2016年3月11日上午9:34:53
	 * @throws
	 */
	public List<Map<String, Object>> getOutBoundList(ShipArrivalDto arrivalDto, int startRecord, int maxresult) throws OAException;
	/**
	 * @Title getOutBoundListCount
	 * @Descrption:TODO
	 * @param:@param arrivalDto
	 * @param:@return
	 * @return:String
	 * @auhor jiahy
	 * @date 2016年3月11日上午9:35:36
	 * @throws
	 */
	public int getOutBoundListCount(ShipArrivalDto arrivalDto) throws OAException;
	/**
	 * 通过id获取作业计划
	 * @Title:ShipDeliverWorkDao
	 * @Description:
	 * @param arrivalId
	 * @return
	 * @throws OAException
	 * @Date:2015年9月18日 下午2:54:51
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public Map<String,Object> getArrivalInfoById(String arrivalId) throws OAException;
	/**
	 * @Title getTubesAndTanksByArrivalId
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @return:Map<? extends String,? extends Object>
	 * @auhor jiahy
	 * @date 2016年3月14日下午2:44:17
	 * @throws
	 */
	public Map<String,Object> getTubesAndTanksByArrivalId(String arrivalId) throws OAException;

	/**
	 * 保存出港计划信息
	 * @Title:ShipDeliverWorkDao
	 * @Description:
	 * @param arrivalInfo
	 * @throws OAException
	 * @Date:2015年9月18日 下午2:53:46
	 * @return: void 
	 * @throws
	 */
	public int saveArrivalInfo(ArrivalInfo arrivalInfo) throws OAException ;
	
	/**
	 * 更新出港计划信息
	 * @Title:ShipDeliverWorkDao
	 * @Description:
	 * @param arrivalInfo
	 * @throws OAException
	 * @Date:2015年9月18日 下午2:54:26
	 * @return: void 
	 * @throws
	 */
	public void updateArrivalInfo(ArrivalInfo arrivalInfo) throws OAException ;
	
	/**
	 * 获取靠泊方案信息
	 * @Title getBerthProgramByArrivalId
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @param:@throws OAException
	 * @return:List<Map<String,Object>>
	 * @auhor jiahy
	 * @date 2016年3月15日上午9:31:21
	 * @throws
	 */
	public Map<String,Object> getBerthProgramByArrivalId(String arrivalId)throws OAException  ;
	/**
	 * @Title getBerthAssess
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @param:@throws OAException
	 * @return:Map<String,Object>
	 * @auhor jiahy
	 * @date 2016年3月16日下午1:24:27
	 * @throws
	 */
	public  Map<String,Object> getBerthAssess(String arrivalId) throws OAException;
	/**
	 * @Title saveBerthAssess
	 * @Descrption:TODO
	 * @param:@param berthAssess
	 * @param:@return
	 * @param:@throws OAException
	 * @return:int
	 * @auhor jiahy
	 * @date 2016年3月16日下午2:45:51
	 * @throws
	 */
	public int saveBerthAssess(BerthAssess berthAssess)  throws OAException ;
	/**
	 * @Title getTransportProgramMsg
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @return:Map<? extends String,? extends Object>
	 * @auhor jiahy
	 * @date 2016年3月17日上午10:14:09
	 * @throws
	 */
	public Map<String,Object> getTransportProgramMsg(String arrivalId) throws OAException;
	/**
	 * 获取船舶出库发货信息
	 * @Title getAmountAffirmMsg
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @param:@throws OAException
	 * @return:List<Map<String,Object>>
	 * @auhor jiahy
	 * @date 2016年3月24日下午2:07:59
	 * @throws
	 */
	public List<Map<String,Object>> getAmountAffirmMsg(String arrivalId) throws OAException ;
	/**
	 * 获取分流台账信息
	 * @Title getShipFlowInfo
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @return:Object
	 * @auhor jiahy
	 * @date 2016年6月1日下午8:54:39
	 * @throws
	 */
	public Map<String, Object> getShipFlowInfo(Integer arrivalId) throws OAException;
	/**
	 * 获取检查信息
	 * @Title getShipFlowCheckMsg
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @param:@throws OAException
	 * @return:List<Map<String,Object>>
	 * @auhor jiahy
	 * @date 2016年6月1日下午9:20:40
	 * @throws
	 */
	public List<Map<String,Object>> getShipFlowCheckMsg(Integer arrivalId)throws OAException ;
	/**
	 * 
	 * @Title:ShipDeliverWorkDao
	 * @Description:
	 * @param arrivalId
	 * @return
	 * @throws OAException
	 * @Date:2015年9月18日 下午2:54:58
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String,Object>> getPortNumCount(String arrivalId) throws OAException;
	
	/**
	 * 
	 * @Title:ShipDeliverWorkDao
	 * @Description:
	 * @param arrivalId
	 * @return
	 * @throws OAException
	 * @Date:2015年9月18日 下午2:55:04
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String,Object>> getAnchorTime(String arrivalId) throws OAException;
	
	/**
	 * 
	 * @Title:ShipDeliverWorkDao
	 * @Description:
	 * @param arrivalId
	 * @return
	 * @throws OAException
	 * @Date:2015年9月18日 下午2:55:10
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String,Object>> getArrivalById(String arrivalId) throws OAException;
	
	/**
	 * 
	 * @Title:ShipDeliverWorkDao
	 * @Description:
	 * @param arrivalId
	 * @return
	 * @throws OAException
	 * @Date:2015年9月18日 下午2:55:16
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String,Object>> getBaseGoodsInfo(String arrivalId) throws OAException;
	
	/**
	 * 
	 * @Title:ShipDeliverWorkDao
	 * @Description:
	 * @param status
	 * @param arrivalId
	 * @throws OAException
	 * @Date:2015年9月18日 下午2:55:54
	 * @return: void 
	 * @throws
	 */
	public void updateStatus(String status,String arrivalId) throws OAException ;
	
	/**
	 * 
	 * @Title:ShipDeliverWorkDao
	 * @Description:
	 * @param arrivalId
	 * @throws OAException
	 * @Date:2015年9月18日 下午2:56:02
	 * @return: void 
	 * @throws
	 */
	public void insertWorkInfo(int arrivalId) throws OAException ;
	
	/**
	 * 
	 * @Title:ShipDeliverWorkDao
	 * @Description:
	 * @param arrivalId
	 * @param flag
	 * @throws OAException
	 * @Date:2015年9月18日 下午2:56:10
	 * @return: void 
	 * @throws
	 */
	public void checkCommitChange(int arrivalId,int flag) throws OAException ;
	public int saveBerthProgram(BerthProgram berthProgram) throws OAException ;
	public void updateBerthProgram(BerthProgram berthProgram) throws OAException ;

	public List<Map<String,Object>> getBerthInfoById(String berthId,String arrivalId) throws OAException ;
	public void saveStore(Store store)  throws OAException ;
	public void saveWork(Work work)  throws OAException ;
	public void updateWork(Work work)  throws OAException ;
	public List<Map<String,Object>> getWorkByArrivalId(String arrivalId)  throws OAException ;
	public List<Map<String,Object>> getStoreByArrivalId(String arrivalId)  throws OAException ;
	public void updateStore(Store store) throws OAException ;
	
	
	public List<Map<String,Object>> getWorkTask(String arrivalId) throws OAException;
	public int saveTransportProgram(TransportProgram transportProgram)throws OAException ;
	public int updateTransportProgram(TransportProgram transportProgram)throws OAException ;
	public int addDockWorkInfo(TransportProgram transportProgram)throws OAException ;
	public void saveTrans(int transportId,String tubeName) throws OAException ;
	public void saveStore(int transportId,String tankName) throws OAException ;
	public void deleteStore(int transportId,String tankIds) throws OAException ;
	public void deleteTrans(int transportId,String tubeIds) throws OAException ;
	public List<Map<String,Object>> queryDockNotifyInfo(String arrivalId) throws OAException ;
	public void deleteWorkCheckByTransportId(int transportId)throws OAException ;
	public void deleteDeliveryShipByTransportId(int transportId)throws OAException ;
	public void saveCheckWork(WorkCheck workCheck)throws OAException ;
	public int getWorkCheckCount(WorkCheck workCheck)throws Exception ;
	public void updateWorkCheck(WorkCheck workcheck) throws Exception ;
	public void updateCheckWork(WorkCheck workCheck)throws OAException ;
	
	public List<Map<String,Object>> getStatisticsByTank(String arrivalId) throws OAException ;
	public void updateArrivalBerthId(String arrivalId,String berthId)throws OAException ;
	public void updateBerth(BerthAssess berth)throws OAException ;
	public List<Map<String, Object>> getWorkList(long startTime,long endTime) throws OAException ;
	public void updateDynamicNotifyInfo(TransportProgram d)throws OAException ;
	public void updatePipingNotifyInfo(TransportProgram d)throws OAException ;
	public void saveDeliveryShip(DeliveryShip d)throws OAException ;
	public void updateDeliveryShip(DeliveryShip d)throws OAException ;
	public List<Map<String,Object>> getWork(String arrivalId)throws OAException ;
	
	public List<Map<String,Object>> getDeliveryShip(String arrivalId)throws OAException ;
	public List<Map<String,Object>> getShipFlowBaseInfo(String arrivalId)throws OAException ;
	public void addConfirmData(ArrivalPlan data) throws OAException ;
	public void updateTranStatus(TransportProgram transportProgram) throws OAException ;
	public List<Map<String,Object>> getLogIsHave(long startTime,long endTime)throws OAException ;
	public List<Map<String,Object>> getTransportInfo(String arrivalId) throws Exception ;
	public List<Map<String,Object>> getFileList(String arrivalId, Integer type) throws Exception ;
	public void deleteUploadFile(String id) throws Exception ;
	public void saveUploadInfo(UploadFileInfo fileInfo) throws OAException ;//保存上传附件信息
	public void updateArrivalTime(long arrivalTime,String arrivalId)throws Exception ; //更新实际到港时间
	public void  deleteCheckInfo(String transportId)throws Exception ; //重置作业通知单 删除作业检查项
	public Map<String,Object> getTranspInfoById(String tpId)throws Exception ; //通过id获取transportProgram信息
	public void updateNotifyTime(String time,int type,int tId)throws Exception ;

	/**
	 * 根据时间获取分流台账出库船舶信息
	 *@author jiahy
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Map<String, Object>> getOutArrivalList(long startTime, long endTime) throws OAException;

	/**
	 * 根据船舶出库arrivalId 获取该船的分流信息
	 *@author jiahy
	 * @param valueOf
	 * @return
	 */
	public List<Map<String, Object>> getOutFlowDataByArrivalId(Integer arrivalId) throws OAException;

	/**
	 *@author jiahy
	 * @param valueOf
	 * @return
	 */
	public List<Map<String, Object>> getTubeNamesByArrivalId(Integer arrivalId) throws OAException;

	/**
	 *@author jiahy
	 * @param valueOf
	 * @return
	 */
	public List<Map<String, Object>> getClientNameByArrivalId(Integer arrivalId) throws OAException;

	/**
	 *@author jiahy
	 * @param arrivalId
	 * @return
	 */
	public int checkIsInvoice(String arrivalId) throws OAException;
	/**
	 * @Title getExportOutbound
	 * @Descrption:TODO
	 * @param:@param startTime
	 * @param:@param endTime
	 * @param:@return
	 * @return:List<Map<String,Object>>
	 * @auhor jiahy
	 * @date 2016年5月3日上午10:42:30
	 * @throws
	 */
	public List<Map<String, Object>> getExportOutbound(String startTime, String endTime) throws OAException;
	
	
	
	
}
