package com.skycloud.oa.inbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 * 调度值班记录
 * 
 * @author jiahy
 *
 */
@Entity
@Table(name = "t_pcs_duty_sys")
public class DutySys {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Translation(name = "调度日志id")
	private Integer dispatchId;
	@Translation(name = "系统名称")
	private String sysName;
	@Translation(name = "检查情况")
	private String sysStatus;
	@Translation(name = "处理结果")
	private String result;
	@Translation(name = "类型")
	private Integer type;//1：系统检查2：系统参数检查
	
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
	public Integer getDispatchId() {
		return dispatchId;
	}
	public void setDispatchId(Integer dispatchId) {
		this.dispatchId = dispatchId;
	}
	public String getSysName() {
		return sysName;
	}
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	public String getSysStatus() {
		return sysStatus;
	}
	public void setSysStatus(String sysStatus) {
		this.sysStatus = sysStatus;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

	
}