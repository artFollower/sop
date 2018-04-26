package com.skycloud.oa.outbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_pcs_everyday_statics")
public class EveryDayStatics {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int type;		//1货体汇总 2货批汇总
	private String url; //文件地址
	private String time;//时间
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
