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
 * 储罐资料表
 * 
 * @author jiahy
 * 
 */
@Entity
@Table(name = "t_pcs_tank")
public class Tank {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;// 主键，储罐id，自增
	@Column(name = "code")
	@Translation(name = "代码")
	private String code;// 储罐代码
	@Column(name = "name")
	@Translation(name = "名称")
	private String name;// 储罐名称，暂不使用
	@Column(name = "type")
	@Translation(name = "类型")
	private Integer type;// 储罐类型，0：普通；1：免税
	@Translation(name = "泵棚")
	private Integer pumpShedId;// 所属泵棚
	@Column(name = "productId")
	@Translation(name = "货品")
	private Integer productId;// 装载货品id，关联货品资料表
	@Column(name = "capacityTotal")
	@Translation(name = "总容量")
	private String capacityTotal;// 总容量
	@Column(name = "capacityCurrent")
	@Translation(name = "当前容量")
	private String capacityCurrent;// 当前容量
	@Column(name = "capacityFree")
	@Translation(name = "空容")
	private String capacityFree;// 空余容量
	@Column(name = "testDensity")
	@Translation(name = "试验密度")
	private String testDensity;// 试验密度
	@Column(name = "testTemperature")
	@Translation(name = "试验温度")
	private String testTemperature;// 试验温度
	@Column(name = "tankTemperature")
	@Translation(name = "储罐温度")
	private String tankTemperature;// 储罐温度
	@Column(name = "description")
	@Translation(name = "描述")
	private String description;// 描述
	@Column(name = "status")
	@Translation(name = "状态")
	private Integer status;// 状态
	@Column(name = "editUserId")
	@Translation(name = "修改人")
	private Integer editUserId;// 修改人
	@Column(name = "editTime")
	@Translation(name = "修改时间")
	private Timestamp editTime;// 修改时间
	@Column(name = "key")
	@Translation(name = "值")
	private String key ;
	private String isVir;//是否是虚拟罐
	
	
	// Constructors


	/** default constructor */
	public Tank() {
	}

	// Property accessors

	public String getKey() {
		return key;
	}

	public String getIsVir() {
		return isVir;
	}

	public void setIsVir(String isVir) {
		this.isVir = isVir;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getId() {
		return this.id;
	}

	public Integer getEditUserId() {
		return editUserId;
	}

	public void setEditUserId(Integer editUserId) {
		this.editUserId = editUserId;
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

	public Integer getPumpShedId() {
		return pumpShedId;
	}

	public void setPumpShedId(Integer pumpShedId) {
		this.pumpShedId = pumpShedId;
	}

	public Integer getProductId() {
		return this.productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getCapacityTotal() {
		return this.capacityTotal;
	}

	public void setCapacityTotal(String capacityTotal) {
		this.capacityTotal = capacityTotal;
	}

	public String getCapacityCurrent() {
		return this.capacityCurrent;
	}

	public void setCapacityCurrent(String capacityCurrent) {
		this.capacityCurrent = capacityCurrent;
	}

	public String getCapacityFree() {
		return this.capacityFree;
	}

	public void setCapacityFree(String capacityFree) {
		this.capacityFree = capacityFree;
	}

	public String getTestDensity() {
		return this.testDensity;
	}

	public void setTestDensity(String testDensity) {
		this.testDensity = testDensity;
	}

	public String getTestTemperature() {
		return this.testTemperature;
	}

	public void setTestTemperature(String testTemperature) {
		this.testTemperature = testTemperature;
	}

	public String getTankTemperature() {
		return this.tankTemperature;
	}

	public void setTankTemperature(String tankTemperature) {
		this.tankTemperature = tankTemperature;
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

	public Timestamp getEditTime() {
		return this.editTime;
	}

	public void setEditTime(Timestamp editTime) {
		this.editTime = editTime;
	}

}