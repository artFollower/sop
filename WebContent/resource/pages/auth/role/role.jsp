<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<style>
<!--
.radio-inline + .radio-inline, .checkbox-inline + .checkbox-inline {
	margin-left: 0px;
}
hr {
	margin-top: 10px; 
	margin-bottom: 10px;
}
-->
</style>
<link href="<%=basePath%>/resource/admin/pages/css/profile.css" rel="stylesheet" type="text/css" />
<div class="row margin-top-20">
	<div class="col-md-12">
		<!-- BEGIN PROFILE CONTENT -->
		<div class="profile-content col-md-12">
			<div class="row">
				<div class="col-md-12">
					<div class="portlet light">
						<div class="portlet-title tabbable-line">
							<div class="caption caption-md">
								<i class="icon-globe theme-font hide"></i> <span class="caption-subject font-blue-madison bold uppercase">角色信息</span>
							</div>
							<ul class="nav nav-tabs">
								<li class="active"><a href="#" data-target="#tab_1_1" data-toggle="tab">基本信息</a></li>
								<li><a href="#" data-target="#tab_1_2" data-toggle="tab">权限设置</a></li>
							</ul>
						</div>
						<div class="portlet-body">
							<div class="tab-content">
								<!-- PERSONAL INFO TAB -->
								<div class="tab-pane active" id="tab_1_1">
									<form class="form-horizontal role-add-form">
										<div class="form-group">
											<label class="col-lg-1 control-label">名称:<span class="required">*</span></label>
											<div class="col-lg-9">
												<input type="text" class="form-control" name="name" data-required="1" data-type="Require">
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-1 control-label">类型:<span class="required">*</span></label>
											<div class="col-lg-9">
												<select name="type" class="form-control" data-required="1" data-type="Require">
													<option value="USER">操作人员</option>
													<option value="ADMINISTRATOR">系统管理员</option>
													<option value="ADMIN">管理员</option>
													<option value="MONITOR">监管人员</option>
													<option value="GUEST">普通用户</option>
												</select>
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-1 control-label">状态:<span class="required">*</span></label>
											<div class="col-lg-9">
												<select name="status" class="form-control" data-required="1" data-type="Require">
													<option value="0">活跃</option>
													<option value="1">锁定</option>
												</select>
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-1 control-label">描述:</label>
											<div class="col-lg-9">
												<textarea rows="2" class="form-control" name="description"></textarea>
											</div>
										</div>
									</form>
								</div>
								<!-- END PERSONAL INFO TAB -->
								<!-- CHANGE AVATAR TAB -->
								<div class="tab-pane" id="tab_1_2">
									<div class="row">
									<form role="form" class="roleResourceForm">
										
									</form>
									</div>
								</div>
								<!-- END CHANGE AVATAR TAB -->
							</div>

							<div class="modal-footer">
								<button type="button" class="btn btn-default" onclick='javascript:history.go(-1)'>返回</button>
								<button type="button" class="btn btn-success" id="save">提交</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- END PROFILE CONTENT -->
	</div>
</div>
