<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="portlet box grey">
	<div class="portlet-title hidden">
		<div class="caption">添加接卸方案</div>
	</div>
	<div class="portlet-body form unloadingplan">
		<!-- BEGIN FORM-->
		<form action="#" class="form-horizontal">
			<div class="form-body">
				<h4 class="form-section">基本信息</h4>
				<div class="form-group">
					<div class="col-md-6">
						<label class="control-label col-md-4">使用罐号</label>
						<div class="col-md-8">
							<input type="text" id="tankIds" data="" readonly class="form-control"  />
						</div>
					</div>
					<div class="col-md-6">
						<label class="control-label col-md-4">使用管线</label>
						<div class="col-md-8">
							<input type="text" id="tubeIds" readonly data=""  class="form-control" />
						</div>
					</div>
						</div>

					<div class="col-md-6 hidden">
						<label class="control-label col-md-4">计划泊位<span class="required">*</span></label>
						<div class="col-md-8">
							<input type="text" data-required="1" class="form-control"  id="berthName"/>
							<label class="hidden" id="currentStatus"></label>
						</div>
					</div>

				<h4 class="form-section">工艺流程</h4>
               
				<div class="form-group">
				          
					<div class="col-md-12">
					<div id="contentDiv" class="content" style="border: 1px solid rgb(216, 216, 216); position: relative; overflow-x: hidden; overflow-y: auto; cursor: move; width: 631px; height: 350px; margin: 0 auto;">
					<div id="toolbarContainer" style="overflow: hidden; width: 100px; height: 20px; margin-top: 0px; z-index: 999; border: 1px solid #d8d8d8; border-radius: 3px;"></div>
					<div id="graphContainer" style="overflow: hidden; width: 100%; height: 320px; margin-top: 0px;"></div>
						</div>
					</div>
				</div>
                 <div class="form-group">
                 <div class="col-md-12 col-md-offset-3">
               <label style="padding-top: 0px;" class="control-label col-md-2">(进货储罐空容</label>
                <input class="col-md-1"  style="padding-left:0px;padding-right:0px;border:0;float:left; width: 110px;text-align:center;" id="unloadingmsg"> 
                <label class="control-label" style="text-align:left;padding-top: 0px;">吨</label>  
                <label  style="display:none;text-align:left;padding-top: 0px;" class="control-label" id="checkMsg">,符合要求</label>  
                <label class="control-label" style="text-align:left;padding-top: 0px;">)</label>   
                </div></div>

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
							<input style="text-align:left;" id="createTime" disabled="disabled"  class="form-control form-control-inline date-picker col-md-8"  type="text" readonly/>
						</div>
					</div>
					</div>



				<div class="form-group reviewDiv">
					<div class="col-md-6">
						<label class="control-label col-md-4">品质审批人</label>
						<div class="col-md-8">
							<input type="text" id="reviewUserId" readonly  class="form-control" />
						</div>
					</div>
					<div class="col-md-6">
						<label class="control-label col-md-4">品质审批日期</label>
						<div class="col-md-8">
							<input style="text-align:left;" id="reviewTime" readonly  disabled="disabled" class="form-control form-control-inline date-picker col-md-8"  type="text" readonly/>
						</div>
					</div>
					</div>



				<div class="form-group reviewCraftDiv">
					<div class="col-md-6">
						<label class="control-label col-md-4">工艺审批人</label>
						<div class="col-md-8">
							<input type="text" id="reviewCraftUserId" readonly  class="form-control blength" />
						</div>
					</div>
					<div class="col-md-6">
						<label class="control-label col-md-4">工艺审批日期</label>
						<div class="col-md-8">
							<input style="text-align:left;" id="reviewCraftTime" disabled="disabled"  readonly class="form-control form-control-inline date-picker col-md-8"  type="text" readonly/>
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
			<input type="hidden" id="flow" class="form-control">
			<input type="hidden" id="svg" class="form-control">
			</div>
			<shiro:hasPermission name="AUNLOUNDINGPROGRAMVERIFYSC">
			<div class="form-body" style="padding-bottom:20px;">
					<h6 class="form-section col-md-12  securityCodeDiv" style="text-align:right;" >验证码审批<a onclick="InboundOperation.openHide(this,5);" href="javascript:void(0)">
					<i class="fa fa-chevron-left"></i></a></h6>
					</div>
					</shiro:hasPermission>
		</form>
		<!-- END FORM-->

		<div class="modal-footer firDiv">
			<button type="button" class="btn btn-default"  onclick="javascript:history.go(-1);">返回</button>
			<shiro:hasPermission name="AUNLOUNDINGPROGRAMUPDATE">
			<button type="button" class="btn btn-primary" id="reset">重置</button>
			<button type="button" key="0" class="btn btn-primary save">保存</button>
			<button type="button" key="1" class="btn btn-primary save">提交</button>
			</shiro:hasPermission>
		</div>
        <div class="modal-footer secDiv">
			<button type="button" class="btn btn-default"  onclick="javascript:history.go(-1);">返回</button>
			<shiro:hasPermission name="AUNLOUNDINGPROGRAMVERIFYQUALITY">
			<button type="button" key="4" class="btn btn-primary save">品质通过</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="AUNLOUNDINGPROGRAMVERIFYCRAFT">
			<button type="button" key="5" class="btn btn-primary save" >工艺通过</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="AUNLOUNDINGPROGRAMVERIFY">
			<button type="button" key="2" class="btn btn-primary save">通过</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="AUNLOUNDINGPROGRAMVERIFYNO">
			<button type="button" key="3" class="btn btn-primary save" >不通过</button>
			</shiro:hasPermission>
		</div>
		<!-- 调度验证码审批 -->
			<shiro:hasPermission name="AUNLOUNDINGPROGRAMVERIFYSC">
			<div class=" modal-footer dialog-warning5" hidden="true">
			<div class=" form-horizontal col-md-12" style="padding-right: 0px;">
					<div class="input-group col-md-6 col-md-offset-6" style="float: right;">
					   <select id="verigy" class="form-control" onclick="SecurityCode.verigy(this)" readonly style="width: 150px; float: right;">
					   <option value="AUNLOUNDINGPROGRAMVERIFY">通过</option>
					   <option value="AUNLOUNDINGPROGRAMVERIFYQUALITY">品质通过</option>
					   <option value="AUNLOUNDINGPROGRAMVERIFYCRAFT">工艺通过</option>
						 </select>
							 <span class="input-group-btn">
							 <button type="button" style="width: 100px; text-align: center;margin-right:20px;"
								disabled class="btn btn-default">审批类型</button></span>
						<input type="text" class="form-control" style="width: 150px; float: right;"
							code="AUNLOUNDINGPROGRAMVERIFY" id="reviewCodeUserId">
							 <span class="input-group-btn">
							<button type="button" style="width: 100px; text-align: center;margin-right:20px;"
								disabled class="btn btn-default">审批人</button></span>
						 <label id="securityCodeContent" class="hidden"></label> <input
							type="text" class="form-control"
							style="width: 200px; float: right;" placeholder='请输入验证码'
							id="securityCode"> <span class="input-group-btn">
							<button type="button" style="width: 100px; text-align: center;"
								class="btn btn-primary" data="0" code="AUNLOUNDINGPROGRAMVERIFY"
								onClick="SecurityCode.init(this)" id="securityCodeBtn">发送验证码</button>
						</span>
					</div>
					<div class="form-group col-md-6 col-md-offset-6" style="margin-top:20px;float:right;" >
             <button type="button" class="btn btn-default"  onclick="javascript:history.go(-1);">返回</button>
			<button type="button" key="4" class="btn btn-primary save" code="AUNLOUNDINGPROGRAMVERIFYQUALITY" >品质通过</button>
			<button type="button" key="5" class="btn btn-primary save" code="AUNLOUNDINGPROGRAMVERIFYCRAFT" >工艺通过</button>
			<button type="button" key="2" class="btn btn-primary save" code="AUNLOUNDINGPROGRAMVERIFY" >通过</button>
			<button type="button" key="3" class="btn btn-primary save"  >不通过</button>
             </div>
             </div>
			 </div>
			 </shiro:hasPermission>
	</div>
</div>
<script>
$(function(){
	InboundOperation.initTransportProgram($(".unloadingplan"),1);
});
	util.initTimePicker($(".unloadingplan"));
</script>