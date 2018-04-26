package com.skycloud.oa.inbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;


/**
 * 调度日志基础表
 * @author jiahy
 *
 */
@Entity
@Table(name = "t_pcs_dispatch")
public class Dispatch  {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	 private Integer id;
	@Translation(name = "天气")
     private Integer weather;
	@Translation(name = "风向")
     private Integer windDirection;
	@Translation(name = "风力")
     private Integer windPower;
	@Translation(name = "调度id")
     private Integer dispatchUserId;
	@Translation(name = "发货班id")
     private Integer deliveryUserId;
	@Translation(name = "日班id")
     private Integer dayWordUserId;
	@Translation(name = "码头id")
     private Integer dockUserId;
	@Translation(name = "动力班id")
     private Integer powerUserId;
	@Translation(name = "调度")
     private String dispatchUser;
	@Translation(name = "发货班")
     private String deliveryUser;
	@Translation(name = "日班")
     private String dayWordUser;
	@Translation(name = "码头班")
     private String dockUser;
	@Translation(name = "动力班")
     private String powerUser;
	@Translation(name = "描述")
     private String description;
	@Translation(name = "天气")
     private String sWeather;//手写天气
	@Translation(name = "风力")
     private String sWindPower;//手写风力
	@Translation(name = "风向")
     private String sWindDirection;//手写风向
	@Translation(name = "温度")
    private String sTemperature;//手写温度
	@Translation(name = "日期")
     private Long time;//所属日期（天）
     
     
	public String getsTemperature() {
		return sTemperature;
	}

	public void setsTemperature(String sTemperature) {
		this.sTemperature = sTemperature;
	}

	public String getsWindDirection() {
		return sWindDirection;
	}

	public void setsWindDirection(String sWindDirection) {
		this.sWindDirection = sWindDirection;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getsWeather() {
		return sWeather;
	}

	public void setsWeather(String sWeather) {
		this.sWeather = sWeather;
	}

	public String getsWindPower() {
		return sWindPower;
	}

	public void setsWindPower(String sWindPower) {
		this.sWindPower = sWindPower;
	}

	public Dispatch() {
		super();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDispatchUser() {
		return dispatchUser;
	}

	public void setDispatchUser(String dispatchUser) {
		this.dispatchUser = dispatchUser;
	}

	public String getDeliveryUser() {
		return deliveryUser;
	}

	public void setDeliveryUser(String deliveryUser) {
		this.deliveryUser = deliveryUser;
	}

	public String getDayWordUser() {
		return dayWordUser;
	}

	public void setDayWordUser(String dayWordUser) {
		this.dayWordUser = dayWordUser;
	}

	public String getDockUser() {
		return dockUser;
	}

	public void setDockUser(String dockUser) {
		this.dockUser = dockUser;
	}

	public String getPowerUser() {
		return powerUser;
	}

	public void setPowerUser(String powerUser) {
		this.powerUser = powerUser;
	}

	public Integer getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(Integer windDirection) {
		this.windDirection = windDirection;
	}

	public Integer getWindPower() {
		return windPower;
	}

	public void setWindPower(Integer windPower) {
		this.windPower = windPower;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getWeather() {
		return weather;
	}
	public void setWeather(Integer weather) {
		this.weather = weather;
	}
	public Integer getDispatchUserId() {
		return dispatchUserId;
	}
	public void setDispatchUserId(Integer dispatchUserId) {
		this.dispatchUserId = dispatchUserId;
	}
	public Integer getDeliveryUserId() {
		return deliveryUserId;
	}
	public void setDeliveryUserId(Integer deliveryUserId) {
		this.deliveryUserId = deliveryUserId;
	}
	public Integer getDayWordUserId() {
		return dayWordUserId;
	}
	public void setDayWordUserId(Integer dayWordUserId) {
		this.dayWordUserId = dayWordUserId;
	}
	public Integer getDockUserId() {
		return dockUserId;
	}
	public void setDockUserId(Integer dockUserId) {
		this.dockUserId = dockUserId;
	}
	public Integer getPowerUserId() {
		return powerUserId;
	}
	public void setPowerUserId(Integer powerUserId) {
		this.powerUserId = powerUserId;
	}
     


}