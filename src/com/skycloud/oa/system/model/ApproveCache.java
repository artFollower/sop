package com.skycloud.oa.system.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 审批缓存表（默认选择上一次的）
 * @author yanyufeng
 *
 */
@Entity
@Table(name = "t_pcs_approvecache")
public class ApproveCache {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Integer userId;//用户id
	private String cacheUser;//上一次选择的用户id
	private Integer type;//类型  1：提交2：抄送
	private Integer workType;//工作流类型 1：合同 2：靠泊3.仓储费首期费4仓储费超期费
	
	public Integer getWorkType() {
		return workType;
	}
	public void setWorkType(Integer workType) {
		this.workType = workType;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
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
	public String getCacheUser() {
		return cacheUser;
	}
	public void setCacheUser(String cacheUser) {
		this.cacheUser = cacheUser;
	}
	
	
}
