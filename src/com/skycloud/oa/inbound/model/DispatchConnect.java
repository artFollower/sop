package com.skycloud.oa.inbound.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;


/**
 * 调度日志基本信息关联表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_pcs_dispatch_connect")
public class DispatchConnect {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Translation(name = "调度日志基本信息id")
	private Integer dispatchId;  //基本信息id
	@Translation(name = "类型")
	private Integer type;//11:调度前 12:调度后   21：发货前 22：发货后   31：日班前  32：日班后  41：码头前 42：码头后   51：动力前   52：动力后 
	
	private Integer userId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getDispatchId() {
		return dispatchId;
	}

	public void setDispatchId(Integer dispatchId) {
		this.dispatchId = dispatchId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}