<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>
	<input type="hidden" class="form-control inputId"  name="inputId"/>
	<div class="portlet">
	<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-file-text-o"></i>修改合同信息
				</div>
				</div>
		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="#" class="form-horizontal contract-update-form">
				<div class="form-body">
					<table width="100%">
					<tr>
					<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">合同编号<span class="required">*</span></label>
									<div class="col-md-8">
										<input type="text" maxlength="16"  name="code" data-required="1" data-type="Require" class="form-control code" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-12" id="source"></label>
								</div>
							</td>
					</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">意向</label>
									<div class="col-md-8" id="select-intentionId">
										
									<input id="intention"  type="text" data-provide="typeahead"  data-type="Require" class="form-control intention">
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-5">合同标题<span class="required">*</span></label>
									<div class="col-md-7">
										<input type="text" maxlength="32" name="name" data-required="1" data-type="Require" class="form-control orderTitle" />
									</div>
								</div>
							</td>
						</tr>
						<tr>
						<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">贸易类型<span class="required">*</span></label>
									<div class="col-md-8" id="select-tradeType">
									<input id="tradeType" maxlength="32" type="text" data-provide="typeahead" data-required="1" data-type="Require" class="form-control tradeType">
									</div>
								</div>
							</td>
						<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-5">客户组</label>
									<div class="col-md-7" >
									<input id="clientGroupId"  type="text" data-provide="typeahead"  class="form-control clientGroupId">
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">合同类型<span class="required">*</span></label>
									<div class="col-md-8" id="select-type">
									<input id="type"  type="text" data-provide="typeahead" data-required="1" data-type="Require" class="form-control type">
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-5">客户<span class="required">*</span></label>
									<div class="col-md-7" id="client">
									<input id="clientId"  type="text" data-provide="typeahead" data-required="1" data-type="Require" class="form-control clientId">
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">货品<span class="required">*</span></label>
									<!-- <div class="col-md-8" id="select-product">
									<input id="productId" type="text" maxlength="32" data-provide="typeahead" data-required="1" data-type="Require" class="form-control productId">
									</div> -->
									<div class="col-md-8">
												  <div class="select2-container select2-container-multi form-control select2" data-required="1" data-type="Require"  id="productId"></div>
												 </div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-5">数量(吨)<span class="required">*</span></label>
									<div class="col-md-7">
										<input type="text" maxlength="9" name="name" data-required="1" data-type="Require" class="form-control quantity"  onkeyup="Contract.addNum(this,3)" onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
						</tr>
						
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">非保税仓储费(元/吨)</label>
									<div class="col-md-8">
										<input type="text" maxlength="8" class="form-control storagePrice" name="storagePrice" onkeyup="Contract.addNum(this,2)" onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
							
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-5">保税仓储费(元/吨)</label>
									<div class="col-md-7">
										<input type="text" maxlength="8" class="form-control protectStoragePrice" name="protectStoragePrice" onkeyup="Contract.addNum(this,2)" onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
							
						</tr>
						<tr>
						
						<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">码头通过费(元/吨)</label>
									<div class="col-md-8">
										<input type="text" maxlength="8" class="form-control passPrice" name="passPrice" onkeyup="Contract.addNum(this,2)" onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
						
						<td class=" col-md-6">
								<div class="form-group sTime divArrivalTime" style="display: none">
									<label class="control-label col-md-5 sArrivalTime">预计到港日期</label>
									<div class="col-md-7">
									<div class="input-group arrivalTime" >
											         <div class="col-md-12" style="padding-right: 0px;padding-left: 0px;"><input   id="arrivalTime" key="1"  class="form-control form-control-inline date-picker col-md-8 arrivalTime"  type="text" />
												 </div>
												     <div class="col-md-0"  style="padding-left: 0px;padding-right: 0px;display: none;"><input  style="border-left:0;" type="text"  id="arrivalTime1" class="form-control col-md-4  timepicker timepicker-24 arrivalTime1" > </div>
												</div>
									</div>
								</div>
							</td>
						
						</tr>
						
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">超期费(元/吨/天)</label>
									<div class="col-md-8">
										<input type="text" maxlength="8" class="form-control overtimePrice" name="overtimePrice" onkeyup="Contract.addNum(this,2)" onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-5">港口设施保安费(元/吨)</label>
									<div class="col-md-7">
										<input type="text" maxlength="8" class="form-control portSecurityPrice" name="portSecurityPrice" onkeyup="Contract.addNum(this,2)" onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
						</tr>
						
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">港务费(元/吨)</label>
									<div class="col-md-8">
										<input type="text" maxlength="8" class="form-control portServicePrice" name="portServicePrice" onkeyup="Contract.addNum(this,2)" onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-5">其他费(元)</label>
									<div class="col-md-7">
										<input type="text" maxlength="8" class="form-control otherPrice" name="otherPrice" onkeyup="Contract.addNum(this,2)" onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
						</tr>
						
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">损耗率(‰)</label>
									<div class="col-md-8">
										<input type="text" maxlength="6" class="form-control lossRate" name="lossRate" onkeyup="Contract.addNum(this)" onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-5">合同总价(元)<span class="required">*</span></label>
									<div class="col-md-7">
										<input type="text" maxlength="16" name="name" data-required="1" data-type="Require" class="form-control totalPrice"  onkeyup="config.clearNoNum(this,2)" onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
						</tr>
						
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">合同周期(天)<span class="required">*</span></label>
									<div class="col-md-8">
										<input type="text" maxlength="16" class="form-control period" data-required="1" data-type="Require" name="period" onkeyup="config.clearNoNum(this)" onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group sTime">
									<label class="control-label col-md-5">签约日期<span class="required">*</span></label>
									<div class="col-md-7">
									<div class="input-group gTime">
											         <div class="col-md-12" style="padding-right: 0px;padding-left: 0px;"><input   id="signDate1" key="1"  class="form-control form-control-inline date-picker col-md-8 signDate1"  type="text" data-required="1"/>
												 </div>
											         <div class="col-md-0"  style="padding-left: 0px;padding-right: 0px;display: none;"><input  style="border-left:0;" type="text"  id="signDate2" class="form-control col-md-4  timepicker timepicker-24 signDate2" > </div>
												</div>
										<!--  <div class="input-group date form_datetime">                                       
		                                    <input type="text" size="16" data-required="1" data-type="Require" readonly class="form-control signDate"  data-required="1">
		                                    <span class="input-group-btn">
		                                    	<button class="btn default date-set" type="button"><i class="icon-calendar"></i></button>
		                                    </span>
		                                 </div>-->
									</div>
								</div>
							</td>
							
						</tr>
						
						<tr>
							<td class=" col-md-6">
								<div class="form-group sTime">
									<label class="control-label col-md-4 sTimeText">起计日期</label>
									<div class="col-md-8">
									<div class="input-group ssTime" >
											         <div class="col-md-12" style="padding-right: 0px;padding-left: 0px;"><input   id="startDate" key="1"  class="form-control form-control-inline date-picker col-md-8 startDate"  type="text" />
												 </div>
											         <div class="col-md-0"  style="padding-left: 0px;padding-right: 0px;display: none;"><input  style="border-left:0;" type="text"  id="startDate1" class="form-control col-md-4  timepicker timepicker-24 startDate1" > </div>
												</div>
									</div>
								</div>
							</td>
							
							<td class=" col-md-6">
								<div class="form-group sTime">
									<label class="control-label col-md-5 endTimeText">截止日期</label>
									<div class="col-md-7">
									<div class="input-group eeTime" >
											         <div class="col-md-12" style="padding-right: 0px;padding-left: 0px;"><input   id="endDate" key="1"  class="form-control form-control-inline date-picker col-md-8 endDate"  type="text" />
												 </div>
											         <div class="col-md-0"  style="padding-left: 0px;padding-right: 0px;display: none;"><input  style="border-left:0;" type="text"  id="endDate1" class="form-control col-md-4  timepicker timepicker-24 endDate1" > </div>
												</div>
									</div>
								</div>
							</td>
						</tr>
						
						
						
						<tr class="create">
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">创建人</label>
									<div class="col-md-8">
										<input type="text" readonly  class="form-control createUserName" name="createUserName"  />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-5">创建日期</label>
									<div class="col-md-7">
										<input type="text" readonly  class="form-control createTime" name="createTime"  />
									</div>
								</div>
							</td>
						</tr>
						
						<tr>
							<td class=" col-md-6" colspan="2">
								<div class="form-group">
									<label class="col-md-2 control-label">特殊需求</label>
									<div class="col-md-10">
										<textarea maxlength="100" class="form-control spDes" rows="1"></textarea>
									</div>
								</div>
							</td>
						</tr>
						
						<tr>
							<td class=" col-md-6" colspan="2">
								<div class="form-group">
									<label class="col-md-2 control-label">备注</label>
									<div class="col-md-10">
										<textarea maxlength="200" class="form-control description" rows="3"></textarea>
									</div>
								</div>
							</td>
						</tr>
						
						
						
						</table>
						<div class="checkgroup" style="display: none">
						<h4 class="form-section">&nbsp;审核信息</h4>
						<table width="100%" id="checkTable">
						
						<tr class="checkContent" style="display: none">
							<td class=" col-md-6" colspan="2">
								<div class="form-group">
									<label class="col-md-2 control-label">您的审批意见</label>
									<div class="col-md-10">
										<textarea class="form-control checkDes" rows="3"></textarea>
									</div>
								</div>
							</td>
						</tr>
					</table>
					
					</div>
					
					<shiro:hasPermission name="ACONTRACTVERIFY">
					<table width="100%" id="ac" style="display: none">
					
					<tr>
						<td class=" col-md-6">
								<div class="form-group">
								<div class="control-label col-md-4"  style="padding-top: 0px;" >
									<a href="javascript:void(0)" onclick="Contract.openApproveModal(3)"  class="btn btn-primary ">提交至...</a>
									</div>
									<div class="col-md-8">
										<input type="text" data-required="1" data-type="Require"  class="form-control submitto"  readonly  />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
								<div class="control-label col-md-4"  style="padding-top: 0px;" >
									<a href="javascript:void(0)" onclick="Contract.openCopyModal()"  class="btn btn-primary ">抄送至...</a>
									</div>
									<div class="col-md-8">
										<input type="text"  class="form-control copyto"  readonly  />
									</div>
								</div>
							</td>
						
						</tr>
					
					</table>
					</shiro:hasPermission>
				</div>
				<div class="modal-footer">
					<div class="col-md-offset-3 col-md-9">
						<a href="#/contract" class="btn btn-default">返回</a>
						<shiro:hasPermission name="ACONTRACTUPDATE">
						<a href="javascript:void(0)" onclick="Contract.doEdit(0)" style="display: none" class="btn btn-primary saveButton">保存</a>
					<a href="javascript:void(0)" onclick="Contract.doApprove(2)" style="display: none" class="btn btn-primary submitButton">提交</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="ACONTRACTVERIFY">
					<!-- <a href="javascript:void(0)" onclick="Contract.openCopyModal()"  class="btn btn-primary CopyButton">抄送给...</a> -->
					<a href="javascript:void(0)" onclick="Contract.doApprove(3)" style="display: none" class="btn btn-primary submitCheckButton">通过并提交</a>
					<shiro:hasPermission name="ACONTRACTVERIFYDIR">
					<a href="javascript:void(0)" onclick="Contract.doSubmit(0,2)" style="display: none" class="btn btn-primary passButton">通过</a>
					<a href="javascript:void(0)" onclick="Contract.doSubmit(0,3)" style="display: none" class="btn btn-primary notpassButton">不通过</a>
					</shiro:hasPermission>
					</shiro:hasPermission>
					</div>
				</div>
			</form>
			<!-- END FORM-->
		</div>
	</div>
	<!-- END PAGE -->
	<div class="modal fade" id="ajax" tabindex="-1" role="basic" aria-hidden="true">
	</div>
	<!-- 页面内容开结束-->
	
</body>