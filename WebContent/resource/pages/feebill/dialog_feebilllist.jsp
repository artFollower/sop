<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<style>
<!--
#flow {
	width: 100%;
}
-->
</style>
<div class="modal fade addLivingFeeBill">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">账单列表</h4>
			</div>
			<div class="modal-body">
				<div class="row">
				<div class="col-md-12" style="padding-left: 25px; padding-right: 25px;">
					<div class="col-md-12" data-role="feeBillListTable"></div>
				</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal"  class="btn btn-default flowClose" id="close">取消</button>
			</div>
		</div>
	</div>
</div>