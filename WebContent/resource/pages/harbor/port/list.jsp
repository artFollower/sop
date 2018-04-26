<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<!-- BEGIN MAIN CONTENT -->
<div class="row">
	<div class="col-md-12">
		<!-- Begin: life time stats -->
		<div class="portlet">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-shopping-cart"></i>船港货动态信息列表
				</div>
				<div class="actions hidden">
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
			<!-- <div class="row">
				<div class="search-form-default col-md-12 col-sm-12" id="queryUserDivId">
					<form name="harborForm" target="_self" class="form-horizontal searchCondition">
						<table border="0" cellspacing="0" cellpadding="0" width="100%" style="margin-left:15px;margin-left:15px;">
						
						 <tr>
						    <td>
					          	<div class="form-group">
						         <label class="control-label" style="float: left;text-align:right;">英文船名:</label>
									<div style="margin-left: 15px; float: left;">
										<input name="shipName" class="form-control" type="text" style="width: 180px;" />
									</div>
									<label class="control-label" style="float: left;">类型:</label>
									<div style="margin-left: 15px; float: left;">
										<select name="fileDescription" class="form-control">
											<option value="">请选择</option>
											<option value="DISCHARGE REPORT">卸货</option>
											<option value="LOAD REPORT">装货</option>
										</select>
									</div>
									<label class="control-label" style="float: left;">状态:</label>
									<div style="margin-left: 15px; float: left;">
										<select name="fileDescription" class="form-control">
											<option value="">请选择</option>
											<option value="0">未发送</option>
											<option value="1">已发送</option>
											<option value="2">发送失败</option>
										</select>
									</div>
									<label class="control-label" style="float: left;">到港时间:</label>
									<div class="col-md-5">
										<div class="input-group input-large date-picker input-daterange" data-date-format="yyyy-mm-dd">
											<input type="text" class="form-control" name="startTime" id="startTime" id="startTime"> <span class="input-group-addon">到</span> <input type="text" class="form-control" name="endTime" name="endTime" id="endTime">
										</div>
									</div>
									</div>
					            </td>
						       <td style="vertical-align: bottom;">
						       	<button id="managerSearch" type="button" style="position:relative; margin-left:35px; top: -15px" class="btn btn-success glyphicon glyphicon-search"></button>
						       </td>
						  </tr>
						</table>
					</form>
				</div>
			</div> -->
			<div class="row">
				<div class="btn-group buttons col-md-6 col-sm-6">
					<button class="btn btn-default btn-circle mar-r-10 btn-add-bulk" type="button">
						<span class="fa fa-plus"></span><span class="text">散货</span>
					</button>
					<!-- 
					<button class="btn btn-default btn-circle mar-r-10 btn-add-container" type="button">
						<span class="fa fa-plus"></span><span class="text">集装箱</span>
					</button> -->
				</div>
			</div>
			<div data-role="portGrid"></div>
		</div>
		<!-- End: life time stats -->
	</div>
</div>
<!-- END MAIN CONTENT -->