<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
				<li><a href="#">预报计划</a></li>
			</ul>
			<!-- END PAGE TITLE & BREADCRUMB-->
		</div>
	</div>
	<div class="portlet box grey">
		<div class="portlet-title">
			<div class="caption">预报计划详情信息</div>
		</div>
		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="#" class="form-horizontal">
				<div class="form-body">
					<table width="100%">
						<tr>
							<td colspan="2"><h4 class="form-section">预报基本信息</h4></td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">计划单号<span class="required">*</span></label>
									<div class="col-md-8">
										<input type="text" name="name" data-required="1" class="form-control" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">贸易性质<span class="required">*</span></label>
									<div class="col-md-8">
										<select class="form-control" name="category">
											<option value="">选择贸易方式...</option>
											<option value="Category 1">内贸</option>
											<option value="Category 2">外贸</option>
										</select>
									</div>
								</div>
							</td>
						</tr>

						<tr>
							<td colspan="2"><h4 class="form-section">船舶基本资料</h4>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">船名<span class="required">*</span></label>
									<div class="col-md-8">
										<select class="form-control" name="category">
											<option value="">选择船名...</option>
											<option value="Category 1">蛟龙号</option>
										</select>
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">中文名称<span class="required">*</span></label>
									<div class="col-md-8">
										<input readonly type="text" name="name" data-required="1" class="form-control" />
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">预计到港<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-group input-medium date date-picker" data-date-viewmode="years" data-date-format="dd-mm-yyyy" data-date="12-02-2012">
											<input class="form-control" type="text" readonly=""> <span class="input-group-btn">
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
									<label class="control-label col-md-4">装货港<span class="required">*</span></label>
									<div class="col-md-8">
										<input type="text" name="name" data-required="1" class="form-control" />
									</div>
								</div>
							</td>
						</tr>

						<tr>
							<td colspan="2"><h4 class="form-section">其他信息</h4></td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">制单人<span class="required">*</span></label>
									<div class="col-md-8">
										<input readonly type="text" name="name" data-required="1" class="form-control" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">制单时间<span class="required">*</span></label>
									<div class="col-md-8">
										<input readonly type="text" name="name" data-required="1" class="form-control" />
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div class="form-group">
									<label class="col-md-2 control-label">备注</label>
									<div class="col-md-10">
										<textarea class="form-control" rows="3"></textarea>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div class="portlet box light-grey">
									<div class="portlet-title">
										<div class="caption">
											<i class="icon-edit"></i>客户信息
										</div>
										<div class="tools">
											<a href="javascript:;" class="expand"></a>
										</div>
									</div>
									<div class="portlet-body" style="display: none;">
										<div class="table-toolbar">
											<div class="btn-group">
												<a href="/sop/resources/pages/inbound/addCustomer.jsp" data-target="#ajax" data-toggle="modal">
													<button id="sample_editable_1_new" class="btn blue">
														<i class="icon-plus"></i>添加
													</button>
												</a>
											</div>
										</div>
										<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
											<thead>
												<tr>
													<th>货主</th>
													<th>货物编码</th>
													<th>货物名称</th>
													<th>计划数量(吨)</th>
													<th>备注</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td>alex</td>
													<td>Alex Nilson</td>
													<td>1234</td>
													<td class="center">power user</td>
													<td>是多大损失</td>
													<td>
														<a href="/sop/resources/pages/inbound_forecast/addCustomer.jsp" class="blue" data-target="#ajax" data-toggle="modal">
																<i class="icon-edit"></i>修改
														</a>
														<a href="javascript:void(0)" class="blue">
															<i class="icon-remove"></i>删除
														</a>
													</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</td>
						</tr>
					</table>
				</div>
				<div class="form-actions fluid">
					<div class="col-md-offset-3 col-md-9">
						<button type="submit" class="btn green">提交</button>
						<button type="button" class="btn default" onclick="openMenu(this,'<%=basePath%>resources/pages/inbound_forecast/list.jsp',0)">返回</button>
					</div>
				</div>
			</form>
			<!-- END FORM-->
		</div>
	</div>
	<!-- END PAGE -->
	<div class="modal fade" id="ajax" tabindex="-1" role="basic" aria-hidden="true">
		<img src="resources/img/ajax-modal-loading.gif" alt="" class="loading">
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
		});
	</script>
</body>