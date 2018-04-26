package com.skycloud.oa.system.dto;

public class HisTankDto {
 public Integer productId;
 public Integer tankId;
 public Long startTime;
 public Long endTime;
 public String tank;
 public Long Dtime;
public Integer getProductId() {
	return productId;
}
public void setProductId(Integer productId) {
	this.productId = productId;
}
public Integer getTankId() {
	return tankId;
}
public void setTankId(Integer tankId) {
	this.tankId = tankId;
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
public String getTank() {
	return tank;
}
public void setTank(String tank) {
	this.tank = tank;
}
public Long getDtime() {
	return Dtime;
}
public void setDtime(Long dtime) {
	Dtime = dtime;
}
	
}
