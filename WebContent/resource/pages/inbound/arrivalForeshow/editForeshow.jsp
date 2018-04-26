<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="modal fade">
	<div class="modal-dialog modal-full" >
		<div class="modal-content"  >
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">修改船期预报</h4>
			</div>
			<div class="modal-body" style="padding-left:45px; padding-right:65px;">
			<div class="row">
				<div class="col-md-12">
			<form action="#" class="form-horizontal arrivalForeshowForm">
							<div class="form-body">
									<input type="hidden" id="id"> 
									
					<div class="form-group">
									<label class="control-label col-md-2">船舶中文名</label>
									<div class="col-md-4">
										<input type="text" id="shipRefId" maxlength="30" 
											 class="form-control" />
									</div>
									<label class="control-label col-md-2">物料名称</label>
									<div class="col-md-4">
										<input type="text" id="productNames" maxlength="100" 
											 class="form-control" />
									</div>
								</div>
					<div class="form-group">
									<label class="control-label col-md-2">泊位</label>
									<div class="col-md-4">
										<input type="text" id="berth" maxlength="10"  
											 class="form-control" />
									</div>
									<label class="control-label col-md-2">数量</label>
									<div class="col-md-4">
										<input type="text" id="count" onkeyup="ArrivalForeshow.countLastLeaveTime()" maxlength="100" 
											 class="form-control" />
									</div>
								</div>
								
					<div class="form-group">
									<!-- <label class="control-label col-md-2">船长</label>
									<div class="col-md-4">
										<input type="text" id="shipLenth" maxlength="10" 
											 class="form-control" />
									</div> -->
									<label class="control-label col-md-2">到港吃水</label>
									<div class="col-md-4">
										<input type="text" id="shipArrivalDraught" maxlength="10"  onkeyup="config.clearNoNum(this,2)"
											 class="form-control" />
									</div>
								</div>
					<div class="form-group">
									<label class="control-label col-md-2">船代</label>
									<div class="col-md-4">
										<input type="text" id="shipAgentId" maxlength="30" 
											 class="form-control" />
									</div>
									<label class="control-label col-md-2">货代</label>
									<div class="col-md-4">
										<input type="text" id="cargoAgentNames" maxlength="30" 
											 class="form-control" />
									</div>
								</div>
								
					<div class="form-group">
									<label class="control-label col-md-2">港序</label>
									<div class="col-md-4">
										<input type="text" id="port" maxlength="100" 
											 class="form-control" />
									</div>
									<label class="control-label col-md-2">接卸通知单</label>
									<div class="col-md-4">
										<input type="text" id="unloadNotify" maxlength="100" 
											 class="form-control" />
									</div>
								</div>
								
					<div class="form-group">
									<label class="control-label col-md-2">海关是否同意卸货</label>
									<div class="col-md-4">
									<select class="form-control select2me type" id="isCustomAgree"  name="type"  >
												<option value="">请选择</option>
												<option value="1">是</option>
												<option value="0">否</option>
												<option value="2">不适用</option>

								</select>
								</div>
									<label class="control-label col-md-2">备注</label>
									<div class="col-md-4">
										<input type="text" id="note" maxlength="200" 
											 class="form-control" />
									</div>
								</div>
								
					<div class="form-group">
									<label class="control-label col-md-2">本年度航次</label>
									<div class="col-md-4">
										<input type="text" id="portNum" maxlength="10" 
											 class="form-control" />
									</div>
									<label class="control-label col-md-2">船舶信息表</label>
									<div class="col-md-4">
										<input type="text" id="shipInfo" maxlength="100" 
											 class="form-control" />
									</div>
								</div>	
								
					<div class="form-group">
									<label class="control-label col-md-2">货主</label>
									<div class="col-md-4">
										<input type="text" id="clientNames" maxlength="200" 
											 class="form-control" />
									</div>
									<label class="control-label col-md-2">申报</label>
									<div class="col-md-4">
										<select class="form-control select2me type" id="report"  name="type"  >
												<option value="">请选择</option>
												<option value="已申报">已申报</option>
												<option value="未申报">未申报</option>

								</select>
									</div>
								</div>	
								
					<!-- <div class="form-group">
									<label class="control-label col-md-2">罐安排</label>
									<div class="col-md-4">
										<input type="text" id="testTemperature" 
											 class="form-control" />
									</div>
									<label class="control-label col-md-2">管线安排</label>
									<div class="col-md-4">
										<input type="text" id="tankTemperature" 
											 class="form-control" />
									</div>
								</div>		 -->
					
					<div class="form-group">
									<label class="control-label col-md-2">允许最大在港时间(以卸货速度150t/h计算)</label>
									<div class="col-md-4">
										<input type="text" id="lastLeaveTime" readonly  onchange="ArrivalForeshow.getSQTime()" onkeyup="config.clearNoNum(this)"
											 class="form-control" />
									</div>
									<label class="control-label col-md-2">速遣时间(小时)</label>
									<div class="col-md-4">
										<input type="text" id="repatriateTime" maxlength="10" readonly onkeyup="config.clearNoNum(this,1)"
											 class="form-control" />
									</div>
								</div>
					<div class="form-group">
									
									<label class="control-label col-md-2">超期时间(小时)</label>
									<div class="col-md-4">
										<input type="text" id="overTime" maxlength="10" readonly  onkeyup="config.clearNoNum(this,1)"
											 class="form-control" />
									</div>
								</div>
					
								<div class="form-group">
					<div class="col-md-6">
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
					
					
					<div class="col-md-6">
						<label class="control-label col-md-4">太仓锚地时间</label>
						<!-- <div class="col-md-8">
							<input type="text" id="tcTime" maxlength="100" 
											 class="form-control" />
						</div> -->
						
						<div class="col-md-8">
							<div class="input-group" id="tcTime">
								<div class="col-md-7" style="padding-right: 0px;">
									<input style="text-align: right; border-right: 0;"
										class="form-control form-control-inline date-picker col-md-8"
										type="text"  />
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
						<label class="control-label col-md-4">NOR发出时间</label>
						<div class="col-md-8">
							<div class="input-group" id="norTime">
								<div class="col-md-6" style="padding-right: 0px;">
									<input style="text-align: right; border-right: 0;"
										id="norTime1" onchange="ArrivalForeshow.countLastLeaveTime()"
										class="form-control form-control-inline date-picker col-md-8 norTime1"
										type="text" />
								</div>
								<div class="col-md-4"
									style="padding-left: 0px; padding-right: 0px;">
									<input style="border-left: 0;" type="text"
										onchange="ArrivalForeshow.countLastLeaveTime()" id="norTime2"
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
						<label class="control-label col-md-4">预计靠泊时间</label>
						<div class="col-md-8">
							<div class="input-group" id="anchorTime">
								<div class="col-md-7" style="padding-right: 0px;">
									<input style="text-align: right; border-right: 0;"
										class="form-control form-control-inline date-picker col-md-8"
										type="text"  />
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
						<label class="control-label col-md-4">预计开泵时间</label>
						<div class="col-md-8">
							<div class="input-group" id="pumpOpenTime">
								<div class="col-md-7" style="padding-right: 0px;">
									<input style="text-align: right; border-right: 0;" onchange="ArrivalForeshow.getDifferTime()"
										class="form-control form-control-inline date-picker col-md-8"
										type="text" />
								</div>
								<div class="col-md-5"
									style="padding-left: 0px; padding-right: 0px;">
									<input style="border-left: 0;" type="text" onchange="ArrivalForeshow.getDifferTime()"
										class="form-control col-md-4  timepicker timepicker-24">
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<label class="control-label col-md-4">预计停泵时间</label>
						<div class="col-md-8">
							<div class="input-group" id="pumpStopTime">
								<div class="col-md-7" style="padding-right: 0px;">
									<input style="text-align: right; border-right: 0;" onchange="ArrivalForeshow.getDifferTime()"
										class="form-control form-control-inline date-picker col-md-8"
										type="text" data-required="1" />
								</div>
								<div class="col-md-5"
									style="padding-left: 0px; padding-right: 0px;">
									<input style="border-left: 0;" type="text" onchange="ArrivalForeshow.getDifferTime()"
										class="form-control col-md-4  timepicker timepicker-24">
								</div>
							</div>
						</div>
					</div>
				</div>
								<div class="form-group">
					<div class="col-md-6">
						<label class="control-label col-md-4">拆管时间</label>
						<div class="col-md-8">
							<div class="input-group" id="tearPipeTime">
								<div class="col-md-7" style="padding-right: 0px;">
									<input style="text-align: right; border-right: 0;" onchange="ArrivalForeshow.getSQTime()"
										class="form-control form-control-inline date-picker col-md-8"
										type="text" data-required="1" />
								</div>
								<div class="col-md-5"
									style="padding-left: 0px; padding-right: 0px;">
									<input style="border-left: 0;" type="text" onchange="ArrivalForeshow.getSQTime()"
										class="form-control col-md-4  timepicker timepicker-24">
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<label class="control-label col-md-4">预计离港时间</label>
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
							<!-- <div class="input-group" id="workTime">
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
							</div> -->
						</div>
					</div>
				</div>
					
					
								
					
							</div>
						</form>
	</div>
			</div>
		</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<shiro:hasPermission name="AARRIVALFORESHOWUPDATE">
				<button type="button" class="btn btn-primary" id="save" >提交</button>
				</shiro:hasPermission>
			</div>
		</div>
	</div>
</div>
<!-- <script>
	$(function() {

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
	
	//计算超期/速遣时间
	function getSQTime(){
	var time=util.getDifferSQTime(util.getTimeVal($("#leaveTime")),$("#lastLeaveTime").val());
	if(time>0){
	//速遣
	$("#repatriateTime").val(time);
	$("#overTime").val("");
	}else{
	//超期
	$("#overTime").val(-time);
	$("#repatriateTime").val("");
	}
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
			$("#lastLeaveTime").val(t.Format("yyyy-MM-dd HH:mm:ss"));
			}
		}else{
			$("#lastLeaveTime").val("");
		}
		
		if($("#leaveTime").val()){
		getSQTime();
		}
		
		
	};
</script> -->