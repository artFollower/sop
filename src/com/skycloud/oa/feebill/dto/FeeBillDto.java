/**
 * 
 */
package com.skycloud.oa.feebill.dto;

import com.skycloud.oa.annatation.Translation;

/**
 *
 * @author jiahy
 * @version 2015年6月14日 下午3:54:56
 */
public class FeeBillDto {

	private Integer id;
	@Translation(name = "账单号")
	private String  code;//账单号
	@Translation(name = "费用类型")
	private Integer feeType;//费用类型
	@Translation(name = "客户名称")
	private Integer clientId;//客户名称
	@Translation(name = "全部费用应付金额")
	private String   totalFee;//全部费用应付金额
	@Translation(name = "费用账单id")
	private Integer feebillId;//费用账单id
	@Translation(name = "发票号")
	private String  billingCode;//发票号
	@Translation(name = "开票抬头")
	private String  feeHead;//开票抬头
	@Translation(name = "创建人id")
	private Integer createUserId;//创建人
	@Translation(name = "创建时间")
	private Long    createTime;//创建时间
	@Translation(name = "审核人id")
	private Integer reviewUserId;//审核人
	@Translation(name = "审核时间")
	private Long    reviewTime;//审核时间
	@Translation(name = "结算日期")
	private Long    accountTime;//结算日期---- 最后审核完成以后的日期  status ==2
	@Translation(name = "备注")
	private String  description;//备注
	@Translation(name = "状态")
	private Integer status;//状态 ：未提交0、已提交1、（商务已审核、财务已审核）2、已到账4、已开发票、5  已退回3、已撤销6
	@Translation(name = "到账金额")
	private Float   fundsTotalFee;//到账金额
	@Translation(name = "到账状态")
    private Integer fundsStatus;//到账状态：0.未到账1，未结清2，已到账
	@Translation(name = "开票状态")
    private Integer billingStatus;//开票状态：0.未开票1.已开票
	@Translation(name = "到账确认人id")
	private Integer fundsUserId;//到账确认人
	@Translation(name = "到账日期")
	private Long    fundsTime;//到账日期
	@Translation(name = "开票确认人id")
	private Integer billingUserId;//开票确认人
	@Translation(name = "开票日期")
	private Long    billingTime;//开票日期
	@Translation(name = "状态")
    private String	statusStr;//状态
	@Translation(name = "账单状态")
    private Integer billStatus;//账单状态
	@Translation(name = "开始时间")
	private Long   startTime;
	@Translation(name = "截止时间")
	private Long  endTime;
	@Translation(name = "开票开始时间")
	private Long billingStartTime;//开票开始时间
	@Translation(name = "开票结束时间")
	private Long billingEndTime;//开票结束时间
	@Translation(name = "多个费用类型")
	private String feeTypes;//多个费用类型
	@Translation(name = "货品id")
	private Integer productId;//货品id
	@Translation(name = "货批号id")
	private Integer  cargoId;//货批号
	@Translation(name = "货批号")
	private String  cargoCode;//货批号
	@Translation(name = "首期费类型")
	private Integer initialType;
	@Translation(name = "列表列号")
	private Integer indexTh;
	public Integer getCargoId() {
		return cargoId;
	}
	public void setCargoId(Integer cargoId) {
		this.cargoId = cargoId;
	}
	public FeeBillDto() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getFeeType() {
		return feeType;
	}
	public void setFeeType(Integer feeType) {
		this.feeType = feeType;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	 
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	public Integer getFeebillId() {
		return feebillId;
	}
	public void setFeebillId(Integer feebillId) {
		this.feebillId = feebillId;
	}
	public String getBillingCode() {
		return billingCode;
	}
	public void setBillingCode(String billingCode) {
		this.billingCode = billingCode;
	}
	public String getFeeHead() {
		return feeHead;
	}
	public void setFeeHead(String feeHead) {
		this.feeHead = feeHead;
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
	public Long getAccountTime() {
		return accountTime;
	}
	public void setAccountTime(Long accountTime) {
		this.accountTime = accountTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Float getFundsTotalFee() {
		return fundsTotalFee;
	}
	public void setFundsTotalFee(Float fundsTotalFee) {
		this.fundsTotalFee = fundsTotalFee;
	}
	public Integer getFundsStatus() {
		return fundsStatus;
	}
	public void setFundsStatus(Integer fundsStatus) {
		this.fundsStatus = fundsStatus;
	}
	public Integer getBillingStatus() {
		return billingStatus;
	}
	public void setBillingStatus(Integer billingStatus) {
		this.billingStatus = billingStatus;
	}
	public Integer getFundsUserId() {
		return fundsUserId;
	}
	public void setFundsUserId(Integer fundsUserId) {
		this.fundsUserId = fundsUserId;
	}
	public Long getFundsTime() {
		return fundsTime;
	}
	public void setFundsTime(Long fundsTime) {
		this.fundsTime = fundsTime;
	}
	public Integer getBillingUserId() {
		return billingUserId;
	}
	public void setBillingUserId(Integer billingUserId) {
		this.billingUserId = billingUserId;
	}
	public Long getBillingTime() {
		return billingTime;
	}
	public void setBillingTime(Long billingTime) {
		this.billingTime = billingTime;
	}
	public String getStatusStr() {
		return statusStr;
	}
	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public Integer getBillStatus() {
		return billStatus;
	}
	public void setBillStatus(Integer billStatus) {
		this.billStatus = billStatus;
	}
	public Long getBillingStartTime() {
		return billingStartTime;
	}
	public void setBillingStartTime(Long billingStartTime) {
		this.billingStartTime = billingStartTime;
	}
	public Long getBillingEndTime() {
		return billingEndTime;
	}
	public void setBillingEndTime(Long billingEndTime) {
		this.billingEndTime = billingEndTime;
	}
	public String getFeeTypes() {
		return feeTypes;
	}
	public void setFeeTypes(String feeTypes) {
		this.feeTypes = feeTypes;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getInitialType() {
		return initialType;
	}
	public void setInitialType(Integer initialType) {
		this.initialType = initialType;
	}
	public Integer getIndexTh() {
		return indexTh;
	}
	public void setIndexTh(Integer indexTh) {
		this.indexTh = indexTh;
	}
	public String getCargoCode() {
		return cargoCode;
	}
	public void setCargoCode(String cargoCode) {
		this.cargoCode = cargoCode;
	}
	
}
