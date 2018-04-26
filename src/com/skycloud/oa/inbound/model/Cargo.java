package com.skycloud.oa.inbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 * 货批信息表
 * 
 * @author jiahy
 *
 */
@Entity
@Table(name = "t_pcs_cargo")
public class Cargo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;// 主键，货批id，自增
	@Translation(name = "代码")
	private String code;// 货批代码，基于合同编号生成
	@Translation(name = "类型")
	private Integer type;// 货批类型，预留//混罐1包罐2
	@Translation(name = "货品id")
	private Integer productId;// 货品id
	@Translation(name = "货主")
	private Integer clientId;// 货主id
	@Translation(name = "合同")
	private Integer contractId;// 合同id
	@Translation(name = "税务类型")
	private Integer taxType;// 税务类型，1：内贸2：外贸3：保税
	@Translation(name = "货批状态")
	private Integer status;// 货批状态，0：计划进港；1：正在接卸；2：已入库 3：虚拟库 4：盘库 5：换货品货批
	@Translation(name = "仓储需求")
	private String requirement;// 仓储要求
	@Translation(name = "产地")
	private String originalArea;// 产地：东北
	@Translation(name = "货代")
	private Integer cargoAgentId;// 货代id
	@Translation(name = "商检")
	private Integer inspectAgentId;// 商检id
	@Translation(name = "开证单位")
	private Integer certifyAgentId;// 开证单位id
	@Translation(name = "批文等级")
	private Integer officeGrade;// 批文等级，0：普通；1：特B；2：特C；3：封存
	@Translation(name = "到港id")
	private Integer arrivalId;// 到港信息id
	@Translation(name = "入库信息")
	private Integer importId;// 入库信息id
	@Translation(name = "损耗率")
	private String lossRate;// 损耗率
	@Translation(name = "商检数量")
	private String goodsInspect;// 商检数量
	@Translation(name = "罐检数量")
	private String goodsTank;// 罐检数量
	@Translation(name = "入库总量")
	private String goodsTotal;// 入库总量
	@Translation(name = "计划数量")
	private String goodsPlan;//计划数量
	@Translation(name = "当前存量")
	private String goodsCurrent;// 当前存量
	@Translation(name = "放行状态")
	private Integer passStatus;// 放行状态，0：未放行；1：海关放行；2：放行
	@Translation(name = "入库放行量")
	private String goodsInPass;// 入库放行总量
	@Translation(name = "出库放行量")
	private String goodsOutPass;// 出库放行总量
	@Translation(name = "描述")
	private String description;// 描述
	@Translation(name = "船检数量")
	private String goodsShip;//船检数量

	@Translation(name = "是否有预入库货体")
	private Integer isPredict;//是否有预入库货体
	@Translation(name = "是否商检")
	private Integer isInspect;//是否商检
	
	
	@Translation(name = "文件")
	private String fileUrl;//文件url
	
	@Translation(name = "商检单位id")
	private String inspectAgentIds;//商检单位ids
	@Translation(name = "商检单位名称")
	private String inspectAgentNames;//商检单位名称s
	@Translation(name = "结算状态")
	private Integer isFinish=0;//结清状态 0-未结清，1-已结清
	
	@Translation(name = "是否申报海关")
	private Integer isDeclareCustom;//是否申报海关
	@Translation(name = "海关是否同意卸货")
	private Integer isCustomAgree;//海关是否同意卸货
	@Translation(name = "海运提单号")
	private String customLading;//海运提单号
	@Translation(name = "海运提单数量")
	private String customLadingCount;//海运提单数量
	@Translation(name = "仓储性质")
	private String storageType;//仓储性质  1：一般 2：保税临租 3：包罐 4 临租 5 通过
	@Translation(name = "报关单位")
	private Integer clearanceClientId;//报关单位；
	
	@Translation(name = "打印单号")
	private String No; //打印单号。
	@Translation(name = "联系单")
	private String connectNo;//联系单
	@Translation(name = "入库损耗单")
	private String inboundNo;//入库及损耗通知单
	@Translation(name = "通过船商检量")
	private String passShipinspect; //通过船商检量
	@Translation(name = "海关放行时间")
	private Long  customLadingTime;
	@Translation(name = "海关放行数量")
	private String customPassCount;
	
	public String getCustomPassCount() {
		return customPassCount;
	}

	public void setCustomPassCount(String customPassCount) {
		this.customPassCount = customPassCount;
	}

	public Long getCustomLadingTime() {
		return customLadingTime;
	}

	public void setCustomLadingTime(Long customLadingTime) {
		this.customLadingTime = customLadingTime;
	}

	public String getConnectNo() {
		return connectNo;
	}

	public void setConnectNo(String connectNo) {
		this.connectNo = connectNo;
	}

	public String getPassShipinspect() {
		return passShipinspect;
	}

	public void setPassShipinspect(String passShipinspect) {
		this.passShipinspect = passShipinspect;
	}

	public String getInboundNo() {
		return inboundNo;
	}

	public void setInboundNo(String inboundNo) {
		this.inboundNo = inboundNo;
	}

	public String getNo() {
		return No;
	}

	public void setNo(String no) {
		No = no;
	}

	public Integer getClearanceClientId() {
		return clearanceClientId;
	}

	public void setClearanceClientId(Integer clearanceClientId) {
		this.clearanceClientId = clearanceClientId;
	}

	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public Integer getIsDeclareCustom() {
		return isDeclareCustom;
	}

	public void setIsDeclareCustom(Integer isDeclareCustom) {
		this.isDeclareCustom = isDeclareCustom;
	}

	public Integer getIsCustomAgree() {
		return isCustomAgree;
	}

	public void setIsCustomAgree(Integer isCustomAgree) {
		this.isCustomAgree = isCustomAgree;
	}

	public String getCustomLading() {
		return customLading;
	}

	public void setCustomLading(String customLading) {
		this.customLading = customLading;
	}

	public String getCustomLadingCount() {
		return customLadingCount;
	}

	public void setCustomLadingCount(String customLadingCount) {
		this.customLadingCount = customLadingCount;
	}

	public String getInspectAgentIds() {
		return inspectAgentIds;
	}

	public void setInspectAgentIds(String inspectAgentIds) {
		this.inspectAgentIds = inspectAgentIds;
	}

	public String getInspectAgentNames() {
		return inspectAgentNames;
	}

	public void setInspectAgentNames(String inspectAgentNames) {
		this.inspectAgentNames = inspectAgentNames;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public Integer getIsInspect() {
		return isInspect;
	}

	public void setIsInspect(Integer isInspect) {
		this.isInspect = isInspect;
	}

	public Integer getIsPredict() {
		return isPredict;
	}

	public void setIsPredict(Integer isPredict) {
		this.isPredict = isPredict;
	}

	public String getGoodsShip() {
		return goodsShip;
	}

	public void setGoodsShip(String goodsShip) {
		this.goodsShip = goodsShip;
	}

	public String getGoodsPlan() {
		return goodsPlan;
	}

	public void setGoodsPlan(String goodsPlan) {
		this.goodsPlan = goodsPlan;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public Integer getTaxType() {
		return taxType;
	}

	public void setTaxType(Integer taxType) {
		this.taxType = taxType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public String getOriginalArea() {
		return originalArea;
	}

	public void setOriginalArea(String originalArea) {
		this.originalArea = originalArea;
	}

	public Integer getCargoAgentId() {
		return cargoAgentId;
	}

	public void setCargoAgentId(Integer cargoAgentId) {
		this.cargoAgentId = cargoAgentId;
	}

	public Integer getInspectAgentId() {
		return inspectAgentId;
	}

	public void setInspectAgentId(Integer inspectAgentId) {
		this.inspectAgentId = inspectAgentId;
	}

	public Integer getCertifyAgentId() {
		return certifyAgentId;
	}

	public void setCertifyAgentId(Integer certifyAgentId) {
		this.certifyAgentId = certifyAgentId;
	}

	public Integer getOfficeGrade() {
		return officeGrade;
	}

	public void setOfficeGrade(Integer officeGrade) {
		this.officeGrade = officeGrade;
	}

	public Integer getArrivalId() {
		return arrivalId;
	}

	public void setArrivalId(Integer arrivalId) {
		this.arrivalId = arrivalId;
	}

	public Integer getImportId() {
		return importId;
	}

	public void setImportId(Integer importId) {
		this.importId = importId;
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
		return goodsTotal;
	}

	public void setGoodsTotal(String goodsTotal) {
		this.goodsTotal = goodsTotal;
	}

	public String getGoodsCurrent() {
		return goodsCurrent;
	}

	public void setGoodsCurrent(String goodsCurrent) {
		this.goodsCurrent = goodsCurrent;
	}

	public Integer getPassStatus() {
		return passStatus;
	}

	public void setPassStatus(Integer passStatus) {
		this.passStatus = passStatus;
	}

	public String getGoodsInPass() {
		return goodsInPass;
	}

	public void setGoodsInPass(String goodsInPass) {
		this.goodsInPass = goodsInPass;
	}

	public String getGoodsOutPass() {
		return goodsOutPass;
	}

	public void setGoodsOutPass(String goodsOutPass) {
		this.goodsOutPass = goodsOutPass;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(Integer isFinish) {
		this.isFinish = isFinish;
	}

}