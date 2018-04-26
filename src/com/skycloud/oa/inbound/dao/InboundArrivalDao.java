package com.skycloud.oa.inbound.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.InboundOperationDto;
import com.skycloud.oa.inbound.model.Arrival;
import com.skycloud.oa.utils.OaMsg;


/**
 * 入库作业对arrival的操作
 * @author jiahy
 *
 */
public interface InboundArrivalDao{
 public	List<Map<String,Object>> getInboundArrivalList(InboundOperationDto inOperationDto,int start,int limit ) throws OAException;
 
 /**
  * 判断如果已经添加过就不能添加
  * true表示没有添加过
  * false 表示已经添加过
 * @param arrivalId
 * @return
 * @throws OAException
 */
public boolean checkInboundArrival(int arrivalId) throws OAException;

/**
 * 获取数量
 * @param inOperationDto
 * @return
 * @throws OAException
 */
public int getInboundArrivalList(InboundOperationDto inOperationDto) throws OAException;

/**
 * 根据给定的limit计算实际需要的limit
 * @param limit
 * @return
 * @throws OAException
 */
public int getActualLimit(InboundOperationDto iDto,int limit)throws OAException;

/**
 * 回退到某个流程
 * @param status
 * @param arrivalId
 */
public void cleanToStatus(Arrival arrival) throws OAException;

/**
 * @Title getExportInbound
 * @Descrption:TODO
 * @param:@param ioDto
 * @param:@return
 * @return:List<Map<String,Object>>
 * @auhor jiahy
 * @date 2016年4月29日下午3:25:48
 * @throws
 */
public List<Map<String, Object>> getExportInbound(InboundOperationDto ioDto) throws OAException;

/**
 * @Title getPrintList
 * @Descrption:TODO
 * @param:@param ioDto
 * @param:@return
 * @return:OaMsg
 * @auhor jiahy
 * @date 2016年12月8日下午2:15:39
 * @throws
 */
public List<Map<String, Object>> getPrintList(InboundOperationDto ioDto) throws OAException;
}
