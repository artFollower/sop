package com.skycloud.oa.inbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 * 岗位检查表
 * 
 * @author jiahy
 * 
 */
@Entity
@Table(name = "t_pcs_job_check")
public class JobCheck {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Translation(name = "岗位类型")
	private Integer job;
	@Translation(name = "结果")
	private String result;
	@Translation(name = "检查内容")
	private String solve;
	@Translation(name = "创建时间")
	private Long createTime;
	@Translation(name = "创建人id")
	private Integer createUserId;
	private Integer transportId;

	public JobCheck(Integer id, Integer job, String result, String solve, Long createTime, Integer createUserId, Integer transportId) {
		super();
		this.id = id;
		this.job = job;
		this.result = result;
		this.solve = solve;
		this.createTime = createTime;
		this.createUserId = createUserId;
	}

	public JobCheck() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getJob() {
		return job;
	}

	public void setJob(Integer job) {
		this.job = job;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSolve() {
		return solve;
	}

	public void setSolve(String solve) {
		this.solve = solve;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getTransportId() {
		return transportId;
	}

	public void setTransportId(Integer transportId) {
		this.transportId = transportId;
	}

}