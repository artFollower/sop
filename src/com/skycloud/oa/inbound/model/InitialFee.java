package com.skycloud.oa.inbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 *首期费
 * @author 作者:jiahy
 * @version 时间：2015年5月6日 下午5:09:39
 */
@Entity
@Table(name = "t_pcs_initial_fee")
public class InitialFee {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;//首期费id
	@Translation(name = "结算单编号")
	private String code;//结算单编号
	@Translation(name = "货批id")
	private Integer cargoId;//货批id
	@Translation(name = "合同id")
	private Integer contractId;//合同id
	@Translation(name = "合同的数量")
	private Float contractNum;//合同的数量
	@Translation(name = "类型")
	private Integer type;//类型 1.货批生成的结算单，2.合同生成的结算单，3.油品结算（通过费），4.出库保安费
	@Translation(name = "首期总费用")
	private String totalFee;//首期总费用
	@Translation(name = "创建人")
	private Integer createUserId;//创建人
	@Translation(name = "创建时间")
	private Long createTime;//创建时间
	@Translation(name = "结算日期")
	private Long accountTime;//结算日期---- 最后审核完成以后的日期  status ==2
	@Translation(name = "备注")
	private String description;//备注
	@Translation(name = "状态")
	private Integer status;//状态 ：未提交0、已提交1、已审核2、已生成账单3、已开票4、已完成5
	@Translation(name = "是否结清")
	private Integer isFinish;//是否完成
	@Translation(name = "开始时间")
	private Long startTime;//  包罐合同的结算单开始时间
	@Translation(name = "截止时间")
	private Long endTime;//   包罐合同的结算单的结束时间
	@Translation(name = "货批号")
	private String cargoCodes;// 包罐合同下的货批
	@Translation(name = "货主id")
	private Integer clientId;
	@Translation(name = "货品id")
	private Integer productId;
	@Translation(name = "到港id")
	private Integer arrivalId;//船舶出库保安费
	public InitialFee() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public InitialFee(Integer id, String code, Integer cargoId, Integer contractId, Float contractNum, Integer type,
			String totalFee, Integer createUserId, Long createTime, Long accountTime, String description,
			Integer status, Integer isFinish, Long startTime, Long endTime, String cargoCodes, Integer clientId,
			Integer productId, Integer arrivalId) {
		super();
		this.id = id;
		this.code = code;
		this.cargoId = cargoId;
		this.contractId = contractId;
		this.contractNum = contractNum;
		this.type = type;
		this.totalFee = totalFee;
		this.createUserId = createUserId;
		this.createTime = createTime;
		this.accountTime = accountTime;
		this.description = description;
		this.status = status;
		this.isFinish = isFinish;
		this.startTime = startTime;
		this.endTime = endTime;
		this.cargoCodes = cargoCodes;
		this.clientId = clientId;
		this.productId = productId;
		this.arrivalId = arrivalId;
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
	public Integer getCargoId() {
		return cargoId;
	}
	public void setCargoId(Integer cargoId) {
		this.cargoId = cargoId;
	}
	public Integer getContractId() {
		return contractId;
	}
	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}
	public Float getContractNum() {
		return contractNum;
	}
	public void setContractNum(Float contractNum) {
		this.contractNum = contractNum;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
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
	public Integer getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(Integer isFinish) {
		this.isFinish = isFinish;
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
	public String getCargoCodes() {
		return cargoCodes;
	}
	public void setCargoCodes(String cargoCodes) {
		this.cargoCodes = cargoCodes;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getArrivalId() {
		return arrivalId;
	}
	public void setArrivalId(Integer arrivalId) {
		this.arrivalId = arrivalId;
	}


}
