package com.skycloud.oa.inbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 * 传输方案
 * @author jiahy
 *
 */
@Entity
@Table(name = "t_pcs_transport_program")
public class TransportProgram{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
     private Integer id;
	@Translation(name = "类型")
     private Integer type;//类型 0：接卸1：打循环2.船发3.倒罐
	@Translation(name = "工艺流程")
     private String flow;//工艺流程
	@Translation(name = "工艺流程图")
     private String svg;//工艺流程svg图片
	@Translation(name = "备注")
     private String node;//备注
	@Translation(name = "到港id")
     private Integer arrivalId;//到港id
	@Translation(name = "货品id")
     private Integer productId;//货品id
	@Translation(name = "多次接卸的次序")
     private Integer orderNum;//多次接卸次序
	@Translation(name = "管线使用情况")
     private String tubeInfo;//管线使用情况
	@Translation(name = "罐使用情况")
     private String tankInfo;//罐使用情况
	@Translation(name = "泵使用情况")
     private String pumpInfo;//泵使用情况
	@Translation(name = "码头作业要求")
     private String dockWork;//码头作业要求
	@Translation(name = "配管作业要求")
     private String tubeWork;//配管作业要求
	@Translation(name = "动力班作业要求")
     private String powerWork;//动力班作业要求
	@Translation(name = "创建人id")
     private Integer createUserId;//创建人
	@Translation(name = "创建时间")
     private Long createTime;//时间
	@Translation(name = "审核人id")
     private Integer reviewUserId;//品质审核人
	@Translation(name = "审核时间")
     private Long reviewTime;
	@Translation(name = "品质审核人id")
     private Integer reviewCraftUserId;//品质审核时间
	@Translation(name = "品质审核时间")
     private Long reviewCraftTime;
     private Long trainId;
     @Translation(name = "配管任务")
     private String pipeTask;//配管任务
     @Translation(name = "工作任务")
     private String workTask;//工作任务
     @Translation(name = "审批状态")
     private Integer status;//审批状态 0,保存 1,提交，2，两个审核都通过3，驳回，4,品质审核通过，5，工艺审核通过，
     @Translation(name = "备注")
     private String comment;//备注
     @Translation(name = "通知单号")
     private String noticeCodeA;//通知单号A
     @Translation(name = "通知单号")
     private String noticeCodeB;//通知单号B
     @Translation(name = "开泵时间")
     private Long openPumpTime;
     @Translation(name = "停泵时间")
     private Long stopPumpTime;
	public TransportProgram() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TransportProgram(Integer id, Integer type, String flow, String svg,
			String node, Integer arrivalId, Integer productId, Integer orderNum,
			String tubeInfo, String tankInfo, String pumpInfo, String dockWork,
			String tubeWork, String powerWork, Integer createUserId,
			Long createTime, Integer reviewUserId, Long reviewTime,
			Integer reviewCraftUserId, Long reviewCraftTime, Long trainId,
			String pipeTask, String workTask, Integer status, String comment,
			String noticeCodeA, String noticeCodeB) {
		super();
		this.id = id;
		this.type = type;
		this.flow = flow;
		this.svg = svg;
		this.node = node;
		this.arrivalId = arrivalId;
		this.productId = productId;
		this.orderNum = orderNum;
		this.tubeInfo = tubeInfo;
		this.tankInfo = tankInfo;
		this.pumpInfo = pumpInfo;
		this.dockWork = dockWork;
		this.tubeWork = tubeWork;
		this.powerWork = powerWork;
		this.createUserId = createUserId;
		this.createTime = createTime;
		this.reviewUserId = reviewUserId;
		this.reviewTime = reviewTime;
		this.reviewCraftUserId = reviewCraftUserId;
		this.reviewCraftTime = reviewCraftTime;
		this.trainId = trainId;
		this.pipeTask = pipeTask;
		this.workTask = workTask;
		this.status = status;
		this.comment = comment;
		this.noticeCodeA = noticeCodeA;
		this.noticeCodeB = noticeCodeB;
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
	public String getFlow() {
		return flow;
	}
	public void setFlow(String flow) {
		this.flow = flow;
	}
	public String getSvg() {
		return svg;
	}
	public void setSvg(String svg) {
		this.svg = svg;
	}
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	public Integer getArrivalId() {
		return arrivalId;
	}
	public void setArrivalId(Integer arrivalId) {
		this.arrivalId = arrivalId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getTubeInfo() {
		return tubeInfo;
	}
	public void setTubeInfo(String tubeInfo) {
		this.tubeInfo = tubeInfo;
	}
	public String getTankInfo() {
		return tankInfo;
	}
	public void setTankInfo(String tankInfo) {
		this.tankInfo = tankInfo;
	}
	public String getPumpInfo() {
		return pumpInfo;
	}
	public void setPumpInfo(String pumpInfo) {
		this.pumpInfo = pumpInfo;
	}
	public String getDockWork() {
		return dockWork;
	}
	public void setDockWork(String dockWork) {
		this.dockWork = dockWork;
	}
	public String getTubeWork() {
		return tubeWork;
	}
	public void setTubeWork(String tubeWork) {
		this.tubeWork = tubeWork;
	}
	public String getPowerWork() {
		return powerWork;
	}
	public void setPowerWork(String powerWork) {
		this.powerWork = powerWork;
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
	public Integer getReviewCraftUserId() {
		return reviewCraftUserId;
	}
	public void setReviewCraftUserId(Integer reviewCraftUserId) {
		this.reviewCraftUserId = reviewCraftUserId;
	}
	public Long getReviewCraftTime() {
		return reviewCraftTime;
	}
	public void setReviewCraftTime(Long reviewCraftTime) {
		this.reviewCraftTime = reviewCraftTime;
	}
	public Long getTrainId() {
		return trainId;
	}
	public void setTrainId(Long trainId) {
		this.trainId = trainId;
	}
	public String getPipeTask() {
		return pipeTask;
	}
	public void setPipeTask(String pipeTask) {
		this.pipeTask = pipeTask;
	}
	public String getWorkTask() {
		return workTask;
	}
	public void setWorkTask(String workTask) {
		this.workTask = workTask;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getNoticeCodeA() {
		return noticeCodeA;
	}
	public void setNoticeCodeA(String noticeCodeA) {
		this.noticeCodeA = noticeCodeA;
	}
	public String getNoticeCodeB() {
		return noticeCodeB;
	}
	public void setNoticeCodeB(String noticeCodeB) {
		this.noticeCodeB = noticeCodeB;
	}

	public Long getOpenPumpTime() {
		return openPumpTime;
	}

	public void setOpenPumpTime(Long openPumpTime) {
		this.openPumpTime = openPumpTime;
	}

	public Long getStopPumpTime() {
		return stopPumpTime;
	}

	public void setStopPumpTime(Long stopPumpTime) {
		this.stopPumpTime = stopPumpTime;
	}

}