package com.skycloud.oa.inbound.dto;

import com.skycloud.oa.annatation.Translation;

public class ArrivalWorkDto {
	@Translation(name = "货批id")
	private int cargoId;//货批id
	@Translation(name = "合同id")
	private int contractId;//合同id
	@Translation(name = "产地")
	private String originalArea;//产地
	@Translation(name = "到港id")
	private int arrivalId;//到港信息
	@Translation(name = "总量")
	private String goodsTatal;//预计总量
	@Translation(name = "船代id")
	private int shipAgentId;//船代id
	@Translation(name = "仓储要求")
	private int requirement;//仓储要求
	public int getCargoId() {
		return cargoId;
	}
	public void setCargoId(int cargoId) {
		this.cargoId = cargoId;
	}
	public int getContractId() {
		return contractId;
	}
	public void setContractId(int contractId) {
		this.contractId = contractId;
	}
	public String getOriginalArea() {
		return originalArea;
	}
	public void setOriginalArea(String originalArea) {
		this.originalArea = originalArea;
	}
	public int getArrivalId() {
		return arrivalId;
	}
	public void setArrivalId(int arrivalId) {
		this.arrivalId = arrivalId;
	}
	public String getGoodsTatal() {
		return goodsTatal;
	}
	public void setGoodsTatal(String goodsTatal) {
		this.goodsTatal = goodsTatal;
	}
	public int getShipAgentId() {
		return shipAgentId;
	}
	public void setShipAgentId(int shipAgentId) {
		this.shipAgentId = shipAgentId;
	}
	public int getRequirement() {
		return requirement;
	}
	public void setRequirement(int requirement) {
		this.requirement = requirement;
	}
	
}
