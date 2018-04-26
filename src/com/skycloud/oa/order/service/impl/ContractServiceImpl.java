package com.skycloud.oa.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.auth.dao.UserDao;
import com.skycloud.oa.auth.dto.UserDto;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.message.dao.MessageDao;
import com.skycloud.oa.message.dto.MessageDto;
import com.skycloud.oa.order.dao.ContractDao;
import com.skycloud.oa.order.dto.ContractDto;
import com.skycloud.oa.order.model.Contract;
import com.skycloud.oa.order.service.ContractService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dao.ApproveCacheDao;
import com.skycloud.oa.system.dao.ApproveContentDao;
import com.skycloud.oa.system.dao.ApproveWorkDao;
import com.skycloud.oa.system.model.ApproveCache;
import com.skycloud.oa.system.model.ApproveContent;
import com.skycloud.oa.system.model.ApproveWork;
import com.skycloud.oa.system.model.OperateLog;
import com.skycloud.oa.system.service.OperateLogService;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.DateTimeUtil;
import com.skycloud.oa.utils.OaMsg;

/**
 * @author yanyufeng
 *
 */
@Service
public class ContractServiceImpl implements ContractService {
	private static Logger LOG = Logger.getLogger(ContractServiceImpl.class);
	
	@Autowired
	private ContractDao contractDao;
	@Autowired
	private ApproveContentDao approveContentDao;
	@Autowired
	private ApproveWorkDao approveWorkDao;
	@Autowired
	private MessageDao messageDao;
	@Autowired
	private ApproveCacheDao approveCacheDao;
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private OperateLogService operateLogService;
	/**
	 * 添加合同
	 * @param contract
	 * @return
	 * @throws OAException
	 * @author yanyufeng
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_ORDER,type=C.LOG_TYPE.CREATE)
	public OaMsg addContract(Contract contract, String ids) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			if(Common.empty(contract.getCode())){
				LOG.debug("生成code");
				String time = DateTimeUtil.getString(new Date(System.currentTimeMillis()), DateTimeUtil
						.getShortDateTimeFormatText());
				String code = time.substring(2, 4) + "YZ";
				String[] type = new String[] { "A", "B", "C", "D","E" };
				code += type[contract.getType() - 1];
				int codecount=contractDao.getTheSameContract(code);
				String codeNumber = "";
				if (codecount < 9) {
					codeNumber = "00" + (codecount + 1);
				} else if (codecount >= 9 && codecount < 99) {
					codeNumber = "0" + (codecount + 1);
				} else {
					codeNumber = (codecount + 1) + "";
				}
				code += codeNumber;
				contract.setCode(code);
				LOG.debug("code="+code);
			}
			LOG.debug("开始插入合同");
			
			
			int id=contractDao.addContract(contract);
			if(!ids.equals("0")){
				String[] idss=ids.split(",");
				List<String> strIds=new ArrayList<String>();
				for(int i=0;i<idss.length;i++){
					strIds.add(idss[i]);
					ApproveContent approveContent =new ApproveContent();
					approveContent.setUserId(Integer.parseInt(idss[i]));
					approveContent.setType(1);
					approveContent.setWorkId(id);
					approveContent.setTypeStatus(0);
					approveContentDao.addApproveContent(approveContent);
				}
				
				
				User mUser = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();

				ApproveCache approveCache=new ApproveCache();
				approveCache.setUserId(mUser.getId());
				approveCache.setCacheUser(ids);
				approveCache.setType(1);
				approveCache.setWorkType(1);
				//缓存选择
				if(approveCacheDao.getApproveCache(approveCache).size()>0){
					approveCacheDao.update(approveCache);
				}else{
					
					approveCacheDao.addApproveCache(approveCache);
				}
				
				
				
				MessageDto mDto=new MessageDto();
				mDto.setSendUserId(mUser.getId());
				mDto.setTaskId(0);
				mDto.setCreateTime(Long.parseLong(new Date().getTime()/1000+""));
				mDto.setUrl("#/contractGet?id="+id);
				mDto.setContent("您有一份合同需要审批，请查看！");
				mDto.setType("1");
				messageDao.createMessage(mDto, strIds);
				
				
				
			}
			Map<String,String> map=new HashMap<String, String>();
			map.put("id", id+"");
			oaMsg.setMap(map);
			LOG.debug("插入成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("插入成功");
		} catch (RuntimeException re) {
			LOG.error("插入失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			
			throw new OAException(Constant.SYS_CODE_DB_ERR, "插入失败",re);
		}
		return oaMsg;
	}

	/**
	 * 删除合同
	 * @param contractId
	 * @return
	 * @throws OAException
	 * @author yanyufeng
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_ORDER,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteContract(String contractId) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始删除合同");
			contractDao.deleteContract(contractId);
			LOG.debug("删除成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("删除成功");
		} catch (RuntimeException re) {
			LOG.error("删除失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "删除失败",re);
		}
		return oaMsg;
	}

	/**
	 * 修改合同
	 * @param contract
	 * @return
	 * @throws OAException
	 * @author yanyufeng
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_ORDER,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateContract(Contract contract) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始修改合同");
			
			if(contract.getStatus()==2){
				ContractDto contractDto=new ContractDto();
				contractDto.setId(contract.getId());
				int sourceContractId=(int) contractDao.getContractListByCondition(contractDto, 0, 0).get(0).get("sourceContractId");
				if(sourceContractId!=0){
					contractDao.updateAndSetSourceContractLose(contract.getId(),sourceContractId);
				}
			}
			
			contractDao.updateContract(contract);
			LOG.debug("修改成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("修改成功");
		} catch (RuntimeException re) {
			LOG.error("修改失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "修改失败",re);
		}
		return oaMsg;
	}

	

	/**
	 * 多条件查询合同
	 * @param contractDto
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 * @author yanyufeng
	 */
	@Override
	public OaMsg getContractListByCondition(ContractDto contractDto,PageView pageView) throws OAException {
		OaMsg oaMsg=new OaMsg();
		int count=0;
		try {
			LOG.debug("开始查询合同");
			List<Map<String,Object>> contractList=contractDao.getContractListByCondition(contractDto, pageView.getStartRecord(), pageView.getMaxresult());
			
			count=contractDao.getContractListCountByCondition(contractDto);
			LOG.debug("查询成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("查询成功");
			oaMsg.getData().addAll(contractList);
			Map<String, String> map = new HashMap<String, String>();
			map.put("totalRecord", count + "");
			oaMsg.setMap(map);
		} catch (RuntimeException re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_ORDER,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateContractStatus(Contract contract) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始修改合同状态");
			if(contract.getStatus()==2||contract.getStatus()==3){
				
				ApproveContent approveContent=new ApproveContent();
				User mUser = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
				approveContent.setUserId(mUser.getId());
				approveContent.setWorkId(contract.getId());
				approveContent.setTypeStatus(contract.getStatus()-1);
				approveContent.setType(1);
				approveContent.setContent(contract.getReviewContent());
				approveContent.setReviewTime(Long.parseLong(new Date().getTime()/1000+""));
				approveContentDao.update(approveContent);
				
				MessageDto mDto=new MessageDto();
				ContractDto cDto=new ContractDto();
				cDto.setId(contract.getId());
				List<String> strIds=new ArrayList<String>();
				strIds.add(contractDao.getContractListByCondition(cDto,0,0).get(0).get("createUserId").toString());
				
				ContractDto contractDto=new ContractDto();
				contractDto.setId(contract.getId());
				if(contract.getStatus()==2){
					//添加日志
					OperateLog operateLog=new OperateLog();
					operateLog.setObject(C.LOG_OBJECT.PCS_ORDER);
					operateLog.setType(C.LOG_TYPE.VERIFY);
					operateLog.setContent("审批通过");
					operateLogService.create(operateLog, new OaMsg());
					
					mDto.setSendUserId(mUser.getId());
					mDto.setTaskId(0);
					mDto.setCreateTime(Long.parseLong(new Date().getTime()/1000+""));
					mDto.setUrl("#/contractGet?id="+contract.getId());
					mDto.setContent("您的合同已经审批通过，请查看！");
					mDto.setType("1");
					messageDao.createMessage(mDto, strIds);
					
					
					int sourceContractId=(int) contractDao.getContractListByCondition(contractDto, 0, 0).get(0).get("sourceContractId");
					if(sourceContractId!=0){
						contractDao.updateAndSetSourceContractLose(contract.getId(),sourceContractId);
					}else{
						contractDao.updateContract(contract);
					}
				}
				if(contract.getStatus()==3){
					//添加日志
					OperateLog operateLog=new OperateLog();
					operateLog.setObject(C.LOG_OBJECT.PCS_ORDER);
					operateLog.setType(C.LOG_TYPE.VERIFY);
					operateLog.setContent("审批退回");
					operateLogService.create(operateLog, new OaMsg());
					
					mDto.setSendUserId(mUser.getId());
					mDto.setTaskId(0);
					mDto.setCreateTime(Long.parseLong(new Date().getTime()/1000+""));
					mDto.setUrl("#/contractGet?id="+contract.getId());
					mDto.setContent("您有一份合同审批未通过，被退回，请查看！");
					mDto.setType("1");
					messageDao.createMessage(mDto, strIds);
					contractDao.updateContract(contract);
				}
//					contractDao.updateContractStatus(id, status);
			}else{
				Contract contract1=new Contract();
				contract1.setId(contract.getId());
				contract1.setStatus(contract.getStatus());
				contractDao.updateContract(contract1);
//				contractDao.updateContractStatus(id, status);
			}
			LOG.debug("修改成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("修改成功");
		} catch (RuntimeException re) {
			LOG.error("修改失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "修改失败",re);
		}
		return oaMsg;
	}

	@Override
	public Object checkCode(String code) throws OAException {
		OaMsg oaMsg=new OaMsg();
		int count=0;
		try {
			LOG.debug("开始查询合同");
			count=contractDao.checkCode(code);
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			Map<String, String> map = new HashMap<String, String>();
			map.put("totalRecord", count + "");
			oaMsg.setMap(map);
		} catch (RuntimeException re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
		return oaMsg;
	}

	@Override
	public Object getApproveContent(ApproveContent approveContent)throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始查询审批");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(approveContentDao.getApproveContent(approveContent, 0, 0));
		} catch (RuntimeException re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_ORDER,type=C.LOG_TYPE.VERIFY)
	public Object sendCheck(String ids,int contractId, String content) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始查询审批");
			String[] id=ids.split(",");
			List<String> strIds=new ArrayList<String>();
			for(int i=0;i<id.length;i++){
				strIds.add(id[i]);
				ApproveContent approveContent =new ApproveContent();
				approveContent.setUserId(Integer.parseInt(id[i]));
				approveContent.setType(1);
				approveContent.setWorkId(contractId);
				
				
				//添加日志
				OperateLog operateLog=new OperateLog();
				operateLog.setObject(C.LOG_OBJECT.PCS_ORDER);
				operateLog.setType(C.LOG_TYPE.VERIFY);
				operateLog.setContent("提交审批");
				operateLogService.create(operateLog, new OaMsg());
				
				if(approveContentDao.getApproveContent(approveContent, 0, 0).size()==0){
					
					approveContent.setTypeStatus(0);
					approveContentDao.addApproveContent(approveContent);
				}else{
					approveContent.setTypeStatus(0);
					approveContentDao.update(approveContent);
				}
				
				
			}
			User mUser = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			//发送消息
			MessageDto mDto=new MessageDto();
			mDto.setSendUserId(mUser.getId());
			mDto.setTaskId(0);
			mDto.setCreateTime(Long.parseLong(new Date().getTime()/1000+""));
			mDto.setUrl("#/contractGet?id="+contractId);
			mDto.setContent("您有一份合同需要审批，请查看！");
			mDto.setType("1");
			messageDao.createMessage(mDto, strIds);
			
			ApproveCache approveCache=new ApproveCache();
			approveCache.setUserId(mUser.getId());
			approveCache.setCacheUser(ids);
			approveCache.setType(1);
			approveCache.setWorkType(1);
			//缓存选择
			if(approveCacheDao.getApproveCache(approveCache).size()>0){
				approveCacheDao.update(approveCache);
			}else{
				
				approveCacheDao.addApproveCache(approveCache);
			}
			
			//如果有审批信息并且用户不在提交列表内，就算该用户审批通过
			boolean isInIds=false;
			for (int i=0;i<id.length;i++){   
				if((mUser.getId()+"").equals(id[i])){
					isInIds=true;
					break;
				}
			}
			if(!Common.empty(content)&&!isInIds){
				ApproveContent approveContent=new ApproveContent();
				
				approveContent.setUserId(mUser.getId());
				approveContent.setWorkId(contractId);
				approveContent.setTypeStatus(1);
				approveContent.setType(1);
				approveContent.setContent(content);
				approveContent.setReviewTime(Long.parseLong(new Date().getTime()/1000+""));
				approveContentDao.update(approveContent);
			}
			
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (RuntimeException re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
		return oaMsg;
	}

	@Override
	public Object sendcopy(String ids, int contractId)throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始抄送");
			String[] id=ids.split(",");
			List<String> strIds=new ArrayList<String>();
			for(int i=0;i<id.length;i++){
				strIds.add(id[i]);
			}
			User mUser = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			
			ApproveCache approveCache=new ApproveCache();
			approveCache.setUserId(mUser.getId());
			approveCache.setCacheUser(ids);
			approveCache.setType(2);
			approveCache.setWorkType(1);
			//缓存选择
			if(approveCacheDao.getApproveCache(approveCache).size()>0){
				approveCacheDao.update(approveCache);
			}else{
				
				approveCacheDao.addApproveCache(approveCache);
			}
			
			
			
			MessageDto mDto=new MessageDto();
			mDto.setSendUserId(mUser.getId());
			mDto.setTaskId(0);
			mDto.setCreateTime(Long.parseLong(new Date().getTime()/1000+""));
			mDto.setUrl("#/contractGet?id="+contractId);
			mDto.setContent("您有一份合同需要查看！");
			mDto.setType("1");
			messageDao.createMessage(mDto, strIds);
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (RuntimeException re) {
			LOG.error("抄送失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "抄送失败",re);
		}
		return oaMsg;
	}

	@Override
	public Object checkcache(int userId,int workType) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			ApproveCache approveCache=new ApproveCache();
			approveCache.setUserId(userId);
			
			approveCache.setWorkType(workType);
			List<Map<String,Object>> data=approveCacheDao.getApproveCache(approveCache);
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			if(data.size()>0){
				List<Map<String,Object>> re=new ArrayList<Map<String,Object>>();
				for(int i=0;i<data.size();i++){
					Map<String,Object> m=new HashMap<String,Object>();
					UserDto uDto=new UserDto();
					uDto.setIds(data.get(i).get("cacheUser").toString());
					List<Map<String,Object>> userList=userDao.get(uDto, 0, 0);
					if(userList.size()>0){
						String userName="";
						for(int j=0;j<userList.size();j++){
							userName+=userList.get(j).get("name").toString()+",";
						}
						userName=userName.substring(0, userName.length()-1);
						m.put("name", userName);
					}
					m.put("ids", uDto.getIds());
					m.put("type", data.get(i).get("type").toString());
					re.add(m);
				}
				oaMsg.getData().addAll(re);
				
			}
		}catch (RuntimeException re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
		return oaMsg;
	}

	@Override
	public Object getCargoCode(int id) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始查询合同");
			oaMsg.getData().addAll(contractDao.getCargoCode(id));
			LOG.debug("查询成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("查询成功");
		} catch (RuntimeException re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
		return oaMsg;
	}
}
