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
 * 港务集装箱
 * @ClassName: HarborContainer 
 * @Description: TODO
 * @author xie
 * @date 2015年1月22日 下午9:43:10
 */
@Entity
@Table(name="t_esb_ctn")
public class HarborContainer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String recordId = "50";//记录类型标识
	private String ctnNO;//箱号
	private String ctnSizeType;//集装箱尺寸类型
	private String ctnOperator;//集装箱经营人
	private String ctnStatus;//集装箱状态
	private String bl;//提单号
	private String seal;//铅封号
	private String location;//船舶泊位
	
	private String cargoName;//货物名称
	private String danger;//危险品标志
	private String dangerNO;//危险品申报单
	private String grossWeight;//净重
	private String transnateFlag;//中转标志
	private String ifcsumType;//内外贸标志
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
		return "50:"+ctnNO+":"+ctnSizeType+":"+ctnOperator+":"+ctnStatus+":"+bl+":"+seal+":"+location+":"+cargoName+":"+danger+":"+dangerNO+":"+
		grossWeight+":"+transnateFlag+":"+ifcsumType+":"+lastShipName+":"+lastCoyage+":"+lastWorkTime+"'";
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

	public String getCtnNO() {
		return ctnNO;
	}

	public void setCtnNO(String ctnNO) {
		this.ctnNO = ctnNO;
	}

	public String getCtnSizeType() {
		return ctnSizeType;
	}

	public void setCtnSizeType(String ctnSizeType) {
		this.ctnSizeType = ctnSizeType;
	}

	public String getCtnOperator() {
		return ctnOperator;
	}

	public void setCtnOperator(String ctnOperator) {
		this.ctnOperator = ctnOperator;
	}

	public String getCtnStatus() {
		return ctnStatus;
	}

	public void setCtnStatus(String ctnStatus) {
		this.ctnStatus = ctnStatus;
	}

	public String getBl() {
		return bl;
	}

	public void setBl(String bl) {
		this.bl = bl;
	}

	public String getSeal() {
		return seal;
	}

	public void setSeal(String seal) {
		this.seal = seal;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCargoName() {
		return cargoName;
	}

	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
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
