package com.skycloud.oa.outbound.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

@Entity
@Table(name = "t_pcs_train")
public class Train {
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Translation(name = "发货时间")
	private Date deliverTime; //发货时间
	@Translation(name = "操作人id")
	private int operator; 	//操作人
	@Translation(name = "计划量")
	private double approveAmount = 0; //计划数量
	@Translation(name = "实发量")
	private double actualAmount = 0; //实发数量
	@Translation(name = "货品id")	
	private int productId; //货品id
	@Translation(name = "状态")
	private String status; //状态
	@Translation(name = "客户id")
	private int clientId; //客户id
	private int transportId; //传输方案id
	private int plateId ;//车位id
	private int isChange ;//是否做变更 1是 0 否
	@Translation(name = "说明")
	private String description;//说明
	private String tip;//ip地址
	private String hostName;//客户端主机名称
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIsChange() {
		return isChange;
	}

	public void setIsChange(int isChange) {
		this.isChange = isChange;
	}

	public int getPlateId() {
		return plateId;
	}

	public void setPlateId(int plateId) {
		this.plateId = plateId;
	}

	public int getTransportId() {
		return transportId;
	}

	public void setTransportId(int transportId) {
		this.transportId = transportId;
	}

	public String getStatus() {
		return status;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDeliverTime() {
		return deliverTime;
	}

	public void setDeliverTime(Date deliverTime) {
		this.deliverTime = deliverTime;
	}

	public int getOperator() {
		return operator;
	}

	public void setOperator(int operator) {
		this.operator = operator;
	}

	public double getApproveAmount() {
		return approveAmount;
	}

	public void setApproveAmount(double approveAmount) {
		this.approveAmount = approveAmount;
	}

	public double getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(double actualAmount) {
		this.actualAmount = actualAmount;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

}
