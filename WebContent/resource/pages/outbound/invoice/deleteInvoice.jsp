<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<style>
.modal-dialog {
	margin: 30px auto;
	width: 100%;
}
</style>
<div class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title">开票作废记录</h4><span class="pull-right">
		<a style="padding-left: 20px;" class="hidden-print" onclick="DeleteInvoice.exportExcel(this)">
		 <i class="fa fa-file-excel-o">&nbsp;导出</i></a>
		 </span>
			</div>
			<div class="modal-body" style="padding-left: 25px; padding-right: 35px;">
				<div class="form-body">
				<form action="#" class="form-horizontal">
					<div class="row">
					<div class="form-body">
						<div class="form-group">
						 <div class="col-md-3">
						 	<label class="control-label col-md-4">货品 </label>
						<div class="col-md-8">
						<input type="text" id="productName" class="form-control intentTitle" />
						</div>
						 </div>
						<div class="col-md-3">
						<label class="control-label col-md-4">货主 </label>
						<div class="col-md-8">
						<input type="text" id="clientName" class="form-control intentTitle" />
						</div>
						</div>
						<div class="col-md-3">
						<label class="control-label col-md-4">开票类型 </label>
						<div class="col-md-8">
						<select class="form-control" id="deliverType">
					    <option value="0" selected>全部</option>
						<option value="1">车发</option>
						<option value="2">船发</option>
						<option value="3">转输</option>
						</select>
						</div>
						</div>
						<div class="col-md-3">
						<label class="control-label col-md-4">作废类型</label>
						<div class="col-md-8">
						<select class="form-control" id="cancelType">
					    <option value="-1" selected>全部</option>
						<option value="0">开票删除</option>
						<option value="1">冲销作废</option>
						</select>
						</div>
						</div>
						</div>
						
						<div class="form-group">
						<div class="col-md-3">
						<label class="control-label col-md-4">车船号</label>
							<div class="col-md-8" id="vsInfo">
								<input type="text" id="vehicleShipNo"
									class="form-control intentTitle"   />
							</div>
						</div>
						<div class="col-md-3">
						<label class="control-label col-md-4">通知单号</label>
							<div class="col-md-8">
								<input type="text" id="serial"
									class="form-control intentTitle"  maxlength="30"/>
							</div>
						</div>
						<div class="col-md-3">
						<label class="control-label col-md-4">提单号</label>
							<div class="col-md-8">
								<input type="text" id="ladingEvidence"
									class="form-control intentTitle"  maxlength="30" style="text-transform: uppercase;font-size: 14px;"/>
							</div>
						</div>
						<div class="col-md-3">
						 <div class="form-group ">
						  <div class="col-md-8 col-md-offset-4 input-group-btn" >
							<button type="button" class="btn btn-success search">
								<span class="fa fa-search"></span>&nbsp;
							</button>
							<button  type="button" style="margin-left:8px;"
								class="btn btn-primary reset">
								<span class="fa fa-undo" title="重置"></span>&nbsp;
							</button>
							</div>
						</div>
						</div>
					</div>
					</div>
					</div>
			</form>
				<div class="col-md-12">
					<div data-role="deleteInvoiceGrid"></div>
				</div>
			</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>

</div>

