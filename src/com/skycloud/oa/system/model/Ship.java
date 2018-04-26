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
 * 船舶资料表
 * 
 * @author jiahy
 * 
 */
@Entity
@Table(name = "t_pcs_ship")
public class Ship {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;// 主键，船舶id，自增
	@Column(name = "code")
	@Translation(name = "代码")
	private String code;// 船舶代码
	@Column(name = "name")
	@Translation(name = "名称")
	private String name;// 船舶名称（英文名）
	@Column(name = "type")
	@Translation(name = "类型")
	private Integer type;// 船舶类型，0：外轮；1：内轮
	@Column(name = "refCode")
	@Translation(name = "助记符")
	private String refCode;// 助记符
	@Column(name = "refName")
	@Translation(name = "参考名")
	private String refName;// 船舶参考名
	@Column(name = "shipRegistry")
	@Translation(name = "船籍")
	private String shipRegistry;// 船籍
	@Column(name = "shipLenth")
	@Translation(name = "船长")
	private String shipLenth;// 船长
	@Column(name = "shipWidth")
	@Translation(name = "船宽")
	private String shipWidth;// 船宽
	@Column(name = "shipDraught")
	@Translation(name = "吃水")
	private String shipDraught;// 吃水
	@Column(name = "buildYear")
	@Translation(name = "建造年份")
	private Integer buildYear;// 建造年份
	@Column(name = "loadCapacity")
	@Translation(name = "载重")
	private String loadCapacity;// 载重
	@Column(name = "grossTons")
	@Translation(name = "总吨")
	private String grossTons;// 总吨
	@Column(name = "netTons")
	@Translation(name = "净吨")
	private String netTons;// 净吨
	@Column(name = "notice")
	@Translation(name = "备注")
	private String notice;// 保安声明
	@Column(name = "owner")
	@Translation(name = "船主")
	private String owner;// 船主
	@Column(name = "manager")
	@Translation(name = "负责人")
	private String manager;// 负责人
	@Column(name = "contactName")
	@Translation(name = "联系人")
	private String contactName;// 联系人
	@Column(name = "contactPhone")
	@Translation(name = "联系电话")
	private String contactPhone;// 联系电话
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
	@Translation(name = "港序")
    private String port;//港序
	// Constructors

	/** default constructor */
	public Ship() {
	}

	// Property accessors

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

	public String getRefCode() {
		return this.refCode;
	}

	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

	public String getRefName() {
		return this.refName;
	}

	public void setRefName(String refName) {
		this.refName = refName;
	}

	public String getShipRegistry() {
		return this.shipRegistry;
	}

	public void setShipRegistry(String shipRegistry) {
		this.shipRegistry = shipRegistry;
	}

	public String getShipLenth() {
		return this.shipLenth;
	}

	public void setShipLenth(String shipLenth) {
		this.shipLenth = shipLenth;
	}

	public String getShipWidth() {
		return this.shipWidth;
	}

	public void setShipWidth(String shipWidth) {
		this.shipWidth = shipWidth;
	}

	public String getShipDraught() {
		return this.shipDraught;
	}

	public void setShipDraught(String shipDraught) {
		this.shipDraught = shipDraught;
	}

	public Integer getBuildYear() {
		return this.buildYear;
	}

	public void setBuildYear(Integer buildYear) {
		this.buildYear = buildYear;
	}

	public String getLoadCapacity() {
		return this.loadCapacity;
	}

	public void setLoadCapacity(String loadCapacity) {
		this.loadCapacity = loadCapacity;
	}

	public String getGrossTons() {
		return this.grossTons;
	}

	public void setGrossTons(String grossTons) {
		this.grossTons = grossTons;
	}

	public String getNetTons() {
		return this.netTons;
	}

	public void setNetTons(String netTons) {
		this.netTons = netTons;
	}

	public String getNotice() {
		return this.notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getManager() {
		return this.manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return this.contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
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

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

}