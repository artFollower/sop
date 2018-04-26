/**
 * 
 * @Project:sop
 * @Title:ExportDao.java
 * @Package:com.skycloud.oa.report.dao
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年11月13日 上午11:24:06
 * @Version:SkyCloud版权所有
 */
package com.skycloud.oa.report.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.report.dto.ExportDto;
import com.skycloud.oa.report.dto.ReportDto;

/**
 * <p>导出Excel数据源</p>
 * @ClassName:ExportDao
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年11月13日 上午11:24:06
 * 
 */
public interface ExportDao 
{
	/**
	 * 查询码头进出港统计表(进港信息)
	 * @Title:ExportDao
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @Date:2015年11月13日 下午2:10:05
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String, Object>> findInDock(ExportDto export) throws OAException;
	
	/**
	 * 查询码头进出港统计表(出港信息)
	 * @Title:ExportDao
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @Date:2015年11月13日 下午2:30:24
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String, Object>> findOutnDock(ExportDto export) throws OAException;
	
	/**
	 * 查询码头进出港统计表(进港-1月到某月的累计)
	 * @Title:ExportDao
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @Date:2015年11月13日 下午5:23:18
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String, Object>> findAddInDock(ExportDto export) throws OAException;
	
	/**
	 * 查询码头进出港统计表(出港-1月到某月的累计)
	 * @Title:ExportDao
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @Date:2015年11月13日 下午5:23:18
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String, Object>> findAddOutDock(ExportDto export) throws OAException;
	
	/**
	 * 查询装车站发货统计表（1#、2#装车站）
	 * @Title:ExportDao
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @Date:2015年11月13日 下午10:45:09
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String,Object>> findStation(ExportDto export) throws OAException;
	
	/**
	 * 查询通过单位统计表
	 * @Title:ExportDao
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @Date:2015年11月14日 下午9:19:00
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String,Object>> findUnits(ExportDto export) throws OAException;
	
	/**
	 * 查询通过单位统计表(发货总量)
	 * @Title:ExportDao
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @Date:2015年11月14日 下午9:39:51
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String,Object>> findOutUnits(ExportDto export) throws OAException;
	
	/**
	 * 查询仓储进出存汇总表
	 * @Title:ExportDao
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @Date:2015年11月15日 下午6:33:45
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String,Object>> findInOut(ExportDto export) throws OAException;
	
	/**
	 * 查询储罐周转率
	 * @Title:ExportDao
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @Date:2015年11月15日 下午7:33:07
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public Map<String,Object> findTurnRate(ExportDto export) throws OAException;
	
	/**
	 * 查询储罐周转率-最大可储存量（吨）
	 * @Title:ExportDao
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @Date:2015年11月18日 上午10:15:35
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String,Object>> findMaxTurnRate(ExportDto export) throws OAException;
	
	/**
	 * 吞吐量统计表-进货总量（船发）
	 * @Title:ExportDao
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @Date:2015年11月19日 上午10:13:01
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public Map<String,Object> queryInThrough(ExportDto export) throws OAException;
	
	/**
	 * 吞吐量统计表-出货总量（船发）
	 * @Title:ExportDao
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @Date:2015年11月19日 上午10:28:31
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public Map<String,Object> queryOutThrough(ExportDto export) throws OAException;
	
	/**
	 * 吞吐量统计表-灌装站发货总量（车发）
	 * @Title:ExportDao
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @Date:2015年11月19日 上午10:28:31
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public Map<String,Object> queryOutCar(ExportDto export) throws OAException;
	
	/**
	 * 吞吐量统计表-转输（输入）
	 * @Title:ExportDao
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @Date:2015年11月19日 下午4:28:19
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public Map<String,Object> queryInput(ExportDto export) throws OAException;
	
	/**
	 * 吞吐量统计表-转输（输出）
	 * @Title:ExportDao
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @Date:2015年11月19日 下午4:38:50
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public Map<String,Object> queryOutput(ExportDto export) throws OAException;
	
	/**
	 * 仓储进出存汇总表-本期入库
	 * @Title:ExportDao
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @Date:2015年11月28日 下午1:42:08
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String,Object>> queryInStorage(ExportDto export) throws OAException;
	
	/**
	 * 仓储进出存汇总表-本期出库
	 * @Title:ExportDao
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @Date:2015年11月28日 下午3:09:59
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String,Object>> queryOutStorage(ExportDto export) throws OAException;
	
	/**
	 * 仓储进出存汇总表-期末结存
	 * @Title:ExportDao
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @Date:2015年11月28日 下午4:55:24
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String,Object>> queryCurrentStorage(ExportDto export) throws OAException;

	/**
	 * 外贸进港
	 * @param export
	 * @return
	 */
	public List<Map<String, Object>> getForeignTradeInbound(ExportDto export) throws OAException;

	/**
	 * 外贸出港
	 * @param export
	 * @return
	 */
	public List<Map<String, Object>> getForeignTradeOutbound(ExportDto export) throws OAException;
    
	
	/**
	 * 获取拼装船信息
	 * @Title getAssemBlyShip
	 * @Descrption:TODO
	 * @param:@param exportDto
	 * @param:@return
	 * @param:@throws OAException
	 * @return:int
	 * @auhor jiahy
	 * @date 2016年12月26日上午10:06:11
	 * @throws
	 */
	public int getAssemBlyShip(ExportDto exportDto) throws OAException;
	/**
	 * 入库吞吐量
	 * @param export
	 * @return
	 * @throws OAException
	 */
	public Map<String, Object> getInboundPass(ExportDto export) throws OAException;

	/**
	 * 出库吞吐量
	 * @param export
	 * @return
	 * @throws OAException
	 */
	public Map<String, Object>  getOutboundPass(ExportDto export) throws OAException;

	/**
	 * @Title queryInThroughOf2015
	 * @Descrption:TODO
	 * @param:@param exportLastYear
	 * @param:@return
	 * @return:Map<String,Object>
	 * @auhor jiahy
	 * @date 2016年4月19日上午9:12:48
	 * @throws
	 */
	public Map<String, Object> queryThroughtRateOf2015(ExportDto exportLastYear) throws OAException;

	/**
	 * @Title getUnit2015
	 * @Descrption:TODO
	 * @param:@param nExportDto
	 * @param:@return
	 * @return:Map<String,Object>
	 * @auhor jiahy
	 * @date 2016年4月19日上午10:24:49
	 * @throws
	 */
	public Map<String, Object> getUnit2015(ExportDto nExportDto) throws OAException;

	/**
	 * @Title getMaxNumOfTank
	 * @Descrption:TODO
	 * @param:@param export
	 * @param:@return
	 * @return:Map<String,Object>
	 * @auhor jiahy
	 * @date 2016年4月20日下午2:59:06
	 * @throws
	 */
	public Map<String, Object> getMaxNumOfTank(ExportDto export) throws OAException;

}
