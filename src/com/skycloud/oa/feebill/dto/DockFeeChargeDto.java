/**
 * 
 */
package com.skycloud.oa.feebill.dto;

import com.skycloud.oa.annatation.Translation;

/**
 * @author feiying
 *
 */
public class DockFeeChargeDto {
	private Integer id;
	@Translation(name = "类型")
	private Integer type;
	@Translation(name = "费用类型")
	private Integer feeType;//费用类型1.首期费2.保安费3,固定费,4.超期费,
	@Translation(name = "费用单价")
	private Float unitFee;//费用单价
	@Translation(name = "费用数量")
	private Float feeCount;//费用数量
	@Translation(name = "全部费用")
	private Float totalFee;//全部费用
	@Translation(name = "费用账单id")
	private Integer feebillId;//费用账单id
	@Translation(name = "码头规费id")
	private Integer dockFeeId;
	@Translation(name = "是否生成账单")
	private Integer billType;//1.未生成账单2.已生成账单
	@Translation(name = "开始时间")
	private Long startTime;
	@Translation(name = "截止时间")
	private Long endTime;
	@Translation(name = "船代单位")
	private String clientName;
	@Translation(name = "贸易类型")
	private Integer tradeType;
	@Translation(name = "开票抬头")
	private String feeHead;
	private Long billTime;//账单时间
	
	public Long getBillTime() {
		return billTime;
	}

	public void setBillTime(Long billTime) {
		this.billTime = billTime;
	}

	public DockFeeChargeDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DockFeeChargeDto(Integer dockFeeId) {
		super();
		this.dockFeeId = dockFeeId;
	}

	public DockFeeChargeDto(Integer id, Integer type, Integer feeType,
			Float unitFee, Float feeCount, Float totalFee, Integer feebillId,
			Integer dockFeeId, Integer billType, Long startTime, Long endTime,
			String clientName, Integer tradeType) {
		super();
		this.id = id;
		this.type = type;
		this.feeType = feeType;
		this.unitFee = unitFee;
		this.feeCount = feeCount;
		this.totalFee = totalFee;
		this.feebillId = feebillId;
		this.dockFeeId = dockFeeId;
		this.billType = billType;
		this.startTime = startTime;
		this.endTime = endTime;
		this.clientName = clientName;
		this.tradeType = tradeType;
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

	public Integer getFeebillId() {
		return feebillId;
	}

	public void setFeebillId(Integer feebillId) {
		this.feebillId = feebillId;
	}

	public Integer getDockFeeId() {
		return dockFeeId;
	}

	public void setDockFeeId(Integer dockFeeId) {
		this.dockFeeId = dockFeeId;
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

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Integer getTradeType() {
		return tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	public String getFeeHead() {
		return feeHead;
	}

	public void setFeeHead(String feeHead) {
		this.feeHead = feeHead;
	}

	
}
