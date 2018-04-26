package com.skycloud.oa.system.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.mock.web.MockMultipartFile;

import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.order.model.Contract;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.ClientDto;
import com.skycloud.oa.system.model.Client;
import com.skycloud.oa.system.model.ClientFile;
import com.skycloud.oa.system.service.ClientGradeService;
import com.skycloud.oa.system.service.ClientQualificationService;
import com.skycloud.oa.system.service.ClientService;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;

/**
 * 客户操作控制类
 * @author yanyufeng
 *
 */
@Controller
@RequestMapping("/client")
public class ClientController {

	@Autowired
	private ClientService clientService;
	@Autowired
	private ClientQualificationService clientQualificationService;
	@Autowired
	private ClientGradeService clientGrageService;
	
	/**
	 * 查询客户列表
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param clientDto
	 * @param pageSize
	 * @param currentPage
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public Object list(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute ClientDto clientDto,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
		PageView pageView=new PageView(pagesize, page);
		OaMsg msg = clientService.getClientList(clientDto, pageView);
		return msg;
//		return new ModelAndView("sys_client/list").addObject("oaMsg", msg);
	}
	

	/**
	 * 得到单个客户
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param clientDto
	 * @param method
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "get")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute ClientDto clientDto, 
			String method) throws OAException {
		if (Common.empty(clientDto.getId()) || clientDto.getId() == 0) {// 为传id
			return new ModelAndView("sys_client/add");
		} else {
			OaMsg clientMsg = clientService.getClientList(clientDto, new PageView(0,0));
			if (clientMsg.getData().size() > 0) {
				if (!Common.empty(method) && method.equals(Constant.METHOD_DO)) {
					return new ModelAndView("sys_client/edit").addObject("client",clientMsg.getData().get(0)).addObject("clientGrade", clientGrageService.getClientGrade());
				} 
				else {
					return new ModelAndView("sys_client/get").addObject("client", clientMsg.getData().get(0));
				}
			} else {
				clientMsg.setMsg("所要查询的对象不存在");
				return new ModelAndView(Constant.PAGE_ERROR).addObject(clientMsg);
			}

		}
	}
	
	

	/**
	 * 添加客户
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param client
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public Object save(HttpServletRequest request, HttpServletResponse response, @ModelAttribute Client client) throws OAException {
		return clientService.addClient(client);
	}

	/**
	 * 删除客户
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param clientIds
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public Object delete(HttpServletRequest request, HttpServletResponse response, String clientIds) throws OAException {
		return clientService.deleteClient(clientIds);
	}
	

	@RequestMapping(value = "update")
	@ResponseBody
	public Object update(HttpServletRequest request, HttpServletResponse response, @ModelAttribute Client client) throws OAException {
		return clientService.updateClient(client);
	}
	
	
	@RequestMapping(value = "select")
	@ResponseBody
	public OaMsg select(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute ClientDto clientDto) throws OAException{
		OaMsg msg = clientService.getClientList(clientDto, new PageView(0,0));
		
		return msg;
	}
	
	/**
	 * 从客户组里移除客户
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param groupId
	 * @return
	 * @throws OAException
	 * 2015-3-9 下午2:05:10
	 */
	@RequestMapping(value = "removeGroup")
	@ResponseBody
	public OaMsg removeGroup(HttpServletRequest request, HttpServletResponse response,
			String clientId) throws OAException{
		OaMsg msg = clientService.removeGroup(clientId);
		
		return msg;
	}
	
	
	
//	@RequestMapping(value="updatefile")
//	@ResponseBody
//	public Object updatefile(HttpServletRequest request, HttpServletResponse response,  
//	          int id) throws OAException{
//		OaMsg oaMsg=new OaMsg();
//		File file = new File("/usr/local/tomcat/webapps/sop/resource/clientFile");
//		System.out.println("1111");
//        if (file.exists()) {
//            File[] files = file.listFiles();
//            if (files.length == 0) {
//                System.out.println("文件夹是空的!");
//                return oaMsg;
//            } else {
//                for (File file2 : files) {
//							try {
//								
//								System.out.println("2222");
//		                    	File nFile=new File("/usr/local/tomcat/webapps/sop/resource/clientFile/"+new String(file2.getName().getBytes(),"GB2312"));
////		                    	file2.renameTo(nFile);
//		                    	FileUtil.copyFile(file2, nFile);
//		                    	System.out.println(nFile.getName());
//		                    	oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
//		                        
//							} catch (IOException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//                }
//            }
//        } else {
//            System.out.println("文件不存在!");
//            return oaMsg;
//        }
//		return oaMsg;
//    }
//	
//	public void upFile(HttpServletRequest request, HttpServletResponse response,  
//			MockMultipartFile file){
//		
//		OaMsg oaMsg=new OaMsg();
//		
//		//MultipartFile是对当前上传的文件的封装，当要同时上传多个文件时，可以给定多个MultipartFile参数  
//        if (!((MultipartFile) file).isEmpty()) {  
//        	FileOutputStream fos = null; 
//            try {
//				byte[] bytes = ((MultipartFile) file).getBytes();
//		            //查找目录，如果不存在，就创建
//		            File dirFile = new File(request.getSession().getServletContext().getRealPath("/")+"resource/clientFile");
//		            if(!dirFile.exists()){
//		                if(!dirFile.mkdir())
//							try {
//								throw new Exception("目录不存在，创建失败！");
//							} catch (Exception e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//		            }
//				String url=request.getSession().getServletContext().getRealPath("/")+"resource/clientFile/"+file.getOriginalFilename();  
//				
//				fos = new FileOutputStream(url);                 
//				fos.write(bytes); 
//				
//            
////				ClientFile clientFile = new ClientFile();
////				clientFile.setClientId(id);
////				clientFile.setName(file.getOriginalFilename());
////				clientFile.setUrl("/resource/clientFile/"+file.getOriginalFilename());
////            clientService.addFile(clientFile);
//            
//           
//            
//            } catch (IOException e) {
//            	// TODO Auto-generated catch block
//            	e.printStackTrace();
//            	 oaMsg.setCode(Constant.SYS_CODE_NULL_ERR);
//            	
//            			 }  
//       } else {  
//    	   oaMsg.setCode(Constant.SYS_CODE_NULL_ERR);
//    		
//             
//      }  
//	}
	
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
		            File dirFile = new File(request.getSession().getServletContext().getRealPath("/")+"resource/clientFile");
		            if(!dirFile.exists()){
		                if(!dirFile.mkdir())
							try {
								throw new Exception("目录不存在，创建失败！");
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		            }
				String url=request.getSession().getServletContext().getRealPath("/")+"resource/clientFile/"+file.getOriginalFilename();  
				
				fos = new FileOutputStream(url);                 
				fos.write(bytes); 
				
            
				ClientFile clientFile = new ClientFile();
				clientFile.setClientId(id);
				clientFile.setName(file.getOriginalFilename());
				clientFile.setUrl("/resource/clientFile/"+file.getOriginalFilename());
            clientService.addFile(clientFile);
            
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
	
	@RequestMapping(value = "deleteFile")
	@ResponseBody
	public Object deleteFile(HttpServletRequest request, HttpServletResponse response, String id,String url) throws OAException {
		return clientService.deleteClientFile(id,url);
	}
	@RequestMapping(value = "getClientFile")
	@ResponseBody
	public Object getClientFile(HttpServletRequest request, HttpServletResponse response,
			String clientId) throws OAException{
		OaMsg msg = clientService.getClientFile(clientId);
		return msg;
}
}
