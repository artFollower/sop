package com.skycloud.oa.outbound.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dto.GoodsLogDto;
import com.skycloud.oa.outbound.model.GoodsLog;
import com.skycloud.oa.utils.OaMsg;

/**
 * 
 * <p>出库管理-发货开票</p>
 * @ClassName:GoodsLogService
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年9月18日 上午11:17:49
 *
 */
public interface GoodsLogService 
{

	/**
	 * @Title list
	 * @Descrption:TODO
	 * @param:@param invoiceDto
	 * @param:@param pageView
	 * @param:@return
	 * @return:public OaMsg
	 * @auhor jiahy
	 * @date 2016年6月12日下午3:21:39
	 * @throws
	 */
	 public OaMsg list(GoodsLogDto invoiceDto, PageView pageView) throws OAException;

	/**
	 * @Title getPrintList
	 * @Descrption:TODO
	 * @param:@param invoiceDto
	 * @param:@return
	 * @return:public OaMsg
	 * @auhor jiahy
	 * @date 2016年6月12日下午3:21:44
	 * @throws
	 */
	public OaMsg getPrintList(GoodsLogDto invoiceDto) throws OAException;

	/**
	 * @Title get
	 * @Descrption:TODO
	 * @param:@param id
	 * @param:@return
	 * @return:public OaMsg
	 * @auhor jiahy
	 * @date 2016年6月12日下午3:21:48
	 * @throws
	 */
	public OaMsg get(String id) throws OAException;

	/**
	 * @Title getSerial
	 * @Descrption:TODO
	 * @param:@param serial
	 * @param:@return
	 * @return:public OaMsg
	 * @auhor jiahy
	 * @date 2016年6月12日下午3:21:51
	 * @throws
	 */
	public OaMsg getSerial(String serial) throws OAException;

	/**
	 * @Title add
	 * @Descrption:TODO
	 * @param:@param s
	 * @param:@return
	 * @return:public OaMsg
	 * @auhor jiahy
	 * @date 2016年6月12日下午3:21:56
	 * @throws
	 */
	public OaMsg add(GoodsLogDto s) throws OAException;

	/**
	 * @Title changeInvoice
	 * @Descrption:TODO
	 * @param:@param s
	 * @param:@return
	 * @return:public OaMsg
	 * @auhor jiahy
	 * @date 2016年6月12日下午3:22:00
	 * @throws
	 */
	public OaMsg changeInvoice(GoodsLog s) throws OAException;

	/**
	 * @Title delete
	 * @Descrption:TODO
	 * @param:@param ids
	 * @param:@return
	 * @return:public OaMsg
	 * @auhor jiahy
	 * @date 2016年6月12日下午3:22:04
	 * @throws
	 */
	public OaMsg delete(String ids) throws OAException;

	/**
	 * @Title checkIsKupono
	 * @Descrption:TODO
	 * @param:@param glDto
	 * @param:@return
	 * @return:public OaMsg
	 * @auhor jiahy
	 * @date 2016年6月12日下午3:22:07
	 * @throws
	 */
	public OaMsg checkIsKupono(GoodsLogDto glDto) throws OAException;

	/**
	 * @Title splitDeliverGoods
	 * @Descrption:TODO
	 * @param:@param glDto
	 * @param:@return
	 * @return:public OaMsg
	 * @auhor jiahy
	 * @date 2016年6月12日下午3:22:11
	 * @throws
	 */
	public OaMsg splitDeliverGoods(GoodsLogDto glDto) throws OAException;

	/**
	 * @Title invoiceQuery
	 * @Descrption:TODO
	 * @param:@param invoiceDto
	 * @param:@param pageView
	 * @param:@return
	 * @return:public OaMsg
	 * @auhor jiahy
	 * @date 2016年6月12日下午3:22:14
	 * @throws
	 */
	public OaMsg invoiceQuery(GoodsLogDto invoiceDto, PageView pageView) throws OAException;

	/**
	 * @Title getTotalNum
	 * @Descrption:TODO
	 * @param:@param invoiceDto
	 * @param:@return
	 * @return:public OaMsg
	 * @auhor jiahy
	 * @date 2016年6月12日下午3:22:18
	 * @throws
	 */
	public OaMsg getTotalNum(GoodsLogDto invoiceDto) throws OAException;

	/**
	 * @Title getFlowMeterBySerial
	 * @Descrption:TODO
	 * @param:@param serial
	 * @param:@return
	 * @return:public OaMsg
	 * @auhor jiahy
	 * @date 2016年6月12日下午3:22:22
	 * @throws
	 */
	public OaMsg getFlowMeterBySerial(String serial) throws OAException;

	/**
	 * @Title searchLading
	 * @Descrption:TODO
	 * @param:@param invoiceDto
	 * @param:@return
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年6月13日上午9:37:11
	 * @throws
	 */
	public OaMsg searchLading(GoodsLogDto invoiceDto) throws OAException;

	/**
	 * @Title exportInbound
	 * @Descrption:TODO
	 * @param:@param request
	 * @param:@return
	 * @return:HSSFWorkbook
	 * @auhor jiahy
	 * @date 2016年8月29日上午9:38:19
	 * @throws
	 */
	public HSSFWorkbook exportInbound(HttpServletRequest request) throws OAException;

}
