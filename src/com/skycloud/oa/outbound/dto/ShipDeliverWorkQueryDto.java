package com.skycloud.oa.outbound.dto;

import java.util.List;

import com.skycloud.oa.annatation.Translation;
import com.skycloud.oa.inbound.model.ArrivalPlan;
import com.skycloud.oa.inbound.model.Store;
import com.skycloud.oa.inbound.model.TransportProgram;
import com.skycloud.oa.inbound.model.Work;
import com.skycloud.oa.inbound.model.WorkCheck;
import com.skycloud.oa.outbound.model.Approve;
import com.skycloud.oa.outbound.model.DeliveryGoodsRecord;
import com.skycloud.oa.outbound.model.DeliveryShip;
import com.skycloud.oa.outbound.model.GoodsLog;

public class ShipDeliverWorkQueryDto {
	@Translation(name = "")
	private int arrivalId=0 ;
	@Translation(name = "")
	private DeliveryShip deliveryShip ;
	@Translation(name = "")
	private List<Store> storeList ;
	@Translation(name = "")
	private Work work ;
	@Translation(name = "")
	private List<WorkCheck> workCheckList ;
	@Translation(name = "")
	private List<ArrivalPlan> planList ;
	@Translation(name = "")
	private Approve approve ;
	@Translation(name = "")
	private TransportProgram t ;
	@Translation(name = "")
	private List<DeliveryGoodsRecord> goodsDto ;
	@Translation(name = "")
	private List<GoodsLog> goodsLoglist ;
	@Translation(name = "")
	private int status ;
	@Translation(name = "")
	private String description ;
	@Translation(name = "")
	private String fileName ;
	@Translation(name = "")
	private boolean isChange;//船发是否修改实发量
	@Translation(name = "选择时间")
	private Long choiceTime;////修改出库时间对应的时间点
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<GoodsLog> getGoodsLoglist() {
		return goodsLoglist;
	}

	public void setGoodsLoglist(List<GoodsLog> goodsLoglist) {
		this.goodsLoglist = goodsLoglist;
	}

	public List<DeliveryGoodsRecord> getGoodsDto() {
		return goodsDto;
	}

	public void setGoodsDto(List<DeliveryGoodsRecord> goodsDto) {
		this.goodsDto = goodsDto;
	}

	public TransportProgram getT() {
		return t;
	}

	public void setT(TransportProgram t) {
		this.t = t;
	}

	public Approve getApprove() {
		return approve;
	}

	public void setApprove(Approve approve) {
		this.approve = approve;
	}


	public List<ArrivalPlan> getPlanList() {
		return planList;
	}

	public void setPlanList(List<ArrivalPlan> planList) {
		this.planList = planList;
	}

	public int getArrivalId() {
		return arrivalId;
	}

	public void setArrivalId(int arrivalId) {
		this.arrivalId = arrivalId;
	}
	public DeliveryShip getDeliveryShip() {
		return deliveryShip;
	}

	public void setDeliveryShip(DeliveryShip deliveryShip) {
		this.deliveryShip = deliveryShip;
	}
	public Work getWork() {
		return work;
	}

	public void setWork(Work work) {
		this.work = work;
	}


	public List<WorkCheck> getWorkCheckList() {
		return workCheckList;
	}

	public void setWorkCheckList(List<WorkCheck> workCheckList) {
		this.workCheckList = workCheckList;
	}

	public List<Store> getStoreList() {
		return storeList;
	}

	public void setStoreList(List<Store> storeList) {
		this.storeList = storeList;
	}

	public boolean isChange() {
		return isChange;
	}

	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}

	public Long getChoiceTime() {
		return choiceTime;
	}

	public void setChoiceTime(Long choiceTime) {
		this.choiceTime = choiceTime;
	}
	
}
