/**
 * @Title:SecurityCodeDto.java
 * @Package com.skycloud.oa.common.dto
 * @Description TODO
 * @autor jiahy
 * @date 2016年5月19日上午8:24:06
 * @version V1.0
 */
package com.skycloud.oa.common.dto;

import com.skycloud.oa.annatation.Translation;

/**
 * @ClassName SecurityCodeDto
 * @Description TODO
 * @author jiahy
 * @date 2016年5月19日上午8:24:06
 */
public class SecurityCodeDto {
	@Translation(name = "验证码")
	private String securityCode;//验证码
	@Translation(name = "权限")
	private String object;//权限
	@Translation(name = "是否是验证码申请")
	private Integer isSecurity;//是否是验证码申请

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public Integer getIsSecurity() {
		return isSecurity;
	}
	public void setIsSecurity(Integer isSecurity) {
		this.isSecurity = isSecurity;
	}
	
	
}
