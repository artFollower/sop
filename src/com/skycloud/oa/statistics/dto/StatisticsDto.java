package com.skycloud.oa.statistics.dto;

public class StatisticsDto {
	private Integer cargoId;
	private Integer productId;
	private Long startTime;
	private String sStartTime;
	private Long endTime;
	private String sEndTime;
	private Integer clientId;
	private Integer order;  //排序     0：建仓倒序  1：建仓正序  2：罐号正序 3：罐号倒序
	private String code;//货批/货体号
	private Integer isFinish;//0:未清盘1:全部
	private Integer type;//日志类型
	private String cargoCode;//货批号
	
	private String goodsIds;//货体ids
	
	private String goodsCode;//货体号
	
	private String ladingCode;//提单号
	
	private Integer isDim;//提单号是否模糊搜索
	
	private Integer showVir;//是否显示虚拟库
	
	private Integer tankId;//罐号
	
	private String sourceLadingCode;//来源提单号
	
	private String sendLadingCode;//发出提单号
	
	private Integer showVirTime;//显示嫁接后时间

	public Integer getCargoId() {
		return cargoId;
	}
	public void setCargoId(Integer cargoId) {
		this.cargoId = cargoId;
	}
	public Integer getShowVirTime() {
		return showVirTime;
	}
	public void setShowVirTime(Integer showVirTime) {
		this.showVirTime = showVirTime;
	}
	public String getSourceLadingCode() {
		return sourceLadingCode;
	}
	public void setSourceLadingCode(String sourceLadingCode) {
		this.sourceLadingCode = sourceLadingCode;
	}
	public String getSendLadingCode() {
		return sendLadingCode;
	}
	public void setSendLadingCode(String sendLadingCode) {
		this.sendLadingCode = sendLadingCode;
	}
	public Integer getTankId() {
		return tankId;
	}
	public void setTankId(Integer tankId) {
		this.tankId = tankId;
	}
	public Integer getShowVir() {
		return showVir;
	}
	public void setShowVir(Integer showVir) {
		this.showVir = showVir;
	}
	public Integer getIsDim() {
		return isDim;
	}
	public void setIsDim(Integer isDim) {
		this.isDim = isDim;
	}
	public String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public String getLadingCode() {
		return ladingCode;
	}
	public void setLadingCode(String ladingCode) {
		this.ladingCode = ladingCode;
	}
	public String getGoodsIds() {
		return goodsIds;
	}
	public void setGoodsIds(String goodsIds) {
		this.goodsIds = goodsIds;
	}
	public String getCargoCode() {
		return cargoCode;
	}
	public void setCargoCode(String cargoCode) {
		this.cargoCode = cargoCode;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(Integer isFinish) {
		this.isFinish = isFinish;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public String getsEndTime() {
		return sEndTime;
	}
	public void setsEndTime(String sEndTime) {
		this.sEndTime = sEndTime;
	}
	public String getsStartTime() {
		return sStartTime;
	}
	public void setsStartTime(String sStartTime) {
		this.sStartTime = sStartTime;
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
	
}
