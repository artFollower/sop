/**
 * @Title:ExceedCleanLogDto.java
 * @Package com.skycloud.oa.feebill.dto
 * @Description TODO
 * @autor jiahy
 * @date 2017年3月25日上午9:12:04
 * @version V1.0
 */
package com.skycloud.oa.feebill.dto;

/**
 * @ClassName ExceedCleanLogDto
 * @Description TODO
 * @author jiahy
 * @date 2017年3月25日上午9:12:04
 */
public class ExceedCleanLogDto {

	private int cargoId;
	private int ladingId;
	
	
	public ExceedCleanLogDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ExceedCleanLogDto(int cargoId, int ladingId) {
		super();
		this.cargoId = cargoId;
		this.ladingId = ladingId;
	}
	public int getCargoId() {
		return cargoId;
	}
	public void setCargoId(int cargoId) {
		this.cargoId = cargoId;
	}
	public int getLadingId() {
		return ladingId;
	}
	public void setLadingId(int ladingId) {
		this.ladingId = ladingId;
	}
	
	
}
