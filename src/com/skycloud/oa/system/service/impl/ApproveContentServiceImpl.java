package com.skycloud.oa.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.auth.dao.UserDao;
import com.skycloud.oa.auth.dto.UserDto;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.message.dao.MessageDao;
import com.skycloud.oa.message.dto.MessageDto;
import com.skycloud.oa.system.dao.ApproveCacheDao;
import com.skycloud.oa.system.dao.ApproveContentDao;
import com.skycloud.oa.system.dto.ApproveContentDto;
import com.skycloud.oa.system.model.ApproveCache;
import com.skycloud.oa.system.model.ApproveContent;
import com.skycloud.oa.system.service.ApproveContentService;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;

@Service
public class ApproveContentServiceImpl implements ApproveContentService {
	private static Logger LOG = Logger.getLogger(ApproveContentServiceImpl.class);
  @Autowired
  private ApproveContentDao approveContentDao;
  @Autowired
  private ApproveCacheDao approveCacheDao;
  @Autowired
  private MessageDao messageDao;
  @Autowired
  private UserDao userDao;
  
	@Override
	public OaMsg getApproveContent(ApproveContent approveContent)throws OAException {
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
	public OaMsg sendCheck(ApproveContentDto appDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			User mUser = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			String[] id=appDto.getIds().split(",");
			List<String> strIds=new ArrayList<String>();
			for(int i=0;i<id.length;i++){
				strIds.add(id[i]);
				ApproveContent approveContent =new ApproveContent();
				approveContent.setUserId(Integer.parseInt(id[i]));
				approveContent.setType(appDto.getType());
				approveContent.setWorkId(appDto.getTypeId());
				approveContent.setContent(appDto.getContent());
				if(appDto.getTypeStatus()!=0&&Integer.valueOf(id[i])==mUser.getId()){
					approveContent.setReviewTime(System.currentTimeMillis()/1000);
					approveContent.setTypeStatus(appDto.getTypeStatus());
				}
				
               if(approveContentDao.getApproveContent(approveContent, 0, 0).size()==0){
                 	  approveContentDao.cleanApproveContent(appDto);
					approveContentDao.addApproveContent(approveContent);
				}else{
					approveContentDao.update(approveContent);
				}
			}
			
			ApproveCache approveCache=new ApproveCache();
			approveCache.setUserId(mUser.getId());
			approveCache.setCacheUser(appDto.getIds());
			approveCache.setWorkType(appDto.getType());
			approveCache.setType(1);
			//缓存选择
			if(approveCacheDao.getApproveCache(approveCache).size()>0){
				approveCacheDao.update(approveCache);
			}else{
				approveCacheDao.addApproveCache(approveCache);
			}
			MessageDto mDto=new MessageDto();
			mDto.setSendUserId(mUser.getId());
			mDto.setTaskId(0);
			mDto.setCreateTime(System.currentTimeMillis()/1000);
			mDto.setUrl(appDto.getUrl());
			mDto.setContent(appDto.getMsgContent());
			mDto.setType("1");
			messageDao.createMessage(mDto, strIds);
			
//			//如果有审批信息并且用户不在提交列表内，就算该用户审批通过
//			boolean isInIds=false;
//			for (int i=0;i<id.length;i++){   
//				if((mUser.getId()+"").equals(id[i])){
//					isInIds=true;
//					break;
//				}
//			}
//			//如果有审批信息，就算该用户审批通过
//			if(!Common.empty(appDto.getContent())){
//				ApproveContent approveContent=new ApproveContent();
//				approveContent.setUserId(mUser.getId());
//				approveContent.setWorkId(appDto.getTypeId());
//				approveContent.setTypeStatus(appDto.getTypeStatus());
//				approveContent.setType(appDto.getType());
//				approveContent.setContent(appDto.getContent());
//				approveContent.setReviewTime(System.currentTimeMillis()/1000);
//				approveContentDao.update(approveContent);
//			}
//			
		}catch(RuntimeException e){
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			LOG.error("service工作流审批失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service工作流 审批");
		}
		return oaMsg;
	}

	@Override
	public OaMsg sendCopy(ApproveContentDto appDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始抄送");
			String[] id=appDto.getIds().split(",");
			List<String> strIds=new ArrayList<String>();
			for(int i=0;i<id.length;i++){
				strIds.add(id[i]);
			}
			User mUser = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			
			ApproveCache approveCache=new ApproveCache();
			approveCache.setUserId(mUser.getId());
			approveCache.setCacheUser(appDto.getIds());
			approveCache.setType(2);
			approveCache.setWorkType(appDto.getType());
			//缓存选择
			if(approveCacheDao.getApproveCache(approveCache).size()>0){
				approveCacheDao.update(approveCache);
			}else{
				
				approveCacheDao.addApproveCache(approveCache);
			}
			
			MessageDto mDto=new MessageDto();
			mDto.setSendUserId(mUser.getId());
			mDto.setTaskId(0);
			mDto.setCreateTime(System.currentTimeMillis()/1000);
			mDto.setUrl(appDto.getUrl());
			mDto.setContent(appDto.getMsgContent());
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
	public OaMsg checkcache(int id, int workType) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			ApproveCache approveCache=new ApproveCache();
			approveCache.setUserId(id);
			
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
	public OaMsg cleanApproveContentMsg(ApproveContentDto approveContentDto)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			approveContentDao.cleanApproveContent(approveContentDto);
		} catch (RuntimeException e) {
			LOG.error("查询失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",e);
		}
		
		return oaMsg;
	}
}
