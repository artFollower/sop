<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<style>
<!--
.modal-dialog {
	width: 60%;
}
-->
</style>
<div class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title">货主货品确认操作</h4>
			</div>
			<div class="modal-body amountaffirm"
				style="padding-left: 45px; padding-right: 65px;">
				<form action="#" class="form-horizontal">
					<div class="form-body">
					    <div class="form-group">
						<label class="col-md-4 control-label">合同类型:</label>
							<div class="col-md-8">
								<select  class="form-control" id="orderType">
												<option value="1">混罐</option>
												<option value="2">包罐</option>
												</select>
							</div></div>
						<div class="form-group">
						<label class="col-md-4 control-label">货主:</label>
							<div class="col-md-8">
								<label class="control-label" id="clientName"></label>
							</div></div>
							
							<div class="form-group">
						  <label class="col-md-4 control-label">货品:</label>
							<div class="col-md-8">
								<label class="control-label" id="productName"></label>
							</div></div>
							
						<div class="form-group">
						<label class="col-md-4 control-label">计划数量(吨):</label>
							<div class="col-md-8">
								<label class="control-label"  id="goodsPlan"></label>
							</div></div>
							
							<!-- <div class="form-group goodsShip">
							<label class="col-md-4 control-label">船检数量:<span class="required">*</span></label>
							<div class="col-md-8">
								<input class="form-control" onkeyup="config.clearNoNum(this)" id="goodsShip" data-required="1">
							</div></div> -->
							
						<div class="form-group hidden">
						<label class="col-md-4 control-label">入库实际损耗:</label>
							<div class="col-md-8">
								<label class="control-label" id="goodsLoss"></label>
							</div></div>
							
							<div class="form-group hidden">
							<label class="col-md-4 control-label">入库损耗率:</label>
							<div class="col-md-8">
								<label class="control-label" id="lossRate"></label>
							</div></div>
							
							<div class=" countDiv">
							<div class="form-group">
							<label class="col-md-4 control-label">货品实际总量(吨)：</label>
							<label class="col-md-8 control-label" style="text-align:left;" id="allTankNum"></label>
							</div>
							<div class="form-group">
							<label class="col-md-4 control-label">相关罐号：</label>
							<!-- <label class="col-md-8 control-label" style="text-align:left;" id="allTankNames"></label> -->
							<div class="col-md-8">
                                          <input type="hidden" id="allTankNames" class="form-control select2" >
                                       </div>
							</div>
							<div class="form-group splitNumDiv">
							<label class="col-md-4 control-label">拆分数量(吨)：<span class="required">*</span></label>
							<div class="col-md-8">
							<input class="form-control" maxlength="12" onkeyup="config.clearNoNum(this)" id="splitNum" data-required="1">
							</div>
							</div>
							</div>
							
						<div class="form-group tankDiv" >
						<div class="col-md-12">
							<table class="table table-striped table-hover table-bordered" style="width:100%"
								id="amountTable">
								<thead>
									<th>罐号</th>
									<th>货品</th>
									<th>罐检数量</th>
									<th>操作量</th>
								</thead>
							</table>
                        </div>
						</div>
						
						<input type="hidden" id="id" class="form-control">
						<input type="hidden" id="ctLossRate" class="form-control">
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" id="close">取消</button>
				<button type="button" class="btn btn-success" id="save">确认</button>
			</div>
		</div>
	</div>
</div>