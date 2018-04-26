package com.skycloud.oa.inbound.dto;

import com.skycloud.oa.annatation.Translation;

public class ArrivalDto {
	@Translation(name = "到港id")
private int id;//到港id
	@Translation(name = "船舶id")
private int shipId;//船id
	@Translation(name = "到港时间")
private String startTime;//到港时间
	@Translation(name = "到港结束时间")
private String endTime;//到港时间
	@Translation(name = "船代id")
private int shipAgentId;//船代id
	@Translation(name = "状态")
private int status;//状态
	@Translation(name = "船名")
private String shipName;
	@Translation(name = "类型")
private int type;//类型
	@Translation(name = "到港状态")
private int arrivalStatus;//到港状态
	@Translation(name = "船舶中文名")
private int shipRefId;//船舶中文名

private String shipRefName;//船舶中文名

private Integer arrivalInfoId;

private int clientId;//到港计划有关货主

private int productId;//到港计划有关货品

private int order;//排序


private String inboundCode;//入库单号


public String getShipRefName() {
	return shipRefName;
}
public void setShipRefName(String shipRefName) {
	this.shipRefName = shipRefName;
}
public String getInboundCode() {
	return inboundCode;
}
public void setInboundCode(String inboundCode) {
	this.inboundCode = inboundCode;
}
public int getOrder() {
	return order;
}
public void setOrder(int order) {
	this.order = order;
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
public int getShipRefId() {
	return shipRefId;
}
public void setShipRefId(int shipRefId) {
	this.shipRefId = shipRefId;
}
public int getArrivalStatus() {
	return arrivalStatus;
}
public void setArrivalStatus(int arrivalStatus) {
	this.arrivalStatus = arrivalStatus;
}
public Integer getArrivalInfoId() {
	return arrivalInfoId;
}
public void setArrivalInfoId(Integer arrivalInfoId) {
	this.arrivalInfoId = arrivalInfoId;
}
public int getType() {
	return type;
}
public void setType(int type) {
	this.type = type;
}
public String getShipName() {
	return shipName;
}
public void setShipName(String shipName) {
	this.shipName = shipName;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getShipId() {
	return shipId;
}
public void setShipId(int shipId) {
	this.shipId = shipId;
}
public String getStartTime() {
	return startTime;
}
public void setStartTime(String startTime) {
	this.startTime = startTime;
}
public String getEndTime() {
	return endTime;
}
public void setEndTime(String endTime) {
	this.endTime = endTime;
}
public int getShipAgentId() {
	return shipAgentId;
}
public void setShipAgentId(int shipAgentId) {
	this.shipAgentId = shipAgentId;
}
public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}

}
