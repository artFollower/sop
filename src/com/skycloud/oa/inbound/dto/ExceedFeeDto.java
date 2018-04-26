package com.skycloud.oa.inbound.dto;

import com.skycloud.oa.annatation.Translation;
import com.skycloud.oa.feebill.model.FeeCharge;
import com.skycloud.oa.inbound.model.ExceedFee;

public class ExceedFeeDto {
	private Integer id;
	@Translation(name = "超期结算单ids")
	private String ids;
	@Translation(name = "类型")
	private Integer type;
	@Translation(name = "货批id")
	private Integer cargoId;
	@Translation(name = "提单id")
	private Integer ladingId;
	@Translation(name = "是否全部显示")
	private Integer isShowAll;//1 全部显示
	@Translation(name = "开始时间")
	private Long startTime;
	@Translation(name = "提单起计日期")
	private Long ladingStartTime;//转卖提单起计日期
	@Translation(name = "截止时间")
	private Long endTime;
	@Translation(name = "货品id")
	private Integer productId;
	@Translation(name = "货主id")
	private Integer clientId;
	@Translation(name = "接收单位id")
	private Integer receiveClientId;
	@Translation(name = "货批号")
	private String cargoCode;
	@Translation(name = "提单号")
	private String ladingCode;
	@Translation(name = "是否结清")
	private Integer isFinish;
	@Translation(name = "列号")
	private Integer indexTh;
	@Translation(name = "状态")
	private Integer status;
	@Translation(name = "超期费结算单")
	private ExceedFee exceedFee;
	@Translation(name = "费用项")
	private FeeCharge feeCharge;
	public ExceedFeeDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ExceedFeeDto(Integer id, String ids, Integer type, Integer cargoId,
			Integer ladingId, Integer isShowAll, Long startTime,
			Long ladingStartTime, Long endTime, Integer productId,
			Integer clientId, Integer receiveClientId, String cargoCode,
			String ladingCode, Integer isFinish) {
		super();
		this.id = id;
		this.ids = ids;
		this.type = type;
		this.cargoId = cargoId;
		this.ladingId = ladingId;
		this.isShowAll = isShowAll;
		this.startTime = startTime;
		this.ladingStartTime = ladingStartTime;
		this.endTime = endTime;
		this.productId = productId;
		this.clientId = clientId;
		this.receiveClientId = receiveClientId;
		this.cargoCode = cargoCode;
		this.ladingCode = ladingCode;
		this.isFinish = isFinish;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
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
	public Integer getIsShowAll() {
		return isShowAll;
	}
	public void setIsShowAll(Integer isShowAll) {
		this.isShowAll = isShowAll;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getLadingStartTime() {
		return ladingStartTime;
	}
	public void setLadingStartTime(Long ladingStartTime) {
		this.ladingStartTime = ladingStartTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
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
	public Integer getReceiveClientId() {
		return receiveClientId;
	}
	public void setReceiveClientId(Integer receiveClientId) {
		this.receiveClientId = receiveClientId;
	}
	public String getCargoCode() {
		return cargoCode;
	}
	public void setCargoCode(String cargoCode) {
		this.cargoCode = cargoCode;
	}
	public String getLadingCode() {
		return ladingCode;
	}
	public void setLadingCode(String ladingCode) {
		this.ladingCode = ladingCode;
	}
	public Integer getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(Integer isFinish) {
		this.isFinish = isFinish;
	}
	public Integer getIndexTh() {
		return indexTh;
	}
	public void setIndexTh(Integer indexTh) {
		this.indexTh = indexTh;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public ExceedFee getExceedFee() {
		return exceedFee;
	}
	public void setExceedFee(ExceedFee exceedFee) {
		this.exceedFee = exceedFee;
	}
	public FeeCharge getFeeCharge() {
		return feeCharge;
	}
	public void setFeeCharge(FeeCharge feeCharge) {
		this.feeCharge = feeCharge;
	}

}
