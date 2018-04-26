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
				<li><a href="#">订单意向</a></li>
			</ul>
			<!-- END PAGE TITLE & BREADCRUMB-->
		</div>
	</div>
	<div class="portlet box grey">
		<div class="portlet-title">
			<div class="caption">查看订单意向信息</div>
		</div>
		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form class="form-horizontal">
				<div class="form-body">
					<table width="100%">
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">意向编号:</label>
									<div class="col-md-8">
										<label class="control-label">${intent.code }</label>
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">标题:</label>
									<div class="col-md-8">
										<label class="control-label">${intent.title }</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">合同类型:</label>
									<div class="col-md-8">
										<label class="control-label">${intent.typeName }</label>
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">客户:</label>
									<div class="col-md-8">
										<label class="control-label">${intent.clientName }</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">货品:</label>
									<div class="col-md-8">
										<label class="control-label">${intent.productName }</label>
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">预计数量:</label>
									<div class="col-md-8">
										<label class="control-label">${intent.quantity }</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">预计单价:</label>
									<div class="col-md-8">
										<label class="control-label">${intent.unitPrice }</label>
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">预计总金额:</label>
									<div class="col-md-8">
										<label class="control-label">${intent.totalPrice }</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">创建时间:</label>
									<div class="col-md-8">
										<label class="control-label">${intent.mCreateTime }</label>
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">最后修改时间:</label>
									<div class="col-md-8">
										<label class="control-label">${intent.mEditTime }</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div class="form-group">
									<label class="col-md-2 control-label">备注:</label>
									<div class="col-md-10">
										<label class="control-label">${intent.description }</label>
									</div>
								</div>
							</td>
						</tr>
					</table>
				</div>
					<div class="modal-footer">
						<a href="#/instent" class="btn btn-default">返回</a>
						<a href="#/instent" class="btn btn-success">修改{{id}}</a>
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
			
		});
	</script>
</body>