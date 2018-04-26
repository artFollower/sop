package com.skycloud.oa.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 反馈信息实体定义
 * @ClassName: OaMsg 
 * @Description: TODO
 * @author xie
 * @date 2014年11月20日 下午4:06:49 
 *
 */
public class OaMsg {

	private String code = "0000";//返回码
	private String msg;//反馈错误信息
	private Map<String, String> map = new HashMap<String, String>();//返回的参数信息
	private List<Object> data = new ArrayList<Object>();//返回的数据

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}
}
