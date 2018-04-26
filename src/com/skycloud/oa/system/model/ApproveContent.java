package com.skycloud.oa.system.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 审批内容表
 * @author yanyufeng
 *
 */
@Entity
@Table(name = "t_pcs_approvecontent")
public class ApproveContent {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Integer userId;//审批人id
	private Integer type;  //类型   1：合同2：入库靠泊评估 5.账单， 6.码头规费账单
	private Integer workId;//该类型id
	private String content;//审批内容
	private Long reviewTime;//审批时间
	private Integer typeStatus;//审批状态  0:未审批  1：通过  2：退回
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getTypeStatus() {
		return typeStatus;
	}
	public void setTypeStatus(Integer typeStatus) {
		this.typeStatus = typeStatus;
	}
	public Long getReviewTime() {
		return reviewTime;
	}
	public void setReviewTime(Long reviewTime) {
		this.reviewTime = reviewTime;
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
	public Integer getWorkId() {
		return workId;
	}
	public void setWorkId(Integer workId) {
		this.workId = workId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
