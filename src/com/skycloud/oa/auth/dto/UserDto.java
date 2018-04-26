package com.skycloud.oa.auth.dto;

import com.skycloud.oa.annatation.Translation;

public class UserDto {
	
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
	@Translation(name = "角色id")
	private int roleId;//角色id
	@Translation(name = "部门id")
	private int organizationId;//部门id
	private String ids;//多个用户
	@Translation(name = "权限")
	private String permission;
	
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
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
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	
}
