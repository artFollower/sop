package com.skycloud.oa.order.dto;


public class GetIntentionDto {
private int id;
private String code;
private String title;
private int clientId;
private String clientName;
private int productId;
private String productName;
private int type;
private String quantity;
private String unitPrice;
private String totalPrice;
private String description;
private String salesUserId;
private int status;
private int workId;
private String createUserId;
private String createUserName;
private String createTime;
private String editUserId;
private String editUserName;
private String editTime;

public String getCreateUserName() {
	return createUserName;
}
public void setCreateUserName(String createUserName) {
	this.createUserName = createUserName;
}
public String getEditUserName() {
	return editUserName;
}
public void setEditUserName(String editUserName) {
	this.editUserName = editUserName;
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
public int getType() {
	return type;
}
public void setType(int type) {
	this.type = type;
}
public String getQuantity() {
	return quantity;
}
public void setQuantity(String quantity) {
	this.quantity = quantity;
}
public String getUnitPrice() {
	return unitPrice;
}
public void setUnitPrice(String unitPrice) {
	this.unitPrice = unitPrice;
}
public String getTotalPrice() {
	return totalPrice;
}
public void setTotalPrice(String totalPrice) {
	this.totalPrice = totalPrice;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public String getSalesUserId() {
	return salesUserId;
}
public void setSalesUserId(String salesUserId) {
	this.salesUserId = salesUserId;
}
public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}
public int getWorkId() {
	return workId;
}
public void setWorkId(int workId) {
	this.workId = workId;
}
public String getCreateUserId() {
	return createUserId;
}
public void setCreateUserId(String createUserId) {
	this.createUserId = createUserId;
}

public String getEditUserId() {
	return editUserId;
}
public void setEditUserId(String editUserId) {
	this.editUserId = editUserId;
}

public String getCreateTime() {
	return createTime;
}
public void setCreateTime(String createTime) {
	this.createTime = createTime;
}
public String getEditTime() {
	return editTime;
}
public void setEditTime(String editTime) {
	this.editTime = editTime;
}
public String getClientName() {
	return clientName;
}
public void setClientName(String clientName) {
	this.clientName = clientName;
}
public String getProductName() {
	return productName;
}
public void setProductName(String productName) {
	this.productName = productName;
}


}
