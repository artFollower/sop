package com.skycloud.oa.outbound.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dto.GoodsLogDto;
import com.skycloud.oa.outbound.model.GoodsLog;
import com.skycloud.oa.outbound.service.GoodsLogService;
import com.skycloud.oa.utils.ExcelOutputUtil;
import com.skycloud.oa.utils.OaMsg;
import com.skycloud.oa.utils.ExcelOutputUtil.CallBack;

/**
 * 
 * <p>出库管理---发货开票</p>
 * @ClassName:GoodsLogController
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年5月27日 下午8:21:01
 *
 */
@Controller
@RequestMapping("/invoice")
public class GoodsLogController 
{
	@Autowired
	private GoodsLogService invoiceService ;
	
	/**
	 * 查询发货开票列表
	 * @Title:GoodsLogController
	 * @Description:
	 * @param pagesize
	 * @param page
	 * @param invoiceDto
	 * @return
	 * @Date:2015年5月27日 下午8:23:24
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public OaMsg list(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page,
			@ModelAttribute GoodsLogDto invoiceDto) throws OAException
	{
		return invoiceService.list(invoiceDto,new PageView(pagesize, page)) ;
	}
	/**
	 * 获取打印联单信息
	 *@author jiahy
	 * @param request
	 * @param response
	 * @param invoiceDto
	 * @return
	 */
	@RequestMapping(value="getprintlist")
	@ResponseBody
	public OaMsg getPrintList(HttpServletRequest request,HttpServletResponse response, @ModelAttribute GoodsLogDto invoiceDto) throws OAException{
		return invoiceService.getPrintList(invoiceDto);
	}
	/**
	 * 通过id获取开票信息
	 * @Title:GoodsLogController
	 * @Description:
	 * @param id
	 * @return
	 * @Date:2015年5月27日 下午8:23:39
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value="get")
	@ResponseBody
	public OaMsg get(String id) throws OAException{
		return invoiceService.get(id) ;
	}
	
	/**
	 * 查询通知单号
	 * @Title:GoodsLogController
	 * @Description:
	 * @return
	 * @Date:2015年5月27日 下午8:23:55
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value="getSerial")
	@ResponseBody
	public OaMsg getSerial(String serial) throws OAException
	{
		return invoiceService.getSerial(serial) ;
	}
	
	/**
	 * 添加发货开票
	 * @Title:GoodsLogController
	 * @Description:
	 * @param invoiceDto
	 * @return
	 * @Date:2015年5月27日 下午8:24:12
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value="add")
	@ResponseBody
	public OaMsg add(HttpServletRequest request,HttpServletResponse response,String invoiceDto) throws OAException
	{
		GoodsLogDto s  = new Gson().fromJson(invoiceDto, GoodsLogDto.class) ;
		 String ip = request.getHeader("x-forwarded-for"); 
		    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		        ip = request.getHeader("Proxy-Client-IP"); 
		    } 
		    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		        ip = request.getHeader("WL-Proxy-Client-IP"); 
		    } 
		    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		        ip = request.getRemoteAddr(); 
		    }
		    s.setTip(ip);
		return invoiceService.add(s) ;
	}
	
	/**
	 * 查询提单
	 * @param vehicleDeliverWorkQueryDto
	 * @return
	 * @throws OAException 
	 */
	@RequestMapping(value = "searchLading")
	@ResponseBody
	public OaMsg searchLading( GoodsLogDto invoiceDto) throws OAException{
		return invoiceService.searchLading(invoiceDto) ;
	}
	/**
	 * 更新发货开票
	 * @Title:GoodsLogController
	 * @Description:
	 * @param goodsLog
	 * @return
	 * @Date:2015年5月27日 下午8:25:37
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value="changeInvoice")
	@ResponseBody
	public OaMsg changeInvoice(String goodsLog) throws OAException
	{
		GoodsLog s  = new Gson().fromJson(goodsLog, GoodsLog.class) ;
		return invoiceService.changeInvoice(s) ;
	}
	
	/**
	 * 删除发货开票
	 * @Title:GoodsLogController
	 * @Description:
	 * @param ids
	 * @return
	 * @Date:2015年5月27日 下午8:25:55
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value="delete")
	@ResponseBody
	public OaMsg delete(String ids) throws OAException
	{
		return invoiceService.delete(ids) ;
	}
	
	/**
	 * 校验是否存在联单可能
	 *@author jiahy
	 * @param goods
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value="checkiskupono")
	@ResponseBody
	public OaMsg checkIsKupono(@ModelAttribute GoodsLogDto glDto) throws OAException 
	{
		return invoiceService.checkIsKupono(glDto) ;
	}
	
	
	/**
	 * 将一个发货通知单拆为两个联单
	 *@author jiahy
	 * @param splitNum, goodslog ---id,splitGoodsId (新生成的goodsId)
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value="splitdelivergoods")
	@ResponseBody
	public OaMsg splitDeliverGoods(@ModelAttribute GoodsLogDto glDto) throws OAException 
	{
		return invoiceService.splitDeliverGoods(glDto) ;
	}
	
	/**
	 * 发货查询
	 * @Title splitDeliverGoods
	 * @Descrption:TODO
	 * @param:@param request
	 * @param:@param response
	 * @param:@param pagesize
	 * @param:@param page
	 * @param:@param invoiceDto
	 * @param:@return
	 * @param:@throws OAException
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年2月27日下午2:15:44
	 * @throws
	 */
	@RequestMapping(value="invoicequery")
	@ResponseBody
	public OaMsg invoiceQuery(HttpServletRequest request,HttpServletResponse response,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize, 
	           @RequestParam(defaultValue = "0", required = false, value = "page") int page,
	           @ModelAttribute GoodsLogDto invoiceDto) throws OAException 
	{
		return invoiceService.invoiceQuery(invoiceDto,new PageView(pagesize, page));
	}
	/**
	 * 获取总量
	 * @Title getTotalNum
	 * @Descrption:TODO
	 * @param:@param request
	 * @param:@param response
	 * @param:@param invoiceDto
	 * @param:@return
	 * @param:@throws OAException
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年6月12日下午3:20:25
	 * @throws
	 */
	@RequestMapping(value="getTotalNum")
	@ResponseBody
	public OaMsg getTotalNum(HttpServletRequest request,HttpServletResponse response, @ModelAttribute GoodsLogDto invoiceDto) throws OAException 
	{
		return invoiceService.getTotalNum(invoiceDto);
	}
	
	/**
	 * @Title getFlowMeterBySerial
	 * @Descrption:TODO
	 * @param:@param request
	 * @param:@param response
	 * @param:@param invoiceDto
	 * @param:@return
	 * @param:@throws OAException
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年5月25日上午9:43:19
	 * @throws
	 */
	@RequestMapping(value="getFlowMeterBySerial")
	@ResponseBody
	public OaMsg getFlowMeterBySerial(String serial) throws OAException 
	{
		return invoiceService.getFlowMeterBySerial(serial);
	}
	
	/**导出出库信息表*/
	@RequestMapping(value = "exportExcel")
	public void exportInbound(HttpServletRequest request,HttpServletResponse response) throws OAException {
		ExcelOutputUtil.handleExcelOutput(request, response, new CallBack(){

			@Override
			public HSSFWorkbook getWorkBook(HttpServletRequest request) {
				HSSFWorkbook workbook = null;
				try {
					workbook= invoiceService.exportInbound(request);
					
				} catch (OAException e) {
					e.printStackTrace();
				}
				return workbook;
			}
		});
	}
}
