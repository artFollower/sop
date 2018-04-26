/**
 * 
 */
package com.skycloud.oa.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.report.utils.FormatUtils;
import com.skycloud.oa.system.dao.TankMeasureDao;
import com.skycloud.oa.system.dto.TankMeasureDto;
import com.skycloud.oa.system.model.TankMeasure;
import com.skycloud.oa.system.service.TankMeasureService;
import com.skycloud.oa.utils.ExcelUtil;
import com.skycloud.oa.utils.ExcelUtil.CallBack;
import com.skycloud.oa.utils.OaMsg;

/**
 *
 * @author jiahy
 * @version 2015年11月30日 下午3:46:02
 */
@Service
public class TankMeasureServiceImpl implements TankMeasureService {
	private static Logger LOG = Logger.getLogger(TankMeasureServiceImpl.class);
	@Autowired
	private TankMeasureDao tankMeasureDao;
	@Override
	public OaMsg getList(TankMeasureDto tankMeasureDto, PageView pageView)
			throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(tankMeasureDao.getList(tankMeasureDto,pageView.getStartRecord(),pageView.getMaxresult()));
			oaMsg.getMap().put(Constant.TOTALRECORD, tankMeasureDao.getListCount(tankMeasureDto)+"");
		} catch (OAException re) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg addTankMeasure(TankMeasure tankMeasure) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd") ;
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			tankMeasure.setNotify(tankMeasureDao.getCodeNum(sdf1.format(new Date())).get("codenum").toString());
			oaMsg.getMap().put("id", tankMeasureDao.addTankMeasure(tankMeasure)+"");
		} catch (OAException re) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg updateTankMeasure(TankMeasure tankMeasure) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			tankMeasureDao.updateTankMeasure(tankMeasure);
			
		} catch (OAException re) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg deleteTankMeasure(TankMeasureDto tankMeasureDto)
			throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			tankMeasureDao.deleteTankMeasure(tankMeasureDto);
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (OAException re) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getCodeNum() throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd") ;
			oaMsg.getMap().put("codenum", tankMeasureDao.getCodeNum(sdf1.format(new Date())).get("codenum").toString());
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (OAException re) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	/**
	 * @Title exportExcel
	 * @Descrption:TODO
	 * @param:@param request
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年9月13日上午9:06:28
	 * @throws
	 */
	@Override
	public HSSFWorkbook exportExcel(final HttpServletRequest request) throws OAException {
		return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/tankMeasure.xls",
				new CallBack() {
					
					@Override
					public void setSheetValue(HSSFSheet sheet) {
						try {
						TankMeasureDto tDto =new TankMeasureDto();
						if(request.getParameter("productId")!=null)
							tDto.setProductId(Integer.valueOf(request.getParameter("productId")));
						if(request.getParameter("tankId")!=null)
							tDto.setTankId(Integer.valueOf(request.getParameter("tankId")));
						List<Map<String, Object>>	data=tankMeasureDao.getList(tDto, 0, 0);
						if(data!=null&&data.size()>0){
							HSSFRow itemRow;
							Map<String, Object> map;
							for (int i = 0,len=data.size(); i <len; i++) {
								itemRow=sheet.createRow(i+2);
								itemRow.setHeight(sheet.getRow(1).getHeight());
								for (int j = 0; j < 11; j++) 
									itemRow.createCell(j).setCellStyle(sheet.getRow(1).getCell(j).getCellStyle());
								map=data.get(i);
								itemRow.getCell(0).setCellValue(FormatUtils.getStringValue(map.get("notify")));
								itemRow.getCell(1).setCellValue(FormatUtils.getStringValue(map.get("productName")));
								itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(map.get("tankName")));
								itemRow.getCell(3).setCellValue(FormatUtils.getStringValue(map.get("port")));
								itemRow.getCell(4).setCellValue(FormatUtils.getDoubleValue(map.get("normalDensity")));
								itemRow.getCell(5).setCellValue(FormatUtils.getDoubleValue(map.get("textDensity")));
								itemRow.getCell(6).setCellValue(FormatUtils.getDoubleValue(map.get("textTemperature")));
								itemRow.getCell(7).setCellValue(FormatUtils.getDoubleValue(map.get("volumeRatio")));
								itemRow.getCell(8).setCellValue(FormatUtils.getDoubleValue(map.get("tankTemperature")));
								itemRow.getCell(9).setCellValue(FormatUtils.getStringValue(map.get("createTimeStr")));
								itemRow.getCell(10).setCellValue(FormatUtils.getStringValue(map.get("description")));
							}
							
						}
					} catch (OAException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						
					}
				}).getWorkbook();
		
		
	}

}
