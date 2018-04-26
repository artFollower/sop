package com.skycloud.oa.inbound.dto;

import com.skycloud.oa.annatation.Translation;

public class TankLogDto {
	@Translation(name = "关联id")
private Integer  id;//storeId
	@Translation(name = "储罐id")
private Integer tankId;//储罐id
	@Translation(name = "货品id")
private Integer productId;//货品id
	@Translation(name = "年份")
private String year;//年份
	@Translation(name = "开始时间")
private Long startTime;
	@Translation(name = "结束时间")
private Long endTime;
	@Translation(name = "储罐编号")
private String tankName ;

public String getTankName() {
	return tankName;
}
public void setTankName(String tankName) {
	this.tankName = tankName;
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
public Integer getProductId() {
	return productId;
}
public void setProductId(Integer productId) {
	this.productId = productId;
}

public String getYear() {
	return year;
}
public void setYear(String year) {
	this.year = year;
}
public TankLogDto() {
	super();
}
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public Integer getTankId() {
	return tankId;
}
public void setTankId(Integer tankId) {
	this.tankId = tankId;
}

}
