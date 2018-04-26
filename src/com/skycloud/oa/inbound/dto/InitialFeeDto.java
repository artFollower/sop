package com.skycloud.oa.inbound.dto;

import java.util.List;

import com.skycloud.oa.annatation.Translation;
import com.skycloud.oa.feebill.model.FeeCharge;
import com.skycloud.oa.inbound.model.InitialFee;

public class InitialFeeDto {
	private Integer id;
	@Translation(name = "首期费ids")
	private String ids;
	@Translation(name = "首期费编号")
	private String initialCode;
	@Translation(name = "首期费类型")
	private Integer type;
	@Translation(name = "货批id")
	private Integer cargoId;
	@Translation(name = "货批号")
	private String cargoCode;
	@Translation(name = "商检量")
	private Float goodsInspect;
	@Translation(name = "合同id")
	private Integer contractId;
	@Translation(name = "合同类型")
	private String contractType;
	@Translation(name = "船舶名")
	private String shipName;
	@Translation(name = "开始时间")
	private Long startTime;
	@Translation(name = "截止时间")
	private Long endTime;
	@Translation(name = "货品id")
	private Integer productId;
	@Translation(name = "货主id")
	private Integer clientId;
	@Translation(name = "是否结清")
	private Integer isFinish;
	@Translation(name = "列号")
	private Integer indexTh;
	private Integer deliverType;
	@Translation(name = "到港id")
	private Integer arrivalId;
	@Translation(name = "首期费")
	private InitialFee initialFee;
	@Translation(name = "费用项列表")
	private List<FeeCharge> feechargelist;
	@Translation(name = "贸易类型")
	private Integer tradeType;
	@Translation(name = "结算状态")
	private Integer status;
	public InitialFeeDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
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
	public String getInitialCode() {
		return initialCode;
	}
	public void setInitialCode(String initialCode) {
		this.initialCode = initialCode;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getCargoId() {
		return cargoId;
	}
	public void setCargoId(Integer cargoId) {
		this.cargoId = cargoId;
	}
	public String getCargoCode() {
		return cargoCode;
	}
	public void setCargoCode(String cargoCode) {
		this.cargoCode = cargoCode;
	}
	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public Float getGoodsInspect() {
		return goodsInspect;
	}
	public void setGoodsInspect(Float goodsInspect) {
		this.goodsInspect = goodsInspect;
	}
	public Integer getContractId() {
		return contractId;
	}
	public void setContractId(Integer contractId) {
		this.contractId = contractId;
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
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public Integer getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(Integer isFinish) {
		this.isFinish = isFinish;
	}
	public Integer getIndexTh() {
		return indexTh;
	}
	public void setIndexTh(Integer indexTh) {
		this.indexTh = indexTh;
	}
	public Integer getDeliverType() {
		return deliverType;
	}
	public void setDeliverType(Integer deliverType) {
		this.deliverType = deliverType;
	}
	public InitialFee getInitialFee() {
		return initialFee;
	}
	public void setInitialFee(InitialFee initialFee) {
		this.initialFee = initialFee;
	}
	
	public Integer getArrivalId() {
		return arrivalId;
	}

	public void setArrivalId(Integer arrivalId) {
		this.arrivalId = arrivalId;
	}

	public List<FeeCharge> getFeechargelist() {
		return feechargelist;
	}
	public void setFeechargelist(List<FeeCharge> feechargelist) {
		this.feechargelist = feechargelist;
	}

	public Integer getTradeType() {
		return tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
