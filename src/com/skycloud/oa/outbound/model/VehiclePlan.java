package com.skycloud.oa.outbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_pcs_vehicle_plan")
public class VehiclePlan {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id ;
	private int trainId ;
	private double deliverNum ;
	private int ladingId ;
	private double actualNum ;
	private int goodsId ;
	private String storageInfo ;
	
	
	public String getStorageInfo() {
		return storageInfo;
	}
	public void setStorageInfo(String storageInfo) {
		this.storageInfo = storageInfo;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public double getActualNum() {
		return actualNum;
	}
	public void setActualNum(double actualNum) {
		this.actualNum = actualNum;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTrainId() {
		return trainId;
	}
	public void setTrainId(int trainId) {
		this.trainId = trainId;
	}
	public double getDeliverNum() {
		return deliverNum;
	}
	public void setDeliverNum(double deliverNum) {
		this.deliverNum = deliverNum;
	}
	public int getLadingId() {
		return ladingId;
	}
	public void setLadingId(int ladingId) {
		this.ladingId = ladingId;
	}
}
