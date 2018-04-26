package com.skycloud.oa.inbound.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dto.ExceedCleanLogDto;
import com.skycloud.oa.feebill.model.ExceedCleanLog;
import com.skycloud.oa.inbound.dto.ExceedFeeDto;
import com.skycloud.oa.inbound.dto.ExceedFeeLogDto;
import com.skycloud.oa.inbound.model.ExceedFee;
import com.skycloud.oa.inbound.model.ExceedFeeLog;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 *超期费接口
 * @author 作者:jiahy
 * @version 时间：2015年5月11日 下午10:20:58
 */
public interface ExceedFeeService {
	
/**
 *获取含有超期费的货批
 *@author jiahy
 * @param eFeeDto
 * @param pageView
 * @return
 */
public OaMsg getExceedFeeCargoList(ExceedFeeDto eFeeDto, PageView pageView) throws OAException;

/**
 *获取含有超期费的提单
 *@author jiahy
 * @param eFeeDto
 * @param pageView
 * @return
 */
public OaMsg getExceedFeeLadingList(ExceedFeeDto eFeeDto, PageView pageView) throws OAException;
/**
 *获取超期费列表
 *@author jiahy
 * @return
 * @throws OAException
 */
public OaMsg  getExceedFeeList(ExceedFeeDto eFeeDto ,PageView pageView) throws OAException;

/**
 *添加超期费
 *@author jiahy
 * @param exceedFee
 * @return
 * @throws OAException
 */
public OaMsg  addExceedFee(ExceedFee exceedFee) throws OAException;

/**
 *更新超期费
 *@author jiahy
 * @param exceedFee
 * @return
 * @throws OAException
 */
public OaMsg  updateExceedFee(ExceedFee exceedFee) throws OAException;

/**
 *删除超期费
 *@author jiahy
 * @param eFeeDto
 * @return
 * @throws OAException
 */
public OaMsg  deleteExceedFee(ExceedFeeDto eFeeDto) throws OAException;

/**
 *获取超期费编号
 *@author jiahy
 * @param eFeeDto
 * @return
 * @throws OAException
 */
public OaMsg  getCodeNum(ExceedFeeDto eFeeDto) throws OAException;

/**
 *获取每天的货批超期量
 *@author jiahy
 * @param eFeeDto
 * @param pageView
 * @return
 */
public OaMsg getExceedFeeCargoItemList(ExceedFeeDto eFeeDto) throws OAException;

/**
 *获取每天的提单超期量
 *@author jiahy
 * @param eFeeDto
 * @return
 */
public OaMsg getExceedFeeLadingItemList(ExceedFeeDto eFeeDto) throws OAException;

/**
 *@author jiahy
 * @param eFeeDto
 * @return
 */
public OaMsg cleanCargo(ExceedFeeDto eFeeDto) throws OAException;

/**
 *@author jiahy
 * @param eFeeDto
 * @return
 */
public OaMsg cleanLading(ExceedFeeDto eFeeDto) throws OAException;

/**
 *@author jiahy
 * @param eFeeDto
 * @return
 */
public OaMsg getLadingTurnList(ExceedFeeDto eFeeDto) throws OAException;

/**
 *@author jiahy
 * @param eFeeDto
 * @return
 */
public OaMsg getCargoTurnList(ExceedFeeDto eFeeDto) throws OAException;

/**
 *@author jiahy
 * @param eFeeDto
 * @return
 */
public OaMsg backStatus(ExceedFee exceedFee) throws OAException;

/**获取已经生成提单的每天提单记录
 * @param elDto
 * @return
 */
public OaMsg getExceedFeeLog(ExceedFeeLogDto elDto) throws OAException;

/**
 * 添加超期提单每日记录
 * @param efLogsList
 * @return
 * @throws OAException
 */
public OaMsg addExceedFeeLogs(List<ExceedFeeLog> efLogsList) throws OAException;

/**
 * @author yanyufeng
 * @param eFeeDto
 * @return
 * @throws OAException
 * 2016-5-31 下午11:25:24
 */
public OaMsg getLadingTurnLst(ExceedFeeDto eFeeDto) throws OAException;

/**
 * @author yanyufeng
 * @param request
 * @param msg
 * @param eFeeDto
 * @param sType 
 * @return
 * @throws OAException
 * 2016-6-1 下午9:06:31
 */
public HSSFWorkbook exportLogExcel(HttpServletRequest request, OaMsg msg,
		ExceedFeeDto eFeeDto, int sType) throws OAException;

/**
 * @Title addOrUpdateExceedFee
 * @Descrption:TODO
 * @param:@param exceedFeeDto
 * @param:@return
 * @return:OaMsg
 * @auhor jiahy
 * @date 2016年8月15日下午4:44:23
 * @throws
 */
public OaMsg addOrUpdateExceedFee(ExceedFeeDto exceedFeeDto) throws OAException;

/**
 * @Title getExceedFeeMsg
 * @Descrption:TODO
 * @param:@param eDto
 * @param:@return
 * @return:OaMsg
 * @auhor jiahy
 * @date 2016年8月15日下午7:20:09
 * @throws
 */
public OaMsg getExceedFeeMsg(ExceedFeeDto eDto) throws OAException;

/**
 * @Title exportExcelList
 * @Descrption:TODO
 * @param:@param request
 * @param:@param eFeeDto
 * @param:@return
 * @return:HSSFWorkbook
 * @auhor jiahy
 * @date 2016年9月23日上午10:22:44
 * @throws
 */
public HSSFWorkbook exportExcelList(HttpServletRequest request,ExceedFeeDto eFeeDto);

/**
 * @Title addExceedCleanLog
 * @Descrption:TODO
 * @param:@param exceedCleanLog
 * @param:@return
 * @return:OaMsg
 * @auhor jiahy
 * @date 2017年3月25日上午9:25:46
 * @throws
 */
public OaMsg addExceedCleanLog(ExceedCleanLog exceedCleanLog) throws OAException ;

/**
 * @Title getExceedCleanLog
 * @Descrption:TODO
 * @param:@param eclDto
 * @param:@return
 * @return:OaMsg
 * @auhor jiahy
 * @date 2017年3月25日上午9:25:54
 * @throws
 */
public OaMsg getExceedCleanLog(ExceedCleanLogDto eclDto) throws OAException ;

/**
 * @Title deleteExceedCleanLog
 * @Descrption:TODO
 * @param:@param id
 * @param:@return
 * @return:OaMsg
 * @auhor jiahy
 * @date 2017年3月25日上午9:27:24
 * @throws
 */
public OaMsg deleteExceedCleanLog(int id) throws OAException ;

}
