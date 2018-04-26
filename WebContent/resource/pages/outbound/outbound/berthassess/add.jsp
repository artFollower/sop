<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<style type="text/css">
.modal-dialog {
	margin: 30px auto;
	width: 65%;
}
</style>
<div class="modal fade">
	<div class="modal-dialog modal-wide">
		<div class="modal-content" style="width:100%;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title">评估信息</h4>
			</div>
			<div class="modal-body"
				style="padding-left: 45px; padding-right: 65px;">
				<!-- BEGIN FORM-->
				<form class="form-horizontal ">
				<label class="hidden" id="berthAssessId"></label>
				<label class="hidden" id="arrivalId"></label>
					<div class="form-body berthassess">
						<div class="form-group">
							<label class="control-label col-md-2">拟靠泊位号<span
								class="required">*</span></label>
							<div class="col-md-4">
								<input class="form-control" id="berthId" data-required="1">
							</div>
							<label class="control-label col-md-2">天气情况</label>
							<div class="col-md-4">
								<input class="form-control " maxlength="16" id="weather"> <input
									type="hidden" name="id" id="id" value="0">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-2">风力</label>
							<div class="col-md-4">
								<input class="form-control " maxlength="16" id="windPower">
							</div>
							<label class="control-label col-md-2">风向</label>
							<div class="col-md-4">
								<input class="form-control" maxlength="16" id="windDirection">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">申请原因</label>
							<div class="col-md-10">
								<textarea class="form-control description" rows="3" id="reason" maxlength="250"
									data-required="1"></textarea>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">安全措施</label>
							<div class="col-md-10">
								<textarea class="form-control description" rows="5" maxlength="250"
									id="security" data-required="1">
1、当天东南风风力超过5级停止靠泊;
2、靠泊时调度及码头负责人要到现场;
3、靠泊后较平时前后各增加一根缆绳,共计10根缆绳;
4、作业过程中调度加强与船方联系;
5、作业现场加强值班,关注潮位,提醒船方及时调整缆绳松紧;
6、落实一条消拖两用拖轮协助靠泊。</textarea>
							</div>
						</div>
						<div class="form-group commentDiv">
							<label class="col-md-2 control-label">评估组意见</label>
							<div class="col-md-10">
								<textarea class="form-control description" maxlength="250" rows="3" id="comment"></textarea>
							</div>
						</div>
					</div>
					<div class="form-body">
						<div class="form-group createDiv">
							<label class="control-label col-md-2">制定人</label>
							<div class="col-md-4">
								<input type="text" id="createUserId" readonly
									class="form-control bpwater" />
							</div>
							<label class="control-label col-md-2">制定日期</label>
							<div class="col-md-4">
								<input type="text" class="form-control date date-picker"
									id="createTime" readonly>
							</div>
						</div>
						<div class="form-group commentsDiv"></div>
						<div class="form-group reviewDiv">
							<label class="control-label col-md-2">审批人</label>
							<div class="col-md-4">
								<input type="text" id="reviewUserId" readonly
									class="form-control blength" />
							</div>
							<label class="control-label col-md-2">审批日期</label>
							<div class="col-md-4">
								<input type="text" class="form-control date date-picker"
									id="reviewTime" readonly>
							</div>
						</div>
					</div>
				</form>
				<shiro:hasPermission name="AOUTBOUNDASSESSVERIFYSC">
					<div class="form-body" style="padding-bottom:20px;">
					<h6 class="form-section col-md-12  securityCodeDiv" style="text-align:right;" >验证码审批<a onclick="InboundOperation.openHide(this,4);" href="javascript:void(0)">
					<i class="fa fa-chevron-left"></i></a></h6>
					</div>
					</shiro:hasPermission>
			</div>


			<div class="modal-footer firDiv">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<shiro:hasPermission name="AOUTBOUNDASSESSADD">
					<button type="button" key="0" class="btn btn-primary" id="save">保存</button>
					<button type="button" key="1" class="btn btn-primary" id="submit">提交</button>
				</shiro:hasPermission>
			</div>

			<div class="modal-footer secDiv">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<shiro:hasPermission name="AOUTBOUNDASSESSVERIFY">
					<button type="button" key="2" class="btn btn-primary" id="save">通过</button>
					<button type="button" key="3" class="btn btn-primary" id="submit">不通过</button>
				</shiro:hasPermission>
			</div>
			<!-- 调度验证码审批 -->
			<shiro:hasPermission name="AOUTBOUNDASSESSVERIFYSC">
			<div class=" modal-footer  dialog-warning4" hidden="true" >
			<div class=" form-horizontal col-md-12" style="padding-right: 0px;">
			<div class="input-group col-md-6 col-md-offset-6" style="float:right;">
							 <input type="text" class="form-control" style="width:150px;float:right;" code="AOUTBOUNDASSESSVERIFY"   id="reviewCodeUserId" >	
                                 <span class="input-group-btn">
                                 <button type="button" style="width:100px;text-align:center;" disabled class="btn btn-default">审批人</button>
                                 </span>
                                 <label id="securityCodeContent" class="hidden"></label>	
                                <input type="text" class="form-control" style="width:200px;float:right;" placeholder='请输入验证码' id="securityCode" >	
                                 <span class="input-group-btn">
                                 <button type="button" style="width:100px;text-align:center;" class="btn btn-primary" data="0" code="AOUTBOUNDASSESSVERIFY" onClick="SecurityCode.init(this)" id="securityCodeBtn">发送验证码</button>
                                 </span>
                              </div>
             <div class="form-group col-md-6 col-md-offset-6" style="margin-top:20px;float:right;" >
             <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			 <button type="button" key="2" code="AOUTBOUNDASSESSVERIFY" class="btn btn-primary" id="save">通过</button>
			 <button type="button" key="3" code="AOUTBOUNDASSESSVERIFY" class="btn btn-primary" id="submit">不通过</button>
             </div>
             </div>
			 </div>
			 </shiro:hasPermission>
		</div>
	</div>
</div>

