package com.skycloud.oa.message.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.message.dao.MessageDao;
import com.skycloud.oa.message.dto.MessageDto;
import com.skycloud.oa.message.model.MessageContent;
import com.skycloud.oa.utils.Common;
@Component
public class MessageDaoImpl extends BaseDaoImpl implements MessageDao {

	@Override
	public int addMessageContent(MessageContent message) throws OAException {
		
		return Integer.parseInt(save(message).toString());
		
	}
	
	
	@Override
	public void createMessage(MessageDto messageDto ,List<String> getUserIdList) throws OAException {
		String sql="INSERT into t_pcs_message_content (type,taskId,sendUserId,content,url,createTime) values(" +
				messageDto.getType()+","+messageDto.getTaskId()+","+messageDto.getSendUserId()+",'"+messageDto.getContent()+"','"+messageDto.getUrl()+"',"+messageDto.getCreateTime()+")";
				execute(sql);
				
				
					String sql1="INSERT into t_pcs_message_get (messageId,getUserId,status) values ";
					for(int i=0;i<getUserIdList.size();i++){
					sql1+="((SELECT MAX(id) FROM t_pcs_message_content),"+getUserIdList.get(i)+",1),";
					}
					sql1=sql1.substring(0,sql1.length()-1);
					execute(sql1);
	}
	
	@Override
	public void createSysMessage(int id,
			String content) throws OAException {
		String sql="INSERT into t_pcs_message_get (messageId,getUserId,status) SELECT "+id+",id,1 FROM t_auth_user";
//		time=time.split(".")[0];
//		String sql="INSERT into t_pcs_message_content (type,taskId,sendUserId,content,url,createTime) values(" +
//				2+","+0+","+0+",'"+content+"','',"+time+");INSERT into t_pcs_message_get (messageId,getUserId,status) SELECT @@IDENTITY,id,1 FROM t_auth_user" ;//+
//				"INSERT into t_pcs_message_get (messageId,getUserId,status) values ";
//				for(int i=0;i<userInfoList.size();i++){
//					sql+="(@@IDENTITY,"+userInfoList.get(i).get("id")+",'1'),";
//				}
//				sql=sql.substring(0,sql.length()-1);
				try {
					execute(sql);
					
				} catch (Exception e) {
					e.printStackTrace();
					throw new OAException(Constant.SYS_CODE_NULL_ERR, "失败");
				}
		
	}

	@Override
	public List<Map<String, Object>> get(MessageDto messageDto, long start,
			long limit) throws OAException {
		String sql="select a.*,b.content,b.url,b.sendUserId,c.name sendUserName,from_unixtime(b.createTime) createTime,b.type from t_pcs_message_get a " +
				" LEFT JOIN t_pcs_message_content b on b.id=a.messageId " +
				" LEFT JOIN t_auth_user c on c.id=b.sendUserId where 1=1 ";
				if(!Common.isNull(messageDto.getStatus())){
					sql+=" and a.status="+messageDto.getStatus();
				}
				if(!Common.isNull(messageDto.getGetUserId())){
					sql+=" and a.getUserId="+messageDto.getGetUserId();
				}
				if(!Common.isNull(messageDto.getType())){
					sql+=" and b.type="+messageDto.getType();
				}
				sql+=" order by b.createTime desc";
				if(limit!=0){
					sql+=" limit "+start+","+limit;
				}
		return executeQuery(sql);
	}

	@Override
	public long count(MessageDto messageDto) throws OAException {
		String sql="select count(*) from t_pcs_message_get a " +
				" LEFT JOIN t_pcs_message_content b on b.id=a.messageId " +
				" LEFT JOIN t_auth_user c on c.id=b.sendUserId where 1=1 ";
				if(!Common.isNull(messageDto.getStatus())){
					sql+=" and a.status="+messageDto.getStatus();
				}
				if(!Common.isNull(messageDto.getGetUserId())){
					sql+=" and a.getUserId="+messageDto.getGetUserId();
				}
				if(!Common.isNull(messageDto.getType())){
					sql+=" and b.type="+messageDto.getType();
				}
				sql+=" order by b.createTime desc";
		return getCount(sql);
	}

	@Override
	public boolean delete(String ids) throws OAException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateReadStatus(String ids) throws OAException {
		String sql="update t_pcs_message_get set status=2 where id in("+ids+")";
		
		return executeUpdate(sql)>0?true:false;
	}

	@Override
	public List<Map<String, Object>> getCount(MessageDto messageDto)
			throws OAException {
		int id=messageDto.getGetUserId();
		String sql="select (select count(*) from t_pcs_message_get where status=1 and getUserId="+id+") allCount,(select count(*) from t_pcs_message_get a LEFT JOIN t_pcs_message_content b on a.messageId=b.id where a.status=1 and b.type=1 and a.getUserId="+id+") taskCount,(select count(*) from t_pcs_message_get a LEFT JOIN t_pcs_message_content b on a.messageId=b.id where a.status=1 and b.type=2 and a.getUserId="+id+") systemCount";
		
		return executeQuery(sql);
	}


	@Override
	public void addMessageBysSQL(String sql) throws OAException {
		try {
			execute(sql);
		} catch (RuntimeException e) {
			throw new OAException(Constant.SYS_CODE_DB_ERR, "添加消息失败",e);
		}
	}


	@Override
	public boolean setAllRead(int id) throws OAException {
		String sql="update t_pcs_message_get set status=2 where getUserId="+id;
		
		return executeUpdate(sql)>0?true:false;
	}





}
