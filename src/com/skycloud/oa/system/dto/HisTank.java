package com.skycloud.oa.system.dto;

/**
 * 储罐资料表
 * 
 * @author jiahy
 * 
 */
public class HisTank {

public String tank;
public String goods;
public Integer waterHeight;
public Integer materialHeight;
public Double materialWeight;
public Double temperature;
public Double density;
public Long DTime;
public Double capacityTotal;
public Integer tankType;
public Integer isUse;
public String getTank() {
	return tank;
}
public void setTank(String tank) {
	this.tank = tank;
}
public String getGoods() {
	return goods;
}
public void setGoods(String goods) {
	this.goods = goods;
}
public Integer getWaterHeight() {
	return waterHeight;
}
public void setWaterHeight(Integer waterHeight) {
	this.waterHeight = waterHeight;
}
public Integer getMaterialHeight() {
	return materialHeight;
}
public void setMaterialHeight(Integer materialHeight) {
	this.materialHeight = materialHeight;
}
public Double getMaterialWeight() {
	return materialWeight;
}
public void setMaterialWeight(Double materialWeight) {
	this.materialWeight = materialWeight;
}
public Double getTemperature() {
	return temperature;
}
public void setTemperature(Double temperature) {
	this.temperature = temperature;
}
public Double getDensity() {
	return density;
}
public void setDensity(Double density) {
	this.density = density;
}
public Long getDTime() {
	return DTime;
}
public void setDTime(Long dTime) {
	DTime = dTime;
}
public Double getCapacityTotal() {
	return capacityTotal;
}
public void setCapacityTotal(Double capacityTotal) {
	this.capacityTotal = capacityTotal;
}
public Integer getTankType() {
	return tankType;
}
public void setTankType(Integer tankType) {
	this.tankType = tankType;
}
public Integer getIsUse() {
	return isUse;
}
public void setIsUse(Integer isUse) {
	this.isUse = isUse;
}
	
}