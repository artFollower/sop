package com.skycloud.oa.esb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.DateTimeUtil;

/**
 * 港务船
 * @ClassName: HarborShip 
 * @Description: TODO
 * @author xie
 * @date 2015年1月22日 下午9:25:27
 */
@Entity
@Table(name="t_esb_ship")
public class HarborShip {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String recordId = "10";//记录类型标识
	private String vesselCode;//船名代码
	private String vessel;//船名
	private String voyage;//航次号
	private String nationality;//船舶国籍
	private String liner;//班轮
	private String arrivlTime;//到港时间
	private String sailingTime;//实际离港时间
	private String dischStartTime;//卸船开始时间
	private String dicshEndTime;//卸船结束时间
	private String loadStartTime;//装船开始时间
	private String loadEndTime;//装船结束时间
	private int ctnNumber;//集装箱数量
	private String port;//泊位代码
	private String shipUniqueNum;//船舶识别号
	private String confirgFlag;//预报/确报
	private String befterDraft;//进港吃水
	private String afterDraft;//离港吃水
	private String cargoDes;//货物描述
	private String netWeight;//本港装卸量
	private String realCapacity;//本港装卸量
	private String portPassenger;//本港上下客
	
	private String catogary;//分类：BULK散货 CON 集装箱
	
	@Column(columnDefinition="char default 0")
	private String status;//报文生成状态
	private String file;//生成的文件
	
	public String toString() {
		if(!Common.empty(arrivlTime)) {
			arrivlTime = DateTimeUtil.dateExchange(arrivlTime, DateTimeUtil.simplateDateFormatText);
		}
		if(!Common.empty(sailingTime)) {
			sailingTime = DateTimeUtil.dateExchange(sailingTime, DateTimeUtil.simplateDateFormatText);
		}
		if(!Common.empty(dischStartTime)) {
			dischStartTime = DateTimeUtil.dateExchange(dischStartTime, DateTimeUtil.simplateDateFormatText);
		}
		if(!Common.empty(dicshEndTime)) {
			dicshEndTime = DateTimeUtil.dateExchange(dicshEndTime, DateTimeUtil.simplateDateFormatText);
		}
		if(!Common.empty(loadStartTime)) {
			loadStartTime = DateTimeUtil.dateExchange(loadStartTime, DateTimeUtil.simplateDateFormatText);
		}
		if(!Common.empty(loadEndTime)) {
			loadEndTime = DateTimeUtil.dateExchange(loadEndTime, DateTimeUtil.simplateDateFormatText);
		}
//		if(catogary.equals("BULK")) {
			return "10:"+vesselCode+":"+vessel+":"+voyage+":"+nationality+":"+liner+":"+arrivlTime+":"+sailingTime+":"+dischStartTime+":"+dicshEndTime+":"+
					loadStartTime+":"+loadEndTime+":"+port+":"+shipUniqueNum+":"+confirgFlag+":"+befterDraft+":"+afterDraft+":"+cargoDes+":"+
					netWeight+":"+realCapacity+":"+portPassenger+"'";
//		}else {
//			return "10:"+vesselCode+":"+vessel+":"+voyage+":"+nationality+":"+liner+":"+arrivlTime+":"+sailingTime+":"+dischStartTime+":"+dicshEndTime+":"+
//					loadStartTime+":"+loadEndTime+":"+ctnNumber+":"+port+":"+shipUniqueNum+":"+confirgFlag+":"+befterDraft+":"+afterDraft+":"+cargoDes+":"+
//					netWeight+":"+realCapacity+":"+portPassenger+"'";
//		}
		
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
	public String getVesselCode() {
		return vesselCode;
	}
	public void setVesselCode(String vesselCode) {
		this.vesselCode = vesselCode;
	}
	public String getVessel() {
		return vessel;
	}
	public void setVessel(String vessel) {
		this.vessel = vessel;
	}
	public String getVoyage() {
		return voyage;
	}
	public void setVoyage(String voyage) {
		this.voyage = voyage;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getLiner() {
		return liner;
	}
	public void setLiner(String liner) {
		this.liner = liner;
	}
	public String getArrivlTime() {
		return arrivlTime;
	}
	public void setArrivlTime(String arrivlTime) {
		this.arrivlTime = arrivlTime;
	}
	public String getSailingTime() {
		return sailingTime;
	}
	public void setSailingTime(String sailingTime) {
		this.sailingTime = sailingTime;
	}
	public String getDischStartTime() {
		return dischStartTime;
	}
	public void setDischStartTime(String dischStartTime) {
		this.dischStartTime = dischStartTime;
	}
	public String getDicshEndTime() {
		return dicshEndTime;
	}
	public void setDicshEndTime(String dicshEndTime) {
		this.dicshEndTime = dicshEndTime;
	}
	public String getLoadStartTime() {
		return loadStartTime;
	}
	public void setLoadStartTime(String loadStartTime) {
		this.loadStartTime = loadStartTime;
	}
	public String getLoadEndTime() {
		return loadEndTime;
	}
	public void setLoadEndTime(String loadEndTime) {
		this.loadEndTime = loadEndTime;
	}
	public int getCtnNumber() {
		return ctnNumber;
	}
	public void setCtnNumber(int ctnNumber) {
		this.ctnNumber = ctnNumber;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getShipUniqueNum() {
		return shipUniqueNum;
	}
	public void setShipUniqueNum(String shipUniqueNum) {
		this.shipUniqueNum = shipUniqueNum;
	}
	public String getConfirgFlag() {
		return confirgFlag;
	}
	public void setConfirgFlag(String confirgFlag) {
		this.confirgFlag = confirgFlag;
	}
	public String getBefterDraft() {
		return befterDraft;
	}
	public void setBefterDraft(String befterDraft) {
		this.befterDraft = befterDraft;
	}
	public String getAfterDraft() {
		return afterDraft;
	}
	public void setAfterDraft(String afterDraft) {
		this.afterDraft = afterDraft;
	}
	public String getCargoDes() {
		return cargoDes;
	}
	public void setCargoDes(String cargoDes) {
		this.cargoDes = cargoDes;
	}
	public String getNetWeight() {
		return netWeight;
	}
	public void setNetWeight(String netWeight) {
		this.netWeight = netWeight;
	}
	public String getRealCapacity() {
		return realCapacity;
	}
	public void setRealCapacity(String realCapacity) {
		this.realCapacity = realCapacity;
	}
	public String getPortPassenger() {
		return portPassenger;
	}
	public void setPortPassenger(String portPassenger) {
		this.portPassenger = portPassenger;
	}

	public String getCatogary() {
		return catogary;
	}

	public void setCatogary(String catogary) {
		this.catogary = catogary;
	}
	
}
