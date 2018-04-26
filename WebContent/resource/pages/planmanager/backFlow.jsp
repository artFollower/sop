<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="portlet">
	<div class="portlet-title">
		<div class="caption">
		<i class="fa fa-rebel"></i>
		<span class="mar-l-5 planTitle">倒罐方案</span>
		</div>
	</div>
	<div class="portlet-body form backflowDiv">
	<div class="nfDiv btn-group" style="padding-top: 0px; padding-bottom: 0px; border-top:0px;" >
	            <shiro:hasPermission name="MNOTICEBACKFLOW">
	            <button id="backFlowNotice" class="btn btn-primary" onclick="PlanManager.dialogBackFlowNotify(this);">
					<i class="icon-edit"></i>倒罐作业通知单
				</button>
				</shiro:hasPermission>
				<shiro:hasPermission name="MNOTICEDOCKCIRCULATION">
				<button id="dockBackFlowNotice" style="margin-left:4px;display:none;"   class="btn btn-primary" onclick="PlanManager.dialogDockBackFlowNotify(this);">
					<i class="icon-edit"></i>打循环作业通知单(码头)
				</button>
				</shiro:hasPermission>
				<shiro:hasPermission name="MNOTICESTORECIRCULATION">
				<button id="storeBackFlowNotice" style="margin-left:4px;display:none;" class="btn btn-primary" onclick="PlanManager.dialogStoreBackFlowNotify(this);">
					<i class="icon-edit"></i>打循环作业通知单(库区)
				</button>
				</shiro:hasPermission>
				<div class="checkbox hidden" style="margin-left:50px;float:left;">
                                 <label class="checkbox-inline"> <input
											type="checkbox"  id="isBackFlow" value="option2">打循环方案
										</label>
                              </div>
		</div>
		<form action="#" class="form-horizontal" id="backflow-form">
			<div class="form-body">
				<h4 class="form-section">基本信息</h4>

				<div class="form-group">
				<label  id="id" class="hidden"></label>
				<label  id="type" data="3" class="hidden"></label>
				<label  id="status"  class="hidden"></label>
				<label  id="currentStatus"  class="hidden"></label>
					<div class="col-md-6">
						<label class="control-label col-md-4">倒出罐号</label>
						<div class="col-md-8">
							<input type="text" id="outtankIds" data="" readonly class="form-control"  />
						</div>
					</div>
					<div class="col-md-6">
						<label class="control-label col-md-4">倒入罐号</label>
						<div class="col-md-8">
							<input type="text" id="intankIds" readonly data=""  class="form-control" />
						</div>
					</div>
						</div>
                <div class="form-group">
					<div class="col-md-6">
						<label class="control-label col-md-4">泵号</label>
						<div class="col-md-8">
							<input type="text" id="pupmIds" data="" readonly class="form-control"  />
						</div>
					</div>
					<div class="col-md-6">
						<label class="control-label col-md-4">管线号</label>
						<div class="col-md-8">
							<input type="text" id="tubeIds"  data="" readonly class="form-control" />
						</div>
					</div>
						</div>
					<div class="form-group ">
					<div class="col-md-6 ">
						<label class="control-label col-md-4">货品<span class="required">*</span></label>
						<div class="col-md-8">
							<input type="text" id="productId" data-required="1" data-type="Require"  data=""  maxlength="12"  class="form-control" />
						</div>
					</div>
					<div class="col-md-6 tankCountDiv">
						<label class="control-label col-md-4">数量(吨)<span class="required">*</span></label>
						<div class="col-md-8">
							<input type="text" id="tankCount" data-required="1" data-type="Require"  data="" onkeyup="config.clearNoNum(this,3)" maxlength="12"  class="form-control" />
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
				<label class="control-label col-md-4">停泵时间<span class="required">*</span></label>
				<div class="col-md-8">
									<div class="input-group stopPumpTime">
										<div class="col-md-6" style="padding-right: 0px;">
											<input style="text-align: right; border-right: 0;" id="stopPumpTime1"  class="form-control form-control-inline date-picker col-md-8" type="text" />
										</div>
										<div class="col-md-4" style="padding-left: 0px;">
											<input style="border-left: 0;" type="text" id="stopPumpTime2"  class="form-control col-md-4  timepicker timepicker-24">
										</div>
										<div class="col-md-2">
										<shiro:hasPermission name="ACHANGETANKPRROGRAMSAVE">
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
					<div id="contentDiv" class="content" style="border: 1px solid rgb(216, 216, 216); position: relative; overflow-x: hidden; overflow-y: auto; cursor: move; width: 631px; height: 350px; margin: 0 auto;">
					<div id="toolbarContainer" style="overflow: hidden; width: 100px; height: 20px; margin-top: 0px; z-index: 999; border: 1px solid #d8d8d8; border-radius: 3px;"></div>
							<div id="graphContainer" style="overflow: hidden; width: 100%; height: 320px; margin-top: 0px;"></div>
						</div>
					</div>
				</div>
                
                <div class="form-group">
                <div class="col-md-6" style="text-align:right;" >
                <label class="control-label" style="text-align:left;padding-top: 0px;">(罐空容为</label>
                </div>
                <input class="col-md-1"  style="padding-left:0px;padding-right:0px;border:0;float:left; width: 110px;text-align:center;" id="flowmessage"> 
                <label class="control-label" style="text-align:left;padding-top: 0px;">吨</label>
                <label class="control-label" style="text-align:left;padding-top: 0px;display:none;" id="checkMsg">，符合要求</label>
                <label class="control-label" style="text-align:left;padding-top: 0px;">)</label>
                </div>
                 <div class="form-group">
                <label class="control-label col-md-2">倒罐目的<span class="required">*</span></label>
                <div class="col-md-10">
								<textarea class="form-control"  rows="1" id="transferPurpose"></textarea>
							</div>
                </div>
                <div class="form-group">
                <label class="control-label col-md-2">备注：</label>
                <div class="col-md-10">
								<textarea class="form-control"  rows="1" id="description"></textarea>
							</div>
                </div>
                <input type="hidden" id="infoId">
				<div class="form-group createDiv">
					<div class="col-md-6">
						<label class="control-label col-md-4">制定人</label>
						<label style="text-align:left;" class="control-label col-md-8" id="createUserId"></label>
					</div>
					<div class="col-md-6">
						<label class="control-label col-md-4">制定日期</label>
						<label style="text-align:left;" class="control-label col-md-8" id="createTime"></label>
					</div>
					</div>
				<div class="form-group reviewDiv">
					<div class="col-md-6">
						<label class="control-label col-md-4">品质审批人</label>
						<label style="text-align:left;" class="control-label col-md-8" id="reviewUserId"></label>
					</div>
					<div class="col-md-6">
						<label class="control-label col-md-4">品质审批日期</label>
						<label style="text-align:left;" class="control-label col-md-8" id="reviewTime"></label>
					</div>
					</div>
				<div class="form-group reviewCraftDiv">
					<div class="col-md-6">
						<label class="control-label col-md-4">工艺审批人</label>
						<label style="text-align:left;" class="control-label col-md-8" id="reviewCraftUserId"></label>
					</div>
					<div class="col-md-6">
						<label class="control-label col-md-4">工艺审批日期</label>
						<label style="text-align:left;" class="control-label col-md-8" id="reviewCraftTime"></label>
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
				<shiro:hasPermission name="ACHANGETANKPRROGRAMSC">
				<div class="form-body ctDiv" style="padding-bottom:20px;">
					<h6 class="form-section col-md-12  securityCodeDiv" style="text-align:right;" >验证码审批<a onclick="InboundOperation.openHide(this,5);" href="javascript:void(0)">
					<i class="fa fa-chevron-left"></i></a></h6>
					</div>
					</shiro:hasPermission> 
				  <shiro:hasPermission name="ABACKFLOWTANKPRROGRAMSC">
				<div class="form-body bfDiv" style="padding-bottom:20px;">
					<h6 class="form-section col-md-12  securityCodeDiv" style="text-align:right;" >验证码审批<a onclick="InboundOperation.openHide(this,6);" href="javascript:void(0)">
					<i class="fa fa-chevron-left"></i></a></h6>
					</div>
					</shiro:hasPermission> 
		</form>

		<div class="modal-footer">
			
			<div class="ct">
			<button type="button" class="btn btn-default" onclick="javascript:history.go(-1);">返回</button>
			<shiro:hasPermission name="ACHANGETANKPRROGRAMSAVE">
			<button type="button" key="0" class="btn btn-primary save createBtn"  >保存</button>
			<button type="button" key="1" class="btn btn-primary save createBtn"  >提交</button></shiro:hasPermission>
			<shiro:hasPermission name="ACHANGETANKPRROGRAMVERIFYQUALITY">
			<button type="button" key="4" class="btn btn-primary save  reviewBtn"  >品质通过</button></shiro:hasPermission>
			<shiro:hasPermission name="ACHANGETANKPRROGRAMVERIFYCRAFT">
			<button type="button" key="5" class="btn btn-primary save  reviewBtn"  >工艺通过</button></shiro:hasPermission>
			<shiro:hasPermission name="ACHANGETANKPRROGRAMVERIFY">
			<button type="button" key="2" class="btn btn-primary save  reviewBtn"  >通过</button>
			<button type="button" key="3" class="btn btn-primary save  reviewBtn"  >不通过</button></shiro:hasPermission>
			<shiro:hasPermission name="ACHANGETANKPRROGRAMUPDATE">
			<button type="button" key="-1"  class="btn btn-primary reback  rebackBtn" >回退</button></shiro:hasPermission>
			</div>
			<div class="bf">
			<button type="button" class="btn btn-default" onclick="javascript:history.go(-1);">返回</button>
			<shiro:hasPermission name="ABACKFLOWTANKPRROGRAMSAVE">
			<button type="button" key="0" class="btn btn-primary save  createBtn"  >保存</button>
			<button type="button" key="1" class="btn btn-primary save  createBtn"  >提交</button></shiro:hasPermission>
			<shiro:hasPermission name="ABACKFLOWTANKPRROGRAMVERIFYQUALITY">
			<button type="button" key="4" class="btn btn-primary save  reviewBtn"  >品质通过</button></shiro:hasPermission>
			<shiro:hasPermission name="ABACKFLOWTANKPRROGRAMVERIFYCRAFT">
			<button type="button" key="5" class="btn btn-primary save  reviewBtn"  >工艺通过</button></shiro:hasPermission>
			<shiro:hasPermission name="ABACKFLOWTANKPRROGRAMVERIFY">
			<button type="button" key="2" class="btn btn-primary save  reviewBtn"  >通过</button>
			<button type="button" key="3" class="btn btn-primary save  reviewBtn"  >不通过</button></shiro:hasPermission>
			<shiro:hasPermission name="ABACKFLOWTANKPRROGRAMVERIFY">
			<button type="button" key="-1" class="btn btn-primary reback  rebackBtn" >回退</button></shiro:hasPermission>
			</div>
		</div>
 	<!-- 倒罐调度验证码审批 -->
			<shiro:hasPermission name="ACHANGETANKPRROGRAMSC">
			<div class=" modal-footer dialog-warning5 ct" hidden="true">
			<div class=" form-horizontal col-md-12" style="padding-right: 0px;">
			<div class="input-group col-md-6 col-md-offset-6" style="float:right;">
							<select id="verigy" class="form-control" onclick="SecurityCode.verigy(this)" readonly style="width: 150px; float: right;">
											<option value="ACHANGETANKPRROGRAMVERIFY">通过</option>
											<option value="ACHANGETANKPRROGRAMVERIFYQUALITY">品质通过</option>
											<option value="ACHANGETANKPRROGRAMVERIFYCRAFT">工艺通过</option>
										</select>
								<span class="input-group-btn">
									<button type="button" style="width: 100px; text-align: center; margin-right: 20px;" disabled class="btn btn-default">审批类型</button>
								</span>
			 					<input type="text" class="form-control reviewCodeUserId" style="width:150px;float:right;" code="ACHANGETANKPRROGRAMVERIFY"  id="reviewCodeUserId" >	
                                 <span class="input-group-btn">
                                 <button type="button" style="width:100px;text-align:center;" disabled class="btn btn-default">审批人</button>
                                 </span>
                                 <label id="securityCodeContent" class="hidden securityCodeContent"></label>
                                <input type="text" class="form-control" style="width:200px;float:right;" placeholder='请输入验证码' id="securityCode" >	
                                 <span class="input-group-btn">
                                 <button type="button" style="width:100px;text-align:center;" class="btn btn-primary" data="0" code="ACHANGETANKPRROGRAMVERIFY" onClick="SecurityCode.init(this)" id="securityCodeBtn">发送验证码</button>
                                 </span>
                              </div>
             <div class="form-group col-md-6 col-md-offset-6" style="margin-top:20px;float:right;" >
             <button type="button" class="btn btn-default"  onclick="javascript:history.go(-1);">返回</button>
			<button type="button" key="4" class="btn btn-primary save" code="ACHANGETANKPRROGRAMVERIFYQUALITY" >品质通过</button>
			<button type="button" key="5" class="btn btn-primary save" code="ACHANGETANKPRROGRAMVERIFYCRAFT" >工艺通过</button>
			<button type="button" key="2" class="btn btn-primary save" code="ACHANGETANKPRROGRAMVERIFY" >通过</button>
			<button type="button" key="3" class="btn btn-primary save"   >不通过</button>
             </div>
             </div>
			 </div>
			 </shiro:hasPermission> 
			 <!-- 打循环调度验证码审批 -->
			<shiro:hasPermission name="ACHANGETANKPRROGRAMSC">
			<div class=" modal-footer dialog-warning6 bf" hidden="true">
			<div class=" form-horizontal col-md-12" style="padding-right: 0px;">
			<div class="input-group col-md-6 col-md-offset-6" style="float:right;">
			<select id="verigy" class="form-control" onclick="SecurityCode.verigy(this)" readonly style="width: 150px; float: right;">
											<option value="ABACKFLOWTANKPRROGRAMVERIFY">通过</option>
											<option value="ABACKFLOWTANKPRROGRAMVERIFYQUALITY">品质通过</option>
											<option value="ABACKFLOWTANKPRROGRAMVERIFYCRAFT">工艺通过</option>
										</select>
				<span class="input-group-btn">
					<button type="button" style="width: 100px; text-align: center; margin-right: 20px;" disabled class="btn btn-default">审批类型</button>
				</span>
			 <input type="text" class="form-control reviewCodeUserId" style="width:150px;float:right;" code="ABACKFLOWTANKPRROGRAMVERIFY"  id="reviewCodeUserId" >	
                 <span class="input-group-btn">
                 <button type="button" style="width:100px;text-align:center;" disabled class="btn btn-default">审批人</button>
                 </span>
                  <label id="securityCodeContent" class="hidden securityCodeContent"></label>
                <input type="text" class="form-control" style="width:200px;float:right;" placeholder='请输入验证码' id="securityCode" >	
                 <span class="input-group-btn">
                 <button type="button" style="width:100px;text-align:center;" class="btn btn-primary" data="0" code="ABACKFLOWTANKPRROGRAMVERIFY" onClick="SecurityCode.init(this)" id="securityCodeBtn">发送验证码</button>
                 </span>
              </div>
             <div class="form-group col-md-6 col-md-offset-6" style="margin-top:20px;float:right;" >
             <button type="button" class="btn btn-default"  onclick="javascript:history.go(-1);">返回</button>
			<button type="button" key="4" class="btn btn-primary save" code="ABACKFLOWTANKPRROGRAMVERIFYQUALITY" >品质通过</button>
			<button type="button" key="5" class="btn btn-primary save" code="ABACKFLOWTANKPRROGRAMVERIFYCRAFT" >工艺通过</button>
			<button type="button" key="2" class="btn btn-primary save" code="ABACKFLOWTANKPRROGRAMVERIFY" >通过</button>
			<button type="button" key="3" class="btn btn-primary save"  >不通过</button>
             </div>
             </div>
			 </div>
			 </shiro:hasPermission> 
	</div>
	</div>
