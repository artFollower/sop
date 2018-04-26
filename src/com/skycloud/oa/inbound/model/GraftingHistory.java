package com.skycloud.oa.inbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 * 到港信息
 * @author jiahy
 *
 */
@Entity
@Table(name = "t_pcs_grafting_history")
public class GraftingHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Translation(name = "类型")
	private int type; //类型 1:整批2:部分
	@Translation(name = "创建时间")
	private Long createTime;//创建时间
	@Translation(name = "转出方")
	private int outClientId;//转出方
	@Translation(name = "转入方")
	private int inClientId;//转入方
	@Translation(name = "转入方货体")
	private String inGoodsCode;//转入方货体
	@Translation(name = "转出方货体")
	private String outGoodsCode;//转出方货体
	@Translation(name = "转入方流转图")
	private String inLZ;//转入方流转图json
	@Translation(name = "转出方流转图")
	private String outLZ;//转出方流转图json
	@Translation(name = "嫁接数量")
	private String count;//嫁接数量
	@Translation(name = "转入方货体")
	private int inGoodsId;//转入方goodsid
	@Translation(name = "转出方货体")
	private int outGoodsId;//转出方goodsid
	@Translation(name = "嫁接货品")
	private String productName;//嫁接货品
	@Translation(name = "转出货批")
	private String outCargoCode;//转出货批code
	@Translation(name = "转入货批")
	private String inCargoCode;//转入货批code
	
	
	

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getOutCargoCode() {
		return outCargoCode;
	}

	public void setOutCargoCode(String outCargoCode) {
		this.outCargoCode = outCargoCode;
	}

	public String getInCargoCode() {
		return inCargoCode;
	}

	public void setInCargoCode(String inCargoCode) {
		this.inCargoCode = inCargoCode;
	}

	public int getInGoodsId() {
		return inGoodsId;
	}

	public void setInGoodsId(int inGoodsId) {
		this.inGoodsId = inGoodsId;
	}

	public int getOutGoodsId() {
		return outGoodsId;
	}

	public void setOutGoodsId(int outGoodsId) {
		this.outGoodsId = outGoodsId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public int getOutClientId() {
		return outClientId;
	}

	public void setOutClientId(int outClientId) {
		this.outClientId = outClientId;
	}

	public int getInClientId() {
		return inClientId;
	}

	public void setInClientId(int inClientId) {
		this.inClientId = inClientId;
	}



	public String getInGoodsCode() {
		return inGoodsCode;
	}

	public void setInGoodsCode(String inGoodsCode) {
		this.inGoodsCode = inGoodsCode;
	}

	public String getOutGoodsCode() {
		return outGoodsCode;
	}

	public void setOutGoodsCode(String outGoodsCode) {
		this.outGoodsCode = outGoodsCode;
	}

	public String getInLZ() {
		return inLZ;
	}

	public void setInLZ(String inLZ) {
		this.inLZ = inLZ;
	}

	public String getOutLZ() {
		return outLZ;
	}

	public void setOutLZ(String outLZ) {
		this.outLZ = outLZ;
	}



	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public GraftingHistory() {
		super();
		// TODO Auto-generated constructor stub
	}


}