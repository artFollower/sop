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
	<div class="row">
		<div class="col-md-12">
			<!-- BEGIN PAGE TITLE & BREADCRUMB-->
			<ul class="page-breadcrumb breadcrumb">
				<li><i class="icon-home"></i> <a href="index.html">控制台</a> <i class="icon-angle-right"></i></li>
				<li><a href="#">提单</a></li>
			</ul>
			<!-- END PAGE TITLE & BREADCRUMB-->
		</div>
	</div>
	<div class="portlet box grey">
		<div class="portlet-title">
			<div class="caption">编辑提单信息</div>
		</div>
		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form class="form-horizontal">
				<div class="form-body">
					<table width="100%">
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">提单类型<span class="required">*</span></label>
									<div class="col-md-8">
										<c:choose>
										<c:when test="${ladingMsg.type == 1 }">
											<input readonly type="text" id="type" data-required="1" class="form-control intentTitle" value="转卖提单" data="${ladingMsg.type}"/>
										</c:when>
										<c:otherwise>
											<input readonly type="text" id="type" data-required="1" class="form-control intentTitle" value="发货提单" data="${ladingMsg.type}"/>
										</c:otherwise>
										</c:choose>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">客户提单号<span class="required">*</span></label>
									<div class="col-md-8">
										<input readonly type="text" id="code" data-required="1" class="form-control intentTitle" value="${ladingMsg.code}"/>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">客户名称<span class="required">*</span></label>
									<div class="col-md-8">
										<input readonly type="text" id="custName" data-required="1" class="form-control intentTitle" value="${ladingMsg.clientName}" data="${ladingMsg.clientId}"/>
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">接收单位<span class="required">*</span></label>
									<div class="col-md-8">
										<input readonly type="text" id="reviveCustName" data-required="1" class="form-control intentTitle" value="${ladingMsg.reviewClientName}" data="${ladingMsg.reviewClientId}"/>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">货品名称<span class="required">*</span></label>
									<div class="col-md-8">
										<input readonly type="text" id="productName" data-required="1" class="form-control intentTitle" value="${ladingMsg.productName}" data="${ladingMsg.productId}"/>
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">提单数量(吨)<span class="required">*</span></label>
									<div class="col-md-8">
										<input readonly type="text" id="totalCount" data-required="1" class="form-control intentTitle" value="${ladingMsg.goodsTotal}"/>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">放行数量(吨)<span class="required">*</span></label>
									<div class="col-md-8">
										<input type="text" id="passCount" data-required="1" class="form-control intentTitle" value="${ladingMsg.goodsPass}"/>
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">发货数量(吨)<span class="required">*</span></label>
									<div class="col-md-8">
										<input type="text" data-required="1" class="form-control quantity" id="deliveryCount" value="${ladingMsg.goodsDelivery}"/>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">有效开始时间<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-group date date-picker" data-date-viewmode="years" data-date-format="yyyy-mm-dd" data-date="2014-12-01">
											<input class="form-control signDate" type="text" readonly="" id="startTime" data-required="1" value="${ladingMsg.startTime}"> <span class="input-group-btn">
												<button class="btn default" type="button">
													<i class="icon-calendar"></i>
												</button>
											</span>
										</div>
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">有效结束时间<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-group date date-picker" data-date-viewmode="years" data-date-format="yyyy-mm-dd" data-date="2014-12-01">
											<input class="form-control signDate" type="text" readonly="" id="endTime" data-required="1" value="${ladingMsg.endTime}"> <span class="input-group-btn">
												<button class="btn default" type="button">
													<i class="icon-calendar"></i>
												</button>
											</span>
										</div>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">提单状态<span class="required">*</span></label>
									<div class="col-md-8">
										<select class="form-control select2me type" id="status" data-required="1" data-placeholder="选择提单状态...">
											<option value="-1">请选择提单状态...</option>
											<c:choose>
											<c:when test="${ladingMsg.status == 0}">
												<option value="0" selected>锁定</option>
											</c:when>
											<c:otherwise>
												<option value="0">锁定</option>
											</c:otherwise>
											</c:choose>
											<c:choose>
											<c:when test="${ladingMsg.status == 1}">
												<option value="1" selected>激活</option>
											</c:when>
											<c:otherwise>
												<option value="1">激活</option>
											</c:otherwise>
											</c:choose>
											<c:choose>
											<c:when test="${ladingMsg.status == 2}">
												<option value="2" selected>终止</option>
											</c:when>
											<c:otherwise>
												<option value="2">终止</option>
											</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
							</td>
							
						</tr>
						<tr>
							<td colspan="2">
								<div class="form-group">
									<label class="col-md-2 control-label">备注</label>
									<div class="col-md-10">
										<textarea class="form-control description" rows="3" id="description">${ladingMsg.description}</textarea>
									</div>
								</div>
							</td>
						</tr>
					</table>
				</div>
				<div class="portlet box yellow">
					<div class="portlet-title">
						<div class="caption">
							<i class="icon-edit"></i>货体列表
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"></a>
						</div>
					</div>
					<div class="portlet-body">
						<div class="table-toolbar">
							<div class="btn-group">
								<a href="javascript:void(0);" onclick="openModal('<%=basePath%>lading/initEdit',0)" data-target="#ajax" data-toggle="modal" id="goodsAdd">
									<button id="sample_editable_1_new" class="btn blue">
										<i class="icon-plus"></i>编辑
									</button>
								</a>
							</div>
						</div>
						<table class="table table-striped table-hover table-bordered" id="goods-table">
							<thead>
								<tr>
									<th>货体代码</th>
									<th>货主</th>
									<th>货体名称</th>
									<th>当前存量(吨)</th>
									<th>加入提单数量(吨)</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<input id="goodsGroupId" type="hidden" data="${ladingMsg.goodsGroupId}" />
								<c:forEach var="goodsInfo" items="${ladingMsg.goodsList}" begin="0">
								<tr>
									<input id="goodsId" type="hidden" data="${goodsInfo.id}" />
									<td id="code" data="${goodsInfo.code}">${goodsInfo.code}</td>
									<td id="productName" data="${goodsInfo.productName}">${goodsInfo.productName}</td>
									<td id="custName" data="${goodsInfo.clientId}">${goodsInfo.clientName}</td>
									<td id="goodsCurrent" data="${goodsInfo.goodsCurrent}">${goodsInfo.goodsCurrent}</td>
									<td id="goodsInLading" data="${goodsInfo.goodsCurrent}">${goodsInfo.goodsCurrent}</td>
									<td><a href='javascript:void(0)' class='blue' onclick='removeGood(this)'> <i class='icon-remove'></i>删除</a>&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="openModal('<%=basePath%>lading/show?id=${goodsInfo.id}',0)" data-target="#ajax" data-toggle="modal" id="goodsShow"><i class='icon-eye-open'></i>查看
								</a></td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<div class="form-actions fluid">
					<div class="col-md-offset-3 col-md-9">
						<input type="hidden" value="${ladingMsg.id }" id="inputId">
						<button type="button" class="btn green saveButton">保存</button>
						<button type="button" class="btn default" onclick="openMenu(this,'<%=basePath%>lading/list',0)">返回</button>
					</div>
				</div>
			</form>
			<!-- END FORM-->
		</div>
	</div>
	<!-- END PAGE -->
	<div class="modal fade" id="ajax" tabindex="-1" role="basic" aria-hidden="true">
		<img src="<%=basePath%>resources/admin/img/ajax-modal-loading.gif" alt="" class="loading">
	</div>
	<!-- 页面内容开结束-->
	<script type="text/javascript">
		$(function() {
			if (jQuery().datepicker) {
				$('.date-picker').datepicker({
					rtl : App.isRTL(),
					autoclose : true
				});
				$('body').removeClass("modal-open"); // fix bug when inline picker is used in modal
			}
			$(".saveButton").click(function() {
				var isOk = true;
				$(".form-horizontal").find("input").each(function() {
					if($(this).attr("data-required") == 1) {
						if($(this).val() == null || $(this).val() == "") {
							$(this).pulsate({
				                color: "#bf1c56",
				                repeat: false
				            });
							isOk = false;
							return;
						}
					}
				});
				$(".form-horizontal").find("select").each(function() {
					if($(this).attr("data-required") == 1) {
						if($(this).val() == null || $(this).val() == "-1") {
							$(this).pulsate({
				                color: "#bf1c56",
				                repeat: false
				            });
							isOk = false;
							return;
						}
					}
				});
				var goodsIdList="";
				var goodsCount="";
				var goodsGroupId = $("#goodsGroupId").attr("data");
				$("#goods-table").children("tbody").children("tr").each(function() {
					if(goodsIdList == ""){
						goodsIdList += $(this).find("#goodsId").attr("data"); 
					}else{
						goodsIdList += ","+$(this).find("#goodsId").attr("data"); 
					}
					if(goodsCount == ""){
						goodsCount += $(this).find("#goodsInLading").attr("data");
					}else{
						goodsCount += ","+$(this).find("#goodsInLading").attr("data"); 
					}
				});
				if(isOk) {
					$.ajax({
						type : "post",
						url : settings.base.domain+"lading/update",
						data : {
							"id" : $("#inputId").val(),
							"code" : $("#code").attr("data"),
							"clientId" : $("#custName").attr("data"),
							"type" : $("#type").attr("data"),
							"goodsTotal" : $("#totalCount").val(),
							"goodsPass" : $("#passCount").val(),
							"goodsDelivery" : $("#deliveryCount").val(),
							"stTime" : $("#startTime").val(),
							"edTime" : $("#endTime").val(),
							"status"  : $("#status").val(),
							"description" : $("#description").val(),
							"productId" : $("#productName").attr("data"),
							"receiveClientId":$("#reviveCustName").attr("data"),
							"goodsIdList": goodsIdList,
							"goodGroupId" : goodsGroupId,
							"goodsCount" : goodsCount
						},
						// data:JSON.stringify(sendGroup),
						dataType : "json",
						success : function(data) {
							if (data.code == "0000") {
								alert("提单修改成功");
								openMenu(null,"<%=basePath%>lading/list?currentPage=1&pageSize=50",0);
							}else {
								alert("提单修改失败");
							}
						}
					});
				}
			});
		});
		function removeGood(obj) {
			var trObj = $(obj).parents("tr");
			$(trObj).remove();
		}
	</script>
</body>
</html>