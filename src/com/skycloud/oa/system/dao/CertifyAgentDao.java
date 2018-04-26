package com.skycloud.oa.system.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dto.CertifyAgentDto;
import com.skycloud.oa.system.model.CertifyAgent;


public interface CertifyAgentDao{

public List<Map<String,Object>> getCertifyAgentList(CertifyAgentDto caDto,int start,int limit)throws OAException;
public int getCertifyAgentCount(CertifyAgentDto caDto,int start,int limit)throws OAException;

public void addCertifyAgent(CertifyAgent certifyAgent)throws OAException;

public void updateCertifyAgent(CertifyAgent certifyAgent)throws OAException;

public void deleteCertifyAgent(String ids)throws OAException;
}
