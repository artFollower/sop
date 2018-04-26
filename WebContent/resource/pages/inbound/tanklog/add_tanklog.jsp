<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="row">
	<div class="col-md-12">
		<div class="portlet ">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-list-alt"></i>添加储罐台账
				</div>
			</div>
			<div class="portlet-body form">
				<form class="form-horizontal addGoodsForm tank-log-form-add">
					<div classs="form-body" style="padding-top: 10px;">
						<table>
							<tr>
								<!--<td><div class="form-group">
										<label class="control-label col-md-4">事件类型:</label>
										<div class="col-md-8">
										<input type="text" id="type" readonly class="form-control type" value="接卸" ></input> 
											 <select class="form-control select2me type" id="type"   data-placeholder="选择事件类型...">
												<option value="">请选择台账类型...</option>
												<option value="1">接卸</option>
												<option value="2">船发</option>
												<option value="3">打循环</option>
												<option value="4">清罐</option>
												<option value="5">压管线</option>
												<option value="6">倒罐</option>
										</select>
										</div>
									</div>
								</td>-->
								<td><div class="form-group">
										<label class="control-label col-md-4">罐号:<span
											class="required">*</span></label>
										<div class="col-md-8">
											<input id="tank"  type="text" name="tank"
												data-provide="typeahead" class="form-control tank"
												data-required="1" data-type="Require">
										</div>
									</div></td>
								<td><div class="form-group">
										<label class="control-label col-md-4">货品:<span
											class="required">*</span></label>
										<div class="col-md-8">
											<input id="productId" readonly type="text" name="productId"
												data-provide="typeahead" data-required="1"
												data-type="Require" class="form-control productId">
										</div>
									</div></td>
							</tr>
							<tr class="gtime">
								<td class="gstart"><div class="form-group">
										<label class="control-label col-md-4">开始时间:<span
											class="required">*</span></label>
										<div class="col-md-8">
											<div class="input-group">
												<div class="col-md-7"
													style="padding-right: 0px; padding-left: 0px;">
													<input  style="text-align: right; border-right: 0;"
														id="startTime1" key="1" data-required="1"
														data-type="Require"
														class="form-control form-control-inline date-picker col-md-8 startTime1"
														type="text" />
												</div>
												<div class="col-md-5"
													style="padding-left: 0px; padding-right: 0px;">
													<input  style="border-left: 0;" type="text" id="startTime2"
														class="form-control col-md-4  timepicker timepicker-24 startTime2"
														value="00:00:00">
												</div>
											</div>
										</div>
									</div></td>
								<td class="gend"><div class="form-group">
										<label class="control-label col-md-4">结束时间:</label>
										<div class="col-md-8">
											<div class="input-group">
												<div class="col-md-7"
													style="padding-right: 0px; padding-left: 0px;">
													<input  style="text-align: right; border-right: 0;"
														id="endTime1" key="1"
														class="form-control form-control-inline date-picker col-md-8 endTime1"
														type="text" />
												</div>
												<div class="col-md-5"
													style="padding-left: 0px; padding-right: 0px;">
													<input  style="border-left: 0;" type="text" id="endTime2"
														class="form-control col-md-4  timepicker timepicker-24 endTime2" value="00:00:00">
												</div>
											</div>
										</div>
									</div></td>
							</tr>
							<tr>
								<td colspan="2"><h4 class="form-section">前尺</h4></td>
							</tr>
							<tr>
								<td class="col-md-4"><div class="form-group">
										<label class="control-label col-md-4">液位(毫米):</label>
										<div class="col-md-8">
											<input maxlength="12" type="text" id="fLiquidLevel"
												onkeyup="TankLog.addFNum(this)" class="form-control" />
										</div>
									</div></td>
								<td class="col-md-4"><div class="form-group">
										<label class="control-label col-md-4">重量(吨):</label>
										<div class="col-md-8">
											<input maxlength="12" type="text" id="fWeight"
												onkeyup="TankLog.addRNum(this)" class="form-control" />
										</div>
									</div></td>
								<td class="col-md-4"><div class="form-group">
										<label class="control-label col-md-4">温度(度):</label>
										<div class="col-md-8">
											<input maxlength="12" type="text" id="fTemperature"
												onkeyup="TankLog.addSJNum(this)" class="form-control" />
										</div>
									</div></td>
							</tr>
							<tr>
								<td class="col-md-4"><div class="form-group">
										<label class="control-label col-md-4">手检尺液位(毫米):</label>
										<div class="col-md-8">
											<input maxlength="12" type="text" id="fHandLevel"
												onkeyup="TankLog.addFNum(this)" class="form-control" />
										</div>
									</div></td>
								<td class="col-md-4"><div class="form-group">
										<label class="control-label col-md-4">手检尺重量(吨):</label>
										<div class="col-md-8">
											<input maxlength="12" type="text" id="fHandWeight"
												onkeyup="TankLog.addSJNum(this)" class="form-control" />
										</div>
									</div></td>
								<td class="col-md-4"><div class="form-group">
										<label class="control-label col-md-4">液位差异(毫米):</label>
										<div class="col-md-8">
											<input maxlength="14" type="text" id="fDiffer" readonly class="form-control" />
										</div>
									</div></td>
							</tr>
							<tr>
								<td colspan="2"><h4 class="form-section">后尺</h4></td>
							</tr>
							<tr>
								<td class="col-md-4"><div class="form-group">
										<label class="control-label col-md-4">液位(毫米):</label>
										<div class="col-md-8">
											<input maxlength="12" type="text" id="bLiquidLevel"
												onkeyup="TankLog.addBNum(this)" class="form-control" />
										</div>
									</div></td>
								<td class="col-md-4"><div class="form-group">
										<label class="control-label col-md-4">重量(吨):</label>
										<div class="col-md-8">
											<input maxlength="12" type="text" id="bWeight"
												onkeyup="TankLog.addRNum(this)" class="form-control" />
										</div>
									</div></td>
								<td class="col-md-4"><div class="form-group">
										<label class="control-label col-md-4">温度(度):</label>
										<div class="col-md-8">
											<input maxlength="12" type="text" id="bTemperature"
												onkeyup="TankLog.addSJNum(this)" class="form-control" />
										</div>
									</div></td>
							</tr>
							<tr>
								<td class="col-md-4"><div class="form-group">
										<label class="control-label col-md-4">手检尺液位(毫米):</label>
										<div class="col-md-8">
											<input maxlength="12" type="text" id="bHandLevel"
												onkeyup="TankLog.addBNum(this)" class="form-control" />
										</div>
									</div></td>
								<td class="col-md-4"><div class="form-group">
										<label class="control-label col-md-4">手检尺重量(吨):</label>
										<div class="col-md-8">
											<input maxlength="12" type="text" id="bHandWeight"
												onkeyup="TankLog.addSJNum(this)" class="form-control" />
										</div>
									</div></td>
								<td class="col-md-4"><div class="form-group">
										<label class="control-label col-md-4">液位差异(毫米):</label>
										<div class="col-md-8">
											<input maxlength="14" type="text" id="bDiffer" readonly class="form-control" />
										</div>
									</div></td>
							</tr>
							<tr>
								<td colspan="2"><h4 class="form-section">统计</h4></td>
							</tr>
							<tr>
								<td><div class="form-group">
										<label class="control-label col-md-4">机房重量(吨):</label>
										<div class="col-md-8">
											<input maxlength="14" type="text" id="outWeight" readonly
												class="form-control" />
										</div>
									</div></td>
								<td><div class="form-group">
										<label class="control-label col-md-4">手检计量(吨):</label>
										<div class="col-md-8">
											<input maxlength="14" type="text" id="handWeight" readonly
												class="form-control" />
										</div>
									</div></td>
							</tr>
							<tr>
								<td colspan="2"><div class="form-group">
										<label class="control-label col-md-2">事件:<span
											class="required">*</span></label>
										<div class="col-md-10">
											<textarea maxlength="100" type="text" id="message1"
												class="form-control message1" rows=1 data-required="1" data-type="Require" ></textarea>
										</div>
									</div></td>

							</tr>
						</table>
					</div>
				</form>
			</div>
		</div>
		<div class="modal-footer">
			<input type="hidden" class="form-control transportId"
				name="transportId" />
			<button type="button" class="btn btn-default" data-dismiss="modal"
				onclick="window.location='#/tanklog'">返回</button>
			<shiro:hasPermission name="ASTORELEDGEUPDATE">
				<button type="button" class="btn btn-success"
					onclick="TankLog.doAdd()" id="save">提交</button>
			</shiro:hasPermission>
		</div>
	</div>
</div>
<script>
	$('.date-picker').datepicker({
		rtl : Metronic.isRTL(),
		orientation : "left",
		format : "yyyy-mm-dd",
		autoclose : true
	});
	$('body').removeClass("modal-open");
</script>