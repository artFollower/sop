package com.skycloud.oa.auth.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 * 用户实体
 * @ClassName: Actor 
 * @Description: TODO
 * @author xie
 * @date 2014年12月25日 下午3:44:38
 */
@Entity
@Table(name="t_auth_user")
//@DynamicUpdate(true)
public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Translation(name = "账号")
	private String account;//账号
	@Translation(name = "别名")
	private String name;//别名
	@Translation(name = "密码")
	private String password;//密码
	@Translation(name = "邮箱")
	private String email;//邮箱
	@Translation(name = "手机号码")
	private String phone;//手机号码
	@Translation(name = "备注")
	private String description;//备注
	@Translation(name = "创建日期")
	private long createDate;//创建日期
	@Translation(name = "状态")
	private int status;//状态
	@Translation(name = "头像")
	private String photo;//头像
	@Translation(name = "类别")
	private String category;//类别
	@Translation(name = "加密盐值")
	private String salt;//加密盐值
	@Translation(name = "代表客户id")
	private String clientId;//关联的客户id
	@Translation(name = "客户组id")
	private String clientGroupId;//客户组id
	@Translation(name = "岗位id")
	private String jobId;//岗位id
	
	private String sessionTime;
	private String openid;//微信openid
	
	@ManyToOne
	@JoinColumn(name="createOwen")
	private User creater;//创建者
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public User getCreater() {
		return creater;
	}

	public void setCreater(User creater) {
		this.creater = creater;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientGroupId() {
		return clientGroupId;
	}

	public void setClientGroupId(String clientGroupId) {
		this.clientGroupId = clientGroupId;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getSessionTime() {
		return sessionTime;
	}

	public void setSessionTime(String sessionTime) {
		this.sessionTime = sessionTime;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
}
