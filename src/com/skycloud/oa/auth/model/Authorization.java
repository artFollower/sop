package com.skycloud.oa.auth.model ;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 用户授权实体
 * @ClassName: Authorization 
 * @Description: TODO
 * @author xie
 * @date 2014年12月25日 下午4:53:03
 */
@Entity
@Table(name="t_auth_authorization")
public class Authorization {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;//关联的用户
	
	@ManyToOne
	@JoinColumn(name="roleId")
	private Role role;//关联的角色

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	
}
