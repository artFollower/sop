<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<style>
.modal-dialog {
	margin: 30px auto;
	width: 1200px;
}
</style>
<div class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title">码头规费结算单</h4>
			</div>
			<div class="modal-body"
				style="padding-left: 25px; padding-right: 35px;">
				<div class="input-group col-md-12">
					<label class="col-md-2 control-label" style="text-align: right;">结算日期:</label>
					<input style="text-align: center; border: 1px solid #ccc"
						id="accountTime" class="date-picker col-md-3" type="text" /> <label
						class="col-md-2 col-md-offset-2 control-label"
						style="text-align: right;">结算单编号:</label> <label
						class="col-md-3 control-label feeKey" key="0"
						style="text-align: left" id="code"></label>
                       <label  id="id" class="hidden"></label>
                       <label  id="status" class="hidden"></label>
                       <label  id="netTons" class="hidden"></label>
                       <label  id="stayDay" class="hidden"></label>
                         <label  id="arrivalId" class="hidden"></label>
				</div>
				<div class="portlet box blue">
					<div class="portlet-title">
						<div class="tools">
							<a onclick="arrivalBill.print()" class="hidden-print"
								style="padding-left: 20px;"> <i class="fa fa-print">&nbsp;打印</i></a><span
								class="pull-right" style="font-size: small;"></span>
						</div>
					</div>
					<div class="portlet-body ">
						<form class="form-horizontal">
							<div class="form-body dockFeeDiv">
								<h4 class="form-section">基本信息</h4>
								<div class="form-group">
									<label class="control-label col-md-1">对方单位<span
										class="required">*</span>
									</label>
									<div class="col-md-3">
										<input type="text" id="clientName" data-required="1"
											data-type="Require" class="form-control" />
									</div>
									<label class="control-label col-md-1">合同号
									</label>
									<div class="col-md-3">
										<input type="text" id="contractName" class="form-control" maxlength="50" />
									</div>
									<label class="control-label col-md-1">品名<span
										class="required">*</span>
									</label>
									<div class="col-md-3">
										<input type="text" id="productName" class="form-control"
											readonly="readonly" maxlength="50" />
									</div>

								</div>
								<div class="form-group">
									<label class="control-label col-md-1">船名<span
										class="required">*</span>
									</label>
									<div class="col-md-3">
										<input type="text" id="shipName" class="form-control"
											readonly="readonly" />
									</div>
									<label class="control-label col-md-1">发生日期<span
										class="required">*</span>
									</label>
									<div class="col-md-3">
										<input type="text" name="arrivalTime" id="arrivalTime"
											class="form-control" readonly="readonly" />
									</div>
									<label class="control-label col-md-1">发票号码</label>
									<div class="col-md-3">
										<input type="text" id="billCode" class="form-control"
											maxlength="15">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-1">贸易类型<span
										class="required">*</span>
									</label>
									<div class="col-md-3">
										<select id="tradeType" data-required="1"
											data-type="Require"
											onchange="DockFee.setTradeType(this)"
											class="form-control">
											<option selected value="2">内贸账单</option>
											<option value="1">外贸账单</option>
										</select>
									</div>
									<label class="control-label col-md-1">结算方式<span
										class="required">*</span></label>
									<div class="col-md-3">
										<select   id="accountType" data-required="1"
											data-type="Require" class="form-control">
											<option value="1">现结</option>
											<option value="2">月结</option>
										</select>
									</div>
									<label class="control-label col-md-1">发票类型<span
										class="required">*</span></label>
									<div class="col-md-3">
										<select   id="billType" data-required="1"
											data-type="Require" class="form-control">
											<option value="1">手撕发票</option>
											<option value="2">增值税发票</option>
										</select>
									</div>
								</div>
								<h4 class="form-section">收费内容</h4>
								<table class="table table-striped table-hover table-bordered"
									id="chargeInfoTable">
									<thead>
										<tr>
											<th>项目</th>
											<th colspan="4">公式</th>
											<th>金额(元)</th>
											<th id="discountFeeTh">实收金额(元)</th>
										</tr>
									</thead>
									<tbody id="arrivalChargeInfo">
									</tbody>
								</table>
								<div class="form-group " id="createUserDiv">
									<div class="col-md-6">
										<div class="form-group">
											<label class="col-md-4 control-label">制定人:</label> <label
												class="col-md-8 control-label" style="text-align:left;" id="createUserId"></label>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label class="col-md-4 control-label">制定时间:</label> <label
												class="col-md-8 control-label" style="text-align:left;" id="createTime"></label>
										</div>
									</div>
								</div>

							</div>
						</form>

					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
				<button type="button" class="btn btn-primary " data="0" id='save'>保存</button>
				<button type="button" class="btn btn-primary " data="1" id="submit">提交</button>
			</div>
		</div>
	</div>
</div>