package com.skycloud.oa.outbound.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.model.TransportProgram;
import com.skycloud.oa.outbound.dto.VehicleDeliverWorkQueryDto;
import com.skycloud.oa.outbound.model.Approve;
import com.skycloud.oa.outbound.model.BatchVehicleInfo;
import com.skycloud.oa.outbound.model.DeliveryGoodsRecord;
import com.skycloud.oa.outbound.model.Measure;
import com.skycloud.oa.outbound.model.Train;
import com.skycloud.oa.outbound.model.VehiclePlan;
import com.skycloud.oa.outbound.model.Weigh;

/**
 * 
 * <p>出库管理---船舶出库</p>
 * @ClassName:VehicleDeliverWorkDao
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年5月28日 下午1:27:51
 *
 */
public interface VehicleDeliverWorkDao 
{
	public int saveTrain(Train train) throws Exception;
	public int updateTrain(Train train) throws Exception;
	public List<Map<String,Object>> searchLading(VehicleDeliverWorkQueryDto vehicleDeliverWorkQueryDto)throws Exception ;
	public List<Map<String,Object>> getTruckInvoiceMsg(String trainId)throws Exception ;
	public void deleteBillInfo(String trainId)throws Exception ;
	public void saveVehiclePlan(VehiclePlan vehiclePlan)throws Exception ;
	public void saveBatchVehicleInfo(BatchVehicleInfo batchVehicleInfo)throws Exception ;
	public void addLadingInfo(VehicleDeliverWorkQueryDto ladingInfo)throws Exception ;
	public void addVehicleInfo(VehicleDeliverWorkQueryDto ladingInfo)throws Exception ;
	public void updateVehicleInfo(VehicleDeliverWorkQueryDto ladingInfo)throws Exception ;
	public void updateTrainStatus(String trainId,String status,boolean isChange)throws Exception ;
	public void saveWrokPlan(Weigh weigh)throws Exception ;
	public void updateWrokPlan(Weigh weigh)throws Exception ;
	public void saveApprove(Approve approve)throws Exception ;
	public List<Map<String, Object>> getApproveInfo(int modelId,String trainId)throws Exception ;
	public void updateApprove(Approve approve,int flag)throws Exception ;
	public void setBillInfoToWeigh(String trainId)throws Exception ;
	public void saveWrokPlan(Measure measure)throws Exception ;
	public void upMeasure(Measure measure)throws Exception ;
	public void deleteWeighInfo(String trainId)throws Exception ;
	public void deleteMeasureInfo(String trainId)throws Exception ;
	public List<Map<String,Object>> getTrainInfoById(String id,String serial)throws Exception ;
	public List<Map<String,Object>> getVehicleInfo(String trainId)throws Exception ;
	public List<Map<String,Object>> getWeighInfo(String trainId)throws Exception ;
	public List<Map<String,Object>> getMeasureInfo(String trainId)throws Exception ;
	public List<Map<String,Object>> getCheckMountPageConetent(String trainId)throws Exception ;
	public void delete(String ids) throws Exception ;
	public List<Map<String,Object>> getTankGroupNum(String id)throws Exception ;
	public List<Map<String,Object>> getParkGroupNum(String id)throws Exception ;
	public List<Map<String,Object>> getTotalWeighInfo(String id)throws Exception ;
	public List<Map<String,Object>> getTotalMeasureInfo(String id)throws Exception ;
	public List<Map<String,Object>> getCheckMountMsg(String id)throws Exception ;
	public List<Map<String,Object>> getVehicleDeliver(String plateId,String trainId) throws Exception ;
	public int savePipeNotifyInfo(TransportProgram tp,String trainId) throws Exception ;
	public int saveChangeTankNotifyInfo(TransportProgram tp,String trainId) throws Exception ;
	public List<Map<String,Object>> getPipeNotifyInfo(String trainId)  throws Exception ;
	public List<Map<String,Object>> getWorkCheckPipe(String trainId)  throws Exception ;
	public List<Map<String,Object>> getWorkCheckChange(String trainId)  throws Exception ;
	public List<Map<String,Object>> getChangeTankNotifyInfo(String trainId) throws Exception ;
	public void updateVehiclePlanActualNum(VehiclePlan vehiclePlan)throws Exception ;
	public void updateLadingNum(VehiclePlan vehiclePlan)throws Exception ;
	public void deleteVehicleInfo(String id) throws Exception;
	public void updateVehiclePlanGoodNum(DeliveryGoodsRecord good) throws Exception;
	public void updateGoodNum(DeliveryGoodsRecord good) throws Exception;
	public void saveVehiclePlanGoodNum(DeliveryGoodsRecord good) throws Exception;
	public List<Map<String,Object>> getStatusByPlateId(int plateId) throws Exception ; 
	public Map<String, Object> getTrainIdByPlateId(int plateId)throws Exception ; 
	public Map<String, Object> getSerial(String dateStr)throws OAException ;
	public int trainNotifyCount(String trainId)throws Exception ;
	
	/**
	 * 获取车发出库列表
	 *@author jiahy
	 * @param vehicleDeliverWorkQueryDto
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 */
	public List<Map<String,Object>> getTrainList(VehicleDeliverWorkQueryDto vehicleDeliverWorkQueryDto,int start,int limit)throws OAException ;
	
	
	/**
	 * 获取车发出库列表
	 *@author jiahy
	 * @param vehicleDeliverWorkQueryDto
	 * @return
	 * @throws OAException
	 */
	public int getTrainListCount(VehicleDeliverWorkQueryDto vehicleDeliverWorkQueryDto) throws OAException;

}
