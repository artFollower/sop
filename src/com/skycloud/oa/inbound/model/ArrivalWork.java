package com.skycloud.oa.inbound.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "t_pcs_arrival_work")
public class ArrivalWork {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int arrivalId;//到港信息id
	private int type;//类型
	private int tradeType;//贸易类型，0：内贸；1：外贸
	private int contractId;//合同id
	private int cargoId;//货批id
	private int shipId;//船id
	private Timestamp arricalTime;//预计到港时间
	private int productId;//货品id
	private int clientId;//货主id
	private String goodsTotal;//预计总量
	private int shipAgentId;//船代id
	private String requirement;//仓储要求
	private String originalArea;//产地
	private int createUserId;//创建人用户id
	private Timestamp createTime;//创建时间
	private int reviewUserId;//审批人id
	private Timestamp reviewTime;//审批时间
	private int status;//状态，0：未提交；1：已提交；
	
	private Integer isDeclareCustom;//是否申报海关
	private Integer isCustomAgree;//海关是否同意卸货
	private String customLading;//海运提单号
	private String customLadingCount;//海运提单数量
	private String storageType;//仓储性质
	
	
	
	
	public String getStorageType() {
		return storageType;
	}
	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}
	public Integer getIsDeclareCustom() {
		return isDeclareCustom;
	}
	public void setIsDeclareCustom(Integer isDeclareCustom) {
		this.isDeclareCustom = isDeclareCustom;
	}
	public Integer getIsCustomAgree() {
		return isCustomAgree;
	}
	public void setIsCustomAgree(Integer isCustomAgree) {
		this.isCustomAgree = isCustomAgree;
	}
	public String getCustomLading() {
		return customLading;
	}
	public void setCustomLading(String customLading) {
		this.customLading = customLading;
	}
	public String getCustomLadingCount() {
		return customLadingCount;
	}
	public void setCustomLadingCount(String customLadingCount) {
		this.customLadingCount = customLadingCount;
	}
	public int getArrivalId() {
		return arrivalId;
	}
	public void setArrivalId(int arrivalId) {
		this.arrivalId = arrivalId;
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
	public int getTradeType() {
		return tradeType;
	}
	public void setTradeType(int tradeType) {
		this.tradeType = tradeType;
	}
	public int getContractId() {
		return contractId;
	}
	public void setContractId(int contractId) {
		this.contractId = contractId;
	}
	public int getCargoId() {
		return cargoId;
	}
	public void setCargoId(int cargoId) {
		this.cargoId = cargoId;
	}
	public int getShipId() {
		return shipId;
	}
	public void setShipId(int shipId) {
		this.shipId = shipId;
	}
	public Timestamp getArricalTime() {
		return arricalTime;
	}
	public void setArricalTime(Timestamp arricalTime) {
		this.arricalTime = arricalTime;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public String getGoodsTotal() {
		return goodsTotal;
	}
	public void setGoodsTotal(String goodsTotal) {
		this.goodsTotal = goodsTotal;
	}
	public int getShipAgentId() {
		return shipAgentId;
	}
	public void setShipAgentId(int shipAgentId) {
		this.shipAgentId = shipAgentId;
	}
	public String getRequirement() {
		return requirement;
	}
	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}
	public String getOriginalArea() {
		return originalArea;
	}
	public void setOriginalArea(String originalArea) {
		this.originalArea = originalArea;
	}
	public int getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public int getReviewUserId() {
		return reviewUserId;
	}
	public void setReviewUserId(int reviewUserId) {
		this.reviewUserId = reviewUserId;
	}
	public Timestamp getReviewTime() {
		return reviewTime;
	}
	public void setReviewTime(Timestamp reviewTime) {
		this.reviewTime = reviewTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	
}