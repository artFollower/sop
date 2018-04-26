<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
		<div class="portlet box grey">
			<div class="portlet-title hidden">
				<div class="caption">入库数量审核</div>
			</div>
			<div class="portlet-body form amountAffirm">
				<form action="#" class="form-horizontal">
				<div class="form-body">
				<h4 class="form-section">货主列表</h4>
				<div class="form-group col-md-6">
				<label class="control-label col-md-4" id="tp1">入库日期:</label>
				<label class="control-label col-md-8" style="text-align:left;" id="inboundTime"></label>
				</div>
				<div class="form-group">
				<div class="col-md-12">
				   <div class="btn-group buttons hidden">
							<button class="btn btn-default btn-circle mar-r-10 modifyClient" type="button">
								<span class="fa fa-edit"></span>修改
							</button>
				   </div>
				   <div class="col-md-12">
				 <div data-role="clientAmountGrid" style="width:100%"></div>
				 </div>
				 </div>
				</div>
				<div class="form-group createDiv">
				  <div class="col-md-6">
				  <label class="control-label col-md-4">制定人：</label>
									<div class="col-md-8">
										<input type="text" id="createUserId"  readonly class="form-control" data-required="1"/>
									</div>
				  </div>
				   <div class="col-md-6">
				  <label class="control-label col-md-4">制定时间：</label>
					<div class="col-md-8">
						<input type="text" id="createTime" class="form-control date date-picker" readonly data-required="1">
                       </div>
				  </div>
				  </div>
				  <div class="form-group reviewDiv">
				  <div class="col-md-6">
				  <label class="control-label col-md-4">审核人：</label>
									<div class="col-md-8">
										<input type="text" id="reviewUserId" readonly  class="form-control" data-required="1"/>
									</div>
				  </div>
				   <div class="col-md-6">
				  <label class="control-label col-md-4">审核时间：</label>
					<div class="col-md-8">
						<input type="text" id="reviewTime" class="form-control date date-picker" readonly data-required="1">
                       </div>
				  </div>
				  </div>
				  <div class="form-group">
						<div class="col-md-offset-11 col-md-1">
				   <button type="button" style="width:100px;text-align:center;" class="btn btn-primary"  code="AAMOUNTCONFIRMUPDATE" onClick="SendMsg.sendMsg(this)" id="sendMsgBtn">通知</button>
						</div>
				</div>
				</div>
				<shiro:hasPermission name="AAMOUNTCONFIRMVERIFYSC">
				<div class="form-body" style="padding-bottom:20px;">
					<h6 class="form-section col-md-12  securityCodeDiv" style="text-align:right;" >验证码审批<a onclick="InboundOperation.openHide(this,5);" href="javascript:void(0)">
					<i class="fa fa-chevron-left"></i></a></h6>
					</div>
					</shiro:hasPermission>
				</form>
				<div class="modal-footer firDiv">
					<button type="button" class="btn btn-default" data-dismiss="modal"
						onclick="javascript:history.go(-1);">返回</button>
						
						<shiro:hasPermission name="AAMOUNTCONFIRMUPDATE">
					<button type="button" class="btn btn-primary" onclick="InboundOperation.cleanToStatus(8,true)">重置</button>
					<button type="button" key="0" class="btn btn-primary" id="save">保存</button>
					<button type="button" key="1" class="btn btn-primary" id="submit">提交</button>
					</shiro:hasPermission>
				</div>
				<div class="modal-footer secDiv">
					<button type="button" class="btn btn-default" data-dismiss="modal"
						onclick="javascript:history.go(-1);">返回</button>
						<shiro:hasPermission name="AAMOUNTCONFIRMVERIFY">
					<button type="button" key="2" class="btn btn-primary" id="save">通过</button>
					<button type="button" key="3" class="btn btn-primary" id="save">不通过</button>
					</shiro:hasPermission>
				</div>
				<!-- 调度验证码审批 -->
			<shiro:hasPermission name="AAMOUNTCONFIRMVERIFYSC">
			<div class=" modal-footer dialog-warning5" hidden="true">
			<div class=" form-horizontal col-md-12" style="padding-right: 0px;">
			<div class="input-group col-md-6 col-md-offset-6" style="float:right;">
			 <input type="text" class="form-control" style="width:150px;float:right;" code="AAMOUNTCONFIRMVERIFY"  id="reviewCodeUserId" >	
                                 <span class="input-group-btn">
                                 <button type="button" style="width:100px;text-align:center;" disabled class="btn btn-default">审批人</button>
                                 </span>
                                 <label id="securityCodeContent" class="hidden"></label>	
                                <input type="text" class="form-control" style="width:200px;float:right;" placeholder='请输入验证码' id="securityCode" >	
                                 <span class="input-group-btn">
                                 <button type="button" style="width:100px;text-align:center;" class="btn btn-primary" data="0" code="AAMOUNTCONFIRMVERIFY" onClick="SecurityCode.init(this)" id="securityCodeBtn">发送验证码</button>
                                 </span>
                              </div>
             <div class="form-group col-md-6 col-md-offset-6" style="margin-top:20px;float:right;" >
             <button type="button" class="btn btn-default"   onclick="javascript:history.go(-1);">关闭</button>
			 <button type="button" key="2" code="AAMOUNTCONFIRMVERIFY" class="btn btn-primary" id="save">通过</button>
			 <button type="button" key="3" code="AAMOUNTCONFIRMVERIFY" class="btn btn-primary" id="submit">不通过</button>
             </div>
             </div>
			 </div>
			 </shiro:hasPermission>
			</div>
		</div>
<script>
$(function(){
	InboundOperation.initAmountAffirm($(".amountAffirm"),1);
});

$(".form_datetime").datetimepicker({
	autoclose : true,
	isRTL : Metronic.isRTL(),
	format : "yyyy-mm-dd hh:ii:ss",
	pickerPosition : (Metronic.isRTL() ? "bottom-right" : "bottom-left")
});
</script>