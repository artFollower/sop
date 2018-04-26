package com.skycloud.oa.message.dto;

public class MessageDto {

	private Integer roleId;//接受者角色id
	private Integer sendUserId;//发送者id
	private Integer getUserId;//接受者id
	private String content;//内容
	private String url;//跳转url
	private String type;//消息类型
	private Integer taskId;//任务id
	private Integer resourceId;//资源id
	
	private Integer status;
	private Long createTime;
	
	
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getGetUserId() {
		return getUserId;
	}
	public void setGetUserId(Integer getUserId) {
		this.getUserId = getUserId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getSendUserId() {
		return sendUserId;
	}
	public void setSendUserId(Integer sendUserId) {
		this.sendUserId = sendUserId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	
	
	
	
}
