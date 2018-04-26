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
 * 泊位表
 * 
 * @author jiahy
 * 
 */
@Entity
@Table(name = "t_pcs_berth")
public class Berth {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Translation(name = "名称")
	private String name;
	@Translation(name = "长度")
	private String limitLength;
	@Translation(name = "泊位长度")
	private String length;
	@Translation(name = "前沿水深")
	private String frontDepth;
	@Translation(name = "最小船长")
	private String minLength;
	@Translation(name = "吃水")
	private String limitDrought;
	@Translation(name = "载重吨")
	private String limitDisplacement;
	@Translation(name = "最大排水量")
	private String limitTonnage;
	@Translation(name = "备注")
	private String description;
	@Translation(name = "安全措施")
	private String safeInfo;
	@Translation(name = "修改人id")
	private Integer editUserId;// 修改人
	@Column(name = "editTime")
	@Translation(name = "修改时间")
	private Timestamp editTime;// 修改时间
	@Translation(name = "状态")
	private Integer status;//1:删除
	// Constructors

	/** default constructor */
	public Berth() {
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public Integer getStatus() {
		return status;
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
		return editTime;
	}

	public void setEditTime(Timestamp editTime) {
		this.editTime = editTime;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLimitLength() {
		return this.limitLength;
	}

	public void setLimitLength(String limitLength) {
		this.limitLength = limitLength;
	}

	public String getLimitDrought() {
		return this.limitDrought;
	}

	public void setLimitDrought(String limitDrought) {
		this.limitDrought = limitDrought;
	}

	public String getLimitDisplacement() {
		return this.limitDisplacement;
	}

	public void setLimitDisplacement(String limitDisplacement) {
		this.limitDisplacement = limitDisplacement;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String getSafeInfo() {
		return safeInfo;
	}

	public void setSafeInfo(String safeInfo) {
		this.safeInfo = safeInfo;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getFrontDepth() {
		return frontDepth;
	}

	public void setFrontDepth(String frontDepth) {
		this.frontDepth = frontDepth;
	}

	public String getMinLength() {
		return minLength;
	}

	public void setMinLength(String minLength) {
		this.minLength = minLength;
	}

	public String getLimitTonnage() {
		return limitTonnage;
	}

	public void setLimitTonnage(String limitTonnage) {
		this.limitTonnage = limitTonnage;
	}

	

}