package com.skycloud.oa.inbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 * 存储台账
 * @author yanyf
 *
 */
@Entity
@Table(name = "t_pcs_tank_log")
public class TankLog{
	  

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
     private Integer id;
	@Translation(name = "货品")
	 private Integer productId;
	@Translation(name = "储罐id")
     private Integer tankId;
	@Translation(name = "前尺液位")
     private String startLevel;
	@Translation(name = "后尺液位")
     private String endLevel;
	@Translation(name = "前尺重量")
     private String startWeight;
	@Translation(name = "后尺重量")
     private String endWeight;
	@Translation(name = "前尺温度")
     private String startTemperature;
	@Translation(name = "后尺温度")
     private String endTemperature;
	@Translation(name = "实际数量")
     private String realAmount;
	@Translation(name = "计量数量")
     private String measureAmount;
	@Translation(name = "差异")
     private String differAmount;
	@Translation(name = "手检前尺")
     private String startHand;
	@Translation(name = "手检后尺")
     private String endHand;
	@Translation(name = "开始时间")
     private Long startTime;
	@Translation(name = "结束时间")
     private Long endTime;
	@Translation(name = "手检前尺液位")
     private String startHandLevel ;
	@Translation(name = "手检后尺液位")
     private String endHandLevel ;
	@Translation(name = "手检前尺重量")
     private String startHandWeight ;
	@Translation(name = "手检后尺重量")
     private String endHandWeight ;
	@Translation(name = "前尺差异")
     private String startDiffer;
	@Translation(name = "后尺差异")
     private String endDiffer;
	@Translation(name = "消息")
     private String message;
	@Translation(name = "类型")
	private Integer messageType=0;
	@Translation(name = "前尺关联id")
     private Integer startStoreId;//前尺关联id
	@Translation(name = "后尺关联id")
     private Integer endStoreId;//后尺关联id
	@Translation(name = "前尺关联类型")
     private Integer startStoreType;//前尺关联1：前尺2：后尺
	@Translation(name = "后尺关联类型")
	private Integer endStoreType;//后尺关联1：前尺2：后尺
     
     
     
     
	public Integer getStartStoreType() {
		return startStoreType;
	}
	public void setStartStoreType(Integer startStoreType) {
		this.startStoreType = startStoreType;
	}
	public Integer getEndStoreType() {
		return endStoreType;
	}
	public void setEndStoreType(Integer endStoreType) {
		this.endStoreType = endStoreType;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getStartStoreId() {
		return startStoreId;
	}
	public void setStartStoreId(Integer startStoreId) {
		this.startStoreId = startStoreId;
	}
	public Integer getEndStoreId() {
		return endStoreId;
	}
	public void setEndStoreId(Integer endStoreId) {
		this.endStoreId = endStoreId;
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
	public String getStartLevel() {
		return startLevel;
	}
	public void setStartLevel(String startLevel) {
		this.startLevel = startLevel;
	}
	public String getEndLevel() {
		return endLevel;
	}
	public void setEndLevel(String endLevel) {
		this.endLevel = endLevel;
	}
	public String getStartWeight() {
		return startWeight;
	}
	public void setStartWeight(String startWeight) {
		this.startWeight = startWeight;
	}
	public String getEndWeight() {
		return endWeight;
	}
	public void setEndWeight(String endWeight) {
		this.endWeight = endWeight;
	}
	public String getStartTemperature() {
		return startTemperature;
	}
	public void setStartTemperature(String startTemperature) {
		this.startTemperature = startTemperature;
	}
	public String getEndTemperature() {
		return endTemperature;
	}
	public void setEndTemperature(String endTemperature) {
		this.endTemperature = endTemperature;
	}
	public String getRealAmount() {
		return realAmount;
	}
	public void setRealAmount(String realAmount) {
		this.realAmount = realAmount;
	}
	public String getMeasureAmount() {
		return measureAmount;
	}
	public void setMeasureAmount(String measureAmount) {
		this.measureAmount = measureAmount;
	}
	public String getDifferAmount() {
		return differAmount;
	}
	public void setDifferAmount(String differAmount) {
		this.differAmount = differAmount;
	}
	public String getStartHand() {
		return startHand;
	}
	public void setStartHand(String startHand) {
		this.startHand = startHand;
	}
	public String getEndHand() {
		return endHand;
	}
	public void setEndHand(String endHand) {
		this.endHand = endHand;
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
	public String getStartHandLevel() {
		return startHandLevel;
	}
	public void setStartHandLevel(String startHandLevel) {
		this.startHandLevel = startHandLevel;
	}
	public String getEndHandLevel() {
		return endHandLevel;
	}
	public void setEndHandLevel(String endHandLevel) {
		this.endHandLevel = endHandLevel;
	}
	public String getStartHandWeight() {
		return startHandWeight;
	}
	public void setStartHandWeight(String startHandWeight) {
		this.startHandWeight = startHandWeight;
	}
	public String getEndHandWeight() {
		return endHandWeight;
	}
	public void setEndHandWeight(String endHandWeight) {
		this.endHandWeight = endHandWeight;
	}
	public String getStartDiffer() {
		return startDiffer;
	}
	public void setStartDiffer(String startDiffer) {
		this.startDiffer = startDiffer;
	}
	public String getEndDiffer() {
		return endDiffer;
	}
	public void setEndDiffer(String endDiffer) {
		this.endDiffer = endDiffer;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getMessageType() {
		return messageType;
	}
	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}
	public TankLog() {
		super();
	}

    // Constructors

   





}