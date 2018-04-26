package com.skycloud.oa.inbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 存储罐
 * @author jiahy
 *
 */
@Entity
@Table(name = "t_pcs_store")
public class Store{
	  

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
     private Integer id;
     private Integer transportId;
     private Integer tankId;
     private String startLevel;
     private String endLevel;
     private String startWeight;
     private String endWeight;
     private String startTemperature;
     private String endTemperature;
     private String realAmount;
     private String measureAmount;
     private String differAmount;
     private String startHand;
     private String endHand;
     private Long startTime;
     private Long endTime;
     private String startHandLevel ;
     private String endHandLevel ;
     private String startHandWeight ;
     private String endHandWeight ;
     private String startDiffer;
     private String endDiffer;
     private String message;
     private Integer messageType=0;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTransportId() {
		return transportId;
	}
	public void setTransportId(Integer transportId) {
		this.transportId = transportId;
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
	public Store() {
		super();
	}

    // Constructors

   





}