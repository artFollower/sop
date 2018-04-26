<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<head>
<style type="text/css">
#goodsTable > thead > tr > th,#goodsTable > thead > tr > td, #goodsTable > tbody > tr > td {
    border-top: 1px solid #ddd;
    line-height: 1.42857;
    padding: 8px;
    text-align:center;
    vertical-align: middle;
}
</style>
</head>
<body>
	<div class="portlet box grey">
		<div class="portlet-body outBoundForm">
		   <label class="col-md-8 hidden feeKey" id="outBoundPlanId"></label>
			<div class="form-group"><h4 class="form-section" >基本信息</h4></div>
			<form class="form-horizontal planInfo plan-info-form">
				<div class="form-body">
								<div class="form-group">
								
								 <div class="col-md-6">
									<label class="control-label col-md-4">船名(英)<span
										class="required">*</span></label>
									<div class="col-md-6 input-group shipId" style="float: left;">
									<input type="text" id="shipId" data-required="1" data-type="Require"  class="form-control" />
									<div class="input-group-btn" style="padding-left:0px;padding-right:0px;">
			                         <button class="btn btn-primary" id="shipInfo" onclick="shipArrival.showShipInfo();" style="display: none;">查看船舶基本信息</button>
			                         <button class="btn btn-primary" id="addShipInfo" onclick="BaseInfo.openShipDialog(null);">添加船舶基本信息</button>
			                        </div>
									</div>
									<div class="col-md-2">
										<div class="checkbox" style="float: right;">
											<label class="checkbox-inline"> <input type="checkbox"
												id="isPassShip" value="option2">通过船
											</label>
										</div>
									</div>
								</div>
									
									<div class="col-md-6">
									<label class="control-label col-md-4">船名(中)<span
										class="required">*</span></label>
									<div class="col-md-6" id="shipRef">
									<input type="text" id="shipRefId" data-required="1" data-type="Require"   class="form-control" /> 
									</div>
									</div>
									
								</div>
								
								<div class="form-group">
								
								<div class="col-md-6">
									<label class="control-label col-md-4">预计到港区间<span
										class="required">*</span></label>
										<div class="col-md-6 input-group date-picker input-daterange"
											data-date-format="yyyy-mm-dd">
											<input type="text" class="form-control arrivalTime"
												name="from" id="startTime" data-required="1" data-type="Require" > <span
												class="input-group-addon">到</span> <input type="text"
												class="form-control endTime" name="to" id="endTime" data-required="1" data-type="Require" >
										</div>
									</div>
									
									<div class="col-md-6">
									<label class="control-label col-md-4">货品<span
										class="required" >*</span></label>
									<div class="col-md-6">
									<input type="text" id="productId" data-required="1" data-type="Require" class="form-control" /> 
									</div>
								</div>
								
								</div>
								
								<div class="form-group">
									<label class="col-md-2 control-label">备注</label>
									<div class="col-md-9">
										<textarea class="form-control " id="description" rows="1"></textarea>
									</div>
								</div>
				</div>
			</form>
		</div>
	</div>
	
	<div class="portlet box blue" style="border-color: #777;">
		<div class="portlet-title" style="background-color: #777;">
			<div class="caption">
				<i class="icon-edit"></i>货体信息
			</div>
		</div>
		<div class="portlet-body">
			<div class="table-toolbar">
			<shiro:hasPermission name="AOUTBOUNDITEMADD">
				<div class="btn-group">
						<button onclick="shipArrival.addItemArrial();" id="goodsAdd" class="btn btn-default btn-circle mar-r-10">
							<span class="fa fa-plus"></span>添加
						</button>
				</div>
				</shiro:hasPermission>
			</div>
			<div style="min-height: 150px; width: 100%; overflow-y: auto;" class="grid-body">
			<div class="table-scrollable" >
						<table id="goodsTable" style="margin: 0px; border: 0px;" class="table table-responsive table-bordered table-hover">
						<thead class="grid-table-head" style="width: 100%;">
						<tr>
							<th style="text-align:center;width:170px;" index="0">提货单位<div class="colResize"></div></th>
							<th style="text-align:center;" index="1">贸易类型<div class="colResize"></div></th>
							<th style="text-align:center;width:170px;" index="2">货主单位<div class="colResize"></div></th>
							<th style="text-align:center;" index="3">货体号<div class="colResize"></div></th>
							<th style="text-align:center;" index="4">货批号<div class="colResize"></div></th>
							<th style="text-align:center;width:170px;" index="5">入库船信<div class="colResize"></div></th>
							<th style="text-align:center;" index="6">报关单位<div class="colResize"></div></th>
							<th style="text-align:center;width:100px;" index="7">罐号<div class="colResize"></div></th>
							<th style="text-align:center;" index="8">开票提单号<div class="colResize"></div></th>
							<th style="text-align:center;" index="9">计划量(吨)<div class="colResize"></div></th>
							<th style="text-align:center;" index="10">是否核销<div class="colResize"></div></th>
							<th style="text-align:center;" index="11">流向<div class="colResize"></div></th>
							<th width="auto" style="text-align:center;" index="12">操作<div class="colResize"></div></th>
							</tr></thead>
							<tbody class="grid-table-body" style="width:100%; height: auto; position: relative;">
							</tbody>
							</table>
							</div>
							</div>
		</div>
	</div>
	<div class="portlet box blue  hasApproveDiv" style="border-color: #777;display:none">
		<div class="portlet-title" style="background-color: #777;">
			<div class="caption">
				<i class="icon-edit"></i>已审批货体信息
			</div>
		</div>
		<div class="portlet-body">
		<div style="min-height: 150px; width: 100%; overflow-y: auto;" class="grid-body">
		<div class="table-scrollable" >
						<table id="hasApproveGoodsTable" style="margin: 0px; border: 0px;" class="table table-responsive table-bordered table-hover">
						<thead class="grid-table-head" style="width: 100%;">
						<tr>
							<th style="text-align:center;width:170px;" index="0">提货单位<div class="colResize"></div></th>
							<th style="text-align:center;" index="1">贸易类型<div class="colResize"></div></th>
							<th style="text-align:center;width:170px;" index="2">货主单位<div class="colResize"></div></th>
							<th style="text-align:center;" index="3">货体号<div class="colResize"></div></th>
							<th style="text-align:center;" index="4">货批号<div class="colResize"></div></th>
							<th style="text-align:center;width:170px;" index="5">入库船信<div class="colResize"></div></th>
							<th style="text-align:center;" index="6">报关单位<div class="colResize"></div></th>
							<th style="text-align:center;width:100px;" index="7">罐号<div class="colResize"></div></th>
							<th style="text-align:center;" index="8">开票提单号<div class="colResize"></div></th>
							<th style="text-align:center;" index="9">计划量(吨)<div class="colResize"></div></th>
							<th style="text-align:center;" index="10">是否核销<div class="colResize"></div></th>
							<th style="text-align:center;" index="11">流向<div class="colResize"></div></th>
							<th width="auto" style="text-align:center;" index="12">操作<div class="colResize"></div></th>
							</tr></thead>
							<tbody class="grid-table-body" style="width:100%; height: auto; position: relative;">
							</tbody>
							</table></div>
							<div class="col-md-12 form-group totalOutboundDiv" style="margin-left: 0px; margin-right: 0px;"></div>
							</div>
		</div>
	</div>
	<div class="portlet-body "  >
				  <div class="form-group createDiv" style="margin-top:20px;margin-bottom:20px;display:none;">
				  <div class="col-md-6">
				  <label class="control-label col-md-4" style="text-align:right;" >制定人：</label>
				  <label class="control-label col-md-4" style="text-align:left;" id="createUserId"></label>
				  </div>
				   <div class="col-md-6">
				  <label class="control-label col-md-4"  style="text-align:right;" >制定时间：</label>
				  <label class="control-label col-md-4" style="text-align:left;" id="createTime"></label>
				  </div>
				  </div>
				  <div class="form-group reviewDiv" style="margin-top:20px;margin-bottom:20px;display:none;">
				  <div class="col-md-6">
				  <label class="control-label col-md-4"  style="text-align:right;">审核人：</label>
				  <label class="control-label col-md-4" style="text-align:left;" id="reviewUserId"></label>
				  </div>
				   <div class="col-md-6">
				  <label class="control-label col-md-4"  style="text-align:right;">审核时间：</label>
				  <label class="control-label col-md-4" style="text-align:left;" id="reviewTime"></label>
				  </div>
				  </div>
				  </div>
	<div class="modal-footer">
		<div class="col-md-offset-3 col-md-9">
			<a type="button" href="#/shipArrival" class="btn btn-default">返回</a>
			<shiro:hasPermission name="AOUTBOUNDSUBMIT">
			<button type="button" class="btn btn-primary save"   key="0" id="planSave">保存</button>
			<button type="button" class="btn btn-primary save" key="1" id="planSubmit">提交</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="AOUTBOUNDVERIFY">
			<button type="button" class="btn btn-primary save" style="display:none;" key="50" id="planPass">通过</button>
			<button type="button" class="btn btn-primary save" style="display:none;" key="3" id="planUnPass">不通过</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="AOUTBOUNDSUBMIT">
			<button type="button" class="btn btn-primary" style="display:none;" id="planBack">回退</button>
			</shiro:hasPermission>
		</div>
	</div>

</body>