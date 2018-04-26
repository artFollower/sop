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
					<i class="fa fa-paperclip"></i>货体管理列表
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
			
			
			
			
			
				<form id="goodsListForm" target="_self" class="form-horizontal searchCondition">
				<div class="form-group">
				
				<label class="control-label col-md-1 " style="width:70px;float:left;" >货体号:</label>
						<div class="col-md-2">
							<input id="code" type="text" name="code"  class="form-control code">
						</div>
						
						<label class="control-label col-md-1 " style="width:70px;float:left;" >货批号:</label>
						<div class="col-md-2">
							<input id="cargoCode" type="text" name="cargoCode"   class="form-control cargoCode">
						</div>
				
				<label class="control-label col-md-2 " style="width:120px;float:left;" >货主:</label>
						<div class="col-md-4">
							<input id="client" type="text" name="clientId" data-provide="typeahead"  class="form-control client">
						</div>
						
				</div>
				
				<div class="form-group">
				
				      <label class="control-label col-md-1 " style="width:70px;float:left;" >货品:</label>
						<div class="col-md-2">
							<input id="product" type="text" name="productId" data-provide="typeahead"  class="form-control product">
						</div>
						<label class="control-label col-md-1 " style="width:70px;float:left;" >罐号:</label>
						<div class="col-md-2">
							<input id="tankId" type="text" name="tankId" data-provide="typeahead"  class="form-control tankId">
						</div>
				      						<label class="control-label col-md-4" style="width:120px;float:left;">统计日期:</label>
												<div class="col-md-4">
													<div
														class="input-group input-large date-picker input-daterange"
														data-date-format="yyyy-mm-dd">
														<input type="text" class="form-control" name="sStartTime" 
															id="startTime"><span class="input-group-addon">到</span>
														<input type="text" class="form-control" name="sEndTime" 
															id="endTime">
													</div>
												</div>
												
												
												
																			<button onclick="goodsManager.search()" type="button" style="position:relative; margin-left:35px" class="btn btn-success"><span class="glyphicon glyphicon-search"></span>&nbsp;</button>
						
												
												
												</div>
												
				</form>
				
				
				
			</div>
			<div class="btn-group buttons" >
				<button id="sample_editable_2_new" class="btn btn-default btn-circle mar-r-10 btn-changeTank" onclick="goodsManager.changeTank()" type="button">
					<span class="fa fa-plus"></span><span class="text">货体换罐</span>
				</button>
				<button id="sample_editable_2_new" class="btn btn-default btn-circle mar-r-10 btn-changeTank" onclick="goodsManager.goodsLoss()" type="button">
					<span class="fa fa-plus"></span><span class="text">货体扣损</span>
				</button>
				
				 <button id="sample_editable_2_new" class="btn btn-default btn-circle mar-r-10 btn-changeTank" onclick="goodsManager.sqlit()" type="button">
					<span class="fa fa-plus"></span><span class="text">货体拆分</span>
				</button> 
				
				<button class="btn btn-default btn-circle mar-r-10 show" type="button">
					<span class="text" id="showText">显示全部</span>
				</button><span class="text" id="sText">当前显示未清盘</span>
					
				
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