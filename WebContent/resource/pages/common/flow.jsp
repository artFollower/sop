<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<style>
.modal-dialog {
	margin: 30px auto;
	width: 900px;
}
</style>
<div class="modal fade dialogChoiceItem">
	<div class="modal-dialog">

		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">添加用户</h4>
			</div>
			<div class="modal-body">
				<div class="row">
				<div class="col-md-12 searchDiv" style="padding-left: 45px; padding-right: 65px;">
					<form id="goodsSearchForm" target="_self" class="form-horizontal searchCondition">
								<label class="control-label col-md-2">货品名:</label>
								<div class="col-md-3">
									<input id="productId" class="form-control" disabled readonly type="text" />
								</div>
								<label class="control-label pumpDiv col-md-2">泵性质:</label>
								<div class="col-md-3 pumpDiv">
									 <select class="form-control" id="type">
									                   <option value="-1" selected>全部</option>
														<option value="1" >船发泵</option>
														<option value="2">车发泵</option>
													</select>
								</div>
						<div class="col-md-2">
								<div class="input-group-btn">
									<button type="button" class="btn btn-success search " style="display:none">
										<span class="glyphicon glyphicon-search"></span>&nbsp;
									</button>
									<button type="button" style="margin-left: 8px;" style="display:none" class="btn btn-primary reset">
										<span class="fa fa-undo"></span>&nbsp;
									</button>
								</div>
						</div>
					</form>
				</div>
				<div class="col-md-12" style="padding-left: 25px; padding-right: 25px;">
					<div id="flow" data-role="flowTable"></div>
				</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default flowClose" id="close">取消</button>
				<button type="button" class="btn btn-success hidden" id="save">提交</button>
			</div>
		</div>
	</div>
</div>