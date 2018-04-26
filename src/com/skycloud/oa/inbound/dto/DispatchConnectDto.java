package com.skycloud.oa.inbound.dto;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 * 到港信息
 * @author jiahy
 *
 */
public class DispatchConnectDto {

	private int id;
	@Translation(name = "调度早")
	private String DD1;
	@Translation(name = "调度晚")
	private String DD2;
	@Translation(name = "发货班早")
	private String FH1;
	@Translation(name = "发货班晚")
	private String FH2;
	@Translation(name = "日班早")
	private String RB1;
	@Translation(name = "日班晚")
	private String RB2;
	@Translation(name = "码头早")
	private String MT1;
	@Translation(name = "码头晚")
	private String MT2;
	@Translation(name = "动力班早")
	private String DL1;
	@Translation(name = "动力班晚")
	private String DL2;
	@Translation(name = "描述")
	private String description;
	@Translation(name = "天气")
     private String sWeather;//手写天气
	@Translation(name = "风力")
     private String sWindPower;//手写风力
	@Translation(name = "风向")
     private String sWindDirection;//手写风向
	@Translation(name = "温度")
    private String sTemperature;//手写风向
	
	@Translation(name = "日期")
     private Long time;//所属日期（天）

	public String getsTemperature() {
		return sTemperature;
	}

	public void setsTemperature(String sTemperature) {
		this.sTemperature = sTemperature;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDD1() {
		return DD1;
	}

	public void setDD1(String dD1) {
		DD1 = dD1;
	}

	public String getDD2() {
		return DD2;
	}

	public void setDD2(String dD2) {
		DD2 = dD2;
	}

	public String getFH1() {
		return FH1;
	}

	public void setFH1(String fH1) {
		FH1 = fH1;
	}

	public String getFH2() {
		return FH2;
	}

	public void setFH2(String fH2) {
		FH2 = fH2;
	}

	public String getRB1() {
		return RB1;
	}

	public void setRB1(String rB1) {
		RB1 = rB1;
	}

	public String getRB2() {
		return RB2;
	}

	public void setRB2(String rB2) {
		RB2 = rB2;
	}

	public String getMT1() {
		return MT1;
	}

	public void setMT1(String mT1) {
		MT1 = mT1;
	}

	public String getMT2() {
		return MT2;
	}

	public void setMT2(String mT2) {
		MT2 = mT2;
	}

	public String getDL1() {
		return DL1;
	}

	public void setDL1(String dL1) {
		DL1 = dL1;
	}

	public String getDL2() {
		return DL2;
	}

	public void setDL2(String dL2) {
		DL2 = dL2;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	
}