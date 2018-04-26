/**
 * 
 */
package com.skycloud.oa.feebill.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dao.FeeChargeDao;
import com.skycloud.oa.feebill.dao.OtherFeeDao;
import com.skycloud.oa.feebill.dto.FeeChargeDto;
import com.skycloud.oa.feebill.model.FeeCharge;
import com.skycloud.oa.feebill.service.FeeChargeService;
import com.skycloud.oa.inbound.dao.ExceedFeeDao;
import com.skycloud.oa.inbound.dao.InitialFeeDao;
import com.skycloud.oa.inbound.service.impl.ArrivalPlanServiceImpl;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;

/**
 *
 * @author jiahy
 * @version 2015年6月14日 下午4:11:42
 */
@Service
public class FeeChargeServiceImpl implements FeeChargeService {
	private static Logger LOG = Logger.getLogger(FeeChargeServiceImpl.class);
   @Autowired
   private FeeChargeDao feeChargeDao;
   @Autowired
   private ExceedFeeDao exceedFeeDao;
   @Autowired
   private InitialFeeDao initialFeeDao;
   @Autowired
   private OtherFeeDao otherFeeDao;
	@Override
	public OaMsg getFeeChargeList(FeeChargeDto feeChargeDto, PageView pageView)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			List<Map<String,Object>> data=feeChargeDao.getFeeChargeList(feeChargeDto, pageView.getStartRecord(),pageView.getMaxresult());
			for(int i=0;i<data.size();i++){
				if(data.get(i).get("cargoCode")==null&&data.get(i).get("exceedCode")!=null){//超期提单
					data.get(i).put("inboundMsg",exceedFeeDao.getInboundMsgListByExceedId(Integer.valueOf(data.get(i).get("exceedId").toString())));
				}else if(data.get(i).get("cargoCode")==null&&data.get(i).get("initialCode")!=null){//合同首期费
					data.get(i).put("inboundMsg",initialFeeDao.getInboundMsgByInitialId(Integer.valueOf(data.get(i).get("initialId").toString())));
				}else if(data.get(i).get("cargoCode")==null&&data.get(i).get("otherCode")!=null){//其他费
					data.get(i).put("inboundMsg", otherFeeDao.getInboundMsgByOtherId(Integer.valueOf(data.get(i).get("initialId").toString())));
				}
			}
			oaMsg.getData().addAll(data);
			oaMsg.getMap().put(Constant.TOTALRECORD, feeChargeDao.getFeeChargeCount(feeChargeDto)+"");
		}catch (RuntimeException e){
			LOG.error("service 获取费用列表失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 获取费用列表失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_FEECHARGE,type=C.LOG_TYPE.CREATE)
	public OaMsg addFeeCharge(FeeCharge feeCharge) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			feeCharge.setCreateTime(System.currentTimeMillis()/1000);
			Integer id=feeChargeDao.addFeeCharge(feeCharge);
			feeChargeDao.insertfeeChargeCargoLading(id);
			oaMsg.getMap().put("id", id+"");
		}catch (RuntimeException e){
			LOG.error("service 添加费用失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 添加费用失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_FEECHARGE,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateFeeCharge(FeeCharge feeCharge) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			feeChargeDao.updateFeeCharge(feeCharge);
		}catch (RuntimeException e){
			LOG.error("service 更新费用失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 更新费用失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_FEECHARGE,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteFeeCharge(FeeChargeDto feeChargeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			feeChargeDao.deleteFeeCharge(feeChargeDto);
		}catch (RuntimeException e){
			LOG.error("service删除费用失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 删除费用失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_FEECHARGE,type=C.LOG_TYPE.CREATE)
	public OaMsg addFeeChargeList(List<FeeCharge> feeChargeList)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			for(int i=0;i<feeChargeList.size();i++){
				if(feeChargeList.get(i).getId()!=null){
				feeChargeDao.updateFeeCharge(feeChargeList.get(i));	
				}else{
				feeChargeList.get(i).setCreateTime(System.currentTimeMillis()/1000);
				int id=feeChargeDao.addFeeCharge(feeChargeList.get(i));
			  feeChargeDao.insertfeeChargeCargoLading(id);
				}
			}
		}catch (RuntimeException e){
			LOG.error("service添加多个费用失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 添加多个费用失败",e);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getFeeChargeFeeHeadList(FeeChargeDto feeChargeDto,
			PageView pageView) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(feeChargeDao.getFeeHeadList(feeChargeDto,pageView.getStartRecord(),pageView.getMaxresult()));
			oaMsg.getMap().put(Constant.TOTALRECORD,feeChargeDao.getFeeHeadCount(feeChargeDto)+"");
		}catch (RuntimeException e){
			LOG.error("service添加多个费用失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 添加多个费用失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_FEECHARGE,type=C.LOG_TYPE.DELETE)
	public OaMsg cleanFeeChargeFromBill(FeeChargeDto feeChargeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			feeChargeDao.cleanFeeCharge(feeChargeDto);
		}catch (RuntimeException e){
			LOG.error("service移除费用失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service 移除费用失败",e);
		}
		return oaMsg;
	}
	/**
	 * 校验是否货批已经生成首期费，保安费
	 *@author jiahy
	 * @param feeChargeDto
	 * @return
	 */
	@Override
	public OaMsg isMakeInitialFee(FeeChargeDto feeChargeDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		  oaMsg.getMap().put("ishave",(feeChargeDao.isMakeInitialFee(feeChargeDto)>0?1:0)+"");// 0,没有生成，1，已经生成
		}catch (RuntimeException e){
			LOG.error("service校验是否货批已经生成首期费，保安费失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service校验是否货批已经生成首期费，保安费失败",e);
		}
		return oaMsg;
	}

}
