<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<style>
.modal-dialog {
	margin: 30px auto;
	width: 75%;
}
</style>
<div class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title">开票明细</h4>
			</div>
			<div class="modal-body"
				style="padding-left: 25px; padding-right: 35px;">
				<input type="hidden" id="idHidden" /> <input type="hidden"
					id="batchId" /> <input type="hidden" class="form-control inputId"
					name="inputId" />
				<div class="portlet box blue ">
					<div class="portlet-title">
					<div class="tools">
						<a
							onclick="javascript:Invoice.print(1,this);" class="hidden-print"
							style="padding-right: 12px; color: white;"> <i class="fa fa-print">&nbsp;打印</i></a>
							<label class="checkbox-inline"> <input type="checkbox"
								value="" class="liandan">联单
						</label>
						</div>
					</div>
					<div class="portlet-body kpxg">
						<form action="#" class="form-horizontal contract-update-form">
							<div class="form-body">
								<div class="form-group">
									<label class="control-label col-md-2">通知单号</label>
									<div class="col-md-4" id="select-intentionId">
										<input type="text" id="serial" data-provide="typeahead"
											data-type="Require" readonly="readonly"
											class="form-control intention">
									</div>
									<label class="control-label col-md-2">客户名称</label>
									<div class="col-md-4" id="select-intentionId">
										<input type="text" id="clientName" data-provide="typeahead"
											data-type="Require" readonly="readonly"
											class="form-control intention">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">上级货主</label>
									<div class="col-md-4" id="select-intentionId">
										<input type="text" id="shangjihuozhu" data-provide="typeahead"
											data-type="Require" readonly="readonly"
											class="form-control intention">
									</div>
									<label class="control-label col-md-2">原始货主</label>
									<div class="col-md-4">
										<input type="text" id="yuanshihuozhu" readonly="readonly"
											class="form-control orderTitle" />
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">货批号</label>
									<div class="col-md-4">
										<input type="text" id="cargoCode" readonly="readonly"
											class="form-control orderTitle" />
									</div>
									<label class="control-label col-md-2">货品名称</label>
									<div class="col-md-4">
										<input type="text" id="productName" readonly="readonly"
											class="form-control orderTitle" />
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">货体号</label>
									<div class="col-md-4">
										<input id="code" type="text" class="form-control type code"
											readonly="readonly" />
									</div>
									<label class="control-label col-md-2">剩余可提量（吨）</label>
									<div class="col-md-4" id="select-type">
										<input id="goodsCurrent" type="text" readonly="readonly"
											class="form-control type">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">发货类型</label>
									<div class="col-md-4" id="select-tradeType">
										<input id="deliverType" type="text" readonly="readonly"
											class="form-control tradeType">
									</div>
									<label class="control-label col-md-2">车船号<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="vehicleShipNo" type="text" data-required="1"
											data-type="Require" class="form-control clientGroupId"
											maxlength="11"> <input id="id" type="hidden" readonly="readonly" 
											readonly="readonly" class="form-control clientGroupId">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">开票数（吨）<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="deliverNum" type="text" data-provide="typeahead"  
											data-required="1" onkeyup="config.clearNoNum(this)"
											data-type="Require" class="form-control clientId"
											maxlength="10">
									</div>
									<label class="control-label col-md-2">实发数（吨）<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input id="actualNum" type="text" data-provide="typeahead"
											readonly="readonly" data-required="1"
											onkeyup="config.clearNoNum(this)" data-type="Require"
											class="form-control clientId" maxlength="10">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">提货单位<span
										class="required">*</span></label>
									<div class="col-md-4" id="client">
										<input id="ladingClientId" type="text"
											data-provide="typeahead" data-required="1"
											data-type="Require" class="form-control clientId"
											maxlength="11">
									</div>
									<label class="control-label col-md-2">提单号</label>
									<div class="col-md-4" id="select-type">
										<input id="ladingEvidence" type="text"
											class="form-control type" maxlength="100">
									</div>
								</div>
								<div class="form-group">
									<!-- <label class="control-label col-md-2">提单号</label>
									<div class="col-md-4" id="client">
									<input id="ladingCode" type="text" data-provide="typeahead" class="form-control clientId" maxlength="16">
									</div> -->
									<label class="control-label col-md-2">罐号</label>
									<div class="col-md-4" id="tankId">
										<input id="tankName" type="text"
											class="form-control type tankName" readonly="readonly" maxlength="6" />
									</div>
									<label class="control-label col-md-2">仓号</label>
									<div class="col-md-4" id="block">
										<input id="blockCode" type="text"  readonly='readonly' data-provide="typeahead" onkeyup="config.clearNoNum(this,1)"
											class="form-control blockCode" maxlength="16">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">备注</label>
									<div class="col-md-10">
										<textarea id="remark" type="text" data-provide="typeahead"
											class="form-control remark" maxlength="100"></textarea>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">开票人</label> <label
										class="control-label col-md-4" style="text-align: left;"
										id="createUserId"></label> <label
										class="control-label col-md-2">开票时间</label> <label
										class="control-label col-md-4" style="text-align: left;"
										id="createTime"></label>
								</div>
								<div class="checkgroup" style="display: none">
									<h4 class="form-section">&nbsp;审核信息</h4>
									<table width="100%">
										<tr class="check" style="display: none">
											<td class=" col-md-6">
												<div class="form-group">
													<label class="control-label col-md-4">审核人</label>
													<div class="col-md-8">
														<input type="text" readonly class="form-control checkUser"
															name="createUserName" />
													</div>
												</div>
											</td>
											<td class=" col-md-6">
												<div class="form-group">
													<label class="control-label col-md-5">审核日期</label>
													<div class="col-md-7">
														<input type="text" readonly class="form-control checkTime"
															name="createTime" />
													</div>
												</div>
											</td>
										</tr>

										<tr class="checkContent" style="display: none">
											<td class=" col-md-6" colspan="2">
												<div class="form-group">
													<label class="col-md-2 control-label">审批意见</label>
													<div class="col-md-10">
														<textarea class="form-control checkDes" rows="3"></textarea>
													</div>
												</div>
											</td>
										</tr>

									</table>
								</div>
							</div>
						</form>
					</div>
				</div>
				<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary submitButton" onclick="Invoice.doEdit(this)" >提交</button>
				</div>
			</div>
		</div>
	</div>
</div>