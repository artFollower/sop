package com.skycloud.oa.auth.model ;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.skycloud.oa.system.model.Client;
import com.skycloud.oa.system.model.ClientGroup;

/**
 * 缔约实体(包含公司、部门、员工)
 * @ClassName: Parties 
 * @Description: TODO
 * @author xie
 * @date 2014年12月25日 下午4:37:27
 */
@Entity
@Table(name="t_auth_parties")
public class Parties {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String category;//类别
	private long createDate;//创建日期
	private String name;//名称
	private String sn;//编码
	private String description;//描述
	private int orgPrincipal;//核心
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;//关联的用户
	
	@ManyToOne
	@JoinColumn(name="parentId")
	private Parties parties;
	
	@ManyToOne
	@JoinColumn(name="clientId")
	private Client client;//关联客户
	
	@ManyToOne
	@JoinColumn(name="clientGroupId")
	private ClientGroup clientGroup;//关联客户组
	
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

	public long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getOrgPrincipal() {
		return orgPrincipal;
	}

	public void setOrgPrincipal(int orgPrincipal) {
		this.orgPrincipal = orgPrincipal;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Parties getParties() {
		return parties;
	}

	public void setParties(Parties parties) {
		this.parties = parties;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public ClientGroup getClientGroup() {
		return clientGroup;
	}

	public void setClientGroup(ClientGroup clientGroup) {
		this.clientGroup = clientGroup;
	}

	public String getSn()
	{
		return sn;
	}

	public void setSn(String sn)
	{
		this.sn = sn;
	}
	
	
	
}
