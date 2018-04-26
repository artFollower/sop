package com.skycloud.oa.system.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 * 客户账户表
 * 
 * @author jiahy
 * 
 */
@Entity
@Table(name = "t_pcs_client_group")
public class ClientGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;// 主键，客户账户id，自增
	@Translation(name = "名称")
	private String name;// 客户账户名
	@Translation(name = "描述")
	private String description;// 客户密码
	@Translation(name = "修改人")
	private Integer editUserId;// 修改人
	@Translation(name = "修改时间")
	private Timestamp editTime;// 修改时间
	@Translation(name = "状态")
	private Integer status;//1:删除
	// Constructors

	/** default constructor */
	public ClientGroup() {
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

}