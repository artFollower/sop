<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="portlet box grey">
	<div class="portlet-title">
		<div class="caption hidden">查看打循环方案</div>
	</div>
	<div class="portlet-body form backflowplan">
		<!-- BEGIN FORM-->
		<form action="#" class="form-horizontal">
			<div class="form-body">
				<h4 class="form-section">基本信息</h4>

				<div class="form-group">
					<div class="col-md-6">
						<label class="control-label col-md-4">倒出罐号:</label>
						<div class="col-md-8">
							<label class="control-label" id="tankNames"></label>
						</div>
					</div>
					<div class="col-md-6">
						<label class="control-label col-md-4">倒入罐号:</label>
						<div class="col-md-8">
							<label class="control-label" id="tubeNames"></label>
						</div>
					</div>
						</div>

				<div class="form-group">
					<div class="col-md-6">
						<label class="control-label col-md-4">泵号:</label>
						<div class="col-md-8">
							<label class="control-label " id="anchorTime"></label>
						</div>
					</div>
					<div class="col-md-6">
						<label class="control-label col-md-4">数量(吨):</label>
						<div class="col-md-8">
							<label class="control-label" id="berthName"></label>

						</div>
					</div>
				</div>

				<h4 class="form-section">工艺流程</h4>

				<div class="form-group">
					<div class="col-md-12">
						<div id="contentDiv" class="content" style="border: 1px solid #d8d8d8; border-radius: 3px; float: left; height: 200px; width: 100%;">
						</div>
					</div>
				</div>



				<div class="form-group">
					<div class="col-md-6">
						<label class="control-label col-md-4">制定人:</label>
						<div class="col-md-8">
							<label class="control-label" id="createUserName"></label>
						</div>
					</div>
					<div class="col-md-6">
						<label class="control-label col-md-4">制定日期:</label>
						<div class="col-md-8">
							<label class="control-label" id="createTime"></label>
						</div>
					</div>
						</div>

				<div class="form-group">
					<div class="col-md-6">
						<label class="control-label col-md-4">品质审批人:</label>
						<div class="col-md-8">
							<label class="control-label" id="reviewUserName"></label>
						</div>
					</div>
					<div class="col-md-6">
						<label class="control-label col-md-4">品质审批日期:</label>
						<div class="col-md-8">
							<label class="control-label" id="reviewTime"></label>
						</div>
					</div>
						</div>

				<div class="form-group">
					<div class="col-md-6">
						<label class="control-label col-md-4">工艺审批人:</label>
						<div class="col-md-8">
							<label class="control-label" id="reviewCraftUserName"></label>
						</div>
					</div>
			
					<div class="col-md-6">
						<label class="control-label col-md-4">工艺审批日期:</label>
						<div class="col-md-8">
							<label class="control-label" id="reviewCraftTime"></label>
						</div>
					</div>
				</div>

				<h4 class="form-section">作业注意事项&nbsp;<a href="javascript:void(0)" onclick="InboundOperation.openWarning(1);"><i class="fa fa-bell-o"></i></a>
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
             <input type="hidden" id="transportprogramId" class="form-control">
            <input type="hidden" id="arrivalId" class="form-control">
            <div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal" onclick="javascript:history.go(-1);">返回</button>
		</div>
			</div>
		</form>
		<!-- END FORM-->
	</div>
</div>
<script>
	$(function() {
		InboundOperation.initBackFlowPlan($(".backflowplan"),2);
	});
</script>