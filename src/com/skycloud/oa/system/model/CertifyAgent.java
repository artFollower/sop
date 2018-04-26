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
 * 开证单位资料表
 * 
 * @author jiahy
 * 
 */
@Entity
@Table(name = "t_pcs_certify_agent")
public class CertifyAgent {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;// 主键，开证单位id，自增
	@Column(name = "code")
	@Translation(name = "代码")
	private String code;// 开证单位简称
	@Column(name = "name")
	@Translation(name = "名称")
	private String name;// 开证单位全称
	@Column(name = "contactName")
	@Translation(name = "联系人")
	private String contactName;// 联系人
	@Column(name = "contactEmail")
	@Translation(name = "联系人电子邮箱")
	private String contactEmail;// 联系人电子邮箱
	@Column(name = "contactPhone")
	@Translation(name = "联系人电话")
	private String contactPhone;// 联系人电话
	@Column(name = "editUserId")
	@Translation(name = "修改人")
	private Integer editUserId;// 修改人
	@Column(name = "editTime")
	@Translation(name = "修改时间")
	private Timestamp editTime;// 修改时间
	@Translation(name = "状态")
	private Integer status;//1:删除
	// Constructors

	/** default constructor */
	public CertifyAgent() {
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/** full constructor */
	public CertifyAgent(String code, String name, String contactName, String contactEmail, String contactPhone) {
		this.code = code;
		this.name = name;
		this.contactName = contactName;
		this.contactEmail = contactEmail;
		this.contactPhone = contactPhone;
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

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactEmail() {
		return this.contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactPhone() {
		return this.contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

}