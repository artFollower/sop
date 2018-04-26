/**
 * @Title:TradeInvoiceDto.java
 * @Package com.skycloud.oa.inbound.dto
 * @Description TODO
 * @autor jiahy
 * @date 2016年4月8日上午8:28:49
 * @version V1.0
 */
package com.skycloud.oa.outbound.dto;

/**
 * @ClassName TradeInvoiceDto
 * @Description TODO
 * @author jiahy
 * @date 2016年4月8日上午8:28:49
 */
public class TradeInvoiceDto {
private Integer productId;
private Integer clientId;
private String serial;
private Integer goodsId;
private Long startTime;
private Long endTime;
private String goodsLogIds;//冲销或更换货体的goodsLogId
private String remark;
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
public String getSerial() {
	return serial;
}
public void setSerial(String serial) {
	this.serial = serial;
}
public Integer getGoodsId() {
	return goodsId;
}
public void setGoodsId(Integer goodsId) {
	this.goodsId = goodsId;
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
public String getGoodsLogIds() {
	return goodsLogIds;
}
public void setGoodsLogIds(String goodsLogIds) {
	this.goodsLogIds = goodsLogIds;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}

}
