package com.skycloud.oa.exception;
/**
 * 资源系统封装的异常类，出现异常封装成这个异常
 * @author 
 *
 */
public class OAException extends Exception{
	
	private static final long serialVersionUID = 1L;

	/* 异常编码 */
	private String code;

	/* 异常信息 */
	private String desc;
	
	/* 异常方法名 */
	private String funName;

	/* 异常对象 */
	private Throwable th;

	public OAException(String code, String funName, String desc, Throwable th) {
		this.code = code;
		this.desc = desc;
		this.th = th;
		this.funName = funName;
	}
	
	public OAException(String code, String desc, Throwable th) {
		this.code = code;
		this.desc = desc;
		this.th = th;
	}
	
	public OAException(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String toString() {
		return " code is " + code + ";desc is " + desc + "exception is " + th;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getFunName() {
		return funName;
	}

	public void setFunName(String funName) {
		this.funName = funName;
	}

	public Throwable getTh() {
		return th;
	}

	public void setTh(Throwable th) {
		this.th = th;
	}

}
