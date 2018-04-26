package com.skycloud.oa.outbound.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 * TPcsLading entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_pcs_lading")
public class Lading implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Translation(name = "提单代码")
	private String code;// 提单代码（客户提单号）
	@Translation(name = "客户id")
	private int clientId;// 客户id
	@Translation(name = "货品id")
	private int productId;// 货品id
	@Translation(name = "提单类型")
	private int type;// 提单类型：1转卖2发货
	@Translation(name = "接收客户id")
	private int receiveClientId;// 收货客户id
	@Translation(name = "提单数量")
	private String goodsTotal;// 提单数量
	@Translation(name = "放行数")
	private String goodsPass;// 放行数
	@Translation(name = "调出量")
	private String goodsOut;// 调出量
	@Translation(name = "发货数量")
	private String goodsDelivery;// 发货数量
	@Translation(name = "起计日期")
	private Timestamp startTime;// 有效开始时间
	@Translation(name = "有效期")
	private Timestamp endTime;// 有效结束时间
	@Translation(name = "状态")
	private int status;// 状态0锁定1激活2终止
	@Translation(name = "描述")
	private String description;// 描述
	@Translation(name = "创建id")
	private int createUserId;// 创建人用户id
	@Translation(name = "创建时间")
	private Timestamp createTime;// 创建时间
	@Translation(name = "审批人id")
	private int reviewUserId;// 审批人用户id
	@Translation(name = "审批时间")
	private Timestamp reviewTime;// 审批时间
	@Translation(name = "海关放行编号")
	private String customsPassCode;//海关放行编号
	@Translation(name = "是否结清")
	private Integer isFinish=-1;//是否结清 0-未结清，1-已结清
	@Translation(name = "提单url")
	private String fileUrl;//提单样本url
	@Translation(name = "是否长期")
	private Integer isLong;//有效期长期    0 否  1  是
	@Translation(name = "单号")
	private String No;//打印单号
	
	
	public String getNo() {
		return No;
	}

	public void setNo(String no) {
		No = no;
	}

	public Integer getIsLong() {
		return isLong;
	}

	public void setIsLong(Integer isLong) {
		this.isLong = isLong;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getCustomsPassCode() {
		return customsPassCode;
	}

	public void setCustomsPassCode(String customsPassCode) {
		this.customsPassCode = customsPassCode;
	}

	public String getGoodsOut() {
		return goodsOut;
	}

	public void setGoodsOut(String goodsOut) {
		this.goodsOut = goodsOut;
	}

	public int getReceiveClientId() {
		return receiveClientId;
	}

	public void setReceiveClientId(int receiveClientId) {
		this.receiveClientId = receiveClientId;
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

	public String getGoodsTotal() {
		return goodsTotal;
	}

	public void setGoodsTotal(String goodsTotal) {
		this.goodsTotal = goodsTotal;
	}

	public String getGoodsPass() {
		return goodsPass;
	}

	public void setGoodsPass(String goodsPass) {
		this.goodsPass = goodsPass;
	}

	public String getGoodsDelivery() {
		return goodsDelivery;
	}

	public void setGoodsDelivery(String goodsDelivery) {
		this.goodsDelivery = goodsDelivery;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public int getReviewUserId() {
		return reviewUserId;
	}

	public void setReviewUserId(int reviewUserId) {
		this.reviewUserId = reviewUserId;
	}

	public Timestamp getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(Timestamp reviewTime) {
		this.reviewTime = reviewTime;
	}

	public Integer getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(Integer isFinish) {
		this.isFinish = isFinish;
	}

}