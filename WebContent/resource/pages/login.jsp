<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path;
%>
<html>
<head>
<title>业务管理系统</title>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="<%=basePath%>/resource/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>/resource/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>/resource/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link href="<%=basePath%>/resource/admin/pages/css/login-soft.css" rel="stylesheet" type="text/css" />
<!-- END PAGE LEVEL SCRIPTS -->
<!-- BEGIN THEME STYLES -->
<link href="<%=basePath%>/resource/global/css/components.css" id="style_components" rel="stylesheet" type="text/css" />
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="favicon.ico" />
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="login">
	<!-- BEGIN LOGO -->
	<div class="logo">
		<a href="javascript:void(0)"> <img src="<%=basePath%>/resource/admin/layout/img/logo-big.png" alt="" />
		</a>
	</div>
	<!-- END LOGO -->
	<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
	<div class="menu-toggler sidebar-toggler"></div>
	<!-- END SIDEBAR TOGGLER BUTTON -->
	<!-- BEGIN LOGIN -->
	<div class="content">
		<!-- BEGIN LOGIN FORM -->
		<form class="login-form">
			<h3 class="form-title">登录</h3>
			<div class="alert alert-danger display-hide">
				<button class="close" data-close="alert"></button>
				<span> 输入账号密码. </span>
			</div>
			<div class="form-group">
				<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
				<label class="control-label visible-ie8 visible-ie9">账号</label>
				<div class="input-icon">
					<i class="fa fa-user"></i> <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="账号" name="account" value="" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label visible-ie8 visible-ie9">密码</label>
				<div class="input-icon">
					<i class="fa fa-lock"></i> <input class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="密码" name="password" value="" />
				</div>
			</div>
			<div class="form-actions">
				<button type="button" class="btn btn-success uppercase login-button">登录</button>
				<!-- <label class="rememberme check"> <input type="checkbox" name="rememberMe" value="1" />记住我 -->
				</label> <a href="javascript:;" id="forget-password" class="forget-password">忘记密码?</a>
			</div>
		</form>
		<!-- END LOGIN FORM -->
		<!-- BEGIN FORGOT PASSWORD FORM -->
		<form class="forget-form">
			<div class="form-group" style="height: 36px;">
				请联系管理员!
			</div>
			<div class="form-actions">
				<button type="button" id="back-btn" class="btn back-btn">
					<i class="m-icon-swapleft"></i> 返回
				</button>
			</div>
			<!-- <ul class="nav nav-tabs">
				<li class="active">
					<a href="#tab_1_1" data-toggle="tab">邮箱找回</a>
				</li>
				<li>
					<a href="#tab_1_2" data-toggle="tab">手机找回</a>
				</li>
			</ul>
			<div class="tab-content">
				<div class="tab-pane active" id="tab_1_1">
					<div class="form-group">
						<div class="input-icon">
							<i class="fa fa-envelope"></i> <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="邮箱" name="email" />
						</div>
					</div>
					<div class="form-actions">
						<button type="button" id="back-btn" class="btn back-btn">
							<i class="m-icon-swapleft"></i> 返回
						</button>
						<button type="submit" class="btn blue pull-right">
							找回 <i class="m-icon-swapright m-icon-white"></i>
						</button>
					</div>
				</div>
				<div class="tab-pane" id="tab_1_2">
					<div class="form-group">
						<div class="input-icon">
							<i class="fa fa-phone"></i> <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="手机号" name="phone" />
						</div>
					</div>
					<div class="form-group" style="height:36px;">
						<input class="form-control placeholder-no-fix input-small pull-left" type="text" autocomplete="off" placeholder="验证码" name="code" />
						<button type="button" class="btn blue pull-right">发送验证码</button>
					</div>
					<div class="form-actions">
						<button type="button" id="back-btn" class="btn back-btn">
							<i class="m-icon-swapleft"></i> 返回
						</button>
						<button type="submit" class="btn blue pull-right">
							找回 <i class="m-icon-swapright m-icon-white"></i>
						</button>
					</div>
				</div>
			</div> -->
		</form>
		<!-- END FORGOT PASSWORD FORM -->

	</div>
	<!-- END LOGIN -->
	<!-- BEGIN COPYRIGHT -->
	<!-- START JAVASCRIPTS -->
	<script src="<%=basePath%>/resource/global/plugins/jquery.min.js" type="text/javascript"></script>
	<!--<script src="<%=basePath%>/resource/global/plugins/jquery-2.0.3.min.js" type="text/javascript"></script>-->

	
	<script src="<%=basePath%>/resource/global/plugins/jquery-migrate.min.js" type="text/javascript"></script>
	<script src="<%=basePath%>/resource/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="<%=basePath%>/resource/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script src="<%=basePath%>/resource/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
	<script src="<%=basePath%>/resource/global/plugins/backstretch/jquery.backstretch.min.js" type="text/javascript"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="<%=basePath%>/resource/global/scripts/metronic.js" type="text/javascript"></script>
	<script src="<%=basePath%>/resource/admin/js/scripts/auth/login.js" type="text/javascript"></script>
	
	
	<%-- <script src="<%=basePath%>/resource/admin/js/scripts/IM/jsjac.js" type="text/javascript"></script>
	<script src="<%=basePath%>/resource/admin/js/scripts/IM/jquery.easydrag.js" type="text/javascript"></script>
	<script src="<%=basePath%>/resource/admin/js/scripts/IM/local.chat-2.0.js" type="text/javascript"></script>
	<script src="<%=basePath%>/resource/admin/js/scripts/IM/remote.jsjac.chat-2.0.js" type="text/javascript"></script>
	<script src="<%=basePath%>/resource/admin/js/scripts/IM/send.message.editor-1.0.js" type="text/javascript"></script> --%>
	
	<!-- END PAGE LEVEL SCRIPTS -->
	<script>
		$(function() {
			domain = '<%=basePath%>';
			  Metronic.init(); // init metronic core components
			  Login.init();
		       // init background slide images
		       $.backstretch([
		        "<%=basePath%>/resource/admin/pages/media/bg/1.jpg",
		        "<%=basePath%>/resource/admin/pages/media/bg/2.jpg",
		        "<%=basePath%>/resource/admin/pages/media/bg/3.jpg",
				"<%=basePath %>/resource/admin/pages/media/bg/4.jpg"
		        ], {
		          fade: 1000,
		          duration: 8000
		    });
		});
	</script>
	
	<script type="text/javascript">    
	window.contextPath = "<%=path%>";    
	window["serverDomin"] = "192.168.3.116";
	</script>
	
	<!-- END JAVASCRIPTS -->
</body>
</html>
