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
</head>
<body>
	<input type="hidden"  class="cargoId" />
	<!-- BEGIN PAGE HEADER-->
	
	<div class="portlet ">
		<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-indent"></i>货批详情信息
				</div>
		</div>
		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form class="form-horizontal">
				<div class="form-body">
					
					
			<div class="portlet box blue" style="border-color: #777">
							<div class="portlet-title" style="background-color: #777">
								<div class="caption">
									<i class="icon-edit"></i>货批内货体信息
								</div>
								<div class="tools">
									<a href="javascript:;" class="collapse"></a>
								</div>
							</div>
							<div class="portlet-body">
							<div class="table-toolbar">
							<div class="btn-group buttons">

				<shiro:hasPermission name="ASPLITGOODS">
			<button id="sample_editable_2_new" class="btn btn-default btn-circle mar-r-10 btn-sqlit" onclick="goodsManager.sqlit()" style="display: none" type="button">
					<span class="fa fa-plus"></span><span class="text">货体拆分</span>
				</button>
				</shiro:hasPermission>
				<shiro:hasPermission name="AGOODSTANKCHANGE">
				<button id="sample_editable_2_new" class="btn btn-default btn-circle mar-r-10 btn-sqlit" onclick="goodsManager.changeTank()" type="button">
					<span class="fa fa-plus"></span><span class="text">货体换罐</span>
				</button>
				</shiro:hasPermission>
				<shiro:hasPermission name="ALADINGKOUSUN">
				<button id="sample_editable_2_new" class="btn btn-default btn-circle mar-r-10 btn-loss" onclick="goodsManager.goodsLoss()" type="button">
					<span class="fa fa-minus"></span><span class="text">货体扣损</span>
				</button>
				</shiro:hasPermission>
				
				
			</div>
			</div>
								<table class="table table-striped table-hover table-bordered" id="goods-table-storage">
									<thead>
										<tr>
										<th style="width1: 8px;"><input type="checkbox" class="group-checkable" data-set="#goods-table-storage .checkboxes" /></th>
											<th style="text-align: center">货体编号</th>
											<th style="text-align: center">货体总量</th>
											<th style="text-align: center">罐号</th>
											<th style="text-align: center">罐检数(吨)</th>
											<th style="text-align: center">商检数(吨)</th>
											<th style="text-align: center">放行数(吨)</th>
											<th style="text-align: center">调出数(吨)</th>
											<th style="text-align: center">发货数(吨)</th>
											<th style="text-align: center">现存数(吨)</th>
											<th style="text-align: center">说明</th>
										</tr>
									</thead>
									<tbody>

									</tbody>
								</table>
							</div>
						</div>
					<div class="modal-footer ">
						<div class="col-md-offset-3 col-md-9">
							<a href="#/goodsManager" class="btn btn-default">返回</a>

						</div>
					</div>
				</div>
			</form>
			<!-- END FORM-->
		</div>
	</div>
	
	<!-- END PAGE -->
	<div class="modal fade" id="ajax" tabindex="-1" role="basic" aria-hidden="true">
	</div>
	<!-- 页面内容开结束-->

</body>