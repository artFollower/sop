package com.skycloud.oa.inbound.dto;

import com.skycloud.oa.annatation.Translation;


public class InsertSingleArrivalPlanDto {
	@Translation(name = "到港id")
private int arrivalId;
	@Translation(name = "客户id")
private int clientId;
	@Translation(name = "货品id")
private int productId;
	@Translation(name = "总量")
private String goodsTotal;
	@Translation(name = "仓储需求")
private String requirement;
	@Translation(name = "创建用户id")
private int createUserId;
	@Translation(name = "贸易类型")
private int tradeType;
	@Translation(name = "是否申报海关")
private Integer isDeclareCustom;//是否申报海关
	@Translation(name = "海关是否同意卸货")
private Integer isCustomAgree;//海关是否同意卸货
	@Translation(name = "海运提单号")
private String customLading;//海运提单号
	@Translation(name = "海运提单数量")
private String customLadingCount;//海运提单数量

public Integer getIsDeclareCustom() {
	return isDeclareCustom;
}
public void setIsDeclareCustom(Integer isDeclareCustom) {
	this.isDeclareCustom = isDeclareCustom;
}
public Integer getIsCustomAgree() {
	return isCustomAgree;
}
public void setIsCustomAgree(Integer isCustomAgree) {
	this.isCustomAgree = isCustomAgree;
}
public String getCustomLading() {
	return customLading;
}
public void setCustomLading(String customLading) {
	this.customLading = customLading;
}
public String getCustomLadingCount() {
	return customLadingCount;
}
public void setCustomLadingCount(String customLadingCount) {
	this.customLadingCount = customLadingCount;
}
public int getTradeType() {
	return tradeType;
}
public void setTradeType(int tradeType) {
	this.tradeType = tradeType;
}
public int getCreateUserId() {
	return createUserId;
}
public void setCreateUserId(int createUserId) {
	this.createUserId = createUserId;
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
public String getRequirement() {
	return requirement;
}
public void setRequirement(String requirement) {
	this.requirement = requirement;
}


}
