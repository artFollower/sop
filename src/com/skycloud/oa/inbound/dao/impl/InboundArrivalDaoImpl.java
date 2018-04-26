package com.skycloud.oa.inbound.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.InboundArrivalDao;
import com.skycloud.oa.inbound.dto.InboundOperationDto;
import com.skycloud.oa.inbound.model.Arrival;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;
@Component
public class InboundArrivalDaoImpl extends BaseDaoImpl implements InboundArrivalDao {

	private static Logger LOG = Logger.getLogger(InboundArrivalDaoImpl.class);

	@Override
	public List<Map<String, Object>> getInboundArrivalList(InboundOperationDto ioDto,int start,int limit) throws OAException {
		try {
		String sql="select ";
		    switch (ioDto.getResult()) {
			case 0://只关联到原始接卸不关联打循环和多次接卸内容
				//入库管理列表
				           //arrival到港信息
				     sql+="a.id,a.shipId shipId,a.type arrivalType,b.code shipCode,b.name shipName,b.shipLenth shipLenth,b.shipWidth shipWidth, b.shipDraught shipDraught, b.netTons netTons,b.loadCapacity loadCapacity,b.port port, a.shipAgentId shipAgentId,bb.code shipAgentCode,bb.name shipAgentName,c.refName shipRefName,DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d %H:%i:%s') arrivalStartTime,a.status statusKey,j.value statusValue,"
				           //货批信息
						+ "d.id cargoId,d.productId productId,e.code productCode,e.name productName,d.contractId contractId,d.cargoAgentId cargoAgentId,dd.code cargoAgentCode,d.clientId clientId,f.code clientCode,f.name clientName,d.code cargoCode,d.goodsPlan goodsPlan,d.description description,"
						   //靠泊方案
						+ "h.id berthprogramId,h.berthId berthId, i.name berthName,h.status bhpnreviewStatus,"
						   //靠泊评估
						+ "k.id berthassessId,k.reviewStatus bhasreviewStatus,"
						   //入库计划
						+ "l.id arrivalinfoId,from_unixtime(l.anchorTime) anchorTime,l.report report,l.shipArrivalDraught shipArrivalDraught,"
						   //接卸方案
						+ "w.id transportprogramId, w.status ttpmreviewStatus,w.orderNum,"
						   //打循环方案
						+ ""
						   //接卸作业表（数量确认）
						+ "aa.id workId,aa.leaveTime, aa.`status` itemStatusKey,aa.reviewStatus atamreviewStatus,a.isCanBack isCanBack"
						+ " from t_pcs_arrival a "
			    		+ " LEFT JOIN t_pcs_ship b on a.shipId=b.id "//船舶资料表
			    		+ " LEFT JOIN t_pcs_ship_ref c on a.shipRefId=c.id "//船中文名表
			    		+ " LEFT JOIN t_pcs_cargo d on a.id=d.arrivalId "//货品表
			    		+ " LEFT JOIN t_pcs_cargo_agent dd on d.cargoAgentId=dd.id"//货代
		    			+ " LEFT JOIN t_pcs_product e on d.productId=e.id "//货品资料
		    			+ " LEFT JOIN t_pcs_client f on d.clientId=f.id "//客户表
		    			+ " LEFT JOIN t_pcs_arrival_status j on a.`status` = j. `key`"//到港信息
			    		+ " LEFT JOIN t_pcs_berth_program h on a.id=h.arrivalId "//靠泊方案
			    		+ " LEFT JOIN t_pcs_berth i on h.berthId=i.id "//泊位
			    		+ " LEFT JOIN t_pcs_berth_assess k on k.arrivalId=a.id "//靠泊评估
			    		+ " LEFT JOIN t_pcs_arrival_info l on l.arrivalId=a.id "//到港附加信息
			    		+ " LEFT JOIN t_pcs_transport_program w on w.arrivalId=a.id and w.type=0 and w.productId=d.productId ";//接卸
			    		  if(ioDto.getOrderNum()!=null){
			    			  sql+=" and w.orderNum="+ioDto.getOrderNum();
			    		  }else{
			    			  sql+=" and w.orderNum=0";
			    		  }
						sql+= " LEFT JOIN t_pcs_work aa on aa.arrivalId=a.id and aa.productId=d.productId ";//入库作业表
						if(ioDto.getOrderNum()!=null){
			    			  sql+=" and aa.orderNum="+ioDto.getOrderNum();
			    		  }else{
			    			  sql+=" and aa.orderNum=0";
			    		  }
					     if(!Common.empty(ioDto.getProductId())&&!Common.isNull(ioDto.getProductId())){
					    	 sql+=" and aa.productId="+ioDto.getProductId();
					     }
						 if(!Common.empty(ioDto.getStatuskey())&&!Common.isNull(ioDto.getStatuskey())){
							    if(ioDto.getStatuskey().equals("3")){
							    	sql+=" and aa.status in (2) and (k.reviewStatus!=0 and !isNull(k.reviewStatus))";
							    } else if(ioDto.getStatuskey().equals("3,4")){
							    	sql+=" and aa.status in (2) and (k.reviewStatus=0 or isNull(k.reviewStatus))";
							    }else{
							    	sql+=" and aa.status in ("+ioDto.getStatuskey()+")";	
							    }
			                }
			    	sql+=" LEFT JOIN t_pcs_ship_agent bb on bb.id=a.shipAgentId"//船代
		    			+" where 1=1  ";
		    			
		    			if(ioDto.getArrivalType()!=null&&ioDto.getArrivalType()!=0){
		    				sql+=" and a.type="+ioDto.getArrivalType();
		    				
		    			}else if(Common.isNull(ioDto.getId())){
		    				sql+=" and (a.type=1 or a.type=3) ";// 入库和通过
		    			}
//		    			+ "and (a.type=1 or a.type=3)"//表示入库
		    			sql+= " and !ISNULL(aa.id) "//入库作业不嫩为空
		    			+ "and  a.status>1 and a.status<11 ";//流程范围
		    			if(!Common.empty(ioDto.getShipId())&&!Common.isNull(ioDto.getShipId())){
		    		    	sql+=" and a.shipId="+ioDto.getShipId();
		    		    }
		    			if(!Common.isNull(ioDto.getShipName())){
		    				sql+=" and ( b.name like '%"+ioDto.getShipName()+"%' or c.refName like '%"+ioDto.getShipName()+"%') ";
		    			}
		    			if(!Common.isNull(ioDto.getIsTransport())&&ioDto.getIsTransport()==2){
		    				sql+=" and c.refName='转输'";
		    			}else{
		    				sql+=" and c.refName!='转输'";
		    			}
		    		    if(!Common.empty(ioDto.getProductId())&&!Common.isNull(ioDto.getProductId())){
		    		    	sql+=" and a.id in ( select g.arrivalId from t_pcs_cargo g where productId="+ioDto.getProductId()+") ";
		    		    }
		    		    if(!Common.empty(ioDto.getStartTime())){
		    		    	sql+=" and  a.arrivalStartTime >='"+ioDto.getStartTime()+" 00:00:00'";
		    		    }
		                if(!Common.empty(ioDto.getEndTime())){
		                	sql+=" and a.arrivalStartTime <='"+ioDto.getEndTime()+" 23:59:59'";
		                } 
		                if(!Common.empty(ioDto.getStatuskey())&&!Common.isNull(ioDto.getStatuskey())){
		                	if(ioDto.getStatuskey().equals("2")||ioDto.getStatuskey().equals("3")||ioDto.getStatuskey().equals("4"))
		                	sql+=" and a.status in ("+ioDto.getStatuskey()+")";
		                }
		                if(!Common.empty(ioDto.getId())&&!Common.isNull(ioDto.getId())){
		                	sql+=" and a.id="+ioDto.getId();
		                }
		                sql+=" and  a.id  in ( SELECT t.id FROM (SELECT DISTINCT a.id FROM t_pcs_arrival a "
		    		+ " LEFT JOIN t_pcs_berth_assess k on k.arrivalId=a.id "
		    		+ " LEFT JOIN t_pcs_cargo d on a.id=d.arrivalId "//货品表
		    		+ " LEFT JOIN t_pcs_ship b on a.shipId=b.id "
		    		+ " LEFT JOIN t_pcs_ship_ref c on a.shipRefId=c.id "
                    + " LEFT JOIN t_pcs_work aa on aa.arrivalId=a.id and aa.productId=d.productId AND aa.orderNum = 0 ";
		                if(!Common.empty(ioDto.getProductId())&&!Common.isNull(ioDto.getProductId())){
					    	 sql+=" and aa.productId="+ioDto.getProductId();
					     }
		                //处理接线方案，接卸准备，数量审核
			        if(!Common.empty(ioDto.getStatuskey())&&!Common.isNull(ioDto.getStatuskey())){
			        	 if(ioDto.getStatuskey().equals("3")){
						    	sql+=" and aa.status in (2) and (k.reviewStatus!=0 and !isNull(k.reviewStatus))";
						    } else if(ioDto.getStatuskey().equals("3,4")){
						    	sql+=" and aa.status in (2) and (k.reviewStatus=0 or isNull(k.reviewStatus))";
						    }else{
						    	sql+=" and aa.status in ("+ioDto.getStatuskey()+")";	
						    }
                    }
			        sql+=" where 1=1 ";
	    			
	    			if(ioDto.getArrivalType()!=null&&ioDto.getArrivalType()!=0){
	    				sql+=" and a.type="+ioDto.getArrivalType();
	    				
	    			}else{
	    				sql+=" and (a.type=1 or a.type=3) ";// 入库和通过
	    			}
			        sql+= " and !ISNULL(aa.id) and a.status>1 and a.status<11 ";
					if(!Common.empty(ioDto.getShipId())&&!Common.isNull(ioDto.getShipId())&&!ioDto.getShipId().equals("0")){
	    		    	sql+=" and a.shipId="+ioDto.getShipId();
	    		    }
					if(!Common.isNull(ioDto.getShipName())){
	    				sql+=" and ( b.name like '%"+ioDto.getShipName()+"%' or c.refName like '%"+ioDto.getShipName()+"%') ";
	    			}
					if(!Common.isNull(ioDto.getIsTransport())&&ioDto.getIsTransport()==2){
	    				sql+=" and c.refName='转输'";
	    			}else if(Common.isNull(ioDto.getId())){
	    				sql+=" and c.refName!='转输'";
	    			}
	    		    if(!Common.empty(ioDto.getStartTime())){
	    		    	sql+=" and  a.arrivalStartTime >='"+ioDto.getStartTime()+" 00:00:00'";
	    		    }
	                if(!Common.empty(ioDto.getEndTime())){
	                	sql+=" and a.arrivalStartTime <='"+ioDto.getEndTime()+" 23:59:59'";
	                } 
	                //处理入库计划，靠泊方案 ，接卸方案情况
	                if(!Common.empty(ioDto.getStatuskey())&&!Common.isNull(ioDto.getStatuskey())){
	                	if(ioDto.getStatuskey().equals("2")){
		                	sql+=" and a.status in ("+ioDto.getStatuskey()+")";
	                	}else if(ioDto.getStatuskey().equals("3")){
	                		sql+=" and a.status in ("+ioDto.getStatuskey()+") and (k.reviewStatus!=0 and !isNull(k.reviewStatus))";
	                	}else if(ioDto.getStatuskey().equals("3,4")){
	                		sql+=" and a.status in (3,4) and (k.reviewStatus=0 or isNull(k.reviewStatus))";
	                	}
	                }
	                
	                if(!Common.empty(ioDto.getId())&&!Common.isNull(ioDto.getId())){
	                	sql+=" and a.id="+ioDto.getId();
	                }
					sql+= " order by a.arrivalStartTime desc LIMIT "+start+","+limit+") as t ) ";
		    		    sql+=" order by a.arrivalStartTime desc";
				break;
			case 1:
				//入库计划
				sql+="l.id arrivalinfoId,from_unixtime(l.cjTime) cjTime,from_unixtime(l.tcTime) tcTime,from_unixtime(l.norTime) norTime,"
						+ " from_unixtime(l.anchorDate) anchorDate,from_unixtime(l.anchorTime) anchorTime,from_unixtime(l.pumpOpenTime) pumpOpenTime,"
						+ " from_unixtime(l.pumpStopTime) pumpStopTime,"
						+ " l.workTime workTime,from_unixtime(l.leaveTime) leaveTime,from_unixtime(l.tearPipeTime) tearPipeTime,"
						+ " l.port port,l.portType portType,l.portNum portNum,l.report report,l.shipInfo shipInfo,l.note note,l.overTime overTime,l.repatriateTime repatriateTime,"
						+ " from_unixtime(l.lastLeaveTime) lastLeaveTime,l.lastLeaveTime mLastLeaveTime,l.createUserId createUserId, p.name createUserName,from_unixtime(l.createTime) createTime,"
						+ " l.reviewUserId reviewUserId,q.name reviewName"
						+ " from t_pcs_arrival_info l "
						+" LEFT JOIN t_auth_user p on l.createUserId=p.id "
    					+ " LEFT JOIN t_auth_user q on l.reviewUserId=q.id "
						+ " where 1=1 ";
				   if(!Common.empty(ioDto.getId())&&!Common.isNull(ioDto.getId())){
	                	sql+=" and l.id="+ioDto.getId();
	                }
				   if(!Common.isNull(ioDto.getArrivalId())){
					   sql+=" and l.arrivalId="+ioDto.getArrivalId();
				   }
				break;
			case 2:
				//靠泊评估
				sql+="k.id id, k.weather weather,k.windDirection windDirection,k.windPower windPower,k.reason reason,k.createUserId createUserId,"
						+ "m.name createUserName,from_unixtime(k.createTime) createTime,k.security security,k.comment comment, n.name reviewUserName,from_unixtime(k.reviewTime) reviewTime,k.reviewStatus bhasreviewStatus"
						+ " from t_pcs_berth_assess k "
						+ " LEFT JOIN t_auth_user n on k.reviewUserId=n.id"
                		+ " LEFT JOIN t_auth_user m on k.createUserId=m.id"
                		+ " where 1=1 ";
				if(!Common.empty(ioDto.getId())&&!Common.isNull(ioDto.getId())){
                	sql+=" and k.id="+ioDto.getId();
                }
				
				
				break;
			case 3:
				//靠泊方案
				sql+="h.berthId berthId,h.safeInfo safeInfo,h.comment comment,h.richDraught,h.windPower,h.status,h.createUserId createUserId,r.name createUserName,from_unixtime(h.createTime,'%Y-%m-%d') createTime,h.reviewUserId reviewUserId,o.name reviewUserName,from_unixtime(h.reviewTime,'%Y-%m-%d') reviewTime"
						+ " from t_pcs_berth_program h"
						//+ " LEFT JOIN t_pcs_dispatch z ON h.createTime-43200 < z.time<h.createTime+43200 "
						+ " LEFT JOIN t_auth_user o on h.reviewUserId=o.id" 
                		+ " LEFT JOIN t_auth_user r on h.createUserId=r.id"
						+ " where 1=1 ";
				if(!Common.empty(ioDto.getId())&&!Common.isNull(ioDto.getId())){
				sql+=" and h.id="+ioDto.getId();
                	//sql+=" and h.id="+ioDto.getId()+" and z.sWindPower LIMIT 1";
                }
				break;
			case 4:
				//接卸方案
				sql+=" s.id id,s.type,s.flow flow,s.svg svg,s.node node,s.comment comment,s.tubeInfo tubeInfo,s.tankInfo tankInfo,s.dockWork dockWork,s.tubeWork,s.powerWork powerWork,s.noticeCodeA noticeCodeA,s.noticeCodeB noticeCodeB,"
						+ "s.createUserId createUserId,t.name createUserName,from_unixtime(s.createTime,'%Y-%m-%d') createTime,s.reviewUserId reviewUserId,u.name reviewUserName,from_unixtime(s.reviewTime,'%Y-%m-%d') reviewTime,"
						+ "s.reviewCraftUserId reviewCraftUserId,v.name reviewCraftUserName,from_unixtime(s.reviewCraftTime,'%Y-%m-%d') reviewCraftTime,s.productId productId, w.name productName,s.status status,"
						+" a.id infoId,a.type transType,a.outTankNames,a.outTankIds,a.inTankNames,a.inTankIds,a.tubeNames infotubeNames,a.tubeIds infotubeIds,a.pupmNames,a.pupmIds,a.tankCount,a.message,a.tubeTask,a.tubeState,a.description,a.transferPurpose"
						+",from_unixtime(s.openPumpTime) openPumpTime,from_unixtime(s.stopPumpTime) stopPumpTime  "
						+ " from t_pcs_transport_program s "
						+ " LEFT JOIN t_auth_user t on s.createUserId=t.id"
						+ " LEFT JOIN t_auth_user u on s.reviewUserId=u.id"
						+ " LEFT JOIN t_auth_user v on s.reviewCraftUserId=v.id"
						+ " LEFT JOIN t_pcs_transport_info a on a.transportId=s.id"
						+ " LEFT JOIN t_pcs_product w on s.productId=w.id"
						+ " where 1=1 ";
				if(!Common.empty(ioDto.getId())&&!Common.isNull(ioDto.getId())){
                	sql+=" and s.id ="+ioDto.getId();
                }
				break;
			case 6:
				//数量确认
				sql+=" a.id id,a.transportId transportId,a.tankId tankId,b.code tankCode,b.productId productId ,c.code productCode,c.name productName,"
						+ "a.realAmount realAmount"
				+ " from t_pcs_store a "
				+ " LEFT JOIN t_pcs_tank b on a.tankId=b.id"
				+ " LEFT JOIN t_pcs_product c on b.productId=c.id"
				+ " where transportId in ("+ioDto.getTransportIds()+")";//传输id
			case 7:
				if(ioDto.getUnloadingType()==1){//多次接卸，获取transportId,productId,arrivalId,orderNum,workId
					sql+=" a.id transportId,a.orderNum,b.id workId,b.status status,b.reviewStatus from t_pcs_transport_program a ,t_pcs_work b where a.type=0 "
                    + " and  a.arrivalId="+ioDto.getArrivalId()+" and a.productId="+ioDto.getProductId()
                    + " and a.arrivalId=b.arrivalId and a.productId=b.productId and a.orderNum=b.orderNum order by a.id asc";
				}else if(ioDto.getUnloadingType()==2){
					sql+=" id backflowId,orderNum,status  FROM t_pcs_transport_program WHERE arrivalId="+ioDto.getArrivalId()+" and productId="+ioDto.getProductId()
							+ " and type=1  order by id asc";
				}
			default:
				break;
			}
		    LOG.debug(sql);
		return executeQuery(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}

	@Override
	public boolean checkInboundArrival(int arrivalId) throws OAException {
		try{
			String sql="select b.id arrivalinfoId,c.id berthassessId,d.id berthprogramId,e.id transportId,f.id workId from t_pcs_arrival a"
					+ " LEFT JOIN t_pcs_arrival_info b  on a.id=b.arrivalId "
					+ " LEFT JOIN t_pcs_berth_assess c on a.id=c.arrivalId "
					+ " LEFT JOIN t_pcs_berth_program d on a.id=d.arrivalId "
					+ " LEFT JOIN t_pcs_transport_program e on a.id=e.arrivalId "
					+ " LEFT JOIN t_pcs_work f on a.id=f.arrivalId "
					+ " where a.id="+arrivalId;
		List<Map<String,Object>> data=executeQuery(sql);
			Map<String,Object> map=data.get(0);
			if(map.get("arrivalinfoId").toString().equals("0")&&map.get("berthassessId").toString().equals("0")&&map.get("berthprogramId").toString().equals("0")&&map.get("transportId").toString().equals("0")&&map.get("workId").toString().equals("0")){
				return true;
			}else{
			return false;
			}
		}catch(RuntimeException e){
			LOG.error("dao校验");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao校验",e);
		}
	}

	@Override
	public int getInboundArrivalList(InboundOperationDto ioDto)
			throws OAException {
		try{
			String sql="select ";
		    switch (ioDto.getResult()) {
			case 0:
				//入库管理列表
		    		        sql+=" count( DISTINCT a.id) FROM t_pcs_arrival a "
		    		    		+ " LEFT JOIN t_pcs_berth_assess k on k.arrivalId=a.id "
		    		    		+ " LEFT JOIN t_pcs_ship b on a.shipId=b.id "//船英文	
		    		    		+ " LEFT JOIN t_pcs_ship_ref c on a.shipRefId=c.id "//船中文名表
		    		    		+ " LEFT JOIN t_pcs_cargo d on a.id=d.arrivalId "//货品表
		                        + " LEFT JOIN t_pcs_work aa on aa.arrivalId=a.id and aa.productId=d.productId and aa.orderNum = 0 ";
		    		        if(!Common.empty(ioDto.getProductId())&&!Common.isNull(ioDto.getProductId())){
						    	 sql+=" and aa.productId="+ioDto.getProductId();
						     }
		    			        if(!Common.empty(ioDto.getStatuskey())&&!Common.isNull(ioDto.getStatuskey())){
		    			        	 if(ioDto.getStatuskey().equals("3")){
		 						    	sql+=" and aa.status in (2) and (k.reviewStatus!=0 and !isNull(k.reviewStatus))";
		 						    } else if(ioDto.getStatuskey().equals("3,4")){
		 						    	sql+=" and aa.status in (2) and (k.reviewStatus=0 or isNull(k.reviewStatus))";
		 						    }else{
		 						    	sql+=" and aa.status in ("+ioDto.getStatuskey()+")";	
		 						    }	
		                        }
		    			        sql+=" where 1=1 ";
				    			
				    			if(ioDto.getArrivalType()!=null&&ioDto.getArrivalType()!=0){
				    				sql+=" and a.type="+ioDto.getArrivalType();
				    				
				    			}else{
				    				sql+=" and (a.type=1 or a.type=3) ";// 入库和通过
				    			}
		    			        		sql+= " and !ISNULL(aa.id) and a.status>1 and a.status<11 ";
		    					if(!Common.empty(ioDto.getShipId())&&!Common.isNull(ioDto.getShipId())){
		    	    		    	sql+=" and a.shipId="+ioDto.getShipId();
		    	    		    }
		    					if(!Common.isNull(ioDto.getShipName())){
		    	    				sql+=" and ( b.name like '%"+ioDto.getShipName()+"%' or c.refName like '%"+ioDto.getShipName()+"%') ";
		    	    			}
		    					if(!Common.isNull(ioDto.getIsTransport())&&ioDto.getIsTransport()==2){
				    				sql+=" and c.refName='转输'";
				    			}else if(Common.isNull(ioDto.getId())){
				    				sql+=" and c.refName!='转输'";
				    			}
//		    	    		    if(!Common.empty(ioDto.getProductId())&&!Common.isNull(ioDto.getProductId())){
//		    	    		    	sql+=" and a.id in ( select g.arrivalId from t_pcs_cargo g where productId="+ioDto.getProductId()+") ";
//		    	    		    }
		    	    		    if(!Common.empty(ioDto.getStartTime())){
		    	    		    	sql+=" and  a.arrivalStartTime >='"+ioDto.getStartTime()+" 00:00:00'";
		    	    		    }
		    	                if(!Common.empty(ioDto.getEndTime())){
		    	                	sql+=" and a.arrivalStartTime <='"+ioDto.getEndTime()+" 23:59:59'";
		    	                } 
		    	                if(!Common.empty(ioDto.getStatuskey())&&!Common.isNull(ioDto.getStatuskey())){
		    	                	if(ioDto.getStatuskey().equals("2")){
		    		                	sql+=" and a.status in ("+ioDto.getStatuskey()+")";
		    	                	}else if(ioDto.getStatuskey().equals("3")){
		    	                		sql+=" and a.status in ("+ioDto.getStatuskey()+") and (k.reviewStatus!=0 and !isNull(k.reviewStatus))";
		    	                	}else if(ioDto.getStatuskey().equals("3,4")){
		    	                		sql+=" and a.status in (3,4) and (k.reviewStatus=0 or isNull(k.reviewStatus))";
		    	                	}
		    	                }
		    	                if(!Common.empty(ioDto.getId())&&!Common.isNull(ioDto.getId())){
		    	                	sql+=" and a.id="+ioDto.getId();
		    	                }
		    					sql+= " order by a.arrivalStartTime desc ";
		    		    
		    		    
				break;
			default:
				break;
			}
		    LOG.debug(sql);
			return (int) getCount(sql);
		}catch(RuntimeException e){
			LOG.debug("dao获取数量");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao获取数量",e);
		}
		
	}

	@Override
	public int getActualLimit(InboundOperationDto ioDto, int limit)
			throws OAException {
		try{
			String sql="SELECT count(*) FROM t_pcs_cargo ss WHERE ss.arrivalId  in ( SELECT t.id FROM(SELECT a.id FROM t_pcs_arrival a "
					+ " LEFT JOIN t_pcs_ship b on a.shipId=b.id "
		    		+ " LEFT JOIN t_pcs_ship_ref c on a.shipRefId=c.id "
	    			+ " LEFT JOIN t_pcs_arrival_status j on a.`status` = j.`key`"
		    		+ " LEFT JOIN t_pcs_berth_program h on a.id=h.arrivalId "
		    		+ " LEFT JOIN t_pcs_berth i on h.berthId=i.id "
		    		+ " LEFT JOIN t_pcs_berth_assess k on k.arrivalId=a.id "
		    		+ " LEFT JOIN t_pcs_arrival_info l on l.arrivalId=a.id "
		    		+ " LEFT JOIN t_pcs_ship_agent bb on bb.id=a.shipAgentId"
		    		+ " LEFT JOIN t_auth_user af on a.affirmUserId=af.id"
		    		+ " LEFT JOIN t_auth_user re on a.reviewUserId=re.id"
                    + " LEFT JOIN t_pcs_work aa on aa.arrivalId=a.id";
			        if(!Common.empty(ioDto.getStatuskey())&&!Common.isNull(ioDto.getStatuskey())){
				    	sql+=" and aa.status in ("+ioDto.getStatuskey()+") and aa.status!=2";	
                    }
			        sql+=" where 1=1 and a.type=1 and and !ISNULL(aa.id) a.status>1 and a.status<11 ";
					if(!Common.empty(ioDto.getShipId())&&!Common.isNull(ioDto.getShipId())){
	    		    	sql+=" and a.shipId="+ioDto.getShipId();
	    		    }
					if(!Common.isNull(ioDto.getShipName())){
	    				sql+=" and ( b.name like '%"+ioDto.getShipName()+"%' or c.refName like '%"+ioDto.getShipName()+"%') ";
	    			}
	    		    if(!Common.empty(ioDto.getProductId())&&!Common.isNull(ioDto.getProductId())){
	    		    	sql+=" and a.id in ( select g.arrivalId from t_pcs_cargo g where productId="+ioDto.getProductId()+") ";
	    		    }
	    		    if(!Common.empty(ioDto.getStartTime())){
	    		    	sql+=" and  a.arrivalStartTime >='"+ioDto.getStartTime()+"'";
	    		    }
	                if(!Common.empty(ioDto.getEndTime())){
	                	sql+=" and a.arrivalStartTime <='"+ioDto.getEndTime()+"'";
	                } 
	                if(!Common.empty(ioDto.getStatuskey())&&!Common.isNull(ioDto.getStatuskey())){
	                	if(ioDto.getStatuskey().equals("2")||ioDto.getStatuskey().equals("3")||ioDto.getStatuskey().equals("4"))
		                	sql+=" and a.status in ("+ioDto.getStatuskey()+")";
	                }
	                if(!Common.empty(ioDto.getId())&&!Common.isNull(ioDto.getId())){
	                	sql+=" and a.id="+ioDto.getId();
	                }
					sql+= " order by a.arrivalStartTime asc LIMIT 0,"+limit+") as t ) ";
			
			      return (int) getCount(sql);
		}catch(RuntimeException e){
			LOG.debug("dao获取实际的limit失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao获取实际的limit失败",e);
		}
	}

	@Override
	public void cleanToStatus(Arrival arrival) throws OAException{
		try{
		}catch (RuntimeException e){
			LOG.debug("dao 回退失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao 回退失败",e);
		}
       		
	}

	/**
	 * @Title getExportInbound
	 * @Descrption:TODO
	 * @param:@param ioDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年4月29日下午3:26:07
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> getExportInbound(InboundOperationDto ioDto) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append("SELECT  b.`name` shipName,c.refName shipRefName,DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') arrivalTime,")
				.append(" (SELECT FROM_UNIXTIME(d.leaveTime,'%Y-%m-%d')  from t_pcs_work d where d.arrivalId=a.id LIMIT 0,1) leaveTime,")
				.append(" b.loadCapacity, a.originalArea ,e.`name` shipAgentName,g.`name` berthName,GROUP_CONCAT(h.name) productName,")
				.append(" GROUP_CONCAT(i.`name`) clientName,GROUP_CONCAT(d.goodsPlan) goodsPlan")
				.append(" FROM t_pcs_arrival a LEFT JOIN t_pcs_ship b on a.shipId=b.id")
				.append("  LEFT JOIN t_pcs_ship_ref c ON c.id=a.shipRefId ")
				.append(" LEFT JOIN t_pcs_ship_agent e ON e.id=a.shipAgentId")
				.append(" LEFT JOIN t_pcs_berth_program f on f.arrivalId=a.id")
				.append(" LEFT JOIN t_pcs_berth g on g.id=f.berthId")
				.append("  LEFT JOIN t_pcs_cargo d on d.arrivalId=a.id")
				.append(" LEFT JOIN t_pcs_product h on h.id=d.productId")
				.append(" LEFT JOIN t_pcs_client i ON i.id=d.clientId")
				.append("  WHERE (a.type=1 or a.type=3) AND  c.refName!='转输' AND")
				.append(" (SELECT d.`status` from t_pcs_work d where d.arrivalId=a.id LIMIT 0,1)=9");
			   if(!Common.isNull(ioDto.getStartTime())){
				   sql.append(" AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d')>='").append(ioDto.getStartTime()).append("'");
			   }	
			   if(!Common.isNull(ioDto.getEndTime())){
				   sql.append(" AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d')<='").append(ioDto.getEndTime()).append("'");
			   }
				sql.append(" GROUP BY a.id ORDER BY a.arrivalStartTime ASC");
			return executeQuery(sql.toString());
		}catch (RuntimeException e){
			LOG.debug("dao 回退失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao 回退失败",e);
		}
	}

	/**
	 * @Title getPrintList
	 * @Descrption:TODO
	 * @param:@param ioDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年12月8日下午2:16:01
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> getPrintList(InboundOperationDto ioDto) throws OAException {
		try {
			StringBuilder sql= new StringBuilder();
				sql.append(" SELECT b.`code` cargoCode,DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') arrivalTime,c.name productName, ")
				.append(" (CASE WHEN ISNULL(d.tankCodes) OR d.tankCodes='' THEN GROUP_CONCAT(DISTINCT e.code) ELSE d.tankCodes END) tankName, ")
				.append(" SUM(ROUND(d.goodsTank,3)) goodsTank,f.name clientName,CONCAT(g.name,'/',h.refName) shipName,from_unixtime(i.reviewTime) reviewTime ")
				.append("  FROM t_pcs_arrival a  ")
				.append(" LEFT JOIN t_pcs_cargo b ON a.id=b.arrivalId ")
				.append(" LEFT JOIN t_pcs_product c ON c.id=b.productId ")
				.append(" LEFT JOIN t_pcs_goods d ON d.cargoId = b.id AND (d.isPredict is null or d.isPredict!=1) and ISNULL(d.sourceGoodsId) ")
				.append(" LEFT JOIN t_pcs_tank e ON e.id=d.tankId ")
				.append(" LEFT JOIN t_pcs_client f ON f.id=b.clientId ")
				.append(" LEFT JOIN t_pcs_ship g ON g.id=a.shipId ")
				.append(" LEFT JOIN t_pcs_ship_ref h ON h.id=a.shipRefId  ")
				.append(" LEFT JOIN t_pcs_work i on i.arrivalId=a.id and i.productId=b.productId AND i.orderNum=0 ")
				.append(" WHERE a.id=").append(ioDto.getId());
				if(ioDto.getCargoId()!=null)
					sql.append(" and b.id=").append(ioDto.getCargoId());
				sql.append(" GROUP BY b.id ");
			
			return executeQuery(sql.toString());
			
		} catch (RuntimeException e) {
			LOG.debug("dao查询入库货批信息失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao 查询入库货批信息失败",e);
		}
	}

	
	
	
	
}
