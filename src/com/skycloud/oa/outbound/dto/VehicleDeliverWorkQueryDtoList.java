package com.skycloud.oa.outbound.dto;

import java.util.List;

import com.skycloud.oa.outbound.model.Approve;
import com.skycloud.oa.outbound.model.DeliveryGoodsRecord;
import com.skycloud.oa.outbound.model.GoodsLog;
import com.skycloud.oa.outbound.model.Measure;
import com.skycloud.oa.outbound.model.Train;
import com.skycloud.oa.outbound.model.WeighBridge;

public class VehicleDeliverWorkQueryDtoList {
	private List<VehicleDeliverWorkQueryDto> vdwq ; //查询字段
	private Approve approve ; //审批信息记录
	private List<DeliveryGoodsRecord> goodsDto ; //发货记录
	private Train train ;	//车发对象
	private List<GoodsLog> goodsLoglist ; //货体日志记录
	private WeighBridge wb ;    //称重数据
	private Measure me	; 	//计量数据
	private int flag ;		//称重1 计量2
	
	
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Measure getMe() {
		return me;
	}

	public void setMe(Measure me) {
		this.me = me;
	}

	public WeighBridge getWb() {
		return wb;
	}

	public void setWb(WeighBridge wb) {
		this.wb = wb;
	}

	public List<GoodsLog> getGoodsLoglist() {
		return goodsLoglist;
	}

	public void setGoodsLoglist(List<GoodsLog> goodsLoglist) {
		this.goodsLoglist = goodsLoglist;
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	public List<DeliveryGoodsRecord> getGoodsDto() {
		return goodsDto;
	}

	public void setGoodsDto(List<DeliveryGoodsRecord> goodsDto) {
		this.goodsDto = goodsDto;
	}

	public Approve getApprove() {
		return approve;
	}

	public void setApprove(Approve approve) {
		this.approve = approve;
	}

	public List<VehicleDeliverWorkQueryDto> getVdwq() {
		return vdwq;
	}

	public void setVdwq(List<VehicleDeliverWorkQueryDto> vdwq) {
		this.vdwq = vdwq;
	}
}
