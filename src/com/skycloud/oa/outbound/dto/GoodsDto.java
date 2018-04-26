package com.skycloud.oa.outbound.dto;

import java.util.ArrayList;
import java.util.List;

import com.skycloud.oa.annatation.Translation;

public class GoodsDto {
	private int id ;
	@Translation(name = "来源货体id")
	private int sourceGoodsId ;
	@Translation(name = "代码")
	private String code ;
	@Translation(name = "名称")
	private String name ;
	@Translation(name = "提单编号")
	private String ladingCode ;
	@Translation(name = "提单类型")
	private String ladingType ;
	@Translation(name = "货体总量")
	private String goodsTotal ;
	@Translation(name = "调入")
	private String goodsIn ;
	@Translation(name = "调出")
	private String goodsOut ;
	@Translation(name = "当前存量")
	private String goodsCurrent ;
	
	private String level ;
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	private List<GoodsDto> children = new ArrayList<GoodsDto>() ;
	public List<GoodsDto> getChildren() {
		return children;
	}
	public void setChildren(List<GoodsDto> children) {
		this.children = children;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSourceGoodsId() {
		return sourceGoodsId;
	}
	public void setSourceGoodsId(int sourceGoodsId) {
		this.sourceGoodsId = sourceGoodsId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLadingCode() {
		return ladingCode;
	}
	public void setLadingCode(String ladingCode) {
		this.ladingCode = ladingCode;
	}
	public String getLadingType() {
		return ladingType;
	}
	public void setLadingType(String ladingType) {
		this.ladingType = ladingType;
	}
	public String getGoodsTotal() {
		return goodsTotal;
	}
	public void setGoodsTotal(String goodsTotal) {
		this.goodsTotal = goodsTotal;
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
	
}
