<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<style>
.staticsDay{
	margin: 30px auto;
	width: 45%;
}
</style>
<div class="modal fade">
	<div class="modal-dialog staticsDay">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title">报表记录</h4>
			</div>
			<div class="modal-body" style="padding-left: 45px; padding-right: 65px;">
				<div class="row">
					<div class="col-md-12">
						<label class="control-label col-md-4">统计时间:</label>
						<div class="col-md-6">
							<input id="time" class=" form-control  date-picker" />
						</div>
					</div>
					<div class="col-md-12" data-role="dayGrid"></div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
			</div>
		</div>
	</div>
</div>