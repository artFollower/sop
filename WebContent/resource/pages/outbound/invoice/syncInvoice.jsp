<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<style>
.modal-dialog {
	margin: 30px auto;
	width: 60%;
}
</style>
<div class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title">手动同步流量计</h4><span class="pull-right">
		 </span>
			</div>
			<div class="modal-body" style="padding-left: 25px; padding-right: 35px;">
				<div class="form-body">
				 <form action="#" class="form-horizontal">
				 	<div class="row">
				 		<div class="form-group">
				 			<label class="control-label col-md-2">通知单号:</label>
				 			<div class="col-md-4">
				 				<input type="text" id="serial" class="form-control" />
				 			</div>
				 			<div class="col-md-2 input-group-btn" >
							<button type="button" class="btn btn-success" id="syncInvoice">
								<span class="fa fa-exchange"></span>&nbsp;手动同步
							</button>
							</div>
				 		</div>
				 	</div>
				 </form>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>

</div>

