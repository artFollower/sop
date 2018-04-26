package com.skycloud.oa.system.dto;

public class ProductDto {
	private Integer id;//主键，货品id，自增
	private String code;//货品代码
	private String name;//货品名称
	private String value;//货品名称
	private Integer oils;
private String letter;
	
	public String getLetter() {
		return letter;
	}
	public void setLetter(String letter) {
		this.letter = letter;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
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
	public Integer getOils() {
		return oils;
	}
	public void setOils(Integer oils) {
		this.oils = oils;
	}
	
}
