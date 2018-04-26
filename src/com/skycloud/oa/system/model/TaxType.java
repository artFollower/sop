package com.skycloud.oa.system.model;

/**
 * 税务类型
 * 
 * @author jiahy
 * 
 */
public class TaxType {

	private Integer key;
	private String value;

	// Constructors

	/** default constructor */
	public TaxType() {
	}

	/** full constructor */
	public TaxType(String value) {
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