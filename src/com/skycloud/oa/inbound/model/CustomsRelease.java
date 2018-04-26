/**
 * 
 */
package com.skycloud.oa.inbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 *海关放行量统计表
 * @author jiahy
 * @version 2015年12月10日 下午2:45:05
 */
@Entity
@Table(name = "t_pcs_customs_release")
public class CustomsRelease {

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Integer id;
@Translation(name = "到港id")
private Integer arrivalId;//到港id
@Translation(name = "货品id")
private Integer productId;//货批id
@Translation(name = "储罐id")
private Integer tankId;//储罐id
@Translation(name = "海关已放行量")
private String hasCustomsPass;//海关已放行量
@Translation(name = "卸货前罐量")
private String beforeInboundTank;//卸货前罐量
@Translation(name = "卸货后罐量")
private String afterInboundTank;//卸货后罐量
@Translation(name = "卸货数量")
private String inboundCount;//卸货数量
@Translation(name = "可发数量")
private String outBoundCount;//可发数量
@Translation(name = "不可发数量")
private String unOutBoundCount;//不可发数量
@Translation(name = "备注")
private String description;//备注
@Translation(name = "创建人")
private Integer userId;
@Translation(name = "创建时间")
private Long createTime;
public CustomsRelease() {
	super();
	// TODO Auto-generated constructor stub
}
public CustomsRelease(Integer id, Integer arrivalId, Integer productId,
		Integer tankId, String hasCustomsPass, String beforeInboundTank,
		String afterInboundTank, String inboundCount, String outBoundCount,
		String unOutBoundCount, String description, Integer userId,
		Long createTime) {
	super();
	this.id = id;
	this.arrivalId = arrivalId;
	this.productId = productId;
	this.tankId = tankId;
	this.hasCustomsPass = hasCustomsPass;
	this.beforeInboundTank = beforeInboundTank;
	this.afterInboundTank = afterInboundTank;
	this.inboundCount = inboundCount;
	this.outBoundCount = outBoundCount;
	this.unOutBoundCount = unOutBoundCount;
	this.description = description;
	this.userId = userId;
	this.createTime = createTime;
}
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public Integer getArrivalId() {
	return arrivalId;
}
public void setArrivalId(Integer arrivalId) {
	this.arrivalId = arrivalId;
}
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
public String getHasCustomsPass() {
	return hasCustomsPass;
}
public void setHasCustomsPass(String hasCustomsPass) {
	this.hasCustomsPass = hasCustomsPass;
}
public String getBeforeInboundTank() {
	return beforeInboundTank;
}
public void setBeforeInboundTank(String beforeInboundTank) {
	this.beforeInboundTank = beforeInboundTank;
}
public String getAfterInboundTank() {
	return afterInboundTank;
}
public void setAfterInboundTank(String afterInboundTank) {
	this.afterInboundTank = afterInboundTank;
}
public String getInboundCount() {
	return inboundCount;
}
public void setInboundCount(String inboundCount) {
	this.inboundCount = inboundCount;
}
public String getOutBoundCount() {
	return outBoundCount;
}
public void setOutBoundCount(String outBoundCount) {
	this.outBoundCount = outBoundCount;
}
public String getUnOutBoundCount() {
	return unOutBoundCount;
}
public void setUnOutBoundCount(String unOutBoundCount) {
	this.unOutBoundCount = unOutBoundCount;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public Integer getUserId() {
	return userId;
}
public void setUserId(Integer userId) {
	this.userId = userId;
}
public Long getCreateTime() {
	return createTime;
}
public void setCreateTime(Long createTime) {
	this.createTime = createTime;
}

}
