<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class=" modal-footer unloadingready" style="padding-top: 0px; padding-bottom: 0px; border-top: 0px;">
<shiro:hasPermission name="AINBOUNDDOCKNOTICE">
	<button id="dockNotice" class="btn btn-primary" onclick="InboundOperation.dialogDockNotify(this);">
		<i class="icon-edit"></i>码头接卸作业通知单
	</button>
	</shiro:hasPermission>
	<shiro:hasPermission name="AINBOUNDDYNAMICNOTICE">
	<button id="dynamicNotice" class="btn btn-primary" style="margin-left: 4px" onclick="InboundOperation.dialogDynamicNotify(this);">
		<i class="icon-edit"></i>动力班接卸作业通知单
	</button>
	</shiro:hasPermission>
</div>
	<div class="unloadingready row" style="margin-top:4px">
	<shiro:hasPermission name="AINBOUNDTANKSHOW">
		<div class="col-md-12">
			<div class="portlet box blue" style="border-color:#777;" >
				<div class="portlet-title" style="background-color: #777;">
					<div class="caption">一、储罐的安排</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a>
					</div>
				</div>
				<div class="portlet-body">
						<form class="form-horizontal">
				<div class="form-body">
				<div class="form-group">
				<div class="col-md-12">
				<div data-role="tankGrid" style="width:100%"></div>
				</div>
				</div>
				</div>
			</form>
				</div>
			</div>
		</div>
</shiro:hasPermission>
<shiro:hasPermission name="AINBOUNDTUBESHOW">
	<div class="col-md-12">
		<div class="portlet box blue" style="border-color:#777;" >
				<div class="portlet-title" style="background-color: #777;">
				<div class="caption">二、管线的安排</div>
				<div class="tools">
					<a href="javascript:;" class="collapse"></a>
				</div>
			</div>
			<div class="portlet-body">
				<form class="form-horizontal">
					<div class="form-body">
						<div class="form-group">
							<div class="col-md-12">
								<div data-role="tubeGrid" style="width: 100%"></div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!-- END EXAMPLE TABLE PORTLET-->
	</div>
</shiro:hasPermission>
<shiro:hasPermission name="AINBOUNDTUBECHECKSHOW">
	<div class="col-md-12">
		<div class="portlet box blue" style="border-color:#777;" >
				<div class="portlet-title" style="background-color: #777;">
				<div class="caption">三、管线的准备及检查情况</div>
				<div class="tools">
					<a href="javascript:;" class="collapse"></a>
				</div>
			</div>
			<div class="portlet-body">
			<form action="#" class="form-horizontal">
			<div class="form-body">
			<div class="form-group">
					<h4 class="form-section col-md-offset-1">工艺流程</h4>
					<div class="col-md-12">
						<div id="contentDiv" class="content" style="border: 1px solid rgb(216, 216, 216); position: relative; overflow-x: hidden; overflow-y: auto; cursor: move; width: 631px; height: 350px; margin: 0 auto;">
						</div>
					</div>
						<div class="col-md-12">
						<div data-role="tubeCheckGrid" style="width: 100%"></div>
						</div>
				</div>
			</div>
			</form>
			</div>
		</div>
		<!-- END EXAMPLE TABLE PORTLET-->
	</div>
</shiro:hasPermission>
<shiro:hasPermission name="AINBOUNDJOBCHECKSHOW">
	<div class="col-md-12">
		<!-- BEGIN EXAMPLE TABLE PORTLET-->
		<div class="portlet box blue" style="border-color:#777;" >
				<div class="portlet-title" style="background-color: #777;">
				<div class="caption" id="tp1">四、船发前各岗位的最后检查确认工作</div>
				<div class="tools">
					<a href="javascript:;" class="collapse"></a>
				</div>
			</div>
			<div class="portlet-body">
                <form action="#" class="form-horizontal">
               <div class="form-group">
                  <div class="col-md-12">
						<div data-role="jobCheckGrid" style="width: 100%"></div>
                  </div>
               </div>
                </form>
			</div>
		</div>
		<!-- END EXAMPLE TABLE PORTLET-->
	</div>
</shiro:hasPermission>
<shiro:hasPermission name="AINBOUNDPROCESSSHOW">
		<div class="col-md-12">
			<div class="portlet box blue" style="border-color:#777;" >
				<div class="portlet-title" style="background-color: #777;">
					<div class="caption" id="tp2">五、船舶接卸过程</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a>
					</div>
				</div>
				<div class="portlet-body">
				<form class="form-horizontal  unloadprogram">
				<div class="form-body">
				<shiro:hasPermission name="AINBOUNDDISPATCHLOGSHOW">
								<div class="form-group">
								<div class="col-md-4">
									<label class="control-label col-md-4" id="tp3">船名:</label>
									<div class="col-md-8">
										<label class="control-label" id="shipRefName"></label>
									</div>
									</div>
									<div class="col-md-4">
									<label class="control-label col-md-5">泊位号:</label>
									<div class="col-md-7">
										<label class="control-label" id="berthName"></label>
									</div>
									</div>
									<div class="col-md-4">
									<label class="control-label col-md-4">调度值班:</label>
									<div class="col-md-8">
										<label class="control-label" id="dispatchBUserId" ></label>
									</div>
									</div>
								</div>
								<div class="form-group">
								<div class="col-md-4">
									<label class="control-label col-md-4">码头值班:</label>
									<div class="col-md-8">
										<label class="control-label" id="dockBUserId" ></label>
									</div>
									</div>
									<div class="col-md-4">
									<label class="control-label col-md-5">动力班值班:</label>
									<div class="col-md-7">
										<label class="control-label" id="dynamicBUserId" ></label>
									</div>
									</div>
									<div class="col-md-4">
									<label class="control-label col-md-4">到港时间:</label>
									<div class="col-md-8">
										<label class="control-label" id="arrivalTime" ></label>
										</div>
									</div>
									</div>
									 <shiro:hasPermission name="AINBOUNDCHECKTIMESHOW">
								<div class="form-group">
								<div class="col-md-4">
									<label class="control-label col-md-4">联检时间</label>
									<div class="col-md-8">
									<label class="control-label" id="checkTime" ></label>
									</div>
								</div>
								</div>
								</shiro:hasPermission>
								</shiro:hasPermission>
								
								<shiro:hasPermission name="AINBOUNDBFOPENPUMPTESTSHOW">
								<h4>开泵前的验证</h4>
								<hr>
								<div class="workNotification"> </div>
						</shiro:hasPermission>
						
						<shiro:hasPermission name="AINBOUNDBFOPENPUMPSURESHOW">
						  <h4>开泵前确认</h4>
						  <hr>
								<div class="form-group">
								<div class="col-md-4">
									<label class="control-label col-md-4">确认内容</label>
									<div class="col-md-8">
										<input class="form-control" id="dynamicContent">
									</div>
									</div>
									<div class="col-md-4">
									<label class="control-label col-md-5">动力班</label>
									<div class="col-md-7">
										<input type="text" id="dynamicUserId" readonly class="form-control" />
									</div>
									</div>
									<shiro:hasPermission name="AOPENPUPMOWER">
									<div class="col-md-4" style="text-align:center">
									<button type="button" class="btn btn-primary" key="1" id="dynamicBtn">确认</button>
									</div>
									</shiro:hasPermission>
								</div>
								<div class="form-group notTransport">
								<div class="col-md-4">
									<label class="control-label col-md-4">确认内容</label>
									<div class="col-md-8">
										<input  id="dockContent"  class="form-control">
									</div>
									</div>
									<div class="col-md-4">
									<label class="control-label col-md-5">码头</label>
									<div class="col-md-7">
										<input type="text" id="dockUserId" readonly class="form-control" />
									</div>
									</div>
									<shiro:hasPermission name="AOPENPUPMDOCK">
									<div class="col-md-4" style="text-align:center">
									<button type="button" class="btn btn-primary" key="2" id="dockBtn">确认</button>
									</div>
									</shiro:hasPermission>
								</div>
								<div class="form-group">
								<div class="col-md-4">
									<label class="control-label col-md-4">确认内容</label>
									<div class="col-md-8">
										<input  id="shipClientContent"  class="form-control">
									</div>
									</div>
									<div class="col-md-4">
									<label class="control-label col-md-5">船方(调度)</label>
									<div class="col-md-7">
										<input type="text" id="shipClientId" readonly class="form-control" />
									</div>
									</div>
									<shiro:hasPermission name="AOPENPUPMDISPATCH">
									<div class="col-md-4" style="text-align:center">
									<button type="button" class="btn btn-primary" key="3" id="shipClientBtn">确认</button>
									</div>
									</shiro:hasPermission>
								</div>
								<shiro:hasPermission name="AINBOUNDDOCKCHECKSHOW">
								<div class="form-group notTransport">
							<div class="col-md-4">
								<label class="control-label col-md-4">码头电动阀测试</label>
								<div class="col-md-8 dockCheck">
									<input type="text"  class="form-control" id="dockCheck" readonly ></input>
								</div>
							</div>
							<div class="col-md-4">
								<label class="control-label col-md-5">调度</label>
								<div class="col-md-7">
									<input type="text" id="dockCheckClientId" readonly class="form-control" />
								</div>
							</div>
							<shiro:hasPermission name="ADOCKDESTADD">
								<div class="col-md-4" style="text-align: center">
									<button type="button" class="btn btn-primary" key="4" id="dockCheckBtn">确认</button>
								</div>
							</shiro:hasPermission>
						</div>	
						</shiro:hasPermission>
						</shiro:hasPermission>
						<shiro:hasPermission name="AINBOUNDDOCKLOGSHOW">
								<h4>入库完成记录</h4><hr>
								 
									<div class="form-group">
									<div class="col-md-4">
									<label class="control-label col-md-4">开泵时间:</label>
									<div class="col-md-8">
										<label class="control-label" id="openPump"
												></label>
										</div>
									</div>
									<div class="col-md-4">
									<label class="control-label col-md-5">停泵时间:</label>
									<div class="col-md-7">
											<label class="control-label" id="stopPump"
												></label>
									</div>
								</div>
								<div class="col-md-4 notTransport">
									<label class="control-label col-md-4">离港时间:</label>
									<div class="col-md-8">
										<label class="control-label" id="leaveTime"
												></label>
										</div>
									</div>
								</div>
							<div class="form-group">
								<div class="col-md-10 col-md-offset-1">
								<div data-role="totalstoreGrid" style="width:100%"></div>
									</div>
								</div>
								<div class="form-group">
								  <div class="col-md-6 notTransport">
									<label class="control-label col-md-4">累计在港时间(小时):</label>
									<div class="col-md-8">
										<label class="control-label" id="stayTime" ></label>
									</div>
									</div>
									<div class="col-md-6">
									<label class="control-label col-md-4">累计作业时间(小时):</label>
									<div class="col-md-8">
										<label class="control-label" id="workTime" ></label>
									</div>
									</div>
									</div>
									<div class="form-group">
								  <div class="col-md-6">
									<label class="control-label col-md-4" id="tp4">本次接卸提供服务评价:</label>
									<div class="col-md-8">
										 <label class="control-label" id="evaluate" ></label>
									</div>
								</div>
								<div class="col-md-6">
									<label class="control-label col-md-4">评价人:</label>
									<div class="col-md-8">
										<label class="control-label" id="evaluateUserId"  ></label>
									</div>
								</div> 
								</div>
								</shiro:hasPermission>
					</div>			
			</form>
				</div>
			</div>
			</div></shiro:hasPermission>
		<shiro:hasPermission name="AINBOUNDUNUSUALSHOW">
		<div class="col-md-12">
			<div class="portlet box blue" style="border-color:#777;" >
				<div class="portlet-title" style="background-color: #777;">
					<div class="caption" id="tp5">六、发货过程中码头及库区异常情况及处置记录</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a>
					</div>
				</div>
				<div class="portlet-body">
				<form class="form-horizontal">
				<div class="form-body">
								<div class="form-group">
									<div class="col-md-12">
										<textarea class="form-control description" rows="3" maxlength="250"
											id="unusualLog"></textarea>
									</div>
								</div>
				</div>
			</form>
				</div>
			</div>
		</div>
		</shiro:hasPermission>
		<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal" onclick="javascript:history.go(-1);">返回</button>
		<shiro:hasPermission name="ABACKSTATUS">
		<button type="button" class="btn btn-primary" onclick="InboundOperation.cleanToStatus(6)">回退到当前状态</button>
		</shiro:hasPermission>
			</div>
			</div>
	<script>
	$(function(){
    InboundOperation.initUnloadingReady($(".unloadingready"),2);		
	});
	util.initTimePicker($(".unloadingready"));
	function handle(){
		if(checkvalue(1)){//累计在港
			var arrivalTime=$(".unloadingready").find("#arrivalTime").text();
			var leaveTime=$(".unloadingready").find("#leaveTime").text();
		$("#stayTime").text(betweenTime(arrivalTime,leaveTime));	
		}
		if(checkvalue(2)){//累计作业
			var openPump=$(".unloadingready").find("#openPump").text();
			var stopPump=$(".unloadingready").find("#stopPump").text();
		$("#workTime").text(betweenTime(openPump,stopPump));	
		}
	}
	function checkvalue(item) {
		if(item==1){
		if ($("#arrivalTime").text() == ""||$("#leaveTime").text() == "") {
			return false;
		}
		return true;
		}else if(item==2){
		if($("#openPump").text() == ""||$("#stopPump").text() == ""){
			return false;
		}	
		return true;
		}
		return true;
	}
	function betweenTime(start,end){
		var startTime = start.replace(/-/g, "/");
		var endTime=end.replace(/-/g, "/");
		var startDate = new Date(startTime);
		var endDate=new Date(endTime);
		var differ=(Math.abs(endDate.getTime()-startDate.getTime())/3600000).toFixed(2);
		return differ;	
	}
	</script>