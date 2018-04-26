
package com.skycloud.oa.outbound.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.common.dto.SecurityCodeDto;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.model.ArrivalInfo;
import com.skycloud.oa.inbound.model.BerthAssess;
import com.skycloud.oa.inbound.model.BerthProgram;
import com.skycloud.oa.inbound.model.TransportProgram;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dto.ShipArrivalDto;
import com.skycloud.oa.outbound.dto.ShipDeliverWorkQueryDto;
import com.skycloud.oa.outbound.model.UploadFileInfo;
import com.skycloud.oa.outbound.service.OutBoundService;
import com.skycloud.oa.utils.OaMsg;

/**
 * 船舶出库
 * @ClassName OutBoundController
 * @Description TODO
 * @author jiahy
 * @date 2016年3月9日上午10:17:45
 */
@Controller
@RequestMapping("/outboundserial")
public class OutBoundController 
{

	@Autowired
	private OutBoundService outBoundService ;
	/**
	 * 查询船发列表
	 * @param request
	 * @param response
	 * @param arrivalDto
	 * @param pagesize
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public OaMsg list(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute ShipArrivalDto arrivalDto,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page)throws OAException{
		return outBoundService.getOutBoundList(arrivalDto, new PageView(pagesize, page));
	}
	/**
	 * 更新船发列表信息
	 */
	@RequestMapping(value = "updateBaseInfo")
	@ResponseBody
	public OaMsg upBaseInfo(@ModelAttribute ShipArrivalDto arrivalDto)throws OAException{
		return outBoundService.updateBaseInfo(arrivalDto);
	}
	/**
	 * 查询到港的货体信息
	 */
	@RequestMapping(value = "getbasegoodsinfo")
	@ResponseBody
	public OaMsg getBaseGoodsInfo(String arrivalId)throws OAException{
		return outBoundService.getBaseGoodsInfo(arrivalId);
	}
	
	/**
	 * 通过id获取作业计划信息
	 * @param arrivalId
	 * @return
	 * @throws OAException 
	 */
	@RequestMapping(value = "getArrivalInfoById")
	@ResponseBody
	public OaMsg getArrivalInfoById(String arrivalId) throws OAException{
		return outBoundService.getArrivalInfoById(arrivalId) ;
	}
	

	/**
	 * 通过id获取到港计划信息
	 * @param arrivalId
	 * @return
	 * @throws OAException 
	 */
	@RequestMapping(value = "getArrivalById")
	@ResponseBody
	public OaMsg getArrivalById(String arrivalId) throws OAException{
		return outBoundService.getArrivalById(arrivalId) ;
	}
	
	/**
	 * 保存到港计划信息
	 * @param arrivalInfo
	 * @param arrivalId
	 * @return
	 * @throws OAException 
	 */
	@RequestMapping(value = "addorupdatearrivalinfo")
	@ResponseBody
	@RequiresPermissions(value="AOUBOUNDPLANUPDATE")
	public OaMsg addorupdatearrivalinfo(@ModelAttribute ArrivalInfo arrivalInfo,Integer isTransport) throws OAException{
		return outBoundService.addOrUpdateArrivalInfo(arrivalInfo,isTransport) ;
	}
	/**
	 * 通过到港id获取靠泊方案信息
	 * @param arrivalId
	 * @return
	 */
	@RequestMapping(value = "getBerthProgramByArrivalId")
	@ResponseBody
	public OaMsg getBerthProgramByArrivalId(String arrivalId) throws OAException{
		return outBoundService.getBerthProgramByArrivalId(arrivalId) ;
	}
	
	/**
	 * 保存或修改靠泊方案信息
	 * @param berthProgram
	 * @param arrivalId
	 * @return
	 * @throws OAException 
	 */
	@RequestMapping(value = "addOrUpdateBerthProgram")
	@ResponseBody
	public OaMsg addOrUpdateBerthProgram(@ModelAttribute BerthProgram berthProgram,@ModelAttribute SecurityCodeDto scDto) throws OAException{
		return outBoundService.addOrUpdateBerthProgram(berthProgram,scDto) ;
	}
	/**
	 * 获取靠泊评估信息
	 * @param arrivalId
	 * @return
	 */
	@RequestMapping(value = "getBerthAssess")
	@ResponseBody
	public OaMsg getBerthAssess(String arrivalId)throws OAException{
		return outBoundService.getBerthAssess(arrivalId) ;
	}
	/**
	 * 保存更新靠泊评估信息
	 * @param berthAssess
	 * @param arrivalId
	 * @param berthId
	 * @return
	 */
	@RequestMapping(value = "addOrUpdateBerthAssess")
	@ResponseBody
	public OaMsg addOrUpdateBerthAssess(@ModelAttribute BerthAssess berthAssess,@ModelAttribute SecurityCodeDto scDto) throws OAException{
		return outBoundService.addOrUpdateBerthAssess(berthAssess,scDto) ;
	}
	
	/**
	 * 获取发货准备信息
	 * @param berthAssess
	 * @param arrivalId
	 * @param berthId
	 * @return
	 */
	@RequestMapping(value = "getDeliverReadyMsg")
	@ResponseBody
	public OaMsg getDeliverReadyMsg(String arrivalId) throws OAException{
		return outBoundService.getDeliverReadyMsg(arrivalId) ;
	}
	/**
	 * 保存更新工艺流程
	 * @param transportProgram
	 * @param tankIds
	 * @param tubeIds
	 * @return
	 */
	@RequestMapping(value = "addOrUpdateTransportProgram")
	@ResponseBody
	public OaMsg addOrUpdateTransportProgram(@ModelAttribute TransportProgram transportProgram)throws OAException{
		return outBoundService.addOrUpdateTransportProgram(transportProgram) ;
	}
	/**
	 * 获取船舶出库发货信息
	 * @param arrivalId
	 * @return
	 */
	@RequestMapping(value = "getAmountAffirmMsg")
	@ResponseBody
	public OaMsg getAmountAffirmMsg(String arrivalId) throws OAException{
		return outBoundService.getAmountAffirmMsg(arrivalId) ;
	}
	
	/**
	 * @Title confirmData
	 * @Descrption:TODO
	 * @param:@param data
	 * @param:@return
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年3月24日下午4:14:56
	 * @throws
	 */
	@RequestMapping(value = "confirmData")
	@ResponseBody
	public OaMsg confirmData(String ouboundData,@ModelAttribute SecurityCodeDto scDto) throws OAException{
		ShipDeliverWorkQueryDto data = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().fromJson(ouboundData, ShipDeliverWorkQueryDto.class) ;
		return outBoundService.confirmData(data,scDto) ;
	}
	
	
	/**
	 * 分流台账列表
	 * @Title getOperationLogList
	 * @Descrption:TODO
	 * @param:@param startTime
	 * @param:@param endTime
	 * @param:@return
	 * @return:Object
	 * @auhor jiahy
	 * @date 2016年6月1日下午3:48:20
	 * @throws
	 */
	@RequestMapping(value = "getshipflowlist")
	@ResponseBody
	public OaMsg getShipFlowList(Long startTime,Long endTime) throws OAException{
		return outBoundService.getShipFlowList(startTime,endTime) ;
	}
	
	/**
	 * 分流台账详情
	 * @Title getDeliveryWorkInfo
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @return:Object
	 * @auhor jiahy
	 * @date 2016年6月1日下午3:48:32
	 * @throws
	 */
	@RequestMapping(value = "getshipflowinfo")
	@ResponseBody
	public OaMsg getShipflowInfo(Integer arrivalId) throws OAException{
		return outBoundService.getShipFlowInfo(arrivalId) ;
	}
	/**
	 * 保存发货作业信息
	 * @param deliverWorkInfo
	 * @return
	 */
	@RequestMapping(value = "saveshipflow")
	@ResponseBody
	public Object saveShipFlow(String shipFlow){
		ShipDeliverWorkQueryDto shipFlowDto = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().fromJson(shipFlow, ShipDeliverWorkQueryDto.class) ;
		return outBoundService.saveShipFlow(shipFlowDto) ;
	}

	/**
	 * 查询码头作业通知单信息
	 * @param arrivalId
	 * @return
	 * @throws OAException 
	 */
	@RequestMapping(value = "queryDockNotifyInfo")
	@ResponseBody
	public Object queryDockNotifyInfo(String arrivalId) throws OAException{
		return outBoundService.queryDockNotifyInfo(arrivalId) ;
	}
	/**
	 * 检查日志
	 * @param request
	 * @param response
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value="loghave")
	@ResponseBody
	public Object loghave(HttpServletRequest request, HttpServletResponse response,String startTime,String endTime){
		long mStartTime=Long.parseLong(startTime);
		long mEndTime=Long.parseLong(endTime);
		return outBoundService.getLogIsHave(mStartTime, mEndTime);
	}
	
	
	/**
	 * 删除附件
	 */
	@RequestMapping(value="deleteUploadFile")
	@ResponseBody
	public Object deleteUploadFile(String id){
		return outBoundService.deleteUploadFile(id) ;
	}
	/**
	 * 获取附件
	 * @Title getFileList
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @return:Object
	 * @auhor jiahy
	 * @date 2016年6月28日上午8:46:53
	 * @throws
	 */
	@RequestMapping(value = "getFileList")
	@ResponseBody
	public Object getFileList(String arrivalId,Integer type){
		return outBoundService.getFileList(arrivalId,type) ;
	}
	
	/**
	 * 上传船发作业附件
	 */
	@RequestMapping(value="uploadWorkFile")
	@ResponseBody
	public Object uploadWorkFile(HttpServletRequest request, HttpServletResponse response,int id,String description,int type,@RequestParam MultipartFile file)
	{
		OaMsg oaMsg=new OaMsg();
		if (!file.isEmpty()) 
		{  
        	FileOutputStream fos = null; 
            try 
            {  
            	
				byte[] bytes = file.getBytes();
				String path=request.getSession().getServletContext().getRealPath(File.separator)+"resource"+ File.separator +"upload"+ File.separator +"file"+File.separator+file.getOriginalFilename();  
				// store the bytes somewhere  
				//在这里就可以对file进行处理了，可以根据自己的需求把它存到数据库或者服务器的某个文件夹  
				File f = new File(path) ;
				f.getParentFile().mkdirs();
				f.createNewFile();
				fos = new FileOutputStream(f);
				fos.write(bytes); 
				User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
				UploadFileInfo uploadFileInfo = new UploadFileInfo() ;
				uploadFileInfo.setFileName(file.getOriginalFilename());
				uploadFileInfo.setDescription(description) ;
				uploadFileInfo.setType(type);
				uploadFileInfo.setRefId(id) ;
				uploadFileInfo.setCreateUserId(user.getId()) ;
				uploadFileInfo.setUrl(File.separator +"resource"+ File.separator +"upload"+File.separator+"file"+File.separator+file.getOriginalFilename()) ;
	            outBoundService.saveUploadInfo(uploadFileInfo) ;
	            oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
            } 
            catch (IOException e) 
            {
            	e.printStackTrace();
            	 oaMsg.setCode(Constant.SYS_CODE_NULL_ERR);
            }  
       } 
	   else 
	   {  
    	   oaMsg.setCode(Constant.SYS_CODE_NULL_ERR);
       }  
		return oaMsg;
	}
	/**导出出库信息表*/
	@RequestMapping(value = "exportOutbound")
	public void exportInbound(HttpServletRequest request,HttpServletResponse response,String startTime,String endTime) throws OAException {
		response.setContentType("application/vnd.ms-excel");
		OutputStream fOut = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//进行转码
			response.setHeader("content-disposition", "attachment;filename=" + sdf.format(new Date()) + ".xls");
			
			HSSFWorkbook workbook = outBoundService.exportOutbound(request,startTime,endTime);
			fOut = response.getOutputStream();
			
			workbook.write(fOut);
		} catch (Exception e) {

		} finally {
			try {
				fOut.flush();
				fOut.close();
			} catch (IOException e) {
			}
		}
	}
	
	
	
	
}
