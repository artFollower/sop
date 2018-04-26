<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<style type="text/css">
.modal-dialog {
	margin: 30px auto;
	width: 65%;
}
</style>
<div class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">评估信息</h4>
			</div>
			<div class="modal-body" style="padding-left:45px; padding-right:65px;">
			<!-- BEGIN FORM-->
			<form class="form-horizontal">
				<div class="form-body">
								<div class="form-group">
									<label class="control-label col-md-3">拟靠离泊位号:</label>
									<div class="col-md-3">
										<label class="control-label" id="berthId"></label>
									</div>
									<label class="control-label col-md-3">天气情况:</label>
									<div class="col-md-3">
										<label class="control-label" id="weather"></label>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">风向:</label>
									<div class="col-md-3">
										<label class="control-label" id="windDirection"></label>
									</div>
									<label class="control-label col-md-3">风力:</label>
									<div class="col-md-3">
										<label class="control-label" id="windPower"></label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">安全措施:</label>
									<div class="col-md-9">
										<textarea class="form-control" rows=3 id="security"></textarea>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">申请原因:</label>
									<div class="col-md-9">
										<textarea class="form-control" rows=3 id="reason"></textarea>
									</div>
								</div>
								<div class="form-group commentDiv">
									<label class="col-md-3 control-label">评估组意见:</label>
									<div class="col-md-9">
										<textarea class="form-control description"   rows="3" id="comment"></textarea>
									</div>
								</div>
								<div class="form-group">
								<label class="col-md-3 control-label">制定人:</label>
								<label class="col-md-3 control-label" style="text-align:left" id="createUserId"></label>
								<label class="col-md-3 control-label">制定日期:</label>
								<label class="col-md-3 control-label" style="text-align:left" id="createTime"></label>
								</div>
								<div class="form-group">
								<label class="col-md-3 control-label">审核人:</label>
								<label class="col-md-3 control-label" style="text-align:left" id="reviewUserId"></label>
								<label class="col-md-3 control-label">审核日期:</label>
								<label class="col-md-3 control-label" style="text-align:left" id="reviewTime"></label>
								</div>
				</div>
				</form>
		</div>
<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
               <shiro:hasPermission name="ABACKSTATUS">
				<button type="button" class="btn btn-primary back" data-dismiss="modal" onclick="InboundOperation.cleanToStatus(3)">回退到当前状态</button>
				</shiro:hasPermission>
			</div>
		</div>
	</div>
</div>