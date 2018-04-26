package com.skycloud.oa.inbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 * 调度值班天气预报记录
 * 
 * @author yanyufeng
 *
 */
@Entity
@Table(name = "t_pcs_duty_weather")
public class DutyWeather {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Translation(name = "调度日志id")
	private Integer dispatchId;
	@Translation(name = "时间")
	private String time;
	@Translation(name = "天气预报")
	private String weather;//天气预报
	@Translation(name = "码头")
	private String port;//码头
	@Translation(name = "预报人")
	private String forecastUser;//预报人
	@Translation(name = "值班人员")
	private String dutyUser;//值班人员
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDispatchId() {
		return dispatchId;
	}
	public void setDispatchId(Integer dispatchId) {
		this.dispatchId = dispatchId;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getForecastUser() {
		return forecastUser;
	}
	public void setForecastUser(String forecastUser) {
		this.forecastUser = forecastUser;
	}
	public String getDutyUser() {
		return dutyUser;
	}
	public void setDutyUser(String dutyUser) {
		this.dutyUser = dutyUser;
	}

	

	
}