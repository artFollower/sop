package com.skycloud.oa.inbound.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "t_pcs_storagefee_record")
public class StoragefeeRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id ;
	private int goodsId ; //货体id
	private BigDecimal storageFee ;//仓储费
	private BigDecimal goodsInspect ;//商检数量
	private String feeType ;//费用类型
	private int createUserId ;//创建人
	
	public BigDecimal getGoodsInspect() {
		return goodsInspect;
	}
	public void setGoodsInspect(BigDecimal goodsInspect) {
		this.goodsInspect = goodsInspect;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public BigDecimal getStorageFee() {
		return storageFee;
	}
	public void setStorageFee(BigDecimal storageFee) {
		this.storageFee = storageFee;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public int getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}
	
}
