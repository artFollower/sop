/**
 * 
 * @Project:sop
 * @Title:GoodsLogQuery.java
 * @Package:com.skycloud.oa.outbound.dto
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年9月21日 下午10:40:35
 * @Version:SkyCloud版权所有
 */
package com.skycloud.oa.outbound.dto;

/**
 * <p>查询条件拼接</p>
 * @ClassName:GoodsLogQuery
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年9月21日 下午10:40:35
 * 
 */
public class GoodsLogQuery 
{
	/**
	 * Good ID
	 */
	private int goodsId;
	
	/**
	 * 货品ID
	 */
	private int productId;
	
	/**
	 * 客户ID
	 */
	private int clientId;
	
	/**
	 * 提货单位ID
	 */
	private int ladingId;
	
	/**
	 * 1：车发，2：船发
	 */
	private int type;

	/**
	 * @get方法:int
	 * @return the goodsId
	 */
	public int getGoodsId() {
		return goodsId;
	}

	/**
	 * @set方法:int
	 * @param goodsId the goodsId to set
	 */
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	/**
	 * @get方法:int
	 * @return the productId
	 */
	public int getProductId() {
		return productId;
	}

	/**
	 * @set方法:int
	 * @param productId the productId to set
	 */
	public void setProductId(int productId) {
		this.productId = productId;
	}

	/**
	 * @get方法:int
	 * @return the clientId
	 */
	public int getClientId() {
		return clientId;
	}

	/**
	 * @set方法:int
	 * @param clientId the clientId to set
	 */
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	/**
	 * @get方法:int
	 * @return the ladingId
	 */
	public int getLadingId() {
		return ladingId;
	}

	/**
	 * @set方法:int
	 * @param ladingId the ladingId to set
	 */
	public void setLadingId(int ladingId) {
		this.ladingId = ladingId;
	}

	/**
	 * @get方法:int
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @set方法:int
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	
}
