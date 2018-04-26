<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
</head>
<body>
	<!-- BEGIN PAGE HEADER-->
	<input type="hidden" value="" class="planId" />
	<div class="portlet ">
		<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-indent"></i>添加到港信息
				</div>
		</div>
		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form class="form-horizontal planInfo arrival-update-form">
				<div class="form-body">
					<table width="100%">
						<tr>
							<td colspan="2"><h4 class="form-section">基本信息</h4></td>
						</tr>
						<tr>
							<td class=" col-md-4" >
								<div class="form-group">
									<label class="control-label col-md-4">船英文名<span
										class="required">*</span></label>
									<div class="col-md-8 " id="ship">
									<input id="shipId"  type="text" data-provide="typeahead" data-required="1" data-type="Require" class="form-control shipId">
									</div>
								</div>
							</td>
							
							<td class=" col-md-4">
								<div class="form-group">
									<label class="control-label col-md-4">船名<span
										class="required">*</span></label>
									<div class="col-md-8 " id="shipRef">
									<input id="shipRefId"  type="text" data-provide="typeahead" data-required="1" data-type="Require" class="form-control shipRefId">
									</div>
								</div>
							</td>

							<%-- <td class=" col-md-4">
								<div class="form-group">
									<label class="control-label col-md-4">贸易类型<span
										class="required">*</span></label>
									<div class="col-md-8">
										<select class="form-control tradeType" name="category">
											<c:forEach var="trade" items="${trade.data}" begin="0">
												<option value="<c:out value="${trade.key}"/>"><c:out
														value="${trade.value}" /></option>
											</c:forEach>
										</select>
									</div>
								</div>
							</td> --%>
							
						</tr>
						
						<tr>
						<td class=" col-md-4">
								<div class="form-group">
									<label class="control-label col-md-4">预计到港区间<span
										class="required">*</span></label>
									<div class="col-md-8">
										<div
											class="input-group date-picker input-daterange"
											data-date-format="yyyy-mm-dd">
											<input type="text" data-required="1"  data-type="Require" class="form-control arrivalTime"
												name="from" id="arrivalTime">  <input  type="text" 
												class="form-control endTime" style="display: none" name="to" id="endTime">
										</div>
									</div>
								</div>
							</td>
							<td class=" col-md-4">
								<div class="form-group">
								<div class="col-md-4"></div>
								<div class="col-md-8">
									<button type="button" style="display: none" onclick="Arrival.shipInfoDialog()" class="btn btn-primary shipInfo">查看船舶基本信息</button>
									<label class="checkbox-inline"> <input type="checkbox" value="" id="isPassShip" name="isPassShip"> 通过船
								</label> 
								
								<label style="display: none" id='isPassShow'>通过船</label>
								
								</div>
								</div>
							</td>
							
						</tr>
						
						<tr >
						<td class=" col-md-4">
						<div class="form-group">
						<div class="col-md-4"></div>
						<div class="col-md-8"> 
								<!-- <label class="checkbox-inline"> <input type="checkbox" value="" id="isDeclareCustom" name="isDeclareCustom"> 申报海关
								</label> --> 
								<label class="checkbox-inline"> <input type="checkbox" value="" id="isCustomAgree" name="isCustomAgree"> 海关同意卸货
									</label>
									</div>
						</div>
						</td>
						</tr>
						
						<tr style="display: none">
							<td class=" col-md-4">
								<div class="form-group">
									<label class="control-label col-md-4">海运提单号</label>
									<div class="col-md-8">
										<input type="text" maxlength="32" class="form-control customLading" name="customLading" />
									</div>
								</div>
							</td>
							<td class=" col-md-4">
								<div class="form-group">
									<label class="control-label col-md-4">海运提单数量(吨)</label>
									<div class="col-md-8">
										<input type="text" maxlength="10" class="form-control customLadingCount" onkeyup="config.clearNoNum(this)" name="customLadingCount"  />
									</div>
								</div>
							</td>
						</tr>
						
						<tr >
							<td class=" col-md-4">
								<div class="form-group">
									<label class="control-label col-md-4">产地</label>
									<div class="col-md-8">
										<input type="text" maxlength="16" class="form-control originalArea" name="originalArea" />
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-12 " colspan="2">
								<div class="form-group">
									<label class="col-md-2 control-label">备注</label>
									<div class="col-md-10">
										<textarea maxlength="100" class="form-control description" rows="2"></textarea>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2"></td>
						</tr>
					</table>
				</div>
			</form>
			<!-- END FORM-->
		</div>
	</div>
	<div class="portlet box blue" style="border-color: #777">
		<div class="portlet-title" style="background-color: #777">
			<div class="caption">
				<i class="icon-edit"></i>货批信息
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a>
			</div>
		</div>
		<div class="portlet-body">
			<div class="table-toolbar">
				<div class="btn-group">
				<shiro:hasPermission name="AARRIVALPLANADD">
					<a href="javascript:void(0)"
						onclick="Arrival.doOpen(1)"
						id="goodsAdd">
						<button id="sample_editable_1_new" class="btn btn-default btn-circle mar-r-10 btn-add" type="button">
					<span class="fa fa-plus"></span><span class="text">添加</span>
				</button>
						
					</a>
					</shiro:hasPermission>
				</div>
			</div>
			<table class="table table-striped table-hover table-bordered"
				id="goods-table">
				<thead>
					<tr>
						<th style="text-align: center">货主</th>
						<th style="text-align: center">货品</th>
						<th style="text-align: center">贸易类型</th>
						<th style="text-align: center">计划数量(吨)</th>
						<th style="text-align: center">申报海关</th>
						<!-- <th style="text-align: center">海关同意卸货</th> -->
						<th style="text-align: center">海运提单号</th>
						<th style="text-align: center">海运提单数量</th>
						<th style="text-align: center">接卸要求</th>
						<th style="text-align: center">操作</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<h4 class="form-section">统计</h4>
			<table class="table table-striped table-hover table-bordered"
				id="total-table">
				<thead>
					<tr>
						<th style="text-align: center">货品</th>
						<th style="text-align: center">总量(吨)</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>


	<div class="modal-footer">
		<div class="col-md-offset-3 col-md-9">
			<a href="#/arrival" class="btn btn-default">返回</a>
			<shiro:hasPermission name="AARRIVALPLANADD">
			<button type="button" class="btn btn-primary planSubmit">提交</button>
			</shiro:hasPermission>
		</div>
	</div>

	<!-- END PAGE -->
</body>