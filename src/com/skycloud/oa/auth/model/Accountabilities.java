package com.skycloud.oa.auth.model ;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 组织架构实体(组织关联关系)
 * @ClassName: Accountabilities 
 * @Description: TODO
 * @author xie
 * @date 2014年12月25日 下午4:56:59
 */
@Entity
@Table(name="t_auth_accountabilities")
public class Accountabilities {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int category;//类别
	private long fromDate;//起始时间
	private long endDate;//截至时间
	private int isParincipal;//核心
	
	@ManyToOne
	@JoinColumn(name="parentId")
	private Parties parent;
	
	@ManyToOne
	@JoinColumn(name="childId")
	private Parties child;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public long getFromDate() {
		return fromDate;
	}

	public void setFromDate(long fromDate) {
		this.fromDate = fromDate;
	}

	public long getEndDate() {
		return endDate;
	}

	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}

	public int getIsParincipal() {
		return isParincipal;
	}

	public void setIsParincipal(int isParincipal) {
		this.isParincipal = isParincipal;
	}

	public Parties getParent() {
		return parent;
	}

	public void setParent(Parties parent) {
		this.parent = parent;
	}

	public Parties getChild() {
		return child;
	}

	public void setChild(Parties child) {
		this.child = child;
	}
	
}
