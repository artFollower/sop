<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<style>
.modal-dialog {
	margin: 30px auto;
	width: 1000px;
}
</style>
<div class="modal fade">
	<div class="modal-dialog modal-wide">
		<div class="modal-content" >
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">车辆信息</h4>
			</div>
			<div class="modal-body" style="padding-left:45px; padding-right:65px;">
			<div class="row">
				<div class="col-md-12">
			<form class="form-horizontal">
				<div class="form-body">
								<div class="form-group">
								<input type="hidden" maxlength="16" id="name" class="form-control "  />
									<input type="hidden" id="id">
									<label class="control-label col-md-2">车牌号<span class="required">*</span></label>
									<div class="col-md-4">
										<input type="text" maxlength="16" id="code" data-required="1" data-type="Require" class="form-control name"  />
									</div>
									<label class="control-label col-md-2">最大允许荷载(吨)</label>
									<div class="col-md-4">
										<input type="text" maxlength="16" id="maxLoadCapacity" onkeyup="config.clearNoNum(this,3);" class="form-control "  />
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">历史最大载重(吨)</label>
									<div class="col-md-4">
										<input type="text" maxlength="10" id="loadCapacity" onkeyup="config.clearNoNum(this,3);"  class="form-control loadCapacity" />
									</div>
									<label class="control-label col-md-2">所属单位</label>
									<div class="col-md-4">
										<input type="text" id="company" maxlength="16" class="form-control company"  />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label">描述</label>
									<div class="col-md-10">
										<textarea class="form-control description" maxlength="100" rows="3" id="description"></textarea>
									</div>
								</div>
				</div>
				</form>
		</div>
			</div>
		</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary" id="save" >提交</button>
			</div>
		</div>
	</div>
</div>
