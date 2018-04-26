package com.skycloud.oa.outbound.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_pcs_arrival_plan")
public class OutArrivalPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int arrivalId;// 到港id
	private int type;// 类型
	private int tradeType;// 贸易类型 0：内贸 1：外贸
	private int shipId;// 船id
	private Timestamp arrivalStartTime;// 预计到港开始时间
	private Timestamp arrivalEndTime;// 预计到港结束时间
	private int productId;// 货品id
	private int clientId;// 货主id
	private String goodsTotal;// 预计总量
	private int shipAgentId;// 船代id
	private String requirement;// 仓储要求
	private String originalArea;// 产地
	private int createUserId;// 创建人id
	private Timestamp createTime;// 创建时间
	private int reviewUserId;// 审批人id
	private Timestamp reviewTime;// 审批时间
	private int status;// 状态，0：未提交；1：待审批；2：已审批通过；3：已审批退回

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getArrivalId() {
		return arrivalId;
	}

	public void setArrivalId(int arrivalId) {
		this.arrivalId = arrivalId;
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

	public int getShipId() {
		return shipId;
	}

	public void setShipId(int shipId) {
		this.shipId = shipId;
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