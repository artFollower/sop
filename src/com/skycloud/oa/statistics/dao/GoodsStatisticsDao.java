package com.skycloud.oa.statistics.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.outbound.model.EveryDayStatics;
import com.skycloud.oa.statistics.dto.StatisticsDto;

/**
 * 货批分析持久化接口类
 * 
 * @ClassName: GoodsStatisticsDao
 * @Description: TODO
 * @author xie
 * @date 2015年4月20日 上午2:17:17
 */
public interface GoodsStatisticsDao
{

	/**
	 * 货批汇总
	 * @author yanyufeng
	 * @param sDto
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 * 2015-8-31 下午4:46:36
	 */
	List<Map<String, Object>> get(StatisticsDto sDto, int start, int limit)
			throws OAException;

	int count(StatisticsDto sDto) throws OAException;

	/**
	 * 货批统计
	 * @author yanyufeng
	 * @param sDto
	 * @return
	 * @throws OAException
	 * 2015-8-31 下午4:46:45
	 */
	List<Map<String, Object>> getcargoTotal(StatisticsDto sDto)throws OAException;

	/**
	 * 查询日志记录
	 * @author yanyufeng
	 * @param statisticsDto
	 * @param startRecord
	 * @param maxresult
	 * @return
	 * @throws OAException
	 * 2015-10-18 下午2:22:25
	 */
	List<Map<String, Object>> getLog(StatisticsDto statisticsDto,
			int startRecord, int maxresult)throws OAException;

	int logCount(StatisticsDto statisticsDto)throws OAException;

	/**
	 * 统计日志
	 * @author yanyufeng
	 * @param statisticsDto
	 * @return
	 * @throws OAException
	 * 2015-10-18 下午4:22:38
	 */
	List<Map<String, Object>> getLogTotal(StatisticsDto statisticsDto)throws OAException;

	/**
	 * 查询进出存的货体ids
	 * @author yanyufeng
	 * @param statisticsDto
	 * @return
	 * 2015-11-3 下午4:27:45
	 */
	List<Map<String, Object>> getLogGoodsIds(StatisticsDto statisticsDto)throws OAException;

	/**
	 * 查询货体统计
	 * @author yanyufeng
	 * @param statisticsDto
	 * @return
	 * @throws OAException
	 * 2015-11-18 上午11:03:04
	 */
	List<Map<String, Object>> getGoodsTotal(StatisticsDto statisticsDto)throws OAException;

	/**
	 * 下级所有货体
	 * @author yanyufeng
	 * @param ids
	 * @return
	 * @throws OAException
	 * 2016-3-24 下午8:28:48
	 */
	List<Map<String, Object>> getDownGoodsId(String ids)throws OAException;

	List<Map<String, Object>> getPassCargo(StatisticsDto statisticsDto,
			int startRecord, int maxresult)throws OAException;

	int getPassCargoCount(StatisticsDto statisticsDto)throws OAException;

	/**
	 * 通过船总量
	 * @author yanyufeng
	 * @param statisticsDto
	 * @return
	 * @throws OAException
	 * 2016-5-31 下午5:28:50
	 */
	List<Map<String, Object>> getPassTotal(StatisticsDto statisticsDto)throws OAException;

	/**
	 * 通过船已开票总商检量
	 * @author yanyufeng
	 * @param statisticsDto
	 * @return
	 * 2016-5-31 下午5:29:01
	 */
	List<Map<String, Object>> getPassKPTotal(StatisticsDto statisticsDto)throws OAException;

	int addEveryDayStatics(EveryDayStatics everyDayStatics)throws OAException;

	List<Map<String, Object>> getEveryDayStatics(EveryDayStatics everyDayStatics)throws OAException;

	/**
	 * @Title cargoList
	 * @Descrption:TODO
	 * @param:@param statisticsDto
	 * @param:@param startRecord
	 * @param:@param maxresult
	 * @param:@return
	 * @return:List<Map<String,Object>>
	 * @auhor jiahy
	 * @date 2017年7月10日上午9:04:15
	 * @throws
	 */
	List<Map<String, Object>> cargoList(StatisticsDto statisticsDto,int startRecord, int maxresult);

	/**
	 * @Title getGoodsSave
	 * @Descrption:TODO
	 * @param:@param statisticsDto
	 * @param:@return
	 * @return:String
	 * @auhor jiahy
	 * @date 2017年7月11日下午5:32:12
	 * @throws
	 */
	Map<String, Object> getGoodsSave(StatisticsDto statisticsDto);

	/**
	 * @Title cargoListCount
	 * @Descrption:TODO
	 * @param:@param statisticsDto
	 * @param:@return
	 * @return:String
	 * @auhor jiahy
	 * @date 2017年7月11日下午6:09:10
	 * @throws
	 */
	int cargoListCount(StatisticsDto statisticsDto);

	/**
	 * 货批汇总统计
	 * @Descrption:TODO
	 * @param:@param statisticsDto
	 * @param:@return
	 * @return:Map<String,Object>
	 * @auhor jiahy
	 * @date 2017年7月13日下午5:35:59
	 * @throws
	 */
	Map<String, Object> getCargoListTotal(StatisticsDto statisticsDto);

}
