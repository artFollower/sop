<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="portlet box grey">
	<div class="portlet-title hidden">
		<div class="caption">添加打循环方案</div>
	</div>
	<div class="portlet-body form backflowplan">
		<div class=" modal-footer"
			style="padding-top: 0px; padding-bottom: 0px; border-top: 0px;">
			<button id="dockBackFlowNotice" class="btn btn-primary"
				onclick="InboundOperation.dialogDockBackFlowNotify(this);">
				<i class="icon-edit"></i>打循环作业通知单(码头)
			</button>
			<button id="storeBackFlowNotice" style="margin-left: 4px"
				class="btn btn-primary"
				onclick="InboundOperation.dialogStoreBackFlowNotify(this);">
				<i class="icon-edit"></i>打循环作业通知单(库区)
			</button>
		</div>
		<!-- BEGIN FORM-->
		<form action="#" class="form-horizontal" id="backflow-form">
			<div class="form-body">
				<h4 class="form-section">基本信息</h4>

				<div class="form-group">
					<div class="col-md-6">
						<label class="control-label col-md-4">倒出罐号</label> <label
							class="hidden" id="currentStatus"></label>
						<div class="col-md-8">
							<input type="text" id="outtankIds" data="" readonly
								class="form-control" />
						</div>
					</div>
					<div class="col-md-6">
						<label class="control-label col-md-4">倒入罐号</label>
						<div class="col-md-8">
							<input type="text" id="intankIds" readonly data=""
								class="form-control" />
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-md-6">
						<label class="control-label col-md-4">泵号</label>
						<div class="col-md-8">
							<input type="text" id="pupmIds" data="" readonly
								class="form-control" />
						</div>
					</div>
					<div class="col-md-6">
						<label class="control-label col-md-4">管线号</label>
						<div class="col-md-8">
							<input type="text" id="tubeIds" data="" readonly
								class="form-control" />
						</div>
					</div>
				</div>
				<div class="form-group  ">
					<div class="col-md-6 tankCountDiv">
						<label class="control-label col-md-4">数量(吨)<span
							class="required">*</span></label>
						<div class="col-md-8">
							<input type="text" id="tankCount" data-required="1"
								data-type="Require" data="" onkeyup="config.clearNoNum(this,3)"
								maxlength="12" class="form-control" />
						</div>
					</div>
					<div style="display: none;" class="col-md-6">
						<label class="control-label col-md-4">泊位</label>
						<div class="col-md-8">
							<input type="text" id="berthName" data="" class="form-control" />
						</div>
					</div>
				</div>
				<h4 class="form-section pumpTimDiv">泵信息</h4>
				<div class="form-group pumpTimDiv">
				<div class="col-md-6">
				<label class="control-label col-md-4">开泵时间</label>
				<div class="col-md-8">
									<div class="input-group openPumpTime">
										<div class="col-md-7" style="padding-right: 0px;">
											<input style="text-align: right; border-right: 0;" id="openPumpTime1"  class="form-control form-control-inline date-picker col-md-8" type="text" />
										</div>
										<div class="col-md-5" style="padding-left: 0px;">
											<input style="border-left: 0;" type="text" id="openPumpTime2"  class="form-control col-md-4  timepicker timepicker-24">
										</div>
									</div>
				</div>
				</div>
				<div class="col-md-6">
				<label class="control-label col-md-4">停泵时间</label>
				<div class="col-md-8">
									<div class="input-group stopPumpTime">
										<div class="col-md-6" style="padding-right: 0px;">
											<input style="text-align: right; border-right: 0;" id="stopPumpTime1"  class="form-control form-control-inline date-picker col-md-8" type="text" />
										</div>
										<div class="col-md-4" style="padding-left: 0px;">
											<input style="border-left: 0;" type="text" id="stopPumpTime2"  class="form-control col-md-4  timepicker timepicker-24">
										</div>
										<div class="col-md-2">
										<shiro:hasPermission name="ABACKFLOWPLANUPDATE">
										<button type="button"  class="btn btn-primary" id="savePumpTime">保存</button>
										</shiro:hasPermission>
										</div>
								</div>
				</div>
				</div>
				</div>
			</div>
			<h4 class="form-section">工艺流程</h4>

			<div class="form-group">

				<div class="col-md-12">
					<div id="contentDiv" class="content"
						style="border: 1px solid rgb(216, 216, 216); position: relative; overflow-x: hidden; overflow-y: auto; cursor: move; width: 631px; height: 350px; margin: 0 auto;">
						<div id="toolbarContainer"
							style="overflow: hidden; width: 100px; height: 20px; margin-top: 0px; z-index: 999; border: 1px solid #d8d8d8; border-radius: 3px;"></div>
						<div id="graphContainer"
							style="overflow: hidden; width: 100%; height: 320px; margin-top: 0px;"></div>
					</div>
				</div>
			</div>

			<div class="form-group">
				<div class="col-md-6" style="text-align: right;">
					<label class="control-label " style="padding-top: 0px;">打循环目的：</label>
					<label class="control-label"
						style="text-align: left; padding-top: 0px;" id="shipName"></label>
					<label class="control-label"
						style="text-align: left; padding-top: 0px;">卸货结束打循环</label> <label
						class="control-label" style="text-align: left; padding-top: 0px;">(罐空容为</label>
				</div>
				<input class="col-md-1"
					style="padding-left: 0px; padding-right: 0px; border: 0; float: left; width: 110px; text-align: center;"
					id="flowmessage"> <label class="control-label"
					style="text-align: left; padding-top: 0px;">吨</label> <label
					class="control-label"
					style="text-align: left; padding-top: 0px; display: none;"
					id="checkMsg">，符合要求</label> <label class="control-label"
					style="text-align: left; padding-top: 0px;">)</label>
			</div>
			<input type="hidden" id="infoId">
			<div class="form-group createDiv">
				<div class="col-md-6">
					<label class="control-label col-md-4">制定人</label>
					<div class="col-md-8">
						<input type="text" id="createUserId" readonly class="form-control" />
					</div>
				</div>
				<div class="col-md-6">
					<label class="control-label col-md-4">制定日期</label>
					<div class="col-md-8">
						<input style="text-align: left;" id="createTime"
							class="form-control form-control-inline date-picker col-md-8"
							type="text" readonly />
					</div>
				</div>
			</div>



			<div class="form-group reviewDiv">
				<div class="col-md-6">
					<label class="control-label col-md-4">品质审批人</label>
					<div class="col-md-8">
						<input type="text" id="reviewUserId" readonly class="form-control" />
					</div>
				</div>
				<div class="col-md-6">
					<label class="control-label col-md-4">品质审批日期</label>
					<div class="col-md-8">
						<input style="text-align: left;" id="reviewTime"
							class="form-control form-control-inline date-picker col-md-8"
							type="text" readonly />
					</div>
				</div>
			</div>



			<div class="form-group reviewCraftDiv">
				<div class="col-md-6">
					<label class="control-label col-md-4">工艺审批人</label>
					<div class="col-md-8">
						<input type="text" id="reviewCraftUserId" readonly
							class="form-control blength" />
					</div>
				</div>
				<div class="col-md-6">
					<label class="control-label col-md-4">工艺审批日期</label>
					<div class="col-md-8">
						<input style="text-align: left;" id="reviewCraftTime"
							class="form-control form-control-inline date-picker col-md-8"
							type="text" readonly />
					</div>
				</div>
			</div>
			<h4 class="form-section">
				作业注意事项&nbsp;<a href="javascript:void(0)"
					onclick="InboundOperation.openWarning(1);"><i
					class="fa fa-bell-o"></i></a>
			</h4>

			<div class="form-group dialog-warning1" hidden="true">
				<div class="col-md-11 col-md-offset-1">
					<h4>&nbsp;调度室:</h4>
					<h5>
						<p>&nbsp;&nbsp;1、倒罐前做好高位报警及阀门联锁测试</p>
						<p>&nbsp;&nbsp;2、通过SCADA系统做好倒出、倒入储罐液位监控工作</p>
						<p>&nbsp;&nbsp;3、对倒罐工艺线管上连接的相关储罐的液位集中监控</p>
						<p>&nbsp;&nbsp;4、做好系统液位和计量人工检测数据的对比</p>
						<p>&nbsp;&nbsp;5、控制倒罐作业过程中的流量，不得超过呼吸阀的通气能力</p>
					</h5>
					<h4>&nbsp;发货操作工:</h4>
					<h5>
						<p>&nbsp;&nbsp;1、正确佩戴劳保用品;</p>
						<p>&nbsp;&nbsp;2、作业前确认主管线泄压罐及管线阀门状态;</p>
						<p>&nbsp;&nbsp;3、作业中对泵的压力情况加强检查及时汇报调度</p>
						<p>&nbsp;&nbsp;4、加强对储罐呼吸阀的检查;</p>
						<p>&nbsp;&nbsp;5、倒罐结束后严格按照调度命令关闭阀门</p>
						<p>&nbsp;&nbsp;6、若是码头打回流,码头作业人员应注意管线压力的变化，及时与调度联系</p>
						<p>&nbsp;&nbsp;4、加强对储罐呼吸阀的检查;</p>
					</h5>
				</div>
			</div>
			<shiro:hasPermission name="ABACKFLOWPLANMODIFYSC">
				<div class="form-body" style="padding-bottom: 20px;">
					<h6 class="form-section col-md-12  securityCodeDiv"
						style="text-align: right;">
						验证码审批<a onclick="InboundOperation.openHide(this,5);"
							href="javascript:void(0)"> <i class="fa fa-chevron-left"></i></a>
					</h6>
				</div>
			</shiro:hasPermission>
		</form>
		<!-- END FORM-->

		<div class="modal-footer firDiv">
			<button type="button" class="btn btn-default"
				onclick="javascript:history.go(-1);">返回</button>
			<shiro:hasPermission name="ABACKFLOWPLANUPDATE">
				<button type="button" class="btn btn-primary" id="reset">重置</button>
				<button type="button" key="0" class="btn btn-primary save">保存</button>
				<button type="button" key="1" class="btn btn-primary save">提交</button>
			</shiro:hasPermission>
		</div>
		<div class="modal-footer secDiv">
			<button type="button" class="btn btn-default"
				onclick="javascript:history.go(-1);">返回</button>
			<shiro:hasPermission name="ABACKFLOWPLANMODIFY">
				<button type="button" key="-1" class="btn btn-primary modify">保存</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="ABACKFLOWPLANVERIFYQUALITY">
				<button type="button" key="4" class="btn btn-primary save">品质通过</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="ABACKFLOWPLANVERIFYCRAFT">
				<button type="button" key="5" class="btn btn-primary save">工艺通过</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="ABACKFLOWPLANVERIFY">
				<button type="button" key="2" class="btn btn-primary save">通过</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="ABACKFLOWPLANVERIFY">
				<button type="button" key="3" class="btn btn-primary save">不通过</button>
			</shiro:hasPermission>
		</div>
		<!-- 调度验证码审批 -->
		<shiro:hasPermission name="ABACKFLOWPLANMODIFYSC">
			<div class=" modal-footer dialog-warning5" hidden="true">
				<div class=" form-horizontal col-md-12" style="padding-right: 0px;">
					<div class="input-group col-md-6 col-md-offset-6"
						style="float: right;">
						<select id="verigy" class="form-control"
							onclick="SecurityCode.verigy(this)" readonly
							style="width: 150px; float: right;">
							<option value="ABACKFLOWPLANVERIFY">通过</option>
							<option value="ABACKFLOWPLANVERIFYQUALITY">品质通过</option>
							<option value="ABACKFLOWPLANVERIFYCRAFT">工艺通过</option>
						</select> <span class="input-group-btn">
							<button type="button"
								style="width: 100px; text-align: center; margin-right: 20px;"
								disabled class="btn btn-default">审批类型</button>
						</span> <input type="text" class="form-control"
							style="width: 150px; float: right;" code="ABACKFLOWPLANVERIFY"
							id="reviewCodeUserId"> <span class="input-group-btn">
							<button type="button"
								style="width: 100px; text-align: center; margin-right: 20px;"
								disabled class="btn btn-default">审批人</button>
						</span> <label id="securityCodeContent" class="hidden"></label> <input
							type="text" class="form-control"
							style="width: 200px; float: right;" placeholder='请输入验证码'
							id="securityCode"> <span class="input-group-btn">
							<button type="button" style="width: 100px; text-align: center;"
								class="btn btn-primary" data="0" code="ABACKFLOWPLANVERIFY"
								onClick="SecurityCode.init(this)" id="securityCodeBtn">发送验证码</button>
						</span>
					</div>
					<div class="form-group col-md-6 col-md-offset-6"
						style="margin-top: 20px; float: right;">
						<button type="button" class="btn btn-default"
							onclick="javascript:history.go(-1);">返回</button>
						<button type="button" key="4" class="btn btn-primary save"
							code="ABACKFLOWPLANVERIFYQUALITY">品质通过</button>
						<button type="button" key="5" class="btn btn-primary save"
							code="ABACKFLOWPLANVERIFYCRAFT">工艺通过</button>
						<button type="button" key="2" class="btn btn-primary save"
							code="ABACKFLOWPLANVERIFY">通过</button>
						<button type="button" key="3" class="btn btn-primary save">不通过</button>
					</div>
				</div>
			</div>
		</shiro:hasPermission>
		<div class="modal-footer nfDiv" style="display: none">
			<button type="button" class="btn btn-default"
				onclick="javascript:history.go(-1);">返回</button>
			<shiro:hasPermission name="ABACKFLOWPLANMODIFY">
				<button type="button"  key='-1' class="btn btn-primary reback">回退</button>
			</shiro:hasPermission>
		</div>
	</div>
</div>
<script>
	$(function() {
		InboundOperation.initBackFlowPlan($(".backflowplan"), 1);
	});
	util.initTimePicker($(".backflowplan"));
</script>