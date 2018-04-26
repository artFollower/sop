<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!-- BEGIN HEADER INNER -->
<div class="page-header-inner">
	<!-- BEGIN LOGO -->
	<div class="page-logo">
		<a href="#/">  
		<img src="<%=basePath %>/resource/admin/layout/img/logo.png" alt="logo" class="logo-default"/>
		</a>
		<div class="menu-toggler sidebar-toggler">
			<!-- DOC: Remove the above "hide" to enable the sidebar toggler button on header -->
		</div>
	</div>
	<!-- END LOGO -->
	<!-- BEGIN HEADER SEARCH BOX -->
	<!-- DOC: Apply "search-form-expanded" right after the "search-form" class to have half expanded search box -->
	<form class="search-form hide" action="extra_search.html" method="GET">
		<div class="input-group">
			<input type="text" class="form-control" placeholder="Search..." name="query">
			<span class="input-group-btn">
			<a href="javascript:;" class="btn submit"><i class="icon-magnifier"></i></a>
			</span>
		</div>
	</form>
	<!-- END HEADER SEARCH BOX -->
	<!-- BEGIN RESPONSIVE MENU TOGGLER -->
	<a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse">
	</a>
	<!-- END RESPONSIVE MENU TOGGLER -->
	<!-- BEGIN TOP NAVIGATION MENU -->
	<div class="top-menu">
		<ul class="nav navbar-nav pull-right">
			<!-- BEGIN NOTIFICATION DROPDOWN -->
			<!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
			<li class="dropdown dropdown-extended dropdown-inbox" id="header_inbox_bar" id="message-contaner">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
				<i class="fa fa-comments"></i>
				<span class="badge badge-default  message-number"></span>
				</a>
				<ul class="dropdown-menu">
					<li class="external">
						<h3><span class="bold message-number">0</span> 条未读消息</h3>
						<a href="#/message">查看所有</a>
					</li>
					<li>
						<ul class="dropdown-menu-list scroller" style="height: 275px;" data-handle-color="#637283" id="message-head-list">
						</ul>
					</li>
				</ul>
			</li>
			<!-- END INBOX DROPDOWN -->
			<!-- BEGIN USER LOGIN DROPDOWN -->
			<!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
			<li class="dropdown dropdown-user">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
				<img alt="" class="img-circle" src="<%=basePath %>/resource/admin/layout/img/avatar3_small.jpg"/>
				<span class="username username-hide-on-mobile">
				<shiro:principal property="name"/> </span>
				<i class="fa fa-angle-down"></i>
				</a>
				<ul class="dropdown-menu dropdown-menu-default">
					<li>
						<a href="#/profile/dashboard">
						<i class="fa fa-desktop"></i> 我的控制台 </a>
					</li>
					<li>
						<a href="#/message">
						<i class="icon-envelope-open"></i> 我的消息 <span class="badge badge-danger  message-number"></span>
						</a>
					</li>
					<!-- <li>
						<a href="#/todo">
						<i class="icon-rocket"></i> 我的任务 <span class="badge badge-success">7 </span>
						</a>
					</li> -->
					<li class="divider">
					<li>
						<a href="#/user/acount?id=<shiro:principal property="id"/>">
						<i class="icon-user"></i> 我的信息 </a>
					</li>
					<li>
						<a href="javascript:User.changeMyPaswd(<shiro:principal property="id"/>)">
						<i class="fa fa-lock"></i> 修改密码 </a>
					</li>
					<li class="divider">
					</li>
					<li>
						<a href="javascript:User.logout('<%= application.getAttribute("logoutUrl") %>','<%= application.getAttribute("loginUrl") %>')">
						<i class="icon-key"></i> 退出 </a>
					</li>
				</ul>
			</li>
			<!-- END USER LOGIN DROPDOWN -->
			<!-- BEGIN LANGUAGE BAR -->
			<!-- END LANGUAGE BAR -->
			<!-- BEGIN QUICK SIDEBAR TOGGLER -->
			<!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
			 <li class="dropdown dropdown-quick-sidebar-toggler">
				<a href="javascript:;" class="dropdown-toggle">
				<i class="fa fa-comments"></i><span class="badge badge-danger"  id="IMCount" ></span>
				</a>
			</li> 
			<!-- END QUICK SIDEBAR TOGGLER -->
		</ul>
	</div>
	<!-- END TOP NAVIGATION MENU -->
</div>
<!-- END HEADER INNER -->