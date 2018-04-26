/**
 * 
 */
package com.skycloud.oa.planmanager.dto;

/**
 *
 * @author jiahy
 * @version 2015年8月14日 下午3:50:13
 */
public class PlanManagerDto {
	private Integer id;//倒灌id
	private Integer shipId;//船id
	private Integer productId;//货品ID
	private Long startTime;//到港时间
	private Long endTime;//到港时间
	private Integer status;//状态  0--未提交，1.审核中，2.已完成，4,5
	private Integer berthId;//泊位ID
	private String shipName;//船名
	private Integer itemType;
	private Integer type;//1  打循环方案 3 倒罐方案
	public PlanManagerDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getShipId() {
		return shipId;
	}
	public void setShipId(Integer shipId) {
		this.shipId = shipId;
	}
	
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getBerthId() {
		return berthId;
	}
	public void setBerthId(Integer berthId) {
		this.berthId = berthId;
	}
	public String getShipName() {
		return shipName;
	}
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
	public Integer getItemType() {
		return itemType;
	}
	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}
