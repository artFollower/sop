package com.skycloud.oa.esb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 港务
 * @ClassName: HarborContainer 
 * @Description: TODO
 * @author xie
 * @date 2015年1月22日 下午9:43:10
 */
@Entity
@Table(name="t_esb_harbor")
public class Harbor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String recordId = "52";//记录类型标识
	private String dischargePortCode;//卸货港代码
	private String dischargePort;//卸货港
	private String loadPortCode;//装货港代码
	private String loadPort;//装货港
	private String deliveryCode;//交货地代码
	private String delivery;//交货地
	private String grossWeight;//箱毛重
	
	private String catogary;//分类：集装箱/散货
	
	private int objectId;
	
	public String toBulkString() {
		return "52:"+dischargePortCode+":"+dischargePort+":"+loadPortCode+":"+loadPort+":"+deliveryCode+":"+delivery+"'";
	}
	
	public String toConString() {
		return "52:"+dischargePortCode+":"+dischargePort+":"+loadPortCode+":"+loadPort+":"+deliveryCode+":"+delivery+":"+grossWeight+"'";
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

	public String getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}

	public String getCatogary() {
		return catogary;
	}

	public void setCatogary(String catogary) {
		this.catogary = catogary;
	}

	public int getObjectId() {
		return objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}
	
	
}
