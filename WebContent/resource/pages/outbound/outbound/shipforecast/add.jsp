<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
	<div class="portlet box grey">
		<div class="portlet-body form  arrivalInfo">
			<form action="#" class="form-horizontal ">
				<h4 class="form-section notTransport">时间信息</h4>
				<label class="hidden" id="arrivalInfoId" ></label>
				<div class="form-group notTransport">
				<div class="col-md-6">
					<label class="control-label col-md-4">长江口时间</label>
					<div class="col-md-8">
						<div class="input-group cjTime">
							<div class="col-md-6" style="padding-right: 0px;">
								<input
									style="text-align: right; border-right: 0;" id="cjTime1"
									class="form-control form-control-inline date-picker col-md-8"
									type="text" />
							</div>
							<div class="col-md-4"
								style="padding-left: 0px; padding-right: 0px;">
								<input style="border-left: 0;" type="text" id="cjTime2"
									class="form-control col-md-4  timepicker timepicker-24">
							</div>
							<div class="col-md-2" style="padding-left: 0Px;">
								<button class="btn btn-primary form-control" type="button"
									onclick="util.cleanTime(this)">清空</button>
							</div>
						</div>
					</div>
					</div>
					<div class="col-md-6 notTransport">
					<label class="control-label col-md-4">太仓锚地时间</label>
					<div class="col-md-8">
						<div class="input-group tcTime">
							<div class="col-md-6" style="padding-right: 0px;">
								<input style="text-align: right; border-right: 0;" id="tcTime1"
									class="form-control form-control-inline date-picker col-md-8"
									type="text" />
							</div>
							<div class="col-md-4"
								style="padding-left: 0px; padding-right: 0px;">
								<input style="border-left: 0;" type="text" id="tcTime2"
									class="form-control col-md-4  timepicker timepicker-24">
							</div>
							<div class="col-md-2" style="padding-left: 0Px;">
								<button class="btn btn-primary form-control" type="button"
									onclick="util.cleanTime(this)">清空</button>
							</div>
						</div>
					</div>
				</div>
				</div>
				<div class="form-group notTransport">
				<div class="col-md-6">
					<label class="control-label col-md-4">NOR发出时间</label>
					<div class="col-md-8">
						<div class="input-group norTimeNOR">
							<div class="col-md-6 time" style="padding-right: 0px;">
								<input style="text-align: right; border-right: 0;"
									id="norTimeNOR1" onchange="Outbound.countLastLeaveTime();"
									class="form-control form-control-inline date-picker col-md-8"
									type="text" />
							</div>
							<div class="col-md-4"
								style="padding-left: 0px; padding-right: 0px;">
								<input style="border-left: 0;" type="text" onchange="Outbound.countLastLeaveTime();" id="norTimeNOR2"
									class="form-control col-md-4  timepicker timepicker-24">
							</div>
							<div class="col-md-2" style="padding-left: 0Px;">
								<button class="btn btn-primary form-control" type="button"
									onclick="util.cleanTime(this)">清空</button>
							</div>
						</div>
						</div>
					</div>
					</div>
					
				<h4 class="form-section">其他信息</h4>
				<div class="form-group notTransport">
				<div class="col-md-6">
					<label class="control-label col-md-4">本年度航次</label>
					<div class="col-md-8">
						<input type="text" name="portNum" id="portNum" disabled="disabled"
							class="form-control " />
					</div>
					</div>
					<div class="col-md-6">
					<label class="control-label col-md-4">申报状态</label>
					<div class="col-md-8">
						<input type="text" name="report" disabled="disabled" id="report"
							class="form-control " />
					</div>
					</div>
				
				</div>
				<div class="form-group notTransport">
				<div class="col-md-6">
					<label class="control-label col-md-4">船舶信息表</label>
					<div class="col-md-8">
						<select id="shipInfo" class="form-control "><option
								value="0">未收到</option>
							<option value="1">收到</option></select>
					</div>
				</div>
				<div class="col-md-6">
					<label class="control-label col-md-4">最大在港时间</label>
					<div class="col-md-8">
						<input type="text"  
							onkeyup="config.clearNoNum(this)" placeholder="以卸货速度150t/h计算"
							id="lastLeaveTime" disabled="disabled"
							class="form-control " />
					</div>
				</div>
				</div>
				<div class="form-group notTransport">
				<div class="col-md-6">
					<label class="control-label col-md-4">超期时间（小时）</label>
					<div class="col-md-8">
						<input type="text" name="overTime" id="overTime"
							disabled="disabled" onkeyup="config.clearNoNum(this)"
							class="form-control " />
					</div>
					</div>
					<div class="col-md-6">
					<label class="control-label col-md-4">速遣时间（小时）</label>
					<div class="col-md-8">
						<input type="text" name="repatriateTime" disabled="disabled"
							id="repatriateTime" onkeyup="config.clearNoNum(this)"
							class="form-control " />
					</div>
					</div>
				</div>
				<div class="form-group">
				<div class="col-md-6">
					<label class="control-label col-md-4">管线安排</label>
					<div class="col-md-8">
						<input type="text"  id="tubeInfo"
							class="form-control " disabled="disabled" />
					</div>
					</div>
					<div class="col-md-6">
					<label class="control-label col-md-4">罐安排</label>
					<div class="col-md-8">
						<input type="text"  id="tankInfo"
							class="form-control " disabled="disabled" />
					</div>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-md-2">备注</label>
					<div class="col-md-10">
						<textarea  name="note" id="note" rows="2"
							class="form-control " ></textarea>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"
						onclick="javascript:history.go(-1);">返回</button>
					<shiro:hasPermission name="AOUBOUNDPLANUPDATE">
						<button type="button" key='0' class="btn btn-primary " id="save"  >保存</button>
						<button type="button" key='1' class="btn btn-primary" id="submit" >提交</button>
					</shiro:hasPermission>
				</div>
			</form>
			<!-- END FORM-->
		</div>
	</div>
</body>