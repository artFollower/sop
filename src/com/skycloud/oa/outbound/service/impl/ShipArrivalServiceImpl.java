package com.skycloud.oa.outbound.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.hibernate.id.IntegralDataTypeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.ArrivalDao;
import com.skycloud.oa.inbound.dto.ArrivalPlanDto;
import com.skycloud.oa.inbound.model.Arrival;
import com.skycloud.oa.inbound.model.ArrivalPlan;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dao.ShipArrivalDao;
import com.skycloud.oa.outbound.dto.ShipArrivalDto;
import com.skycloud.oa.outbound.service.ShipArrivalService;
import com.skycloud.oa.utils.OaMsg;

@Service
public class ShipArrivalServiceImpl implements ShipArrivalService {
	private static Logger LOG = Logger.getLogger(VehicleDeliverWorkServiceImpl.class);
	@Autowired
	private ShipArrivalDao shipArrivalDao ;
	@Autowired
	private ArrivalDao arrivalDao;
	

	@Override
	public OaMsg list(ShipArrivalDto shipArrivalDto, PageView pageView) {
		
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			List<Map<String, Object>> data = shipArrivalDao.list(shipArrivalDto, pageView);
			if(data!=null&&data.size()>0){
				for(int i=0;i<data.size();i++){
					if(data.get(i).get("id")!=null){
						data.get(i).put("arrivalPlan",shipArrivalDao.getArrivalPlan(data.get(i).get("id").toString()));
                        data.get(i).put("isEqual", shipArrivalDao.getIsEqual(Integer.valueOf(data.get(i).get("id").toString())));						
					}
				}
			}
			oaMsg.getData().addAll(data) ; 
			oaMsg.getMap().put(Constant.TOTALRECORD,  shipArrivalDao.getCount(shipArrivalDto)+"");
		} catch (Exception re) {
			LOG.error("service查询出港计划列表失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
	
	@Override
	@Log(object=C.LOG_OBJECT.PCS_OUTARRIVAL,type=C.LOG_TYPE.DELETE)
	public OaMsg delete(String id) {
		OaMsg oaMsg = new OaMsg();
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		try {
		 Map<String,Object> invoiceCount=shipArrivalDao.getInvoiceByArrivalId(id);
			if(Integer.valueOf(invoiceCount.get("count").toString())>0){
				oaMsg.getMap().put("reMsg", "该计划已开票，请将开票作废后再操作");
			}else{
			shipArrivalDao.delete(id);
			}
		} catch (Exception re) {
			LOG.error("service删除出港计划失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_OUTARRIVAL_ITEM,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteShipPlanById(String arrivalPlanId,Integer status) {
		
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			if(status!=null&&status==2){
				shipArrivalDao.updateShipPlanStatus(arrivalPlanId,0);
			}else{
				shipArrivalDao.deleteShipPlanById(arrivalPlanId) ;
			}
		} catch (Exception re) {
			LOG.error("service删除出港计划出港明细失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_OUTARRIVAL_ITEM,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateShipPlanStatus(String arrivalPlanId) {
		OaMsg oaMsg = new OaMsg();
		try {
			shipArrivalDao.updateShipPlanStatus(arrivalPlanId,2) ;
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (Exception re) {
			LOG.error("service更新出港计划出港明细状态失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
	@Override
	@Log(object=C.LOG_OBJECT.PCS_OUTARRIVAL,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateArrivalStatus(String arrivalPlanId) {
		
		OaMsg oaMsg = new OaMsg();
		try {
			shipArrivalDao.updateArrivalStatus(arrivalPlanId) ;
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (Exception re) {
			LOG.error("service更新出港计划状态失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
	
	@Override
	public OaMsg get(String id) {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			List<Object> list = new ArrayList<Object>() ;
			list.add(shipArrivalDao.get(id));
			list.add(shipArrivalDao.getArrivalPlan(id)) ;
			oaMsg.setData(list) ;
		} catch (Exception re) {
			LOG.error("service查询单个出港计划基本信息失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getGoodsList(int clientId, int productId) {
		
				OaMsg oaMsg = new OaMsg();
				try {
					oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
					oaMsg.getData().addAll(shipArrivalDao.getGoodsList(clientId,productId)) ;
				} catch (Exception re) {
					LOG.error("service查询货体信息失败", re);
					oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
				}
				return oaMsg;
	}

	//添加出港计划
	@Override
	@Log(object=C.LOG_OBJECT.PCS_OUTARRIVAL,type=C.LOG_TYPE.CREATE)
	public OaMsg add(ShipArrivalDto shipArrivalDto) {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			Arrival arrival = shipArrivalDto.getArrival() ;
			int size=shipArrivalDto.getArrivalPlanList().size();
			ArrivalPlan itemPlan;
			if(arrival.getId()!=0){//表示存在 则更新
				arrivalDao.updateArrival(arrival) ;
				for(int i=0;i<size;i++){
					itemPlan=shipArrivalDto.getArrivalPlanList().get(i);
					if(itemPlan.getId()!=0){
					shipArrivalDao.updatePlan(itemPlan);	
					}else{
                    shipArrivalDao.add(itemPlan);						
					}
				}
				oaMsg.getMap().put("id", String.valueOf(arrival.getId())) ;	
			}else{//表示不存在 则添加
			int id = shipArrivalDao.add(arrival) ;
			for(int i= 0;i<size;i++){
				itemPlan= shipArrivalDto.getArrivalPlanList().get(i) ;
				itemPlan.setArrivalId(id) ;
				shipArrivalDao.add(itemPlan);
			}
			oaMsg.getMap().put("id", String.valueOf(id));
			
			}
		} catch (Exception re) {
			LOG.error("service添加出港计划出港明细失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_OUTARRIVAL,type=C.LOG_TYPE.UPDATE)
	public OaMsg update(ShipArrivalDto shipArrivalDto) {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			shipArrivalDao.updateArrival(shipArrivalDto.getArrival()) ;
		} catch (Exception re) {
			LOG.error("service更新出港计划失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
	
	
	@Override
	@Log(object=C.LOG_OBJECT.PCS_OUTARRIVAL_ITEM,type=C.LOG_TYPE.UPDATE)
	public OaMsg updatePlan(ArrivalPlan arrivalPlan) {
		OaMsg oaMsg = new OaMsg();
		try {
			shipArrivalDao.updatePlan(arrivalPlan) ;
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (Exception re) {
			LOG.error("serivice更新出港计划出港明细失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
	@Override
	@Log(object=C.LOG_OBJECT.PCS_OUTARRIVAL_ITEM,type=C.LOG_TYPE.CREATE)
	public OaMsg saveArrivalPlan(ArrivalPlan arrivalPlan) {
		
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			arrivalPlan.setCreateUserId(user.getId()) ;
			int id = shipArrivalDao.add(arrivalPlan) ;
			Map<String,String> map = new HashMap<String,String>();
			map.put("planId", id+"") ;
			oaMsg.setMap(map) ;
		} catch (Exception re) {
			LOG.error("添加出港计划出港明细失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getGoodsMsg(ArrivalPlanDto aDto) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			List<Map<String, Object>> data = shipArrivalDao.getGoodsMsg(aDto);
			if(data!=null&&data.size()>0){
				for(int i=0;i<data.size();i++){
				data.get(i).put("arrivalPlanAmount", shipArrivalDao.getArrivalPlanAmount(Integer.valueOf(data.get(i).get("id").toString())).get("amount"));	
				data.get(i).putAll(shipArrivalDao.getClearanceClientMsg(Integer.valueOf(data.get(i).get("id").toString())));
				}
			}
			oaMsg.getData().addAll(data);
			
		} catch (Exception re) {
			LOG.error("插入失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	/**
	 * @Title reBackPlan
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年8月23日下午3:01:52
	 * @throws
	 */
	@Override
	public OaMsg reBackPlan(Integer arrivalId) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			//TODO 1/ 更新arrival状态为0 2.清除与arrival有关联的数据
			Arrival arrival=new Arrival();
			arrival.setId(arrivalId);
			arrival.setStatus(-1);
			arrivalDao.updateArrival(arrival);
			//
			
		} catch (Exception re) {
			LOG.error("添加出港计划出港明细失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	/**
	 * @Title getDifPlanAndInvoiceMsg
	 * @Descrption:TODO
	 * @param:@param arrivalPlanDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年10月31日上午9:41:05
	 * @throws
	 */
	@Override
	public OaMsg getDifPlanAndInvoiceMsg(ArrivalPlanDto arrivalPlanDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);	
			
			oaMsg.getData().addAll(shipArrivalDao.getDifPlanAndInvoiceMsg(arrivalPlanDto));
			 
		} catch (Exception e) {
			LOG.error(" 失败", e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	/**
	 * @Title updateArrivalPlan
	 * @Descrption:TODO
	 * @param:@param arrivalPlan
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年10月31日下午3:28:53
	 * @throws
	 */
	@Override
	public OaMsg updateArrivalPlan(ArrivalPlan arrivalPlan) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);	
			shipArrivalDao.updateArrivalPlan(arrivalPlan);
		} catch (Exception e) {
			LOG.error(" 失败", e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
	
}
