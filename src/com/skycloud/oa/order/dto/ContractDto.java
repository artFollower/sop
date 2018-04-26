package com.skycloud.oa.order.dto;

import com.skycloud.oa.annatation.Translation;

public class ContractDto {
private int id;
@Translation(name = "合同编号")
private String code;
@Translation(name = "标题")
private String title;
@Translation(name = "客户id")
private int clientId;
@Translation(name = "货品id")
private int productId;
@Translation(name = "类型")
private Integer type; 
@Translation(name = "状态")
private Integer status;
@Translation(name = "时间类型")
private int timeType;
@Translation(name = "时间范围")
private int timeRange;
@Translation(name = "开始时间")
private String startTime;
@Translation(name = "结束时间")
private String endTime;
@Translation(name = "text")
private String text;
@Translation(name = "类型")
private String types;


public String getTypes() {
	return types;
}
public void setTypes(String types) {
	this.types = types;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
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


public Integer getType() {
	return type;
}
public void setType(Integer type) {
	this.type = type;
}
public Integer getStatus() {
	return status;
}
public void setStatus(Integer status) {
	this.status = status;
}
public int getTimeType() {
	return timeType;
}
public void setTimeType(int timeType) {
	this.timeType = timeType;
}
public int getTimeRange() {
	return timeRange;
}
public void setTimeRange(int timeRange) {
	this.timeRange = timeRange;
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

}
