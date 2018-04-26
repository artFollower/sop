<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="modal fade">
	<div class="modal-dialog modal-wide" style="width: 800px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">修改调度日志基本信息</h4>
			</div>
			<div class="modal-body" style="padding-left:45px; padding-right:65px;">
			<div class="row">
				<div class="col-md-12">
					<form class="form-horizontal addGoodsForm">
						<div class="form-group">
							<table width="100%">
						<tr>
								<td colspan="2"><h4 class="form-section">作业信息</h4>
								</td>
							</tr>
						<tr>
							<td class=" col-md-4 gArrivalTime" >
								<div class="form-group">
										<label class="control-label col-md-4">到港时间:</label>
										<div class="col-md-8">
											<div class="input-group">
											         <div class="col-md-7" style="padding-right: 0px;padding-left: 0px;"><input maxlength="16" style="text-align:right;border-right:0;" id="arrivalTime1" key="1"  class="form-control form-control-inline date-picker col-md-8 arrivalTime1"  type="text"/>
												 </div>
											         <div class="col-md-5" style="padding-right: 0px;padding-left: 0px;"><input maxlength="16" style="border-left:0;" type="text"  id="arrivalTime2" class="form-control col-md-4  timepicker timepicker-24 arrivalTime2"> </div>
												</div>
										</div>
									</div>
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
							
							<td class=" col-md-4 gLeaveTime">
								<div class="form-group">
										<label class="control-label col-md-4">离港时间:</label>
										<div class="col-md-8">
											<div class="input-group">
											         <div class="col-md-7" style="padding-right: 0px;padding-left: 0px;"><input maxlength="16" style="text-align:right;border-right:0;" id="leaveTime1" key="1"  class="form-control form-control-inline date-picker col-md-8 leaveTime1"  type="text" />
												 </div>
											         <div class="col-md-5" style="padding-left: 0px;padding-right: 0px;"><input maxlength="16" style="border-left:0;" type="text"  id="leaveTime2" class="form-control col-md-4  timepicker timepicker-24 leaveTime2" > </div>
												</div>
										</div>
									</div>
							</td>
							</tr>
							<tr>
								<td class=" col-md-4"><div class="form-group">
										<label class="control-label col-md-4">服务评价:</label>
										<div class="col-md-8">
											<input type="text" id="evaluate" maxlength="64" class="form-control evaluate" />
										</div>
									</div>
								</td>
								<td class=" col-md-4"><div class="form-group">
										<label class="control-label col-md-4">评价人:</label>
										<div class="col-md-8">
											<input type="text" id="evaluateUserName" maxlength="16" data-provide="typeahead" class="form-control evaluateUserName" />
										</div>
									</div>
								</td>
								</tr>
							
							
							<tr>
							<td class=" col-md-4" colspan="2">
								<div class="form-group">
									<label class="col-md-2 control-label">备注</label>
									<div class="col-md-10">
										<textarea class="form-control description" maxlength="100" rows="3"></textarea>
									</div>
								</div>
							</td>
						</tr>
							
							
							<tr>
						<td colspan="2">
								<div style="border-color: #777;" class="portlet box blue ng-scope">
									<div style="background-color: #777;" class="portlet-title">
										<div class="caption">
											<i class="icon-edit"></i>验证记录
										</div>
										<div class="tools">
											<a class="collapse" href="javascript:;" data-original-title="" title=""></a>
										</div>
									</div>
									<div class="portlet-body">
										<div class="table-toolbar">
											<div class="btn-group">
													<button class="btn btn-default btn-circle mar-r-10" id="notifiAdd">
														<span class="fa fa-plus"></span>添加
													</button>
											</div>
										</div>
										<table id="goods-table" class="table table-striped table-hover table-bordered">
											<thead>
												<tr>
													<th>送达时间</th>
													<th>报告号</th>
													<th>验证结论</th>
													<th>通知人</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody id="tbody">
											</tbody>
										</table>
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