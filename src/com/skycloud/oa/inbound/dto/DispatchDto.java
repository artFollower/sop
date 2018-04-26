package com.skycloud.oa.inbound.dto;

import com.skycloud.oa.annatation.Translation;

/**
 *调度日志基础资料表
 * @author 作者:jiahy
 * @version 时间：2015年3月19日 下午5:11:35
 */
public class DispatchDto {
	@Translation(name = "调度日志id")
private Integer dispatchId;
	@Translation(name = "开始时间")
private Long  startTime;
	@Translation(name = "结束时间")
private Long  endTime;



public Integer getDispatchId() {
	return dispatchId;
}
public void setDispatchId(Integer dispatchId) {
	this.dispatchId = dispatchId;
}
public DispatchDto() {
	super();
	// TODO Auto-generated constructor stub
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

}
