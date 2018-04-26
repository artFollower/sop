package com.skycloud.oa.feebill.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 * @author jiahy
 * 仓储费用表
 */
@Entity
@Table(name = "t_pcs_dockfee_charge")
public class DockFeeCharge {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Translation(name = "费用项是否提交")
	private Integer type;//0，表示还未提交的费用项，1表示已经提交 的费用项
	@Translation(name = "费用类型")
	private Integer feeType;//费用类型 1靠泊费，2淡水费
	@Translation(name = "费用单价")
	private String unitFee;//费用单价
	@Translation(name = "费用数量")
	private String feeCount;//费用数量
	@Translation(name = "全部费用")
	private String totalFee;//全部费用
	@Translation(name = "创建时间")
    private Long createTime;//创建时间	
	@Translation(name = "折扣后的费用")
    private String discountFee;//折扣后 费用
	@Translation(name = "备注")
    private String description;//备注
	@Translation(name = "发票代码")
    private String billCode;//发票代码--码头规费
	@Translation(name = "发生单位")
    private String clientName;//发生单位
	@Translation(name = "码头规费id")
    private Integer dockFeeId;//码头规费id
	@Translation(name = "费用账单id")
    private Integer feebillId;//费用账单id
	public DockFeeCharge() {
		super();
	}
	public DockFeeCharge(Integer id, Integer type, Integer feeType, String unitFee, String feeCount, String totalFee,
			Long createTime, String discountFee, String description, String billCode, String clientName,
			Integer dockFeeId, Integer feebillId) {
		super();
		this.id = id;
		this.type = type;
		this.feeType = feeType;
		this.unitFee = unitFee;
		this.feeCount = feeCount;
		this.totalFee = totalFee;
		this.createTime = createTime;
		this.discountFee = discountFee;
		this.description = description;
		this.billCode = billCode;
		this.clientName = clientName;
		this.dockFeeId = dockFeeId;
		this.feebillId = feebillId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getFeeType() {
		return feeType;
	}
	public void setFeeType(Integer feeType) {
		this.feeType = feeType;
	}
	public String getUnitFee() {
		return unitFee;
	}
	public void setUnitFee(String unitFee) {
		this.unitFee = unitFee;
	}
	public String getFeeCount() {
		return feeCount;
	}
	public void setFeeCount(String feeCount) {
		this.feeCount = feeCount;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getDiscountFee() {
		return discountFee;
	}
	public void setDiscountFee(String discountFee) {
		this.discountFee = discountFee;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBillCode() {
		return billCode;
	}
	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public Integer getDockFeeId() {
		return dockFeeId;
	}
	public void setDockFeeId(Integer dockFeeId) {
		this.dockFeeId = dockFeeId;
	}
	public Integer getFeebillId() {
		return feebillId;
	}
	public void setFeebillId(Integer feebillId) {
		this.feebillId = feebillId;
	}

}
