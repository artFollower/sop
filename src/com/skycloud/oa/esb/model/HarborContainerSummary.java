package com.skycloud.oa.esb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 集装箱汇总信息
 * @ClassName: HarborContainer 
 * @Description: TODO
 * @author xie
 * @date 2015年1月22日 下午9:43:10
 */
@Entity
@Table(name="t_esb_ctn_summary")
public class HarborContainerSummary {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String recordId = "60";//记录类型标识
	private String ctnSpec;//集装箱规格
	private String ctnEmptyNum;//空箱数量
	private String ctnHeavyNum;//重箱数量
	private String danger;//是否有危险品
	private String dangerNO;//危险品申报单序列号
	
	@OneToOne
	@JoinColumn(name="portId")
	private HarborShip ship;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getCtnSpec() {
		return ctnSpec;
	}

	public void setCtnSpec(String ctnSpec) {
		this.ctnSpec = ctnSpec;
	}

	public String getCtnEmptyNum() {
		return ctnEmptyNum;
	}

	public void setCtnEmptyNum(String ctnEmptyNum) {
		this.ctnEmptyNum = ctnEmptyNum;
	}

	public String getCtnHeavyNum() {
		return ctnHeavyNum;
	}

	public void setCtnHeavyNum(String ctnHeavyNum) {
		this.ctnHeavyNum = ctnHeavyNum;
	}

	public String getDanger() {
		return danger;
	}

	public void setDanger(String danger) {
		this.danger = danger;
	}

	public String getDangerNO() {
		return dangerNO;
	}

	public void setDangerNO(String dangerNO) {
		this.dangerNO = dangerNO;
	}

	public HarborShip getShip() {
		return ship;
	}

	public void setShip(HarborShip ship) {
		this.ship = ship;
	}

}
