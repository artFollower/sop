package com.skycloud.oa.inbound.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;


@Entity
@Table(name = "t_pcs_arrival_plan")
public class ArrivalPlan{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;   //$--->表示出港计划对应字段
	@Translation(name = "到港id")
	private int arrivalId;//到港id$
	@Translation(name = "类型")
	private int type;//类型$
	@Translation(name = "贸易类型")
	private int tradeType;//贸易类型 1.内贸2.外贸，3.保税 $
	@Translation(name = "船舶id")
	private int shipId;//船id$
	@Translation(name = "预计到港开始时间")
	private Timestamp arrivalStartTime;//预计到港开始时间
	@Translation(name = "预计到港结束时间")
	private Timestamp arrivalEndTime;//预计到港结束时间
	@Translation(name = "货品id")
	private int productId;//货品id
	@Translation(name = "货主id")
	private int clientId;//货主id$
	@Translation(name = "预计总量")
	private String goodsTotal;//预计总量$
	@Translation(name = "船代id")
	private int shipAgentId;//船代id
	@Translation(name = "仓储需求")
	private String requirement;//仓储要求
	@Translation(name = "产地")
	private String originalArea;//产地
	@Translation(name = "创建人id")
	private int createUserId;//创建人id$
	@Translation(name = "创建时间")
	private Timestamp createTime;//创建时间$
	@Translation(name = "审批人id")
	private int reviewUserId;//审批人id
	@Translation(name = "审批时间")
	private Timestamp reviewTime;//审批时间
	@Translation(name = "状态")
	private int status;//状态，0：未提交；1：待审批；2：已审批通过；3：已审批退回
	@Translation(name = "提单")
	private int ladingId ;
	@Translation(name = "实际数量")
	private String actualNum ;
	@Translation(name = "货体id")
	private int goodsId;//$
	@Translation(name = "是否核销")
	private String isVerification ;//是否核销$
	@Translation(name = "流向")
	private String flow ;//流向$
	@Translation(name = "发货提单号")
	private String ladingCode;//发货提单号$
	@Translation(name = "船舶id")
	private int arrivalShipId ;
	@Translation(name = "储罐代码")
	private String tankCodes;//储罐信息   出库$
	@Translation(name = "提货单位")
	private Integer ladingClientId;//提货单位$
	@Translation(name = "货批信息")
	private Integer cargoId;//选择货批信息$
	@Translation(name = "报关单位")
	private Integer clearanceClientId;//报关单位$
	@Translation(name = "实际提货人")
	private Integer actualLadingClientId;//实际提货人$
	@Translation(name = "是否申报海关")
	private Integer isDeclareCustom;//是否申报海关
	@Translation(name = "海关是否同意")
	private Integer isCustomAgree;//海关是否同意卸货
	@Translation(name = "海运提单号")
	private String customLading;//海运提单号
	@Translation(name = "海运提单数量")
	private String customLadingCount;//海运提单数量
	@Translation(name = "单号")
	private String No;//打印单号。

	public ArrivalPlan() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ArrivalPlan(int id, int arrivalId, int type, int tradeType,
			int shipId, Timestamp arrivalStartTime, Timestamp arrivalEndTime,
			int productId, int clientId, String goodsTotal, int shipAgentId,
			String requirement, String originalArea, int createUserId,
			Timestamp createTime, int reviewUserId, Timestamp reviewTime,
			int status, int ladingId, String actualNum, int goodsId,
			String isVerification, String flow, String ladingCode,
			int arrivalShipId, String tankCodes, Integer ladingClientId,
			Integer cargoId, Integer clearanceClientId,
			Integer actualLadingClientId, Integer isDeclareCustom,
			Integer isCustomAgree, String customLading,
			String customLadingCount, String no) {
		super();
		this.id = id;
		this.arrivalId = arrivalId;
		this.type = type;
		this.tradeType = tradeType;
		this.shipId = shipId;
		this.arrivalStartTime = arrivalStartTime;
		this.arrivalEndTime = arrivalEndTime;
		this.productId = productId;
		this.clientId = clientId;
		this.goodsTotal = goodsTotal;
		this.shipAgentId = shipAgentId;
		this.requirement = requirement;
		this.originalArea = originalArea;
		this.createUserId = createUserId;
		this.createTime = createTime;
		this.reviewUserId = reviewUserId;
		this.reviewTime = reviewTime;
		this.status = status;
		this.ladingId = ladingId;
		this.actualNum = actualNum;
		this.goodsId = goodsId;
		this.isVerification = isVerification;
		this.flow = flow;
		this.ladingCode = ladingCode;
		this.arrivalShipId = arrivalShipId;
		this.tankCodes = tankCodes;
		this.ladingClientId = ladingClientId;
		this.cargoId = cargoId;
		this.clearanceClientId = clearanceClientId;
		this.actualLadingClientId = actualLadingClientId;
		this.isDeclareCustom = isDeclareCustom;
		this.isCustomAgree = isCustomAgree;
		this.customLading = customLading;
		this.customLadingCount = customLadingCount;
		No = no;
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

	public int getLadingId() {
		return ladingId;
	}

	public void setLadingId(int ladingId) {
		this.ladingId = ladingId;
	}

	public String getActualNum() {
		return actualNum;
	}

	public void setActualNum(String actualNum) {
		this.actualNum = actualNum;
	}

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public String getIsVerification() {
		return isVerification;
	}

	public void setIsVerification(String isVerification) {
		this.isVerification = isVerification;
	}

	public String getFlow() {
		return flow;
	}

	public void setFlow(String flow) {
		this.flow = flow;
	}

	public String getLadingCode() {
		return ladingCode;
	}

	public void setLadingCode(String ladingCode) {
		this.ladingCode = ladingCode;
	}

	public int getArrivalShipId() {
		return arrivalShipId;
	}

	public void setArrivalShipId(int arrivalShipId) {
		this.arrivalShipId = arrivalShipId;
	}

	public String getTankCodes() {
		return tankCodes;
	}

	public void setTankCodes(String tankCodes) {
		this.tankCodes = tankCodes;
	}

	public Integer getLadingClientId() {
		return ladingClientId;
	}

	public void setLadingClientId(Integer ladingClientId) {
		this.ladingClientId = ladingClientId;
	}

	public Integer getCargoId() {
		return cargoId;
	}

	public void setCargoId(Integer cargoId) {
		this.cargoId = cargoId;
	}

	public Integer getClearanceClientId() {
		return clearanceClientId;
	}

	public void setClearanceClientId(Integer clearanceClientId) {
		this.clearanceClientId = clearanceClientId;
	}

	public Integer getActualLadingClientId() {
		return actualLadingClientId;
	}

	public void setActualLadingClientId(Integer actualLadingClientId) {
		this.actualLadingClientId = actualLadingClientId;
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

	public String getNo() {
		return No;
	}

	public void setNo(String no) {
		No = no;
	}
	
	
}