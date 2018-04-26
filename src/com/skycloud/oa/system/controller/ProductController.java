package com.skycloud.oa.system.controller;

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
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.ProductDto;
import com.skycloud.oa.system.model.Product;
import com.skycloud.oa.system.service.ProductService;
import com.skycloud.oa.system.service.ProductTypeService;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;

@Controller
@RequestMapping("/product")
public class ProductController 
{	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductTypeService productTypeService;
	
	/**
	 * 查询货品
	 * @Title:ProductController
	 * @Description:
	 * @param ptDto
	 * @param request
	 * @param response
	 * @param pagesize
	 * @param page
	 * @return
	 * @throws OAException
	 * @Date:2015年11月22日 下午12:34:55
	 * @return:Object 
	 * @throws
	 */
    @RequestMapping("/list")
    @ResponseBody
	public Object list(@ModelAttribute ProductDto ptDto,HttpServletRequest request ,HttpServletResponse response,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException
    {
    	PageView pageView=new PageView(pagesize, page);
		OaMsg oaMsg=productService.getProductList(ptDto, pageView);
		return oaMsg;
//		return new ModelAndView("sys_product/list").addObject("oaMsg", oaMsg);
	}
    
    /**
     * 查询货品类型
     * @Title:ProductController
     * @Description:
     * @param request
     * @param response
     * @param ptDto
     * @return
     * @throws OAException
     * @Date:2015年11月22日 下午12:39:20
     * @return:Object 
     * @throws
     */
    @RequestMapping("/typeList")
    @ResponseBody
	public Object list(HttpServletRequest request,HttpServletResponse response,@ModelAttribute ProductDto ptDto) throws OAException
    {
		OaMsg oaMsg = productTypeService.getProductTypeList();
		return oaMsg;
	}
    
    /**
     * 查询货品类型
     * @Title:ProductController
     * @Description:
     * @param request
     * @param response
     * @param ptDto
     * @param method
     * @return
     * @throws OAException
     * @Date:2015年11月22日 下午12:35:22
     * @return:ModelAndView 
     * @throws
     */
    @RequestMapping("/get")
    @ResponseBody
	public ModelAndView get(HttpServletRequest request,HttpServletResponse response,@ModelAttribute ProductDto ptDto,String method) throws OAException
    {
		if(Common.empty(ptDto.getId())&&Common.isNull(ptDto.getId()))
		{
			return new ModelAndView("sys_product/add").addObject("type", productTypeService.getProductTypeList());
		}
		else 
		{
				OaMsg oaMsg=productService.getProductList(ptDto, new PageView(0,0));
				if(oaMsg.getData().size()>0)
				{
					if (!Common.empty(method) && method.equals(Constant.METHOD_DO)) 
					{
						return new ModelAndView("sys_product/edit").addObject("product", oaMsg.getData().get(0)).addObject("type", productTypeService.getProductTypeList());
					}
				else
				{
					return new ModelAndView("sys_product/add").addObject("product", oaMsg.getData().get(0));
				}
			}
			else
			{
				oaMsg.setMsg("所要查询的对象不存在");
				return new ModelAndView(Constant.PAGE_ERROR).addObject(oaMsg);
			}
		}
	}
    
    @RequestMapping("/save")
    @ResponseBody
	public Object save(HttpServletRequest request,HttpServletResponse response,@ModelAttribute Product product) throws OAException{
		return productService.addProduct(product);
	}
    @RequestMapping("/update")
    @ResponseBody
	public Object update(HttpServletRequest request,HttpServletResponse response,@ModelAttribute Product product) throws OAException{
		return productService.updateProduct(product);
	}
    @RequestMapping("/delete")
    @ResponseBody
	public Object delete(HttpServletRequest request,HttpServletResponse response,String id) throws OAException{
		return productService.deleteProduct(id);
	}
	
    @RequestMapping("/select")
    @ResponseBody
	public OaMsg select(@ModelAttribute ProductDto ptDto,HttpServletRequest request,HttpServletResponse response,String method) throws OAException{
		OaMsg oaMsg=productService.getProductList(ptDto,new PageView(0,0));
		return oaMsg;
	}
}
