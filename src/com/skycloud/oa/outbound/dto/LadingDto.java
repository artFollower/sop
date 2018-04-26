package com.skycloud.oa.outbound.dto;

import com.skycloud.oa.annatation.Translation;

public class LadingDto {
private int id;
@Translation(name = "代码")
private String code;
@Translation(name = "客户id")
private int clientId;
@Translation(name = "客户名称")
private String clientName;
@Translation(name = "货品id")
private int productId;
@Translation(name = "收货人id")
private Integer buyClientId;
@Translation(name = "类型")
private int type;
@Translation(name = "状态")
private String status;
@Translation(name = "开始时间")
private String startTime;
@Translation(name = "结束时间")
private String endTime;
@Translation(name = "货体id")
private String goodsIds;
@Translation(name = "货体组id")
private Integer goodsGroupId;//提单的货体组id
@Translation(name = "提单类型")
private Integer ladingStatus;//提单类型1.货体转卖2.提货权转卖
@Translation(name = "货体id")
private Integer goodsId;

public Integer getGoodsId() {
	return goodsId;
}
public void setGoodsId(Integer goodsId) {
	this.goodsId = goodsId;
}
public String getClientName() {
	return clientName;
}
public void setClientName(String clientName) {
	this.clientName = clientName;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
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
public int getType() {
	return type;
}
public void setType(int type) {
	this.type = type;
}

public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
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
public String getGoodsIds() {
	return goodsIds;
}
public void setGoodsIds(String goodsIds) {
	this.goodsIds = goodsIds;
}
public Integer getGoodsGroupId() {
	return goodsGroupId;
}
public void setGoodsGroupId(Integer goodsGroupId) {
	this.goodsGroupId = goodsGroupId;
}
public Integer getBuyClientId() {
	return buyClientId;
}
public void setBuyClientId(Integer buyClientId) {
	this.buyClientId = buyClientId;
}
public Integer getLadingStatus() {
	return ladingStatus;
}
public void setLadingStatus(Integer ladingStatus) {
	this.ladingStatus = ladingStatus;
}


}
