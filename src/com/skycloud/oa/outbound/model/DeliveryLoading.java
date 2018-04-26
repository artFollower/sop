package com.skycloud.oa.outbound.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_pcs_delivery_loading")
public class DeliveryLoading implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "loadingId", unique = false, nullable = false)
	private int loadingId;
	private int deliveryId;

	public int getLoadingId() {
		return loadingId;
	}

	public void setLoadingId(int loadingId) {
		this.loadingId = loadingId;
	}

	public int getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(int deliveryId) {
		this.deliveryId = deliveryId;
	}

}
