package com.skycloud.oa.esb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_esb_esbship")
public class EsbShip {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String code;//主键（规则为：中国籍船舶使用A+船舶初次登记号；外国籍船舶使用B+IMO编号；港澳船舶使用C+牌簿号，考虑到未来的“二岸三通”问题，预留D+台湾船舶标识号。
	private String category;//船舶分类，A/中国籍船舶、B/外国籍船舶、C/港澳船舶
	private String registNO;//船舶登记号，P(表示临时证书号，可选)+4位发证机构编码＋2位年＋6位顺序号
	private String chineseName;//中文船名
	private String englishName;//英文船名
	private String initRegist;//初次登记号
	private String inspectRegist;//船检登记号，船舶建造完工四位年份＋1位计算机纠错码＋2位中国船级社及各省(自治区\直辖市)船检局(处)的编码＋5位船检登记流水号
	private String callSign;//船舶呼号
	private String imo;//IMO编号
	private String shipNumber;//船舶标识号，考虑到与水监一期的兼容预留字段，(6位机构代码＋2位计算机标识＋2位年份＋6位顺序号
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getRegistNO() {
		return registNO;
	}
	public void setRegistNO(String registNO) {
		this.registNO = registNO;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getInitRegist() {
		return initRegist;
	}
	public void setInitRegist(String initRegist) {
		this.initRegist = initRegist;
	}
	public String getInspectRegist() {
		return inspectRegist;
	}
	public void setInspectRegist(String inspectRegist) {
		this.inspectRegist = inspectRegist;
	}
	public String getCallSign() {
		return callSign;
	}
	public void setCallSign(String callSign) {
		this.callSign = callSign;
	}
	public String getImo() {
		return imo;
	}
	public void setImo(String imo) {
		this.imo = imo;
	}
	public String getShipNumber() {
		return shipNumber;
	}
	public void setShipNumber(String shipNumber) {
		this.shipNumber = shipNumber;
	}
}
