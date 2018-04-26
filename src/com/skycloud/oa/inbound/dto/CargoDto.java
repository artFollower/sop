package com.skycloud.oa.inbound.dto;

import java.util.List;

import com.skycloud.oa.annatation.Translation;
import com.skycloud.oa.inbound.model.Cargo;
import com.skycloud.oa.inbound.model.Goods;

public class CargoDto {
	@Translation(name = "货批信息")
private Cargo cargo;
	@Translation(name = "货体信息")
private List<Goods> goodsList;
public Cargo getCargo() {
	return cargo;
}
public void setCargo(Cargo cargo) {
	this.cargo = cargo;
}
public List<Goods> getGoodsList() {
	return goodsList;
}
public void setGoodsList(List<Goods> goodsList) {
	this.goodsList = goodsList;
}

}
