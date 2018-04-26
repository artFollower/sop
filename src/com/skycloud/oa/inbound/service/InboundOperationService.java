
package com.skycloud.oa.inbound.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.skycloud.oa.common.dto.SecurityCodeDto;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.InboundOperationDto;
import com.skycloud.oa.inbound.model.Arrival;
import com.skycloud.oa.inbound.model.ArrivalInfo;
import com.skycloud.oa.inbound.model.BerthAssess;
import com.skycloud.oa.inbound.model.BerthProgram;
import com.skycloud.oa.inbound.model.Cargo;
import com.skycloud.oa.inbound.model.Goods;
import com.skycloud.oa.inbound.model.JobCheck;
import com.skycloud.oa.inbound.model.Notification;
import com.skycloud.oa.inbound.model.Store;
import com.skycloud.oa.inbound.model.Trans;
import com.skycloud.oa.inbound.model.TransportInfo;
import com.skycloud.oa.inbound.model.TransportProgram;
import com.skycloud.oa.inbound.model.Work;
import com.skycloud.oa.inbound.model.WorkCheck;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.model.ApproveContent;
import com.skycloud.oa.utils.OaMsg;

public interface InboundOperationService {
  
  /**
   * 获取信息
 * @param ioDto
 * @param pageView
 * @return
 */
public OaMsg getInboundOperationList(InboundOperationDto ioDto, PageView pageView) throws OAException;

/**
 * 添加入库相关表字段
 * @param ioDto
 * @return
 * @throws OAException
 */
public OaMsg addInboundOperationItem(InboundOperationDto ioDto) throws OAException;
/**
 * 更新入库计划
 * @param arrivalInfo
 * @return
 * @throws OAException
 */
public OaMsg updateArrivalInfo(ArrivalInfo arrivalInfo) throws OAException;
/**
 * 更新靠泊评估,根据reviewStatus==2更新流程状态
 * @param berthAssess
 * @param scDto 
 * @return
 * @throws OAException
 */
public OaMsg updateBerthAssess(BerthAssess berthAssess, SecurityCodeDto scDto)throws OAException; 

/**
 * 更新靠泊方案,根据status==2更新流程状态
 * @param berthProgram
 * @param scDto 
 * @return
 * @throws OAException
 */
public OaMsg updateBerthProgram(BerthProgram berthProgram, SecurityCodeDto scDto) throws OAException;

/**
 * 更新到港信息表
 * @param arrival
 * @return
 * @throws OAException
 */
public OaMsg updateArrival(Arrival arrival,String shipRefName) throws OAException;

/**
 * 更新接卸方案
 * @param transportProgram
 * @param tankIds 储罐ids
 * @param scDto 
 * @param workId 
 * @return
 * @throws OAException
 */
public OaMsg updateTransportProgram(TransportProgram transportProgram, String tankIds, SecurityCodeDto scDto, Integer workId) throws OAException;

/**
 *传输管线 
 * @param trans
 * @return
 * @throws OAException
 */
public OaMsg addTransList(List<Trans> transList) throws OAException;

/**
 * 添加存储罐
 * @param storeList
 * @return
 * @throws OAException
 */
public OaMsg addStoreList(List<Store> storeList) throws OAException;

/**
 * 添加管线作业检查表(根据工艺流程)
 * @param tpId
 * @throws OAException
 */
public OaMsg addWorkCheckList(Integer tpId)throws OAException;
/**
 * 添加管线作业检查表
 * @param tpId
 * @throws OAException
 */
public OaMsg addWorkCheck(WorkCheck workCheck)throws OAException;
/**
 * 添加岗位作业检查表
 * @param tpId
 * @return
 * @throws OAException
 */
public OaMsg addJobCheckList(List<JobCheck> list) throws OAException;

/**
 * 更新工作检查表
 * @param workCheck
 * @return
 * @throws OAException
 */
public OaMsg updateWorkCheck(WorkCheck workCheck) throws OAException;

/**
 * 更新岗位检查表
 * @param jobCheck
 * @return
 */
public OaMsg updateJobCheck(JobCheck jobCheck) throws OAException;

/**
 * 更新入库作业表
 * @param arrivalId 
 * @param work
 * @throws OAException
 */
public OaMsg updateWork(String arrivalId, Work work,List<Notification> notificationList) throws OAException;
/**
 * 更新入库作业表
 * @param arrivalId 
 * @param work
 * @throws OAException
 */
public OaMsg updateWork(Work work) throws OAException;
/**
 * 更新存储罐
 * @param storeList
 * @return
 * @throws OAException
 */
public OaMsg updateStoreList(List<Store> storeList)throws OAException;

/**
 * 更新货批添加货体
 * @param cargoList
 * @param goodsList
 * @param scDto 
 * @param arrival
 * @return
 */
public OaMsg updateCargoGoodsList(List<Cargo> cargoList, List<Goods> goodsList,
		Work work ,String shipId, SecurityCodeDto scDto) throws OAException;

/**
 * 回退到某个流程
 * @param status
 * @param arrivalId
 * @return
 * @throws OAException
 */
public OaMsg cleanToStatus(InboundOperationDto ioDto)throws OAException;
/**
 *添加传输附属信息
 *@author jiahy
 * @param transportInfo
 * @return
 * @throws OAException
 */
public OaMsg addTransportInfo(TransportInfo transportInfo) throws OAException;

/**
 *更新传输方案附属信息
 *@author jiahy
 * @param transportInfo
 * @return
 * @throws OAException
 */
public OaMsg updateTransportInfo(TransportInfo transportInfo) throws OAException;

/**
 *靠泊评估审批工作流
 *@author jiahy
 * @param ids
 * @param berthassessId
 * @param content
 * @param msgContent 
 * @param url 
 * @return
 * @throws OAException
 */
public OaMsg sendCheck(String ids, Integer berthassessId, String content, String url, String msgContent)throws OAException;

/**
 *获取靠泊评估审批工作流
 *@author jiahy
 * @param approveContent
 * @return
 * @throws OAException
 */
public OaMsg getApproveContent(ApproveContent approveContent)throws OAException;

/**
 *抄送消息
 *@author jiahy
 * @param ids
 * @param berthassessId
 * @param content
 * @return
 * @throws OAException
 */
public OaMsg sendCopy(String ids, Integer berthassessId, String content)throws OAException;

/**
 * 添加一次多次接卸
 *@author jiahy
 * @param ioDto
 * @return
 */
public OaMsg addUnloading(InboundOperationDto ioDto) throws OAException;

/**
 * 删除一次多次接卸
 *@author jiahy
 * @param ioDto
 * @return
 */
public OaMsg deleteUnloading(InboundOperationDto ioDto)throws OAException;

/**
 * 添加一次打循环
 *@author jiahy
 * @param ioDto
 * @return
 */
public OaMsg addBackFlow(InboundOperationDto ioDto)throws OAException;

/**
 * 删除一次打循环
 *@author jiahy
 * @param ioDto
 * @return
 */
public OaMsg deleteBackFlow(InboundOperationDto ioDto)throws OAException;

/**
 *@author jiahy
 * @param ioDto
 * @return
 */
public OaMsg getTotalGoodsTank(InboundOperationDto ioDto)throws OAException;

/**
 * @Title exportInbound
 * @Descrption:TODO
 * @param:@param request
 * @param:@param ioDto
 * @param:@return
 * @return:HSSFWorkbook
 * @auhor jiahy
 * @date 2016年4月28日下午3:07:49
 * @throws
 */
public HSSFWorkbook exportInbound(HttpServletRequest request, InboundOperationDto ioDto) throws OAException;

/**
 * @Title updateInboundInfo
 * @Descrption:TODO
 * @param:@param ioDto
 * @param:@return
 * @return:OaMsg
 * @auhor jiahy
 * @date 2016年6月28日下午2:14:15
 * @throws
 */
public OaMsg updateInboundInfo(InboundOperationDto ioDto) throws OAException;

/**
 * @Title getPrintList
 * @Descrption:TODO
 * @param:@param ioDto
 * @param:@return
 * @return:Object
 * @auhor jiahy
 * @date 2016年12月8日上午10:27:01
 * @throws
 */
public OaMsg getPrintList(InboundOperationDto ioDto) throws OAException;

}