<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<style>
.logDiv {
	margin: 30px auto;
	width: 80%;
}
</style>
<div class="modal fade ">
	<div class="modal-dialog  logDiv">

		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">冲销历史纪录</h4>
			</div>
			<div class="modal-body">
				<div class="row">
				<div class="col-md-12" style="padding-left: 25px; padding-right: 25px;">
					<div data-role="logGrid"></div>
				</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal" >关闭</button>
			</div>
		</div>
	</div>
</div>