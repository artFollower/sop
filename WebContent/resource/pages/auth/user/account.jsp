<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<link href="<%=basePath%>/resource/admin/pages/css/profile.css"
	rel="stylesheet" type="text/css" />
<link
	href="<%=basePath%>/resource/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css"
	rel="stylesheet" type="text/css" />
<script
	src="<%=basePath%>/resource/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js"
	type="text/javascript"></script>
<div class="row margin-top-20">
	<div class="col-md-12">
		<!-- BEGIN PROFILE SIDEBAR -->
		<div class="profile-sidebar col-md-3">
			<!-- PORTLET MAIN -->
			<div class="portlet light profile-sidebar-portlet">
				<!-- SIDEBAR USERPIC -->
				<div class="profile-userpic">
					<img
						src="<%=basePath%>/resource/admin/pages/media/profile/profile_user.jpg"
						class="img-responsive" alt="">
				</div>
				<!-- END SIDEBAR USERPIC -->
				<!-- SIDEBAR USER TITLE -->
				<div class="profile-usertitle">
					<div class="profile-usertitle-name"></div>
					<div class="profile-usertitle-job"></div>
					<div class="profile-userbuttons profile-usertitle-role"></div>
				</div>
				<!-- END SIDEBAR USER TITLE -->
			</div>
			<!-- END PORTLET MAIN -->
		</div>
		<!-- END BEGIN PROFILE SIDEBAR -->
		<!-- BEGIN PROFILE CONTENT -->
		<div class="profile-content col-md-8">
			<div class="row">
				<div class="col-md-12">
					<div class="portlet light">
						<div class="portlet-title tabbable-line">
							<div class="caption caption-md">
								<i class="icon-globe theme-font hide"></i> <span
									class="caption-subject font-blue-madison bold uppercase">用户账户信息</span>
							</div>
							<ul class="nav nav-tabs">
								<li class="active"><a href="#" data-target="#tab_1_1"
									data-toggle="tab">账户信息</a></li>
								<!-- <li><a href="#" data-target="#tab_1_2" data-toggle="tab">靓照</a></li> -->
								<li><a href="#" data-target="#tab_1_3" data-toggle="tab">修改密码</a></li>
								<!-- <li><a href="#" data-target="#tab_1_4" data-toggle="tab">权限设置</a></li> -->
							</ul>
						</div>
						<div class="portlet-body">
							<div class="tab-content">
								<!-- PERSONAL INFO TAB -->
								<div class="tab-pane active" id="tab_1_1">
									<form role="form" class="userBaseInfoForm form-horizontal">
										<div class="portlet-body">
										<input type="hidden" class="formUserId" name="id" />
										<div class="row">
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label col-md-3">账号:</label>
													<div class="col-md-9">
														<input type="text" class="form-control" readonly="readonly" name="account" data-required="1" data-type="Require">
														<span class="help-block"> </span>
													</div>
												</div>
											</div>
											<!--/span-->
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label col-md-3">别名:</label>
													<div class="col-md-9">
														<input type="text" class="form-control" name="name" data-required="1" data-type="Require">
														<span class="help-block"> </span>
													</div>
												</div>
											</div>
											<!--/span-->
										</div>
										
										<div class="row">
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label col-md-3">手机号:</label>
													<div class="col-md-9">
														<input type="text"
														class="form-control" name="phone" data-required="1"
														data-type="mobile">
														<span class="help-block"> </span>
													</div>
												</div>
											</div>
											<!--/span-->
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label col-md-3">邮箱:</label>
													<div class="col-md-9">
														<input type="text"
															class="form-control" name="email" data-required="1"
															data-type="email">
															<span class="help-block"> </span>
													</div>
												</div>
											</div>
											<!--/span-->
										</div>
										
										<div class="row">
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label col-md-3">状态:</label>
													<div class="col-md-9">
														<select name="status" class="form-control" data-required="1"
															data-type="Require">
															<option value="0">活跃</option>
															<option value="1">锁定</option>
														</select>
															<span class="help-block"> </span>
													</div>
												</div>
											</div>
											<!--/span-->
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label col-md-3">类别:</label>
													<div class="col-md-9">
														<select name="category" class="form-control" data-required="1" data-type="Require">
															<option value="SYSTEM">系统管理员</option>
															<option value="ADMIN">管理员</option>
															<option value="MONITOR">监管员</option>
															<option value="EMPLOYEE">员工</option>
															<option value="USER">企业管理员</option>
														</select>
															<span class="help-block"> </span>
													</div>
												</div>
											</div>
											<!--/span-->
										</div>
										
										<div class="row">
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label col-md-3">客户组:</label>
													<div class="col-md-9">
														<select name="clientGroupId" class="form-control" disabled="disabled">
															<option value="">选择客户组</option>
														</select>
															<span class="help-block"> </span>
													</div>
												</div>
											</div>
											<!--/span-->
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label col-md-3">客户:</label>
													<div class="col-md-9">
														<select name="clientId" class="form-control" disabled="disabled">
															<option value="">选择客户</option>
														</select>
															<span class="help-block"> </span>
													</div>
												</div>
											</div>
											<!--/span-->
										</div>
									
										<div class="row">
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label col-md-3">岗位:</label>
													<div class="col-md-9">
														<select name="jobId" class="form-control" disabled="disabled">
															<option value="">选择岗位</option>
														</select>
															<span class="help-block"> </span>
													</div>
												</div>
											</div>
											<!--/span-->
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label col-md-3">备注:</label>
													<div class="col-md-9">
														<textarea class="form-control" name="description"></textarea>
															<span class="help-block"> </span>
													</div>
												</div>
											</div>
											<!--/span-->
										</div>
										<shiro:hasPermission name="userUpdate">
										<div class="margiv-top-10">
											<a href="javascript:User.modifyBaseInfo()"
												class="btn green-haze">更新账号信息</a>
										</div>
										</shiro:hasPermission>
										</div>
									</form>
								</div>
								<!-- END PERSONAL INFO TAB -->
								<!-- CHANGE AVATAR TAB -->
								<div class="tab-pane" id="tab_1_2">
									<form action="javascript:void(0)">
										<input type="hidden" class="formUserId" name="id" />
										<div class="form-group">
											<div class="fileinput fileinput-new"
												data-provides="fileinput">
												<div class="fileinput-new thumbnail"
													style="width: 200px; height: 150px;">
													<img src="" class="user-photo" />
												</div>
												<div class="fileinput-preview fileinput-exists thumbnail"
													style="max-width: 200px; max-height: 150px;"></div>
												<div>
													<span class="btn default btn-file"> <span class="fileinput-new"> 选择 </span> <span class="fileinput-exists"> 重新选择 </span> <input type="file" name="...">
													</span> <a href="#" class="btn default fileinput-exists" data-dismiss="fileinput"> 移除 </a>
												</div>
											</div>
											<div class="clearfix margin-top-10">
												<span class="label label-danger">注意! </span> <span>仅支持最新的Firefox,
													Chrome, Opera, Safari 和 Internet Explorer 10 +浏览器.</span>
											</div>
										</div>
										<div class="margin-top-10">
											<a href="javascript:User.uploadPhoto()" class="btn green-haze">上传 </a>
										</div>
									</form>
								</div>
								<!-- END CHANGE AVATAR TAB -->
								<!-- CHANGE PASSWORD TAB -->
								<div class="tab-pane" id="tab_1_3">
									<form class="userPswdForm">
										<input type="hidden" class="formUserId" name="id" />
										<div class="form-group">
											<label class="control-label">新密码:</label> <input
												type="password" name="password" class="form-control"
												data-required="1" data-type="Require" />
										</div>
										<div class="form-group">
											<label class="control-label">确认新密码:</label> <input
												type="password" name="repswd" class="form-control"
												data-required="1" data-type="Require" />
										</div>
										<shiro:hasPermission name="userUpdate">
										<div class="margin-top-10">
											<a href="javascript:User.changeUserPassword()"
												class="btn green-haze"> 修改密码 </a>
										</div>
										</shiro:hasPermission>
									</form>
								</div>
								<!-- END CHANGE PASSWORD TAB -->
								<!-- PRIVACY SETTINGS TAB -->
								<div class="tab-pane" id="tab_1_4">
									<form action="#">
										<table class="table table-light table-hover">
											<tr>
												<td>Anim pariatur cliche reprehenderit, enim eiusmod
													high life accusamus..</td>
												<td><label class="uniform-inline"> <input
														type="radio" name="optionsRadios1" value="option1" /> Yes
												</label> <label class="uniform-inline"> <input type="radio"
														name="optionsRadios1" value="option2" checked /> No
												</label></td>
											</tr>
											<tr>
												<td>Enim eiusmod high life accusamus terry richardson
													ad squid wolf moon</td>
												<td><label class="uniform-inline"> <input
														type="checkbox" value="" /> Yes
												</label></td>
											</tr>
											<tr>
												<td>Enim eiusmod high life accusamus terry richardson
													ad squid wolf moon</td>
												<td><label class="uniform-inline"> <input
														type="checkbox" value="" /> Yes
												</label></td>
											</tr>
											<tr>
												<td>Enim eiusmod high life accusamus terry richardson
													ad squid wolf moon</td>
												<td><label class="uniform-inline"> <input
														type="checkbox" value="" /> Yes
												</label></td>
											</tr>
										</table>
										<!--end profile-settings-->
										<div class="margin-top-10">
											<a href="#" class="btn green-haze"> Save Changes </a> <a
												href="#" class="btn default"> Cancel </a>
										</div>
									</form>
								</div>
								<!-- END PRIVACY SETTINGS TAB -->
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- END PROFILE CONTENT -->
	</div>
</div>
