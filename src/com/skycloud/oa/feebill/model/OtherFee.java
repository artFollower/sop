package com.skycloud.oa.feebill.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;
import com.skycloud.oa.feebill.dto.OtherFeeDto;

/**
 *首期费
 * @author 作者:jiahy
 * @version 时间：2015年5月6日 下午5:09:39
 */
@Entity
@Table(name = "t_pcs_other_fee")
public class OtherFee {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;//首期费id
	@Translation(name = "结算单编号")
	private String code;//结算单编号
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
	private Integer status;//状态：未提交0、已提交1、已审核2、已生成账单3、已开票4、已完成5
	@Translation(name = "货主id")
    private Integer clientId; //货主
	@Translation(name = "货品id")
    private Integer productId;//货品
	public OtherFee() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OtherFee(Integer id, String code, Integer type,
			Integer createUserId, Long createTime, Long accountTime,
			String description, Integer status, Integer clientId,
			Integer productId) {
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
	}
	public OtherFee(OtherFeeDto otherFeeDto) {
		this.id = otherFeeDto.getId();
		this.code = otherFeeDto.getCode();
		this.type = otherFeeDto.getType();
		this.createUserId = otherFeeDto.getCreateUserId();
		this.createTime = otherFeeDto.getCreateTime();
		this.accountTime = otherFeeDto.getAccountTime();
		this.description = otherFeeDto.getDescription();
		this.status = otherFeeDto.getStatus();
		this.clientId = otherFeeDto.getClientId();
		this.productId = otherFeeDto.getProductId();
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
	
}
