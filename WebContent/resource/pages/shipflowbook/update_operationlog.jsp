<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="row">
	<div class="col-md-12">
		<div class="portlet box grey">
			<div class="portlet-title">
				<div class="caption">修改分流台账信息</div>
			</div>
			<div class="portlet-body form">
				<form class="form-horizontal addGoodsForm">
				<label class="hidden" id="workId"></label>
				<label class="hidden" id="measureCheckId"></label>
				<label class="hidden" id="reviewCheckId"></label>
				<label class="hidden" id="transportId"></label>
					<div class="form-body shipFlowInfo">
								<div class="form-group">
										<label class="control-label col-md-1">船名:</label>
										<div class="col-md-2">
											<input type="text" id="shipName" class="form-control" disabled="disabled"/>
										</div>
										<label class="control-label col-md-2">品种:</label>
										<div class="col-md-2">
											<input type="text" id="productName" class="form-control" disabled="disabled"/>
										</div>
										<label class="control-label col-md-2">提单数量(t):</label>
										<div class="col-md-2">
											<input type="text" id="totalNum" class="form-control" disabled="disabled"/>
										</div>
								</div>
							
							
								<div class="form-group">
										<label class="control-label col-md-1">到港时间:<span class="required">*</span></label>
										<div class="col-md-2">
										<div style="width: 100%;" class="input-group arrivalTime">
											         <div style="padding-right: 0px; padding-left: 0px;" class="col-md-7"><input type="text" class="form-control form-control-inline date-picker col-md-8" data-type="Require" data-required="1" id="arrivalTime1" style="text-align:right;border-right:0;">
												 </div>
											         <div style="padding-left: 0px; padding-right: 0px;" class="col-md-5"><input type="text" class="form-control col-md-4  timepicker timepicker-24" id="arrivalTime2" style="border-left:0;"> </div>
										</div>
										</div>
										<label class="control-label col-md-2">开泵时间:</label>
										<div class="col-md-2">
										<div style="width: 100%;" class="input-group openPump">
											         <div style="padding-right: 0px; padding-left: 0px;" class="col-md-7"><input type="text" class="form-control form-control-inline date-picker col-md-8"  id="openPump1" style="text-align:right;border-right:0;">
												 </div>
											         <div style="padding-left: 0px; padding-right: 0px;" class="col-md-5"><input type="text" class="form-control col-md-4  timepicker timepicker-24" id="openPump2" style="border-left:0;"> </div>
												</div>
										</div>
										<label class="control-label col-md-2">停泵时间:</label>
										<div class="col-md-2">
										<div style="width: 100%;" class="input-group stopPump">
											         <div style="padding-right: 0px; padding-left: 0px;" class="col-md-7"><input type="text" class="form-control form-control-inline date-picker col-md-8"  id="stopPump1" style="text-align:right;border-right:0;" >
												 </div>
											         <div style="padding-left: 0px; padding-right: 0px;" class="col-md-5"><input type="text" class="form-control col-md-4  timepicker timepicker-24" id="stopPump2" style="border-left:0;"> </div>
										</div>
										</div>
									</div>
							
							
								<div class="form-group">
								<label class="control-label col-md-1">离港时间:</label>
										<div class="col-md-2">
										<div style="width: 100%;" class="input-group leaveTime">
											         <div style="padding-right: 0px; padding-left: 0px;" class="col-md-7"><input type="text" class="form-control form-control-inline date-picker col-md-8"  id="leaveTime1" style="text-align:right;border-right:0;">
												 </div>
											         <div style="padding-left: 0px; padding-right: 0px;" class="col-md-5"><input type="text"  class="form-control col-md-4  timepicker timepicker-24" id="leaveTime2" style="border-left:0;"> </div>
										</div>
										</div>
									<label class="control-label col-md-2">管线:</label>
									<div class="col-md-2">
										<input type="text" name="trans" id="tubes" disabled="disabled" class="form-control" />
									</div>
									<label class="control-label col-md-2">管线状态:</label>
								<div class="col-md-2">
									<select name="tubeStatus"  class="form-control " id="tubeStatus" maxlength="10">
										<option value=>请选择</option>
										<option value="1">充满</option>
										<option value="2">局部空</option>
										<option value="3">全空</option>
									</select>
									</div>
									</div>
									
								<div class="form-group">
										<label class="control-label col-md-1">计量人员:</label>
										<div class="col-md-2">
											<input type="text"  class="form-control " id="measureUserId"> 
										</div>
										<label class="control-label col-md-2">复核人员:</label>
										<div class="col-md-2">
										    <input type="text"  class="form-control " id="reviewUserId" > 
										</div>
									</div>
								<div class="form-group">
										<label class="control-label col-md-1">服务评价:</label>
										<div class="col-md-2">
										<input id="evaluate" name="evaluate" class="form-control" maxlength="10">
										</div>
									
										<label class="control-label col-md-2">评价人:</label>
										<div class="col-md-2">
											<input type="text" id="evaluateUser" class="form-control limit_length" maxlength="10"/>
										</div>
									</div>
								<div class="form-group">
									<label class="control-label col-md-1">备注:</label>
										<div class="col-md-10">
											<textarea  id="comment" class="form-control description" rows=1 maxlength="255"></textarea>
										</div>
									</div>
					</div>
				</form>
			</div>
		</div>
		<div class="modal-footer">
			 <button type="button" class="btn btn-default"   onclick="javascript:history.go(-1);">返回</button>
			<button type="button" class="btn btn-success" id="save">保存</button>
		</div>
	</div>
</div>
