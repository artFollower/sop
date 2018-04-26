package com.skycloud.oa.order.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.order.dao.ContractDao;
import com.skycloud.oa.order.dto.ContractDto;
import com.skycloud.oa.order.model.Contract;
import com.skycloud.oa.utils.Common;

/**
 * @author yanyufeng
 * 
 */
@Component
public class ContractDaoImpl extends BaseDaoImpl implements ContractDao {

	// @Autowired
	// public JdbcTemplate jdbcTemplate;

	/**
	 * 添加合同
	 * 
	 * @param contract
	 * @author yanyufeng
	 */
	@Override
	public int addContract(Contract contract) throws OAException{
		try {
			return (Integer) save(contract);
		} catch (Exception e) {
			throw new OAException(Constant.SYS_CODE_DB_ERR, "添加失败", e);
		}

	}

	/**
	 * 获得当年相同类型的合同数量
	 * 
	 * @param code
	 * @return
	 * @author yanyufeng
	 */
	@Override
	public int getTheSameContract(String code) {
		String codeSql = "SELECT COUNT(*) FROM t_pcs_contract WHERE code like '%"
				+ code + "%'";
		int count = (int) getCount(codeSql);
		return count;
	}

	/**
	 * 删除合同
	 * 
	 * @param contractId
	 * @author yanyufeng
	 */
	@Override
	public void deleteContract(String contractId) {
		String sql = "update t_pcs_contract set status=4 where id in(" + contractId
				+ ")";
		// jdbcTemplate.update(sql,new Object[]{contractId});
		try {
			execute(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 修改合同
	 * 
	 * @param contract
	 * @author yanyufeng
	 */
	@Override
	public void updateContract(Contract contract) {
		String sql="update t_pcs_contract set id=id";
		if(!Common.empty(contract.getCode())){
			sql+=" ,code='"+contract.getCode()+"'";
		}
		if(!Common.empty(contract.getTitle())){
			sql+=" ,title='"+contract.getTitle()+"'";
		}
		if(!Common.isNull(contract.getType())){
			sql+=" ,type="+contract.getType();
		}
		if(!Common.isNull(contract.getClientId())){
			sql+=" ,clientId="+contract.getClientId();
		}
		if(!Common.isNull(contract.getProductId())){
			sql+=" ,productId="+contract.getProductId();
		}
		if(!Common.empty(contract.getQuantity())){
			sql+=" ,quantity='"+contract.getQuantity()+"'";
		}
		if(!Common.empty(contract.getLossRate())){
			sql+=" ,lossRate='"+contract.getLossRate()+"'";
		}
		if(!Common.empty(contract.getTotalPrice())){
			sql+=" ,totalPrice='"+contract.getTotalPrice()+"'";
		}
		if(!Common.empty(contract.getStartDate())){
			sql+=" ,startDate='"+contract.getStartDate()+"'";
		}
		if(!Common.empty(contract.getEndDate())){
			sql+=" ,endDate='"+contract.getEndDate()+"'";
		}
		if(!Common.empty(contract.getDescription())){
			sql+=" ,description='"+contract.getDescription()+"'";
		}
		if(!Common.empty(contract.getSignDate())){
			sql+=" ,signDate='"+contract.getSignDate()+"'";
		}
		if(!Common.empty(contract.getStartDate())){
			sql+=" ,startDate='"+contract.getStartDate()+"'";
		}
		if(!Common.empty(contract.getEndDate())){
			sql+=" ,endDate='"+contract.getEndDate()+"'";
		}
		if(!Common.empty(contract.getSupplementary())){
			sql+=" ,supplementary='"+contract.getSupplementary()+"'";
		}
		if(!Common.empty(contract.getFileUrl())){
			sql+=" ,fileUrl='"+contract.getFileUrl()+"'";
		}
		if(!Common.isNull(contract.getStatus())){
			sql+=" ,status="+contract.getStatus();
		}
		if(!Common.isNull(contract.getIntentionId())){
			sql+=" ,intentionId="+contract.getIntentionId();
		}
		if(!Common.isNull(contract.getSourceContractId())){
			sql+=" ,sourceContractId="+contract.getSourceContractId();
		}
		if(!Common.isNull(contract.getCreateUserId())){
			sql+=" ,createUserId="+contract.getCreateUserId();
		}
		if(!Common.empty(contract.getCreateTime())){
			sql+=" ,createTime='"+contract.getCreateTime()+"'";
		}
		if(!Common.isNull(contract.getEditUserId())){
			sql+=" ,editUserId="+contract.getEditUserId();
		}
		if(!Common.empty(contract.getEditTime())){
			sql+=" ,editTime='"+contract.getEditTime()+"'";
		}
		if(!Common.isNull(contract.getTradeType())){
			sql+=" ,tradeType="+contract.getTradeType();
		}
		if(!Common.empty(contract.getOtherPrice())){
			sql+=" ,otherPrice='"+contract.getOtherPrice()+"'";
		}
		if(!Common.empty(contract.getOvertimePrice())){
			sql+=" ,overtimePrice='"+contract.getOvertimePrice()+"'";
		}
		if(!Common.empty(contract.getPassPrice())){
			sql+=" ,passPrice='"+contract.getPassPrice()+"'";
		}
		if(!Common.empty(contract.getPeriod())){
			sql+=" ,period='"+contract.getPeriod()+"'";
		}
		if(!Common.empty(contract.getPortSecurityPrice())){
			sql+=" ,portSecurityPrice='"+contract.getPortSecurityPrice()+"'";
		}
		if(!Common.empty(contract.getPortServicePrice())){
			sql+=" ,portServicePrice='"+contract.getPortServicePrice()+"'";
		}
		if(!Common.empty(contract.getStoragePrice())){
			sql+=" ,storagePrice='"+contract.getStoragePrice()+"'";
		}
		if(!Common.empty(contract.getProtectStoragePrice())){
			sql+=" ,protectStoragePrice='"+contract.getProtectStoragePrice()+"'";
		}
		if(!Common.empty(contract.getReviewContent())){
			sql+=" ,reviewContent='"+contract.getReviewContent()+"'";
		}
		if(!Common.isNull(contract.getReviewUserId())){
			sql+=" ,reviewUserId="+contract.getReviewUserId();
		}
		if(!Common.isNull(contract.getReviewTime())){
			sql+=" ,reviewTime="+contract.getReviewTime();
		}
		
		if(!Common.empty(contract.getProductNameList())){
			sql+=" ,productNameList='"+contract.getProductNameList()+"'";
		}
		if(!Common.empty(contract.getTradeTypeNameList())){
			sql+=" ,tradeTypeNameList='"+contract.getTradeTypeNameList()+"'";
		}
		if(!Common.empty(contract.getProductIdList())){
			sql+=" ,productIdList='"+contract.getProductIdList()+"'";
		}
		
		
		if(!Common.empty(contract.getArrivalTime())){
			sql+=" ,arrivalTime='"+contract.getArrivalTime()+"'";
		}
		if(!Common.empty(contract.getSpDes())){
			sql+=" ,spDes='"+contract.getSpDes()+"'";
		}
		
		
		
		sql+=" where id="+contract.getId();
		try {
			execute(sql);
//			update(contract);
		} catch (OAException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 多条件查询合同
	 * 
	 * @param contractDto
	 * @param start
	 * @param limit
	 * @return
	 * @author yanyufeng
	 */
	@Override
	public List<Map<String, Object>> getContractListByCondition(
			ContractDto contractDto, int start, int limit) {
		String sql = "SELECT tpi.*,DATE_FORMAT(tpi.arrivalTime,'%Y-%m-%d %H:%i:%s') mArrivalTime,DATE_FORMAT(tpi.createTime,'%Y-%m-%d %H:%i:%s') mCreateTime,DATE_FORMAT(tpi.editTime,'%Y-%m-%d %H:%i:%s') mEditTime,DATE_FORMAT(tpi.signDate,'%Y-%m-%d %H:%i:%s') mSignDate,DATE_FORMAT(tpi.startDate,'%Y-%m-%d %H:%i:%s') mStartDate,DATE_FORMAT(tpi.endDate,'%Y-%m-%d %H:%i:%s') mEndDate,tpc.name clientName,tpp.name productName ,tsu.name createUserName,tpcg.id clientGroupId,tpcg.name clientGroupName,"
				+ "tsu2.name editUserName,tpt.value typeName,tps.value statusName,tptt.value tradeTypeName,tsu3.name reviewUserName,from_unixtime(tpi.reviewTime) mReviewTime,i.title intentionTitle "
				+ " from t_pcs_contract tpi INNER JOIN t_pcs_client tpc on tpi.clientId=tpc.id "
				+ " LEFT JOIN t_pcs_trade_type tptt on tptt.key=tpi.tradeType "
				+ " LEFT JOIN t_pcs_product tpp on tpi.productId=tpp.id INNER JOIN t_auth_user tsu on "
				+ " tpi.createUserId=tsu.id LEFT JOIN t_auth_user tsu2 on tpi.editUserId=tsu2.id " +
				" INNER JOIN t_pcs_contract_type tpt on tpi.type=tpt.key " +
				" LEFT JOIN t_pcs_status tps on tpi.status=tps.key " +
				" LEFT JOIN t_pcs_client_group tpcg on tpc.clientGroupId=tpcg.id " +
				" LEFT JOIN t_auth_user tsu3 on tpi.reviewUserId=tsu3.id " +
				" LEFT JOIN t_pcs_intention i on i.id=tpi.intentionId where 1=1 ";
		if (!Common.empty(contractDto)) {
			if (!Common.empty(contractDto.getId()) && contractDto.getId() != 0) {
				sql += " and tpi.id=" + contractDto.getId();
			}
			if (!Common.empty(contractDto.getCode())) {
				sql += " and tpi.code like'%" + contractDto.getCode() + "%'";
			}
			if (!Common.empty(contractDto.getTitle())) {
				sql += " and tpi.title like '%" + contractDto.getTitle() + "%'";
			}
			if (!Common.empty(contractDto.getClientId())
					&& contractDto.getClientId() != 0) {
				sql += " and tpi.clientId=" + contractDto.getClientId();
			}
			if (!Common.empty(contractDto.getProductId())
					&& contractDto.getProductId() != 0) {
				sql += " and (tpi.productId=" + contractDto.getProductId()+" or  FIND_IN_SET("+contractDto.getProductId()+",tpi.productIdList)>0)";
			}
			if (!Common.empty(contractDto.getType())
					&& contractDto.getType() != 0) {
				sql += " and tpi.type=" + contractDto.getType();
			}
			if (contractDto.getStatus()!=null) {
				sql += " and tpi.status=" + contractDto.getStatus();
			}
			else{
				sql+="  and tpi.status<>4 and tpi.status<>5 and tpi.status<>6 ";
			}
			if (!Common.empty(contractDto.getTimeType())) {
				switch (contractDto.getTimeType()) {
				// 创建时间
				case 1:
					sql += " and tpi.createTime between '"
							+ contractDto.getStartTime() + "' and '"
							+ contractDto.getEndTime() + "'";
					break;
				// 修改时间
				case 2:
					sql += " and tpi.editTime between '"
							+ contractDto.getStartTime() + "' and '"
							+ contractDto.getEndTime() + "'";
					break;
				// 提交时间
				case 3:
					break;
				// 确认时间
				case 4:
					break;
				// 签订时间
				case 5:
					sql += " and tpi.signDate between '"
							+ contractDto.getStartTime() + "' and '"
							+ contractDto.getEndTime() + "'";
					break;
				// 合同结束日期
				case 6:
					sql += " and tpi.endDate between '"
							+ contractDto.getStartTime() + "' and '"
							+ contractDto.getEndTime() + "'";
					break;
				}
			}
			if (!Common.empty(contractDto.getText())) {
				sql += " and (tpi.title like'%" + contractDto.getText()
						+ "%' or tpc.name like '%" + contractDto.getText()
						+ "%' " + " or tpp.name like '%"
						+ contractDto.getText() + "%' or tpi.code like '%"
						+ contractDto.getText()
						+ "%' or tpi.description like '%"
						+ contractDto.getText() + "%')";
			}
		}
		
		if(!Common.empty(contractDto.getTypes())){
			sql+=" and tpi.type in("+contractDto.getTypes()+") ";
		}
		
		if (limit == 0) {
			sql += " order by field(tpi.status,3,0,1,2,3,4,5,6)  ASC, tpi.code DESC ";
		} else {
			sql += " order by field(tpi.status,3,0,1,2,3,4,5,6)  ASC, tpi.code DESC limit " + start + "," + limit;
		}
		try {
			List<Map<String, Object>> mapList = executeQuery(sql);
			return mapList;
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 多条件查询合同的数量
	 * 
	 * @param contractDto
	 * @return
	 * @author yanyufeng
	 */
	@Override
	public int getContractListCountByCondition(ContractDto contractDto) {
		String sql = "SELECT COUNT(*) from t_pcs_contract tpi INNER JOIN t_pcs_client tpc on tpi.clientId=tpc.id "
				+ " LEFT JOIN t_pcs_product tpp on tpi.productId=tpp.id INNER JOIN t_auth_user tsu on "
				+ " tpi.createUserId=tsu.id LEFT JOIN t_auth_user tsu2 on tpi.editUserId=tsu2.id "
				+ " LEFT JOIN t_pcs_contract_type tpt on tpi.type=tpt.key LEFT JOIN t_pcs_status tps on tpi.status=tps.key where 1=1 ";
		if (!Common.empty(contractDto)) {
			if (!Common.empty(contractDto.getId()) && contractDto.getId() != 0) {
				sql += " and tpi.id=" + contractDto.getId();
			}
			if (!Common.empty(contractDto.getCode())) {
				sql += " and tpi.code like'%" + contractDto.getCode() + "%'";
			}
			if (!Common.empty(contractDto.getTitle())) {
				sql += " and tpi.title like '%" + contractDto.getTitle() + "%'";
			}
			if (!Common.empty(contractDto.getClientId())
					&& contractDto.getClientId() != 0) {
				sql += " and tpi.clientId=" + contractDto.getClientId();
			}
			if (!Common.empty(contractDto.getProductId())
					&& contractDto.getProductId() != 0) {
				sql += " and (tpi.productId=" + contractDto.getProductId()+" or  FIND_IN_SET("+contractDto.getProductId()+",tpi.productIdList)>0)";
			}
			if (!Common.empty(contractDto.getType())
					&& contractDto.getType() != 0) {
				sql += " and tpi.type=" + contractDto.getType();
			}
			if (contractDto.getStatus()!=null) {
				sql += " and tpi.status=" + contractDto.getStatus();
			}else{
				sql+="  and tpi.status<>4 and tpi.status<>5 and tpi.status<>6";
			}
			if (!Common.empty(contractDto.getTimeType())) {
				switch (contractDto.getTimeType()) {
				// 创建时间
				case 1:
					sql += " and tpi.createTime between '"
							+ contractDto.getStartTime() + "' and '"
							+ contractDto.getEndTime() + "'";
					break;
				// 修改时间
				case 2:
					sql += " and tpi.editTime between '"
							+ contractDto.getStartTime() + "' and '"
							+ contractDto.getEndTime() + "'";
					break;
				// 提交时间
				case 3:
					break;
				// 确认时间
				case 4:
					break;
				// 签订时间
				case 5:
					sql += " and tpi.signDate between '"
							+ contractDto.getStartTime() + "' and '"
							+ contractDto.getEndTime() + "'";
					break;
				// 合同结束日期
				case 6:
					sql += " and tpi.endDate between '"
							+ contractDto.getStartTime() + "' and '"
							+ contractDto.getEndTime() + "'";
					break;
				}
			}
			
			

			if(!Common.empty(contractDto.getTypes())){
				sql+=" and tpi.type in("+contractDto.getTypes()+") ";
			}
			if (!Common.empty(contractDto.getText())) {
				sql += " and (tpi.title like'%" + contractDto.getText()
						+ "%' or tpc.name like '%" + contractDto.getText()
						+ "%' " + " or tpp.name like '%"
						+ contractDto.getText() + "%' or tpi.code like '%"
						+ contractDto.getText()
						+ "%' or tpi.description like '%"
						+ contractDto.getText() + "%')";
			}
		}
		// int count=jdbcTemplate.queryForInt(sql);
		int count = (int) getCount(sql);
		return count;
	}

	@Override
	public void updateContractStatus(int id, int status) {
		
		String sql="update t_pcs_contract set status="+status+" where id="+id;
		try {
			execute(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateAndSetSourceContractLose(int id,int sourceContractId) {
		String sql="update t_pcs_contract tpc1,t_pcs_contract tpc2 set tpc1.`status`=5 , " +
				" tpc2.`status`=2 where tpc1.id="+sourceContractId+" and tpc2.id="+id;
		try {
			execute(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int checkCode(String code) {
		String sql=" select count(*) from t_pcs_contract where status not in (4,5,6) and code='"+code+"'";
		return (int) getCount(sql);
		
	}

	@Override
	public List<Map<String, Object>> getCargoCode(int id) throws OAException {
		String sql="select GROUP_CONCAT(code ORDER BY code DESC) code from t_pcs_cargo where contractId="+id +" order by id DESC";
		return executeQuery(sql);
		
	}

}
