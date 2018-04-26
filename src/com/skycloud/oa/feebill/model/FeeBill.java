/**
 * 
 */
package com.skycloud.oa.feebill.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 *账单
 * @author jiahy
 * @version 2015年6月14日 下午3:16:23
 */
@Entity
@Table(name = "t_pcs_fee_bill")
public class FeeBill {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Translation(name = "账单号")
	private String code;//账单号
	@Translation(name = "客户id")
	private Integer clientId;//客户名称
	@Translation(name = "账单费用类型")
	private Integer feeType;//账单费用类型
	@Translation(name = "全部费用应付金额")
	private String totalFee;//全部费用应付金额
	@Translation(name = "已经收取的金额")
	private String hasTotalFee;//已经得到的金额
	@Translation(name = "发票号")
	private String billingCode;//发票号
	@Translation(name = "开票抬头")
	private String feeHead;//开票抬头
	@Translation(name = "创建人id")
	private Integer createUserId;//创建人
	@Translation(name = "创建时间")
	private Long createTime;//创建时间
	@Translation(name = "审核人id")
	private Integer reviewUserId;//审核人
	@Translation(name = "审核时间")
	private Long reviewTime;//审核时间
	@Translation(name = "结算部日期")
	private Long accountTime;//结算日期---- 最后审核完成以后的日期  status ==2
	@Translation(name = "备注")
	private String description;//备注
	@Translation(name = "状态")
	private Integer status;//状态 ：未提交0、已提交1、（商务已审核）2、已到账4、已开发票
	@Translation(name = "到账金额")
	private String fundsTotalFee;//到账金额
	@Translation(name = "到账状态")
    private Integer fundsStatus;//到账状态：0.未到账1，未结清2，已到账
	@Translation(name = "开票状态")
    private Integer billingStatus;//开票状态：0.未开票1.已开票
	@Translation(name = "开票确认人id")
	private Integer billingUserId;//开票确认人
	@Translation(name = "开票日期")
	private Long   billingTime;//开票日期
	@Translation(name = "未税金额")
	private String unTaxFee;//未税金额
	@Translation(name = "税率")
	private String taxRate;//税率
	@Translation(name = "税额")
	private String taxFee;//税额
	@Translation(name = "开票备注")
	private String billingContent;//开票备注
	
	public FeeBill() {
		super();
		// TODO Auto-generated constructor stub
	}


	public FeeBill(Integer id, String code, Integer clientId, Integer feeType, String totalFee, String hasTotalFee,
			String billingCode, String feeHead, Integer createUserId, Long createTime, Integer reviewUserId,
			Long reviewTime, Long accountTime, String description, Integer status, String fundsTotalFee,
			Integer fundsStatus, Integer billingStatus, Integer billingUserId, Long billingTime, String unTaxFee,
			String taxRate, String taxFee) {
		super();
		this.id = id;
		this.code = code;
		this.clientId = clientId;
		this.feeType = feeType;
		this.totalFee = totalFee;
		this.hasTotalFee = hasTotalFee;
		this.billingCode = billingCode;
		this.feeHead = feeHead;
		this.createUserId = createUserId;
		this.createTime = createTime;
		this.reviewUserId = reviewUserId;
		this.reviewTime = reviewTime;
		this.accountTime = accountTime;
		this.description = description;
		this.status = status;
		this.fundsTotalFee = fundsTotalFee;
		this.fundsStatus = fundsStatus;
		this.billingStatus = billingStatus;
		this.billingUserId = billingUserId;
		this.billingTime = billingTime;
		this.unTaxFee = unTaxFee;
		this.taxRate = taxRate;
		this.taxFee = taxFee;
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


	public Integer getClientId() {
		return clientId;
	}


	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}


	public Integer getFeeType() {
		return feeType;
	}


	public void setFeeType(Integer feeType) {
		this.feeType = feeType;
	}


	public String getTotalFee() {
		return totalFee;
	}


	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}


	public String getHasTotalFee() {
		return hasTotalFee;
	}


	public void setHasTotalFee(String hasTotalFee) {
		this.hasTotalFee = hasTotalFee;
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


	public String getFundsTotalFee() {
		return fundsTotalFee;
	}


	public void setFundsTotalFee(String fundsTotalFee) {
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


	public String getUnTaxFee() {
		return unTaxFee;
	}


	public void setUnTaxFee(String unTaxFee) {
		this.unTaxFee = unTaxFee;
	}


	public String getTaxRate() {
		return taxRate;
	}


	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}


	public String getTaxFee() {
		return taxFee;
	}


	public void setTaxFee(String taxFee) {
		this.taxFee = taxFee;
	}


	public String getBillingContent() {
		return billingContent;
	}


	public void setBillingContent(String billingContent) {
		this.billingContent = billingContent;
	}

}
