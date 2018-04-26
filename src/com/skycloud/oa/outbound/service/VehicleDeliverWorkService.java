package com.skycloud.oa.outbound.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.model.TransportProgram;
import com.skycloud.oa.inbound.model.WorkCheck;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dto.GoodsLogDto;
import com.skycloud.oa.outbound.dto.VehicleDeliverWorkQueryDto;
import com.skycloud.oa.outbound.dto.VehicleDeliverWorkQueryDtoList;
import com.skycloud.oa.outbound.model.BatchVehicleInfo;
import com.skycloud.oa.outbound.model.Train;
import com.skycloud.oa.outbound.model.VehiclePlan;
import com.skycloud.oa.outbound.model.WeighBridge;
import com.skycloud.oa.utils.OaMsg;

public interface VehicleDeliverWorkService{

	public OaMsg saveTrain(Train d) throws OAException;

	public OaMsg list(VehicleDeliverWorkQueryDto vehicleDeliverWorkQueryDto, PageView pageView) throws OAException;

	public OaMsg delete(String ids) throws OAException;

	public OaMsg deleteVehicleInfo(String id) throws OAException;

	public OaMsg saveVehiclePlan(VehiclePlan d) throws OAException;

	public OaMsg saveBatchVehicleInfo(BatchVehicleInfo d) throws OAException;

	public OaMsg searchLading(VehicleDeliverWorkQueryDto d) throws OAException;

	public OaMsg deleteBillInfo(String planId) throws OAException;

	public OaMsg getVehicleDeliver(String plateId, String trainId) throws OAException;

	public OaMsg saveWeighBridge(WeighBridge wb,GoodsLogDto glDto) throws OAException;

	public OaMsg addVehicleInfo(VehicleDeliverWorkQueryDtoList d, String trainId) throws OAException;

	public OaMsg getTrainInfoById(String id, String serial) throws OAException;

	public OaMsg getTruckInvoiceMsg(String trainId) throws OAException;

	public OaMsg getTruckReadyMsg(String trainId) throws OAException;

	public OaMsg getCheckMountMsg(String trainId) throws OAException;

	public OaMsg savePipeNotifyInfo(TransportProgram t, String trainId, WorkCheck wk) throws OAException;

	public OaMsg confirmData(VehicleDeliverWorkQueryDtoList d, String trainId) throws OAException;

	public HSSFWorkbook exportTruckExcel(HttpServletRequest request, VehicleDeliverWorkQueryDto vDto)throws OAException;


}
