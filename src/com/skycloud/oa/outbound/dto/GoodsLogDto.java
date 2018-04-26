package com.skycloud.oa.outbound.dto;

import java.util.List;

import com.skycloud.oa.annatation.Translation;
import com.skycloud.oa.outbound.model.GoodsLog;

public class GoodsLogDto {
	private List<GoodsLog> goodsLogList ;  //
	@Translation(name = "客户id")
	private int clientId ;  //客户id
	@Translation(name = "货品id")
	private int productId ; //货品id
	@Translation(name = "发货类型")
	private Integer deliverType ;//发货类型
	@Translation(name = "车船id")
	private int vehicleShipId ;//车船id
	@Translation(name = "通知单号")
	private String serial ;//通知单号
	@Translation(name = "打印类型")
	private String printType ;//打印类型
	@Translation(name = "批次id")
	private String batchId ;//批次id
	@Translation(name = "车牌号")
	private String vehiclePlate ;//车牌号
	@Translation(name = "车传明")
	private String vsName ;//车船名
	@Translation(name = "是否是联单")
    private Integer isKupono;//是否是联单
	@Translation(name = "是否待提")
    private Integer actualType;//是否待提
	@Translation(name = "提单号")
    private String ladingEvidence;//提单号
	@Translation(name = "日志id")
    private Integer goodsLogId;// goodslog--id (将一个发货通知单拆为两个联单)
	@Translation(name = "拆分数量")	
    private Float  splitNum;//要拆的数量  (将一个发货通知单拆为两个联单)
	@Translation(name = "拆分的货体id")
    private Integer splitGoodsId;//拆分的goodsLog对应的goodsId  (将一个发货通知单拆为两个联单)
	@Translation(name = "车发id")
    private Integer trainId;
	@Translation(name = "修改时间")
    private Long  choiceTime;//修改出库时间对应的时间点
	@Translation(name = "ip地址")
	private String tip;//ip地址
	@Translation(name = "主机名称")
    private String tHostName;//主机名称
	@Translation(name = "开始时间")
    private Long startTime;
	@Translation(name = "结束时间")
    private Long endTime;
	@Translation(name = "储罐名称")
    private String tankNames;
	@Translation(name = "是否是油品")
    private Integer isOils;//是否是油品
	@Translation(name = "时间类型")
    private Integer timeType;//时间类型
	@Translation(name = "货品名称")
    private String productCode;//货品名称
	@Translation(name = "是否显示全部提单")
	private Integer zeroFlag;//是否显示全部提单
	@Translation(name = "发货状态")
	private Integer status;//发货状态
	@Translation(name = "记录状态")
	private Integer type;//goodsLog状态
	@Translation(name = "作废状态")
	private Integer cancelType;//0,开票删除，1.冲销作废
	private String cargoCode;//货批号
	/**
	 * ID
	 */
	private int id;
	
	
	public GoodsLogDto(String serial,Long choiceTime) {
		super();
		this.serial = serial;
		this.choiceTime=choiceTime;
	}
	
	
	public GoodsLogDto() {
		// TODO Auto-generated constructor stub
	}
	public String getVsName() {
		return vsName;
	}
	public void setVsName(String vsName) {
		this.vsName = vsName;
	}
	public String getVehiclePlate() {
		return vehiclePlate;
	}
	public void setVehiclePlate(String vehiclePlate) {
		this.vehiclePlate = vehiclePlate;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getPrintType() {
		return printType;
	}
	public void setPrintType(String printType) {
		this.printType = printType;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public int getVehicleShipId() {
		return vehicleShipId;
	}
	public void setVehicleShipId(int vehicleShipId) {
		this.vehicleShipId = vehicleShipId;
	}
	
	public List<GoodsLog> getGoodsLogList() {
		return goodsLogList;
	}
	public void setGoodsLogList(List<GoodsLog> goodsLogList) {
		this.goodsLogList = goodsLogList;
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
	public Integer getDeliverType() {
		return deliverType;
	}
	public void setDeliverType(Integer deliverType) {
		this.deliverType = deliverType;
	}
	/**
	 * @get方法:int
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @set方法:int
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	public Integer getIsKupono() {
		return isKupono;
	}
	public void setIsKupono(Integer isKupono) {
		this.isKupono = isKupono;
	}
	public Integer getActualType() {
		return actualType;
	}
	public void setActualType(Integer actualType) {
		this.actualType = actualType;
	}
	public Integer getGoodsLogId() {
		return goodsLogId;
	}
	public void setGoodsLogId(Integer goodsLogId) {
		this.goodsLogId = goodsLogId;
	}
	public Float getSplitNum() {
		return splitNum;
	}
	public void setSplitNum(Float splitNum) {
		this.splitNum = splitNum;
	}
	public Integer getSplitGoodsId() {
		return splitGoodsId;
	}
	public void setSplitGoodsId(Integer splitGoodsId) {
		this.splitGoodsId = splitGoodsId;
	}
	public String getLadingEvidence() {
		return ladingEvidence;
	}
	public void setLadingEvidence(String ladingEvidence) {
		this.ladingEvidence = ladingEvidence;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public String gettHostName() {
		return tHostName;
	}
	public void settHostName(String tHostName) {
		this.tHostName = tHostName;
	}

	public Long getChoiceTime() {
		return choiceTime;
	}

	public void setChoiceTime(Long choiceTime) {
		this.choiceTime = choiceTime;
	}

	public Integer getTrainId() {
		return trainId;
	}

	public void setTrainId(Integer trainId) {
		this.trainId = trainId;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getTankNames() {
		return tankNames;
	}

	public void setTankNames(String tankNames) {
		this.tankNames = tankNames;
	}

	public Integer getIsOils() {
		return isOils;
	}

	public void setIsOils(Integer isOils) {
		this.isOils = isOils;
	}

	public Integer getTimeType() {
		return timeType;
	}

	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Integer getZeroFlag() {
		return zeroFlag;
	}

	public void setZeroFlag(Integer zeroFlag) {
		this.zeroFlag = zeroFlag;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCancelType() {
		return cancelType;
	}

	public void setCancelType(Integer cancelType) {
		this.cancelType = cancelType;
	}


	public String getCargoCode() {
		return cargoCode;
	}


	public void setCargoCode(String cargoCode) {
		this.cargoCode = cargoCode;
	}
	
}
