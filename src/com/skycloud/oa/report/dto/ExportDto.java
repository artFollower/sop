/**
 * 
 * @Project:sop
 * @Title:ExportDto.java
 * @Package:com.skycloud.oa.report.dto
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年11月13日 下午2:04:19
 * @Version:SkyCloud版权所有
 */
package com.skycloud.oa.report.dto;

/**
 * <p>导出Excel数据源</p>
 * @ClassName:ExportDto
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年11月13日 下午2:04:19
 * 
 */
public class ExportDto 
{
	
	private String statisMonth;

	private int productId;
	private int berthId;
	private int clientId;
	private int flag;//是否油品
	private String productName;
	private int type;//判断是通过，还是其他的  1：通过，0：其他
	private String inPort;//发货口
	private String clientName;
	private int addUp;//计算累计 0：不是累计的，1：累计的
	private int lastThis;// 判断今年和去年，0：今年，1：去年
	private int tank;// 包罐，0：其他，1：包罐
	private int inOut;// 判断外贸，1：外贸
	private int oils;// 判断油品
	private int planTank;//判断计划量和罐检量，1：罐检量
    private String startTime;
    private String endTime;
    private String clientCode;//客户代号
    private Integer taxType;//贸易性质
    private Integer isPass;//是否是通过单位
    private String berthIds;//泊位数组
    private String  noClientId;//不属于该货主
    private String  yesClientId;//属于该货主
    private String taxTypes;//贸易性质
    private int isYearType;//2015年类型 1，
    private Integer storageType;//仓储性质
    private String tankKeys;//储罐ids
	public String getStatisMonth() {
		return statisMonth;
	}
	public void setStatisMonth(String statisMonth) {
		this.statisMonth = statisMonth;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getBerthId() {
		return berthId;
	}
	public void setBerthId(int berthId) {
		this.berthId = berthId;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getInPort() {
		return inPort;
	}
	public void setInPort(String inPort) {
		this.inPort = inPort;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public int getAddUp() {
		return addUp;
	}
	public void setAddUp(int addUp) {
		this.addUp = addUp;
	}
	public int getLastThis() {
		return lastThis;
	}
	public void setLastThis(int lastThis) {
		this.lastThis = lastThis;
	}
	public int getTank() {
		return tank;
	}
	public void setTank(int tank) {
		this.tank = tank;
	}
	public int getInOut() {
		return inOut;
	}
	public void setInOut(int inOut) {
		this.inOut = inOut;
	}
	public int getOils() {
		return oils;
	}
	public void setOils(int oils) {
		this.oils = oils;
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
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public Integer getTaxType() {
		return taxType;
	}
	public void setTaxType(Integer taxType) {
		this.taxType = taxType;
	}
	public Integer getIsPass() {
		return isPass;
	}
	public void setIsPass(Integer isPass) {
		this.isPass = isPass;
	}
	public String getBerthIds() {
		return berthIds;
	}
	public void setBerthIds(String berthIds) {
		this.berthIds = berthIds;
	}
	public String getNoClientId() {
		return noClientId;
	}
	public void setNoClientId(String noClientId) {
		this.noClientId = noClientId;
	}
	public String getYesClientId() {
		return yesClientId;
	}
	public void setYesClientId(String yesClientId) {
		this.yesClientId = yesClientId;
	}
	public String getTaxTypes() {
		return taxTypes;
	}
	public void setTaxTypes(String taxTypes) {
		this.taxTypes = taxTypes;
	}
	public int getIsYearType() {
		return isYearType;
	}
	public void setIsYearType(int isYearType) {
		this.isYearType = isYearType;
	}
	public Integer getStorageType() {
		return storageType;
	}
	public void setStorageType(Integer storageType) {
		this.storageType = storageType;
	}
	public String getTankKeys() {
		return tankKeys;
	}
	public void setTankKeys(String tankKeys) {
		this.tankKeys = tankKeys;
	}
	
}