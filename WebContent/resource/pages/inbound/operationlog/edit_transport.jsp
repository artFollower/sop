<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="modal fade">
	<div class="modal-dialog modal-wide" style="width: 800px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">修改接卸信息</h4>
			</div>
			<div class="modal-body" style="padding-left:45px; padding-right:65px;">
			<div class="row">
				<div class="col-md-12">
					<form class="form-horizontal addGoodsForm">
						<div class="form-group">
							<table width="100%">
						<tr>
								<td colspan="2"><h4 class="form-section">接卸信息</h4>
								</td>
							</tr>
						
						
						<tr>
							<td class=" col-md-4 gOpenPump" >
								<div class="form-group ">
										<label class="control-label col-md-4">开泵时间:</label>
										<div class="col-md-8">
											<div class="input-group">
											         <div class="col-md-7" style="padding-right: 0px;padding-left: 0px;"><input maxlength="16" style="text-align:right;border-right:0;" id="openPump1" key="1"  class="form-control form-control-inline date-picker col-md-8 openPump1"  type="text"/>
												 </div>
											         <div class="col-md-5" style="padding-right: 0px;padding-left: 0px;"><input maxlength="16" style="border-left:0;" type="text"  id="openPump2" class="form-control col-md-4  timepicker timepicker-24 openPump2" > </div>
												</div>
										</div>
									</div>
							</td>
							
							<td class=" col-md-4 gStopPump">
								<div class="form-group">
										<label class="control-label col-md-4">停泵时间:</label>
										<div class="col-md-8">
											<div class="input-group">
											         <div class="col-md-7" style="padding-right: 0px;padding-left: 0px;"><input maxlength="16" style="text-align:right;border-right:0;" id="stopPump1" key="1"  class="form-control form-control-inline date-picker col-md-8 stopPump1"  type="text" />
												 </div>
											         <div class="col-md-5" style="padding-right: 0px;padding-left: 0px;"><input maxlength="16" style="border-left:0;" type="text"  id="stopPump2" class="form-control col-md-4  timepicker timepicker-24 stopPump2" > </div>
												</div>
										</div>
									</div>
							</td>
							</tr>
							
							<tr>
							<td class=" col-md-4 gTearPipeTime" >
								<div class="form-group">
										<label class="control-label col-md-4">拆管时间:</label>
										<div class="col-md-8">
											<div class="input-group">
											         <div class="col-md-7" style="padding-right: 0px;padding-left: 0px;"><input maxlength="16" style="text-align:right;border-right:0;" id="tearPipeTime1" key="1"  class="form-control form-control-inline date-picker col-md-8 tearPipeTime1"  type="text" />
												 </div>
											         <div class="col-md-5" style="padding-right: 0px;padding-left: 0px;"><input maxlength="16" style="border-left:0;" type="text"  id="tearPipeTime2" class="form-control col-md-4  timepicker timepicker-24 tearPipeTime2" > </div>
												</div>
										</div>
									</div>
							</td>
							
							
							</tr>
							
							
							<!--<tr>
							<td><div class="form-group">
										<label class="control-label col-md-4">送达时间:</label>
										<div class="col-md-8">
										<div class="input-group">
											         <div class="col-md-7" style="padding-right: 0px;"><input style="text-align:right;border-right:0;" id="notificationTime1" key="1"  class="form-control form-control-inline date-picker col-md-8 notificationTime1"  type="text" />
												 </div>
											         <div class="col-md-5" style="padding-left: 0px;"><input style="border-left:0;" type="text"  id="notificationTime2" class="form-control col-md-4  timepicker timepicker-24 notificationTime2" > </div>
												</div>
												</div>
									</div>
								</td>
								<td><div class="form-group">
										<label class="control-label col-md-4">报告号:</label>
										<div class="col-md-8">
											<input type="text" id="notificationNum"  class="form-control notificationNum" />
										</div>
									</div>
								</td>
								</tr>
								<tr>
								<td><div class="form-group">
										<label class="control-label col-md-4">验证结论:</label>
										<div class="col-md-8">
											<input type="text" id="notification" class="form-control notification" />
										</div>
									</div>
								</td>
								<td><div class="form-group">
										<label class="control-label col-md-4">通知人:</label>
										<div class="col-md-8">
											<input type="text" id="notificationUserName" data-provide="typeahead" class="form-control notificationUserName" />
										</div>
									</div>
								</td>
								</tr>
								
								 <tr>
								<td  colspan="2"><div class="form-group">
										<label class="control-label col-md-2">备注:</label>
										<div class="col-md-10">
											<textarea type="text" id="message"
												class="form-control message" rows=1></textarea>
										</div>
									</div>
								</td>
								</tr> -->
								
							</table>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
				<button type="button" class="btn btn-primary update"  >保存</button>
			</div>
		</div>
	</div>
</div>
<script>
		
	
		
	</script>