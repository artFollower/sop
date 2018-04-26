<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="portlet box grey">
	<div class="portlet-title hidden">
		<div class="caption">查看接卸方案</div>
	</div>
	<div class="portlet-body form unloadingplan">
		<!-- BEGIN FORM-->
		<form action="#" class="form-horizontal">
			<div class="form-body">
				<h4 class="form-section">基本信息</h4>

				<div class="form-group">
					<div class="col-md-6">
						<label class="control-label col-md-4">使用罐号:</label>
						<div class="col-md-8">
							<label class="control-label" id="tankNames"></label>
						</div>
					</div>
					<div class="col-md-6">
						<label class="control-label col-md-4">使用管线:</label>
						<div class="col-md-8">
							<label class="control-label" id="tubeNames"></label>
						</div>
					</div>
						</div>

<!-- 				<div class="form-group"> -->
<!-- 					<div class="col-md-6"> -->
<!-- 						<label class="control-label col-md-4">预计靠泊时间:</label> -->
<!-- 						<div class="col-md-8"> -->
<!-- 							<label class="control-label " id="anchorTime"></label> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 					<div class="col-md-6"> -->
<!-- 						<label class="control-label col-md-4">计划泊位:</label> -->
<!-- 						<div class="col-md-8"> -->
<!-- 							<label class="control-label" id="berthName"></label> -->

<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->

				<h4 class="form-section">工艺流程</h4>

				<div class="form-group">
					<div class="col-md-12">
						<div id="contentDiv" class="content" style="border: 1px solid rgb(216, 216, 216); position: relative; overflow-x: hidden; overflow-y: auto; cursor: move; width: 631px; height: 350px; margin: 0 auto;">
						</div>
					</div>
				</div>
                <div class="form-group">
                 <div class="col-md-12 col-md-offset-3">
               <label style="padding-top: 0px;" class="control-label col-md-2">(进货储罐空容</label>
                <input class="col-md-1" style="padding-left:0px;padding-right:0px;border:0;float:left; width: 110px;text-align:center;" id="unloadingmsg"> 
                <label class="control-label" style="text-align:left;padding-top: 0px;">吨</label>  
                <label  style="display:none;text-align:left;padding-top: 0px;" class="control-label" id="checkMsg">,符合要求</label>  
                <label class="control-label" style="text-align:left;padding-top: 0px;">)</label>   
                </div></div>


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

				<div class="form-group" >
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
							<p>&nbsp;&nbsp;1、做好接卸前高位报警及阀门联锁测试</p>
							<p>&nbsp;&nbsp;2、通过SCADA系统做好卸货储罐液位监控工作</p>
							<p>&nbsp;&nbsp;3、对接卸管线上连接的相关储罐的液位集中监控</p>
							<p>&nbsp;&nbsp;4、做好系统液位和计量人工监测数据的对比</p>
							<p>&nbsp;&nbsp;5、控制卸货过程中的流量，不得超过呼吸阀的通气能力</p>
							<p>&nbsp;&nbsp;6、对同时接卸多品种的作业，在连接软管时要认真做好检查确认工作</p>
						</h5>
						<h4>&nbsp;动力班:</h4>
						<h5>
							<p>&nbsp;&nbsp;1、正确佩戴个人防护用品</p>
							<p>&nbsp;&nbsp;2、储罐应开进口阀，泄压阀应关闭</p>
							<p>&nbsp;&nbsp;3、卸货时检查罐呼吸阀是否正常，如有异常及时与调度联系</p>
							<p>&nbsp;&nbsp;4、卸货过程中加强对罐及管线的检查，及时与调度联系</p>
						</h5>
						<h4>&nbsp;码头:</h4>
						<h5>
							<p>&nbsp;&nbsp;1、正确佩戴个人防护用品</p>
							<p>&nbsp;&nbsp;2、加强船岸检查</p>
							<p>&nbsp;&nbsp;3、关注船舶干舷</p>
							<p>&nbsp;&nbsp;4、注意风力变化及关注潮水及缆绳情况</p>
							<p>&nbsp;&nbsp;5、作业过程中要加强对卸货管线的巡回检查工作</p>
						</h5>
					</div>
				</div>
            <div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal" onclick="javascript:history.go(-1);">返回</button>
            <shiro:hasPermission name="ABACKSTATUS">
            <button type="button" class="btn btn-primary" onclick="InboundOperation.cleanToStatus(5)">回退到当前状态</button>
            </shiro:hasPermission>
		</div>
			</div>
			
		</form>
		<!-- END FORM-->

		

	</div>
</div>
<script>
	$(function() {
		InboundOperation.initTransportProgram($(".unloadingplan"),2);
	});
</script>