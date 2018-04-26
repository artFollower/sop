/**
 * 
 */
package com.skycloud.oa.outbound.controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.CargoGoodsDto;
import com.skycloud.oa.inbound.model.Goods;
import com.skycloud.oa.inbound.service.CargoGoodsService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dto.GoodsDto;
import com.skycloud.oa.outbound.dto.LadingDto;
import com.skycloud.oa.outbound.model.Lading;
import com.skycloud.oa.outbound.service.LadingService;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.DateTimeUtil;
import com.skycloud.oa.utils.OaMsg;

/**
 * 提单控制类
 * @ClassName: LandingController
 * @author wuhaijie
 *
 */
@Controller
@RequestMapping("/lading")
public class LadingController {
	
	@Autowired
	private LadingService ladingService;
	@Autowired
	private CargoGoodsService goodService;
	
	/**
	 * 查询所有提单信息
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
	public OaMsg list(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute LadingDto ladingDto,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException {
		OaMsg msg=new OaMsg();
		if(!Common.isNull(ladingDto.getId())){
			//查询单个
			msg = ladingService.getLadingList(ladingDto, new PageView(0,0));
		}else{
			if(Common.empty(ladingDto.getStatus()) || ladingDto.getStatus() == ""){
				ladingDto.setStatus("0,1,2");
			}
			msg = ladingService.getLadingList(ladingDto, new PageView(pagesize,page));
		}
//		OaMsg userOaMsg = clientService.getClientList(new ClientDto(),new PageView(0,0));
		List<Object> ladingList = msg.getData();
		String time = "";
		for(int i = 0;i < ladingList.size(); i++){
			@SuppressWarnings("unchecked")
			Map<String, Object> lading = (Map<String, Object>)ladingList.get(i);
			time = (String)lading.get("endTime");
			lading.put("endTime", DateTimeUtil.dateTimeToShortDate(time));
			lading.put("mEndTime", time);
			lading.put("startTime",DateTimeUtil.dateTimeToShortDate((String)lading.get("startTime")));
			lading.put("mStartTime", (String)lading.get("startTime"));
		}
		return msg;
	}
	
	/**
	 * 根据提单id查询该提单下的货体
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param ladingId
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午3:24:53
	 */
	@RequestMapping(value = "getgoods")
	@ResponseBody
	public OaMsg getgoods(HttpServletRequest request, HttpServletResponse response, String ladingId)
			throws OAException {
		OaMsg msg=new OaMsg();
		msg = ladingService.getLadingGoods(Integer.parseInt(ladingId), msg);
		return msg;
	}
	
	/**
	 * 添加提单
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param lading
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public Object save(HttpServletRequest request, HttpServletResponse response, @ModelAttribute Lading lading,
			@RequestParam String stTime,@RequestParam String edTime,@RequestParam String goodsIdList,@RequestParam String goodsCount,@RequestParam String buyClientId) throws OAException {
		
		OaMsg check=(OaMsg) ladingService.checkCode(lading.getCode(),lading.getClientId());
		if(!"0000".equals(check.getCode())){
			return check;
		}
		
		User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		lading.setCreateUserId(user.getId());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		OaMsg ladingMsg = new OaMsg();
		OaMsg ladingGoodsGroupMsg =  new OaMsg();
		try {
			if(!Common.empty(stTime)){
				long time = df.parse(stTime).getTime();
				Timestamp startTime = new Timestamp(time);
				lading.setStartTime(startTime);
			}
			if(!Common.empty(edTime)){
				
				long etime = df.parse(edTime).getTime()+86400000-1000;
				Timestamp endTime = new Timestamp(etime);
				lading.setEndTime(endTime);
			}
			String[] ids=goodsIdList.split(",");
			String[] total=goodsCount.split(",");
			String newGoodsIds="";
			List<Goods> goodsNewList=new ArrayList<Goods>();
			List<Goods> goodsOldList=new ArrayList<Goods>();
			for(int i=0; i<ids.length;i++){
				Goods goodsold=new Goods();
				goodsold.setId(Integer.valueOf(ids[i]));
				
				CargoGoodsDto agDto = new CargoGoodsDto();
				agDto.setGoodsId(goodsold.getId());
				OaMsg oldgood = (OaMsg) goodService.getGoodsById(agDto);
				Map<String,Object> oGood=(Map<String, Object>) oldgood.getData().get(0);
				if(Double.parseDouble(total[i])>Common.fixDouble((Double.parseDouble(oGood.get("goodsCurrent").toString())-(Double.parseDouble(oGood.get("goodsTotal").toString())-Math.min(Double.parseDouble(oGood.get("goodsInPass").toString()), Double.parseDouble(oGood.get("goodsOutPass").toString())))),3))
				{
					ladingGoodsGroupMsg.setCode("1005");
					return ladingGoodsGroupMsg;
				}
				if (lading.getType()==2){
					if(!Common.empty(oGood.get("ladingEndTime"))){
						long etimeo = df.parse(oGood.get("ladingEndTime").toString()).getTime();
						if(!Common.empty(edTime)){
							if(etimeo<df.parse(edTime).getTime()){
								ladingGoodsGroupMsg.setCode("1004");
								return ladingGoodsGroupMsg;
							}
								
						}
						
					}
					
					if(Integer.parseInt(oGood.get("ladingType").toString())==2&&Integer.parseInt(oGood.get("isLong").toString())!=1){
						if(lading.getIsLong()==1){
							ladingGoodsGroupMsg.setCode("1004");
							return ladingGoodsGroupMsg;
						}
					}
					
					
				}
				
				Goods goodsnew=new Goods();
				goodsnew.setGoodsTotal(total[i]);
				goodsnew.setGoodsInPass(total[i]);
				goodsnew.setGoodsOutPass(0+"");
				goodsNewList.add(goodsnew);
				goodsOldList.add(goodsold);
			}
			ladingMsg = ladingService.addLading(lading);
			int ladingId=Integer.valueOf(ladingMsg.getMap().get("id"));
			newGoodsIds=goodService.splitGoods(ladingId,lading.getCode(),goodsNewList, goodsOldList,buyClientId,lading.getType()).getMap().get("id");
			LadingDto lgDto=new LadingDto();
			lgDto.setId(ladingId);
			lgDto.setGoodsIds(newGoodsIds);
			lgDto.setBuyClientId(Integer.valueOf(buyClientId));
			lgDto.setLadingStatus(lading.getType());
			
			ladingGoodsGroupMsg=	ladingService.addLadingGoodsGroup(lgDto);
			lgDto.setGoodsGroupId(Integer.parseInt(ladingGoodsGroupMsg.getMap().get("goodsGroupId").toString()));
			List<LadingDto> ladList=new ArrayList<LadingDto>();
			ladList.add(lgDto);
			ladingGoodsGroupMsg.getData().addAll(ladList);
			ladingGoodsGroupMsg.setMap(ladingMsg.getMap());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ladingGoodsGroupMsg;
	}
	
	@RequestMapping(value = "upgoods")
	@ResponseBody
	public OaMsg addgoods(HttpServletRequest request, HttpServletResponse response,@ModelAttribute LadingDto ladingDto) throws OAException {
		return ladingService.updateGoods(ladingDto);
		
	}
	/**
	 * 提单内添加货体
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param lading
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "addgoods")
	@ResponseBody
	public OaMsg addgoods(HttpServletRequest request, HttpServletResponse response,@RequestParam String code, @RequestParam String id,
			@RequestParam String goodsIdList,@RequestParam String goodsCount,@RequestParam String goodGroupId) throws OAException {
		OaMsg ladingGoodsGroupMsg = null;
			String[] ids=goodsIdList.split(",");
			String[] total=goodsCount.split(",");
			List<Goods> goodsNewList=new ArrayList<Goods>();
			List<Goods> goodsOldList=new ArrayList<Goods>();
			for(int i=0; i<ids.length;i++){
				Goods goodsold=new Goods();
				goodsold.setId(Integer.valueOf(ids[i]));
				Goods goodsnew=new Goods();
				goodsnew.setGoodsTotal(total[i]);
//				goodsnew.setGoodsInPass(total[i]);
//				goodsnew.setGoodsOutPass(total[i]);
				goodsNewList.add(goodsnew);
				goodsOldList.add(goodsold);
			}
			ladingGoodsGroupMsg=goodService.splitupdateGoods(id,code,goodsNewList, goodsOldList,goodGroupId);
		return ladingGoodsGroupMsg;
	}
	
	
	/**
	 * 编辑提单
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param lading
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "update")
	@ResponseBody
	public Object update(HttpServletRequest request, HttpServletResponse response, @ModelAttribute Lading lading,
			@RequestParam String stTime,@RequestParam String edTime,@RequestParam String goodsIdList,
			@RequestParam int goodGroupId) throws OAException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		OaMsg oaMsg = null; 
		try {
			if(!Common.empty(stTime)){
				long time = df.parse(stTime).getTime();
				Timestamp startTime = new Timestamp(time);
				lading.setStartTime(startTime);
			}
			if(!Common.empty(edTime)){
				
				long etime = df.parse(edTime).getTime()+86400000-1000;
				Timestamp endTime = new Timestamp(etime);
				lading.setEndTime(endTime);
				System.out.println(endTime);
			}
			oaMsg = ladingService.updateLading(lading);
//			LadingDto lgDto=new LadingDto();
//			lgDto.setGoodsIds(goodsIdList);
//			lgDto.setGoodsGroupId(goodGroupId);
//			lgDto.setBuyClientId(lading.getReceiveClientId());
//			lgDto.setLadingStatus(lading.getStatus());
//			ladingService.updateLadingGoodsGroup(lgDto);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return oaMsg;
	}
	
	/**
	 * 撤销提单
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午3:58:56
	 */
	@RequestMapping(value = "cancel")
	@ResponseBody
	public Object cancel(HttpServletRequest request, HttpServletResponse response,@RequestParam String id) throws OAException{
		return ladingService.deleteLading(id);
	}
	
	/**
	 * 编辑提单
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "goodsEdit")
	@ResponseBody
	public ModelAndView goodsEdit(HttpServletRequest request, HttpServletResponse response,@RequestParam int id){
		return new ModelAndView("outbound/ladingGoods");
	}
	
	
	/**
	 * 修改货品
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param ladingDto
	 * @param method
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "initEdit")
	public ModelAndView initEdit(HttpServletRequest request, HttpServletResponse response, @ModelAttribute LadingDto ladingDto, String method) throws OAException {
		return new ModelAndView("outbound/editGoods")
					.addObject("productList",goodService.getGoodsList(new CargoGoodsDto(), new PageView(0,0)));
	}
	/**
	 * 查看所有父货体
	 * @param request
	 * @param response
	 * @param arrivalDto
	 * @param method
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "show")
	@ResponseBody
	public OaMsg show(HttpServletRequest request, HttpServletResponse response, @ModelAttribute GoodsDto goodsDto) throws OAException {
		return ladingService.getGoodsTree(goodsDto);
	}
	
	@RequestMapping(value = "showCaroLZ")
	@ResponseBody
	public OaMsg showCaroLZ(HttpServletRequest request, HttpServletResponse response, String cargoId) throws OAException {
		return ladingService.getCargoTree(cargoId);
	}
	
	/**
	 * 检索货品
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param ladingDto
	 * @param method
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "serchGoods")
	@ResponseBody
	public Object serchGoods(HttpServletRequest request, HttpServletResponse response,@ModelAttribute CargoGoodsDto cargoDto) throws OAException{
		
		return goodService.getGoodsList(cargoDto, new PageView(0,0));
	}
	
	@RequestMapping(value = "clientGoods")
	@ResponseBody
	public Object clientGoods(HttpServletRequest request, HttpServletResponse response,@ModelAttribute CargoGoodsDto cargoDto) throws OAException{
		
		return goodService.clientGoods(cargoDto, new PageView(0,0));
	}
	@RequestMapping(value = "clientGoodstotal")
	@ResponseBody
	public Object clientGoodstotal(HttpServletRequest request, HttpServletResponse response,@ModelAttribute CargoGoodsDto cargoDto) throws OAException{
		
		return goodService.clientGoodstotal(cargoDto);
	}
	
	/**
	 * 查询所有原始货体
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param ladingDto
	 * @param method
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "getOriginalGoods")
	@ResponseBody
	public Object getOriginalGoods(HttpServletRequest request, HttpServletResponse response,@ModelAttribute CargoGoodsDto cargoDto
			,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
		
		return goodService.getOriginalGoodsList(cargoDto, new PageView(pagesize,page));
	}

	/**
	 * 余量退回
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param ladingId
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "backGoods")
	@ResponseBody
	public Object backGoods(HttpServletRequest request, HttpServletResponse response,String ladingId) throws OAException{
		
		return ladingService.backLading(ladingId);
	}
	
	
	/**
	 * 删除单个货体
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param ladingId
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "removeGood")
	@ResponseBody
	public Object removeGood(HttpServletRequest request, HttpServletResponse response,String id,String ladingId) throws OAException{
		
		return ladingService.removeGood(id,ladingId);
	}
	
	
	/**
	 * 提单扣损
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param ladingId
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "lossGoods")
	@ResponseBody
	public Object lossGoods(HttpServletRequest request, HttpServletResponse response,String ladingId) throws OAException{
		
		return ladingService.lossLading(ladingId);
	}
	
	
	/**
	 * 提单冲销
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param ladingId
	 * @return
	 * @throws OAException
	 * 2016-2-18 下午3:10:51
	 */
	@RequestMapping(value = "againstLading")
	@ResponseBody
	public Object againstGoods(HttpServletRequest request, HttpServletResponse response,String ladingId) throws OAException{
		
		return ladingService.againstLading(ladingId);
	}
	
	
	/**
	 * 提单内货体放行
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param ladingId
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "goodsPass")
	@ResponseBody
	public Object goodsPass(HttpServletRequest request, HttpServletResponse response,String contractId ,String ladingId,String goodsId,String count,String goodsTotal) throws OAException{
		
		return ladingService.goodsPass(ladingId, goodsId, count,contractId,goodsTotal);
		
	}
	
	
	/**
	 * 查询货体流水表
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param goodsId
	 * @return
	 * @throws OAException
	 * 2015-2-27 下午7:56:28
	 */
	@RequestMapping(value = "getGoodsLS")
	@ResponseBody
	public Object getGoodsLS(HttpServletRequest request, HttpServletResponse response,String goodsId) throws OAException{
		
		return ladingService.getGoodsLS(goodsId);
		
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
				
            
           
            ladingService.updateLading(id,"/resource/upload/"+file.getOriginalFilename());
            
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
	 * 查询货体
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param goodsId
	 * @return
	 * @throws OAException
	 * 2015-2-27 下午7:56:28
	 */
	@RequestMapping(value = "getGoods")
	@ResponseBody
	public Object getGoods(HttpServletRequest request, HttpServletResponse response,CargoGoodsDto cargoGoodsDto) throws OAException{
		
		return ladingService.getGoods(cargoGoodsDto);
		
	}
	
	@RequestMapping(value = "updateLadingNo")
	@ResponseBody
	public Object updateLadingNo(HttpServletRequest request, HttpServletResponse response,String id,String No) throws OAException{
		
		return ladingService.updateLadingNo(id,No);
		
	}
	
	
	@RequestMapping(value = "exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,@ModelAttribute LadingDto agDto) 
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
			
			OaMsg msg=ladingService.getLadingList(agDto, new PageView(0, 0));
			
			String[] str={"提单号","发货单位","接收单位","类型","货品名称","提单数量(吨)","可提数量(吨)","仓储费起计日","提单有效期","状态","创建人","创建时间"};
			
			
			List<List<Object>> data=new ArrayList<List<Object>>();
			
			for(int i=0;i<msg.getData().size();i++){
				Map<String,Object> map=(Map<String, Object>) msg.getData().get(i);
				
				List<Object> d=new ArrayList<Object>();
				if(!Common.empty(map.get("code"))){
					
					d.add(map.get("code").toString());
				}else{
					d.add("");
				}
				d.add(map.get("clientName").toString());
				d.add(map.get("receiveClientName").toString());
//				d.add(map.get("shipName").toString());
				if(!Common.empty(map.get("type"))){
					if(Integer.parseInt(map.get("type").toString())==1){
						d.add("转卖提单");
					}else{
						d.add("发货提单");
					}
					}
				
				d.add(map.get("productName").toString());
				d.add(map.get("goodsTotal").toString());

				double goodsPass=Common.empty(map.get("goodsPass"))?0:Double.parseDouble(map.get("goodsPass").toString());
				double goodsOut=Common.empty(map.get("goodsOut"))?0:Double.parseDouble(map.get("goodsOut").toString());
				double goodsDelivery=Common.empty(map.get("goodsDelivery"))?0:Double.parseDouble(map.get("goodsDelivery").toString());
				double goodsWait=Common.empty(map.get("goodsWait"))?0:Double.parseDouble(map.get("goodsWait").toString());
				double goodsLoss=Common.empty(map.get("goodsLoss"))?0:Double.parseDouble(map.get("goodsLoss").toString());
				double goodsTotal=Common.empty(map.get("goodsTotal"))?0:Double.parseDouble(map.get("goodsTotal").toString());
				
				d.add(((goodsPass-goodsOut)-(goodsDelivery+goodsWait+goodsLoss))+"");
				
				if(!Common.empty(map.get("type"))){
					if(Integer.parseInt(map.get("type").toString())==1){
						if(!Common.empty(map.get("startTime"))){
							
							d.add(map.get("startTime").toString());
						}else{
							d.add("");
						}
					}else{
						d.add("");	
					}
				}else{
					d.add("");
				}
				if(!Common.empty(map.get("type"))){
					if(Integer.parseInt(map.get("type").toString())==2){
						if(!Common.empty(map.get("isLong"))&&Integer.parseInt(map.get("isLong").toString())==1){
							d.add("长期");
						}else{
							if(!Common.empty(map.get("endTime"))){
								
								d.add(map.get("endTime").toString());
							}else{
								d.add("");
							}
						}
					}else{
						d.add("");	
					}
				}else{
					d.add("");
				}
				
				
				double goodsCurrent=goodsTotal-goodsOut-goodsDelivery-goodsLoss;
				if(!Common.empty(map.get("status"))){
					
					if(Integer.parseInt(map.get("status").toString())==0){
						d.add("锁定");
					}else if (Integer.parseInt(map.get("status").toString())==1){
				if(Common.fixDouble(goodsTotal-goodsPass,3)==0){
					d.add("激活");
				}else if(Common.fixDouble(goodsPass, 3)==0){
					d.add("锁定");
				}else{
					d.add("激活(部分)");
				}
				}else if(Integer.parseInt(map.get("status").toString())==2){
					d.add("激活");
					
				}
			}
				
				
				d.add(map.get("createUserName").toString());
				d.add(map.get("createTime").toString().substring(0, map.get("createTime").toString().length()-2));
				
				
				data.add(d);
			}
			
			HSSFWorkbook workbook=Common.getExcel("提单管理", str, data,new int[]{0,0,0,0,0,1,1,0,0,0,0,0});
			
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
	
	
	
}
