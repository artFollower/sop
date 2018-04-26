package com.skycloud.oa.outbound.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

@Entity
@Table(name = "t_pcs_approve")
public class Approve {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int status;		//0 保存 1提交 2审批通过
	@Translation(name = "检查人id")
	private int checkUserId;//检查人id
	@Translation(name = "备注")
	private String comment;//备注
	@Translation(name = "创建时间")
	private Date createTime; //创建时间
	@Translation(name = "类型")
	private int modelId;	//引用的车发船发模块id  1发货开票  2车发作业 3 车发数量确认 4 船发数量确认
	private int refId;		//引用的车发船发id
	@Translation(name = "审核人id")
	private int reviewUserId;//复核人id
	@Translation(name = "审核时间")
	private long reviewTime;//复核时间

	public int getReviewUserId() {
		return reviewUserId;
	}

	public void setReviewUserId(int reviewUserId) {
		this.reviewUserId = reviewUserId;
	}

	public long getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(long reviewTime) {
		this.reviewTime = reviewTime;
	}

	public int getRefId() {
		return refId;
	}

	public void setRefId(int refId) {
		this.refId = refId;
	}

	public int getModelId() {
		return modelId;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCheckUserId() {
		return checkUserId;
	}

	public void setCheckUserId(int checkUserId) {
		this.checkUserId = checkUserId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
