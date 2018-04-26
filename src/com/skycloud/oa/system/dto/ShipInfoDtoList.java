package com.skycloud.oa.system.dto;

import java.util.List;

import com.skycloud.oa.system.model.Ship;
import com.skycloud.oa.system.model.ShipRef;

public class ShipInfoDtoList {
	private Ship ship ;
	private List<ShipRef> shipRefInfoList ;
	public List<ShipRef> getShipRefInfoList() {
		return shipRefInfoList;
	}
	public void setShipRefInfoList(List<ShipRef> shipRefInfoList) {
		this.shipRefInfoList = shipRefInfoList;
	}
	public Ship getShip() {
		return ship;
	}
	public void setShip(Ship ship) {
		this.ship = ship;
	}
	
	
}
