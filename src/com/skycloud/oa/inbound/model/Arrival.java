package com.skycloud.oa.inbound.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 * 到港信息
 * @author jiahy
 *
 */
@Entity
@Table(name = "t_pcs_arrival")
public class Arrival {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Translation(name = "类型")
	private int type; //类型 1:入库 2：出库 3: 入库通过 4:虚拟库5.出库通过  9:出港作废 10：入港作废  13：入库/通过
	@Translation(name = "船舶id")
	private int shipId;//船id
	@Translation(name = "船舶中文名")
	private int shipRefId;//船舶助记中文名
	@Translation(name = "开始时间")
	private Timestamp arrivalStartTime;//开始时间
	@Translation(name = "结束时间")
	private Timestamp arrivalEndTime;//结束时间
	@Translation(name = "船检时间")
	private String goodsShip;//船检时间
	@Translation(name = "船代id")
	private int shipAgentId;//船代id
	@Translation(name = "说明")
	private String description;//说明
	@Translation(name = "到港状态")
	private int status;//到港状态：0：预计到港；1：已到港；2：已离港// 流程状态  2,入库计划3,靠泊评估4,靠泊方案
	@Translation(name = "货品id")
	private int productId ;//货品id
	@Translation(name = "泊位")
	private int berthId ;//泊位（为军）
	@Translation(name = "调度日志基本信息")
	private int dispatchId;//调度日志基本信息
	@Translation(name = "创建人")
	private Integer createUserId;//创建人
	@Translation(name = "创建时间")
	private Long createTime;
	@Translation(name = "审核到港人")
	private Integer reviewArrivalUserId;
	@Translation(name = "审批时间")
	private Long reviewArrivalTime;
	@Translation(name = "审核人")
	private Integer reviewUserId;
	@Translation(name = "审核时间")
	private Long reviewTime;
	@Translation(name = "审批字段")
	private Integer reviewStatus;//审批字段 0，保存，1，提交，2，通过3，驳回//审批状态
	@Translation(name = "是否可以回退")	
	private Integer isCanBack;//是否可以回退 0,可以,1，不可以
	@Translation(name = "是否申报海关")
	private Integer isDeclareCustom;//是否申报海关
	@Translation(name = "海关是否同意卸货")
	private Integer isCustomAgree;//海关是否同意卸货
	@Translation(name = "海运提单号")
	private String customLading;//海运提单号
	@Translation(name = "海运提单数量")
	private String customLadingCount;//海运提单数量
	@Translation(name = "是否理货")
	private Integer isTrim;//是否理货。
	@Translation(name = "产地")
	private String originalArea; //产地
	
	public String getOriginalArea() {
		return originalArea;
	}
	public void setOriginalArea(String originalArea) {
		this.originalArea = originalArea;
	}
	public Integer getIsTrim() {
		return isTrim;
	}
	public void setIsTrim(Integer isTrim) {
		this.isTrim = isTrim;
	}
	public Arrival() { 
		super();
		// TODO Auto-generated constructor stub
	}
	public Arrival(int id,int status) {
		super();
		this.id = id;
		this.status = status;
	}
	public Arrival(int id, int type, int shipId, int shipRefId,
			Timestamp arrivalStartTime, Timestamp arrivalEndTime,
			String goodsShip, int shipAgentId, String description, int status,
			int productId, int berthId, int dispatchId, Integer createUserId,
			Long createTime, Integer reviewArrivalUserId,
			Long reviewArrivalTime, Integer reviewStatus, Integer isCanBack,
			Integer isDeclareCustom, Integer isCustomAgree,
			String customLading, String customLadingCount) {
		super();
		this.id = id;
		this.type = type;
		this.shipId = shipId;
		this.shipRefId = shipRefId;
		this.arrivalStartTime = arrivalStartTime;
		this.arrivalEndTime = arrivalEndTime;
		this.goodsShip = goodsShip;
		this.shipAgentId = shipAgentId;
		this.description = description;
		this.status = status;
		this.productId = productId;
		this.berthId = berthId;
		this.dispatchId = dispatchId;
		this.createUserId = createUserId;
		this.createTime = createTime;
		this.reviewArrivalUserId = reviewArrivalUserId;
		this.reviewArrivalTime = reviewArrivalTime;
		this.reviewStatus = reviewStatus;
		this.isCanBack = isCanBack;
		this.isDeclareCustom = isDeclareCustom;
		this.isCustomAgree = isCustomAgree;
		this.customLading = customLading;
		this.customLadingCount = customLadingCount;
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

	public int getShipId() {
		return shipId;
	}

	public void setShipId(int shipId) {
		this.shipId = shipId;
	}

	public int getShipRefId() {
		return shipRefId;
	}

	public void setShipRefId(int shipRefId) {
		this.shipRefId = shipRefId;
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

	public String getGoodsShip() {
		return goodsShip;
	}

	public void setGoodsShip(String goodsShip) {
		this.goodsShip = goodsShip;
	}

	public int getShipAgentId() {
		return shipAgentId;
	}

	public void setShipAgentId(int shipAgentId) {
		this.shipAgentId = shipAgentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getBerthId() {
		return berthId;
	}

	public void setBerthId(int berthId) {
		this.berthId = berthId;
	}

	public int getDispatchId() {
		return dispatchId;
	}

	public void setDispatchId(int dispatchId) {
		this.dispatchId = dispatchId;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Integer getReviewArrivalUserId() {
		return reviewArrivalUserId;
	}

	public void setReviewArrivalUserId(Integer reviewArrivalUserId) {
		this.reviewArrivalUserId = reviewArrivalUserId;
	}

	public Long getReviewArrivalTime() {
		return reviewArrivalTime;
	}

	public void setReviewArrivalTime(Long reviewArrivalTime) {
		this.reviewArrivalTime = reviewArrivalTime;
	}

	public Integer getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(Integer reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public Integer getIsCanBack() {
		return isCanBack;
	}

	public void setIsCanBack(Integer isCanBack) {
		this.isCanBack = isCanBack;
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
	public Integer getReviewUserId() {
		return reviewUserId;
	}
	public void setReviewUserId(Integer reviewUserId) {
		this.reviewUserId = reviewUserId;
	}
	public Long getReviewTime() {
		return reviewTime;
	}
	public void setReviewTime(Long reviewTime) {
		this.reviewTime = reviewTime;
	}


}