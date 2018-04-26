package com.skycloud.oa.system.dto;

public class ClientDto {
	private Integer id;//主键，客户id，自增
	private String code;//客户代码
	private String name;//客户公司名称
	private String contactName;//客户公司名称
	private String contactPhone;//客户公司名称
private String letter;
	
	public String getLetter() {
		return letter;
	}
	public void setLetter(String letter) {
		this.letter = letter;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	private Integer clientGroupId;//客户组id
	
	public Integer getClientGroupId() {
		return clientGroupId;
	}
	public void setClientGroupId(Integer clientGroupId) {
		this.clientGroupId = clientGroupId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
