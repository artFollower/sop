package com.skycloud.oa.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.QualificationDto;
import com.skycloud.oa.system.model.Qualification;
import com.skycloud.oa.system.service.QualificationService;
import com.skycloud.oa.utils.OaMsg;

@Controller
@RequestMapping("/qualification")
public class QualificationController {
@Autowired
private QualificationService qualificationService;
    
    @RequestMapping("/list")
    @ResponseBody
	public Object list(HttpServletRequest request ,HttpServletResponse response,@ModelAttribute QualificationDto qDto,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
    	OaMsg oaMsg=null;
    	 oaMsg=qualificationService.getQualificationList(qDto,new PageView(pagesize, page));
		return oaMsg;
	}
    @RequestMapping("/add")
    @ResponseBody
    public Object add(HttpServletRequest request ,HttpServletResponse response,@ModelAttribute Qualification cq)throws OAException{
    	OaMsg oaMsg=null;
    	oaMsg=qualificationService.addQualification(cq);
    	return oaMsg;
    }
    
    @RequestMapping("/update")
    @ResponseBody
	public Object update(HttpServletRequest request ,HttpServletResponse response,@ModelAttribute Qualification cq) throws OAException{
    	OaMsg oaMsg=null;
    	oaMsg=qualificationService.updateQualification(cq);
    	return oaMsg;
    }
    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(HttpServletRequest request ,HttpServletResponse response,@ModelAttribute QualificationDto cqDto)throws OAException{
    	
    	OaMsg oaMsg=null;
    	oaMsg=qualificationService.deleteQualification(cqDto.getIds());
    	return oaMsg;
    }
}
