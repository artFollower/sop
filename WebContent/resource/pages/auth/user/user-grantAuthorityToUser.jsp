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
</style>
<div class="modal fade">
	<div class="modal-dialog" style="width: 900px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title center">授权角色</h4>
			</div>
			<div class="userGrantAuthority modal-body">
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
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
			</div>
		</div>
	</div>
</div>
