/**
 * @Title:TradeInvoiceServiceImpl.java
 * @Package com.skycloud.oa.outbound.service.impl
 * @Description TODO
 * @autor jiahy
 * @date 2016年4月8日上午8:47:24
 * @version V1.0
 */
package com.skycloud.oa.outbound.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dao.TradeInvoiceDao;
import com.skycloud.oa.outbound.dto.TradeInvoiceDto;
import com.skycloud.oa.outbound.service.TradeInvoiceService;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;

/**
 * 发货冲销和更换货体
 * @ClassName TradeInvoiceServiceImpl
 * @Description TODO
 * @author jiahy
 * @date 2016年6月13日上午9:05:53
 */
@Service
public class TradeInvoiceServiceImpl implements TradeInvoiceService {
	private static Logger LOG = Logger.getLogger(TradeInvoiceServiceImpl.class);
	@Autowired
	private TradeInvoiceDao tradeInvoiceDao;
	/**
	 * @Title getCargoList
	 * @Descrption:TODO
	 * @param:@param tIDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年4月8日上午8:47:24
	 * @throws
	 */
	@Override
	public OaMsg getGoodsList(TradeInvoiceDto tIDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(tradeInvoiceDao.getGoodsList(tIDto));
		} catch (RuntimeException e) {
			LOG.error("service 获取货体列表失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获取货体列表失败",e);
		}
		return oaMsg;
	}
	/**
	 * @Title getOutboundList
	 * @Descrption:TODO
	 * @param:@param tIDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年4月8日下午1:46:12
	 * @throws
	 */
	@Override
	public OaMsg getOutboundList(TradeInvoiceDto tIDto,PageView pageView) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(tradeInvoiceDao.getOutboundList(tIDto,pageView.getStartRecord(),pageView.getMaxresult()));
			oaMsg.getMap().put(Constant.TOTALRECORD, tradeInvoiceDao.getOutboundListCount(tIDto)+"");
		} catch (RuntimeException e) {
			LOG.error("service 获取发货列表失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获取发货列表失败",e);
		}
		return oaMsg;
	}
	/**
	 * 发货冲销
	 * @Title tradeInvoice
	 * @Descrption:TODO
	 * @param:@param tIDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年4月8日下午4:28:33
	 * @throws
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_TRADEINVOICE,type=C.LOG_TYPE.DELETE)
	public OaMsg tradeInvoice(TradeInvoiceDto tIDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			if(!Common.isNull(tIDto.getGoodsLogIds())){
				String[] idArray=tIDto.getGoodsLogIds().split(",");
				for(int i=0,len=idArray.length;i<len;i++){
				tradeInvoiceDao.tradeInvoice(idArray[i],tIDto.getRemark());	
				}
			}
		} catch (RuntimeException e) {
			LOG.error("service 冲销发货失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 冲销发货失败",e);
		}
		return oaMsg;
	}
	/**
	 * @Title getTradeInvoiceLogList
	 * @Descrption:TODO
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年4月11日下午3:12:23
	 * @throws
	 */
	@Override
	public OaMsg getTradeInvoiceLogList(PageView pageView) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(tradeInvoiceDao.getTradeInvoiceLogList(pageView.getStartRecord(),pageView.getMaxresult()));
			oaMsg.getMap().put(Constant.TOTALRECORD, tradeInvoiceDao.getTradeInvoiceLogListCount()+"");
		} catch (RuntimeException e) {
			LOG.error("service 冲销发货失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 冲销发货失败",e);
		}
		return oaMsg;
	}

}
