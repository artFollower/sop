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
 * 货品资料表
 * 
 * @author jiahy
 * 
 */
@Entity
@Table(name = "t_pcs_product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;// 主键，货品id，自增
	@Column(name = "code")
	@Translation(name = "代码")
	private String code;// 货品代码
	@Column(name = "name")
	@Translation(name = "货品")
	private String name;// 货品名称
	@Column(name = "type")
	@Translation(name = "类型")
	private Integer type;// 货品类型，1：油品；2：化学品
	@Column(name = "oils")
	@Translation(name = "类型")
	private Integer oils;// 货品类型，1：油品；2：化学品
	@Column(name = "standardDensity")
	@Translation(name = "标准密度")
	private String standardDensity;// 标准密度
	@Column(name = "volumeRatio")
	@Translation(name = "体积比")
	private String volumeRatio;// 体积比
	@Column(name = "description")
	@Translation(name = "描述")
	private String description;// 描述
	@Column(name = "status")
	@Translation(name = "状态")
	private Integer status;// 状态，预留
	@Column(name = "editUserId")
	@Translation(name = "修改人")
	private Integer editUserId;// 修改人
	@Column(name = "editTime")
	@Translation(name = "修改时间")
	private Timestamp editTime;// 修改时间
	@Translation(name = "状态")
	private Integer sysStatus;
	/**
	 * 货品颜色
	 */
	@Column(name="fontColor")
	private String fontColor;

	// Constructors

	public Integer getSysStatus() {
		return sysStatus;
	}

	public void setSysStatus(Integer sysStatus) {
		this.sysStatus = sysStatus;
	}

	/** default constructor */
	public Product() {
	}

	// Property accessors

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
	public Integer getOils() {
		return oils;
	}

	public void setOils(Integer oils) {
		this.oils = oils;
	}

	public String getStandardDensity() {
		return this.standardDensity;
	}

	public void setStandardDensity(String standardDensity) {
		this.standardDensity = standardDensity;
	}

	public String getVolumeRatio() {
		return this.volumeRatio;
	}

	public void setVolumeRatio(String volumeRatio) {
		this.volumeRatio = volumeRatio;
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

	/**
	 * @get方法:String
	 * @return the fontColor
	 */
	public String getFontColor() {
		return fontColor;
	}

	/**
	 * @set方法:String
	 * @param fontColor the fontColor to set
	 */
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}
	
}