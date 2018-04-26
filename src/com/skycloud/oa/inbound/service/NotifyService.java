package com.skycloud.oa.inbound.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.NotifyDto;
import com.skycloud.oa.inbound.model.Notify;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 *其他通知单
 * @author 作者:jiahy
 * @version 时间：2015年3月16日 下午2:24:21
 */
public interface NotifyService {

	/**
	 *获取通知单列表
	 *@author jiahy
	 * @param notifyDto
	 * @param pageView
	 * @return
	 * @throws OAException
	 */
	public OaMsg  getNotifyList(NotifyDto notifyDto, PageView pageView) throws OAException;
	
	/**
	 *添加通知单
	 *@author jiahy
	 * @param notify
	 * @return
	 * @throws OAException
	 */
	public OaMsg  addNotify(Notify notify) throws OAException;
	
	/**
	 *更新通知单
	 *@author jiahy
	 * @param notify
	 * @return
	 * @throws OAException
	 */
	public OaMsg updateNotify(Notify notify) throws OAException;
	/**
	 *删除通知单
	 *@author jiahy
	 * @param notifyDto
	 * @return
	 * @throws OAException
	 */
	public OaMsg deleteNotify(NotifyDto notifyDto) throws OAException;

	/**
	 *根据创建时间获取货体编号
	 *@author jiahy
	 * @param notifyDto
	 * @return
	 */
	public OaMsg getCodeNum(NotifyDto notifyDto) throws OAException;

	/**
	 *重置通知单
	 *@author jiahy
	 * @param notifyDto
	 * @return
	 * @throws OAException
	 */
	public OaMsg resetNotify(NotifyDto notifyDto) throws OAException;

	/**
	 * @param request 
	 * @Title exportItemExcel
	 * @Descrption:TODO
	 * @param:@param notifyDto
	 * @param:@return
	 * @return:HSSFWorkbook
	 * @auhor jiahy
	 * @date 2017年1月10日上午11:16:14
	 * @throws
	 */
	public HSSFWorkbook exportItemExcel(HttpServletRequest request, NotifyDto notifyDto) throws OAException;
}
