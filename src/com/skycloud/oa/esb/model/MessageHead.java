package com.skycloud.oa.esb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 报文头
 * @ClassName: MessageHead 
 * @Description: TODO
 * @author xie
 * @date 2015年1月22日 下午9:35:21
 */
@Entity
@Table(name="t_esb_head")
public class MessageHead {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String recordId = "00";//记录类型标识
	private String messageType = "COARRI";//报文类型
	private String fileDescription;//文件说明
	private String fileType;//文件类型
	private String sendCode;//发送方代码
	private String receiverCode = "XXZX111122";//接收方代码
	private String sendPortCode;//发送港代码
	private String receiverPortCode;//接受港代码
	private String fileCreateTime;//文件建立时间
	
	@ManyToOne
	@JoinColumn(name="portId")
	private HarborShip ship;
	
	public String toString() {
		return "00:"+messageType+":"+fileDescription+":9:CJSH111205:XXZX111122:"+System.currentTimeMillis()/1000+":"+sendPortCode+":"+receiverPortCode+"'";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getFileDescription() {
		return fileDescription;
	}

	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getSendCode() {
		return sendCode;
	}

	public void setSendCode(String sendCode) {
		this.sendCode = sendCode;
	}

	public String getReceiverCode() {
		return receiverCode;
	}

	public void setReceiverCode(String receiverCode) {
		this.receiverCode = receiverCode;
	}

	public String getSendPortCode() {
		return sendPortCode;
	}

	public void setSendPortCode(String sendPortCode) {
		this.sendPortCode = sendPortCode;
	}

	public String getReceiverPortCode() {
		return receiverPortCode;
	}

	public void setReceiverPortCode(String receiverPortCode) {
		this.receiverPortCode = receiverPortCode;
	}

	public String getFileCreateTime() {
		return fileCreateTime;
	}

	public void setFileCreateTime(String fileCreateTime) {
		this.fileCreateTime = fileCreateTime;
	}

	public HarborShip getShip() {
		return ship;
	}

	public void setShip(HarborShip ship) {
		this.ship = ship;
	}
	
}
