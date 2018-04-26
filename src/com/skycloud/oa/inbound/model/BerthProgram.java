package com.skycloud.oa.inbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 * 靠泊方案
 * @author jiahy
 *
 */
@Entity
@Table(name = "t_pcs_berth_program")
public class BerthProgram{


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Translation(name = "靠泊方案id")
     private Integer id;
	@Translation(name = "泊位id")
     private Integer berthId;
	@Translation(name = "安全措施")
     private String safeInfo;
	@Translation(name = "审批意见")
     private String comment;
	@Translation(name = "富裕水深")
     private Float richDraught;//富裕水深
	@Translation(name = "现场风力")
    private String windPower;//现场风力
	@Translation(name = "审核人id")
     private Integer reviewUserId;
	@Translation(name = "审核时间")
     private Long reviewTime;
	@Translation(name = "创建人id")
     private Integer createUserId;
	@Translation(name = "创建日期")
     private Long createTime;
	@Translation(name = "到港id")
     private Integer arrivalId;
	@Translation(name = "状态")
     private Integer status ;
     

    // Constructors

    /** default constructor */
    public BerthProgram() {
    }


	public BerthProgram(Integer id, Integer berthId, String safeInfo,
			String comment, Float richDraught, String windPower,
			Integer reviewUserId, Long reviewTime, Integer createUserId,
			Long createTime, Integer arrivalId, Integer status) {
		super();
		this.id = id;
		this.berthId = berthId;
		this.safeInfo = safeInfo;
		this.comment = comment;
		this.richDraught = richDraught;
		this.windPower = windPower;
		this.reviewUserId = reviewUserId;
		this.reviewTime = reviewTime;
		this.createUserId = createUserId;
		this.createTime = createTime;
		this.arrivalId = arrivalId;
		this.status = status;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getBerthId() {
		return berthId;
	}


	public void setBerthId(Integer berthId) {
		this.berthId = berthId;
	}


	public String getSafeInfo() {
		return safeInfo;
	}


	public void setSafeInfo(String safeInfo) {
		this.safeInfo = safeInfo;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public Float getRichDraught() {
		return richDraught;
	}


	public void setRichDraught(Float richDraught) {
		this.richDraught = richDraught;
	}


	public String getWindPower() {
		return windPower;
	}


	public void setWindPower(String windPower) {
		this.windPower = windPower;
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


	public Integer getArrivalId() {
		return arrivalId;
	}


	public void setArrivalId(Integer arrivalId) {
		this.arrivalId = arrivalId;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}
    


}