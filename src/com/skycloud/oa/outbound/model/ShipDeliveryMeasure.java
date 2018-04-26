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
@Table(name = "t_pcs_shipDeliveryMeasure")
public class ShipDeliveryMeasure {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Translation(name = "到港id")
	private Integer arrivalId;//到港id
	@Translation(name = "前尺")
	private BigDecimal startLevel;//前尺
	@Translation(name = "后尺")
	private BigDecimal endLevel;//后尺
	@Translation(name = "船方量")
	private BigDecimal shipAmount;//船方
	@Translation(name = "计量量")
	private BigDecimal metering;//计量量
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getArrivalId() {
		return arrivalId;
	}
	public void setArrivalId(Integer arrivalId) {
		this.arrivalId = arrivalId;
	}
	public BigDecimal getStartLevel() {
		return startLevel;
	}
	public void setStartLevel(BigDecimal startLevel) {
		this.startLevel = startLevel;
	}
	public BigDecimal getEndLevel() {
		return endLevel;
	}
	public void setEndLevel(BigDecimal endLevel) {
		this.endLevel = endLevel;
	}
	public BigDecimal getShipAmount() {
		return shipAmount;
	}
	public void setShipAmount(BigDecimal shipAmount) {
		this.shipAmount = shipAmount;
	}
	public BigDecimal getMetering() {
		return metering;
	}
	public void setMetering(BigDecimal metering) {
		this.metering = metering;
	}
	
}
