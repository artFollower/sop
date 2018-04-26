package com.skycloud.oa.inbound.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.ArrivalWorkDao;
import com.skycloud.oa.inbound.dao.GraftingCargoDao;
import com.skycloud.oa.inbound.model.ArrivalWork;
import com.skycloud.oa.inbound.model.Goods;
import com.skycloud.oa.inbound.model.GraftingHistory;
import com.skycloud.oa.utils.Common;
@Component
public class GraftingCargoDaoImpl extends BaseDaoImpl implements GraftingCargoDao {



	@Override
	public List<Map<String, Object>> getClient() throws OAException {
		// TODO Auto-generated method stub
		String sql="select DISTINCT c.id clientId,c.name clientName from t_pcs_goods a LEFT JOIN t_pcs_cargo b on a.cargoId=b.id left join t_pcs_client c on c.id=a.clientId where b.arrivalId=0 and (isnull(a.rootGoodsId) or a.rootGoodsId=0)";
		try {
			return executeQuery(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<Map<String, Object>>();
	}

	@Override
	public List<Map<String, Object>> getGraftingGoods(Goods goods) throws OAException {
		
		//原始货体
		String sql="select f.code ladingCode,a.*,c.name clientName,d.name productName ,b.code cargoCode from t_pcs_goods a LEFT JOIN t_pcs_cargo b on a.cargoId=b.id left join t_pcs_client c  on c.id=a.clientId left join t_pcs_product d on d.id=a.productId left join t_pcs_goods_group e on e.id=a.goodsGroupId left join t_pcs_lading f on f.id=e.ladingId where b.arrivalId=0 and isnull(a.rootGoodsId)";
		if(!Common.isNull(goods.getClientId())){
			sql+=" and a.clientId="+goods.getClientId();
		}
		if(!Common.isNull(goods.getProductId())){
			sql+=" and a.productId="+goods.getProductId();
		}
		try {
			return  executeQuery(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<Map<String, Object>>();
		
	}

	@Override
	public List<Map<String, Object>> toGraftingGoods(Goods goods)
			throws OAException {
		
		String sql="select f.code ladingCode,a.*,c.name clientName,d.name productName,b.code cargoCode from t_pcs_goods a LEFT JOIN t_pcs_cargo b on a.cargoId=b.id left join t_pcs_client c  on c.id=a.clientId left join t_pcs_product d on d.id=a.productId left join t_pcs_goods_group e on e.id=a.goodsGroupId left join t_pcs_lading f on f.id=e.ladingId where b.arrivalId<>0 and a.goodsCurrent>0 and  (f.type = 1 OR ISNULL(f.type)) ";
		if(!Common.isNull(goods.getClientId())){
			sql+=" and a.clientId="+goods.getClientId();
		}
		if(!Common.isNull(goods.getProductId())){
			sql+=" and a.productId="+goods.getProductId();
		}
		try {
			return  executeQuery(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<Map<String, Object>>();
		
	}

	@Override
	public void updateSourceGoodsId(int sourceGoodsId, int realId)
			throws OAException {
		
		String sql="update t_pcs_goods set sourceGoodsId="+realId+" where sourceGoodsId="+sourceGoodsId;
		try {
		executeUpdate(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void changeLog(int sourceGoodsId, int realId) throws OAException {
		// TODO Auto-generated method stub
		String sql="call vir_graftingLog("+sourceGoodsId+","+realId+")";
	
		try {
			executeProcedure(sql);
			} catch (OAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public void changeCargoId(int virGoodsId,int virCargoId, int realCargoId, int type)
			throws OAException {
		// TODO Auto-generated method stub
		String sql="";
		//原始货体 
		if(type==1){
		 sql="update t_pcs_goods a,(select id from t_pcs_goods where sourceGoodsId="+virGoodsId+") b set a.cargoId="+realCargoId+" where a.sourceGoodsId="+virGoodsId+" or a.rootGoodsId in (b.id)";
		}
		//原号货体
		else if(type==2){
			 sql="update t_pcs_goods set cargoId="+realCargoId+" where rootGoodsId="+virGoodsId;
		}
		try {
			executeUpdate(sql);
			} catch (OAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public void repairLog(int realId) throws OAException {
		// TODO Auto-generated method stub
		String sql="call repairLog("+realId+")";
		try {
			executeProcedure(sql);
			} catch (OAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public void updateRootGoodsId(int virtualGoodsId, int realId)
			throws OAException {
		// TODO Auto-generated method stub
		 String sql="update t_pcs_goods a,(select id from t_pcs_goods where sourceGoodsId="+virtualGoodsId+") b set a.rootGoodsId="+realId+" where a.sourceGoodsId="+virtualGoodsId+" or a.rootGoodsId in (b.id)";
		try {
			executeUpdate(sql);
			} catch (OAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public void updateRootGoodsId(int virtualGoodsId) throws OAException {
		// TODO Auto-generated method stub
		 String sql="update t_pcs_goods set rootGoodsId=0 where sourceGoodsId="+virtualGoodsId;
		 
		 String sql1="call vir_updateRootGoodsId("+virtualGoodsId+")";
		 
		try {
			executeUpdate(sql);
			executeProce(sql1);
			} catch (OAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public void updateRootGoodsIdByYTY(int virtualGoodsId, int realId)
			throws OAException {
		// TODO Auto-generated method stub
		 String sql="update t_pcs_goods set rootGoodsId="+realId+" where rootGoodsId="+virtualGoodsId;
		 
		try {
			executeUpdate(sql);
			} catch (OAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public void createOutLog(int virtualGoodsId, int realId) throws OAException {
		// TODO Auto-generated method stub
		String sql="call vir_createOutLog("+virtualGoodsId+","+realId+")";
		try {
			executeProcedure(sql);
			} catch (OAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public void deleteOutLog(int virtualGoodsId,double count) throws OAException {
		String sql="delete from t_pcs_goodslog where type=3 and  goodsId=(select a.sourceGoodsId from t_pcs_goods a where id ="+virtualGoodsId+") and nextGoodsId="+virtualGoodsId+" and nextLadingId=(select b.ladingId from t_pcs_goods a LEFT JOIN t_pcs_goods_group b on a.goodsGroupId=b.id where a.id="+virtualGoodsId+")";
		try {
			execute(sql);
			} catch (OAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@Override
	public void repairGoods(int goodsId) throws OAException {
		String sql="update t_pcs_goods a set a.goodsOut = COALESCE((select round(-sum(c.goodsChange),3) from t_pcs_goodslog c where c.goodsId=a.id and type=3),0),"+
	"a.goodsIn=COALESCE((select round(sum(c.goodsChange),3) from t_pcs_goodslog c where c.goodsId=a.id and type=2 ),0),"+
				"a.goodsTotal=(select round(sum(c.goodsChange),3) from t_pcs_goodslog c where c.goodsId=a.id and (type=2 or type=1 or type=10)),"+
	"a.goodsCurrent=(select round((c.surplus+c.goodsSave),3) from t_pcs_goodslog c where c.goodsId=a.id order by c.originalTime DESC limit 0,1),a.goodsInPass=(select round((a.goodsTotal-c.goodsSave),3) from t_pcs_goodslog c where c.goodsId=a.id order by c.originalTime DESC limit 0,1),a.goodsOutPass=(select round((a.goodsTotal-c.goodsSave),3) from t_pcs_goodslog c where c.goodsId=a.id order by c.originalTime DESC limit 0,1) where a.id="+goodsId;
	
		try {
			executeUpdate(sql);
			} catch (OAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@Override
	public void updateRKDJLog(int virtualGoodsId, double virgoodsTotal)
			throws OAException {
		String sql="update t_pcs_goodslog a ,(select id from t_pcs_goodslog where goodsId="+virtualGoodsId+"  and (type=1 or type=2) order by createTime ASC limit 0,1) b set a.goodsChange=(a.goodsChange-"+virgoodsTotal+"),a.goodsSave=(a.goodsSave-"+virgoodsTotal+") where a.id=b.id ";
		try {
			executeUpdate(sql);
			} catch (OAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public void updateRKFF(int virtualGoodsId, String goodsTotal)
			throws OAException {
		
		String sql="update t_pcs_goodslog a ,(select id from t_pcs_goodslog where goodsId="+virtualGoodsId+"  and (type=1 or type=2) order by createTime ASC limit 0,1) b set a.goodsChange=round(a.goodsChange-"+goodsTotal+",3) ,a.goodsSave=round(a.goodsSave-"+goodsTotal+",3) where a.id=b.id ";
		String sql1="update t_pcs_goodslog a set a.goodsSave=round(a.goodsSave-"+goodsTotal+",3) where a.type=4 and a.goodsId="+virtualGoodsId;
		executeUpdate(sql);
		executeUpdate(sql1);
	}

	@Override
	public void splitSourceOutLog(int virtualGoodsId,double count,int newgoodsId) throws OAException {	
		
		String sql1="update t_pcs_goodslog set goodsChange=round(goodsChange+"+count+",3) where id=(select id from (select l.id from t_pcs_goodslog l where l.goodsChange<0 and  l.type=3 and l.goodsId=(select a.sourceGoodsId from t_pcs_goods a where id ="+virtualGoodsId+") and l.nextgoodsId="+virtualGoodsId+" and l.nextLadingId=(select b.ladingId from t_pcs_goods a LEFT JOIN t_pcs_goods_group b on a.goodsGroupId=b.id where a.id="+virtualGoodsId+") order by l.createTime DESC limit 0,1 ) a)  " ;
//				"goodsId=(select a.sourceGoodsId from t_pcs_goods a where id ="+virtualGoodsId+") and nextgoodsId="+virtualGoodsId+" and nextLadingId=(select b.ladingId from t_pcs_goods a LEFT JOIN t_pcs_goods_group b on a.goodsGroupId=b.id where a.id="+virtualGoodsId+")";
		
		String sql="insert into t_pcs_goodslog (goodsId,clientId,type,deliverNum,deliverType,ladingId,goodsChange,surplus,goodsSave,deliverNo,"+
				"serial,batchId,ladingType,vehicleShipId,actualNum,createTime,ladingClientId,createUserId,nextLadingId,actualType,originalTime,nextGoodsId)  "+ 
				"select goodsId,clientId,type,deliverNum,deliverType,ladingId,-"+count+" as goodsChange,surplus,goodsSave,deliverNo,serial,batchId,ladingType,vehicleShipId,actualNum,(createTime+1) as createTime,ladingClientId,createUserId,nextLadingId,actualType,0 as originalTime,"+newgoodsId+" as nextGoodsId from t_pcs_goodslog " +
						" where id=(select id from (select l.id from t_pcs_goodslog l where l.goodsChange<0 and  l.type=3 and l.goodsId=(select a.sourceGoodsId from t_pcs_goods a where id ="+virtualGoodsId+") and l.nextgoodsId="+virtualGoodsId+" and l.nextLadingId=(select b.ladingId from t_pcs_goods a LEFT JOIN t_pcs_goods_group b on a.goodsGroupId=b.id where a.id="+virtualGoodsId+") order by l.createTime DESC limit 0,1 ) a)" ;
//						"type=3 and goodsId=(select a.sourceGoodsId from t_pcs_goods a where id ="+virtualGoodsId+") and goodsChange<0 and  nextLadingId=(select b.ladingId from t_pcs_goods a LEFT JOIN t_pcs_goods_group b on a.goodsGroupId=b.id where a.id="+virtualGoodsId+")";
		try {
			execute(sql1);
			insert(sql);
			} catch (OAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@Override
	public List<Map<String, Object>> getTrueOutCountLog(int logId) throws OAException {
		String sql="select a.*,sum(b.goodsChange) backCount from t_pcs_goodslog a,t_pcs_goodslog b  where a.goodsId=b.goodsId and a.nextLadingId=b.nextLadingId and a.nextGoodsId=b.nextGoodsId and b.goodsChange>0 and b.type=3 and a.id="+logId;
		try {
			return  executeQuery(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<Map<String, Object>>();
		
		
	}

	@Override
	public void graftingOutLog(int logId, int newgoodsId) throws OAException {
		
		String sql="update t_pcs_goodslog a,(select * from t_pcs_goodslog where id="+logId+") b set a.goodsId="+newgoodsId+" where a.goodsId=b.goodsId and a.type=3 and a.goodsChange>0 and a.nextLadingId=b.nextLadingId ";
		String sql1="update t_pcs_goodslog set goodsId="+newgoodsId+" where id="+logId;
		
		try {
			executeUpdate(sql);
			executeUpdate(sql1);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateNextGoodsSourceRoot(int virtualGoodsId, int newgoodsId,
			int ladingId) throws OAException {
		
		String sql="update t_pcs_goods a ,(select * from t_pcs_goods where id="+newgoodsId+") b set a.sourceGoodsId="+newgoodsId+" ,a.rootGoodsId=(case b.rootGoodsId when 0 then b.id when null then 0 else b.rootGoodsId end) where a.sourceGoodsId="+virtualGoodsId +" and a.goodsGroupId=(select id from t_pcs_goods_group where ladingId="+ladingId+")";
		
		try {
			executeUpdate(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public int splitOutLog(int logId, int newgoodsId, double count)
			throws OAException {	
		
		String sql1="update t_pcs_goodslog set goodsChange=round(goodsChange+"+count+",3) where   id="+logId;
		
		String sql="insert into t_pcs_goodslog (goodsId,clientId,type,deliverNum,deliverType,ladingId,goodsChange,surplus,goodsSave,deliverNo,"+
				"serial,batchId,ladingType,vehicleShipId,actualNum,createTime,ladingClientId,createUserId,nextLadingId,actualType,originalTime,nextGoodsId)  "+ 
				"select "+newgoodsId+" as goodsId,clientId,type,deliverNum,deliverType,ladingId,-"+count+" as goodsChange,surplus,goodsSave,deliverNo,serial,batchId,ladingType,vehicleShipId,actualNum,(createTime+1) as createTime,ladingClientId,createUserId,nextLadingId,actualType,0 as originalTime,nextGoodsId from t_pcs_goodslog where id="+logId;
		try {
			execute(sql1);
			return insert(sql);
			} catch (OAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return 0;
	}

	@Override
	public List<Map<String, Object>> getNextGoods(int virtualGoodsId, int nextLadingId)
			throws OAException {
		String sql="select * from t_pcs_goods where sourceGoodsId="+virtualGoodsId+" and goodsGroupId=(select id from t_pcs_goods_group where ladingId="+nextLadingId+")";
		
		try {
			return executeQuery(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<Map<String, Object>>();
	}

	@Override
	public void graftingActualLog(int logId, int newgoodsId) throws OAException {
		String sql="update t_pcs_goodslog set goodsId="+newgoodsId +" where id="+logId;
		try {
			executeUpdate(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateLogNextGoods(int logId, int mNewgoodsId)
			throws OAException {
		String sql="update t_pcs_goodslog set nextGoodsId="+mNewgoodsId +" where id="+logId;
		try {
			executeUpdate(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Map<String, Object>> checkWaitOut(int goodsId)
			throws OAException {
		String sql="select count(a.id) count from t_pcs_goodslog a,t_pcs_goods b,t_pcs_cargo c where a.type=5 and a.actualNum=0 and a.actualType=0 and  a.goodsId=b.id and b.cargoId=c.id and c.id=(select cargoId from t_pcs_goods where id="+goodsId+")";
		
		try {
			return executeQuery(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<Map<String, Object>>();
	}

	@Override
	public void repairLading(int virtualGoodsId, int realId) throws OAException {
		// TODO Auto-generated method stub
		String sql="UPDATE sop.t_pcs_lading a,(select  b.goodsGroupId ,round(sum(b.goodsTotal),3) goodsTotal, sum(b.goodsOutPass) goodsOutPass,sum(b.goodsOut) goodsOut from sop.t_pcs_goods b where b.goodsGroupId in (select GROUP_CONCAT(z.goodsGroupId) from t_pcs_goods z where z.id in ("+virtualGoodsId+","+realId+")) GROUP BY b.goodsGroupId) b, sop.t_pcs_goods_group c,(select  c.goodsGroupId , -sum(b.actualNum) deliverNum from sop.t_pcs_goodslog b ,sop.t_pcs_goods c where b.type=5 and c.goodsGroupId in (select GROUP_CONCAT(z.goodsGroupId) from t_pcs_goods z where z.id in ("+virtualGoodsId+","+realId+")) and b.goodsId=c.id  GROUP BY c.goodsGroupId) d "+
"SET a.goodsPass = b.goodsOutPass,"+
 "a.goodsOut = b.goodsOut,"+
" a.goodsDelivery = abs(d.deliverNum),"+
"a.goodsTotal=b.goodsTotal WHERE b.goodsGroupId = c.id AND c.ladingId = a.id and d.goodsGroupId=b.goodsGroupId ";
		String sql1="UPDATE sop.t_pcs_lading a,(select  b.goodsGroupId ,sum(b.goodsTotal) goodsTotal, sum(b.goodsOutPass) goodsOutPass,sum(b.goodsOut) goodsOut from sop.t_pcs_goods b where b.goodsGroupId in (select GROUP_CONCAT(z.goodsGroupId) from t_pcs_goods z where z.id in ("+virtualGoodsId+","+realId+")) GROUP BY b.goodsGroupId) b,"+
" sop.t_pcs_goods_group c "+
"SET a.goodsPass = b.goodsOutPass,"+
" a.goodsOut = b.goodsOut,"+
"a.goodsTotal=b.goodsTotal "+
"WHERE b.goodsGroupId = c.id AND c.ladingId = a.id ";
		try {
			executeUpdate(sql);
			executeUpdate(sql1);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void updateLogLadingId(int virtualGoodsId, int realId) throws OAException {
		// TODO Auto-generated method stub
		String sql="UPDATE t_pcs_goodslog a ,t_pcs_goods b ,t_pcs_goods_group c ,t_pcs_lading d set a.ladingId=d.id ,a.ladingType=d.type where a.goodsId=b.id and b.goodsGroupId=c.id and d.id=c.ladingId and (a.type=2 or a.type=5) and b.id in ("+virtualGoodsId+","+realId+")";
		String sql1="update t_pcs_goods set goodsOut=0 where ISNULL(goodsOut)";
		String sql2="update t_pcs_goods set goodsIn=0 where ISNULL(goodsIn)";
		String sql3="update t_pcs_goods a ,t_pcs_goods_group b , t_pcs_lading c set a.ladingClientId=c.receiveClientId where a.goodsGroupId=b.id and b.ladingId=c.id and c.type=2";
		String sql4="update t_pcs_goods a ,t_pcs_goods_group b , t_pcs_lading c set a.clientId=c.receiveClientId where a.goodsGroupId=b.id and b.ladingId=c.id and c.type=1";
		
		
		try {
			executeUpdate(sql);
			executeUpdate(sql1);
			executeUpdate(sql2);
			executeUpdate(sql3);
			executeUpdate(sql4);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void updateRootGoodsIdByProduce(int virtualGoodsId)
			throws OAException {
		
		 String sql="call vir_updateRootGoodsId("+virtualGoodsId+")";
		 try {
				executeUpdate(sql);
			} catch (OAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public int addHistory(GraftingHistory graftingHistory) throws OAException {try {
		return (Integer) save(graftingHistory);
	} catch (Exception e) {
		throw new OAException(Constant.SYS_CODE_DB_ERR, "添加失败", e);
	}
}

	@Override
	public List<Map<String, Object>> getHistory(
			GraftingHistory graftingHistory, int startRecord, int maxresult)
			throws OAException {
		
		String sql="select a.* ,b.name outClientName,c.name inClientName from t_pcs_grafting_history a,t_pcs_client b ,t_pcs_client c where a.outClientId =b.id and a.inClientId=c.id ";
		if (maxresult!= 0) {
			sql+=" limit "+startRecord+","+maxresult;
			
		}
		
		try {
			return  executeQuery(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public int getHistoryCount(GraftingHistory graftingHistory)
			throws OAException {
		
		String sql="select count(a.id) from t_pcs_grafting_history a,t_pcs_client b ,t_pcs_client c where a.outClientId =b.id and a.inClientId=c.id ";
		
		return  (int) getCount(sql);
		
	}

	@Override
	public void repairCargo(int cargoId) throws OAException {
		
		String sql="update sop.t_pcs_cargo a ,(select b.cargoId,round(sum(b.goodsCurrent),3) goodsCurrent,round(sum(b.goodsInPass),3) goodsInPass,round(sum(b.goodsOutPass),3) goodsOutPass from sop.t_pcs_goods b where ISNULL(b.goodsGroupId) GROUP BY b.cargoId) b,(select cargoId, round(sum(b.goodsTotal),3) goodsTotal from t_pcs_goods b where ISNULL(b.goodsGroupId) GROUP BY cargoId) c set"
+" a.goodsTotal=c.goodsTotal,"
+" a.goodsCurrent=b.goodsCurrent,"
+" a.goodsInPass=b.goodsInPass,"
+" a.goodsOutPass=b.goodsOutPass where a.id=b.cargoId and a.id=c.cargoId and a.id="+cargoId;
		try {
			  executeUpdate(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public List<Map<String, Object>> checkRealWaitOut(int goodsId)
			throws OAException {
		String sql="select count(a.id) count from t_pcs_goodslog a,t_pcs_goods b where a.type=5 and a.actualNum=0 and a.actualType=0 and  a.goodsId=b.id  and b.id="+goodsId;
		
		try {
			return executeQuery(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<Map<String, Object>>();
	}

	@Override
	public void updateLogLadingId(int realId) throws OAException {
		
		String sql="update t_pcs_goodslog a,t_pcs_goods b,t_pcs_goods_group c set a.ladingId=c.ladingId where (a.type=3 or a.type=5) and a.goodsId=b.id and b.goodsGroupId=c.id and a.goodsId="+realId;
		try {
			  executeUpdate(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void repairLadingOutByGoodsId(int parseInt) throws OAException {
		// TODO Auto-generated method stub
		String sql="UPDATE sop.t_pcs_lading a,(select  b.goodsGroupId ,sum(b.goodsTotal) goodsTotal, sum(b.goodsOutPass) goodsOutPass,sum(b.goodsOut) goodsOut from sop.t_pcs_goods b GROUP BY b.goodsGroupId) b, sop.t_pcs_goods_group c,sop.t_pcs_goods d"+
" SET a.goodsOut = b.goodsOut  WHERE b.goodsGroupId = c.id AND c.ladingId = a.id and d.goodsGroupId=b.goodsGroupId and d.id="+parseInt;
		
		try {
			executeUpdate(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public List<Map<String, Object>> getRKDJTime(int oldGoodsId)
			throws OAException {
		String sql="select a.createTime from  t_pcs_goodslog a  where (a.type=1 or a.type=2) and a.goodsId="+oldGoodsId+" order by a.originalTime ASC limit 0,1 ";
		try {
			return executeQuery(sql);
			} catch (OAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}

	@Override
	public void changeLogTime(int virtualGoodsId, int type,String tankId) throws OAException {
		// TODO Auto-generated method stub
		String sql="";
		//原始货体 
		if(type==1){
		 sql="call vir_graftingLogYRootTime("+virtualGoodsId+","+tankId+") ";
		}
		//原号货体
		else if(type==2){
			 sql="call vir_graftingLogRootTime("+virtualGoodsId+","+tankId+") ";
				}
		try {
			executeUpdate(sql);
			} catch (OAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
