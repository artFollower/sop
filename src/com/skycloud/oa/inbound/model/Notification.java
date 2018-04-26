package com.skycloud.oa.inbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author jiahy
 *
 */
@Entity
@Table(name = "t_pcs_work_notification")
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	 private Integer id;
    
     private String notificationNum;
     private String notification;
     private String notificationUserId;
     private Long notificationTime;
     private String notificationUserName;
     private Integer workId;
     
     
     
	public String getNotificationUserName() {
		return notificationUserName;
	}
	public void setNotificationUserName(String notificationUserName) {
		this.notificationUserName = notificationUserName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNotificationNum() {
		return notificationNum;
	}
	public void setNotificationNum(String notificationNum) {
		this.notificationNum = notificationNum;
	}
	public String getNotification() {
		return notification;
	}
	public void setNotification(String notification) {
		this.notification = notification;
	}
	public String getNotificationUserId() {
		return notificationUserId;
	}
	public void setNotificationUserId(String notificationUserId) {
		this.notificationUserId = notificationUserId;
	}
	public Long getNotificationTime() {
		return notificationTime;
	}
	public void setNotificationTime(Long notificationTime) {
		this.notificationTime = notificationTime;
	}
	public Integer getWorkId() {
		return workId;
	}
	public void setWorkId(Integer workId) {
		this.workId = workId;
	}


}