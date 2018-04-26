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
					<i class="fa fa-indent"></i>入港计划列表<span style="font-size: small;margin: 0;padding-right: 18px;" class="pull-right"><a style="padding-left: 20px;" class="hidden-print" onclick="javascript:Arrival.exportExcel();"> <i class="fa fa-file-excel-o">&nbsp;导出</i></a></span>
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
				<form id="arrivalListForm" target="_self" class="form-horizontal searchCondition">
				<div  id="roleManagerQueryDivId" >
				<table border="0" cellspacing="0" cellpadding="0">
				  <tr>
				      <td>
				          <div class="form-group">
				              <label class="control-label" style="width:50px;float:left;">船名:</label>
				              <div style="margin-left:15px;float:left;">
				                  <input name="shipId" maxlength="32" class="form-control" type="text"id="shipId" data="0" style="width:180px;"/>
				              </div>
				
												 <label class="control-label" style="width:80px;float:left;">起始范围:</label>
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
				      <td>
				              <div class="form-group">
				              <label class="control-label" style="width:70px;float:left;">&nbsp;&nbsp;&nbsp;类型:</label>
				              <div style="margin-left:15px;width:100px;float:left;">
				              <select class="form-control select2me status" id="type"  name="type"  >
												<option value="13">全部</option>
												<option value="3">通过</option>
												<option value="1">入库</option>
								</select>
				              </div>
				              <label class="control-label" style="width:70px;float:left;">&nbsp;&nbsp;&nbsp;状态:</label>
				              <div style="margin-left:15px;width:100px;float:left;">
				              <select class="form-control select2me status" id="arrivalStatus"  name="arrivalStatus"  >
												<option value="3">全部</option>
												<option value="1">未接卸</option>
												<option value="2">已接卸</option>
												<option value="4">已作废</option>
												<option value="5">未关联船期预告</option>
								</select>
				              </div>
				              
				              </div>
				      </td>
				      </tr>
				  
				  <tr>
				  <td>
				  <div class="form-group">
				       <label class="control-label" style="width:50px;float:left;">货品:</label>
						<div style="margin-left:15px;float:left;">
							<input id="product" type="text" name="productId" data-provide="typeahead"  class="form-control product">
						</div>
				  <label class="control-label" style="width:80px;float:left;">&nbsp;&nbsp;&nbsp;货主:</label>
						<div style="margin-left:15px;float:left;width:280px;">
							<input id="client" type="text" name="clientId" data-provide="typeahead"  class="form-control client">
						</div>
						
				  </div>
				  </td>
				      <td>
				       <div class="form-group">
				       
				       <label class="control-label" style="width:80px;float:left;">&nbsp;&nbsp;&nbsp;单号:</label>
						<div style="margin-left:15px;float:left;width:280px;">
							<input id="inboundCode" type="text" name="inboundCode"  class="form-control inboundCode">
						</div>
				      
				              
				              
				              </div>
				              </td>
				      
				      <td style="vertical-align: bottom;"><button onclick="Arrival.search()" type="button" style="position:relative; margin-left:35px; top: -15px" class="btn btn-success"><span class="glyphicon glyphicon-search"></span>&nbsp;</button>
				       <button class="btn btn-primary reset" onclick="Arrival.reset()" style="position:relative; margin-left:35px; top: -15px" type="button">
												<span title="重置" class="fa fa-undo"></span>&nbsp;
											</button></td>
				  
				  </tr>
				  
				</table>	
				</div>
				</form>
			</div>
			<div class="btn-group buttons">
			<shiro:hasPermission name="AARRIVALPLANADD">
				<button class="btn btn-default btn-circle mar-r-10 btn-add" type="button">
					<span class="fa fa-plus"></span><span class="text">添加</span>
				</button>
				</shiro:hasPermission>
				<shiro:hasPermission name="AARRIVALPLANUPDATE">
				<button class="btn btn-default btn-circle mar-r-10 btn-modify" type="button" style="display: none">
						<span class="fa fa-edit"></span><span class="pad-l-5">修改</span>
					</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="AARRIVALDELETE">
				<button class="btn btn-default btn-circle mar-r-10 btn-remove" type="button" style="display: none">
						<span class="fa fa-remove"></span><span class="pad-l-5">撤销</span>
					</button> 
					</shiro:hasPermission>
				<button class="btn btn-default btn-circle  btn-search" type="button">
					<i class="fa  fa-search"></i><span class="pad-l-5" style="line-height: 16px;">搜索</span>
				</button>
			</div>
			<div data-role="arrivalGrid">
			</div>
		</div>
		<!-- End: life time stats -->
	</div>
</div>
	
<script>
</script>
</body>
</html>

