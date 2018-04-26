package com.skycloud.oa.outbound.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.Region;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.model.TransportProgram;
import com.skycloud.oa.inbound.model.WorkCheck;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dao.GoodsLogDao;
import com.skycloud.oa.outbound.dao.OutBoundDao;
import com.skycloud.oa.outbound.dao.VehicleDeliverWorkDao;
import com.skycloud.oa.outbound.dao.WeighBridgeDao;
import com.skycloud.oa.outbound.dto.GoodsLogDto;
import com.skycloud.oa.outbound.dto.VehicleDeliverWorkQueryDto;
import com.skycloud.oa.outbound.dto.VehicleDeliverWorkQueryDtoList;
import com.skycloud.oa.outbound.model.Approve;
import com.skycloud.oa.outbound.model.BatchVehicleInfo;
import com.skycloud.oa.outbound.model.GoodsLog;
import com.skycloud.oa.outbound.model.Train;
import com.skycloud.oa.outbound.model.VehiclePlan;
import com.skycloud.oa.outbound.model.WeighBridge;
import com.skycloud.oa.outbound.service.VehicleDeliverWorkService;
import com.skycloud.oa.report.utils.FormatUtils;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.ExcelUtil;
import com.skycloud.oa.utils.OaMsg;
import com.skycloud.oa.utils.ExcelUtil.CallBack;
/**
 * 
 * <p>
 * 请用一句话概括功能
 * </p>
 * 
 * @ClassName:VehicleDeliverWorkServiceImpl
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年7月31日 下午5:21:41
 *
 */
@Service
public class VehicleDeliverWorkServiceImpl implements VehicleDeliverWorkService {

	private static Logger LOG = Logger.getLogger(VehicleDeliverWorkServiceImpl.class);

	@Autowired
	private VehicleDeliverWorkDao vehicleDeliverWorkDao;
	@Autowired
	private OutBoundDao shipDeliverWorkDao;
	@Autowired
	private GoodsLogDao goodsLogDao;
	@Autowired
	private WeighBridgeDao weighBridgeDao;

	@Override
	@Log(object = C.LOG_OBJECT.PCS_TRAIN, type = C.LOG_TYPE.DELETE)
	public OaMsg delete(String id) {

		OaMsg oaMsg = new OaMsg();
		try {
			vehicleDeliverWorkDao.delete(id);
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (Exception re) {
			LOG.error("插入失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	@Log(object = C.LOG_OBJECT.PCS_TRAIN, type = C.LOG_TYPE.DELETE)
	public OaMsg deleteVehicleInfo(String id) {
		OaMsg oaMsg = new OaMsg();
		try {
			vehicleDeliverWorkDao.deleteVehicleInfo(id);
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (Exception re) {
			LOG.error("插入失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	@Log(object = C.LOG_OBJECT.PCS_TRAIN, type = C.LOG_TYPE.CONFIRM)
	public OaMsg confirmData(VehicleDeliverWorkQueryDtoList vehiclePlanList, String trainId) {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			int status = 0;
			if (vehiclePlanList.getApprove() != null) {
				status = vehiclePlanList.getApprove().getStatus();
			}
			if (vehiclePlanList.getGoodsLoglist().size() > 0) {
				List<GoodsLog> goodsLogList = vehiclePlanList.getGoodsLoglist();
				int nSize = vehiclePlanList.getGoodsLoglist().size();
				for (int i = 0; i < nSize; i++) {
					// goodsLogDao.up(goodsLogList.get(i));
					if (status == 2) {
						// 实发量改变才同步
						if (goodsLogList.get(i).getAfDiffNum() != null
								&& goodsLogList.get(i).getAfDiffNum().doubleValue() != 0) {
							// 同步goods ,lading
							goodsLogDao.upChangeValue(goodsLogList.get(i));
							// 同步goodsLog 货体流转图的结存量
							goodsLogDao.synGoodsLog(goodsLogList.get(i));
						}
					}
					if (status == 3) {
						goodsLogDao.rebackGoodsLog(goodsLogList.get(i));
						vehicleDeliverWorkDao.updateTrainStatus(trainId, "42", false);
					}
				}
			}
			approve(vehiclePlanList.getApprove(), "43", trainId, 3);
		} catch (Exception re) {
			LOG.error("插入失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	/**
	 * 获取车辆发货信息
	 */
	@Override
	public OaMsg getVehicleDeliver(String plateId, String trainId) {

		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.getData().addAll(vehicleDeliverWorkDao.getVehicleDeliver(plateId, trainId));
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (Exception re) {
			LOG.error("插入失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	/**
	 * 保存车发作业计划信息
	 */
	@Override
	@Log(object = C.LOG_OBJECT.PCS_WEIGHBRIDGE, type = C.LOG_TYPE.CREATE)
	public OaMsg saveWeighBridge(WeighBridge wb, GoodsLogDto glDto) {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);

			if (wb != null && wb.getId() != 0) {
				// 更新称重记录
				// 如果存在调整量
				if (wb.getActualRoughWeight() != null && wb.getActualRoughWeight().doubleValue() != 0
						|| wb.getActualTareWeight() != null && wb.getActualRoughWeight().doubleValue() != 0) {
					// 更新goodslog
					vehicleDeliverWorkDao.updateTrainStatus(glDto.getTrainId() + "", "42", true);
					weighBridgeDao.updateActualNum(wb);
				}
				if (wb.getType() == 1) {
					wb.setActualRoughWeight(null);
					wb.setActualTareWeight(null);
				}
				weighBridgeDao.up(wb);
			} else {
				vehicleDeliverWorkDao.updateTrainStatus(glDto.getTrainId() + "", "42", true);
				weighBridgeDao.updateActualNum(wb);

				wb.setActualRoughWeight(null);
				wb.setActualTareWeight(null);

				weighBridgeDao.addWeighBridge(wb);

			}
			if (wb.getOutTime() != null && wb.getOutTime() != -1) {
				// 同步goodslog的createTime与weighbridge一致
				goodsLogDao.syncGoodsLogOutTime(new GoodsLog(wb.getOutTime(), wb.getSerial()));
				// 修改出库时间遍历修改结存量
				goodsLogDao.syncGoodsLogGoodsSave(new GoodsLogDto(wb.getSerial(), glDto.getChoiceTime()));
			}

		} catch (Exception re) {
			LOG.error("车发出库修改称重记录失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	@Log(object = C.LOG_OBJECT.PCS_TRAIN, type = C.LOG_TYPE.CREATE)
	public OaMsg saveTrain(Train train) {

		OaMsg oaMsg = new OaMsg();
		try {
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			train.setOperator(user.getId());
			train.setStatus("40");
			int trainId = 0;
			if (train.getId() > 0) {
				vehicleDeliverWorkDao.updateTrain(train);
			} else {
				trainId = vehicleDeliverWorkDao.saveTrain(train);
			}

			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			Map<String, String> map = new HashMap<String, String>();
			map.put("trainId", trainId + "");
			oaMsg.setMap(map);
		} catch (Exception re) {
			LOG.error("插入失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getTrainInfoById(String id, String serial) {

		OaMsg oaMsg = new OaMsg();
		try {

			oaMsg.getData().addAll(vehicleDeliverWorkDao.getTrainInfoById(id, serial));

			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (Exception re) {
			LOG.error("插入失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getTruckInvoiceMsg(String trainId) {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.getData().addAll(vehicleDeliverWorkDao.getTruckInvoiceMsg(trainId));
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (Exception re) {
			LOG.error("插入失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getTruckReadyMsg(String trainId) {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.getData().addAll(vehicleDeliverWorkDao.getWeighInfo(trainId));
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("查询成功");
		} catch (Exception re) {
			LOG.error("插入失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	@Log(object = C.LOG_OBJECT.PCS_OUTTRANSPORT, type = C.LOG_TYPE.CREATE)
	public OaMsg savePipeNotifyInfo(TransportProgram tp, String trainId, WorkCheck wk) {

		OaMsg oaMsg = new OaMsg();
		try {
			int tpId = vehicleDeliverWorkDao.savePipeNotifyInfo(tp, trainId);
			wk.setTransportId(tpId);
			shipDeliverWorkDao.saveCheckWork(wk);
			Map<String, String> map = new HashMap<String, String>();
			map.put("transportId", trainId);
			oaMsg.setMap(map);
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (Exception re) {
			LOG.error("插入失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getCheckMountMsg(String trainId) {

		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().add(vehicleDeliverWorkDao.getCheckMountMsg(trainId));
			oaMsg.getData().add(vehicleDeliverWorkDao.getApproveInfo(3, trainId));
		} catch (Exception re) {
			LOG.error("插入失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg deleteBillInfo(String trainId) {

		OaMsg oaMsg = new OaMsg();
		try {

			vehicleDeliverWorkDao.deleteBillInfo(trainId);

			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (Exception re) {
			LOG.error("插入失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg addVehicleInfo(VehicleDeliverWorkQueryDtoList ladingInfo, String trainId) {

		OaMsg oaMsg = new OaMsg();
		try {
			if (ladingInfo.getApprove().getStatus() == 2) {
				vehicleDeliverWorkDao.setBillInfoToWeigh(trainId);
			}
			approve(ladingInfo.getApprove(), "41", trainId, 1);
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (Exception re) {
			LOG.error("插入失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	/**
	 * 添加审批信息 @Title:VehicleDeliverWorkServiceImpl @Description: @param o @param
	 * status @param trainId @param modelId @throws Exception @Date:2015年8月17日
	 * 下午5:56:32 @return :void @throws
	 */
	public void approve(Approve approve, String status, String trainId, int modelId) throws Exception {
		User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		approve.setModelId(modelId);
		// 添加审批信息
		if (approve.getStatus() == 0 || approve.getStatus() == 1) {
			approve.setCheckUserId(user.getId());
			approve.setCreateTime(new Date());
			if (approve.getId() > 0) {
				if (approve.getStatus() == 1) {
					// 清空先前审批信息
					vehicleDeliverWorkDao.updateApprove(approve, 1);
				} else {
					vehicleDeliverWorkDao.updateApprove(approve, 0);
				}
			} else {
				vehicleDeliverWorkDao.saveApprove(approve);
			}
		}
		if (approve.getStatus() == 2) {
			approve.setReviewUserId(user.getId());
			approve.setReviewTime(new Date().getTime() / 1000);
			vehicleDeliverWorkDao.updateApprove(approve, 0);
			vehicleDeliverWorkDao.updateTrainStatus(trainId, status, false);
		}
		if (approve.getStatus() == 3) {
			approve.setStatus(3);
			approve.setReviewTime(new Date().getTime() / 1000);
			approve.setReviewUserId(user.getId());
			vehicleDeliverWorkDao.updateApprove(approve, 0);
		}
	}

	@Override
	public OaMsg list(VehicleDeliverWorkQueryDto vehicleDeliverWorkQueryDto, PageView pageView) {

		OaMsg oaMsg = new OaMsg();
		try {
			List<Map<String, Object>> hdata = new ArrayList<Map<String, Object>>();
			List<String> lsids = new ArrayList<String>();
			Map<String, Map<String, Object>> trainData = new HashMap<String, Map<String, Object>>();
			List<Map<String, Object>> listData = vehicleDeliverWorkDao.getTrainList(vehicleDeliverWorkQueryDto,
					pageView.getStartRecord(), pageView.getMaxresult());
			if (listData != null && listData.size() > 0) {
				for (int i = 0; i < listData.size(); i++) {
					Map<String, Object> map = listData.get(i);
					String trainId = map.get("id").toString();
					if (lsids.contains(trainId)) {
						List<Map<String, Object>> serialMsg = (List<Map<String, Object>>) trainData.get(trainId)
								.get("serialData");
						Map<String, Object> itemSerialMsg = new HashMap<String, Object>();
						itemSerialMsg.put("goodslogId", map.get("goodslogId"));
						itemSerialMsg.put("serial", map.get("serial"));
						itemSerialMsg.put("actualNum", map.get("actualNum"));
						itemSerialMsg.put("deliverNum", map.get("deliverNum"));
						itemSerialMsg.put("createTime", map.get("createTime"));
						itemSerialMsg.put("invoiceTime", map.get("invoiceTime"));
						itemSerialMsg.put("productId", map.get("productId"));
						itemSerialMsg.put("productName", map.get("productName"));
						itemSerialMsg.put("tankName", map.get("tankName"));
						trainData.get(trainId).put("actualNum", Common.add(trainData.get(trainId).get("actualNum"), map.get("actualNum"), 3));
						serialMsg.add(itemSerialMsg);
					} else {
						lsids.add(trainId);
						Map<String, Object> itemTrain = new HashMap<String, Object>();
						itemTrain.put("id", map.get("id"));
						itemTrain.put("deliverTime", map.get("deliverTime"));
						itemTrain.put("plateName", map.get("plateName"));
						itemTrain.put("clientName", map.get("clientName"));
						itemTrain.put("actualNum", map.get("actualNum"));
						itemTrain.put("operator", map.get("operator"));
						itemTrain.put("userName", map.get("userName"));
						itemTrain.put("status", map.get("status"));
						itemTrain.put("statusValue", map.get("statusValue"));

						Map<String, Object> itemSerialMsg = new HashMap<String, Object>();
						itemSerialMsg.put("goodslogId", map.get("goodslogId"));
						itemSerialMsg.put("serial", map.get("serial"));
						itemSerialMsg.put("actualNum", map.get("actualNum"));
						itemSerialMsg.put("deliverNum", map.get("deliverNum"));
						itemSerialMsg.put("createTime", map.get("createTime"));
						itemSerialMsg.put("invoiceTime", map.get("invoiceTime"));
						itemSerialMsg.put("productId", map.get("productId"));
						itemSerialMsg.put("productName", map.get("productName"));
						itemSerialMsg.put("tankName", map.get("tankName"));
						List<Map<String, Object>> serialMsg = new ArrayList<Map<String, Object>>();
						serialMsg.add(itemSerialMsg);
						itemTrain.put("serialData", serialMsg);
						trainData.put(trainId, itemTrain);
					}
				}

				for (int i = 0; i < lsids.size(); i++) {
					hdata.add(trainData.get(lsids.get(i)));
				}
			}
			oaMsg.getData().addAll(hdata);
			oaMsg.getMap().put(Constant.TOTALRECORD,
					vehicleDeliverWorkDao.getTrainListCount(vehicleDeliverWorkQueryDto) + "");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (Exception re) {
			LOG.error("service获取车发出库列表失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg saveVehiclePlan(VehiclePlan vehiclePlan) {

		OaMsg oaMsg = new OaMsg();
		try {

			vehicleDeliverWorkDao.saveVehiclePlan(vehiclePlan);

			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (Exception re) {
			LOG.error("插入失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg searchLading(VehicleDeliverWorkQueryDto vehicleDeliverWorkQueryDto) {
		OaMsg oaMsg = new OaMsg();
		try {
			List<Map<String, Object>> data = vehicleDeliverWorkDao.searchLading(vehicleDeliverWorkQueryDto);
			if (data != null && data.size() > 0) {
				for (int i = 0; i < data.size(); i++) {
					if (data.get(i).get("id") != null && Integer.valueOf(data.get(i).get("id").toString()) != 0) {
						data.get(i).put("waitAmount", goodsLogDao
								.queryWaitAmount(Integer.valueOf(data.get(i).get("id").toString())).get("waitAmount"));
					}
				}
			}
			oaMsg.getData().addAll(data);
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (Exception re) {
			LOG.error("插入失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg saveBatchVehicleInfo(BatchVehicleInfo batchVehicleInfo) {

		OaMsg oaMsg = new OaMsg();
		try {

			vehicleDeliverWorkDao.saveBatchVehicleInfo(batchVehicleInfo);

			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (Exception re) {
			LOG.error("插入失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public HSSFWorkbook exportTruckExcel(HttpServletRequest request, final VehicleDeliverWorkQueryDto vDto)
			throws OAException {
		ExcelUtil excelUtil=new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/truckList.xls",new CallBack() {
			@Override
			public void setSheetValue(HSSFSheet sheet) {
			List<Object> data=list(vDto, new PageView(0, 0)).getData();
			Map<String, Object> map=null;
			List<Map<String, Object>> itemList=null;
			HSSFRow itemRow=null,itemItemRow=null;
				if(data!=null&&data.size()>0){
					int itemRowNum=2, startRow ,endRow;
				for(int i=0,len=data.size();i<len;i++){
					startRow=itemRowNum;
					itemRow=sheet.createRow(itemRowNum++);
					itemRow.setHeight(sheet.getRow(1).getHeight());
					for(int j=0;j<10;j++){
						itemRow.createCell(j).setCellStyle(sheet.getRow(1).getCell(j).getCellStyle());
					}
					map=(Map<String, Object>) data.get(i);
					itemRow.getCell(0).setCellValue(i+1);//序号
					itemRow.getCell(1).setCellValue(FormatUtils.getStringValue(map.get("plateName")));//车牌号
					itemRow.getCell(7).setCellValue(FormatUtils.getStringValue(map.get("clientName")));//客户名称
					itemRow.getCell(8).setCellValue(FormatUtils.getDoubleValue(map.get("actualNum")));//实发量
					itemRow.getCell(9).setCellValue(FormatUtils.getStringValue(map.get("userName")));//开票人
					itemList=(List<Map<String, Object>>) map.get("serialData");
					for(int l=0;l<itemList.size();l++){
						map=itemList.get(l);
						if(l!=0){
							itemRow=sheet.createRow(itemRowNum++);
							itemRow.setHeight(sheet.getRow(1).getHeight());
							for(int j=0;j<10;j++){
								itemRow.createCell(j).setCellStyle(sheet.getRow(1).getCell(j).getCellStyle());
							}
						}
						itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(map.get("serial")));//通知单号
						itemRow.getCell(3).setCellValue(FormatUtils.getStringValue(map.get("invoiceTime")));//开票日期
						itemRow.getCell(4).setCellValue(FormatUtils.getStringValue(map.get("productName")));//货品
						itemRow.getCell(5).setCellValue(FormatUtils.getStringValue(map.get("deliverNum")));//开票量
						itemRow.getCell(6).setCellValue(FormatUtils.getStringValue(map.get("tankName")));//罐号
					}
					endRow=itemRowNum-1;
					  if(itemList.size()>1){
                     	 sheet.addMergedRegion(new Region(startRow,(short)0, endRow, (short)0));
                     	 sheet.addMergedRegion(new Region(startRow,(short)1, endRow, (short)1));
                     	 sheet.addMergedRegion(new Region(startRow,(short)7, endRow, (short)7));
                     	 sheet.addMergedRegion(new Region(startRow,(short)8, endRow, (short)8));
                     	 sheet.addMergedRegion(new Region(startRow,(short)9, endRow, (short)9));
                      }
				}
				}
			}
		});
		return excelUtil.getWorkbook();
	}

}
