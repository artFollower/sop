package com.skycloud.oa.inbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 * 存储罐
 * @author yanyf
 *
 */
@Entity
@Table(name = "t_pcs_tanklogstore")
public class TankLogStore{
	  


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
     private Integer id;
	@Translation(name = "到港id")
	 private Integer arrivalId;//到港id
	@Translation(name = "储罐id")
     private Integer tankId;//储罐id
	@Translation(name = "前尺液位")
     private String startLevel;//前尺液位
	@Translation(name = "前尺重量")
     private String startWeight;//前尺重量
	@Translation(name = "前尺温度")
     private String startTemperature;//前尺温度
	@Translation(name = "手检前尺液位")
     private String startHandLevel ;//前尺手检尺液位
	@Translation(name = "手检前尺重量")
     private String startHandWeight ;//前尺手检尺重量
	@Translation(name = "后尺液位")
     private String endLevel;//后尺液位
	@Translation(name = "后尺重量")
     private String endWeight;//后尺重量
	@Translation(name = "后尺温度")
     private String endTemperature;//后尺温度
	@Translation(name = "手检后尺液位")
     private String endHandLevel ;//后尺手检尺液位
	@Translation(name = "手检后尺重量")
     private String endHandWeight ;//后尺手检尺重量
	@Translation(name = "机房实收")
     private String realAmount;//机房实收量
	@Translation(name = "计量实收")
     private String measureAmount;//计量实收
	@Translation(name = "差异量")
     private String differAmount;//差异量
	@Translation(name = "前尺关联台账id")
     private Integer startLogId;//前尺的logId
	@Translation(name = "前尺关联类型")
     private Integer startType;//前尺log的 1：前尺2：后尺
	@Translation(name = "后尺关联台账id")
     private Integer endLogId;//后尺的logId
	@Translation(name = "后尺关联类型")
     private Integer endType;//后尺log的 1：前尺2：后尺
     
     
     
	public Integer getStartLogId() {
		return startLogId;
	}
	public void setStartLogId(Integer startLogId) {
		this.startLogId = startLogId;
	}
	public Integer getStartType() {
		return startType;
	}
	public void setStartType(Integer startType) {
		this.startType = startType;
	}
	public Integer getEndLogId() {
		return endLogId;
	}
	public void setEndLogId(Integer endLogId) {
		this.endLogId = endLogId;
	}
	public Integer getEndType() {
		return endType;
	}
	public void setEndType(Integer endType) {
		this.endType = endType;
	}
	public Integer getArrivalId() {
		return arrivalId;
	}
	public void setArrivalId(Integer arrivalId) {
		this.arrivalId = arrivalId;
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
	public TankLogStore() {
		super();
	}

    // Constructors

   





}