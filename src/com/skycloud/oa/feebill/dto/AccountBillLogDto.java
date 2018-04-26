/**
 * 
 */
package com.skycloud.oa.feebill.dto;

import com.skycloud.oa.annatation.Translation;

/**
 *
 * @author jiahy
 * @version 2015年6月17日 下午9:59:48
 */
public class AccountBillLogDto {
	private Integer id;
	@Translation(name = "账单id")
	private Integer feebillId;//账单id
	@Translation(name = "码头规费账单id")
	private Integer dockfeebillId;
	@Translation(name = "到账确认人id")
	private Integer accountUserId;//到账确认人
	@Translation(name = "到账时间")
	private Long  accountTime;//到账时间
	@Translation(name = "到账金额")
	private Float accountFee;//到账金额
	public AccountBillLogDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AccountBillLogDto(Integer id, Integer feebillId, Integer dockfeebillId, Integer accountUserId,
			Long accountTime, Float accountFee) {
		super();
		this.id = id;
		this.feebillId = feebillId;
		this.dockfeebillId = dockfeebillId;
		this.accountUserId = accountUserId;
		this.accountTime = accountTime;
		this.accountFee = accountFee;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFeebillId() {
		return feebillId;
	}
	public void setFeebillId(Integer feebillId) {
		this.feebillId = feebillId;
	}
	public Integer getDockfeebillId() {
		return dockfeebillId;
	}
	public void setDockfeebillId(Integer dockfeebillId) {
		this.dockfeebillId = dockfeebillId;
	}
	public Integer getAccountUserId() {
		return accountUserId;
	}
	public void setAccountUserId(Integer accountUserId) {
		this.accountUserId = accountUserId;
	}
	public Long getAccountTime() {
		return accountTime;
	}
	public void setAccountTime(Long accountTime) {
		this.accountTime = accountTime;
	}
	public Float getAccountFee() {
		return accountFee;
	}
	public void setAccountFee(Float accountFee) {
		this.accountFee = accountFee;
	}
	
}
