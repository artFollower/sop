package com.skycloud.oa.system.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 客户账户表
 * 
 * @author jiahy
 * 
 */
@Entity
@Table(name = "t_pcs_guest")
public class Guest {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;// 主键，客户账户id，自增
	@Column(name = "username")
	private String username;// 客户账户名
	@Column(name = "password")
	private String password;// 客户密码
	@Column(name = "editUserId")
	private Integer editUserId;// 修改人
	@Column(name = "editTime")
	private Timestamp editTime;// 修改时间

	// Constructors

	/** default constructor */
	public Guest() {
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

	/** full constructor */
	public Guest(String username, String password) {
		this.username = username;
		this.password = password;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}