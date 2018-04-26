package com.skycloud.oa.inbound.dto;

import java.util.List;

import com.skycloud.oa.annatation.Translation;
import com.skycloud.oa.feebill.model.DockFeeCharge;
import com.skycloud.oa.inbound.model.DockFee;

public class DockFeeDto {
	private Integer id;
	@Translation(name = "码头规费ids")
	private String ids;
	@Translation(name = "到港id")
	private Integer arrivalId;
	@Translation(name = "船名id")
	private Integer shipId;//船名
	@Translation(name = "船名")
	private String shipName;//船名
	@Translation(name = "结算单编号")
	private String code;//编号
	@Translation(name = "发生单位")
	private String clientName;//发生单位
	@Translation(name = "开始时间")
	private Long startTime;
	@Translation(name = "结束时间")
	private Long endTime;
	@Translation(name = "当前时间")
	private String nowTime;
	@Translation(name = "到港类型")
	private Integer arrivalType;
	@Translation(name = "发票类型")
	private Integer billType;
	@Translation(name = "码头规费")
	private DockFee dockfee;
	@Translation(name = "费用项列表")
	private List<DockFeeCharge> feeChargeList;
	@Translation(name = "状态")
	private Integer status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	public Integer getArrivalId() {
		return arrivalId;
	}
	public void setArrivalId(Integer arrivalId) {
		this.arrivalId = arrivalId;
	}
	public Integer getShipId() {
		return shipId;
	}
	public void setShipId(Integer shipId) {
		this.shipId = shipId;
	}
	
	public String getShipName() {
		return shipName;
	}
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public String getNowTime() {
		return nowTime;
	}
	public void setNowTime(String nowTime) {
		this.nowTime = nowTime;
	}
	public Integer getArrivalType() {
		return arrivalType;
	}
	public void setArrivalType(Integer arrivalType) {
		this.arrivalType = arrivalType;
	}
	
	public Integer getBillType() {
		return billType;
	}
	public void setBillType(Integer billType) {
		this.billType = billType;
	}
	public DockFee getDockfee() {
		return dockfee;
	}
	public void setDockfee(DockFee dockfee) {
		this.dockfee = dockfee;
	}
	public List<DockFeeCharge> getFeeChargeList() {
		return feeChargeList;
	}
	public void setFeeChargeList(List<DockFeeCharge> feeChargeList) {
		this.feeChargeList = feeChargeList;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
