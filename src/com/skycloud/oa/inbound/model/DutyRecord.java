package com.skycloud.oa.inbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 * 调度值班记录
 * 
 * @author jiahy
 *
 */
@Entity
@Table(name = "t_pcs_duty_record")
public class DutyRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Translation(name = "调度日志id")
	private Integer dispatchId;
	@Translation(name = "时间")
	private String time;
	@Translation(name = "天气")
	private Integer weather;
	@Translation(name = "内容")
	private String content;
	@Translation(name = "创建人")
	private Integer createUserId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDispatchId() {
		return dispatchId;
	}
	public void setDispatchId(Integer dispatchId) {
		this.dispatchId = dispatchId;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	public Integer getWeather() {
		return weather;
	}
	public void setWeather(Integer weather) {
		this.weather = weather;
	}
	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	
}