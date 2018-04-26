package com.skycloud.oa.order.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

@Entity
@Table(name = "t_pcs_contract")
public class Contract {

	// Fields
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Translation(name = "合同编号")
	private String code;
	@Translation(name = "标题")
	private String title;
	@Translation(name = "合同类型")
	private int type;//合同类型：   1包罐 2包量 3 全年 4临租 5 通过
	@Translation(name = "客户id")
	private int clientId;
	@Translation(name = "货品id")
	private int productId;
	@Translation(name = "数量")
	private String quantity;
	// private String unitPrice;
	@Translation(name = "总价")
	private String totalPrice;

	// private String clientGroupId;//客户组id
	@Translation(name = "非保税仓储费")
	private String storagePrice; // 非保税仓储费
	@Translation(name = "保税仓储费")
	private String protectStoragePrice; // 保税仓储费
	@Translation(name = "码头通过费")
	private String passPrice;// 码头通过费
	@Translation(name = "超期费")
	private String overtimePrice;// 超期费
	@Translation(name = "港口设施保安费")
	private String portSecurityPrice;// 港口设施保安费
	@Translation(name = "港务费")
	private String portServicePrice;// 港务费
	@Translation(name = "其他费")
	private String otherPrice;// 其他费
	@Translation(name = "合同周期")
	private String period;// 合同周期
	@Translation(name = "描述")
	private String description;// 描述
	@Translation(name = "签约时间")
	private Timestamp signDate;// 签约时间
	@Translation(name = "合同电子档")
	private String fileUrl;// 合同电子档
	@Translation(name = "合同状态")
	private int status;// 合同状态  0：未提交   1：已提交  2： 已通过  3：已退回  4：已删除  5：已失效  6：已作废     7：商务部经理审批    8：财务审批   9：主管副总审批（抄送财务副总，抄送总经理）     
	@Translation(name = "创建人id")
	private int createUserId;// 创建人id
	@Column(name = "createTime", updatable = false)
	@Translation(name = "创建时间")
	private Timestamp createTime;// 创建时间
	@Translation(name = "修改人id")
	private int editUserId;// 修改人
	@Translation(name = "修改时间")
	private Timestamp editTime;// 修改时间
	@Translation(name = "损耗率")
	private String lossRate;// 损耗率
	@Translation(name = "开始时间")
	private Timestamp startDate;// 开始日期
	@Translation(name = "结束时间")
	private Timestamp endDate;// 结束日期
	@Translation(name = "补充条款")
	private String supplementary;// 补充条款
	@Translation(name = "意向id")
	private int intentionId;// 意向id
	@Translation(name = "贸易类型")
	private int tradeType;// 贸易类型
	@Translation(name = "原合同号")
	private int sourceContractId;// 原合同号
	@Translation(name = "货品")
	private String productNameList;// 多个货品名称
	@Translation(name = "货品id")
	private String productIdList;//多个货品id
	@Translation(name = "贸易类型")
	private String tradeTypeNameList;// 多个贸易类型
	@Translation(name = "审核人")
	private Integer reviewUserId;// 审核人
	@Translation(name = "审核时间")
	private Long reviewTime;// 审核时间
	@Translation(name = "审核意见")
	private String reviewContent;// 审核意见
	@Translation(name = "预计到港时间")
	private Timestamp arrivalTime; //预计到港时间
	@Translation(name = "特殊需求")
	private String spDes;//特殊需求

	public String getSpDes() {
		return spDes;
	}

	public void setSpDes(String spDes) {
		this.spDes = spDes;
	}



	public Timestamp getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Timestamp arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getProtectStoragePrice() {
		return protectStoragePrice;
	}

	public void setProtectStoragePrice(String protectStoragePrice) {
		this.protectStoragePrice = protectStoragePrice;
	}

	public String getProductIdList() {
		return productIdList;
	}

	public void setProductIdList(String productIdList) {
		this.productIdList = productIdList;
	}

	public String getTradeTypeNameList() {
		return tradeTypeNameList;
	}

	public void setTradeTypeNameList(String tradeTypeNameList) {
		this.tradeTypeNameList = tradeTypeNameList;
	}

	public String getProductNameList() {
		return productNameList;
	}

	public void setProductNameList(String productNameList) {
		this.productNameList = productNameList;
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

	public String getReviewContent() {
		return reviewContent;
	}

	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}

	public String getStoragePrice() {
		return storagePrice;
	}

	public void setStoragePrice(String storagePrice) {
		this.storagePrice = storagePrice;
	}

	public String getPassPrice() {
		return passPrice;
	}

	public void setPassPrice(String passPrice) {
		this.passPrice = passPrice;
	}

	public String getOvertimePrice() {
		return overtimePrice;
	}

	public void setOvertimePrice(String overtimePrice) {
		this.overtimePrice = overtimePrice;
	}

	public String getPortSecurityPrice() {
		return portSecurityPrice;
	}

	public void setPortSecurityPrice(String portSecurityPrice) {
		this.portSecurityPrice = portSecurityPrice;
	}

	public String getPortServicePrice() {
		return portServicePrice;
	}

	public void setPortServicePrice(String portServicePrice) {
		this.portServicePrice = portServicePrice;
	}

	public String getOtherPrice() {
		return otherPrice;
	}

	public void setOtherPrice(String otherPrice) {
		this.otherPrice = otherPrice;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public int getSourceContractId() {
		return sourceContractId;
	}

	public void setSourceContractId(int sourceContractId) {
		this.sourceContractId = sourceContractId;
	}

	public int getTradeType() {
		return tradeType;
	}

	public void setTradeType(int tradeType) {
		this.tradeType = tradeType;
	}

	public int getIntentionId() {
		return intentionId;
	}

	public void setIntentionId(int intentionId) {
		this.intentionId = intentionId;
	}

	public String getLossRate() {
		return lossRate;
	}

	public void setLossRate(String lossRate) {
		this.lossRate = lossRate;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public String getSupplementary() {
		return supplementary;
	}

	public void setSupplementary(String supplementary) {
		this.supplementary = supplementary;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	// public String getUnitPrice() {
	// return unitPrice;
	// }
	// public void setUnitPrice(String unitPrice) {
	// this.unitPrice = unitPrice;
	// }
	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getSignDate() {
		return signDate;
	}

	public void setSignDate(Timestamp signDate) {
		this.signDate = signDate;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public int getEditUserId() {
		return editUserId;
	}

	public void setEditUserId(int editUserId) {
		this.editUserId = editUserId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getEditTime() {
		return editTime;
	}

	public void setEditTime(Timestamp editTime) {
		this.editTime = editTime;
	}

}