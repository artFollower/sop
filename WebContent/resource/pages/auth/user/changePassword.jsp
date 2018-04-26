<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">修改密码</h4>
			</div>
			<div class="modal-body" style="padding-left:45px; padding-right:65px;">
				<form class="form-horizontal change-passwd-form">
					
					<div class="form-group">
						<label class="col-lg-3 control-label">原密码:<span class="required">*</span></label>
						<div class="col-lg-9">
							<input type="password" class="form-control" name="opswd" data-required="1"  data-type="Require">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">新密码:<span class="required">*</span></label>
						<div class="col-lg-9">
							<input type="password" class="form-control" name="password" data-required="1"  data-type="Require">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">确认新密码:<span class="required">*</span></label>
						<div class="col-lg-9">
							<input type="password" class="form-control" name="repswd" data-required="1"  data-type="Require">
						</div>
					</div>
					
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" id="close">取消</button>
				<button type="button" class="btn btn-success" id="save">提交</button>
			</div>
		</div>
	</div>
</div>