package com.skycloud.oa.order.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hibernate.property.MapAccessor.MapGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.order.dto.ContractDto;
import com.skycloud.oa.order.dto.IntentionDto;
import com.skycloud.oa.order.model.Contract;
import com.skycloud.oa.order.service.ContractService;
import com.skycloud.oa.order.service.IntentionService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.statistics.dto.StatisticsDto;
import com.skycloud.oa.system.dto.ClientDto;
import com.skycloud.oa.system.dto.ProductDto;
import com.skycloud.oa.system.model.ApproveContent;
import com.skycloud.oa.system.model.ApproveWork;
import com.skycloud.oa.system.service.ClientService;
import com.skycloud.oa.system.service.ContractTypeService;
import com.skycloud.oa.system.service.ProductService;
import com.skycloud.oa.system.service.TaxTypeService;
import com.skycloud.oa.system.service.TradeTypeService;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;

/**
 * 合同控制类
 * @ClassName: OrderIntentController 
 * @Description: TODO
 * @author xie
 * @date 2014年11月29日 下午6:50:16
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private IntentionService intentionService;
	
	@Autowired
	private ContractService contractService;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ContractTypeService contractTypeService;
	
	@Autowired
	private TradeTypeService tradeTypeService;
	
	@Autowired
	private TaxTypeService taxTypeService;
	
	/**
	 * 查询所有订单意向
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param intentionDto
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public Object list(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute ContractDto contractDto,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException {
		OaMsg msg = contractService.getContractListByCondition(contractDto, new PageView(pagesize, page));
		return msg;
	}

	
	@RequestMapping(value = "exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,@ModelAttribute ContractDto contractDto) 
	{
		response.setContentType("application/vnd.ms-excel");
		OutputStream fOut = null;
		try 
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			// 进行转码，使其支持中文文件名 type 1-10 是出库用 20开始入库用
			response.setHeader("content-disposition", "attachment;filename=" + sdf.format(new Date()) + ".xls");
			// 产生工作簿对象
//			HSSFWorkbook workbook = invoiceService.exportExcel(request);
			
			OaMsg msg=contractService.getContractListByCondition(contractDto, new PageView(0,0));
			
			String[] str={"合同编号","合同名称","签约客户","类型","货品","数量(吨)"
					,"非保税仓储费","保税仓储费","码头通过费","超期费","港口设施保安费","港务费"
					,"其他费","总金额(元)","签约时间","周期","合同有效期","状态"};
			List<List<Object>> data=new ArrayList<List<Object>>();
			
			for(int i=0;i<msg.getData().size();i++){
				Map<String,Object> map=(Map<String, Object>) msg.getData().get(i);
				List<Object> d=new ArrayList<Object>();
				d.add(map.get("code").toString());
				d.add(map.get("title").toString());
				d.add(map.get("clientName").toString());
				d.add(map.get("typeName").toString());
				d.add(map.get("productNameList").toString());
				d.add(map.get("quantity").toString());
				d.add(!Common.empty(map.get("storagePrice"))?map.get("storagePrice").toString():"");
				d.add(!Common.empty(map.get("protectStoragePrice"))?map.get("protectStoragePrice").toString():"");
				d.add(!Common.empty(map.get("passPrice"))?map.get("passPrice").toString():"");
				d.add(!Common.empty(map.get("overtimePrice"))?map.get("overtimePrice").toString():"");
				d.add(!Common.empty(map.get("portSecurityPrice"))?map.get("portSecurityPrice").toString():"");
				d.add(!Common.empty(map.get("portServicePrice"))?map.get("portServicePrice").toString():"");
				d.add(!Common.empty(map.get("otherPrice"))?map.get("otherPrice").toString():"");
				d.add(map.get("totalPrice").toString());
				d.add(map.get("mSignDate").toString().split(" ")[0]);
				d.add(map.get("period").toString());
				
				String startDate=!Common.empty(map.get("startDate"))?map.get("startDate").toString().split(" ")[0]:"";
				String endDate=!Common.empty(map.get("endDate"))?map.get("endDate").toString().split(" ")[0]:"";
				
				d.add(startDate+"-"+endDate);
				d.add(map.get("statusName").toString());
				data.add(d);
			}
			HSSFWorkbook workbook=Common.getExcel("合同汇总", str, data,new int[]{0,0,0,0,0,1,1,1,1,1,1,1,1,1,0,1,0,0});
			
			fOut = response.getOutputStream();
			workbook.write(fOut);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				fOut.flush();
				fOut.close();
			} 
			catch (IOException e) 
			{
				
			}
		}
	}
	
	
	
	/**
	 * 添加合同
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param contract
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public Object save(HttpServletRequest request, HttpServletResponse response,String ids, @ModelAttribute Contract contract,String signDate,String tStartDate,String tEndDate,String tArrivalTime) throws OAException {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			contract.setSignDate(new Timestamp(format.parse(signDate).getTime()));
			if(!"".equals(tStartDate)){
				contract.setStartDate(new Timestamp(format.parse(tStartDate).getTime()));
			}
			if(!"".equals(tEndDate)){
				contract.setEndDate(new Timestamp(format.parse(tEndDate).getTime()));
			}
			if(!"".equals(tArrivalTime)){
				contract.setArrivalTime(new Timestamp(format.parse(tArrivalTime).getTime()));
			}
		}catch(Exception e) {
			contract.setSignDate(new Timestamp(new Date().getTime()));
//			contract.setStartDate(new Timestamp(new Date().getTime()));
//			contract.setEndDate(new Timestamp(new Date().getTime()));
		}
		contract.setCreateTime(new Timestamp(new Date().getTime()));
		User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		contract.setCreateUserId(user.getId());
		return contractService.addContract(contract,ids);
	}
	
	
	/**
	 * 合同号校验
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param contract
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "check")
	@ResponseBody
	public Object check(HttpServletRequest request, HttpServletResponse response,String code) throws OAException {
		return contractService.checkCode(code);
	}
	
	
	
	
	/**
	 * 删除合同
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param intentionIds
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public Object delete(HttpServletRequest request, HttpServletResponse response, String contractIds) throws OAException {
		return contractService.deleteContract(contractIds);
	}
	
	/**
	 * 更新
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param contract
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "update")
	@ResponseBody
	public Object update(HttpServletRequest request, HttpServletResponse response, @ModelAttribute Contract contract,String signDate,String tReviewTime,String tStartDate,String tEndDate,String tArrivalTime) throws OAException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			contract.setSignDate(new Timestamp(format.parse(signDate).getTime()));
			if(!"".equals(tStartDate)){
				contract.setStartDate(new Timestamp(format.parse(tStartDate).getTime()));
			}
			if(!"".equals(tEndDate)){
				contract.setEndDate(new Timestamp(format.parse(tEndDate).getTime()));
			}
			if(!"".equals(tArrivalTime)){
				contract.setArrivalTime(new Timestamp(format.parse(tArrivalTime).getTime()));
			}
		}catch(Exception e) {
			contract.setSignDate(new Timestamp(new Date().getTime()));
//			contract.setStartDate(new Timestamp(new Date().getTime()));
//			contract.setEndDate(new Timestamp(new Date().getTime()));
		}
		if(!Common.empty(tReviewTime)){
			long mReviewTime;
			//				mReviewTime = format.parse(tReviewTime).getTime() / 1000;
			contract.setReviewTime(new Date().getTime()/1000);
		}
		
		User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		contract.setEditUserId(user.getId());
		if(contract.getStatus()==2){
			contract.setReviewUserId(user.getId());
		}
		return contractService.updateContract(contract);
	}
	
	@RequestMapping(value="updatestatus")
	@ResponseBody
	public Object updateStatus(HttpServletRequest request, HttpServletResponse response,@ModelAttribute Contract contract) throws OAException{
		return contractService.updateContractStatus(contract);
	}
	
	@RequestMapping(value="updatefile")
	@ResponseBody
	public Object updatefile(HttpServletRequest request, HttpServletResponse response,  
	         @RequestParam MultipartFile file, int id) throws OAException{
		
		OaMsg oaMsg=new OaMsg();
		
		//MultipartFile是对当前上传的文件的封装，当要同时上传多个文件时，可以给定多个MultipartFile参数  
        if (!file.isEmpty()) {  
        	FileOutputStream fos = null; 
            try {
				byte[] bytes = file.getBytes();
		            //查找目录，如果不存在，就创建
		            File dirFile = new File(request.getSession().getServletContext().getRealPath("/")+"resource/upload");
		            if(!dirFile.exists()){
		                if(!dirFile.mkdir())
							try {
								throw new Exception("目录不存在，创建失败！");
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		            }
				String url=request.getSession().getServletContext().getRealPath("/")+"resource/upload/"+file.getOriginalFilename();  
				
				fos = new FileOutputStream(url);                 
				fos.write(bytes); 
				
            
            Contract contract = new Contract();
            contract.setId(id);
            contract.setFileUrl("/resource/upload/"+file.getOriginalFilename());
            contractService.updateContract(contract);
            
            oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
            
           return oaMsg;
            } catch (IOException e) {
            	// TODO Auto-generated catch block
            	e.printStackTrace();
            	 oaMsg.setCode(Constant.SYS_CODE_NULL_ERR);
            	 return oaMsg;
            			 }  
       } else {  
    	   oaMsg.setCode(Constant.SYS_CODE_NULL_ERR);
    		 return oaMsg;
             
      }  
	}
	
	
	/**
	 * 查询审批信息
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param approveContent
	 * @param user  是否是当前用户
	 * @return
	 * @throws OAException
	 * 2015-4-2 下午4:29:24
	 */
	@RequestMapping(value="getapprovecontent")
	@ResponseBody
	public Object getapprovework(HttpServletRequest request, HttpServletResponse response,@ModelAttribute ApproveContent approveContent,int user) throws OAException{
		if(user==1){
			User mUser = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			approveContent.setUserId(mUser.getId());
		}
		return contractService.getApproveContent(approveContent);
	}
	
	
	
	/**
	 * 发送给多个人审批
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param ids
	 * @param contractId
	 * @param content
	 * @return
	 * @throws OAException
	 * 2015-4-2 下午4:29:11
	 */
	@RequestMapping(value="sendcheck")
	@ResponseBody
	public Object sendcheck(HttpServletRequest request, HttpServletResponse response,String ids,int contractId,String content) throws OAException{
		return contractService.sendCheck(ids,contractId,content);
	}
	
	/**
	 * 抄送
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param ids
	 * @param contractId
	 * @return
	 * @throws OAException
	 * 2015-4-8 下午1:07:27
	 */
	@RequestMapping(value="sendcopy")
	@ResponseBody
	public Object sendcopy(HttpServletRequest request, HttpServletResponse response,String ids,int contractId) throws OAException{
		return contractService.sendcopy(ids,contractId);
	}
	
	
	/**
	 * 检查是否有上一次记录
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param type 类型 1：提交 2：抄送
	 * @return
	 * @throws OAException
	 * 2015-4-8 下午1:08:53
	 */
	@RequestMapping(value="checkcache")
	@ResponseBody
	public Object checkcache(HttpServletRequest request, HttpServletResponse response,int workType) throws OAException{
		User mUser = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		return contractService.checkcache(mUser.getId(),workType);
	}
	
	

	/**
	 * 查询合同下所有货批
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws OAException
	 * 2015-10-14 下午5:35:15
	 */
	@RequestMapping(value="getCargoCode")
	@ResponseBody
	public Object getCargoCode(HttpServletRequest request, HttpServletResponse response,int id) throws OAException{
		return contractService.getCargoCode(id);
	}
	
	
	
}
