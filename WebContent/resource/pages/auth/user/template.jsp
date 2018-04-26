<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title">添加用户</h4>
			</div>
			<div class="modal-body"
				style="padding-left: 45px; padding-right: 65px;">
				<form class="form-horizontal add-user-form">
					<div class="form-group">
						<label class="col-lg-3 control-label">别名:<span
							class="required">*</span></label>
						<div class="col-lg-9">
							<input type="text" class="form-control" name="name"
								data-required="1" data-type="Require">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">账号:<span
							class="required">*</span></label>
						<div class="col-lg-9">
							<input type="text" class="form-control" name="account"
								data-required="1" data-type="Require">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">密码:<span
							class="required">*</span></label>
						<div class="col-lg-9">
							<input type="password" value="skycloud" class="form-control"
								name="password" data-required="1" data-type="Require">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">邮箱:<span
							class="required">*</span></label>
						<div class="col-lg-9">
							<input type="text" class="form-control" name="email"
								data-required="1" data-type="email">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">手机号:<span
							class="required">*</span></label>
						<div class="col-lg-9">
							<input type="text" class="form-control" name="phone"
								data-required="1" data-type="mobile">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">状态:<span
							class="required">*</span></label>
						<div class="col-lg-9">
							<select name="status" class="form-control" data-required="1"
								data-type="Require">
								<option value="0">活跃</option>
								<option value="1">锁定</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">类别:<span
							class="required">*</span></label>
						<div class="col-lg-9">
							<select name="category" class="form-control" data-required="1"
								data-type="Require">
								<option value="SYSTEM">系统管理员</option>
								<option value="ADMIN">管理员</option>
								<option value="MONITOR">监管员</option>
								<option value="EMPLOYEE">员工</option>
								<option value="USER">企业管理员</option>
							</select>
						</div>
					</div>
					<div class="form-group client-group">
						<label class="col-lg-3 control-label">客户组:</label>
						<div class="col-lg-9">
							<select name="clientGroupId" class="form-control" disabled="disabled">
								<option value="">选择客户组</option>
							</select>
						</div>
					</div>
					<div class="form-group client-group">
						<label class="col-lg-3 control-label">客户:</label>
						<div class="col-lg-9">
							<!-- <select name="clientId" class="form-control" disabled="disabled">
								<option value="">选择客户</option>
							</select> -->
							<input id="client" type="text" name="clientId"  class="form-control" style="display: none">
						
								<input id="clientId" type="text"  data-provide="typeahead" disabled="disabled"  class="form-control clientId">
						
							
						</div>
					</div>
					<div class="form-group job-group">
						<label class="col-lg-3 control-label">岗位:</label>
						<div class="col-lg-9">
							<select name="jobId" class="form-control" disabled="disabled">
								<option value="">选择岗位</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">描述:</label>
						<div class="col-lg-9">
							<textarea class="form-control" name="description"></textarea>
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