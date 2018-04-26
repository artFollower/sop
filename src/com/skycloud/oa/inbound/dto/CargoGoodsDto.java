package com.skycloud.oa.inbound.dto;

import com.skycloud.oa.annatation.Translation;

/**
 * 货批货体信息
 * @author jiahy
 *
 */
public class CargoGoodsDto {
	@Translation(name = "货批id")
private Integer cargoId;//货批id
	@Translation(name = "货主id")
private Integer clientId;//货主id
	@Translation(name = "货品id")
private Integer productId;//货品id
	@Translation(name = "货批代码")
private String arrivalcode;//货批代码
	@Translation(name = "货主名称")
private String clientName;//货主名称
	@Translation(name = "货体id")
private Integer goodsId;//货体id
	@Translation(name = "到港id")
private Integer arrivalId;//到港id
	@Translation(name = "货体组id")
private Integer goodsGroupId;//货体组id
	@Translation(name = "货体id")
private String  goodsIds;// 1,2,3,4, 货体的拼接字符串（提单使用）
	@Translation(name = "类型")
private int ladingType=0;//是否显示是提单的货体1,搜索货主拥有提货权的自己的货体ladingclientid=0或null（货体专卖）2.搜索货主拥有提货权的货体（包括自己和他人）（提货权专卖）
	@Translation(name = "是否是原始货体")
	private boolean isTankGoods=false;//是否显示是初始入库的货体
//private String goodsGroupIds;//1,2,3,4, 货体组的拼接字符串（提单使用）
//private String[] itemGoodsGroups;//   itemGoodsGroups=new Stirng[]{"1","1,2,3"}  货体组里的部分货体（提单使用） 
	@Translation(name = "提单id")
private Integer ladingId;
	@Translation(name = "到港状态")
private Integer arrivalStatus;
	@Translation(name = "来源货体id")
private Integer sourceGoodsId;//来源货体id
	@Translation(name = "是否预入库")
private Integer isPredict;//预入库
	@Translation(name = "货体状态")
private String goodsStatus;//货体状态   1：已删除  2：生产运行部的货体
	@Translation(name = "代码")
private String code;
	@Translation(name = "船舶id")
private Integer shipId;
	@Translation(name = "货批状态")
private Integer cargoStatus;
	@Translation(name = "是否查询全部")
private Integer isAll;//是否查询全部    0：是  1：只查有存量的
	@Translation(name = "类型")
private Integer type ;  //1 不查虚拟库  2  查询虚拟库等没有到港时间的货
	@Translation(name = "罐号id")
private Integer tankId; //罐号
	@Translation(name = "到港时间")
private String startTime;//到港时间
	@Translation(name = "到港结束时间")
private String endTime;//到港时间
	@Translation(name = "贸易类型")
private String taxType;//贸易类型 1：内贸 2：外贸 3：保税

private String passStatus;//放行状态  1：全部放行2：部分放行3：未放行


public String getPassStatus() {
	return passStatus;
}
public void setPassStatus(String passStatus) {
	this.passStatus = passStatus;
}
public String getTaxType() {
		return taxType;
	}
	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}
public String getStartTime() {
	return startTime;
}
public void setStartTime(String startTime) {
	this.startTime = startTime;
}
public String getEndTime() {
	return endTime;
}
public void setEndTime(String endTime) {
	this.endTime = endTime;
}
public Integer getTankId() {
	return tankId;
}
public void setTankId(Integer tankId) {
	this.tankId = tankId;
}
public Integer getType() {
	return type;
}
public void setType(Integer type) {
	this.type = type;
}
public Integer getIsAll() {
	return isAll;
}
public void setIsAll(Integer isAll) {
	this.isAll = isAll;
}
public Integer getCargoStatus() {
	return cargoStatus;
}
public void setCargoStatus(Integer cargoStatus) {
	this.cargoStatus = cargoStatus;
}
public Integer getShipId() {
	return shipId;
}
public void setShipId(Integer shipId) {
	this.shipId = shipId;
}
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}
public String getGoodsStatus() {
	return goodsStatus;
}
public void setGoodsStatus(String goodsStatus) {
	this.goodsStatus = goodsStatus;
}
public Integer getIsPredict() {
	return isPredict;
}
public void setIsPredict(Integer isPredict) {
	this.isPredict = isPredict;
}
public Integer getSourceGoodsId() {
	return sourceGoodsId;
}
public void setSourceGoodsId(Integer sourceGoodsId) {
	this.sourceGoodsId = sourceGoodsId;
}
public Integer getArrivalStatus() {
	return arrivalStatus;
}
public void setArrivalStatus(Integer arrivalStatus) {
	this.arrivalStatus = arrivalStatus;
}
public Integer getLadingId() {
	return ladingId;
}
public void setLadingId(Integer ladingId) {
	this.ladingId = ladingId;
}
public Integer getArrivalId() {
	return arrivalId;
}
public String getClientName() {
	return clientName;
}
public void setClientName(String clientName) {
	this.clientName = clientName;
}
public void setArrivalId(Integer arrivalId) {
	this.arrivalId = arrivalId;
}
public Integer getCargoId() {
	return cargoId;
}
public void setCargoId(Integer cargoId) {
	this.cargoId = cargoId;
}
public String getArrivalcode() {
	return arrivalcode;
}
public void setArrivalcode(String arrivalcode) {
	this.arrivalcode = arrivalcode;
}
public Integer getClientId() {
	return clientId;
}
public void setClientId(Integer clientId) {
	this.clientId = clientId;
}
public Integer getGoodsId() {
	return goodsId;
}
public void setGoodsId(Integer goodsId) {
	this.goodsId = goodsId;
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
public String getGoodsIds() {
	return goodsIds;
}
public void setGoodsIds(String goodsIds) {
	this.goodsIds = goodsIds;
}

public int getLadingType() {
	return ladingType;
}
public void setLadingType(int ladingType) {
	this.ladingType = ladingType;
}
public boolean isTankGoods() {
	return isTankGoods;
}
public void setTankGoods(boolean isTankGoods) {
	this.isTankGoods = isTankGoods;
}

//public String getGoodsGroupIds() {
//	return goodsGroupIds;
//}
//public void setGoodsGroupIds(String goodsGroupIds) {
//	this.goodsGroupIds = goodsGroupIds;
//}
//public String[] getItemGoodsGroups() {
//	return itemGoodsGroups;
//}
//public void setItemGoodsGroups(String[] itemGoodsGroups) {
//	this.itemGoodsGroups = itemGoodsGroups;
//}
	
	
	
}
