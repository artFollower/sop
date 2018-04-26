package com.skycloud.oa.system.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 *泵
 * 
 * @author jiahy
 * 
 */
@Entity
@Table(name = "t_pcs_pump")
public class Pump {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	@Translation(name = "名称")
	private String name;
	@Translation(name = "货品")
	private Integer productId;
	@Translation(name = "状态")
	private String status;//状态0未使用，1.使用中
	@Translation(name = "清洗记录")
	private String note;//清洗记录
	@Translation(name = "修改人")
	private Integer editUserId;// 修改人
	@Translation(name = "修改时间")
	private Timestamp editTime;// 修改时间
	@Translation(name = "描述")
	private String description;
	@Translation(name = "类型")
	private Integer type;//1，船发泵，2，车发泵
	@Translation(name = "状态")
	private Integer sysStatus;//1:删除
	@Translation(name = "泵棚id")
	private Integer pumpShedId;//泵棚
	public Integer getSysStatus() {
		return sysStatus;
	}
	public void setSysStatus(Integer sysStatus) {
		this.sysStatus = sysStatus;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Integer getEditUserId() {
		return editUserId;
	}
	public void setEditUserId(Integer editUserId) {
		this.editUserId = editUserId;
	}
	public Timestamp getEditTime() {
		return editTime;
	}
	public void setEditTime(Timestamp editTime) {
		this.editTime = editTime;
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
	public Integer getPumpShedId() {
		return pumpShedId;
	}
	public void setPumpShedId(Integer pumpShedId) {
		this.pumpShedId = pumpShedId;
	}
	
}