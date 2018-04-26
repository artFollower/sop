package com.skycloud.oa.inbound.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dto.ExceedCleanLogDto;
import com.skycloud.oa.feebill.model.ExceedCleanLog;
import com.skycloud.oa.inbound.dto.ExceedFeeDto;
import com.skycloud.oa.inbound.dto.ExceedFeeLogDto;
import com.skycloud.oa.inbound.model.ExceedFee;
import com.skycloud.oa.inbound.service.ExceedFeeService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.ExcelOutputUtil;
import com.skycloud.oa.utils.ExcelOutputUtil.CallBack;
import com.skycloud.oa.utils.OaMsg;

/**
 * 超期费
 * 
 * @author 作者:jiahy
 * @version 时间：2015年3月16日 下午3:13:30
 */
@Controller
@RequestMapping("/exceedfee")
public class ExceedFeeController {

	@Autowired
	private ExceedFeeService exceedFeeService;

	// 获取每天超期的货批的列表
	@RequestMapping(value = "/cargoitemlist")
	@ResponseBody
	public OaMsg cargoitemlist(HttpServletRequest request,HttpServletResponse response, ExceedFeeDto eFeeDto)throws OAException {
		return exceedFeeService.getExceedFeeCargoItemList(eFeeDto);
	}

	// 获取每天超期的货批的流转列表
	@RequestMapping(value = "/cargoturnlist")
	@ResponseBody
	public OaMsg cargoturnlist(HttpServletRequest request,HttpServletResponse response, ExceedFeeDto eFeeDto)throws OAException {
		return exceedFeeService.getCargoTurnList(eFeeDto);
	}

	// 获取超期的货批列表
	@RequestMapping(value = "/cargolist")
	@ResponseBody
	public OaMsg cargolist(HttpServletRequest request,HttpServletResponse response,ExceedFeeDto eFeeDto,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page)throws OAException {
		return exceedFeeService.getExceedFeeCargoList(eFeeDto, new PageView(pagesize, page));
	}

	// 获取每天超期的提单的列表
	@RequestMapping(value = "/ladingitemlist")
	@ResponseBody
	public OaMsg ladingitemlist(HttpServletRequest request,HttpServletResponse response, ExceedFeeDto eFeeDto)throws OAException {
		return exceedFeeService.getExceedFeeLadingItemList(eFeeDto);
	}

	// 获取每天超期的提单的流转列表
	@RequestMapping(value = "/ladingturnlist")
	@ResponseBody
	public OaMsg ladingturnlist(HttpServletRequest request,HttpServletResponse response, ExceedFeeDto eFeeDto)throws OAException {
		return exceedFeeService.getLadingTurnList(eFeeDto);
	}

	// 获取每天超期的提单的流转列表
	@RequestMapping(value = "/ladingturnlst")
	@ResponseBody
	public OaMsg ladingturnlst(HttpServletRequest request,HttpServletResponse response, ExceedFeeDto eFeeDto)throws OAException {
		return exceedFeeService.getLadingTurnLst(eFeeDto);
	}

	// 获取要计算超期费的提单列表
	@RequestMapping(value = "/ladinglist")
	@ResponseBody
	public OaMsg ladinglist(HttpServletRequest request,HttpServletResponse response,ExceedFeeDto eFeeDto,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page)throws OAException {
		return exceedFeeService.getExceedFeeLadingList(eFeeDto, new PageView(pagesize, page));
	}

	// 获取超期费列表
	@RequestMapping(value = "/list")
	@ResponseBody
	public OaMsg list(HttpServletRequest request,HttpServletResponse response,ExceedFeeDto eFeeDto,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page)throws OAException {
		OaMsg oaMsg = exceedFeeService.getExceedFeeList(eFeeDto, new PageView(pagesize, page));
		return oaMsg;
	}

	// 更新超期费结算单
	@RequestMapping(value = "/update")
	@ResponseBody
	public OaMsg update(HttpServletRequest request,HttpServletResponse response, @ModelAttribute ExceedFee exceedFee)throws OAException {
		User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		if (exceedFee.getStatus() != null&& (exceedFee.getStatus() == 0 || exceedFee.getStatus() == 1)) 
			exceedFee.setCreateUserId(user.getId());
		OaMsg oaMsg = exceedFeeService.updateExceedFee(exceedFee);
		return oaMsg;
	}

	// 添加超期费结算单
	@RequestMapping(value = "/add")
	@ResponseBody
	public OaMsg add(HttpServletRequest request, HttpServletResponse response,@ModelAttribute ExceedFee exceedFee) throws OAException {
		User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		exceedFee.setCreateUserId(user.getId());
		OaMsg oaMsg = exceedFeeService.addExceedFee(exceedFee);
		return oaMsg;
	}

	// 添加或更新超期费结算单
	@RequestMapping(value = "/addorupdate")
	@ResponseBody
	public OaMsg addorupdate(HttpServletRequest request,HttpServletResponse response, String eFeeDto) throws OAException {
		ExceedFeeDto exceedFeeDto = new Gson().fromJson(eFeeDto,ExceedFeeDto.class);
		OaMsg oaMsg = exceedFeeService.addOrUpdateExceedFee(exceedFeeDto);
		return oaMsg;
	}

	// 获取超期费结算单信息
	@RequestMapping(value = "/getexceedfeemsg")
	@ResponseBody
	public OaMsg getExceedFeeMsg(HttpServletRequest request,HttpServletResponse response, @ModelAttribute ExceedFeeDto eDto)throws OAException {
		OaMsg oaMsg = exceedFeeService.getExceedFeeMsg(eDto);
		return oaMsg;
	}

	// 删除超期费结算单
	@RequestMapping(value = "/delete")
	@ResponseBody
	public OaMsg delete(HttpServletRequest request,HttpServletResponse response, ExceedFeeDto eFeeDto)throws OAException {
		OaMsg oaMsg = exceedFeeService.deleteExceedFee(eFeeDto);
		return oaMsg;
	}

	// 获取超期费结算单编号
	@RequestMapping(value = "/getcodenum")
	@ResponseBody
	public OaMsg getCodeNum(HttpServletRequest request,HttpServletResponse response, ExceedFeeDto eFeeDto)throws OAException {
		OaMsg oaMsg = exceedFeeService.getCodeNum(eFeeDto);
		return oaMsg;
	}

	// 货批超期费结清操作
	@RequestMapping(value = "/cleancargo")
	@ResponseBody
	public OaMsg cleanCargo(HttpServletRequest request,HttpServletResponse response, ExceedFeeDto eFeeDto)throws OAException {
		OaMsg oaMsg = exceedFeeService.cleanCargo(eFeeDto);
		return oaMsg;
	}

	// 转卖提单超期费结清操作
	@RequestMapping(value = "/cleanlading")
	@ResponseBody
	public OaMsg cleanLading(HttpServletRequest request,HttpServletResponse response, ExceedFeeDto eFeeDto)throws OAException{
		OaMsg oaMsg = exceedFeeService.cleanLading(eFeeDto);
		return oaMsg;
	}

	@RequestMapping(value = "/backstatus")
	@ResponseBody
	public OaMsg backStatus(HttpServletRequest request,HttpServletResponse response, @ModelAttribute ExceedFee exceedFee)throws OAException{
		OaMsg oaMsg = exceedFeeService.backStatus(exceedFee);
		return oaMsg;
	}

	/**
	 * 获取已经生成提单的每天提单记录
	 * 
	 * @param request
	 * @param response
	 * @param exceedFee
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "/getexceedfeelog")
	@ResponseBody
	public OaMsg getExceedFeeLog(HttpServletRequest request,HttpServletResponse response, @ModelAttribute ExceedFeeLogDto elDto)
			throws OAException {
		return exceedFeeService.getExceedFeeLog(elDto);
	}

	/**
	 * 保存或者添加每日超期记录列表
	 * 
	 * @param request
	 * @param response
	 * @param efLogsList
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "/addexceedfeelogs")
	@ResponseBody
	public OaMsg addExceedFeeLogs(HttpServletRequest request,HttpServletResponse response, String eflogsDto) throws OAException {
		ExceedFeeLogDto logsList = new Gson().fromJson(eflogsDto,ExceedFeeLogDto.class);
		return exceedFeeService.addExceedFeeLogs(logsList.getEfList());
	}

	@RequestMapping(value = "excel")
	public void exportLogExcel(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute final ExceedFeeDto eFeeDto, final int sType) {
		ExcelOutputUtil.handleExcelOutput(request, response, new CallBack() {
			@Override
			public HSSFWorkbook getWorkBook(HttpServletRequest request) {
				OaMsg msg = new OaMsg();
				HSSFWorkbook workbook = null;
				try {
					if (sType == 2)
						msg = exceedFeeService.getLadingTurnLst(eFeeDto);
					else if (sType == 3)
						msg = exceedFeeService.getLadingTurnList(eFeeDto);
					else
						msg = exceedFeeService.getCargoTurnList(eFeeDto);
					workbook = exceedFeeService.exportLogExcel(request, msg,
							eFeeDto, sType);
				} catch (OAException e) {
					e.printStackTrace();
				}
				return workbook;
			}
		});
	};
   @RequestMapping(value="exportExcelList")
   public void exportExcelList(HttpServletRequest request,HttpServletResponse response,final ExceedFeeDto eFeeDto){
		ExcelOutputUtil.handleExcelOutput(request, response, new CallBack() {
			@Override
			public HSSFWorkbook getWorkBook(HttpServletRequest request) {
				HSSFWorkbook workbook = null;
				 workbook=exceedFeeService.exportExcelList(request,eFeeDto);
				return workbook;
			}
		});
	
   };
   
   /**
    * 添加结清日志
 * @Title addExceedCleanLog
 * @Descrption:TODO
 * @param:@param exceedCleanLog
 * @param:@return
 * @param:@throws OAException
 * @return:OaMsg
 * @auhor jiahy
 * @date 2017年3月25日上午9:24:55
 * @throws
 */
@RequestMapping(value = "/addExceedCleanLog")
	@ResponseBody
	public OaMsg addExceedCleanLog(ExceedCleanLog exceedCleanLog) throws OAException {
		return exceedFeeService.addExceedCleanLog(exceedCleanLog);
	}
	/**
	 * 查看结清日志
	* @Title addExceedCleanLog
	* @Descrption:TODO
	* @param:@param exceedCleanLog
	* @param:@return
	* @param:@throws OAException
	* @return:OaMsg
	* @auhor jiahy
	* @date 2017年3月25日上午9:24:55
	* @throws
	*/
	@RequestMapping(value = "/getExceedCleanLog")
	@ResponseBody
	public OaMsg getExceedCleanLog(@ModelAttribute ExceedCleanLogDto eclDto,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException {
		return exceedFeeService.getExceedCleanLog(eclDto);
	}

	/**
	 * 删除结清日志
	 * @Title deleteExceedCleanLog
	 * @Descrption:TODO
	 * @param:@param id
	 * @param:@return
	 * @param:@throws OAException
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2017年3月25日上午9:26:43
	 * @throws
	 */
	@RequestMapping(value = "/deleteExceedCleanLog")
	@ResponseBody
	public OaMsg deleteExceedCleanLog(int id) throws OAException {
		return exceedFeeService.deleteExceedCleanLog(id);
	}

}
