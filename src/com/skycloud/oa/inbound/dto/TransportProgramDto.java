package com.skycloud.oa.inbound.dto;

import java.sql.Timestamp;

import com.skycloud.oa.annatation.Translation;

/**
 * 入库作业Dto
 * @author jiahy
 *
 */
public class TransportProgramDto {
	@Translation(name = "到港id")
public Integer id;//到港信息表id
	@Translation(name = "船舶id")
public String  shipId;//船舶id
	@Translation(name = "货品id")
public Integer productId;//货品id
	@Translation(name = "是否搜索货品")
public boolean isItemProduct;//是否是流程搜索货品
	@Translation(name = "起始时间")
public String startTime;//搜索起始时间
	@Translation(name = "截止日期")
public String endTime;//搜索截止时间
	@Translation(name = "作业id")
public Integer workId;//入库作业id
	@Translation(name = "操作id")
public Integer transportId;//
	@Translation(name = "管线关联id")
public String transportIds;//管线关联ids
	@Translation(name = "状态")
public String statuskey;//// 0--全部，1--作业计划，2--靠泊评估，3--靠泊方案，4---接卸方案，5--接卸准备，6--打回流方案，7--打回流准备 8--数量确认
	@Translation(name = "审批人")
	public Integer reviewArrivalUserId;//到港审批人
	@Translation(name = "审批时间")
public Long reviewArrivalTime;//到港审批时间
/**************************************/
	
	@Translation(name = "入库列表结果")
public Integer result=0;//获取的结果集 0---入库列表
	@Translation(name = "基础信息")
public Integer baseResult=-1;//获取基础表信息
	@Translation(name = "到港id")
public Integer arrivalId;//到港id
	@Translation(name = "货体状态")
public String  cargoGoodsStatus;//货体的状态0，确认的1，生产运行部处理的
	@Translation(name = "类型")
public Integer type;//transport类型

public Integer getType() {
	return type;
}
public void setType(Integer type) {
	this.type = type;
}
public Integer getArrivalId() {
	return arrivalId;
}
public void setArrivalId(Integer arrivalId) {
	this.arrivalId = arrivalId;
}
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getShipId() {
	return shipId;
}
public void setShipId(String shipId) {
	this.shipId = shipId;
}
public Integer getProductId() {
	return productId;
}
public void setProductId(Integer productId) {
	this.productId = productId;
}
public boolean isItemProduct() {
	return isItemProduct;
}
public void setItemProduct(boolean isItemProduct) {
	this.isItemProduct = isItemProduct;
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
public Integer getWorkId() {
	return workId;
}
public void setWorkId(Integer workId) {
	this.workId = workId;
}
public Integer getTransportId() {
	return transportId;
}
public void setTransportId(Integer transportId) {
	this.transportId = transportId;
}
public String getTransportIds() {
	return transportIds;
}
public void setTransportIds(String transportIds) {
	this.transportIds = transportIds;
}
public String getStatuskey() {
	return statuskey;
}
public void setStatuskey(String statuskey) {
	this.statuskey = statuskey;
}
public Integer getReviewArrivalUserId() {
	return reviewArrivalUserId;
}
public void setReviewArrivalUserId(Integer reviewArrivalUserId) {
	this.reviewArrivalUserId = reviewArrivalUserId;
}
public Long getReviewArrivalTime() {
	return reviewArrivalTime;
}
public void setReviewArrivalTime(Long reviewArrivalTime) {
	this.reviewArrivalTime = reviewArrivalTime;
}
public Integer getResult() {
	return result;
}
public void setResult(Integer result) {
	this.result = result;
}
public Integer getBaseResult() {
	return baseResult;
}
public void setBaseResult(Integer baseResult) {
	this.baseResult = baseResult;
}
public String getCargoGoodsStatus() {
	return cargoGoodsStatus;
}
public void setCargoGoodsStatus(String cargoGoodsStatus) {
	this.cargoGoodsStatus = cargoGoodsStatus;
}

}
