package com.skycloud.oa.system.controller;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.InboundOperationDto;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.ProductDto;
import com.skycloud.oa.system.dto.TankDto;
import com.skycloud.oa.system.model.Tank;
import com.skycloud.oa.system.service.ProductService;
import com.skycloud.oa.system.service.TankService;
import com.skycloud.oa.system.service.TankTypeService;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;

/**
 * 储罐基础模块
 * @author jiahy
 *
 */
@Controller
@RequestMapping("/tank")
public class TankController {
@Autowired
private TankService tankService;
@Autowired
private TankTypeService tankTypeService;
@Autowired
private ProductService productService;

    //获取储罐列表
    @RequestMapping("/list")
    @ResponseBody
	public Object list(HttpServletRequest request ,HttpServletResponse response,@ModelAttribute TankDto tankDto,@ModelAttribute InboundOperationDto ioDto,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
		OaMsg oaMsg=tankService.getTankList(tankDto, new PageView(pagesize, page));
		return oaMsg;
	}
    
    //获取储罐修改记录列表
    @RequestMapping("/updatelogList")
    @ResponseBody
	public Object updatelogList(HttpServletRequest request ,HttpServletResponse response,@ModelAttribute TankDto tankDto,@ModelAttribute InboundOperationDto ioDto,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
		OaMsg oaMsg=tankService.getUpdateTankLogList(tankDto, new PageView(pagesize, page));
		return oaMsg;
	}
    
    //获取单个储罐信息
    @RequestMapping("/get")
    @ResponseBody
	public ModelAndView get(HttpServletRequest request,HttpServletResponse response,@ModelAttribute TankDto ptDto,String method) throws OAException{
		if(Common.empty(ptDto.getId())&&Common.isNull(ptDto.getId())){
			return new ModelAndView("sys_tank/add").addObject("type",tankTypeService.getTankTypeList()).addObject("product",productService.getProductList(new ProductDto(),new PageView(0,0)));
		}else {
			OaMsg oaMsg=tankService.getTankList(ptDto, new PageView(0, 0));
			if(oaMsg.getData().size()>0){
				if (!Common.empty(method) && method.equals(Constant.METHOD_DO)) {
				return new ModelAndView("sys_tank/edit").addObject("oaMsg", oaMsg.getData().get(0)).addObject("type",tankTypeService.getTankTypeList()).addObject("product",productService.getProductList(new ProductDto(), new PageView(0,0)));
			}else{
				return new ModelAndView("sys_tank/add").addObject("tank", oaMsg.getData().get(0)).addObject("type",tankTypeService.getTankTypeList()).addObject("product",productService.getProductList(new ProductDto(), new PageView(0,0)));
			}
		}else{
			oaMsg.setMsg("所要查询的对象不存在");
			return new ModelAndView(Constant.PAGE_ERROR).addObject(oaMsg);
		}
		}
	}
    //添加储罐
    @RequestMapping("/add")
    @ResponseBody
	public Object save(HttpServletRequest request,HttpServletResponse response,@ModelAttribute Tank tank) throws OAException{
		return tankService.addTank(tank);
	}
    //更新储罐
    @RequestMapping("/update")
    @ResponseBody
	public Object update(HttpServletRequest request,HttpServletResponse response,@ModelAttribute Tank tank) throws OAException{
    	tank.setEditTime(new Timestamp(System.currentTimeMillis()));
		return tankService.updateTank(tank);
	}
    //删除储罐
    @RequestMapping("/delete")
    @ResponseBody
	public Object delete(HttpServletRequest request,HttpServletResponse response,@ModelAttribute TankDto tankDto) throws OAException{
		return tankService.deleteTank(tankDto.getIds());
	}
	
	
}
