package com.skycloud.oa.system.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 * 车辆资料表
 * 
 * @author jiahy
 * 
 */
@Entity
@Table(name = "t_pcs_truck")
public class Truck {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;// 主键，车辆id，自增
	@Column(name = "code")
	@Translation(name = "代码")
	private String code;// 车辆代码，车牌号
	@Column(name = "name")
	@Translation(name = "名称")
	private String name;// 车辆名称
	@Column(name = "type")
	@Translation(name = "类型")
	private Integer type;// 车辆类型
	@Column(name = "loadCapacity")
	@Translation(name = "历史最大载重吨")
	private String loadCapacity;// 历史最大载重量，单位吨
	@Column(name = "company")
	@Translation(name = "所属单位")
	private String company;// 所属单位
	@Column(name = "description")
	@Translation(name = "描述")
	private String description;// 描述
	@Column(name = "status")
	@Translation(name = "状态")
	private Integer status;// 状态
	@Column(name = "editUserId")
	@Translation(name = "编辑人")
	private Integer editUserId;// 编辑人
	@Column(name = "editTime")
	@Translation(name = "修改时间")
	private Timestamp editTime;// 修改时间
	@Translation(name = "最大允许荷载吨")
    private String maxLoadCapacity;//最大允许荷载吨
	// Constructors

	/** default constructor */
	public Truck() {
	}

	public Truck(String code) {
		super();
		this.code = code;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getLoadCapacity() {
		return this.loadCapacity;
	}

	public void setLoadCapacity(String loadCapacity) {
		this.loadCapacity = loadCapacity;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getEditUserId() {
		return editUserId;
	}

	public void setEditUserId(Integer editUserId) {
		this.editUserId = editUserId;
	}

	public Timestamp getEditTime() {
		return this.editTime;
	}

	public void setEditTime(Timestamp editTime) {
		this.editTime = editTime;
	}

	public String getMaxLoadCapacity() {
		return maxLoadCapacity;
	}

	public void setMaxLoadCapacity(String maxLoadCapacity) {
		this.maxLoadCapacity = maxLoadCapacity;
	}

}