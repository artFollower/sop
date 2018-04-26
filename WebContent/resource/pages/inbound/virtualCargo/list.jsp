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
					<i class="fa fa-paperclip"></i>副库列表
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
				<div  id="roleManagerQueryDivId" hidden="true" >
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
				              
				          </div>
				      </td>
				      <td style="vertical-align: bottom;"><button onclick="VirtualCargo.search()" type="button" style="position:relative; margin-left:35px; top: -15px" class="btn btn-success"><span class="glyphicon glyphicon-search"></span>&nbsp;</button></td>
				  </tr>
				</table>	
				</div>
				</form>
			</div>
			<div class="btn-group buttons">
			<shiro:hasPermission name="AADDGRAFTCARGO">
				<button class="btn btn-default btn-circle mar-r-10 btn-modify" type="button"  onclick="VirtualCargo.doOpen()" >
						<span class="fa fa-plus"></span><span class="pad-l-5">添加副库货批</span>
					</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="ADOGRAFT">
					<button class="btn btn-default btn-circle mar-r-10 btn-grafting" type="button" " >
						<span class="fa fa-cut"></span><span class="pad-l-5">嫁接</span>
					</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="ASELECTGRAFTHISTORY">
					<button class="btn btn-default btn-circle mar-r-10 btn-history" type="button" " >
						<span class="fa fa-clock-o"></span><span class="pad-l-5">嫁接历史记录</span>
					</button>
					</shiro:hasPermission>
					
				<button class="btn btn-default btn-circle  btn-search" type="button">
					<i class="fa  fa-search"></i><span class="pad-l-5" style="line-height: 16px;">搜索</span>
				</button>
			</div>
			<div data-role="virtualGrid">
			</div>
		</div>
		<!-- End: life time stats -->
	</div>
</div>
	
<script>
</script>
</body>
</html>