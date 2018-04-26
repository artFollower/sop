package com.skycloud.oa.outbound.dto;

import java.util.List;

public class testDto {
	private int id;
private String name;
private String code;
private String url;
private String description;
private List<testDto> children;
private int level;

public int getLevel() {
	return level;
}
public void setLevel(int level) {
	this.level = level;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public List<testDto> getChildren() {
	return children;
}
public void setChildren(List<testDto> children) {
	this.children = children;
}

}
