package com.skycloud.oa.inbound.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.TankLogDto;
import com.skycloud.oa.inbound.model.TransportInfo;
import com.skycloud.oa.inbound.model.TankLog;


public interface TransportInfoDao{
	
	/**
	 *添加传输信息
	 *@author jiahy
	 * @param transportInfo
	 * @return
	 * @throws OAException
	 */
	public int addTransportInfo(TransportInfo transportInfo)throws OAException;
	/**
	 *获取传输信息
	 *@author jiahy
	 * @param transportIds
	 * @return
	 * @throws OAException
	 */
	public List<Map<String,Object>> getTransportInfoList(String transportIds)throws OAException;
	/**
	 *更新传输信息
	 *@author jiahy
	 * @param TransportInfo
	 * @throws OAException
	 */
	public void updateTransportInfo(TransportInfo TransportInfo)throws OAException;
	/**
	 *获取数量
	 *@author jiahy
	 * @param tankLogDto
	 * @return
	 * @throws OAException
	 */
	public int getTransportInfoListCount(TankLogDto tankLogDto)throws OAException;
	/**
	 *删除传输信息
	 *@author jiahy
	 * @param id
	 * @throws OAException
	 */
	public void deleteTransportInfoByTransPortId(Integer id) throws OAException;
	/**
	 *清除传输信息
	 *@author jiahy
	 * @param id
	 * @param b
	 * @throws OAException
	 */
	public void cleanTransportInfo(int id, boolean b)throws OAException;
}
