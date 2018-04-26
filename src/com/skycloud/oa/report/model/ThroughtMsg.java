/**
 * @Title:ThroughtMsg.java
 * @Package com.skycloud.oa.report.model
 * @Description TODO
 * @autor jiahy
 * @date 2016年12月20日下午1:45:22
 * @version V1.0
 */
package com.skycloud.oa.report.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 * @ClassName ThroughtMsg
 * @Description TODO
 * @author jiahy
 * @date 2016年12月20日下午1:45:22
 */
@Entity
@Table(name = "t_pcs_throught_msg")
public class ThroughtMsg {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Translation(name = "id")
	private Integer id;
	@Translation(name = "时间")
	private Long time;
	@Translation(name = "内容")
	private String content;
	
	public ThroughtMsg() {
		super();
	}

	public ThroughtMsg(Integer id, Long time, String content) {
		super();
		this.id = id;
		this.time = time;
		this.content = content;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
