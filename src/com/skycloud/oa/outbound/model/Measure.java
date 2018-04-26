package com.skycloud.oa.outbound.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

@Entity
@Table(name = "t_pcs_measure")
public class Measure {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Translation(name = "小表起")
	private BigDecimal meterStart;//小表起
	@Translation(name = "小表止")
	private BigDecimal meterEnd;//小表止
	@Translation(name = "实发数")
	private BigDecimal deliverNum;//实发数
	private String trainId;//车发id
	@Translation(name = "车位id")
	private String parkId;//车位id
	@Translation(name = "储罐id")
	private String tankId;//罐id
	@Translation(name = "实际小表起")
	private BigDecimal actualMeterStart;//小表起
	@Translation(name = "实际小表止")
	private BigDecimal actualMeterEnd;//小表止
	@Translation(name = "实发")
	private BigDecimal actualDeliverNum;//实发数	
	@Translation(name = "时间")
	private Date workTime ;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public BigDecimal getMeterStart() {
		return meterStart;
	}
	public void setMeterStart(BigDecimal meterStart) {
		this.meterStart = meterStart;
	}
	public BigDecimal getMeterEnd() {
		return meterEnd;
	}
	public void setMeterEnd(BigDecimal meterEnd) {
		this.meterEnd = meterEnd;
	}
	public BigDecimal getDeliverNum() {
		return deliverNum;
	}
	public void setDeliverNum(BigDecimal deliverNum) {
		this.deliverNum = deliverNum;
	}
	public String getTrainId() {
		return trainId;
	}
	public void setTrainId(String trainId) {
		this.trainId = trainId;
	}
	public String getParkId() {
		return parkId;
	}
	public void setParkId(String parkId) {
		this.parkId = parkId;
	}
	public String getTankId() {
		return tankId;
	}
	public void setTankId(String tankId) {
		this.tankId = tankId;
	}
	public BigDecimal getActualMeterStart() {
		return actualMeterStart;
	}
	public void setActualMeterStart(BigDecimal actualMeterStart) {
		this.actualMeterStart = actualMeterStart;
	}
	public BigDecimal getActualMeterEnd() {
		return actualMeterEnd;
	}
	public void setActualMeterEnd(BigDecimal actualMeterEnd) {
		this.actualMeterEnd = actualMeterEnd;
	}
	public BigDecimal getActualDeliverNum() {
		return actualDeliverNum;
	}
	public void setActualDeliverNum(BigDecimal actualDeliverNum) {
		this.actualDeliverNum = actualDeliverNum;
	}
	public Date getWorkTime() {
		return workTime;
	}
	public void setWorkTime(Date workTime) {
		this.workTime = workTime;
	}
	
}
