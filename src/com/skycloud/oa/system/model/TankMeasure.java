package com.skycloud.oa.system.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 储罐油品参数表
 * 
 * @author jiahy
 * 
 */
@Entity
@Table(name = "t_pcs_tank_measure")
public class TankMeasure {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;// 主键，储罐id，自增
	private String notify;//识别号
	private Integer productId;//货品id
	private Integer tankId;//储罐id
	private String port;//发货口
	private String normalDensity;//标准密度
	private String textDensity;//实验密度
	private String textTemperature;//实验温度
	private String volumeRatio;//体积比
	private String tankTemperature;//货罐温度
	private String viewDensity;//视密度
	private String viewVolume;//视体积
	private String normalVolume;//标体积
	private Long createTime;//提交时间
	private Integer userId ;//提交人
	private String description;//说明
	
	public TankMeasure() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TankMeasure(Integer id, String notify, Integer productId,
			Integer tankId, String port, String normalDensity,
			String textDensity, String textTemperature, String volumeRatio,
			String tankTemperature, String viewDensity, String viewVolume,
			String normalVolume, Long createTime, Integer userId,
			String description) {
		super();
		this.id = id;
		this.notify = notify;
		this.productId = productId;
		this.tankId = tankId;
		this.port = port;
		this.normalDensity = normalDensity;
		this.textDensity = textDensity;
		this.textTemperature = textTemperature;
		this.volumeRatio = volumeRatio;
		this.tankTemperature = tankTemperature;
		this.viewDensity = viewDensity;
		this.viewVolume = viewVolume;
		this.normalVolume = normalVolume;
		this.createTime = createTime;
		this.userId = userId;
		this.description = description;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNotify() {
		return notify;
	}
	public void setNotify(String notify) {
		this.notify = notify;
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
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getNormalDensity() {
		return normalDensity;
	}
	public void setNormalDensity(String normalDensity) {
		this.normalDensity = normalDensity;
	}
	public String getTextDensity() {
		return textDensity;
	}
	public void setTextDensity(String textDensity) {
		this.textDensity = textDensity;
	}
	public String getTextTemperature() {
		return textTemperature;
	}
	public void setTextTemperature(String textTemperature) {
		this.textTemperature = textTemperature;
	}
	public String getVolumeRatio() {
		return volumeRatio;
	}
	public void setVolumeRatio(String volumeRatio) {
		this.volumeRatio = volumeRatio;
	}
	public String getTankTemperature() {
		return tankTemperature;
	}
	public void setTankTemperature(String tankTemperature) {
		this.tankTemperature = tankTemperature;
	}
	public String getViewDensity() {
		return viewDensity;
	}
	public void setViewDensity(String viewDensity) {
		this.viewDensity = viewDensity;
	}
	public String getViewVolume() {
		return viewVolume;
	}
	public void setViewVolume(String viewVolume) {
		this.viewVolume = viewVolume;
	}
	public String getNormalVolume() {
		return normalVolume;
	}
	public void setNormalVolume(String normalVolume) {
		this.normalVolume = normalVolume;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
    	
}