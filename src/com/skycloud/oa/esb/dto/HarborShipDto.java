package com.skycloud.oa.esb.dto;

import java.util.List;

import com.skycloud.oa.esb.model.HarborShip;
import com.skycloud.oa.esb.model.MessageHead;

public class HarborShipDto {
	
	private HarborShip harborShip;
	private MessageHead messageHead;
	
	private List<BulkDto> discharges;

	public HarborShip getHarborShip() {
		return harborShip;
	}

	public void setHarborShip(HarborShip harborShip) {
		this.harborShip = harborShip;
	}

	public MessageHead getMessageHead() {
		return messageHead;
	}

	public void setMessageHead(MessageHead messageHead) {
		this.messageHead = messageHead;
	}

	public List<BulkDto> getDischarges() {
		return discharges;
	}

	public void setDischarges(List<BulkDto> discharges) {
		this.discharges = discharges;
	}

}
