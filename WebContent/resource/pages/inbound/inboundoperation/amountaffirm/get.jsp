<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
	<div class="col-md-12">
		<div class="portlet box grey">
			<div class="portlet-title hidden">
				<div class="caption">查看入库数量审核</div>
			</div>
			<div class="portlet-body form amountAffirm">
				<form action="#" class="form-horizontal">
				<div class="form-body">
					<h4 class="form-section">货主列表</h4>
				<div class="form-group col-md-6">
				<label class="control-label col-md-4" id="tp1">入库日期:</label>
				<label class="control-label col-md-8" style="text-align:left;" id="inboundTime"></label>
				</div>
					<div class="form-group">
					<div class="col-md-12">
					<a  id="inboundPrint" class="hidden-print" onclick="InboundPrint.print(this)">
		 			<i class="fa fa-print">&nbsp;打印</i></a>
						<div data-role="clientAmountGrid" style="width: 100%"></div>
						</div>
						</div>
					<div class="form-group createDiv" >
							<div class="col-md-6">
								<label class="control-label col-md-4">制定人:</label>
								<div class="col-md-8">
									<label class="control-label" id="createUserId" ></label>
								</div>
							</div>
							<div class="col-md-6">
								<label class="control-label col-md-4">制定时间:</label> <label
									class="control-label" id="createTime"></label>
							</div>
						</div>
						<div class="form-group reviewDiv">
							<div class="col-md-6">
								<label class="control-label col-md-4">审核人:</label>
								<div class="col-md-8">
									<label class="control-label" id="reviewUserId" ></label>
								</div>
							</div>
							<div class="col-md-6">
								<label class="control-label col-md-4">审核时间:</label> <label
									class="control-label" id="reviewTime"></label>
							</div>
						</div>
						</div>
				</form>
			</div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal" onclick="javascript:history.go(-1);">返回</button>
		<shiro:hasPermission name="ABACKSTATUS">
		<button type="button" class="btn btn-primary" onclick="InboundOperation.cleanToStatus(8)">回退到当前状态</button>
		</shiro:hasPermission>
		</div>
	</div>
<script>
	$(function() {
		InboundOperation.initAmountAffirm($(".amountAffirm"), 2);
	});

	$(".form_datetime").datetimepicker({
		autoclose : true,
		isRTL : Metronic.isRTL(),
		format : "yyyy-mm-dd hh:ii:ss",
		pickerPosition : (Metronic.isRTL() ? "bottom-right" : "bottom-left")
	});
	var formatlong = function(time) {
		if (time == "" || time == " 00:00:00") {
			return "";
		} else {
			var time2 = time.replace(/-/g, "/");
			var d = new Date(time2);
			return d.getTime() / 1000;
		}
		return "";
	};
</script>