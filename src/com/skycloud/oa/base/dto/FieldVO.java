package com.skycloud.oa.base.dto;
public class FieldVO {
	private String name;
	private boolean isInt;	private boolean isLong;	private boolean isDate;	private boolean isDouble;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isInt() {
		return isInt;
	}	public boolean isLong() {		return isLong;	}	public boolean isDate() {		return isDate;	}
	public void setInt(boolean isInt) {
		this.isInt = isInt;
	}	public void setLong(boolean isLong) {		this.isLong = isLong;	}	public void setDate(boolean isDate) {		this.isDate = isDate;	}	public boolean isDouble() {		return isDouble;	}	public void setDouble(boolean isDouble) {		this.isDouble = isDouble;	}
}