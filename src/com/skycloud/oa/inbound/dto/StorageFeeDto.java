package com.skycloud.oa.inbound.dto;

import java.math.BigDecimal;

public class StorageFeeDto {
	private int productId ;
	private int clientId ;
	private int goodsId ;
	private int type ;
	private String startTime ;
	private String recordTime ;
	private String endTime ;
	private String feeType ;
	private BigDecimal storageFee ;
	private BigDecimal goodsInspect ;
	private Integer status;
	
	
	public BigDecimal getGoodsInspect() {
		return goodsInspect;
	}
	public void setGoodsInspect(BigDecimal goodsInspect) {
		this.goodsInspect = goodsInspect;
	}
	public BigDecimal getStorageFee() {
		return storageFee;
	}
	public void setStorageFee(BigDecimal storageFee) {
		this.storageFee = storageFee;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public String getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
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
	private int goodsAccountStatus ;
	
	public int getGoodsAccountStatus() {
		return goodsAccountStatus;
	}
	public void setGoodsAccountStatus(int goodsAccountStatus) {
		this.goodsAccountStatus = goodsAccountStatus;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}