package com.skycloud.oa.outbound.dto;

public class VehicleDeliveryStatementDto {
	private String date ;
	private int parkId ;
	private int productId;
	private int tankId ;
	private Long startTime;
	private Long endTime;
	private String inPort;
	private String year;
	private String month;
	private String dateTime;
	private Integer isShowOutbound;//1 不显示。2显示
	private Integer isShowTruck;//1 不显示。2显示
	private Integer timeType;//时间类型
	private Integer clientId;//货主
	public VehicleDeliveryStatementDto() {
	}
	public VehicleDeliveryStatementDto(Integer productId, String dateTime) {
		this.productId=productId;
		this.dateTime=dateTime;
	}
	public VehicleDeliveryStatementDto(String inPort, String dateTime, Integer productId) {
		this.inPort=inPort;
		this.dateTime=dateTime;
		this.productId=productId;
	}
	public VehicleDeliveryStatementDto(Integer tankId, String dateTime, Integer productId) {
		this.tankId=tankId;
		this.dateTime=dateTime;
		this.productId=productId;
	}
	public  VehicleDeliveryStatementDto(Integer tankId, String dateTime, Integer productId,Integer clientId){
		this.tankId=tankId;
		this.dateTime=dateTime;
		this.productId=productId;
		this.clientId=clientId;
	}
	public int getTankId() {
		return tankId;
	}
	public void setTankId(int tankId) {
		this.tankId = tankId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getParkId() {
		return parkId;
	}
	public void setParkId(int parkId) {
		this.parkId = parkId;
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
	public String getInPort() {
		return inPort;
	}
	public void setInPort(String inPort) {
		this.inPort = inPort;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public Integer getIsShowOutbound() {
		return isShowOutbound;
	}
	public void setIsShowOutbound(Integer isShowOutbound) {
		this.isShowOutbound = isShowOutbound;
	}
	public Integer getIsShowTruck() {
		return isShowTruck;
	}
	public void setIsShowTruck(Integer isShowTruck) {
		this.isShowTruck = isShowTruck;
	}
	public Integer getTimeType() {
		return timeType;
	}
	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	
}
