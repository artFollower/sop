<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="portlet box grey">
	<div class="portlet-body form berthPlanDiv">
		<form class="form-horizontal">
			<div class="form-body">
			    <shiro:hasPermission name="AOUTBOUNDASSESSADD"><button type="button" class="btn btn-info" id="berthAssess">靠泊评估</button></shiro:hasPermission>
					<div class="form-group">
					<label class="hidden" id="berthProgramId"></label>
					<div class="col-md-12">
					<h4 style="text-align:center">码头泊位接收指南和限制条件</h4>
					<div  data-role="berthGrid" style="width:100%"></div>
					<div class="btn-group buttons isChoiceBerth">
							<button class="btn btn-default btn-circle mar-r-10 check" id="checkBerth" type="button">
								<span class="fa fa-edit"></span>确定
							</button>
						</div>
					</div>
					</div>
                <div class="form-group">
                 <div class="col-md-12">
                 <label class="control-label col-md-2">富裕水深(米)</label>
                 <div class="col-md-4">
                 <input class="form-control"  onkeyup="config.clearNoNum(this)" maxlength="4" id="richDraught">
                 </div>
                 <label class="control-label col-md-2">现场风向风力(级)</label>
                 <div class="col-md-4">
                 <input class="form-control"   maxlength="100" id="windPower">
                 </div>
                 </div>
                 </div>
                 
                 <div class="form-group">
					<div class="col-md-12">
						<label class="col-md-2 control-label">泊位概况</label>
						<div class="col-md-10">
							<textarea class="form-control" rows="5" id="description" disabled="disabled"></textarea>
						</div>
					</div>
				</div>
                    
				<div class="form-group">
					<div class="col-md-12">
						<label class="col-md-2 control-label">安全措施&nbsp;<!-- <a href="javascript:void(0);"
									onclick="util.dialogShowLog(this,20,$('#safeInfo'))"><i
									class="fa fa-file-text-o"></i></a> &nbsp;<a
									href="javascript:void(0);" onclick="util.saveLog(this,20,$('#safeInfo'))"><i
									class="fa fa-floppy-o"></i></a> --></label>
						<div class="col-md-10">
							<textarea class="form-control description" rows="5" id="safeInfo"> 
						</textarea>
						</div>
					</div>
				</div>
				<div class="form-group commentDiv">
					<div class="col-md-12">
						<label class="col-md-2 control-label">审核意见</label>
						<div class="col-md-10">
							<textarea class="form-control description" maxlength="100" rows="1" id="comment"></textarea>
						</div>
					</div>
				</div>

				<div class="form-group createDiv" >
					<div class="col-md-6">
						<label class="control-label col-md-4">制定人</label>
						<div class="col-md-8">
							<input type="text" id="createUserId" readonly class="form-control bpwater" />
						</div>
					</div>
					<div class="col-md-6">
						<label class="control-label col-md-4">制定日期</label>
						<div class="col-md-8">
							<input style="text-align:left;" id="createTime"  class="form-control form-control-inline date-picker col-md-8"  type="text" readonly/>
						</div>
					</div>
					</div>

				<div class="form-group reviewDiv">
					<div class="col-md-6">
						<label class="control-label col-md-4">审批人</label>
						<div class="col-md-8">
							<input type="text" id="reviewUserId" readonly  class="form-control blength" />
						</div>
					</div>
					<div class="col-md-6">
						<label class="control-label col-md-4">审批日期</label>
						<div class="col-md-8">
							<input style="text-align:left;" id="reviewTime"  class="form-control form-control-inline date-picker col-md-8"  type="text" readonly/>
						</div>
					</div>
				</div>
			</div>
			<shiro:hasPermission name="AOUTBOUNDBERTHINGVERIFYSC">
			<div class="form-body" style="padding-bottom:20px;">
					<h6 class="form-section col-md-12  securityCodeDiv" style="text-align:right;" >验证码审批<a onclick="InboundOperation.openHide(this,5);" href="javascript:void(0)">
					<i class="fa fa-chevron-left"></i></a></h6>
					</div>
					</shiro:hasPermission>
		</form>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"  onclick="javascript:history.go(-1);">返回</button>
				<!-- <button type="button" class="btn btn-primary" id="reset" >重置</button> -->
				<shiro:hasPermission name="AOUTBOUNDBERTHINGUPDATE"><button type="button"  key="0"   class="btn btn-primary save"  >保存</button>
				<button type="button"  key="1"  class="btn btn-primary save" >提交</button></shiro:hasPermission>
				<shiro:hasPermission name="AOUTBOUNDBERTHINGVERIFY"><button type="button" key="2" class="btn btn-primary save"  >通过</button>
				<button type="button" key="3" class="btn btn-primary save"  >不通过</button></shiro:hasPermission>
				<button type="button" id="back" class="btn btn-primary"  >回退</button>
			</div>
			<!-- 调度验证码审批 -->
			<shiro:hasPermission name="AOUTBOUNDBERTHINGVERIFYSC">
			<div class=" modal-footer dialog-warning5" hidden="true">
			<div class=" form-horizontal col-md-12" style="padding-right: 0px;">
			<div class="input-group col-md-6 col-md-offset-6" style="float:right;">
			 <input type="text" class="form-control" style="width:150px;float:right;" code="AOUTBOUNDBERTHINGVERIFY"  id="reviewCodeUserId" >	
                                 <span class="input-group-btn">
                                 <button type="button" style="width:100px;text-align:center;" disabled class="btn btn-default">审批人</button>
                                 </span>
                                 <label id="securityCodeContent" class="hidden"></label>	
                                <input type="text" class="form-control" style="width:200px;float:right"  placeholder='请输入验证码' id="securityCode" >	
                                 <span class="input-group-btn">
                                 <button type="button" style="width:100px;text-align:center;" class="btn btn-primary" data="0" code="AOUTBOUNDBERTHINGVERIFY" onClick="SecurityCode.init(this)" id="securityCodeBtn">发送验证码</button>
                                 </span>
                              </div>
             <div class="form-group col-md-6 col-md-offset-6" style="margin-top:20px;float:right;" >
             <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			 <button type="button" key="2" code="AOUTBOUNDBERTHINGVERIFY" class="btn btn-primary save"  >通过</button>
			 <button type="button" key="3" code="AOUTBOUNDBERTHINGVERIFY" class="btn btn-primary save" >不通过</button>
             </div>
             </div>
			 </div>
			 </shiro:hasPermission>
		<!-- END FORM-->
	</div>
</div>