package com.skycloud.oa.system.dto;

public class TankMeasureDto {
	private Integer id;//主键，
    private Integer tankId;//储罐id
    private Integer productId;//货品id
private String letter;
	
	public String getLetter() {
		return letter;
	}
	public void setLetter(String letter) {
		this.letter = letter;
	}
	public TankMeasureDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TankMeasureDto(Integer tankId) {
		super();
		this.tankId = tankId;
	}

	public TankMeasureDto(Integer id, Integer tankId, Integer productId) {
		super();
		this.id = id;
		this.tankId = tankId;
		this.productId = productId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTankId() {
		return tankId;
	}
	public void setTankId(Integer tankId) {
		this.tankId = tankId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
    
    
    
}
