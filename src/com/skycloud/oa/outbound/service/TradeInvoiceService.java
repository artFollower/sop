/**
 * @Title:TradeInvoiceService.java
 * @Package com.skycloud.oa.outbound.service
 * @Description TODO
 * @autor jiahy
 * @date 2016年4月8日上午8:30:53
 * @version V1.0
 */
package com.skycloud.oa.outbound.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dto.TradeInvoiceDto;
import com.skycloud.oa.utils.OaMsg;

/**
 * @ClassName TradeInvoiceService
 * @Description TODO
 * @author jiahy
 * @date 2016年4月8日上午8:30:53
 */
public interface TradeInvoiceService {

	/**
	 * 获取货批列表
	 * @Title getCargoList
	 * @Descrption:TODO
	 * @param:@param tIDto
	 * @param:@return
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年4月8日上午8:31:13
	 * @throws
	 */
	OaMsg getGoodsList(TradeInvoiceDto tIDto) throws OAException;

	/**
	 * @param pageView 
	 * @Title getOutboundList
	 * @Descrption:TODO
	 * @param:@param tIDto
	 * @param:@return
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年4月8日下午1:45:57
	 * @throws
	 */
	OaMsg getOutboundList(TradeInvoiceDto tIDto, PageView pageView) throws OAException;

	/**
	 * @Title tradeInvoice
	 * @Descrption:TODO
	 * @param:@param tIDto
	 * @param:@return
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年4月8日下午4:28:18
	 * @throws
	 */
	OaMsg tradeInvoice(TradeInvoiceDto tIDto) throws OAException;

	/**
	 * @param pageView 
	 * @Title getTradeInvoiceLogList
	 * @Descrption:TODO
	 * @param:@return
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年4月11日下午3:12:08
	 * @throws
	 */
	OaMsg getTradeInvoiceLogList(PageView pageView) throws OAException;

}
