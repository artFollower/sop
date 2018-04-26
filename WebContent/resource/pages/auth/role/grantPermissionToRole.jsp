<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<style>
.roleGrantPermission {
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
	height:500px;
}
.grid-table-body {
	min-height:400px;
	max-height:460px;
}
</style>

<div class="portlet box grey">
	<div class="portlet-title">
		<div class="caption">角色权限分配</div>
	</div>
	<div class="roleGrantPermission portlet-body form">
		<div class="left">
			<div class="right-modal-body">未授予权限</div>
			<div id="notGrantPermissionToRoleGrid"></div>
		</div>
		<div class="middle">
			<button class="btn btn-danger glyphicon glyphicon-chevron-left" id="notGrantPermissionToRoleButton">&nbsp;撤权</button>
			<br /> <br /> <br /> <br />
			<button class="btn btn-success glyphicon glyphicon-chevron-right" id="grantPermissionToRoleButton">&nbsp;授权</button>
			<br /> <br /> <br /> <br />
			<div id="grantPermissionToRoleMessage"></div>
		</div>
		<div class="right">
			<div class="left-modal-body">已授予权限</div>
			<div id="grantPermissionToRoleGrid"></div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal" onclick="window.location.href='#/role/list'">返回</button>
		</div>
	</div>
</div>
