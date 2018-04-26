/**
 * 
 */
package com.skycloud.oa.feebill.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dao.DockFeeChargeDao;
import com.skycloud.oa.feebill.dto.DockFeeBillDto;
import com.skycloud.oa.feebill.dto.DockFeeChargeDto;
import com.skycloud.oa.feebill.dto.FeeChargeDto;
import com.skycloud.oa.feebill.service.DockFeeChargeService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;

/**
 *
 * @author jiahy
 * @version 2015年6月14日 下午4:11:42
 */
@Service
public class DockFeeChargeServiceImpl implements DockFeeChargeService {
	private static Logger LOG = Logger.getLogger(DockFeeChargeServiceImpl.class);
@Autowired
private DockFeeChargeDao dockFeeChargeDao;
	@Override
	public OaMsg getDockFeeChargeList(DockFeeChargeDto dDto, PageView pageView) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(dockFeeChargeDao.getDockFeeChargeList(dDto,pageView.getStartRecord(),pageView.getMaxresult()));
			oaMsg.getMap().put(Constant.TOTALRECORD, dockFeeChargeDao.getDockFeeChargeCount(dDto)+"");
		}catch (RuntimeException e){
			LOG.error("service 获取码头规费列表失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service获取码头规费列表失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_DOCKFEECHARGE,type=C.LOG_TYPE.CREATE)
	public OaMsg deleteDockFeeCharge(DockFeeChargeDto dDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			dockFeeChargeDao.deleteDockFeeCharge(dDto);
		}catch (RuntimeException e){
			LOG.error("service删除费用失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 删除费用失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_DOCKFEECHARGE,type=C.LOG_TYPE.DELETE)
	public OaMsg cleanFeeChargeFromBill(DockFeeBillDto dDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			dockFeeChargeDao.cleanDockFeeCharge(dDto);
		}catch (RuntimeException e){
			LOG.error("service移除费用失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 移除费用失败",e);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getFeeHeadList(DockFeeChargeDto dDto, PageView pageView)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(dockFeeChargeDao.getFeeHeadList(dDto,pageView.getStartRecord(),pageView.getMaxresult()));
			oaMsg.getMap().put(Constant.TOTALRECORD, dockFeeChargeDao.getFeeHeadListCount(dDto)+"");
			
		}catch (RuntimeException e){
			LOG.error("service获取码头规费列表失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service获取码头规费列表失败",e);
		}
		return oaMsg;
	}

}
