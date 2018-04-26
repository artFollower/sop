package com.skycloud.oa.esb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.DateTimeUtil;

/**
 * 港务提单
 * @ClassName: HarborContainer 
 * @Description: TODO
 * @author xie
 * @date 2015年1月22日 下午9:43:10
 */
@Entity
@Table(name="t_esb_bill")
public class HarborBill {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String recordId = "50";//记录类型标识
	private String bl;//提单号
	private String consingee;//收货人
	private String shipper;//发货人
	private String consignor;//托运人
	private String cargoName;//货物名称
	private String cargoType;//货物类别
	private String danger;//危险品标志
	private String dangerNO;//危险品申报单
	private String grossWeight;//净重
	private String transnateFlag;//中转标志
	private String ifcsumType;//内外贸标志
	private String pay;//曾缴情况
	private String startPort;//启运港
	private String disPort;//目的港
	private String lastShipName;//一程船名
	private String lastCoyage;//一程船航次
	private String lastWorkTime;//一程船作业时间
	
	@OneToOne
	@JoinColumn(name="portId")
	private HarborShip ship;
	
	public String toString() {
		if(!Common.empty(lastWorkTime)) {
			lastWorkTime = DateTimeUtil.dateExchange(lastWorkTime, DateTimeUtil.simplateDateFormatText);
		}
		return "50:"+bl+":"+consingee+":"+shipper+":"+consignor+":"+cargoName+":"+cargoType+":"+grossWeight+":"+danger+":"+dangerNO+":"+transnateFlag+":"+
				ifcsumType+":"+pay+":"+startPort+":"+disPort+":"+lastShipName+":"+lastCoyage+":"+lastWorkTime+"'";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getBl() {
		return bl;
	}

	public void setBl(String bl) {
		this.bl = bl;
	}

	public String getConsingee() {
		return consingee;
	}

	public void setConsingee(String consingee) {
		this.consingee = consingee;
	}

	public String getShipper() {
		return shipper;
	}

	public void setShipper(String shipper) {
		this.shipper = shipper;
	}

	public String getConsignor() {
		return consignor;
	}

	public void setConsignor(String consignor) {
		this.consignor = consignor;
	}

	public String getCargoName() {
		return cargoName;
	}

	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
	}

	public String getCargoType() {
		return cargoType;
	}

	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}

	public String getDanger() {
		return danger;
	}

	public void setDanger(String danger) {
		this.danger = danger;
	}

	public String getDangerNO() {
		return dangerNO;
	}

	public void setDangerNO(String dangerNO) {
		this.dangerNO = dangerNO;
	}

	public String getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}

	public String getTransnateFlag() {
		return transnateFlag;
	}

	public void setTransnateFlag(String transnateFlag) {
		this.transnateFlag = transnateFlag;
	}

	public String getIfcsumType() {
		return ifcsumType;
	}

	public void setIfcsumType(String ifcsumType) {
		this.ifcsumType = ifcsumType;
	}

	public String getPay() {
		return pay;
	}

	public void setPay(String pay) {
		this.pay = pay;
	}

	public String getStartPort() {
		return startPort;
	}

	public void setStartPort(String startPort) {
		this.startPort = startPort;
	}

	public String getDisPort() {
		return disPort;
	}

	public void setDisPort(String disPort) {
		this.disPort = disPort;
	}

	public String getLastShipName() {
		return lastShipName;
	}

	public void setLastShipName(String lastShipName) {
		this.lastShipName = lastShipName;
	}

	public String getLastCoyage() {
		return lastCoyage;
	}

	public void setLastCoyage(String lastCoyage) {
		this.lastCoyage = lastCoyage;
	}

	public String getLastWorkTime() {
		return lastWorkTime;
	}

	public void setLastWorkTime(String lastWorkTime) {
		this.lastWorkTime = lastWorkTime;
	}

	public HarborShip getShip() {
		return ship;
	}

	public void setShip(HarborShip ship) {
		this.ship = ship;
	}

}
