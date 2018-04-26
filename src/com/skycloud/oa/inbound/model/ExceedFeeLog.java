package com.skycloud.oa.inbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 * 保存每个超期提单的数据记录
 * @author littleFly
 *
 */
@Entity
@Table(name = "t_pcs_exceed_fee_log")
public class ExceedFeeLog {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)	
private Integer id ;
@Translation(name = "超期费id")
private Integer exceedId;//超期费id
@Translation(name = "创建日期")
private String createTime;
@Translation(name = "类型")
private Integer type;
@Translation(name = "提单id")
private Integer ladingId;
@Translation(name = "车船号")
private String truckCode;
@Translation(name = "提单号")
private String ladingCode;
@Translation(name = "操作量")
private String operateNum;
@Translation(name = "剩余量")
private String currentNum;
private String col;
private String isCurrent;
@Translation(name = "货主")
private String clientName;
@Translation(name = "上下家")
private String contactClientName;
public ExceedFeeLog() {
	super();
	// TODO Auto-generated constructor stub
}


public ExceedFeeLog(Integer id, Integer exceedId, String createTime, Integer type, Integer ladingId, String truckCode,
		String ladingCode, String operateNum, String currentNum, String col, String isCurrent, String clientName,
		String contactClientName) {
	super();
	this.id = id;
	this.exceedId = exceedId;
	this.createTime = createTime;
	this.type = type;
	this.ladingId = ladingId;
	this.truckCode = truckCode;
	this.ladingCode = ladingCode;
	this.operateNum = operateNum;
	this.currentNum = currentNum;
	this.col = col;
	this.isCurrent = isCurrent;
	this.clientName = clientName;
	this.contactClientName = contactClientName;
}


public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public Integer getExceedId() {
	return exceedId;
}

public void setExceedId(Integer exceedId) {
	this.exceedId = exceedId;
}



public String getCreateTime() {
	return createTime;
}

public void setCreateTime(String createTime) {
	this.createTime = createTime;
}

public Integer getType() {
	return type;
}

public void setType(Integer type) {
	this.type = type;
}

public Integer getLadingId() {
	return ladingId;
}

public void setLadingId(Integer ladingId) {
	this.ladingId = ladingId;
}

public String getTruckCode() {
	return truckCode;
}

public void setTruckCode(String truckCode) {
	this.truckCode = truckCode;
}

public String getLadingCode() {
	return ladingCode;
}

public void setLadingCode(String ladingCode) {
	this.ladingCode = ladingCode;
}

public String getOperateNum() {
	return operateNum;
}

public void setOperateNum(String operateNum) {
	this.operateNum = operateNum;
}

public String getCurrentNum() {
	return currentNum;
}

public void setCurrentNum(String currentNum) {
	this.currentNum = currentNum;
}

public String getCol() {
	return col;
}

public void setCol(String col) {
	this.col = col;
}

public String getIsCurrent() {
	return isCurrent;
}

public void setIsCurrent(String isCurrent) {
	this.isCurrent = isCurrent;
}

public String getClientName() {
	return clientName;
}

public void setClientName(String clientName) {
	this.clientName = clientName;
}



public String getContactClientName() {
	return contactClientName;
}



public void setContactClientName(String contactClientName) {
	this.contactClientName = contactClientName;
}
	

}
