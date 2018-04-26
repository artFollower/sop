package com.skycloud.oa.inbound.dto;

import com.skycloud.oa.annatation.Translation;


public class ArrivalPlanDto {
	@Translation(name = "到港id")
private int arrivalId;
	@Translation(name = "客户id")
private int clientId;
	@Translation(name = "货品id")
private int productId;
	@Translation(name = "货体id")
private Integer goodsId;
	@Translation(name = "货体总量")
private String goodsTotal;
	@Translation(name = "船舶id")
private int shipId;
	@Translation(name = "状态")
private int status;
	@Translation(name = "计划id")
private int planId;
	@Translation(name = "货批id")
private int cargoId;

public int getCargoId() {
	return cargoId;
}
public void setCargoId(int cargoId) {
	this.cargoId = cargoId;
}
public int getPlanId() {
	return planId;
}
public void setPlanId(int planId) {
	this.planId = planId;
}
public int getArrivalId() {
	return arrivalId;
}
public void setArrivalId(int arrivalId) {
	this.arrivalId = arrivalId;
}
public int getClientId() {
	return clientId;
}
public void setClientId(int clientId) {
	this.clientId = clientId;
}
public int getProductId() {
	return productId;
}
public void setProductId(int productId) {
	this.productId = productId;
}
public String getGoodsTotal() {
	return goodsTotal;
}
public void setGoodsTotal(String goodsTotal) {
	this.goodsTotal = goodsTotal;
}
public int getShipId() {
	return shipId;
}
public void setShipId(int shipId) {
	this.shipId = shipId;
}
public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}
public Integer getGoodsId() {
	return goodsId;
}
public void setGoodsId(Integer goodsId) {
	this.goodsId = goodsId;
}



}
