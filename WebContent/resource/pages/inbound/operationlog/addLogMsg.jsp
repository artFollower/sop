<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="modal fade">
	<div class="modal-dialog modal-wide">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title">添加调度作业记录</h4>
			</div>
			<div class="modal-body"
				style="padding-left:45px; padding-right:65px;">
				<div class="row">
					<div class="col-md-12">
						<form class="form-horizontal addGoodsForm">
							<div class="form-group">
								<label class="control-label col-md-4">时间</label>
								<div class="col-md-8">
									<div class="input-group">
										<input type="text" maxlength="16"
											class="form-control timepicker timepicker-24 " id="time">
									</div>
								</div>
							</div>
					<div class="form-group">
						<label class="control-label col-md-4">内容</label>
						<div class="col-md-8">
							<textarea class="form-control content" maxlength="128" rows="3" name="content"></textarea>
						</div>

					</div>


					</form>
					</div>
				</div>
			</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
			<%-- 				<shiro:hasPermission name="user:create"><button type="button" class="btn btn-success" id="save">保存</button></shiro:hasPermission> --%>
			<button type="button" class="btn blue" id="save">提交</button>
		</div>
		</div>
	</div>
</div>
</div>
<script>
	
</script>