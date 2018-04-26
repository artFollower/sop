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
 * 车位
 * 
 * @author jiahy
 * 
 */
@Entity
@Table(name = "t_pcs_park")
public class Park {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	@Translation(name = "名称")
	private String name;
	@Translation(name = "修改人")
	private int editUserId ;
	@Translation(name = "货品")
	private Integer productId;
	@Translation(name = "描述")
	private String description;
	@Translation(name = "修改时间")
	private Timestamp editTime;
	@Translation(name = "状态")
	private Integer status;//1:删除
	@Translation(name = "泵id")
	private Integer pumpId;
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getEditTime() {
		return editTime;
	}

	public void setEditTime(Timestamp editTime) {
		this.editTime = editTime;
	}

	public int getEditUserId() {
		return editUserId;
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

	public void setEditUserId(int editUserId) {
		this.editUserId = editUserId;
	}

	public Integer getId() {
		return id;
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

	public Integer getPumpId() {
		return pumpId;
	}

	public void setPumpId(Integer pumpId) {
		this.pumpId = pumpId;
	}
	
	
}