package com.skycloud.oa.order.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

@Entity
@Table(name = "t_pcs_intention")
public class Intention {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private int id;
	@Translation(name = "意向代码")
	private String code;
	@Translation(name = "标题")
	private String title;
	@Translation(name = "客户id")
	private int clientId;
	@Translation(name = "货品id")
	private int productId;
	@Translation(name = "类型")
	private int type;
	@Translation(name = "数量")
	private String quantity;

	// private String clientGroupId;//客户组id
	@Translation(name = "仓储费")
	private String storagePrice; // 仓储费
	@Translation(name = "码头通过费")
	private String passPrice;// 码头通过费
	@Translation(name = "超期费")
	private String overtimePrice;// 超期费
	@Translation(name = "港口设施保安费")
	private String portSecurityPrice;// 港口设施保安费
	@Translation(name = "港务费")
	private String portServicePrice;// 港务费
	@Translation(name = "其他费")
	private String otherPrice;// 其他费
	@Translation(name = "损耗率")
	private String lossRate;// 损耗率
	@Translation(name = "合同周期")
	private String period;// 合同周期

	// private String unitPrice;
	@Translation(name = "总金额")
	private String totalPrice;// 总金额
	@Translation(name = "描述")
	private String description;// 描述
	@Translation(name = "销售员id")
	private int salesUserId;// 销售员id
	@Translation(name = "状态")
	private int status;// 状态
	@Translation(name = "创建人id")
	private int createUserId;// 创建人id
	@Column(name = "createTime", updatable = false)
	@Translation(name = "创建时间")
	private Timestamp createTime;// 创建时间
	@Translation(name = "修改人id")
	private int editUserId;// 修改人id
	@Translation(name = "修改时间")
	private Timestamp editTime;// 修改时间
	@Translation(name = "审核人")
	private Integer reviewUserId;// 审核人
	@Translation(name = "审核时间")
	private Long reviewTime;// 审核时间
	@Translation(name = "货品")
	private String productNameList;// 多个货品名
	@Translation(name = "货品id")
	private String productIdList;//多个货品ids
	
	
	
	public String getProductIdList() {
		return productIdList;
	}

	public void setProductIdList(String productIdList) {
		this.productIdList = productIdList;
	}

	public String getProductNameList() {
		return productNameList;
	}

	public void setProductNameList(String productNameList) {
		this.productNameList = productNameList;
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

	public String getStoragePrice() {
		return storagePrice;
	}

	public void setStoragePrice(String storagePrice) {
		this.storagePrice = storagePrice;
	}

	public String getPassPrice() {
		return passPrice;
	}

	public void setPassPrice(String passPrice) {
		this.passPrice = passPrice;
	}

	public String getOvertimePrice() {
		return overtimePrice;
	}

	public void setOvertimePrice(String overtimePrice) {
		this.overtimePrice = overtimePrice;
	}

	public String getPortSecurityPrice() {
		return portSecurityPrice;
	}

	public void setPortSecurityPrice(String portSecurityPrice) {
		this.portSecurityPrice = portSecurityPrice;
	}

	public String getPortServicePrice() {
		return portServicePrice;
	}

	public void setPortServicePrice(String portServicePrice) {
		this.portServicePrice = portServicePrice;
	}

	public String getOtherPrice() {
		return otherPrice;
	}

	public void setOtherPrice(String otherPrice) {
		this.otherPrice = otherPrice;
	}

	public String getLossRate() {
		return lossRate;
	}

	public void setLossRate(String lossRate) {
		this.lossRate = lossRate;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	// public String getUnitPrice() {
	// return unitPrice;
	// }
	//
	// public void setUnitPrice(String unitPrice) {
	// this.unitPrice = unitPrice;
	// }

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getEditTime() {
		return editTime;
	}

	public void setEditTime(Timestamp editTime) {
		this.editTime = editTime;
	}

	public int getSalesUserId() {
		return salesUserId;
	}

	public void setSalesUserId(int salesUserId) {
		this.salesUserId = salesUserId;
	}

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public int getEditUserId() {
		return editUserId;
	}

	public void setEditUserId(int editUserId) {
		this.editUserId = editUserId;
	}

}
