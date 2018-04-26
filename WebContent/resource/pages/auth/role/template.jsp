<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">添加角色</h4>
			</div>
			<div class="modal-body" style="padding-left:45px; padding-right:65px;">
				<form class="form-horizontal role-add-form">
					<div class="form-group">
						<label class="col-lg-3 control-label">名称:<span class="required">*</span></label>
						<div class="col-lg-9">
							<input type="text" class="form-control" name="name" data-required="1" data-type="Require">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">类型:<span class="required">*</span></label>
						<div class="col-lg-9">
							<select  name="type" class="form-control" data-required="1"  data-type="Require">
				          	 <option value="ADMINISTRATOR">系统管理员</option>
				             <option value="ADMIN">管理员</option>
				             <option value="USER">操作人员</option>
				             <option value="MONITOR">监管人员</option>
				             <option value="GUEST">普通用户</option>
				           </select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">状态:<span class="required">*</span></label>
						<div class="col-lg-9">
							<select  name="status" class="form-control" data-required="1"  data-type="Require">
				          	 <option value="0">活跃</option>
				             <option value="1">锁定</option>
				           </select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">描述:</label>
						<div class="col-lg-9">
							<input type="text" class="form-control" name="description">
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