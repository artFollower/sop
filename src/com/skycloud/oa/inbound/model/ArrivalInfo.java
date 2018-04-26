package com.skycloud.oa.inbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;




/**
 * 到港附加信息
 * @author jiahy
 *
 */
@Entity
@Table(name = "t_pcs_arrival_info")
public class ArrivalInfo{


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
     private Integer id;
	@Translation(name = "长江口时间")
     private Long cjTime;//长江口时间
	@Translation(name = "太仓锚地时间")
     private Long tcTime;//太仓锚地时间
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
	@Translation(name = "预计拆管时间")
     private Long tearPipeTime;//预计拆管时间
	@Translation(name = "港序")
     private String port;//港序
	@Translation(name = "港口类型")
     private Integer portType;//0，空，1：国内港，2.国外港
	@Translation(name = "本年度航次")
     private String portNum;//本年度航次
	@Translation(name = "是否申报")
     private String report;//是否已申报
	@Translation(name = "船舶信息表")
     private String shipInfo;//船舶信息表
	@Translation(name = "备注")
     private String note;//备注
	@Translation(name = "超期时间")
     private Float overTime;//超期时间（小时）
	@Translation(name = "速遣时间")
     private Float repatriateTime;//速遣时间（小时）
	@Translation(name = "最大在港时间")
     private Long lastLeaveTime;//最大在港时间（时间点）
	@Translation(name = "到港吃水")
     private Float shipArrivalDraught;//到港吃水
	@Translation(name = "到港id")
     private Integer arrivalId;
	@Translation(name = "创建人")
     private Integer createUserId;
	@Translation(name = "创建时间")
     private Long createTime;
	@Translation(name = "审批人")
     private Integer reviewUserId;
	@Translation(name = "状态")
     private Integer status ;
	@Translation(name = "审批时间")
    private Long reviewTime;
	@Translation(name = "来源港")
     private String fromPlace;//来源港
	@Translation(name = "去向")
     private String toPlace;//去向
    // Constructors
	public ArrivalInfo() {
		super();
	}
	public ArrivalInfo(Integer id, Long cjTime, Long tcTime, Long norTime,
			Long anchorDate, Long anchorTime, Long pumpOpenTime,
			Long pumpStopTime, String workTime, Long leaveTime,
			Long tearPipeTime, String port, Integer portType, String portNum,
			String report, String shipInfo, String note, Float overTime,
			Float repatriateTime, Long lastLeaveTime, Float shipArrivalDraught,
			Integer arrivalId, Integer createUserId, Long createTime,
			Integer reviewUserId, Integer status, Long reviewTime) {
		super();
		this.id = id;
		this.cjTime = cjTime;
		this.tcTime = tcTime;
		this.norTime = norTime;
		this.anchorDate = anchorDate;
		this.anchorTime = anchorTime;
		this.pumpOpenTime = pumpOpenTime;
		this.pumpStopTime = pumpStopTime;
		this.workTime = workTime;
		this.leaveTime = leaveTime;
		this.tearPipeTime = tearPipeTime;
		this.port = port;
		this.portType = portType;
		this.portNum = portNum;
		this.report = report;
		this.shipInfo = shipInfo;
		this.note = note;
		this.overTime = overTime;
		this.repatriateTime = repatriateTime;
		this.lastLeaveTime = lastLeaveTime;
		this.shipArrivalDraught = shipArrivalDraught;
		this.arrivalId = arrivalId;
		this.createUserId = createUserId;
		this.createTime = createTime;
		this.reviewUserId = reviewUserId;
		this.status = status;
		this.reviewTime = reviewTime;
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
	public Long getTcTime() {
		return tcTime;
	}
	public void setTcTime(Long tcTime) {
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
	public Long getTearPipeTime() {
		return tearPipeTime;
	}
	public void setTearPipeTime(Long tearPipeTime) {
		this.tearPipeTime = tearPipeTime;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public Integer getPortType() {
		return portType;
	}
	public void setPortType(Integer portType) {
		this.portType = portType;
	}
	public String getPortNum() {
		return portNum;
	}
	public void setPortNum(String portNum) {
		this.portNum = portNum;
	}
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	public String getShipInfo() {
		return shipInfo;
	}
	public void setShipInfo(String shipInfo) {
		this.shipInfo = shipInfo;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Float getOverTime() {
		return overTime;
	}
	public void setOverTime(Float overTime) {
		this.overTime = overTime;
	}
	public Float getRepatriateTime() {
		return repatriateTime;
	}
	public void setRepatriateTime(Float repatriateTime) {
		this.repatriateTime = repatriateTime;
	}
	public Long getLastLeaveTime() {
		return lastLeaveTime;
	}
	public void setLastLeaveTime(Long lastLeaveTime) {
		this.lastLeaveTime = lastLeaveTime;
	}
	public Float getShipArrivalDraught() {
		return shipArrivalDraught;
	}
	public void setShipArrivalDraught(Float shipArrivalDraught) {
		this.shipArrivalDraught = shipArrivalDraught;
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
	public Integer getReviewUserId() {
		return reviewUserId;
	}
	public void setReviewUserId(Integer reviewUserId) {
		this.reviewUserId = reviewUserId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getReviewTime() {
		return reviewTime;
	}
	public void setReviewTime(Long reviewTime) {
		this.reviewTime = reviewTime;
	}

}