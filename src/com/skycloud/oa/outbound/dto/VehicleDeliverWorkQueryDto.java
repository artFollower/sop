package com.skycloud.oa.outbound.dto;

import java.util.Date;

public class VehicleDeliverWorkQueryDto {
	private String clientName ;
	private Date planStartTime ;
	private Date planEndTime ;
	private String productName ;
	private String ladingCode ;
	private String ladingType ;
	private String clientId ;
	private String productId ;
	private String ladingId ;
	private double goodsPass = 0.0 ;
	private String trainId ;
	private double deliverNum = 0.0 ;
	private String plateId ;
	private String status ;
	private int vehicleInfoId ;
	private int zeroFlag ;
	
	/**
	 * 开始日期
	 */
	private String startTime;
	
	/**
	 * 结束日期
	 */
	private String endTime;
	
	/**
	 * 单号
	 */
	private String serialNum;
	
	public int getZeroFlag() {
		return zeroFlag;
	}

	public void setZeroFlag(int zeroFlag) {
		this.zeroFlag = zeroFlag;
	}

	public int getVehicleInfoId() {
		return vehicleInfoId;
	}

	public void setVehicleInfoId(int vehicleInfoId) {
		this.vehicleInfoId = vehicleInfoId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPlateId() {
		return plateId;
	}

	public void setPlateId(String plateId) {
		this.plateId = plateId;
	}

	public double getDeliverNum() {
		return deliverNum;
	}

	public void setDeliverNum(double deliverNum) {
		this.deliverNum = deliverNum;
	}

	public String getTrainId() {
		return trainId;
	}

	public void setTrainId(String trainId) {
		this.trainId = trainId;
	}

	public String getLadingId() {
		return ladingId;
	}

	public void setLadingId(String ladingId) {
		this.ladingId = ladingId;
	}

	public double getGoodsPass() {
		return goodsPass;
	}

	public void setGoodsPass(double goodsPass) {
		this.goodsPass = goodsPass;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the ladingCode
	 */
	public String getLadingCode() {
		return ladingCode;
	}

	/**
	 * @param ladingCode the ladingCode to set
	 */
	public void setLadingCode(String ladingCode) {
		this.ladingCode = ladingCode;
	}

	/**
	 * @return the ladingType
	 */
	public String getLadingType() {
		return ladingType;
	}

	/**
	 * @param ladingType the ladingType to set
	 */
	public void setLadingType(String ladingType) {
		this.ladingType = ladingType;
	}

	public Date getPlanStartTime() {
		return planStartTime;
	}

	public void setPlanStartTime(Date planStartTime) {
		this.planStartTime = planStartTime;
	}

	public Date getPlanEndTime() {
		return planEndTime;
	}

	public void setPlanEndTime(Date planEndTime) {
		this.planEndTime = planEndTime;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	/**
	 * @get方法:String
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @set方法:String
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @get方法:String
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @set方法:String
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @get方法:String
	 * @return the serialNum
	 */
	public String getSerialNum() {
		return serialNum;
	}

	/**
	 * @set方法:String
	 * @param serialNum the serialNum to set
	 */
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	
}
