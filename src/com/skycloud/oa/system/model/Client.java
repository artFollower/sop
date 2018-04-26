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
 * 客户资料表
 * 
 * @author jiahy
 * 
 */
@Entity
@Table(name = "t_pcs_client")
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;// 主键，客户id，自增
	@Column(name = "code")
	@Translation(name = "代码")
	private String code;// 客户代码
	@Column(name = "name")
	@Translation(name = "名称")
	private String name;// 客户公司名称
	@Column(name = "type")
	@Translation(name = "类型")
	private Integer type;// 客户类型，0：普通客户；1：大客户
	@Translation(name = "客户id")
	private Integer guestId;
	@Column(name = "email")
	@Translation(name = "邮箱")
	private String email;// 电子邮箱
	@Column(name = "fax")
	@Translation(name = "传真")
	private String fax;// 传真
	@Column(name = "phone")
	@Translation(name = "电话")
	private String phone;// 电话
	@Column(name = "address")
	@Translation(name = "地址")
	private String address;// 地址
	@Column(name = "postcode")
	@Translation(name = "邮编")
	private String postcode;// 邮编
	@Column(name = "contactName")
	@Translation(name = "联系人姓名")
	private String contactName;// 联系人姓名
	@Column(name = "contactSex")
	@Translation(name = "性别")
	private String contactSex;// 联系人性别，0：男；1：女
	@Column(name = "contactEmail")
	@Translation(name = "电子邮箱")
	private String contactEmail;// 联系人电子邮箱
	@Column(name = "contactPhone")
	@Translation(name = "联系人电话")
	private String contactPhone;// 联系人电话
	@Column(name = "bankAccount")
	@Translation(name = "银行账号")
	private String bankAccount;// 银行账号
	@Column(name = "bankName")
	@Translation(name = "开户行名称")
	private String bankName;// 开户行名称
	@Column(name = "description")
	@Translation(name = "描述")
	private String description;// 描述
	@Column(name = "status")
	@Translation(name = "状态")
	private Integer status;// 状态，预留
	@Column(name = "ladingSample")
	@Translation(name = "提单样本")
	private String ladingSample;// 提单样本
	@Translation(name = "客户组id")
	private Integer ClientGroupId;
	@Column(name = "editUserId")
	@Translation(name = "修改人")
	private String editUserId;// 修改人
	@Column(name = "editTime")
	@Translation(name = "修改时间")
	private Timestamp editTime;// 修改时间
	@Translation(name = "信誉")
	private Integer creditGrade;
	@Translation(name = "支付情况")
	private Integer paymentGrade;

	// Constructors

	/** default constructor */
	public Client() {
	}

	// Property accessors

	public Integer getGuestId() {
		return guestId;
	}

	public void setGuestId(Integer guestId) {
		this.guestId = guestId;
	}



	public Integer getClientGroupId() {
		return ClientGroupId;
	}

	public void setClientGroupId(Integer clientGroupId) {
		ClientGroupId = clientGroupId;
	}

	public Integer getCreditGrade() {
		return creditGrade;
	}

	public void setCreditGrade(Integer creditGrade) {
		this.creditGrade = creditGrade;
	}

	public Integer getPaymentGrade() {
		return paymentGrade;
	}

	public void setPaymentGrade(Integer paymentGrade) {
		this.paymentGrade = paymentGrade;
	}

	public Integer getId() {
		return this.id;
	}


	public String getEditUserId() {
		return editUserId;
	}

	public void setEditUserId(String editUserId) {
		this.editUserId = editUserId;
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

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactSex() {
		return this.contactSex;
	}

	public void setContactSex(String contactSex) {
		this.contactSex = contactSex;
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

	public String getBankAccount() {
		return this.bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getLadingSample() {
		return this.ladingSample;
	}

	public void setLadingSample(String ladingSample) {
		this.ladingSample = ladingSample;
	}

	public Timestamp getEditTime() {
		return this.editTime;
	}

	public void setEditTime(Timestamp editTime) {
		this.editTime = editTime;
	}

}