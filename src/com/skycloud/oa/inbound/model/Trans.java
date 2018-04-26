package com.skycloud.oa.inbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 * 传输管线
 * 
 * @author jiahy
 *
 */
@Entity
@Table(name = "t_pcs_trans")
public class Trans {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Translation(name = "储罐id")
	private Integer tankId;
	@Translation(name = "管线id")
	private Integer tubeId;
	private Integer transportId;// 传输方案id
	@Translation(name = "管线类型")
	private Integer tubeType;
	@Translation(name = "父节点")
	private Integer parentId;
	@Translation(name = "工作内容")
	private String workContent;
	@Translation(name = "创建人Id")
	private Integer workUserId;
	@Translation(name = "创建时间")
	private Long workTime;
	@Translation(name = "备注")
	private String description;

	public Trans() {
	}

	public Trans(Integer id, Integer tankId, Integer tubeId, Integer tubeType,
			Integer parentId, String workContent, Integer workUserId,
			Long workTime, String description) {
		super();
		this.id = id;
		this.tubeId = tubeId;
		this.tubeType = tubeType;
		this.workContent = workContent;
		this.workUserId = workUserId;
		this.workTime = workTime;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getTankId() {
		return tankId;
	}

	public void setTankId(Integer tankId) {
		this.tankId = tankId;
	}

	public Integer getTransportId() {
		return transportId;
	}

	public void setTransportId(Integer transportId) {
		this.transportId = transportId;
	}

	public Integer getTubeId() {
		return tubeId;
	}

	public void setTubeId(Integer tubeId) {
		this.tubeId = tubeId;
	}

	public Integer getTubeType() {
		return tubeType;
	}

	public void setTubeType(Integer tubeType) {
		this.tubeType = tubeType;
	}


	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}


	public String getWorkContent() {
		return workContent;
	}

	public void setWorkContent(String workContent) {
		this.workContent = workContent;
	}

	public Integer getWorkUserId() {
		return workUserId;
	}

	public void setWorkUserId(Integer workUserId) {
		this.workUserId = workUserId;
	}

	public Long getWorkTime() {
		return workTime;
	}

	public void setWorkTime(Long workTime) {
		this.workTime = workTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}