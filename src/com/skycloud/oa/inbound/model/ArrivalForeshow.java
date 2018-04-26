package com.skycloud.oa.inbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;




/**
 * 船期预告
 * @author yanyf
 *
 */
@Entity
@Table(name = "t_pcs_arrival_foreshow")
public class ArrivalForeshow{


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
     private Integer id;
	@Translation(name = "长江口时间")
     private Long cjTime;//长江口时间
	@Translation(name = "太仓锚地时间")
     private String tcTime;//太仓锚地时间
	@Translation(name = "NOR发出时间")
     private Long norTime;//NOR发出时间
	@Translation(name = "预计靠泊日期")
     private Long anchorDate;//预计靠泊日期
	@Translation(name = "预计靠泊时间")
     private Long anchorTime;//预计靠泊时间
	@Translation(name = "预计开泵时间")
     private Long pumpOpenTime;//预计开泵时间
	@Translation(name = "预计停泵时间")
     private Long pumpStopTime;//预计停泵时间
	@Translation(name = "预计作业时间")
     private String workTime;//预计作业时间
	@Translation(name = "预计离港时间")
     private Long leaveTime;//预计离港时间
	@Translation(name = "泊位")
     private Integer berth;//泊位
	@Translation(name = "船舶中文名")
     private Integer shipRefId;//船舶中文名
	@Translation(name = "物料名称id")
     private String productIds;//物料名称ids;
	@Translation(name = "物料名称")
     private String productNames;//物料名称;
	@Translation(name = "数量")
     private String count;//数量
	@Translation(name = "船长")
     private String shipLenth;//船长
	@Translation(name = "到港吃水")
     private String shipArrivalDraught;//到港吃水
	@Translation(name = "船代")
     private Integer shipAgentId;//船代
	@Translation(name = "货代")
     private String cargoAgentIds;//货代
	@Translation(name = "货代名称")
     private String cargoAgentNames;//货代名称
	@Translation(name = "港序")
     private String port;//港序
	@Translation(name = "接卸通知单")
     private String unloadNotify;//接卸通知单
	@Translation(name = "海关是否同意卸货")
     private Integer isCustomAgree;//海关是否同意卸货
	@Translation(name = "备注")
     private String note;//备注
	@Translation(name = "本年度航次")
     private String portNum;//本年度航次
	@Translation(name = "船舶信息表")
     private String shipInfo;//船舶信息表
	@Translation(name = "货主id")
     private String clientIds;//货主ids
	@Translation(name = "货主")
     private String clientNames;//货主
	@Translation(name = "是否申报")
     private String report;//是否已申报
	@Translation(name = "最大在港时间")
     private Long lastLeaveTime;//最大在港时间（时间点）
	@Translation(name = "预计拆管时间")
     private Long tearPipeTime;//预计拆管时间
	@Translation(name = "速遣时间")
     private Float repatriateTime;//速遣时间（小时）
	@Translation(name = "超期时间")
     private Float overTime;//超期时间（小时）
	@Translation(name = "到港id")
     private Integer arrivalId;
	@Translation(name = "创建人id")
     private Integer createUserId;
	@Translation(name = "创建时间")
     private Long createTime;
	@Translation(name = "编辑人id")
     private Integer editUserId;
	@Translation(name = "编辑时间")
     private Long editTime;
	@Translation(name = "状态")
     private Integer status ;//状态  1：作废
	@Translation(name = "贸易类型")
     private String taxTypes;//贸易类型 
     private Integer color;//颜色 1:黑色2:蓝色3:绿色4：红色
     
     
	public Integer getColor() {
		return color;
	}
	public void setColor(Integer color) {
		this.color = color;
	}
	public String getTaxTypes() {
		return taxTypes;
	}
	public void setTaxTypes(String taxTypes) {
		this.taxTypes = taxTypes;
	}
	public Integer getEditUserId() {
		return editUserId;
	}
	public void setEditUserId(Integer editUserId) {
		this.editUserId = editUserId;
	}
	
	
	public Long getEditTime() {
		return editTime;
	}
	public void setEditTime(Long editTime) {
		this.editTime = editTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getCjTime() {
		return cjTime;
	}
	public void setCjTime(Long cjTime) {
		this.cjTime = cjTime;
	}
	
	
	public String getTcTime() {
		return tcTime;
	}
	public void setTcTime(String tcTime) {
		this.tcTime = tcTime;
	}
	public Long getNorTime() {
		return norTime;
	}
	public void setNorTime(Long norTime) {
		this.norTime = norTime;
	}
	public Long getAnchorDate() {
		return anchorDate;
	}
	public void setAnchorDate(Long anchorDate) {
		this.anchorDate = anchorDate;
	}
	public Long getAnchorTime() {
		return anchorTime;
	}
	public void setAnchorTime(Long anchorTime) {
		this.anchorTime = anchorTime;
	}
	public Long getPumpOpenTime() {
		return pumpOpenTime;
	}
	public void setPumpOpenTime(Long pumpOpenTime) {
		this.pumpOpenTime = pumpOpenTime;
	}
	public Long getPumpStopTime() {
		return pumpStopTime;
	}
	public void setPumpStopTime(Long pumpStopTime) {
		this.pumpStopTime = pumpStopTime;
	}
	public String getWorkTime() {
		return workTime;
	}
	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
	public Long getLeaveTime() {
		return leaveTime;
	}
	public void setLeaveTime(Long leaveTime) {
		this.leaveTime = leaveTime;
	}
	public Integer getBerth() {
		return berth;
	}
	public void setBerth(Integer berth) {
		this.berth = berth;
	}
	public Integer getShipRefId() {
		return shipRefId;
	}
	public void setShipRefId(Integer shipRefId) {
		this.shipRefId = shipRefId;
	}
	public String getProductIds() {
		return productIds;
	}
	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}
	public String getProductNames() {
		return productNames;
	}
	public void setProductNames(String productNames) {
		this.productNames = productNames;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getShipLenth() {
		return shipLenth;
	}
	public void setShipLenth(String shipLenth) {
		this.shipLenth = shipLenth;
	}
	public String getShipArrivalDraught() {
		return shipArrivalDraught;
	}
	public void setShipArrivalDraught(String shipArrivalDraught) {
		this.shipArrivalDraught = shipArrivalDraught;
	}
	public Integer getShipAgentId() {
		return shipAgentId;
	}
	public void setShipAgentId(Integer shipAgentId) {
		this.shipAgentId = shipAgentId;
	}
	
	
	public String getCargoAgentIds() {
		return cargoAgentIds;
	}
	public void setCargoAgentIds(String cargoAgentIds) {
		this.cargoAgentIds = cargoAgentIds;
	}
	public String getCargoAgentNames() {
		return cargoAgentNames;
	}
	public void setCargoAgentNames(String cargoAgentNames) {
		this.cargoAgentNames = cargoAgentNames;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getUnloadNotify() {
		return unloadNotify;
	}
	public void setUnloadNotify(String unloadNotify) {
		this.unloadNotify = unloadNotify;
	}
	public Integer getIsCustomAgree() {
		return isCustomAgree;
	}
	public void setIsCustomAgree(Integer isCustomAgree) {
		this.isCustomAgree = isCustomAgree;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getPortNum() {
		return portNum;
	}
	public void setPortNum(String portNum) {
		this.portNum = portNum;
	}
	public String getShipInfo() {
		return shipInfo;
	}
	public void setShipInfo(String shipInfo) {
		this.shipInfo = shipInfo;
	}
	public String getClientIds() {
		return clientIds;
	}
	public void setClientIds(String clientIds) {
		this.clientIds = clientIds;
	}
	public String getClientNames() {
		return clientNames;
	}
	public void setClientNames(String clientNames) {
		this.clientNames = clientNames;
	}
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	public Long getLastLeaveTime() {
		return lastLeaveTime;
	}
	public void setLastLeaveTime(Long lastLeaveTime) {
		this.lastLeaveTime = lastLeaveTime;
	}
	public Long getTearPipeTime() {
		return tearPipeTime;
	}
	public void setTearPipeTime(Long tearPipeTime) {
		this.tearPipeTime = tearPipeTime;
	}
	public Float getRepatriateTime() {
		return repatriateTime;
	}
	public void setRepatriateTime(Float repatriateTime) {
		this.repatriateTime = repatriateTime;
	}
	public Float getOverTime() {
		return overTime;
	}
	public void setOverTime(Float overTime) {
		this.overTime = overTime;
	}
	public Integer getArrivalId() {
		return arrivalId;
	}
	public void setArrivalId(Integer arrivalId) {
		this.arrivalId = arrivalId;
	}
	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
   
}