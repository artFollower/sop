package com.skycloud.oa.inbound.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 * 货体信息表
 * 
 * @author jiahy
 * 
 */
@Entity
@Table(name = "t_pcs_goods")
public class Goods {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;// 主键，货体id，自增
	@Translation(name = "代码")
	private String code;// 货体代码，基于货批代码生成
	@Translation(name = "类型")
	private Integer type;// 货体类型，预留
	@Translation(name = "货批id")
	private Integer cargoId;// 货批id
	@Translation(name = "合同id")
	private Integer contractId;// 合同id
	@Translation(name = "货体组")
	private Integer goodsGroupId;// 货体组
	@Translation(name = "货主id")
	private Integer clientId;// 货主id
	@Translation(name = "货品id")
	private Integer productId;// 货品id
	@Translation(name = "来源货体id")
	private Integer sourceGoodsId;// 来源货体id
	@Translation(name = "母货体id")
	private Integer rootGoodsId;// 母货体id
	@Translation(name = "储罐id")
	private Integer tankId;// 储罐id
	@Translation(name = "混罐的货罐")
	private String tankCodes;//保存混罐的货罐名
	@Translation(name = "创建日期")
	private Timestamp createTime;// 建仓日期
	@Translation(name = "损耗率")
	private String lossRate;// 损耗率
	@Translation(name = "货体总量")
	private String goodsTotal;// 货体总量
	@Translation(name = "商检数量")
	private String goodsInspect;// 商检数量
	@Translation(name = "罐检数量")
	private String goodsTank;// 罐检数量
	@Translation(name = "调入总量")
	private String goodsIn;// 调入总量
	@Translation(name = "调出总量")
	private String goodsOut;// 调出总量
	@Translation(name = "当前存量")
	private String goodsCurrent;// 当前存量
	@Translation(name = "入库放行总量")
	private String goodsInPass;// 入库放行总量
	@Translation(name = "出库放行总量")
	private String goodsOutPass;// 出库放行总量
	@Translation(name = "结算状态")
	private Integer isAccountComp ; // 1 为已结算
	@Translation(name = "仓储费起计日期")
	private String recordTime ;//仓储费起止日期
	@Translation(name = "海关放行编号")
	private String customsPassCode;//海关放行编号
	@Translation(name = "是否清盘")
	private Integer isFinish;//是否清盘   0：未清盘  1：已清盘
	@Translation(name = "海关入库放行")
	private String customInPass;//海关入库放行
	@Translation(name = "海关出库放行")
	private String customOutPass;//海关出库放行
	
	@Translation(name = "报关单位id")
	private Integer clearanceClientId;//报关单位；
	@Translation(name = "储罐贸易类型")
	private Integer tankType;//储罐贸易类型;
	
	public Integer getTankType() {
		return tankType;
	}

	public void setTankType(Integer tankType) {
		this.tankType = tankType;
	}

	public Integer getClearanceClientId() {
		return clearanceClientId;
	}

	public void setClearanceClientId(Integer clearanceClientId) {
		this.clearanceClientId = clearanceClientId;
	}

	public String getCustomInPass() {
		return customInPass;
	}

	public void setCustomInPass(String customInPass) {
		this.customInPass = customInPass;
	}

	public String getCustomOutPass() {
		return customOutPass;
	}

	public void setCustomOutPass(String customOutPass) {
		this.customOutPass = customOutPass;
	}

	public Integer getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(Integer isFinish) {
		this.isFinish = isFinish;
	}

	public String getCustomsPassCode() {
		return customsPassCode;
	}

	public void setCustomsPassCode(String customsPassCode) {
		this.customsPassCode = customsPassCode;
	}

	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	/**
	 * @return the isAccountComp
	 */
	public Integer getIsAccountComp() {
		return isAccountComp;
	}

	/**
	 * @param isAccountComp the isAccountComp to set
	 */
	public void setIsAccountComp(Integer isAccountComp) {
		this.isAccountComp = isAccountComp;
	}
	@Translation(name = "状态")
	private Integer status = 0;// 货体状态  1:已删除 2:生产运行部操作中数据
	@Translation(name = "说明")
	private String description;// 说明
	@Translation(name = "提货单位")
	private Integer ladingClientId;// 提货单位
	@Translation(name = "是否预入库")
	private Integer isPredict;//是否是预入库  1:预入库  2：预入库已关联
	
	
	// Constructors

	/** default constructor */
	public Goods() {
	}

	public Integer getIsPredict() {
		return isPredict;
	}

	public void setIsPredict(Integer isPredict) {
		this.isPredict = isPredict;
	}

	/** minimal constructor */
	public Goods(Timestamp createTime) {
		this.createTime = createTime;
	}

	/** full constructor */
	public Goods(String code, Integer type, Integer cargoId, Integer clientId, Integer sourceGoodsId, Integer rootGoodsId, Integer tankId, Timestamp createTime, String goodsTotal, String goodsIn,
			String goodsOut, String goodsCurrent, String goodsInPass, String goodsOutPass, Integer status, String description) {
		this.code = code;
		this.type = type;
		this.cargoId = cargoId;
		this.clientId = clientId;
		this.sourceGoodsId = sourceGoodsId;
		this.rootGoodsId = rootGoodsId;
		this.tankId = tankId;
		this.createTime = createTime;
		this.goodsTotal = goodsTotal;
		this.goodsIn = goodsIn;
		this.goodsOut = goodsOut;
		this.goodsCurrent = goodsCurrent;
		this.goodsInPass = goodsInPass;
		this.goodsOutPass = goodsOutPass;
		this.status = status;
		this.description = description;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public Integer getLadingClientId() {
		return ladingClientId;
	}

	public void setLadingClientId(Integer ladingClientId) {
		this.ladingClientId = ladingClientId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCargoId() {
		return this.cargoId;
	}

	public void setCargoId(Integer cargoId) {
		this.cargoId = cargoId;
	}

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getGoodsGroupId() {
		return goodsGroupId;
	}

	public void setGoodsGroupId(Integer goodsGroupId) {
		this.goodsGroupId = goodsGroupId;
	}

	public Integer getClientId() {
		return this.clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Integer getRootGoodsId() {
		return this.rootGoodsId;
	}

	public void setRootGoodsId(Integer rootGoodsId) {
		this.rootGoodsId = rootGoodsId;
	}

	public Integer getTankId() {
		return this.tankId;
	}

	public void setTankId(Integer tankId) {
		this.tankId = tankId;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getLossRate() {
		return lossRate;
	}

	public void setLossRate(String lossRate) {
		this.lossRate = lossRate;
	}

	public String getGoodsInspect() {
		return goodsInspect;
	}

	public void setGoodsInspect(String goodsInspect) {
		this.goodsInspect = goodsInspect;
	}

	public String getGoodsTank() {
		return goodsTank;
	}

	public void setGoodsTank(String goodsTank) {
		this.goodsTank = goodsTank;
	}

	public String getGoodsTotal() {
		return this.goodsTotal;
	}

	public void setGoodsTotal(String goodsTotal) {
		this.goodsTotal = goodsTotal;
	}

	public String getGoodsIn() {
		return this.goodsIn;
	}

	public void setGoodsIn(String goodsIn) {
		this.goodsIn = goodsIn;
	}

	public String getGoodsOut() {
		return this.goodsOut;
	}

	public void setGoodsOut(String goodsOut) {
		this.goodsOut = goodsOut;
	}

	public String getGoodsCurrent() {
		return this.goodsCurrent;
	}

	public void setGoodsCurrent(String goodsCurrent) {
		this.goodsCurrent = goodsCurrent;
	}

	public String getGoodsInPass() {
		return this.goodsInPass;
	}

	public void setGoodsInPass(String goodsInPass) {
		this.goodsInPass = goodsInPass;
	}

	public String getGoodsOutPass() {
		return this.goodsOutPass;
	}

	public void setGoodsOutPass(String goodsOutPass) {
		this.goodsOutPass = goodsOutPass;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSourceGoodsId() {
		return sourceGoodsId;
	}

	public void setSourceGoodsId(Integer sourceGoodsId) {
		this.sourceGoodsId = sourceGoodsId;
	}

	public String getTankCodes() {
		return tankCodes;
	}

	public void setTankCodes(String tankCodes) {
		this.tankCodes = tankCodes;
	}

}