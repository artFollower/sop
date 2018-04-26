package com.skycloud.oa.esb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 残损信息
 * @ClassName: HarborContainer 
 * @Description: TODO
 * @author xie
 * @date 2015年1月22日 下午9:43:10
 */
@Entity
@Table(name="t_esb_damage")
public class HarborContainerDamage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String recordId = "60";//记录类型标识
	private String typeCode;//残损类型代码
	private String type;//残损类型
	private String argeCode;//残损范围代码
	private String arge;//残损范围
	private String severity;//残损程度
	
	@OneToOne
	@JoinColumn(name="containerId")
	private HarborContainer container;

	public String getRecordId() {
		return recordId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getArgeCode() {
		return argeCode;
	}

	public void setArgeCode(String argeCode) {
		this.argeCode = argeCode;
	}

	public String getArge() {
		return arge;
	}

	public void setArge(String arge) {
		this.arge = arge;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public HarborContainer getContainer() {
		return container;
	}

	public void setContainer(HarborContainer container) {
		this.container = container;
	}

}
