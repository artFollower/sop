package com.skycloud.oa.message.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_pcs_message_get")
public class MessageGet {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;// id
	private Integer messageId;// 消息id
	private Integer getUserId;// 接收者id
	private Integer status;// 状态 1：未读 2：已读

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public Integer getGetUserId() {
		return getUserId;
	}

	public void setGetUserId(Integer getUserId) {
		this.getUserId = getUserId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
