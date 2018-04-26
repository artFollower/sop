package com.skycloud.oa.inbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 * 货品的到港作业表
 * @author jiahy
 *
 */
@Entity
@Table(name = "t_pcs_transport_info")
public class TransportInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	 private Integer id;
	 private Integer transportId;//传输方案id
	 @Translation(name = "类型")
	 private Integer type;//类型 1.打循环
	 @Translation(name = "倒出罐号")
	 private String outTankNames;//倒出罐号
	 private String outTankIds;//倒出罐ids
	 @Translation(name = "倒入罐号")
	 private String inTankNames;//倒入罐号
	 private String inTankIds;//倒入罐ids
	 @Translation(name = "泵名")
	 private String pupmNames;//泵名
	 private String pupmIds;//泵ids
	 private String tubeIds;//管线ids
	 @Translation(name = "管线名称")
	 private String tubeNames;//管线名字
	 @Translation(name = "罐内数量")
	 private String tankCount;//罐内数量
	 @Translation(name = "备注信息")
	 private String message;//备注信息
	 @Translation(name = "管线任务")
	 private String tubeTask;//管线任务
	 @Translation(name = "管线状态")
	 private String tubeState;//管线状态
	 @Translation(name = "备注")
	 private String description;//备注
	 @Translation(name = "倒罐目的")
	 private String transferPurpose;//倒罐目的
	public TransportInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTransportId() {
		return transportId;
	}
	public void setTransportId(Integer transportId) {
		this.transportId = transportId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getOutTankNames() {
		return outTankNames;
	}
	public void setOutTankNames(String outTankNames) {
		this.outTankNames = outTankNames;
	}
	public String getOutTankIds() {
		return outTankIds;
	}
	public void setOutTankIds(String outTankIds) {
		this.outTankIds = outTankIds;
	}
	public String getInTankNames() {
		return inTankNames;
	}
	public void setInTankNames(String inTankNames) {
		this.inTankNames = inTankNames;
	}
	public String getInTankIds() {
		return inTankIds;
	}
	public void setInTankIds(String inTankIds) {
		this.inTankIds = inTankIds;
	}
	public String getPupmNames() {
		return pupmNames;
	}
	public void setPupmNames(String pupmNames) {
		this.pupmNames = pupmNames;
	}
	public String getPupmIds() {
		return pupmIds;
	}
	public void setPupmIds(String pupmIds) {
		this.pupmIds = pupmIds;
	}
	public String getTubeIds() {
		return tubeIds;
	}
	public void setTubeIds(String tubeIds) {
		this.tubeIds = tubeIds;
	}
	public String getTubeNames() {
		return tubeNames;
	}
	public void setTubeNames(String tubeNames) {
		this.tubeNames = tubeNames;
	}
	public String getTankCount() {
		return tankCount;
	}
	public void setTankCount(String tankCount) {
		this.tankCount = tankCount;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTubeTask() {
		return tubeTask;
	}
	public void setTubeTask(String tubeTask) {
		this.tubeTask = tubeTask;
	}
	public String getTubeState() {
		return tubeState;
	}
	public void setTubeState(String tubeState) {
		this.tubeState = tubeState;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getTransferPurpose() {
		return transferPurpose;
	}

	public void setTransferPurpose(String transferPurpose) {
		this.transferPurpose = transferPurpose;
	}

}