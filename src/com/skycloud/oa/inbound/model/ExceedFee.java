package com.skycloud.oa.inbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 *超期费结算单
 * @author 作者:jiahy
 * @version 时间：2015年5月11日 下午9:33:57
 */
@Entity
@Table(name = "t_pcs_exceed_fee")
public class ExceedFee {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private Integer  id;//id
	@Translation(name = "结算单编号")
	private String   code;//编号
	@Translation(name = "类型")
	private Integer  type;//类型 1，货批 2.提单3.添加
	@Translation(name = "货批id")
	private Integer  cargoId;//货批id	
	@Translation(name = "提单id")
	private Integer  ladingId;//提单id
	@Translation(name = "超期费单价")
	private String    exceedFee;//超期费单价
	@Translation(name = "超期费数量")
	private String    exceedCount;//超期费数量	
	@Translation(name = "超期费总价")
	private String    exceedTotalFee;//超期费总价
	@Translation(name = "货品id")
	private Integer  productId;//货品id
	@Translation(name = "货主id")
	private Integer  clientId;//货主id
	@Translation(name = "起始时间")
	private Long     startTime;//起始时间
	@Translation(name = "截至时间")
	private Long     endTime;//截止时间
	@Translation(name = "创建人id")
	private Integer  createUserId;//创建人
	@Translation(name = "创建时间")
	private Long     createTime;//创建时间
	@Translation(name = "结算日期")
	private Long     accountTime;//结算日期---- 最后审核完成以后的日期  status ==2
	@Translation(name = "备注")
	private String   description;//备注
	@Translation(name = "状态")
	private Integer  status;//状态 ：未提交0、已提交1、已审核2、已生成账单3、已开票4、已完成5
	@Translation(name = "到账金额")
	private String    fundsTotalFee;//到账金额
	@Translation(name = "到账状态")
	private Integer  fundsStatus;//到账状态：0.未到账1，未结清2，已到账
	@Translation(name = "开票状态")
	private Integer  billingStatus;//开票状态：0.未开票1.已开票
	@Translation(name = "到账确认人id")
	private Integer  fundsUserId;//到账确认人
	@Translation(name = "到账日期")
	private Long     fundsTime;//到账日期
	@Translation(name = "开票确认人id")
	private Integer  billingUserId;//开票确认人
	@Translation(name = "开票日期")
	private Long     billingTime;//开票日期
	@Translation(name = "是否结清")
	private Integer  isFinish;//是否完成   1.表示已完成
public ExceedFee() {
	super();
	// TODO Auto-generated constructor stub
}
public ExceedFee(Integer id, String code, Integer type, Integer cargoId,
		Integer ladingId, String exceedFee, String exceedCount,
		String exceedTotalFee, Integer productId, Integer clientId,
		Long startTime, Long endTime, Integer createUserId, Long createTime,
		Long accountTime, String description, Integer status,
		Integer fundsUserId, Long fundsTime, Integer billingUserId,
		Long billingTime, Integer isFinish) {
	super();
	this.id = id;
	this.code = code;
	this.type = type;
	this.cargoId = cargoId;
	this.ladingId = ladingId;
	this.exceedFee = exceedFee;
	this.exceedCount = exceedCount;
	this.exceedTotalFee = exceedTotalFee;
	this.productId = productId;
	this.clientId = clientId;
	this.startTime = startTime;
	this.endTime = endTime;
	this.createUserId = createUserId;
	this.createTime = createTime;
	this.accountTime = accountTime;
	this.description = description;
	this.status = status;
	this.fundsUserId = fundsUserId;
	this.fundsTime = fundsTime;
	this.billingUserId = billingUserId;
	this.billingTime = billingTime;
	this.isFinish = isFinish;
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
public Integer getType() {
	return type;
}
public void setType(Integer type) {
	this.type = type;
}
public Integer getCargoId() {
	return cargoId;
}
public void setCargoId(Integer cargoId) {
	this.cargoId = cargoId;
}
public Integer getLadingId() {
	return ladingId;
}
public void setLadingId(Integer ladingId) {
	this.ladingId = ladingId;
}
public String getExceedFee() {
	return exceedFee;
}
public void setExceedFee(String exceedFee) {
	this.exceedFee = exceedFee;
}
public String getExceedCount() {
	return exceedCount;
}
public void setExceedCount(String exceedCount) {
	this.exceedCount = exceedCount;
}
public String getExceedTotalFee() {
	return exceedTotalFee;
}
public void setExceedTotalFee(String exceedTotalFee) {
	this.exceedTotalFee = exceedTotalFee;
}
public Integer getProductId() {
	return productId;
}
public void setProductId(Integer productId) {
	this.productId = productId;
}
public Integer getClientId() {
	return clientId;
}
public void setClientId(Integer clientId) {
	this.clientId = clientId;
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
public Integer getIsFinish() {
	return isFinish;
}
public void setIsFinish(Integer isFinish) {
	this.isFinish = isFinish;
}


}
