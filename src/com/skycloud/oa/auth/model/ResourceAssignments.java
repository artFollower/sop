package com.skycloud.oa.auth.model ;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * 角色分配的权限实体
 * @ClassName: ResourceAssignments 
 * @Description: TODO
 * @author xie
 * @date 2014年12月25日 下午4:34:46
 */
@Entity
@Table(name="t_auth_resource_assignments")
public class ResourceAssignments {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="roleId")
	@Cascade(value={CascadeType.DELETE})
	private Role role;//角色
	
	@ManyToOne
	@JoinColumn(name="sourceId")
	private SecurityResources securityResources;//资源

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public SecurityResources getSecurityResources() {
		return securityResources;
	}

	public void setSecurityResources(SecurityResources securityResources) {
		this.securityResources = securityResources;
	}

}
