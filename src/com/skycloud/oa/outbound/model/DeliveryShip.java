package com.skycloud.oa.outbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_pcs_delivery_ship")
public class DeliveryShip {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id = 0;
	private int transportId = 0;
	private int tubeId = 0;
	private String tubeStatus;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTransportId() {
		return transportId;
	}

	public void setTransportId(int transportId) {
		this.transportId = transportId;
	}

	public int getTubeId() {
		return tubeId;
	}

	public void setTubeId(int tubeId) {
		this.tubeId = tubeId;
	}

	public String getTubeStatus() {
		return tubeStatus;
	}

	public void setTubeStatus(String tubeStatus) {
		this.tubeStatus = tubeStatus;
	}

}
