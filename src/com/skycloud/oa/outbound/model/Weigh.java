package com.skycloud.oa.outbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * <p>称重</p>
 * @ClassName:Weigh
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年9月18日 上午11:15:22
 *
 */
@Entity
@Table(name="t_pcs_weigh")
public class Weigh 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id ;//id
	private int plateId ;//车牌id
	private String roughWeight ;//毛重
	private String tare ;//皮重
	private String netWeight ;//净重
	private int tankId ;//罐id
	private int parkId ;//车位id
	private String trainId ;//车发id
	
	public String getTrainId() {
		return trainId;
	}
	public void setTrainId(String trainId) {
		this.trainId = trainId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPlateId() {
		return plateId;
	}
	public void setPlateId(int plateId) {
		this.plateId = plateId;
	}
	public String getRoughWeight() {
		return roughWeight;
	}
	public void setRoughWeight(String roughWeight) {
		this.roughWeight = roughWeight;
	}
	public String getTare() {
		return tare;
	}
	public void setTare(String tare) {
		this.tare = tare;
	}
	public String getNetWeight() {
		return netWeight;
	}
	public void setNetWeight(String netWeight) {
		this.netWeight = netWeight;
	}
	public int getTankId() {
		return tankId;
	}
	public void setTankId(int tankId) {
		this.tankId = tankId;
	}
	public int getParkId() {
		return parkId;
	}
	public void setParkId(int parkId) {
		this.parkId = parkId;
	}
}
