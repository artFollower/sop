<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="modal fade">
	<div class="modal-dialog modal-wide" >
		<div class="modal-content"  style="width: 800px;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">审批信息</h4>
			</div>
			<div class="modal-body" style="padding-left:45px; padding-right:65px;">
			<div class="row">
				<div class="col-md-12">
			<form action="#" class="form-horizontal">
				<div class="form-body">
								<div class="form-group">
									<input type="hidden" id="id">
									<label class="control-label col-md-2">提交人</label>
									<div class="col-md-4">
										<input type="text" id="createUserName" maxlength="16" readonly class="form-control" />
									</div>
									<label class="control-label col-md-2">提交时间</label>
									<div class="col-md-4">
										<input type="text" maxlength="16" id="createTime"  readonly class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label">内容</label>
									<div class="col-md-10">
										<textarea class="form-control" maxlength="100" readonly id="content" rows="2"></textarea>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label">理由</label>
									<div class="col-md-10">
										<textarea class="form-control" maxlength="100" readonly id="reason" rows="2"></textarea>
									</div>
								</div>
							<div class="form-group">
							<label class="control-label col-md-2">审批人</label>
									<div class="col-md-4">
										<input type="text" id="reviewUserName" maxlength="16" readonly class="form-control" />
									</div>
							<label class="control-label col-md-2">审批时间</label>
									<div class="col-md-4">
										<input type="text" id="reviewTime" maxlength="16" readonly class="form-control" />
									</div>
								</div>
				</div>
			</form>
	</div>
			</div>
		</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary noPass" id="noPass">不通过</button>
				<button type="button" class="btn btn-primary pass" id="pass">通过</button>
			</div>
		</div>
	</div>
</div>
