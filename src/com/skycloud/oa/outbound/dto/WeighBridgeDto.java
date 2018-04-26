/**
 * 
 * @Project:sop
 * @Title:WeighBridgeDto.java
 * @Package:com.skycloud.oa.outbound.dto
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年7月9日 下午3:21:22
 * @Version:SkyCloud版权所有
 */
package com.skycloud.oa.outbound.dto;

/**
 * <p>地磅信息</p>
 * @ClassName:WeighBridgeDto
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年7月9日 下午3:21:22
 * 
 */
public class WeighBridgeDto 
{
	/**
	 * 用户ID
	 */
	public String userId;
	
	/**
	 * 选中的IP地址
	 */
	public String selectIp;
	
	/**
	 * 未选中的IP地址
	 */
	public String notSelectIp;
	
	/**
	 * 罐号
	 */
	public int tankId;
	
	/**
	 * 发运口
	 */
	public String inPort;
	
	/**
	 * 货品ID
	 */
	public int productId;
	
	/**
	 * 颜色值
	 */
	public String fontColor;
	
	/**
	 * 发运数
	 */
	public String deliveryNum;
	
	/**
	 * 入库重
	 */
	public String inWeigh;
	
	/**
	 * 出库重
	 */
	public String outWeigh;
	
	/**
	 * 开单号
	 */
	public String serial;//通知单号
	
	public String ticketNo;//通知单号
	
    public String deliverType;//车发，船发
    
    public Integer oils;//油品化学品
    
    public Integer status;//状态
    
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSelectIp() {
		return selectIp;
	}

	public void setSelectIp(String selectIp) {
		this.selectIp = selectIp;
	}

	public String getNotSelectIp() {
		return notSelectIp;
	}

	public void setNotSelectIp(String notSelectIp) {
		this.notSelectIp = notSelectIp;
	}

	public int getTankId() {
		return tankId;
	}

	public void setTankId(int tankId) {
		this.tankId = tankId;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getInPort() {
		return inPort;
	}

	public void setInPort(String inPort) {
		this.inPort = inPort;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getFontColor() {
		return fontColor;
	}

	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	public String getDeliveryNum() {
		return deliveryNum;
	}

	public void setDeliveryNum(String deliveryNum) {
		this.deliveryNum = deliveryNum;
	}

	public String getInWeigh() {
		return inWeigh;
	}

	public void setInWeigh(String inWeigh) {
		this.inWeigh = inWeigh;
	}

	public String getOutWeigh() {
		return outWeigh;
	}

	public void setOutWeigh(String outWeigh) {
		this.outWeigh = outWeigh;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getDeliverType() {
		return deliverType;
	}

	public void setDeliverType(String deliverType) {
		this.deliverType = deliverType;
	}

	public Integer getOils() {
		return oils;
	}

	public void setOils(Integer oils) {
		this.oils = oils;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
