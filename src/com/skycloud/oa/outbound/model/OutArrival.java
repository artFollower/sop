package com.skycloud.oa.outbound.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 到港信息
 * 
 * @author jiahy
 * 
 */
@Entity
@Table(name = "t_pcs_arrival")
public class OutArrival {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int type; // 类型
	private int shipId;// 船id
	private int shipRefId;// 船舶助记中文名
	// private Timestamp arrivalTime;//到港时间
	// private Timestamp endTime;//到港截止时间区间
	private Timestamp arrivalStartTime;// 开始时间
	private Timestamp arrivalEndTime;// 结束时间
	private String goodsShip;// 船检时间
	private int shipAgentId;// 船代id
	private String description;// 说明
	private int status;// 到港状态：0：预计到港；1：已到港；2：已离港

	public Timestamp getArrivalStartTime() {
		return arrivalStartTime;
	}

	public void setArrivalStartTime(Timestamp arrivalStartTime) {
		this.arrivalStartTime = arrivalStartTime;
	}

	public Timestamp getArrivalEndTime() {
		return arrivalEndTime;
	}

	public void setArrivalEndTime(Timestamp arrivalEndTime) {
		this.arrivalEndTime = arrivalEndTime;
	}

	public String getGoodsShip() {
		return goodsShip;
	}

	public void setGoodsShip(String goodsShip) {
		this.goodsShip = goodsShip;
	}

	public int getShipRefId() {
		return shipRefId;
	}

	public void setShipRefId(int shipRefId) {
		this.shipRefId = shipRefId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getShipId() {
		return shipId;
	}

	public void setShipId(int shipId) {
		this.shipId = shipId;
	}

	public int getShipAgentId() {
		return shipAgentId;
	}

	public void setShipAgentId(int shipAgentId) {
		this.shipAgentId = shipAgentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}