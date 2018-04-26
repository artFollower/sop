<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<style>
.modal-dialog {
	margin: 30px auto;
	width: 1000px;
}
</style>
<div class="modal fade">
	<div class="modal-dialog modal-wide">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title">海关放行统计明细</h4>
			</div>
			<div class="modal-body"
				style="padding-left: 45px; padding-right: 65px;">
				<div class="row">
					<div class="col-md-12">
						<form class="form-horizontal addGoodsForm">
							<label class="col-md-8 hidden " id="id"></label> <label
								class="col-md-8 hidden " id="status"></label>
							<div class="form-group">
								<label class="control-label col-md-2">入库日期:</label> <label
									class="control-label col-md-4" style="text-align: left;"
									id="inboundTime"></label> <label class="control-label col-md-2">船名:</label>
								<label class="control-label col-md-4" style="text-align: left;"
									id="shipName"></label>
							</div>
							<div class="form-group">
								<label class="control-label col-md-2">货品:</label> <label
									class="control-label col-md-4" style="text-align: left;"
									id="productName"></label> <label class="control-label col-md-2">罐号:</label>
								<label class="control-label col-md-4" style="text-align: left;"
									id="tankName"></label>
							</div>

							<div class="form-group">

								<label class="control-label col-md-2">卸货前罐量(吨):</label>
								<div class="col-md-4">
									<input type="text" class=" form-control" id="beforeInboundTank"
										onkeyup="config.clearNoNum(this,3)" data-required="1"
										data-type="Require" maxlength="10">
								</div>
								<label class="control-label col-md-2">海关已放行量(吨):</label>
								<div class="col-md-4">
									<input type="text" class=" form-control" id="hasCustomsPass"
										onkeyup="config.clearNoNum(this,3)" data-required="1"
										data-type="Require" maxlength="10">
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-md-2">卸货后罐量(吨):</label>
								<div class="col-md-4">
									<input type="text" class=" form-control" id="afterInboundTank"
										onkeyup="config.clearNoNum(this,3)" data-required="1"
										data-type="Require" maxlength="10">
								</div>
								<label class="control-label col-md-2">可发数量(吨):</label>
								<div class="col-md-4">
									<input type="text" class=" form-control" readonly="readonly"
										id="outBoundCount" onkeyup="config.clearNoNum(this,3)"
										data-required="1" data-type="Require" maxlength="10">
								</div>
							</div>


							<div class="form-group">
								<label class="control-label col-md-2">卸货数量(吨):</label>
								<div class="col-md-4 ">
									<input type="text" class=" form-control" readonly="readonly"
										id="inboundCount" onkeyup="config.clearNoNum(this,3)"
										data-required="1" data-type="Require" maxlength="10">
								</div>
								<label class="control-label col-md-2">不可发数量(吨):</label>
								<div class="col-md-4">
									<input type="text" class=" form-control" readonly="readonly"
										id="unOutBoundCount" onkeyup="config.clearNoNum(this,3)"
										data-required="1" data-type="Require" maxlength="10">
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-md-2">备注:</label>
								<div class="col-md-10">
									<input type="text" class=" form-control" id="description"
										maxlength="30">
								</div>
							</div>

							<div class="form-group createUserDiv">
								<label class="control-label user col-md-2">提交人:</label> <label
									class="control-label user col-md-4" style="text-align: left;"
									id="userId"></label> <label class="control-label col-md-2">提交时间:</label>
								<label class="control-label user col-md-4"
									style="text-align: left;" id="createTime"></label>
							</div>
							<div class="col-md-12" data-role="baseCargoPassGrid"></div>
							<div class="col-md-12" data-role="bondedCargoPassGrid"></div>
						</form>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<shiro:hasPermission name="ACUSTOMSRELEASESAVE">
				<button type="button" class="btn btn-primary" id="addCustomsRelease">提交</button>
				</shiro:hasPermission>
			</div>
		</div>
	</div>
</div>
