package com.skycloud.oa.inbound.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.CargoGoodsDto;
import com.skycloud.oa.inbound.model.GoodsGroup;
import com.skycloud.oa.utils.OaMsg;

public interface GoodsGroupService {

	
	public OaMsg addgoodsGroup(GoodsGroup goodsGroup) throws OAException;
	
	public OaMsg addToLading(CargoGoodsDto dto) throws OAException;
	
}
