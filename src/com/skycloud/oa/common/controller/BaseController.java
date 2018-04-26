package com.skycloud.oa.common.controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.common.service.BaseControllerService;
import com.skycloud.oa.config.Global;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.model.BerthProgram;
import com.skycloud.oa.order.dto.ContractDto;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.BaseDto;
import com.skycloud.oa.system.dto.BerthDto;
import com.skycloud.oa.system.dto.CargoAgentDto;
import com.skycloud.oa.system.dto.CertifyAgentDto;
import com.skycloud.oa.system.dto.ClientDto;
import com.skycloud.oa.system.dto.InspectAgentDto;
import com.skycloud.oa.system.dto.ParkDto;
import com.skycloud.oa.system.dto.PortDto;
import com.skycloud.oa.system.dto.ProductDto;
import com.skycloud.oa.system.dto.PumpDto;
import com.skycloud.oa.system.dto.QualificationDto;
import com.skycloud.oa.system.dto.ShipAgentDto;
import com.skycloud.oa.system.dto.ShipDto;
import com.skycloud.oa.system.dto.TankDto;
import com.skycloud.oa.system.dto.TruckDto;
import com.skycloud.oa.system.dto.TubeDto;
import com.skycloud.oa.system.model.Truck;
import com.skycloud.oa.system.service.BerthService;
import com.skycloud.oa.system.service.CargoAgentService;
import com.skycloud.oa.system.service.CertifyAgentService;
import com.skycloud.oa.system.service.ClientGroupService;
import com.skycloud.oa.system.service.ClientService;
import com.skycloud.oa.system.service.InspectAgentService;
import com.skycloud.oa.system.service.ParkService;
import com.skycloud.oa.system.service.PortService;
import com.skycloud.oa.system.service.ProductService;
import com.skycloud.oa.system.service.PumpService;
import com.skycloud.oa.system.service.QualificationService;
import com.skycloud.oa.system.service.ShipAgentService;
import com.skycloud.oa.system.service.ShipService;
import com.skycloud.oa.system.service.TankService;
import com.skycloud.oa.system.service.TruckService;
import com.skycloud.oa.system.service.TubeService;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;

@Controller
@RequestMapping("/baseController")
public class BaseController {
	
	@Autowired
	private BaseControllerService baseControllerService ;
	
	@Autowired
	private ClientGroupService clientGroupService ;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CargoAgentService cargoAgentService;
	

	@Autowired
	private ShipService shipService;
	
	@Autowired
	private ShipAgentService shipAgentService;

	@Autowired
	private TruckService truckService;
	
	@Autowired
	private InspectAgentService inspectAgentService;
	
	@Autowired
	private CertifyAgentService certifyAgentService;
	@Autowired
	private QualificationService qualificationService;
	@Autowired
	private TubeService tubeService;
	
	@Autowired
	private BerthService berthService;
	
	@Autowired
	private ParkService parkService;
	@Autowired
	private PortService portService;
	
	@Autowired
	private TankService tankService;
	@Autowired
	private PumpService pumpService;
	
	/**
	 * 查询车牌下拉菜单方法
	 * @param vehiclePlate
	 * @return
	 * @
	 */
	@RequestMapping(value = "getVehiclePlate")
	@ResponseBody
	public Object getVehiclePlate(String vehiclePlate){
		return baseControllerService.getVehiclePlateList(vehiclePlate) ;
	}
	
	
	/**
	 * 获取船信
	 * @param vehiclePlate
	 * @return
	 * @
	 */
	@RequestMapping(value = "getArrivalShipInfo")
	@ResponseBody
	public Object getArrivalShipInfo(){
		return baseControllerService.getArrivalShipInfo() ;
	}
	/**
	 * 通过车发id查询车牌下拉菜单方法
	 * @param vehiclePlate
	 * @return
	 * @
	 */
	@RequestMapping(value = "getVehiclePlateByTrainId")
	@ResponseBody
	public Object getVehiclePlateByTrainId(String trainId){
		return baseControllerService.getVehiclePlateByTrainId(trainId) ;
	}
	
	
	/**
	 * 获取地磅的url地址
	 * @param vehiclePlate
	 * @return
	 * @
	 */
	@RequestMapping(value = "getUrl")
	@ResponseBody
	public Object getUrl(){
		return Global.cloudConfig.get("weight.url").toString() ;
	}
	
	
	
	/**
	 * 查询提单号下拉菜单方法
	 * @param id
	 * @param code
	 * @return
	 * @
	 */
	@RequestMapping(value = "getLadingCode")
	@ResponseBody
	public Object getLadingCode(String id,String code,String productId){
		return baseControllerService.getLadingCodeList(id, code,productId) ;
	}
	
	/**
	 * 查询客户单位名称下拉菜单方法
	 * @param clientName
	 * @return
	 * @
	 */
	@RequestMapping(value = "getClientName")
	@ResponseBody
	public Object getClientName(String clientName){
		return baseControllerService.getClientNameList(clientName) ;
	}
	
	/**
	 * 查询客户单位名称下拉菜单方法通过货品id
	 * @param clientName
	 * @return
	 * @
	 */
	@RequestMapping(value = "getClientNameByProductId")
	@ResponseBody
	public Object getClientNameByProductId(String clientName,String productId){
		return baseControllerService.getClientNameByProductId(clientName,productId) ;
	}
	
	
	/**
	 * 发货开票界面查询所有历史提货客户
	 * @param clientName
	 * @return
	 * @
	 */
	@RequestMapping(value = "getHistoryClientName")
	@ResponseBody
	public Object getHistoryClientName(){
		return baseControllerService.getHistoryClientName() ;
	}
	
	/**
	 * 查询产品名称下拉菜单方法
	 * @param productName
	 * @return
	 * @
	 */
	@RequestMapping(value = "productName")
	@ResponseBody
	public Object getProductName(String productName){
		return baseControllerService.getProductNameList(productName) ;
	}
	
	/**
	 * 通过英文id查询中文名称
	 * @param shipId
	 * @return
	 * @
	 */
	@RequestMapping(value = "getShipChineseName")
	@ResponseBody
	public Object getShipChineseName(String shipId){
		return baseControllerService.getShipChineseName(shipId) ;
	}
	
	/**
	 * 通过名称查询船名
	 * @param shipId
	 * @return
	 * @
	 */
	@RequestMapping(value = "getShipName")
	@ResponseBody
	public Object getShipName(String shipName){
		return baseControllerService.getShipName(shipName) ;
	}
	
	/**
	 * 查询可以用来开票的船名
	 * @param shipId
	 * @return
	 * @
	 */
	@RequestMapping(value = "getCanMakeInvoiceShipName")
	@ResponseBody
	public Object getCanMakeInvoiceShipName(String productId,String ladingCode,Integer isNoTransport){
		return baseControllerService.getCanMakeInvoiceShipName(productId,ladingCode,isNoTransport) ;
	}
	
	
	/**
	 * 查询可以用来开票的船名
	 * @param shipId
	 * @return
	 * @
	 */
	@RequestMapping(value = "getCanMakeInvoiceTruck")
	@ResponseBody
	public Object getCanMakeInvoiceTruck(){
		return baseControllerService.getCanMakeInvoiceTruck() ;
	}
	
	/**
	 * 通过名称查询罐号
	 * @param shipId
	 * @return
	 * @
	 */
	@RequestMapping(value = "getTankName")
	@ResponseBody
	public Object getTankName(String tankName){
		return baseControllerService.getTankName(tankName) ;
	}
	
	/**
	 * 通过名称查询罐号
	 * @param shipId
	 * @return
	 * @
	 */
	@RequestMapping(value = "getTankCode")
	@ResponseBody
	public Object getTankCode(Integer productId){
		return baseControllerService.getTankCode(productId) ;
	}
	
	
	/**
	 * 通过名称查询车位
	 * @param shipId
	 * @return
	 * @
	 */
	@RequestMapping(value = "getParkName")
	@ResponseBody
	public Object getParkName(String parkName){
		return baseControllerService.getParkName(parkName) ;
	}
	/**
	 * 通过名称查询泊位
	 * @param berthName
	 * @return
	 * @
	 */
	@RequestMapping(value = "getBerthByName")
	@ResponseBody
	public Object getBerthByName(String berthName){
		return baseControllerService.getBerthByName(berthName) ;
	}
	
	/**
	 * 通过名称查询泊位
	 * @param getParkList
	 * @return
	 * @
	 */
	@RequestMapping(value = "getParkList")
	@ResponseBody
	public Object getWeather(String parkName,String ids){
		return baseControllerService.getParkList(parkName,ids) ;
	}
	
	/**
	 * 查询天气
	 * @param getParkList
	 * @return
	 * @
	 */
	@RequestMapping(value = "getWeather")
	@ResponseBody
	public Object getWeather(){
		return baseControllerService.getWeather() ;
	}
	
	/**
	 * 查询风向
	 * @param getParkList
	 * @return
	 * @
	 */
	@RequestMapping(value = "getWindDirection")
	@ResponseBody
	public Object getWindDirection(){
		return baseControllerService.getWindDirection() ;
	}
	
	/**
	 * 查询风力
	 * @param getParkList
	 * @return
	 * @
	 */
	@RequestMapping(value = "getWindPower")
	@ResponseBody
	public Object getWindPower(){
		return baseControllerService.getWindPower() ;
	}
	
	/**
	 * 查询风力
	 * @param getParkList
	 * @return
	 * @
	 */
	@RequestMapping(value = "getUser")
	@ResponseBody
	public Object getUser(){
		return baseControllerService.getUser() ;
	}
	
	/**
	 * 查询风力
	 * @param getParkList
	 * @return
	 * @
	 */
	@RequestMapping(value = "getTrans")
	@ResponseBody
	public Object getTrans(String arrivalId){
		return baseControllerService.getTrans(arrivalId) ;
	}
	
	@RequestMapping(value = "getCargoAgent")
	@ResponseBody
	public Object getTrans(){
		try {
			return baseControllerService.getCargoAgentList(new CargoAgentDto(), 0, 0);
		} catch (OAException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询流量计台帐中船名
	 * @Title:BaseController
	 * @Description:
	 * @return
	 * @throws OAException
	 * @Date:2015年8月3日 下午5:27:28
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value = "queryShipInfo")
	@ResponseBody
	public Object queryShipInfo() throws OAException
	{
		return baseControllerService.queryShipInfo() ;
	}
	
	/**
	 * 系统管理-基础信息-客户资料-添加客户编号总数
	 * @Title:BaseController
	 * @Description:
	 * @param queryStr
	 * @return
	 * @throws OAException
	 * @Date:2015年8月5日 下午8:27:52
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value = "queryClientCount")
	@ResponseBody
	public Object queryClientCount(String queryStr) throws OAException
	{
		return baseControllerService.queryClientCount(queryStr) ;
	}
	
	/**
	 * 查询某辆车的核载量
	 * @Title:BaseController
	 * @Description:
	 * @param carNo
	 * @return
	 * @throws OAException
	 * @Date:2015年8月28日 下午1:26:29
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value = "queryOneCar")
	@ResponseBody
	public Object queryOneCar(String carNo) throws OAException
	{
		return baseControllerService.queryOneCar(carNo) ;
	}
	
	/**
	 * 更新某辆车的核载量
	 * @Title:BaseController
	 * @Description:
	 * @param carNo
	 * @return
	 * @throws OAException
	 * @Date:2015年8月28日 下午2:12:48
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value = "updateOneCar")
	@ResponseBody
	public Object updateOneCar(HttpServletRequest request, HttpServletResponse response, @ModelAttribute Truck truck) throws OAException
	{
		return baseControllerService.updateOneCar(truck) ;
	}
	
	@RequestMapping(value="getcargolist")
	@ResponseBody
	public Object  getCargoList(HttpServletRequest request,HttpServletResponse response) throws OAException{
		OaMsg oaMsg=null;
		oaMsg=baseControllerService.getCargoList();
		return oaMsg;
	}
	
	
	@RequestMapping(value = "exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,BaseDto baseDto,int type) 
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
			
//			OaMsg msg=contractService.getContractListByCondition(contractDto, new PageView(0,0));
			
			HSSFWorkbook workbook=new HSSFWorkbook();
			OaMsg msg=new OaMsg();
			List<List<Object>> data=new ArrayList<List<Object>>();
			
			switch (type) {
			case 0:
				//客户组
				String[] kehuzu={"客户组名称","描述"};
				
				 msg =clientGroupService.getClientGroup(null,baseDto.getName(),baseDto.getLetter(),new PageView(0, 0));
				
				for(int i=0;i<msg.getData().size();i++){
					Map<String,Object> map=(Map<String, Object>) msg.getData().get(i);
					List<Object> d=new ArrayList<Object>();
					d.add(map.get("name").toString());
					d.add(map.get("description").toString());
					data.add(d);
				}
				
				workbook=Common.getExcel("客户组", kehuzu, data,new int[]{0,0});
				
				
				break;

			case 1:
				
				//客户
				String[] kehu={"客户代码","客户名称","联系人","联系电话","联系地址","传真","邮箱","邮编","银行账户","开户行"};
				
				ClientDto clientDto=new ClientDto();
				
				clientDto.setCode(baseDto.getCode());
				clientDto.setName(baseDto.getName());
				clientDto.setContactName(baseDto.getContactName());
				clientDto.setContactPhone(baseDto.getContactPhone());
				clientDto.setLetter(baseDto.getLetter());
				msg =clientService.getClientList(clientDto,new PageView(0, 0));
				
				for(int i=0;i<msg.getData().size();i++){
					Map<String,Object> map=(Map<String, Object>) msg.getData().get(i);
					List<Object> d=new ArrayList<Object>();
					d.add(map.get("code")==null?"":map.get("code").toString());
					d.add(map.get("name")==null?"":map.get("name").toString());
					d.add(map.get("contactName")==null?"":map.get("contactName").toString());
					d.add(map.get("contactPhone")==null?"":map.get("contactPhone").toString());
					d.add(map.get("address")==null?"":map.get("address").toString());
					d.add(map.get("fax")==null?"":map.get("fax").toString());
					d.add(map.get("email")==null?"":map.get("email").toString());
					d.add(map.get("postcode")==null?"":map.get("postcode").toString());
					d.add(map.get("bankAccount")==null?"":map.get("bankAccount").toString());
					d.add(map.get("bankName")==null?"":map.get("bankName").toString());
					
					data.add(d);
				}
				
				workbook=Common.getExcel("客户", kehu, data,new int[]{0,0,0,0,0,0,0,0,0,0});
				
				
				break;
				
			case 2:
				
				//货品
				String[] huopin={"货品代码","货品名称","货品类型"};
				
				
				ProductDto productDto=new ProductDto();
				
				productDto.setCode(baseDto.getCode());
				productDto.setName(baseDto.getName());
				productDto.setValue(baseDto.getValue());
				productDto.setLetter(baseDto.getLetter());
				msg =productService.getProductList(productDto,new PageView(0, 0));
				
				for(int i=0;i<msg.getData().size();i++){
					Map<String,Object> map=(Map<String, Object>) msg.getData().get(i);
					List<Object> d=new ArrayList<Object>();
					d.add(map.get("code")==null?"":map.get("code").toString());
					d.add(map.get("name")==null?"":map.get("name").toString());
					d.add(map.get("value")==null?"":map.get("value").toString());
					
					data.add(d);
				}
				
				workbook=Common.getExcel("货品", huopin, data,new int[]{0,0,0});
				
				
				
				
				break;
				
			case 3:
				
				//货代
				String[] huodai={"货代简称","货代名称","联系人","联系电话"};
				
				CargoAgentDto ptDto=new CargoAgentDto();
				
				ptDto.setCode(baseDto.getCode());
				ptDto.setName(baseDto.getName());
				ptDto.setContactName(baseDto.getContactName());
				ptDto.setContactPhone(baseDto.getContactPhone());
				ptDto.setLetter(baseDto.getLetter());
				msg = cargoAgentService.getCargoAgentList(ptDto,new PageView(0, 0));
				
				
				for(int i=0;i<msg.getData().size();i++){
					Map<String,Object> map=(Map<String, Object>) msg.getData().get(i);
					List<Object> d=new ArrayList<Object>();
					d.add(map.get("code")==null?"":map.get("code").toString());
					d.add(map.get("name")==null?"":map.get("name").toString());
					d.add(map.get("contactName")==null?"":map.get("contactName").toString());
					d.add(map.get("contactPhone")==null?"":map.get("contactPhone").toString());
					
					data.add(d);
				}
				
				workbook=Common.getExcel("货代", huodai, data,new int[]{0,0,0,0});
				
				
						
						break;
				
			case 4:
				
				//船舶
				String[] chuanbo={"船舶代码","船舶英文名","船籍","船主","船长","船宽","最大吃水","建造年份","载重","总吨","净吨","负责人","联系人","联系电话"};
				
				ShipDto shipDto=new ShipDto();
				
				shipDto.setCode(baseDto.getCode());
				shipDto.setName(baseDto.getName());
				shipDto.setContactName(baseDto.getContactName());
				shipDto.setRefCode(baseDto.getRefCode());
				shipDto.setOwner(baseDto.getOwner());
				shipDto.setManager(baseDto.getManager());
				shipDto.setRefName(baseDto.getRefName());
				shipDto.setLetter(baseDto.getLetter());
				msg = shipService.getShipList(shipDto,new PageView(0, 0));
				
				
				for(int i=0;i<msg.getData().size();i++){
					Map<String,Object> map=(Map<String, Object>) msg.getData().get(i);
					List<Object> d=new ArrayList<Object>();
					d.add(map.get("code")==null?"":map.get("code").toString());
					d.add(map.get("name")==null?"":map.get("name").toString());
					d.add(map.get("shipRegistry")==null?"":map.get("shipRegistry").toString());
					d.add(map.get("owner")==null?"":map.get("owner").toString());
					d.add(map.get("shipLenth")==null?"":map.get("shipLenth").toString());
					d.add(map.get("shipWidth")==null?"":map.get("shipWidth").toString());
					d.add(map.get("shipDraught")==null?"":map.get("shipDraught").toString());
					d.add(map.get("buildYear")==null?"":map.get("buildYear").toString());
					d.add(map.get("loadCapacity")==null?"":map.get("loadCapacity").toString());
					d.add(map.get("grossTons")==null?"":map.get("grossTons").toString());
					d.add(map.get("netTons")==null?"":map.get("netTons").toString());
					d.add(map.get("manager")==null?"":map.get("manager").toString());
					d.add(map.get("contactName")==null?"":map.get("contactName").toString());
					d.add(map.get("contactPhone")==null?"":map.get("contactPhone").toString());
					
					data.add(d);
				}
				
				workbook=Common.getExcel("船舶", chuanbo, data,new int[]{0,0,0,0,1,1,1,0,1,1,1,0,0,0});
				
				
				
				break;
				
			case 5:
				
				//船代
				String[] chuandai={"船代简称","船代名称","联系人","联系电话"};
				
				
				ShipAgentDto shipAgentDto=new ShipAgentDto();
				
				shipAgentDto.setCode(baseDto.getCode());
				shipAgentDto.setName(baseDto.getName());
				shipAgentDto.setContactName(baseDto.getContactName());
				shipAgentDto.setContactPhone(baseDto.getContactPhone());
				shipAgentDto.setLetter(baseDto.getLetter());
				msg = shipAgentService.getShipAgentList(shipAgentDto,new PageView(0, 0));
				
				
				for(int i=0;i<msg.getData().size();i++){
					Map<String,Object> map=(Map<String, Object>) msg.getData().get(i);
					List<Object> d=new ArrayList<Object>();
					d.add(map.get("code")==null?"":map.get("code").toString());
					d.add(map.get("name")==null?"":map.get("name").toString());
					d.add(map.get("contactName")==null?"":map.get("contactName").toString());
					d.add(map.get("contactPhone")==null?"":map.get("contactPhone").toString());
					data.add(d);
				}
				
				workbook=Common.getExcel("船代", chuandai, data,new int[]{0,0,0,0});
				
				
				
				
				break;
				
			case 6:
				
				//车辆
				String[] cheliang={"车牌号","历史最大载重吨","最大允许荷载吨","所属单位","描述"};
				
				TruckDto truckDto=new TruckDto();
				
				truckDto.setCode(baseDto.getCode());
				truckDto.setName(baseDto.getName());
				truckDto.setCompany(baseDto.getCompany());
				truckDto.setLetter(baseDto.getLetter());
				
				msg = truckService.getTruckList(truckDto,new PageView(0, 0));
				
				
				for(int i=0;i<msg.getData().size();i++){
					Map<String,Object> map=(Map<String, Object>) msg.getData().get(i);
					List<Object> d=new ArrayList<Object>();
					d.add(map.get("code")==null?"":map.get("code").toString());
					d.add(map.get("loadCapacity")==null?"":map.get("loadCapacity").toString());
					d.add(map.get("maxLoadCapacity")==null?"":map.get("maxLoadCapacity").toString());
					d.add(map.get("company")==null?"":map.get("company").toString());
					d.add(map.get("description")==null?"":map.get("description").toString());
					data.add(d);
				}
				
				workbook=Common.getExcel("车辆", cheliang, data,new int[]{0,1,1,0,0});
				
				
				
				break;
				
			case 7:
				//商检
				String[] shangjian={"商检简称","商检全称","联系人","联系人电子邮箱","联系人电话"};
				
				InspectAgentDto inspectAgentDto=new InspectAgentDto();
				
				inspectAgentDto.setCode(baseDto.getCode());
				inspectAgentDto.setName(baseDto.getName());
				inspectAgentDto.setContactName(baseDto.getContactName());
				inspectAgentDto.setContactPhone(baseDto.getContactPhone());
				
				inspectAgentDto.setLetter(baseDto.getLetter());
				
				msg = inspectAgentService.getInpectAgentList(inspectAgentDto,new PageView(0, 0));
				
				
				for(int i=0;i<msg.getData().size();i++){
					Map<String,Object> map=(Map<String, Object>) msg.getData().get(i);
					List<Object> d=new ArrayList<Object>();
					d.add(map.get("code")==null?"":map.get("code").toString());
					d.add(map.get("name")==null?"":map.get("name").toString());
					d.add(map.get("contactName")==null?"":map.get("contactName").toString());
					d.add(map.get("contactEmail")==null?"":map.get("contactEmail").toString());
					d.add(map.get("contactPhone")==null?"":map.get("contactPhone").toString());
					data.add(d);
				}
				
				workbook=Common.getExcel("商检", shangjian, data,new int[]{0,0,0,0,0});
							
				
				break;
				
			case 8:
				

				//开证单位
				String[] kaizheng={"开证单位简称","开证单位全称","联系人","联系人电子邮箱","联系人电话"};
				
				CertifyAgentDto certifyAgentDto=new CertifyAgentDto();
				
				certifyAgentDto.setCode(baseDto.getCode());
				certifyAgentDto.setName(baseDto.getName());
				certifyAgentDto.setContactName(baseDto.getContactName());
				certifyAgentDto.setContactPhone(baseDto.getContactPhone());
				
				certifyAgentDto.setLetter(baseDto.getLetter());
				
				msg = certifyAgentService.getCertifyAgentList(certifyAgentDto,new PageView(0, 0));
				
				
				for(int i=0;i<msg.getData().size();i++){
					Map<String,Object> map=(Map<String, Object>) msg.getData().get(i);
					List<Object> d=new ArrayList<Object>();
					d.add(map.get("code")==null?"":map.get("code").toString());
					d.add(map.get("name")==null?"":map.get("name").toString());
					d.add(map.get("contactName")==null?"":map.get("contactName").toString());
					d.add(map.get("contactEmail")==null?"":map.get("contactEmail").toString());
					d.add(map.get("contactPhone")==null?"":map.get("contactPhone").toString());
					data.add(d);
				}
				
				workbook=Common.getExcel("开证单位", kaizheng, data,new int[]{0,0,0,0,0});
								
				
				
				
				break;
				
			case 9:
				//客户资质
				String[] kehuzizhi={"资质名","描述"};
				
				QualificationDto qualificationDto=new QualificationDto();
				
				qualificationDto.setName(baseDto.getName());
				
				qualificationDto.setLetter(baseDto.getLetter());
				
				msg = qualificationService.getQualificationList(qualificationDto,new PageView(0, 0));
				
				
				for(int i=0;i<msg.getData().size();i++){
					Map<String,Object> map=(Map<String, Object>) msg.getData().get(i);
					List<Object> d=new ArrayList<Object>();
					d.add(map.get("name")==null?"":map.get("name").toString());
					d.add(map.get("description")==null?"":map.get("description").toString());
					data.add(d);
				}
				
				workbook=Common.getExcel("客户资质", kehuzizhi, data,new int[]{0,0});
				
				break;
				
			case 10:
				//泊位
				String[] bowei={"泊位","船长(米)","吃水(米)","最大载重吨(吨)","泊位信息"};
				
				BerthDto berthDto=new BerthDto();
				
				berthDto.setName(baseDto.getName());
				
				berthDto.setLetter(baseDto.getLetter());
				
				msg = berthService.getBerthList(berthDto,new PageView(0, 0));
				
				
				for(int i=0;i<msg.getData().size();i++){
					Map<String,Object> map=(Map<String, Object>) msg.getData().get(i);
					List<Object> d=new ArrayList<Object>();
					d.add(map.get("name")==null?"":map.get("name").toString());
					d.add(map.get("limitLength")==null?"":map.get("limitLength").toString());
					d.add(map.get("limitDrought")==null?"":map.get("limitDrought").toString());
					d.add(map.get("limitDisplacement")==null?"":map.get("limitDisplacement").toString());
					d.add(map.get("description")==null?"":map.get("description").toString());
					
					data.add(d);
				}
				
				workbook=Common.getExcel("泊位", bowei, data,new int[]{0,1,1,1,0});
				
				break;
				
			case 11:
				//管线
				String[] guanxian={"管名","类型","前期存储物料","使用情况","清洗记录"};
				
				TubeDto tubeDto=new TubeDto();
				
				tubeDto.setName(baseDto.getName());
				tubeDto.setProductName(baseDto.getProductName());
				tubeDto.setLetter(baseDto.getLetter());
				
				msg = tubeService.getTubeList(tubeDto,new PageView(0, 0));
				
				
				for(int i=0;i<msg.getData().size();i++){
					Map<String,Object> map=(Map<String, Object>) msg.getData().get(i);
					List<Object> d=new ArrayList<Object>();
					d.add(map.get("name").toString());
					String tubeType="";
					if(map.get("limitLength")!=null){
						
						if(Integer.parseInt(map.get("limitLength").toString())==0){
							tubeType="主管线";
						}else{
							tubeType="过渡管线";
						}
					}
					
					d.add(tubeType);
					d.add(map.get("productName")==null?"":map.get("productName").toString());
					d.add(map.get("description")==null?"":map.get("description").toString());
					
					data.add(d);
				}
				
				workbook=Common.getExcel("管线", guanxian, data,new int[]{0,0,0,0});
				break;
			case 12:
				//车位
				String[] chewei={"序号","名称","货品名","使用情况"};
				
				ParkDto parkDto=new ParkDto();
				
				parkDto.setName(baseDto.getName());
				parkDto.setLetter(baseDto.getLetter());
				
				msg = parkService.getParkList(parkDto,new PageView(0, 0));
				
				
				for(int i=0;i<msg.getData().size();i++){
					Map<String,Object> map=(Map<String, Object>) msg.getData().get(i);
					List<Object> d=new ArrayList<Object>();
					d.add(i+1+"");
					d.add(map.get("name")==null?"":map.get("name").toString());
					d.add(map.get("productName")==null?"":map.get("productName").toString());
					d.add(map.get("description")==null?"":map.get("description").toString());
					
					data.add(d);
				}
				
				workbook=Common.getExcel("车位", chewei, data,new int[]{1,0,0,0});
				break;
				
			case 13:
				//港口
				String[] gangkou={"编号","名称","描述"};
				PortDto portDto=new PortDto();
				
				portDto.setName(baseDto.getName());
				portDto.setCode(baseDto.getCode());
				portDto.setLetter(baseDto.getLetter());
				
				msg = portService.getPortList(portDto,new PageView(0, 0));
				
				
				for(int i=0;i<msg.getData().size();i++){
					Map<String,Object> map=(Map<String, Object>) msg.getData().get(i);
					List<Object> d=new ArrayList<Object>();
					d.add(map.get("code")==null?"":map.get("code").toString());
					d.add(map.get("name")==null?"":map.get("name").toString());
					d.add(map.get("description")==null?"":map.get("description").toString());
					
					data.add(d);
				}
				
				workbook=Common.getExcel("港口", gangkou, data,new int[]{0,0,0});
				
				break;
				
			case 14:
				

				//储罐
				String[] chuguan={"罐号","货品","储罐类型","最大储存量(吨)","实际储存量(吨)","可储容量(吨)","实验密度(ρ)","实验温度(℃)","储罐温度(℃)","使用情况"};
				TankDto tankDto=new TankDto();
				
				tankDto.setName(baseDto.getName());
				tankDto.setCode(baseDto.getCode());
				tankDto.setProductName(baseDto.getProductName());
				
				tankDto.setLetter(baseDto.getLetter());
				
				msg =tankService.getTankList(tankDto,new PageView(0, 0));
				
				
				for(int i=0;i<msg.getData().size();i++){
					Map<String,Object> map=(Map<String, Object>) msg.getData().get(i);
					List<Object> d=new ArrayList<Object>();
					d.add(map.get("code")==null?"":map.get("code").toString());
					d.add(map.get("productName")==null?"":map.get("productName").toString());
					
					
					String tanktype="";
					if(Integer.parseInt(map.get("type").toString())==0){
						tanktype="内贸";
					}
					else if(Integer.parseInt(map.get("type").toString())==1){
						tanktype="外贸";
					}
					else if(Integer.parseInt(map.get("type").toString())==2){
						tanktype="保税非包罐";
					}
					else if(Integer.parseInt(map.get("type").toString())==3){
						tanktype="保税包罐";
					}
					d.add(tanktype);
					d.add(map.get("capacityTotal")==null?"":map.get("capacityTotal").toString());
					d.add(map.get("capacityCurrent")==null?"":map.get("capacityCurrent").toString());
					d.add(map.get("capacityFree")==null?"":map.get("capacityFree").toString());
					d.add(map.get("testDensity")==null?"":map.get("testDensity").toString());
					d.add(map.get("testTemperature")==null?"":map.get("testTemperature").toString());
					d.add(map.get("tankTemperature")==null?"":map.get("tankTemperature").toString());
					d.add(map.get("description")==null?"":map.get("description").toString());
					
					data.add(d);
				}
				
				workbook=Common.getExcel("储罐", chuguan, data,new int[]{0,0,0,1,1,1,1,1,1,0});
				
				
				break;
				
				
			case 15:
				
				//泵
				String[] ben={"名称","前期存储物料","泵性质","使用情况"};
				PumpDto pumpDto=new PumpDto();
				
				pumpDto.setName(baseDto.getName());
				
				pumpDto.setLetter(baseDto.getLetter());
				
				msg =pumpService.getPumpList(pumpDto,new PageView(0, 0));
				
				
				for(int i=0;i<msg.getData().size();i++){
					Map<String,Object> map=(Map<String, Object>) msg.getData().get(i);
					List<Object> d=new ArrayList<Object>();
					d.add(map.get("name")==null?"":map.get("name").toString());
					d.add(map.get("productName")==null?"":map.get("productName").toString());
					String pumpType="";
					if(Integer.parseInt(map.get("type").toString())==1){
						pumpType="船发泵";
					}else if(Integer.parseInt(map.get("type").toString())==2){
						pumpType="车发泵 ";
					}
					d.add(pumpType);
					
					d.add(map.get("description")==null?"":map.get("description").toString());
					
					data.add(d);
				}
				
				workbook=Common.getExcel("泵", ben, data,new int[]{0,0,0,0});
				
				
				
				break;
			}
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
