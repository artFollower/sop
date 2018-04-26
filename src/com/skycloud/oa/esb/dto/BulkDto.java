package com.skycloud.oa.esb.dto;

import com.skycloud.oa.esb.model.Harbor;
import com.skycloud.oa.esb.model.HarborBill;
import com.skycloud.oa.esb.model.HarborShip;

public class BulkDto {
	
	private int id;
	private String bl;//提单号
	private String consingee;//收货人
	private String shipper;//收货人
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
	
	private String dischargePortCode;//卸货港代码
	private String dischargePort;//卸货港
	private String loadPortCode;//装货港代码
	private String loadPort;//装货港
	private String deliveryCode;//交货地代码
	private String delivery;//交货地
	private String catogary;//类型
	
	private HarborShip ship;
	
	public Harbor getHarbor() {
		Harbor harbor = new Harbor();
		harbor.setDischargePortCode(dischargePortCode);
		harbor.setDischargePort(dischargePort);
		harbor.setLoadPortCode(loadPortCode);
		harbor.setLoadPort(loadPort);
		harbor.setDelivery(delivery);
		harbor.setDeliveryCode(deliveryCode);
		harbor.setCatogary(catogary);
		harbor.setObjectId(id);
		return harbor;
	}
	public HarborBill getHarborBill() {
		HarborBill harborBill = new HarborBill();
		harborBill.setId(id);
		harborBill.setBl(bl);
		harborBill.setCargoName(cargoName);
		harborBill.setCargoType(cargoType);
		harborBill.setConsignor(consignor);
		harborBill.setConsingee(consingee);
		harborBill.setDanger(danger);
		harborBill.setDangerNO(dangerNO);
		harborBill.setDisPort(disPort);
		harborBill.setGrossWeight(grossWeight);
		harborBill.setIfcsumType(ifcsumType);
		harborBill.setLastCoyage(lastCoyage);
		harborBill.setLastShipName(lastShipName);
//		harborBill.setLastWorkTime(DateTimeUtil.getDate(lastWorkTime, DateTimeUtil.shortDateTimeFormatText).getTime()/1000);
		harborBill.setPay(pay);
		harborBill.setShipper(shipper);
		harborBill.setStartPort(startPort);
		harborBill.setTransnateFlag(transnateFlag);
		harborBill.setShip(ship);
		return harborBill;
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
	public String getDischargePortCode() {
		return dischargePortCode;
	}
	public void setDischargePortCode(String dischargePortCode) {
		this.dischargePortCode = dischargePortCode;
	}
	public String getDischargePort() {
		return dischargePort;
	}
	public void setDischargePort(String dischargePort) {
		this.dischargePort = dischargePort;
	}
	public String getLoadPortCode() {
		return loadPortCode;
	}
	public void setLoadPortCode(String loadPortCode) {
		this.loadPortCode = loadPortCode;
	}
	public String getLoadPort() {
		return loadPort;
	}
	public void setLoadPort(String loadPort) {
		this.loadPort = loadPort;
	}
	public String getDeliveryCode() {
		return deliveryCode;
	}
	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}
	public String getDelivery() {
		return delivery;
	}
	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCatogary() {
		return catogary;
	}
	public void setCatogary(String catogary) {
		this.catogary = catogary;
	}
	public HarborShip getShip() {
		return ship;
	}
	public void setShip(HarborShip ship) {
		this.ship = ship;
	}
	
}
