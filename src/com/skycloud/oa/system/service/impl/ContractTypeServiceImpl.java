package com.skycloud.oa.system.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.order.dto.ContractDto;
import com.skycloud.oa.order.model.Contract;
import com.skycloud.oa.order.service.ContractService;
import com.skycloud.oa.system.dao.ClientDao;
import com.skycloud.oa.system.dao.ContractTypeDao;
import com.skycloud.oa.system.dto.ClientDto;
import com.skycloud.oa.system.dto.ContractTypeDto;
import com.skycloud.oa.system.model.Client;
import com.skycloud.oa.system.service.ClientService;
import com.skycloud.oa.system.service.ContractTypeService;
import com.skycloud.oa.utils.OaMsg;

@Service
public class ContractTypeServiceImpl implements ContractTypeService {
	private static Logger LOG = Logger.getLogger(ContractTypeServiceImpl.class);

	@Autowired
	private ContractTypeDao contractTypeDao;

	@Override
	public OaMsg getContractTypeList() throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(contractTypeDao.getContractTypeList());
		} catch (RuntimeException re) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	// @Override
	// public OaMsg addClient(Client client) throws OAException {
	// OaMsg oaMsg=new OaMsg();
	// try{ oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
	// clientDao.addClient(client);
	// }catch(RuntimeException re){
	// oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
	// throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
	// }
	// return null;
	// }
	//
	// @Override
	// public OaMsg updateClient(Client client) throws OAException {
	// OaMsg oaMsg=new OaMsg();
	// try{ oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
	// clientDao.updateClient(client);
	// }catch(RuntimeException re){
	// oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
	// throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
	// }
	// return null;
	// }
	//
	// @Override
	// public OaMsg deleteClient(String client) throws OAException {
	// OaMsg oaMsg=new OaMsg();
	// try{ oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
	// clientDao.deleteClient(client);
	// }catch(RuntimeException re){
	// oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
	// throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
	// }
	// return null;
	// }

}
