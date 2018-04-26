package com.skycloud.oa.system.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.common.dao.BaseControllerDao;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.ArrivalDto;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dao.ClientDao;
import com.skycloud.oa.system.dto.ClientDto;
import com.skycloud.oa.system.model.Client;
import com.skycloud.oa.system.model.ClientFile;
import com.skycloud.oa.system.service.ClientService;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;

@Service
public class ClientServiceImpl implements ClientService {
	private static Logger LOG = Logger.getLogger(ClientServiceImpl.class);

	@Autowired
	private ClientDao clientDao;
	@Autowired
	private BaseControllerDao baseControllerDao;
	@Override
	public OaMsg getClientList(ClientDto cDto,PageView pageView)
			throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(clientDao.getClientList(cDto, pageView.getStartRecord(),pageView.getMaxresult()));
			int count=clientDao.getClientListCount(cDto);
			Map<String, String> map = new HashMap<String, String>();
			map.put("currentPage", pageView.getCurrentpage()+"");
			map.put("totalpage", pageView.getTotalpage()+"");
			map.put("totalRecord", count + "");
			oaMsg.setMap(map);
		} catch (RuntimeException re) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}
	
	@Override
	public OaMsg getTreeGoods(ArrivalDto cDto)
			throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(clientDao.getTreeGoodsList(cDto));
		} catch (RuntimeException re) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}
	@Override
	@Log(object=C.LOG_OBJECT.PCS_CLIENT,type=C.LOG_TYPE.CREATE)
	public OaMsg addClient(Client client) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			
			int count=clientDao.checkSame(client);
			if(count>0){
				oaMsg.setCode(Constant.SYS_CODE_DISABLED);
				return oaMsg;
			}
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			client.setEditUserId(user.getId()+"") ;
			client.setCode(Common.getAllFirstLetter(client.getName()));
			int id=clientDao.addClient(client);
			
			
//			ClientDto cDto=new ClientDto();
//			List<Map<String,Object>> clientList=clientDao.getClientList(cDto, 0, 0);
//			
//			for(int i=0 ;i<clientList.size();i++){
//				Client client1=new Client();
//				client1.setId(Integer.parseInt(clientList.get(i).get("id").toString()));
//				client1.setCode(Common.getAllFirstLetter(clientList.get(i).get("name").toString()));
//				clientDao.updateClient(client1);
//			}
			
			oaMsg.setMsg("添加成功");
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id+"");
			oaMsg.setMap(map);
		} catch (RuntimeException re) {
			LOG.error("添加失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("添加失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_CLIENT,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateClient(Client client) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			client.setEditUserId(user.getId()+"") ;
			client.setCode(Common.getAllFirstLetter(client.getName()));
			clientDao.updateClient(client);
			baseControllerDao.sendSystemMessage("客户", user.getId(),"BASEINFO",1) ;
		} catch (RuntimeException re) {
			LOG.error("更新失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("更新失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_CLIENT,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteClient(String client) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			clientDao.deleteClient(client);
		} catch (RuntimeException re) {
			LOG.error("删除失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("删除失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_CLIENT,type=C.LOG_TYPE.DELETE)
	public OaMsg removeGroup(String clientId) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			clientDao.removeGroup(clientId);
		} catch (RuntimeException re) {
			LOG.error("删除失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("删除失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	public OaMsg addFile(ClientFile clientFile) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			
			int id=clientDao.addClientFile(clientFile);
			
			
			oaMsg.setMsg("添加成功");
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id+"");
			oaMsg.setMap(map);
		} catch (RuntimeException re) {
			LOG.error("添加失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("添加失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	public OaMsg deleteClientFile(String id, String url) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			clientDao.deleteClientFile(id);
			
			if(deleteFile(url)){
				oaMsg.setMsg("删除成功");
			}
			
			
		} catch (RuntimeException re) {
			LOG.error("删除失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("删除失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}
	/** 
	 * 删除单个文件 
	 * @param   sPath    被删除文件的文件名 
	 * @return 单个文件删除成功返回true，否则返回false 
	 */  
	public boolean deleteFile(String sPath) {  
	    boolean flag = false;  
	    
	    sPath=sPath.replace("//", "\\");
	    sPath=sPath.replace("/", "\\");
	    File file = new File(sPath);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	}

	@Override
	public OaMsg getClientFile(String clientId) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(clientDao.getClientFile(clientId));
			Map<String, String> map = new HashMap<String, String>();
			oaMsg.setMap(map);
		} catch (RuntimeException re) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}  
}
