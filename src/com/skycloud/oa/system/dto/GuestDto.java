package com.skycloud.oa.system.dto;

public class GuestDto {
	private Integer id;//主键，客户账户id，自增
	private String username;//客户账户名
	private String password;//客户密码
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
