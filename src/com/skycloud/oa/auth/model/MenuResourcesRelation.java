package com.skycloud.oa.auth.model ;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 菜单资源关联关系实体
 * @ClassName: MenuResourcesRelation 
 * @Description: TODO
 * @author xie
 * @date 2014年12月25日 下午4:30:07
 */
@Entity
@Table(name="t_auth_menu_resources_relation")
public class MenuResourcesRelation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int level;//级别
	private String menuIcon;//图标
	private int position;//位置,排序
	
	@ManyToOne
	@JoinColumn(name="parentId")
	private SecurityResources parent;//父节点
	
	@ManyToOne
	@JoinColumn(name="childId")
	private SecurityResources child;//节点

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public SecurityResources getParent() {
		return parent;
	}

	public void setParent(SecurityResources parent) {
		this.parent = parent;
	}

	public SecurityResources getChild() {
		return child;
	}

	public void setChild(SecurityResources child) {
		this.child = child;
	}

}
