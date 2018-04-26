package com.skycloud.oa.outbound.model;


import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;
@Entity
@Table(name = "t_pcs_goodslog")
public class GoodsLog {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id ;
	@Translation(name = "货体id")
	private int goodsId ;//货体id
	@Translation(name = "上下级id")
	private int clientId;//上下家
	@Translation(name = "类型")
	private int type;//类型  1：入库 2：调入3：调出4：封放 5：发货6:退回7:预入库8:撤销9:扣损 10:商检 11:清盘  12:复盘 13冲销记录（当等于13时，originalTime是冲销时间，createUserId是冲销人）
	@Translation(name = "开票量")
	private double deliverNum ;//发货量
	@Translation(name = "发货类型")
	private int deliverType ;//发货类型   1：车发，2-船发
	@Translation(name = "创建时间")
	private long createTime ;//创建时间
	@Translation(name = "提单id")
	private int ladingId;//提单id
	@Translation(name = "仓位")
	private String storageInfo ;//仓位
	@Translation(name = "货量变化")
	private double goodsChange ;//货量变化
	@Translation(name = "货体存量")
	private double surplus ;//货体存量
	@Translation(name = "货体封量")
	private double goodsSave;//货体封量
	@Translation(name = "发货单")
	private String deliverNo ;//发货单
	@Translation(name = "车船id")
	private int batchId ;//车船id
	@Translation(name = "提货单位")
	private int ladingClientId ; //提货单位
	@Translation(name = "提货凭证")
	private String ladingEvidence ; //提货凭证
	@Translation(name = "车船id")
	private int vehicleShipId ;//车船id
	@Translation(name = "实发量")
	private double actualNum ; //实发量
	@Translation(name = "开票编号")
	private String serial ;  //开票编号
	@Translation(name = "创建人id")
	private int createUserId ; //创建人id
	@Translation(name = "打印状态")
	private String printType ; //1为已打印 2为未打印
	@Translation(name = "提单编号")
	private String ladingCode ;//提单号 此提单号非彼提单号 手动输入的提单号
	@Translation(name = "临时发货量")
	private String tempDeliverNum ; //临时的发货量
	@Translation(name = "开票时间")
	private Long invoiceTime;//开票时间
	@Translation(name = "开票备注")
	private String remark;//开票备注
	@Translation(name = "开票清单")
	private Integer isCleanInvoice;//开票清单
	@Translation(name = "提单类型")
	private Integer ladingType;//提单类型 1：转卖2：发货     用于区分调入调出操作
	@Translation(name = "调整后量")
	private BigDecimal afUpNum ; //调整后量
	@Translation(name = "差异量")
	private BigDecimal afDiffNum ;//差异量
	@Translation(name = "储罐名字")
	private String tankName ;
	@Translation(name = "储罐id")
	private int tankId ;
	@Translation(name = "是否实发")
	private Integer actualType=0;//是否已经实发0，未实发，1，已实发
	@Translation(name = "下级货体")
	private int nextLadingId; //针对调出，保存调出到的提单id
	@Translation(name = "原始操作时间")
	private long originalTime; //原始操作时间
	@Translation(name = "下级货体id")
	private int nextGoodsId=0;//针对调出，保存调出到的货体id
	
	
	
	
	public GoodsLog(long createTime, String serial) {
		super();
		this.createTime = createTime;
		this.serial = serial;
	}
	public GoodsLog() {
		// TODO Auto-generated constructor stub
	}
	public int getNextGoodsId() {
		return nextGoodsId;
	}
	public void setNextGoodsId(int nextGoodsId) {
		this.nextGoodsId = nextGoodsId;
	}
	public long getOriginalTime() {
		return originalTime;
	}
	public void setOriginalTime(long originalTime) {
		this.originalTime = originalTime;
	}
	public int getNextLadingId() {
		return nextLadingId;
	}
	public void setNextLadingId(int nextLadingId) {
		this.nextLadingId = nextLadingId;
	}
	public int getTankId() {
		return tankId;
	}
	public void setTankId(int tankId) {
		this.tankId = tankId;
	}
	public String getTankName() {
		return tankName;
	}
	public void setTankName(String tankName) {
		this.tankName = tankName;
	}
	public BigDecimal getAfUpNum() {
		return afUpNum;
	}
	public void setAfUpNum(BigDecimal afUpNum) {
		this.afUpNum = afUpNum;
	}
	public BigDecimal getAfDiffNum() {
		return afDiffNum;
	}
	public void setAfDiffNum(BigDecimal afDiffNum) {
		this.afDiffNum = afDiffNum;
	}
	public String getTempDeliverNum() {
		return tempDeliverNum;
	}
	public void setTempDeliverNum(String tempDeliverNum) {
		this.tempDeliverNum = tempDeliverNum;
	}
	public String getLadingCode() {
		return ladingCode;
	}
	public void setLadingCode(String ladingCode) {
		this.ladingCode = ladingCode;
	}
	public Integer getLadingType() {
		return ladingType;
	}
	public void setLadingType(Integer ladingType) {
		this.ladingType = ladingType;
	}
	public String getPrintType() {
		return printType;
	}
	public void setPrintType(String printType) {
		this.printType = printType;
	}
	public int getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public double getActualNum() {
		return actualNum;
	}
	public void setActualNum(double actualNum) {
		this.actualNum = actualNum;
	}
	public int getVehicleShipId() {
		return vehicleShipId;
	}
	public void setVehicleShipId(int vehicleShipId) {
		this.vehicleShipId = vehicleShipId;
	}
	public int getLadingId() {
		return ladingId;
	}
	public void setLadingId(int ladingId) {
		this.ladingId = ladingId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public double getDeliverNum() {
		return deliverNum;
	}
	public void setDeliverNum(double deliverNum) {
		this.deliverNum = deliverNum;
	}
	public int getDeliverType() {
		return deliverType;
	}
	public void setDeliverType(int deliverType) {
		this.deliverType = deliverType;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public String getStorageInfo() {
		return storageInfo;
	}
	public void setStorageInfo(String storageInfo) {
		this.storageInfo = storageInfo;
	}
	public double getGoodsChange() {
		return goodsChange;
	}
	public void setGoodsChange(double goodsChange) {
		this.goodsChange = goodsChange;
	}
	public double getSurplus() {
		return surplus;
	}
	public void setSurplus(double surplus) {
		this.surplus = surplus;
	}
	public double getGoodsSave() {
		return goodsSave;
	}
	public void setGoodsSave(double goodsSave) {
		this.goodsSave = goodsSave;
	}
	public String getDeliverNo() {
		return deliverNo;
	}
	public void setDeliverNo(String deliverNo) {
		this.deliverNo = deliverNo;
	}
	public int getBatchId() {
		return batchId;
	}
	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}
	public int getLadingClientId() {
		return ladingClientId;
	}
	public void setLadingClientId(int ladingClientId) {
		this.ladingClientId = ladingClientId;
	}
	public String getLadingEvidence() {
		return ladingEvidence;
	}
	public void setLadingEvidence(String ladingEvidence) {
		this.ladingEvidence = ladingEvidence;
	}
	public Integer getActualType() {
		return actualType;
	}
	public void setActualType(Integer actualType) {
		this.actualType = actualType;
	}
	public Long getInvoiceTime() {
		return invoiceTime;
	}
	public void setInvoiceTime(Long invoiceTime) {
		this.invoiceTime = invoiceTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getIsCleanInvoice() {
		return isCleanInvoice;
	}
	public void setIsCleanInvoice(Integer isCleanInvoice) {
		this.isCleanInvoice = isCleanInvoice;
	}
	
}
