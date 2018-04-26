package com.skycloud.oa.inbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 货体组表
 * 
 * @author jiahy
 * 
 */
@Entity
@Table(name = "t_pcs_goods_group")
public class GoodsGroup {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer id;// 主键，资质类型id，自增
	public String code;// 货体组代码
	public Integer clientid;// 货主id
	public Integer productid;// 货品id
	public Integer ladingid;// 提单id
	private String goodsTotal;// 货体总量
	private String goodsInspect;// 商检数量
	private String goodsTank;// 罐检数量
	private String goodsIn;// 调入总量
	private String goodsOut;// 调出总量
	private String goodsCurrent;// 调出总量
	private String goodsInPass;// 入库放行总量
	private String goodsOutPass;// 出库放行总量

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

	public Integer getClientid() {
		return clientid;
	}

	public void setClientid(Integer clientid) {
		this.clientid = clientid;
	}

	public Integer getProductid() {
		return productid;
	}

	public void setProductid(Integer productid) {
		this.productid = productid;
	}

	public Integer getLadingid() {
		return ladingid;
	}

	public void setLadingid(Integer ladingid) {
		this.ladingid = ladingid;
	}

	public String getGoodsTotal() {
		return goodsTotal;
	}

	public void setGoodsTotal(String goodsTotal) {
		this.goodsTotal = goodsTotal;
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

	public String getGoodsIn() {
		return goodsIn;
	}

	public void setGoodsIn(String goodsIn) {
		this.goodsIn = goodsIn;
	}

	public String getGoodsOut() {
		return goodsOut;
	}

	public void setGoodsOut(String goodsOut) {
		this.goodsOut = goodsOut;
	}

	public String getGoodsCurrent() {
		return goodsCurrent;
	}

	public void setGoodsCurrent(String goodsCurrent) {
		this.goodsCurrent = goodsCurrent;
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

}
