<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<style>
.modal-dialog {
	margin: 30px auto;
	width: 75%;
}
</style>
<div class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title">流量计台账明细</h4>
			</div>
			<div class="modal-body" style="padding-left: 25px; padding-right: 35px;">
				<div class="portlet box blue" style="margin-bottom: 0px;">
					<div class="portlet-title"></div>
					<div class="portlet-body cflltz">
						<form action="#" class="form-horizontal ">
							<input type="hidden" id="arrivalId" /> 
							<input type="hidden" id="id">
							<div class="form-body">
								<div class="form-group">
									<label class="control-label col-md-2">开始时间:</label>
									<div class="col-md-4">
										<div class="input-group startTime">
											<div class="col-md-7"
												style="padding-right: 0px; padding-left: 0px;">
												<input style="text-align: right; border-right: 0;"
													id="startTime1" disabled="disabled"
													class="form-control date-picker" />
											</div>
											<div class="col-md-5"
												style="padding-left: 0px; padding-right: 0px;">
												<input style="border-left: 0;" id="startTime2"
													disabled="disabled"
													class="form-control timepicker timepicker-24 ">
											</div>
										</div>
									</div>
									<label class="control-label col-md-2">结束时间:</label>
									<div class="col-md-4">
										<div class="input-group endTime">
											<div class="col-md-7"
												style="padding-right: 0px; padding-left: 0px;">
												<input style="text-align: right; border-right: 0;"
													id="endTime1" disabled="disabled"
													class="form-control  date-picker " />
											</div>
											<div class="col-md-5"
												style="padding-left: 0px; padding-right: 0px;">
												<input style="border-left: 0;" id="endTime2"
													disabled="disabled"
													class="form-control  timepicker timepicker-24 ">
											</div>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">船名</label>
									<div class="col-md-4">
										<input id="shipName" disabled="disabled" class="form-control" />
									</div>
									<label class="control-label col-md-2">泊位</label>
									<div class="col-md-4">
										<input id="berthName" disabled="disabled" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">罐号</label>
									<div class="col-md-4">
										<input id="tankName" disabled="disabled" class="form-control">
									</div>
									<label class="control-label col-md-2">管线号</label>
									<div class="col-md-4">
										<input id="tubeName" disabled="disabled" class="form-control">
									</div>

								</div>
								<div class="form-group">
									<label class="control-label col-md-2">检尺人</label>
									<div class="col-md-4">
										<input class="form-control " disabled="disabled"
											id="checkUserName" />
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">前尺（吨）</label>
									<div class="col-md-4">
										<input id="startLevel"
											onkeyup="config.clearNoNum(this);shipDeliveryMeasure.flowVal(this);"
											class="form-control " maxlength="16">
									</div>
									<label class="control-label col-md-2">后尺（吨）</label>
									<div class="col-md-4" id="select-type">
										<input id="endLevel"
											onkeyup="config.clearNoNum(this);shipDeliveryMeasure.flowVal(this);"
											class="form-control" maxlength="16">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">流量计（吨）</label>
									<div class="col-md-4">
										<input id="flowmeter" disabled="disabled" class="form-control">
									</div>
									<label class="control-label col-md-2">流-计</label>
									<div class="col-md-4">
										<input id="flowmeterDiff" disabled="disabled"
											class="form-control ">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">机房实发量（吨）</label>
									<div class="col-md-4">
										<input id="realAmount" class="form-control"
											disabled="disabled">
									</div>
									<label class="control-label col-md-2">雷-计</label>
									<div class="col-md-4">
										<input id="meteringDiff"  
											class="form-control " disabled="disabled">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">船方（吨）</label>
									<div class="col-md-4">
										<input id="shipAmount"
											onkeyup="config.clearNoNum(this);shipDeliveryMeasure.shipVal(this,this.value);"
											class="form-control">
									</div>
									<label class="control-label col-md-2">船-计</label>
									<div class="col-md-4">
										<input id="shipAmountDiff" disabled="disabled"
											class="form-control ">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">计量（吨）</label>
									<div class="col-md-4">
										<input id="metering" onkeyup="config.clearNoNum(this);shipDeliveryMeasure.meteringVal(this,this.value);" class="form-control">
									</div>
								</div>
							</div>
						</form>
					</div>
							
				</div>
			</div>
			<div class="modal-footer">
								<div class="col-md-offset-3 col-md-9">
									<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
									<a href="javascript:void(0)" id="submit" class="btn btn-primary">提交</a>
								</div>
							</div>
		</div>
	</div>
</div>
