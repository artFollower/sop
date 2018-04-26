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
@Table(name = "t_pcs_fee_charge")
public class FeeCharge {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Translation(name = "费用项是否提交")
	private Integer type;//0，表示还未提交的费用项，1表示已经提交 的费用项
	@Translation(name = "费用类型")
	private Integer feeType;//费用类型1.仓储费2.保安费3,固定费(包罐费),4.超量费,5.超期费，6，其他费，8.通过费，//9.油品费,10.LAB
	@Translation(name = "费用单价")
	private String unitFee;//费用单价
	@Translation(name = "货物数量")
	private String feeCount;//费用数量
	@Translation(name = "全部费用")
	private String totalFee;//全部费用
	@Translation(name = "开票抬头")
	private String feeHead;//开票抬头
	@Translation(name = "货品id")
	private Integer productId;//货品id
	@Translation(name = "费用账单id")
	private Integer feebillId;//费用账单id
	@Translation(name = "首期费id")
	private Integer initialId;//首期费id
	@Translation(name = "超期费id")
	private Integer exceedId;//超期费id
	@Translation(name = "货主id")
	private Integer clientId;//货主id
	@Translation(name = "创建时间")
    private Long createTime;//创建时间	
	@Translation(name = "折扣后的金额")
    private String discountFee;//折扣后 费用
	@Translation(name = "备注")
    private String description;//备注
	@Translation(name = "是否是复制的费用")
    private Integer isCopy;//是否是复制后的费用项
	public FeeCharge() {
		super();
	}
	public FeeCharge(Integer id, Integer type, Integer feeType, String unitFee,
			String feeCount, String totalFee, String feeHead,
			Integer productId, Integer feebillId, Integer initialId,
			Integer exceedId, Integer clientId, Long createTime,
			String discountFee, String description) {
		super();
		this.id = id;
		this.type = type;
		this.feeType = feeType;
		this.unitFee = unitFee;
		this.feeCount = feeCount;
		this.totalFee = totalFee;
		this.feeHead = feeHead;
		this.productId = productId;
		this.feebillId = feebillId;
		this.initialId = initialId;
		this.exceedId = exceedId;
		this.clientId = clientId;
		this.createTime = createTime;
		this.discountFee = discountFee;
		this.description = description;
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
	public String getFeeHead() {
		return feeHead;
	}
	public void setFeeHead(String feeHead) {
		this.feeHead = feeHead;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getFeebillId() {
		return feebillId;
	}
	public void setFeebillId(Integer feebillId) {
		this.feebillId = feebillId;
	}
	public Integer getInitialId() {
		return initialId;
	}
	public void setInitialId(Integer initialId) {
		this.initialId = initialId;
	}
	public Integer getExceedId() {
		return exceedId;
	}
	public void setExceedId(Integer exceedId) {
		this.exceedId = exceedId;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
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
	public Integer getIsCopy() {
		return isCopy;
	}
	public void setIsCopy(Integer isCopy) {
		this.isCopy = isCopy;
	}

	
}
