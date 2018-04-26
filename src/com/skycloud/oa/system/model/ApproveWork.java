package com.skycloud.oa.system.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 * 审批表
 * @author yanyufeng
 *
 */
@Entity
@Table(name = "t_pcs_approvework")
public class ApproveWork {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Translation(name = "用户id")
	private Integer userId;//用户id
	@Translation(name = "审批类型")
	private Integer type;//审批类型   1：合同
	@Translation(name = "状态")
	private Integer typeStatus;//对应类型状态
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getTypeStatus() {
		return typeStatus;
	}
	public void setTypeStatus(Integer typeStatus) {
		this.typeStatus = typeStatus;
	}
	
	
	
	
}
