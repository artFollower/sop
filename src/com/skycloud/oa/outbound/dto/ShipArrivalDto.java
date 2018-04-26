package com.skycloud.oa.outbound.dto;

import java.util.List;

import com.skycloud.oa.annatation.Translation;
import com.skycloud.oa.inbound.model.Arrival;
import com.skycloud.oa.inbound.model.ArrivalPlan;

public class ShipArrivalDto {
	private Integer id;
	@Translation(name = "到港信息")
	private Arrival arrival ;
	@Translation(name = "离港计划列表")
	private List<ArrivalPlan> arrivalPlanList ;
	@Translation(name = "船名")
	private String shipName ;
	@Translation(name = "离港计划")
	private ArrivalPlan arrivalPlan ;
	@Translation(name = "开始时间")
	private Long startTime ;
	@Translation(name = "截止时间")
	private Long endTime ;
	@Translation(name = "状态")
	private String status ;
	@Translation(name = "船代id")
	private String shipAgentId ;
	@Translation(name = "船舶吃水")
	private String shipArrivalDraught ;
	@Translation(name = "是否申报")
	private String report ;
	@Translation(name = "泊位id")
	private int berthId ;
	@Translation(name = "作业计划id")
	private String arrivalInfoId ;
	@Translation(name = "到港id")
	private String arrivalId ;
	@Translation(name = "货品id")
	private Integer productId;
	@Translation(name = "提货单位id")
	private Integer ladingClientId;//提货单位
	@Translation(name = "提单号")
	private String ladingEvidence;//提单号
	@Translation(name = "是否作废")
	private Integer type;// 0全部（不包括作废）1.作废的
	@Translation(name = "到港时间")
	private String arrivalTime;
	@Translation(name = "中文船名")
	private String shipRefName;
	@Translation(name = "船舶id")
	private Integer shipId;
	@Translation(name = "是否转输")
	private Integer isTransport;//是否是转输
	@Translation(name = "是否显示全部")
	private Integer isShowAll;//是否显示全部
	@Translation(name = "是否显示警告")
	private Integer isWarning;//是否显示警告
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Arrival getArrival() {
		return arrival;
	}
	public void setArrival(Arrival arrival) {
		this.arrival = arrival;
	}
	public List<ArrivalPlan> getArrivalPlanList() {
		return arrivalPlanList;
	}
	public void setArrivalPlanList(List<ArrivalPlan> arrivalPlanList) {
		this.arrivalPlanList = arrivalPlanList;
	}
	public String getShipName() {
		return shipName;
	}
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
	public ArrivalPlan getArrivalPlan() {
		return arrivalPlan;
	}
	public void setArrivalPlan(ArrivalPlan arrivalPlan) {
		this.arrivalPlan = arrivalPlan;
	}
	
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getShipAgentId() {
		return shipAgentId;
	}
	public void setShipAgentId(String shipAgentId) {
		this.shipAgentId = shipAgentId;
	}
	public String getShipArrivalDraught() {
		return shipArrivalDraught;
	}
	public void setShipArrivalDraught(String shipArrivalDraught) {
		this.shipArrivalDraught = shipArrivalDraught;
	}
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	
	public int getBerthId() {
		return berthId;
	}
	public void setBerthId(int berthId) {
		this.berthId = berthId;
	}
	public String getArrivalInfoId() {
		return arrivalInfoId;
	}
	public void setArrivalInfoId(String arrivalInfoId) {
		this.arrivalInfoId = arrivalInfoId;
	}
	public String getArrivalId() {
		return arrivalId;
	}
	public void setArrivalId(String arrivalId) {
		this.arrivalId = arrivalId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getLadingClientId() {
		return ladingClientId;
	}
	public void setLadingClientId(Integer ladingClientId) {
		this.ladingClientId = ladingClientId;
	}
	public String getLadingEvidence() {
		return ladingEvidence;
	}
	public void setLadingEvidence(String ladingEvidence) {
		this.ladingEvidence = ladingEvidence;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getShipRefName() {
		return shipRefName;
	}
	public void setShipRefName(String shipRefName) {
		this.shipRefName = shipRefName;
	}
	public Integer getShipId() {
		return shipId;
	}
	public void setShipId(Integer shipId) {
		this.shipId = shipId;
	}
	public Integer getIsTransport() {
		return isTransport;
	}
	public void setIsTransport(Integer isTransport) {
		this.isTransport = isTransport;
	}
	public Integer getIsShowAll() {
		return isShowAll;
	}
	public void setIsShowAll(Integer isShowAll) {
		this.isShowAll = isShowAll;
	}
	public Integer getIsWarning() {
		return isWarning;
	}
	public void setIsWarning(Integer isWarning) {
		this.isWarning = isWarning;
	}
	
}
