package com.skycloud.oa.system.dto;

public class ShipDto {
	private Integer id;//主键，船舶id，自增
	private String code;//船舶代码
	private String name;//船舶名称（英文名）
	private String refCode;//船舶名称（英文名）
	private String owner;//船舶名称（英文名）
	private String contactName;//船舶名称（英文名）
	private String manager;//船舶名称（英文名）
	private String refName;//船舶名称（英文名）
	private String letter; //查询首字母
	
	public String getLetter() {
		return letter;
	}
	public void setLetter(String letter) {
		this.letter = letter;
	}
	public String getRefCode() {
		return refCode;
	}
	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getRefName() {
		return refName;
	}
	public void setRefName(String refName) {
		this.refName = refName;
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
