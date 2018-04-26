package com.skycloud.oa.system.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 * 储罐记录表
 * 
 * @author jiahy
 * 
 */
@Entity
@Table(name = "t_pcs_tank_clean_log")
public class TankCleanLog {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;// 主键，储罐id，自增
	@Translation(name = "储罐id")
	private Integer tankId;// 储罐Id
	@Translation(name = "货品")
	private Integer productId;// 装载货品id，关联货品资料表
	@Translation(name = "描述")
	private String description;// 描述
	@Translation(name = "修改人")
	private Integer editUserId;// 修改人
	@Translation(name = "修改时间")
	private Long editTime;// 修改时间
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTankId() {
		return tankId;
	}
	public void setTankId(Integer tankId) {
		this.tankId = tankId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
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
	


}