package com.skycloud.oa.inbound.dto;

import com.skycloud.oa.annatation.Translation;

public class NotifyDto {
private Integer id;//
@Translation(name = "通知单类型")
private String types;//类型
@Translation(name = "通知单编号")
private String code;//编号
@Translation(name = "是否获取数量")
private Integer isCodeNum;//是否获取数量
@Translation(name = "编号")
private String codeF;//编号搜索使用

@Translation(name = "关键字")
private String keyWord;//编号搜索使用

@Translation(name = "编号组")
private String codes;//编号组（删除使用）
@Translation(name = "开始时间")
private Long startTime;//开始时间
@Translation(name = "截止时间")
private Long endTime;//截止时间
@Translation(name = "创建时间")
private Long createTime;//创建时间（计算code）
@Translation(name = "是否获取列表")
private Integer isList;//是否是获取列表
@Translation(name = "接卸方案id")
private Integer transportId;
@Translation(name = "列表列号")
private Integer indexTh;
@Translation(name = "作业要求")
private String taskRequire;
@Translation(name = "作业内容")
private String taskMsg;
public NotifyDto() {
	super();
}
public NotifyDto(Integer id, String types, String code, Integer isCodeNum,
		String codeF, String codes, Long startTime, Long endTime,
		Long createTime, Integer isList, Integer transportId) {
	super();
	this.id = id;
	this.types = types;
	this.code = code;
	this.isCodeNum = isCodeNum;
	this.codeF = codeF;
	this.keyWord=keyWord;
	this.codes = codes;
	this.startTime = startTime;
	this.endTime = endTime;
	this.createTime = createTime;
	this.isList = isList;
	this.transportId = transportId;
}
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getTypes() {
	return types;
}
public void setTypes(String types) {
	this.types = types;
}
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}
public Integer getIsCodeNum() {
	return isCodeNum;
}
public void setIsCodeNum(Integer isCodeNum) {
	this.isCodeNum = isCodeNum;
}
public String getCodeF() {
	return codeF;
}
public void setCodeF(String codeF) {
	this.codeF = codeF;
}
public String getCodes() {
	return codes;
}
public void setCodes(String codes) {
	this.codes = codes;
}
public Long getStartTime() {
	return startTime;
}
public void setStartTime(Long startTime) {
	this.startTime = startTime;
}
public Long getEndTime() {
	return endTime;
}
public void setEndTime(Long endTime) {
	this.endTime = endTime;
}
public Long getCreateTime() {
	return createTime;
}
public void setCreateTime(Long createTime) {
	this.createTime = createTime;
}
public Integer getIsList() {
	return isList;
}
public void setIsList(Integer isList) {
	this.isList = isList;
}
public Integer getTransportId() {
	return transportId;
}
public void setTransportId(Integer transportId) {
	this.transportId = transportId;
}
public Integer getIndexTh() {
	return indexTh;
}
public void setIndexTh(Integer indexTh) {
	this.indexTh = indexTh;
}

public String getKeyWord() {
	return keyWord;
}
public void setKeyWord(String keyWord) {
	this.keyWord = keyWord;
}
public String getTaskRequire() {
	return taskRequire;
}
public void setTaskRequire(String taskRequire) {
	this.taskRequire = taskRequire;
}
public String getTaskMsg() {
	return taskMsg;
}
public void setTaskMsg(String taskMsg) {
	this.taskMsg = taskMsg;
}


}
