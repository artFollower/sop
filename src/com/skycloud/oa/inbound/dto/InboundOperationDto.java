package com.skycloud.oa.inbound.dto;

import java.sql.Time;
import java.sql.Timestamp;

import com.skycloud.oa.annatation.Translation;

/**
 * 入库作业Dto
 * @author jiahy
 *
 */
public class InboundOperationDto {
	@Translation(name = "到港id")
public Integer id;//到港信息表id
	@Translation(name = "类型")
public Integer arrivalType;
	@Translation(name = "船舶id")
public Integer  shipId;//船舶id
	@Translation(name = "船名")
public String shipName;//船名
	@Translation(name = "货品id")
public Integer productId;//货品id
	@Translation(name = "多次接卸次序")
public Integer orderNum;//多次接卸次序
	@Translation(name = "是否是流程搜索货品")
public boolean isItemProduct;//是否是流程搜索货品
	@Translation(name = "起始时间")
public String startTime;//搜索起始时间
	@Translation(name = "截止时间")
public String endTime;//搜索截止时间
	@Translation(name = "入库作业id")
public Integer workId;//入库作业id
	@Translation(name = "接卸方案id")
public Integer transportId;//接卸
	@Translation(name = "打循环id")
public Integer backflowId;//打循环
	@Translation(name = "管线关联id")
public String transportIds;//管线关联ids
	@Translation(name = "流程状态")
public String statuskey;//// 0--全部，1--作业计划，2--靠泊评估，3--靠泊方案，4---接卸方案，5--接卸准备，6--打回流方案，7--打回流准备 8--数量确认
	@Translation(name = "到港审批人")
public Integer reviewArrivalUserId;//到港审批人
	@Translation(name = "到港审批时间")
public Long reviewArrivalTime;//到港审批时间
	@Translation(name = "获取的结果集")
public Integer result=0;//获取的结果集 0---入库列表
	@Translation(name = "获取基础表信息")
public Integer baseResult=-1;//获取基础表信息
	@Translation(name = "到港id")
public Integer arrivalId;//到港id
	@Translation(name = "货体的状态")
public String  cargoGoodsStatus;//货体的状态0，确认的1，生产运行部处理的
	@Translation(name = "多次接卸")
public Integer  unloadingType;// 1.多次接卸2.多次打循环
	@Translation(name = "是否是转输输入")
public Integer isTransport;//是否是转输输入
	@Translation(name = "靠泊方案id")
public Integer berthprogramId;//靠泊方案id
	@Translation(name = "泊位id")
public Integer berthId;//泊位id
	@Translation(name = "是否生成调度日志")
public Integer isMakeDispatchLog;//是否生成调度日志
	@Translation(name = "到港时间")
public Long arrivalTime;//到港时间
	@Translation(name = "到港时间")
public Timestamp arrivalStartTime;//到港时间
	@Translation(name = "申报状态")
public String report;//申报状态
	@Translation(name = "船舶吃水")
public Float shipArrivalDraught;//船舶吃水
	@Translation(name = "船代id")
public Integer shipAgentId;//船代id
	@Translation(name = "船舶中文名")
public String shipRefName;//船舶中文名
	@Translation(name = "货批id")
public Integer cargoId;
public InboundOperationDto() {
	super();
	// TODO Auto-generated constructor stub
}

public InboundOperationDto(Integer id, Integer arrivalType, Integer shipId, String shipName, Integer productId,
		Integer orderNum, boolean isItemProduct, String startTime, String endTime, Integer workId, Integer transportId,
		Integer backflowId, String transportIds, String statuskey, Integer reviewArrivalUserId, Long reviewArrivalTime,
		Integer result, Integer baseResult, Integer arrivalId, String cargoGoodsStatus, Integer unloadingType,
		Integer isTransport, Integer berthprogramId, Integer berthId, Integer isMakeDispatchLog, Long arrivalTime,
		Timestamp arrivalStartTime, String report, Float shipArrivalDraught, Integer shipAgentId, String shipRefName) {
	super();
	this.id = id;
	this.arrivalType = arrivalType;
	this.shipId = shipId;
	this.shipName = shipName;
	this.productId = productId;
	this.orderNum = orderNum;
	this.isItemProduct = isItemProduct;
	this.startTime = startTime;
	this.endTime = endTime;
	this.workId = workId;
	this.transportId = transportId;
	this.backflowId = backflowId;
	this.transportIds = transportIds;
	this.statuskey = statuskey;
	this.reviewArrivalUserId = reviewArrivalUserId;
	this.reviewArrivalTime = reviewArrivalTime;
	this.result = result;
	this.baseResult = baseResult;
	this.arrivalId = arrivalId;
	this.cargoGoodsStatus = cargoGoodsStatus;
	this.unloadingType = unloadingType;
	this.isTransport = isTransport;
	this.berthprogramId = berthprogramId;
	this.berthId = berthId;
	this.isMakeDispatchLog = isMakeDispatchLog;
	this.arrivalTime = arrivalTime;
	this.arrivalStartTime = arrivalStartTime;
	this.report = report;
	this.shipArrivalDraught = shipArrivalDraught;
	this.shipAgentId = shipAgentId;
	this.shipRefName = shipRefName;
}

public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public Integer getArrivalType() {
	return arrivalType;
}
public void setArrivalType(Integer arrivalType) {
	this.arrivalType = arrivalType;
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
public Integer getProductId() {
	return productId;
}
public void setProductId(Integer productId) {
	this.productId = productId;
}
public Integer getOrderNum() {
	return orderNum;
}
public void setOrderNum(Integer orderNum) {
	this.orderNum = orderNum;
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
public Integer getBackflowId() {
	return backflowId;
}
public void setBackflowId(Integer backflowId) {
	this.backflowId = backflowId;
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
public Integer getArrivalId() {
	return arrivalId;
}
public void setArrivalId(Integer arrivalId) {
	this.arrivalId = arrivalId;
}
public String getCargoGoodsStatus() {
	return cargoGoodsStatus;
}
public void setCargoGoodsStatus(String cargoGoodsStatus) {
	this.cargoGoodsStatus = cargoGoodsStatus;
}
public Integer getUnloadingType() {
	return unloadingType;
}
public void setUnloadingType(Integer unloadingType) {
	this.unloadingType = unloadingType;
}
public Integer getIsTransport() {
	return isTransport;
}
public void setIsTransport(Integer isTransport) {
	this.isTransport = isTransport;
}
public Integer getBerthprogramId() {
	return berthprogramId;
}
public void setBerthprogramId(Integer berthprogramId) {
	this.berthprogramId = berthprogramId;
}
public Integer getBerthId() {
	return berthId;
}
public void setBerthId(Integer berthId) {
	this.berthId = berthId;
}
public Integer getIsMakeDispatchLog() {
	return isMakeDispatchLog;
}
public void setIsMakeDispatchLog(Integer isMakeDispatchLog) {
	this.isMakeDispatchLog = isMakeDispatchLog;
}
public Long getArrivalTime() {
	return arrivalTime;
}
public void setArrivalTime(Long arrivalTime) {
	this.arrivalTime = arrivalTime;
}
public Timestamp getArrivalStartTime() {
	return arrivalStartTime;
}
public void setArrivalStartTime(Timestamp arrivalStartTime) {
	this.arrivalStartTime = arrivalStartTime;
}
public String getReport() {
	return report;
}
public void setReport(String report) {
	this.report = report;
}
public Float getShipArrivalDraught() {
	return shipArrivalDraught;
}
public void setShipArrivalDraught(Float shipArrivalDraught) {
	this.shipArrivalDraught = shipArrivalDraught;
}
public Integer getShipAgentId() {
	return shipAgentId;
}
public void setShipAgentId(Integer shipAgentId) {
	this.shipAgentId = shipAgentId;
}
public String getShipRefName() {
	return shipRefName;
}
public void setShipRefName(String shipRefName) {
	this.shipRefName = shipRefName;
}

public Integer getCargoId() {
	return cargoId;
}

public void setCargoId(Integer cargoId) {
	this.cargoId = cargoId;
}



}
