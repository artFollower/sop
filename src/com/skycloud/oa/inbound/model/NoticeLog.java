package com.skycloud.oa.inbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 * 通知单记录表
 * 
 * @author jiahy
 * 
 */
@Entity
@Table(name = "t_pcs_notice_log")
public class NoticeLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Translation(name = "通知单类型")
	private Integer type;// 1.接卸（码头）通知单记录，2.接卸（动力班）通知单记录3.倒灌通知单记录，4.配管通知单记录  5.管线清洗码头 6。管线清洗库区 7。管线清扫码头  8。管线清扫库区
	//9.清罐 10.转输 11。码头船发12动力班船发 20。靠泊方案  ，21.超期货批，超期提单结清记录
	@Translation(name = "内容")
	private String content;
	@Translation(name = "创建人id")
	private Integer createUserId;
	@Translation(name = "创建时间")
	private Integer createTime;

	// Constructors

	/** default constructor */
	public NoticeLog() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}
}