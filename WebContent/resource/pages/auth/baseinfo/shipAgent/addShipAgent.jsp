<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="modal fade">
	<div class="modal-dialog modal-wide" >
		<div class="modal-content"  style="width: 800px;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">船舶代理信息</h4>
			</div>
			<div class="modal-body" style="padding-left:45px; padding-right:65px;">
			<div class="row">
				<div class="col-md-12">
			<form action="#" class="form-horizontal">
				<div class="form-body">
								<div class="form-group">
									<input type="hidden" id="id">
									<label class="control-label col-md-2">船代简称<span class="required">*</span></label>
									<div class="col-md-4">
										<input type="text" id="code" maxlength="16" data-required="1" data-type="Require"  class="form-control" />
									</div>
									<label class="control-label col-md-2">船代名称<span class="required">*</span></label>
									<div class="col-md-4">
										<input type="text" maxlength="16" id="name" data-required="1" data-type="Require"  class="form-control" />
									</div>
								</div>
							<div class="form-group">
							<label class="control-label col-md-2">联系人</label>
									<div class="col-md-4">
										<input type="text" id="contactName" maxlength="16"  class="form-control" />
									</div>
							<label class="control-label col-md-2">联系电话</label>
									<div class="col-md-4">
										<input type="text" id="contactPhone" maxlength="16" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">联系人邮箱</label>
									<div class="col-md-4">
										<input type="text" id="contactEmail" maxlength="32" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label">描述</label>
									<div class="col-md-10">
										<textarea class="form-control" maxlength="100" id="description" rows="2"></textarea>
									</div>
								</div>
				</div>
			</form>
	</div>
			</div>
		</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary" id="save">提交</button>
			</div>
		</div>
	</div>
</div>
