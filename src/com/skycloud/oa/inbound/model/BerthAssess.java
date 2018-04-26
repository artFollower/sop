package com.skycloud.oa.inbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 *  靠泊评估
 * @author jiahy
 *
 */
@Entity
@Table(name = "t_pcs_berth_assess")
public class BerthAssess{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Translation(name = "到港id")
    private Integer arrivalId;
	@Translation(name = "天气")
    private String weather;
	@Translation(name = "风向")
    private String windDirection;
	@Translation(name = "风力")
    private String windPower;
	@Translation(name = "安全措施")
    private String security;//安全措施
	@Translation(name = "原因")
    private String reason;
	@Translation(name = "创建人id")
    private Integer createUserId;
	@Translation(name = "创建时间")
    private Long createTime;
	@Translation(name = "备注")
    private String comment;
	@Translation(name = "审核人id")
    private Integer reviewUserId;
	@Translation(name = "审核时间")
    private Long reviewTime;
    private Integer reviewStatus;//0,添加，保存，1.提交审核，2，审核通过，3，审核驳回
	public BerthAssess() {
	}
	public BerthAssess(Integer id, Integer arrivalId, String weather,
			String windDirection, String windPower, String security,
			String reason, Integer createUserId, Long createTime,
			String comment, Integer reviewUserId, Long reviewTime,
			Integer reviewStatus) {
		super();
		this.id = id;
		this.arrivalId = arrivalId;
		this.weather = weather;
		this.windDirection = windDirection;
		this.windPower = windPower;
		this.security = security;
		this.reason = reason;
		this.createUserId = createUserId;
		this.createTime = createTime;
		this.comment = comment;
		this.reviewUserId = reviewUserId;
		this.reviewTime = reviewTime;
		this.reviewStatus = reviewStatus;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getArrivalId() {
		return arrivalId;
	}
	public void setArrivalId(Integer arrivalId) {
		this.arrivalId = arrivalId;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getWindDirection() {
		return windDirection;
	}
	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}
	public String getWindPower() {
		return windPower;
	}
	public void setWindPower(String windPower) {
		this.windPower = windPower;
	}
	public String getSecurity() {
		return security;
	}
	public void setSecurity(String security) {
		this.security = security;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getReviewUserId() {
		return reviewUserId;
	}
	public void setReviewUserId(Integer reviewUserId) {
		this.reviewUserId = reviewUserId;
	}
	public Long getReviewTime() {
		return reviewTime;
	}
	public void setReviewTime(Long reviewTime) {
		this.reviewTime = reviewTime;
	}
	public Integer getReviewStatus() {
		return reviewStatus;
	}
	public void setReviewStatus(Integer reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

}