/**
 * 
 */
package com.skycloud.oa.feebill.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 *仓储注册费每个货批信息
 * @author jiahy
 * @version 2015年10月21日 下午1:27:08
 */
@Entity
@Table(name = "t_pcs_other_fee_cargo")
public class OtherFeeCargo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;//id
	@Translation(name = "其他费id")
	private Integer otherFeeId;//其他费用
	@Translation(name = "货批id")
	private Integer cargoId;
	@Translation(name = "数量")
	private String count;//数量
	@Translation(name = "金额")
	private String itemFee;//金额
	
	public OtherFeeCargo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OtherFeeCargo(Integer id, Integer otherFeeId, Integer cargoId, String count, String itemFee) {
		super();
		this.id = id;
		this.otherFeeId = otherFeeId;
		this.cargoId = cargoId;
		this.count = count;
		this.itemFee = itemFee;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOtherFeeId() {
		return otherFeeId;
	}

	public void setOtherFeeId(Integer otherFeeId) {
		this.otherFeeId = otherFeeId;
	}

	public Integer getCargoId() {
		return cargoId;
	}

	public void setCargoId(Integer cargoId) {
		this.cargoId = cargoId;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getItemFee() {
		return itemFee;
	}

	public void setItemFee(String itemFee) {
		this.itemFee = itemFee;
	}

	
	
}
