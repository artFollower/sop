/**
 * @Title:PumpShed.java
 * @Package com.skycloud.oa.system.model
 * @Description TODO
 * @autor jiahy
 * @date 2016年9月2日下午1:19:49
 * @version V1.0
 */
package com.skycloud.oa.system.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**泵棚
 * @ClassName PumpShed
 * @Description TODO
 * @author jiahy
 * @date 2016年9月2日下午1:19:49
 */
@Entity
@Table(name = "t_pcs_pump_shed")
public class PumpShed {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	@Translation(name="名称")
	private String name;
	@Translation(name="类型")
	private Integer type;
	@Translation(name="描述")
	private String description;
	@Translation(name="编辑人")
	private Integer editUserId;
	@Translation(name="编辑时间")
	private Long editTime;
	@Translation(name="状态")
	private Integer status;
	
	public PumpShed() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PumpShed(Integer id, String name, Integer type, String description, Integer editUserId, Long editTime,
			Integer status) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.description = description;
		this.editUserId = editUserId;
		this.editTime = editTime;
		this.status = status;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getEditUserId() {
		return editUserId;
	}
	public void setEditUserId(Integer editUserId) {
		this.editUserId = editUserId;
	}
	public Long getEditTime() {
		return editTime;
	}
	public void setEditTime(Long editTime) {
		this.editTime = editTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
