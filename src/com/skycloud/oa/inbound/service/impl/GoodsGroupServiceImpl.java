package com.skycloud.oa.inbound.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.CargoGoodsDao;
import com.skycloud.oa.inbound.dao.GoodsGroupDao;
import com.skycloud.oa.inbound.dto.CargoGoodsDto;
import com.skycloud.oa.inbound.model.GoodsGroup;
import com.skycloud.oa.inbound.service.GoodsGroupService;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;
@Service
public class GoodsGroupServiceImpl implements GoodsGroupService {
	private static Logger LOG = Logger.getLogger(GoodsGroupServiceImpl.class);
@Autowired
private GoodsGroupDao goodsGroupDao;
@Autowired
private CargoGoodsDao cargoGoodsDao;
	
	@Override
	public OaMsg addgoodsGroup(GoodsGroup goodsGroup) throws OAException {
		OaMsg oaMsg = new OaMsg();
try{
	   oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		String code = "HTZ"
				+ Common.changeNum((goodsGroupDao
						.getTheSameGoodsGroupCount("HTZ") + 1));
		goodsGroup.setCode(code);
		goodsGroup.setGoodsCurrent("0");
		goodsGroup.setGoodsIn("0");
		goodsGroup.setGoodsInPass("0");
		goodsGroup.setGoodsInspect("0");
		goodsGroup.setGoodsOut("0");
		goodsGroup.setGoodsOutPass("0");
		goodsGroup.setGoodsTank("0");
		goodsGroup.setGoodsTotal("0");
		Map<String,String> map=new HashMap<String, String>();
		  map.put("goodsgroupid",""+goodsGroupDao.addGoodsGroup(goodsGroup));
		oaMsg.setMap(map);
} catch (RuntimeException re) {
	LOG.error("查询失败", re);
	oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
	throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败", re);
}
return oaMsg;
		
	}
       // goodsGroupId 必须传入该字段
	@Override
	public OaMsg addToLading(CargoGoodsDto dto) throws OAException {
		
		OaMsg oaMsg = new OaMsg();
		try{  
			  oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			  String[] ids=dto.getGoodsIds().split(",");
			  int goodsGroupId=dto.getGoodsGroupId();
			  CargoGoodsDto ccDto=new CargoGoodsDto();
			  ccDto.setGoodsGroupId(goodsGroupId);
			  List<Map<String,Object>> gcid=  cargoGoodsDao.getGoodsList(ccDto, 0, 0);
              for(int i=0;i<gcid.size();i++){
            	 goodsGroupDao.removeGoodsFromGoodsGroup(Integer.valueOf(gcid.get(i).get("id").toString()),Integer.valueOf(gcid.get(i).get("goodsGroupId").toString()));
              }
			  LOG.debug("获取是货体组的货体");
				  CargoGoodsDto caDto=new CargoGoodsDto();
				  caDto.setGoodsIds(dto.getGoodsIds());
				List<Map<String,Object>> ggid=  cargoGoodsDao.getGoodsList(caDto, 0, 0);
                      for(int i=0;i<ggid.size();i++){
                    	 goodsGroupDao.removeGoodsFromGoodsGroup(Integer.valueOf(ggid.get(i).get("id").toString()),Integer.valueOf(ggid.get(i).get("goodsGroupId").toString()));
                      }	
                      if(!Common.empty(dto.getGoodsIds()))
                      goodsGroupDao.updateGoodsToGoodsGroup(dto.getGoodsIds(), goodsGroupId);
///****************************方法2*循环执行***********************************/                      
//                      for(int i=0;i<ids.length;i++){
//                    	  CargoGoodsDto cgDto=new CargoGoodsDto();
//        				  cgDto.setGoodsId(Integer.valueOf(ids[i]));
//                      Object gid=cargoGoodsDao.getGoodsList(cgDto, 0, 0).get(0).get("goodsGroupId");
//                      
//                      {
//				  if(Common.empty(goodsGroupId)||Common.isNull(Integer.valueOf(gid.toString()))){
//					  LOG.debug("没有货体组的");
//					  goodsGroupDao.updateGoodsToGoodsGroup(Integer.valueOf(ids[i]),goodsGroupId);
//				  }else{
//					  LOG.debug("有货体组的");
//					  goodsGroupDao.removeGoodsFromGoodsGroup(Integer.valueOf(ids[i]), Integer.valueOf(gid.toString()));
//					  goodsGroupDao.updateGoodsToGoodsGroup(Integer.valueOf(ids[i]),goodsGroupId);
//				  }}
				  
				
			  
//	/***************************方法1*针对指定了货体组，货体************************************/		  
//		if(Common.isNull(dto.getGoodsIds())){
//			LOG.debug("将不是货体组的货体添加到指定货体组里");
//			//TODO添加货体到货体组,加入updateGoodsids
//			String[] goodsids=dto.getGoodsGroupIds().split(",");
//			   for(int i=0;i<goodsids.length;i++){
//				   goodsGroupDao.addGoodsToGoodsGroup(Integer.valueOf(goodsids[i]),dto.getGoodsGroupId());
//			   }
//		}
//        if(Common.isNull(dto.getGoodsGroupIds())){
//        	LOG.debug("将整个货体组添加到货体组");
//                 //TODO 调用货体组添加货体组接口，获取对应货体ids(加入updateGoodsids),删除原来的货体组	
//        	String[] goodsgroupids=dto.getGoodsGroupIds().split(",");
//        	for(int i=0;i<goodsgroupids.length;i++){
//               goodsGroupDao.addGoodsGroupToGoodsGroup(Integer.valueOf(goodsgroupids[i]),dto.getGoodsGroupId());
//               goodsGroupDao.removeGoodsGroup(Integer.valueOf(goodsgroupids[i]));
//        	}
//        }    			  
	}catch (RuntimeException re) {
		LOG.error("查询失败", re);
		oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败", re);
	}
	return oaMsg;
	}
	
}
