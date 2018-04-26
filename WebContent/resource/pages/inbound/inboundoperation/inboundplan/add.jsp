<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="portlet box grey">
	<div class="portlet-title hidden">
		<div class="caption">&nbsp;添加入库计划</div>
	</div>
	<div class="portlet-body form inboundplan ">
		<!-- BEGIN FORM-->
		<form action="#" class="form-horizontal arrivalinfo">
			<div class="form-body">
				<h4 class="form-section">时间信息</h4>
				<div class="form-group">
					<div class="col-md-6 notTransport">
						<label class="control-label col-md-4">长江口时间</label>
						<div class="col-md-8">
							<div class="input-group" id="cjTime">
								<div class="col-md-6" style="padding-right: 0px;">
									<input style="text-align: right; border-right: 0;"
										class="form-control form-control-inline date-picker"
										type="text" />
								</div>
								<div class="col-md-4"
									style="padding-left: 0px; padding-right: 0px;">
									<input style="border-left: 0; float: left;" type="text"
										class="form-control  timepicker timepicker-24">
								</div>
								<div class="col-md-2"
									style="padding-left: 0Px; padding-right: 0px;">
									<button type="button" onclick="util.cleanTime(this)"
										class="btn btn-primary form-control">清空</button>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-6 notTransport">
						<label class="control-label col-md-4">太仓锚地时间</label>
						<div class="col-md-8">
							<div class="input-group" id="tcTime">
								<div class="col-md-6" style="padding-right: 0px;">
									<input style="text-align: right; border-right: 0;"
										class="form-control form-control-inline date-picker col-md-8"
										type="text" />
								</div>
								<div class="col-md-4"
									style="padding-left: 0px; padding-right: 0px;">
									<input style="border-left: 0;" type="text"
										class="form-control col-md-4  timepicker timepicker-24">
								</div>
								<div class="col-md-2"
									style="padding-left: 0Px; padding-right: 0px;">
									<button type="button" onclick="util.cleanTime(this)"
										class="btn btn-primary form-control">清空</button>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="form-group">
					<div class="col-md-6 notTransport">
						<label class="control-label col-md-4">NOR发出时间</label>
						<div class="col-md-8">
							<div class="input-group" id="norTime">
								<div class="col-md-6" style="padding-right: 0px;">
									<input style="text-align: right; border-right: 0;"
										id="norTime1" onchange="countLastLeaveTime()"
										class="form-control form-control-inline date-picker col-md-8 norTime1"
										type="text" />
								</div>
								<div class="col-md-4"
									style="padding-left: 0px; padding-right: 0px;">
									<input style="border-left: 0;" type="text"
										onchange="countLastLeaveTime()" id="norTime2"
										class="form-control col-md-4  timepicker timepicker-24 norTime2">
								</div>
								<div class="col-md-2"
									style="padding-left: 0Px; padding-right: 0px;">
									<button type="button" onclick="util.cleanTime(this)"
										id="norCleanBtn" class="btn btn-primary form-control">清空</button>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<label class="control-label col-md-4" id="tp1">预计到港时间<span
							class="required">*</span></label>
						<div class="col-md-8">
							<div class="input-group" id="anchorTime" key="1">
								<div class="col-md-7" style="padding-right: 0px;">
									<input style="text-align: right; border-right: 0;"
										class="form-control form-control-inline date-picker col-md-8"
										type="text" data-required="1" />
								</div>
								<div class="col-md-5"
									style="padding-left: 0px; padding-right: 0px;">
									<input style="border-left: 0;" type="text"
										class="form-control col-md-4  timepicker timepicker-24">
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-md-6">
						<label class="control-label col-md-4">预计开泵时间<span
							class="required">*</span></label>
						<div class="col-md-8">
							<div class="input-group" id="pumpOpenTime">
								<div class="col-md-7" style="padding-right: 0px;">
									<input style="text-align: right; border-right: 0;" onchange="getDifferTime()"
										class="form-control form-control-inline date-picker col-md-8"
										type="text" data-required="1" />
								</div>
								<div class="col-md-5"
									style="padding-left: 0px; padding-right: 0px;">
									<input style="border-left: 0;" type="text" onchange="getDifferTime()"
										class="form-control col-md-4  timepicker timepicker-24">
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<label class="control-label col-md-4">预计停泵时间<span
							class="required">*</span></label>
						<div class="col-md-8">
							<div class="input-group" id="pumpStopTime">
								<div class="col-md-7" style="padding-right: 0px;">
									<input style="text-align: right; border-right: 0;" onchange="getDifferTime()"
										class="form-control form-control-inline date-picker col-md-8"
										type="text" data-required="1" />
								</div>
								<div class="col-md-5"
									style="padding-left: 0px; padding-right: 0px;">
									<input style="border-left: 0;" type="text" onchange="getDifferTime()"
										class="form-control col-md-4  timepicker timepicker-24">
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="form-group">
					<div class="col-md-6">
						<label class="control-label col-md-4">预计拆管时间<span
							class="required">*</span></label>
						<div class="col-md-8">
							<div class="input-group" id="tearPipeTime">
								<div class="col-md-7" style="padding-right: 0px;">
									<input style="text-align: right; border-right: 0;"
										class="form-control form-control-inline date-picker col-md-8"
										type="text" data-required="1" />
								</div>
								<div class="col-md-5"
									style="padding-left: 0px; padding-right: 0px;">
									<input style="border-left: 0;" type="text"
										class="form-control col-md-4  timepicker timepicker-24">
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<label class="control-label col-md-4" id="tp2">预计离港时间<span
							class="required">*</span></label>
						<div class="col-md-8">
							<div class="input-group" id="leaveTime">
								<div class="col-md-7" style="padding-right: 0px;">
									<input style="text-align: right; border-right: 0;"
										class="form-control form-control-inline date-picker col-md-8"
										type="text" data-required="1" />
								</div>
								<div class="col-md-5"
									style="padding-left: 0px; padding-right: 0px;">
									<input style="border-left: 0;" type="text"
										class="form-control col-md-4  timepicker timepicker-24">
								</div>
							</div>
						</div>
					</div>
					</div>
				<div class="form-group">
					<div class="col-md-6">
						<label class="control-label col-md-4">预计作业时间(小时)</label>
						<div class="col-md-8">
						<label class="control-label col-md-offset-1" id="workTime"></label>
						</div>
					</div>
				</div>
				
				<h4 class="form-section">其他信息</h4>
				<div class="form-group">
					<div class="col-md-6 notTransport">
						<label class="control-label col-md-4">港序</label>
						<div class="col-md-8">
							<input type="text" id="port" maxlength="64" class="form-control" />
						</div>
					</div>
					<div class="col-md-6 notTransport">
						<label class="control-label col-md-4">上一港类型</label>
						<div class="col-md-8">
							<input class="form-control" id="portType">
						</div>
					</div>
				</div>

				<div class="form-group notTransport">
					<div class="col-md-6">
						<label class="control-label col-md-4">本年度航次</label>
						<div class="col-md-8">
							<input type="text" id="portNum" readonly class="form-control" />
						</div>
					</div>
				</div>

				<div class="form-group">
					<div class="col-md-6">
						<label class="control-label col-md-4">罐安排</label>
						<div class="col-md-8">
							<input type="text" id="tankNames" readonly class="form-control" />
						</div>
					</div>
					<div class="col-md-6">
						<label class="control-label col-md-4">管线安排</label>
						<div class="col-md-8">
							<input type="text" id="tubeNames" readonly class="form-control" />
						</div>
					</div>
				</div>

				<div class="form-group notTransport">
					<div class="col-md-6">
						<label class="control-label col-md-4">申报状态</label>
						<div class="col-md-8">
							<input class="form-control" readonly id="report">
						</div>
					</div>
					<div class="col-md-6 notTransport">
						<label class="control-label col-md-4">船舶信息表</label>
						<div class="col-md-8">
							<select class="form-control" id="shipInfo">
								<option value="未收到" selected>未收到</option>
								<option value="已收到">已收到</option>
							</select>
						</div>
					</div>
				</div>

				<div class="form-group notTransport">
					<div class="col-md-6">
						<label class="control-label col-md-4">滞港时间(小时)</label>
						<div class="col-md-8">
							<input type="text" id="overTime" readonly
								onkeyup="config.clearNoNum(this)" class="form-control" />
						</div>
					</div>
					<div class="col-md-6 notTransport">
						<label class="control-label col-md-4">速遣时间(小时)</label>
						<div class="col-md-8">
							<input type="text" id="repatriateTime" readonly
								onkeyup="config.clearNoNum(this)" class="form-control" />
						</div>
					</div>
				</div>

				<div class="form-group notTransport">
					<div class="col-md-6">
						<label class="control-label col-md-4">最大在港时间</label>
						<div class="col-md-8">
							<input type="text" readonly="readonly"
								class="form-control stayTime" id="lastLeaveTime"
								placeholder="以卸货速度150t/h计算" />
						</div>
					</div>
					<div class="col-md-6 notTransport">
						<label class="control-label col-md-4">船舶代理</label>
						<div class="col-md-8">
							<input type="text" id="shipAgentId" readonly
								data-provide="typeahead" class="form-control" />
						</div>
					</div>
				</div>

				<div class="form-group">
					<div class="col-md-12">
						<label class="control-label col-md-2">备注</label>
						<div class="col-md-10">
							<textarea class="form-control" rows="1" id="note" maxlength="100"
								name="description"></textarea>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"
					onclick="javascript:history.go(-1);">返回</button>
				<shiro:hasPermission name="ABACKSTATUS">
					<button type="button" class="btn btn-primary"
						onclick="InboundOperation.cleanToStatus(2)">回退到当前状态</button>
				</shiro:hasPermission>
				<shiro:hasPermission name="AINBOUNDWORKUPDATE">
					<button type="button" key="0" class="btn btn-primary" id="save">保存</button>
					<button type="button" key="1" class="btn btn-primary" id="submit">提交</button>
				</shiro:hasPermission>
			</div>
		</form>
	</div>
</div>
<script>
	$(function() {
		util.initTimePicker($(".inboundplan"));
		InboundOperation.initArrivalInfo($(".inboundplan"), 1);
		$('#norCleanBtn').click(function(){
			countLastLeaveTime();
		});
	});
	//初始化时间控件

	//同步初始化其他日期
	function insertAll() {
		if ($("#anchorTime").attr("key") == 1) {
			var time = util.getTimeVal($("#anchorTime"));
			if (util.getTimeVal($("#pumpOpenTime")) == "") {
				util.initTimeVal($("#pumpOpenTime"), time);
				util.initTimeVal($("#pumpStopTime"), time);
				util.initTimeVal($("#tearPipeTime"), time);
		//      util.initTimeVal($("#workTime"), time);
				util.initTimeVal($("#leaveTime"), time);
				$("#anchorTime").attr("key", 2);
				countLastLeaveTime();
			}
		}
	}
	//计算作业时间
	function getDifferTime(){
		$("#workTime").text(util.getDifferTime(util.getTimeVal($("#pumpOpenTime")),util.getTimeVal($("#pumpStopTime"))));
	}
	//同步计算最大在港时间
	function countLastLeaveTime() {
		if (util.getTimeVal($("#norTime")) != "") {
			var time = util.getTimeVal($("#norTime"));
			if(time.length==10){
				time=time+" 00:00:00";
			}
			var time2 = time.replace(/-/g, "/");
			if(time2.length==10){
				time2=time2+" 00:00:00";
			}
			var d = new Date(time2);
			var num = InboundOperation.countAllGoodsNum();
			d.setTime(util.FloatAdd(d.getTime(),(6 + num / 150) * 3600000));
			var t = new Date(d.getTime());
			if((d.getTime()+"").length!=13){
				$("body").message({
					type:'warning',
					content:"货品总量过大，无法计算最大在港时间"
				});
			}else{
			$(".stayTime").val(t.Format("yyyy-MM-dd HH:mm:ss"));
			}
		}else{
			$(".stayTime").val("");
		}
	};
</script>
