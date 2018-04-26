package com.skycloud.oa.outbound.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dao.ShipDeliveryMeasureDao;
import com.skycloud.oa.outbound.dto.ShipDeliverMeasureDto;
import com.skycloud.oa.outbound.model.ShipDeliveryMeasure;
import com.skycloud.oa.outbound.service.ShipDeliveryMeasureService;
import com.skycloud.oa.report.utils.FormatUtils;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.ExcelUtil;
import com.skycloud.oa.utils.ExcelUtil.CallBack;
import com.skycloud.oa.utils.OaMsg;

/**
 * <p>台账管理---流量计台账</p>
 * @ClassName:ShipDeliveryMeasureServiceImpl
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年9月18日 上午11:23:10
 *
 */
@Service
public class ShipDeliveryMeasureServiceImpl implements ShipDeliveryMeasureService 
{
	@Autowired
	private ShipDeliveryMeasureDao shipDeliveryMeasureDao ;
	private static Logger LOG = Logger.getLogger(ShipDeliveryMeasureServiceImpl.class);
	
	@Override
	public OaMsg list(ShipDeliverMeasureDto shipDeliverMeasureDto,PageView pageView) 
	{
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(shipDeliveryMeasureDao.list(shipDeliverMeasureDto, pageView));
			oaMsg.getMap().put(Constant.TOTALRECORD, shipDeliveryMeasureDao.getCount(shipDeliverMeasureDto) + "");
		} catch (Exception re) {
			LOG.error("service查询流量计台账列表失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg get(int id) 
	{
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.getData().add(shipDeliveryMeasureDao.get(id));
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (Exception re) {
			LOG.error("service查询单个流量计信息失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_OUTDELIVERYMEASURE,type=C.LOG_TYPE.DELETE)
	public OaMsg delete(int id) 
	{
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			ShipDeliverMeasureDto sDto=new ShipDeliverMeasureDto();
			sDto.setId(id);
			shipDeliveryMeasureDao.delete(sDto);
		} catch (Exception re) {
			LOG.error("service删除流量计台账失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_OUTDELIVERYMEASURE,type=C.LOG_TYPE.UPDATE)
	public OaMsg addorupdate(ShipDeliveryMeasure shipDelvieryMeasure) 
	{
		OaMsg oaMsg = new OaMsg();
		int id=0;
		try {
			if (shipDelvieryMeasure.getId() > 0){
				id=shipDelvieryMeasure.getId();
				shipDeliveryMeasureDao.update(shipDelvieryMeasure);
			} else {
				id=shipDeliveryMeasureDao.add(shipDelvieryMeasure);
			}
			oaMsg.getMap().put("id", id+"");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (Exception re) {
			LOG.error("service添加或更新流量计台账失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	/**
	 * @Title exportExcel
	 * @Descrption:TODO
	 * @param:@param request
	 * @param:@return
	 * @auhor jiahy
	 * @date 2016年9月18日上午10:01:14
	 * @throws
	 */
	@Override
	public HSSFWorkbook exportExcel(final HttpServletRequest request) {
		 return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/shipDeliveryMeasure.xls", new CallBack() {
			
			@Override
			public void setSheetValue(HSSFSheet sheet) {
				try {
				ShipDeliverMeasureDto sdDto=new ShipDeliverMeasureDto();
				if(request.getParameter("shipName")!=null)
					sdDto.setShipName(request.getParameter("shipName"));
				if(request.getParameter("startTime")!=null)
					sdDto.setStartTime(request.getParameter("startTime"));
				if(request.getParameter("endTime")!=null)
					sdDto.setEndTime(request.getParameter("endTime"));
				    HSSFRow itemRow;
					List<Map<String, Object>> dataList=shipDeliveryMeasureDao.list(sdDto,null);
					Map<String, Object> map;
					for(int i=0,len=dataList.size();i<len;i++){
						itemRow=sheet.createRow(i+2);
						itemRow.setHeight(sheet.getRow(1).getHeight());
						for(int j=0;j<15;j++)
						itemRow.createCell(j).setCellStyle(sheet.getRow(1).getCell(j).getCellStyle());
						map=dataList.get(i);
						itemRow.getCell(0).setCellValue(FormatUtils.getStringValue(map.get("startTime")));
						itemRow.getCell(1).setCellValue(FormatUtils.getStringValue(map.get("shipName")));
						itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(map.get("berthName")));
						itemRow.getCell(3).setCellValue(FormatUtils.getStringValue(map.get("tankName")));
						itemRow.getCell(4).setCellValue(FormatUtils.getStringValue(map.get("tubeName")));
						itemRow.getCell(5).setCellValue(FormatUtils.getDoubleValue(map.get("startLevel")));
						itemRow.getCell(6).setCellValue(FormatUtils.getStringValue(map.get("endLevel")));
						itemRow.getCell(7).setCellValue(FormatUtils.getStringValue(map.get("flowmeter")));
						itemRow.getCell(8).setCellValue(Common.sub(map.get("flowmeter"), map.get("metering"), 3));
						itemRow.getCell(9).setCellValue(FormatUtils.getStringValue(map.get("realAmount")));
						itemRow.getCell(10).setCellValue(Common.sub(map.get("realAmount"), map.get("metering"), 3));
						itemRow.getCell(11).setCellValue(FormatUtils.getStringValue(map.get("shipAmount")));
						itemRow.getCell(12).setCellValue(Common.sub(map.get("shipAmount"), map.get("metering"), 3));
						itemRow.getCell(13).setCellValue(FormatUtils.getStringValue(map.get("metering")));
						itemRow.getCell(14).setCellValue(FormatUtils.getStringValue(map.get("userName")));
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
		}).getWorkbook();
	}

}
