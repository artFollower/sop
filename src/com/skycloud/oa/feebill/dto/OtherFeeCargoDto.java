/**
 * 
 */
package com.skycloud.oa.feebill.dto;

import com.skycloud.oa.annatation.Translation;

/**
 *
 * @author jiahy
 * @version 2015年10月21日 下午2:41:58
 */
public class OtherFeeCargoDto {
	private Integer id;//id
	@Translation(name = "其他费用id")
	private Integer otherFeeId;//其他费用
	@Translation(name = "货批号id")
	private Integer cargoId;
	
	public OtherFeeCargoDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OtherFeeCargoDto(Integer otherFeeId) {
		super();
		this.otherFeeId = otherFeeId;
	}
	public OtherFeeCargoDto(Integer id, Integer otherFeeId, Integer cargoId) {
		super();
		this.id = id;
		this.otherFeeId = otherFeeId;
		this.cargoId = cargoId;
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
	
}
