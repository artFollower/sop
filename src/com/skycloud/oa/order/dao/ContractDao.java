package com.skycloud.oa.order.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.skycloud.oa.base.dao.BaseDao;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.order.dto.ContractDto;
import com.skycloud.oa.order.dto.GetContractDto;
import com.skycloud.oa.order.model.Contract;

public interface ContractDao{

/**
 * 添加合同
 * @author yanyufeng
 * @param contract
 * @return 
 */
public int addContract(Contract contract)throws OAException;

/**
 * 获得当年相同类型的合同数量
 * @author yanyufeng
 * @param code
 * @return
 */
public int getTheSameContract(String code);

/**
 * 删除合同
 * @author yanyufeng
 * @param contractId
 */
public void deleteContract(String contractId);


/**
 * 修改合同
 * @author yanyufeng
 * @param contract
 */
public void updateContract(Contract contract);

/**
 * 多条件查询合同
 * @author yanyufeng
 * @param contractDto
 * @param start
 * @param limit
 * @return
 */
public List<Map<String,Object>> getContractListByCondition(ContractDto contractDto,int start,int limit);


/**
 * 多条件查询合同的数量
 * @author yanyufeng
 * @param contractDto
 * @return
 */
public int getContractListCountByCondition(ContractDto contractDto);


/**
 * 更新合同状态
 * @author yanyufeng
 * @param id
 * @param status
 */
public void updateContractStatus(int id,int status);

/**
 * 让原合同失效，新合同生效
 * @author yanyufeng
 * @param id
 * @param sourceContractId 
 */
public void updateAndSetSourceContractLose(int id, int sourceContractId);

/**
 * code校验
 * @author Administrator
 * @param code
 * @return
 * 2015-3-23 下午8:12:41
 */
public int checkCode(String code);

/**
 * 查询合同下所有货批
 * @author yanyufeng
 * @param id
 * @return
 * @throws OAException
 * 2015-10-14 下午5:37:24
 */
public List<Map<String,Object>> getCargoCode(int id)throws OAException;

}
