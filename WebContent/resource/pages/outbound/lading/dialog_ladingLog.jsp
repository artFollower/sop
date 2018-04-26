<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<style>
<!--
#flow {
	width: 100%;
}
.modal-dialog {
	margin: 30px auto;
	width: 900px;
}
</style>
<div class="modal fade addLivingFeeBill">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">流转列表</h4>
			</div>
			<div class="modal-body">
			
			<h3 style="text-align: center;font-family:宋体">客户发货清单</h3>
				<div class="row">
				<h6 style="padding-left: 25px; padding-right: 25px;font-family:宋体;text-align: right ; float:right" id="time"></h6>
				<h4 style="padding-left: 25px; padding-right: 25px;font-family:宋体" id="client"></h4>
				<div class="col-md-12" style="padding-left: 25px; padding-right: 25px;">
				 <div class="scroller" style="max-height:650px;overflow-y:scroll;">
					<div class="col-md-12" data-role="trunlistTable"></div>
					</div>
				</div>
				</div>
			</div>
			<div class="modal-footer">
			<button type="button"   class="btn btn-default" id="excel">导出excel</button>
			<button type="button"   class="btn btn-default" id="print">打印</button>
				<button type="button" data-dismiss="modal"  class="btn btn-default" id="close">取消</button>
			</div>
		</div>
	</div>
</div>