package com.skycloud.oa.approve.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

@Entity
@Table(name = "t_pcs_approve_center")
public class ApproveCenter {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Translation(name = "创建人id")
	private int createUserId; //提交人id
	@Translation(name = "审核人id")
	private int reviewUserId;//审核人id
	@Translation(name = "内容")
	private String content;//内容
	@Translation(name = "请求的url")
	private String url;//请求URL
	@Translation(name = "状态")
	private int status;//状态  1：未审核，2：已通过 3：不通过 4：已失效。
	@Translation(name = "类型")
	private int part;//1：入港计划 2：入库确认 3：提单管理
	@Translation(name = "提交时间")
	private Long createTime;//提交时间
	@Translation(name = "审批时间")
	private Long reviewTime;//审批时间
	@Translation(name = "理由")
	private String reason;//理由
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}
	public int getReviewUserId() {
		return reviewUserId;
	}
	public void setReviewUserId(int reviewUserId) {
		this.reviewUserId = reviewUserId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getPart() {
		return part;
	}
	public void setPart(int part) {
		this.part = part;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getReviewTime() {
		return reviewTime;
	}
	public void setReviewTime(Long reviewTime) {
		this.reviewTime = reviewTime;
	}
	
	

}
