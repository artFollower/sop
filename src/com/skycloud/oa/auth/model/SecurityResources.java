package com.skycloud.oa.auth.model ;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 安全资源实体(包含菜单，功能等)
 * @ClassName: SecurityResources 
 * @Description: TODO
 * @author xie
 * @date 2014年12月25日 下午4:33:13
 */
@Entity
@Table(name="t_auth_security_resources")
public class SecurityResources {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String category;//类别
	private String description;//描述
	private String name;//名称
	private String indentifier;//标识
	private String status;//状态
	
	@ManyToOne
	@JoinColumn(name="parentId")
	private SecurityResources parent;//父节点
	
	@Transient
	private String parentId;//父节点id
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getIndentifier() {
		return indentifier;
	}

	public void setIndentifier(String indentifier) {
		this.indentifier = indentifier;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public SecurityResources getParent()
	{
		return parent;
	}

	public void setParent(SecurityResources parent)
	{
		this.parent = parent;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
