/**
 * 
 */
package com.skycloud.oa.statistics.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.model.Goods;
import com.skycloud.oa.message.dto.MessageDto;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.model.EveryDayStatics;
import com.skycloud.oa.statistics.dto.StatisticsDto;
import com.skycloud.oa.utils.OaMsg;



/**
 * 统计报表业务逻辑处理接口类
 * @ClassName: StatisticsService 
 * @Description: TODO
 * @author yanyf
 */
public interface StatisticsService {
	
	/**
	 * 货批汇总表
	 * @author Administrator
	 * @param sDto
	 * @param pageView
	 * @return
	 * 2015-3-24 下午4:08:55
	 */
	public abstract OaMsg getCargo(StatisticsDto sDto, PageView pageView)throws OAException;
	

	/**
	 * 货体汇总表
	 * @author Administrator
	 * @param sDto
	 * @param pageView
	 * @return
	 * 2015-3-24 下午4:09:03
	 */
	public abstract OaMsg getGoods(StatisticsDto sDto, PageView pageView,boolean isAll)throws OAException;


	/**
	 * 海关放行拆分
	 * @author yanyufeng
	 * @param oldGoodsId
	 * @param goods
	 * @return
	 * 2015-4-10 下午1:30:44
	 */
	public abstract OaMsg sqlitPassGoods(int oldGoodsId, Goods goods) throws OAException;


	/**导出货体excel
	 * @author yanyufeng
	 * @param msg
	 * @param type 
	 * @return
	 * 2015-5-29 上午11:33:48
	 */
	public abstract HSSFWorkbook exportGoodsExcel(OaMsg msg, Integer type) throws OAException;


	/**
	 * 导出货批excel
	 * @author yanyufeng
	 * @param msg
	 * @param type 
	 * @param request 
	 * @param statisticsDto 
	 * @return
	 * 2015-5-29 下午2:43:40
	 */
	public abstract HSSFWorkbook exportCargoExcel(OaMsg msg, int type, HttpServletRequest request, StatisticsDto statisticsDto) throws OAException;


	/**
	 * 统计货批
	 * @author yanyufeng
	 * @param statisticsDto
	 * @return
	 * @throws OAException
	 * 2015-8-31 下午4:43:08
	 */
	public abstract OaMsg getCargoTotal(StatisticsDto statisticsDto)throws OAException;


	/**
	 * 查询日志
	 * @author yanyufeng
	 * @param statisticsDto
	 * @param pageView
	 * @return
	 * 2015-10-18 下午2:03:51
	 */
	public abstract OaMsg getLog(StatisticsDto statisticsDto, PageView pageView)throws OAException;


	/**
	 * 统计日志
	 * @author yanyufeng
	 * @param statisticsDto
	 * @return
	 * 2015-10-18 下午4:19:42
	 */
	public abstract OaMsg getLogTotal(StatisticsDto statisticsDto)throws OAException;


	/**
	 * 导出日志excel
	 * @author yanyufeng
	 * @param request 
	 * @param msg
	 * @param type 
	 * @param statisticsDto 
	 * @return
	 * @throws OAException
	 * 2015-11-2 下午2:32:21
	 */
	public abstract HSSFWorkbook exportLogExcel(HttpServletRequest request, OaMsg msg, Integer type, StatisticsDto statisticsDto)throws OAException;


	/**
	 * 查询货体总量
	 * @author yanyufeng
	 * @param statisticsDto
	 * @return
	 * @throws OAException
	 * 2015-11-18 上午10:56:21
	 */
	public abstract OaMsg getGoodsTotal(StatisticsDto statisticsDto)throws OAException;


	/**
	 * 进出存简易发货模式
	 * @author yanyufeng
	 * @param statisticsDto
	 * @return
	 * @throws OAException
	 * 2015-12-3 上午11:41:25
	 */
	public abstract OaMsg getEasyLog(StatisticsDto statisticsDto)throws OAException;


	/**
	 * 查询通过货批
	 * @author yanyufeng
	 * @param statisticsDto
	 * @param pageView
	 * @return
	 * @throws OAException
	 * 2016-4-27 下午6:28:12
	 */
	public abstract OaMsg getPassCargo(StatisticsDto statisticsDto,
			PageView pageView)throws OAException;


	public abstract OaMsg getPassTotal(StatisticsDto statisticsDto)throws OAException;
	
	
	
	/**
	 * 
	 * 插入每日统计表
	 * @author yanyufeng
	 * @param everyDayStatics
	 * @return
	 * @throws OAException
	 * 2016-10-17 下午3:59:01
	 */
	public abstract OaMsg addEveryDayStatics(EveryDayStatics everyDayStatics)throws OAException;
	
	
	
	public abstract OaMsg getEveryDayStatics(EveryDayStatics everyDayStatics)throws OAException;
	
	/**
	 * @Title cargoList
	 * @Descrption:TODO
	 * @param:@param statisticsDto
	 * @param:@param pageView
	 * @param:@param msg
	 * @return:void
	 * @auhor jiahy
	 * @date 2017年7月10日上午9:02:26
	 * @throws
	 */
	public abstract void cargoList(StatisticsDto statisticsDto, PageView pageView, OaMsg msg);


	/**
	 * 货批统计信息
	 * @Descrption:TODO
	 * @param:@param statisticsDto
	 * @param:@param msg
	 * @return:void
	 * @auhor jiahy
	 * @date 2017年7月13日下午5:33:34
	 * @throws
	 */
	public abstract void getCargoListTotal(StatisticsDto statisticsDto,OaMsg msg);


	/**
	 *导出货批列表
	 * @Descrption:TODO
	 * @param:@param request
	 * @param:@param sDto
	 * @param:@return
	 * @return:HSSFWorkbook
	 * @auhor jiahy
	 * @date 2017年7月13日下午5:58:35
	 * @throws
	 */
	public abstract HSSFWorkbook exportCargoListExcel(HttpServletRequest request, StatisticsDto sDto);
	public abstract HSSFWorkbook exportCargoListExcel2(HttpServletRequest request, StatisticsDto sDto);
	
}
