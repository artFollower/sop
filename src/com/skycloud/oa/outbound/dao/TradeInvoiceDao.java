/**
 * @Title:TradeInvoiceDao.java
 * @Package com.skycloud.oa.outbound.dao
 * @Description TODO
 * @autor jiahy
 * @date 2016年4月8日上午8:51:14
 * @version V1.0
 */
package com.skycloud.oa.outbound.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.outbound.dto.TradeInvoiceDto;

/**
 * @ClassName TradeInvoiceDao
 * @Description TODO
 * @author jiahy
 * @date 2016年4月8日上午8:51:14
 */
public interface TradeInvoiceDao {

	/**
	 * @Title getCargoList
	 * @Descrption:TODO
	 * @param:@param tIDto
	 * @param:@return
	 * @return:Collection<? extends Object>
	 * @auhor jiahy
	 * @date 2016年4月8日上午8:51:20
	 * @throws
	 */
	List<Map<String, Object>> getGoodsList(TradeInvoiceDto tIDto)throws OAException;

	/**
	 * @param limit 
	 * @param start 
	 * @Title getOutboundList
	 * @Descrption:TODO
	 * @param:@param tIDto
	 * @param:@return
	 * @return:Collection<? extends Object>
	 * @auhor jiahy
	 * @date 2016年4月8日下午1:46:25
	 * @throws
	 */
	List<Map<String, Object>> getOutboundList(TradeInvoiceDto tIDto, int start, int limit) throws OAException;

	/**
	 * @Title getOutboundListCount
	 * @Descrption:TODO
	 * @param:@param tIDto
	 * @param:@return
	 * @return:String
	 * @auhor jiahy
	 * @date 2016年4月8日下午2:04:24
	 * @throws
	 */
	int getOutboundListCount(TradeInvoiceDto tIDto) throws OAException;

	/**
	 * @Title tradeInvoice
	 * @Descrption:TODO
	 * @param:@param string
	 * @return:void
	 * @auhor jiahy
	 * @date 2016年4月11日上午9:06:21
	 * @throws
	 */
	void tradeInvoice(String id,String remark) throws OAException;

	/**
	 * @Title getTradeInvoiceLogList
	 * @Descrption:TODO
	 * @param:@param startRecord
	 * @param:@param maxresult
	 * @param:@return
	 * @return:Collection<? extends Object>
	 * @auhor jiahy
	 * @date 2016年4月11日下午3:14:17
	 * @throws
	 */
	List<Map<String, Object>> getTradeInvoiceLogList(int start, int limit) throws OAException;

	/**
	 * @Title getTradeInvoiceLogListCount
	 * @Descrption:TODO
	 * @param:@return
	 * @return:String
	 * @auhor jiahy
	 * @date 2016年4月11日下午3:14:22
	 * @throws
	 */
	int getTradeInvoiceLogListCount() throws OAException;

}
