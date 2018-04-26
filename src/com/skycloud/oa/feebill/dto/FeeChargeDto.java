/**
 * 
 */
package com.skycloud.oa.feebill.dto;

import com.skycloud.oa.annatation.Translation;

/**
 * @author feiying
 *
 */
public class FeeChargeDto {
	private Integer id;
	@Translation(name = "类型")
	private Integer type;
	@Translation(name = "类型")
	private Integer feeType;//费用类型1.首期费2.保安费3,固定费,4.超期费,
	@Translation(name = "类型")
	private Float unitFee;//费用单价
	@Translation(name = "费用数量")
	private Float feeCount;//费用数量
	@Translation(name = "全部费用")
	private Float totalFee;//全部费用
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
	@Translation(name = "是否生成账单")
	private Integer billType;//1.未生成账单2.已生成账单
	@Translation(name = "开始时间")
	private Long startTime;
	@Translation(name = "截止时间")
	private Long endTime;
	@Translation(name = "货批号id")
	private Integer cargoId;//货批号
	@Translation(name = "其他费id")
	private Integer otherFeeId;
	public FeeChargeDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FeeChargeDto(Integer id, Integer type, Integer feeType,
			Float unitFee, Float feeCount, Float totalFee, String feeHead,
			Integer productId, Integer feebillId, Integer initialId,
			Integer exceedId, Integer clientId, Integer billType,
			Long startTime, Long endTime, Integer cargoId) {
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
		this.billType = billType;
		this.startTime = startTime;
		this.endTime = endTime;
		this.cargoId = cargoId;
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
	public Float getUnitFee() {
		return unitFee;
	}
	public void setUnitFee(Float unitFee) {
		this.unitFee = unitFee;
	}
	public Float getFeeCount() {
		return feeCount;
	}
	public void setFeeCount(Float feeCount) {
		this.feeCount = feeCount;
	}
	public Float getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Float totalFee) {
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
	public Integer getBillType() {
		return billType;
	}
	public void setBillType(Integer billType) {
		this.billType = billType;
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
	public Integer getCargoId() {
		return cargoId;
	}
	public void setCargoId(Integer cargoId) {
		this.cargoId = cargoId;
	}
	
	public Integer getOtherFeeId() {
		return otherFeeId;
	}
	public void setOtherFeeId(Integer otherFeeId) {
		this.otherFeeId = otherFeeId;
	}
}
