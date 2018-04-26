package com.skycloud.oa.esb.dto;

/**
 * 集装箱姐卸货报文传值对象
 * @ClassName: LoadDto 
 * @Description: TODO
 * @author xie
 * @date 2015年1月24日 下午3:36:23
 */
public class ContainerDto {
	
	private int id;
	
	private String ctnNO;//箱号
	private String ctnSizeType;//集装箱尺寸类型
	private String ctnOperator;//集装箱经营人
	private String ctnStatus;//集装箱状态
	private String bl;//提单号
	private String seal;//铅封号
	private String location;//船舶泊位
	
	private String cargoName;//货物名称
	private String danger;//危险品标志
	private String dangerNO;//危险品申报单
	private String grossWeight;//净重
	private String transnateFlag;//中转标志
	private String ifcsumType;//内外贸标志
	private String lastShipName;//一程船名
	private String lastCoyage;//一程船航次
	private String lastWorkTime;//一程船作业时间
	
	private String ctnSpec;//箱号
	private String ctnEmptyNum;//集装箱尺寸类型
	private String ctnHeavyNum;//集装箱经营人代码
	
	private String typeCode;//残损类型代码
	private String type;//残损类型
	private String argeCode;//残损范围代码
	private String arge;//残损范围
	private String severity;//残损程度

	private String dischargePortCode;//卸货港代码
	private String dischargePort;//卸货港
	private String loadPortCode;//装货港代码
	private String loadPort;//装货港
	private String deliveryCode;//交货地代码
	private String delivery;//交货地
	
	private String catogary;//分类：集装箱/散货

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCtnNO() {
		return ctnNO;
	}

	public void setCtnNO(String ctnNO) {
		this.ctnNO = ctnNO;
	}

	public String getCtnSizeType() {
		return ctnSizeType;
	}

	public void setCtnSizeType(String ctnSizeType) {
		this.ctnSizeType = ctnSizeType;
	}

	public String getCtnOperator() {
		return ctnOperator;
	}

	public void setCtnOperator(String ctnOperator) {
		this.ctnOperator = ctnOperator;
	}

	public String getCtnStatus() {
		return ctnStatus;
	}

	public void setCtnStatus(String ctnStatus) {
		this.ctnStatus = ctnStatus;
	}

	public String getBl() {
		return bl;
	}

	public void setBl(String bl) {
		this.bl = bl;
	}

	public String getSeal() {
		return seal;
	}

	public void setSeal(String seal) {
		this.seal = seal;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCargoName() {
		return cargoName;
	}

	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
	}

	public String getDanger() {
		return danger;
	}

	public void setDanger(String danger) {
		this.danger = danger;
	}

	public String getDangerNO() {
		return dangerNO;
	}

	public void setDangerNO(String dangerNO) {
		this.dangerNO = dangerNO;
	}

	public String getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}

	public String getTransnateFlag() {
		return transnateFlag;
	}

	public void setTransnateFlag(String transnateFlag) {
		this.transnateFlag = transnateFlag;
	}

	public String getIfcsumType() {
		return ifcsumType;
	}

	public void setIfcsumType(String ifcsumType) {
		this.ifcsumType = ifcsumType;
	}

	public String getLastShipName() {
		return lastShipName;
	}

	public void setLastShipName(String lastShipName) {
		this.lastShipName = lastShipName;
	}

	public String getLastCoyage() {
		return lastCoyage;
	}

	public void setLastCoyage(String lastCoyage) {
		this.lastCoyage = lastCoyage;
	}

	public String getLastWorkTime() {
		return lastWorkTime;
	}

	public void setLastWorkTime(String lastWorkTime) {
		this.lastWorkTime = lastWorkTime;
	}

	public String getCtnSpec() {
		return ctnSpec;
	}

	public void setCtnSpec(String ctnSpec) {
		this.ctnSpec = ctnSpec;
	}

	public String getCtnEmptyNum() {
		return ctnEmptyNum;
	}

	public void setCtnEmptyNum(String ctnEmptyNum) {
		this.ctnEmptyNum = ctnEmptyNum;
	}

	public String getCtnHeavyNum() {
		return ctnHeavyNum;
	}

	public void setCtnHeavyNum(String ctnHeavyNum) {
		this.ctnHeavyNum = ctnHeavyNum;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getArgeCode() {
		return argeCode;
	}

	public void setArgeCode(String argeCode) {
		this.argeCode = argeCode;
	}

	public String getArge() {
		return arge;
	}

	public void setArge(String arge) {
		this.arge = arge;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getDischargePortCode() {
		return dischargePortCode;
	}

	public void setDischargePortCode(String dischargePortCode) {
		this.dischargePortCode = dischargePortCode;
	}

	public String getDischargePort() {
		return dischargePort;
	}

	public void setDischargePort(String dischargePort) {
		this.dischargePort = dischargePort;
	}

	public String getLoadPortCode() {
		return loadPortCode;
	}

	public void setLoadPortCode(String loadPortCode) {
		this.loadPortCode = loadPortCode;
	}

	public String getLoadPort() {
		return loadPort;
	}

	public void setLoadPort(String loadPort) {
		this.loadPort = loadPort;
	}

	public String getDeliveryCode() {
		return deliveryCode;
	}

	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public String getCatogary() {
		return catogary;
	}

	public void setCatogary(String catogary) {
		this.catogary = catogary;
	}
	
}
