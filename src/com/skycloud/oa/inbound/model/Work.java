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
@Table(name = "t_pcs_work")
public class Work {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	 private Integer id;
	private  Integer type;// 1.表示生成调度日志
	@Translation(name = "联检时间")
     private Long checkTime;//联检时间
	@Translation(name = "实际到港时间")
     private Long arrivalTime;//实际到港时间
	@Translation(name = "开泵时间")
     private Long openPump;//开泵
	@Translation(name = "停泵时间")
     private Long stopPump;//停泵
	@Translation(name = "离港时间")
     private Long leaveTime;//离港时间
	@Translation(name = "拆管时间")
     private Long tearPipeTime;//拆管时间
	@Translation(name = "货品id")
     private Integer productId;//货品id
	@Translation(name = "到港id")
     private Integer arrivalId;//arrivalId
	@Translation(name = "多次接卸次序")
     private Integer orderNum;//多次接卸次序
	@Translation(name = "累计在港时间")
     private Float stayTime;//累计在港时间
	@Translation(name = "累计作业时间")
     private Float workTime;//累计作业时间
     private String dockCheck;//
     @Translation(name = "评价")
     private String evaluate;//评价
     @Translation(name = "评价人id")
     private Integer evaluateUserId;//评价人
     @Translation(name = "评价人")
     private String evaluateUser;//评价人
     @Translation(name = "评价时间")
     private Long evaluateTime;//评价时间
     @Translation(name = "动力班id")
     private  Integer dynamicUserId;//动力班
     @Translation(name = "确认内容")
     private String dynamicContent;//确认内容
     @Translation(name = "码头班id")
     private Integer dockUserId;//码头班
     @Translation(name = "确认内容")
     private String dockContent;//测试内容
     @Translation(name = "船方id")
     private Integer shipClientId;//船方
     @Translation(name = "确认内容")
     private String shipClientContent;//测试内容
     @Translation(name = "异常情况")
     private String unusualLog;//异常情况
     @Translation(name = "流程状态")
     private Integer status;//流程状态     5,接卸方案6,接卸准备7,打循环方案8,数量审核9，入库完成
     @Translation(name = "审核状态")
     private Integer reviewStatus;//数量审核审批流程状态     1.未提交，2.提交,3.审核，4.审核通过（进入下个流程），5.审核退回
     @Translation(name = "创建人id")
     private Integer createUserId;//数量确认创建人
     @Translation(name = "创建时间")
     private Long createTime;
     @Translation(name = "审核人id")
     private Integer reviewUserId;//数量确认审核人
     @Translation(name = "审核时间")
     private Long reviewTime;
     private Integer createRUserId;//接卸准备创建人
     private Long createRTime;//接卸准备创建时间
     @Translation(name = "备注")
     private String description;//备注
     
	public Work() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Work(Integer id, Integer type, Long checkTime, Long arrivalTime,
			Long openPump, Long stopPump, Long leaveTime, Long tearPipeTime,
			Integer productId, Integer arrivalId, Integer orderNum,
			Float stayTime, Float workTime, String dockCheck, String evaluate,
			Integer evaluateUserId, String evaluateUser, Long evaluateTime,
			Integer dynamicUserId, String dynamicContent, Integer dockUserId,
			String dockContent, Integer shipClientId, String shipClientContent,
			String unusualLog, Integer status, Integer reviewStatus,
			Integer createUserId, Long createTime, Integer reviewUserId,
			Long reviewTime, Integer createRUserId, Long createRTime,
			String description) {
		super();
		this.id = id;
		this.type = type;
		this.checkTime = checkTime;
		this.arrivalTime = arrivalTime;
		this.openPump = openPump;
		this.stopPump = stopPump;
		this.leaveTime = leaveTime;
		this.tearPipeTime = tearPipeTime;
		this.productId = productId;
		this.arrivalId = arrivalId;
		this.orderNum = orderNum;
		this.stayTime = stayTime;
		this.workTime = workTime;
		this.dockCheck = dockCheck;
		this.evaluate = evaluate;
		this.evaluateUserId = evaluateUserId;
		this.evaluateUser = evaluateUser;
		this.evaluateTime = evaluateTime;
		this.dynamicUserId = dynamicUserId;
		this.dynamicContent = dynamicContent;
		this.dockUserId = dockUserId;
		this.dockContent = dockContent;
		this.shipClientId = shipClientId;
		this.shipClientContent = shipClientContent;
		this.unusualLog = unusualLog;
		this.status = status;
		this.reviewStatus = reviewStatus;
		this.createUserId = createUserId;
		this.createTime = createTime;
		this.reviewUserId = reviewUserId;
		this.reviewTime = reviewTime;
		this.createRUserId = createRUserId;
		this.createRTime = createRTime;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Long checkTime) {
		this.checkTime = checkTime;
	}

	public Long getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Long arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Long getOpenPump() {
		return openPump;
	}

	public void setOpenPump(Long openPump) {
		this.openPump = openPump;
	}

	public Long getStopPump() {
		return stopPump;
	}

	public void setStopPump(Long stopPump) {
		this.stopPump = stopPump;
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

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getArrivalId() {
		return arrivalId;
	}

	public void setArrivalId(Integer arrivalId) {
		this.arrivalId = arrivalId;
	}
	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Float getStayTime() {
		return stayTime;
	}

	public void setStayTime(Float stayTime) {
		this.stayTime = stayTime;
	}

	public Float getWorkTime() {
		return workTime;
	}

	public void setWorkTime(Float workTime) {
		this.workTime = workTime;
	}

	public String getDockCheck() {
		return dockCheck;
	}

	public void setDockCheck(String dockCheck) {
		this.dockCheck = dockCheck;
	}

	public String getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}

	public Integer getEvaluateUserId() {
		return evaluateUserId;
	}

	public void setEvaluateUserId(Integer evaluateUserId) {
		this.evaluateUserId = evaluateUserId;
	}

	public String getEvaluateUser() {
		return evaluateUser;
	}

	public void setEvaluateUser(String evaluateUser) {
		this.evaluateUser = evaluateUser;
	}

	public Long getEvaluateTime() {
		return evaluateTime;
	}

	public void setEvaluateTime(Long evaluateTime) {
		this.evaluateTime = evaluateTime;
	}

	public Integer getDynamicUserId() {
		return dynamicUserId;
	}

	public void setDynamicUserId(Integer dynamicUserId) {
		this.dynamicUserId = dynamicUserId;
	}

	public String getDynamicContent() {
		return dynamicContent;
	}

	public void setDynamicContent(String dynamicContent) {
		this.dynamicContent = dynamicContent;
	}

	public Integer getDockUserId() {
		return dockUserId;
	}

	public void setDockUserId(Integer dockUserId) {
		this.dockUserId = dockUserId;
	}

	public String getDockContent() {
		return dockContent;
	}

	public void setDockContent(String dockContent) {
		this.dockContent = dockContent;
	}

	public Integer getShipClientId() {
		return shipClientId;
	}

	public void setShipClientId(Integer shipClientId) {
		this.shipClientId = shipClientId;
	}

	public String getShipClientContent() {
		return shipClientContent;
	}

	public void setShipClientContent(String shipClientContent) {
		this.shipClientContent = shipClientContent;
	}

	public String getUnusualLog() {
		return unusualLog;
	}

	public void setUnusualLog(String unusualLog) {
		this.unusualLog = unusualLog;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(Integer reviewStatus) {
		this.reviewStatus = reviewStatus;
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

	public Long getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(Long reviewTime) {
		this.reviewTime = reviewTime;
	}

	public Integer getCreateRUserId() {
		return createRUserId;
	}

	public void setCreateRUserId(Integer createRUserId) {
		this.createRUserId = createRUserId;
	}

	public Long getCreateRTime() {
		return createRTime;
	}

	public void setCreateRTime(Long createRTime) {
		this.createRTime = createRTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}