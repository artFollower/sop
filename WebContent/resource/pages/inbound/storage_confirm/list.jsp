<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
	.row-details-close {
	    background: url("<%=basePath%>resources/admin/img/datatable-row-openclose.png") no-repeat scroll 0 0 rgba(0, 0, 0, 0);
	    cursor: pointer;
	    display: inline-block;
	    height: 14px;
	    margin-top: 3px;
	    width: 14px;
	}
	.row-details-open {
	    background: url("<%=basePath%>resources/admin/img/datatable-row-openclose.png") no-repeat scroll 0 -23px rgba(0, 0, 0, 0);
	    cursor: pointer;
	    display: inline-block;
	    height: 14px;
	    margin-top: 3px;
	    width: 14px;
	}
</style>

</head>
<body>
	<!-- BEGIN PAGE HEADER-->
	<div class="row">
	<div class="col-md-12">
		<!-- Begin: life time stats -->
		<div class="portlet">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-paperclip"></i>入库确认列表<span style="font-size: small;margin: 0;padding-right: 18px;" class="pull-right"><a style="padding-left: 20px;" class="hidden-print" onclick="javascript:Storage.exportExcel();"> <i class="fa fa-file-excel-o">&nbsp;导出</i></a></span>
				</div>
				<div class="actions" style="display: none">
					<div class="btn-group">
						<a class="btn default yellow-stripe" href="javascript:void(0)" data-toggle="dropdown"> <i class="fa fa-share"></i> <span class="hidden-480"> 工具 </span> <i class="fa fa-angle-down"></i>
						</a>
						<ul class="dropdown-menu pull-right">
							<li><a href="javascript:void(0)"> 导出EXCEL </a></li>
							<li><a href="javascript:void(0)"> 导出CSV </a></li>
							<li><a href="javascript:void(0)"> 导出XML </a></li>
							<li class="divider"></li>
							<li><a href="javascript:void(0)"> 打印 </a></li>
							<li><a href="#/intentAdd">添加</a></li>
						</ul>
					</div>
				</div>
			</div>
			<div>
				<form id="storageListForm" target="_self" class="form-horizontal searchCondition">
				<div  id="roleManagerQueryDivId" >
				<table border="0" cellspacing="0" cellpadding="0">
				  <tr>
				      <td>
				          <div class="form-group">
				              <label class="control-label" style="width:100px;float:left;">货批编号:&nbsp;</label>
				              <div style="margin-left:15px;float:left;">
				                  <input name="arrivalcode" maxlength="32" class="form-control arrivalcode" type="text" style="width:180px;"/>
				              </div>
				
				              <label class="control-label" style="width:100px;float:left;">货主:&nbsp;</label>
				              <div style="margin-left:15px;float:left;">
									<input id="clientId" type="text" name="clientId" data-provide="typeahead"  class="form-control clientId">
				              </div>
				              
				               <label class="control-label" style="width:100px;float:left;">入库船舶:&nbsp;</label>
				              <div style="margin-left:15px;float:left;">
									<input id="shipId" type="text" name="shipId" data-provide="typeahead"  class="form-control shipId">
				              </div>
				              
				               <label class="control-label" style="width:100px;float:left;">货品:&nbsp;</label>
				              <div style="margin-left:15px;float:left;">
									<input id="productId" type="text" name="productId" data-provide="typeahead"  class="form-control productId">
				              </div>
				              
				              
				          </div>
				      </td>
				     
				  </tr>
				  
				  <tr>
				  <td>
				  <div class="form-group">
				  <label class="control-label" style="width:100px;float:left;">类型:</label>
				              <div style="margin-left:15px;width:100px;float:left;">
				              <select class="form-control select2me status" id="taxType"  name="taxType"  >
				              					<option value="13">全部</option>
												<option value="1">内贸</option>
												<option value="2">外贸</option>
												<option value="3">保税</option>
								</select>
				              </div>
				              <label class="control-label" style="width:100px;float:left;">货批状态:</label>
				              <div style="margin-left:15px;width:100px;float:left;">
				              <select class="form-control select2me passStatus" id="passStatus"  name="passStatus"  >
				              					<option value="13">默认</option>
												<option value="1">全部放行</option>
												<option value="2">部分放行</option>
												<option value="3">未放行</option>
												<option value="4">未扣损耗</option>
								</select>
				              </div>
				  <label class="control-label" style="width:80px;float:left;">到港日期:</label>
												<div style="margin-left:15px;float:left;">
													<div
														class="input-group input-large date-picker input-daterange"
														data-date-format="yyyy-mm-dd">
														<input type="text" class="form-control" name="startTime" id="startTime"
															id="startTime"> <span class="input-group-addon">到</span>
														<input type="text" class="form-control" name="endTime" name="endTime"
															id="endTime">
													</div>
												</div>
												</div>
				  </td>
				  
				   <td style="vertical-align: bottom;">
				      <button onclick="Storage.search(1)" type="button" style="position:relative; margin-left:35px;top:-15px; " class="btn btn-success"><span class="glyphicon glyphicon-search"></span>&nbsp;</button>
				      
				      
				      <button class="btn btn-primary reset" onclick="Storage.reset()" style="position:relative; margin-left:35px; top: -15px" type="button">
												<span title="重置" class="fa fa-undo"></span>&nbsp;
											</button>
				      
				      <button  onclick="Storage.search(2)" class="btn btn-default btn-square " type="button" style="position:relative; margin-left:35px;top:-15px; ">
					<span class="pad-l-5" style="line-height: 16px;"></span>
				</button>
				      </td>
				  </tr>
				</table>	
				</div>
				</form>
			</div>
			<div class="btn-group buttons">
			<shiro:hasPermission name="AINSTOREUPDATE">
				<button class="btn btn-default btn-circle mar-r-10 btn-modify" type="button" style="display: none">
						<span class="fa fa-edit"></span><span class="pad-l-5">修改</span>
					</button>
					</shiro:hasPermission>
				<button class="btn btn-default btn-circle  btn-search" style="display:none" type="button" >
					<i class="fa  fa-search"></i><span class="pad-l-5" style="line-height: 16px;">搜索</span>
				</button>
				<button class="btn btn-default btn-circle  btn-moban" onclick="Storage.printmb()" type="button">
					<i class="fa  "></i><span class="pad-l-5" style="line-height: 16px;">打印损耗单模板</span>
				</button>
			</div>
			<div data-role="storageGrid">
			</div>
		</div>
		<!-- End: life time stats -->
	</div>
</div>
	
<script>
</script>
</body>
</html>