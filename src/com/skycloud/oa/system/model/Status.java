package com.skycloud.oa.system.model;

/**
 * 状态定义表
 * 
 * @author jiahy
 * 
 */
public class Status {

	private Integer key;
	private String value;

	// Constructors

	/** default constructor */
	public Status() {
	}

	/** full constructor */
	public Status(String value) {
		this.value = value;
	}

	// Property accessors

	public Integer getKey() {
		return this.key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}