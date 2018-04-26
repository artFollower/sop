<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>
<!-- BEGIN PAGE HEADER-->
	<div class="portlet ">
		<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-yelp"></i>添加提单信息
				</div>
		</div>
		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form class="form-horizontal loading-form">
				<div class="form-body">
					<table width="100%">
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">提单类型<span class="required">*</span></label>
									<div class="col-md-8">
										<select class="form-control select2me type" id="type" onchange="Lading.changeType()" data-required="1" data-type="Require" data-placeholder="选择提单类型...">
												<option value="-1">请选择提单类型...</option>
												<option value="1">转卖提单</option>
												<option value="2">发货提单</option>
										</select>
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">客户提单号<span class="required">*</span></label>
									<div class="col-md-8">
										<input type="text" id="code" maxlength='30' data-required="1" data-type="Require" class="form-control code" />
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">发货单位<span class="required">*</span></label>
									<div class="col-md-6 " id="client">
									<input id="clientId" type="text"  data-provide="typeahead" data-required="1" data-type="Require" class="form-control clientId">
									</div>
									<div class="col-md-2 " >
									<button type="button" onclick="Lading.getClientGoods()"  class="btn btn-primary clientGoods" >查看</button>
									</div>
								</div>
							</td>
							
							<td class=" col-md-6" style="display: none">
								<div class="form-group">
									<label class="control-label col-md-4">海关放行编号</label>
									<div class="col-md-8">
										<input type="text" id="customsPassCode" maxlength='16'  class="form-control customsPassCode" />
									</div>
								</div>
							</td>
							
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">接收单位<span class="required">*</span></label>
									<div class="col-md-6 " id="receiveClient">
									<input id="receiveClientId" type="text"  data-provide="typeahead" data-required="1" data-type="Require" class="form-control receiveClientId">
									</div>
									<div class="col-md-2 " >
									<button type="button" onclick="Lading.addNewClient()" class="btn btn-primary newClient" >新建客户</button>
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">货品名称<span class="required">*</span></label>
									<div class="col-md-8" id="select-product">
										
									<input id="productName"  type="text" data-provide="typeahead" data-required="1" data-type="Require" class="form-control productName">
									</div>
								</div>
							</td>
							
							
						</tr>
						
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">提单数量(吨)<span class="required">*</span></label>
									<div class="col-md-8">
										<input type="text" id="totalCount"  readonly data-required="1" data-type="Require" class="form-control totalCount" />
									</div>
								</div>
							</td>
							<td class=" col-md-6" id="tdStartTime" style="display: none">
								<div class="form-group">
									<label class="control-label col-md-4">仓储费起计日期<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-group date date-picker" data-date-viewmode="years"  data-date-format="yyyy-mm-dd" >
											<input class="form-control signDate"  type="text"   id="startTime" > <span class="input-group-btn">
												<button class="btn default" type="button">
													<i class="icon-calendar"></i>
												</button>
											</span>
										</div>
									</div>
								</div>
							</td>
							<td class=" col-md-6" id="tdEndTime" style="display: none"> 
								<div class="form-group">
									<label class="control-label col-md-4">提单有效期<span style="color: red">*</span></label>
									<div class="col-md-6">
										<div class="input-group date date-picker" data-date-viewmode="years"  data-date-format="yyyy-mm-dd">
											<input class="form-control signDate"  type="text"  id="endTime" > <span class="input-group-btn">
												<button class="btn default" type="button">
													<i class="icon-calendar"></i>
												</button>
											</span>
										</div>
									</div>
									<label class="checkbox-inline" > <input type="checkbox" value="" id="isLong" name="isLong"> 长期
									
								</label> 
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6" colspan="2">
								<div class="form-group">
									<label class="col-md-2 control-label">备注</label>
									<div class="col-md-10">
										<textarea class="form-control description" maxlength='100' rows="3" id="description"></textarea>
									</div>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</form>
			<!-- END FORM-->
	<div class="portlet box blue" style="border-color: #777">
		<div class="portlet-title" style="background-color: #777">
			<div class="caption">
				<i class="icon-edit"></i>来源货体列表
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a>
			</div>
		</div>
		<div class="portlet-body">
			<div class="table-toolbar">
				<div class="btn-group">
					<a href="javascript:void(0)" onclick="Lading.openDialog(1)" style="display: none" id="goodsAdd">
						<button id="sample_editable_1_new" class="btn btn-default btn-circle mar-r-10 btn-add" type="button">
					<span class="fa fa-plus"></span><span class="text">添加</span>
				</button>
					</a>
				</div>
			</div>
			<table class="table table-striped table-hover table-bordered" id="goods-table">
				<thead>
					<tr>
						<th style="text-align: center">货体代码</th>
						<th style="text-align: center">货主</th>
						<th style="text-align: center">货体名称</th>
						<th style="text-align: center">当前存量(吨)
						<th style="text-align: center">加入提单数量(吨)</th>
						<th style="text-align: center">操作</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>
	<div class="modal-footer ">
			<a href="#/lading" class="btn btn-default">返回</a>
			<button type="button" class="btn btn-primary saveButton" >保存</button>
	</div>
	</div>
	</div>
	<!-- END PAGE -->
	<!-- 页面内容开结束-->

</body>
</html>