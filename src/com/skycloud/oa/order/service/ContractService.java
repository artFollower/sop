/**
 * 
 */
package com.skycloud.oa.order.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.order.dto.ContractDto;
import com.skycloud.oa.order.model.Contract;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.model.ApproveContent;
import com.skycloud.oa.system.model.ApproveWork;
import com.skycloud.oa.utils.OaMsg;


/**
 * @author yanyufeng
 *
 */
public interface ContractService {
	
	

	/**
	 * 添加合同
	 * @author yanyufeng
	 * @param contract
	 * @param ids 
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg addContract(Contract contract, String ids) throws OAException;
	
	/**
	 * 删除合同
	 * @author yanyufeng
	 * @param contractId
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg deleteContract(String contractId) throws OAException;
	
	/**
	 * 修改合同
	 * @author yanyufeng
	 * @param contract
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg updateContract(Contract contract) throws OAException;

	
	/**
	 * 多条件查询合同
	 * @author yanyufeng
	 * @param contractDto
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg getContractListByCondition(ContractDto contractDto,PageView pageView) throws OAException;

	/**
	 * 更新合同状态
	 * @author yanyufeng
	 * @param id
	 * @param status
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg updateContractStatus(Contract contract)throws OAException;

	/**
	 * 检查code
	 * @author Administrator
	 * @param code
	 * @return
	 * @throws OAException
	 * 2015-3-23 下午8:10:25
	 */
	public abstract Object checkCode(String code)throws OAException;

	/**
	 * 检查审批权限
	 * @author yanyufeng
	 * @param approveWork
	 * @return
	 * 2015-3-30 下午8:34:31
	 */
	public abstract Object getApproveContent(ApproveContent approveContent)throws OAException;

	/**
	 * 申请审批（多人）
	 * @author yanyufeng
	 * @param ids
	 * @param contractId 
	 * @param content 
	 * @return
	 * 2015-4-1 上午11:22:21
	 */
	public abstract Object sendCheck(String ids, int contractId, String content)throws OAException;

	/**
	 * 抄送
	 * @author yanyufeng
	 * @param ids
	 * @param contractId
	 * @return
	 * 2015-4-2 下午4:30:23
	 */
	public abstract Object sendcopy(String ids, int contractId)throws OAException;

	/**
	 * 检查是否有上一次记录
	 * @author yanyufeng
	 * @param userId
	 * @param type 
	 * @return
	 * 2015-4-8 下午1:09:30
	 */
	public abstract Object checkcache(int userId,int workType)throws OAException;

	/**
	 * 查询合同下所有货批
	 * @author yanyufeng
	 * @param id
	 * @return
	 * 2015-10-14 下午5:34:57
	 */
	public abstract Object getCargoCode(int id)throws OAException;
	
}
