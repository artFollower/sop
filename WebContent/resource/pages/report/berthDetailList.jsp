<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<style>
.modal-dialog {
	margin: 30px auto;
	width: 100%;
}
</style>
<div class="modal fade ">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">泊位利用率明细</h4>
			</div>
			<div class="modal-body">
				<div class="row">
				<div class="col-md-12 searchDiv" style="padding-left: 45px; padding-right: 65px;">
					<form   target="_self" class="form-horizontal searchCondition">
					<div class="row">
					<div class="form-body">
					<div class="form-group">
					<div class="col-md-3">
					<label class="col-md-4 control-label">货品</label>
					<div class="col-md-8">
					<input id="productId" class="form-control">
					</div>
					</div>
					<div class="col-md-3">
					<label class="col-md-4 control-label">泊位</label>
					<div class="col-md-8">
					<input id="berthId" class="form-control">
					</div>
					</div>
					<div class="col-md-3">
					<label class="col-md-2 control-label">月份</label>
					<div class="col-md-10">
					<div class="input-group date-picker input-daterange" data-date-format="yyyy-mm-dd">
						<input type="text" class="form-control" name="startTime" id="startTime">
					</div>
					</div>
					</div>
					<div class="col-md-3">
					<div class="input-group-btn">
									<button type="button" class="btn btn-success"  id="search">
										<span class="glyphicon glyphicon-search"></span>&nbsp;
									</button>

								</div>
					</div>
					</div></div></div>
					</form>
				</div>
				<div class="col-md-12" >
					<div  data-role="berthDetailTable"></div>
				</div>
				</div>
			</div>
		</div>
	</div>
</div>