/**
 * 
 * @Project:sop
 * @Title:ReportDto.java
 * @Package:com.skycloud.oa.report.dto
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年10月26日 上午10:25:05
 * @Version:SkyCloud版权所有
 */
package com.skycloud.oa.report.dto;

/**
 * <p>报表公共查询模块查询条件封装</p>
 * @ClassName:ReportDto
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年10月26日 上午10:25:05
 * 
 */
public class ReportDto 
{
	private int module;
	
	private String statisMonth;
	private String statisYear;
	private int productId;
	private int clientId;
	private Integer berthId;
	private String clientName;
	private String productName;
	private Integer isMonth;
	private String shipName;
	/**
	 * 1 （入库 1，3，出库2，5）2.（入库3，出库5 ）3.（入库1，转输，出库2，转输）
	 */
	private Integer type;
	
	private String startTime ;
	private String endTime;
	/**
	 * 计划量和罐检量，1：罐检量
	 */
	private int planTank;
    private String tankKeys;//储罐ids
    private Integer storageType;//仓储性质
	public int getModule() {
		return module;
	}

	public void setModule(int module) {
		this.module = module;
	}

	public String getStatisMonth() {
		return statisMonth;
	}

	public void setStatisMonth(String statisMonth) {
		this.statisMonth = statisMonth;
	}

	public String getStatisYear() {
		return statisYear;
	}

	public void setStatisYear(String statisYear) {
		this.statisYear = statisYear;
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


	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getIsMonth() {
		return isMonth;
	}

	public void setIsMonth(Integer isMonth) {
		this.isMonth = isMonth;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public int getPlanTank() {
		return planTank;
	}

	public void setPlanTank(int planTank) {
		this.planTank = planTank;
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

	public String getTankKeys() {
		return tankKeys;
	}

	public void setTankKeys(String tankKeys) {
		this.tankKeys = tankKeys;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Integer getStorageType() {
		return storageType;
	}

	public void setStorageType(Integer storageType) {
		this.storageType = storageType;
	}

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public Integer getBerthId() {
		return berthId;
	}

	public void setBerthId(Integer berthId) {
		this.berthId = berthId;
	}

}