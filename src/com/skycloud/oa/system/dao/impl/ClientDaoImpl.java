package com.skycloud.oa.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.ArrivalDto;
import com.skycloud.oa.system.dao.ClientDao;
import com.skycloud.oa.system.dto.ClientDto;
import com.skycloud.oa.system.model.Client;
import com.skycloud.oa.system.model.ClientFile;
import com.skycloud.oa.utils.Common;

@Repository
public class ClientDaoImpl extends BaseDaoImpl implements ClientDao {
	private static Logger LOG = Logger.getLogger(ClientDaoImpl.class);
	@Override
	public List<Map<String, Object>> getClientList(ClientDto cDto,int start,int limit) throws OAException {
		try {
			
			String sql="";
			if(!Common.empty(cDto.getLetter())&&!("all").equals(cDto.getLetter())){
				 sql = "select tpc.*,s.name editUserName ,tpcg.name clientGroupName from t_pcs_client tpc LEFT JOIN t_pcs_client_group tpcg on tpcg.id=tpc.clientGroupId LEFT JOIN t_auth_user s on s.id=tpc.editUserId,t_sys_pinyin b where 1=1 and isnull(tpc.status) ";
				
				String up=cDto.getLetter().toUpperCase();
				
				sql+=" and (tpc.`name` like '"+cDto.getLetter()+"%' or tpc.`name` like '"+up+"%' or CONV(HEX(left(CONVERT(tpc.`name` USING gbk),1)),16,10) between b.charBegin and b.charEnd) and b.letter='"+up+"'";
			}else{
				sql="select tpc.*,s.name editUserName ,tpcg.name clientGroupName from t_pcs_client tpc LEFT JOIN t_pcs_client_group tpcg on tpcg.id=tpc.clientGroupId LEFT JOIN t_auth_user s on s.id=tpc.editUserId where 1=1 and isnull(tpc.status) ";
				
			}
			
//			 sql="select tpc.* ,tpcg.name clientGroupName from t_pcs_client tpc LEFT JOIN t_pcs_client_group tpcg on tpcg.id=tpc.clientGroupId where 1=1";
			if(!Common.empty(cDto.getId())){
				sql+=" and tpc.id="+cDto.getId();
			}
			if(!Common.empty(cDto.getClientGroupId())){
				sql+=" and tpc.clientGroupId ="+cDto.getClientGroupId();
			}
			if(!Common.empty(cDto.getCode())){
				sql+=" and ( tpc.code like '%"+cDto.getCode()+"%' or tpc.name like '%"+cDto.getName()+"%' or  tpc.contactName like '%"+cDto.getContactName()+"%' or tpc.contactPhone like '%"+cDto.getContactPhone()+"%' )";
			}
//			sql += " order by CONVERT( tpc.name USING gbk ) COLLATE gbk_chinese_ci ASC";
			if(limit!=0){
				sql+=" limit "+start+" , "+limit;
			}
			return executeQuery(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
		}
	}
	
	@Override
	public int getClientListCount(ClientDto cDto) throws OAException {
		try {
			
			String sql="";
			if(!Common.empty(cDto.getLetter())&&!("all").equals(cDto.getLetter())){
				 sql = "select count(1) from t_pcs_client tpc ,t_sys_pinyin b where 1=1 and isnull(tpc.status) ";
				
				String up=cDto.getLetter().toUpperCase();
				
				sql+=" and (tpc.`name` like '"+cDto.getLetter()+"%' or tpc.`name` like '"+up+"%' or CONV(HEX(left(CONVERT(tpc.`name` USING gbk),1)),16,10) between b.charBegin and b.charEnd) and b.letter='"+up+"'";
			}else{
				sql="select count(1) from t_pcs_client tpc where 1=1 and isnull(tpc.status) ";
				
			}
			
//			String sql="select count(1) from t_pcs_client tpc where 1=1";
			if(!Common.empty(cDto.getId())){
				sql+=" and tpc.id="+cDto.getId();
			}
			if(!Common.empty(cDto.getCode())){
				sql+=" and ( tpc.code like '%"+cDto.getCode()+"%' or tpc.name like '%"+cDto.getName()+"%' or  tpc.contactName like '%"+cDto.getContactName()+"%' or tpc.contactPhone like '%"+cDto.getContactPhone()+"%' )";
			}
			if(!Common.empty(cDto.getClientGroupId())){
				sql+=" and tpc.clientGroupId ="+cDto.getClientGroupId();
			}
			return (int) getCount(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
		}
	}

	
	
	@Override
	public List<Map<String, Object>> getTreeGoodsList(ArrivalDto cDto) throws OAException {
		try {
			return executeProcedure("call showChildLst("+cDto.getId()+")");
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询货体树失败",e);
		}
	}
	@Override
	public int addClient(Client client) throws OAException {
       try {
		return (Integer) save(client);
   	} catch (RuntimeException e) {
		LOG.error("dao添加失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR,"dao添加失败",e);
	}		
	}

	@Override
	public void updateClient(Client client) throws OAException {
try {
	String sql="update t_pcs_client set id=id";
	if(!Common.empty(client.getAddress())){
		sql+=" ,address='"+client.getAddress()+"'";
	}
	if(!Common.empty(client.getBankAccount())){
		sql+=" ,bankAccount='"+client.getBankAccount()+"'";
	}
	if(!Common.empty(client.getBankName())){
		sql+=" ,bankName='"+client.getBankName()+"'";
	}
	if(!Common.isNull(client.getClientGroupId())){
		sql+=" ,clientGroupId="+client.getClientGroupId();
	}
	if(!Common.empty(client.getCode())){
		sql+=" ,code='"+client.getCode()+"'";
	}
	if(!Common.empty(client.getContactEmail())){
		sql+=" ,contactEmail='"+client.getContactEmail()+"'";
	}
	if(!Common.empty(client.getContactName())){
		sql+=" ,contactName='"+client.getContactName()+"'";
	}
	if(!Common.empty(client.getContactPhone())){
		sql+=" ,contactPhone='"+client.getContactPhone()+"'";
	}
	if(!Common.empty(client.getContactSex())){
		sql+=" ,contactSex='"+client.getContactSex()+"'";
	}
	if(!Common.isNull(client.getCreditGrade())){
		sql+=" ,creditGrade="+client.getCreditGrade();
	}
	if(!Common.empty(client.getDescription())){
		sql+=" ,description='"+client.getDescription()+"'";
	}
	if(!Common.empty(client.getEmail())){
		sql+=" ,email='"+client.getEmail()+"'";
	}
	if(!Common.empty(client.getFax())){
		sql+=" ,fax='"+client.getFax()+"'";
	}
	if(!Common.isNull(client.getGuestId())){
		sql+=" ,guestId="+client.getGuestId();
	}
	if(!Common.empty(client.getLadingSample())){
		sql+=" ,ladingSample='"+client.getLadingSample()+"'";
	}
	if(!Common.empty(client.getName())){
		sql+=" ,name='"+client.getName()+"'";
	}
	if(!Common.isNull(client.getPaymentGrade())){
		sql+=" ,paymentGrade="+client.getPaymentGrade();
	}
	if(!Common.empty(client.getPhone())){
		sql+=" ,phone='"+client.getPhone()+"'";
	}
	if(!Common.empty(client.getPostcode())){
		sql+=" ,postCode='"+client.getPostcode()+"'";
	}
	if(!Common.isNull(client.getStatus())){
		sql+=" ,status="+client.getStatus();
	}
	if(!Common.isNull(client.getType())){
		sql+=" ,type="+client.getType();
	}
	if(!Common.isNull(client.getEditUserId())){
		sql+=" ,editUserId="+client.getEditUserId();
	}
	sql+=" where id="+client.getId();
	
	execute(sql);
} catch (RuntimeException e) {
	LOG.error("dao更新失败");
	throw new OAException(Constant.SYS_CODE_DB_ERR,"dao更新失败",e);
}	
	}

	@Override
	public void deleteClient(String ids) throws OAException {
try {
	String sql="update  t_pcs_client set status=1 where id in ("+ids+")";
	execute(sql);
} catch (RuntimeException e) {
	LOG.error("dao删除失败");
	throw new OAException(Constant.SYS_CODE_DB_ERR,"dao删除失败",e);
}	
	}

	@Override
	public void removeClientGroup(String ids)throws OAException {
		// TODO Auto-generated method stub
		try {
			String sql="update t_pcs_client set clientGroupId=0 where clientGroupId in ("+ids+")";
			execute(sql);
		} catch (OAException e) {
			LOG.error("dao更新失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao更新失败",e);
		}
	}

	@Override
	public void removeGroup(String clientId)throws OAException {
		try {
			String sql="update t_pcs_client set clientGroupId=0 where id in("+clientId+")";
			execute(sql);
		} catch (OAException e) {
			LOG.error("dao更新失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao更新失败",e);
		}
	}

	@Override
	public int checkSame(Client client) throws OAException {
		
		String sql="select count(id) from t_pcs_client where code = '"+client.getCode()+"' or name='"+client.getName()+"'";
		return (int) getCount(sql);
		
		
	}

	@Override
	public int addClientFile(ClientFile clientFile) throws OAException {
	       try {
	   		return (Integer) save(clientFile);
	      	} catch (RuntimeException e) {
	   		LOG.error("dao添加失败");
	   		throw new OAException(Constant.SYS_CODE_DB_ERR,"dao添加失败",e);
	   	}		
	   	}

	@Override
	public void deleteClientFile(String id) throws OAException {
		try {
			String sql="delete from  t_pcs_client_file where id="+id;
			execute(sql);
		} catch (RuntimeException e) {
			LOG.error("dao删除失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao删除失败",e);
		}	
			}

	@Override
	public List<Map<String, Object>> getClientFile(String clientId)
			throws OAException {
		
		String sql="select * from  t_pcs_client_file where clientId="+clientId;
		
		return executeQuery(sql);
	}

}
