package com.skycloud.oa.inbound.dto;

import java.util.List;

import com.skycloud.oa.inbound.model.Arrival;
import com.skycloud.oa.inbound.model.ArrivalPlan;

public class InsertArrivalPlanDto {
private Arrival arrival;
private List<ArrivalPlan> arrivalPlanList;
public Arrival getArrival() {
	return arrival;
}
public void setArrival(Arrival arrival) {
	this.arrival = arrival;
}
public List<ArrivalPlan> getArrivalPlanList() {
	return arrivalPlanList;
}
public void setArrivalPlanList(List<ArrivalPlan> arrivalPlanList) {
	this.arrivalPlanList = arrivalPlanList;
}

}
