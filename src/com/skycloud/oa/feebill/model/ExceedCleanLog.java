/**
 * @Title:ExceedCleanLog.java
 * @Package com.skycloud.oa.feebill.model
 * @Description TODO
 * @autor jiahy
 * @date 2017年3月25日上午9:07:15
 * @version V1.0
 */
package com.skycloud.oa.feebill.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 超期费结清日志
 * @ClassName ExceedCleanLog
 * @Description TODO
 * @author jiahy
 * @date 2017年3月25日上午9:07:18
 */
@Entity
@Table(name = "t_pcs_exceed_clean_log")
public class ExceedCleanLog {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int cargoId;
	private int ladingId;
	private String content;
	private int createUserId;
	private long createTime;
	public ExceedCleanLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ExceedCleanLog(int id, int cargoId, int ladingId, String content,
			int createUserId, long createTime) {
		super();
		this.id = id;
		this.cargoId = cargoId;
		this.ladingId = ladingId;
		this.content = content;
		this.createUserId = createUserId;
		this.createTime = createTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCargoId() {
		return cargoId;
	}
	public void setCargoId(int cargoId) {
		this.cargoId = cargoId;
	}
	public int getLadingId() {
		return ladingId;
	}
	public void setLadingId(int ladingId) {
		this.ladingId = ladingId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	
	
}
