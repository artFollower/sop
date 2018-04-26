<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<style>
.userGrantAuthority {
	display: block;
	text-align: center;
}

.left,.middle,.right {
	display: inline-block;
	font-size: 14px;
	vertical-align: middle;
}

.left {
	width: 350px;
}

.middle {
	width: 85px;
	text-align: center;
}

.right {
	width: 350px;
}

.grid-body {
	height: 500px;
}

.grid-table-body {
	min-height: 400px;
	max-height: 460px;
}

.radio-inline + .radio-inline, .checkbox-inline + .checkbox-inline {
	margin-left: 0px;
}
hr {
	margin-top: 10px; 
	margin-bottom: 10px;
}
</style>

<div class="portlet box grey">
	<div class="portlet-title">
		<div class="caption">用户角色分配</div>
	</div>
	
	<div class="userGrantAuthority portlet-body form">
		<div class="left">
			<div class="right-modal-body">未授权角色</div>
			<div id="notGrantAuthoritiesToUserGrid"></div>
		</div>
		<div class="middle">
			<button class="btn btn-danger glyphicon glyphicon-chevron-left" id="notGrantAuthorityToUserButton">&nbsp;撤权</button>
			<br /> <br /> <br /> <br />
			<button class="btn btn-success glyphicon glyphicon-chevron-right" id="grantAuthorityToUserButton">&nbsp;授权</button>
			<br /> <br /> <br /> <br />
			<div id="grantAuthorityToUserMessage"></div>
		</div>
		<div class="right">
			<div class="left-modal-body">已授权角色</div>
			<div id="grantAuthoritiesToUserGrid"></div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal" onclick="window.location.href='#/user/list'">返回</button>
		</div>
	</div>
</div>
