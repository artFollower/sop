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
 * 资质类型表
 * 
 * @author jiahy
 * 
 */
@Entity
@Table(name = "t_pcs_qualification")
public class Qualification {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;// 主键，资质类型id，自增
	@Column(name = "name")
	@Translation(name = "名称")
	private String name;// 资质名称
	@Translation(name = "描述")
	@Column(name = "description")
	private String description;// 描述
	@Column(name = "editUserId")
	@Translation(name = "修改人")
	private Integer editUserId;// 修改人
	@Column(name = "editTime")
	@Translation(name = "修改时间")
	private Timestamp editTime;// 修改时间
	@Translation(name = "状态")
	private Integer status;//1:删除
	
	// Constructors

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/** default constructor */
	public Qualification() {
	}

	// Property accessors

	public Integer getId() {
		return this.id;
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

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}