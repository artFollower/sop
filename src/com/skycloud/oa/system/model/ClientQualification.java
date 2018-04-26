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
 * 客户资质表
 * 
 * @author JIAHY
 * 
 */
@Entity
@Table(name = "t_pcs_client_qualification")
public class ClientQualification {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;// 主键，资质id，自增
	@Column(name = "clientId")
	@Translation(name = "客户id")
	private Integer clientId;// 客户id
	@Column(name = "type")
	@Translation(name = "资质类型")
	private Integer type;// 资质类型id
	@Column(name = "fileUrl")
	@Translation(name = "资质文件")
	private String fileUrl;// 资质文件url
	@Column(name = "editUserId")
	@Translation(name = "编辑人id")
	private Integer editUserId;// 编辑人用户id
	@Column(name = "editTime")
	@Translation(name = "修改时间")
	private Timestamp editTime;// 编辑时间
	@Translation(name = "状态")
	private Integer status;//1:删除
	// Constructors

	/** default constructor */
	public ClientQualification() {
	}

	// Property accessors

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClientId() {
		return this.clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getFileUrl() {
		return this.fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public Integer getEditUserId() {
		return editUserId;
	}

	public void setEditUserId(Integer editUserId) {
		this.editUserId = editUserId;
	}

	public Timestamp getEditTime() {
		return this.editTime;
	}

	public void setEditTime(Timestamp editTime) {
		this.editTime = editTime;
	}

}