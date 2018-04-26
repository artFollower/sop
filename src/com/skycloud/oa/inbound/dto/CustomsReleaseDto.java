/**
 * 
 */
package com.skycloud.oa.inbound.dto;

import com.skycloud.oa.annatation.Translation;

/**
 *
 * @author jiahy
 * @version 2015年12月10日 下午2:53:05
 */
public class CustomsReleaseDto {
private Integer id ;
@Translation(name = "货品id")
private Integer productId;
@Translation(name = "到港id")
private Integer arrivalId;
@Translation(name = "储罐id")
private Integer tankId;
@Translation(name = "船名")
private String shipName;
@Translation(name = "开始时间")
private Long startTime;
@Translation(name = "截止时间")
private Long endTime;
@Translation(name = "类型")
private Integer type;//0全部，1.未放行，2已放行
@Translation(name = "贸易类型")
private Integer taxType;//1.一般 2.保税
public CustomsReleaseDto() {
	super();
	// TODO Auto-generated constructor stub
}
public CustomsReleaseDto(Integer productId, Integer arrivalId) {
	super();
	this.productId = productId;
	this.arrivalId = arrivalId;
}
public CustomsReleaseDto(Integer productId, Integer arrivalId,Integer taxType) {
	super();
	this.productId = productId;
	this.arrivalId = arrivalId;
	this.taxType=taxType;
}
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public Integer getProductId() {
	return productId;
}

public Integer getTankId() {
	return tankId;
}
public void setTankId(Integer tankId) {
	this.tankId = tankId;
}
public void setProductId(Integer productId) {
	this.productId = productId;
}

public Integer getArrivalId() {
	return arrivalId;
}
public void setArrivalId(Integer arrivalId) {
	this.arrivalId = arrivalId;
}
public String getShipName() {
	return shipName;
}
public void setShipName(String shipName) {
	this.shipName = shipName;
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
public Integer getType() {
	return type;
}
public void setType(Integer type) {
	this.type = type;
}
public Integer getTaxType() {
	return taxType;
}
public void setTaxType(Integer taxType) {
	this.taxType = taxType;
}

}
