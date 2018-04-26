package com.skycloud.oa.common.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.skycloud.oa.annatation.Translation;

/**
 * 验证码
 * 
 * @ClassName: SecurityCode
 * @Description: TODO
 * @author xie
 * @date 2016年1月14日 下午5:51:06
 */
@Entity
@Table(name = "t_sys_security_code")
public class SecurityCode
{
	@Id
	@Translation(name = "验证对象")
	private String object;// 验证对象
	@Translation(name = "验证码")
	private String code;// 验证码
	@Translation(name = "验证类型")
	private String type;// 验证类型 0:手机,1:邮箱 2微信
	@Translation(name = "创建时间")
	private long createTime;// 创建时间
	@Translation(name = "失效时间")
	private long failTime;// 失效时间
	
	private String userId;//用户id
	
	@Transient
	@Translation(name = "类型")
	private String objectType;//类型

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getFailTime() {
		return failTime;
	}

	public void setFailTime(long failTime) {
		this.failTime = failTime;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


}
