/**
 * 
 */
package com.skycloud.oa.feebill.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skycloud.oa.annatation.Translation;

/**
 *账单到账历史记录
 * @author jiahy
 * @version 2015年6月17日 上午10:55:45
 */
@Entity
@Table(name = "t_pcs_account_bill_log")
public class AccountBillLog {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Translation(name = "账单id")
	private Integer feebillId;//账单id
	@Translation(name = "到账确认人id")
	private Integer accountUserId;//到账确认人
	@Translation(name = "到账时间")
	private Long  accountTime;//到账时间
	@Translation(name = "到账金额")
	private String accountFee;//到账金额
	@Translation(name = "码头规费账单id")
	private Integer dockfeebillId;//码头规费账单id
	public AccountBillLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AccountBillLog(Integer id, Integer feebillId, Integer accountUserId, Long accountTime, String accountFee,
			Integer dockfeebillId) {
		super();
		this.id = id;
		this.feebillId = feebillId;
		this.accountUserId = accountUserId;
		this.accountTime = accountTime;
		this.accountFee = accountFee;
		this.dockfeebillId = dockfeebillId;
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
	public String getAccountFee() {
		return accountFee;
	}
	public void setAccountFee(String accountFee) {
		this.accountFee = accountFee;
	}
	public Integer getDockfeebillId() {
		return dockfeebillId;
	}
	public void setDockfeebillId(Integer dockfeebillId) {
		this.dockfeebillId = dockfeebillId;
	}
	
	
}
