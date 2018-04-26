<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<!-- BEGIN MAIN CONTENT -->
<div
	class="row page-header-fixed page-sidebar-closed-hide-logo page-sidebar-closed page-quick-sidebar-over-content page-container-bg-solid page-compact">
	<div class="col-md-12">
		<!-- Begin: life time stats -->
		<div class="portlet">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-comment"></i>消息中心
				</div>
				<div class="actions" style="display: none">
					<div class="btn-group">
						<a class="btn default yellow-stripe" href="javascript:void(0)"
							data-toggle="dropdown"> <i class="fa fa-share"></i> <span
							class="hidden-480"> 工具 </span> <i class="fa fa-angle-down"></i> </a>
						<ul class="dropdown-menu pull-right">
							<li><a href="javascript:void(0)"> 导出EXCEL </a></li>
							<li><a href="javascript:void(0)"> 导出CSV </a></li>
							<li><a href="javascript:void(0)"> 导出XML </a></li>
							<li class="divider"></li>
							<li><a href="javascript:void(0)"> 打印 </a></li>
							<li><a href="#/intentAdd">添加</a></li>
						</ul>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-12">
					<div class="todo-ui">
						<div class="todo-sidebar">
							<div class="portlet light">
								<div class="portlet-title">
									<div data-target=".todo-project-list-content"
										data-toggle="collapse" class="caption">
										<span class="caption-subject font-green-sharp bold uppercase">消息
										</span>
										<!--  <span
											class="caption-helper visible-sm-inline-block visible-xs-inline-block">click
											to view project list</span>-->
									</div>
									<!-- <div class="actions">
										<div class="btn-group">
											<a data-close-others="true" dropdown-menu-hover=""
												data-toggle="dropdown" href="#"
												class="btn green-haze btn-circle btn-sm todo-projects-config"
												aria-expanded="false"> <i class="icon-settings"></i>
												&nbsp; <i class="fa fa-angle-down"></i> </a>
											<ul class="dropdown-menu pull-right">
												<li class="divider"></li>
												<li><a href="#"> 待完成 <span
														class="badge badge-danger"> 4 </span> </a>
												</li>
												<li><a href="#"> 已完成 <span
														class="badge badge-success"> 12 </span> </a>
												</li>
												<li><a href="#"> 已超时 <span
														class="badge badge-warning"> 9 </span> </a>
												</li>
											</ul>
										</div>
									</div>-->
								</div>
								<div class="portlet-body todo-project-list-content"
									style="height: auto;">
									<div class="todo-project-list">
										<ul class="nav nav-pills nav-stacked  tabparent">
											<li class="active"><a href="javascript:void(0);"
												onclick="Message.changetask(this,0)" data-toggle="tab"
												id="tab0"> <span
													class="badge badge-success badge-active all" data="0">
												</span> 全部 </a>
											</li>
											<li><a href="javascript:void(0);"
												onclick="Message.changetask(this,1)" data-toggle="tab"
												id="tab1"> <span class="badge badge-success task"
													data="1"> </span> 业务消息 </a>
											</li>
											<li><a href="javascript:void(0);"
												onclick="Message.changetask(this,2)" data-toggle="tab"
												id="tab2"> <span class="badge badge-success system"
													data="2"> </span> 系统消息 </a>
											</li>
										</ul>
										<a href="javascript:void(0)" onclick="Message.setAllRead()">设置全部为已读</a>
									</div>
								</div>
							</div>
						</div>
						<div class="todo-content">
							<div class="portlet light">
								<div class="portlet-title">
									<div class="caption">
										<i class="icon-bar-chart font-green-sharp hide"></i> <span
											class="caption-subject font-green-sharp bold uppercase">详情</span>
									</div>

								</div>
								<div class="portlet-body">
								<shiro:hasPermission name="AMESSAGEADD">
									<form class="form-horizontal ng-pristine ng-valid" action="#" id="sendForm" style="display: none">
										<div class="form">

											<!-- END TASK HEAD -->
											<!-- TASK DESC -->
											<div class="form-group">
												<div class="col-md-12">
													<textarea placeholder="消息内容" rows="2"
														class="form-control todo-taskbody-taskdesc taskContent"></textarea>
												</div>
											</div>
											<!-- END TASK DESC -->
											<!-- TASK TAGS -->
											<div class="form-actions right todo-form-actions">
												<button type="button" class=" btn btn-primary  sendMessage"
													id="sendMessage">发送</button>
											</div>
										</div>
									</form>
									</shiro:hasPermission>
									<div class="row">
										<div>
											<div class="slimScrollDiv"
												style="position: relative; overflow: hidden; width: auto; height: 600px; margin-left: 15px;">
												<div data-handle-color="#dae3e7" data-rail-visible="0"
													data-always-visible="0"
													style="max-height: 600px; overflow: hidden; width: auto; height: 600px;"
													class="mScroller" id="tasklist" data-initialized="0">
													<div class="todo-tasklist" id="tlist"></div>
												</div>
												<div class="slimScrollBar"
													style="background: none repeat scroll 0% 0% rgb(218, 227, 231); width: 7px; position: absolute; top: 0px; opacity: 0.4; display: none; border-radius: 7px; z-index: 99; right: 1px; height: 346.487px;"></div>
												<div class="slimScrollRail"
													style="width: 7px; height: 100%; position: absolute; top: 0px; display: none; border-radius: 7px; background: none repeat scroll 0% 0% rgb(234, 234, 234); opacity: 0.2; z-index: 90; right: 1px;"></div>
											</div>
										</div>

									</div>
								</div>
							</div>
						</div>





					</div>
				</div>
			</div>
		</div>
		<!-- End: life time stats -->
	</div>
</div>
<!-- END MAIN CONTENT -->

