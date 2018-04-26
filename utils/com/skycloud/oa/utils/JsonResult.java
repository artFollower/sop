package com.skycloud.oa.utils;

import java.io.Serializable;
import java.util.Map;

/**
 * 待删除
* @ClassName: JsonResult 
* @Description: TODO
* @author chao.peng@baidao.com   
* @date 2014-11-26 上午11:56:35 
*
 */

public class JsonResult implements Serializable {

	private static final long serialVersionUID = -5420102319380525692L;

	private Boolean success;
	private String message;
	private Integer code;

	private Map<String, Object> datas;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getDatas() {
		return datas;
	}

	public void setDatas(Map<String, Object> datas) {
		this.datas = datas;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public JsonResult(Boolean success, Integer code, String message, Map<String, Object> datas) {
		super();
		this.success = success;
		this.code = code;
		this.message = message;
		this.datas = datas;
	}

	public JsonResult(Boolean success, String message, Map<String, Object> datas) {
		super();
		this.success = success;
		this.message = message;
		this.datas = datas;
	}

	public JsonResult(Boolean success, Map<String, Object> datas) {
		this(success, null, datas);
	}

	public JsonResult(Integer code, Map<String, Object> datas) {
		this(null, code, null, datas);
	}

	public JsonResult(Boolean success, String message) {
		this(success, message, null);
	}

	public JsonResult(Boolean success, Integer code) {
		this(success, code, null, null);
	}

	public JsonResult(Integer code) {
		this(null, code);
	}

	public JsonResult(Boolean success) {
		this(success, null, null);
	}

}
