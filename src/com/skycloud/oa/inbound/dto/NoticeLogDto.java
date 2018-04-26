package com.skycloud.oa.inbound.dto;

public class NoticeLogDto {

private Integer type;//类型 1.接卸（码头）通知单记录，2.接卸（动力班）通知单记录3.打回流通知单记录，4.配管通知单记录
private String startTime;
private String endTime;
public Integer getType() {
	return type;
}
public void setType(Integer type) {
	this.type = type;
}
public String getStartTime() {
	return startTime;
}
public void setStartTime(String startTime) {
	this.startTime = startTime;
}
public String getEndTime() {
	return endTime;
}
public void setEndTime(String endTime) {
	this.endTime = endTime;
}

}
