package com.skycloud.oa.system.dto;

/**
 * 港口dto
 * @author jiahy
 *
 */
public class PortDto {
	private Integer id;//主键，储罐id，自增
	private String code;//储罐代码
	private String name;//储罐名称，暂不使用
	private String ids;
private String letter;
	
	public String getLetter() {
		return letter;
	}
	public void setLetter(String letter) {
		this.letter = letter;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	
}
