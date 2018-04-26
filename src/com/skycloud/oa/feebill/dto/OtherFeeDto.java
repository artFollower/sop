/**
 * 
 */
package com.skycloud.oa.feebill.dto;

import java.util.List;

import com.skycloud.oa.annatation.Translation;
import com.skycloud.oa.feebill.model.FeeCharge;
import com.skycloud.oa.feebill.model.OtherFeeCargo;
import com.skycloud.oa.utils.OaMsg;

/**
 *
 * @author jiahy
 * @version 2015年10月21日 下午2:41:45
 */
public class OtherFeeDto {
	private Integer id;//首期费id
	@Translation(name = "结算单编号")
	private String code;//结算单编号
	@Translation(name = "类型")
	private Integer type;//类型  预留
	@Translation(name = "创建人id")
	private Integer createUserId;//创建人
	@Translation(name = "创建时间")
	private Long createTime;//创建时间
	@Translation(name = "结算日期")
	private Long accountTime;//结算日期---- 最后审核完成以后的日期  status ==2
	@Translation(name = "备注")
	private String description;//备注
	@Translation(name = "状态")
	private Integer status;//状态 ：未提交0、已提交1、
	@Translation(name = "货主id")
    private Integer clientId; //货主
	@Translation(name = "货批号id")
    private Integer productId;//货品
	@Translation(name = "开始时间")
    private Long startTime;
	@Translation(name = "截止时间")
    private Long endTime;
	@Translation(name = "费用对应货批信息")
    private List<OtherFeeCargo> otherFeeCargos;
	@Translation(name = "费用项信息")
    private FeeCharge feecharge;
	@Translation(name = "列数")
	private Integer indexTh;
	public OtherFeeDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public OtherFeeDto(Integer id, String code, Integer type,
			Integer createUserId, Long createTime, Long accountTime,
			String description, Integer status, Integer clientId,
			Integer productId, Long startTime, Long endTime) {
		super();
		this.id = id;
		this.code = code;
		this.type = type;
		this.createUserId = createUserId;
		this.createTime = createTime;
		this.accountTime = accountTime;
		this.description = description;
		this.status = status;
		this.clientId = clientId;
		this.productId = productId;
		this.startTime = startTime;
		this.endTime = endTime;
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


	public List<OtherFeeCargo> getOtherFeeCargos() {
		return otherFeeCargos;
	}


	public void setOtherFeeCargos(List<OtherFeeCargo> otherFeeCargos) {
		this.otherFeeCargos = otherFeeCargos;
	}


	public FeeCharge getFeecharge() {
		return feecharge;
	}


	public void setFeecharge(FeeCharge feecharge) {
		this.feecharge = feecharge;
	}


	public Integer getIndexTh() {
		return indexTh;
	}


	public void setIndexTh(Integer indexTh) {
		this.indexTh = indexTh;
	}
   	
}
