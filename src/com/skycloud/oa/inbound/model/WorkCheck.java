package com.skycloud.oa.inbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 * 作业检查表
 * @author jiahy
 *
 */
@Entity
@Table(name = "t_pcs_work_check")
public class WorkCheck {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
     private Integer id;
	@Translation(name = "类型")
     private Integer checkType;//1.管线检查-消防动力班2.管线检查-码头操作班 4.码头确认，5码头作业前，6码头作业中，7码头作业后，8配管确认，9配管前，10配管后，
//     11.动力班确认12动力班前，13动力班中，14动力班后,15打循环码头确认16.打循环码头前，17打循环码头中，18打循环码头后
//     19打循环库区确认20.打循环库区前，21打循环库区中，22打循环库区后
	@Translation(name = "检查人id")
     private Integer checkUserId;
	@Translation(name = "检查时间")
     private Long checkTime;
     private Integer transportId;
     @Translation(name = "检查内容")
     private String content;
     @Translation(name = "备注")
     private String description;//备注
     @Translation(name = "部门类型")
     private Integer type;//多个部门确认的0.普通，1.特殊
     private Integer flag;
	public WorkCheck() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCheckType() {
		return checkType;
	}
	public void setCheckType(Integer checkType) {
		this.checkType = checkType;
	}
	public Integer getCheckUserId() {
		return checkUserId;
	}
	public void setCheckUserId(Integer checkUserId) {
		this.checkUserId = checkUserId;
	}
	public Long getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Long checkTime) {
		this.checkTime = checkTime;
	}
	public Integer getTransportId() {
		return transportId;
	}
	public void setTransportId(Integer transportId) {
		this.transportId = transportId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
    
}