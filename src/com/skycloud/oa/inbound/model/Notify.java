package com.skycloud.oa.inbound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 *通知单
 * @author 作者:jiahy
 * @version 时间：2015年3月11日 下午4:25:07
 */
@Entity
@Table(name = "t_pcs_notify")
public class Notify {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Translation(name = "通知单编号")
	private String code;//编号   TZD140102001 当天的第1个通知单
	@Translation(name = "通知单类型")
	private Integer type;//类型 0, 配管作业通知单1,苯加热作业通知单2,打循环作业通知单(码头)3,打循环作业通知单(库区)4,管线清洗作业通知单(码头)5,管线清洗作业通知单(库区)
	                     //6,扫线作业通知单(码头)7,扫线作业通知单(库区)8,清罐作业通知单9,储罐放水作业通知单10,储罐开人孔作业通知单11,转输作业通知单 12.打回流13码头接卸14动力班接卸  
							//15,船发码头作业16,船发操作班作业17车发换罐
	@Translation(name = "内容类型")
	private Integer contentType;//通知单类型 0，确认需求1,作业前2作业中3，作业后	
	@Translation(name = "作业内容")
	private String content;//作业内容
	@Translation(name = "通知单状态")
	private Integer  state;// 通知单状态，  0--未发布 1--已发布 2--作业前完成3--作业中完成4--已完成 
	private Integer status;//1,保存2，提交
	private Integer flag;//0,1.调度与操作工处理选项2.修改人员
	@Translation(name = "创建人id")
	private Integer createUserId;//创建人
	@Translation(name = "创建时间")
	private Long createTime;//创建时间
	@Translation(name = "确认人Id")
	private Integer sureUserId;//确认人
	@Translation(name = "确认时间")
	private Long sureTime;//确认时间
	@Translation(name = "船发id")
	private Integer batchId ; //车发船发批次id
	@Translation(name = "备注")
	private String description;//备注描述
	@Translation(name = "工艺流程")
	private String firPFDSvg;//工艺流程svg代码
	@Translation(name = "工艺流程")
	private String secPFDSvg;//工艺流程svg代码
	public Notify() {
		super();
	}
	public Notify(Integer id, String code, Integer type, Integer contentType,
			String content, Integer state, Integer status, Integer flag,
			Integer createUserId, Long createTime, Integer sureUserId,
			Long sureTime, Integer batchId, String description,
			String firPFDSvg, String secPFDSvg) {
		super();
		this.id = id;
		this.code = code;
		this.type = type;
		this.contentType = contentType;
		this.content = content;
		this.state = state;
		this.status = status;
		this.flag = flag;
		this.createUserId = createUserId;
		this.createTime = createTime;
		this.sureUserId = sureUserId;
		this.sureTime = sureTime;
		this.batchId = batchId;
		this.description = description;
		this.firPFDSvg = firPFDSvg;
		this.secPFDSvg = secPFDSvg;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getContentType() {
		return contentType;
	}
	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
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
	public Integer getSureUserId() {
		return sureUserId;
	}
	public void setSureUserId(Integer sureUserId) {
		this.sureUserId = sureUserId;
	}
	public Long getSureTime() {
		return sureTime;
	}
	public void setSureTime(Long sureTime) {
		this.sureTime = sureTime;
	}
	public Integer getBatchId() {
		return batchId;
	}
	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFirPFDSvg() {
		return firPFDSvg;
	}
	public void setFirPFDSvg(String firPFDSvg) {
		this.firPFDSvg = firPFDSvg;
	}
	public String getSecPFDSvg() {
		return secPFDSvg;
	}
	public void setSecPFDSvg(String secPFDSvg) {
		this.secPFDSvg = secPFDSvg;
	}
	
}
