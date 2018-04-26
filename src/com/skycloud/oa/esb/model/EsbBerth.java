package com.skycloud.oa.esb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_esb_berth")
public class EsbBerth {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String berthCode;// 泊位代码
	private String portCode;// 港口代码
	private String berthName;// 泊位名称
	private String berthShortName;// 泊位简称
	private String pinyinCode;// 拼音码
	private String berthDepth;// 泊位深度
	private String berthLevel;// 泊位等级
	private String berthLength;// 泊位长度
	private String limitTEU;// 限制TEU
	private String dock;// 所属码头
	private String masCode;// 所属海事处
	private String orgCode;// 机构代码
	private String outsideOpen;// 是否对外开放
	private String soldDangeFlag;// 固体危货标
	private String liquidDangeFlag;// 液体危货标志
	private String dockOIL;// 是否供油码头
	private String shipMaxNum;// 最大容纳船舶数
	private String turnoverTime;// 吞吐周转时间
	private String dockConfirm;// 是否码头主确认
	private String blackList;// 是否黑名单
	private String validFlag;// 有效标志
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBerthCode() {
		return berthCode;
	}
	public void setBerthCode(String berthCode) {
		this.berthCode = berthCode;
	}
	public String getPortCode() {
		return portCode;
	}
	public void setPortCode(String portCode) {
		this.portCode = portCode;
	}
	public String getBerthName() {
		return berthName;
	}
	public void setBerthName(String berthName) {
		this.berthName = berthName;
	}
	public String getBerthShortName() {
		return berthShortName;
	}
	public void setBerthShortName(String berthShortName) {
		this.berthShortName = berthShortName;
	}
	public String getPinyinCode() {
		return pinyinCode;
	}
	public void setPinyinCode(String pinyinCode) {
		this.pinyinCode = pinyinCode;
	}
	public String getBerthDepth() {
		return berthDepth;
	}
	public void setBerthDepth(String berthDepth) {
		this.berthDepth = berthDepth;
	}
	public String getBerthLevel() {
		return berthLevel;
	}
	public void setBerthLevel(String berthLevel) {
		this.berthLevel = berthLevel;
	}
	public String getBerthLength() {
		return berthLength;
	}
	public void setBerthLength(String berthLength) {
		this.berthLength = berthLength;
	}
	public String getLimitTEU() {
		return limitTEU;
	}
	public void setLimitTEU(String limitTEU) {
		this.limitTEU = limitTEU;
	}
	public String getDock() {
		return dock;
	}
	public void setDock(String dock) {
		this.dock = dock;
	}
	public String getMasCode() {
		return masCode;
	}
	public void setMasCode(String masCode) {
		this.masCode = masCode;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOutsideOpen() {
		return outsideOpen;
	}
	public void setOutsideOpen(String outsideOpen) {
		this.outsideOpen = outsideOpen;
	}
	public String getSoldDangeFlag() {
		return soldDangeFlag;
	}
	public void setSoldDangeFlag(String soldDangeFlag) {
		this.soldDangeFlag = soldDangeFlag;
	}
	public String getLiquidDangeFlag() {
		return liquidDangeFlag;
	}
	public void setLiquidDangeFlag(String liquidDangeFlag) {
		this.liquidDangeFlag = liquidDangeFlag;
	}
	public String getDockOIL() {
		return dockOIL;
	}
	public void setDockOIL(String dockOIL) {
		this.dockOIL = dockOIL;
	}
	public String getShipMaxNum() {
		return shipMaxNum;
	}
	public void setShipMaxNum(String shipMaxNum) {
		this.shipMaxNum = shipMaxNum;
	}
	public String getTurnoverTime() {
		return turnoverTime;
	}
	public void setTurnoverTime(String turnoverTime) {
		this.turnoverTime = turnoverTime;
	}
	public String getDockConfirm() {
		return dockConfirm;
	}
	public void setDockConfirm(String dockConfirm) {
		this.dockConfirm = dockConfirm;
	}
	public String getBlackList() {
		return blackList;
	}
	public void setBlackList(String blackList) {
		this.blackList = blackList;
	}
	public String getValidFlag() {
		return validFlag;
	}
	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}
}
