package com.skycloud.oa.outbound.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 * 
 * <p>出库管理-车发称重</p>
 * @ClassName:WeighBridge
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年9月18日 上午11:13:56
 *
 */
@Entity
@Table(name="t_pcs_weighbridge")
public class WeighBridge implements Cloneable 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private int id ;
	@Translation(name = "发货单号")
	private String serial ; //发货单号
	@Translation(name = "入库时间")
	private Long outTime ;//入库时间
	@Translation(name = "出库时间")
	private Long intoTime ;//出库时间
	@Translation(name = "入库重")
	private BigDecimal inWeigh ;//入库重
	@Translation(name = "出库重")
	private BigDecimal outWeigh ;//出库重
	@Translation(name = "表返重量")
	private BigDecimal measureWeigh ;//表返重量
	@Translation(name = "发运数")
	private BigDecimal deliveryNum ;//发运数
	@Translation(name = "发货口")
	private String inPort ;//发货口
	@Translation(name = "作业人id")
	private int createUserId ; //作业人id
	private int status ;//状态
	@Translation(name = "创建时间")
	private Date createTime ;//创建时间
	@Translation(name = "描述")
	private String description ;//描述
	@Translation(name = "调整后的皮重")
	private BigDecimal actualRoughWeight ;   //调整后的皮重
	@Translation(name = "调整后的毛重")
	private BigDecimal actualTareWeight ;	//调整后的毛重
	private int tankId ;
	private int type=1 ; //1 称重2计量
	private String inip;//称重主机ip
	private String hostName;//称重主机名称
	/**
	 * 进库称重人ID
	 */
	private int inStockPersonId=0;
	
	/**
	 * 出库称重人ID
	 */
	private int outStockPersonId=0;
	
	/**
	 * 克隆对象
	 * @Title:clone
	 * @Description:
	 * @return
	 * @see
	 */
	public Object clone()
	{     
		WeighBridge cb = null;     
        try 
        {     
            cb = (WeighBridge)super.clone();     
        } 
        catch (CloneNotSupportedException e) 
        {     
            e.printStackTrace();     
        }     
        
        return cb;     
    }   
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getTankId() {
		return tankId;
	}
	public void setTankId(int tankId) {
		this.tankId = tankId;
	}
	public BigDecimal getActualRoughWeight() {
		return actualRoughWeight;
	}
	public void setActualRoughWeight(BigDecimal actualRoughWeight) {
		this.actualRoughWeight = actualRoughWeight;
	}
	public BigDecimal getActualTareWeight() {
		return actualTareWeight;
	}
	public void setActualTareWeight(BigDecimal actualTareWeight) {
		this.actualTareWeight = actualTareWeight;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public Long getOutTime() {
		return outTime;
	}
	public void setOutTime(Long outTime) {
		this.outTime = outTime;
	}
	public Long getIntoTime() {
		return intoTime;
	}
	public void setIntoTime(Long intoTime) {
		this.intoTime = intoTime;
	}
	public BigDecimal getInWeigh() {
		return inWeigh;
	}
	public void setInWeigh(BigDecimal inWeigh) {
		this.inWeigh = inWeigh;
	}
	public BigDecimal getOutWeigh() {
		return outWeigh;
	}
	public void setOutWeigh(BigDecimal outWeigh) {
		this.outWeigh = outWeigh;
	}
	public BigDecimal getMeasureWeigh() {
		return measureWeigh;
	}
	public void setMeasureWeigh(BigDecimal measureWeigh) {
		this.measureWeigh = measureWeigh;
	}
	public BigDecimal getDeliveryNum() {
		return deliveryNum;
	}
	public void setDeliveryNum(BigDecimal deliveryNum) {
		this.deliveryNum = deliveryNum;
	}
	public String getInPort() {
		return inPort;
	}
	public void setInPort(String inPort) {
		this.inPort = inPort;
	}
	public int getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @get方法:int
	 * @return the inStockPersonId
	 */
	public int getInStockPersonId() {
		return inStockPersonId;
	}
	/**
	 * @set方法:int
	 * @param inStockPersonId the inStockPersonId to set
	 */
	public void setInStockPersonId(int inStockPersonId) {
		this.inStockPersonId = inStockPersonId;
	}
	/**
	 * @get方法:int
	 * @return the outStockPersonId
	 */
	public int getOutStockPersonId() {
		return outStockPersonId;
	}
	/**
	 * @set方法:int
	 * @param outStockPersonId the outStockPersonId to set
	 */
	public void setOutStockPersonId(int outStockPersonId) {
		this.outStockPersonId = outStockPersonId;
	}

	public String getInip() {
		return inip;
	}

	public void setInip(String inip) {
		this.inip = inip;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	
}
