package com.skycloud.oa.outbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_pcs_invoice")
public class DeliverInvoice implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private double amount;
	private char status = '0';
	private int deliveryPlanId;
	private int ladingId;

	public int getLadingId() {
		return ladingId;
	}

	public void setLadingId(int ladingId) {
		this.ladingId = ladingId;
	}

	public int getDeliveryPlanId() {
		return deliveryPlanId;
	}

	public void setDeliveryPlanId(int deliveryPlanId) {
		this.deliveryPlanId = deliveryPlanId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

}
