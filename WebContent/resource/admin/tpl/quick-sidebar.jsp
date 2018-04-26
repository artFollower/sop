<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<div class="page-quick-sidebar">
	<div class="nav-justified">
		<ul class="nav nav-tabs nav-justified">
			<li class="active">
				<a href="#" data-target="#quick_sidebar_tab_1" data-toggle="tab" data-hover="dropdown" data-close-others="true">
				用户状态: <span class="text state text-green"></span>
				</a>
			</li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane active page-quick-sidebar-chat" id="quick_sidebar_tab_1">
				<div class="page-quick-sidebar-chat-users" data-rail-color="#ddd" data-wrapper-class="page-quick-sidebar-list">
					<ul class="media-list list-items"></ul>
				</div>
				<div class="page-quick-sidebar-item">
					<div class="page-quick-sidebar-chat-user">
						<div class="page-quick-sidebar-nav">
							<a href="javascript:;" class="page-quick-sidebar-back-to-list"><i class="icon-arrow-left"></i>返回</a>
						</div>
						<div class="page-quick-sidebar-chat-user-messages">
						</div>
						<div class="page-quick-sidebar-chat-user-form">
							<div class="input-group">
								<input type="text" class="form-control" placeholder="">
								<div class="input-group-btn">
									<button type="button" class="btn blue"><i class="icon-paper-clip"></i></button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>