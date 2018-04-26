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
					<table width="100%">
						<tr>
							<td colspan="2"><h4 class="form-section">基本信息</h4></td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">货批编号:</label>
									<div class="col-md-8">
										<input readonly type="text" name="name" class="form-control cargoCode"  />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">货主:</label>
									<div class="col-md-8">
										<input readonly type="text" name="name" class="form-control clientName"  />
									</div>
								</div>
							</td>
						</tr>
						
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">货批船信:</label>
									<div class="col-md-8">
										<input readonly type="text" name="name" class="form-control arrivalInfo" />
									</div>
								</div>
							</td>
							
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">合同损耗率(‰):</label>
									<div class="col-md-8">
										<input readonly type="text" name="name" class="form-control lossRate" />
									</div>
								</div>
							</td>
							
						</tr>
						
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">货品名称:</label>
									<div class="col-md-8">
										<input readonly type="text" name="name" class="form-control goodsName" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">罐检数(吨):</label>
									<div class="col-md-8">
										<input type="text" size="16" readonly class="form-control cargoGoodsTank" /> 
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">商检数(吨):</label>
									<div class="col-md-8">
										<input readonly type="text" name="name" class="form-control cargoGoodsInspect"  />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">内控放行数(吨):</label>
									<div class="col-md-8">
										<input type="text" size="16" readonly class="form-control cargoGoodsInPass" /> 
									</div>
								</div>
							</td>
						</tr>
						 <tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">海运提单号:</label>
									<div class="col-md-8">
										<input  type="text" name="name" class="form-control customLading" id="customLading"/>
									</div>
							
							<div class="col-md-1 " >
							</div>
							
								</div>
							</td>
							
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">海关放行数量:</label>
									<div class="col-md-8">
										<input  type="text" name="name" class="form-control customPassCount" onkeyup="config.clearNoNum(this)" id="customPassCount"/>
									</div>
							
							<div class="col-md-1 " >
							</div>
							
								</div>
							</td>
							
						</tr> 
						
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4" >海关放行日期:</label>
												<div class="col-md-8">
													<div
														class="input-group input-large date-picker input-daterange"
														data-date-format="yyyy-mm-dd">
														<input type="text" class="form-control" name="sCustomLadingTime" 
															id="sCustomLadingTime">
													</div>
												</div>
							
							
								</div>
							</td>
							
							
						</tr> 
						
						
						
						<tr>
						<td class=" col-md-6">
						</td>
						<td class=" col-md-6">
						<div class="form-group">
						<div class="col-md-8 " ></div>
						<div class="col-md-4 " >
						<shiro:hasPermission name="AUPDATEHAIYUN">
								<button type="button"  class="btn btn-primary saveClearance"  >保存</button>
								</shiro:hasPermission>
							<shiro:hasPermission name="APRINTSTORAGE">
								<button type="button"  class="btn btn-primary print"  >打印入库损耗单</button>
								</shiro:hasPermission>
							</div>
							
						</div>
						
						</tr>
						
					</table>
					
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
							<shiro:hasPermission name="AMERGEGOODS">
			<button id="sample_editable_1_new" class="btn btn-default btn-circle mar-r-10 btn-merge" style="display: none" onclick="Storage.merge()" type="button">
					<span class="fa fa-plus"></span><span class="text">货体合并</span>
				</button>
				</shiro:hasPermission>
				<shiro:hasPermission name="ASPLITGOODS">
			<button id="sample_editable_2_new" class="btn btn-default btn-circle mar-r-10 btn-sqlit" onclick="Storage.sqlit()" type="button">
					<span class="fa fa-plus"></span><span class="text">货体拆分</span>
				</button>
				</shiro:hasPermission>
				
				<shiro:hasPermission name="ACONNCETYURUKU">
			<button id="sample_editable_2_new" class="btn btn-default btn-circle mar-r-10 btn-predict" onclick="Storage.predict()" type="button">
					<span class="fa fa-plus"></span><span class="text">预入库关联</span>
				</button>
				</shiro:hasPermission>
				
				
				<shiro:hasPermission name="ACHANGESTORAGE">
			<button id="sample_editable_2_new" class="btn btn-default btn-circle mar-r-10 btn-editGoodsTotal" onclick="Storage.editGoodsTotal()" type="button">
					<span class="fa fa-plus"></span><span class="text">修改入库量</span>
				</button>
				</shiro:hasPermission>
				<shiro:hasPermission name="AGOODSTANKCHANGE">
				<button id="sample_editable_2_new" class="btn btn-default btn-circle mar-r-10 btn-changeTank" onclick="Storage.changeTank()" type="button">
					<span class="fa fa-plus"></span><span class="text">货体换罐</span>
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
											<th style="text-align: center">报关单位</th>
											<th style="text-align: center">罐检数(吨)</th>
											<th style="text-align: center">参考量(吨)</th>
											<th style="text-align: center">商检数(吨)</th>
											<th style="text-align: center">海关入库放行数(吨)</th>
											<th style="text-align: center">海关出库放行数(吨)</th>
											<th style="text-align: center">内控放行数(吨)</th>
											<th style="text-align: center">说明</th>
											<th style="text-align: center">操作</th>
										</tr>
									</thead>
									<tbody>

									</tbody>
								</table>
							</div>
						</div>
					<div class="modal-footer ">
						<div class="col-md-offset-3 col-md-9">
							<a href="#/storage" class="btn btn-default">返回</a>

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