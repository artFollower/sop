<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<head>
<style type="text/css">
#checkAmountTable > thead > tr > th,#checkAmountTable > thead > tr > td, #checkAmountTable > tbody > tr > td {
    border-top: 1px solid #ddd;
    line-height: 1.42857;
    padding: 8px;
    text-align:center;
    vertical-align: middle;
}</style>
</head>

<div class="col-md-12">
<input type="hidden" name="status">
<input type="hidden" name="approveId">
<input type="hidden" id="weighStatus">
	<div class="portlet box blue" style="border-color: #777;margin-top:50px;">
		<div class="portlet-title" style="background-color: #777;">
			<div class="caption">
				<i class="icon-reorder"></i>通知单统计
			</div>
		</div>
		<div class="portlet-body">
			<form action="#" class="form-horizontal">
				<div class="form-inline form-table">
				<div class="table-scrollable" >
								<table id="checkAmountTable" class="table table-striped table-bordered table-hover table-condensed">
									<thead>
										<tr>
										    <th>通知单号</th>
										    <th>出库时间</th>
										    <th>发货提单号</th>
											<th>提货单位</th>
											<th>货体编号</th>
											<th>货品提单号</th>
											<th>货物名称</th>
											<th>货体剩余可提量(吨)</th>
											<th>开票数</th>
											<th>发货量(吨)</th>
											<th class="changeNum" style="display: none;">调整量</th>
											<th class="changeNum" style="display: none;">差异</th>
										</tr>
									</thead>
									<tbody align=center id="checkAmountList">
									</tbody>
								</table>
				</div></div>
			</form>
		</div>
	</div>

	 	<div class="approveDiv" style="display:none;">
				<h4 class="form-section">&nbsp;审核信息</h4>
				<form class="form-horizontal">
					<div class="form-body">
						<div class="form-group approveInfo" style="display:none;">
							<label class="control-label col-md-2">审核人</label>
							<div class="col-md-4">
								<input type="text" disabled="disabled" class="form-control"
									id="checkUser" />
							</div>
							<label class="control-label col-md-2">审核日期</label>
							<div class="col-md-4">
								<input type="text" disabled="disabled" class="form-control"
									id="checkTime" />
							</div>
						</div>
						<div class="form-group ">
							<label class="col-md-2 control-label">审批意见</label>
							<div class="col-md-8">
								<textarea class="form-control checkDes" id="comment" rows="2"></textarea>
							</div>
						</div>
					</div>
				</form>
			</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default" onclick="window.location ='#/outboundtruckserial/list' ">返回</button>
		<%-- <shiro:hasPermission name="ACARAMOUNTUPDATE"><button type="button" class="btn btn-primary h"
			onclick="v_outbound.confirmData(1);">修改</button></shiro:hasPermission> --%>
		<shiro:hasPermission name="ACARAMOUNTVERIFY"><a href="javascript:void(0)" style="display: none;" data="2" id="passButton"
			class="btn btn-primary  ">通过</a></shiro:hasPermission>
			 <shiro:hasPermission name="ACARAMOUNTVERIFY"><a href="javascript:void(0)" style="display: none;" data='3' id="notpassButton"
			class="btn btn-primary  ">不通过</a></shiro:hasPermission>
	</div>
	</div>