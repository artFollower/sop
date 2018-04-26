package com.skycloud.oa.inbound.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Where;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.TransDao;
import com.skycloud.oa.inbound.dao.TransportInfoDao;
import com.skycloud.oa.inbound.dto.TankLogDto;
import com.skycloud.oa.inbound.model.Trans;
import com.skycloud.oa.inbound.model.TransportInfo;
import com.skycloud.oa.utils.Common;
@Component
public class TransportInfoDaoImpl extends BaseDaoImpl implements TransportInfoDao {

	private static Logger LOG = Logger.getLogger(TransportInfoDaoImpl.class);
	
	@Override
	public int addTransportInfo(TransportInfo transportInfo) throws OAException {
		try {
			  Serializable s=	save(transportInfo);
			  return Integer.valueOf(s.toString());
			} catch (RuntimeException e) {
			LOG.error("dao添加传输附加信息失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加添加传输附加信息失败", e);
			}
	}

	@Override
	public List<Map<String, Object>> getTransportInfoList(String transportIds)
			throws OAException {
		return null;
	}

	@Override
	public void updateTransportInfo(TransportInfo transportInfo)
			throws OAException {
try {   
	       String sql="update t_pcs_transport_info set id=id";
	       if(!Common.empty(transportInfo.getType())){
	    	 sql+=",type="+transportInfo.getType();  
	       }
	       if(!Common.empty(transportInfo.getTransportId())){
		    	 sql+=",transportId="+transportInfo.getTransportId();  
		       }
	       if(!Common.empty(transportInfo.getOutTankNames())){
		    	 sql+=",outTankNames='"+transportInfo.getOutTankNames()+"'";  
		       }
	       if(!Common.empty(transportInfo.getOutTankIds())){
		    	 sql+=",outTankIds='"+transportInfo.getOutTankIds()+"'";  
		       }
	       if(!Common.empty(transportInfo.getInTankNames())){
		    	 sql+=",inTankNames='"+transportInfo.getInTankNames()+"'";  
		       }
	       if(!Common.empty(transportInfo.getInTankIds())){
		    	 sql+=",inTankIds='"+transportInfo.getInTankIds()+"'";  
		       }
	       if(!Common.empty(transportInfo.getPupmNames())){
		    	 sql+=",pupmNames='"+transportInfo.getPupmNames()+"'";  
		       }
	       if(!Common.empty(transportInfo.getPupmIds())){
		    	 sql+=",pupmIds='"+transportInfo.getPupmIds()+"'";  
		       }
	       if(!Common.empty(transportInfo.getTubeNames())){
		    	 sql+=",tubeNames='"+transportInfo.getTubeNames()+"'";  
		       }
	       if(!Common.empty(transportInfo.getTubeIds())){
		    	 sql+=",tubeIds='"+transportInfo.getTubeIds()+"'";  
		       }
	       if(!Common.empty(transportInfo.getTankCount())){
		    	 sql+=",tankCount="+transportInfo.getTankCount();  
		       }
	       if(!Common.empty(transportInfo.getMessage())){
		    	 sql+=",message='"+transportInfo.getMessage()+"'";  
		       }
	       if(!Common.empty(transportInfo.getTubeTask())){
		    	 sql+=",tubeTask='"+transportInfo.getTubeTask()+"'";  
		       }
	       if(!Common.empty(transportInfo.getTubeState())){
		    	 sql+=",tubeState='"+transportInfo.getTubeState()+"'";  
		       }
	       if(!Common.empty(transportInfo.getDescription())){
	    	   sql+=",description='"+transportInfo.getDescription()+"'";
	       }
	       if(!Common.empty(transportInfo.getTransferPurpose()))
	    	   sql+=",transferPurpose='"+transportInfo.getTransferPurpose()+"'";
	       sql+=" where id="+transportInfo.getId();
	       executeUpdate(sql);
		} catch (RuntimeException e) {
			LOG.error("dao更新失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新传输附加信息失败",e);
		}
	}

	@Override
	public int getTransportInfoListCount(TankLogDto tankLogDto)
			throws OAException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteTransportInfoByTransPortId(Integer id) throws OAException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cleanTransportInfo(int id, boolean b) throws OAException {
		// TODO Auto-generated method stub
		
	}


}
