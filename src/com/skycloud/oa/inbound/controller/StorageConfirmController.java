package com.skycloud.oa.inbound.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.ArrivalDto;
import com.skycloud.oa.inbound.dto.CargoDto;
import com.skycloud.oa.inbound.dto.CargoGoodsDto;
import com.skycloud.oa.inbound.model.Cargo;
import com.skycloud.oa.inbound.model.Goods;
import com.skycloud.oa.inbound.model.Notification;
import com.skycloud.oa.inbound.service.CargoGoodsService;
import com.skycloud.oa.order.model.Contract;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.statistics.service.StatisticsService;
import com.skycloud.oa.system.dto.ClientDto;
import com.skycloud.oa.system.dto.ProductDto;
import com.skycloud.oa.system.service.ClientService;
import com.skycloud.oa.system.service.ProductService;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.HibernateUtils;
import com.skycloud.oa.utils.OaMsg;

/**
 * 入库确认
 * 
 * @ClassName: StorageConfirmController
 * @Description: TODO
 * @author wu
 * @date 2014-12-08
 */
@Controller
@RequestMapping("/storageConfirm")
public class StorageConfirmController {
	
	@Autowired
	private CargoGoodsService cargoService;
	@Autowired
	private StatisticsService statisticsService;
	/**
	 * 查询货批
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param agDto
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public OaMsg list(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute CargoGoodsDto agDto,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException {
		OaMsg storageMsg=new OaMsg();
		agDto.setTankGoods(true);
		agDto.setArrivalStatus(9);
		agDto.setGoodsStatus("0");
		if (Common.empty(agDto.getCargoId()) || agDto.getCargoId() == 0) {// 为传id
			storageMsg = cargoService.getCargoList(agDto, new PageView(pagesize,page),false);
		} else {
			storageMsg = cargoService.getCargoList(agDto, new PageView(0, 0),true);
		}
		return storageMsg;
	}
	
	@RequestMapping(value = "getInspectTotal")
	@ResponseBody
	public Object getInspectTotal(HttpServletRequest request, HttpServletResponse response,@ModelAttribute CargoGoodsDto cargoDto) throws OAException{
		
		return cargoService.getInspectTotal(cargoDto);
	}
	
	@RequestMapping(value = "exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,@ModelAttribute CargoGoodsDto agDto) 
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
			agDto.setTankGoods(true);
			agDto.setArrivalStatus(9);
			agDto.setGoodsStatus("0");
			OaMsg msg=cargoService.getCargoList(agDto, new PageView(0, 0),true);
			
			String[] str={"货批编号","货主","货品名称","进货船","到港日期","贸易类型","计划量","罐检数(吨)","商检数(吨)","内控放行数(吨)","海运提单号","说明"};
			
			
			List<List<Object>> data=new ArrayList<List<Object>>();
			
			for(int i=0;i<msg.getData().size();i++){
				Map<String,Object> map1=(Map<String, Object>) msg.getData().get(i);
				Map<String,Object> map=(Map<String, Object>) map1.get("cargodata");
				
				List<Object> d=new ArrayList<Object>();
				if(!Common.empty(map.get("code"))){
					
					d.add(map.get("code").toString());
				}else{
					d.add("");
				}
				d.add(map.get("clientName").toString());
				d.add(map.get("productName").toString());
//				d.add(map.get("shipName").toString());
				if(!Common.empty(map.get("shipName"))){
					
					d.add(map.get("shipName").toString());
				}else{
					d.add("");
				}
				if(!Common.empty(map.get("arrivalTime"))){
					
					d.add(map.get("arrivalTime").toString().split(" ")[0]);
				}else{
					d.add("");
				}
				
				String tax="";
				if(!Common.empty(map.get("taxType"))){
					if(Integer.parseInt(map.get("taxType").toString())==1){
						tax="内贸";
					}else if(Integer.parseInt(map.get("taxType").toString())==2){
						tax="外贸";
					}else if(Integer.parseInt(map.get("taxType").toString())==3){
						tax="保税";
					}
				}
				
				d.add(tax);
				
				d.add(map.get("goodsPlan").toString());
//				d.add(map.get("goodsTank").toString());
				if(!Common.empty(map.get("goodsTank"))){
					
					d.add(map.get("goodsTank").toString());
				}else{
					d.add("0");
				}
				d.add(map.get("goodsInspect").toString());
				d.add(map.get("goodsOutPass").toString());
				if(!Common.empty(map.get("customLading"))){
					
					d.add(map.get("customLading").toString());
				}else{
					d.add("");
				}
				if(!Common.empty(map.get("description"))){
					
					d.add(map.get("description").toString());
				}else{
					d.add("");
				}
				
				
				data.add(d);
			}
			
			HSSFWorkbook workbook=Common.getExcel("入库确认", str, data,new int[]{0,0,0,0,2,0,1,1,1,1,0,0});
			
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
	 * 查询货批下所有货体
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param agDto
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "goods")
	@ResponseBody
	public OaMsg goods(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute CargoGoodsDto agDto,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException {
		OaMsg storageMsg=new OaMsg();
		agDto.setTankGoods(false);
		agDto.setArrivalStatus(9);
		agDto.setGoodsStatus("0");
		agDto.setIsPredict(2);
		if (Common.empty(agDto.getCargoId()) || agDto.getCargoId() == 0) {// 为传id
			storageMsg = cargoService.getCargoList(agDto, new PageView(pagesize,page),false);
		} else {
			storageMsg = cargoService.getCargoList(agDto, new PageView(0, 0),true);
		}
		return storageMsg;
	}
	
	/**
	 * 更新货批
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param cargoDto
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午2:39:02
	 */
	@RequestMapping(value = "update")
	@ResponseBody
	public Object update(HttpServletRequest request, HttpServletResponse response, String cargoDto) throws OAException {
		CargoDto cagDto =  new Gson().fromJson(cargoDto,CargoDto.class);
		return cargoService.updateCargo(cagDto, false);
	}
	/**
	 * 更新货批内货体
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param goods
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午2:39:10
	 */
	@RequestMapping(value = "updategoods")
	@ResponseBody
	public Object updateGoods(HttpServletRequest request, HttpServletResponse response, Goods goods) throws OAException {
			
		
		
		if(!Common.isNull(goods.getIsPredict())&&goods.getIsPredict()==1){
				return cargoService.updateGoods(goods, false,true);
			}
		return cargoService.updateGoods(goods, true,true);
	}
	
	/**
	 * 货体合并
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param ids
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午2:39:33
	 */
	@RequestMapping(value = "mergegoods")
	@ResponseBody
	public Object mergeGoodsForNewGood(HttpServletRequest request, HttpServletResponse response, String ids) throws OAException {
		OaMsg oaMsg= cargoService.mergeGoodsForNewGood(ids);
		Session session = HibernateUtils.getCurrentSession();
		if (session != null) {
			session.getTransaction().commit();
		}
		if(oaMsg.getCode().equals("0000")){
			session.beginTransaction();
			return cargoService.updateGoodsCode(Integer.parseInt(oaMsg.getMap().get("cargoId").toString()), oaMsg.getMap().get("code").toString());
		}
		else{
			oaMsg.setCode(Constant.SYS_CODE_NULL_ERR);
			return oaMsg;
		}
	}
	
	/**
	 * 原始货体拆分，生成两个新的原始货体，原货体删除
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param goodsList
	 * @return
	 * @throws OAException
	 * 2015-3-2 下午3:48:33
	 */
	@RequestMapping(value = "sqlitGoods")
	@ResponseBody
	public Object sqlitGoods(HttpServletRequest request, HttpServletResponse response,String oldGoodsId,@ModelAttribute Goods goods) throws OAException {
		
//		List<Goods> mGoodsList= new Gson().fromJson(goodsList,new TypeToken<List<Goods>>() {}.getType());
		OaMsg oaMsg= cargoService.sqlitOriginalGoods(oldGoodsId, goods);
		return oaMsg;
//		Session session = HibernateUtils.getCurrentSession();
//		if (session != null) {
//			session.getTransaction().commit();
//		}
//		if(oaMsg.getCode().equals("0000")){
//			return cargoService.updateGoodsCode(Integer.parseInt(oaMsg.getMap().get("cargoId").toString()), "");
//		}
//		else{
//			oaMsg.setCode(Constant.SYS_CODE_NULL_ERR);
//			return oaMsg;
//		}
	}
	
	/**
	 * 关联合并预入库
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param cargoId
	 * @param predictGoodsId
	 * @param goodsId
	 * @return
	 * @throws OAException
	 * 2015-3-25 下午2:29:51
	 */
	@RequestMapping(value = "predict")
	@ResponseBody
	public Object predict(HttpServletRequest request, HttpServletResponse response,String cargoId,String predictGoodsId,String goodsId) throws OAException {
		
//		List<Goods> mGoodsList= new Gson().fromJson(goodsList,new TypeToken<List<Goods>>() {}.getType());
		OaMsg oaMsg= cargoService.mergePredict(predictGoodsId, goodsId);
		Session session = HibernateUtils.getCurrentSession();
		if (session != null) {
			session.getTransaction().commit();
		}
		if(oaMsg.getCode().equals("0000")){
			session.beginTransaction();
			return cargoService.updateGoodsCode(Integer.parseInt(cargoId), "");
		}
		else{
			oaMsg.setCode(Constant.SYS_CODE_NULL_ERR);
			return oaMsg;
		}
	}
	
	
	/**
	 * 货体管理---货体拆分
	 * @author yanyufeng
	 * @param oldGoodsId  原货体id
	 * @param goods  新货体数据（海关放行编号，货体量）
	 * @param type  1 提单里的货体  0 原始货体
	 * @return
	 * 2015-4-10 下午1:28:58
	 */
	@ResponseBody
	@RequestMapping(value = "/goodsSqlit")
	public Object goodsSqlit(int oldGoodsId,@ModelAttribute Goods goods,int type){
		OaMsg oaMsg = new OaMsg();
		try {
			if(type==1){
				oaMsg = cargoService.sqlitPassGoods(oldGoodsId, goods);
			}else{
				oaMsg = cargoService.sqlitOriginalGoods2(oldGoodsId+"", goods);
			}
//			Session session = HibernateUtils.getCurrentSession();
//			if (session != null) {
//				session.getTransaction().commit();
//			}
//			if(oaMsg.getCode().equals("0000")){
//				if(!Common.empty(oaMsg.getMap().get("ladingId"))&&!"0".equals(oaMsg.getMap().get("ladingId"))){
//					return cargoService.updateGoodsCode(Integer.parseInt(oaMsg.getMap().get("ladingId").toString()));
//				}
//				else{
//					return cargoService.updateGoodsCode(Integer.parseInt(oaMsg.getMap().get("cargoId").toString()), "");
//				}
//			}
//			else{
//				oaMsg.setCode(Constant.SYS_CODE_NULL_ERR);
//				return oaMsg;
//			}
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return oaMsg;
		
	}
	
	
	/**
	 * 货体管理---货体换罐
	 */
	@ResponseBody
	@RequestMapping(value = "/changeTank")
	public Object changeTank(@ModelAttribute Goods goods){
		OaMsg oaMsg = new OaMsg();
		try {
			
			cargoService.updateGoods(goods);
//			cargoService.updateGoods(goods, false, false);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return oaMsg;
		
	}
	
	
	/**
	 * 货体修改入库量
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/editGoodsTotal")
	public Object editGoodsTotal(Integer goodsId,String goodsTotal,Integer isPredict){
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg=cargoService.editGoodsTotal(goodsId,goodsTotal,isPredict);
//			cargoService.updateGoods(goods, false, false);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return oaMsg;
		
	}
	
	
	/**
	 *货体扣损
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/goodsLoss")
	public Object goodsLoss(Integer goodsId,String lossCount){
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg=cargoService.goodsLoss(goodsId,lossCount);
//			cargoService.updateGoods(goods, false, false);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return oaMsg;
		
	}
	
	
	@RequestMapping(value="/updatefile")
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
				String url=request.getSession().getServletContext().getRealPath("/")+"resource/upload\\"+file.getOriginalFilename();  
				
				fos = new FileOutputStream(url);                 
				fos.write(bytes); 
				
            
            Cargo cargo = new Cargo();
            cargo.setId(id);
            cargo.setFileUrl("/resource/upload/"+file.getOriginalFilename());
            cargoService.updateCargo(cargo);
            
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
	 * 查询货批
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param goodsId
	 * @return
	 * @throws OAException
	 * 2015-2-27 下午7:56:28
	 */
	@RequestMapping(value = "getCargo")
	@ResponseBody
	public Object getGoods(HttpServletRequest request, HttpServletResponse response,CargoGoodsDto cargoGoodsDto) throws OAException{
		
		return cargoService.getCargoList(cargoGoodsDto, new PageView(0, 0), false);
		
	}
	
	@RequestMapping(value = "getCargoByClientId")
	@ResponseBody
	public Object getCargoByClientId(HttpServletRequest request, HttpServletResponse response,int clientId) throws OAException{
		
		return cargoService.getCargoByClientId(clientId);
		
	}
	
	@RequestMapping(value = "getClientByCargoId")
	@ResponseBody
	public Object getClientByCargoId(HttpServletRequest request, HttpServletResponse response,int cargoId) throws OAException{
		
		return cargoService.getClientByCargoId(cargoId);
		
	}
	
	@RequestMapping(value = "deleteCargo")
	@ResponseBody
	public Object deleteCargo(HttpServletRequest request, HttpServletResponse response,int cargoId) throws OAException{
		
		return cargoService.deleteCargo(cargoId);
		
	}
	
	
}
