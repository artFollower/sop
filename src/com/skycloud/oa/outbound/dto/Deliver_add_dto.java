package com.skycloud.oa.outbound.dto;

import java.util.List;

import com.skycloud.oa.outbound.model.DeliverInvoice;
import com.skycloud.oa.outbound.model.DeliverPlan;

public class Deliver_add_dto {
	private DeliverPlan deliverPlan ;
	private List<DeliverInvoice> deliverInvoiceList ;
	public DeliverPlan getDeliverPlan() {
		return deliverPlan;
	}
	public void setDeliverPlan(DeliverPlan deliverPlan) {
		this.deliverPlan = deliverPlan;
	}
	public List<DeliverInvoice> getDeliverInvoiceList() {
		return deliverInvoiceList;
	}
	public void setDeliverInvoiceList(List<DeliverInvoice> deliverInvoiceList) {
		this.deliverInvoiceList = deliverInvoiceList;
	}
}
