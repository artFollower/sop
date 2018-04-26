package com.skycloud.oa.inbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 * 码头规费结算单
 * @author littleFly
 *
 */
@Entity
@Table(name = "t_pcs_dock_fee")
public class DockFee {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;//结算单id
	@Translation(name = "结算单编号")
	private String code;//结算单编号
	@Translation(name = "发票代码")
	private String billCode;//发票代码
	@Translation(name = "结算类型")
	private Integer accountType;//结算类型，1.现结，2.月结
	@Translation(name = "账单类型")
	private Integer tradeType;//账单类型，2.内贸账单，1.外贸账单
	@Translation(name = "发票类型")
	private Integer billType;//发票类型,手撕发票，增值税发票
	@Translation(name = "到港id")
	private Integer arrivalId;//arrivalId
	@Translation(name = "船代")
	private String clientName;//对方单位(船代)
	@Translation(name = "货品名")
	private String productName;//货品名
	@Translation(name = "合同号")
	private String contractName;//合同号
	@Translation(name = "超期小时数")
	private String overTime;//特殊靠泊费的超期小时数
	@Translation(name = "淡水费吨数")
	private String waterWeigh;//淡水费吨数
	@Translation(name = "结算时间")
	private Long accountTime;//结算时间
	@Translation(name = "创建时间")
	private Long createTime;//创建日期
	@Translation(name = "创建人id")
	private Integer createUserId;//创建人
	@Translation(name = "结算状态")
	private Integer status;//状态 0保存1提交
	public DockFee() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DockFee(Integer id, String code, String billCode, Integer accountType, Integer tradeType, Integer billType,
			Integer arrivalId, String clientName, String productName, String contractName, String overTime,
			String waterWeigh, Long accountTime, Long createTime, Integer createUserId, Integer status) {
		super();
		this.id = id;
		this.code = code;
		this.billCode = billCode;
		this.accountType = accountType;
		this.tradeType = tradeType;
		this.billType = billType;
		this.arrivalId = arrivalId;
		this.clientName = clientName;
		this.productName = productName;
		this.contractName = contractName;
		this.overTime = overTime;
		this.waterWeigh = waterWeigh;
		this.accountTime = accountTime;
		this.createTime = createTime;
		this.createUserId = createUserId;
		this.status = status;
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
	public String getBillCode() {
		return billCode;
	}
	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	public Integer getTradeType() {
		return tradeType;
	}
	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}
	public Integer getBillType() {
		return billType;
	}
	public void setBillType(Integer billType) {
		this.billType = billType;
	}
	public Integer getArrivalId() {
		return arrivalId;
	}
	public void setArrivalId(Integer arrivalId) {
		this.arrivalId = arrivalId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public String getOverTime() {
		return overTime;
	}
	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}
	public String getWaterWeigh() {
		return waterWeigh;
	}
	public void setWaterWeigh(String waterWeigh) {
		this.waterWeigh = waterWeigh;
	}
	public Long getAccountTime() {
		return accountTime;
	}
	public void setAccountTime(Long accountTime) {
		this.accountTime = accountTime;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	


}
